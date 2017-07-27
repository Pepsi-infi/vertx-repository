package api;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.lang.StringUtils;
import rxjava.RestAPIVerticle;
import service.DeviceService;
import service.dto.DeviceDto;
import utils.IPUtil;
import utils.JsonUtil;

/**
 * Created by lufei
 * Date : 2017/7/27 10:13
 * Description :
 */
public class RestDeviceVerticle extends RestAPIVerticle {

    private static final Logger logger = LoggerFactory.getLogger(RestDeviceVerticle.class);

    private DeviceService deviceService;

    @Override
    public void start() throws Exception {
        super.start();

        logger.info("Rest device verticle: Start...");

        Router router = Router.router(vertx);
        router.route(StatRestConstants.Stat.USER_DEVICE_REPORT).handler(this::reportUserDevice);
        Future<Void> voidFuture = Future.future();

        String serverHost = this.getServerHost();
        createHttpServer(router, serverHost, StatRestConstants.Stat.HTTP_PORT).compose(
                serverCreated -> publishHttpEndpoint(StatRestConstants.Stat.SERVICE_NAME, serverHost,
                        StatRestConstants.Stat.HTTP_PORT, StatRestConstants.Stat.SERVICE_ROOT)).setHandler(
                voidFuture.completer());

        this.initUserDeviceService();
    }

    private void initUserDeviceService() {
        deviceService = DeviceService.createLocalProxy(vertx.getDelegate());
    }

    private String getServerHost() {
        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
    }

    private void reportUserDevice(RoutingContext context) {
        DeviceDto userDeviceDto = buildDeviceDto(context);
        logger.info("the request params : userDeviceDto : {}", userDeviceDto);
        deviceService.reportUserDevice(userDeviceDto, resultHandler(context, JsonUtil::encodePrettily));
    }

    /**
     * @param context
     * @return
     */
    private DeviceDto buildDeviceDto(RoutingContext context) {
        DeviceDto userDeviceDto = new DeviceDto();
        String uid = context.request().params().get("uid");
        String phone = context.request().params().get("phone");
        String deviceType = context.request().params().get("deviceType");
        String deviceToken = context.request().params().get("deviceToken");
        String imei = context.request().params().get("imei");
        String osType = context.request().params().get("osType");
        String osVersion = context.request().params().get("osVersion");
        String appCode = context.request().params().get("appCode");
        String appVersion = context.request().params().get("appVersion");
        String antFingerprint = context.request().params().get("antFingerprint");


        userDeviceDto.setDeviceToken(deviceToken);
        userDeviceDto.setOsVersion(osVersion);
        userDeviceDto.setPhone(phone);
        userDeviceDto.setDeviceType(deviceType);
        userDeviceDto.setImei(imei);
        userDeviceDto.setAppVersion(appVersion);
        userDeviceDto.setAntFingerprint(antFingerprint);
        if (StringUtils.isNotBlank(osType)) {
            userDeviceDto.setOsType(Integer.valueOf(osType));
        }
        if (StringUtils.isNotBlank(uid)) {
            userDeviceDto.setUid(Long.valueOf(uid));
        }
        if (StringUtils.isNotBlank(appCode)) {
            userDeviceDto.setAppCode(Integer.valueOf(appCode));
        }
        return userDeviceDto;
    }
}
