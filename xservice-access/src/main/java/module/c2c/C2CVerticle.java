package module.c2c;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import constants.IMCmd;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import module.c2c.protocol.MessageBuilder;
import module.c2c.protocol.SQIMBody;
import module.persistence.IMData;
import module.persistence.MongoVerticle;
import module.session.IMSessionVerticle;
import utils.IPUtil;

public class C2CVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(C2CVerticle.class);

	private EventBus eb;

	private String innerIP;

	private static final String MONGO_COLLECTION = "message";

	public interface method {
		public static final String sendMessage = "sendMessage";
	}

	@Override
	public void start() throws Exception {
		innerIP = IPUtil.getInnerIP();
		eb = vertx.eventBus();
		eb.<JsonObject>consumer(C2CVerticle.class.getName() + innerIP, res -> {
			logger.info("C2CVerticle, {}", res.body().encode());
			MultiMap headers = res.headers();
			JsonObject resBody = res.body();
			SQIMBody imMessage = Json.decodeValue(resBody.getJsonObject("body").encode(), SQIMBody.class);
			JsonObject msgHeader = resBody.getJsonObject("header");
			int clientVersion = msgHeader.getInteger("clientVersion");
			int cmd = msgHeader.getInteger("cmd");
			if (headers != null) {
				String action = headers.get("action");
				logger.info("action={}", action);
				switch (action) {
				case "sendMessage":
					res.reply(sendMessage(clientVersion, cmd, imMessage));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	private int sendMessage(int clientVersion, int cmd, SQIMBody msg) {
		logger.info("send start ... ");
		int result = 0;

		String to = msg.getToTel();

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", IMSessionVerticle.method.getHandlerIDByUid);
		option.setSendTimeout(3000);
		JsonObject p = new JsonObject().put("to", to);
		eb.<JsonObject>send(IMSessionVerticle.class.getName() + "10.10.10.193", p, option, res -> {
			logger.info("sendMessage, {}", res.result());
			if (res.succeeded()) {
				JsonObject res11 = res.result().body();
				String toHandlerID = res11.getString("handlerID");
				if (StringUtils.isNotEmpty(toHandlerID)) {
					long ts = System.currentTimeMillis();

					msg.setTimeStamp(ts);
					String body = Json.encode(msg);
					int bodyLength = 0;
					try {
						bodyLength = Json.encode(msg).getBytes("UTF-8").length;
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Buffer headerBuffer = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
							cmd, bodyLength);

					logger.info("sendMessage, toHandlerID={}body={}", toHandlerID, body.toString());

					eb.send(toHandlerID, headerBuffer.appendString(body));

					saveData2Mongo(toHandlerID, clientVersion, cmd, bodyLength, msg);
				} else {
					logger.error("sendMessage, toHandlerID is null, toTel={}", to);
				}
			} else {
				logger.error("sendMessage error.", res.cause());
			}
		});

		return result;
	}

	private void saveData2Mongo(String toHandlerID, int clientVersion, int cmd, int bodyLength, SQIMBody msg) {
		JsonObject mongoMsg = new JsonObject();
		mongoMsg.put("collection", MONGO_COLLECTION);

		IMData data = new IMData();
		data.setBody(msg);
		data.setHeaderLength(MessageBuilder.HEADER_LENGTH);
		data.setClientVersion(clientVersion);
		data.setCmdId(IMCmd.MSG_N);
		data.setBodyLength(bodyLength);

		mongoMsg.put("data", Json.encode(data));

		/**
		 * mongo message data: message body + msgId + timeStamp + date
		 */

		DeliveryOptions mongoOp = new DeliveryOptions();
		mongoOp.addHeader("action", MongoVerticle.method.saveData);
		mongoOp.setSendTimeout(3000);

		eb.<JsonObject>send(MongoVerticle.class.getName(), mongoMsg, mongoOp, mongoRes -> {
			if (mongoRes.succeeded()) {

				SQIMBody ackMsg = new SQIMBody();
				ackMsg.setMsgId(msg.getMsgId());
				ackMsg.setTimeStamp(msg.getTimeStamp());

				int ackMsgBodyLength = 0;
				String ackMsgStr = Json.encode(msg);
				try {
					ackMsgBodyLength = ackMsgStr.getBytes("UTF-8").length;
				} catch (Exception e) {
					logger.error(e);
				}
				// 给FROM发A
				Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
						cmd + MessageBuilder.MSG_ACK_CMD_RADIX, ackMsgBodyLength);
				eb.send(toHandlerID, aMsgHeader.appendString(ackMsgStr));
			} else {
				logger.error(mongoRes.cause().getMessage());
			}
		});
	}
}
