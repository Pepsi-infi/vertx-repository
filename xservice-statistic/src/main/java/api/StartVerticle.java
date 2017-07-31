package api;

import dao.impl.MsgStatResultDaoImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import dao.impl.DeviceDaoImpl;
import service.impl.MsgStatResultServiceImpl;
import service.impl.MsgStatServiceImpl;
import service.impl.DeviceServiceImpl;
import xservice.BaseServiceVerticle;

/**
 * Created by lufei
 * Date : 2017/7/25 11:36
 * Description :
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


    private void deployRestService() {
        this.deployVerticle(DeviceDaoImpl.class.getName());
        this.deployVerticle(DeviceServiceImpl.class.getName());
        this.deployVerticle(RestDeviceVerticle.class.getName());
        this.deployVerticle(MsgStatServiceImpl.class.getName());
        this.deployVerticle(RestMsgStatVerticle.class.getName());
    }

    private void deployEventBusService() {
        this.deployVerticle(MsgStatResultDaoImpl.class.getName());
        this.deployVerticle(MsgStatResultServiceImpl.class.getName());
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
