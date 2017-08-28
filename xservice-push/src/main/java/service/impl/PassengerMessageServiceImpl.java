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
    public static final String SQL_PASSENGER_MSGADD = "INSERT INTO msg_passenger_msg (`title`, `content`, `action`, `msgCenterImgUrl`, `inMsgCenter`, `openUrl`, `openType`," +
            " `sendType`, `status`, `expireTime`, `sendTime`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    public void start(){
        XProxyHelper.registerService(PassengerMessageService.class, vertx, this, PassengerMessageService.class.getName());

        JsonObject mysqlOptions = config().getJsonObject("mysql.config");
        sqlClient = MySQLClient.createShared(vertx, mysqlOptions);
    }

    public void add(JsonArray req, Handler<AsyncResult<String>> resultHandler){
//        JsonArray params = new JsonArray();
//        params.add(req.getString("title"));
//        params.add(req.getString("content"));
//        params.add(req.getString("action"));
//        params.add(req.getString("msgCenterImgUrl"));
//        params.add(req.getString("inMsgCenter"));
//        params.add(req.getString("openUrl"));
//        params.add(req.getString("openType"));
//        params.add(req.getString("sendType"));
//        params.add(req.getString("status"));
//        params.add(req.getString("expireTime"));
//        params.add(req.getString("sendTime"));
        Future<String> future = addOne(req, SQL_PASSENGER_MSGADD);
        future.setHandler(res -> {
           if(res.succeeded()){
               logger.info(" passengerMsg add response successed");
               resultHandler.handle(Future.succeededFuture(res.result()));
           }else{
               logger.error(" passengerMsg add error response : ", res.cause());
               resultHandler.handle(Future.failedFuture(res.cause()));
           }
        });
    }

    public void get(JsonObject req, Handler<AsyncResult<String>> resultHandler){
        String id = req.getString("id");
        JsonArray params = new JsonArray();
        params.add(id);
        Future<Optional<JsonObject>> future = queryOne(params, SQL_PASSENGER_MSGGET);
        future.setHandler(res -> {
            if(res.succeeded()){
                Optional<JsonObject> result = res.result();
                logger.info(" passengerMsg get response : " + result);
                resultHandler.handle(Future.succeededFuture(result.get().toString()));
            }else{
                logger.error(" passengerMsg get error response : ", res.cause());
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
        Future<List<JsonObject>> queryFuture = queryForPage(params, querySql);
        Future countFuture = queryOne(countParams, countSql);
        executeList(page, pageSize, queryFuture, countFuture, resultHandler);
    }

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
                logger.info(" passengerMsg list response : " + result);
                resultHandler.handle(Future.succeededFuture(pageBean));
            }else{
                logger.error(" passengerMsg list response error! " , res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    protected Future<String> addOne(JsonArray params, String sql) {
        return getConnection().compose(conn -> {
            Future<String> future = Future.future();
            conn.updateWithParams(sql, params, res -> {
                if(res.succeeded()){
                    future.complete("success");
                }else{
                    logger.error(" addOne error, ", res.cause());
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
                    logger.error(" queryOne error, ", res.cause());
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
                        logger.info(" passengerMsg list : null ");
                        future.complete(Collections.EMPTY_LIST);
                    }else{
                        logger.info(" passengerMsg list : " + Json.encode(list));
                        future.complete(list);
                    }
                }else{
                    logger.error("passengerMsg select * error: ", res.cause());
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
