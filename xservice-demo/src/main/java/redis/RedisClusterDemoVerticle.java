package redis;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisOptions;

public class RedisClusterDemoVerticle extends AbstractVerticle {
	private final static Logger log = LoggerFactory.getLogger(RedisClusterDemoVerticle.class);
	private static RedisCluster redisCluster;

	@Override
	public void start() throws Exception {
		super.start();
		RedisOptions rOptions = new RedisOptions();
		String password = config().getString("redis.cluster.password");
		rOptions.setAuth(password);
		RedisClusterOptions redisOptions = new RedisClusterOptions();
		redisOptions.setRedisOptions(rOptions);
		JsonArray array = config().getJsonArray("redis.slaves");
		if (array != null && !CollectionUtils.isEmpty(array.getList())) {
			for (int i = 0; i < array.size(); i++) {
				String slaveHost = array.getJsonObject(i).getString("host");
				Integer slavePort = array.getJsonObject(i).getInteger("port");
				redisOptions.addNode(new HostAndPort(slaveHost, slavePort));
			}
		}

		redisCluster = RedisCluster.create(vertx, redisOptions);
	}

	public static void deleteByKeys(List<String> keys, Handler<AsyncResult<Void>> resultHandler) {
		redisCluster.delMany(keys, res -> {
			if (res.succeeded()) {
				log.info("redis delete success!" + keys);
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.succeededFuture());
				log.info("redis delete failed!");
			}
		});
	}

}
