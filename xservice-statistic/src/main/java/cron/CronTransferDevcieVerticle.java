package cron;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import service.TransferDeviceService;

/**
 * Created by lufei
 * Date : 2017/8/10 11:05
 * Description :
 */
public class CronTransferDevcieVerticle extends BaseServiceVerticle {

    private static final Logger logger = LoggerFactory.getLogger(CronTransferDevcieVerticle.class);

    private TransferDeviceService transferDeviceService;

    public CronTransferDevcieVerticle() {

    }

    @Override
    public void start() throws Exception {
        super.start();

        transferDeviceService = TransferDeviceService.createProxy(vertx.getDelegate());

        vertx.setTimer(60000, handler -> {
            logger.info("transfer device timer run .....");
            transferDeviceService.transferDevice(asyncResult -> {
                if (asyncResult.succeeded()) {
                    logger.info("transfer device  timer success. ");
                } else {
                    logger.error("transfer device  timer error", asyncResult.cause());
                }
            });
        });
    }
}
