package server;

import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.net.NetServer;

public class TCPServer extends AbstractVerticle{

	private long count = 0;
	
	@Override
	public void start() throws Exception {
		NetServer server = vertx.createNetServer();
		server.connectHandler(socket -> {
			  socket.handler(buffer -> {
				  count = count + Integer.valueOf(buffer.getString(0, 1));
			    System.out.println("total clients : " + count);
			  });
			});
		server.listen(1234, "localhost", res -> {
		  if (res.succeeded()) {
		    System.out.println("Server is now listening!");
		  } else {
		    System.out.println("Failed to bind!");
		  }
		});
	}
	
	public static void main(String[] args) {
		Vertx v = Vertx.vertx();
		v.deployVerticle("server.TCPServer");
	}
}
