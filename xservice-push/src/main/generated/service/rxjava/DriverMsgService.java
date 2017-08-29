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
import domain.DriverMsg;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import domain.Page;


@io.vertx.lang.rxjava.RxGen(service.DriverMsgService.class)
public class DriverMsgService {

  public static final io.vertx.lang.rxjava.TypeArg<DriverMsgService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new DriverMsgService((service.DriverMsgService) obj),
    DriverMsgService::getDelegate
  );

  private final service.DriverMsgService delegate;
  
  public DriverMsgService(service.DriverMsgService delegate) {
    this.delegate = delegate;
  }

  public service.DriverMsgService getDelegate() {
    return delegate;
  }

  public static DriverMsgService createProxy(Vertx vertx) { 
    DriverMsgService ret = DriverMsgService.newInstance(service.DriverMsgService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static DriverMsgService createLocalProxy(Vertx vertx) { 
    DriverMsgService ret = DriverMsgService.newInstance(service.DriverMsgService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.DriverMsgService.getLocalAddress(ip);
    return ret;
  }

  public void addDriverMsg(JsonObject msg, Handler<AsyncResult<Integer>> resultHandler) { 
    delegate.addDriverMsg(msg, resultHandler);
  }

  public Single<Integer> rxAddDriverMsg(JsonObject msg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addDriverMsg(msg, fut);
    }));
  }

  public void selectByPage(JsonObject dto, Handler<AsyncResult<Page>> resultHandler) { 
    delegate.selectByPage(dto, resultHandler);
  }

  public Single<Page> rxSelectByPage(JsonObject dto) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      selectByPage(dto, fut);
    }));
  }

  public void getDriverMsgDetail(Long id, Handler<AsyncResult<DriverMsg>> completer) { 
    delegate.getDriverMsgDetail(id, completer);
  }

  public Single<DriverMsg> rxGetDriverMsgDetail(Long id) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getDriverMsgDetail(id, fut);
    }));
  }


  public static  DriverMsgService newInstance(service.DriverMsgService arg) {
    return arg != null ? new DriverMsgService(arg) : null;
  }
}
