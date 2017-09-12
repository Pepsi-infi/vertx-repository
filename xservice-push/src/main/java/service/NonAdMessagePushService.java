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
public interface NonAdMessagePushService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "x-push-NonAdMessage-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.NonAdMessage.service";

	String LOCAL_SERVICE_NAME = "local-x-push-NonAdMessage-service";

	static NonAdMessagePushService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(NonAdMessagePushService.class, vertx, NonAdMessagePushService.SERVICE_ADDRESS);
	}

	static NonAdMessagePushService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(NonAdMessagePushService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}

	void pushMsg(String senderId, String senderKey, String httpMsg,
			Handler<AsyncResult<String>> resultHandler);
}
