package channel;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import utils.BaseResponse;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface XiaoMiPushService {

    /**
	 * The name of the event bus service.
	 */
	String SERVICE_NAME = "x-push-xiaomi-service";

	/**
	 * The address on which the service is published.
	 */
	String SERVICE_ADDRESS = "push.xiaomi.service";

	String LOCAL_SERVICE_NAME = "local-x-push-xiaomi-service";

	static XiaoMiPushService createProxy(Vertx vertx){
		return ProxyHelper.createProxy(XiaoMiPushService.class, vertx, XiaoMiPushService.class.getName());
	}

	static XiaoMiPushService createLocalProxy(Vertx vertx) {
		return ProxyHelper.createProxy(XiaoMiPushService.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
				new DeliveryOptions().setSendTimeout(3000));
	}

	static String getLocalAddress(String ip) {
		return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
	}

	void sendMsg(JsonObject recieveMsg,Handler<AsyncResult<BaseResponse>> resultHandler);

}
