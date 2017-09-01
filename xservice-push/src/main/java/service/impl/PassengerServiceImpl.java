package service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import domain.Pager;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import service.PassengerService;
import util.DateUtil;
import xservice.BaseServiceVerticle;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by weim on 2017/8/22.
 */
public class PassengerServiceImpl extends BaseServiceVerticle implements PassengerService {

    private static Logger logger = LoggerFactory.getLogger(PassengerServiceImpl.class);

    private SQLClient sqlClient;

    //乘客消息列表
    public static final String SQL_PASSENGER_MSGLIST_PAGE = "select * from msg_passenger_msg where 1=1 %s order by createTime desc limit ?,?";
    public static final String SQL_PASSENGER_MSGLIST_COUNT = "select count(1) from msg_passenger_msg where 1=1 %s";
    public static final String SQL_PASSENGER_MSGGET = "select * from msg_passenger_msg where id = ? ";
    //乘客消息新增
    public static final String SQL_PASSENGER_MSGADD = "insert into msg_passenger_msg (%s) values (%s)";
    //乘客消息更新
    public static final String SQL_PASSENGER_MSGUPDATE = "update msg_passenger_msg set %s  where id= ? ";

    public static final String SQL_PASSENGER_MSGDEL = "delete from msg_passenger_msg where id = ? ";
    //乘客符合推送条件
    public static final String SQL_PASSENGER_MSG_GETPUSH = "select id,title,content,action,msgCenterImgUrl,inMsgCenter,openType,openUrl,sendType,status,\n" +
            "             to_seconds(sendTime)*1000 sendTime, to_seconds(expireTime)*1000 expireTime from msg_passenger_msg where sendTime < now()\n" +
            "and expireTime > now() and status = 1 and id = ?";

    //导入的手机号文件
    public static final String SQL_IMPORT_FILElIST = "select * from msg_passenger_importfile";
    public static final String SQL_IMPORT_FILEPAGE = "select * from msg_passenger_importfile where 1=1 %s order by createTime desc limit ?,?";
    public static final String SQL_IMPORT_FILECOUNT = "select count(1) from msg_passenger_importfile where 1=1 %s ";

    public static final String SQL_IMPORT_PHONElIST = "select * from msg_passenger_importphone where importFileId=?";

    public void start(){
        XProxyHelper.registerService(PassengerService.class, vertx, this, PassengerService.class.getName());

        JsonObject mysqlOptions = config().getJsonObject("mysql.config");
        sqlClient = MySQLClient.createShared(vertx, mysqlOptions);
    }

    public void addOrUpdate(JsonObject param, Handler<AsyncResult<String>> resultHandler){
        JsonArray params = new JsonArray();
        String sql;

        String id = param.getString("id");
        if(StringUtils.isNotBlank(id)){
            //拼字段
            StringBuilder columnSb = new StringBuilder();
            //更新
            sql = SQL_PASSENGER_MSGUPDATE;
            updatePassengerMsg(param, columnSb, params);
            //更新时间
            columnSb.append(", updateTime=?");
            params.add(DateUtil.getDateTime(System.currentTimeMillis()));

            params.add(id);
            sql = String.format(sql, columnSb.toString());
        }else{
            //拼字段
            StringBuilder columnSb = new StringBuilder();
            //拼占位符
            StringBuilder positionSb = new StringBuilder();
            //新增
            sql = SQL_PASSENGER_MSGADD;
            //处理参数
            insertPassengerMsg(param, columnSb, positionSb, params);
            //创建时间
            columnSb.append(", createTime");
            positionSb.append(",?");
            params.add(DateUtil.getDateTime(System.currentTimeMillis()));

            sql = String.format(sql, columnSb.toString(), positionSb.toString());
        }
        Future<String> future = executeSQL(params, sql);
        logger.info("新增乘客消息[sql : " + sql + "],[params : " + params + "]");
        future.setHandler(res -> {
           if(res.succeeded()){
               logger.info("新增乘客消息成功," + res.result());
               resultHandler.handle(Future.succeededFuture("新增乘客消息成功"));
           }else{
               logger.error("新增乘客消息出错,", res.cause());
               resultHandler.handle(Future.failedFuture(res.cause()));
           }
        });
    }
    //处理新增字段组装
    private void insertPassengerMsg(JsonObject param, StringBuilder colStr, StringBuilder positionStr, JsonArray positionValue){
        Set<String> fieldNames = param.fieldNames();
        for(String fieldName : fieldNames){
            String field = param.getString(fieldName);
            if(StringUtils.isNotBlank(field)){
                colStr.append("," + fieldName);
                positionStr.append(",?");
                if(field.endsWith("Z")){
                    positionValue.add(DateUtil.dateTimeGmt2Local(field));
                }else{
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
    //处理更新字段组装
    private void updatePassengerMsg(JsonObject param, StringBuilder colStr, JsonArray positionValue){
        Set<String> fieldNames = param.fieldNames();
        for(String fieldName : fieldNames){
            String field = param.getString(fieldName);
            if(StringUtils.isNotBlank(field)){
                colStr.append("," + fieldName + "=? ");
                if(field.endsWith("Z")){
                    positionValue.add(DateUtil.dateTimeGmt2Local(field));
                }else{
                    positionValue.add(field);
                }
            }
        }
        if(colStr.indexOf(",") != -1){
            colStr = colStr.deleteCharAt(0);
        }
    }

    public void get(JsonObject req, Handler<AsyncResult<String>> resultHandler){
        String id = req.getString("id");
        JsonArray params = new JsonArray();
        params.add(id);
        logger.info("查询乘客消息[sql : " + SQL_PASSENGER_MSGGET + "],[params : " + params + "]");
        Future<Optional<JsonObject>> future = queryOne(params, SQL_PASSENGER_MSGGET);
        future.setHandler(res -> {
            if(res.succeeded()){
                Optional<JsonObject> result = res.result();
                logger.info("查询乘客消息成功，" + result);
                resultHandler.handle(Future.succeededFuture(result.get().toString()));
            }else{
                logger.error("查询乘客消息出错，", res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    public void getPushMsg(JsonObject req, Handler<AsyncResult<String>> resultHandler){
        String id = req.getString("id");
        JsonArray params = new JsonArray();
        params.add(id);
        logger.info("查询乘客消息[sql : " + SQL_PASSENGER_MSG_GETPUSH + "],[params : " + params + "]");
        Future<Optional<JsonObject>> future = queryOne(params, SQL_PASSENGER_MSG_GETPUSH);
        future.setHandler(res -> {
            if(res.succeeded()){
                Optional<JsonObject> result = res.result();
                logger.info("查询乘客消息成功，" + result);
                resultHandler.handle(Future.succeededFuture(result.get().toString()));
            }else{
                logger.error("查询乘客消息出错，", res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    public void list(JsonObject req, Handler<AsyncResult<String>> resultHandler){
        String title = req.getString("title");
        String createTime = req.getString("createTime");
        Integer page = req.getInteger("page");
        Integer pageIndex = req.getInteger("pageIndex");
        Integer pageSize = req.getInteger("pageSize");
        StringBuilder sb = new StringBuilder();
        JsonArray params = new JsonArray();
        JsonArray countParams = new JsonArray();
        if(StringUtils.isNotBlank(title)){
            params.add(title);
            countParams.add(title);
            sb.append(" and title = ? ");
        }
        if(StringUtils.isNotBlank(createTime)){
            params.add(DateUtil.dateTimeGmt2Local(createTime));
            countParams.add(DateUtil.dateTimeGmt2Local(createTime));
            sb.append(" and createTime = ? ");
        }
        //查总数
        String countSql = String.format(SQL_PASSENGER_MSGLIST_COUNT, sb.toString());
        params.add(pageIndex);
        params.add(pageSize);

        String querySql = String.format(SQL_PASSENGER_MSGLIST_PAGE, sb.toString());
        logger.info("查询乘客消息列表[sql : " + querySql + "],[params : " + params + "]");
        Future<List<JsonObject>> queryFuture = queryForList(params, querySql);
        logger.info("查询乘客消息列表总数[sql : " + countSql + "],[params : " + countParams + "]");
        Future countFuture = queryOne(countParams, countSql);
        makePageResponse(page, pageSize, queryFuture, countFuture, resultHandler);
    }

    /**
     * 根据id删除一条乘客消息
     * @param req
     * @param resultHandler
     */
    public void del(JsonObject req, Handler<AsyncResult<String>> resultHandler){
        Optional<String> id = Optional.ofNullable(req.getString("id"));
        if(!id.isPresent()){
            logger.error("删除乘客消息时，消息id不能为空");
            resultHandler.handle(Future.failedFuture("删除乘客消息时，消息id不能为空"));
            return;
        }
        JsonArray params = new JsonArray();
        params.add(id.get());
        Future<String> future = executeSQL(params, SQL_PASSENGER_MSGDEL);
        future.setHandler(res -> {
            if(res.succeeded()){
                String result = res.result();
                logger.info("删除乘客消息成功," + result);
                resultHandler.handle(Future.succeededFuture("删除乘客消息成功"));
            }else{
                logger.error("删除乘客消息出错，", res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    //查询列表
    private void makePageResponse(int page, int pageSize, Future<List<JsonObject>> queryFuture, Future countFuture,
                                  Handler<AsyncResult<String>> resultHandler){
        CompositeFuture compositeFuture = CompositeFuture.all(queryFuture, countFuture);
        compositeFuture.setHandler(res -> {
            if(res.succeeded()){
                //列表
                List<JsonObject> list = res.result().resultAt(0);
                List<Map> newList = Lists.transform(list, new Function<JsonObject, Map>() {
                    @Nullable
                    @Override
                    public Map apply(@Nullable JsonObject jsonObject) {
                        return jsonObject.mapTo(Map.class);
                    }
                });
                //总条数
                Optional<JsonObject> resultCount = res.result().resultAt(1);
                Long total = resultCount.orElse(new JsonObject()).getLong("count(1)");
                Pager pager = new Pager(total, page, pageSize, list);
                String result = Json.encode(pager);
                logger.info("查询乘客消息列表成功，" + result);
                resultHandler.handle(Future.succeededFuture(result));
            }else{
                logger.error("查询乘客消息列表出错，" , res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    //执行一条SQL
    protected Future<String> executeSQL(JsonArray params, String sql) {
        return getConnection().compose(conn -> {
            Future<String> future = Future.future();
            conn.updateWithParams(sql, params, res -> {
                if(res.succeeded()){
                    future.complete();
                }else{
                    future.fail(res.cause());
                }
                conn.close();
            });
            return future;
        });
    }

    protected Future<Optional<JsonObject>> queryOne(JsonArray params, String sql) {
        return getConnection().compose(conn -> {
            Future<Optional<JsonObject>> future = Future.future();
            conn.queryWithParams(sql, params, res -> {
                if(res.succeeded()){
                    List<JsonObject> list  = res.result().getRows();
                    if(CollectionUtils.isEmpty(list)){
                        future.complete(Optional.empty());
                    }else{
                        future.complete(Optional.of(list.get(0)));
                    }
                }else{
                    future.fail(res.cause());
                }
                conn.close();
            });
            return future;
        });
    }

    protected Future<List<JsonObject>> queryForList(JsonArray params, String sql) {
        return getConnection().compose(conn -> {
            Future<List<JsonObject>> future = Future.future();
            conn.queryWithParams(sql, params, res -> {
                if(res.succeeded()){
                    List<JsonObject> list  = res.result().getRows();
                    if(CollectionUtils.isEmpty(list)){
                        logger.info("query list : null ");
                        future.complete(Collections.EMPTY_LIST);
                    }else{
                        logger.info("query list : " + Json.encode(list));
                        future.complete(list);
                    }
                }else{
                    logger.error("query list error: ", res.cause());
                    future.fail(res.cause());
                }
                conn.close();
            });
            return future;
        });
    }

    protected Future<SQLConnection> getConnection(){
        Future<SQLConnection> future = Future.future();
        sqlClient.getConnection(future.completer());
        return future;
    }


    public void addImportFile(JsonObject param, Handler<AsyncResult<String>> resultHandler){

    }

    public void getImportFileList(JsonObject param, Handler<AsyncResult<List<JsonObject>>> resultHandler){
        Future<List<JsonObject>> future = queryImportFileList();
        future.setHandler(res ->{
           if(res.succeeded()){
               List<JsonObject> list = res.result();
               logger.info("查询导入文件列表成功" + list);
               resultHandler.handle(Future.succeededFuture(list));
           } else {
               logger.error("查询导入文件列表失败",res.cause());
               resultHandler.handle(Future.failedFuture(res.cause()));
           }
        });
    }

    private Future<List<JsonObject>> queryImportFileList(){
        String sql = SQL_IMPORT_FILElIST;
        return getConnection().compose(conn -> {
            Future<List<JsonObject>> future = Future.future();
            conn.query(sql, res -> {
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

    public void getImportPhoneList(String param, Handler<AsyncResult<List<JsonObject>>> resultHandler){
        Future<List<JsonObject>> future = queryImportPhoneList(param);
//        future.setHandler(res ->{
//            if(res.succeeded()){
//                List<JsonObject> list = res.result();
//                logger.info("查询导入的手机号列表成功" + list);
//                resultHandler.handle(Future.succeededFuture(list));
//            } else {
//                logger.error("查询导入的手机号列表失败",res.cause());
//                resultHandler.handle(Future.failedFuture(res.cause()));
//            }
//        });
        future.setHandler(resultHandler);
    }

    public Future<List<JsonObject>> queryImportPhoneList(String param){
        String sql = SQL_IMPORT_PHONElIST;
        JsonArray params = new JsonArray().add(param);
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

    public void getImportFilePage(JsonObject param, Handler<AsyncResult<String>> resultHandler){
        String createTime = param.getString("createTime");
        Integer pageIndex = param.getInteger("pageIndex");
        Integer page = param.getInteger("page");
        Integer pageSize = param.getInteger("pageSize");

        StringBuilder sb = new StringBuilder("");
        JsonArray listParams = new JsonArray();
        JsonArray countParams = new JsonArray();
        if(StringUtils.isNotBlank(createTime)){
            sb.append(" and DATE_FORMAT(createTime,'%Y-%m-%d')=?");
            listParams.add(DateUtil.dateGmt2Local(createTime));
            countParams.add(DateUtil.dateGmt2Local(createTime));
        }

        //总数
        String countSql = SQL_IMPORT_FILECOUNT;
        countSql = String.format(countSql, sb);
        Future<Optional<JsonObject>> countFature = queryOne(countParams, countSql);
        //列表
        listParams.add(pageIndex).add(pageSize);
        String listSql = SQL_IMPORT_FILEPAGE;
        listSql = String.format(listSql, sb);
        Future<List<JsonObject>>  listFature = queryForList(listParams, listSql);
        makePageJsonResponse(page, pageSize, listFature, countFature, resultHandler);
    }
    //查询列表
    private void makePageJsonResponse(int page, int pageSize, Future<List<JsonObject>> queryFuture, Future countFuture,
                              Handler<AsyncResult<String>> resultHandler){
        CompositeFuture compositeFuture = CompositeFuture.all(queryFuture, countFuture);
        compositeFuture.setHandler(res -> {
            if(res.succeeded()){
                //列表
                List<JsonObject> list = res.result().resultAt(0);
                List<Map> newList = Lists.transform(list, new Function<JsonObject, Map>() {
                    @Nullable
                    @Override
                    public Map apply(@Nullable JsonObject jsonObject) {
                        return jsonObject.mapTo(Map.class);
                    }
                });
                //总条数
                Optional<JsonObject> resultCount = res.result().resultAt(1);
                Integer total = resultCount.orElse(new JsonObject()).getInteger("count(1)");
                Pager pager = new Pager(total, page, pageSize, list);
                String result = Json.encode(pager);
                logger.info("查询乘客消息列表成功，" + result);
                resultHandler.handle(Future.succeededFuture(result));
            }else{
                logger.error("查询乘客消息列表出错，" , res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    public void delImportFile(JsonObject param, Handler<AsyncResult<String>> resultHandler){

    }
}
