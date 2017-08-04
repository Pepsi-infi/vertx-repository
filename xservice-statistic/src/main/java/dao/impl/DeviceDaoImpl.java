package dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import dao.BaseDaoVerticle;
import dao.DeviceDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import iservice.dto.DeviceDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import util.ConfigUtils;
import utils.BaseResponse;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei
 * Date : 2017/7/26 10:32
 * Description :
 */
public class DeviceDaoImpl extends BaseDaoVerticle implements DeviceDao {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDaoImpl.class);

    public interface Sql {
        static final String ADD_USER_DEVICE = "insert into device (uid,phone,deviceType,deviceToken,imei,osType,osVersion,appCode,appVersion,antFingerprint,isAcceptPush) values (?,?,?,?,?,?,?,?,?,?,?)";

        static final String UPDATE_USER_DEVICE = "UPDATE device SET uid=?,phone=?,deviceType=?,deviceToken=?,imei=?,osType=?," +
                "osVersion=?,appCode=?,appVersion=?,isAcceptPush=? " +
                "WHERE antFingerprint=?";

        static final String QUERY_USER_DEVICE = "SELECT * FROM device WHERE 1=1 %s ORDER BY id DESC";

    }

    public DeviceDaoImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(DeviceDao.class, vertx, this, DeviceDao.SERVICE_ADDRESS);
        publishEventBusService(DeviceDao.SERVICE_NAME, DeviceDao.SERVICE_ADDRESS, DeviceDao.class);


        String env = System.getProperty("env", "dev");
        JsonObject jsonObject = ConfigUtils.getJsonConf(env + "/jdbc-device-" + env + ".json");

        client = MySQLClient.createNonShared(vertx, jsonObject);

    }


    @Override
    public void addDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
        if (StringUtils.isBlank(userDeviceDto.getAntFingerprint()) || StringUtils.isBlank(userDeviceDto.getImei()) || userDeviceDto.getOsType() <= 0) {
            logger.error("[addDevice] the antFingerprint or imei or osType is null");
            resultHandler.handle(Future.failedFuture("the antFingerprint or imei or osType is null"));
        } else {
            //(uid,phone,deviceType,deviceToken,imei,osType,osVersion,appCode,appVersion,antFingerprint,isAcceptPush)
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(userDeviceDto.getUid() != null ? userDeviceDto.getUid() : "")
                    .add(userDeviceDto.getPhone() != null ? userDeviceDto.getPhone() : "")
                    .add(userDeviceDto.getDeviceType() != null ? userDeviceDto.getDeviceType() : "")
                    .add(userDeviceDto.getDeviceToken()).add(userDeviceDto.getImei()).add(userDeviceDto.getOsType())
                    .add(userDeviceDto.getOsVersion() != null ? userDeviceDto.getOsVersion() : "")
                    .add(userDeviceDto.getAppCode() != null ? userDeviceDto.getAppCode() : 0)
                    .add(userDeviceDto.getAppVersion() != null ? userDeviceDto.getAppVersion() : "")
                    .add(userDeviceDto.getAntFingerprint() != null ? userDeviceDto.getAntFingerprint() : "")
                    .add(userDeviceDto.getIsAcceptPush() != null ? userDeviceDto.getIsAcceptPush() : 0);
            execute(jsonArray, Sql.ADD_USER_DEVICE, new BaseResponse(), resultHandler);
        }
    }

    @Override
    public void updateDevice(DeviceDto deviceDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
        if (StringUtils.isBlank(deviceDto.getAntFingerprint())) {
            logger.error("[updateDevice] the antFingerprint is null");
            resultHandler.handle(Future.failedFuture("the antFingerprint is null"));
        } else {
            JsonArray jsonArray = new JsonArray();
            //(uid,phone,deviceType,deviceToken,imei,osType,osVersion,appCode,appVersion,antFingerprint)
            jsonArray.add(deviceDto.getUid() != null ? deviceDto.getUid() : "")
                    .add(deviceDto.getPhone() != null ? deviceDto.getPhone() : "")
                    .add(deviceDto.getDeviceType() != null ? deviceDto.getDeviceType() : "")
                    .add(deviceDto.getDeviceToken()).add(deviceDto.getImei()).add(deviceDto.getOsType())
                    .add(deviceDto.getOsVersion() != null ? deviceDto.getOsVersion() : "")
                    .add(deviceDto.getAppCode() != null ? deviceDto.getAppCode() : 0)
                    .add(deviceDto.getAppVersion() != null ? deviceDto.getAppVersion() : "")
                    .add(deviceDto.getIsAcceptPush() != null ? deviceDto.getIsAcceptPush() : 0)
                    .add(deviceDto.getAntFingerprint() != null ? deviceDto.getAntFingerprint() : "");
            execute(jsonArray, Sql.UPDATE_USER_DEVICE, new BaseResponse(), resultHandler);
        }
    }

    @Override
    public void getDevice(Map<String, String> params, Handler<AsyncResult<DeviceDto>> resultHandler) {
        String sql = Sql.QUERY_USER_DEVICE;
        StringBuilder sb = new StringBuilder();
        String antFingerprint = MapUtils.getString(params, "antFingerprint");
        if (StringUtils.isNotBlank(antFingerprint)) {
            sb.append(" and antFingerprint = '").append(antFingerprint).append("'");
        }
        sql = String.format(sql, sb.toString());
        Future<List<JsonObject>> future = retrieveMany(new JsonArray(), sql);
        future.setHandler(result -> {
            if (result.succeeded()) {
                List<DeviceDto> deviceDtos = Lists.transform(result.result(), new Function<JsonObject, DeviceDto>() {
                    @Nullable
                    @Override
                    public DeviceDto apply(@Nullable JsonObject jsonObject) {
                        return jsonObject.mapTo(DeviceDto.class);
                    }
                });
                resultHandler.handle(Future.succeededFuture(CollectionUtils.isNotEmpty(deviceDtos) ? deviceDtos.get(0) : null));
            } else {
                logger.error(result.cause());
                resultHandler.handle(Future.failedFuture(result.cause()));
            }
        });
    }

    @Override
    public void queryDevices(Map<String, String> params, Handler<AsyncResult<List<DeviceDto>>> resultHandler) {
        String sql = Sql.QUERY_USER_DEVICE;
        StringBuilder sb = new StringBuilder();
        String phone = MapUtils.getString(params, "phone");
        if (StringUtils.isNotBlank(phone)) {
            sb.append(" and phone = '").append(phone).append("'");
        }
        sql = String.format(sql, sb.toString());
        Future<List<JsonObject>> future = retrieveMany(new JsonArray(), sql);
        future.setHandler(result -> {
            if (result.succeeded()) {
                List<DeviceDto> deviceDtos = Lists.transform(result.result(), new Function<JsonObject, DeviceDto>() {
                    @Nullable
                    @Override
                    public DeviceDto apply(@Nullable JsonObject jsonObject) {
                        return jsonObject.mapTo(DeviceDto.class);
                    }
                });
                resultHandler.handle(Future.succeededFuture(deviceDtos));
            } else {
                logger.error(result.cause());
                resultHandler.handle(Future.failedFuture(result.cause()));
            }
        });
    }


}
