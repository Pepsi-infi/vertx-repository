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

package persistence.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(persistence.MongoService.class)
public class MongoService {

  public static final io.vertx.lang.rxjava.TypeArg<MongoService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MongoService((persistence.MongoService) obj),
    MongoService::getDelegate
  );

  private final persistence.MongoService delegate;
  
  public MongoService(persistence.MongoService delegate) {
    this.delegate = delegate;
  }

  public persistence.MongoService getDelegate() {
    return delegate;
  }

  public static MongoService createProxy(Vertx vertx) { 
    MongoService ret = MongoService.newInstance(persistence.MongoService.createProxy(vertx.getDelegate()));
    return ret;
  }

  /**
   * json format: {collection: "", data: {}}
   * @param json 
   * @param resultHandler 
   */
  public void saveData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.saveData(json, resultHandler);
  }

  /**
   * json format: {collection: "", data: {}}
   * @param json 
   * @return 
   */
  public Single<JsonObject> rxSaveData(JsonObject json) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      saveData(json, fut);
    }));
  }

  /**
   * TODO json format: {collection: "", data: {}}
   * @param json 
   * @param resultHandler 
   */
  public void updateData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.updateData(json, resultHandler);
  }

  /**
   * TODO json format: {collection: "", data: {}}
   * @param json 
   * @return 
   */
  public Single<JsonObject> rxUpdateData(JsonObject json) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateData(json, fut);
    }));
  }


  public static  MongoService newInstance(persistence.MongoService arg) {
    return arg != null ? new MongoService(arg) : null;
  }
}
