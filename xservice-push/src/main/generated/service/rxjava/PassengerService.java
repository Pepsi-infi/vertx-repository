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
import java.util.List;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by weim on 2017/8/22.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.PassengerService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.PassengerService.class)
public class PassengerService {

  public static final io.vertx.lang.rxjava.TypeArg<PassengerService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new PassengerService((service.PassengerService) obj),
    PassengerService::getDelegate
  );

  private final service.PassengerService delegate;
  
  public PassengerService(service.PassengerService delegate) {
    this.delegate = delegate;
  }

  public service.PassengerService getDelegate() {
    return delegate;
  }

  public static PassengerService createProxy(Vertx vertx) { 
    PassengerService ret = PassengerService.newInstance(service.PassengerService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void list(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.list(param, resultHandler);
  }

  public Single<String> rxList(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      list(param, fut);
    }));
  }

  public void get(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.get(param, resultHandler);
  }

  public Single<String> rxGet(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      get(param, fut);
    }));
  }

  public void addOrUpdate(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.addOrUpdate(param, resultHandler);
  }

  public Single<String> rxAddOrUpdate(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addOrUpdate(param, fut);
    }));
  }

  public void del(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.del(param, resultHandler);
  }

  public Single<String> rxDel(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      del(param, fut);
    }));
  }

  public void getPushMsg(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.getPushMsg(param, resultHandler);
  }

  public Single<String> rxGetPushMsg(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getPushMsg(param, fut);
    }));
  }

  public void addImportFile(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.addImportFile(param, resultHandler);
  }

  public Single<String> rxAddImportFile(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addImportFile(param, fut);
    }));
  }

  public void delImportFile(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.delImportFile(param, resultHandler);
  }

  public Single<String> rxDelImportFile(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      delImportFile(param, fut);
    }));
  }

  public void getImportFilePage(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.getImportFilePage(param, resultHandler);
  }

  public Single<String> rxGetImportFilePage(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getImportFilePage(param, fut);
    }));
  }

  public void getImportFileList(JsonObject param, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.getImportFileList(param, resultHandler);
  }

  public Single<List<JsonObject>> rxGetImportFileList(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getImportFileList(param, fut);
    }));
  }

  public void getImportPhoneList(String param, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.getImportPhoneList(param, resultHandler);
  }

  public Single<List<JsonObject>> rxGetImportPhoneList(String param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getImportPhoneList(param, fut);
    }));
  }


  public static  PassengerService newInstance(service.PassengerService arg) {
    return arg != null ? new PassengerService(arg) : null;
  }
}
