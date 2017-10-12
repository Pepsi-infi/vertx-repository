package protocol;

import io.vertx.core.buffer.Buffer;
import util.ByteUtil;

public class MessageBuilder {

	public static final String IM_MSG_SEPARATOR = "\001";

	public static final int MSG_ACK_CMD_RADIX = 100;

	public static Buffer buildMsgHeader(int headerLength, int clientVersion, int cmdId, int bodyLength) {
		Buffer buffer = Buffer.buffer();
		buffer.appendBytes(ByteUtil.unsignedShortToByte2(headerLength));
		buffer.appendBytes(ByteUtil.unsignedShortToByte2(clientVersion));
		buffer.appendBytes(ByteUtil.intToBytes(cmdId));
		buffer.appendBytes(ByteUtil.intToBytes(bodyLength));

		return buffer;
	}

}
