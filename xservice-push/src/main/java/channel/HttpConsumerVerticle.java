package channel;

import constant.PushConsts;
import domain.AmqpConsumeMessage;
import enums.MsgStatusEnum;
import enums.PushTypeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import service.MsgRecordService;
import service.GcmPushService;
import service.RedisService;
import service.SocketPushService;
import service.XiaoMiPushService;
import util.MsgUtil;

public class HttpConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(AmqpConsumerVerticle.class);

	private JsonObject recieveMsg = null;

	private Object activity = null;

	private SocketPushService socketPushService;

	private XiaoMiPushService xiaomiPushService;

	private GcmPushService gcmPushService;

	private RedisService redisService;

	private MsgRecordService deviceService;

	private HttpServer httpServer;

	private Router router;

	@Override
	public void start() throws Exception {

		// 初始化化服务
		this.initService();

		// 接收消息
		recivedHttpMessage();

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

			HttpServerRequest request = context.request();

			String httpMsg = request.getParam("body");

			recieveMsg = new JsonObject(httpMsg);

			if (recieveMsg == null) {
				logger.error("请求数据为空，不做处理");
				return;
			}

			logger.info("开始消费数据");
			consumMsg();
		});

		httpServer.requestHandler(router::accept).listen(8989);

	}

	private void initService() {
	
			socketPushService = SocketPushService.createProxy(vertx);
	
		
			xiaomiPushService = XiaoMiPushService.createLocalProxy(vertx);
		
			gcmPushService = GcmPushService.createLocalProxy(vertx);
		
			deviceService = MsgRecordService.createLocalProxy(vertx);
	
			redisService = RedisService.createLocalProxy(vertx);
	
	}

	private void consumMsg() {

		// 校验
		if (!validateRecieveMsg(recieveMsg)) {
			return;
		}
		String msgId =  (String) recieveMsg.getValue("msgId");
		Object customerId = recieveMsg.getValue("customerId");
		String apnsToken = (String) recieveMsg.getValue("apnsToken");

		redisService.get(PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId, res -> {
			if (res.succeeded()) {
				activity = res.result();
			}
		});

		if (activity != null) {
			logger.error("消费PassengerMsg：这个消息发送过，禁止重复发送！，msgId==:" + msgId);
			return;
		}

		if (StringUtil.isNullOrEmpty(apnsToken) || "null".equals(apnsToken)) {

			String token = (String) recieveMsg.getValue("deviceToken"); // sokit、gcm,小米连接token
			String devicePushType = (String) recieveMsg.getValue("devicePushType"); // 消息推送类型
																					// 1:IOS；2：GCM(或FCM)；3:小米
			if (StringUtil.isNullOrEmpty(devicePushType)) {
				logger.error("消息推送类型为空");
				return;
			}

			String sendType = getSendType(token, devicePushType);

			// 推送消息
			pushMsg(sendType, token);

			// 消息入库
			saveMsg(sendType);

		}

	}

	private void saveMsg(String sendType) {
		AmqpConsumeMessage msg=new AmqpConsumeMessage();
		msg.setAmqpMsgId(MsgUtil.createMsgId());
		msg.setChannel(sendType);
		msg.setMsgBody(recieveMsg.toString());
		msg.setStatus(MsgStatusEnum.SUCCESS.getCode());

		deviceService.addMessage(msg, res ->{
			if(res.succeeded()){
				logger.info("保存消息成功：" + msg);
			}else{
				logger.info("保存消息失败：" + msg);
			}
		});
	}

	private String getSendType(String token, String devicePushType) {

		String sendType = "sokit";

		if (!StringUtil.isNullOrEmpty(token)) {

			if ("3".equals(devicePushType)) {
				sendType = "gcm";
			}

			if ("2".equals(devicePushType)) {
				sendType = "xiaomi";
			}
		}
		return sendType;
	}

	private void pushMsg(String sendType, String token) {

		recieveMsg.put("regId", token);

		if (PushTypeEnum.SOCKET.getCode().equals(sendType)) {

			token = null; // TODO socket推送
			logger.info("开始走socket推送");
			socketPushService.sendMsg(recieveMsg.toString(),res->{
				
				if(res.succeeded()){
					logger.info("socket推送成功");
				}else{
					logger.error("socket推送失败");
				}
				
			});

		} else if (PushTypeEnum.GCM.getCode().equals(sendType)) {

			token = null; // TODO gcm推送
			logger.info("开始走gcm推送");
			gcmPushService.sendMsg(recieveMsg,res->{
				
				if(res.succeeded()){
					logger.info("gcm推送成功");
				}else{
					logger.error("gcm推送失败");
				}
				
			});


		} else if (PushTypeEnum.XIAOMI.getCode().equals(sendType)) {

			// TODO 只用作对安卓手机进行推送
			logger.info("开始走小米推送");
			xiaomiPushService.sendMsg(recieveMsg,res->{
				
				if(res.succeeded()){
					logger.info("小米推送成功");
				}else{
					logger.error("小米推送失败");
				}
				
			});

		} else {
			logger.error("无效推送渠道");
			return;
		}

	}

	private boolean validateRecieveMsg(JsonObject msg) {

		if (msg == null) {
			logger.error("校验未通过，recieveMsg==" + msg);
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
