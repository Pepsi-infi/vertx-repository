package tp;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface TpService {

	static final String SERVICE_NAME = TpService.class.getName();

	static final String SERVICE_ADDRESS = TpService.class.getName();

	static TpService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(TpService.class, vertx, SERVICE_ADDRESS);
	}

	void auth(JsonObject param, Handler<AsyncResult<String>> result);

	void updateOnlineState(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result);

	void updateOnlineSimple(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result);

	void setClientOnline(JsonObject param, Handler<AsyncResult<String>> result);

	void setClientOffline(JsonObject param, Handler<AsyncResult<String>> result);

	void subscribe(JsonObject msg, Handler<AsyncResult<String>> result);

	void unsubscribe(JsonObject msg, Handler<AsyncResult<String>> result);
}
