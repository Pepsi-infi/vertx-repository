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

package iservice.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import java.util.List;
import utils.BaseResponse;
import java.util.Map;
import io.vertx.rxjava.core.Vertx;
import iservice.dto.DeviceDto;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/25 13:32
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link iservice.DeviceService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(iservice.DeviceService.class)
public class DeviceService {

  public static final io.vertx.lang.rxjava.TypeArg<DeviceService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new DeviceService((iservice.DeviceService) obj),
    DeviceService::getDelegate
  );

  private final iservice.DeviceService delegate;
  
  public DeviceService(iservice.DeviceService delegate) {
    this.delegate = delegate;
  }

  public iservice.DeviceService getDelegate() {
    return delegate;
  }

  public static DeviceService createProxy(Vertx vertx) { 
    DeviceService ret = DeviceService.newInstance(iservice.DeviceService.createProxy(vertx.getDelegate()));
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

  /**
   * @param param 
   * @param result 
   */
  public void queryDevices(Map<String,String> param, Handler<AsyncResult<List<DeviceDto>>> result) { 
    delegate.queryDevices(param, result);
  }

  /**
   * @param param 
   * @return 
   */
  public Single<List<DeviceDto>> rxQueryDevices(Map<String,String> param) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryDevices(param, fut);
    }));
  }


  public static  DeviceService newInstance(iservice.DeviceService arg) {
    return arg != null ? new DeviceService(arg) : null;
  }
}
