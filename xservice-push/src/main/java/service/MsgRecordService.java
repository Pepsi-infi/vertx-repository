package service;

import domain.AmqpConsumeMessage;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import utils.BaseResponse;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface MsgRecordService {

	/**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "x-push-device-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.device.service";

	String LOCAL_SERVICE_NAME = "local-x-push-device-service";

	static MsgRecordService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(MsgRecordService.class, vertx, MsgRecordService.SERVICE_ADDRESS);
	}

	static MsgRecordService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(MsgRecordService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}

	void addMessage(AmqpConsumeMessage dto, Handler<AsyncResult<BaseResponse>> resultHandler);
}
