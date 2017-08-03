package server;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.net.NetClientOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.net.NetClient;
import io.vertx.rxjava.core.net.NetSocket;

public class TCPClient extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
		NetClient client = vertx.createNetClient(options);
		for (int i = 0; i < 5000; i++) {
			client.connect(1234, "localhost", res -> {
				if (res.succeeded()) {
					System.out.println("Connected!");
					NetSocket socket = res.result();
					Buffer buff = Buffer.buffer();
					buff.appendString("1");
					socket.write(buff);
				} else {
					System.out.println("Failed to connect: " + res.cause().getMessage());
				}
			});
		}
	}

	public static void main(String[] args) {
		Vertx v = Vertx.vertx();
		v.deployVerticle("server.TCPClient", new DeploymentOptions().setWorker(true));
	}
}
