package server;

import cluster.ConsistentHashingService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import logic.C2CService;

public class ValidateServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(ValidateServerVerticle.class);

	private C2CService c2cService;
	private EventBus eb;
	private ConsistentHashingService consistentHashingService;
	private int header = -1;
	private int msgLength = 4;
	private RecordParser parser;

	@Override
	public void start() throws Exception {
		NetServerOptions options = new NetServerOptions().setPort(4321);
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(socket -> {
			socket.handler(RecordParser.newDelimited("\n\n", buffer -> {
				/// mobile?user=13666098&hash=UDE4NTEwMjUyNzk5fDE1MDUyNjc5NDY3ODQ.&mid=iphone&cid=AppStore&ver=5.2.0
				/// HTTP/
				logger.info("validate " + buffer);
				sendValidateOK(socket.writeHandlerID());
				sendRedirect(socket.writeHandlerID());
			}));
		});

		server.listen();
	}

	private void sendValidateOK(String writeHandlerID) {
		Buffer response = Buffer.buffer().appendString("HTTP/1.0 200 OK").appendString("\n")
				.appendString("Server: MochiWeb/1.0 (Any of you quaids got a smint?)")
				.appendString("Expires: Mon, 26 Jul 1997 05:00:00 GMT")
				.appendString("Date: Thu, 07 Sep 2017 08:34:45 GMT").appendString("Content-Type: text/x-live-message")
				.appendString("Connection: keep-alive").appendString("Cache-Control: no-cache, must-revalidate");
		eb.send(writeHandlerID, response);
	}

	private void sendRedirect(String writeHandlerID) {
		JsonObject message = new JsonObject();
		message.put("cmd", 57);

		eb.send(writeHandlerID, message);
	}

}
