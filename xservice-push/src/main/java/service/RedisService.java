package service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface RedisService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "push-redis-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.redis.service";

	String LOCAL_SERVICE_NAME = "local-push-redis-service";

	static RedisService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(RedisService.class, vertx, RedisService.SERVICE_ADDRESS);
	}

	static RedisService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(RedisService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}

	void set(String key, String value, Handler<AsyncResult<Void>> result);

	void setEx(String key, long expire, String value, Handler<AsyncResult<String>> result);

	void expire(String key, long expire, Handler<AsyncResult<Long>> result);

	void get(String key, Handler<AsyncResult<String>> result);

	void lpush(String key, String value, Handler<AsyncResult<Long>> result);

	void rpush(String key, String value, Handler<AsyncResult<Long>> result);

}
