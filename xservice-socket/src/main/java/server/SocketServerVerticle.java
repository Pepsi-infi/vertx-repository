package server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import cluster.impl.SocketConsistentHashingVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.rxjava.core.Future;
import logic.impl.SocketSessionVerticle;
import socket.heartbeat.HeartBeat;
import tp.DriverTpService;
import tp.PassengerTpService;
import util.ByteUtil;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

public class SocketServerVerticle extends BaseServiceVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

	private EventBus eb;

	private PassengerTpService pTpService;
	private DriverTpService dTpService;

	private String innerIP;

	private Map<String, String> ipMap = new HashMap<String, String>();

	private String serverType;

	// TCP连接保活时间1个小时
	private static final int KEEP_ALIVE_TIME_SECONDS = 3600;

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("config={}", config().encode());

		JsonArray socketNodes = config().getJsonArray("socket");
		for (Object object : socketNodes) {
			JsonObject node = JsonObject.mapFrom(object);
			ipMap.put(node.getString("innerIP"), node.getString("node"));
		}

		serverType = config().getString("socket.server.type");

		eb = vertx.eventBus();
		pTpService = PassengerTpService.createProxy(vertx);
		dTpService = DriverTpService.createProxy(vertx);
		innerIP = IPUtil.getInnerIP();

		publishSocketService(SocketServerVerticle.class.getName(), innerIP, config().getString("socket.server.type"),
				new JsonObject().put("publicAddress", ipMap.get(innerIP)).put("innerIP", innerIP));

		logger.info("start...innerIP={}ipMap={}", innerIP, ipMap.toString());

		NetServerOptions options = new NetServerOptions().setPort(config().getInteger("tcp.port"))
				.setIdleTimeout(KEEP_ALIVE_TIME_SECONDS);
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(new Handler<NetSocket>() {

			@Override
			public void handle(NetSocket socket) {
				String handlerID = socket.writeHandlerID();

				logger.info("TCP ... ###");

				final RecordParser parser = RecordParser.newDelimited("\n\n", null);
				parser.setOutput(new Handler<Buffer>() {
					private int op = 1;// 1 登录 2 header 3 body

					private int simpleHeartBeatCount = 0;

					private String clientIP;

					@Override
					public void handle(Buffer buffer) {
						if (buffer.toString().startsWith("get /mobile?") || buffer.toString().contains("get /mobile?")
								|| buffer.toString().startsWith("get /live?")
								|| buffer.toString().contains("get /live?")) {
							op = 1;
						}

						switch (op) {
						case 1:
							op = 2;
							parser.fixedSizeMode(4);
							logger.info("login, handlerID={} op={} buffer={}", handlerID, op, buffer);

							clientIP = socket.remoteAddress().host();
							Map<String, String> paramMap = URLRequest(buffer.toString());

							sendValidateOK(handlerID);

							String userId = paramMap.get("user");
							cHash(socket.localAddress().host(), userId, handlerID);
							loginSocketSession(innerIP, handlerID, userId);
							loginConfirm(clientIP, handlerID, paramMap);
							setClientOnline(userId);

							break;
						case 2:
							op = 3;
							int bodyLength = buffer.getInt(0);
							parser.fixedSizeMode(bodyLength);

							break;
						case 3:
							op = 2;
							parser.fixedSizeMode(4);

							logger.info("body, handlerID={} buffer={} ", handlerID, buffer);
							JsonObject message = buffer.toJsonObject();
							int cmd = message.getInteger("cmd");
							switch (cmd) {
							case 14:// heart beat
								heartBeat(handlerID);

								updateOnlineSimple(innerIP, handlerID, message);
								simpleHeartBeatCount++;
								if (simpleHeartBeatCount == 10 || simpleHeartBeatCount == 1) {
									updateOnlineState(innerIP, handlerID, message);
									simpleHeartBeatCount = 0;
								}
								break;
							case 17:// 订阅
								subscribe(handlerID, message);
								break;
							case 18:// 取消订阅
								unsubscribe(handlerID, message);
								break;
							case 313:
								msgConfirm(handlerID, message);
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

				socket.closeHandler(v -> {
					setClientOffline(handlerID);
					sessionLogout(handlerID);
					socket.close();
					logger.info("closeHandler, handlerID={}", handlerID);
				});

				socket.exceptionHandler(t -> {
					setClientOffline(handlerID);
					sessionLogout(handlerID);
					socket.close();
					logger.info("exceptionHandler, handlerID={}", handlerID);
				});
			}

		});

		server.listen();
	}

	private void updateOnlineState(String innerIP, String writeHandlerID, JsonObject message) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getUidByHandlerID");

		JsonObject param = new JsonObject();
		param.put("handlerID", writeHandlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("userId");

				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String date = now.format(format);
				JsonObject data = null;
				try {
					data = message.getJsonObject("data");
				} catch (Exception e) {
					try {
						HeartBeat hb = Json.mapper.readValue(message.getString("data").replace("\\\\", ""),
								HeartBeat.class);
						data = JsonObject.mapFrom(hb);
					} catch (Exception e1) {
						logger.error(e1);
					}
				}

				if ("driver-socket-server".equalsIgnoreCase(serverType)) {
					dTpService.updateOnlineState(uid, date, data, result -> {
						if (result.succeeded()) {
							logger.info("updateOnlineState, handlerID={} result={}", writeHandlerID, result.result());
						} else {
							logger.error("updateOnlineState, handlerID={} result={}", writeHandlerID, result.cause());
						}
					});
				} else {
					pTpService.updateOnlineState(uid, date, data, result -> {
						if (result.succeeded()) {
							logger.info("updateOnlineState, handlerID={} result={}", writeHandlerID, result.result());
						} else {
							logger.error("updateOnlineState, handlerID={} result={}", writeHandlerID, result.cause());
						}
					});
				}
			} else {
				// TODO
			}
		});
	}

	private void updateOnlineSimple(String innerIP, String handlerID, JsonObject message) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getUidByHandlerID");

		JsonObject param = new JsonObject();
		param.put("handlerID", handlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("userId");
				if (StringUtils.isEmpty(uid)) {
					// uid is null, relogin.
					sendReLogin(handlerID, null);
				} else {
					LocalDateTime now = LocalDateTime.now();
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					String date = now.format(format);
					JsonObject data = null;
					try {
						data = message.getJsonObject("data");
					} catch (Exception e) {
						try {
							HeartBeat hb = Json.mapper.readValue(message.getString("data").replace("\\\\", ""),
									HeartBeat.class);
							data = JsonObject.mapFrom(hb);
						} catch (Exception e1) {
							logger.error(e1);
						}
					}

					if ("driver-socket-server".equalsIgnoreCase(serverType)) {
						dTpService.updateOnlineSimple(uid, date, data, result -> {
							if (result.succeeded()) {
								// TODO
								logger.info("updateOnlineSimple, handlerID={} result={}", handlerID, result.result());
							} else {
								logger.error("updateOnlineSimple, handlerID={} result={}", handlerID, result.cause());
							}
						});
					} else {
						pTpService.updateOnlineSimple(uid, date, data, result -> {
							if (result.succeeded()) {
								logger.info("updateOnlineSimple, handlerID={} result={}", handlerID, result.result());
							} else {
								logger.error("updateOnlineSimple, handlerID={} result={}", handlerID, result.cause());
							}
						});
					}

				}
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

	private void sendValidateOK(String handlerID) {
		Buffer response = Buffer.buffer().appendString("HTTP/1.0 200 OK")
				.appendString("Server: MochiWeb/1.0 (Any of you quaids got a smint?)")
				.appendString("Expires: Mon, 26 Jul 1997 05:00:00 GMT")
				.appendString("Date: Thu, 07 Sep 2017 08:34:45 GMT").appendString("Content-Type: text/x-live-message")
				.appendString("Connection: keep-alive").appendString("Cache-Control: no-cache, must-revalidate");

		Buffer bf = response.appendString("\r\n\r\n");// Android司机端代码判断是否包含 13101301

		logger.info("sendValidateOK, handlerID={} bf={}", handlerID, bf.getBytes().toString());

		eb.send(handlerID, bf);
	}

	private void sendReLogin(String writeHandlerID, String socketNode) {
		JsonObject message = new JsonObject();
		message.put("cmd", 57);
		message.put("data", socketNode);
		Buffer bf = Buffer.buffer(ByteUtil.intToBytes(message.encode().length())).appendString(message.encode());

		logger.info("sendReLogin, bf={}", bf.toString());

		eb.send(writeHandlerID, bf);
	}

	private void cHash(String localhost, String userId, String handlerID) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getInnerNode");

		Future<Message<JsonObject>> chFuture = Future.future();

		JsonObject message = new JsonObject();
		message.put("userId", userId);
		if (StringUtils.isNotEmpty(userId)) {
			eb.<JsonObject>send(SocketConsistentHashingVerticle.class.getName() + innerIP, message, option,
					chFuture.completer());
		} else {

		}

		chFuture.setHandler(res -> {
			if (res.succeeded()) {
				JsonObject jsonRes = res.result().body();
				String socketNode = jsonRes.getString("host");
				logger.info("cHash, userId={}socketNode={}", userId, socketNode);
				if (!socketNode.equalsIgnoreCase(localhost)) {
					sendReLogin(handlerID, ipMap.get(socketNode));
				}
			} else {

			}
		});

	}

	private void loginConfirm(String clientIP, String writeHandlerID, Map<String, String> paramMap) {
		String userId = paramMap.get("user");
		String hash = paramMap.get("hash");
		String mid = paramMap.get("mid");
		String cid = paramMap.get("cid");
		String version = paramMap.get("ver");

		String v = version.split(" ")[0];

		JsonObject param = new JsonObject();
		param.put("userId", userId);
		param.put("hash", hash);
		param.put("mid", mid);
		param.put("cid", cid);
		param.put("ver", v);
		param.put("ip", clientIP);
		param.put("mode", "0");

		if ("driver-socket-server".equalsIgnoreCase(serverType)) {
			dTpService.auth(param, res -> {
				if (res.succeeded()) {
					logger.info("Auth " + res.result());
				} else {
					logger.error("Auth " + res.cause());
				}
			});
		} else {
			pTpService.auth(param, res -> {
				if (res.succeeded()) {
					logger.info("Auth " + res.result());
				} else {
					logger.error("Auth " + res.cause());
				}
			});
		}

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
		logger.info("loginConfirm, Params handlerID={} userId={} hash={} mid={} cid={} version={}, bf={}",
				writeHandlerID, userId, hash, mid, cid, version, bf);
		eb.send(writeHandlerID, bf);
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
		data.put("speed", "10000");

		message.put("data", data);

		Buffer bf = Buffer.buffer(ByteUtil.intToBytes(message.encode().getBytes().length))
				.appendBytes(message.encode().getBytes());

		eb.send(writeHandlerID, bf);
	}

	private void setClientOnline(String userId) {
		JsonObject clientOnlineParam = new JsonObject();
		clientOnlineParam.put("userId", userId);

		if ("driver-socket-server".equalsIgnoreCase(serverType)) {
			dTpService.setClientOnline(clientOnlineParam, result -> {
				if (result.succeeded()) {
					logger.info("setClientOnline, result={}", result.result());
				} else {
					logger.error("setClientOnline, e={}", result.cause().getMessage());
				}
			});
		} else {
			pTpService.setClientOnline(clientOnlineParam, result -> {
				if (result.succeeded()) {
					logger.info("setClientOnline, result={}", result.result());
				} else {
					logger.error("setClientOnline, e={}", result.cause().getMessage());
				}
			});
		}
	}

	private void setClientOffline(String handlerID) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getUidByHandlerID");

		JsonObject param = new JsonObject();
		param.put("handlerID", handlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("userId");

				JsonObject clientOfflineParam = new JsonObject();
				clientOfflineParam.put("userId", uid);

				if ("driver-socket-server".equalsIgnoreCase(serverType)) {
					dTpService.setClientOffline(clientOfflineParam, r -> {
						if (r.succeeded()) {
							logger.info("setClientOffline, result={}", r.result());
						} else {
							logger.error("setClientOffline, e={}", r.cause());
						}
					});
				} else {
					pTpService.setClientOffline(clientOfflineParam, r -> {
						if (r.succeeded()) {
							logger.info("setClientOffline, result={}", r.result());
						} else {
							logger.error("setClientOffline, e={}", r.cause());
						}
					});
				}
			} else {
				// TODO
			}
		});
	}

	private void subscribe(String handlerID, JsonObject message) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getUidByHandlerID");

		JsonObject param = new JsonObject();
		param.put("handlerID", handlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("userId");
				if (StringUtils.isEmpty(uid)) {
					// uid is null, relogin.
					sendReLogin(handlerID, null);
				} else {
					JsonObject subscribeParam = new JsonObject();
					subscribeParam.put("userId", uid);
					subscribeParam.put("data", message.getJsonObject("data").encode());

					if ("driver-socket-server".equalsIgnoreCase(serverType)) {
						dTpService.subscribe(subscribeParam, r -> {
							if (r.succeeded()) {
								logger.info("subscribe, handlerID={} userId={} result={}", handlerID, uid, r.result());
							} else {
								logger.error("subscribe, handlerID={} userId={} e={}", handlerID, uid,
										r.cause().getMessage());
							}
						});
					} else {
						pTpService.subscribe(subscribeParam, r -> {
							if (r.succeeded()) {
								logger.info("subscribe, handlerID={} userId={} result={}", handlerID, uid, r.result());
							} else {
								logger.error("subscribe, handlerID={} userId={} e={}", handlerID, uid,
										r.cause().getMessage());
							}
						});
					}
				}
			} else {
				// TODO
			}
		});
	}

	private void unsubscribe(String handlerID, JsonObject message) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getUidByHandlerID");

		JsonObject param = new JsonObject();
		param.put("handlerID", handlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("userId");
				if (StringUtils.isEmpty(uid)) {
					// uid is null, relogin.
					sendReLogin(handlerID, null);
				} else {
					JsonObject subscribeParam = new JsonObject();
					subscribeParam.put("userId", uid);
					subscribeParam.put("data", message.getJsonObject("data").encode());

					if ("driver-socket-server".equalsIgnoreCase(serverType)) {
						dTpService.unsubscribe(subscribeParam, r -> {
							if (r.succeeded()) {
								logger.info("unsubscribe, handlerID={} userId={} result={}", handlerID, uid,
										r.result());
							} else {
								logger.error("unsubscribe, handlerID={} userId={} e={}", handlerID, uid,
										r.cause().getMessage());
							}
						});
					} else {
						pTpService.unsubscribe(subscribeParam, r -> {
							if (r.succeeded()) {
								logger.info("unsubscribe, handlerID={} userId={} result={}", handlerID, uid,
										r.result());
							} else {
								logger.error("unsubscribe, handlerID={} userId={} e={}", handlerID, uid,
										r.cause().getMessage());
							}
						});
					}
				}
			} else {
				// TODO
			}
		});
	}

	private void sessionLogout(String handlerID) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "delUserSocket");

		JsonObject param = new JsonObject();
		param.put("handlerID", handlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				logger.info("sessionLogout, {}", handlerID);
			} else {
				// TODO
			}
		});
	}

	private void msgConfirm(String handlerID, JsonObject message) {
		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getUidByHandlerID");

		JsonObject param = new JsonObject();
		param.put("handlerID", handlerID);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option, reply -> {
			if (reply.succeeded()) {
				JsonObject res = reply.result().body();
				String uid = res.getString("userId");
				if (StringUtils.isEmpty(uid)) {
					// uid is null, relogin.
					sendReLogin(handlerID, null);
				} else {
					JsonObject msgConfirmParam = new JsonObject();
					msgConfirmParam.put("userId", uid);
					msgConfirmParam.put("msgId", message.getString("msgId"));

					if ("driver-socket-server".equalsIgnoreCase(serverType)) {
						dTpService.updateMsgState(msgConfirmParam, r -> {
							if (r.succeeded()) {
								logger.info("msgConfirm, handlerID={} userId={} result={}", handlerID, uid, r.result());
							} else {
								logger.error("msgConfirm, handlerID={} userId={} e={}", handlerID, uid,
										r.cause().getMessage());
							}
						});
					} else {
						// pTpService.unsubscribe(msgConfirmParam, r -> {
						// if (r.succeeded()) {
						// logger.info("unsubscribe, handlerID={} userId={} result={}", handlerID, uid,
						// r.result());
						// } else {
						// logger.error("unsubscribe, handlerID={} userId={} e={}", handlerID, uid,
						// r.cause().getMessage());
						// }
						// });
					}
				}
			} else {
				// TODO
			}
		});
	}
}
