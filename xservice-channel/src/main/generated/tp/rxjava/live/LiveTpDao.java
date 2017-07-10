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

package tp.rxjava.live;

import java.util.Map;
import rx.Observable;
import rx.Single;
import tp.live.response.PlayBillCurrentTpResponse;
import io.vertx.rxjava.core.Vertx;
import tp.live.request.PlayBillCurrentRequest;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import tp.live.request.ProgramWaterMarkRequest;
import tp.live.request.TheaterWaterMarkRequest;


@io.vertx.lang.rxjava.RxGen(tp.live.LiveTpDao.class)
public class LiveTpDao {

  public static final io.vertx.lang.rxjava.TypeArg<LiveTpDao> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new LiveTpDao((tp.live.LiveTpDao) obj),
    LiveTpDao::getDelegate
  );

  private final tp.live.LiveTpDao delegate;
  
  public LiveTpDao(tp.live.LiveTpDao delegate) {
    this.delegate = delegate;
  }

  public tp.live.LiveTpDao getDelegate() {
    return delegate;
  }

  public static LiveTpDao createProxy(Vertx vertx) { 
    LiveTpDao ret = LiveTpDao.newInstance(tp.live.LiveTpDao.createProxy(vertx.getDelegate()));
    return ret;
  }

  public static LiveTpDao createLocalProxy(Vertx vertx) { 
    LiveTpDao ret = LiveTpDao.newInstance(tp.live.LiveTpDao.createLocalProxy(vertx.getDelegate()));
    return ret;
  }

  public static String getLocalAddress() { 
    String ret = tp.live.LiveTpDao.getLocalAddress();
    return ret;
  }

  /**
   * 获取轮播数据
   * @param searchUrl 
   * @param result 
   */
  public void getLunBoDataFromCloudPlatform(String searchUrl, Handler<AsyncResult<String>> result) { 
    delegate.getLunBoDataFromCloudPlatform(searchUrl, result);
  }

  /**
   * 获取轮播数据
   * @param searchUrl 
   * @return 
   */
  public Single<String> rxGetLunBoDataFromCloudPlatform(String searchUrl) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getLunBoDataFromCloudPlatform(searchUrl, fut);
    }));
  }

  /**
   * 获取云平台直播后台直播数据
   * @param searchUrl 
   * @param result 
   */
  public void getZhiBoDataFromCloudPlatform(String searchUrl, Handler<AsyncResult<String>> result) { 
    delegate.getZhiBoDataFromCloudPlatform(searchUrl, result);
  }

  /**
   * 获取云平台直播后台直播数据
   * @param searchUrl 
   * @return 
   */
  public Single<String> rxGetZhiBoDataFromCloudPlatform(String searchUrl) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getZhiBoDataFromCloudPlatform(searchUrl, fut);
    }));
  }

  /**
   * 获取LMS直播数据
   * @param url 
   * @param sourceId 
   * @param offSet 
   * @param fetchSize 
   * @param splatid 
   * @param result 
   */
  public void getZhiBoDataFromLMSBySearchUrl(String url, String sourceId, int offSet, int fetchSize, String splatid, Handler<AsyncResult<String>> result) { 
    delegate.getZhiBoDataFromLMSBySearchUrl(url, sourceId, offSet, fetchSize, splatid, result);
  }

  /**
   * 获取LMS直播数据
   * @param url 
   * @param sourceId 
   * @param offSet 
   * @param fetchSize 
   * @param splatid 
   * @return 
   */
  public Single<String> rxGetZhiBoDataFromLMSBySearchUrl(String url, String sourceId, int offSet, int fetchSize, String splatid) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getZhiBoDataFromLMSBySearchUrl(url, sourceId, offSet, fetchSize, splatid, fut);
    }));
  }

  /**
   * 获取LMS直播数据
   * @param request 
   * @param result 
   */
  public void getProgramWaterMark(ProgramWaterMarkRequest request, Handler<AsyncResult<String>> result) { 
    delegate.getProgramWaterMark(request, result);
  }

  /**
   * 获取LMS直播数据
   * @param request 
   * @return 
   */
  public Single<String> rxGetProgramWaterMark(ProgramWaterMarkRequest request) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getProgramWaterMark(request, fut);
    }));
  }

  public void getTheaterWaterMark(TheaterWaterMarkRequest request, Handler<AsyncResult<String>> result) { 
    delegate.getTheaterWaterMark(request, result);
  }

  public Single<String> rxGetTheaterWaterMark(TheaterWaterMarkRequest request) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getTheaterWaterMark(request, fut);
    }));
  }

  public void getTpPlayBillCurrent(PlayBillCurrentRequest request, Handler<AsyncResult<PlayBillCurrentTpResponse>> result) { 
    delegate.getTpPlayBillCurrent(request, result);
  }

  public Single<PlayBillCurrentTpResponse> rxGetTpPlayBillCurrent(PlayBillCurrentRequest request) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getTpPlayBillCurrent(request, fut);
    }));
  }


  public static LiveTpDao newInstance(tp.live.LiveTpDao arg) {
    return arg != null ? new LiveTpDao(arg) : null;
  }
}
