package service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import rxjava.BaseServiceVerticle;
import constants.CacheConstants;
import constants.PushActionEnum;
import iservice.dto.MsgStatDto;
import utils.BaseResponse;
import iservice.MsgStatService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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
            if (StringUtils.isNotBlank(jsonObject.getString("password"))) {
                redisOptions.setAuth(jsonObject.getString("password"));
            }
            redisOptions.setHost(jsonObject.getString("host"));
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
    public void statPushMsg(MsgStatDto msgStatDto, Handler<AsyncResult<BaseResponse>> result) {
        String msgSendKey = CacheConstants.getPushMsgKey(msgStatDto);
        List<String> fields = getFiledsForMsgStat(msgStatDto);
        if (CollectionUtils.isEmpty(fields)) {
            logger.warn("the msgStat:{} need stat is null ", msgStatDto);
            return;
        }
        try {
            for (String field : fields) {
                redisClient.hincrby(msgSendKey, field, 1, handler -> {
                    if (handler.succeeded()) {
                        logger.info("stat msgStatDto : {}  by filed :{} success.", msgStatDto, field);
                    } else {
                        logger.error("stat msgStatDto : {} by filed :{} error.", msgStatDto, field, handler.cause());
                    }
                });
            }
            result.handle(Future.succeededFuture(new BaseResponse()));
        } catch (Exception e) {
            logger.error("stat msgStatDto : {} error.", msgStatDto);
            result.handle(Future.failedFuture(e.getCause()));
        }

    }

    private List<String> getFiledsForMsgStat(MsgStatDto msgStatDto) {
        List<String> fieldsList = Lists.newArrayList();
        if (PushActionEnum.SEND.getType() == msgStatDto.getAction()) {
            fieldsList.add(CacheConstants.PUSH_SEND_SUM);
            if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_SEND_CHANNEL).append(msgStatDto.getChannel()).toString();
                fieldsList.add(filed);
            }
        } else if (PushActionEnum.ARRIVE.getType() == msgStatDto.getAction()) {
            fieldsList.add(CacheConstants.PUSH_ARRIVE_SUM);
            if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_ARRIVE_CHANNEL).append(msgStatDto.getChannel()).toString();
                fieldsList.add(filed);
            }
        }
        logger.info("the msgstatDto : {} need stat fileds : {}", JSON.toJSONString(fieldsList));
        return fieldsList;
    }


}
