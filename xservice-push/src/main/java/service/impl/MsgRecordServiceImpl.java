package service.impl;

import domain.MsgRecord;
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
import service.MsgRecordService;
import utils.BaseResponse;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

/**
 *  保存消息记录
 */
public class MsgRecordServiceImpl extends BaseServiceVerticle implements MsgRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MsgRecordServiceImpl.class);

    private SQLClient sqlClient;

    public MsgRecordServiceImpl() {
    }


    public interface Sql {

        //保存消息记录
        public static final String ADD_MESSAGE = "insert into amqp_consume_message "
                + "(amqp_msg_id,channel,msg_body,status,"
                + "created_time,updated_time) "
                + "values "
                + "(?,?,?,?,now(),now())";

    }
    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgRecordService.class, vertx, this, MsgRecordService.SERVICE_ADDRESS);
        publishEventBusService(MsgRecordService.SERVICE_NAME, MsgRecordService.SERVICE_ADDRESS, MsgRecordService.class);

        String ip = IPUtil.getInnerIP();
        XProxyHelper.registerService(MsgRecordService.class, vertx, this, MsgRecordService.getLocalAddress(ip));
        publishEventBusService(MsgRecordService.LOCAL_SERVICE_NAME, MsgRecordService.getLocalAddress(ip), MsgRecordService.class);

        JsonObject mysqlOptions = config().getJsonObject("mysql.config");
        sqlClient = MySQLClient.createShared(vertx, mysqlOptions);
    }

    @Override
    public void addMessage(MsgRecord msg, Handler<AsyncResult<BaseResponse>> resultHandler) {

        if (msg == null) {
            logger.warn("the msg is null");
            return;
        }
        JsonArray array = new JsonArray().add(msg.getAmqpMsgId()).add(msg.getChannel()).add(msg.getMsgBody()).add(msg.getStatus());
        execute(array, Sql.ADD_MESSAGE, new BaseResponse(), resultHandler);
    }

    protected <R> void execute(JsonArray params, String sql, R ret, Handler<AsyncResult<R>> resultHandler) {
        sqlClient.getConnection(connHandler(resultHandler, connection -> {
            connection.updateWithParams(sql, params, r -> {
                if (r.succeeded()) {
                    resultHandler.handle(Future.succeededFuture(ret));
                } else {
                    logger.error( " insert msgRecord error : " + r.cause());
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
