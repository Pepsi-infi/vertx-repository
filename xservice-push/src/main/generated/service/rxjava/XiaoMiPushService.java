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


@io.vertx.lang.rxjava.RxGen(service.XiaoMiPushService.class)
public class XiaoMiPushService {

  public static final io.vertx.lang.rxjava.TypeArg<XiaoMiPushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new XiaoMiPushService((service.XiaoMiPushService) obj),
    XiaoMiPushService::getDelegate
  );

  private final service.XiaoMiPushService delegate;
  
  public XiaoMiPushService(service.XiaoMiPushService delegate) {
    this.delegate = delegate;
  }

  public service.XiaoMiPushService getDelegate() {
    return delegate;
  }

  public static XiaoMiPushService createProxy(Vertx vertx) { 
    XiaoMiPushService ret = XiaoMiPushService.newInstance(service.XiaoMiPushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static XiaoMiPushService createLocalProxy(Vertx vertx) { 
    XiaoMiPushService ret = XiaoMiPushService.newInstance(service.XiaoMiPushService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.XiaoMiPushService.getLocalAddress(ip);
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


  public static  XiaoMiPushService newInstance(service.XiaoMiPushService arg) {
    return arg != null ? new XiaoMiPushService(arg) : null;
  }
}
