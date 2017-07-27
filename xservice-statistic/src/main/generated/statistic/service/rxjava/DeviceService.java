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

package statistic.service.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import statistic.service.dto.DeviceDto;
import utils.BaseResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/25 13:32
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link statistic.service.DeviceService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(statistic.service.DeviceService.class)
public class DeviceService {

  public static final io.vertx.lang.rxjava.TypeArg<DeviceService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new DeviceService((statistic.service.DeviceService) obj),
    DeviceService::getDelegate
  );

  private final statistic.service.DeviceService delegate;
  
  public DeviceService(statistic.service.DeviceService delegate) {
    this.delegate = delegate;
  }

  public statistic.service.DeviceService getDelegate() {
    return delegate;
  }

  public static DeviceService createProxy(Vertx vertx) { 
    DeviceService ret = DeviceService.newInstance(statistic.service.DeviceService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static DeviceService createLocalProxy(Vertx vertx) { 
    DeviceService ret = DeviceService.newInstance(statistic.service.DeviceService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress() { 
    String ret = statistic.service.DeviceService.getLocalAddress();
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


  public static  DeviceService newInstance(statistic.service.DeviceService arg) {
    return arg != null ? new DeviceService(arg) : null;
  }
}
