package server;

import constants.CmdConstants;
import constants.MessageConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
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
	private LocalMap<Long, String> sessionMap;// uid -> handlerID
	private LocalMap<String, Long> sessionReverse; // handlerID -> uid

	private C2CService c2cService;

	@Override
	public void start() throws Exception {
		sharedData = vertx.sharedData();
		sessionMap = sharedData.getLocalMap("session");
		sessionReverse = sharedData.getLocalMap("sessionReverse");
		vertx.setPeriodic(5000, id -> {
			System.out.println(sessionMap.toString());
		});

		c2cService = C2CService.createProxy(vertx);

		NetServerOptions options = new NetServerOptions().setPort(4321);
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
						msgBody = buffer
								.getBuffer(MessageConstant.HEADER_LENGTH, MessageConstant.HEADER_LENGTH + bodyLength)
								.toJsonObject();
					} catch (Exception e) {
						logger.error("Json parse error." + e.toString());
					}

				}

				if (msgBody != null) {
					Long from = msgBody.getLong("from");// uid
					if (from != null) {
						switch (cmd) {
						case CmdConstants.LOGIN:
							sessionMap.put(from, socket.writeHandlerID());
							sessionReverse.put(socket.writeHandlerID(), from);
							break;
						case CmdConstants.LOGOUT:
							Long uid = sessionReverse.get(socket.writeHandlerID());
							sessionReverse.remove(socket.writeHandlerID());
							if (uid != null) {
								sessionMap.remove(uid);
							}
							break;
						case CmdConstants.MSG_R:
							JsonObject rMsg = new JsonObject().put("clientVersion", clientVersion).put("seq", seq)
									.put("body", msgBody);

							Future<JsonObject> msgRF = Future.future();
							c2cService.doWithMsgRequest(rMsg, msgRF.completer());
							break;
						case CmdConstants.ACK_R:
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
				Long uid = sessionReverse.get(socket.writeHandlerID());
				sessionReverse.remove(socket.writeHandlerID());

				if (uid != null) {
					sessionMap.remove(uid);
				}
			});
		});

		server.listen();
	}
}
