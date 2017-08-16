package service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import service.RedisService;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

/**
 * Created by weim on 2017/7/27.
 */
public class RedisServiceImpl extends BaseServiceVerticle implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(MsgRecordServiceImpl.class);

    RedisClient redisClient;

    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(RedisService.class, vertx, this, RedisService.SERVICE_ADDRESS);
        publishEventBusService(RedisService.SERVICE_NAME, RedisService.SERVICE_ADDRESS, RedisService.class);

        String ip = IPUtil.getInnerIP();
        XProxyHelper.registerService(RedisService.class, vertx, this, RedisService.getLocalAddress(ip));
        publishEventBusService(RedisService.LOCAL_SERVICE_NAME, RedisService.getLocalAddress(ip), RedisService.class);

        //连接redis
        this.initRedisClient();

    }

    private void initRedisClient() {
        //读取配置文件
        JsonObject redisConf = config().getJsonObject("redis.config");
        RedisOptions redisOptions = redisConf.mapTo(RedisOptions.class);

        redisClient = RedisClient.create(vertx, redisOptions);

    }

    @Override
    public void set(String key, String value, Handler<AsyncResult<Void>> result) {
        redisClient.set(key, value, handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(handler.result()));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    @Override
    public void expire(String key, long expire, Handler<AsyncResult<Long>> result) {
        redisClient.expire(key, expire, handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(handler.result()));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    @Override
    public void get(String key, Handler<AsyncResult<String>> result) {
        redisClient.get(key, handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(handler.result()));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    @Override
    public void lpush(String queue, String key, Handler<AsyncResult<Long>> result) {
        redisClient.lpush(queue, key, handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(handler.result()));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    @Override
    public void rpush(String queue, String key, Handler<AsyncResult<Long>> result) {
        redisClient.lpush(queue, key, handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(handler.result()));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    public static void main(String[] args) {
//        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(RedisServiceImpl.class.getName());
//
//        RedisService service = RedisService.createProxy(vertx);

//        service.set("aa", "111111", res -> {
//            if(res.succeeded()){
//                logger.info("----------11--------- :" + res.result());
//            }else{
//                logger.info("----------11--------- :" + res.cause());
//            }
//        });
//        service.get("aa", res -> {
//            if(res.succeeded()){
//                logger.info("----------22--------- :" + res.result());
//            }else{
//                logger.info("----------22--------- :" + res.cause());
//            }
//        });

//        JsonObject redisConf = new JsonObject();
//        redisConf.put("host","aa");
//        redisConf.put("port",22);
//        redisConf.put("encoding","utf-8");
//        redisConf.put("tcpKeepAlive","true");
//        redisConf.put("tcpNoDelay","true");
//        RedisOptions redisOptions = redisConf.mapTo(RedisOptions.class);
//        System.out.println(redisOptions.toString());

    }

}
