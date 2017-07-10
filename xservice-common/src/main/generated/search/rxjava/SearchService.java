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

package search.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import search.param.ParamForSearch;
import search.request.SearchRequest;
import search.response.SearchMixResultTp;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by zhushenghao1 on 17/1/16.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link search.SearchService original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(search.SearchService.class)
public class SearchService {

  public static final io.vertx.lang.rxjava.TypeArg<SearchService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new SearchService((search.SearchService) obj),
    SearchService::getDelegate
  );

  private final search.SearchService delegate;
  
  public SearchService(search.SearchService delegate) {
    this.delegate = delegate;
  }

  public search.SearchService getDelegate() {
    return delegate;
  }

  /**
   * search数据
   * @param uuid 
   * @param request 
   * @param resultHandler 
   */
  public void searchTypes(String uuid, SearchRequest request, Handler<AsyncResult<SearchMixResultTp>> resultHandler) { 
    delegate.searchTypes(uuid, request, resultHandler);
  }

  /**
   * search数据
   * @param uuid 
   * @param request 
   * @return 
   */
  public Single<SearchMixResultTp> rxSearchTypes(String uuid, SearchRequest request) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      searchTypes(uuid, request, fut);
    }));
  }

  /**
   * search addOn 分页数据
   * @param uuid 
   * @param params 
   * @param productId 
   * @param pageNum 
   * @param pageSize 
   * @param dataType 
   * @param resultHandler 
   */
  public void searchAddOnData(String uuid, ParamForSearch params, Integer productId, Integer pageNum, Integer pageSize, String dataType, Handler<AsyncResult<SearchMixResultTp>> resultHandler) { 
    delegate.searchAddOnData(uuid, params, productId, pageNum, pageSize, dataType, resultHandler);
  }

  /**
   * search addOn 分页数据
   * @param uuid 
   * @param params 
   * @param productId 
   * @param pageNum 
   * @param pageSize 
   * @param dataType 
   * @return 
   */
  public Single<SearchMixResultTp> rxSearchAddOnData(String uuid, ParamForSearch params, Integer productId, Integer pageNum, Integer pageSize, String dataType) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      searchAddOnData(uuid, params, productId, pageNum, pageSize, dataType, fut);
    }));
  }


  public static SearchService newInstance(search.SearchService arg) {
    return arg != null ? new SearchService(arg) : null;
  }
}
