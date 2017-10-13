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
import logic.impl.C2CVerticle;
import logic.impl.IMSessionVerticle;
import persistence.MongoService;
import persistence.message.IMMongoMessage;
import protocol.IMCmd;
import protocol.IMMessage;
import protocol.MessageBuilder;
import util.ByteUtil;

public class IMServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(IMServerVerticle.class);

	private C2CService c2cService;
	private MongoService mongoService;
	private EventBus eb;
	private ConsistentHashingService consistentHashingService;

	@Override
	public void start() throws Exception {
		logger.info("start ... ");
		eb = vertx.eventBus();

		c2cService = C2CService.createProxy(vertx);
		mongoService = MongoService.createProxy(vertx);
		consistentHashingService = ConsistentHashingService.createProxy(vertx);

		NetServerOptions options = new NetServerOptions().setPort(4321);
		// options.setSsl(true).setPemKeyCertOptions(
		// new
		// PemKeyCertOptions().setKeyPath("server-key2.pem").setCertPath("server-cert.pem"));
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(socket -> {
			socket.handler(RecordParser.newDelimited(MessageBuilder.IM_MSG_SEPARATOR, buffer -> {
				int headerLength = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(0, 2));
				int clientVersion = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(2, 4));
				int cmd = ByteUtil.bytesToInt(buffer.getBytes(4, 8));
				int bodyLength = ByteUtil.bytesToInt(buffer.getBytes(8, 12));

				logger.info("Msg header, headerLength={}clientVersion={}cmd={}bodyLength={}", headerLength,
						clientVersion, cmd, bodyLength);

				Buffer bufferBody = null;
				switch (cmd) {
				case IMCmd.HEART_BEAT:
					heartBeat(socket.writeHandlerID(), clientVersion, cmd);

					break;
				case IMCmd.LOGIN:
					bufferBody = buffer.getBuffer(headerLength, headerLength + bodyLength);
					login(socket.writeHandlerID(), clientVersion, cmd, bodyLength, bufferBody);

					break;
				case IMCmd.LOGOUT:
					bufferBody = buffer.getBuffer(headerLength, headerLength + bodyLength);
					logout(socket.writeHandlerID(), clientVersion, cmd, bodyLength, bufferBody);

					break;
				case IMCmd.MSG_R:
					bufferBody = buffer.getBuffer(headerLength, headerLength + bodyLength);
					msgRequest(socket.writeHandlerID(), clientVersion, cmd, bodyLength, bufferBody);

					break;
				case IMCmd.ACK_N:
					bufferBody = buffer.getBuffer(headerLength, headerLength + bodyLength);
					msgAck(socket.writeHandlerID(), clientVersion, cmd, bufferBody);

					break;
				default:
					break;
				}
			}));

			socket.closeHandler(v -> {
				socketClose(socket.writeHandlerID());
			});
		});

		server.listen();
	}

	private void msgAck(String handlerID, int clientVersion, int cmd, Buffer bufferBody) {

		logger.info("msgAck, buffer={}", bufferBody);

		if (bufferBody != null && bufferBody.length() != 0) {
			JsonObject jsonBody = null;
			try {
				jsonBody = bufferBody.toJsonObject();
				if (jsonBody != null) {
					String msgId = jsonBody.getString("msgId");
					// {collection: "", data: {}}

					JsonObject data = new JsonObject().put(IMMongoMessage.key_msgId, msgId)
							.put(IMMongoMessage.key_cmdId, IMCmd.MSG_A);
					JsonObject update = new JsonObject().put("collection", "message").put("data", data);

					mongoService.updateData(update, mongo -> {

					});
				}
			} catch (Exception e) {
				logger.error("Msg body parse error, buffer={}", bufferBody, e);
			}
		} else {
			logger.warn("Msg body is Null. ClientVersion={}CMD={}", clientVersion, cmd);
		}
	}

	private void heartBeat(String writeHandlerID, int clientVersion, int cmd) {
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH, clientVersion,
				cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);
		logger.info("heartBeat,handlerId={} msgHeader={}", writeHandlerID, aMsgHeader);

		// 1、心跳消息确认
		eb.send(writeHandlerID, aMsgHeader.appendString(MessageBuilder.IM_MSG_SEPARATOR));
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
					from = jsonBody.getString(IMMessage.key_userTel);
					if (StringUtils.isNotEmpty(from)) {
						DeliveryOptions option = new DeliveryOptions();
						option.addHeader("action", IMSessionVerticle.method.setUserSocket);
						option.setSendTimeout(3000);
						JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
						logger.info("IMCmdConstants.LOGIN from={}cmd={}handlerID={}", from, cmd, handlerID);
						eb.send(IMSessionVerticle.class.getName() + "10.10.10.193", msg, option);

						// 1、给FROM发A
						Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								clientVersion, cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);

						logger.info("DoWithLogin, handlerId={}clientVersion={}cmd={}bodyLength={}", handlerID,
								clientVersion, cmd, bodyLength);
						eb.send(handlerID, aMsgHeader.appendString(MessageBuilder.IM_MSG_SEPARATOR));

						String sceneId = jsonBody.getString(IMMessage.key_sceneId);
						if (StringUtils.isNotEmpty(sceneId)) {
							// 2、发送离线消息
							JsonObject query = new JsonObject().put("cmd", IMCmd.MSG_N).put("toTel", from)
									.put("sceneId", sceneId)
									.put("timeStamp", new JsonObject().put("$lte", System.currentTimeMillis()));
							mongoService.findOffLineMessage(query, mongo -> {
								eb.send(handlerID, mongo.result());
							});
						}
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
						eb.send(IMSessionVerticle.class.getName(), msg, option);

						// 给FROM发A
						Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								msg.getInteger("clientVersion"), cmd + 100, 0);
						eb.send(handlerID, aMsgHeader.appendString("\001"));
					}
				}
			} catch (Exception e) {
				logger.error("logout, buffer={} e={}", bufferBody, e.getCause().getMessage());
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
					logger.info("msgRequest, json={}", jsonBody);
					String from = jsonBody.getString(IMMessage.key_fromTel);
					String to = jsonBody.getString(IMMessage.key_toTel);
					if (StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to)) {
						String msgId = jsonBody.getString(IMMessage.key_msgId);
						String sceneId = jsonBody.getString(IMMessage.key_sceneId);
						int sceneType = jsonBody.getInteger(IMMessage.key_sceneType);
						int msgType = jsonBody.getInteger(IMMessage.key_msgType);
						String content = jsonBody.getString(IMMessage.key_content);

						//
						Future<String> hashFuture = Future.future();
						consistentHashingService.getNode(to, hashFuture.completer());

						hashFuture.setHandler(res -> {
							logger.info("msgRequest, hashFuture={}", res.result());
							if (res.succeeded()) {
								JsonObject param = new JsonObject();

								JsonObject header = new JsonObject();
								header.put("clientVersion", clientVersion);
								header.put("cmd", cmd);
								header.put("fromHandlerID", handlerID);

								JsonObject body = new JsonObject().put("from", from).put("to", to)
										.put("sceneId", sceneId).put("sceneType", sceneType).put("msgType", msgType)
										.put("content", content);

								param.put("header", header);
								param.put("body", body);

								DeliveryOptions option = new DeliveryOptions();
								option.addHeader("action", C2CVerticle.method.sendMessage);
								option.setSendTimeout(1000);
								eb.send(C2CVerticle.SERVICE_ADDRESS + res.result().split(":")[0], param, option);
							}
						});

					}
				}
			} catch (Exception e) {
				logger.error("msgRequest, buffer={} e={}", bufferBody, e.getMessage());
			}
		} else {
			logger.warn("Msg body is Null. ClientVersion={}CMD={}", clientVersion, cmd);
		}
	}

	private void socketClose(String handlerID) {
		logger.info("socket.close handlerID={}", handlerID);

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", IMSessionVerticle.method.delUserSocket);
		option.setSendTimeout(3000);
		JsonObject msg = new JsonObject().put("handlerID", handlerID);
		eb.publish(IMSessionVerticle.class.getName(), msg, option);
	}
}
