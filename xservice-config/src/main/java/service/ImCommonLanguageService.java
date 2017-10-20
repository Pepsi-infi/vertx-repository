package service;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by lufei Date : 2017/8/30 9:58 Description :
 */
@ProxyGen
@VertxGen
public interface ImCommonLanguageService {

	public static final String SERVICE_NAME = "http.imCommonLanguage.eb.service";

	public static final String SERVICE_ADDRESS = "http-imCommonLanguage-eb-service";

	static ImCommonLanguageService createProxy(Vertx vertx) {
		return XProxyHelper.createProxy(ImCommonLanguageService.class, ImCommonLanguageService.class, vertx,
				ImCommonLanguageService.SERVICE_ADDRESS);
	}

	void addImCommonLanguage(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result);

	void getImCommonLanguage(int id, Handler<AsyncResult<JsonObject>> result);

	void updateImCommonLanguage(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result);

	void deleteImCommonLanguage(int id, Handler<AsyncResult<JsonObject>> result);

	void queryImCommonLanguage(int type, Integer userId, Handler<AsyncResult<List<JsonObject>>> result);

}
