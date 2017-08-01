package dao.impl;

import dao.BaseDaoVerticle;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import org.apache.commons.lang.StringUtils;
import dao.DeviceDao;
import service.dto.DeviceDto;
import util.ConfigUtils;
import utils.BaseResponse;

/**
 * Created by lufei
 * Date : 2017/7/26 10:32
 * Description :
 */
public class DeviceDaoImpl extends BaseDaoVerticle implements DeviceDao {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDaoImpl.class);

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
        JsonObject jsonObject = ConfigUtils.getJsonConf(env + "/jdbc-device-" + env + ".json");

        client = MySQLClient.createNonShared(vertx, jsonObject);

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


}
