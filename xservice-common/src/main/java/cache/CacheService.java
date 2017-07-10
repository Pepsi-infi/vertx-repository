package cache;

import java.util.List;

import cache.dto.Album;
import cache.dto.PageCache;
import cache.dto.SeriesCache;
import cache.dto.SeriesPage;
import cache.dto.Video;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@VertxGen
@ProxyGen
public interface CacheService {
    /**
     * The name of the event bus service.
     */
    public static String SERVICE_NAME = "cache.eb.service";

    /**
     * The address on which the service is published.
     */
    public static String SERVICE_ADDRESS = "cache-eb-service";

    /**
     * 获取视频缓存
     * @param key
     * @param resultHandler
     */
    public void getVideoById(String uuid, Long videoId, Handler<AsyncResult<Video>> resultHandler);

    /**
     * 获取专辑缓存
     * @param key
     * @param resultHandler
     */
    public void getAlbumById(String uuid, Long albumId, Handler<AsyncResult<Album>> resultHandler);

    /**
     * 批量获取视频缓存
     * @param keys
     * @param resultHandler
     */
    public void mgetVideoByIds(String uuid, List<Long> videoids, Handler<AsyncResult<List<Video>>> resultHandler);

    /**
     * 批量获取专辑缓存
     * @param keys
     * @param resultHandler
     */
    public void mgetAlbumByIds(String uuid, List<Long> albumids, Handler<AsyncResult<List<Album>>> resultHandler);

    /**
     * 专辑分页缓存
     * @param album
     * @param cacheAccess
     * @param resultHandler
     */
    public void getPageList(String uuid, Long albumId , Integer categoryId , String varietyShow, Handler<AsyncResult<PageCache>> resultHandler);

    /**
     * 某页剧集缓存
     * @param albumId
     * @param page
     * @param cacheAccess
     * @param resultHandler
     */
    public void getSeries(String uuid, Long albumId, Integer page, Handler<AsyncResult<SeriesCache>> resultHandler);

    /**
     * 专辑剧集及分页
     * @param album
     * @param page
     * @param resultHandler
     */
    public void getSeriesPage(String uuid, Album album, Integer page,
            Handler<AsyncResult<List<SeriesPage>>> resultHandler);
    
    /**
     * 更新视频guava缓存
     * @param uuid
     * @param albumId
     * @param resultHandler
     */
    public void updateGuavaVideo(String uuid, Long videoId , Handler<AsyncResult<Void>> resultHandler);
    
    /**
     * 更新专辑guava缓存
     * @param uuid
     * @param videoId
     * @param resultHandler
     */
    public void updateGuavaAlbum(String uuid, Long albumId , Handler<AsyncResult<Void>> resultHandler);
}
