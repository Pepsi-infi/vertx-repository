package channel;

import constant.PushConsts;
import domain.AmqpConsumeMessage;
import enums.ErrorCodeEnum;
import enums.MsgStatusEnum;
import enums.PushTypeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import service.*;
import util.MsgUtil;
import utils.BaseResponse;

public class HttpConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(AmqpConsumerVerticle.class);

	private Object activity = null;

	private SocketPushService socketPushService;

	private XiaoMiPushService xiaomiPushService;

	private GcmPushService gcmPushService;

	private RedisService redisService;

	private MsgRecordService msgRecordService;

	private HttpServer httpServer;

	private Router router;

	//接收到的消息
	private JsonObject receiveMsg = null;


	//上游消息id
	private String msgId;
	//小米，GCM   token
	private String token;
	//推送类型
	private String sendType;

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

		router.route("/sqyc/push/sokcet.htm").handler(context -> {
			
			HttpServerResponse resp= context.response();
			HttpServerRequest request = context.request();
			String httpMsg = request.getParam("body");
			logger.info(" 接收到的消息内容：" + httpMsg);
			receiveMsg = new JsonObject(httpMsg);
			if (receiveMsg == null) {
				logger.error("请求数据为空，不做处理");
				resp.putHeader("content-type", "text/plain").end(ErrorCodeEnum.FAIL.getCode());
				return;
			}
			resp.putHeader("content-type", "text/plain").end(ErrorCodeEnum.SUCCESS.getCode());
			consumMsg(res -> {
				if(res.succeeded()){
					logger.info("消费消息成功！" );
				}else{
					logger.error("消费消息失败,失败原因：" + res.cause());
				}
			});
		});

		httpServer.requestHandler(router::accept).listen(8989);
	}

	private void initService() {
			socketPushService = SocketPushService.createProxy(vertx);
			xiaomiPushService = XiaoMiPushService.createProxy(vertx);
			gcmPushService = GcmPushService.createProxy(vertx);
			msgRecordService = MsgRecordService.createProxy(vertx);
			redisService = RedisService.createProxy(vertx);
	}

	private void consumMsg(Handler<AsyncResult<BaseResponse>> resultHandler) {
		// 校验
		if (!validateRecieveMsg(receiveMsg)) {
			return;
		}
		msgId =  (String) receiveMsg.getValue("msgId");
		token = (String) receiveMsg.getValue("deviceToken"); // sokit、gcm,小米连接token
		String devicePushType = (String) receiveMsg.getValue("devicePushType"); // 消息推送类型
		sendType = MsgUtil.convertCode(devicePushType);

		Object customerId = receiveMsg.getValue("customerId");
		String apnsToken = (String) receiveMsg.getValue("apnsToken");

		redisService.get(PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId, res -> {
			if (res.succeeded()) {
				activity = res.result();
			}else{
				logger.error("from redis get key fail : key = " + PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId);
			}
		});
		if (activity != null) {
			logger.error("消费PassengerMsg：这个消息发送过，禁止重复发送！，msgId==:" + msgId);
			return;
		}
		if (StringUtil.isNullOrEmpty(apnsToken) || "null".equals(apnsToken)) {
			// 推送消息到下游
			pushMsgToDownStream(resultHandler);
			// 消息入库
			saveMsgRecord();
		}
	}

	/**
	 * 保存消息记录
	 */
	private void saveMsgRecord() {
		AmqpConsumeMessage msg=new AmqpConsumeMessage();
		msg.setAmqpMsgId(msgId);
		msg.setChannel(sendType);
		msg.setMsgBody(receiveMsg.toString());
		msg.setStatus(MsgStatusEnum.SUCCESS.getCode());
		msgRecordService.addMessage(msg, res ->{
			if(res.succeeded()){
				logger.info("保存消息成功：" + res);
			}else{
				logger.info("保存消息失败：" + res.cause());
			}
		});
	}

	private void pushMsgToDownStream(Handler<AsyncResult<BaseResponse>> resultHandler) {
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

	}

	private boolean validateRecieveMsg(JsonObject msg) {
		if (msg == null) {
			logger.error("校验未通过，receiveMsg==" + msg);
			return false;
		}
		if (msg.getValue("msgId") == null) {
			logger.error("校验未通过：msgId为空");
			return false;
		}
		if (msg.getValue("customerId") == null) {
			logger.error("校验未通过：customerId为空");
			return false;
		}
		return true;
	}

}
