package service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import model.Pager;
import org.apache.commons.lang.StringUtils;
import service.SensitiveWordService;
import utils.CalendarUtil;
import utils.JsonUtil;
import xservice.BaseDaoVerticle;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by lufei
 * Date : 2017/9/4 11:59
 * Description :
 */
public class SensitiveWordServiceImpl extends BaseDaoVerticle implements SensitiveWordService {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordServiceImpl.class);

    public interface Sql {
        static final String QUERY_SENSITIVE_WORD = "SELECT * from sensitive_word where 1=1 %s ORDER BY id DESC LIMIT ?,?";

        static final String COUNT_SENSITIVE_WORD = "SELECT count(id) from sensitive_word where 1=? %s";

        static final String INSERT_SENSITIVE_WORD = "INSERT INTO sensitive_word(word,status,createTime,updateTime) VALUES (?,?,?,?)";

        static final String DELETE_SENSITIVE_WORD = "DELETE FROM sensitive_word WHERE id = ?";

        static final String UPDATE_SENSITIVE_WORD = "UPDATE sensitive_word SET word =?,status = ?,updateTime = ? WHERE id = ?";

        static final String GET_ONE_SENSITIVE_WORD = "SELECT * from sensitive_word where id=?";
    }

    public SensitiveWordServiceImpl() {

    }


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);

        XProxyHelper.registerService(SensitiveWordService.class, vertx, this, SensitiveWordService.SERVICE_ADDRESS);
        publishEventBusService(SensitiveWordService.SERVICE_NAME, SensitiveWordService.SERVICE_ADDRESS, SensitiveWordService.class);

        client = MySQLClient.createNonShared(vertx, config().getJsonObject("mysql").getJsonObject("mc-admin"));
    }


    @Override
    public void addSensitiveWord(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject.getString("word")).add(1)
                .add(CalendarUtil.format(new Date())).add(CalendarUtil.format(new Date()));
        execute(jsonArray, Sql.INSERT_SENSITIVE_WORD, new JsonObject(), result);
    }

    @Override
    public void getSensitiveWord(int id, Handler<AsyncResult<JsonObject>> resultHandler) {
        Future<Optional<JsonObject>> future = retrieveOne(id, Sql.GET_ONE_SENSITIVE_WORD);
        future.setHandler(result -> {
            if (result.succeeded()) {
                Optional<JsonObject> jsonObject = result.result();
                resultHandler.handle(Future.succeededFuture(jsonObject.orElse(new JsonObject())));
            } else {
                logger.error(result.cause());
                resultHandler.handle(Future.failedFuture(result.cause()));
            }
        });
    }

    @Override
    public void updateSensitiveWord(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject.getString("word")).add(jsonObject.getInteger("status"))
                .add(CalendarUtil.format(new Date())).add(jsonObject.getInteger("id"));
        execute(jsonArray, Sql.UPDATE_SENSITIVE_WORD, new JsonObject(), result);
    }

    @Override
    public void deleteSensitiveWord(int id, Handler<AsyncResult<JsonObject>> result) {
        JsonArray jsonArray = new JsonArray().add(id);
        execute(jsonArray, Sql.DELETE_SENSITIVE_WORD, new JsonObject(), result);
    }

    @Override
    public void querySensitiveWord(JsonObject jsonObject, int page, int limit, Handler<AsyncResult<String>> result) {
        String querySql = Sql.QUERY_SENSITIVE_WORD;
        String countSql = Sql.COUNT_SENSITIVE_WORD;
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(jsonObject.getString("word"))) {
            sb.append(" and word = '" + jsonObject.getString("word") + "'");
        }
        querySql = String.format(querySql, sb.toString());
        countSql = String.format(countSql, sb.toString());

        List<Future> futures = new ArrayList<>();
        Future<List<JsonObject>> future = retrieveByPage(page, limit, querySql);
        futures.add(future);

        Future<Optional<JsonObject>> countFuture = retrieveOne(1, countSql);
        futures.add(countFuture);

        CompositeFuture compositeFuture = CompositeFuture.all(futures);
        compositeFuture.setHandler(asr1 -> {
            if (asr1.succeeded()) {
                List<JsonObject> jsonObjects = asr1.result().resultAt(0);
                List<Map> mapList = Lists.transform(jsonObjects, new Function<JsonObject, Map>() {
                    @Nullable
                    @Override
                    public Map apply(@Nullable JsonObject input) {
                        return input.getMap();
                    }
                });
                Optional<JsonObject> countJsonObject = asr1.result().resultAt(1);
                Long count = countJsonObject.orElse(new JsonObject()).getLong("count(id)");
                Pager pager = new Pager(count, page, limit, mapList);
                model.Result response = model.Result.success(pager);
                result.handle(Future.succeededFuture(JsonUtil.encodePrettily(response)));
            } else {
                logger.error(asr1.cause());
                result.handle(Future.failedFuture(asr1.cause()));
            }
        });
    }
}
