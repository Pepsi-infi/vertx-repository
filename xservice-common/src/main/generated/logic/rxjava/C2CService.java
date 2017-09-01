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

package logic.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(logic.C2CService.class)
public class C2CService {

  public static final io.vertx.lang.rxjava.TypeArg<C2CService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new C2CService((logic.C2CService) obj),
    C2CService::getDelegate
  );

  private final logic.C2CService delegate;
  
  public C2CService(logic.C2CService delegate) {
    this.delegate = delegate;
  }

  public logic.C2CService getDelegate() {
    return delegate;
  }

  public static C2CService createProxy(Vertx vertx) { 
    C2CService ret = C2CService.newInstance(logic.C2CService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void doWithFileUpload(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.doWithFileUpload(msg, resultHandler);
  }

  public Single<JsonObject> rxDoWithFileUpload(JsonObject msg) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      doWithFileUpload(msg, fut);
    }));
  }


  public static  C2CService newInstance(logic.C2CService arg) {
    return arg != null ? new C2CService(arg) : null;
  }
}
