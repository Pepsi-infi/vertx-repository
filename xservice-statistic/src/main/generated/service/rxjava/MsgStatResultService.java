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
import java.util.Map;
import io.vertx.rxjava.core.Vertx;
import service.dto.MsgStatResultPageWrapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/28 17:26
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link service.MsgStatResultService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(service.MsgStatResultService.class)
public class MsgStatResultService {

  public static final io.vertx.lang.rxjava.TypeArg<MsgStatResultService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MsgStatResultService((service.MsgStatResultService) obj),
    MsgStatResultService::getDelegate
  );

  private final service.MsgStatResultService delegate;
  
  public MsgStatResultService(service.MsgStatResultService delegate) {
    this.delegate = delegate;
  }

  public service.MsgStatResultService getDelegate() {
    return delegate;
  }

  public static MsgStatResultService createProxy(Vertx vertx) { 
    MsgStatResultService ret = MsgStatResultService.newInstance(service.MsgStatResultService.createProxy(vertx.getDelegate()));
    return ret;
  }

  /**
   * 持久化push message 数据
   * @param result 
   */
  public void storeMsgStatResult(Handler<AsyncResult<BaseResponse>> result) { 
    delegate.storeMsgStatResult(result);
  }

  /**
   * 持久化push message 数据
   * @return 
   */
  public Single<BaseResponse> rxStoreMsgStatResult() { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      storeMsgStatResult(fut);
    }));
  }

  /**
   * @param param 
   * @param page 
   * @param limit 
   * @param result 
   */
  public void queryMsgStatResult(Map<String,String> param, int page, int limit, Handler<AsyncResult<MsgStatResultPageWrapper>> result) { 
    delegate.queryMsgStatResult(param, page, limit, result);
  }

  /**
   * @param param 
   * @param page 
   * @param limit 
   * @return 
   */
  public Single<MsgStatResultPageWrapper> rxQueryMsgStatResult(Map<String,String> param, int page, int limit) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      queryMsgStatResult(param, page, limit, fut);
    }));
  }


  public static  MsgStatResultService newInstance(service.MsgStatResultService arg) {
    return arg != null ? new MsgStatResultService(arg) : null;
  }
}
