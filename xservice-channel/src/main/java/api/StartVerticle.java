package api;

import inke.AnchorServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.LiveServiceImpl;
import service.impl.SearchServiceImpl;
import tp.cms.impl.CmsTpDaoImpl;
import tp.live.impl.LiveTpDaoImpl;
import xservice.BaseServiceVerticle;

/**
 * Created by zhushenghao1 on 17/5/2. channel服务的部署和发布
 */

public class StartVerticle extends BaseServiceVerticle {
    private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

    @Override
    public void start() throws Exception {
        super.start();
        // 提供EventBus服务
        deployEventBusService();

        // 提供其他非EventBus服务
        deployRestService();
    }

    private void deployEventBusService() {
        this.deployVerticle(SearchServiceImpl.class.getName());
        // this.deployVerticle(EventBusVerticle.class.getName());
    }

    private void deployRestService() {
        this.deployVerticle(CmsTpDaoImpl.class.getName());
        this.deployVerticle(LiveTpDaoImpl.class.getName());
        this.deployVerticle(LiveServiceImpl.class.getName());
        this.deployVerticle(RestTvLiveVerticle.class.getName());
        this.deployVerticle(RestMobileLiveVerticle.class.getName());
        this.deployVerticle(RestChannelVerticle.class.getName());
        this.deployVerticle(AnchorServiceImpl.class.getName());
    }

    public void deployVerticle(String verticleName) {
        Future<String> future = Future.future();
        future.setHandler(ar -> logger.info(ar.succeeded() ? "success:" + ar.result() : "failed:" + ar.cause()));
        vertx.deployVerticle(verticleName, readBossOpts().setConfig(config()), future.completer());
    }

    public static DeploymentOptions readBossOpts() {
        DeploymentOptions options = new DeploymentOptions();
        options.setInstances(Runtime.getRuntime().availableProcessors());
        return options;
    }

    public static DeploymentOptions readWorkerOpts() {
        DeploymentOptions options = new DeploymentOptions();
        options.setWorker(true);
        return options;
    }
}
