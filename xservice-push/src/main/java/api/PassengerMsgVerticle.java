package api;

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
        router.route("/passengerMsg/addOrEdit").handler(this::addOrUpdate);
        router.route("/passengerMsg/get").handler(this::get);
        router.route("/passengerMsg/del").handler(this::del);
        logger.info("PassengerMessageServiceImpl starting at 9000 ...");
        vertx.createHttpServer().requestHandler(router::accept).listen(9100);
    }

    private void push(RoutingContext context){
        String id = context.request().getParam("id");
        JsonObject param = new JsonObject().put("id", id);
        passengerMessageService.get(param, resultHandler(context));
    }

    private void list(RoutingContext context){
        JsonObject param = buildParams(context);
        passengerMessageService.list(param, resultHandler(context, JsonUtil::encodePrettily));
    }

    private void addOrUpdate(RoutingContext context){
        HttpServerRequest request = context.request();
        JsonObject param = new JsonObject();
        param.put("id", request.getParam("id"));
        param.put("title", request.getParam("title"));
        param.put("content", request.getParam("content"));
        param.put("action", request.getParam("action"));
        param.put("msgCenterImgUrl", request.getParam("msgCenterImgUrl"));
        param.put("inMsgCenter", request.getParam("inMsgCenter"));
        param.put("openUrl", request.getParam("openUrl"));
        param.put("openType", request.getParam("openType"));
        param.put("sendType", request.getParam("sendType"));
        param.put("status", request.getParam("status"));
        param.put("expireTime", request.getParam("expireTime"));
        param.put("sendTime", request.getParam("sendTime"));
        passengerMessageService.addOrUpdate(param, resultHandler(context));
    }

    private void get(RoutingContext context){
        String id = context.request().getParam("id");
        JsonObject param = new JsonObject().put("id", id);
        passengerMessageService.get(param, resultHandler(context));
    }

    private void del(RoutingContext context){
        String id = context.request().getParam("id");
        JsonObject param = new JsonObject().put("id", id);
        passengerMessageService.del(param, resultHandler(context));
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
