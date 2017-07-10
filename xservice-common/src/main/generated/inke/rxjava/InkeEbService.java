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

import inke.InkeMetaService;
import rx.Single;
import java.util.List;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by wanglonghu on 17/6/8.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link InkeMetaService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(InkeMetaService.class)
public class InkeEbService {

  public static final io.vertx.lang.rxjava.TypeArg<InkeEbService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new InkeEbService((InkeMetaService) obj),
    InkeEbService::getDelegate
  );

  private final InkeMetaService delegate;
  
  public InkeEbService(InkeMetaService delegate) {
    this.delegate = delegate;
  }

  public InkeMetaService getDelegate() {
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
   * @param resultHandler 
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


  public static InkeEbService newInstance(InkeMetaService arg) {
    return arg != null ? new InkeEbService(arg) : null;
  }
}
