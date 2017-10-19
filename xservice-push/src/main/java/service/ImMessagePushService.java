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
public interface ImMessagePushService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "x-push-ImMessage-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.ImMessage.service";

	String LOCAL_SERVICE_NAME = "local-x-push-ImMessage-service";

	static ImMessagePushService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(ImMessagePushService.class, vertx, ImMessagePushService.SERVICE_ADDRESS);
	}

	static ImMessagePushService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(ImMessagePushService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}
	
	/**
	 * 发送im消息
	 * @param senderId  发送方身份id(用于签名)
	 * @param senderKey 发送方senderKey(用于签名)
	 * @param httpMsg   消息体
	 * @param resultHandler 异步返回结果
	 */
	void pushMsg(String senderId, String senderKey, String httpMsg,
			Handler<AsyncResult<String>> resultHandler);
}
