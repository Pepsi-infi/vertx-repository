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
import java.util.List;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by wanglonghu on 17/6/8.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link inke.InkeMetaService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(inke.InkeMetaService.class)
public class InkeMetaService {

  public static final io.vertx.lang.rxjava.TypeArg<InkeMetaService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new InkeMetaService((inke.InkeMetaService) obj),
    InkeMetaService::getDelegate
  );

  private final inke.InkeMetaService delegate;
  
  public InkeMetaService(inke.InkeMetaService delegate) {
    this.delegate = delegate;
  }

  public inke.InkeMetaService getDelegate() {
    return delegate;
  }

  /**
   * 根据主播id获得主播信息
   * @param anchorId 
   * @param resultHandler 
   */
  public void getAnchorById(String anchorId, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.getAnchorById(anchorId, resultHandler);
  }

  /**
   * 根据主播id获得主播信息
   * @param anchorId 
   * @return 
   */
  public Single<JsonObject> rxGetAnchorById(String anchorId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getAnchorById(anchorId, fut);
    }));
  }

  /**
   * 获取在线主播列表(分页)
   * @param page 
   * @param pageSize 
   * @param resultHandler : InkeAnchor.java
   */
  public void getOnlineAnchors(int page, int pageSize, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.getOnlineAnchors(page, pageSize, resultHandler);
  }

  /**
   * 获取在线主播列表(分页)
   * @param page 
   * @param pageSize 
   * @return 
   */
  public Single<List<JsonObject>> rxGetOnlineAnchors(int page, int pageSize) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getOnlineAnchors(page, pageSize, fut);
    }));
  }

  public void getOnlineAnchorsOrderByInke(int page, int pageSize, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.getOnlineAnchorsOrderByInke(page, pageSize, resultHandler);
  }

  public Single<List<JsonObject>> rxGetOnlineAnchorsOrderByInke(int page, int pageSize) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getOnlineAnchorsOrderByInke(page, pageSize, fut);
    }));
  }


  public static InkeMetaService newInstance(inke.InkeMetaService arg) {
    return arg != null ? new InkeMetaService(arg) : null;
  }
}
