package tp.live;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import tp.live.request.PlayBillCurrentRequest;
import tp.live.request.ProgramWaterMarkRequest;
import tp.live.request.TheaterWaterMarkRequest;
import tp.live.response.PlayBillCurrentTpResponse;
import utils.IPUtil;

@ProxyGen
@VertxGen
public interface LiveTpDao {
    public static final String SERVICE_NAME = "tp.live.eb.service";

    public static final String SERVICE_ADDRESS = "tp-live-eb-service";
    
    public static final String LOCAL_SERVICE_NAME = "local.tp.live.eb.service";

    static LiveTpDao createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(LiveTpDao.class, vertx, LiveTpDao.SERVICE_ADDRESS);
    }
    
    static LiveTpDao createLocalProxy(Vertx vertx) {
        return ProxyHelper.createProxy(LiveTpDao.class, vertx, getLocalAddress());
    }
    
    static String getLocalAddress(){
        return new StringBuffer().append(IPUtil.getInnerIP()).append("-").append(SERVICE_ADDRESS).toString();
    }

    /**
     * 获取轮播数据
     * 
     * @param request
     * @param result
     */
    void getLunBoDataFromCloudPlatform(String searchUrl, Handler<AsyncResult<String>> result);

    /**
     * 获取云平台直播后台直播数据
     * 
     * @param request
     * @param result
     */
    void getZhiBoDataFromCloudPlatform(String searchUrl, Handler<AsyncResult<String>> result);

    /**
     * 获取LMS直播数据
     * 
     * @param request
     * @param result
     */
    void getZhiBoDataFromLMSBySearchUrl(String url, String sourceId, int offSet, int fetchSize, String splatid, Handler<AsyncResult<String>> result);

    /**
     * 获取LMS直播数据
     * 
     * @param request
     * @param result
     */
    void getProgramWaterMark(ProgramWaterMarkRequest request, Handler<AsyncResult<String>> result);

    void getTheaterWaterMark(TheaterWaterMarkRequest request, Handler<AsyncResult<String>> result);

    void getTpPlayBillCurrent(PlayBillCurrentRequest request, Handler<AsyncResult<PlayBillCurrentTpResponse>> result);
}
