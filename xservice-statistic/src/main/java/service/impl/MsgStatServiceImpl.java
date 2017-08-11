package service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import constants.CacheConstants;
import constants.PushActionEnum;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import iservice.MsgStatService;
import iservice.dto.MsgStatDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import rxjava.BaseServiceVerticle;
import util.ConfigUtils;
import utils.BaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        JsonObject jsonObject = ConfigUtils.getJsonConf(env + "/redis-" + env + ".json");
        RedisOptions redisOptions = ConfigUtils.getRedisOptions(jsonObject);
        redisClient = RedisClient.create(vertx.getDelegate(), redisOptions);

    }


    private void statSinglePushMsg(MsgStatDto msgStatDto, Handler<AsyncResult<Map>> result) {
        String msgSendKey = CacheConstants.getPushMsgKey(msgStatDto);
        List<String> fields = getFieldsForMsgStat(msgStatDto);
        if (CollectionUtils.isEmpty(fields)) {
            logger.warn("the msgStat:{} need stat is null ", msgStatDto);
            result.handle(Future.failedFuture("the msgStat :" + msgStatDto + "need stat is null"));
        } else if (StringUtils.isEmpty(msgStatDto.getAntFingerprint())) {
            logger.warn("the antFingerprint of msgStat:{} is null ", msgStatDto);
            result.handle(Future.failedFuture("the antFingerprint of msgStat is null"));
        } else {
            try {
                List<Future> futures = Lists.newArrayList();
                //设置过期时间 12小时
                Future<Long> expireFuture = Future.future();
                futures.add(expireFuture);
                redisClient.expire(msgSendKey, 43200, expireFuture.completer());
                expireFuture.setHandler(handler -> {
                    if (handler.succeeded()) {
                        logger.info("key : {} for  msgStatDto :{} set expire success : {} .", msgSendKey, msgStatDto, handler.result());
                    } else {
                        logger.error("key : {} for  msgStatDto :{} set expire error : {} .", msgSendKey, msgStatDto, handler.cause());
                    }
                });
                for (String field : fields) {
                    Future<Long> fieldSetFuture = Future.future();
                    futures.add(fieldSetFuture);
                    redisClient.hincrby(msgSendKey, field, 1, fieldSetFuture.completer());
                    fieldSetFuture.setHandler(handler -> {
                        if (handler.succeeded()) {
                            logger.info("stat msgStatDto : {}  by filed :{} success.", msgStatDto, field);
                        } else {
                            logger.error("stat msgStatDto : {} by filed :{} error.", msgStatDto, field, handler.cause());
                        }
                    });
                }
                CompositeFuture compositeFuture = CompositeFuture.all(futures);
                compositeFuture.setHandler(res -> {
                    if (res.succeeded()) {
                        result.handle(Future.succeededFuture());
                    } else {
                        logger.error(res.cause());
                        result.handle(Future.failedFuture(res.cause()));
                    }
                });
            } catch (Exception e) {
                logger.error("stat msgStatDto : {} error.", msgStatDto, e);
                result.handle(Future.failedFuture(e.getCause()));
            }
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
        } else if (PushActionEnum.CLICK.getType() == msgStatDto.getAction()) {
            fieldsList.add(CacheConstants.PUSH_CLICK_SUM);
            if (msgStatDto.getOsType() != null && msgStatDto.getOsType() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_CLICK_OSTYPE).append(msgStatDto.getOsType()).toString();
                fieldsList.add(filed);
            }
            if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
                String filed = new StringBuilder(CacheConstants.PUSH_CLICK_CHANNEL).append(msgStatDto.getChannel()).toString();
                fieldsList.add(filed);
            }
        }
        logger.info("the msgStatDto : {} need stat fields : {}", msgStatDto, Json.encode(fieldsList));
        return fieldsList;
    }


    @Override
    public void statPushMsg(List<MsgStatDto> msgStatDtos, Handler<AsyncResult<String>> result) {
        List<Future> pushFutureList = new ArrayList<>();
        Map<Future<Map>, String> futureMap = Maps.newHashMap();
        for (MsgStatDto msgStatDto : msgStatDtos) {
            Future<Map> msgStatDtoFuture = Future.future();
            pushFutureList.add(msgStatDtoFuture);
            futureMap.put(msgStatDtoFuture, msgStatDto.getMsgId());
            statSinglePushMsg(msgStatDto, msgStatDtoFuture.completer());
        }
        CompositeFuture compositeFuture = CompositeFuture.all(pushFutureList);
        compositeFuture.setHandler(res -> {
            if (res.succeeded()) {
                result.handle(Future.succeededFuture(Json.encode(buildSuccessResponse())));
            } else {
                Map responseMap = Maps.newHashMap();
                responseMap.put("status", BaseResponse.RESPONSE_FAIL_CODE);
                List<Map> list = Lists.newArrayList();
                for (int i = 0; i < pushFutureList.size(); i++) {
                    Map msgMap = Maps.newHashMap();
                    msgMap.put("msgId", futureMap.get(pushFutureList.get(i)));
                    list.add(msgMap);
                }
                responseMap.put("msgList", list);
                result.handle(Future.succeededFuture(Json.encode(responseMap)));
            }
        });
    }

    private Map buildErrorResponse(String msgId) {
        Map map = Maps.newHashMap();
        map.put("status", BaseResponse.RESPONSE_FAIL_CODE);
        map.put("msgId", msgId);
        return map;
    }

    private Map buildSuccessResponse() {
        Map map = Maps.newHashMap();
        map.put("status", BaseResponse.RESPONSE_SUC_CODE);
        return map;
    }

    private <T extends BaseResponse> void buildErrorBaseResponse(T response, String message) {
        if (response != null) {
            response.setStatus(BaseResponse.RESPONSE_FAIL_CODE);
            response.setErrorMessage(message);
        }
    }
}
