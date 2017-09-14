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

package channel.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import utils.BaseResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(channel.SocketPushService.class)
public class SocketPushService {

  public static final io.vertx.lang.rxjava.TypeArg<SocketPushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new SocketPushService((channel.SocketPushService) obj),
    SocketPushService::getDelegate
  );

  private final channel.SocketPushService delegate;
  
  public SocketPushService(channel.SocketPushService delegate) {
    this.delegate = delegate;
  }

  public channel.SocketPushService getDelegate() {
    return delegate;
  }

  public static SocketPushService createProxy(Vertx vertx) { 
    SocketPushService ret = SocketPushService.newInstance(channel.SocketPushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static SocketPushService createLocalProxy(Vertx vertx) { 
    SocketPushService ret = SocketPushService.newInstance(channel.SocketPushService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = channel.SocketPushService.getLocalAddress(ip);
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


  public static  SocketPushService newInstance(channel.SocketPushService arg) {
    return arg != null ? new SocketPushService(arg) : null;
  }
}
