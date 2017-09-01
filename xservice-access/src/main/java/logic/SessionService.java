package logic;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface SessionService {

	static final String SERVICE_NAME = "logic.SessionService";

	static final String SERVICE_ADDRESS = "logic.SessionService";

	static SessionService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(SessionService.class, vertx, SERVICE_ADDRESS);
	}

	void getHandlerIDByUid(String uid, Handler<AsyncResult<String>> resultHandler);

	void getUidByHandlerId(String handlerId, Handler<AsyncResult<String>> resultHandler);
}
