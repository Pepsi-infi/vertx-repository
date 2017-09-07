package api;

import constant.MsgHttpConsts;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import org.apache.commons.lang.StringUtils;
import rxjava.RestAPIVerticle;
import service.MessagePushService;
import service.PassengerService;
import util.HttpUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 乘客消息相关功能
 * Created by weim on 2017/8/24.
 */
public class PassengerMsgVerticle extends RestAPIVerticle {

    private static Logger logger = LoggerFactory.getLogger(PassengerMsgVerticle.class);

    PassengerService passengerService;

    MessagePushService messagePushService;

    public void start() throws Exception {
        super.start();

        this.initServiceListening();

        this.initService();
    }

    private void initService(){
        passengerService = PassengerService.createProxy(vertx.getDelegate());
        messagePushService = MessagePushService.createProxy(vertx.getDelegate());
    }

    private void initServiceListening(){
        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("*"));
        router.route().handler(BodyHandler.create());
        router.route(MsgHttpConsts.PASSENGERMSG_LIST).handler(this::list);
        router.route(MsgHttpConsts.PASSENGERMSG_ADDOREDIT).handler(this::addOrUpdate);
        router.route(MsgHttpConsts.PASSENGERMSG_GET).handler(this::get);
        router.route(MsgHttpConsts.PASSENGERMSG_DEL).handler(this::del);
        router.route(MsgHttpConsts.PASSENGERMSG_PUSH).handler(this::pushMsg);
        router.route(MsgHttpConsts.PASSENGERMSG_GET_IMPORTFILELIST).handler(this::getImportFileList);
        router.route(MsgHttpConsts.PASSENGERMSG_GET_CITYLIST).handler(this::getCityList);
        router.route(MsgHttpConsts.PASSENGERMSG_GET_FILEPAGE).handler(this::getImportFilePage);
        logger.info("PassengerServiceImpl starting at 8989 ...");
        vertx.createHttpServer().requestHandler(router::accept).listen(8989);
    }

    //推送消息
    private void pushMsg(RoutingContext context){
        String id = context.request().getParam("id");
        JsonObject param = new JsonObject().put("id", id);
        Future<String> future = Future.future();
        passengerService.getPushMsg(param, future);
        future.setHandler(res -> {
           if(res.succeeded()){
               String result = res.result();
               pushPassengerMsg(result);
           }else{
               logger.error(res.cause());
           }
        });
    }

    private void list(RoutingContext context){
        JsonObject param = buildParams(context);
        passengerService.list(param, resultHandler(context));
    }

    private void addOrUpdate(RoutingContext context){
        HttpServerRequest request = context.request();
        JsonObject param = new JsonObject();
        param.put("id", request.getParam("id"));
        param.put("title", request.getParam("title"));
        param.put("content", request.getParam("content"));
        param.put("action", StringUtils.isBlank(request.getParam("action")) ? null : request.getParam("action"));
        param.put("msgCenterImgUrl", request.getParam("msgCenterImgUrl"));
        param.put("inMsgCenter", request.getParam("inMsgCenter"));
        param.put("openUrl", request.getParam("openUrl"));
        param.put("openType", request.getParam("openType"));
        param.put("sendType", request.getParam("sendType"));
        param.put("status", request.getParam("status"));
        param.put("expireTime", request.getParam("expireTime"));
        param.put("sendTime", request.getParam("sendTime"));
        param.put("importFileId", request.getParam("importFileId"));
        param.put("cityIds", request.getParam("cityIds"));
        param.put("inputPhones", request.getParam("inputPhones"));
        passengerService.addOrUpdate(param, resultHandler(context));
    }

    private void get(RoutingContext context){
        String id = context.request().getParam("id");
        JsonObject param = new JsonObject().put("id", id);
        passengerService.get(param, resultHandler(context));
    }

    private void del(RoutingContext context){
        String id = context.request().getParam("id");
        JsonObject param = new JsonObject().put("id", id);
        passengerService.del(param, resultHandler(context));
    }

    private JsonObject buildParams(RoutingContext context){
        JsonObject param = new JsonObject();
        HttpServerRequest request = context.request();
        MultiMap map = request.params();
        for(String name : map.names()){
            param.put(name ,map.get(name));
        }
        setPageParams(request, param);
        return param;
    }

    private void setPageParams(HttpServerRequest request, JsonObject param){
        //查分页数据
        String pageS = request.getParam("page");
        String pageSizeS = request.getParam("pageSize");
        int page = Integer.parseInt(pageS);
        int pageSize = Integer.parseInt(pageSizeS);
        int pageIndex = (page - 1) * pageSize;
        param.put("page",page);
        param.put("pageIndex",pageIndex);
        param.put("pageSize",pageSize);
    }

    private void pushPassengerMsg(String jsonMsg){
        if(StringUtils.isNotBlank(jsonMsg)) {
            JsonObject message = new JsonObject(jsonMsg);
            message.put("msgId", message.getValue("id"));
            message.put("jumpPage", message.getValue("action"));
            message.put("isIntoPsnCenter", message.getValue("inMsgCenter"));
            Optional<Long> sendTime = Optional.of(message.getLong("sendTime"));
            message.put("sendTime", sendTime.get());
            Optional<Long> expireTime = Optional.of(message.getLong("expireTime"));
            message.put("expireTime", expireTime.get());
            message.put("url", message.getValue("openUrl"));
            message.put("type", message.getValue("openType"));
            String sendType = message.getString("sendType");
            if(StringUtils.isNotBlank(sendType) && "1".equals(sendType)){
                logger.info("全部用户推送消息");
                sendForAllUser(message);
            }else if(StringUtils.isNotBlank(sendType) && "2".equals(sendType)){
                logger.info("指定用户推送消息");
                sendByOnlyUser(message);
            }else if(StringUtils.isNotBlank(sendType) && "3".equals(sendType)){
                logger.info("指定城市推送消息");
                sendForCityUser(message);
            }else{
                logger.error("发送类型未指定，推送不执行");
            }
        }else {
            logger.error("无符合条件的消息数据，推送不执行");
        }
    }

    //1、推送所有用户
    private void sendForAllUser(JsonObject message){
        message.put("phone", "13621241006");
        message.put("customerId", 13666050);
    }

//    2、按指定用户推送
//    private void sendByOnlyUser(JsonObject message){
//        String importFileId = message.getString("importFileId");
//        Future<List<JsonObject>> future = Future.future();
//        passengerService.getImportPhoneList(importFileId, future);
//        future.setHandler(res -> {
//            if (res.succeeded()) {
//                List<JsonObject> phoneList = res.result();
//                if (CollectionUtils.isNotEmpty(phoneList)) {
//                    for (JsonObject phone : phoneList) {
//                        message.put("phone", phone.getString("telephone"));
//                        message.put("customerId", 13666050);
//                        sendMessage(message);
//                    }
//                } else {
//                    logger.info("查询指定手机号列表为空，importFileId：" + importFileId);
//                }
//            } else {
//                logger.info("查询指定手机号列表为空失败，importFileId：" + importFileId);
//            }
//        });
//    }

    //2、按指定用户推送
    private void sendByOnlyUser(JsonObject message){
        try {
            //新的指定用户，是把手机号以字符串的形式存到了数据库中
            String inputPhones = message.getString("inputPhones");
            String[] phones = inputPhones.split(",");
            if (phones != null && phones.length > 0) {
                for (String phone : phones) {
                    message.put("phone", phone);
                    sendMessage(message);
                }
            }
        }catch (Exception e){
            logger.error("指定用户推送出错：" + e.getMessage());
        }
    }
    //3、按城市推送
    private void sendForCityUser(JsonObject message){
        message.put("phone", "13621241006");
        message.put("customerId", 13666050);
    }

    private void sendMessage(JsonObject message){
        JsonObject buildMessage = new JsonObject();
        buildMessage.put("body", message.toString());
        Future<String> future = Future.future();
        //调用发送消息
        messagePushService.bisnessMessage(buildMessage.toString(), future);
        future.setHandler(resPush -> {
            if (resPush.succeeded()) {
                logger.info(resPush.result());
            } else {
                logger.error(resPush.cause());
            }
        });
    }
    //查询导入的手机号文件列表
    private void getImportFileList(RoutingContext context){
        JsonObject param = new JsonObject();
        HttpServerRequest request = context.request();
        String createTime = request.getParam("createTime");
        param.put("createTime", createTime);
        passengerService.getImportFileList(param, resultHandler(context));
    }

    private void getCityList(RoutingContext context){
        Map params = new HashMap();
        logger.info("调用城市列表接口");
        HttpUtil.doGet(params, config().getString("city.list.url"), resultHandler(context));
    }

    private void getImportFilePage(RoutingContext context){
        JsonObject param = new JsonObject();
        HttpServerRequest request = context.request();
        String createTime = request.getParam("createTime");
        param.put("createTime", createTime);
        setPageParams(request, param);
        passengerService.getImportFilePage(param, resultHandler(context));
    }
}
