package util;

public class ByteUtil {

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

	/**
	 * Convert byte[] to hex string
	 * 
	 * @param src
	 * @return
	 */
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
