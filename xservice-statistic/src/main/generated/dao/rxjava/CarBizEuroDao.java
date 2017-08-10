/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package dao.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import java.util.List;
import service.dto.CarBizEuroDto;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/8/9 17:57
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link dao.CarBizEuroDao original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(dao.CarBizEuroDao.class)
public class CarBizEuroDao {

  public static final io.vertx.lang.rxjava.TypeArg<CarBizEuroDao> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new CarBizEuroDao((dao.CarBizEuroDao) obj),
    CarBizEuroDao::getDelegate
  );

  private final dao.CarBizEuroDao delegate;
  
  public CarBizEuroDao(dao.CarBizEuroDao delegate) {
    this.delegate = delegate;
  }

  public dao.CarBizEuroDao getDelegate() {
    return delegate;
  }

  public static CarBizEuroDao createProxy(Vertx vertx) { 
    CarBizEuroDao ret = CarBizEuroDao.newInstance(dao.CarBizEuroDao.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void countCarBizEuro(String date, Handler<AsyncResult<Long>> resultHandler) { 
    delegate.countCarBizEuro(date, resultHandler);
  }

  public Single<Long> rxCountCarBizEuro(String date) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      countCarBizEuro(date, fut);
    }));
  }

  public void queryCarBizEuro(String date, int page, int limit, Handler<AsyncResult<List<CarBizEuroDto>>> resultHandler) { 
    delegate.queryCarBizEuro(date, page, limit, resultHandler);
  }

  public Single<List<CarBizEuroDto>> rxQueryCarBizEuro(String date, int page, int limit) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryCarBizEuro(date, page, limit, fut);
    }));
  }


  public static  CarBizEuroDao newInstance(dao.CarBizEuroDao arg) {
    return arg != null ? new CarBizEuroDao(arg) : null;
  }
}
