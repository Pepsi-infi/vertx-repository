package service;

import io.vertx.codegen.annotations.Nullable;
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
public interface AdMessagePushService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "x-push-adMessage-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.adMessage.service";

	String LOCAL_SERVICE_NAME = "local-x-push-adMessage-service";

	static AdMessagePushService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(AdMessagePushService.class, vertx, AdMessagePushService.SERVICE_ADDRESS);
	}

	static AdMessagePushService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(AdMessagePushService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}

	void pushMsg(String httpMsg, Handler<AsyncResult<String>> resultHandler);

}
