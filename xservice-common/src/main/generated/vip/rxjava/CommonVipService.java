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

package vip.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import vip.response.GetVipInfoResponse;
import vip.response.PackageInfoResponse;
import vip.response.LecomGetPackageByProductIdResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(vip.CommonVipService.class)
public class CommonVipService {

  public static final io.vertx.lang.rxjava.TypeArg<CommonVipService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new CommonVipService((vip.CommonVipService) obj),
    CommonVipService::getDelegate
  );

  private final vip.CommonVipService delegate;
  
  public CommonVipService(vip.CommonVipService delegate) {
    this.delegate = delegate;
  }

  public vip.CommonVipService getDelegate() {
    return delegate;
  }

  public void getLecomPackageInfo(Integer packageId, Long uid, Handler<AsyncResult<PackageInfoResponse>> result) { 
    delegate.getLecomPackageInfo(packageId, uid, result);
  }

  public Single<PackageInfoResponse> rxGetLecomPackageInfo(Integer packageId, Long uid) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getLecomPackageInfo(packageId, uid, fut);
    }));
  }

  public void lecomGetPackageByProductId(Integer productId, Handler<AsyncResult<LecomGetPackageByProductIdResponse>> resultHandler) { 
    delegate.lecomGetPackageByProductId(productId, resultHandler);
  }

  public Single<LecomGetPackageByProductIdResponse> rxLecomGetPackageByProductId(Integer productId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      lecomGetPackageByProductId(productId, fut);
    }));
  }

  public void getUserAddonInfo(Long uid, Handler<AsyncResult<GetVipInfoResponse>> resultHandler) { 
    delegate.getUserAddonInfo(uid, resultHandler);
  }

  public Single<GetVipInfoResponse> rxGetUserAddonInfo(Long uid) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getUserAddonInfo(uid, fut);
    }));
  }

  public static CommonVipService createProxy(Vertx vertx) { 
    CommonVipService ret = CommonVipService.newInstance(vip.CommonVipService.createProxy(vertx.getDelegate()));
    return ret;
  }


  public static CommonVipService newInstance(vip.CommonVipService arg) {
    return arg != null ? new CommonVipService(arg) : null;
  }
}
