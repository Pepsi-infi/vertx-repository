package api;

import cron.CronMsgStatVerticle;
import dao.impl.CarBizEuroDaoImpl;
import dao.impl.DeviceDaoImpl;
import dao.impl.MsgStatResultDaoImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.DeviceServiceImpl;
import service.impl.MsgStatResultServiceImpl;
import service.impl.MsgStatServiceImpl;
import service.impl.TransferDeviceServiceImpl;
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

        this.deployVerticle(MsgStatResultDaoImpl.class.getName());
        this.deployVerticle(MsgStatResultServiceImpl.class.getName());

        this.deployVerticle(CarBizEuroDaoImpl.class.getName());
        this.deployVerticle(TransferDeviceServiceImpl.class.getName());

    }

    private void deployEventBusService() {
        this.deployCronVerticle(CronMsgStatVerticle.class.getName());
//        this.deployCronVerticle(CronTransferDevcieVerticle.class.getName());
    }

    public void deployVerticle(String verticleName) {
        Future<String> future = Future.future();
        future.setHandler(ar -> logger.info(ar.succeeded() ? "success:" + ar.result() : "failed:" + ar.cause()));
        vertx.deployVerticle(verticleName, readBossOpts().setConfig(config()), future.completer());
    }

    public void deployCronVerticle(String verticleName) {
        Future<String> future = Future.future();
        future.setHandler(ar -> logger.info(ar.succeeded() ? "success:" + ar.result() : "failed:" + ar.cause()));
        vertx.deployVerticle(verticleName, new DeploymentOptions().setConfig(config()), future.completer());
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
