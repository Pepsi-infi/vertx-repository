package test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.parsetools.RecordParser;
import util.ByteUtil;

public class TCPTest extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		NetServerOptions options = new NetServerOptions().setPort(4321);
		// options.setSsl(true).setPemKeyCertOptions(
		// new
		// PemKeyCertOptions().setKeyPath("server-key2.pem").setCertPath("server-cert.pem"));
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(socket -> {
			socket.handler(RecordParser.newDelimited("\001", buffer -> {
				
				System.out.println(buffer.length());
				
				int headerLength = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(0, 2));

				int clientVersion = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(2, 4));
				int cmd = ByteUtil.bytesToInt(buffer.getBytes(4, 8));
				int bodyLength = ByteUtil.bytesToInt(buffer.getBytes(8, 12));
			}));

			socket.closeHandler(v -> {
			});
		});

		server.listen();

	}
}
