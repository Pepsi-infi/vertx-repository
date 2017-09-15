//package api;
//
//import constant.RestConstants;
//import io.vertx.core.Future;
//import io.vertx.core.json.JsonObject;
//import io.vertx.core.logging.Logger;
//import io.vertx.core.logging.LoggerFactory;
//import io.vertx.rxjava.ext.web.Router;
//import io.vertx.rxjava.ext.web.RoutingContext;
//import io.vertx.rxjava.ext.web.handler.BodyHandler;
//import io.vertx.rxjava.ext.web.handler.CorsHandler;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import rxjava.RestAPIVerticle;
//import service.DriverService;
//import utils.IPUtil;
//
///**
// * Created by lufei
// * Date : 2017/7/25 11:40
// * Description :
// */
//public class RestPushVerticle extends RestAPIVerticle {
//    private static final Logger logger = LoggerFactory.getLogger(RestPushVerticle.class);
//
//    private DriverService driverService;
//
//
//    @Override
//    public void start() throws Exception {
//        super.start();
//
//        logger.info("Rest xservice-push verticle: Start...");
//
//        Router router = Router.router(vertx);
//        router.route().handler(CorsHandler.create("*"));
//        router.route().handler(BodyHandler.create());
//        router.route(RestConstants.DRIVER_QUERY).handler(this::queryDriver);
//        Future<Void> voidFuture = Future.future();
//
//        String serverHost = this.getServerHost();
//        createHttpServer(router, serverHost, RestConstants.HTTP_PORT).compose(
//                serverCreated -> publishHttpEndpoint(RestConstants.SERVICE_NAME, serverHost,
//                        RestConstants.HTTP_PORT, RestConstants.SERVICE_ROOT)).setHandler(
//                voidFuture.completer());
//
//        this.initService();
//    }
//
//    private void initService() {
//        driverService = DriverService.createProxy(vertx.getDelegate());
//    }
//
//    private String getServerHost() {
//        return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : "127.0.0.1";
//    }
//
//    private void queryDriver(RoutingContext context) {
//        JsonObject query = new JsonObject();
//        String driverName = context.request().params().get("driverName");
//        String driverPhone = context.request().params().get("driverPhone");
//        String cityId = context.request().params().get("cityId");
//        String supplierId = context.request().params().get("supplierId");
//        String page = context.request().params().get("page");
//        String size = context.request().params().get("size");
//        if (StringUtils.isNotBlank(driverName)) {
//            query.put("driverName", driverName);
//        }
//        if (StringUtils.isNotBlank(driverPhone)) {
//            query.put("driverPhone", driverPhone);
//        }
//        if (StringUtils.isNotBlank(cityId)) {
//            query.put("cityId", NumberUtils.toInt(cityId));
//        }
//        if (StringUtils.isNotBlank(supplierId)) {
//            query.put("supplierId", NumberUtils.toInt(supplierId));
//        }
//        driverService.queryDriver(query, NumberUtils.toInt(page), NumberUtils.toInt(size), resultStringHandler(context));
//    }
//
//
//}
