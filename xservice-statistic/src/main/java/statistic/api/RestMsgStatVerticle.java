package statistic.api;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.lang.StringUtils;
import rxjava.RestAPIVerticle;
import statistic.service.dto.MsgStatDto;
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
        router.route(StatRestConstants.Stat.MSG_SEND).handler(this::statMsgSend);
        router.route(StatRestConstants.Stat.MSG_ARRIVE).handler(this::statMsgArrive);
        Future<Void> voidFuture = Future.future();

        String serverHost = this.getServerHost();
        createHttpServer(router, serverHost, StatRestConstants.Stat.HTTP_PORT).compose(
                serverCreated -> publishHttpEndpoint(StatRestConstants.Stat.SERVICE_NAME, serverHost,
                        StatRestConstants.Stat.HTTP_PORT, StatRestConstants.Stat.SERVICE_ROOT)).setHandler(
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
        MsgStatDto statDto = buildMsgStatDto(context);
        statDto.setAction(PushActionEnum.SEND.getType());
        logger.info("the request params , msgStat: {}", statDto);
        msgStatService.statPushMsg(statDto, resultHandler(context, JsonUtil::encodePrettily));
    }

    private void statMsgArrive(RoutingContext context) {
        MsgStatDto statDto = buildMsgStatDto(context);
        statDto.setAction(PushActionEnum.ARRIVE.getType());
        logger.info("the request params , msgStat: {}", statDto);
        msgStatService.statPushMsg(statDto, resultHandler(context, JsonUtil::encodePrettily));
    }

    private MsgStatDto buildMsgStatDto(RoutingContext context) {
        String msgId = context.request().params().get("msgId");
        String osType = context.request().params().get("osType");
        String channel = context.request().params().get("channel");
        String sendTime = context.request().params().get("sendTime");

        MsgStatDto statDto = new MsgStatDto();
        if (StringUtils.isNotBlank(osType)) {
            statDto.setOsType(Integer.valueOf(osType));
        }
        if (StringUtils.isNotBlank(channel)) {
            statDto.setChannel(Integer.valueOf(channel));
        }
        statDto.setMsgId(msgId);
        statDto.setSendTime(sendTime);
        return statDto;
    }


}
