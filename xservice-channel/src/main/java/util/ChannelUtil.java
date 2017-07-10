package util;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.RoutingContext;
import service.param.LiveCommonParam;

public class ChannelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelUtil.class);

    public static LiveCommonParam tramsformCommomParams(RoutingContext context) {
        LiveCommonParam commonParam = new LiveCommonParam();
        commonParam.setTerminalSeries(context.request().params().get("terminalSeries"));
        commonParam.setBsChannel(context.request().params().get("bsChannel"));
        commonParam.setTerminalApplication(context.request().params().get("terminalApplication"));
        commonParam.setLangcode(context.request().params().get("langcode"));
        commonParam.setWcode(context.request().params().get("wcode"));
        commonParam.setTerminalBrand(context.request().params().get("terminalBrand"));
        commonParam.setAppVersion(context.request().params().get("appVersion"));
        commonParam.setPcode(context.request().params().get("pcode"));
        commonParam.setDevId(context.request().params().get("devId"));
        commonParam.setUid(context.request().params().get("uid"));
        commonParam.setToken(context.request().params().get("token"));
        commonParam.setCountryArea(context.request().params().get("countryArea"));
        commonParam.setMac(context.request().params().get("mac"));
        commonParam.setSalesArea(context.request().params().get("salesArea"));
        return commonParam;
    }

}
