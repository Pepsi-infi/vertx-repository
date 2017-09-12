package server;

import org.apache.commons.lang.StringUtils;

import cluster.ConsistentHashingService;
import constants.IMMessageConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import logic.C2CService;
import logic.SessionService;
import logic.impl.C2CVerticle;
import protocol.MessageBuilder;

public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private C2CService c2cService;
	private EventBus eb;
	private ConsistentHashingService consistentHashingService;
	private RecordParser parser;
	private int header = -1;

	@Override
	public void start() throws Exception {
		NetServerOptions options = new NetServerOptions().setPort(4321);
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(socket -> {
			parser = RecordParser.newFixed(4, buffer -> {
				switch (header) {
				case -1:// parse header
					int bodyLength = buffer.getByte(0);
					parser.fixedSizeMode(bodyLength);
					logger.info("msg header " + bodyLength);
					System.out.println("msg header " + bodyLength);
					header = 0;
					break;
				case 0:// message body
					String msg = buffer.toString();
					if (msg.startsWith("/mobile")) {
						logger.info("msg body " + msg);
					} else {
						JsonObject jsonMsg = buffer.toJsonObject();
						logger.info("json msg body " + jsonMsg);
						System.out.println("msg body " + jsonMsg);

						int cmd = jsonMsg.getInteger("cmd");
						switch (cmd) {
						case 14:// heart beat
							heartBeat(socket.writeHandlerID());
							break;
						default:
							break;
						}
					}

					break;
				default:
					break;
				}
			});
		});

		server.listen();
	}

	private void heartBeat(String writeHandlerID) {
		JsonObject jo = new JsonObject();
		jo.put("ping", "ok").put("speed", 3000);

		eb.send(writeHandlerID, jo.encode());
	}

	private void ackNotify(String writeHandlerID, int clientVersion, String msgId, JsonObject jsonBody, String to) {
		// TODO Auto-generated method stub

	}

	private void login(String handlerID, int clientVersion, int cmd, int bodyLength, Buffer bufferBody) {
		if (bufferBody != null && bufferBody.length() != 0) {
			JsonObject jsonBody = null;
			String from = null;
			try {
				jsonBody = bufferBody.toJsonObject();
				if (jsonBody != null) {
					from = jsonBody.getString("userTel");
					if (StringUtils.isNotEmpty(from)) {
						DeliveryOptions option = new DeliveryOptions();
						option.addHeader("action", "setUserSocket");
						option.setSendTimeout(3000);
						JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
						logger.info("IMCmdConstants.LOGIN from={}cmd={}handlerID={}", from, cmd, handlerID);
						eb.send(SessionService.SERVICE_ADDRESS, msg, option);

						// 给FROM发A
						Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								clientVersion, cmd + 100, 0);

						logger.info("DoWithLogin, handlerId={}clientVersion={}cmd={}bodyLength={}", handlerID,
								clientVersion, cmd, bodyLength);
						eb.send(handlerID, aMsgHeader.appendString("\001"));
					}
				}
			} catch (Exception e) {
				logger.error("Msg body parse error, buffer={}", bufferBody, e);
			}
		} else {
			logger.warn("Msg body is Null. ClientVersion={}CMD={}", clientVersion, cmd);
		}
	}

	private void logout(String handlerID, int clientVersion, int cmd, int bodyLength, Buffer bufferBody) {
		if (bufferBody != null && bufferBody.length() != 0) {
			JsonObject jsonBody = null;
			String from = null;
			try {
				jsonBody = bufferBody.toJsonObject();
				if (jsonBody != null) {
					from = jsonBody.getString("userTel");
					if (StringUtils.isNotEmpty(from)) {
						DeliveryOptions option = new DeliveryOptions();
						option.addHeader("action", "delUserSocket");
						option.setSendTimeout(3000);
						JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
						eb.send(SessionService.SERVICE_ADDRESS, msg, option);

						// 给FROM发A
						Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								msg.getInteger("clientVersion"), cmd + 100, 0);
						eb.send(handlerID, aMsgHeader.appendString("\001"));
					}
				}
			} catch (Exception e) {
				logger.error("Msg body parse error, buffer={}", bufferBody);
			}
		} else {
			logger.warn("Msg body is Null. ClientVersion={}CMD={}", clientVersion, cmd);
		}
	}

	private void msgRequest(String handlerID, int clientVersion, int cmd, int bodyLength, Buffer bufferBody) {
		if (bufferBody != null && bufferBody.length() != 0) {
			JsonObject jsonBody = null;
			try {
				jsonBody = bufferBody.toJsonObject();
				if (jsonBody != null) {
					String from = jsonBody.getString("fromTel");
					String to = jsonBody.getString("toTel");
					if (StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to)) {
						String msgId = jsonBody.getString("msgId");
						String sceneId = jsonBody.getString("sceneId");
						int sceneType = jsonBody.getInteger("sceneType");
						int msgType = jsonBody.getInteger("msgType");
						String content = jsonBody.getString("content");

						//
						Future<String> hashFuture = Future.future();
						consistentHashingService.getNode(to, hashFuture.completer());
						hashFuture.setHandler(res -> {
							if (res.succeeded()) {
								JsonObject param = new JsonObject();

								JsonObject header = new JsonObject();
								header.put("clientVersion", clientVersion);
								header.put("cmd", cmd);

								JsonObject body = new JsonObject().put("from", from).put("to", to)
										.put("sceneId", sceneId).put("sceneType", sceneType).put("msgType", msgType)
										.put("content", content);

								param.put("header", header);
								param.put("body", body);

								DeliveryOptions option = new DeliveryOptions();
								option.addHeader("action", "sendMessage");
								option.setSendTimeout(1000);
								eb.send(C2CVerticle.SERVICE_ADDRESS + res.result(), param, option);
							}
						});

						// 给FROM发A
						JsonObject msgBody = new JsonObject();
						msgBody.put("msgId", msgId);
						msgBody.put("timeStamp", System.currentTimeMillis());
						Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								clientVersion, cmd + 100, msgBody.toString().length());
						eb.send(handlerID, aMsgHeader.appendString(msgBody.toString()).appendString("\001"));
					}
				}
			} catch (Exception e) {
				logger.error("Msg body parse error, buffer={}", bufferBody);
			}
		} else {
			logger.warn("Msg body is Null. ClientVersion={}CMD={}", clientVersion, cmd);
		}
	}

	private void socketClose(String handlerID) {
		logger.info("socket.close handlerID={}", handlerID);

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "delUserSocket");
		option.setSendTimeout(3000);
		JsonObject msg = new JsonObject().put("handlerID", handlerID);
		eb.publish("session-eb-service", msg, option);
	}
}
