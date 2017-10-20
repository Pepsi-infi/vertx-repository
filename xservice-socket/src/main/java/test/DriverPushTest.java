package test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.json.JsonObject;
import serializer.SocketByteUtils;

public class DriverPushTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		Vertx vertx = Vertx.vertx();

		DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions());

		Map<String, Object> params = new HashMap<>();
		Map<String, Object> otherParams = new HashMap<>();

		List<Object> list = new ArrayList<Object>();

		Map<String, Object> msgInfo = new HashMap<String, Object>();

		JsonObject body = new JsonObject();
		body.put("title", "这是标题");
		body.put("detil", "这是内容");
		body.put("iScreen", "0");
		body.put("chatUserId", "2743");
		body.put("msgTime", "2017-10-16 13:10:19");
		body.put("ifImport", "1");
		body.put("newId", String.valueOf(408));//int 不能超过int

		msgInfo.put("body", body.encode());
		msgInfo.put("nick", null);
		msgInfo.put("msgId", UUID.randomUUID().toString());
		msgInfo.put("title", "王龙虎");

		if (otherParams != null) {
			msgInfo.putAll(otherParams);
		}

		list.add(2743);// to charUserId
		list.add(406);// type
		list.add(1);
		list.add(msgInfo);

		params.put("method", "sendmsg");
		params.put("params", list);

		System.out.println(new String(SocketByteUtils.objectToByte(params), "UTF-8"));

		socket.send(Buffer.buffer().appendBytes(SocketByteUtils.objectToByte(params)), 9099, "10.10.10.105", res -> {
			System.out.println("105" + res.succeeded());
		});
		socket.send(Buffer.buffer().appendBytes(SocketByteUtils.objectToByte(params)), 9099, "10.10.10.106", res -> {
			System.out.println("106" + res.succeeded());
		});
	}
}
