package server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import logic.impl.SocketSessionVerticle;
import serializer.SocketByteUtils;
import util.ByteUtil;
import utils.IPUtil;

public class UdpServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(UdpServerVerticle.class);

	private EventBus eb;

	private String innerIP;

	@Override
	public void start() throws Exception {
		eb = vertx.eventBus();
		innerIP = IPUtil.getInnerIP();

		DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		socket.listen(9099, innerIP, asyncResult -> {
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
						logger.info("UDP Map " + map.toString());

						try {
							ArrayList<Object> msgBody = (ArrayList<Object>) map.get("params");
							userId = String.valueOf(msgBody.get(0));// userId
							String cmd = String.valueOf(msgBody.get(1));
							logger.info("UDP userId={}", userId);

							JsonObject data = JsonObject.mapFrom(msgBody.get(3));

							JsonObject msg2Send = new JsonObject();
							msg2Send.put("cmd", cmd);
							msg2Send.put("data", data);

							logger.info("UDP userId={}, Msg2Send={}", userId, msg2Send.encode());

							DeliveryOptions option = new DeliveryOptions();
							option.setSendTimeout(3000);
							option.addHeader("action", "getHandlerIDByUid");

							JsonObject param = new JsonObject();
							param.put("userId", userId);
							eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, option,
									reply -> {
										if (reply.succeeded()) {
											JsonObject res = reply.result().body();
											if (res != null) {
												String handlerID = res.getString("handlerID");
												Buffer bf = null;
												try {
													bf = Buffer
															.buffer(ByteUtil.intToBytes(
																	msg2Send.encode().getBytes("UTF-8").length))
															.appendString(msg2Send.encode());
													logger.info("UDP send, handlerID={} header={} bf={}", handlerID,
															msg2Send.encode().getBytes("UTF-8").length, bf);
												} catch (UnsupportedEncodingException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}

												eb.send(handlerID, bf);
											}
										} else {
											logger.error("getHandlerIDByUid, e={}", reply.cause().getMessage());
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
}
