package api;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import iservice.DeviceService;
import iservice.dto.DeviceDto;
import org.apache.commons.lang.StringUtils;
import rxjava.RestAPIVerticle;
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
        router.route().handler(BodyHandler.create());
        router.post(StatRestConstants.Device.DEVICE_REPORT).handler(this::reportUserDevice);
        Future<Void> voidFuture = Future.future();

        String serverHost = this.getServerHost();
        createHttpServer(router, serverHost, StatRestConstants.Device.HTTP_PORT).compose(
                serverCreated -> publishHttpEndpoint(StatRestConstants.Stat.SERVICE_NAME, serverHost,
                        StatRestConstants.Device.HTTP_PORT, StatRestConstants.Stat.SERVICE_ROOT)).setHandler(
                voidFuture.completer());

        this.initUserDeviceService();
    }

    private void initUserDeviceService() {
        deviceService = DeviceService.createProxy(vertx.getDelegate());
    }

    private String getServerHost() {
        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
    }

    private void reportUserDevice(RoutingContext context) {
        DeviceDto userDeviceDto = buildDeviceDto(context);
        logger.info("the request params : userDeviceDto : {}", userDeviceDto);
        deviceService.reportDevice(userDeviceDto, resultHandler(context, JsonUtil::encodePrettily));
    }

    /**
     * @param context
     * @return
     */
    private DeviceDto buildDeviceDto(RoutingContext context) {
        DeviceDto userDeviceDto = new DeviceDto();
        String uid = context.request().formAttributes().get("uid");
        String phone = context.request().formAttributes().get("phone");
        String deviceType = context.request().formAttributes().get("deviceType");
        String miToken = context.request().formAttributes().get("miToken");
        String gcmToken = context.request().formAttributes().get("gcmToken");
        String apnsToken = context.request().formAttributes().get("apnsToken");
        String imei = context.request().formAttributes().get("imei");
        String osType = context.request().formAttributes().get("osType");
        String osVersion = context.request().formAttributes().get("osVersion");
        String appCode = context.request().formAttributes().get("appCode");
        String appVersion = context.request().formAttributes().get("appVersion");
        String antFingerprint = context.request().formAttributes().get("antFingerprint");
        String isAcceptPush = context.request().formAttributes().get("isAcceptPush");

        if (StringUtils.isBlank(antFingerprint) || StringUtils.isBlank(osType) || StringUtils.isBlank(appCode)
                || StringUtils.isBlank(appVersion)) {
            badRequest(context, new Throwable("Param [antFingerprint or osType or appCode or appVersion] cannot be empty."));
        }

        userDeviceDto.setMiToken(miToken);
        userDeviceDto.setGcmToken(gcmToken);
        userDeviceDto.setApnsToken(apnsToken);
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
        if (StringUtils.isNotBlank(isAcceptPush)) {
            userDeviceDto.setIsAcceptPush(Integer.valueOf(isAcceptPush));
        }
        return userDeviceDto;
    }
}
