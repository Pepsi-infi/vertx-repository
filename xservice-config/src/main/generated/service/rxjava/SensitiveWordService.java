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
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/9/4 11:53
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.SensitiveWordService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.SensitiveWordService.class)
public class SensitiveWordService {

  public static final io.vertx.lang.rxjava.TypeArg<SensitiveWordService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new SensitiveWordService((service.SensitiveWordService) obj),
    SensitiveWordService::getDelegate
  );

  private final service.SensitiveWordService delegate;
  
  public SensitiveWordService(service.SensitiveWordService delegate) {
    this.delegate = delegate;
  }

  public service.SensitiveWordService getDelegate() {
    return delegate;
  }

  public static SensitiveWordService createProxy(Vertx vertx) { 
    SensitiveWordService ret = SensitiveWordService.newInstance(service.SensitiveWordService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void addSensitiveWord(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) { 
    delegate.addSensitiveWord(jsonObject, result);
  }

  public Single<JsonObject> rxAddSensitiveWord(JsonObject jsonObject) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addSensitiveWord(jsonObject, fut);
    }));
  }

  public void getSensitiveWord(int id, Handler<AsyncResult<JsonObject>> result) { 
    delegate.getSensitiveWord(id, result);
  }

  public Single<JsonObject> rxGetSensitiveWord(int id) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getSensitiveWord(id, fut);
    }));
  }

  public void updateSensitiveWord(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) { 
    delegate.updateSensitiveWord(jsonObject, result);
  }

  public Single<JsonObject> rxUpdateSensitiveWord(JsonObject jsonObject) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateSensitiveWord(jsonObject, fut);
    }));
  }

  public void deleteSensitiveWord(int id, Handler<AsyncResult<JsonObject>> result) { 
    delegate.deleteSensitiveWord(id, result);
  }

  public Single<JsonObject> rxDeleteSensitiveWord(int id) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      deleteSensitiveWord(id, fut);
    }));
  }

  public void querySensitiveWord(JsonObject jsonObject, int page, int limit, Handler<AsyncResult<String>> result) { 
    delegate.querySensitiveWord(jsonObject, page, limit, result);
  }

  public Single<String> rxQuerySensitiveWord(JsonObject jsonObject, int page, int limit) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      querySensitiveWord(jsonObject, page, limit, fut);
    }));
  }


  public static  SensitiveWordService newInstance(service.SensitiveWordService arg) {
    return arg != null ? new SensitiveWordService(arg) : null;
  }
}
