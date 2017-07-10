package service.impl;

import org.apache.commons.lang.StringUtils;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Vertx;
import search.SearchService;
import search.param.ParamForSearch;
import search.request.LecomSearchRequest;
import search.request.SearchRequest;
import search.response.SearchMixResultTp;
import tp.cms.constants.CmsTpConstant;
import tp.search.SearchTpDao;
import tp.search.impl.SearchTpDaoImpl;
import xservice.BaseServiceVerticle;

public class SearchServiceImpl extends BaseServiceVerticle implements SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

    private SearchTpDao searchTpDao;

    public SearchServiceImpl(Vertx vertx) {
        searchTpDao = new SearchTpDaoImpl(vertx.createHttpClient());;
    }


    @Override
    public void searchTypes(String uuid, SearchRequest request, Handler<AsyncResult<SearchMixResultTp>> resultHandler) {
        log.info("searchTypes");
        Future<SearchMixResultTp> future = Future.future();
        searchTpDao.searchTypes(request, future.completer());
        future.setHandler(ar -> {
            if (ar.succeeded()) {
                resultHandler.handle(Future.succeededFuture(ar.result()));
            }
        });
    }

    @Override
    public void searchAddOnData(String uuid, ParamForSearch params, Integer productId, Integer pageNum,
            Integer pageSize, String dataType, Handler<AsyncResult<SearchMixResultTp>> resultHandler) {
        SearchRequest request = new LecomSearchRequest();
        setAddOnRequest(request, params, productId, pageNum, pageSize, dataType);
        this.searchTypes(uuid, request, resultHandler);
    }

    private void setAddOnRequest(SearchRequest request, ParamForSearch params, Integer productId, Integer pageNum,
            Integer pageSize, String dataType) {
        request.setPn(pageNum);
        request.setPs(pageSize);
        request.setDt(dataType);
        if (productId != null) {
            request.setVipIds(productId.toString());
        }
        // 固定
        request.setPh(CmsTpConstant.CMSCopyright.SEARCH_MOBILE_COPYRIGHT);
        request.setSrc(SearchRequest.SEARCH_PARAM_SRC_LETV);
        // from
        request.setRegion(params.getAreaCode());
        request.setLang(params.getLangcode());
        request.setUid(params.getUid());
        request.setSales_area(params.getImeiArea());
        request.setUser_setting_country(params.getCountryArea());
        String clientIp = params.getIp();
        if (StringUtils.isNotBlank(clientIp)) {
//            request.setCity_info(IpAddrUtil.getCityInfo(clientIp));
        }
    }
}
