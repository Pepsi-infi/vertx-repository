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

package cache.rxjava;

import java.util.Map;
import rx.Observable;
import rx.Single;
import cache.dto.SeriesCache;
import java.util.List;
import cache.dto.SeriesPage;
import cache.dto.Album;
import cache.dto.PageCache;
import cache.dto.Video;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.rxjava.RxGen(cache.CacheService.class)
public class CacheService {

  public static final io.vertx.lang.rxjava.TypeArg<CacheService> __TYPE_ARG = new io.vertx.lang.rxjava.TypeArg<>(
    obj -> new CacheService((cache.CacheService) obj),
    CacheService::getDelegate
  );

  private final cache.CacheService delegate;
  
  public CacheService(cache.CacheService delegate) {
    this.delegate = delegate;
  }

  public cache.CacheService getDelegate() {
    return delegate;
  }

  /**
   * 获取视频缓存
   * @param uuid 
   * @param videoId 
   * @param resultHandler 
   */
  public void getVideoById(String uuid, Long videoId, Handler<AsyncResult<Video>> resultHandler) { 
    delegate.getVideoById(uuid, videoId, resultHandler);
  }

  /**
   * 获取视频缓存
   * @param uuid 
   * @param videoId 
   * @return 
   */
  public Single<Video> rxGetVideoById(String uuid, Long videoId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getVideoById(uuid, videoId, fut);
    }));
  }

  /**
   * 获取专辑缓存
   * @param uuid 
   * @param albumId 
   * @param resultHandler 
   */
  public void getAlbumById(String uuid, Long albumId, Handler<AsyncResult<Album>> resultHandler) { 
    delegate.getAlbumById(uuid, albumId, resultHandler);
  }

  /**
   * 获取专辑缓存
   * @param uuid 
   * @param albumId 
   * @return 
   */
  public Single<Album> rxGetAlbumById(String uuid, Long albumId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getAlbumById(uuid, albumId, fut);
    }));
  }

  /**
   * 批量获取视频缓存
   * @param uuid 
   * @param videoids 
   * @param resultHandler 
   */
  public void mgetVideoByIds(String uuid, List<Long> videoids, Handler<AsyncResult<List<Video>>> resultHandler) { 
    delegate.mgetVideoByIds(uuid, videoids, resultHandler);
  }

  /**
   * 批量获取视频缓存
   * @param uuid 
   * @param videoids 
   * @return 
   */
  public Single<List<Video>> rxMgetVideoByIds(String uuid, List<Long> videoids) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      mgetVideoByIds(uuid, videoids, fut);
    }));
  }

  /**
   * 批量获取专辑缓存
   * @param uuid 
   * @param albumids 
   * @param resultHandler 
   */
  public void mgetAlbumByIds(String uuid, List<Long> albumids, Handler<AsyncResult<List<Album>>> resultHandler) { 
    delegate.mgetAlbumByIds(uuid, albumids, resultHandler);
  }

  /**
   * 批量获取专辑缓存
   * @param uuid 
   * @param albumids 
   * @return 
   */
  public Single<List<Album>> rxMgetAlbumByIds(String uuid, List<Long> albumids) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      mgetAlbumByIds(uuid, albumids, fut);
    }));
  }

  /**
   * 专辑分页缓存
   * @param uuid 
   * @param albumId 
   * @param categoryId 
   * @param varietyShow 
   * @param resultHandler 
   */
  public void getPageList(String uuid, Long albumId, Integer categoryId, String varietyShow, Handler<AsyncResult<PageCache>> resultHandler) { 
    delegate.getPageList(uuid, albumId, categoryId, varietyShow, resultHandler);
  }

  /**
   * 专辑分页缓存
   * @param uuid 
   * @param albumId 
   * @param categoryId 
   * @param varietyShow 
   * @return 
   */
  public Single<PageCache> rxGetPageList(String uuid, Long albumId, Integer categoryId, String varietyShow) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getPageList(uuid, albumId, categoryId, varietyShow, fut);
    }));
  }

  /**
   * 某页剧集缓存
   * @param uuid 
   * @param albumId 
   * @param page 
   * @param resultHandler 
   */
  public void getSeries(String uuid, Long albumId, Integer page, Handler<AsyncResult<SeriesCache>> resultHandler) { 
    delegate.getSeries(uuid, albumId, page, resultHandler);
  }

  /**
   * 某页剧集缓存
   * @param uuid 
   * @param albumId 
   * @param page 
   * @return 
   */
  public Single<SeriesCache> rxGetSeries(String uuid, Long albumId, Integer page) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getSeries(uuid, albumId, page, fut);
    }));
  }

  /**
   * 专辑剧集及分页
   * @param uuid 
   * @param album 
   * @param page 
   * @param resultHandler 
   */
  public void getSeriesPage(String uuid, Album album, Integer page, Handler<AsyncResult<List<SeriesPage>>> resultHandler) { 
    delegate.getSeriesPage(uuid, album, page, resultHandler);
  }

  /**
   * 专辑剧集及分页
   * @param uuid 
   * @param album 
   * @param page 
   * @return 
   */
  public Single<List<SeriesPage>> rxGetSeriesPage(String uuid, Album album, Integer page) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      getSeriesPage(uuid, album, page, fut);
    }));
  }

  /**
   * 更新视频guava缓存
   * @param uuid 
   * @param videoId 
   * @param resultHandler 
   */
  public void updateGuavaVideo(String uuid, Long videoId, Handler<AsyncResult<Void>> resultHandler) { 
    delegate.updateGuavaVideo(uuid, videoId, resultHandler);
  }

  /**
   * 更新视频guava缓存
   * @param uuid 
   * @param videoId 
   * @return 
   */
  public Single<Void> rxUpdateGuavaVideo(String uuid, Long videoId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateGuavaVideo(uuid, videoId, fut);
    }));
  }

  /**
   * 更新专辑guava缓存
   * @param uuid 
   * @param albumId 
   * @param resultHandler 
   */
  public void updateGuavaAlbum(String uuid, Long albumId, Handler<AsyncResult<Void>> resultHandler) { 
    delegate.updateGuavaAlbum(uuid, albumId, resultHandler);
  }

  /**
   * 更新专辑guava缓存
   * @param uuid 
   * @param albumId 
   * @return 
   */
  public Single<Void> rxUpdateGuavaAlbum(String uuid, Long albumId) { 
    return Single.create(new io.vertx.rx.java.SingleOnSubscribeAdapter<>(fut -> {
      updateGuavaAlbum(uuid, albumId, fut);
    }));
  }


  public static CacheService newInstance(cache.CacheService arg) {
    return arg != null ? new CacheService(arg) : null;
  }
}
