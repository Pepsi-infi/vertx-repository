package cluster.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import cluster.ConsistentHashingService;
import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ConsistentHashingVerticle extends AbstractVerticle implements ConsistentHashingService {

	private static final Logger logger = LoggerFactory.getLogger(ConsistentHashingVerticle.class);

	// 真实节点对应的虚拟节点数量
	private int length = 160;
	// 虚拟节点信息
	private TreeMap<Long, String> virtualNodes;
	// 真实节点信息
	private List<String> realNodes;

	@Override
	public void start() throws Exception {
		logger.info("start ... ");
		this.realNodes = new ArrayList<String>();

//		this.realNodes.add("10.10.10.193");// TODO
		this.realNodes.add("127.0.0.1");// TODO
		init();

		XProxyHelper.registerService(ConsistentHashingService.class, vertx, this,
				ConsistentHashingService.SERVICE_ADDRESS);
	}

	/**
	 * 初始化虚拟节点
	 */
	private void init() {
		virtualNodes = new TreeMap<Long, String>();
		for (int i = 0; i < realNodes.size(); i++) {
			for (int j = 0; j < length; j++) {
				virtualNodes.put(hash("aa" + i + j), realNodes.get(i));
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
	 * 获取一个结点
	 * 
	 * @param uid
	 * @return
	 */
	public void getNode(String uid, Handler<AsyncResult<String>> resultHandler) {
		Long hashedKey = hash(uid);
		Entry<Long, String> en = virtualNodes.ceilingEntry(hashedKey);
		if (en == null) {
			resultHandler.handle(Future.succeededFuture(virtualNodes.firstEntry().getValue()));
		} else {
			resultHandler.handle(Future.succeededFuture(en.getValue()));
		}
	}
}
