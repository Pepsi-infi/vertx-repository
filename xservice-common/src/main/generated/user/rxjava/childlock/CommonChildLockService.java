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

package user.rxjava.childlock;

import java.util.Map;
import rx.Observable;
import rx.Single;
import user.childlock.request.ChildLockRequest;
import user.childlock.response.ChildLockTpResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(user.childlock.CommonChildLockService.class)
public class CommonChildLockService {

  public static final io.vertx.lang.rxjava.TypeArg<CommonChildLockService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new CommonChildLockService((user.childlock.CommonChildLockService) obj),
    CommonChildLockService::getDelegate
  );

  private final user.childlock.CommonChildLockService delegate;
  
  public CommonChildLockService(user.childlock.CommonChildLockService delegate) {
    this.delegate = delegate;
  }

  public user.childlock.CommonChildLockService getDelegate() {
    return delegate;
  }

  public void setUserChildLock(String uuid, ChildLockRequest childLockRequest, Handler<AsyncResult<ChildLockTpResponse>> handler) { 
    delegate.setUserChildLock(uuid, childLockRequest, handler);
  }

  public Single<ChildLockTpResponse> rxSetUserChildLock(String uuid, ChildLockRequest childLockRequest) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      setUserChildLock(uuid, childLockRequest, fut);
    }));
  }

  public void checkUserChildLock(String uuid, ChildLockRequest childLockRequest, Handler<AsyncResult<ChildLockTpResponse>> handler) { 
    delegate.checkUserChildLock(uuid, childLockRequest, handler);
  }

  public Single<ChildLockTpResponse> rxCheckUserChildLock(String uuid, ChildLockRequest childLockRequest) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      checkUserChildLock(uuid, childLockRequest, fut);
    }));
  }


  public static CommonChildLockService newInstance(user.childlock.CommonChildLockService arg) {
    return arg != null ? new CommonChildLockService(arg) : null;
  }
}
