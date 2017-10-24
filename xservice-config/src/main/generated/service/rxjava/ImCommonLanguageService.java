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
import java.util.List;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei Date : 2017/8/30 9:58 Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.ImCommonLanguageService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.ImCommonLanguageService.class)
public class ImCommonLanguageService {

  public static final io.vertx.lang.rxjava.TypeArg<ImCommonLanguageService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new ImCommonLanguageService((service.ImCommonLanguageService) obj),
    ImCommonLanguageService::getDelegate
  );

  private final service.ImCommonLanguageService delegate;
  
  public ImCommonLanguageService(service.ImCommonLanguageService delegate) {
    this.delegate = delegate;
  }

  public service.ImCommonLanguageService getDelegate() {
    return delegate;
  }

  public static ImCommonLanguageService createProxy(Vertx vertx) { 
    ImCommonLanguageService ret = ImCommonLanguageService.newInstance(service.ImCommonLanguageService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void addImCommonLanguage(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) { 
    delegate.addImCommonLanguage(jsonObject, result);
  }

  public Single<JsonObject> rxAddImCommonLanguage(JsonObject jsonObject) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addImCommonLanguage(jsonObject, fut);
    }));
  }

  public void getImCommonLanguage(int id, Handler<AsyncResult<JsonObject>> result) { 
    delegate.getImCommonLanguage(id, result);
  }

  public Single<JsonObject> rxGetImCommonLanguage(int id) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getImCommonLanguage(id, fut);
    }));
  }

  public void updateImCommonLanguage(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result) { 
    delegate.updateImCommonLanguage(jsonObject, result);
  }

  public Single<JsonObject> rxUpdateImCommonLanguage(JsonObject jsonObject) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateImCommonLanguage(jsonObject, fut);
    }));
  }

  public void deleteImCommonLanguage(int id, Handler<AsyncResult<JsonObject>> result) { 
    delegate.deleteImCommonLanguage(id, result);
  }

  public Single<JsonObject> rxDeleteImCommonLanguage(int id) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      deleteImCommonLanguage(id, fut);
    }));
  }

  public void queryImCommonLanguage(int type, Handler<AsyncResult<List<JsonObject>>> result) { 
    delegate.queryImCommonLanguage(type, result);
  }

  public Single<List<JsonObject>> rxQueryImCommonLanguage(int type) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryImCommonLanguage(type, fut);
    }));
  }


  public static  ImCommonLanguageService newInstance(service.ImCommonLanguageService arg) {
    return arg != null ? new ImCommonLanguageService(arg) : null;
  }
}
