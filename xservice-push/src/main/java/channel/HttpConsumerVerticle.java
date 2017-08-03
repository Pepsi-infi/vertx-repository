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
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import iservice.DeviceService;
import iservice.MsgStatService;
import iservice.dto.DeviceDto;
import iservice.dto.MsgStatDto;
import result.ResultData;
import service.GcmPushService;
import service.MsgRecordService;
import service.RedisService;
import service.SocketPushService;
import service.XiaoMiPushService;
import util.DateUtil;
import util.MsgUtil;
import utils.BaseResponse;

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
	private String devicePushType;
	// 推送类型，下游需要
	private String sendType;
	// apnsToken
	private String apnsToken;
	// 用户手机号
	private String phone;
	// 用户ID
	private Object customerId;

	private List<DeviceDto> deviceList;

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
				logger.error("请求数据为空，不做处理");
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
				// 已推送消息上报接口
				MsgStatDto msgStatDto = new MsgStatDto();
				// 首约app乘客端 1001；首约app司机端 1002
				msgStatDto.setAppCode(1001);
				msgStatDto.setChannel(Integer.parseInt(devicePushType));
				msgStatDto.setMsgId(msgId);
				// 1 安卓
				msgStatDto.setOsType(1);
				// 1发送，2接收
				msgStatDto.setAction(1);
				msgStatDto.setSendTime(DateUtil.getDateTime(System.currentTimeMillis()));
				msgStatService.statPushMsg(msgStatDto, statRes -> {
					if (statRes.succeeded()) {
						logger.info("已推送消息上报成功");
						resultHandler.handle(Future.succeededFuture());
					} else {
						logger.error("已推送消息上报失败,失败原因：" + statRes.cause());
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

	private void initService() {
		socketPushService = SocketPushService.createProxy(vertx);
		xiaomiPushService = XiaoMiPushService.createProxy(vertx);
		gcmPushService = GcmPushService.createProxy(vertx);
		msgRecordService = MsgRecordService.createProxy(vertx);
		redisService = RedisService.createProxy(vertx);
		msgStatService = MsgStatService.createProxy(vertx);
		deviceService = DeviceService.createProxy(vertx);
	}

	private void checkRecivedMsg(Handler<AsyncResult<BaseResponse>> resultHandler) {
		// 校验必填项
		if (!validateRecieveMsg(receiveMsg)) {
			resultHandler.handle(Future.failedFuture("必填项字段不能为空"));
			return;
		}

		phone = (String) receiveMsg.getValue("phone");

		if (!StringUtils.isNotBlank(phone)) {
			resultHandler.handle(Future.failedFuture("上送手机号不能为空"));
			return;
		}

		msgId = (String) receiveMsg.getValue("msgId");
		token = (String) receiveMsg.getValue("deviceToken"); // sokit、gcm,小米连接token

		Future<List<DeviceDto>> deviceFuture = Future.future();

		if (StringUtil.isNullOrEmpty(token)) {
			logger.info("设备token为空，从数据库获取设备token");

			Map<String, String> param = new HashMap<>();
			param.put("phone", phone);
			deviceService.queryDevices(param, deviceFuture.completer());
		} else {
			deviceList = new ArrayList<>();
			DeviceDto dto = new DeviceDto();
			dto.setDeviceToken(token);
			deviceList.add(dto);
			deviceFuture.handle(Future.succeededFuture(deviceList));
		}

		// 从上游接收到的 推送类型
		devicePushType = (String) receiveMsg.getValue("devicePushType");
		// 转化成下游需要的推送类型
		sendType = MsgUtil.convertCode(devicePushType);
		// IOS apnsToken
		apnsToken = (String) receiveMsg.getValue("apnsToken");
		// 用户id
		customerId = receiveMsg.getValue("customerId");

		if (StringUtils.isNotBlank(apnsToken) && !"null".equals(apnsToken)) {
			resultHandler.handle(Future.failedFuture("apnsToken不为空，请调用其它推送服务"));
			return;
		}

		// 判断消息是否接收过
		String redisMsgKey = PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId;
		Future<String> redisFuture = Future.future();
		redisService.get(redisMsgKey, redisFuture.completer());

		Future<CompositeFuture> compositFuture = CompositeFuture.all(deviceFuture, redisFuture);
		compositFuture.setHandler(handler -> {
			if (handler.succeeded()) {

				// 校验并获取设备token
				deviceList = handler.result().resultAt(0);
				logger.info(deviceList);
				if (CollectionUtils.isEmpty(deviceList)) {
					logger.error("设备token不存在,推送操作不执行");
					resultHandler.handle(Future.failedFuture("设备token不存在"));
					return;
				}
				token = deviceList.get(0).getDeviceToken();

				// 验证redis
				String redisResult = handler.result().resultAt(1);
				if (StringUtils.isNotBlank(redisResult)) {
					String repeatRecivedErrorMsg = "这个消息已发送过，禁止重复发送，msgId =" + msgId;
					resultHandler.handle(Future.failedFuture(repeatRecivedErrorMsg));
					return;
				}

				logger.info("from redis get key fail : key = " + redisMsgKey);
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
				msg.setChannel(sendType);
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
				logger.debug("token=" + token);
				receiveMsg.put("regId", token);
				if (PushTypeEnum.SOCKET.getCode().equals(sendType)) {
					// socket推送
					logger.info("开始走socket推送");
					socketPushService.sendMsg(receiveMsg.toString(), resultHandler);
				} else if (PushTypeEnum.GCM.getCode().equals(sendType)) {
					// gcm推送
					logger.info("开始走gcm推送");
					gcmPushService.sendMsg(receiveMsg, resultHandler);
				} else if (PushTypeEnum.XIAOMI.getCode().equals(sendType)) {
					// 只用作对安卓手机进行推送
					logger.info("开始走小米推送");
					xiaomiPushService.sendMsg(receiveMsg, resultHandler);
				} else {
					logger.error("无效推送渠道");
					return;
				}
			} else {
				logger.error("数据验证未通过，原因：" + checkFutrue.cause());
				resultHandler.handle(Future.failedFuture(checkFutrue.cause().getMessage()));
			}
		});
	}

	private boolean validateRecieveMsg(JsonObject msg) {
		String errorMsg;
		if (msg == null) {
			errorMsg = "校验未通过，receiveMsg==" + msg;
			logger.error(errorMsg);
			return false;
		}
		if (msg.getValue("msgId") == null) {
			errorMsg = "校验未通过：msgId为空";
			logger.error(errorMsg);
			return false;
		}
		if (msg.getValue("customerId") == null) {
			errorMsg = "校验未通过：customerId为空";
			logger.error(errorMsg);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(!StringUtils.isNotBlank(null));
		System.out.println(!StringUtils.isNotBlank(""));
	}
}
