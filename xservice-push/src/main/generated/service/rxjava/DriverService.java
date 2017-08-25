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

package service.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/8/23 11:13
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.DriverService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.DriverService.class)
public class DriverService {

  public static final io.vertx.lang.rxjava.TypeArg<DriverService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new DriverService((service.DriverService) obj),
    DriverService::getDelegate
  );

  private final service.DriverService delegate;
  
  public DriverService(service.DriverService delegate) {
    this.delegate = delegate;
  }

  public service.DriverService getDelegate() {
    return delegate;
  }

  public static DriverService createProxy(Vertx vertx) { 
    DriverService ret = DriverService.newInstance(service.DriverService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void saveDriver(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) { 
    delegate.saveDriver(jsonObject, result);
  }

  public Single<JsonObject> rxSaveDriver(JsonObject jsonObject) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      saveDriver(jsonObject, fut);
    }));
  }

  public void queryDriver(JsonObject query, int page, int size, Handler<AsyncResult<String>> result) { 
    delegate.queryDriver(query, page, size, result);
  }

  public Single<String> rxQueryDriver(JsonObject query, int page, int size) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryDriver(query, page, size, fut);
    }));
  }

  public void queryBatchDriver(JsonObject query, Handler<AsyncResult<JsonObject>> result) { 
    delegate.queryBatchDriver(query, result);
  }

  public Single<JsonObject> rxQueryBatchDriver(JsonObject query) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryBatchDriver(query, fut);
    }));
  }


  public static  DriverService newInstance(service.DriverService arg) {
    return arg != null ? new DriverService(arg) : null;
  }
}
