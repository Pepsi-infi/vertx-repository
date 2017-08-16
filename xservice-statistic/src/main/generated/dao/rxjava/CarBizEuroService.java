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
import io.vertx.ext.sql.ResultSet;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei Date : 2017/8/9 17:57 Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link dao.CarBizEuroService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(dao.CarBizEuroService.class)
public class CarBizEuroService {

  public static final io.vertx.lang.rxjava.TypeArg<CarBizEuroService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new CarBizEuroService((dao.CarBizEuroService) obj),
    CarBizEuroService::getDelegate
  );

  private final dao.CarBizEuroService delegate;
  
  public CarBizEuroService(dao.CarBizEuroService delegate) {
    this.delegate = delegate;
  }

  public dao.CarBizEuroService getDelegate() {
    return delegate;
  }

  public static CarBizEuroService createProxy(Vertx vertx) { 
    CarBizEuroService ret = CarBizEuroService.newInstance(dao.CarBizEuroService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void countCarBizEuro(Handler<AsyncResult<ResultSet>> resultHandler) { 
    delegate.countCarBizEuro(resultHandler);
  }

  public Single<ResultSet> rxCountCarBizEuro() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      countCarBizEuro(fut);
    }));
  }

  public void queryCarBizEurop(int from, int to, Handler<AsyncResult<ResultSet>> resultHandler) { 
    delegate.queryCarBizEurop(from, to, resultHandler);
  }

  public Single<ResultSet> rxQueryCarBizEurop(int from, int to) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryCarBizEurop(from, to, fut);
    }));
  }


  public static  CarBizEuroService newInstance(dao.CarBizEuroService arg) {
    return arg != null ? new CarBizEuroService(arg) : null;
  }
}
