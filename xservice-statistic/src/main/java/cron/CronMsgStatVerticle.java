package cron;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import service.MsgStatResultService;

/**
 * Created by lufei
 * Date : 2017/8/4 11:24
 * Description :
 */
public class CronMsgStatVerticle extends BaseServiceVerticle {
    private static final Logger logger = LoggerFactory.getLogger(CronMsgStatVerticle.class);

    private MsgStatResultService msgStatResultService;

    public CronMsgStatVerticle() {

    }

    @Override
    public void start() throws Exception {
        super.start();

        msgStatResultService = MsgStatResultService.createProxy(vertx.getDelegate());

        vertx.setPeriodic(10000, handler -> {
            logger.info("msgStatResult timer run .....");
            msgStatResultService.storeMsgStatResult(asyncResult -> {
                if (asyncResult.succeeded()) {
                    logger.info("msgStatResult timer success. ");
                } else {
                    logger.error("msgStatResult timer error", asyncResult.cause());
                }
            });
        });
    }
}
