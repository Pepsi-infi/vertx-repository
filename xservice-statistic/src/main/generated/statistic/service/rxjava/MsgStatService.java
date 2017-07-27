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
import statistic.service.dto.MsgStatDto;
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
 * NOTE: This class has been automatically generated from the {@link statistic.service.MsgStatService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(statistic.service.MsgStatService.class)
public class MsgStatService {

  public static final io.vertx.lang.rxjava.TypeArg<MsgStatService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MsgStatService((statistic.service.MsgStatService) obj),
    MsgStatService::getDelegate
  );

  private final statistic.service.MsgStatService delegate;
  
  public MsgStatService(statistic.service.MsgStatService delegate) {
    this.delegate = delegate;
  }

  public statistic.service.MsgStatService getDelegate() {
    return delegate;
  }

  public static MsgStatService createProxy(Vertx vertx) { 
    MsgStatService ret = MsgStatService.newInstance(statistic.service.MsgStatService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static MsgStatService createLocalProxy(Vertx vertx) { 
    MsgStatService ret = MsgStatService.newInstance(statistic.service.MsgStatService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress() { 
    String ret = statistic.service.MsgStatService.getLocalAddress();
    return ret;
  }

  /**
   * 统计push消息
   * @param msgStatDto 
   * @param result 
   */
  public void statPushMsg(MsgStatDto msgStatDto, Handler<AsyncResult<BaseResponse>> result) { 
    delegate.statPushMsg(msgStatDto, result);
  }

  /**
   * 统计push消息
   * @param msgStatDto 
   * @return 
   */
  public Single<BaseResponse> rxStatPushMsg(MsgStatDto msgStatDto) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      statPushMsg(msgStatDto, fut);
    }));
  }


  public static  MsgStatService newInstance(statistic.service.MsgStatService arg) {
    return arg != null ? new MsgStatService(arg) : null;
  }
}
