package statistic.api;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.lang.StringUtils;
import rxjava.RestAPIVerticle;
import utils.IPUtil;
import utils.JsonUtil;
import statistic.constants.PushActionEnum;
import statistic.service.MsgStatService;

/**
 * Created by lufei
 * Date : 2017/7/25 11:40
 * Description :
 */
public class RestMsgStatVerticle extends RestAPIVerticle {
    private static final Logger logger = LoggerFactory.getLogger(RestMsgStatVerticle.class);

    private MsgStatService msgStatService;

    @Override
    public void start() throws Exception {
        super.start();

        logger.info("Rest message stat verticle: Start...");

        Router router = Router.router(vertx);
        router.route(MsgRestConstants.Msg.MSG_SEND).handler(this::statMsgSend);
        router.route(MsgRestConstants.Msg.MSG_ARRIVE).handler(this::statMsgArrive);
        Future<Void> voidFuture = Future.future();

        String serverHost = this.getServerHost();
        createHttpServer(router, serverHost, MsgRestConstants.Msg.HTTP_PORT).compose(
                serverCreated -> publishHttpEndpoint(MsgRestConstants.Msg.SERVICE_NAME, serverHost,
                        MsgRestConstants.Msg.HTTP_PORT, MsgRestConstants.Msg.SERVICE_ROOT)).setHandler(
                voidFuture.completer());

        this.initMsgStatService();
    }

    private void initMsgStatService() {
        msgStatService = MsgStatService.createLocalProxy(vertx.getDelegate());
    }

    private String getServerHost() {
        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
    }

    private void statMsgSend(RoutingContext context) {
        String msgId = context.request().params().get("msgId");
        String osType = context.request().params().get("osType");

        logger.info("the request params : msgId : {},osType :{}", msgId, osType);

        msgStatService.statPushMsg(PushActionEnum.SEND.getType(), msgId, Integer.valueOf(osType
        ), resultHandler(context, JsonUtil::encodePrettily));
    }

    private void statMsgArrive(RoutingContext context) {
        String msgId = context.request().params().get("msgId");
        String osType = context.request().params().get("osType");

        logger.info("the request params : msgId : {},osType :{}", msgId, osType);

        msgStatService.statPushMsg(PushActionEnum.ARRIVE.getType(), msgId, Integer.valueOf(osType
        ), resultHandler(context, JsonUtil::encodePrettily));
    }


}
