package dao.impl;

import constant.ConnectionConsts;
import domain.AmqpConsumeMessage;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import dao.DeviceDao;
import utils.BaseResponse;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  保存消息记录
 */
public class DeviceDaoImpl extends BaseServiceVerticle implements DeviceDao {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDaoImpl.class);

    private SQLClient sqlClient;

    public interface Sql {

        //保存消息记录
        public static final String ADD_MESSAGE = "insert into amqp_consume_message "
                + "(amqp_msg_id,channel,msg_body,status,"
                + "created_time,updated_time) "
                + "values "
                + "(?,?,?,?,now(),now())";

    }

    public DeviceDaoImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(DeviceDao.class, vertx, this, DeviceDao.SERVICE_ADDRESS);
        publishEventBusService(DeviceDao.SERVICE_NAME, DeviceDao.SERVICE_ADDRESS, DeviceDao.class);

        String ip = IPUtil.getInnerIP();
        XProxyHelper.registerService(DeviceDao.class, vertx, this, DeviceDao.getLocalAddress(ip));
        publishEventBusService(DeviceDao.LOCAL_SERVICE_NAME, DeviceDao.getLocalAddress(ip), DeviceDao.class);


        String jdbcConfig = System.getProperty("jdbcConfig", ConnectionConsts.JDBC_CONFIG_PATH);
        JsonObject jsonObject = this.getJsonConf(jdbcConfig);
        sqlClient = MySQLClient.createShared(vertx, jsonObject);

//        AmqpConsumeMessage dto = new AmqpConsumeMessage();
//        dto.setAmqpMsgId("qqqq");
//        dto.setChannel("aaaa");
//        dto.setMsgBody("qdaslfjasdf");
//        dto.setStatus(2);
//        addMessage(dto, result -> {
//            System.out.println( " ================= " + result.result().getResultStatus());
//        });
    }

    private JsonObject getJsonConf(String configPath) {
        logger.info("jdbc Path: " + configPath);
        JsonObject conf = new JsonObject();
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = ctxClsLoader.getResourceAsStream(configPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conf = new JsonObject(sb.toString());
            logger.info("Loaded jdbc.json file from [" + configPath + "] : " + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load configuration file" + e);
        }
        return conf;
    }


    @Override
    public void addMessage(AmqpConsumeMessage msg, Handler<AsyncResult<BaseResponse>> resultHandler) {
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


    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("dao.impl.DeviceDaoImpl");

    }
}
