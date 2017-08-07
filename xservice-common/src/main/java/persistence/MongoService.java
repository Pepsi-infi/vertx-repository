package persistence;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface MongoService {

	public static final String SERVICE_NAME = "mongo.eb.service";

	public static final String SERVICE_ADDRESS = "mongo-eb-service";

	static MongoService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(MongoService.class, vertx, SERVICE_ADDRESS);
	}

	/**
	 * json format: {collection: "", data: {}}
	 * 
	 * @param json
	 * @param resultHandler
	 */
	void saveData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler);

	/**
	 * TODO json format: {collection: "", data: {}}
	 * 
	 * @param json
	 * @param resultHandler
	 */
	void updateData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler);
}
