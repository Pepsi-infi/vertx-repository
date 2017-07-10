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

package cms.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import cms.response.CmsBlockTpResponse;
import cms.response.CmsPageTpResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * 暂时不对外提供eventbus服务
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link cms.CmsService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(cms.CmsService.class)
public class CmsService {

  public static final io.vertx.lang.rxjava.TypeArg<CmsService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new CmsService((cms.CmsService) obj),
    CmsService::getDelegate
  );

  private final cms.CmsService delegate;
  
  public CmsService(cms.CmsService delegate) {
    this.delegate = delegate;
  }

  public cms.CmsService getDelegate() {
    return delegate;
  }

  /**
   * 获取cms的版块数据
   * @param uuid 
   * @param blockId 
   * @param resultHandler 
   */
  public void getCmsBlock(String uuid, String blockId, Handler<AsyncResult<CmsBlockTpResponse>> resultHandler) { 
    delegate.getCmsBlock(uuid, blockId, resultHandler);
  }

  /**
   * 获取cms的版块数据
   * @param uuid 
   * @param blockId 
   * @return 
   */
  public Single<CmsBlockTpResponse> rxGetCmsBlock(String uuid, String blockId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getCmsBlock(uuid, blockId, fut);
    }));
  }

  public void getTvCmsBlock(String uuid, String cmsPageId, String langCode, Handler<AsyncResult<CmsPageTpResponse>> resultHandler) { 
    delegate.getTvCmsBlock(uuid, cmsPageId, langCode, resultHandler);
  }

  public Single<CmsPageTpResponse> rxGetTvCmsBlock(String uuid, String cmsPageId, String langCode) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getTvCmsBlock(uuid, cmsPageId, langCode, fut);
    }));
  }


  public static CmsService newInstance(cms.CmsService arg) {
    return arg != null ? new CmsService(arg) : null;
  }
}
