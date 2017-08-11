package dao;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.sql.ResultSet;

/**
 * Created by lufei Date : 2017/8/9 17:57 Description :
 */
@VertxGen
@ProxyGen
public interface CarBizEuroService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "CarBizEuroService-eb-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "CarBizEuroService.eb.service";

	static CarBizEuroService createProxy(Vertx vertx) {
		return XProxyHelper.createProxy(CarBizEuroService.class, CarBizEuroService.class, vertx,
				CarBizEuroService.SERVICE_ADDRESS);
	}

	void countCarBizEuro(Handler<AsyncResult<ResultSet>> resultHandler);

	void queryCarBizEurop(int from, int to, Handler<AsyncResult<ResultSet>> resultHandler);
}