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

package dao.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import domain.AmqpConsumeMessage;
import utils.BaseResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/26 10:17
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link dao.DeviceDao original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(dao.DeviceDao.class)
public class DeviceDao {

  public static final io.vertx.lang.rxjava.TypeArg<DeviceDao> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new DeviceDao((dao.DeviceDao) obj),
    DeviceDao::getDelegate
  );

  private final dao.DeviceDao delegate;
  
  public DeviceDao(dao.DeviceDao delegate) {
    this.delegate = delegate;
  }

  public dao.DeviceDao getDelegate() {
    return delegate;
  }

  public static DeviceDao createProxy(Vertx vertx) { 
    DeviceDao ret = DeviceDao.newInstance(dao.DeviceDao.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static DeviceDao createLocalProxy(Vertx vertx) { 
    DeviceDao ret = DeviceDao.newInstance(dao.DeviceDao.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = dao.DeviceDao.getLocalAddress(ip);
    return ret;
  }

  public void addMessage(AmqpConsumeMessage dto, Handler<AsyncResult<BaseResponse>> resultHandler) { 
    delegate.addMessage(dto, resultHandler);
  }

  public Single<BaseResponse> rxAddMessage(AmqpConsumeMessage dto) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addMessage(dto, fut);
    }));
  }


  public static  DeviceDao newInstance(dao.DeviceDao arg) {
    return arg != null ? new DeviceDao(arg) : null;
  }
}
