package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import tp.impl.TpServiceImpl;

public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private boolean header;

	private RecordParser parser;

	private EventBus eb;

	@Override
	public void start() throws Exception {
		NetServerOptions options = new NetServerOptions().setPort(2345);
		NetServer server = vertx.createNetServer(options);
		eb = vertx.eventBus();

		server.connectHandler(socket -> {
			socket.handler(parser = RecordParser.newFixed(4, buffer -> {
				logger.info("buffer " + buffer);
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
					case 17:
						subscribe(socket.writeHandlerID(), message);
						break;
					case 18:
						unsubscribe(socket.writeHandlerID(), message);
						break;
					default:
						break;
					}
				}
			}));
		});

		server.listen();
	}

	private void subscribe(String writeHandlerID, JsonObject message) {
		String uid = message.getString("uid");

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "subscribe");
		JsonObject msg = new JsonObject();
		msg.put("uid", message.getString(uid));

		eb.send(TpServiceImpl.class.getName(), message, option);
	}

	private void unsubscribe(String writeHandlerID, JsonObject message) {
		String uid = message.getString("uid");

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "subscribe");
		JsonObject msg = new JsonObject();
		msg.put("uid", message.getString(uid));

		eb.send(TpServiceImpl.class.getName(), message, option);
	}

	/**
	 * {"cmd":14,"data":{"ping":"ok"}} //心跳发送成功
	 * 
	 * @param writeHandlerID
	 */
	private void heartBeat(String writeHandlerID) {
		JsonObject data = new JsonObject();
		data.put("ping", "ok");
		data.put("speed", "1000");
		eb.send(writeHandlerID, data.encode());
	}

}
