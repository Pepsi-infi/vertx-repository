package com.message;

import com.message.constant.ConnectionConsts;
import com.message.constant.PushConsts;
import com.message.constant.PushTypeEnum;

import io.netty.util.internal.StringUtil;
import io.vertx.amqpbridge.AmqpBridge;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class MessagePushVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(MessagePushVerticle.class);

	private JsonObject recieveMsg = null;

	private Object activity = null;
	
	private EventBus eventBus;
	
	private MessageProducer<String> mp;

	@Override
	public void start() throws Exception {

		RedisClient redisClient = getRedisClient();

		if (redisClient == null) {
			logger.error("redis服务器连接失败");
			return;
		}
				
		recieveMsg = getMsg();

		if (!validateRecieveMsg(recieveMsg)) {
			return;
		}

		Integer msgId = (Integer) recieveMsg.getValue("msgId");
		Object customerId = recieveMsg.getValue("customerId");
		String apnsToken=(String) recieveMsg.getValue("apnsToken");

		redisClient.get(PushConsts.AD_PASSENGER_MSG_PREFIX + msgId +"_"+ customerId, res -> {
			if (res.succeeded()) {
				activity = res.result();
			}
		});

		if (activity != null) {
			logger.error("消费PassengerMsg：这个消息发送过，禁止重复发送！，msgId==:" + msgId);
			return;
		}
		
		if(StringUtil.isNullOrEmpty(apnsToken)||"null".equals(apnsToken)){
			
			redisClient.set(PushConsts.AD_PASSENGER_MSG_PREFIX + msgId +"_"+ customerId, 1+"", handler->{
				if(!handler.succeeded()){
					logger.error("redis赋值失败");
					return;
				}
			});
			
			long now=System.currentTimeMillis();
			Integer seconds = (int) ((Long)recieveMsg.getValue("expireTime") - now) / 1000;	
			
			redisClient.expire(PushConsts.AD_PASSENGER_MSG_PREFIX + msgId +"_"+ customerId, seconds, handler->{
				if(!handler.succeeded()){
					logger.error("redis赋值失败");
					return;
				}
			});
			
					
			String token = (String) recieveMsg.getValue("deviceToken"); // sokit、gcm，小米连接token
			String sendType = "sokit"; // sokit: sokit推送， gcm:gcm推送，xiaomi:小米推送		
			String devicePushType = (String) recieveMsg.getValue("devicePushType"); // 消息推送类型 1:IOS；2：GCM(或FCM)；3:小米  
			
			if (StringUtil.isNullOrEmpty(devicePushType)) {
				logger.error("消息推送类型为空");
				return;
			}
			
			if(!StringUtil.isNullOrEmpty(token)){
				
				if ("3".equals(devicePushType)) {
					sendType = "gcm";
				}  
				
				if ("2".equals(devicePushType)) {
					sendType = "xiaomi";
				}
			}
				
			// 安装消息推送：在sokit有连接时，优先sokit推送，其次是 GCM推送，最后是小米推送，如果都不支持，重连sokit进行推送
			
			eventBus=vertx.eventBus();
		
			if(PushTypeEnum.SOCKET.getCode().equals(sendType)) {
				
				token = null; // TODO socket推送
				mp=eventBus.sender(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX+PushTypeEnum.SOCKET.getCode());
				
			} else if (PushTypeEnum.GCM.getCode().equals(sendType)) {
				
				token = null; // TODO gcm推送
				mp=eventBus.sender(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX+PushTypeEnum.GCM.getCode());
				
			} else if (PushTypeEnum.XIAOMI.getCode().equals(sendType)) {
				
				//TODO 小米推送
				mp=eventBus.sender(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX+PushTypeEnum.XIAOMI.getCode());
				
			} else{
				logger.error("无效推送渠道");
				return;
			}
			
			mp.send(recieveMsg.toString());
		}
		
		
	}

	private JsonObject getMsg() {
		AmqpBridge bridge = AmqpBridge.create(vertx);

		bridge.start(ConnectionConsts.activemq_server_url, ConnectionConsts.activemq_server_port, res -> {

			MessageConsumer<JsonObject> consumer = bridge.createConsumer("");

			consumer.handler(msg -> {

				recieveMsg = msg.body();

			});

		});
		return recieveMsg;
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

	private RedisClient getRedisClient() {

		RedisOptions config = new RedisOptions().setAddress(ConnectionConsts.redis_server_address);

		return RedisClient.create(vertx, config);
	}
}
