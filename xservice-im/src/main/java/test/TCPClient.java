package test;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

public class TCPClient {

	public static void main(String[] args) throws InterruptedException {
		Vertx vertx = Vertx.vertx();
		NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
		NetClient client = vertx.createNetClient(options);
		for (int i = 0; i < 10000; i++) {
			Thread.sleep(5);
			client.connect(1234, "localhost", res -> {
				if (res.succeeded()) {
					NetSocket socket = res.result();
					Buffer bf = Buffer.buffer();
					bf.appendString("/connect#" + System.currentTimeMillis() + "\n");
					socket.write(bf);
				} else {
					System.out.println("Failed to connect: " + res.cause().getMessage());
				}
			});
		}
	}

}
