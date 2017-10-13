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

	public static final String SERVICE_NAME = MongoService.class.getName();

	public static final String SERVICE_ADDRESS = MongoService.class.getName();

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

	void saveDataBatch(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler);

	void findOffLineMessage(JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler);
}
