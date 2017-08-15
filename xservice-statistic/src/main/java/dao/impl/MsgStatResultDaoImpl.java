package dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import dao.BaseDaoVerticle;
import dao.MsgStatResultDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import service.dto.MsgStatResultDto;
import service.dto.MsgStatResultPage;
import service.dto.MsgStatResultPageWrapper;
import utils.BaseResponse;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Created by lufei
 * Date : 2017/7/30 22:31
 * Description :
 */
public class MsgStatResultDaoImpl extends BaseDaoVerticle implements MsgStatResultDao {

    private static final Logger logger = LoggerFactory.getLogger(MsgStatResultDaoImpl.class);


    public MsgStatResultDaoImpl() {
    }


    public interface Sql {
        static final String ADD_MSG_STAT_RESULT = "insert into msg_stat (msgId,statTime,sendSum,sendAndroidSum,sendIosSum,sendSockSum,sendMiSum,sendGcmSum," +
                "arriveSum,arriveAndroidSum,arriveIosSum,arriveSockSum,arriveMiSum,arriveGcmSum," +
                "clickSum,clickAndroidSum,clickIosSum,clickSockSum,clickMiSum,clickGcmSum)" +
                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        static final String UPDATE_MSG_STAT_RESULT = "UPDATE msg_stat SET statTime =?, " +
                "sendSum =?, sendAndroidSum =?, sendIosSum=?,sendSockSum =?, sendMiSum =?, sendGcmSum =?," +
                "arriveSum =?, arriveAndroidSum =?, arriveIosSum =?, arriveSockSum =?,arriveMiSum =?, arriveGcmSum =?, " +
                "clickSum =?, clickAndroidSum =?, clickIosSum =?, clickSockSum =?, clickMiSum =?, clickGcmSum =? " +
                "WHERE msgId =?";

        static final String GET_MSG_STAT_RESULT = "SELECT * FROM `msg_stat` WHERE msgId=? ORDER BY id DESC LIMIT 1;";

        static final String QUERY_MSG_STAT_RESULT = "SELECT * FROM `msg_stat` WHERE 1=1 %s ORDER BY id DESC LIMIT ?,?;";

        static final String COUNT_MSG_STAT_RESULT = "SELECT COUNT(*) FROM `msg_stat` WHERE 1=? %s ";

    }


    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgStatResultDao.class, vertx, this, MsgStatResultDao.SERVICE_ADDRESS);
        publishEventBusService(MsgStatResultDao.SERVICE_NAME, MsgStatResultDao.SERVICE_ADDRESS, MsgStatResultDao.class);

        String env = System.getProperty("env", "dev");

        client = MySQLClient.createNonShared(vertx, config().getJsonObject("mysql").getJsonObject("mc-statistic"));
    }


    @Override
    public void addMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
        if (StringUtils.isBlank(msgStatResultDto.getMsgId())) {
            logger.error("the msgId is null. msgStatResultDto : {}", msgStatResultDto);
            resultHandler.handle(Future.failedFuture("the stat msgId is null. "));
        } else {
            JsonArray jsonArray = new JsonArray();
            //(channel,msgId,statTime,sendSum,sendAndroidSum,sendIosSum,sendSockSum,sendMiSum,sendGcmSum,arriveSum,arriveAndroidSum,arriveIosSum,arriveSockSum,arriveMiSum,arriveGcmSum)
            jsonArray.add(msgStatResultDto.getMsgId())
                    .add(msgStatResultDto.getStatTime())
                    .add(msgStatResultDto.getSendSum()).add(msgStatResultDto.getSendAndroidSum()).add(msgStatResultDto.getSendIosSum()).add(msgStatResultDto.getSendSockSum()).add(msgStatResultDto.getSendMiSum()).add(msgStatResultDto.getSendGcmSum())
                    .add(msgStatResultDto.getArriveSum()).add(msgStatResultDto.getArriveAndroidSum()).add(msgStatResultDto.getArriveIosSum()).add(msgStatResultDto.getArriveSockSum()).add(msgStatResultDto.getArriveMiSum()).add(msgStatResultDto.getArriveGcmSum())
                    .add(msgStatResultDto.getClickSum()).add(msgStatResultDto.getClickAndroidSum()).add(msgStatResultDto.getClickIosSum()).add(msgStatResultDto.getClickSockSum()).add(msgStatResultDto.getClickMiSum()).add(msgStatResultDto.getClickGcmSum());
            execute(jsonArray, MsgStatResultDaoImpl.Sql.ADD_MSG_STAT_RESULT, new BaseResponse(), resultHandler);
        }
    }

    @Override
    public void updateMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
        if (StringUtils.isBlank(msgStatResultDto.getMsgId())) {
            logger.error("the msgId is null. msgStatResultDto : {}", msgStatResultDto);
            resultHandler.handle(Future.failedFuture("the stat msgId is null. "));
        } else {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(msgStatResultDto.getStatTime())
                    .add(msgStatResultDto.getSendSum()).add(msgStatResultDto.getSendAndroidSum()).add(msgStatResultDto.getSendIosSum()).add(msgStatResultDto.getSendSockSum()).add(msgStatResultDto.getSendMiSum()).add(msgStatResultDto.getSendGcmSum())
                    .add(msgStatResultDto.getArriveSum()).add(msgStatResultDto.getArriveAndroidSum()).add(msgStatResultDto.getArriveIosSum()).add(msgStatResultDto.getArriveSockSum()).add(msgStatResultDto.getArriveMiSum()).add(msgStatResultDto.getArriveGcmSum())
                    .add(msgStatResultDto.getClickSum()).add(msgStatResultDto.getClickAndroidSum()).add(msgStatResultDto.getClickIosSum()).add(msgStatResultDto.getClickSockSum()).add(msgStatResultDto.getClickMiSum()).add(msgStatResultDto.getClickGcmSum())
                    .add(msgStatResultDto.getMsgId());
            execute(jsonArray, Sql.UPDATE_MSG_STAT_RESULT, new BaseResponse(), resultHandler);
        }
    }

    @Override
    public void getMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<MsgStatResultDto>> resultHandler) {
        if (StringUtils.isBlank(msgStatResultDto.getMsgId())) {
            logger.error("the msgId is null. msgStatResultDto : {}", msgStatResultDto);
            resultHandler.handle(Future.failedFuture("the stat msgId is null. "));
        } else {
            Future<Optional<JsonObject>> future = retrieveOne(msgStatResultDto.getMsgId(), Sql.GET_MSG_STAT_RESULT);
            future.setHandler(result -> {
                if (result.succeeded()) {
                    Optional<JsonObject> jsonObject = result.result();
                    JsonObject jsonObject1 = jsonObject.orElse(null);
                    MsgStatResultDto msgStatResultDto1 = jsonObject1 != null ? jsonObject1.mapTo(MsgStatResultDto.class) : null;
                    resultHandler.handle(Future.succeededFuture(msgStatResultDto1));
                } else {
                    logger.error("[dao] get msgStatResult error.", result.cause());
                    resultHandler.handle(Future.failedFuture(result.cause()));
                }
            });
        }
    }

    @Override
    public void queryMsgStatResultByPage(Map<String, String> params, int page, int limit, Handler<AsyncResult<MsgStatResultPageWrapper>> resultHandler) {
        String querySql = Sql.QUERY_MSG_STAT_RESULT;
        String countSql = Sql.COUNT_MSG_STAT_RESULT;
        StringBuilder sb = new StringBuilder();
        String msgId = MapUtils.getString(params, "msgId");
        if (StringUtils.isNotBlank(msgId)) {
            sb.append(" and msgId = '").append(msgId).append("'");
        }
        querySql = String.format(querySql, sb.toString());
        countSql = String.format(countSql, sb.toString());

        List<Future> futures = new ArrayList<>();
        Future<List<JsonObject>> future = retrieveByPage(page, limit, querySql);
        futures.add(future);

        Future<Optional<JsonObject>> countFuture = retrieveOne(1, countSql);
        futures.add(countFuture);

        CompositeFuture compositeFuture = CompositeFuture.all(futures);
        compositeFuture.setHandler(asr1 -> {
            if (asr1.succeeded()) {
                List<JsonObject> jsonObjects = asr1.result().resultAt(0);
                List<MsgStatResultDto> msgStatResultDtos = Lists.transform(jsonObjects, new Function<JsonObject, MsgStatResultDto>() {
                    @Nullable
                    @Override
                    public MsgStatResultDto apply(@Nullable JsonObject jsonObject) {
                        return jsonObject.mapTo(MsgStatResultDto.class);
                    }
                });
                Optional<JsonObject> jsonObject = asr1.result().resultAt(1);
                Long count = jsonObject.orElse(new JsonObject()).getLong("COUNT(*)");
                MsgStatResultPageWrapper wrapper = new MsgStatResultPageWrapper();
                MsgStatResultPage msgStatResultPage = new MsgStatResultPage(msgStatResultDtos, page, limit, count);
                wrapper.setData(msgStatResultPage);
                resultHandler.handle(Future.succeededFuture(wrapper));
            } else {
                logger.error(asr1.cause());
                resultHandler.handle(Future.failedFuture(asr1.cause()));
            }
        });

    }
}
