package module.c2c;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import constants.IMCmd;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import module.c2c.protocol.MessageBuilder;
import module.c2c.protocol.SQIMBody;
import module.hash.IMConsistentHashingVerticle;
import module.persistence.IMData;
import module.persistence.MongoVerticle;
import module.sensitivewords.SensitiveWordsVerticle;
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
			MultiMap headers = res.headers();
			JsonObject resBody = res.body();
			SQIMBody imMessage = Json.decodeValue(resBody.getJsonObject("body").encode(), SQIMBody.class);
			JsonObject msgHeader = resBody.getJsonObject("header");
			int clientVersion = msgHeader.getInteger("clientVersion");
			String fromHandlerID = msgHeader.getString("fromHandlerID");
			int cmd = msgHeader.getInteger("cmdId").intValue();
			if (headers != null) {
				String action = headers.get("action");
				switch (action) {
				case "sendMessage":
					res.reply(sendMessage(fromHandlerID, clientVersion, cmd, imMessage));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	private int sendMessage(String fromHandlerID, int clientVersion, int cmd, SQIMBody msg) {
		int result = 0;

		String to = msg.getToTel();

		long ts = System.currentTimeMillis();
		msg.setTimeStamp(ts);

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", IMSessionVerticle.method.getHandlerIDByUid);
		option.setSendTimeout(3000);
		JsonObject p = new JsonObject().put("to", to);

		//
		DeliveryOptions chOption = new DeliveryOptions();
		chOption.setSendTimeout(3000);
		chOption.addHeader("action", "getInnerNode");

		Future<Message<JsonObject>> chFuture = Future.future();

		JsonObject message = new JsonObject();
		message.put("userId", to);
		eb.<JsonObject>send(IMConsistentHashingVerticle.class.getName(), message, chOption, chFuture.completer());

		//
		chFuture.setHandler(r -> {
			if (r.succeeded()) {
				String innerHost = r.result().body().getString("host");
				logger.info("to host={} phone={}", innerHost, to);
				eb.<JsonObject>send(IMSessionVerticle.class.getName() + innerHost, p, option, res -> {
					if (res.succeeded()) {
						JsonObject res11 = res.result().body();
						String toHandlerID = res11.getString("handlerID");
						if (StringUtils.isNotEmpty(toHandlerID)) {
							if (StringUtils.isNotEmpty(msg.getContent())) {// 敏感词过滤
								DeliveryOptions swOption = new DeliveryOptions();
								swOption.addHeader("action", SensitiveWordsVerticle.method.filterSensitiveWords);
								swOption.setSendTimeout(3000);

								eb.<String>send(SensitiveWordsVerticle.class.getName(), msg.getContent(), swOption,
										swRes -> {
											if (swRes.succeeded()) {
												msg.setContent(swRes.result().body());

												String body = Json.encode(msg);
												int bodyLength = 0;
												try {
													bodyLength = Json.encode(msg).getBytes("UTF-8").length;
												} catch (UnsupportedEncodingException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}

												Buffer headerBuffer = MessageBuilder.buildMsgHeader(
														MessageBuilder.HEADER_LENGTH, clientVersion, IMCmd.MSG_N,
														bodyLength);

												logger.info("sendMessage, toHandlerID={}body={}", toHandlerID,
														body.toString());

												eb.send(toHandlerID, headerBuffer.appendString(body));

												// 只有聊天消息入库
												if (IMCmd.MONGO_CMD_SET.contains(cmd)) {
													saveData2Mongo(fromHandlerID, clientVersion, cmd, msg);
												}
											} else {
												logger.error("filterSensitiveWords, error={}",
														swRes.cause().getMessage());
											}
										});
							} else {
								int bodyLength = 0;
								try {
									bodyLength = Json.encode(msg).getBytes("UTF-8").length;
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// 服务器透传
								Buffer headerBuffer = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH,
										clientVersion, cmd, bodyLength);
								String body = Json.encode(msg);
								eb.send(toHandlerID, headerBuffer.appendString(body));

								// 只有聊天消息入库
								if (IMCmd.MONGO_CMD_SET.contains(cmd)) {
									saveData2Mongo(fromHandlerID, clientVersion, cmd, msg);
								}
							}
						} else {
							// ios - apns

							logger.error("sendMessage, toHandlerID is null, toTel={}", to);
							// 只有聊天消息入库
							if (IMCmd.MONGO_CMD_SET.contains(cmd)) {
								saveData2Mongo(fromHandlerID, clientVersion, cmd, msg);
							}
						}
					} else {
						logger.error("sendMessage error.", res.cause());
					}
				});
			} else {
				logger.error("IMConsistentHashingVerticle error={}", r.cause().getMessage());
			}
		});

		return result;
	}

	private void saveData2Mongo(String toHandlerID, int clientVersion, int cmd, SQIMBody msg) {
		JsonObject mongoMsg = new JsonObject();
		mongoMsg.put("collection", MONGO_COLLECTION);

		IMData data = new IMData();
		data.setCmdId(cmd);
		data.setFromTel(msg.getFromTel());
		data.setToTel(msg.getToTel());
		data.setIdentity(msg.getIdentity());
		data.setMsgId(msg.getMsgId());
		data.setSceneId(msg.getSceneId());
		data.setSceneType(msg.getSceneType());
		data.setMsgType(msg.getMsgType());
		data.setContent(msg.getContent());
		data.setTimeStamp(msg.getTimeStamp());
		data.setDuration(msg.getDuration());
		data.setAddress(msg.getAddress());
		data.setsAddress(msg.getsAddress());
		data.setLat(msg.getLat());
		data.setLon(msg.getLon());

		if (cmd == IMCmd.POSITION_SHARE_START) {
			data.setContent("我发起了位置共享");
			data.setMsgType(1);// 文本，后面优化
		}

		mongoMsg.put("data", JsonObject.mapFrom(data));

		/**
		 * mongo message data: message body + msgId + timeStamp + date
		 */

		DeliveryOptions mongoOp = new DeliveryOptions();
		mongoOp.addHeader("action", MongoVerticle.method.saveData);
		mongoOp.setSendTimeout(3000);

		eb.<JsonObject>send(MongoVerticle.class.getName() + innerIP, mongoMsg, mongoOp, mongoRes -> {
			if (mongoRes.succeeded()) {

				// SQIMBody ackMsg = new SQIMBody();
				// ackMsg.setMsgId(msg.getMsgId());
				// ackMsg.setTimeStamp(msg.getTimeStamp());
				//
				// int ackMsgBodyLength = 0;
				// String ackMsgStr = Json.encode(msg);
				// try {
				// ackMsgBodyLength = ackMsgStr.getBytes("UTF-8").length;
				// } catch (Exception e) {
				// logger.error(e);
				// }
				// // 给FROM发A
				// Buffer aMsgHeader =
				// MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				// cmd + MessageBuilder.MSG_ACK_CMD_RADIX, ackMsgBodyLength);
				// eb.send(toHandlerID, aMsgHeader.appendString(ackMsgStr));
			} else {
				logger.error(mongoRes.cause().getMessage());
			}
		});
	}
}
