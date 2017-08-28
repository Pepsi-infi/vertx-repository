package api;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import org.apache.commons.lang.StringUtils;
import rxjava.RestAPIVerticle;
import service.PassengerMessageService;
import util.DateUtil;
import utils.JsonUtil;

/**
 * Created by weim on 2017/8/24.
 */
public class PassengerMsgVerticle extends RestAPIVerticle {

    private static Logger logger = LoggerFactory.getLogger(PassengerMsgVerticle.class);

    private PassengerMessageService passengerMessageService;

    public void start() throws Exception {
        super.start();

        this.initServiceListening();

        this.initService();
    }

    private void initService(){
        passengerMessageService = PassengerMessageService.createProxy(vertx.getDelegate());
    }

    private void initServiceListening(){
        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("*"));
        router.route().handler(BodyHandler.create());
        router.route("/passengerMsg/list").handler(this::list);
        router.route("/passengerMsg/add").handler(this::add);
        router.route("/passengerMsg/get").handler(this::get);
        logger.info("PassengerMessageServiceImpl starting at 9000 ...");
        vertx.createHttpServer().requestHandler(router::accept).listen(9100);
    }

    private void list(RoutingContext context){
        JsonObject param = buildParams(context);
        passengerMessageService.list(param, resultHandler(context, JsonUtil::encodePrettily));
    }

    private void add(RoutingContext context){
        HttpServerRequest request = context.request();
        JsonArray params = new JsonArray();
        params.add(request.getParam("title"));
        params.add(request.getParam("content"));
        params.add(request.getParam("action"));
        params.add(request.getParam("msgCenterImgUrl"));
        params.add(request.getParam("inMsgCenter"));
        params.add(request.getParam("openUrl"));
        params.add(request.getParam("openType"));
        params.add(request.getParam("sendType"));
        params.add(request.getParam("status"));
        String expireTime = request.getParam("expireTime");
        params.add(DateUtil.getLocalDate(expireTime));
        String sendTime = request.getParam("sendTime");
        params.add(DateUtil.getLocalDate(sendTime));
        passengerMessageService.add(params, resultHandler(context));
    }

    private void get(RoutingContext context){
        String id = context.request().getParam("id");
        JsonObject param = new JsonObject().put("id", id);
        passengerMessageService.get(param, resultHandler(context));
    }


    private JsonObject buildParams(RoutingContext context){
        JsonObject param = new JsonObject();
        HttpServerRequest request = context.request();
        String title = request.getParam("title");
        String sendTime = request.getParam("sendTime");
        String pageS = request.getParam("page");
        String pageSizeS = request.getParam("pageSize");
        if(StringUtils.isNotBlank(title)){
            param.put("title",title);
        }
        if(StringUtils.isNotBlank(sendTime)){
            param.put("sendTime",sendTime);
        }
        //查分页数据
        int page = Integer.parseInt(pageS);
        int pageSize = Integer.parseInt(pageSizeS);
        int pageIndex = (page - 1) * pageSize;
        param.put("page",page);
        param.put("pageIndex",pageIndex);
        param.put("pageSize",pageSize);
        return param;
    }
}
