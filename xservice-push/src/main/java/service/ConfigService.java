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
public interface ConfigService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "push-config-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.config.service";

	String LOCAL_SERVICE_NAME = "local-push-config-service";

	static ConfigService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(ConfigService.class, vertx, ConfigService.SERVICE_ADDRESS);
	}

	static ConfigService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(ConfigService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}
	
	/**
	 * 消息发送方身份注册
	 * @param senderId 
	 * @param senderKey
	 * @param resultHandler
	 */
	void getVerifyFromMsgCenter(String senderId, String senderKey,
			Handler<AsyncResult<String>> resultHandler);

	

}
