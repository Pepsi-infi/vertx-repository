package logic;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface IMSessionService {

	static final String SERVICE_NAME = IMSessionService.class.getName();

	static final String SERVICE_ADDRESS = IMSessionService.class.getName();

	static IMSessionService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(IMSessionService.class, vertx, SERVICE_ADDRESS);
	}

	void getHandlerIDByUid(String uid, Handler<AsyncResult<String>> resultHandler);

	void getUidByHandlerId(String handlerId, Handler<AsyncResult<String>> resultHandler);
}
