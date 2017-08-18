package util;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisOptions;
import org.apache.commons.collections.CollectionUtils;
import redis.HostAndPort;
import redis.RedisClusterOptions;

/**
 * Created by lufei
 * Date : 2017/8/17 18:02
 * Description :
 */
public class ConfigUtil {

    public static RedisClusterOptions getRedisClusterOptions(JsonObject jsonObject) {
        RedisOptions redisOptions = new RedisOptions();
        String password = jsonObject.getString("redis.cluster.password");
        redisOptions.setAuth(password);
        RedisClusterOptions redisClusterOptions = new RedisClusterOptions();
        redisClusterOptions.setRedisOptions(redisOptions);
        JsonArray array = jsonObject.getJsonArray("redis.node");
        if (array != null && !CollectionUtils.isEmpty(array.getList())) {
            for (int i = 0; i < array.size(); i++) {
                String slaveHost = array.getJsonObject(i).getString("host");
                Integer slavePort = array.getJsonObject(i).getInteger("port");
                redisClusterOptions.addNode(new HostAndPort(slaveHost, slavePort));
            }
        }
        return redisClusterOptions;
    }
}
