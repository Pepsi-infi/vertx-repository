package protocol;

import io.vertx.core.buffer.Buffer;
import util.ByteUtil;

public class MessageBuilder {

	public static final int HEADER_LENGTH = 24;

	/**
	 * msg header: headerLength [0-4] clientVersion[5-8] cmdid[9-12] seq [13-16]
	 * bodyLength [17-20]
	 */
	public static Buffer buildMsgHeader(int headerLength, int clientVersion, int cmdId, int seq, int bodyLength) {
		Buffer buffer = Buffer.buffer();
		buffer.appendBytes(ByteUtil.intToBytes(headerLength));
		buffer.appendBytes(ByteUtil.intToBytes(clientVersion));
		buffer.appendBytes(ByteUtil.intToBytes(cmdId));
		buffer.appendBytes(ByteUtil.intToBytes(seq));
		buffer.appendBytes(ByteUtil.intToBytes(bodyLength));

		return buffer;
	}

}
