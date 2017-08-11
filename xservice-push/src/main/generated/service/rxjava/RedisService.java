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

  public static RedisService createLocalProxy(Vertx vertx) { 
    RedisService ret = RedisService.newInstance(service.RedisService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.RedisService.getLocalAddress(ip);
    return ret;
  }

  public void set(String key, String value, Handler<AsyncResult<Void>> result) { 
    delegate.set(key, value, result);
  }

  public Single<Void> rxSet(String key, String value) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      set(key, value, fut);
    }));
  }

  public void expire(String key, long expire, Handler<AsyncResult<Long>> result) { 
    delegate.expire(key, expire, result);
  }

  public Single<Long> rxExpire(String key, long expire) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      expire(key, expire, fut);
    }));
  }

  public void get(String key, Handler<AsyncResult<String>> result) { 
    delegate.get(key, result);
  }

  public Single<String> rxGet(String key) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      get(key, fut);
    }));
  }

  public void lpush(String queue, String key, Handler<AsyncResult<Long>> result) { 
    delegate.lpush(queue, key, result);
  }

  public Single<Long> rxLpush(String queue, String key) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      lpush(queue, key, fut);
    }));
  }

  public void rpush(String queue, String key, Handler<AsyncResult<Long>> result) { 
    delegate.rpush(queue, key, result);
  }

  public Single<Long> rxRpush(String queue, String key) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      rpush(queue, key, fut);
    }));
  }


  public static  RedisService newInstance(service.RedisService arg) {
    return arg != null ? new RedisService(arg) : null;
  }
}
