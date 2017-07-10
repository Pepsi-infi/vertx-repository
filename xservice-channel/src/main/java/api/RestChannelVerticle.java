package api;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import rxjava.RestAPIVerticle;
import search.request.LecomSearchRequest;
import service.ChannelService;
import service.impl.ChannelServiceImpl;
import service.param.CommonParam;
import utils.IPUtil;
import utils.JsonUtil;

/**
 * Created by zhushenghao1 on 17/5/2. channel服务 api接口的定义和发布
 */
public class RestChannelVerticle extends RestAPIVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestChannelVerticle.class);

    private static ChannelService channelService;

    @Override
    public void start() throws Exception {
        super.start();
        LOGGER.info("RestChannelVerticle:start");
        Router router = Router.router(vertx);
        router.route(this.getRoutePath(RestConstants.SHORT_CUT)).handler(this::getShortCut);
        router.route(this.getRoutePath(RestConstants.ADDON_PAGE)).handler(this::addOnPage);
        router.route(this.getRoutePath(RestConstants.HOME_PAGE)).handler(this::homePage);
        router.route(this.getRoutePath(RestConstants.CHANNEL_DATA)).handler(this::pageData);
        router.route(this.getRoutePath(RestConstants.CATEGORY_LIST)).handler(this::categoryPage);
        ;
        router.route(this.getRoutePath(RestConstants.SEARCH_DATA)).handler(this::searchData);

        String serverHost = this.getServerHost();
        int port = config().getInteger("service.port");
        Future<Void> voidFuture = Future.future();
        createHttpServer(router, serverHost, port).compose(
                serverCreated -> publishHttpEndpoint(config().getString("service.name"), serverHost, port, config()
                        .getString("service.root"))).setHandler(voidFuture.completer());

        this.initChannelService();
    }

    private void initChannelService() {
        if (channelService == null) {
            synchronized (this) {
                if (channelService == null) {
                    channelService = new ChannelServiceImpl(vertx, config());
                }
            }
        }
    }

    private String getServerHost() {
        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
    }

    private String getRoutePath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(config().getString("service.root"));
        builder.append(path);
        return builder.toString();
    }

    private void getShortCut(RoutingContext context) {
        CommonParam param = new CommonParam();
        MultiMap params = context.request().params();
        param.setTerminalApplication(params.get("terminalApplication"));
        param.setImeiArea(params.get("imeiArea"));
        param.setLangcode(params.get("langcode"));
        param.setUid(params.get("uid"));
        param.setIp(context.request().remoteAddress().host());
        channelService.getShortCut(param, resultHandler(context, JsonUtil::encodePrettily));
    }

    private void addOnPage(RoutingContext context) {
        CommonParam param = new CommonParam();
        MultiMap params = context.request().params();
        param.setLangcode(params.get("langcode"));
        param.setUid(params.get("uid"));
        param.setImeiArea(params.get("imeiArea"));
        param.setCountryArea(params.get("countryArea"));
        param.setIp(context.request().remoteAddress().host());
        channelService.addOnPage(NumberUtils.toInt(params.get("addonid"), -1),
                NumberUtils.toInt(params.get("productid"), -1), NumberUtils.toInt(params.get("page"), -1),
                NumberUtils.toInt(params.get("pageSize"), -1), param, resultHandler(context, JsonUtil::encodePrettily));
    }

    private void homePage(RoutingContext context) {
        CommonParam param = new CommonParam();
        MultiMap params = context.request().params();
        param.setTerminalApplication(params.get("terminalApplication"));
        param.setUid(params.get("uid"));
        param.setLangcode(params.get("langcode"));
        param.setImeiArea(params.get("imeiArea"));
        param.setDevId(params.get("devId"));
        param.setCountryArea(params.get("countryArea"));
        param.setIp(context.request().remoteAddress().host());
        channelService.homePage(params.get("history"), params.get("cityLevel"), param,
                resultHandler(context, JsonUtil::encodePrettily));
    }

    private void pageData(RoutingContext context) {
        CommonParam param = new CommonParam();
        MultiMap params = context.request().params();

        param.setUid(params.get("uid"));
        param.setTerminalApplication(params.get("terminalApplication"));
        param.setLangcode(params.get("langcode"));
        param.setImeiArea(params.get("imeiArea"));
        param.setDevId(params.get("devId"));
        param.setCountryArea(params.get("countryArea"));
        param.setIp(context.request().remoteAddress().host());

        channelService.pageData(params.get("pageId"), params.get("history"), params.get("cityLevel"), param,
                resultHandler(context, JsonUtil::encodePrettily));
    }

    private void categoryPage(RoutingContext context) {
        CommonParam param = new CommonParam();
        MultiMap params = context.request().params();
        channelService.getCategoryPage(params.get("channelCode"), param,
                resultHandler(context, JsonUtil::encodePrettily));
    }

    private void searchData(RoutingContext context) {
        MultiMap params = context.request().params();

        String filter = params.get("filter");
        Integer cid = NumberUtils.toInt(params.get("cid"), -1);
        String ph = params.get("ph") != null ? params.get("ph")
                : "lecom".equals(params.get("terminalApplication")) ? "420003,420004,420020" : "";
        String dt = params.get("dt");
        Integer page = NumberUtils.toInt(params.get("page"), -1);
        Integer pageSize = NumberUtils.toInt(params.get("pageSize"), -1);

        CommonParam param = new CommonParam();
        param.setTerminalApplication(params.get("terminalApplication"));
        param.setImeiArea(params.get("imeiArea"));
        param.setCountryArea(params.get("countryArea"));
        param.setLangcode(params.get("langcode"));
        param.setDevId(params.get("devId"));
        param.setIp(context.request().remoteAddress().host());

        LecomSearchRequest searchRequest = new LecomSearchRequest();
        searchRequest.setSales_area(param.getImeiArea());
        searchRequest.setUser_setting_country(param.getCountryArea());
        searchRequest.setPn(page);
        searchRequest.setPs(pageSize <= 100 ? pageSize : 100);
        searchRequest.setExtraParam(filter);
        searchRequest.setCg(cid);
        searchRequest.setPh(ph);
        searchRequest.setDt(dt);
        searchRequest.setLang(param.getLangcode());
        searchRequest.setRegion(param.getWcode());
        searchRequest.setLc(param.getDevId());
        String clientIp = param.getIp();
        searchRequest.setClient_ip(clientIp);
        // TODO
        // if (StringUtils.isNotBlank(clientIp)) {
        // String city_info = IPUtil.getCityInfo(clientIp);
        // searchRequest.setCity_info(city_info);
        // }

        channelService.searchData(searchRequest, param, resultHandler(context, JsonUtil::encodePrettily));
    }
}
