package cluster.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.servicediscovery.Record;
import xservice.BaseServiceVerticle;

public class SocketConsistentHashingVerticle extends BaseServiceVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketConsistentHashingVerticle.class);

	// 真实节点对应的虚拟节点数量
	private int length = 160;

	// 虚拟Socket节点信息
	private TreeMap<Long, String> virtualNodes;

	// 虚拟内网ip
	private TreeMap<Long, String> virtualInnerNodes;

	// 真实节点信息
	private List<String> realSocketNodes;

	private List<String> realInnerNodes;

	private EventBus eb;

	@Override
	public void start() throws Exception {
		super.start();
		
		logger.info("start ... ");
		this.realSocketNodes = new ArrayList<String>();

		this.realSocketNodes.add("111.206.162.233:8088");
		this.realSocketNodes.add("111.206.162.234:8088");

		JsonObject filter = new JsonObject().put("type", "socket-server");
		vertx.setPeriodic(5000, handler -> {
			discovery.getRecords(filter, result -> {
				if (result.succeeded()) {
					List<Record> records = result.result();
					for (Record r : records) {
						String address = r.getMetadata().getString("publicAddress");
						logger.info("publicAddress, {}", address);
					}
				}
			});
		});

		this.realInnerNodes = new ArrayList<String>();
		realInnerNodes.add("10.10.10.102");
		realInnerNodes.add("10.10.10.103");

		initSocketNodes();
		initInnerNodes();

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(SocketConsistentHashingVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject param = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String key = null;
				switch (action) {
				case "getSocketNode":
					key = param.getString("userId");
					res.reply(getNode(key));
					break;
				case "getInnerNode":
					key = param.getString("userId");
					res.reply(getInnerNode(key));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	/**
	 * 初始化虚拟节点
	 */
	private void initSocketNodes() {
		virtualNodes = new TreeMap<Long, String>();
		for (int i = 0; i < realSocketNodes.size(); i++) {
			for (int j = 0; j < length; j++) {
				virtualNodes.put(hash("aa" + i + j), realSocketNodes.get(i));
			}
		}
	}

	/**
	 * 初始化虚拟内网IP
	 */
	private void initInnerNodes() {
		virtualInnerNodes = new TreeMap<Long, String>();
		for (int i = 0; i < realInnerNodes.size(); i++) {
			for (int j = 0; j < length; j++) {
				virtualInnerNodes.put(hash("aa" + i + j), realInnerNodes.get(i));
			}
		}
	}

	/**
	 * MurMurHash算法，是非加密HASH算法，性能很高，
	 * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
	 * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
	 */
	private Long hash(String key) {
		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x1234ABCD;

		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);

		long m = 0xc6a4a7935bd1e995L;
		int r = 47;

		long h = seed ^ (buf.remaining() * m);

		long k;
		while (buf.remaining() >= 8) {
			k = buf.getLong();

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
			// for big-endian version, do this first:
			// finish.position(8-buf.remaining());
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		buf.order(byteOrder);
		return h;
	}

	/**
	 * 
	 * @param key
	 * @param nodes
	 * @param resultHandler
	 */
	public JsonObject getNode(String key) {
		JsonObject result = new JsonObject();
		Long hashedKey = hash(key);
		Entry<Long, String> en = virtualNodes.ceilingEntry(hashedKey);
		if (en == null) {
			result.put("host", virtualNodes.firstEntry().getValue());
		} else {
			result.put("host", en.getValue());
		}

		return result;
	}

	/**
	 * 
	 * @param key
	 * @param nodes
	 * @param resultHandler
	 */
	public JsonObject getInnerNode(String key) {
		JsonObject result = new JsonObject();
		Long hashedKey = hash(key);
		Entry<Long, String> en = virtualInnerNodes.ceilingEntry(hashedKey);
		if (en == null) {
			result.put("host", virtualInnerNodes.firstEntry().getValue());
		} else {
			result.put("host", en.getValue());
		}

		return result;
	}

	public void getNodeWithNodeList(String key, List<String> nodes, Handler<AsyncResult<String>> resultHandler) {

	}
}
