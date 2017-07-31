package service.impl;

import com.google.common.collect.Lists;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
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
import util.FileUtils;
import utils.BaseResponse;
import iservice.MsgStatService;

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

        String env = System.getProperty("env", "dev");
        JsonObject jsonObject = FileUtils.getJsonConf(env + "/redis-" + env + ".json");
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


    @Override
    public void statPushMsg(MsgStatDto msgStatDto, Handler<AsyncResult<BaseResponse>> result) {
        String msgSendKey = CacheConstants.getPushMsgKey(msgStatDto);
        List<String> fields = getFieldsForMsgStat(msgStatDto);
        if (CollectionUtils.isEmpty(fields)) {
            logger.warn("the msgStat:{} need stat is null ", msgStatDto);
            return;
        }
        try {
            //设置过期时间 12小时
            redisClient.expire(msgSendKey, 43200, handler -> {
                if (handler.succeeded()) {
                    logger.info("key : {} for  msgStatDto :{} set expire success : {} .", msgSendKey, msgStatDto, handler.result());
                } else {
                    logger.error("key : {} for  msgStatDto :{} set expire error : {} .", msgSendKey, msgStatDto, handler.cause());
                }
            });
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
            logger.error("stat msgStatDto : {} error.", msgStatDto, e);
            BaseResponse baseResponse = new BaseResponse();
            buildErrorBaseResponse(baseResponse, null, e.getMessage());
            result.handle(Future.succeededFuture(baseResponse));
        }

    }

    private List<String> getFieldsForMsgStat(MsgStatDto msgStatDto) {
        List<String> fieldsList = Lists.newArrayList();
        if (PushActionEnum.SEND.getType() == msgStatDto.getAction()) {
            fieldsList.add(CacheConstants.PUSH_SEND_SUM);
            if (msgStatDto.getOsType() != null && msgStatDto.getOsType() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_SEND_OSTYPE).append(msgStatDto.getOsType()).toString();
                fieldsList.add(filed);
            }
            if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_SEND_CHANNEL).append(msgStatDto.getChannel()).toString();
                fieldsList.add(filed);
            }
        } else if (PushActionEnum.ARRIVE.getType() == msgStatDto.getAction()) {
            fieldsList.add(CacheConstants.PUSH_ARRIVE_SUM);
            if (msgStatDto.getOsType() != null && msgStatDto.getOsType() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_ARRIVE_OSTYPE).append(msgStatDto.getOsType()).toString();
                fieldsList.add(filed);
            }
            if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_ARRIVE_CHANNEL).append(msgStatDto.getChannel()).toString();
                fieldsList.add(filed);
            }
        }
        logger.info("the msgStatDto : {} need stat fields : {}", msgStatDto, Json.encode(fieldsList));
        return fieldsList;
    }

    private <T extends BaseResponse> void buildErrorBaseResponse(T response, String errCode, String message) {
        if (response != null) {
            response.setStatus(BaseResponse.RESPONSE_FAIL_CODE);
            response.setErrorCode(errCode);
            response.setErrorMessage(message);
        }
    }

}
