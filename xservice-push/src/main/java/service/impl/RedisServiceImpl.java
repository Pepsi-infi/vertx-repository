package service.impl;

import constant.ConnectionConsts;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import rxjava.BaseServiceVerticle;
import service.RedisService;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;

/**
 * Created by weim on 2017/7/27.
 */
public class RedisServiceImpl extends BaseServiceVerticle implements RedisService{

    RedisClient redisClient;

    public void start() throws Exception {
        super.start();
        //连接redis
        initRedisClient();

    }

    private void initRedisClient(){
        String config = System.getProperty("redis.config", ConnectionConsts.REDIS_CONFIG_PATH);
        JsonObject jsonObject = PropertiesLoaderUtils.getJsonConf(config);

        RedisOptions redisOptions = new RedisOptions();
        if (jsonObject != null && !jsonObject.isEmpty()) {
//            redisOptions.setAuth(jsonObject.getString("password"));
            redisOptions.setAddress(jsonObject.getString("host"));
            redisOptions.setPort(jsonObject.getInteger("port"));
            redisOptions.setTcpKeepAlive(Boolean.getBoolean(jsonObject.getString("tcpKeepAlive")));
            redisOptions.setEncoding(jsonObject.getString("encoding"));
            redisOptions.setTcpNoDelay(Boolean.getBoolean(jsonObject.getString("tcpNoDelay")));
        }
        redisClient = RedisClient.create(vertx.getDelegate(), redisOptions);
    }

    @Override
    public void set(String key, String value, Handler<AsyncResult<BaseResponse>> result) {
        redisClient.set(key, value, handler ->{
           if(handler.succeeded()){
               result.handle(Future.succeededFuture(null));
           } else {
               result.handle(Future.failedFuture(handler.cause()));
           }
        });
    }

    @Override
    public void expire(String key, long expire, Handler<AsyncResult<BaseResponse>> result) {
        redisClient.expire(key, expire, handler ->{
            if(handler.succeeded()){
                result.handle(Future.succeededFuture(null));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    @Override
    public void get(String key, Handler<AsyncResult<String>> result) {
        redisClient.get(key, handler ->{
            if(handler.succeeded()){
                result.handle(handler);
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

}
