package dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import dao.BaseDaoVerticle;
import dao.CarBizEuroDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import service.dto.CarBizEuroDto;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Created by lufei
 * Date : 2017/8/9 18:08
 * Description :
 */
public class CarBizEuroDaoImpl extends BaseDaoVerticle implements CarBizEuroDao {
    private static final Logger logger = LoggerFactory.getLogger(CarBizEuroDaoImpl.class);

    public interface Sql {
        static final String QUERY_CAR_BIZ_EURO = "SELECT * FROM `car_biz_europ` WHERE device_token !='' ORDER BY id  LIMIT ?,?;";

        static final String COUNT_CAR_BIZ_EURO = "select count(*) from car_biz_europ where 1=? and device_token !='' ";

    }

    public CarBizEuroDaoImpl() {
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);

        XProxyHelper.registerService(CarBizEuroDao.class, vertx, this, CarBizEuroDao.SERVICE_ADDRESS);
        publishEventBusService(CarBizEuroDao.SERVICE_NAME, CarBizEuroDao.SERVICE_ADDRESS, CarBizEuroDao.class);

        client = MySQLClient.createNonShared(vertx, config().getJsonObject("mysql").getJsonObject("rentcar"));

    }


    @Override
    public void countCarBizEuro(String date, Handler<AsyncResult<Long>> resultHandler) {
        String sql = Sql.COUNT_CAR_BIZ_EURO;

        Future<Optional<JsonObject>> future = retrieveOne(1, sql);
        future.setHandler(result -> {
            if (result.succeeded()) {
                Optional<JsonObject> jsonObject = result.result();
                long count = jsonObject.orElse(new JsonObject()).getLong("count(*)");
                resultHandler.handle(Future.succeededFuture(count));
            } else {
                logger.error(result.cause());
                resultHandler.handle(Future.failedFuture(result.cause()));
            }
        });
    }

    @Override
    public void queryCarBizEuro(String date, int page, int limit, Handler<AsyncResult<List<CarBizEuroDto>>> resultHandler) {
        String sql = Sql.QUERY_CAR_BIZ_EURO;
        Future<List<JsonObject>> future = retrieveByPage(page, limit, sql);
        future.setHandler(result -> {
            if (result.succeeded()) {
                List<CarBizEuroDto> carBizEuroDtos = Lists.transform(result.result(), new Function<JsonObject, CarBizEuroDto>() {
                    @Nullable
                    @Override
                    public CarBizEuroDto apply(@Nullable JsonObject jsonObject) {
                        CarBizEuroDto carBizEuroDto = new CarBizEuroDto();
                        carBizEuroDto.setId(jsonObject.getLong("id"));
                        carBizEuroDto.setPhone(jsonObject.getString("phone"));
                        carBizEuroDto.setDeviceToken(jsonObject.getString("device_token"));
                        return carBizEuroDto;
                    }
                });
                resultHandler.handle(Future.succeededFuture(carBizEuroDtos));
            } else {
                logger.error(result.cause());
                resultHandler.handle(Future.failedFuture(result.cause()));
            }
        });

    }
}
