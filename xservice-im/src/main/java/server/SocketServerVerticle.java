package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import logic.iml.SocketSessionVerticle;
import serializer.ByteUtils;
import tp.TpService;
import tp.impl.TpServiceImpl;
import util.ByteUtil;

public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private int op;// 1 登录 2 header 3 body

	private int redirect = 0;

	private RecordParser parser;

	private EventBus eb;

	private TpService tpService;

	@Override
	public void start() throws Exception {
		NetServerOptions options = new NetServerOptions().setPort(9099);
		NetServer server = vertx.createNetServer(options);
		eb = vertx.eventBus();

		tpService = TpService.createProxy(vertx);

		logger.info("start...");

		server.connectHandler(socket -> {
			op = 1;
			socket.handler(parser = RecordParser.newDelimited("\n\n", buffer -> {
				logger.info("buffer " + buffer);

				switch (op) {
				case 1:
					logger.info("login op " + op + buffer);

					sendValidateOK(socket.writeHandlerID());

					logger.info("redirect " + redirect);
					login(socket.writeHandlerID(), buffer.toString());
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
						getUidByHandlerID(socket.writeHandlerID(), message);
						updateOnlineSimple();
						break;

					default:
						break;
					}
					break;
				default:
					break;
				}
			}));
		});

		server.listen();

		DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		socket.listen(9099, "10.10.10.102", asyncResult -> {
			if (asyncResult.succeeded()) {
				logger.info("UDP listening...");
				socket.handler(packet -> {
					logger.info("UDP packet " + packet.data());

					Map<String, Object> map = null;

					try {
						map = (Map<String, Object>) ByteUtils.byteToObject(packet.data().getBytes());
					} catch (Exception e) {
						logger.error("UDP unserialize packet={}e={}", packet.data(), e.getCause());
						e.printStackTrace();
					}

					if (map != null) {
						logger.info("Map " + map.toString());
					} else {
						logger.info("Map is null.");
					}
				});
			} else {
				logger.error("UDP", asyncResult.cause());
			}
		});
	}

	private void sendMsgFromUDP() {

	}

	private void getUidByHandlerID(String writeHandlerID, JsonObject message2) {

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "getUidByHandlerID");

		JsonObject message = new JsonObject();
		message.put("handlerID", writeHandlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName(), message, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("uid");

				String date = "2017-09-22 13:30:22";// TODO
				tpService.updateOnlineSimple(uid, date, message2, result -> {
					logger.info(result.result());
					eb.send(writeHandlerID, result.result());
				});
			} else {
				// TODO
			}
		});
	}

	private void updateOnlineSimple() {
		// TODO Auto-generated method stub

	}

	/**
	 * req get /mobile?user=1123123&hash=
	 * 
	 * @param writeHandlerID
	 * @param req
	 */
	private void login(String writeHandlerID, String req) {
		Map<String, String> paramMap = URLRequest(req);
		String userId = paramMap.get("user");

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "setUserSocket");
		JsonObject message = new JsonObject();
		message.put("from", userId);
		message.put("handlerID", writeHandlerID);

		eb.send(SocketSessionVerticle.class.getName(), message, option, reply -> {
			if (reply.succeeded()) {
				logger.info("setUserSocket " + reply.result());
			} else {
				logger.error("setUserSocket " + reply.cause());
			}
		});
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组 www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
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
