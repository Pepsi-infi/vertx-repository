package service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import result.ResultData;
import service.AdMessagePushService;
import service.NonAdMessagePushService;
import service.PassengerUnSendService;
import util.DateUtil;
import xservice.BaseServiceVerticle;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by weim on 2017/8/22.
 */
public class PassengerUnSendServiceImpl extends BaseServiceVerticle implements PassengerUnSendService {

    private static Logger logger = LoggerFactory.getLogger(PassengerUnSendServiceImpl.class);

    private SQLClient sqlClient;

    private AdMessagePushService adMessagePushService;

    private NonAdMessagePushService nonAdMessagePushService;
    //乘客消息列表
    public static final String SQL_UNSEND_GET = "select * from msg_passenger_unsend where expireTime > now() %s ";
    public static final String SQL_UNSEND_EXPIREDEL = "delete from msg_passenger_unsend where expireTime < now() ";
    public static final String SQL_UNSEND_ADD = "insert into msg_passenger_unsend (%s) values (%s)";
    public static final String SQL_UNSEND_DEL = "delete from msg_passenger_unsend where expireTime < now() %s ";

    public void start(){
        XProxyHelper.registerService(PassengerUnSendService.class, vertx, this, PassengerUnSendService.class.getName());

        JsonObject mysqlOptions = config().getJsonObject("mysql.config");
        sqlClient = MySQLClient.createShared(vertx, mysqlOptions);

        adMessagePushService = AdMessagePushService.createProxy(vertx);
        nonAdMessagePushService = NonAdMessagePushService.createProxy(vertx);
    }

    @Override
    public void getUnSendMsg(JsonObject param, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
        JsonArray array = new JsonArray();
        StringBuilder positionStr = new StringBuilder();
        makeParam(param, positionStr, array);
        String sql = SQL_UNSEND_GET;
        sql = String.format(sql, positionStr.toString());
        logger.info("【查询补发消息】查询补发消息sql[" + sql + "],param[" + array + "]");
        Future<List<JsonObject>> future = queryForList(array, sql);
        future.setHandler(res -> {
           if(res.succeeded()){
//               logger.info("获取用户未发送出去的消息成功param[" + array + "]");
               resultHandler.handle(Future.succeededFuture(res.result()));
           } else {
               logger.error("【查询补发消息】查询补发消息失败param[" + array + "]");
               resultHandler.handle(Future.failedFuture(res.cause()));
           }
        });
    }

    @Override
    public void addUnSendMsg(JsonObject param, Handler<AsyncResult<Integer>> resultHandler) {
        JsonArray array = new JsonArray();
        String sql = SQL_UNSEND_ADD;
        StringBuilder colStr = new StringBuilder();
        StringBuilder positionStr = new StringBuilder();
        makeInsertParam(param,  colStr, positionStr, array);
        sql = String.format(sql, colStr.toString(), positionStr.toString());
        logger.info("【新增补发消息】新增补发消息,sql[" + sql + "],param[" + array + "]");
        Future<Integer> future = executeSQL(array, sql);
        future.setHandler(res -> {
            if(res.succeeded()){
                logger.info("【新增补发消息】新增补发消息成功");
                resultHandler.handle(Future.succeededFuture(res.result()));
            } else {
                logger.error("【新增补发消息】新增补发消息失败，",res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    @Override
    public void pushAddUnSendMsg(JsonObject param, Handler<AsyncResult<String>> resultHandler) {
        String msgId = param.getString("msgId");
        String phone = param.getString("phone");
        JsonObject requestParam = new JsonObject();
        requestParam.put("msgId", msgId);
        requestParam.put("phone", phone);
        Future<List<JsonObject>> getFuture = Future.future();
        this.getUnSendMsg(requestParam, getFuture.completer());
        getFuture.setHandler(res -> {
            if (res.succeeded()) {
                List<JsonObject> list = res.result();
                if (CollectionUtils.isEmpty(list)) {
                    requestParam.put("userId", param.getString("userId"));
                    requestParam.put("expireTime", param.getString("expireTime"));
                    requestParam.put("content", param.getString("content"));
                    requestParam.put("callFlag", param.getString("callFlag"));
                    requestParam.put("senderId", param.getString("senderId"));
                    requestParam.put("senderKey", param.getString("senderKey"));
                    logger.info("【调用新增补发消息】msgId[" + msgId + "],phone[" + phone + "]");
                    this.addUnSendMsg(requestParam, Future.future());
                } else {
                    logger.info("【调用新增补发消息】需要补发的消息已存在,msgId[" + param.getValue("msgId") + "],phone[" + param.getValue("phone") + "],不再新增");
                }
            } else {
//					logger.error("获取用户未发送出去的消息失败");
            }
        });
    }

    @Override
    public void delExpireUnSendMsg(Handler<AsyncResult<Integer>> resultHandler) {
        JsonArray array = new JsonArray();
        Future<Integer> future = executeSQL(array, SQL_UNSEND_EXPIREDEL);
        future.setHandler(res -> {
            if(res.succeeded()){
//                logger.info("删除过期的未发送出去的消息成功");
                resultHandler.handle(Future.succeededFuture(res.result()));
            } else {
                logger.error("【删除过期补发消息】删除失败，",res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    @Override
    public void delUnSendMsg(JsonObject param, Handler<AsyncResult<Integer>> resultHandler) {
        JsonArray array = new JsonArray();
        StringBuilder positionStr = new StringBuilder();
        makeParam(param, positionStr, array);
        Future<Integer> future = executeSQL(array, SQL_UNSEND_DEL);
        future.setHandler(res -> {
            if(res.succeeded()){
                logger.info("【删除补发消息】完成");
                resultHandler.handle(Future.succeededFuture(res.result()));
            } else {
                logger.error("【删除补发消息】失败，",res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }
    //组装字段
    private void makeParam(JsonObject param, StringBuilder positionStr, JsonArray positionValue){
        Set<String> fieldNames = param.fieldNames();
        for(String fieldName : fieldNames){
            String field = param.getString(fieldName);
            if(StringUtils.isNotBlank(field)){
                positionStr.append(" and " + fieldName + " = ?");
                positionValue.add(field);
            }
        }
        if(positionStr.indexOf(",") != -1){
            positionStr = positionStr.deleteCharAt(0);
        }
    }
    //组装新增字段
    private void makeInsertParam(JsonObject param, StringBuilder colStr, StringBuilder positionStr, JsonArray positionValue){
        Set<String> fieldNames = param.fieldNames();
        for(String fieldName : fieldNames){
            String field = param.getString(fieldName);
            if(StringUtils.isNotBlank(field)){
                colStr.append("," + fieldName);
                positionStr.append(",?");
                if("expireTime".equals(fieldName)){
                    positionValue.add(DateUtil.getDateTime(Long.valueOf(field)));
                }else {
                    positionValue.add(field);
                }
            }
        }
        if(colStr.indexOf(",") != -1){
            colStr = colStr.deleteCharAt(0);
        }
        if(positionStr.indexOf(",") != -1){
            positionStr = positionStr.deleteCharAt(0);
        }
    }
    //执行一条SQL
    protected Future<Integer> executeSQL(JsonArray params, String sql) {
        return getConnection().compose(conn -> {
            Future<Integer> future = Future.future();
            conn.updateWithParams(sql, params, res -> {
                if(res.succeeded()){
                    UpdateResult result = res.result();
                    future.complete(result.getUpdated());
                }else{
                    future.fail(res.cause());
                }
                conn.close();
            });
            return future;
        });
    }

    /**
     * 获取数据库连接
     * @return
     */
    protected Future<SQLConnection> getConnection(){
        Future<SQLConnection> future = Future.future();
        sqlClient.getConnection(future.completer());
        return future;
    }
    /**
     * 查询结果列表
     * @param params
     * @param sql
     * @return
     */
    protected Future<List<JsonObject>> queryForList(JsonArray params, String sql) {
        return getConnection().compose(conn -> {
            Future<List<JsonObject>> future = Future.future();
            conn.queryWithParams(sql, params, res -> {
                if(res.succeeded()){
                    List<JsonObject> list  = res.result().getRows();
                    if(CollectionUtils.isEmpty(list)){
                        future.complete(Collections.EMPTY_LIST);
                    }else{
                        future.complete(list);
                    }
                }else{
                    future.fail(res.cause());
                }
                conn.close();
            });
            return future;
        });
    }


    public void pushUnSendMsg(String phone, Handler<AsyncResult<String>> resultHandler){
        JsonObject param = new JsonObject();
        param.put("phone", phone);
        Future<List<JsonObject>> future = Future.future();
        logger.info("【补发消息】查询需要补发消息，phone=" + phone);
        this.getUnSendMsg(param, future);
        future.setHandler(res -> {
            if(res.succeeded()){
                List<JsonObject> list = res.result();
//                logger.info("查询用户未推送成功的消息成功,phone=" + phone);
                pushUnSendMsg(phone, list);
            }else{
                logger.info("【补发消息】查询需要补发消息失败,phone=" + phone);
            }
        });
        resultHandler.handle(Future.succeededFuture(new ResultData().toString()));
    }

    private void pushUnSendMsg(String phone, List<JsonObject> list){
        if(CollectionUtils.isNotEmpty(list)){
            logger.info("【补发消息】用户需要补发的消息，共有" + list.size() + "条");
            for(JsonObject unSendMsg : list){
                String msgId = unSendMsg.getValue("msgId") + "";
                String msg = unSendMsg.getString("content");
                Integer callFlag =  unSendMsg.getInteger("callFlag");
                Future<String> pushFuture = Future.future();
//                logger.info("用户需要补发的消息:[" + msg + "]");
                //1 是广告消息处理，2 是非广告消息处理
                if(callFlag == 1){
                    adMessagePushService.pushMsg(msg, pushFuture);
                }else{
                    String senderId = unSendMsg.getString("senderId");
                    String senderKey = unSendMsg.getString("senderKey");
                    nonAdMessagePushService.pushMsg(senderId, senderKey, msg, pushFuture);
                }
                pushFuture.setHandler(res -> {
                    if(res.succeeded()){
                        String result = res.result();
                        logger.info("【补发消息】推送消息msgId[" + msgId +  "],phone[" + phone + "],返回结果：" + result);
                        ResultData rd = Json.decodeValue(result, ResultData.class);
                        if(rd != null && ResultData.SUCCESS == rd.getCode()){
                            JsonObject param = new JsonObject();
                            param.put("msgId", msgId);
                            param.put("phone",phone);
                            this.delUnSendMsg(param, Future.future());
                        }
                    }
                });
            }
        }else {
            logger.info("【补发消息】用户无未推送成功的消息不需要补发,phone="+ phone);
        }
    }

}
