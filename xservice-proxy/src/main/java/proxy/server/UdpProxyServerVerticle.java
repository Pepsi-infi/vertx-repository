package proxy.server;

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

	private int udpPort;

	private List<String> oldUDPServer;
	private List<String> newUDPServer;

	private int count;

	private String oldIP;
	private String newIP;

	@SuppressWarnings("unchecked")
	@Override
	public void start() throws Exception {
		innerIP = IPUtil.getInnerIP();
		udpPort = config().getInteger("udp.port");

		oldUDPServer = config().getJsonArray("udp.server").getList();
		newUDPServer = config().getJsonArray("new.udp.server").getList();

		count = 0;

		logger.info("innerIP={}updPort={}config={}", innerIP, udpPort, config().encode());

		DatagramSocket receiver = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		receiver.listen(udpPort, innerIP, asyncResult -> {
			if (asyncResult.succeeded()) {
				logger.info("UDP listening...");
				receiver.handler(packet -> {
					count++;
					oldIP = oldUDPServer.get(Math.abs(count % oldUDPServer.size()));
					receiver.send(packet.data(), 9098, oldIP, handler -> {
						if (handler.succeeded()) {
							logger.info("UDP packet, oldIP={}data={}", oldIP, packet.data());
						} else {
							logger.error(handler.cause().getMessage());
						}
					});

					newIP = newUDPServer.get(Math.abs(count % newUDPServer.size()));
					receiver.send(packet.data(), 9099, newIP, r -> {
						if (r.succeeded()) {
							logger.info("UDP packet, newIP={}data={}", newIP, packet.data());
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
