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
import service.param.LiveCommonParam;
import service.dto.cmsPage.CmsPageWrapper;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import service.dto.theaterpack.TheaterPackedWapper;


@io.vertx.lang.rxjava.RxGen(service.LiveService.class)
public class LiveService {

  public static final io.vertx.lang.rxjava.TypeArg<LiveService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new LiveService((service.LiveService) obj),
    LiveService::getDelegate
  );

  private final service.LiveService delegate;
  
  public LiveService(service.LiveService delegate) {
    this.delegate = delegate;
  }

  public service.LiveService getDelegate() {
    return delegate;
  }

  public static LiveService createProxy(Vertx vertx) { 
    LiveService ret = LiveService.newInstance(service.LiveService.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static LiveService createLocalProxy(Vertx vertx) { 
    LiveService ret = LiveService.newInstance(service.LiveService.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress() { 
    String ret = service.LiveService.getLocalAddress();
    return ret;
  }

  public void getProgramListByPageId(long uuid, String pageid, String columnid, String langCode, Handler<AsyncResult<CmsPageWrapper>> result) { 
    delegate.getProgramListByPageId(uuid, pageid, columnid, langCode, result);
  }

  public Single<CmsPageWrapper> rxGetProgramListByPageId(long uuid, String pageid, String columnid, String langCode) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getProgramListByPageId(uuid, pageid, columnid, langCode, fut);
    }));
  }

  public void getTheaterpackWaterMark(String playbillId, String channelId, LiveCommonParam commonParam, String date, Handler<AsyncResult<TheaterPackedWapper>> resultHandler) { 
    delegate.getTheaterpackWaterMark(playbillId, channelId, commonParam, date, resultHandler);
  }

  public Single<TheaterPackedWapper> rxGetTheaterpackWaterMark(String playbillId, String channelId, LiveCommonParam commonParam, String date) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getTheaterpackWaterMark(playbillId, channelId, commonParam, date, fut);
    }));
  }


  public static LiveService newInstance(service.LiveService arg) {
    return arg != null ? new LiveService(arg) : null;
  }
}
