package proxy.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import utils.IPUtil;

public class UdpProxyServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(UdpProxyServerVerticle.class);

	private String innerIP;

	@Override
	public void start() throws Exception {
		innerIP = IPUtil.getInnerIP();

		DatagramSocket sender = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));

		DatagramSocket receiver = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		receiver.listen(9099, innerIP, asyncResult -> {
			if (asyncResult.succeeded()) {
				logger.info("UDP listening...");
				receiver.handler(packet -> {
					logger.info("UDP packet " + packet.data());
					sender.send(packet.data(), 9, "", handler -> {
						if (handler.succeeded()) {

						} else {
							logger.error(handler.cause().getMessage());
						}
					});
				});
			} else {
				logger.error("UDP", asyncResult.cause());
			}
		});
	}
}
