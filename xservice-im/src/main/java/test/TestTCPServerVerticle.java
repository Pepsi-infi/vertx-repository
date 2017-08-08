package test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

public class TestTCPServerVerticle extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(TestTCPServerVerticle.class);
	private SharedData sharedData;
	private LocalMap<String, String> sessionMap;
	private LocalMap<String, String> sessionReverse;
	private EventBus eb;

	@Override
	public void start() throws Exception {
		sharedData = vertx.sharedData();
		sessionMap = sharedData.getLocalMap("session");
		sessionReverse = sharedData.getLocalMap("sessionReverse");
		eb = vertx.eventBus();

		NetServer server = vertx.createNetServer();
		server.connectHandler(socket -> {
			Buffer bf = Buffer.buffer();
			bf.appendString("Welcome to Chat Room.\n");
			socket.write(bf);
			socket.handler(RecordParser.newDelimited("\n", buffer -> {
				String rawString = buffer.getString(0, buffer.length());
				String[] params = rawString.trim().split("#");
				switch (params[0]) {
				case "/connect":
					sessionMap.put(params[1], socket.writeHandlerID());
					sessionReverse.put(socket.writeHandlerID(), params[1]);
					Buffer bf1 = Buffer.buffer();
					bf1.appendString("Connect " + params[1] + " success.\n");
					socket.write(bf1);
					logger.info("session map size " + sessionMap.size());
					break;
				case "/send":
					String sender = sessionReverse.get(socket.writeHandlerID());
					eb.send(sessionMap.get(params[1]), Buffer.buffer().appendString(sender + " says " + params[2]),
							res -> {
								Buffer bf3 = Buffer.buffer();
								if (res.succeeded()) {
									bf3.appendString("Msg send success.\n");
									socket.write(bf3);
								} else {
									bf.appendString("fail.\n");
									socket.write(bf3);
								}
							});
					break;
				default:
					break;
				}
			}));
		});
		server.listen(1234, "localhost", res -> {
			if (res.succeeded()) {
				System.out.println("Server is now listening!");
			} else {
				System.out.println("Failed to bind!");
			}
		});
	}
}
