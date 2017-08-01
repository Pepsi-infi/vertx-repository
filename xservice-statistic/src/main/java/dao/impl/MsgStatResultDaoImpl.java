package dao.impl;

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
import org.apache.commons.lang.StringUtils;
import service.dto.MsgStatResultDto;
import util.ConfigUtils;
import utils.BaseResponse;

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
        String ADD_MSG_STAT_RESULT = "insert into msg_stat (msgId,statTime,sendSum,sendAndroidSum," +
                "sendIosSum,sendSockSum,sendMiSum,sendGcmSum,arriveSum,arriveAndroidSum,arriveIosSum,arriveSockSum,arriveMiSum,arriveGcmSum)" +
                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String GET_MSG_STAT_RESULT = "SELECT * FROM `msg_stat` WHERE msgId=? ORDER BY id DESC LIMIT 1;";

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
            return;
        }

        JsonArray jsonArray = new JsonArray();
        //(channel,msgId,statTime,sendSum,sendAndroidSum,sendIosSum,sendSockSum,sendMiSum,sendGcmSum,arriveSum,arriveAndroidSum,arriveIosSum,arriveSockSum,arriveMiSum,arriveGcmSum)
        jsonArray.add(msgStatResultDto.getMsgId())
                .add(msgStatResultDto.getStatTime())
                .add(msgStatResultDto.getSendSum()).add(msgStatResultDto.getSendAndroidSum()).add(msgStatResultDto.getSendIosSum()).add(msgStatResultDto.getSendSockSum()).add(msgStatResultDto.getSendMiSum()).add(msgStatResultDto.getSendGcmSum())
                .add(msgStatResultDto.getArriveSum()).add(msgStatResultDto.getArriveAndroidSum()).add(msgStatResultDto.getArriveIosSum()).add(msgStatResultDto.getArriveSockSum()).add(msgStatResultDto.getArriveMiSum()).add(msgStatResultDto.getArriveGcmSum());
        execute(jsonArray, MsgStatResultDaoImpl.Sql.ADD_MSG_STAT_RESULT, new BaseResponse(), resultHandler);
    }

    @Override
    public void getMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<MsgStatResultDto>> resultHandler) {
        Future<Optional<JsonObject>> future = retrieveOne(msgStatResultDto.getMsgId(), Sql.GET_MSG_STAT_RESULT);
        future.setHandler(result -> {
            if (result.succeeded()) {
                Optional<JsonObject> jsonObject = result.result();
                MsgStatResultDto msgStatResultDto1 = new MsgStatResultDto();
                JsonObject jsonObject1 = jsonObject.orElse(new JsonObject());
                msgStatResultDto1.setSendSum(jsonObject1.getLong("sendSum"));
                msgStatResultDto1.setArriveSum(jsonObject1.getLong("arriveSum"));
                resultHandler.handle(Future.succeededFuture(msgStatResultDto1));
            } else {
                resultHandler.handle(Future.failedFuture(result.cause()));
            }
        });
    }


}
