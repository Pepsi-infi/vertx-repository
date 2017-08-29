package service;

import domain.DriverMsg;
import domain.Page;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface DriverMsgService {
	
	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "push-drivermsg-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.drivermsg.service";

	String LOCAL_SERVICE_NAME = "local-drivermsg-service";

	static DriverMsgService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(DriverMsgService.class, vertx, DriverMsgService.SERVICE_ADDRESS);
	}

	static DriverMsgService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(DriverMsgService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}

	
	void addDriverMsg(JsonObject msg, Handler<AsyncResult<Integer>> resultHandler);
		
	void selectByPage(JsonObject dto, Handler<AsyncResult<Page>> resultHandler);

	void getDriverMsgDetail(Long id, Handler<AsyncResult<DriverMsg>> completer);
}
