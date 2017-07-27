package service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface GcmPushService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "x-push-gcm-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.gcm.service";

	String LOCAL_SERVICE_NAME = "local-x-push-gcm-service";

	static GcmPushService createProxy(Vertx vertx){

		return ProxyHelper.createProxy(GcmPushService.class, vertx, GcmPushService.class.getName());

	}

	static GcmPushService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(GcmPushService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}

	void sendMsg(JsonObject recieveMsg);

}
