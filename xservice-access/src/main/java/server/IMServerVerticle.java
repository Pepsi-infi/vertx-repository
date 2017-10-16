package server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import constants.IMCmd;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import module.c2c.C2CVerticle;
import module.c2c.protocol.MessageBuilder;
import module.c2c.protocol.SQIMBody;
import module.persistence.IMData;
import module.persistence.MongoVerticle;
import module.session.IMSessionVerticle;
import utils.ByteUtil;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

public class IMServerVerticle extends BaseServiceVerticle {

	private static final Logger logger = LoggerFactory.getLogger(IMServerVerticle.class);

	private EventBus eb;

	private String innerIP;
	private Map<String, String> ipMap = new HashMap<String, String>();

	@Override
	public void start() throws Exception {
		super.start();

		JsonArray socketNodes = config().getJsonArray("im");
		for (Object object : socketNodes) {
			JsonObject node = JsonObject.mapFrom(object);
			ipMap.put(node.getString("innerIP"), node.getString("node"));
		}
		innerIP = IPUtil.getInnerIP();
		publishIMService(IMServerVerticle.class.getName(), innerIP,
				new JsonObject().put("publicAddress", ipMap.get(innerIP)).put("innerIP", innerIP));

		logger.info("start ... ");
		eb = vertx.eventBus();

		NetServerOptions options = new NetServerOptions().setPort(4321);
		// options.setSsl(true).setPemKeyCertOptions(
		// new
		// PemKeyCertOptions().setKeyPath("server-key2.pem").setCertPath("server-cert.pem"));
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(new Handler<NetSocket>() {

			@Override
			public void handle(NetSocket event) {
				String handlerID = event.writeHandlerID();

				final RecordParser parser = RecordParser.newFixed(MessageBuilder.HEADER_LENGTH, null);
				parser.setOutput(new Handler<Buffer>() {
					private int bodyLength = -1;

					private int clientVersion = -1;
					private int cmd = -1;

					@Override
					public void handle(Buffer buffer) {
						if (bodyLength == -1) {
							int headerLength = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(0, 2));
							clientVersion = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(2, 4));
							cmd = ByteUtil.bytesToInt(buffer.getBytes(4, 8));
							bodyLength = ByteUtil.bytesToInt(buffer.getBytes(8, 12));
							logger.info("Msg header, headerLength={}clientVersion={}cmd={}bodyLength={}", headerLength,
									clientVersion, cmd, bodyLength);

							parser.fixedSizeMode(bodyLength);
						} else {
							SQIMBody imMessage = Json.decodeValue(buffer, SQIMBody.class);
							logger.info("imMessage={}", imMessage.toString());
							switch (cmd) {
							case IMCmd.HEART_BEAT:
								heartBeat(handlerID, clientVersion, cmd);

								break;
							case IMCmd.LOGIN:

								if (imMessage != null && StringUtils.isNotEmpty(imMessage.getUserTel())) {
									login(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}

								break;
							case IMCmd.LOGOUT:
								if (imMessage != null && StringUtils.isNotEmpty(imMessage.getUserTel())) {
									logout(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}

								break;
							case IMCmd.MSG_R:
								if (imMessage != null && StringUtils.isNotEmpty(imMessage.getFromTel())
										&& StringUtils.isNotEmpty(imMessage.getToTel())) {
									msgRequest(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}

								break;
							case IMCmd.ACK_N:
								if (imMessage != null) {
									msgAck(handlerID, clientVersion, cmd, imMessage);
								}

								break;
							default:
								break;
							}

							// reset
							clientVersion = -1;
							cmd = -1;
							bodyLength = -1;
							parser.fixedSizeMode(MessageBuilder.HEADER_LENGTH);
						}

					}

				});
				event.handler(parser);
				event.closeHandler(v -> {
					socketClose(handlerID);
				});
				event.exceptionHandler(t -> {
					socketClose(handlerID);
				});
			}
		});

		// server.connectHandler(socket -> {
		// socket.handler(RecordParser.newDelimited(MessageBuilder.IM_MSG_SEPARATOR,
		// buffer -> {
		// if (buffer.length() > 2) {
		// int headerLength = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(0, 2));
		// int clientVersion = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(2, 4));
		// int cmd = ByteUtil.bytesToInt(buffer.getBytes(4, 8));
		// int bodyLength = ByteUtil.bytesToInt(buffer.getBytes(8, 12));
		//
		// logger.info("Msg header, headerLength={}clientVersion={}cmd={}bodyLength={}",
		// headerLength,
		// clientVersion, cmd, bodyLength);
		//
		// Buffer bufferBody = buffer.getBuffer(headerLength, headerLength +
		// bodyLength);
		// SQIMBody imMessage = Json.decodeValue(bufferBody, SQIMBody.class);
		// logger.info("imMessage={}", imMessage.toString());
		// switch (cmd) {
		// case IMCmd.HEART_BEAT:
		// heartBeat(socket.writeHandlerID(), clientVersion, cmd);
		//
		// break;
		// case IMCmd.LOGIN:
		// if (imMessage != null && StringUtils.isNotEmpty(imMessage.getUserTel())) {
		// login(socket.writeHandlerID(), clientVersion, cmd, bodyLength, imMessage);
		// }
		//
		// break;
		// case IMCmd.LOGOUT:
		// if (imMessage != null && StringUtils.isNotEmpty(imMessage.getUserTel())) {
		// logout(socket.writeHandlerID(), clientVersion, cmd, bodyLength, imMessage);
		// }
		//
		// break;
		// case IMCmd.MSG_R:
		// if (imMessage != null && StringUtils.isNotEmpty(imMessage.getFromTel())
		// && StringUtils.isNotEmpty(imMessage.getToTel())) {
		// msgRequest(socket.writeHandlerID(), clientVersion, cmd, bodyLength,
		// imMessage);
		// }
		//
		// break;
		// case IMCmd.ACK_N:
		// if (imMessage != null) {
		// msgAck(socket.writeHandlerID(), clientVersion, cmd, imMessage);
		// }
		//
		// break;
		// default:
		// break;
		// }
		// }
		// }));
		//
		// socket.closeHandler(v -> {
		// socketClose(socket.writeHandlerID());
		// });
		// });

		server.listen();
	}

	private void msgAck(String handlerID, int clientVersion, int cmd, SQIMBody imMessage) {
		logger.info("msgAck, buffer={}", Json.encode(imMessage));
		String msgId = imMessage.getMsgId();

		JsonObject data = new JsonObject().put(IMData.key_msgId, msgId).put(IMData.key_cmdId, IMCmd.MSG_A);
		JsonObject update = new JsonObject().put("collection", "message").put("data", data);

		DeliveryOptions mongoOp = new DeliveryOptions();
		mongoOp.addHeader("action", MongoVerticle.method.saveData);
		mongoOp.setSendTimeout(3000);

		eb.<JsonObject>send(MongoVerticle.class.getName(), update, mongoOp, mongoRes -> {
			if (mongoRes.succeeded()) {
				eb.send(handlerID, mongoRes.result());
			} else {
				logger.error(mongoRes.cause().getMessage());
			}
		});

	}

	private void heartBeat(String writeHandlerID, int clientVersion, int cmd) {
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);
		logger.info("heartBeat,handlerId={} msgHeader={}", writeHandlerID, aMsgHeader);

		// 1、心跳消息确认
		eb.send(writeHandlerID, aMsgHeader);
	}

	private void login(String handlerID, int clientVersion, int cmd, int bodyLength, SQIMBody imMessage) {
		String from = imMessage.getUserTel();

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", IMSessionVerticle.method.setUserSocket);
		option.setSendTimeout(3000);
		JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
		logger.info("IMCmdConstants.LOGIN from={}cmd={}handlerID={}", from, cmd, handlerID);
		eb.send(IMSessionVerticle.class.getName() + innerIP, msg, option);

		// 1、给FROM发A
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);

		logger.info("DoWithLogin, handlerId={}clientVersion={}cmd={}bodyLength={}", handlerID, clientVersion, cmd,
				bodyLength);
		eb.send(handlerID, aMsgHeader);
	}

	private void logout(String handlerID, int clientVersion, int cmd, int bodyLength, SQIMBody imMessage) {
		String from = imMessage.getUserTel();
		if (StringUtils.isNotEmpty(from)) {
			DeliveryOptions option = new DeliveryOptions();
			option.addHeader("action", "delUserSocket");
			option.setSendTimeout(3000);
			JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
			eb.send(IMSessionVerticle.class.getName(), msg, option);

			// 给FROM发A
			Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH,
					msg.getInteger("clientVersion"), cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);
			eb.send(handlerID, aMsgHeader);
		}

	}

	private void msgRequest(String handlerID, int clientVersion, int cmd, int bodyLength, SQIMBody imMessage) {
		//
		// Future<String> hashFuture = Future.future();
		//
		// hashFuture.setHandler(res -> {
		// logger.info("msgRequest, hashFuture={}", res.result());
		// if (res.succeeded()) {}
		// });

		JsonObject param = new JsonObject();

		JsonObject header = new JsonObject();
		header.put("clientVersion", clientVersion);
		header.put("cmd", cmd);
		header.put("fromHandlerID", handlerID);

		param.put("header", header);
		param.put("body", JsonObject.mapFrom(imMessage));

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", C2CVerticle.method.sendMessage);
		option.setSendTimeout(1000);

		logger.info("msgRequest, param={}", param.encode());
		eb.send(C2CVerticle.class.getName() + "10.10.10.193", param, option);

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
