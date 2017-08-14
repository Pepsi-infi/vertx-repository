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
import utils.BaseResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/8/9 18:37
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.TransferDeviceService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.TransferDeviceService.class)
public class TransferDeviceService {

  public static final io.vertx.lang.rxjava.TypeArg<TransferDeviceService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new TransferDeviceService((service.TransferDeviceService) obj),
    TransferDeviceService::getDelegate
  );

  private final service.TransferDeviceService delegate;
  
  public TransferDeviceService(service.TransferDeviceService delegate) {
    this.delegate = delegate;
  }

  public service.TransferDeviceService getDelegate() {
    return delegate;
  }

  public static TransferDeviceService createProxy(Vertx vertx) { 
    TransferDeviceService ret = TransferDeviceService.newInstance(service.TransferDeviceService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void transferDevice(Handler<AsyncResult<BaseResponse>> result) { 
    delegate.transferDevice(result);
  }

  public Single<BaseResponse> rxTransferDevice() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      transferDevice(fut);
    }));
  }


  public static  TransferDeviceService newInstance(service.TransferDeviceService arg) {
    return arg != null ? new TransferDeviceService(arg) : null;
  }
}
