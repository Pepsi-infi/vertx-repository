package logic;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface C2CService {

	public static final String SERVICE_NAME = "logic.C2CService";

	public static final String SERVICE_ADDRESS = "logic.C2CService";

	static C2CService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(C2CService.class, vertx, SERVICE_ADDRESS);
	}

	void doWithFileUpload(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler);
}
