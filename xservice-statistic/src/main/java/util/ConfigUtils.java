package util;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisOptions;
import org.apache.commons.lang.StringUtils;

/**
 * Created by lufei
 * Date : 2017/7/31 13:56
 * Description :
 */
public class ConfigUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    public static RedisOptions getRedisOptions(JsonObject jsonObject) {
        RedisOptions redisOptions = new RedisOptions();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            if (StringUtils.isNotBlank(jsonObject.getString("password"))) {
                redisOptions.setAuth(jsonObject.getString("password"));
            }
            redisOptions.setHost(jsonObject.getString("host"));
            redisOptions.setPort(jsonObject.getInteger("port"));
            redisOptions.setTcpKeepAlive(Boolean.getBoolean(jsonObject.getString("tcpKeepAlive")));
            redisOptions.setEncoding(jsonObject.getString("encoding"));
            redisOptions.setTcpNoDelay(Boolean.getBoolean(jsonObject.getString("tcpNoDelay")));
        }
        return redisOptions;
    }
}
