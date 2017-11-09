package iservice;

import java.util.List;
import java.util.Map;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import iservice.dto.DeviceDto;
import utils.BaseResponse;

/**
 * Created by lufei
 * Date : 2017/7/25 13:32
 * Description :
 */
@ProxyGen
@VertxGen
public interface DeviceService {
    public static final String SERVICE_NAME = "http.device.eb.service";

    public static final String SERVICE_ADDRESS = "http-device-eb-service";

    static DeviceService createProxy(Vertx vertx) {
        return XProxyHelper.createProxy(DeviceService.class, DeviceService.class, vertx, DeviceService.SERVICE_ADDRESS);
    }


    /**
     * @param userDeviceDto
     * @param result
     */
    void reportDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> result);


    /**
     * @param param
     * @param result
     */
    void queryDevices(Map<String, String> param, Handler<AsyncResult<List<DeviceDto>>> result);

    /**
     * 增加设备号，按照该方式进行上报
     * @param userDeviceDto
     * @param resultHandler
     */
	void reportDeviceByAddDeviceId(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> resultHandler);
}
