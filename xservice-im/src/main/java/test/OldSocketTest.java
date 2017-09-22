package test;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

public class OldSocketTest {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		NetClient client = vertx.createNetClient();

		// 1
		String req = "get /mobile?user=13666098&hash=UDE4NTEwMjUyNzk5fDE1MDU4Nzg4NzcyMzY.&mid=iphone&cid=AppStore&ver=5.2.0 HTTP/1.0";

		client.connect(8088, "111.206.162.233", con -> {
			if (con.succeeded()) {
				NetSocket socket = con.result();
				socket.write(req);
			} else {

			}
		});
	}

}
