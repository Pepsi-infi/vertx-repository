package channel;

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
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import iservice.DeviceService;
import iservice.MsgStatService;
import iservice.dto.DeviceDto;
import iservice.dto.MsgStatDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import result.ResultData;
import service.*;
import util.DateUtil;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(HttpConsumerVerticle.class);

	private SocketPushService socketPushService;

	private XiaoMiPushService xiaomiPushService;

	private RedisService redisService;

	private MsgRecordService msgRecordService;

	private MsgStatService msgStatService;

	private HttpServer httpServer;

	private Router router;

	private DeviceService deviceService;

	private ApplePushService applePushService;

	private String token;
	private Integer channel;

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
				logger.error("body is null");
				responseError(resp, "body is null");
				return;
			}

			JsonObject receiveMsg = new JsonObject(httpMsg);

			// 验证必填项
			ResultData checkResult = checkRecivedMsg(receiveMsg);
			if (ResultData.FAIL == checkResult.getCode()) {
				responseError(resp, checkResult.getMsg());
				return;
			}

			// 验证消息是否重复推送
			Future<BaseResponse> repeatFuture = Future.future();

			// 推送给下游
			Future<BaseResponse> pushFuture = Future.future();

			checkRepeatMsg(receiveMsg, repeatFuture.completer());
			repeatFuture.setHandler(res -> {
				if (res.succeeded()) {
					pushMsgToDownStream(receiveMsg, pushFuture.completer());
				} else {
					logger.error("消息重复推送：" + res.cause());
					responseError(resp, res.cause().getMessage());
				}
			});

			// 消息推送成功后，调用上报消息接口
			Future<BaseResponse> statFuture = Future.future();
			pushFuture.setHandler(res -> {
				if (res.succeeded()) {
					callStatPushMsg(receiveMsg, statFuture.completer());
				} else {
					// 输出推送时的错误
					logger.error("调用推送时出错：" + pushFuture.cause());
					responseError(resp, res.cause().getMessage());
				}
			});

			// 根据推送结果返回结果数据给http调用方
			statFuture.setHandler(res -> {
				if (res.succeeded()) {
					responseSuccess(resp, new ResultData().toString());
				} else {
					responseError(resp, res.cause().getMessage());
				}
			});

		});

		httpServer.requestHandler(router::accept).listen(8989);
	}

	public void responseSuccess(HttpServerResponse resp, String msg) {
		resp.putHeader("content-type", "text/plain;charset=UTF-8").end(msg);
	}

	public void responseError(HttpServerResponse resp, String msg) {
		ResultData resultData = new ResultData(ErrorCodeEnum.FAIL);
		resultData.setMsg(msg);
		resp.putHeader("content-type", "text/plain;charset=UTF-8").end(msg);
	}

	/**
	 * 已推送消息上报 //接口参见wiki :
	 * http://cowiki.01zhuanche.com/pages/viewpage.action?pageId=329268
	 */
	private void callStatPushMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		String msgId = receiveMsg.getValue("msgId") + "";
		String customerId = receiveMsg.getValue("customerId") + "";
		// 推送成功的消息把msgId保存到redis,用来防止重复推送
		Future<Void> setRedisFuture = Future.future();
		this.setMsgToRedis(msgId, customerId, setRedisFuture.completer());

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
				exeStatPushMsgResult(msgId, result, resultHandler);
			} else {
				logger.error("已推送消息上报失败", statRes.cause());
				resultHandler.handle(Future.failedFuture(statRes.cause()));
			}
		});

	}

	/**
	 * 处理返回结果
	 *
	 * @param result
	 * @param resultHandler
	 */
	private void exeStatPushMsgResult(String msgId, String result, Handler<AsyncResult<BaseResponse>> resultHandler) {
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
		msgRecordService = MsgRecordService.createProxy(vertx);
		redisService = RedisService.createProxy(vertx);
		msgStatService = MsgStatService.createProxy(vertx);
		deviceService = DeviceService.createProxy(vertx);
		applePushService = ApplePushService.createProxy(vertx);
	}

	private ResultData checkRecivedMsg(JsonObject receiveMsg) {
		ResultData result = new ResultData();
		// 校验必填项
		String msgId = receiveMsg.getValue("msgId") + "";
		if (StringUtils.isBlank(msgId)) {
			result.reSetResult(ResultData.FAIL, "msgId不能为空");
		}
		// 用户id
		Object customerId = receiveMsg.getValue("customerId");
		if (null == customerId) {
			result.reSetResult(ResultData.FAIL, "customerId不能为空");
		}

		String phone = (String) receiveMsg.getValue("phone");
		if (StringUtils.isBlank(phone)) {
			result.reSetResult(ResultData.FAIL, "上送手机号不能为空");
		}
		// sokit、gcm,小米连接token
		token = (String) receiveMsg.getValue("deviceToken");

		return result;
	}

	/**
	 * 消息是否重复接收
	 * 
	 * @param resultHandler
	 */
	private void checkRepeatMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {

		String msgId = receiveMsg.getValue("msgId") + "";
		String customerId = receiveMsg.getValue("customerId") + "";
		// 判断消息是否接收过
		String redisMsgKey = PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId;
		Future<String> redisFuture = Future.future();
		redisService.get(redisMsgKey, redisFuture.completer());

		redisFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				// 验证redis
				String redisResult = handler.result();
				if (StringUtils.isNotBlank(redisResult)) {
					String repeatRecivedErrorMsg = "这个消息已发送过，禁止重复发送，msgId =" + msgId;
					resultHandler.handle(Future.failedFuture(repeatRecivedErrorMsg));
					return;
				}
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});
	}

	/**
	 * 保存消息记录
	 */
	private void saveMsgRecord(JsonObject receiveMsg, Future<BaseResponse> checkFutrue) {
		String msgId = receiveMsg.getValue("msgId") + "";
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

	private void pushMsgToDownStream(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		String apnsToken = receiveMsg.getString("apnsToken");
		String phone = receiveMsg.getString("phone");

		if (!StringUtil.isNullOrEmpty(apnsToken)) {
			this.pushByApple(receiveMsg, resultHandler);
			return;
		}
		
		Map<String, String> param = new HashMap<>();
		param.put("phone", phone);
		Future<List<DeviceDto>> deviceFuture = Future.future();
		deviceService.queryDevices(param, deviceFuture.completer());
		deviceFuture.setHandler(devRes -> {

			if (devRes.succeeded()) {
				String errorMsg;
				List<DeviceDto> list = devRes.result();
				if (CollectionUtils.isNotEmpty(list)) {
					String deviceToken = list.get(0).getDeviceToken();
					channel = list.get(0).getChannel();

					if (PushTypeEnum.APNS.getSrcCode() == channel && !StringUtil.isNullOrEmpty(deviceToken)) {
						receiveMsg.put("apnsToken", deviceToken);
						this.pushByApple(receiveMsg, resultHandler);
					} else {
						this.pushByAndroid(receiveMsg, resultHandler);
					}

				} else {
					errorMsg = "设备token不存在,推送操作不执行";
					logger.error(errorMsg);
					resultHandler.handle(Future.failedFuture(errorMsg));
					return;
				}

			} else {
				resultHandler.handle(Future.failedFuture(devRes.cause()));
			}

		});

	}

	private void pushByAndroid(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		Integer customerId = receiveMsg.getInteger("customerId");
		Map<String, String> params = new HashMap<>();
		params.put("uid", customerId + "");

		Future<Boolean> socketFuture = Future.future();
		this.getSocketCheckResult(params, socketFuture.completer());

		socketFuture.setHandler(socketHandler -> {
			boolean isUseSocket = false;
			if (socketHandler.succeeded()) {
				isUseSocket = socketHandler.result();
				pushMessage2AndroidDevice(receiveMsg, isUseSocket, resultHandler);
			} else {
				pushMessage2AndroidDevice(receiveMsg, isUseSocket, resultHandler);
			}
		});

	}

	private void pushByApple(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		logger.info("开始走apns推送");
		// 上报要用token
		token = receiveMsg.getString("apnsToken");
		channel = PushTypeEnum.APNS.getSrcCode();
		applePushService.sendMsg(receiveMsg, resultHandler);
	}

	private void getSocketCheckResult(Map<String, String> params, Handler<AsyncResult<Boolean>> resultHandler) {

		WebClient webClient = WebClient.create(vertx);

		webClient.postAbs(PropertiesLoaderUtils.multiProp.getProperty("socket.valid.url"))
				.putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
				.addQueryParam("uid", params.get("uid")).send(responseHandler -> {
					if (responseHandler.succeeded()) {
						JsonObject checkSocket = responseHandler.result().bodyAsJsonObject();
						logger.info("socket状态检查返回结果：" + checkSocket);
						if (checkSocket == null) {
							resultHandler.handle(Future.succeededFuture(false));
							return;
						}
						String returnCode = checkSocket.getString("returnCode");
						String isValid = checkSocket.getString("isValid");
						if ("0".equals(returnCode) && "1".equals(isValid)) {
							resultHandler.handle(Future.succeededFuture(true));
						} else {
							logger.info("检测到socket未连接，" + Json.encode(checkSocket));
							resultHandler.handle(Future.succeededFuture(false));
						}
					} else {
						logger.error("sokcet状态检查异常" + responseHandler.cause());
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
	private void pushMessage2AndroidDevice(JsonObject receiveMsg, boolean isSocket,
			Handler<AsyncResult<BaseResponse>> resultHandler) {

		if (isSocket) {
			logger.info("开始走socket推送");
			socketPushService.sendMsg(receiveMsg, resultHandler);
			channel = PushTypeEnum.SOCKET.getSrcCode();
			return;
		}

		String phone = receiveMsg.getString("phone");
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
	private void setMsgToRedis(String msgId, String customerId, Handler<AsyncResult<Void>> resultHandler) {
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
