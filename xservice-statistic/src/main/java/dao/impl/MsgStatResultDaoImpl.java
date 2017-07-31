package dao.impl;

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
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import org.apache.commons.lang.StringUtils;
import service.dto.MsgStatResultDto;
import util.FileUtils;
import utils.BaseResponse;
import xservice.BaseServiceVerticle;


/**
 * Created by lufei
 * Date : 2017/7/30 22:31
 * Description :
 */
public class MsgStatResultDaoImpl extends BaseServiceVerticle implements MsgStatResultDao {

    private static final Logger logger = LoggerFactory.getLogger(MsgStatResultDaoImpl.class);

    private SQLClient sqlClient;

    public interface Sql {
        static final String ADD_MSG_STAT_RESULT = "insert into msg_stat (channel,msgId,statTime,sendSum,sendAndroidSum," +
                "sendIosSum,sendSockSum,sendMiSum,sendGcmSum,arriveSum,arriveAndroidSum,arriveIosSum,arriveSockSum,arriveMiSum,arriveGcmSum)" +
                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    }

    public MsgStatResultDaoImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgStatResultDao.class, vertx, this, MsgStatResultDao.SERVICE_ADDRESS);
        publishEventBusService(MsgStatResultDao.SERVICE_NAME, MsgStatResultDao.SERVICE_ADDRESS, MsgStatResultDao.class);

        String env = System.getProperty("env", "dev");
        JsonObject jsonObject = FileUtils.getJsonConf(env + "/jdbc-statistic-" + env + ".json");

        sqlClient = MySQLClient.createNonShared(vertx, jsonObject);

    }


    @Override
    public void addMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
        if (StringUtils.isBlank(msgStatResultDto.getMsgId())) {
            logger.error("the msgId is null. msgStatResultDto : {}", msgStatResultDto);
            return;
        }

        JsonArray jsonArray = new JsonArray();
        //(channel,msgId,statTime,sendSum,sendAndroidSum,sendIosSum,sendSockSum,sendMiSum,sendGcmSum,arriveSum,arriveAndroidSum,arriveIosSum,arriveSockSum,arriveMiSum,arriveGcmSum)
        jsonArray.add(msgStatResultDto.getChannel() != null ? msgStatResultDto.getChannel() : 0)
                .add(StringUtils.defaultIfEmpty(msgStatResultDto.getMsgId(), ""))
                .add(msgStatResultDto.getStatTime())
                .add(msgStatResultDto.getSendSum()).add(msgStatResultDto.getSendAndroidSum()).add(msgStatResultDto.getSendIosSum()).add(msgStatResultDto.getSendSockSum()).add(msgStatResultDto.getSendMiSum()).add(msgStatResultDto.getSendGcmSum())
                .add(msgStatResultDto.getArriveSum()).add(msgStatResultDto.getArriveAndroidSum()).add(msgStatResultDto.getArriveIosSum()).add(msgStatResultDto.getArriveSockSum()).add(msgStatResultDto.getArriveMiSum()).add(msgStatResultDto.getArriveGcmSum());
        execute(jsonArray, MsgStatResultDaoImpl.Sql.ADD_MSG_STAT_RESULT, new BaseResponse(), resultHandler);
    }

    protected <R> void execute(JsonArray params, String sql, R ret, Handler<AsyncResult<R>> resultHandler) {
        sqlClient.getConnection(connHandler(resultHandler, connection -> {
            connection.updateWithParams(sql, params, r -> {
                if (r.succeeded()) {
                    resultHandler.handle(Future.succeededFuture(ret));
                } else {
                    logger.error(r.cause());
                    resultHandler.handle(Future.failedFuture(r.cause()));
                }
                connection.close();
            });
        }));
    }

    /**
     * A helper methods that generates async handler for SQLConnection
     *
     * @return generated handler
     */
    protected <R> Handler<AsyncResult<SQLConnection>> connHandler(Handler<AsyncResult<R>> h1, Handler<SQLConnection> h2) {
        return conn -> {
            if (conn.succeeded()) {
                final SQLConnection connection = conn.result();
                h2.handle(connection);
            } else {
                h1.handle(Future.failedFuture(conn.cause()));
            }
        };
    }
}
