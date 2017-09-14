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


@io.vertx.lang.rxjava.RxGen(service.ConfigService.class)
public class ConfigService {

  public static final io.vertx.lang.rxjava.TypeArg<ConfigService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new ConfigService((service.ConfigService) obj),
    ConfigService::getDelegate
  );

  private final service.ConfigService delegate;
  
  public ConfigService(service.ConfigService delegate) {
    this.delegate = delegate;
  }

  public service.ConfigService getDelegate() {
    return delegate;
  }

  public static ConfigService createProxy(Vertx vertx) { 
    ConfigService ret = ConfigService.newInstance(service.ConfigService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static ConfigService createLocalProxy(Vertx vertx) { 
    ConfigService ret = ConfigService.newInstance(service.ConfigService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.ConfigService.getLocalAddress(ip);
    return ret;
  }

  /**
   * 消息发送方身份注册
   * @param senderId 
   * @param senderKey 
   * @param resultHandler 
   */
  public void getVerifyFromMsgCenter(String senderId, String senderKey, Handler<AsyncResult<String>> resultHandler) { 
    delegate.getVerifyFromMsgCenter(senderId, senderKey, resultHandler);
  }

  /**
   * 消息发送方身份注册
   * @param senderId 
   * @param senderKey 
   * @return 
   */
  public Single<String> rxGetVerifyFromMsgCenter(String senderId, String senderKey) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getVerifyFromMsgCenter(senderId, senderKey, fut);
    }));
  }


  public static  ConfigService newInstance(service.ConfigService arg) {
    return arg != null ? new ConfigService(arg) : null;
  }
}
