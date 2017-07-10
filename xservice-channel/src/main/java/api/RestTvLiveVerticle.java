package api;

import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import rxjava.RestAPIVerticle;
import service.CurrentProgramService;
import service.LiveService;
import service.impl.CurrentProgramServiceImpl;
import service.param.LiveCommonParam;
import util.ChannelUtil;
import utils.IPUtil;
import utils.JsonUtil;
import constants.LiveConstants;
import utils.SystemCache;

public class RestTvLiveVerticle extends RestAPIVerticle {
    private static final Logger logger = LoggerFactory.getLogger(RestTvLiveVerticle.class);

    private CurrentProgramService currentProgramService;

    private LiveService liveService;

    @Override
    public void start() throws Exception {
        super.start();

        logger.info("Rest TV Live Verticle: Start...");

        Router router = Router.router(vertx);
        router.route(LiveRestConstants.TV.PROGRAM_LIST).handler(this::getProgramList);
        router.route(LiveRestConstants.TV.THEATERPACKED_GET).handler(this::getWaterMark);
        router.route(LiveRestConstants.TV.CHANNEL_CURRENT_PROGRAM).handler(this::getCurrentProgram);
        Future<Void> voidFuture = Future.future();

        String serverHost = this.getServerHost();
        createHttpServer(router, serverHost, LiveRestConstants.TV.HTTP_PORT).compose(
                serverCreated -> publishHttpEndpoint(LiveRestConstants.TV.SERVICE_NAME, serverHost,
                        LiveRestConstants.TV.HTTP_PORT, LiveRestConstants.TV.SERVICE_ROOT)).setHandler(
                voidFuture.completer());

        this.initLiveService();
    }

    private void initLiveService() {
        currentProgramService = new CurrentProgramServiceImpl(vertx);
        liveService = LiveService.createLocalProxy(vertx.getDelegate());
    }

    private String getServerHost() {
        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
    }

    private void getProgramList(RoutingContext context) {
        long uuid = SystemCache.currentTimeMillis;
//        LiveCommonParam commonParam = ChannelUtil.tramsformCommomParams(context);
        String pageId = context.request().params().get("pageId");
        String columnld = context.request().params().get("columnId");
        String langCode = context.request().params().get("langcode");
        if (StringUtils.isBlank(pageId)) {
            pageId = LiveConstants.DEFAULT_CMS_TV_PAGE;
        }

        liveService.getProgramListByPageId(uuid, pageId, columnld, langCode,
                resultHandler(context, JsonUtil::encodePrettily));
    }

    private void getWaterMark(RoutingContext context) {
        LiveCommonParam commonParam = ChannelUtil.tramsformCommomParams(context);
        String playbillId = context.request().params().get("playbillId");
        String channelId = context.request().params().get("channelId");
        String date = context.request().params().get("date");
        liveService.getTheaterpackWaterMark(playbillId, channelId, commonParam, date,
                resultHandler(context, JsonUtil::encodePrettily));
    }

    private void getCurrentProgram(RoutingContext context) {
        String ids = context.request().getParam("channelIds");

        if (StringUtils.isEmpty(ids)) {
            badRequest(context, new Throwable("Param [channelIds] cannot be empty."));
        }
        List<String> channelIds = Arrays.asList(ids.split(","));

        int clientId = 1036;

        currentProgramService.getChannelCurrentProgram(channelIds, clientId,
                resultHandler(context, Json::encodePrettily));
    }
}
