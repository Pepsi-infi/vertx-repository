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

package sysconfig.whitelist.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import sysconfig.whitelist.response.WhiteListResponse;
import sysconfig.whitelist.request.WhiteListRequest;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by wangbingbing on 2016/11/15.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link sysconfig.whitelist.WhiteListService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(sysconfig.whitelist.WhiteListService.class)
public class WhiteListService {

  public static final io.vertx.lang.rxjava.TypeArg<WhiteListService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new WhiteListService((sysconfig.whitelist.WhiteListService) obj),
    WhiteListService::getDelegate
  );

  private final sysconfig.whitelist.WhiteListService delegate;
  
  public WhiteListService(sysconfig.whitelist.WhiteListService delegate) {
    this.delegate = delegate;
  }

  public sysconfig.whitelist.WhiteListService getDelegate() {
    return delegate;
  }

  public void addDevId(String uuid, WhiteListRequest request, Handler<AsyncResult<WhiteListResponse>> resultHandler) { 
    delegate.addDevId(uuid, request, resultHandler);
  }

  public Single<WhiteListResponse> rxAddDevId(String uuid, WhiteListRequest request) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addDevId(uuid, request, fut);
    }));
  }

  public void removeDevId(String uuid, WhiteListRequest request, Handler<AsyncResult<WhiteListResponse>> resultHandler) { 
    delegate.removeDevId(uuid, request, resultHandler);
  }

  public Single<WhiteListResponse> rxRemoveDevId(String uuid, WhiteListRequest request) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      removeDevId(uuid, request, fut);
    }));
  }

  public void retrieveDevId(String uuid, WhiteListRequest request, Handler<AsyncResult<WhiteListResponse>> resultHandler) { 
    delegate.retrieveDevId(uuid, request, resultHandler);
  }

  public Single<WhiteListResponse> rxRetrieveDevId(String uuid, WhiteListRequest request) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      retrieveDevId(uuid, request, fut);
    }));
  }


  public static WhiteListService newInstance(sysconfig.whitelist.WhiteListService arg) {
    return arg != null ? new WhiteListService(arg) : null;
  }
}
