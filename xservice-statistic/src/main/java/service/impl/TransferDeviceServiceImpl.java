package service.impl;

import com.google.common.collect.Maps;
import constants.AppCodeEnum;
import constants.ChannelEnum;
import dao.CarBizEuroDao;
import dao.DeviceDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import iservice.dto.DeviceDto;
import org.apache.commons.collections.CollectionUtils;
import rxjava.BaseServiceVerticle;
import service.TransferDeviceService;
import service.dto.CarBizEuroDto;
import utils.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei
 * Date : 2017/8/9 18:39
 * Description :
 */
public class TransferDeviceServiceImpl extends BaseServiceVerticle implements TransferDeviceService {

    private static final Logger logger = LoggerFactory.getLogger(TransferDeviceServiceImpl.class);

    private CarBizEuroDao carBizEuroDao;

    private DeviceDao deviceDao;


    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(TransferDeviceService.class, vertx.getDelegate(), this, TransferDeviceService.SERVICE_ADDRESS);
        publishEventBusService(TransferDeviceService.SERVICE_NAME, TransferDeviceService.SERVICE_ADDRESS, TransferDeviceService.class);
        deviceDao = DeviceDao.createProxy(vertx.getDelegate());
        carBizEuroDao = CarBizEuroDao.createProxy(vertx.getDelegate());

    }


    @Override
    public void transferDevice(Handler<AsyncResult<BaseResponse>> result) {
        carBizEuroDao.countCarBizEuro(null, res1 -> {
            if (res1.succeeded()) {
                long total = res1.result();
                int count = 0;
                int page = 0;
                while (count < total) {
                    Future<List<CarBizEuroDto>> future = Future.future();
                    carBizEuroDao.queryCarBizEuro(null, page, 100, future.completer());
                    count += 100;
                    page++;
                    future.setHandler(ar1 -> {
                        if (ar1.succeeded()) {
                            List<CarBizEuroDto> carBizEuroDtos = ar1.result();
                            for (CarBizEuroDto carBizEuroDto : carBizEuroDtos) {
                                DeviceDto deviceDto = new DeviceDto();
                                deviceDto.setPhone(carBizEuroDto.getPhone());
                                deviceDto.setChannel(ChannelEnum.APNS.getType());
                                deviceDto.setDeviceToken(carBizEuroDto.getDeviceToken());
                                deviceDto.setAppCode(AppCodeEnum.SY_PASSENGER.getCode());

                                Map<String, String> params = Maps.newHashMap();
                                params.put("phone", deviceDto.getPhone());
                                params.put("deviceToken", deviceDto.getDeviceToken());
                                deviceDao.queryDevices(params, ar3 -> {
                                    if (ar3.succeeded()) {
                                        List<DeviceDto> deviceDtos = ar3.result();
                                        if (CollectionUtils.isEmpty(deviceDtos)) {
                                            deviceDao.addDevice(deviceDto, ar2 -> {
                                                if (ar2.failed()) {
                                                    logger.error(ar2.cause());
                                                }
                                            });
                                        }
                                    } else {
                                        logger.error(ar3.cause());
                                    }
                                });
                            }
                        } else {
                            logger.error(ar1.cause());
                        }
                    });
                }
                result.handle(Future.succeededFuture(new BaseResponse()));
            } else {
                logger.error(res1.cause());
                result.handle(Future.failedFuture(res1.cause()));
            }
        });

    }
}
