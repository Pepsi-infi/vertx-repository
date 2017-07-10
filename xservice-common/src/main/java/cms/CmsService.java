package cms;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import cms.response.CmsBlockTpResponse;
import cms.response.CmsPageTpResponse;

/**
 * 暂时不对外提供eventbus服务
 */
@VertxGen
@ProxyGen
public interface CmsService {

    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "cms-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "service.cms";

    /**
     * 获取cms的版块数据
     * @return
     */
    public void getCmsBlock(String uuid, String blockId, Handler<AsyncResult<CmsBlockTpResponse>> resultHandler);

    public void getTvCmsBlock(String uuid, String cmsPageId, String langCode,
            Handler<AsyncResult<CmsPageTpResponse>> resultHandler);
}
