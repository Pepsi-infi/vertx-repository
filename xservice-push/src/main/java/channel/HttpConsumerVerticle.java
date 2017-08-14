package channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import constant.PushConsts;
import constant.ServiceUrlConstant;
import domain.MsgRecord;
import enums.ErrorCodeEnum;
import enums.MsgStatusEnum;
import enums.PushTypeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import iservice.DeviceService;
import iservice.MsgStatService;
import iservice.dto.DeviceDto;
import iservice.dto.MsgStatDto;
import result.ResultData;
import service.ApplePushService;
import service.GcmPushService;
import service.MsgRecordService;
import service.RedisService;
import service.SocketPushService;
import service.XiaoMiPushService;
import util.DateUtil;
import util.JsonUtil;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;
import xservice.HttpClientVerticle;

public class HttpConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(HttpConsumerVerticle.class);

	private SocketPushService socketPushService;

	private XiaoMiPushService xiaomiPushService;

	private GcmPushService gcmPushService;

	private RedisService redisService;

	private MsgRecordService msgRecordService;

	private MsgStatService msgStatService;

	private HttpServer httpServer;

	private Router router;

	private DeviceService deviceService;

	// 接收到的消息
	private JsonObject receiveMsg = null;

	// 上游消息id
	private String msgId;
	// 小米，GCM token
	private String token;
	// 推送类型
	private Integer channel;
	// apnsToken
	private String apnsToken;
	// 用户手机号
	private String phone;
	// 用户ID
	private Object customerId;

	private List<DeviceDto> deviceList;

	private ApplePushService applePushService;

	private boolean isUseSocket = false;

	@Override
	public void start() throws Exception {

		// 初始化化服务
		this.initService();

		// 接收消息
		this.recivedHttpMessage();

	}

	private void recivedHttpMessage() {
		httpServer = vertx.createHttpServer();
		if (httpServer == null) {
			logger.error("HttpServer is null");
			return;
		}
		router = Router.router(vertx);
		if (router == null) {
			logger.error("Router is null");
			return;
		}

		router.route().handler(BodyHandler.create());

		router.route(ServiceUrlConstant.PUSH_MSG_URL).handler(context -> {
			HttpServerResponse resp = context.response();
			HttpServerRequest request = context.request();
			String httpMsg = request.getParam("body");
			logger.info(" 接收到的消息内容：" + httpMsg);
			if (StringUtil.isNullOrEmpty(httpMsg)) {
				logger.error("请求数据为空，不做处理");
				resp.putHeader("content-type", "text/plain;charset=UTF-8")
						.end(new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(), "body is null", null).toString());
				return;
			}

			receiveMsg = new JsonObject(httpMsg);
			if (receiveMsg == null) {
				logger.error("body转换成json对象出错");
				resp.putHeader("content-type", "text/plain;charset=UTF-8")
						.end(new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(), "body is null", null).toString());
				return;
			}

			// 校验消息数据合法性
			Future checkFuture = Future.future();
			this.checkRecivedMsg(checkFuture.completer());

			// 推送给下游
			Future pushFuture = Future.future();
			this.pushMsgToDownStream(pushFuture.completer(), checkFuture);

			// 保存消息到数据库
			// this.saveMsgRecord(checkFuture);

			// 消息推送成功后，调用上报消息接口
			Future<BaseResponse> statFuture = Future.future();
			this.callStatPushMsg(statFuture.completer(), pushFuture);

			// 根据推送结果返回结果数据给http调用方
			statFuture.setHandler(statRes -> {
				if (statRes.succeeded()) {
					resp.putHeader("content-type", "text/plain;charset=UTF-8")
							.end(new ResultData<Object>(ErrorCodeEnum.SUCCESS, null).toString());
				} else {
					resp.putHeader("content-type", "text/plain;charset=UTF-8").end(
							new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(), statRes.cause().getMessage(), null)
									.toString());
				}
			});

		});

		httpServer.requestHandler(router::accept).listen(8989);
	}

	/**
	 * 已推送消息上报 //接口参见wiki :
	 * http://cowiki.01zhuanche.com/pages/viewpage.action?pageId=329268
	 */
	private void callStatPushMsg(Handler<AsyncResult<BaseResponse>> resultHandler,
			Future<AsyncResult<BaseResponse>> pushFuture) {
		pushFuture.setHandler(res -> {
			if (res.succeeded()) {

				// 推送成功的消息把msgId保存到redis,用来防止重复推送
				Future<Void> setRedisFuture = Future.future();
				this.setMsgToRedis(setRedisFuture.completer());

				// 已推送消息上报接口
				List<MsgStatDto> msgList = new ArrayList<>();
				MsgStatDto msgStatDto = new MsgStatDto();
				// 首约app乘客端 1001；首约app司机端 1002
				msgStatDto.setAppCode(PushConsts.MsgStat_APPCODE_ENGER);
				msgStatDto.setChannel(channel);
				msgStatDto.setMsgId(msgId);
				// 1 安卓
				msgStatDto.setOsType(PushConsts.MsgStat_OSTYPE_ANDROID);
				// 1发送，2接收
				msgStatDto.setAction(PushConsts.MsgStat_ACTION_SEND);
				msgStatDto.setSendTime(DateUtil.getDateTime(System.currentTimeMillis()));
				msgList.add(msgStatDto);
				msgStatService.statPushMsg(msgList, statRes -> {
					if (statRes.succeeded()) {
						String result = statRes.result();
						exeStatPushMsgResult(result, resultHandler);
					} else {
						logger.error("已推送消息上报失败", statRes.cause());
						resultHandler.handle(Future.failedFuture(statRes.cause()));
					}
				});
			} else {
				// 输出推送时的错误
				logger.error("调用推送时出错：" + pushFuture.cause());
				resultHandler.handle(Future.failedFuture(pushFuture.cause().getMessage()));
			}
		});
	}

	/**
	 * 处理返回结果
	 * 
	 * @param result
	 * @param resultHandler
	 */
	private void exeStatPushMsgResult(String result, Handler<AsyncResult<BaseResponse>> resultHandler) {
		if (StringUtils.isNotBlank(result)) {
			logger.info("exeStatPushMsgResult result :" + result);
			JsonObject json = new JsonObject(result);
			if (json != null) {
				Object status = json.getValue("status");
				Integer sts = (status != null) ? (Integer) status : null;
				if (sts != null && PushConsts.MsgStat_StatPushMsg_SUCCESS == sts) {
					logger.error("消息上报成功");
					resultHandler.handle(Future.succeededFuture());
				} else {
					logger.error("消息上报失败，msgId :" + msgId);
					resultHandler.handle(Future.failedFuture("消息上报失败，msgId :" + msgId));
				}
			} else {
				logger.error("消息上报返回结果转换json失败，msgId :" + msgId);
				resultHandler.handle(Future.failedFuture("消息上报返回结果转换json失败，msgId :" + msgId));
			}
		} else {
			logger.error("消息上报没有返回结果msgId :" + msgId);
			resultHandler.handle(Future.failedFuture("消息上报没有返回结果msgId :" + msgId));
		}
	}

	private void initService() {
		socketPushService = SocketPushService.createProxy(vertx);
		xiaomiPushService = XiaoMiPushService.createProxy(vertx);
		gcmPushService = GcmPushService.createProxy(vertx);
		msgRecordService = MsgRecordService.createProxy(vertx);
		redisService = RedisService.createProxy(vertx);
		msgStatService = MsgStatService.createProxy(vertx);
		deviceService = DeviceService.createProxy(vertx);
		applePushService = ApplePushService.createProxy(vertx);
	}

	private void checkRecivedMsg(Handler<AsyncResult<BaseResponse>> resultHandler) {
		// 校验必填项
		msgId = receiveMsg.getValue("msgId") + "";
		if (StringUtils.isBlank(msgId)) {
			resultHandler.handle(Future.failedFuture("msgId不能为空"));
			return;
		}
		// 用户id
		customerId = receiveMsg.getValue("customerId");
		if (null == customerId) {
			resultHandler.handle(Future.failedFuture("customerId不能为空"));
			return;
		}

		phone = (String) receiveMsg.getValue("phone");
		if (StringUtils.isBlank(phone)) {
			resultHandler.handle(Future.failedFuture("上送手机号不能为空"));
			return;
		}
		// sokit、gcm,小米连接token
		token = (String) receiveMsg.getValue("deviceToken");
		// // 从上游接收到的 推送类型
		// devicePushType = (String) receiveMsg.getValue("devicePushType");
		// // 转化成下游需要的推送类型
		// if (!StringUtil.isNullOrEmpty(devicePushType)) {
		// try {
		// Integer pushType = Integer.valueOf(devicePushType);
		// sendType = MsgUtil.convertCode(pushType);
		// } catch (Exception e) {
		// logger.error("Recived Param Error devicePushType [" + devicePushType
		// + "]");
		// resultHandler.handle(Future.failedFuture("devicePushType format is
		// error"));
		// return;
		// }
		// }

		// IOS apnsToken
		apnsToken = (String) receiveMsg.getValue("apnsToken");

		// Future<List<DeviceDto>> deviceFuture = Future.future();
		// if (StringUtil.isNullOrEmpty(token)) {
		// logger.info("设备token为空，从数据库获取设备token");
		// Map<String, String> param = new HashMap<>();
		// param.put("phone", phone);
		// deviceService.queryDevices(param, deviceFuture.completer());
		// } else {
		// DeviceDto dto = new DeviceDto();
		// dto.setDeviceToken(token);
		// deviceFuture.handle(Future.succeededFuture(Arrays.asList(dto)));
		// }

		// 判断消息是否接收过
		String redisMsgKey = PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId;
		Future<String> redisFuture = Future.future();
		redisService.get(redisMsgKey, redisFuture.completer());

		// Future<CompositeFuture> compositFuture =
		// CompositeFuture.all(deviceFuture, redisFuture);
		redisFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				// 验证redis
				String redisResult = handler.result();
				if (StringUtils.isNotBlank(redisResult)) {
					String repeatRecivedErrorMsg = "这个消息已发送过，禁止重复发送，msgId =" + msgId;
					resultHandler.handle(Future.failedFuture(repeatRecivedErrorMsg));
					return;
				}
				// logger.info("From redis not find key : " + redisMsgKey);
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});

	}

	/**
	 * 保存消息记录
	 */
	private void saveMsgRecord(Future<BaseResponse> checkFutrue) {
		checkFutrue.setHandler(handler -> {
			if (handler.succeeded()) {
				MsgRecord msg = new MsgRecord();
				msg.setAmqpMsgId(msgId);
				msg.setChannel(channel);
				msg.setMsgBody(receiveMsg.toString());
				msg.setStatus(MsgStatusEnum.SUCCESS.getCode());
				msgRecordService.addMessage(msg, res -> {
					if (res.succeeded()) {
						logger.info("保存消息成功：" + res);
					} else {
						logger.info("保存消息失败：" + res.cause());
					}
				});
			} else {
				logger.error("数据验证未通过，原因：" + checkFutrue.cause());
			}
		});

	}

	private void pushMsgToDownStream(Handler<AsyncResult<BaseResponse>> resultHandler,
			Future<BaseResponse> checkFutrue) {
		checkFutrue.setHandler(res -> {
			if (res.succeeded()) {

				if (!StringUtil.isNullOrEmpty(apnsToken)) {
					logger.info("开始走apns推送");
					applePushService.sendMsg(receiveMsg, resultHandler);
					// 上报要用token
					token = apnsToken;
					channel = PushTypeEnum.APNS.getSrcCode();
					return;
				}

				Map<String, String> params = new HashMap<>();
				params.put("uid", customerId + "");

				Future<Boolean> socketFuture = Future.future();
				this.getSocketCheckResult(params, socketFuture.completer());

				socketFuture.setHandler(socketHandler -> {
					if (socketHandler.succeeded()) {

						isUseSocket = socketHandler.result();
						pushMessage2AndroidDevice(isUseSocket, resultHandler);

					} else {
						pushMessage2AndroidDevice(isUseSocket, resultHandler);
					}
				});

				// try {
				// logger.info("开始获取socket连接状态");
				//
				// String checkResult = HttpUtils.URLPost(
				// PropertiesLoaderUtils.multiProp.getProperty("socket.valid.url"),
				// params,
				// HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
				// if (StringUtils.isNotBlank(checkResult)) {
				// JsonObject checkSocket = new JsonObject(checkResult);
				// String returnCode = checkSocket.getString("returnCode");
				// String isValid = checkSocket.getString("isValid");
				// if ("0".equals(returnCode) && "1".equals(isValid)) {
				// isUseSocket = true;
				// } else {
				// logger.info("检测到socket未连接，" +
				// JsonUtil.toJsonString(checkResult));
				// }
				// } else {
				// logger.info("调用获取socket连接状态成功，但无结果返回");
				// }
				// } catch (IOException e) {
				// logger.error("检查socket连接是否有效接口调用异常", e);
				// }
			} else {
				logger.error("数据验证未通过，原因：" + checkFutrue.cause());
				resultHandler.handle(Future.failedFuture(checkFutrue.cause().getMessage()));
			}
		});
	}

	private void getSocketCheckResult(Map<String, String> params, Handler<AsyncResult<Boolean>> resultHandler) {

		HttpClientVerticle.getHttpClient().getNow(PropertiesLoaderUtils.multiProp.getProperty("socket.valid.host"),
				PropertiesLoaderUtils.multiProp.getProperty("socket.valid.uri"), responseHandler -> {

					logger.info("socket状态检查返回结果：" + responseHandler.statusCode());

					if (responseHandler.statusCode() == 200) {
						responseHandler.handler(handler -> {
							JsonObject checkSocket = handler.toJsonObject();
							logger.info("socket状态检查返回结果：" + checkSocket);
							String returnCode = checkSocket.getString("returnCode");
							String isValid = checkSocket.getString("isValid");
							if ("0".equals(returnCode) && "1".equals(isValid)) {
								resultHandler.handle(Future.succeededFuture(true));
							} else {
								logger.info("检测到socket未连接，" + JsonUtil.toJsonString(checkSocket));
								resultHandler.handle(Future.succeededFuture(false));
							}
						});
					} else {
						logger.error("sokcet状态检查异常" + responseHandler.statusCode());
						resultHandler.handle(Future.succeededFuture(false));
					}
				});

	}

	/**
	 * 选择推送渠道进行推送
	 * 
	 * @param isSocket
	 * @param resultHandler
	 */
	private void pushMessage2AndroidDevice(boolean isSocket, Handler<AsyncResult<BaseResponse>> resultHandler) {

		if (isSocket) {
			logger.info("开始走socket推送");
			socketPushService.sendMsg(receiveMsg, resultHandler);
			channel = PushTypeEnum.SOCKET.getSrcCode();
			return;
		}

		// 如果设备token为空，从库中查询token出来
		if (StringUtils.isBlank(token)) {

			Map<String, String> param = new HashMap<>();
			param.put("phone", phone);
			Future<List<DeviceDto>> deviceFuture = Future.future();
			deviceService.queryDevices(param, deviceFuture.completer());
			deviceFuture.setHandler(devRes -> {
				// 错误信息
				String errorMsg = "";
				if (devRes.succeeded()) {
					List<DeviceDto> list = devRes.result();
					if (CollectionUtils.isNotEmpty(list)) {
						token = list.get(0).getDeviceToken();
						receiveMsg.put("regId", token);

						if (StringUtils.isNotBlank(token)) {
							// 只用作对安卓手机进行推送,目前没有gcm的推送逻辑
							logger.info("开始走小米推送");
							xiaomiPushService.sendMsg(receiveMsg, resultHandler);
							channel = PushTypeEnum.XIAOMI.getSrcCode();
						}
					} else {
						errorMsg = "设备token不存在,推送操作不执行";
						logger.error(errorMsg);
						resultHandler.handle(Future.failedFuture(errorMsg));
						return;
					}
				} else {
					errorMsg = "查询设备token失败：" + devRes.cause();
					logger.error(errorMsg);
					resultHandler.handle(Future.failedFuture(errorMsg));
					return;
				}
			});
		} else {
			// 只用作对安卓手机进行推送,目前没有gcm的推送逻辑
			logger.info("开始走小米推送");
			receiveMsg.put("regId", token);
			xiaomiPushService.sendMsg(receiveMsg, resultHandler);
			channel = PushTypeEnum.XIAOMI.getSrcCode();
		}
	}

	/**
	 * 推送成功的消息保存到redis中
	 *
	 * @param resultHandler
	 */
	private void setMsgToRedis(Handler<AsyncResult<Void>> resultHandler) {
		String redisMsgKey = PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId;
		redisService.set(redisMsgKey, msgId, setRes -> {
			if (setRes.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				String errorMsg = "exec save to redis fail : key = " + redisMsgKey;
				logger.error(errorMsg, setRes.cause());
				resultHandler.handle(Future.failedFuture(setRes.cause()));
			}
		});

	}

	public static void main(String[] args) {
		System.out.println(!StringUtils.isNotBlank(null));
		System.out.println(!StringUtils.isNotBlank(""));
	}
}
