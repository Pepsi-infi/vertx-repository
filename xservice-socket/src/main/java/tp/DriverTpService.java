package tp;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface DriverTpService {

	static final String SERVICE_NAME = DriverTpService.class.getName();

	static final String SERVICE_ADDRESS = DriverTpService.class.getName();

	static DriverTpService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(DriverTpService.class, vertx, SERVICE_ADDRESS,
				new DeliveryOptions().setSendTimeout(3000));
	}

	void auth(JsonObject param, Handler<AsyncResult<String>> result);

	void updateOnlineState(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result);

	void updateOnlineSimple(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result);

	void setClientOnline(JsonObject param, Handler<AsyncResult<String>> result);

	void setClientOffline(JsonObject param, Handler<AsyncResult<String>> result);

	void subscribe(JsonObject msg, Handler<AsyncResult<String>> result);

	void unsubscribe(JsonObject msg, Handler<AsyncResult<String>> result);
}
