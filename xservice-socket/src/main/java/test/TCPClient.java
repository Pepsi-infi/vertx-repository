package test;

import java.util.Random;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.PemTrustOptions;

public class TCPClient {

	public static void main(String[] args) throws InterruptedException {
		Vertx vertx = Vertx.vertx();
		NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
		// options.setSsl(true).setPemTrustOptions(new
		// PemTrustOptions().addCertPath("server-cert.pem"));
		NetClient client = vertx.createNetClient(options);
		client.connect(4321, "127.0.0.1", res -> {
			if (res.succeeded()) {
				NetSocket socket = res.result();

				// 1001
				byte[] headerLength = intToBytes(24);
				byte[] clientVersion = intToBytes(10001);
				byte[] cmdId = intToBytes(1001);
				byte[] seq = intToBytes(1);
				JsonObject msgBody = new JsonObject().put("from", "11111");
				byte[] bodyLength = intToBytes(msgBody.toString().length());
				Buffer msg = Buffer.buffer().appendBytes(headerLength).appendBytes(clientVersion).appendBytes(cmdId)
						.appendBytes(seq).appendBytes(bodyLength).appendString(msgBody.toString()).appendString("^A");
				// Buffer bf = Buffer.buffer();
				// bf.appendString("/connect#" + System.currentTimeMillis() + "\n");
				socket.write(msg);

				// 1002
				// byte[] headerLength2 = intToBytes(24);
				// byte[] clientVersion2 = intToBytes(10001);
				// byte[] cmdId2 = intToBytes(1002);
				// byte[] seq2 = intToBytes(1);
				// JsonObject msgBody2 = new JsonObject().put("from", 11111);
				// byte[] bodyLength2 = intToBytes(msgBody.toString().length());
				// Buffer msg2 =
				// Buffer.buffer().appendBytes(headerLength2).appendBytes(clientVersion2).appendBytes(cmdId2)
				// .appendBytes(seq2).appendBytes(bodyLength2).appendString(msgBody.toString()).appendString("\n");
				// Buffer bf = Buffer.buffer();
				// bf.appendString("/connect#" + System.currentTimeMillis() + "\n");
				// socket.write(msg2);
			} else {
				System.out.println("Failed to connect: " + res.cause().getMessage());
			}
		});
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

	/**
	 * short整数转换为2字节的byte数组
	 * 
	 * @param s
	 *            short整数
	 * @return byte数组
	 */
	public static byte[] unsignedShortToByte2(int s) {
		byte[] targets = new byte[2];
		targets[0] = (byte) (s >> 8 & 0xFF);
		targets[1] = (byte) (s & 0xFF);
		return targets;
	}

	/**
	 * byte数组转换为无符号short整数
	 * 
	 * @param bytes
	 *            byte数组
	 * @return short整数
	 */
	public static int byte2ToUnsignedShort(byte[] bytes) {
		return byte2ToUnsignedShort(bytes, 0);
	}

	/**
	 * byte数组转换为无符号short整数
	 * 
	 * @param bytes
	 *            byte数组
	 * @param off
	 *            开始位置
	 * @return short整数
	 */
	public static int byte2ToUnsignedShort(byte[] bytes, int off) {
		int high = bytes[off];
		int low = bytes[off + 1];
		return (high << 8 & 0xFF00) | (low & 0xFF);
	}

	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	/**
	 * 随机ID生成器，由数字、小写字母和大写字母组成
	 * 
	 * @param size
	 * @return
	 */
	public static String id(int size) {
		Random random = new Random();
		char[] cs = new char[size];
		for (int i = 0; i < cs.length; i++) {
			cs[i] = digits[random.nextInt(digits.length)];
		}
		return new String(cs);
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
