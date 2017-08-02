package dao;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import iservice.dto.DeviceDto;
import utils.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei
 * Date : 2017/7/26 10:17
 * Description :
 */
@VertxGen
@ProxyGen
public interface DeviceDao {
    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "dao-device-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "dao.device.service";


    static DeviceDao createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(DeviceDao.class, vertx, DeviceDao.SERVICE_ADDRESS);
    }


    void addDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> resultHandler);

    void queryDevices(Map<String, String> params, Handler<AsyncResult<List<DeviceDto>>> resultHandler);

}
