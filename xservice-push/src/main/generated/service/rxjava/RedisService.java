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
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(service.RedisService.class)
public class RedisService {

  public static final io.vertx.lang.rxjava.TypeArg<RedisService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new RedisService((service.RedisService) obj),
    RedisService::getDelegate
  );

  private final service.RedisService delegate;
  
  public RedisService(service.RedisService delegate) {
    this.delegate = delegate;
  }

  public service.RedisService getDelegate() {
    return delegate;
  }

  public static RedisService createProxy(Vertx vertx) { 
    RedisService ret = RedisService.newInstance(service.RedisService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void set(String key, String value, Handler<AsyncResult<BaseResponse>> result) { 
    delegate.set(key, value, result);
  }

  public Single<BaseResponse> rxSet(String key, String value) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      set(key, value, fut);
    }));
  }

  public void expire(String key, long expire, Handler<AsyncResult<BaseResponse>> result) { 
    delegate.expire(key, expire, result);
  }

  public Single<BaseResponse> rxExpire(String key, long expire) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      expire(key, expire, fut);
    }));
  }


  public static  RedisService newInstance(service.RedisService arg) {
    return arg != null ? new RedisService(arg) : null;
  }
}
