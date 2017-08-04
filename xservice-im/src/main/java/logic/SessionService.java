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

	static final String SERVICE_NAME = "session.eb.service";

	static final String SERVICE_ADDRESS = "session-eb-service";

	static SessionService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(SessionService.class, vertx, SERVICE_ADDRESS);
	}

	void setUserSocket(String uid, String handlerID, Handler<AsyncResult<Integer>> resultHandler);

	void delUserSocket(String uid, String handlerID, Handler<AsyncResult<Integer>> resultHandler);

	void getHandlerIDByUid(String uid, Handler<AsyncResult<String>> resultHandler);

	void getUidByHandlerId(String handlerId, Handler<AsyncResult<String>> resultHandler);
}
