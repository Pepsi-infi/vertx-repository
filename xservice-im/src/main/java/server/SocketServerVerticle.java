package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;

public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private boolean header;

	private RecordParser parser;

	private EventBus eb;

	@Override
	public void start() throws Exception {
		NetServerOptions options = new NetServerOptions().setPort(4321);
		NetServer server = vertx.createNetServer(options);
		eb = vertx.eventBus();

		server.connectHandler(socket -> {
			socket.handler(parser = RecordParser.newFixed(4, buffer -> {
				if (header) {
					int bodyLength = buffer.getInt(0);
					parser.fixedSizeMode(bodyLength);
					header = false;
				} else {
					JsonObject message = buffer.toJsonObject();
					header = true;
					int cmd = message.getInteger("cmd");

					switch (cmd) {
					case 14:
						heartBeat(socket.writeHandlerID());
						break;

					default:
						break;
					}
				}
			}));
		});

		server.listen();
	}

	/**
	 * {"cmd":14,"data":{"ping":"ok"}} //心跳发送成功
	 * 
	 * @param writeHandlerID
	 */
	private void heartBeat(String writeHandlerID) {
		JsonObject message = new JsonObject();
		message.put("cmd", 14);
		JsonObject data = new JsonObject();
		data.put("ping", "ok");
		message.put("data", data);
		eb.send(writeHandlerID, message);
	}

}
