package util;

import java.io.IOException;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;

import logic.message.Message;

public class ProtoBufUtil {

	private static final Codec<Message> messageCodec;

	static {
		messageCodec = ProtobufProxy.create(Message.class);
	}

	public static void main(String[] args) throws IOException {
		Message msg = new Message();
		msg.setContent("hello");

		String code = ProtobufIDLGenerator.getIDL(Message.class);
		System.out.println(code);
		System.out.println(ProtoBufUtil.messageCodec.encode(msg).length);
	}
}
