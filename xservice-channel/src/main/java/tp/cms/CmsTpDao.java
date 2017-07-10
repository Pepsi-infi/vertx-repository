package tp.cms;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import tp.cms.request.CmsPageRequest;
import tp.cms.request.ColumnListRequest;
import tp.cms.response.CmsMutilangDataResponse;
import utils.IPUtil;

/**
 * Created by zhushenghao1 on 17/1/23.
 */

@VertxGen
@ProxyGen
public interface CmsTpDao {

    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "cms-tp-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "cms.tp.search";

    String LOCAL_SERVICE_NAME = "local-cms-tp-service";

    static CmsTpDao createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(CmsTpDao.class, vertx, CmsTpDao.SERVICE_ADDRESS);
    }

    static CmsTpDao createLocalProxy(Vertx vertx) {
        return ProxyHelper.createProxy(CmsTpDao.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
                new DeliveryOptions().setSendTimeout(3000));
    }

    static String getLocalAddress(String ip) {
        return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
    }

    void getCmsBlockForLang(String blockId, String langCode, Handler<AsyncResult<CmsMutilangDataResponse>> resultHandler);

    // 获取CMS"页面"数据
    void getCMSDataByPageId(long uuid, String platform, String pageId, String langCode, Handler<AsyncResult<String>> result);

    // 根据内容id获取一级栏目下的内容
    void getCMSColumnList(String columnId, String langCode, String platform, Handler<AsyncResult<String>> result);
}
