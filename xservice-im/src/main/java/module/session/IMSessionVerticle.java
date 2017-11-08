package module.session;

import org.apache.commons.lang.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import utils.IPUtil;

public class IMSessionVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(IMSessionVerticle.class);

	private EventBus eb;
	private Cache<String, String> sessionMap;// uid -> handlerID
	private Cache<String, String> sessionReverse;// handlerID -> uid

	public interface method {

		public static final String setUserSocket = "setUserSocket";

		public static final String delUserSocket = "delUserSocket";

		public static final String getHandlerIDByUid = "getHandlerIDByUid";
	}

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		int memSize = config().getInteger("im.memory.size").intValue();
		logger.info("im.memory.size={}", memSize);
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("session",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
								ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(memSize, MemoryUnit.MB)))
				.withCache("sessionReverse",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
								ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(memSize, MemoryUnit.MB)))
				.build(true);
		sessionMap = cacheManager.getCache("session", String.class, String.class);
		sessionReverse = cacheManager.getCache("sessionReverse", String.class, String.class);

		String innerIP = IPUtil.getInnerIP();

		logger.info("innerIP={}", innerIP);

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(IMSessionVerticle.class.getName() + innerIP, res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String from = body.getString("from");
				String handlerID = body.getString("handlerID");
				String to = body.getString("to");
				logger.info("from={}action={}innerIP={}", from, action, innerIP);
				switch (action) {
				case method.setUserSocket:
					res.reply(setUserSocket(from, handlerID));
					break;
				case method.delUserSocket:
					res.reply(delUserSocket(from, handlerID));
					break;
				case method.getHandlerIDByUid:
					res.reply(getHandlerIDByUid(to));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});

		eb.<JsonObject>consumer(IMSessionVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String from = body.getString("from");
				String handlerID = body.getString("handlerID");
				logger.info("from={}action={}innerIP={}", from, action, innerIP);
				switch (action) {
				case "delUserSocket":
					res.reply(delUserSocket(from, handlerID));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	public int setUserSocket(String uid, String handlerId) {
		long start = System.currentTimeMillis();

		this.sessionMap.put(uid, handlerId);
		this.sessionReverse.put(handlerId, uid);

		long end = System.currentTimeMillis();

		long cost = end - start;
		if ((cost) > 20) {
			logger.warn("setUserSocket, cost={}", cost);
		}
		return 0;
	}

	public int delUserSocket(String uid, String handlerId) {
		long start = System.currentTimeMillis();

		if (StringUtils.isNotEmpty(uid)) {
			// logout
			this.sessionMap.remove(uid);
			this.sessionReverse.remove(handlerId);
		} else {
			// socket close
			uid = sessionReverse.get(handlerId);
			if (uid != null) {
				sessionMap.remove(uid);
			}
			sessionReverse.remove(handlerId);
		}

		long end = System.currentTimeMillis();

		long cost = end - start;
		if ((cost) > 20) {
			logger.warn("delUserSocket, cost={}", cost);
		}

		return 0;
	}

	private JsonObject getHandlerIDByUid(String uid) {
		long start = System.currentTimeMillis();

		JsonObject jo = new JsonObject();
		jo.put("handlerID", sessionMap.get(uid));

		long end = System.currentTimeMillis();

		long cost = end - start;
		if ((cost) > 20) {
			logger.warn("getHandlerIDByUid, cost={}", cost);
		}
		logger.info("getHandlerIDByUid, uid={} result={}", uid, jo.encode());
		return jo;
	}
}
