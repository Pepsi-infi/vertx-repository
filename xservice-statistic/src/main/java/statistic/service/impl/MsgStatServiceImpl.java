package statistic.service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.apache.commons.lang.StringUtils;
import rxjava.BaseServiceVerticle;
import statistic.constants.CacheConstants;
import statistic.constants.PushActionEnum;
import utils.BaseResponse;
import statistic.service.MsgStatService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei
 * Date : 2017/7/25 14:54
 * Description :
 */
public class MsgStatServiceImpl extends BaseServiceVerticle implements MsgStatService {
    private static final Logger logger = LoggerFactory.getLogger(MsgStatServiceImpl.class);

    private RedisClient redisClient;


    public MsgStatServiceImpl() {

    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgStatService.class, vertx.getDelegate(), this, MsgStatService.SERVICE_ADDRESS);
        publishEventBusService(MsgStatService.SERVICE_NAME, MsgStatService.SERVICE_ADDRESS, MsgStatService.class);

        XProxyHelper.registerService(MsgStatService.class, vertx.getDelegate(), this, MsgStatService.getLocalAddress());
        publishEventBusService(MsgStatService.LOCAL_SERVICE_NAME, MsgStatService.getLocalAddress(), MsgStatService.class);

        String root = System.getProperty("config", "mc");
        String redis = System.getProperty("redis", "dev");
        JsonObject jsonObject = this.getJsonConf(root + "/" + redis + "/redis.json");
        RedisOptions redisOptions = new RedisOptions();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            redisOptions.setAuth(jsonObject.getString("password"));
            redisOptions.setAddress(jsonObject.getString("host"));
            redisOptions.setPort(jsonObject.getInteger("port"));
            redisOptions.setTcpKeepAlive(Boolean.getBoolean(jsonObject.getString("tcpKeepAlive")));
            redisOptions.setEncoding(jsonObject.getString("encoding"));
            redisOptions.setTcpNoDelay(Boolean.getBoolean(jsonObject.getString("tcpNoDelay")));
        }
        redisClient = RedisClient.create(vertx.getDelegate(), redisOptions);
    }

    private JsonObject getJsonConf(String configPath) {
        logger.info("redidPath: " + configPath);
        JsonObject conf = new JsonObject();
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = ctxClsLoader.getResourceAsStream(configPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conf = new JsonObject(sb.toString());
            logger.info("Loaded redis.json file from [" + configPath + "/redis.json] and config.json="
                    + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load configuration file" + e);
        }
        return conf;
    }



    @Override
    public void statPushMsg(int action, String msgId, int osType, Handler<AsyncResult<BaseResponse>> result) {
        String msgSendKey = CacheConstants.getPushMsgKey(msgId);
        String field = getFiledForRedisStat(action, osType);
        if (StringUtils.isBlank(field)) {
            logger.warn("the msgId : {} statistical indicators is null,action :{},osType : {} ", msgId, action, osType);
            return;
        }
        redisClient.hincrby(msgSendKey, CacheConstants.PUSH_MSG_SEND, 1, handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(null));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    private String getFiledForRedisStat(int action, int osType) {
        String redisFiled = null;
        if (PushActionEnum.SEND.getType() == action) {
            redisFiled = CacheConstants.PUSH_MSG_SEND;
        } else if (PushActionEnum.ARRIVE.getType() == action) {
            redisFiled = CacheConstants.PUSH_MSG_ARRIVE;
        }
        return redisFiled;
    }

}
