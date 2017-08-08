package api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import iservice.MsgStatService;
import iservice.dto.MsgStatDto;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import rxjava.RestAPIVerticle;
import service.MsgStatResultService;
import utils.IPUtil;
import utils.JsonUtil;
import utils.TimeUtil;

import java.util.HashMap;
import java.util.List;
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
        router.post(StatRestConstants.Stat.PUSH_MSG_REPORT).handler(this::statPushMsg);
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
        List<MsgStatDto> msgStatDtos = buildMsgStatDto(context);
        logger.info("stat pushMsg,the msgStatDtos: {}", Json.encode(msgStatDtos));
        msgStatService.statPushMsg(msgStatDtos, resultStringHandler(context));
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


    private List<MsgStatDto> buildMsgStatDto(RoutingContext context) {
        String appCode = context.request().formAttributes().get("appCode");
        String osType = context.request().formAttributes().get("osType");
        String antFingerprint = context.request().formAttributes().get("antFingerprint");
        String msgList = context.request().formAttributes().get("msgList");

        if (StringUtils.isBlank(appCode) || StringUtils.isBlank(osType) || StringUtils.isBlank(msgList)) {
            paramBadRequest(context, "Param [appCode or osType or msgList] cannot be empty.");
        }
        List<MsgStatDto> msgStatDtos = Lists.newArrayList();
        List<HashMap> msgStatList = Json.decodeValue(msgList, List.class);
        for (HashMap msgStatMap : msgStatList) {
            MsgStatDto statDto = new MsgStatDto();
            if (StringUtils.isNotBlank(osType)) {
                statDto.setOsType(Integer.valueOf(osType));
            }
            if (StringUtils.isNotBlank(appCode)) {
                statDto.setAppCode(Integer.valueOf(appCode));
            }
            statDto.setAntFingerprint(antFingerprint);
            statDto.setAction(MapUtils.getInteger(msgStatMap, "action"));
            statDto.setMsgId(MapUtils.getString(msgStatMap, "msgId"));
            statDto.setChannel(MapUtils.getInteger(msgStatMap, "channel"));
            Long arriveTime = MapUtils.getLong(msgStatMap, "arriveTime");
            if (arriveTime != null) {
                statDto.setArriveTime(TimeUtil.timestamp2date(arriveTime));
            }
            msgStatDtos.add(statDto);
        }
        return msgStatDtos;
    }


}
