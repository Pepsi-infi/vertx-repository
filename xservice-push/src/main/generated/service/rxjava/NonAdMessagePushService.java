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


@io.vertx.lang.rxjava.RxGen(service.NonAdMessagePushService.class)
public class NonAdMessagePushService {

  public static final io.vertx.lang.rxjava.TypeArg<NonAdMessagePushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new NonAdMessagePushService((service.NonAdMessagePushService) obj),
    NonAdMessagePushService::getDelegate
  );

  private final service.NonAdMessagePushService delegate;
  
  public NonAdMessagePushService(service.NonAdMessagePushService delegate) {
    this.delegate = delegate;
  }

  public service.NonAdMessagePushService getDelegate() {
    return delegate;
  }

  public static NonAdMessagePushService createProxy(Vertx vertx) { 
    NonAdMessagePushService ret = NonAdMessagePushService.newInstance(service.NonAdMessagePushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static NonAdMessagePushService createLocalProxy(Vertx vertx) { 
    NonAdMessagePushService ret = NonAdMessagePushService.newInstance(service.NonAdMessagePushService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.NonAdMessagePushService.getLocalAddress(ip);
    return ret;
  }

  public void pushMsg(String senderId, String senderKey, String httpMsg, Handler<AsyncResult<String>> resultHandler) { 
    delegate.pushMsg(senderId, senderKey, httpMsg, resultHandler);
  }

  public Single<String> rxPushMsg(String senderId, String senderKey, String httpMsg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      pushMsg(senderId, senderKey, httpMsg, fut);
    }));
  }


  public static  NonAdMessagePushService newInstance(service.NonAdMessagePushService arg) {
    return arg != null ? new NonAdMessagePushService(arg) : null;
  }
}
