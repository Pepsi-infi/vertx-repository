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

  public void getImportFileList(JsonObject param, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.getImportFileList(param, resultHandler);
  }

  public Single<List<JsonObject>> rxGetImportFileList(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getImportFileList(param, fut);
    }));
  }

  public void getImportPhone(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.getImportPhone(param, resultHandler);
  }

  public Single<String> rxGetImportPhone(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getImportPhone(param, fut);
    }));
  }


  public static  PassengerMessageService newInstance(service.PassengerMessageService arg) {
    return arg != null ? new PassengerMessageService(arg) : null;
  }
}
