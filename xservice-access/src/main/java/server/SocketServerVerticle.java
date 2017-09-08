package server;

import org.apache.commons.lang.StringUtils;

import cluster.ConsistentHashingService;
import constants.IMCmdConstants;
import constants.IMMessageConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.impl.MessageImpl;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import logic.C2CService;
import logic.SessionService;
import logic.impl.C2CVerticle;
import protocol.MessageBuilder;
import util.ByteUtil;

public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private C2CService c2cService;
	private EventBus eb;
	private ConsistentHashingService consistentHashingService;

	@Override
	public void start() throws Exception {
		logger.info("start ... ");
		eb = vertx.eventBus();

		c2cService = C2CService.createProxy(vertx);
		consistentHashingService = ConsistentHashingService.createProxy(vertx);

		NetServerOptions options = new NetServerOptions().setPort(8888);
		// options.setSsl(true).setPemKeyCertOptions(
		// new
		// PemKeyCertOptions().setKeyPath("server-key2.pem").setCertPath("server-cert.pem"));
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(new Handler<NetSocket>() {
			@Override
			public void handle(NetSocket socket) {
				final RecordParser parser = RecordParser.newFixed(4, null);
				Handler<Buffer> handler = new Handler<Buffer>() {
					int size = -1;

					public void handle(Buffer buff) {
						if (size == -1) {
							size = buff.getInt(0);
							parser.fixedSizeMode(size);
						} else {
							String msg = buff.getString(0, size);
							if (msg.startsWith("get /mobile?")) {
								//
							}

							int cmd = buff.toJsonObject().getInteger("cmd");
							switch (cmd) {
							case 14:// heart beat
								heartBeat(socket.writeHandlerID());
								break;
							case 5001:// 当前位置附近司机
								break;
							case 5002:// 当前行程司机位置
								break;
							case 5003:// 下单成功
								break;
							case 5004:// 已通知附近的司机数
								break;
							case 5005:// 取消订单
								break;
							case 5010:// 订单状态推送
								break;
							case 5007:// 被踢下线
							case 5008:// 自定义推送消息

							default:
								break;
							}
							if (1 == 1) {

								socket.write("");
							} else {
							}
							parser.fixedSizeMode(4);
							size = -1;
						}
					}
				};
				parser.setOutput(handler);

				socket.closeHandler(v -> {
					socketClose(socket.writeHandlerID());
				});
			}
		});

		// socket.handler(RecordParser.newDelimited("\001", buffer -> {
		// int headerLength = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(0, 2));
		// int clientVersion = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(2, 4));
		// int cmd = ByteUtil.bytesToInt(buffer.getBytes(4, 8));
		// int bodyLength = ByteUtil.bytesToInt(buffer.getBytes(8, 12));
		//
		// logger.info("Msg header, headerLength={}clientVersion={}cmd={}bodyLength={}",
		// headerLength,
		// clientVersion, cmd, bodyLength);
		//
		// Buffer bufferBody = null;
		// switch (cmd) {
		// case IMCmdConstants.HEART_BEAT:
		// heartBeat(socket.writeHandlerID(), clientVersion, cmd);
		//
		// break;
		// case IMCmdConstants.LOGIN:
		// bufferBody = buffer.getBuffer(headerLength, headerLength + bodyLength);
		// login(socket.writeHandlerID(), clientVersion, cmd, bodyLength, bufferBody);
		//
		// break;
		// case IMCmdConstants.LOGOUT:
		// bufferBody = buffer.getBuffer(headerLength, headerLength + bodyLength);
		// logout(socket.writeHandlerID(), clientVersion, cmd, bodyLength, bufferBody);
		//
		// break;
		// case IMCmdConstants.MSG_R:
		// bufferBody = buffer.getBuffer(headerLength, headerLength + bodyLength);
		// msgRequest(socket.writeHandlerID(), clientVersion, cmd, bodyLength,
		// bufferBody);
		// break;
		// default:
		// break;
		// }
		// }));
		server.listen();

	}

	private void heartBeat(String writeHandlerID) {
		JsonObject jo = new JsonObject();
		jo.put("ping", "ok").put("speed", 3000);

		eb.send(writeHandlerID, jo.encode());
	}

	private void heartBeat(String writeHandlerID, int clientVersion, int cmd) {
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH, clientVersion, cmd + 100, 0);
		logger.info("Msg Ack HeartBeat,handlerId={} msgHeader={}", writeHandlerID, aMsgHeader);
		eb.send(writeHandlerID, aMsgHeader.appendString("\001"));
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
