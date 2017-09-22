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

package tp.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(tp.TpService.class)
public class TpService {

  public static final io.vertx.lang.rxjava.TypeArg<TpService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new TpService((tp.TpService) obj),
    TpService::getDelegate
  );

  private final tp.TpService delegate;
  
  public TpService(tp.TpService delegate) {
    this.delegate = delegate;
  }

  public tp.TpService getDelegate() {
    return delegate;
  }

  public static TpService createProxy(Vertx vertx) { 
    TpService ret = TpService.newInstance(tp.TpService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void updateOnlineState(String uid, String date, String content, Handler<AsyncResult<String>> result) { 
    delegate.updateOnlineState(uid, date, content, result);
  }

  public Single<String> rxUpdateOnlineState(String uid, String date, String content) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateOnlineState(uid, date, content, fut);
    }));
  }

  public void updateOnlineSimple(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result) { 
    delegate.updateOnlineSimple(uid, date, content, result);
  }

  public Single<String> rxUpdateOnlineSimple(String uid, String date, JsonObject content) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateOnlineSimple(uid, date, content, fut);
    }));
  }


  public static  TpService newInstance(tp.TpService arg) {
    return arg != null ? new TpService(arg) : null;
  }
}
