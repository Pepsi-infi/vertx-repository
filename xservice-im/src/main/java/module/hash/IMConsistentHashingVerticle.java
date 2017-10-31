package module.hash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.servicediscovery.Record;
import xservice.BaseServiceVerticle;

public class IMConsistentHashingVerticle extends BaseServiceVerticle {

	private static final Logger logger = LoggerFactory.getLogger(IMConsistentHashingVerticle.class);

	// 真实节点对应的虚拟节点数量
	private int length = 160;
	// 虚拟节点信息
	private TreeMap<Integer, String> virtualIMNodes;

	// 虚拟内网ip
	private TreeMap<Integer, String> virtualInnerNodes;

	// 真实节点信息
	private List<String> realIMNodes;

	private List<String> realInnerNodes;

	private EventBus eb;

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("start ... ");

		this.realIMNodes = new ArrayList<String>();
		this.realInnerNodes = new ArrayList<String>();

		getNodesFromDiscovery();

//		vertx.setPeriodic(5000, handler -> {
//			getNodesFromDiscovery();
//		});

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(IMConsistentHashingVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject param = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String key = null;
				switch (action) {
				case "getIMNode":
					key = param.getString("userId");
					res.reply(getIMNode(key));
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

	private void getNodesFromDiscovery() {
		JsonObject filter = new JsonObject().put("type", "im-server");
		discovery.getRecords(filter, result -> {
			if (result.succeeded()) {
				List<Record> records = result.result();
				for (Record r : records) {
					String publicAddress = r.getMetadata().getString("publicAddress");
					String innerIP = r.getMetadata().getString("innerIP");
					if (!realIMNodes.contains(publicAddress) && StringUtils.isNotEmpty(publicAddress)) {
						realIMNodes.add(publicAddress);
					}
					if (!realInnerNodes.contains(innerIP) && StringUtils.isNotEmpty(innerIP)) {
						realInnerNodes.add(innerIP);
					}
				}

				initIMNodes();
				initInnerNodes();

				logger.info("realIMNodes={}realInnerNodes={}", realIMNodes.toString(), realInnerNodes.toString());
			}
		});
	}

	/**
	 * 初始化虚拟节点
	 */
	private void initIMNodes() {
		virtualIMNodes = new TreeMap<Integer, String>();
		for (int i = 0; i < realIMNodes.size(); i++) {
			for (int j = 0; j < length; j++) {
				virtualIMNodes.put(hash("aa" + i + j), realIMNodes.get(i));
			}
		}
	}

	/**
	 * 初始化虚拟节点
	 */
	private void initInnerNodes() {
		virtualInnerNodes = new TreeMap<Integer, String>();
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
	private int hash(String key) {
		HashFunction hf = Hashing.murmur3_32();
		HashCode hc = hf.newHasher().putString(key, Charsets.UTF_8).hash();

		return hc.asInt();
	}

	/**
	 * 获取一个结点
	 * 
	 * @param uid
	 * @return
	 */
	public JsonObject getIMNode(String key) {
		JsonObject result = new JsonObject();
		int hashedKey = hash(key);

		Entry<Integer, String> en = virtualIMNodes.ceilingEntry(hashedKey);

		if (en == null) {
			result.put("host", virtualIMNodes.firstEntry().getValue());
		} else {
			result.put("host", en.getValue());
		}

		return result;
	}

	/**
	 * 获取一个结点
	 * 
	 * @param uid
	 * @return
	 */
	public JsonObject getInnerNode(String key) {
		JsonObject result = new JsonObject();
		int hashedKey = hash(key);
		Entry<Integer, String> en = virtualInnerNodes.ceilingEntry(hashedKey);
		if (en == null) {
			result.put("host", virtualInnerNodes.firstEntry().getValue());
		} else {
			result.put("host", en.getValue());
		}

		return result;
	}
}
