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

package inke.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import inke.dto.AnchorListWapperDto;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by wanglonghu on 17/6/9.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link inke.AnchorService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(inke.AnchorService.class)
public class AnchorService {

  public static final io.vertx.lang.rxjava.TypeArg<AnchorService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new AnchorService((inke.AnchorService) obj),
    AnchorService::getDelegate
  );

  private final inke.AnchorService delegate;
  
  public AnchorService(inke.AnchorService delegate) {
    this.delegate = delegate;
  }

  public inke.AnchorService getDelegate() {
    return delegate;
  }

  public void getOnlineAnchorList(int index, String uuid, Handler<AsyncResult<AnchorListWapperDto>> resultHandler) { 
    delegate.getOnlineAnchorList(index, uuid, resultHandler);
  }

  public Single<AnchorListWapperDto> rxGetOnlineAnchorList(int index, String uuid) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getOnlineAnchorList(index, uuid, fut);
    }));
  }


  public static AnchorService newInstance(inke.AnchorService arg) {
    return arg != null ? new AnchorService(arg) : null;
  }
}
