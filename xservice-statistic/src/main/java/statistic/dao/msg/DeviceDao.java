package statistic.dao.msg;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import statistic.service.dto.DeviceDto;
import utils.BaseResponse;
import utils.IPUtil;

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
    String SERVICE_NAME = "dao-userDevice-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "dao.userDevice.service";

    String LOCAL_SERVICE_NAME = "local-dao-userDevice-service";

    static DeviceDao createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(DeviceDao.class, vertx, DeviceDao.SERVICE_ADDRESS);
    }

    static DeviceDao createLocalProxy(Vertx vertx) {
        return ProxyHelper.createProxy(DeviceDao.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
                new DeliveryOptions().setSendTimeout(3000));
    }

    static String getLocalAddress(String ip) {
        return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
    }

    void addUserDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> resultHandler);

}
