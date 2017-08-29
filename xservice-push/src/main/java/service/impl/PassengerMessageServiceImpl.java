package service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import domain.PageBean;
import domain.PassengerMsgDto;
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
import service.PassengerMessageService;
import util.DateUtil;
import xservice.BaseServiceVerticle;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by weim on 2017/8/22.
 */
public class PassengerMessageServiceImpl extends BaseServiceVerticle implements PassengerMessageService{

    private static Logger logger = LoggerFactory.getLogger(PassengerMessageServiceImpl.class);

    private SQLClient sqlClient;

    public static final String SQL_PASSENGER_MSGLIST_PAGE = "select * from msg_passenger_msg where 1=1 %s order by sendTime limit ?,?";
    public static final String SQL_PASSENGER_MSGLIST_COUNT = "select count(1) from msg_passenger_msg where 1=1 %s";
    public static final String SQL_PASSENGER_MSGGET = "select * from msg_passenger_msg where id = ? ";

    public static final String SQL_PASSENGER_MSGADD = "insert into msg_passenger_msg (title, content, action, inMsgCenter, openType," +
            " sendType, status, expireTime, sendTime, msgCenterImgUrl, openUrl) values (?,?,?,?,?,?,?,?,?,?,?)";

    public static final String SQL_PASSENGER_MSGUPDATE = "update msg_passenger_msg set %s  where id= ? ";

    public static final String SQL_PASSENGER_MSGDEL = "delete from msg_passenger_msg where id = ? ";
    public static final String SQL_PASSENGER_MSGSET_INVALID = "update from msg_passenger_msg set = 2 where id = ? ";

    public void start(){
        XProxyHelper.registerService(PassengerMessageService.class, vertx, this, PassengerMessageService.class.getName());

        JsonObject mysqlOptions = config().getJsonObject("mysql.config");
        sqlClient = MySQLClient.createShared(vertx, mysqlOptions);
    }

    public void addOrUpdate(JsonObject param, Handler<AsyncResult<String>> resultHandler){
        JsonArray params = new JsonArray();
//        params.add(param.getValue("title"));
//        params.add(param.getValue("content"));
//        params.add(param.getValue("action"));
//        params.add(param.getValue("inMsgCenter"));
//        params.add(param.getValue("openType"));
//        params.add(param.getValue("sendType"));
//        params.add(param.getValue("status"));
//        Object expireTime = param.getValue("expireTime");
//        params.add(DateUtil.getLocalDate((String)expireTime));
//        Object sendTime = param.getValue("sendTime");
//        params.add(DateUtil.getLocalDate((String)sendTime));

        String sql;
        StringBuilder updateBuilder = new StringBuilder();
        Optional<String> id = Optional.ofNullable(param.getString("id"));
        Optional<String> title = Optional.ofNullable(param.getString("title"));
        Optional<String> content = Optional.ofNullable(param.getString("content"));
        Optional<String> action = Optional.ofNullable(param.getString("action"));
        Optional<String> inMsgCenter = Optional.ofNullable(param.getString("inMsgCenter"));
        Optional<String> openType = Optional.ofNullable(param.getString("openType"));
        Optional<String> sendType = Optional.ofNullable(param.getString("sendType"));
        Optional<String> status = Optional.ofNullable(param.getString("status"));
        Optional<String> expireTime = Optional.ofNullable(param.getString("expireTime"));
        Optional<String> sendTime = Optional.ofNullable(param.getString("sendTime"));
        Optional<String> msgCenterImgUrl = Optional.ofNullable(param.getString("msgCenterImgUrl"));
        Optional<String> openUrl = Optional.ofNullable(param.getString("openUrl"));
        if(id.isPresent()){
            if(title.isPresent()){
                params.add(title.get());
                updateBuilder.append(", title=?");
            }
            if(content.isPresent()){
                params.add(content.get());
                updateBuilder.append(", content=?");
            }
            if(action.isPresent()){
                params.add(action.get());
                updateBuilder.append(", action=?");
            }
            if(inMsgCenter.isPresent()){
                params.add(inMsgCenter.get());
                updateBuilder.append(", inMsgCenter=?");
            }
            if(openType.isPresent()){
                params.add(openType.get());
                updateBuilder.append(", openType=?");
            }
            if(sendType.isPresent()){
                params.add(sendType.get());
                updateBuilder.append(", sendType=?");
            }
            if(status.isPresent()){
                params.add(status.get());
                updateBuilder.append(", status=?");
            }
            if(expireTime.isPresent()){
                params.add(DateUtil.getLocalDate(expireTime.get()));
                updateBuilder.append(", expireTime=?");
            }
            if(sendTime.isPresent()){
                params.add(DateUtil.getLocalDate(sendTime.get()));
                updateBuilder.append(", sendTime=?");
            }
            if(msgCenterImgUrl.isPresent()){
                params.add(msgCenterImgUrl.get());
                updateBuilder.append(", msgCenterImgUrl=?");
            }
            if(openUrl.isPresent()){
                params.add(openUrl.get());
                updateBuilder.append(", openUrl=?");
            }
            params.add(id.get());
            //更新
            sql = SQL_PASSENGER_MSGUPDATE;
            updateBuilder = updateBuilder.length() > 0 ? updateBuilder.deleteCharAt(0) : updateBuilder;
            sql = String.format(sql, updateBuilder.toString());
        }else{
            params.add(title.orElse(""));
            params.add(content.orElse(""));
            params.add(action.orElse(""));
            params.add(inMsgCenter.orElse(""));
            params.add(openType.orElse(""));
            params.add(sendType.orElse(""));
            params.add(status.orElse(""));
            params.add(DateUtil.getLocalDate(expireTime.get()));
            params.add(DateUtil.getLocalDate(sendTime.get()));
            params.add(msgCenterImgUrl.orElse(""));
            params.add(openUrl.orElse(""));
            //新增
            sql = SQL_PASSENGER_MSGADD;
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

    public void list(JsonObject req, Handler<AsyncResult<PageBean>> resultHandler){
        String title = req.getString("title");
        String sendTime = req.getString("sendTime");
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
        if(StringUtils.isNotBlank(sendTime)){
            params.add(sendTime);
            countParams.add(sendTime);
            sb.append(" and sendTime = ? ");
        }
        //查总数
        String countSql = String.format(SQL_PASSENGER_MSGLIST_COUNT, sb.toString());
        params.add(pageIndex);
        params.add(pageSize);

        String querySql = String.format(SQL_PASSENGER_MSGLIST_PAGE, sb.toString());
        logger.info("查询乘客消息列表[sql : " + querySql + "],[params : " + params + "]");
        Future<List<JsonObject>> queryFuture = queryForPage(params, querySql);
        logger.info("查询乘客消息列表总数[sql : " + countSql + "],[params : " + countParams + "]");
        Future countFuture = queryOne(countParams, countSql);
        executeList(page, pageSize, queryFuture, countFuture, resultHandler);
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
    private void executeList(int page, int pageSize,Future<List<JsonObject>> queryFuture, Future countFuture,
                             Handler<AsyncResult<PageBean>> resultHandler){
        CompositeFuture compositeFuture = CompositeFuture.all(queryFuture, countFuture);
        compositeFuture.setHandler(res -> {
            if(res.succeeded()){
                //列表
                List<JsonObject> list = res.result().resultAt(0);
                List<PassengerMsgDto> newList = Lists.transform(list, new Function<JsonObject, PassengerMsgDto>() {
                    @Nullable
                    @Override
                    public PassengerMsgDto apply(@Nullable JsonObject jsonObject) {
                        return jsonObject.mapTo(PassengerMsgDto.class);
                    }
                });
                //总条数
                Optional<JsonObject> resultCount = res.result().resultAt(1);
                Long total = resultCount.orElse(new JsonObject()).getLong("count(1)");
                PageBean pageBean = new PageBean(page, pageSize, newList, total);
                String result = Json.encode(pageBean);
                logger.info("查询乘客消息列表成功，" + result);
                resultHandler.handle(Future.succeededFuture(pageBean));
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

    protected Future<List<JsonObject>> queryForPage(JsonArray params, String sql) {
        return getConnection().compose(conn -> {
            Future<List<JsonObject>> future = Future.future();
            conn.queryWithParams(sql, params, res -> {
                if(res.succeeded()){
                    List<JsonObject> list  = res.result().getRows();
                    if(CollectionUtils.isEmpty(list)){
                        logger.info("passengerMsg list : null ");
                        future.complete(Collections.EMPTY_LIST);
                    }else{
                        logger.info("passengerMsg list : " + Json.encode(list));
                        future.complete(list);
                    }
                }else{
                    logger.error("passengerMsg list error: ", res.cause());
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
}
