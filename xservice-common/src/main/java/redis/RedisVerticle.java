package redis;

import org.apache.commons.collections.CollectionUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisOptions;

public class RedisVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		super.start();
		RedisOptions rOptions = new RedisOptions();
		// String host = config().getString("redis.cluster.host");
		String password = config().getString("redis.cluster.password");
		// rOptions.setHost(host);
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

		RedisCluster redisCluster = RedisCluster.create(vertx, redisOptions);
	}
}
