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
import utils.BaseResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(service.ApplePushService.class)
public class ApplePushService {

  public static final io.vertx.lang.rxjava.TypeArg<ApplePushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new ApplePushService((service.ApplePushService) obj),
    ApplePushService::getDelegate
  );

  private final service.ApplePushService delegate;
  
  public ApplePushService(service.ApplePushService delegate) {
    this.delegate = delegate;
  }

  public service.ApplePushService getDelegate() {
    return delegate;
  }

  public static ApplePushService createProxy(Vertx vertx) { 
    ApplePushService ret = ApplePushService.newInstance(service.ApplePushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static ApplePushService createLocalProxy(Vertx vertx) { 
    ApplePushService ret = ApplePushService.newInstance(service.ApplePushService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.ApplePushService.getLocalAddress(ip);
    return ret;
  }

  public void sendMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) { 
    delegate.sendMsg(receiveMsg, resultHandler);
  }

  public Single<BaseResponse> rxSendMsg(JsonObject receiveMsg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      sendMsg(receiveMsg, fut);
    }));
  }


  public static  ApplePushService newInstance(service.ApplePushService arg) {
    return arg != null ? new ApplePushService(arg) : null;
  }
}
