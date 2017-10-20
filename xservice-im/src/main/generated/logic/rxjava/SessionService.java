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
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(logic.SessionService.class)
public class SessionService {

  public static final io.vertx.lang.rxjava.TypeArg<SessionService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new SessionService((logic.SessionService) obj),
    SessionService::getDelegate
  );

  private final logic.SessionService delegate;
  
  public SessionService(logic.SessionService delegate) {
    this.delegate = delegate;
  }

  public logic.SessionService getDelegate() {
    return delegate;
  }

  public static SessionService createProxy(Vertx vertx) { 
    SessionService ret = SessionService.newInstance(logic.SessionService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void getHandlerIDByUid(String uid, Handler<AsyncResult<String>> resultHandler) { 
    delegate.getHandlerIDByUid(uid, resultHandler);
  }

  public Single<String> rxGetHandlerIDByUid(String uid) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getHandlerIDByUid(uid, fut);
    }));
  }

  public void getUidByHandlerId(String handlerId, Handler<AsyncResult<String>> resultHandler) { 
    delegate.getUidByHandlerId(handlerId, resultHandler);
  }

  public Single<String> rxGetUidByHandlerId(String handlerId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getUidByHandlerId(handlerId, fut);
    }));
  }


  public static  SessionService newInstance(logic.SessionService arg) {
    return arg != null ? new SessionService(arg) : null;
  }
}
