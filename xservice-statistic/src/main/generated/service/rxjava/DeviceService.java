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
import service.dto.DeviceDto;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/25 13:32
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.DeviceService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.DeviceService.class)
public class DeviceService {

  public static final io.vertx.lang.rxjava.TypeArg<DeviceService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new DeviceService((service.DeviceService) obj),
    DeviceService::getDelegate
  );

  private final service.DeviceService delegate;
  
  public DeviceService(service.DeviceService delegate) {
    this.delegate = delegate;
  }

  public service.DeviceService getDelegate() {
    return delegate;
  }

  public static DeviceService createProxy(Vertx vertx) { 
    DeviceService ret = DeviceService.newInstance(service.DeviceService.createProxy(vertx.getDelegate()));
    return ret;
  }

  /**
   * @param userDeviceDto 
   * @param result 
   */
  public void reportUserDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> result) { 
    delegate.reportUserDevice(userDeviceDto, result);
  }

  /**
   * @param userDeviceDto 
   * @return 
   */
  public Single<BaseResponse> rxReportUserDevice(DeviceDto userDeviceDto) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      reportUserDevice(userDeviceDto, fut);
    }));
  }


  public static  DeviceService newInstance(service.DeviceService arg) {
    return arg != null ? new DeviceService(arg) : null;
  }
}
