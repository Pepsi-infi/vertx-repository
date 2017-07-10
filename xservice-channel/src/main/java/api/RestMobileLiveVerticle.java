package api;

import api.LiveRestConstants.MOBILE;
import inke.AnchorService;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.Json;
import io.vertx.rxjava.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import org.apache.commons.lang.StringUtils;

import constants.LiveConstants;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.lang.math.NumberUtils;
import rxjava.RestAPIVerticle;
import service.LiveService;
import service.impl.LiveServiceImpl;
import utils.IPUtil;
import utils.JsonUtil;
import utils.SystemCache;

public class RestMobileLiveVerticle extends RestAPIVerticle {
    private static final Logger logger = LoggerFactory.getLogger(RestMobileLiveVerticle.class);

    private LiveService liveService;// Don't use static!

    private AnchorService anchorService;

    @Override
    public void start() throws Exception {
        super.start();

        logger.info("Rest Mobile Live Verticle: Start...");

        Router router = Router.router(vertx);
        router.route(LiveRestConstants.MOBILE.PROGRAM_LIST).handler(this::getProgramList);
        router.route(MOBILE.ANCHOR_LIST).handler(this::getOnlineAnchorList);
        Future<Void> voidFuture = Future.future();

        String serverHost = this.getServerHost();
        createHttpServer(router, serverHost, LiveRestConstants.MOBILE.HTTP_PORT)
                .compose(serverCreated -> publishHttpEndpoint(LiveRestConstants.MOBILE.SERVICE_NAME, serverHost,
                        LiveRestConstants.MOBILE.HTTP_PORT, LiveRestConstants.MOBILE.SERVICE_ROOT))
                .setHandler(voidFuture.completer());

        this.initLiveService();
    }

    private void initLiveService() {
        if (liveService == null) {
            liveService = new LiveServiceImpl();
        }

        anchorService = ProxyHelper.createProxy(AnchorService.class, vertx.getDelegate(),
                AnchorService.SERVICE_ADDRESS + IPUtil.getInnerIP(), new DeliveryOptions().setSendTimeout(3000));
    }

    private String getServerHost() {
        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
    }

    private void getProgramList(RoutingContext context) {
        // LiveCommonParam commonParam =
        // ChannelUtil.tramsformCommomParams(context);
        String pageid = context.request().params().get("pageId");
        String columnld = context.request().params().get("columnId");
        String langCode = context.request().params().get("langcode");
        long uuid = SystemCache.currentTimeMillis;
        if (StringUtils.isBlank(pageid)) {
            pageid = LiveConstants.DEFAULT_CMS_MOBILE_PAGE;
        }
        liveService.getProgramListByPageId(uuid, pageid, columnld, langCode,
                resultHandler(context, JsonUtil::encodePrettily));
    }

    private void getOnlineAnchorList(RoutingContext context) {
        int index = NumberUtils.toInt(context.request().params().get("page"), 1);
        String uuid = context.request().params().get("Kong-Request-ID");
        anchorService.getOnlineAnchorList(index, uuid, resultHandler(context, Json::encode));
    }

}
