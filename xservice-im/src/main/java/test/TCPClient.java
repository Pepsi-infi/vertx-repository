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
					byte[] headerLength = intToBytes(20);
					byte[] clientVersion = floatToBytes(1.43f);
					byte[] cmdId = intToBytes(1000);
					byte[] seq = intToBytes(4320);
					byte[] bodyLength = intToBytes(3000);
					Buffer msg = Buffer.buffer().appendBytes(headerLength).appendBytes(clientVersion).appendBytes(cmdId)
							.appendBytes(seq).appendBytes(bodyLength).appendString("body");
					bf.appendString("/connect#" + System.currentTimeMillis() + "\n");
					socket.write(msg);
				} else {
					System.out.println("Failed to connect: " + res.cause().getMessage());
				}
			});
		}
	}

	/**
	 * 将一个整数转换位字节数组(4个字节)，b[0]存储高位字符，大端
	 * 
	 * @param i
	 *            整数
	 * @return 代表整数的字节数组
	 */
	public static byte[] intToBytes(int i) {
		byte[] b = new byte[4];
		b[0] = (byte) (i >>> 24);
		b[1] = (byte) (i >>> 16);
		b[2] = (byte) (i >>> 8);
		b[3] = (byte) i;
		return b;
	}

	/**
	 * 将一个浮点数转换为字节数组（4个字节），b[0]存储高位字符，大端
	 * 
	 * @param f
	 *            浮点数
	 * @return 代表浮点数的字节数组
	 */
	public static byte[] floatToBytes(float f) {
		return intToBytes(Float.floatToIntBits(f));
	}

	/**
	 * 将一个4位字节数组转换为4整数。<br>
	 * 注意，函数中不会对字节数组长度进行判断，请自行保证传入参数的正确性。
	 * 
	 * @param b
	 *            字节数组
	 * @return 整数
	 */
	public static int bytesToInt(byte[] b) {
		int i = (b[0] << 24) & 0xFF000000;
		i |= (b[1] << 16) & 0xFF0000;
		i |= (b[2] << 8) & 0xFF00;
		i |= b[3] & 0xFF;
		return i;
	}

	/**
	 * 将一个4位字节数组转换为浮点数。<br>
	 * 注意，函数中不会对字节数组长度进行判断，请自行保证传入参数的正确性。
	 * 
	 * @param b
	 *            字节数组
	 * @return 浮点数
	 */
	public static float bytesToFloat(byte[] b) {
		return Float.intBitsToFloat(bytesToInt(b));
	}
}
