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
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(service.AdMessagePushService.class)
public class AdMessagePushService {

  public static final io.vertx.lang.rxjava.TypeArg<AdMessagePushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new AdMessagePushService((service.AdMessagePushService) obj),
    AdMessagePushService::getDelegate
  );

  private final service.AdMessagePushService delegate;
  
  public AdMessagePushService(service.AdMessagePushService delegate) {
    this.delegate = delegate;
  }

  public service.AdMessagePushService getDelegate() {
    return delegate;
  }

  public static AdMessagePushService createProxy(Vertx vertx) { 
    AdMessagePushService ret = AdMessagePushService.newInstance(service.AdMessagePushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static AdMessagePushService createLocalProxy(Vertx vertx) { 
    AdMessagePushService ret = AdMessagePushService.newInstance(service.AdMessagePushService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.AdMessagePushService.getLocalAddress(ip);
    return ret;
  }

  public void pushMsg(String httpMsg, Handler<AsyncResult<String>> resultHandler) { 
    delegate.pushMsg(httpMsg, resultHandler);
  }

  public Single<String> rxPushMsg(String httpMsg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      pushMsg(httpMsg, fut);
    }));
  }


  public static  AdMessagePushService newInstance(service.AdMessagePushService arg) {
    return arg != null ? new AdMessagePushService(arg) : null;
  }
}
