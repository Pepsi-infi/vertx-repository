package logic.iml;

import org.apache.commons.lang.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
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

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("session",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
								ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(20, MemoryUnit.MB)))
				.withCache("sessionReverse", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,
						String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(20, MemoryUnit.MB)))
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
				String from = body.getString("from");
				String handlerID = body.getString("handlerID");
				String to = body.getString("to");

				logger.info("from={}action={}innerIP={}", from, action, innerIP);

				switch (action) {
				case "setUserSocket":
					res.reply(setUserSocket(from, handlerID));
					break;
				case "delUserSocket":
					res.reply(delUserSocket(from, handlerID));
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

		eb.<JsonObject>consumer(SocketSessionVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String from = body.getString("from");
				String handlerID = body.getString("handlerID");
				String to = body.getString("to");
				logger.info("from={}action={}innerIP={}", from, action, innerIP);
				switch (action) {
				case "setUserSocket":
					res.reply(setUserSocket(from, handlerID));
					break;
				case "delUserSocket":
					res.reply(delUserSocket(from, handlerID));
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

	public int setUserSocket(String uid, String handlerId) {
		logger.info("setUserSocket, uid={}", uid);
		this.sessionMap.put(uid, handlerId);
		this.sessionReverse.put(handlerId, uid);

		return 0;
	}

	public int delUserSocket(String uid, String handlerId) {
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

		return 0;
	}

	public void getHandlerIDByUid(String uid, Handler<AsyncResult<String>> resultHandler) {
		resultHandler.handle(Future.succeededFuture(sessionMap.get(uid)));
	}

	public void getUidByHandlerId(String handlerId, Handler<AsyncResult<String>> resultHandler) {
		resultHandler.handle(Future.succeededFuture(sessionReverse.get(handlerId)));
	}

	private JsonObject getHandlerIDByUid(String uid) {
		JsonObject jo = new JsonObject();
		jo.put("handlerID", sessionMap.get(uid));

		return jo;
	}

	// ---------------------------
	private JsonObject getUidByHandlerID(String handlerID) {
		JsonObject result = new JsonObject();
		result.put("uid", sessionReverse.get(handlerID));
		return result;
	}
}
