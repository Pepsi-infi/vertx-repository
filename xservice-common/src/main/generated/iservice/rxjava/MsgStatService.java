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
import io.vertx.rxjava.core.Vertx;
import iservice.dto.MsgStatDto;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/25 13:32
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link iservice.MsgStatService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(iservice.MsgStatService.class)
public class MsgStatService {

  public static final io.vertx.lang.rxjava.TypeArg<MsgStatService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MsgStatService((iservice.MsgStatService) obj),
    MsgStatService::getDelegate
  );

  private final iservice.MsgStatService delegate;
  
  public MsgStatService(iservice.MsgStatService delegate) {
    this.delegate = delegate;
  }

  public iservice.MsgStatService getDelegate() {
    return delegate;
  }

  public static MsgStatService createProxy(Vertx vertx) { 
    MsgStatService ret = MsgStatService.newInstance(iservice.MsgStatService.createProxy(vertx.getDelegate()));
    return ret;
  }

  /**
   * @param msgStatDtos 
   * @param result 
   */
  public void statPushMsg(List<MsgStatDto> msgStatDtos, Handler<AsyncResult<String>> result) { 
    delegate.statPushMsg(msgStatDtos, result);
  }

  /**
   * @param msgStatDtos 
   * @return 
   */
  public Single<String> rxStatPushMsg(List<MsgStatDto> msgStatDtos) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      statPushMsg(msgStatDtos, fut);
    }));
  }


  public static  MsgStatService newInstance(iservice.MsgStatService arg) {
    return arg != null ? new MsgStatService(arg) : null;
  }
}
