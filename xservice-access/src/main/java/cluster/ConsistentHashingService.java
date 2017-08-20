package cluster;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface ConsistentHashingService {

	static final String SERVICE_NAME = "consistentHashing.eb.service";

	static final String SERVICE_ADDRESS = "consistentHashing-eb-service";

	static ConsistentHashingService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(ConsistentHashingService.class, vertx, SERVICE_ADDRESS);
	}

	/**
	 * 根据uid获得用户所属机器内网IP
	 * 
	 * @param uid
	 * @param resultHandler
	 */
	void getNode(String uid, Handler<AsyncResult<String>> resultHandler);
	
	void getNodeWithNodeList();
}
