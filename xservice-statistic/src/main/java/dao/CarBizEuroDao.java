package dao;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import service.dto.CarBizEuroDto;

import java.util.List;

/**
 * Created by lufei
 * Date : 2017/8/9 17:57
 * Description :
 */
@VertxGen
@ProxyGen
public interface CarBizEuroDao {

    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "dao-carBizEuro-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "dao.carBizEuro.service";


    static CarBizEuroDao createProxy(Vertx vertx) {
        return XProxyHelper.createProxy(CarBizEuroDao.class, CarBizEuroDao.class, vertx, CarBizEuroDao.SERVICE_ADDRESS);
    }


    void countCarBizEuro(String date, Handler<AsyncResult<Long>> resultHandler);

    void queryCarBizEuro(String date, int page, int limit, Handler<AsyncResult<List<CarBizEuroDto>>> resultHandler);
}
