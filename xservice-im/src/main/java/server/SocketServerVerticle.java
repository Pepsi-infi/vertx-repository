package server;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import tp.impl.TpServiceImpl;
import util.ByteUtil;

public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private int op;// 1 登录 2 header 3 body

	private int redirect = 0;

	private RecordParser parser;

	private EventBus eb;

	@Override
	public void start() throws Exception {
		NetServerOptions options = new NetServerOptions().setPort(4321);
		NetServer server = vertx.createNetServer(options);
		eb = vertx.eventBus();

		server.connectHandler(socket -> {
			op = 1;
			socket.handler(parser = RecordParser.newDelimited("\n\n", buffer -> {
				logger.info("buffer " + buffer);

				switch (op) {
				case 1:
					logger.info("login op " + op + buffer);
					sendValidateOK(socket.writeHandlerID());

					logger.info("redirect " + redirect);
					loginConfirm(socket.writeHandlerID());
					op = 2;

					parser.fixedSizeMode(4);

					break;
				case 2:
					logger.info("header " + buffer.getInt(0));

					op = 3;
					int bodyLength = buffer.getInt(0);
					parser.fixedSizeMode(bodyLength);
					break;
				case 3:
					logger.info("body " + buffer);

					op = 2;
					parser.fixedSizeMode(4);

					JsonObject message = buffer.toJsonObject();
					int cmd = message.getInteger("cmd");
					switch (cmd) {
					case 14:
						heartBeat(socket.writeHandlerID());
						break;

					default:
						break;
					}
					break;
				default:
					break;
				}
			}));

			// parser = RecordParser.newFixed(4, buffer -> {
			// logger.info("buffer " + buffer);
			// if (header) {
			// int bodyLength = buffer.getInt(0);
			// parser.fixedSizeMode(bodyLength);
			// header = false;
			// } else {
			// JsonObject message = buffer.toJsonObject();
			// header = true;
			// int cmd = message.getInteger("cmd");
			//
			// switch (cmd) {
			// case 14:
			// heartBeat(socket.writeHandlerID());
			// break;
			// case 17:
			// subscribe(socket.writeHandlerID(), message);
			// break;
			// case 18:
			// unsubscribe(socket.writeHandlerID(), message);
			// break;
			// default:
			// break;
			// }
			// }
			// }));
		});

		server.listen();
	}

	private void sendValidateOK(String writeHandlerID) {
		Buffer response = Buffer.buffer().appendString("HTTP/1.0 200 OK").appendString("\r\n")
				.appendString("Server: MochiWeb/1.0 (Any of you quaids got a smint?)").appendString("\r\n")
				.appendString("Expires: Mon, 26 Jul 1997 05:00:00 GMT").appendString("\r\n")
				.appendString("Date: Thu, 07 Sep 2017 08:34:45 GMT").appendString("\r\n")
				.appendString("Content-Type: text/x-live-message").appendString("\r\n")
				.appendString("Connection: keep-alive").appendString("\r\n")
				.appendString("Cache-Control: no-cache, must-revalidate").appendString("\r\n\r\n");
		logger.info("send validate ok " + response);
		eb.send(writeHandlerID, response);
	}

	private void sendRedirect(String writeHandlerID) {
		JsonObject message = new JsonObject();
		message.put("cmd", 57);
		message.put("data", "192.168.2.220:4321");
		// Buffer bf = Buffer.buffer(message.encode()).appendString("\n\n");
		Buffer bf = Buffer.buffer(ByteUtil.intToBytes(message.encode().length())).appendString(message.encode());

		logger.info("send redirect " + bf);
		eb.send(writeHandlerID, bf);
	}

	private void loginConfirm(String writeHandlerID) {
		JsonObject message = new JsonObject();
		message.put("cmd", 54);

		JsonObject data = new JsonObject();
		data.put("uid", 13666098);
		data.put("omc", 0);
		List<String> tokenList = new ArrayList<String>();
		tokenList.add("710d5e67139a68ff2666729a84dfb642");
		tokenList.add("4944092672b9b81a13cb7554f6e45144");
		tokenList.add("81e450f7f9064dc828ded649ba64d9be");
		tokenList.add("16e088060f929d1749a82cfba56c5182");
		data.put("tokens", tokenList);
		data.put("now", "1506042848");

		message.put("data", data);

		Buffer bf = Buffer.buffer(ByteUtil.intToBytes(message.encode().length())).appendString(message.encode());
		logger.info("loginConfirm " + bf);
		eb.send(writeHandlerID, bf);
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
		JsonObject message = new JsonObject();
		message.put("cmd", 14);

		JsonObject data = new JsonObject();
		data.put("ping", "ok");
		data.put("speed", "3000");

		message.put("data", data);
		Buffer bf = Buffer.buffer(ByteUtil.intToBytes(message.encode().length())).appendString(message.encode());

		logger.info("heart beat " + bf);

		eb.send(writeHandlerID, bf);
	}

}
