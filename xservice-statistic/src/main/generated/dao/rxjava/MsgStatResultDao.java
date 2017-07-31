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
import service.dto.MsgStatResultDto;
import utils.BaseResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lufei
 * Date : 2017/7/30 21:58
 * Description :
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link dao.MsgStatResultDao original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(dao.MsgStatResultDao.class)
public class MsgStatResultDao {

  public static final io.vertx.lang.rxjava.TypeArg<MsgStatResultDao> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new MsgStatResultDao((dao.MsgStatResultDao) obj),
    MsgStatResultDao::getDelegate
  );

  private final dao.MsgStatResultDao delegate;
  
  public MsgStatResultDao(dao.MsgStatResultDao delegate) {
    this.delegate = delegate;
  }

  public dao.MsgStatResultDao getDelegate() {
    return delegate;
  }

  public static MsgStatResultDao createProxy(Vertx vertx) { 
    MsgStatResultDao ret = MsgStatResultDao.newInstance(dao.MsgStatResultDao.createProxy(vertx.getDelegate()));
    return ret;
  }

  public void addMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<BaseResponse>> resultHandler) { 
    delegate.addMsgStatResult(msgStatResultDto, resultHandler);
  }

  public Single<BaseResponse> rxAddMsgStatResult(MsgStatResultDto msgStatResultDto) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      addMsgStatResult(msgStatResultDto, fut);
    }));
  }


  public static  MsgStatResultDao newInstance(dao.MsgStatResultDao arg) {
    return arg != null ? new MsgStatResultDao(arg) : null;
  }
}
