package dao.impl;

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
import dao.DeviceDao;
import service.dto.DeviceDto;
import utils.BaseResponse;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei
 * Date : 2017/7/26 10:32
 * Description :
 */
public class DeviceDaoImpl extends BaseServiceVerticle implements DeviceDao {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDaoImpl.class);

    private SQLClient sqlClient;

    public interface Sql {
        static final String ADD_USER_DEVICE = "insert into device (uid,phone,deviceType,deviceToken,imei,osType,osVersion,appCode,appVersion,antFingerprint) values (?,?,?,?,?,?,?,?,?,?)";

    }

    public DeviceDaoImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(DeviceDao.class, vertx, this, DeviceDao.SERVICE_ADDRESS);
        publishEventBusService(DeviceDao.SERVICE_NAME, DeviceDao.SERVICE_ADDRESS, DeviceDao.class);


        String env = System.getProperty("env", "dev");
        JsonObject jsonObject = this.getJsonConf(env + "/jdbc-" + env + ".json");

        sqlClient = MySQLClient.createShared(vertx, jsonObject);

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
            logger.info("Loaded jdbc-dev.json file from [" + configPath + "/jdbc-dev.json] and config.json="
                    + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load configuration file" + e);
        }
        return conf;
    }


    @Override
    public void addUserDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
        if (StringUtils.isBlank(userDeviceDto.getDeviceToken()) || StringUtils.isBlank(userDeviceDto.getImei()) || userDeviceDto.getOsType() <= 0) {
            logger.warn("the deviceToken or imei or osType is null");
            return;
        }
        JsonArray jsonArray = new JsonArray();
        //(uid,phone,deviceType,deviceToken,imei,osType,osVersion,appCode,appVersion,antFingerprint)
        jsonArray.add(userDeviceDto.getUid() != null ? userDeviceDto.getUid() : "")
                .add(userDeviceDto.getPhone() != null ? userDeviceDto.getPhone() : "")
                .add(userDeviceDto.getDeviceType() != null ? userDeviceDto.getDeviceType() : "")
                .add(userDeviceDto.getDeviceToken()).add(userDeviceDto.getImei()).add(userDeviceDto.getOsType())
                .add(userDeviceDto.getOsVersion() != null ? userDeviceDto.getOsVersion() : "")
                .add(userDeviceDto.getAppCode() != null ? userDeviceDto.getAppCode() : 0)
                .add(userDeviceDto.getAppVersion() != null ? userDeviceDto.getAppVersion() : "")
                .add(userDeviceDto.getAntFingerprint() != null ? userDeviceDto.getAntFingerprint() : "");
        execute(jsonArray, Sql.ADD_USER_DEVICE, new BaseResponse(), resultHandler);
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

}
