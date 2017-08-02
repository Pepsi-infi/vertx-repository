package dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import constants.CacheConstants;
import dao.BaseDaoVerticle;
import dao.MsgStatResultDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import service.dto.MsgStatResultDto;
import util.ConfigUtils;
import utils.BaseResponse;
import utils.CalendarUtil;

import javax.annotation.Nullable;
import java.util.Date;
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

    }


    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgStatResultDao.class, vertx, this, MsgStatResultDao.SERVICE_ADDRESS);
        publishEventBusService(MsgStatResultDao.SERVICE_NAME, MsgStatResultDao.SERVICE_ADDRESS, MsgStatResultDao.class);

        String env = System.getProperty("env", "dev");
        JsonObject jsonObject = ConfigUtils.getJsonConf(env + "/jdbc-statistic-" + env + ".json");

        client = MySQLClient.createNonShared(vertx, jsonObject);
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
                    resultHandler.handle(Future.succeededFuture(convertMsgStatResultDto(jsonObject1)));
                } else {
                    resultHandler.handle(Future.failedFuture(result.cause()));
                }
            });
        }
    }

    @Override
    public void queryMsgStatResultByPage(Map<String, String> params, int page, int limit, Handler<AsyncResult<List<MsgStatResultDto>>> resultHandler) {
        String sql = Sql.QUERY_MSG_STAT_RESULT;
        StringBuilder sb = new StringBuilder();
        String msgId = MapUtils.getString(params, "msgId");
        if (StringUtils.isNotBlank(msgId)) {
            sb.append(" and msgId = '").append(msgId).append("'");
        }
        sql = String.format(sql, sb.toString());
        Future<List<JsonObject>> future = retrieveByPage(page, limit, sql);
        future.setHandler(result -> {
            if (result.succeeded()) {
                List<MsgStatResultDto> msgStatResultDtos = Lists.transform(result.result(), new Function<JsonObject, MsgStatResultDto>() {
                    @Nullable
                    @Override
                    public MsgStatResultDto apply(@Nullable JsonObject jsonObject) {
                        return convertMsgStatResultDto(jsonObject);
                    }
                });
                resultHandler.handle(Future.succeededFuture(msgStatResultDtos));
            } else {
                resultHandler.handle(Future.failedFuture(result.cause()));
            }
        });

    }

    private MsgStatResultDto convertMsgStatResultDto(JsonObject jsonObject1) {
        if (jsonObject1 == null) {
            return null;
        }
        MsgStatResultDto msgStatResultDto = new MsgStatResultDto();
        msgStatResultDto.setMsgId(jsonObject1.getString("msgId"));
        msgStatResultDto.setStatTime(jsonObject1.getString("statTime"));
        msgStatResultDto.setSendSum(jsonObject1.getLong("sendSum"));
        msgStatResultDto.setSendAndroidSum(jsonObject1.getLong("sendAndroidSum"));
        msgStatResultDto.setSendIosSum(jsonObject1.getLong("sendIosSum"));
        msgStatResultDto.setSendSockSum(jsonObject1.getLong("sendSockSum"));
        msgStatResultDto.setSendGcmSum(jsonObject1.getLong("sendGcmSum"));
        msgStatResultDto.setSendMiSum(jsonObject1.getLong("sendMiSum"));
        msgStatResultDto.setArriveSum(jsonObject1.getLong("arriveSum"));
        msgStatResultDto.setArriveAndroidSum(jsonObject1.getLong("arriveAndroidSum"));
        msgStatResultDto.setArriveIosSum(jsonObject1.getLong("arriveIosSum"));
        msgStatResultDto.setArriveSockSum(jsonObject1.getLong("arriveSockSum"));
        msgStatResultDto.setArriveGcmSum(jsonObject1.getLong("arriveGcmSum"));
        msgStatResultDto.setArriveMiSum(jsonObject1.getLong("arriveMiSum"));
        msgStatResultDto.setClickSum(jsonObject1.getLong("clickSum"));
        msgStatResultDto.setClickAndroidSum(jsonObject1.getLong("clickAndroidSum"));
        msgStatResultDto.setClickIosSum(jsonObject1.getLong("clickIosSum"));
        msgStatResultDto.setClickSockSum(jsonObject1.getLong("clickSockSum"));
        msgStatResultDto.setClickGcmSum(jsonObject1.getLong("clickGcmSum"));
        msgStatResultDto.setClickMiSum(jsonObject1.getLong("clickMiSum"));
        return msgStatResultDto;
    }


}
