package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import cluster.impl.SocketConsistentHashingVerticle;
import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
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

					MixedArray map = null;

					try {
						map = Pherialize.unserialize(packet.data().toString()).toArray();
					} catch (Exception e) {
						logger.error("UDP unserialize packet={}e={}", packet.data(), e.getCause());
					}

					if (map != null) {
						logger.info("UDP Map " + map.toString());

						try {
							MixedArray msgBody = map.getArray("params");
							final String userId = String.valueOf(msgBody.get(0));// userId
							int cmd = NumberUtils.toInt(String.valueOf(msgBody.get(1)));

							//
							DeliveryOptions option = new DeliveryOptions();
							option.setSendTimeout(3000);
							option.addHeader("action", "getInnerNode");

							Future<Message<JsonObject>> chFuture = Future.future();

							JsonObject message = new JsonObject();
							message.put("userId", userId);
							if (StringUtils.isNotEmpty(userId)) {
								eb.<JsonObject>send(SocketConsistentHashingVerticle.class.getName() + innerIP, message,
										option, chFuture.completer());
							} else {

							}

							Future<Message<JsonObject>> ssFuture = Future.future();

							chFuture.setHandler(res -> {
								if (res.succeeded()) {
									JsonObject jsonRes = res.result().body();
									String hostIP = jsonRes.getString("host");

									DeliveryOptions msOption = new DeliveryOptions();
									msOption.setSendTimeout(3000);
									msOption.addHeader("action", "sendMsg");

									logger.info("UDP userId={}innerIP={}", userId, hostIP);

									JsonObject msgData = new JsonObject();
									MixedArray bodyArray = msgBody.getArray(3);
									msgData.put("nick", bodyArray.getString("nick"));
									msgData.put("msgId", bodyArray.getString("msgId"));
									msgData.put("body", bodyArray.getString("body"));

									JsonObject msg2Send = new JsonObject();
									msg2Send.put("cmd", cmd);
									msg2Send.put("data", msgData.encode());

									JsonObject param = new JsonObject();
									param.put("userId", userId);
									param.put("msg", msg2Send);

									eb.<JsonObject>send(MessageSendVerticle.class.getName() + hostIP, param, msOption,
											ssFuture.completer());

								} else {

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
