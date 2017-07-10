package api;

import helper.XProxyHelper;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import search.SearchService;
import service.impl.SearchServiceImpl;

public class EventBusVerticle extends BaseServiceVerticle {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private io.vertx.core.Vertx coreVertx;

    @Override
    public void start() throws Exception {
        super.start();
        logger.info("EventBusVerticle start");
        coreVertx = vertx.getDelegate();

        this.registerAndPublishService();
    }
    
    private void registerAndPublishService(){
//        XProxyHelper.registerService(LiveTpDao.class, coreVertx, new LiveTpDaoImpl(vertx, config()), LiveTpDao.SERVICE_ADDRESS);
//        publishEventBusService(LiveTpDao.SERVICE_NAME, LiveTpDao.SERVICE_ADDRESS, LiveTpDao.class);

        XProxyHelper.registerService(SearchService.class, coreVertx, new SearchServiceImpl(vertx), SearchService.SERVICE_ADDRESS);
        publishEventBusService(SearchService.SERVICE_NAME, SearchService.SERVICE_ADDRESS, SearchService.class);
    }
}
