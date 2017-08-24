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
import domain.PageBean;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by weim on 2017/8/22.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.PassengerMessageService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.PassengerMessageService.class)
public class PassengerMessageService {

  public static final io.vertx.lang.rxjava.TypeArg<PassengerMessageService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new PassengerMessageService((service.PassengerMessageService) obj),
    PassengerMessageService::getDelegate
  );

  private final service.PassengerMessageService delegate;
  
  public PassengerMessageService(service.PassengerMessageService delegate) {
    this.delegate = delegate;
  }

  public service.PassengerMessageService getDelegate() {
    return delegate;
  }

  public static PassengerMessageService createProxy(Vertx vertx) { 
    PassengerMessageService ret = PassengerMessageService.newInstance(service.PassengerMessageService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void list(JsonObject param, Handler<AsyncResult<PageBean>> resultHandler) { 
    delegate.list(param, resultHandler);
  }

  public Single<PageBean> rxList(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      list(param, fut);
    }));
  }

  public void add(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.add(param, resultHandler);
  }

  public Single<String> rxAdd(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      add(param, fut);
    }));
  }


  public static  PassengerMessageService newInstance(service.PassengerMessageService arg) {
    return arg != null ? new PassengerMessageService(arg) : null;
  }
}
