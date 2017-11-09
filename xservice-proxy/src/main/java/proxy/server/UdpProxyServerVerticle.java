package proxy.server;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import utils.IPUtil;

public class UdpProxyServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(UdpProxyServerVerticle.class);

	private String innerIP;

	private int updPort;

	private List<String> oldUDPServer;
	private List<String> newUDPServer;

	private int count;

	@Override
	public void start() throws Exception {
		innerIP = IPUtil.getInnerIP();
		updPort = config().getInteger("udp.port");
		oldUDPServer = new ArrayList<String>();
		newUDPServer = new ArrayList<String>();
		count = 0;

		logger.info("innerIP={}updPort={}config={}", config().encode());

		oldUDPServer.addAll(config().getJsonArray("udp.server").getList());
		newUDPServer.addAll(config().getJsonArray("new.udp.server").getList());

		DatagramSocket oldSender = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		DatagramSocket newSender = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));

		DatagramSocket receiver = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		receiver.listen(updPort, innerIP, asyncResult -> {
			if (asyncResult.succeeded()) {
				logger.info("UDP listening...");
				receiver.handler(packet -> {
					count++;
					logger.info("UDP packet " + packet.data());
					oldSender.send(packet.data(), 9098, oldUDPServer.get(Math.abs(count % oldUDPServer.size())),
							handler -> {
								if (handler.succeeded()) {

								} else {
									logger.error(handler.cause().getMessage());
								}
							});

					newSender.send(packet.data(), 9099, newUDPServer.get(Math.abs(count % newUDPServer.size())), r -> {
						if (r.succeeded()) {

						} else {
							logger.error(r.cause().getMessage());
						}
					});
				});
			} else {
				logger.error("UDP", asyncResult.cause());
			}
		});
	}
}
