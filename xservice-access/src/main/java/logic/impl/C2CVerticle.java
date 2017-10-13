package logic.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import constants.IMMessageConstant;
import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import logic.C2CService;
import persistence.impl.MongoVerticle;
import persistence.message.IMMongoMessage;
import protocol.IMCmd;
import protocol.IMMessage;
import protocol.MessageBuilder;
import utils.IPUtil;

public class C2CVerticle extends AbstractVerticle implements C2CService {

	private static final Logger logger = LoggerFactory.getLogger(C2CVerticle.class);

	private EventBus eb;

	private static final String MONGO_COLLECTION = "message";

	public interface method {

		public static final String sendMessage = "sendMessage";

	}

	@Override
	public void start() throws Exception {
		// sharedData = vertx.sharedData();
		// sessionMap = sharedData.getLocalMap("session");
		// sessionReverse = sharedData.getLocalMap("sessionReverse");

		XProxyHelper.registerService(C2CService.class, vertx, this, C2CService.SERVICE_ADDRESS);

		String innerIP = IPUtil.getInnerIP();
		eb = vertx.eventBus();
		eb.<JsonObject>consumer(C2CVerticle.SERVICE_ADDRESS + innerIP, res -> {
			logger.info("C2CVerticle, {}", res.body().encode());
			MultiMap headers = res.headers();
			JsonObject param = res.body();
			if (headers != null) {
				String action = headers.get("action");
				logger.info("action={}", action);
				switch (action) {
				case "sendMessage":
					res.reply(sendMessage(headers, param));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	private int sendMessage(MultiMap headers, JsonObject msg) {
		logger.info("send start ... ");
		int result = 0;
		if (msg != null) {
			String to = null;
			try {
				to = msg.getJsonObject("body").getString("to");
				logger.info("send to={}", to);
			} catch (Exception e) {
				result = 1;
				logger.error("sendMessage, Parse json error.", e);
			}
			if (StringUtils.isNotEmpty(to)) {

				DeliveryOptions option = new DeliveryOptions();
				option.addHeader("action", IMSessionVerticle.method.getHandlerIDByUid);
				option.setSendTimeout(3000);
				JsonObject p = new JsonObject().put("to", to);
				eb.<JsonObject>send(IMSessionVerticle.class.getName() + "10.10.10.193", p, option, res -> {
					logger.info("sendMessage, {}", res.result());
					if (res.succeeded()) {
						JsonObject res11 = res.result().body();
						String toHandlerID = res11.getString("handlerID");
						JsonObject header = msg.getJsonObject("header");
						JsonObject body = msg.getJsonObject("body");
						long ts = System.currentTimeMillis();
						int clientVersion = header.getInteger("clientVersion");
						int cmd = header.getInteger("cmd");
						body.put(IMMessage.key_msgId, body.getString(IMMessage.key_msgId));
						body.put(IMMessage.key_timeStamp, ts);
						body.put(IMMessage.key_fromTel, body.getString("from"));
						body.put(IMMessage.key_toTel, body.getString("to"));

						int bodyLength = 0;
						try {
							bodyLength = body.toString().getBytes("UTF-8").length;
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Buffer headerBuffer = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								clientVersion, cmd, bodyLength);
						logger.info("sendMessage, toHandlerID={}body={}", toHandlerID, body.toString());

						// eb.send(toHandlerID, headerBuffer.appendString(body.toString())
						// .appendString(MessageBuilder.IM_MSG_SEPARATOR));

						JsonObject mongoMsg = new JsonObject();
						mongoMsg.put("collection", MONGO_COLLECTION);

						JsonObject mongoData = new JsonObject();
						mongoData.put(IMMongoMessage.key_msgId, body.getString(IMMessage.key_msgId));
						mongoData.put(IMMongoMessage.key_sceneType, body.getInteger(IMMessage.key_sceneType));
						mongoData.put(IMMongoMessage.key_sceneId, body.getString(IMMessage.key_sceneId));
						mongoData.put(IMMongoMessage.key_content, body.getString(IMMessage.key_content));
						mongoData.put(IMMongoMessage.key_msgType, body.getInteger(IMMessage.key_msgType));
						mongoData.put(IMMongoMessage.key_cmdId, IMCmd.MSG_N);
						mongoData.put(IMMongoMessage.key_timeStamp, ts);

						mongoMsg.put("data", mongoData);

						/**
						 * mongo message data: message body + msgId + timeStamp + date
						 */

						DeliveryOptions mongoOp = new DeliveryOptions();
						mongoOp.addHeader("action", MongoVerticle.method.saveData);
						mongoOp.setSendTimeout(3000);

						eb.<JsonObject>send(MongoVerticle.class.getName(), mongoMsg, mongoOp, mongoRes -> {
							if (mongoRes.succeeded()) {
								// 给FROM发A
								JsonObject msgBody = new JsonObject();
								msgBody.put(IMMessage.key_msgId, body.getString(IMMessage.key_msgId));
								msgBody.put(IMMessage.key_timeStamp, ts);
								Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
										clientVersion, cmd + MessageBuilder.MSG_ACK_CMD_RADIX,
										msgBody.toString().length());
								eb.send(header.getString("fromHandlerID"), aMsgHeader.appendString(msgBody.toString())
										.appendString(MessageBuilder.IM_MSG_SEPARATOR));
							} else {
								logger.error(mongoRes.cause().getMessage());
							}
						});
					} else {
						logger.error("sendMessage error.", res.cause());
					}
				});
			} else {
				result = 1;
			}
		}

		return result;
	}

	@Override
	public void doWithFileUpload(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) {
		//  gei to fa msg, xiao xi ru ku
	}
}
