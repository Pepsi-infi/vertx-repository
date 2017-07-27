package channel;

import java.util.Properties;

import constant.ConnectionConsts;
import constant.PushConsts;
import domain.AmqpConsumeMessage;
import enums.MsgStatusEnum;
import enums.PushTypeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.amqpbridge.AmqpBridge;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.DeviceService;
import service.GcmPushService;
import service.RedisService;
import service.SocketPushService;
import service.XiaoMiPushService;
import util.MsgUtil;
import util.PropertiesLoaderUtils;

/**
 * 
 * @author yanglf
 * 
 *         消息推送消费入口类
 *
 */

public class AmqpConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(AmqpConsumerVerticle.class);

	private Object activity = null;

	private SocketPushService socketPushService;

	private XiaoMiPushService xiaomiPushService;

	private GcmPushService gcmPushService;

	private RedisService redisService;

	private DeviceService deviceService;

	@Override
	public void start() throws Exception {

		this.initService();
		//接收消息
		recivedMessage();

	}

	private void initService() {
		if (socketPushService == null) {
			socketPushService = SocketPushService.createProxy(vertx);
		}
		if (xiaomiPushService == null) {
			xiaomiPushService = XiaoMiPushService.createLocalProxy(vertx);
		}
		if (gcmPushService == null) {
			gcmPushService = GcmPushService.createLocalProxy(vertx);
		}
		if(deviceService == null) {
			deviceService = DeviceService.createLocalProxy(vertx);
		}
		if(redisService == null){
			redisService = RedisService.createLocalProxy(vertx);
		}
	}

	/**
	 * 处理消息
	 * @param recieveMsg
	 */
	private void executeMessage(JsonObject recieveMsg) {
		//校验
		if (!validateRecieveMsg(recieveMsg)) {
			return;
		}
		Integer msgId = (Integer) recieveMsg.getValue("msgId");
		Object customerId = recieveMsg.getValue("customerId");
		String apnsToken = (String) recieveMsg.getValue("apnsToken");

		redisService.get(PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId,res -> {
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
			pushMsg(recieveMsg,sendType, token);

			// 消息入库
			saveMsg(recieveMsg,sendType);

		}

	}

	private void saveMsg(JsonObject recieveMsg, String sendType) {
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

			if ("2".equals(devicePushType)) {
				sendType = "gcm";
			}

			if ("3".equals(devicePushType)) {
				sendType = "xiaomi";
			}
		}
		return sendType;
	}

	private void pushMsg(JsonObject recieveMsg, String sendType, String token) {
		recieveMsg.put("regId", token);
		if (PushTypeEnum.SOCKET.getCode().equals(sendType)) {
			token = null; //socket推送
			socketPushService.sendMsg(recieveMsg, res->{
				
			});
		} else if (PushTypeEnum.GCM.getCode().equals(sendType)) {
			token = null; //gcm推送
			gcmPushService.sendMsg(recieveMsg,res->{
				
			});

		} else if (PushTypeEnum.XIAOMI.getCode().equals(sendType)) {
			// 只用作对安卓手机进行推送
			xiaomiPushService.sendMsg(recieveMsg,res->{
				
			});
		} else {
			logger.error("无效推送渠道");
			return;
		}

	}

	/**
	 * 接收消息
	 */
	private void recivedMessage(){
		AmqpBridge bridge = AmqpBridge.create(vertx);
		//读取配置
		Properties prop = PropertiesLoaderUtils.loadProperties( ConnectionConsts.MQ_CONFIG_PATH);
		//bridge.start
		String addr = prop.getProperty(ConnectionConsts.ACTIVEMQ_SERVER_URL);
		int port = Integer.parseInt(prop.getProperty(ConnectionConsts.ACTIVE_SERVER_PORT));
		bridge.start(addr, port, res -> {
			if(!res.succeeded()) {
				System.out.println("Bridge startup failed: " + res.cause());
				return;
			}
			MessageConsumer<JsonObject> consumer = bridge.createConsumer(ConnectionConsts.ACTIVE_QUEUE_TOPIC);
			consumer.handler(vertxMsg -> {
				JsonObject reciveMsg = vertxMsg.body();
				logger.info("Received a message with body : " + reciveMsg.toString());
				this.executeMessage(reciveMsg);
			});
		});
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
