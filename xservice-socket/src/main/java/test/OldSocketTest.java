package test;

import io.vertx.core.Vertx;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;

public class OldSocketTest {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions());
		socket.listen(1234, "192.168.2.220", asyncResult -> {
			if (asyncResult.succeeded()) {
				socket.handler(packet -> {
					// Do something with the packet
				});
			} else {
				System.out.println("Listen failed" + asyncResult.cause());
			}
		});
	}

}
