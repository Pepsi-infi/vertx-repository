package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class UdpServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(UdpServerVerticle.class);

	@Override
	public void start() throws Exception {
		DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		socket.listen(4321, "192.168.2.220", asyncResult -> {
			if (asyncResult.succeeded()) {
				socket.handler(packet -> {
					logger.info("1101 Receive " + packet.data());
				});
			} else {
			}
		});
	}
}
