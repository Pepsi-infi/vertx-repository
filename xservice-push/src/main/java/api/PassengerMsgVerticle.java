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
        router.route("/passengerMsg/add").handler(this::add);
        logger.info("PassengerMessageServiceImpl starting at 9000 ...");
        vertx.createHttpServer().requestHandler(router::accept).listen(9100);
    }

    private void list(RoutingContext context){
        JsonObject param = buildParams(context);
        passengerMessageService.list(param, resultHandler(context, JsonUtil::encodePrettily));
    }

    private void add(RoutingContext context){

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
