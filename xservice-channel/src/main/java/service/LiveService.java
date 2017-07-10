package service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import service.dto.cmsPage.CmsPageWrapper;
import service.dto.theaterpack.TheaterPackedWapper;
import service.param.LiveCommonParam;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface LiveService {
    public static final String SERVICE_NAME = "http.live.eb.service";

    public static final String SERVICE_ADDRESS = "http-live-eb-service";

    public static final String LOCAL_SERVICE_NAME = "local.http.live.eb.service";

    static LiveService createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(LiveService.class, vertx, LiveService.SERVICE_ADDRESS);
    }

    static LiveService createLocalProxy(Vertx vertx) {
        return ProxyHelper.createProxy(LiveService.class, vertx, getLocalAddress(),
                new DeliveryOptions().setSendTimeout(3000));
    }

    static String getLocalAddress() {
        return new StringBuffer().append(IPUtil.getInnerIP()).append("-").append(SERVICE_ADDRESS).toString();
    }

    /*
     * 根据pageid获取页面数据
     */
    void getProgramListByPageId(long uuid, String pageid, String columnid, String langCode,
            Handler<AsyncResult<CmsPageWrapper>> result);

    void getTheaterpackWaterMark(String playbillId, String channelId, LiveCommonParam commonParam, String date,
            Handler<AsyncResult<TheaterPackedWapper>> resultHandler);

}
