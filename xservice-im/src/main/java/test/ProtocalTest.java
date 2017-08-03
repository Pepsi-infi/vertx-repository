package test;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import util.ByteUtil;

public class ProtocalTest {

	public static void main(String[] args) {
		byte[] headerLength = ByteUtil.intToBytes(24);
		byte[] clientVersion = ByteUtil.intToBytes(10001);
		byte[] cmdId = ByteUtil.intToBytes(2001);
		byte[] seq = ByteUtil.intToBytes(1);
		JsonObject msgBody = new JsonObject().put("from", 11111).put("to", 22222).put("content", "hello");
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
	}
}
