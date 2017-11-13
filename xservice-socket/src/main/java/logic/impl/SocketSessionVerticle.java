package logic.impl;

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

public class SocketSessionVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SocketSessionVerticle.class);

	private EventBus eb;
	private Cache<String, String> sessionMap;// uid -> handlerID
	private Cache<String, String> sessionReverse;// handlerID -> uid

	private long counter = 0;
	private long recounter = 0;

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		int memSize = config().getInteger("im.memory.size").intValue();
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

		eb.<JsonObject>consumer(SocketSessionVerticle.class.getName() + innerIP, res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String userId = body.getString("userId");
				String handlerID = body.getString("handlerID");

				switch (action) {
				case "setUserSocket":
					res.reply(setUserSocket(userId, handlerID));
					break;
				case "delUserSocket":
					res.reply(delUserSocket(userId, handlerID));
					break;
				case "getHandlerIDByUid":
					res.reply(getHandlerIDByUid(userId));
					break;
				case "getUidByHandlerID":
					res.reply(getUidByHandlerID(handlerID));
					break;
				case "status":
					res.reply(status(userId));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});

		eb.<JsonObject>consumer(SocketSessionVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String userId = body.getString("userId");
				String handlerID = body.getString("handlerID");
				String to = body.getString("to");
				logger.info("userId={}action={}innerIP={}", userId, action, innerIP);
				switch (action) {
				case "setUserSocket":
					res.reply(setUserSocket(userId, handlerID));
					break;
				case "delUserSocket":
					res.reply(delUserSocket(userId, handlerID));
					break;
				case "getHandlerIDByUid":
					res.reply(getHandlerIDByUid(to));
					break;
				case "getUidByHandlerID":
					res.reply(getUidByHandlerID(handlerID));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	public int setUserSocket(String userId, String handlerId) {
		long start = System.currentTimeMillis();
		this.sessionMap.put(userId, handlerId);
		this.sessionReverse.put(handlerId, userId);

		long end = System.currentTimeMillis();
		if ((end - start) > 10) {
			logger.warn("setUserSocket, handlerID={} userId={} waste={}", handlerId, userId, end - start);
		}

		counter++;
		recounter++;

		return 0;
	}

	public JsonObject delUserSocket(String uid, String handlerId) {
		if (StringUtils.isNotEmpty(uid)) {
			// logout
			this.sessionMap.remove(uid);
			this.sessionReverse.remove(handlerId);
		} else {
			// socket close
			sessionReverse.remove(handlerId);
			uid = sessionReverse.get(handlerId);
			if (uid != null) {
				sessionMap.remove(uid);
			}
		}

		counter--;
		recounter--;

		return new JsonObject().put("status", 0);
	}

	private JsonObject getHandlerIDByUid(String uid) {
		JsonObject jo = null;
		if (StringUtils.isNotEmpty(uid)) {
			String handlerID = sessionMap.get(uid);
			if (StringUtils.isNotEmpty(handlerID)) {
				jo = new JsonObject();
				jo.put("handlerID", handlerID);

				logger.info("getHandlerIDByUid, {}", jo.encode());
			}
		}

		return jo;
	}

	// ---------------------------
	private JsonObject getUidByHandlerID(String handlerID) {
		JsonObject result = new JsonObject();
		result.put("userId", sessionReverse.get(handlerID));

		return result;
	}

	private Object status(String userId) {
		JsonObject status = new JsonObject();
		status.put("ip", IPUtil.getInnerIP());
		status.put("sessionMap", counter);
		status.put("sessionReverse", recounter);

		if (StringUtils.isNotEmpty(userId)) {
			status.put("status", sessionMap.get(userId));
		}

		return status;
	}
}
