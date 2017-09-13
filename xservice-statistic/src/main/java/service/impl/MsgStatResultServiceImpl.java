package service.impl;

import constants.CacheConstants;
import dao.MsgStatResultDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import redis.RedisCluster;
import redis.RedisClusterOptions;
import rxjava.BaseServiceVerticle;
import service.MsgStatResultService;
import service.dto.MsgStatResultDto;
import util.ConfigUtil;
import utils.BaseResponse;
import utils.CalendarUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by lufei
 * Date : 2017/7/29 12:04
 * Description :
 */
public class MsgStatResultServiceImpl extends BaseServiceVerticle implements MsgStatResultService {

    private static final Logger logger = LoggerFactory.getLogger(MsgStatResultServiceImpl.class);

    private RedisCluster redisClient;

    private MsgStatResultDao msgStatResultDao;

    public MsgStatResultServiceImpl() {

    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgStatResultService.class, vertx.getDelegate(), this, MsgStatResultService.SERVICE_ADDRESS);
        publishEventBusService(MsgStatResultService.SERVICE_NAME, MsgStatResultService.SERVICE_ADDRESS, MsgStatResultService.class);

        msgStatResultDao = MsgStatResultDao.createProxy(vertx.getDelegate());

        RedisClusterOptions redisClusterOptions = ConfigUtil.getRedisClusterOptions(config().getJsonObject("redis"));
        redisClient = RedisCluster.create(vertx.getDelegate(), redisClusterOptions);

        //启动时自动清除之前的错误数据
        vertx.setTimer(10000, id -> {
            repireData(Future.future());
        });
    }


    @Override
    public void storeMsgStatResult(Handler<AsyncResult<BaseResponse>> result) {
        redisClient.smembers(CacheConstants.getAllPushMsgKey(), ar -> {
            if (ar.succeeded()) {
                JsonArray jsonArray = ar.result();
                List<String> keys = jsonArray.getList();
                logger.info("date : {} get keys ： {} from redis ", new Date(), keys);
                processRedisMsgStatResult(jsonArray, result);
            } else {
                logger.error("get keys from redis error", ar.cause());
                result.handle(Future.failedFuture(ar.cause()));
            }
        });
    }


    private void processRedisMsgStatResult(JsonArray jsonArray, Handler<AsyncResult<BaseResponse>> result) {
        List<String> keys = jsonArray.getList();
        if (CollectionUtils.isEmpty(keys)) {
            logger.info("need stat msg is null");
            result.handle(Future.succeededFuture());
        } else {
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
        }

    }

    private void saveOrUpdateMsgStatResultToDb(String key, List<String> values, Handler<AsyncResult<BaseResponse>> result) {
        MsgStatResultDto msgStatResultDto = buildMsgStatResult(key, values);
        logger.info("the msgStatResultDto:{}", msgStatResultDto);

        Future<MsgStatResultDto> getDbMsgStatFuture = Future.future();
        Future<BaseResponse> addFuture = Future.future();
        Future<BaseResponse> updateFuture = Future.future();

        msgStatResultDao.getMsgStatResult(msgStatResultDto, getDbMsgStatFuture.completer());
        getDbMsgStatFuture.setHandler(result1 -> {
            if (result1.succeeded()) {
                MsgStatResultDto dbMsgStatResult = result1.result();
                if (dbMsgStatResult == null) {
                    msgStatResultDao.addMsgStatResult(msgStatResultDto, addFuture.completer());
                } else {
                    if (dbMsgStatResult != null && dbMsgStatResult.getSendSum() >= msgStatResultDto.getSendSum() &&
                            dbMsgStatResult.getArriveSum() >= msgStatResultDto.getArriveSum() &&
                            dbMsgStatResult.getClickSum() >= msgStatResultDto.getClickSum()) {
                        logger.info("the data of  msgStatResult :{} in db gt in redis.", msgStatResultDto);
                        result.handle(Future.succeededFuture());
                    } else {
                        msgStatResultDao.updateMsgStatResult(msgStatResultDto, updateFuture.completer());
                    }
                }
            } else {
                logger.error("get msgStatResult:{} from db error.", msgStatResultDto, result1.cause());
                result.handle(Future.failedFuture(result1.cause()));
            }
        });

        addFuture.setHandler(ar1 -> {
            if (ar1.succeeded()) {
                result.handle(Future.succeededFuture(new BaseResponse()));
            } else {
                logger.error("add msgStatResult:{} to db error.", msgStatResultDto, ar1.cause());
                result.handle(Future.failedFuture(ar1.cause()));
            }
        });
        updateFuture.setHandler(resultHandler -> {
            if (resultHandler.succeeded()) {
                result.handle(Future.succeededFuture(new BaseResponse()));
            } else {
                logger.error("update msgStatResult:{} to db error.", msgStatResultDto, resultHandler.cause());
                result.handle(Future.failedFuture(resultHandler.cause()));
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

    public void repireData(Handler<AsyncResult<BaseResponse>> result){
        redisClient.del(CacheConstants.getAllPushMsgKey(), ar ->{
            if(ar.succeeded()){
                logger.info("========= 已清空 MC_STAT_PUSH_MSG_ALL ========");
                Future<BaseResponse> future = Future.future();
                msgStatResultDao.delErrorData(future.completer());
                future.setHandler(res ->{
                   if(res.succeeded()){
                       logger.info("清除错误消息数据成功");
                   } else {
                       logger.info("清除错误消息数据失败");
                   }
                });
            }else {
                logger.info("========= 清空 MC_STAT_PUSH_MSG_ALL 失败 ========");
            }
        });
    }
}
