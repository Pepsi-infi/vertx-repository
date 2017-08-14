package service.impl;

import constants.CacheConstants;
import dao.MsgStatResultDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import rxjava.BaseServiceVerticle;
import service.MsgStatResultService;
import service.dto.MsgStatResultDto;
import service.dto.MsgStatResultPageWrapper;
import util.ConfigUtils;
import utils.BaseResponse;
import utils.CalendarUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei
 * Date : 2017/7/29 12:04
 * Description :
 */
public class MsgStatResultServiceImpl extends BaseServiceVerticle implements MsgStatResultService {

    private static final Logger logger = LoggerFactory.getLogger(MsgStatResultServiceImpl.class);

    private RedisClient redisClient;

    private MsgStatResultDao msgStatResultDao;

    public MsgStatResultServiceImpl() {

    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgStatResultService.class, vertx.getDelegate(), this, MsgStatResultService.SERVICE_ADDRESS);
        publishEventBusService(MsgStatResultService.SERVICE_NAME, MsgStatResultService.SERVICE_ADDRESS, MsgStatResultService.class);

        msgStatResultDao = MsgStatResultDao.createProxy(vertx.getDelegate());

        String env = System.getProperty("env", "dev");
        JsonObject jsonObject = ConfigUtils.getJsonConf(env + "/redis-" + env + ".json");
        RedisOptions redisOptions = ConfigUtils.getRedisOptions(jsonObject);
        redisClient = RedisClient.create(vertx.getDelegate(), redisOptions);

    }


    @Override
    public void storeMsgStatResult(Handler<AsyncResult<BaseResponse>> result) {
        redisClient.keys(CacheConstants.PUSH_MSG + "*", handler -> {
            if (handler.succeeded()) {
                JsonArray jsonArray = handler.result();
                List<String> keys = jsonArray.getList();
                logger.info("date : {} get keys ： {} from redis ", new Date(), keys);
                processRedisMsgStatResult(jsonArray, result);
            } else {
                logger.error("get keys from redis error", handler.cause());
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

    @Override
    public void queryMsgStatResult(Map<String, String> param, int page, int limit, Handler<AsyncResult<MsgStatResultPageWrapper>> result) {
        Future<MsgStatResultPageWrapper> future = Future.future();
        msgStatResultDao.queryMsgStatResultByPage(param, page, limit, future.completer());
        future.setHandler(ar1 -> {
            if (ar1.succeeded()) {
                result.handle(Future.succeededFuture(ar1.result()));
            } else {
                logger.error("[service] query msgStatResult error", ar1.cause());
                result.handle(Future.failedFuture(ar1.cause()));
            }
        });
    }

    private void processRedisMsgStatResult(JsonArray jsonArray, Handler<AsyncResult<BaseResponse>> result) {
        List<String> keys = jsonArray.getList();
        if (CollectionUtils.isEmpty(keys)) {
            logger.info("need stat msg is null");
            result.handle(Future.succeededFuture());
        }
        try {
            for (String key : keys) {
                Future<JsonArray> mgetFuture = Future.future();
                redisClient.hmget(key, CacheConstants.PUSH_MSG_FIELDS, mgetFuture.completer());
                mgetFuture.setHandler(handler -> {
                    if (handler.succeeded()) {
                        JsonArray fieldsValues = handler.result();
                        List<String> valueList = fieldsValues.getList();
                        saveOrUpdateMsgStatResultToDb(key, valueList, resultHandler -> {
                            if (resultHandler.succeeded()) {
                                logger.info("save or update msgStatResult for : {} to db success. result:{}", key, resultHandler.result());
                            } else {
                                logger.error("save or update msgStatResult for :{} to db error.", resultHandler.cause());
                            }
                        });
                    } else {
                        logger.error("get values for key:{} from redis error", key, handler.cause());
                    }
                });
            }
            result.handle(Future.succeededFuture());
        } catch (Exception e) {
            logger.error("process redis msgStat result error.", e);
            result.handle(Future.failedFuture(e.getCause()));
        }

    }

    private void saveOrUpdateMsgStatResultToDb(String key, List<String> values, Handler<AsyncResult<BaseResponse>> result) {
        MsgStatResultDto msgStatResultDto = buildMsgStatResult(key, values);
        logger.info("the msgStatResultDto:{}", msgStatResultDto);
        //获取db数据
        Future<MsgStatResultDto> getDbMsgStatFuture = Future.future();
        msgStatResultDao.getMsgStatResult(msgStatResultDto, getDbMsgStatFuture.completer());
        getDbMsgStatFuture.setHandler(result1 -> {
            if (result1.succeeded()) {
                MsgStatResultDto dbMsgStatResult = result1.result();
                if (dbMsgStatResult == null) {
                    msgStatResultDao.addMsgStatResult(msgStatResultDto, ar1 -> {
                        if (ar1.succeeded()) {
                            result.handle(Future.succeededFuture(new BaseResponse()));
                        } else {
                            logger.error("add msgStatResult:{} to db error.", msgStatResultDto, ar1.cause());
                            result.handle(Future.failedFuture(ar1.cause()));
                        }
                    });
                } else {
                    if (dbMsgStatResult != null && dbMsgStatResult.getSendSum() >= msgStatResultDto.getSendSum() &&
                            dbMsgStatResult.getArriveSum() >= msgStatResultDto.getArriveSum() &&
                            dbMsgStatResult.getClickSum() >= msgStatResultDto.getClickSum()) {
                        logger.info("the data of  msgStatResult :{} in db gt in redis.", msgStatResultDto);
                        result.handle(Future.succeededFuture());
                    } else {
                        Future<BaseResponse> future = Future.future();
                        msgStatResultDao.updateMsgStatResult(msgStatResultDto, future.completer());
                        future.setHandler(resultHandler -> {
                            if (resultHandler.succeeded()) {
                                result.handle(Future.succeededFuture(new BaseResponse()));
                            } else {
                                logger.error("update msgStatResult:{} to db error.", msgStatResultDto, resultHandler.cause());
                                result.handle(Future.failedFuture(resultHandler.cause()));
                            }
                        });
                    }
                }
            } else {
                logger.error("get msgStatResult:{} from db error.", msgStatResultDto, result1.cause());
                result.handle(Future.failedFuture(result1.cause()));
            }
        });
    }

    private MsgStatResultDto buildMsgStatResult(String key, List<String> values) {
        MsgStatResultDto msgStatResultDto = new MsgStatResultDto();
        msgStatResultDto.setMsgId(CacheConstants.getMsgId(key));
        msgStatResultDto.setStatTime(CalendarUtil.format(new Date()));
        msgStatResultDto.setSendSum(NumberUtils.toLong(values.get(0), 0));
        msgStatResultDto.setSendAndroidSum(NumberUtils.toLong(values.get(1), 0));
        msgStatResultDto.setSendIosSum(NumberUtils.toLong(values.get(2), 0));
        msgStatResultDto.setSendSockSum(NumberUtils.toLong(values.get(3), 0));
        msgStatResultDto.setSendGcmSum(NumberUtils.toLong(values.get(4), 0));
        msgStatResultDto.setSendMiSum(NumberUtils.toLong(values.get(5), 0));
        msgStatResultDto.setArriveSum(NumberUtils.toLong(values.get(6), 0));
        msgStatResultDto.setArriveAndroidSum(NumberUtils.toLong(values.get(7), 0));
        msgStatResultDto.setArriveIosSum(NumberUtils.toLong(values.get(8), 0));
        msgStatResultDto.setArriveSockSum(NumberUtils.toLong(values.get(9), 0));
        msgStatResultDto.setArriveGcmSum(NumberUtils.toLong(values.get(10), 0));
        msgStatResultDto.setArriveMiSum(NumberUtils.toLong(values.get(11), 0));
        msgStatResultDto.setClickSum(NumberUtils.toLong(values.get(12), 0));
        msgStatResultDto.setClickAndroidSum(NumberUtils.toLong(values.get(13), 0));
        msgStatResultDto.setClickIosSum(NumberUtils.toLong(values.get(14), 0));
        msgStatResultDto.setClickSockSum(NumberUtils.toLong(values.get(15), 0));
        msgStatResultDto.setClickGcmSum(NumberUtils.toLong(values.get(16), 0));
        msgStatResultDto.setClickMiSum(NumberUtils.toLong(values.get(17), 0));
        return msgStatResultDto;
    }

}
