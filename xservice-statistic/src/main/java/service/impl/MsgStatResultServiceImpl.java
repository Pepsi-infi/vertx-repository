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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import rxjava.BaseServiceVerticle;
import service.MsgStatResultService;
import service.dto.MsgStatResultDto;
import util.FileUtils;
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

    private static final Logger logger = LoggerFactory.getLogger(MsgStatServiceImpl.class);

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

        vertx.setPeriodic(10000, handler -> {
            logger.info("msgStatResult timer run .....");
            storeMsgStatResult(asyncResult -> {
                if (asyncResult.succeeded()) {
                    logger.info("msgStatResult timer success. ");
                } else {
                    logger.error("msgStatResult timer error", asyncResult.cause());
                }
            });
        });
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

    private void processRedisMsgStatResult(JsonArray jsonArray, Handler<AsyncResult<BaseResponse>> result) {
        List<String> keys = jsonArray.getList();
        if (CollectionUtils.isEmpty(keys)) {
            logger.info("need stat msg is null");
            return;
        }
        for (String key : keys) {
            redisClient.hmget(key, CacheConstants.PUSH_MSG_FIELDS, handler -> {
                if (handler.succeeded()) {
                    JsonArray fieldsValues = handler.result();
                    List<String> valueList = fieldsValues.getList();
                    addMsgStatResultToDb(key, valueList, resultHandler -> {
                        if (resultHandler.succeeded()) {
                            logger.info("add msgStatResult for : {} to db success. result:{}", key, resultHandler.result());
                        } else {
                            logger.error(resultHandler.cause());
                        }
                    });
                } else {
                    logger.error("get values for key:{} from redis error", key, handler.cause());
                }
            });
        }
    }

    private void addMsgStatResultToDb(String key, List<String> values, Handler<AsyncResult<BaseResponse>> result) {
        MsgStatResultDto msgStatResultDto = buildMsgStatResult(key, values);
        logger.info("the msgStatResultDto:{}", msgStatResultDto);
        Future<BaseResponse> future = Future.future();
        msgStatResultDao.addMsgStatResult(msgStatResultDto, future.completer());
        future.setHandler(resultHandler -> {
            BaseResponse baseResponse = new BaseResponse();
            if (resultHandler.succeeded()) {
                result.handle(Future.succeededFuture(baseResponse));
            } else {
                logger.error(resultHandler.cause());
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
        return msgStatResultDto;
    }

}
