package test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import util.ByteUtil;

public class SocketClientVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
		NetClient client = vertx.createNetClient(options);
		for (int i = 0; i < 2000; i++) {
			client.connect(8088, "10.10.10.102", res -> {
				if (res.succeeded()) {
					NetSocket socket = res.result();

					long userId = System.currentTimeMillis();
					String login = "get /mobile?user=" + userId
							+ "&hash=UDE4NTEwMjUyNzk5fDE1MDY2Njc4OTEzMTY.&mid=android&ci\n" + "d=AnZhi_1&ver=5.2.1\n\n";
					Buffer bf = Buffer.buffer(ByteUtil.intToBytes(login.length())).appendBytes(login.getBytes());

					socket.write(bf);

					final String h = "{\"cmd\":14,\"data\":{\"lat\":39.920239,\"lon\":116.432999}}";
					Buffer hf = Buffer.buffer(ByteUtil.intToBytes(h.length())).appendBytes(h.getBytes());
					vertx.setPeriodic(3000, handler -> {
						socket.write(hf);
					});
				} else {
					System.out.println("Failed to connect: " + res.cause().getMessage());
				}
			});
		}

	}
}
