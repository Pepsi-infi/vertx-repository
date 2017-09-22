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


@io.vertx.lang.rxjava.RxGen(service.MessagePushService.class)
public class MessagePushService {

  public static final io.vertx.lang.rxjava.TypeArg<MessagePushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MessagePushService((service.MessagePushService) obj),
    MessagePushService::getDelegate
  );

  private final service.MessagePushService delegate;
  
  public MessagePushService(service.MessagePushService delegate) {
    this.delegate = delegate;
  }

  public service.MessagePushService getDelegate() {
    return delegate;
  }

  public static MessagePushService createProxy(Vertx vertx) { 
    MessagePushService ret = MessagePushService.newInstance(service.MessagePushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void bisnessMessage(String recieveMsg, Handler<AsyncResult<String>> resultHandler) { 
    delegate.bisnessMessage(recieveMsg, resultHandler);
  }

  public Single<String> rxBisnessMessage(String recieveMsg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      bisnessMessage(recieveMsg, fut);
    }));
  }


  public static  MessagePushService newInstance(service.MessagePushService arg) {
    return arg != null ? new MessagePushService(arg) : null;
  }
}
