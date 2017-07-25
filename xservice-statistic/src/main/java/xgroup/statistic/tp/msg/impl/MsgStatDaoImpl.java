package xgroup.statistic.tp.msg.impl;

import helper.XProxyHelper;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.ext.web.client.WebClient;
import org.apache.commons.lang.StringUtils;
import rxjava.BaseServiceVerticle;
import utils.BaseResponse;
import xgroup.statistic.constants.CacheConstants;
import xgroup.statistic.constants.PushActionEnum;
import xgroup.statistic.service.MsgStatService;
import xgroup.statistic.tp.msg.MsgStatDao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei
 * Date : 2017/7/25 15:22
 * Description :
 */
public class MsgStatDaoImpl extends BaseServiceVerticle implements MsgStatDao {
    private static final Logger logger = LoggerFactory.getLogger(MsgStatDaoImpl.class);
    private static final int PORT = 80;
    private CircuitBreaker circuitBreaker;
    private WebClient webClient;
    private HttpClient rxHttpClient;
    private RedisClient redisClient;


    public MsgStatDaoImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgStatDao.class, vertx.getDelegate(), this, MsgStatDao.SERVICE_ADDRESS);
        publishEventBusService(MsgStatDao.SERVICE_NAME, MsgStatDao.SERVICE_ADDRESS, MsgStatDao.class);

        XProxyHelper.registerService(MsgStatDao.class, vertx.getDelegate(), this, MsgStatDao.getLocalAddress());
        publishEventBusService(MsgStatDao.LOCAL_SERVICE_NAME, MsgStatDao.getLocalAddress(), MsgStatDao.class);

        circuitBreaker = this.createCircuitBreaker(vertx, config());
        rxHttpClient = vertx.createHttpClient();

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
        logger.info("rediPath: " + configPath);
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

    private CircuitBreaker createCircuitBreaker(Vertx vertx, JsonObject config) {
        JsonObject circuitObject = config.getJsonObject("circuit-breaker");
        JsonObject cbOptions = circuitObject != null ? circuitObject : new JsonObject();
        CircuitBreakerOptions options = new CircuitBreakerOptions();
        options.setMaxFailures(cbOptions.getInteger("max-failures", 5));
        options.setTimeout(cbOptions.getLong("timeout", 10000L));
        options.setFallbackOnFailure(true);
        options.setResetTimeout(cbOptions.getLong("reset-timeout", 30000L));
        String name = cbOptions.getString("name", "circuit-breaker");
        return CircuitBreaker.create(name, vertx.getDelegate(), options);
    }

    private WebClient createWebClient(Vertx vertx) {
        WebClientOptions webClientOptions = new WebClientOptions();
        webClientOptions.setConnectTimeout(1000);
        webClientOptions.setKeepAlive(true);
        webClientOptions.setLogActivity(false);
        webClientOptions.setMaxPoolSize(128);
        webClientOptions.setTryUseCompression(true);
        webClientOptions.setSendBufferSize(1048576);
        webClientOptions.setReceiveBufferSize(1048576);
        webClientOptions.setReuseAddress(true);
        webClientOptions.setPipelining(true);
        return WebClient.create(vertx, webClientOptions);
    }


    @Override
    public void statPushMsg(int action, String msgId, int osType, Handler<AsyncResult<String>> result) {
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
