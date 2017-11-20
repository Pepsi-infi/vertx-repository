package server;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import cluster.impl.SocketConsistentHashingVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import serializer.SerialiazerVerticle;
import serializer.SocketByteUtils;
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
		socket.listen(config().getInteger("udp.port"), innerIP, asyncResult -> {
			if (asyncResult.succeeded()) {
				logger.info("UDP listening...");
				socket.handler(packet -> {
					logger.info("UDP packet " + packet.data());

					Map<String, Object> map = null;

					try {
						DeliveryOptions option = new DeliveryOptions();
						option.setSendTimeout(3000);
						option.addHeader("action", "unserialize");

						Future<Message<JsonArray>> unsFuture = Future.future();

						eb.<JsonArray>send(SerialiazerVerticle.class.getName(), packet.data().toString(), option,
								unsFuture.completer());
						unsFuture.setHandler(r -> {
							if (r.succeeded()) {
								JsonArray msgList = r.result().body();
								if (msgList != null) {
									List<Object> msgBody = msgList.getList();
									logger.info("UDP Map " + msgBody.toString());

									try {
										// ArrayList<Object> msgBody = (ArrayList<Object>) res.get("params");
										final String userId = String.valueOf(msgBody.get(0));// userId
										int cmd = NumberUtils.toInt(String.valueOf(msgBody.get(1)));

										//
										DeliveryOptions scOption = new DeliveryOptions();
										scOption.setSendTimeout(3000);
										scOption.addHeader("action", "getInnerNode");

										Future<Message<JsonObject>> chFuture = Future.future();

										JsonObject message = new JsonObject();
										message.put("userId", userId);
										if (StringUtils.isNotEmpty(userId)) {
											eb.<JsonObject>send(
													SocketConsistentHashingVerticle.class.getName() + innerIP, message,
													scOption, chFuture.completer());
										} else {

										}

										Future<Message<JsonObject>> ssFuture = Future.future();

										chFuture.setHandler(chRes -> {
											if (chRes.succeeded()) {
												JsonObject jsonRes = chRes.result().body();
												String hostIP = jsonRes.getString("host");

												DeliveryOptions msOption = new DeliveryOptions();
												msOption.setSendTimeout(3000);
												msOption.addHeader("action", "sendMsg");

												logger.info("UDP userId={}innerIP={}", userId, hostIP);

												JsonObject data = JsonObject.mapFrom(msgBody.get(3));

												JsonObject msg2Send = new JsonObject();
												msg2Send.put("cmd", cmd);
												msg2Send.put("data", data);

												JsonObject param = new JsonObject();
												param.put("userId", userId);
												param.put("msg", msg2Send);

												eb.<JsonObject>send(MessageSendVerticle.class.getName() + hostIP, param,
														msOption, ssFuture.completer());

											} else {

											}
										});
									} catch (Exception e2) {
										logger.error("Get userId error ", e2);
									}

								}
							} else {

							}
						});

						map = (Map<String, Object>) SocketByteUtils.byteToObject(packet.data().getBytes());
					} catch (Exception e) {
						logger.error("UDP unserialize packet={}e={}", packet.data(), e.getCause());
					}

					if (map != null) {
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
