package test;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import util.ByteUtil;

public class ProtocalTest {

	public static void main(String[] args) {
		byte[] headerLength = ByteUtil.intToBytes(20);
		byte[] clientVersion = ByteUtil.intToBytes(100);
		byte[] cmdId = ByteUtil.intToBytes(1001);
		byte[] seq = ByteUtil.intToBytes(1);
		JsonObject msgBody = new JsonObject().put("from", "11111").put("to", "22222").put("content", "hello");
		byte[] body = ByteUtil.intToBytes(msgBody.toString().length());
		System.out.println(headerLength.length + " " + " " + clientVersion.length + " " + cmdId.length + " "
				+ seq.length + " " + msgBody.toString().getBytes().length);
		Buffer msg = Buffer.buffer().appendBytes(headerLength).appendBytes(clientVersion).appendBytes(cmdId)
				.appendBytes(seq).appendBytes(body).appendString(msgBody.toString()).appendString("\n");

		System.out.println(ByteUtil.bytesToHexString(
				Buffer.buffer().appendBytes(headerLength).appendBytes(clientVersion).appendBytes(cmdId).appendBytes(seq)
						.appendBytes(body).appendString(msgBody.toString()).appendString("\n").getBytes()));

		System.out.println(String
				.valueOf(ByteUtil.hexStringToBytes("00 00 00 18 00 00 27 11 00 00 07 D3 00 00 00 01 00 00 00 57")));

		// 1、login user 111 action 1001 clientVersion 100
		byte[] headerLength1 = ByteUtil.unsignedShortToByte2(12);
		byte[] clientVersion1 = ByteUtil.unsignedShortToByte2(999);
		byte[] cmdId1 = ByteUtil.intToBytes(1001);
		JsonObject msgBody1 = new JsonObject().put("userTel", "123456");
		byte[] body1 = ByteUtil.intToBytes(msgBody1.toString().length());
		System.out.println("1 " + ByteUtil.bytesToHexString(
				Buffer.buffer().appendBytes(headerLength1).appendBytes(clientVersion1).appendBytes(cmdId1)
						.appendBytes(body1).appendString(msgBody1.toString()).appendString("\001").getBytes()));
		// 2、login user 222 action 1001
		byte[] headerLength2 = ByteUtil.intToBytes(20);
		byte[] clientVersion2 = ByteUtil.intToBytes(100);
		byte[] cmdId2 = ByteUtil.intToBytes(1001);
		byte[] seq2 = ByteUtil.intToBytes(1);
		JsonObject msgBody2 = new JsonObject().put("from", "222");
		byte[] body2 = ByteUtil.intToBytes(msgBody2.toString().length());
		System.out.println("login user 222 action 1001 clientVersion 100 " + ByteUtil.bytesToHexString(Buffer.buffer()
				.appendBytes(headerLength2).appendBytes(clientVersion2).appendBytes(cmdId2).appendBytes(seq2)
				.appendBytes(body2).appendString(msgBody2.toString()).appendString("\n").getBytes()));
		// 3、logout user 111 action 1002

		// 4、msg r user 111 to 222 action 2001
		byte[] headerLength4 = ByteUtil.intToBytes(20);
		byte[] clientVersion4 = ByteUtil.intToBytes(100);
		byte[] cmdId4 = ByteUtil.intToBytes(2001);
		byte[] seq4 = ByteUtil.intToBytes(1);
		JsonObject msgBody4 = new JsonObject().put("from", "111").put("to", "222").put("content", "hello");
		byte[] body4 = ByteUtil.intToBytes(msgBody4.toString().length());
		System.out.println("msg r user 111 to 222 action 2001 clientVersion 100 " + ByteUtil.bytesToHexString(Buffer
				.buffer().appendBytes(headerLength4).appendBytes(clientVersion4).appendBytes(cmdId4).appendBytes(seq4)
				.appendBytes(body4).appendString(msgBody4.toString()).appendString("\n").getBytes()));

		// 5、Heart Beat
		byte[] headerLength5 = ByteUtil.unsignedShortToByte2(12);
		byte[] clientVersion5 = ByteUtil.unsignedShortToByte2(44);
		byte[] cmdId5 = ByteUtil.intToBytes(0);
		byte[] bodyLength5 = ByteUtil.intToBytes(0);
		System.out.println(
				"5 " + ByteUtil.bytesToHexString(Buffer.buffer().appendBytes(headerLength5).appendBytes(clientVersion5)
						.appendBytes(cmdId5).appendBytes(bodyLength5).appendString("\001").getBytes()));
	}
}
