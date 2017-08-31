package service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface MessagePushService {

	static MessagePushService createProxy(Vertx vertx){
		return ProxyHelper.createProxy(MessagePushService.class, vertx, MessagePushService.class.getName());
	}

	void bisnessMessage(String recieveMsg, Handler<AsyncResult<String>> resultHandler);

}
