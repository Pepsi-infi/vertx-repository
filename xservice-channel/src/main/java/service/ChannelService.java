package service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import search.request.LecomSearchRequest;
import service.dto.channelPage.ChannelPageResponse;
import service.dto.page.PageResponse;
import service.dto.search.SearchResponse;
import service.dto.pageCategory.PageCategoryResponse;
import service.param.CommonParam;

/**
 * Created by zhushenghao1 on 17/5/3.
 */
public interface ChannelService {
    void getShortCut(CommonParam params, Handler<AsyncResult<PageResponse>> handler);

    void addOnPage(int addOnId, int productId, Integer page, Integer pageSize, CommonParam param,
                          Handler<AsyncResult<ChannelPageResponse>> resultHandler);

    void homePage(String history,String cityLevel, CommonParam param, Handler<AsyncResult<ChannelPageResponse>> resultHandler);

    void pageData(String pageId,String history,String citylevel, CommonParam param, Handler<AsyncResult<ChannelPageResponse>> resultHandler);

    void getCategoryPage(String channelId, CommonParam param, Handler<AsyncResult<PageCategoryResponse>> resultHandler);

    void searchData(LecomSearchRequest searchRequest, CommonParam param, Handler<AsyncResult<SearchResponse>> resultHandler);

}
