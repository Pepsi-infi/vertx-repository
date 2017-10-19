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

package iservice.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(iservice.ImMessagePushService.class)
public class ImMessagePushService {

  public static final io.vertx.lang.rxjava.TypeArg<ImMessagePushService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new ImMessagePushService((iservice.ImMessagePushService) obj),
    ImMessagePushService::getDelegate
  );

  private final iservice.ImMessagePushService delegate;
  
  public ImMessagePushService(iservice.ImMessagePushService delegate) {
    this.delegate = delegate;
  }

  public iservice.ImMessagePushService getDelegate() {
    return delegate;
  }

  public static ImMessagePushService createProxy(Vertx vertx) { 
    ImMessagePushService ret = ImMessagePushService.newInstance(iservice.ImMessagePushService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static ImMessagePushService createLocalProxy(Vertx vertx) { 
    ImMessagePushService ret = ImMessagePushService.newInstance(iservice.ImMessagePushService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = iservice.ImMessagePushService.getLocalAddress(ip);
    return ret;
  }

  /**
   * 发送im消息
   * @param senderId 发送方身份id(用于签名)
   * @param senderKey 发送方senderKey(用于签名)
   * @param httpMsg 消息体
   * @param resultHandler 异步返回结果
   */
  public void pushMsg(String senderId, String senderKey, String httpMsg, Handler<AsyncResult<String>> resultHandler) { 
    delegate.pushMsg(senderId, senderKey, httpMsg, resultHandler);
  }

  /**
   * 发送im消息
   * @param senderId 发送方身份id(用于签名)
   * @param senderKey 发送方senderKey(用于签名)
   * @param httpMsg 消息体
   * @return 
   */
  public Single<String> rxPushMsg(String senderId, String senderKey, String httpMsg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      pushMsg(senderId, senderKey, httpMsg, fut);
    }));
  }


  public static  ImMessagePushService newInstance(iservice.ImMessagePushService arg) {
    return arg != null ? new ImMessagePushService(arg) : null;
  }
}
