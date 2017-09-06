package service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import service.ImCommonLanguageService;
import xservice.BaseDaoVerticle;

import java.util.List;
import java.util.Optional;

/**
 * Created by lufei
 * Date : 2017/8/30 10:57
 * Description :
 */
public class ImCommonLanguageServiceImpl extends BaseDaoVerticle implements ImCommonLanguageService {

    private static final Logger logger = LoggerFactory.getLogger(ImCommonLanguageServiceImpl.class);


    public interface Sql {
        static final String QUERY_IM_COMMON_LANGUAGE = "SELECT * FROM im_common_language WHERE type= ? ORDER BY weight";

        static final String INSERT_IM_COMMON_LANGUAGE = "INSERT INTO im_common_language(content,weight,type) VALUES (?,?,?)";

        static final String DELETE_IM_COMMON_LANGUAGE = "DELETE FROM im_common_language WHERE id=?";

        static final String UPDATE_IM_COMMON_LANGUAGE = "UPDATE im_common_language SET content= ?,weight = ? WHERE id = ?";

        static final String GET_ONE_IM_COMMON_LANGUAGE = "SELECT * FROM im_common_language WHERE id= ?";
    }

    public ImCommonLanguageServiceImpl() {

    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);

        XProxyHelper.registerService(ImCommonLanguageService.class, vertx, this, ImCommonLanguageService.SERVICE_ADDRESS);
        publishEventBusService(ImCommonLanguageService.SERVICE_NAME, ImCommonLanguageService.SERVICE_ADDRESS, ImCommonLanguageService.class);

        client = MySQLClient.createNonShared(vertx, config().getJsonObject("mysql").getJsonObject("mc-admin"));
    }


    @Override
    public void addImCommonLanguage(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject.getString("content")).add(jsonObject.getInteger("weight")).add(jsonObject.getInteger("type"));
        execute(jsonArray, Sql.INSERT_IM_COMMON_LANGUAGE, new JsonObject(), result);
    }

    @Override
    public void getImCommonLanguage(int id, Handler<AsyncResult<JsonObject>> resultHandler) {
        Future<Optional<JsonObject>> future = retrieveOne(id, Sql.GET_ONE_IM_COMMON_LANGUAGE);
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
    public void updateImCommonLanguage(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject.getString("content")).add(jsonObject.getInteger("weight")).add(jsonObject.getInteger("id"));
        execute(jsonArray, Sql.UPDATE_IM_COMMON_LANGUAGE, new JsonObject(), result);
    }

    @Override
    public void deleteImCommonLanguage(int id, Handler<AsyncResult<JsonObject>> result) {
        JsonArray jsonArray = new JsonArray().add(id);
        execute(jsonArray, Sql.DELETE_IM_COMMON_LANGUAGE, new JsonObject(), result);
    }

    @Override
    public void queryImCommonLanguage(int type, Handler<AsyncResult<List<JsonObject>>> result) {
        Future<List<JsonObject>> listFuture = retrieveMany(new JsonArray().add(type), Sql.QUERY_IM_COMMON_LANGUAGE);
        listFuture.setHandler(res -> {
            if (res.succeeded()) {
                result.handle(Future.succeededFuture(res.result()));
            } else {
                logger.error("[dao] query ImCommonLanguage error.", res.cause());
                result.handle(Future.failedFuture(res.cause()));
            }
        });
    }
}
