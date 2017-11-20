package server;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import cluster.impl.SocketConsistentHashingVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import serializer.SocketByteUtils;
import serializer.UnSerializeResult;
import utils.IPUtil;

public class UdpServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(UdpServerVerticle.class);

	private EventBus eb;

	private String innerIP;

	private static final byte __Quote = 34;
	private static final byte __0 = 48;
	private static final byte __1 = 49;
	private static final byte __Colon = 58;
	private static final byte __Semicolon = 59;
	private static final byte __C = 67;
	private static final byte __N = 78;
	private static final byte __O = 79;
	private static final byte __R = 82;
	private static final byte __U = 85;
	private static final byte __Slash = 92;
	private static final byte __a = 97;
	private static final byte __b = 98;
	private static final byte __d = 100;
	private static final byte __i = 105;
	private static final byte __r = 114;
	private static final byte __s = 115;
	private static final byte __LeftB = 123;
	private static final byte __RightB = 125;
	private static final String __NAN = new String("NAN");
	private static final String __INF = new String("INF");
	private static final String __NINF = new String("-INF");

	@Override
	public void start() throws Exception {
		eb = vertx.eventBus();
		innerIP = IPUtil.getInnerIP();

		DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions().setReceiveBufferSize(204800));
		socket.listen(config().getInteger("udp.port"), innerIP, asyncResult -> {
			if (asyncResult.succeeded()) {
				logger.info("UDP listening...");
				socket.handler(packet -> {

					int pos = 0;

					logger.info("UDP packet " + packet.data());

					Map<String, Object> map = null;

					try {
						map = (Map<String, Object>) SocketByteUtils.byteToObject(packet.data().getBytes());
					} catch (Exception e) {
						logger.error("UDP unserialize packet={}e={}", packet.data(), e.getCause());
					}

					if (map != null) {
						logger.info("UDP Map " + map.toString());

						try {
							ArrayList<Object> msgBody = (ArrayList<Object>) map.get("params");
							final String userId = String.valueOf(msgBody.get(0));// userId
							int cmd = NumberUtils.toInt(String.valueOf(msgBody.get(1)));

							//
							DeliveryOptions option = new DeliveryOptions();
							option.setSendTimeout(3000);
							option.addHeader("action", "getInnerNode");

							Future<Message<JsonObject>> chFuture = Future.future();

							JsonObject message = new JsonObject();
							message.put("userId", userId);
							if (StringUtils.isNotEmpty(userId)) {
								eb.<JsonObject>send(SocketConsistentHashingVerticle.class.getName() + innerIP, message,
										option, chFuture.completer());
							} else {

							}

							Future<Message<JsonObject>> ssFuture = Future.future();

							chFuture.setHandler(res -> {
								if (res.succeeded()) {
									JsonObject jsonRes = res.result().body();
									String hostIP = jsonRes.getString("host");

									DeliveryOptions msOption = new DeliveryOptions();
									msOption.setSendTimeout(3000);
									msOption.addHeader("action", "sendMsg");

									logger.info("UDP userId={}innerIP={}", userId, hostIP);

									JsonObject data = JsonObject.mapFrom(msgBody.get(3));

									JsonObject msg2Send = new JsonObject();
									msg2Send.put("cmd", cmd);
									msg2Send.put("data", data);

									JsonObject param = new JsonObject();
									param.put("userId", userId);
									param.put("msg", msg2Send);

									eb.<JsonObject>send(MessageSendVerticle.class.getName() + hostIP, param, msOption,
											ssFuture.completer());

								} else {

								}
							});
						} catch (Exception e2) {
							logger.error("Get userId error ", e2);
						}
					} else {
						logger.info("Map is null.");
					}
				});
			} else {
				logger.error("UDP", asyncResult.cause());
			}
		});
	}

	private Map<String, Object> parseUDPData(Buffer buffer) {
		Map<Integer, Object> parseResult = new HashMap<Integer, Object>();
		Object obj;
		int hv = 1;
		int pos = 0;
		int k;
		int len;
		StringBuffer sbu;
		while (pos < buffer.length()) {
			int b = buffer.getByte(pos) & 0xFF;
			pos++;

			switch (b) {
			case __N:
				obj = readNull(b);
				pos++;
				parseResult.put(new Integer(hv++), obj);

			case __b:
				obj = readBoolean(b);
				pos++;
				parseResult.put(new Integer(hv++), obj);

			case __i:
				StringBuffer sbi = new StringBuffer();
				int i = buffer.getByte(pos) & 0xFF;
				pos++;

				while ((i != __Semicolon) && (i != __Colon)) {
					sbi.append((char) i);
					i = buffer.getByte(pos) & 0xFF;
					pos++;
				}

				obj = readInteger(sbi.toString());
				parseResult.put(new Integer(hv++), obj);

			case __d:
				StringBuffer sbd = new StringBuffer();
				int j = buffer.getByte(pos) & 0xFF;
				pos++;

				while ((j != __Semicolon) && (j != __Colon)) {
					sbd.append((char) j);
					j = buffer.getByte(pos) & 0xFF;
					pos++;
				}

				obj = readDouble(sbd.toString());
				parseResult.put(new Integer(hv++), obj);

			case __s:
				StringBuffer sbs = new StringBuffer();
				k = buffer.getByte(pos) & 0xFF;
				pos++;

				while ((k != __Semicolon) && (k != __Colon)) {
					sbs.append((char) i);
					k = buffer.getByte(pos) & 0xFF;
					pos++;
				}

				len = Integer.parseInt(sbs.toString());
				obj = buffer.getString(pos, pos + len, "UTF-8");
				parseResult.put(new Integer(hv++), obj);

			case __U:
				sbu = new StringBuffer();
				k = buffer.getByte(pos) & 0xFF;
				pos++;

				while ((k != __Semicolon) && (k != __Colon)) {
					sbu.append((char) i);
					k = buffer.getByte(pos) & 0xFF;
					pos++;
				}

				len = Integer.parseInt(sbu.toString());

				int c;
				byte[] byteArray = buffer.getBytes(pos, pos + len);
				StringBuffer u = new StringBuffer(len);
				for (int z = 0; z < len; z = z + 4) {
					if ((c = byteArray[z]) == __Slash) {
						char c1 = (char) byteArray[z];
						char c2 = (char) byteArray[z + 1];
						char c3 = (char) byteArray[z + 2];
						char c4 = (char) byteArray[z + 3];

						u.append((char) (Integer.parseInt(new String(new char[] { c1, c2, c3, c4 }), 16)));
					} else {
						u.append((char) c);
					}
				}

				parseResult.put(new Integer(hv++), u.toString());

			case __r:
				return readRef(stream, ht, hv, rt);

			case __a:
				return readArray(stream, ht, hv, rt, charset);

			case __O:
				return readObject(stream, ht, hv, rt, charset);

			case __C:
				return readCustomObject(stream, ht, hv, charset);

			case __R:
				return readPointRef(stream, ht, hv, rt);

			default:
				return null;
			}
		}

	}

	private Object readNull(int b) {
		return null;
	}

	private Object readBoolean(int b) {
		Boolean bl = new Boolean(b == __1);

		return bl;
	}

	private Number readInteger(String i) {
		try {
			return new Byte(i);
		} catch (Exception e1) {
			try {
				return new Short(i);
			} catch (Exception e2) {
				return new Integer(i);
			}
		}
	}

	private Number readDouble(String d) {
		if (d.equals(__NAN)) {
			return new Double(Double.NaN);
		}
		if (d.equals(__INF)) {
			return new Double(Double.POSITIVE_INFINITY);
		}
		if (d.equals(__NINF)) {
			return new Double(Double.NEGATIVE_INFINITY);
		}
		try {
			return new Long(d);
		} catch (Exception e1) {
			try {
				Float f = new Float(d);

				if (f.isInfinite()) {
					return new Double(d);
				} else {
					return f;
				}
			} catch (Exception e2) {
				return new Float(0);
			}
		}
	}

	private String readString(String s) {
		int len = Integer.parseInt(s);
		byte[] buf = new byte[len];

		buf = buffer.getBytes(0, len);

		String s = getString(buf, "UTF-8");

		pos = pos + 2;
		return s;
	}

	private String getString(byte[] data, String charset) {
		try {
			return new String(data, charset);
		} catch (Exception e) {
			return new String(data);
		}
	}

	private String readUnicodeString(Buffer buffer, int pos) {
		pos++;
		int l = Integer.parseInt(readNumber(buffer, pos));

		pos++;
		StringBuffer sb = new StringBuffer(l);
		int c;

		for (int i = 0; i < l; i++) {
			if ((c = buffer.getByte(pos)) == __Slash) {
				char c1 = (char) buffer.getByte(pos + 1);
				char c2 = (char) buffer.getByte(pos + 2);
				char c3 = (char) buffer.getByte(pos + 3);
				char c4 = (char) buffer.getByte(pos + 4);

				sb.append((char) (Integer.parseInt(new String(new char[] { c1, c2, c3, c4 }), 16)));
			} else {
				sb.append((char) c);
			}
		}
		pos = pos + 2;
		return sb.toString();
	}

	private String readNumber(Buffer buffer) {
		StringBuffer sb = new StringBuffer();
		int i = buffer.getByte(pos) & 0xFF;

		while ((i != __Semicolon) && (i != __Colon)) {
			sb.append((char) i);
			i = buffer.getByte(pos) & 0xFF;
		}

		return sb.toString();
	}

	private UnSerializeResult readRef(ByteArrayInputStream stream, HashMap ht, int hv, HashMap rt) {
		stream.skip(1);
		Integer r = new Integer(readNumber(stream));

		if (rt.containsKey(r)) {
			rt.put(r, new Boolean(true));
		}
		Object obj = ht.get(r);

		ht.put(new Integer(hv++), obj);
		return new UnSerializeResult(obj, hv);
	}
}
