package logic.impl;

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
import logic.SessionService;
import utils.IPUtil;

public class SessionVerticle extends AbstractVerticle implements SessionService {

	private static final Logger logger = LoggerFactory.getLogger(SessionVerticle.class);

	// private SharedData sharedData;
	// private LocalMap<String, String> sessionMap;// uid -> handlerID
	// private LocalMap<String, String> sessionReverse; // handlerID -> uid

	private Cache<String, String> sessionMap;// uid -> handlerID
	private Cache<String, String> sessionReverse;// handlerID -> uid

	@Override
	public void start() throws Exception {
		// sharedData = vertx.sharedData();
		// sessionMap = sharedData.getLocalMap("session");
		// sessionReverse = sharedData.getLocalMap("sessionReverse");

		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("session",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
								ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(1, MemoryUnit.GB)))
				.withCache("sessionReverse", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,
						String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(1, MemoryUnit.GB)))
				.build(true);
		sessionMap = cacheManager.getCache("session", String.class, String.class);
		sessionReverse = cacheManager.getCache("sessionReverse", String.class, String.class);

		String innerIP = IPUtil.getInnerIP();
		logger.info("innerIP={}", innerIP);
		EventBus eb = vertx.eventBus();
		eb.<JsonObject>consumer(SessionService.SERVICE_ADDRESS + innerIP, res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String from = body.getString("from");
				String handlerID = body.getString("handlerID");
				logger.info("from={}action={}innerIP={}", from, action, innerIP);
				switch (action) {
				case "setUserSocket":
					res.reply(setUserSocket(from, handlerID));
					break;
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
		this.sessionMap.put(uid, handlerId);
		this.sessionReverse.put(handlerId, uid);

		return 0;
	}

	public int delUserSocket(String uid, String handlerId) {
		if (StringUtils.isNotEmpty(uid)) {
			this.sessionMap.remove(uid);
			this.sessionReverse.remove(handlerId);
		} else {
			sessionReverse.remove(handlerId);
			uid = sessionReverse.get(handlerId);
			if (uid != null) {
				sessionMap.remove(uid);
			}
		}

		return 0;
	}

	@Override
	public void getHandlerIDByUid(String uid, Handler<AsyncResult<String>> resultHandler) {
		resultHandler.handle(Future.succeededFuture(sessionMap.get(uid)));
	}

	@Override
	public void getUidByHandlerId(String handlerId, Handler<AsyncResult<String>> resultHandler) {
		resultHandler.handle(Future.succeededFuture(sessionReverse.get(handlerId)));
	}
}
