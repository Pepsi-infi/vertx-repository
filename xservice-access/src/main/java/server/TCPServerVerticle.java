package server;

import cluster.ConsistentHashingService;
import constants.IMCmdConstants;
import constants.IMMessageConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import logic.C2CService;
import util.ByteUtil;

public class TCPServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(TCPServerVerticle.class);

	private SharedData sharedData;
	private LocalMap<String, String> sessionMap;// uid -> handlerID
	private LocalMap<String, String> sessionReverse; // handlerID -> uid

	private C2CService c2cService;
	private ConsistentHashingService consistentHashingService;
	private EventBus eb;

	@Override
	public void start() throws Exception {
		logger.info("start ... ");

		eb = vertx.eventBus();
		sharedData = vertx.sharedData();
		sessionMap = sharedData.getLocalMap("session");
		sessionReverse = sharedData.getLocalMap("sessionReverse");

		c2cService = C2CService.createProxy(vertx);
		consistentHashingService = ConsistentHashingService.createProxy(vertx);

		NetServerOptions options = new NetServerOptions().setPort(4321);
		// options.setSsl(true).setPemKeyCertOptions(
		// new
		// PemKeyCertOptions().setKeyPath("server-key2.pem").setCertPath("server-cert.pem"));
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(socket -> {
			socket.handler(RecordParser.newDelimited("\n", buffer -> {
				int headerLength = ByteUtil.bytesToInt(buffer.getBytes(0, 4));
				int clientVersion = ByteUtil.bytesToInt(buffer.getBytes(4, 8));
				int cmd = ByteUtil.bytesToInt(buffer.getBytes(8, 12));
				int seq = ByteUtil.bytesToInt(buffer.getBytes(12, 16));
				int bodyLength = ByteUtil.bytesToInt(buffer.getBytes(16, 20));

				JsonObject msgBody = null;
				if (bodyLength != 0) {
					try {
						msgBody = buffer.getBuffer(IMMessageConstant.HEADER_LENGTH,
								IMMessageConstant.HEADER_LENGTH + bodyLength).toJsonObject();
					} catch (Exception e) {
						logger.error("Json parse error." + e.toString());
					}

				}

				if (msgBody != null) {
					String from = msgBody.getString("from");// uid
					if (from != null) {
						// 把用户做一致性hash，分散到集群机器上
						Future<String> nodeFuture = Future.future();
						consistentHashingService.getNode(from, nodeFuture.completer());
						switch (cmd) {
						case IMCmdConstants.LOGIN:
							nodeFuture.setHandler(res -> {
								if (res.succeeded()) {
									DeliveryOptions option = new DeliveryOptions();
									option.addHeader("action", "setUserSocket");
									option.setSendTimeout(3000);
									JsonObject msg = new JsonObject().put("handlerID", socket.writeHandlerID())
											.put("from", from);
									logger.info("from={}cmd={}node={}handlerID={}", from, cmd, res.result(),
											socket.writeHandlerID());
									eb.send("session-eb-service" + res.result(), msg, option);
								} else {
									// TODO
								}
							});

							break;
						case IMCmdConstants.LOGOUT:
							nodeFuture.setHandler(res -> {
								if (res.succeeded()) {
									DeliveryOptions option = new DeliveryOptions();
									option.addHeader("action", "delUserSocket");
									option.setSendTimeout(3000);
									JsonObject msg = new JsonObject().put("handlerID", socket.writeHandlerID())
											.put("from", from);
									eb.send("session-eb-service" + res.result(), msg, option);
								} else {
									// TODO
								}
							});

							break;
						case IMCmdConstants.MSG_R:
							JsonObject rMsg = new JsonObject().put("clientVersion", clientVersion).put("seq", seq)
									.put("body", msgBody);

							Future<JsonObject> msgRF = Future.future();
							c2cService.doWithMsgRequest(rMsg, msgRF.completer());
							break;
						case IMCmdConstants.ACK_R:
							JsonObject aMsg = new JsonObject().put("clientVersion", clientVersion).put("seq", seq)
									.put("body", msgBody);
							Future<JsonObject> msgAF = Future.future();
							c2cService.doWithAckRequest(aMsg, msgAF.completer());
							break;
						default:
							break;
						}
					}
				}
			}));

			socket.closeHandler(v -> {
				String uid = sessionReverse.get(socket.writeHandlerID());
				sessionReverse.remove(socket.writeHandlerID());

				if (uid != null) {
					sessionMap.remove(uid);
				}
			});
		});

		server.listen();
	}
}
