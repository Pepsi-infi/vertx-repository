package service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface SocketPushService {
	
	static SocketPushService createProxy(Vertx vertx){
		
		return ProxyHelper.createProxy(SocketPushService.class, vertx, SocketPushService.class.getName());
	
	}

	void sendMsg(JsonObject recieveMsg);

}
