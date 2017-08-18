package util;

import java.util.HashMap;
import java.util.Map;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;

import logic.message.Message;

public class ProtoBufUtil {

	private static final Map<String, Codec> protobufCodec = new HashMap<>();

	static {
		protobufCodec.put(Message.class.getName(), ProtobufProxy.create(Message.class));
	}
}
