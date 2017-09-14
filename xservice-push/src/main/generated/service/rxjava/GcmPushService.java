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

import rx.Single;
import utils.BaseResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(channel.GcmPushService.class)
public class GcmPushService {

  public static final io.vertx.lang.rxjava.TypeArg<GcmPushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new GcmPushService((channel.GcmPushService) obj),
    GcmPushService::getDelegate
  );

  private final channel.GcmPushService delegate;
  
  public GcmPushService(channel.GcmPushService delegate) {
    this.delegate = delegate;
  }

  public channel.GcmPushService getDelegate() {
    return delegate;
  }

  public static GcmPushService createProxy(Vertx vertx) { 
    GcmPushService ret = GcmPushService.newInstance(channel.GcmPushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static GcmPushService createLocalProxy(Vertx vertx) { 
    GcmPushService ret = GcmPushService.newInstance(channel.GcmPushService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = channel.GcmPushService.getLocalAddress(ip);
    return ret;
  }

  public void sendMsg(JsonObject recieveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) { 
    delegate.sendMsg(recieveMsg, resultHandler);
  }

  public Single<BaseResponse> rxSendMsg(JsonObject recieveMsg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      sendMsg(recieveMsg, fut);
    }));
  }


  public static  GcmPushService newInstance(channel.GcmPushService arg) {
    return arg != null ? new GcmPushService(arg) : null;
  }
}
