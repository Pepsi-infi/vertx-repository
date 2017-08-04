package api;

import com.google.common.collect.Maps;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import rxjava.RestAPIVerticle;
import iservice.dto.MsgStatDto;
import service.MsgStatResultService;
import utils.IPUtil;
import utils.JsonUtil;
import iservice.MsgStatService;
import java.util.Map;

/**
 * Created by lufei
 * Date : 2017/7/25 11:40
 * Description :
 */
public class RestMsgStatVerticle extends RestAPIVerticle {
    private static final Logger logger = LoggerFactory.getLogger(RestMsgStatVerticle.class);

    private MsgStatService msgStatService;

    private MsgStatResultService msgStatResultService;

    @Override
    public void start() throws Exception {
        super.start();

        logger.info("Rest message stat verticle: Start...");

        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("*"));
        router.route().handler(BodyHandler.create());
        router.route(StatRestConstants.Stat.PUSH_MSG_REPORT).handler(this::statPushMsg);
        router.route(StatRestConstants.Stat.QUERY_PUSH_MSG_STAT).handler(this::queryMsgStatResult);
        Future<Void> voidFuture = Future.future();

        String serverHost = this.getServerHost();
        createHttpServer(router, serverHost, StatRestConstants.Stat.HTTP_PORT).compose(
                serverCreated -> publishHttpEndpoint(StatRestConstants.Stat.SERVICE_NAME, serverHost,
                        StatRestConstants.Stat.HTTP_PORT, StatRestConstants.Stat.SERVICE_ROOT)).setHandler(
                voidFuture.completer());

        this.initMsgStatService();
    }

    private void initMsgStatService() {
        msgStatService = MsgStatService.createProxy(vertx.getDelegate());
        msgStatResultService = MsgStatResultService.createProxy(vertx.getDelegate());
    }

    private String getServerHost() {
        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
    }

    private void statPushMsg(RoutingContext context) {
        MsgStatDto statDto = buildMsgStatDto(context);
        logger.info("stat pushMsg,the statDto: {}", statDto);
        if (statDto.getAction() == null || statDto.getAction() < 0) {
            logger.error("the action is null for {} ", statDto);
            return;
        }
        msgStatService.statPushMsg(statDto, resultHandler(context, JsonUtil::encodePrettily));
    }

    private void queryMsgStatResult(RoutingContext context) {
        String msgId = context.request().params().get("msgId");
        String page = context.request().params().get("page");
        String size = context.request().params().get("size");
        logger.info("query msgStatResult msgId: {}，page: {},size:{}", msgId, page, size);
        Map<String, String> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(msgId)) {
            params.put("msgId", msgId);
        }
        msgStatResultService.queryMsgStatResult(params, NumberUtils.toInt(page), NumberUtils.toInt(size), resultHandler(context, JsonUtil::encodePrettily));

    }


    private MsgStatDto buildMsgStatDto(RoutingContext context) {
        String action = context.request().params().get("action");
        String appCode = context.request().params().get("appCode");
        String msgId = context.request().params().get("msgId");
        String osType = context.request().params().get("osType");
        String channel = context.request().params().get("channel");
        String sendTime = context.request().params().get("sendTime");
        String arriveTime = context.request().params().get("arriveTime");
        String imei = context.request().params().get("imei");
        String antFingerprint = context.request().params().get("antFingerprint");

        if (StringUtils.isBlank(action) || StringUtils.isBlank(msgId) || StringUtils.isBlank(channel) || StringUtils.isBlank(osType)) {
            badRequest(context, new Throwable("Param [action or msgId or channel or osType] cannot be empty."));
        }

        MsgStatDto statDto = new MsgStatDto();
        if (StringUtils.isNotBlank(action)) {
            statDto.setAction(Integer.valueOf(action));
        }
        if (StringUtils.isNotBlank(osType)) {
            statDto.setOsType(Integer.valueOf(osType));
        }
        if (StringUtils.isNotBlank(channel)) {
            statDto.setChannel(Integer.valueOf(channel));
        }
        if (StringUtils.isNotBlank(appCode)) {
            statDto.setAppCode(Integer.valueOf(appCode));
        }
        statDto.setMsgId(msgId);
        statDto.setSendTime(sendTime);
        statDto.setArriveTime(arriveTime);
        statDto.setImei(imei);
        statDto.setAntFingerprint(antFingerprint);
        return statDto;
    }


}
