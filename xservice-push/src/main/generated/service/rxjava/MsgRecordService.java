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
import domain.MsgRecord;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(service.MsgRecordService.class)
public class MsgRecordService {

  public static final io.vertx.lang.rxjava.TypeArg<MsgRecordService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MsgRecordService((service.MsgRecordService) obj),
    MsgRecordService::getDelegate
  );

  private final service.MsgRecordService delegate;
  
  public MsgRecordService(service.MsgRecordService delegate) {
    this.delegate = delegate;
  }

  public service.MsgRecordService getDelegate() {
    return delegate;
  }

  public static MsgRecordService createProxy(Vertx vertx) { 
    MsgRecordService ret = MsgRecordService.newInstance(service.MsgRecordService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static MsgRecordService createLocalProxy(Vertx vertx) { 
    MsgRecordService ret = MsgRecordService.newInstance(service.MsgRecordService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = service.MsgRecordService.getLocalAddress(ip);
    return ret;
  }

  public void addMessage(MsgRecord dto, Handler<AsyncResult<BaseResponse>> resultHandler) { 
    delegate.addMessage(dto, resultHandler);
  }

  public Single<BaseResponse> rxAddMessage(MsgRecord dto) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addMessage(dto, fut);
    }));
  }


  public static  MsgRecordService newInstance(service.MsgRecordService arg) {
    return arg != null ? new MsgRecordService(arg) : null;
  }
}
