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
 * NOTE: This class has been automatically generated from the {@link service.PassengerUnSendService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.PassengerUnSendService.class)
public class PassengerUnSendService {

  public static final io.vertx.lang.rxjava.TypeArg<PassengerUnSendService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new PassengerUnSendService((service.PassengerUnSendService) obj),
    PassengerUnSendService::getDelegate
  );

  private final service.PassengerUnSendService delegate;
  
  public PassengerUnSendService(service.PassengerUnSendService delegate) {
    this.delegate = delegate;
  }

  public service.PassengerUnSendService getDelegate() {
    return delegate;
  }

  public static PassengerUnSendService createProxy(Vertx vertx) { 
    PassengerUnSendService ret = PassengerUnSendService.newInstance(service.PassengerUnSendService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void getUnSendMsg(JsonObject param, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.getUnSendMsg(param, resultHandler);
  }

  public Single<List<JsonObject>> rxGetUnSendMsg(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getUnSendMsg(param, fut);
    }));
  }

  public void addUnSendMsg(JsonObject param, Handler<AsyncResult<Integer>> resultHandler) { 
    delegate.addUnSendMsg(param, resultHandler);
  }

  public Single<Integer> rxAddUnSendMsg(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addUnSendMsg(param, fut);
    }));
  }

  public void delExpireUnSendMsg(Handler<AsyncResult<Integer>> resultHandler) { 
    delegate.delExpireUnSendMsg(resultHandler);
  }

  public Single<Integer> rxDelExpireUnSendMsg() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      delExpireUnSendMsg(fut);
    }));
  }

  public void delUnSendMsg(JsonObject param, Handler<AsyncResult<Integer>> resultHandler) { 
    delegate.delUnSendMsg(param, resultHandler);
  }

  public Single<Integer> rxDelUnSendMsg(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      delUnSendMsg(param, fut);
    }));
  }

  public void pushUnSendMsg(String phone, Handler<AsyncResult<String>> resultHandler) { 
    delegate.pushUnSendMsg(phone, resultHandler);
  }

  public Single<String> rxPushUnSendMsg(String phone) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      pushUnSendMsg(phone, fut);
    }));
  }

  public void pushAddUnSendMsg(JsonObject param, Handler<AsyncResult<String>> resultHandler) { 
    delegate.pushAddUnSendMsg(param, resultHandler);
  }

  public Single<String> rxPushAddUnSendMsg(JsonObject param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      pushAddUnSendMsg(param, fut);
    }));
  }


  public static  PassengerUnSendService newInstance(service.PassengerUnSendService arg) {
    return arg != null ? new PassengerUnSendService(arg) : null;
  }
}
