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

package tp.rxjava.cms;

import java.util.Map;
import rx.Observable;
import rx.Single;
import tp.cms.response.CmsMutilangDataResponse;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by zhushenghao1 on 17/1/23.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link tp.cms.CmsTpDao original} non RX-ified interface using Vert.x codegen.
 */

@io.vertx.lang.rxjava.RxGen(tp.cms.CmsTpDao.class)
public class CmsTpDao {

  public static final io.vertx.lang.rxjava.TypeArg<CmsTpDao> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new CmsTpDao((tp.cms.CmsTpDao) obj),
    CmsTpDao::getDelegate
  );

  private final tp.cms.CmsTpDao delegate;
  
  public CmsTpDao(tp.cms.CmsTpDao delegate) {
    this.delegate = delegate;
  }

  public tp.cms.CmsTpDao getDelegate() {
    return delegate;
  }

  public static CmsTpDao createProxy(Vertx vertx) { 
    CmsTpDao ret = CmsTpDao.newInstance(tp.cms.CmsTpDao.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static CmsTpDao createLocalProxy(Vertx vertx) { 
    CmsTpDao ret = CmsTpDao.newInstance(tp.cms.CmsTpDao.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress(String ip) { 
    String ret = tp.cms.CmsTpDao.getLocalAddress(ip);
    return ret;
  }

  public void getCmsBlockForLang(String blockId, String langCode, Handler<AsyncResult<CmsMutilangDataResponse>> resultHandler) { 
    delegate.getCmsBlockForLang(blockId, langCode, resultHandler);
  }

  public Single<CmsMutilangDataResponse> rxGetCmsBlockForLang(String blockId, String langCode) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getCmsBlockForLang(blockId, langCode, fut);
    }));
  }

  public void getCMSDataByPageId(long uuid, String platform, String pageId, String langCode, Handler<AsyncResult<String>> result) { 
    delegate.getCMSDataByPageId(uuid, platform, pageId, langCode, result);
  }

  public Single<String> rxGetCMSDataByPageId(long uuid, String platform, String pageId, String langCode) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getCMSDataByPageId(uuid, platform, pageId, langCode, fut);
    }));
  }

  public void getCMSColumnList(String columnId, String langCode, String platform, Handler<AsyncResult<String>> result) { 
    delegate.getCMSColumnList(columnId, langCode, platform, result);
  }

  public Single<String> rxGetCMSColumnList(String columnId, String langCode, String platform) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getCMSColumnList(columnId, langCode, platform, fut);
    }));
  }


  public static CmsTpDao newInstance(tp.cms.CmsTpDao arg) {
    return arg != null ? new CmsTpDao(arg) : null;
  }
}
