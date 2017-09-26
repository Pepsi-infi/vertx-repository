package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class TestUdpServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(TestUdpServerVerticle.class);

	@Override
	public void start() throws Exception {
		DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions());
		socket.listen(1102, "192.168.2.220", asyncResult -> {
			if (asyncResult.succeeded()) {
				logger.info("1102 de " + asyncResult.succeeded());
				socket.handler(packet -> {
					logger.info("1102 Receive " + packet.data());
				});
			} else {
				logger.info("1102 er " + asyncResult.cause());
			}
		});
	}
}
