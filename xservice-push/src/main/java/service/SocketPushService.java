package service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import utils.BaseResponse;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface SocketPushService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "x-push-socket-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.socket.service";

	String LOCAL_SERVICE_NAME = "local-x-push-socket-service";

	static SocketPushService createProxy(Vertx vertx){
		return ProxyHelper.createProxy(SocketPushService.class, vertx, SocketPushService.class.getName());
	}

	static SocketPushService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(SocketPushService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}


	void sendMsg(String recieveMsg,Handler<AsyncResult<BaseResponse>> resultHandler);

}
