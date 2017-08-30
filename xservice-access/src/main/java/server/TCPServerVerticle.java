package server;

import cluster.ConsistentHashingService;
import constants.IMCmdConstants;
import constants.IMMessageConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import logic.C2CService;
import util.ByteUtil;

public class TCPServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(TCPServerVerticle.class);

	private C2CService c2cService;
	private ConsistentHashingService consistentHashingService;
	private EventBus eb;

	@Override
	public void start() throws Exception {
		logger.info("start ... ");
		eb = vertx.eventBus();
		c2cService = C2CService.createProxy(vertx);
		consistentHashingService = ConsistentHashingService.createProxy(vertx);

		NetServerOptions options = new NetServerOptions().setPort(4321);
		// options.setSsl(true).setPemKeyCertOptions(
		// new
		// PemKeyCertOptions().setKeyPath("server-key2.pem").setCertPath("server-cert.pem"));
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(socket -> {
			socket.handler(RecordParser.newDelimited("\001", buffer -> {
				int headerLength = ByteUtil.bytesToInt(buffer.getBytes(0, 2));
				int clientVersion = ByteUtil.bytesToInt(buffer.getBytes(2, 4));
				int cmd = ByteUtil.bytesToInt(buffer.getBytes(4, 6));
				int bodyLength = ByteUtil.bytesToInt(buffer.getBytes(6, 10));

				final JsonObject msgBody = buffer
						.getBuffer(IMMessageConstant.HEADER_LENGTH, IMMessageConstant.HEADER_LENGTH + bodyLength)
						.toJsonObject();

				if (msgBody != null) {
					String from = msgBody.getString("from");
					String to = msgBody.getString("to");
					long seq = msgBody.getLong("id");
					if (from != null) {
						switch (cmd) {
						case IMCmdConstants.LOGIN:
							login(socket.writeHandlerID(), clientVersion, cmd, from);
							break;
						case IMCmdConstants.LOGOUT:
							logout(socket.writeHandlerID(), clientVersion, cmd, from);
							break;
						case IMCmdConstants.MSG_R:
							msgRequest(socket.writeHandlerID(), clientVersion, seq, msgBody, to);
							break;
						case IMCmdConstants.ACK_R:
							ackRequest(socket.writeHandlerID(), clientVersion, seq, msgBody, to);
							break;
						default:
							break;
						}
					}
				}
			}));

			socket.closeHandler(v -> {
				socketClose(socket.writeHandlerID());
			});
		});

		server.listen();
	}

	/**
	 * 登录
	 * 
	 * @param handlerID
	 * @param cmd
	 * @param from
	 *            登录用户id，必传
	 */
	private void login(String handlerID, int clientVersion, int cmd, String from) {
		// 把用户做一致性hash，分散到集群机器上
		Future<String> consistentHashFuture = Future.future();
		consistentHashingService.getNode(from, consistentHashFuture.completer());
		consistentHashFuture.setHandler(res -> {
			if (res.succeeded()) {
				DeliveryOptions option = new DeliveryOptions();
				option.addHeader("action", "setUserSocket");
				option.setSendTimeout(3000);
				JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
				logger.info("IMCmdConstants.LOGIN from={}cmd={}node={}handlerID={}", from, cmd, res.result(),
						handlerID);
				eb.send("session-eb-service" + res.result(), msg, option);
			} else {
				logger.error("Consistent Hash ", res.cause());
			}
		});

		Future<JsonObject> c2cFuture = Future.future();
		JsonObject rMsg = new JsonObject().put("clientVersion", clientVersion).put("cmd", cmd).put("fromHandlerID",
				handlerID);
		c2cService.doWithLogin(rMsg, c2cFuture.completer());
	}

	/**
	 * 登出
	 * 
	 * @param handlerID
	 * @param from
	 *            用户id，必传
	 */
	private void logout(String handlerID, int clientVersion, int cmd, String from) {
		// 把用户做一致性hash，分散到集群机器上
		Future<String> consistentHashFuture = Future.future();
		consistentHashingService.getNode(from, consistentHashFuture.completer());
		consistentHashFuture.setHandler(res -> {
			if (res.succeeded()) {
				DeliveryOptions option = new DeliveryOptions();
				option.addHeader("action", "delUserSocket");
				option.setSendTimeout(3000);
				JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
				eb.send("session-eb-service" + res.result(), msg, option);
			} else {
				logger.error("Consistent Hash ", res.cause());
			}
		});

		Future<JsonObject> c2cFuture = Future.future();
		JsonObject rMsg = new JsonObject().put("clientVersion", clientVersion).put("cmd", cmd).put("fromHandlerID",
				handlerID);
		c2cService.doWithLogout(rMsg, c2cFuture.completer());
	}

	private void msgRequest(String handlerID, int clientVersion, long seq, final JsonObject msgBody, String to) {
		Future<String> consistentHashFuture = Future.future();
		consistentHashingService.getNode(to, consistentHashFuture.completer());
		Future<Message<JsonObject>> sessionFuture = Future.future();
		// 根据to去session查出对应handlerID
		consistentHashFuture.setHandler(res -> {
			if (res.succeeded()) {
				DeliveryOptions option = new DeliveryOptions();
				option.addHeader("action", "getHandlerIDByUid");
				option.setSendTimeout(3000);
				JsonObject rMsg = new JsonObject().put("to", to);
				eb.send("session-eb-service" + res.result(), rMsg, option, sessionFuture.completer());
			} else {
				logger.error("Consistent Hash ", res.cause());
			}
		});

		Future<JsonObject> c2cFuture = Future.future();
		sessionFuture.setHandler(res -> {
			if (res.succeeded()) {
				JsonObject result = res.result().body();
				String toHandlerID = result.getString("handlerID");

				JsonObject rMsg = new JsonObject().put("clientVersion", clientVersion).put("seq", seq)
						.put("body", msgBody).put("fromHandlerID", handlerID).put("toHandlerID", toHandlerID);
				c2cService.doWithMsgRequest(rMsg, c2cFuture.completer());
			} else {
				logger.error("Session ", res.cause());
			}
		});
	}

	private void ackRequest(String handlerID, int clientVersion, long seq, final JsonObject msgBody, String to) {
		Future<String> consistentHashFuture = Future.future();
		consistentHashingService.getNode(to, consistentHashFuture.completer());
		Future<Message<JsonObject>> sessionFuture = Future.future();
		// 根据to去session查出对应handlerID
		consistentHashFuture.setHandler(res -> {
			if (res.succeeded()) {
				DeliveryOptions option = new DeliveryOptions();
				option.addHeader("action", "getHandlerIDByUid");
				option.setSendTimeout(3000);
				JsonObject rMsg = new JsonObject().put("to", to);
				eb.send("session-eb-service" + res.result(), rMsg, option, sessionFuture.completer());
			} else {
				// TODO
			}
		});

		Future<JsonObject> c2cFuture = Future.future();
		sessionFuture.setHandler(res -> {
			if (res.succeeded()) {
				JsonObject result = res.result().body();
				String toHandlerID = result.getString("handlerID");

				JsonObject aMsg = new JsonObject().put("clientVersion", clientVersion).put("seq", seq)
						.put("body", msgBody).put("fromHandlerID", handlerID).put("toHandlerID", toHandlerID);
				c2cService.doWithAckRequest(aMsg, c2cFuture.completer());
			} else {
				// TODO
			}
		});
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
