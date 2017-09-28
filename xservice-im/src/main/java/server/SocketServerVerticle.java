package server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
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
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import logic.iml.SocketSessionVerticle;
import serializer.SocketByteUtils;
import tp.TpService;
import tp.impl.TpServiceImpl;
import util.ByteUtil;
import utils.IPUtil;

public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private EventBus eb;

	private TpService tpService;
	private String innerIP;

	@Override
	public void start() throws Exception {
		eb = vertx.eventBus();
		tpService = TpService.createProxy(vertx);
		innerIP = IPUtil.getInnerIP();
		logger.info("start...innerIP={}", innerIP);

		NetServerOptions options = new NetServerOptions().setPort(8088);
		NetServer server = vertx.createNetServer(options);

		// server.connectHandler(socket -> {
		// socket.handler(new Handler<Buffer>() {
		//
		// private int op = 1;
		//
		// private RecordParser parser;
		//
		// private String handlerID = socket.writeHandlerID();
		//
		// @Override
		// public void handle(Buffer event) {
		// parser = RecordParser.newDelimited("\n\n", buffer -> {
		//
		// logger.info("buffer, handlerID={} buffer={} op={}", handlerID, buffer, op);
		//
		// if (buffer.toString().startsWith("get /mobile?")) {
		// logger.info("send login, ");
		// op = 1;
		// }
		//
		// switch (op) {
		// case 1:
		// op = 2;
		// parser.fixedSizeMode(4);
		// logger.info("login, handlerID={} op={} buffer={}", handlerID, op, buffer);
		//
		// sendValidateOK(handlerID);
		//
		// Map<String, String> paramMap = URLRequest(buffer.toString());
		// String userId = paramMap.get("user");
		// // loginSocketSession(innerIP, handlerID, userId);
		// loginConfirm(handlerID, paramMap);
		//
		// break;
		// case 2:
		// op = 3;
		// logger.info("header, handlerID={} header={} op={}", handlerID,
		// buffer.getInt(0), op);
		//
		// int bodyLength = buffer.getInt(0);
		// parser.fixedSizeMode(bodyLength);
		// break;
		// case 3:
		// op = 2;
		// parser.fixedSizeMode(4);
		// logger.info("body, handlerID={} body={} op={}", handlerID, buffer, op);
		//
		// JsonObject message = buffer.toJsonObject();
		// int cmd = message.getInteger("cmd");
		// switch (cmd) {
		// case 14:
		// heartBeat(handlerID);
		// // getUidByHandlerID(innerIP, handlerID, message);
		// break;
		//
		// default:
		// break;
		// }
		// break;
		// default:
		// break;
		// }
		//
		// });
		// }
		//
		// });
		// });

		server.connectHandler(new Handler<NetSocket>() {

			@Override
			public void handle(NetSocket socket) {
				String handlerID = socket.writeHandlerID();

				logger.info("TCP ... ###");
				
				final RecordParser parser = RecordParser.newDelimited("\n\n", null);
				parser.setOutput(new Handler<Buffer>() {
					private int op = 1;// 1 登录 2 header 3 body

					@Override
					public void handle(Buffer buffer) {
						logger.info("buffer, handlerID={} buffer={} op={}", handlerID, buffer, op);

						if (buffer.toString().startsWith("get /mobile?")) {
							logger.info("send login, ");
							op = 1;
						}

						switch (op) {
						case 1:
							op = 2;
							parser.fixedSizeMode(4);
							logger.info("login, handlerID={} op={} buffer={}", handlerID, op, buffer);

							sendValidateOK(handlerID);

							Map<String, String> paramMap = URLRequest(buffer.toString());
							String userId = paramMap.get("user");
							// loginSocketSession(innerIP, handlerID, userId);
							loginConfirm(handlerID, paramMap);

							break;
						case 2:
							op = 3;
							logger.info("header, handlerID={} header={} op={}", handlerID, buffer.getInt(0), op);

							int bodyLength = buffer.getInt(0);
							parser.fixedSizeMode(bodyLength);
							break;
						case 3:
							op = 2;
							parser.fixedSizeMode(4);
							logger.info("body, handlerID={} body={} op={}", handlerID, buffer, op);

							JsonObject message = buffer.toJsonObject();
							int cmd = message.getInteger("cmd");
							switch (cmd) {
							case 14:
								heartBeat(handlerID);
								// getUidByHandlerID(innerIP, handlerID, message);
								break;

							default:
								break;
							}
							break;
						default:
							break;
						}
					}

				});
				
				socket.handler(parser);

				// socket.handler(parser = RecordParser.newDelimited("\n\n", new
				// Handler<Buffer>() {
				// private int op = 1;// 1 登录 2 header 3 body
				//
				// private RecordParser parser;
				//
				// @Override
				// public void handle(Buffer buffer) {
				// logger.info("buffer, handlerID={} buffer={} op={}", handlerID, buffer, op);
				//
				// if (buffer.toString().startsWith("get /mobile?")) {
				// logger.info("send login, ");
				// op = 1;
				// }
				//
				// switch (op) {
				// case 1:
				// op = 2;
				// parser.fixedSizeMode(4);
				// logger.info("login, handlerID={} op={} buffer={}", handlerID, op, buffer);
				//
				// sendValidateOK(handlerID);
				//
				// Map<String, String> paramMap = URLRequest(buffer.toString());
				// String userId = paramMap.get("user");
				// // loginSocketSession(innerIP, handlerID, userId);
				// loginConfirm(handlerID, paramMap);
				//
				// break;
				// case 2:
				// op = 3;
				// logger.info("header, handlerID={} header={} op={}", handlerID,
				// buffer.getInt(0), op);
				//
				// int bodyLength = buffer.getInt(0);
				// parser.fixedSizeMode(bodyLength);
				// break;
				// case 3:
				// op = 2;
				// parser.fixedSizeMode(4);
				// logger.info("body, handlerID={} body={} op={}", handlerID, buffer, op);
				//
				// JsonObject message = buffer.toJsonObject();
				// int cmd = message.getInteger("cmd");
				// switch (cmd) {
				// case 14:
				// heartBeat(handlerID);
				// // getUidByHandlerID(innerIP, handlerID, message);
				// break;
				//
				// default:
				// break;
				// }
				// break;
				// default:
				// break;
				// }
				// }
				//
				// }));
				socket.closeHandler(v -> {
					// op = 1;
					// logger.info("closeHandler, handlerID={} op={} close", handlerID, op);
				});

				socket.exceptionHandler(t -> {
					// op = 1;
					// logger.info("exceptionHandler, handlerID={} op={} close", handlerID, op);
				});
			}
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
						map = (Map<String, Object>) SocketByteUtils.byteToObject(packet.data().getBytes());
					} catch (Exception e) {
						logger.error("UDP unserialize packet={}e={}", packet.data(), e.getCause());
					}

					String userId = null;
					if (map != null) {
						logger.info("Map " + map.toString());

						map.forEach((k, v) -> {
							logger.info("map k={} v={}", k, v);
						});

						try {
							ArrayList<Object> msgBody = (ArrayList<Object>) map.get("params");
							userId = String.valueOf(msgBody.get(0));// userId
							String cmd = String.valueOf(msgBody.get(1));
							logger.info("UDP userId={}", userId);

							JsonObject data = JsonObject.mapFrom(msgBody.get(3));

							JsonObject msg2Send = new JsonObject();
							msg2Send.put("cmd", cmd);
							msg2Send.put("data", data);

							logger.info("userId={}Msg2Send={}", userId, msg2Send.encode());

							DeliveryOptions option = new DeliveryOptions();
							option.setSendTimeout(3000);
							option.addHeader("action", "getHandlerIDByUid");

							JsonObject param = new JsonObject();
							param.put("userId", userId);
							eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option,
									reply -> {
										if (reply.succeeded()) {
											JsonObject res = reply.result().body();
											String handlerID = res.getString("handlerID");

											if (StringUtils.isNotEmpty(handlerID)) {
												Buffer bf = Buffer
														.buffer(ByteUtil.intToBytes(msg2Send.encode().length()))
														.appendString(msg2Send.encode());
												eb.send(handlerID, bf);
											} else {
												logger.warn("handlerID is null.");
											}
										} else {
											// TODO
										}
									});

						} catch (Exception e2) {
							logger.error("Get userId error ", e2);
						}
					} else {
						logger.info("Map is null.");
					}
				});
			} else {
				logger.error("UDP", asyncResult.cause());
			}
		});
	}

	private void getUidByHandlerID(String innerIP, String writeHandlerID, JsonObject message) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getUidByHandlerID");

		JsonObject param = new JsonObject();
		param.put("handlerID", writeHandlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("userId");
				logger.info("getUidByHandlerID, handlerID={} userId={}", writeHandlerID, uid);

				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				String date = now.format(format);

				tpService.updateOnlineSimple(uid, date, message, result -> {
					if (result.succeeded()) {
						logger.info("updateOnlineSimple, handlerID={} result={}", writeHandlerID, result.result());
						if (StringUtils.isNotEmpty(result.result())) {
							Buffer bf = Buffer.buffer(ByteUtil.intToBytes(result.result().length()))
									.appendString(result.result());
							eb.send(writeHandlerID, bf);
						} else {
							logger.warn("updateOnlineSimple, handlerID={} result is null", writeHandlerID);
						}
					} else {
						logger.error("updateOnlineSimple, handlerID={} result={}", writeHandlerID, result.cause());
					}
				});
			} else {
				// TODO
			}
		});
	}

	/**
	 * req get /mobile?user=1123123&hash=
	 * 
	 * @param writeHandlerID
	 * @param req
	 */
	private void loginSocketSession(String innerIP, String writeHandlerID, String userId) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "setUserSocket");

		JsonObject message = new JsonObject();
		message.put("userId", userId);
		message.put("handlerID", writeHandlerID);

		eb.send(SocketSessionVerticle.class.getName() + innerIP, message, option, reply -> {
			if (reply.succeeded()) {
				logger.info("handlerID={} setUserSocket={}", writeHandlerID, reply.result());
			} else {
				logger.error("handlerID={} setUserSocket={}", writeHandlerID, reply.cause());
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

		// TODO delete
		mapRequest.forEach((k, v) -> {
			logger.info("mapRequest, k={} v={}", k, v);
		});

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

	private void loginConfirm(String writeHandlerID, Map<String, String> paramMap) {
		String userId = paramMap.get("user");
		String hash = paramMap.get("hash");
		String mid = paramMap.get("mid");
		String cid = paramMap.get("cid");
		String version = paramMap.get("ver");

		logger.info("Params handlerID={} userId={} hash={} mid={} cid={} version={}", writeHandlerID, userId, hash, mid,
				cid, version);

		JsonObject param = new JsonObject();
		param.put("userId", userId);
		param.put("hash", hash);
		param.put("mid", mid);
		param.put("cid", cid);
		param.put("ver", version);
		tpService.auth(param, res -> {
			if (res.succeeded()) {
				logger.info("Auth " + res.result());
			} else {
				logger.error("Auth " + res.cause());
			}
		});

		JsonObject message = new JsonObject();
		message.put("cmd", 54);

		JsonObject data = new JsonObject();
		data.put("uid", userId);
		data.put("omc", 0);// session:get_unread_msg_count(User, ?MOBILE_USER)
		List<String> tokenList = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			tokenList.add(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		data.put("tokens", tokenList);
		data.put("now", System.currentTimeMillis() / 1000);

		message.put("data", data);

		Buffer bf = Buffer.buffer(ByteUtil.intToBytes(message.encode().length())).appendString(message.encode());
		logger.info("loginConfirm, handlerID={} bf={}", writeHandlerID, bf);
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

		Buffer bf = Buffer.buffer(ByteUtil.intToBytes(message.encode().getBytes().length))
				.appendBytes(message.encode().getBytes());
		int msgLength = message.encode().getBytes().length;
		byte[] result = ByteUtil.intToBytes(msgLength);
		logger.info("heart beat, byteLength={}", msgLength);

		logger.info("heart beat, handlerID={} bf={} message={} length={}", writeHandlerID, bf, message.encode(),
				message.encode().length());

		eb.send(writeHandlerID, bf);
	}

}
