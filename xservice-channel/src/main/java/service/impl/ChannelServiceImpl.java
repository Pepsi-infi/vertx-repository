package service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

import cache.CacheService;
import cache.constants.CacheConstants;
import cache.dto.Album;
import cache.dto.Video;
import cms.CmsService;
import cms.response.CmsBlockContent;
import cms.response.CmsBlockContentTpResponse;
import cms.response.CmsBlockTpResponse;
import cms.response.CmsPageTpResponse;
import cms.response.CmsPageTpResponseFrag;
import constants.ChannelConstants;
import constants.CommonConstants;
import function.Functional;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Vertx;
import search.request.LecomSearchRequest;
import search.request.SearchRequest;
import search.response.SearchMixResult;
import search.response.SearchMixResultTp;
import service.ChannelService;
import service.dto.channelPage.AddOnInfoDto;
import service.dto.channelPage.BaseDto;
import service.dto.channelPage.BlockDataDto;
import service.dto.channelPage.ChannelBlockDto;
import service.dto.channelPage.ChannelPage;
import service.dto.channelPage.ChannelPageResponse;
import service.dto.channelPage.RectRetrieve;
import service.dto.page.ChannelInfo;
import service.dto.page.ChannelList;
import service.dto.page.PageResponse;
import service.dto.pageCategory.Channel;
import service.dto.pageCategory.ChannelData;
import service.dto.pageCategory.JumpData;
import service.dto.pageCategory.Page;
import service.dto.pageCategory.PageCategoryResponse;
import service.dto.search.AlbumInfoDto;
import service.dto.search.FilterResultDto;
import service.dto.search.SearchResponse;
import service.param.CommonParam;
import service.param.ParseDataParam;
import tp.cms.CmsTpDao;
import tp.cms.constants.CmsTpConstant;
import tp.cms.impl.CmsTpDaoImpl;
import tp.cms.response.CmsMutilangDataResponse;
import tp.rec.RecTpDao;
import tp.rec.constants.RecTpConstant;
import tp.rec.impl.RecTpDaoImpl;
import tp.rec.request.LeTVRecRequest;
import tp.rec.request.LecomRecRequest;
import tp.rec.request.RecBaseRequest;
import tp.rec.response.RecData;
import tp.rec.response.RecResponse;
import tp.rec.response.RecommendTpResponse;
import tp.search.SearchTpDao;
import tp.search.constants.SearchTpConstant;
import tp.search.impl.SearchTpDaoImpl;
import util.ChannelBlockParsing;
import util.ChannelSkip;
import utils.BaseResponse;
import utils.CalendarUtil;
import utils.JsonUtil;
import vip.CommonVipService;
import vip.response.Discount;
import vip.response.PackageInfoResponse;

public class ChannelServiceImpl implements ChannelService {
    private static final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
    private Vertx vertx;
    private CmsTpDao cmsTpDao;
    private SearchTpDao searchTpDao;
    private RecTpDao recTpDao;

    public ChannelServiceImpl(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.cmsTpDao = new CmsTpDaoImpl(vertx, config);
        this.searchTpDao = new SearchTpDaoImpl(vertx.createHttpClient());
        this.recTpDao = new RecTpDaoImpl(vertx.createHttpClient());
        logger.info("construct: " + this.getClass().getName());
    }

    @Override
    public void getShortCut(CommonParam params, Handler<AsyncResult<PageResponse>> handler){
        Future<CmsMutilangDataResponse> cmsBlockFuture2 = Future.future();
        cmsTpDao.getCmsBlockForLang("7687", "us", cmsBlockFuture2.completer());
        
        long start = System.currentTimeMillis();
        //提前处理参数问题
        List<String> blockIds = ChannelConstants.CMS_SHORTCUT_BLOCK_MAP.get(params.getTerminalApplication());
        if (blockIds.isEmpty()) {
            PageResponse response = new PageResponse();
            this.channelErrorResponse(response, CommonConstants.ErrorCode.ERROR_CODE_CHANNEL_PARAM_ERROR
                    ,"getShortcut blockId are empty;");
            handler.handle(Future.succeededFuture(response));
        }
        else {
            //批量请求cms板块内容接口
            List<Future> cmsBlockFutureList = new ArrayList<>();
            for (String blockId : blockIds) {
                Future<CmsMutilangDataResponse> cmsBlockFuture = Future.future();
                cmsBlockFutureList.add(cmsBlockFuture);
                cmsTpDao.getCmsBlockForLang(blockId, params.getLangcode(), cmsBlockFuture.completer());
            }
            CompositeFuture cpFuture = CompositeFuture.all(cmsBlockFutureList);
            cpFuture.setHandler(ar -> {
                PageResponse response = new PageResponse();
                if(ar.failed()){
                    channelWarnResponse(response,null,"cms block get fail");
                }
                else{
                    List<ChannelList> channelList = new ArrayList<>();
                    for (int i = 0; i < cmsBlockFutureList.size(); i++) {
                        CmsMutilangDataResponse cmsMutilangDataResponse = ar.result().resultAt(i);
                        CmsBlockTpResponse cmsBlockTpResponse = cmsMutilangDataResponse.getData();
                        if (cmsBlockTpResponse!=null&&!CollectionUtils.isEmpty(cmsBlockTpResponse.getBlockContent())) {
                            channelList.add(this.getDtoList(cmsBlockTpResponse));
                        }
                    }
                    response.setData(channelList);
                }
                logger.info("ChannelService::getShortcut SUCCESSFULLY : DURING=" + (System.currentTimeMillis() - start));
                handler.handle(Future.succeededFuture(response));
            });
        }
    }

    @Override
    public void addOnPage(int addOnId, int productId, Integer page, Integer pageSize, CommonParam param,
                          Handler<AsyncResult<ChannelPageResponse>> handler) {
        Long uid = Long.parseLong(param.getUid());
        logger.info("addOn ID ="+addOnId+";productId="+productId+"; page="+page+"; uid="+uid);
        if (addOnId== -1) {
            ChannelPageResponse channelPageResponse = new ChannelPageResponse();
            this.channelErrorResponse(channelPageResponse,CommonConstants.ErrorCode.ERROR_CODE_CHANNEL_PARAM_ERROR
                    ,"ERROR_CODE_CHANNEL_PARAM_ERROR");
            handler.handle(Future.succeededFuture(channelPageResponse));
        } else {
            ChannelPageResponse channelPageResponse = new ChannelPageResponse();
            CommonVipService.createProxy(vertx.getDelegate());
            handler.handle(Future.succeededFuture(channelPageResponse));
        }
    }
    @Override
    public void homePage(String history, String cityLevel, CommonParam param,
                         Handler<AsyncResult<ChannelPageResponse>> resultHandler) {
        String homePageId = ChannelConstants.REC_HOME_PAGE_MAP.get(param.getTerminalApplication());
        pageData(homePageId,history,cityLevel,param,resultHandler);
    }

    @Override
    public void pageData(String pageId, String history, String cityLevel, CommonParam param,
                         Handler<AsyncResult<ChannelPageResponse>> resultHandler) {
        long start = System.currentTimeMillis();
        if (StringUtils.isNotBlank(pageId)) {
            final String channelPageId = "page_cms" + pageId;
            Future<List<RecommendTpResponse>> page = Future.future();
            getPageData(param, channelPageId, cityLevel, page.completer());
            page.setHandler(ar -> {
                if (!ar.succeeded()) {
                    resultHandler.handle(Future.failedFuture(ar.toString()));
                }
            });
            page.compose(pageDataAr -> {
                logger.info("DURING getPageData "+(System.currentTimeMillis()-start));
                Future<List<RecommendTpResponse>> processRecData = Future.future();
                processRecData(pageDataAr, param, processRecData.completer());
                processRecData.setHandler(ar -> {
                    if (!ar.succeeded()) {
                        resultHandler.handle(Future.failedFuture(ar.toString()));
                    }
                });
                return processRecData;
            }).compose(processRecDataAr -> {
                logger.info("DURING processRecData "+(System.currentTimeMillis()-start));
                Future<ChannelPage> fillChannelData = Future.future();
                fillChannelData(processRecDataAr, channelPageId, param, fillChannelData.completer());
                fillChannelData.setHandler(ar -> {
                    if (!ar.succeeded()) {
                        resultHandler.handle(Future.failedFuture(ar.toString()));
                    }
                });
                return fillChannelData;
            }).compose(fillChannelDataAr -> {
                logger.info("DURING fillChannelData "+(System.currentTimeMillis()-start));
                ChannelPageResponse channelPageResponse = setStatus(fillChannelDataAr);
                resultHandler.handle(Future.succeededFuture(channelPageResponse));
                return Future.future();
            });
        } else {
            resultHandler.handle(Future.failedFuture(""));
        }
    }

    @Override
    public void searchData(LecomSearchRequest request, CommonParam commonParam,
                           Handler<AsyncResult<SearchResponse>> resultHandler) {
        SearchResponse searchResponse = new SearchResponse();
        if (request == null) {
            searchResponse.setErrorCode(SearchTpConstant.ERROR_CODE_SEARCH_PARAM_ERROR);
            resultHandler.handle(Future.succeededFuture(searchResponse));
        }
        else {
            request.setExtraParam(this.parseSearchParam(request.getExtraParam()));
            request.setSrc(SearchRequest.SEARCH_PARAM_SRC_LETV); // 检索只允许出现乐视自有版权的数据
            this.processFilterRequest(request, commonParam.getTerminalApplication());
            Future<SearchMixResultTp> future = Future.future();
            future.setHandler(ar -> {
                if (ar.succeeded()) {
                    SearchMixResultTp tpResponse = ar.result();
                    FilterResultDto dto = new FilterResultDto();
                    List<AlbumInfoDto> albumList = new LinkedList<>();
                    dto.setResult(albumList);
                    if (tpResponse != null && tpResponse.getData_count() != 0 && tpResponse.getData_list() != null
                            && tpResponse.getData_list().size() > 0) {

                        List<SearchMixResult> searchList = tpResponse.getData_list();
                        AlbumInfoDto albumInfoDto = null;

                        for (SearchMixResult searchResult : searchList) {
                            albumInfoDto = new AlbumInfoDto();
                            albumInfoDto.setCid(searchResult.getCategory());
                            // albumInfo.setDesc(searchResult.getDescription());
                            albumInfoDto.setEpisodes(searchResult.getEpisodes());
                            albumInfoDto.setIsEnd(searchResult.getIsEnd());
                            albumInfoDto.setName(searchResult.getName());
                            albumInfoDto.setNowEpisodes(searchResult.getNowEpisodes());

                            if ("lecom".equals(commonParam
                                    .getTerminalApplication())) {
                                String pic = searchResult.getImage(400, 225);
                                albumInfoDto.setPic(pic);
                                if ("1".equals(searchResult.getIspay())) {
                                    albumInfoDto.setCornerLabel(ChannelConstants.CornerLabel.IS_PAY);
                                }
                            } else if ("le_auto_letv"
                                    .equals(commonParam.getTerminalApplication())) {
                                Map<String, String> images = new HashMap<>();
                                images.put("pic_1440*810", searchResult.getImages().get("1440*810"));
                                images.put("pic_400*225", searchResult.getImages().get("400*225"));
                                images.put("pic_300*400", searchResult.getImages().get("300*400"));
                                albumInfoDto.setPicAll(images);
                            } else {
                                String pic = searchResult.getImage(400, 300);
                                albumInfoDto.setPic(pic);
                            }

                            albumInfoDto.setImages(searchResult.getImages());
                            albumInfoDto.setPlayCount(searchResult.getPlayCount());
                            albumInfoDto.setDirector(searchResult.getDirector());
                            albumInfoDto.setStar(searchResult.getStars());
                            albumInfoDto.setSubTitle(searchResult.getSubname());
                            albumInfoDto.setUrl(searchResult.getUrl());
                            albumInfoDto.setDataType(searchResult.getDataType());
                            albumInfoDto.setDuration(searchResult.getDuration());
                            albumInfoDto.setSubCategory(searchResult.getSubCategoryName());
                            albumInfoDto.setIsEnd(searchResult.getIsEnd());
                            albumInfoDto.setArea(searchResult.getAreaName());
                            albumInfoDto.setUpdateTime(NumberUtils.toLong(searchResult.getMtime()));

                            SimpleDateFormat sdf = new SimpleDateFormat(CalendarUtil.SHORT_DATE_FORMAT);
                            albumInfoDto.setReleaseDate(sdf.format(new Date(NumberUtils.toLong(searchResult.getReleaseDate()))));

                            if (SearchTpConstant.DATA_TYPE.DATA_TYPE_ALUM.equals(searchResult.getDataType())) { // 专辑
                                albumInfoDto.setPid(searchResult.getAid());
                            } else if (SearchTpConstant.DATA_TYPE.DATA_TYPE_VIDEO.equals(searchResult.getDataType())) { // 视频
                                albumInfoDto.setPid(searchResult.getAid());
                                albumInfoDto.setVid(searchResult.getVid());
                            } else if (SearchTpConstant.DATA_TYPE.DATA_TYPE_SUBJECT.equals(searchResult.getDataType())) { // 专题
                                albumInfoDto.setZid(searchResult.getAid());
                            }
                            albumList.add(albumInfoDto);
                        }
                        dto.setTotal(tpResponse.getData_count());
                        dto.setCurPage(request.getPn());
                        int pageSize = (request.getPs() != null && request.getPs() > 0) ? request.getPs() : albumList
                                .size();
                        if (pageSize != 0 || request.getPn() != null && request.getPn() > 0) {
                            int maxSize = tpResponse.getData_count() / pageSize
                                    + (tpResponse.getData_count() % pageSize == 0 ? 0 : 1);
                            dto.setNextPage((request.getPn() + 1) <= maxSize ? (request.getPn() + 1) : maxSize);
                        }
                    }
                    searchResponse.setData(dto);
                    resultHandler.handle(Future.succeededFuture(searchResponse));
                }
            });
            searchTpDao.searchTypes(request, future.completer());
        }
    }

    @Override
    public void getCategoryPage(String channelCode, CommonParam commonParam,
                                Handler<AsyncResult<PageCategoryResponse>> resultHandler) {
        Future<PageCategoryResponse> pageCategoryFuture = Future.future();
        if ("category".equalsIgnoreCase(channelCode)) {
            String pageId = "1003452469";
            this.getPageDataResponse(pageId, commonParam, pageCategoryFuture.completer());
            pageCategoryFuture.setHandler(pageCategory -> {
                if (pageCategory.succeeded()) {
                    PageCategoryResponse pageCategoryResponse = pageCategory.result();
                    resultHandler.handle(Future.succeededFuture(pageCategoryResponse));
                } else {
                    resultHandler.handle(Future.succeededFuture(null));
                }
            });
        } else {
            resultHandler.handle(Future.succeededFuture(null));
        }
    }

    private <T extends BaseResponse> void channelErrorResponse(T response,String errCode,String message) {
        if(response!=null){
            response.setStatus(CommonConstants.ErrorCode.RESPONSE_FAIL_CODE);
            response.setErrorCode(errCode);
            response.setErrorMessage(message);
        }
    }

    private <T extends BaseResponse> void channelWarnResponse(T response,String errCode,String message) {
        if(response!=null){
            response.setStatus(CommonConstants.ErrorCode.RESPONSE_SUC_CODE);
            response.setErrorCode(errCode);
            response.setErrorMessage(message);
        }
    }

    private ChannelList getDtoList(CmsBlockTpResponse tpResponse) {
        if(CollectionUtils.isEmpty(tpResponse.getBlockContent())){
            return null;
        }
        return this.getChannelPageDto(tpResponse);
    }

    private ChannelList getChannelPageDto(CmsBlockTpResponse channelPage){
        if (channelPage == null || CollectionUtils.isEmpty(channelPage.getBlockContent())) {
            return new ChannelList();
        }
        ChannelList channelPageDto = new ChannelList();
        channelPageDto.setName(channelPage.getName());
        List<CmsBlockContent> blocks = channelPage.getBlockContent();
        List<ChannelInfo> channels = new ArrayList<>();
        Map<Integer, String> searchurl = ChannelSkip.CHANNEL_SKIP_SEARCH_US;
        for (CmsBlockContent block : blocks) {
            // 过滤版权
            if (block.getPushflag() != null
                    && block.getPushflag().contains(CmsTpConstant.CMSCopyright.ANDROID_COPYRIGHT)
                    && StringUtils.isNotBlank(block.getType())) {
                ChannelInfo channel = new ChannelInfo();
                channel.setName(block.getTitle());
                channel.setMzcid(block.getSubTitle());
                channel.setType(block.getUrl()==null?"1":block.getUrl());
                channel.setPic(block.getMobilePic());
                channel.setPic1(block.getPic1());
                channel.setPic2(block.getPic2());
                channel.setUrl(block.getShorDesc());
                channel.setCmsId(block.getId());

                if (StringUtils.isNotBlank(block.getSkipUrl())) {
                    channel.setDataUrl(block.getSkipUrl());
                    channel.setSkipType(ChannelConstants.SkipType.SKIP_TYPE_H5_PAGE);
                } else {
                    Integer cid = null;
                    if (StringUtils.isNotBlank(block.getSubTitle())) {
                        cid = Ints.tryParse(block.getSubTitle());
                    }
                    channel.setDataUrl(ChannelSkip.getChannelSkipUrl(block.getType(), block.getContent(), cid, null));
                    if (cid != null && searchurl.containsKey(cid)) {
                        // 搜索
                        channel.setSkipType(ChannelConstants.SkipType.SEARCH_PAGE);
                    }
                    else if ("searchpage".equals(block.getRemark())) {
                        channel.setSkipType(ChannelConstants.SkipType.SEARCH_PAGE);
                        channel.setDataUrl(ChannelSkip.getLeAutoChannelSkipUrl(block.getSubTitle(), block.getShorDesc()));
                    } else {
                        channel.setSkipType(ChannelConstants.SkipType.NAVIGATION_PAGE);
                    }
                }

                switch (block.getType()) {
                    case CmsTpConstant.CMS_TYPE_CHANNEL_PAGE:
                        //普通频道页
                        channel.setPageid(block.getContent());
                        channels.add(channel);
                        break;
                    case CmsTpConstant.CMS_TYPE_ADDON_PAGE:
                        // addon页面
                        if (StringUtils.isNotBlank(block.getContent())) {
                            channel.setSkipType(ChannelConstants.SkipType.ADDON_PAGE);
                            channel.setAddOnId(block.getContent());
                            channels.add(channel);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        channelPageDto.setChannels(channels);
        return channelPageDto;
    }

    private ChannelPageResponse setStatus(ChannelPage channelPage) {
        ChannelPageResponse response = new ChannelPageResponse();
        if (channelPage != null) {
            response.setData(channelPage);
        } else {
            response = this.setErrorResponse(response, CommonConstants.ErrorCode.ERROR_CODE_HOMEPAGE_DATA_ERROR);
            ChannelPage dto = new ChannelPage();
            String backUrl = "/s/s/mobile/retry/lecom/home/homePage.json";
            dto.setBackUrl(backUrl);
            response.setData(dto);
        }
        return response;
    }

    private void fillChannelData(List<RecommendTpResponse> recResponseList, String pageId, CommonParam commonParam,
            Handler<AsyncResult<ChannelPage>> resultHandler) {
        List<Long> albumCacheKeys = new ArrayList<>();
        List<Long> videoCacheKeys = new ArrayList<>();
        List<Long> keys = new ArrayList<>();
        for (RecommendTpResponse recResponse : recResponseList) {
            Map<String, List<Long>> cacheKeys = null;
            if (recResponse.getRec() != null && recResponse.getRec().size() > 0) {
                cacheKeys = getBlockDataKeys(recResponse);
            }
            if (cacheKeys != null) {
                if (cacheKeys.get("albumCacheKeys") != null) {
                    albumCacheKeys.addAll(cacheKeys.get("albumCacheKeys"));
                }
                if (cacheKeys.get("videoCacheKeys") != null) {
                    videoCacheKeys.addAll(cacheKeys.get("videoCacheKeys"));
                }
                if (cacheKeys.get("keys") != null) {
                    keys.addAll(cacheKeys.get("keys"));
                }
            }
        }

        if (!CollectionUtils.isEmpty(keys)) {
            Future<Map<String, Object>> future = Future.future();
            long t1 = System.currentTimeMillis();
            getCacheFormCbase(albumCacheKeys, videoCacheKeys, future.completer());
            future.setHandler(cachesResult -> {
                logger.info("getCacheFormCbase waste all: " + (System.currentTimeMillis() - t1));
                if (cachesResult.succeeded()) {
                    Map<String, Object> caches = cachesResult.result();
                    ChannelPage dto = new ChannelPage();
                    List<ChannelBlockDto> blockList = new LinkedList<>();
                    for (RecommendTpResponse recTp : recResponseList) {
                        String contentStyle = recTp.getContentStyle();
                        switch (contentStyle) {
                            case RecTpConstant.ContentStyle.CONTENT_SYTLE_FOCUS:
                                List<BaseDto> focusBlock = this.parseBlockData(recTp, caches, commonParam, pageId,
                                        false);
                                if (!CollectionUtils.isEmpty(focusBlock)) {
                                    dto.setFocus(focusBlock);
                                }
                                break;
                            default:
                                ChannelBlockDto channelBlockDto = this.parseChannelBlock(recTp);
                                List<BaseDto> blockDatas = this.parseBlockData(recTp, caches, commonParam, pageId, false);
                                channelBlockDto.setBlockType(ChannelConstants.DATA_TYPE_CHANNEL);
                                channelBlockDto.setList(blockDatas);
                                blockList.add(channelBlockDto);
                                break;
                        }
                    }
                    if (blockList.size() > 0) {
                        dto.setBlock(blockList);
                    }
                    resultHandler.handle(Future.succeededFuture(dto));
                }
                else {
                    resultHandler.handle(Future.failedFuture("get Cache failed"));
                }
            });
        }
    }

    private void processRecData(List<RecommendTpResponse> recResponseList, CommonParam param,
            Handler<AsyncResult<List<RecommendTpResponse>>> resultHandler) {
        List<Future> futures = new ArrayList<>();
        for (RecommendTpResponse recResponse : recResponseList) {
            if (StringUtils.isNotBlank(recResponse.getContentStyle())
                    && CmsTpConstant.CMS_CONTENT_TYPE_SEARCH.equals(recResponse.getContentType())) {
                // 页面类型为搜索需要补数据
                Future<RecommendTpResponse> future = Future.future();
                futures.add(future);
                this.fillSearchChannelData(recResponse, param, future.completer());
            }
        }
        CompositeFuture cpFuture = CompositeFuture.all(futures);
        cpFuture.setHandler(ar -> {
            if (ar.succeeded()) {
                resultHandler.handle(Future.succeededFuture(recResponseList));
            } else {
                resultHandler.handle(Future.failedFuture(""));
            }
        });
    }

    private void getPageData(CommonParam param, String pageId, String cityLevel,
            Handler<AsyncResult<List<RecommendTpResponse>>> resultHandler) {
        if (StringUtils.isNotBlank(pageId)) {
            {
                RecBaseRequest recRequest = new LecomRecRequest();
                if("le".equals(param.getTerminalApplication())){
                    recRequest = new LeTVRecRequest();
                }
                recRequest.setPageid(pageId);
                recRequest.setRandom(Math.random());
                recRequest.setCitylevel(cityLevel);
                String ip = param.getIp();
                if (StringUtils.isNotBlank(ip)) {
//                    String region = IpAddrUtil.getCountryCode(ip).toLowerCase();
                    String region = "us";
                    recRequest.setRegion(region);
                }
                recRequest.setRom_country(param.getImeiArea());
                recRequest.setUser_country(param.getCountryArea());
                recRequest.setLang("en_us");
                // 个性化推荐
                recRequest.setHistory(param.getHistory());
                recRequest.setUid(param.getUid());
                recRequest.setLc(param.getDevId());
                Future<RecResponse> future = Future.future();
                recTpDao.getMultiBlocksResult(recRequest, future.completer());
                future.setHandler(ar -> {
                    if (ar.succeeded()) {
                        List<RecommendTpResponse> recDataList = new ArrayList<>();
                        for(int i=1;i<=ar.result().size();i++){
                            recDataList.add(ar.result().get("rec_"+i));
                        }
                        resultHandler.handle(Future.succeededFuture(recDataList));
                    } else {
                        resultHandler.handle(Future.failedFuture(ar.cause()));
                    }
                });
            }
        } else {
            resultHandler.handle(Future.failedFuture("fail"));
        }
    }


    private LecomSearchRequest getAddOnDataRequest(Integer productId, Integer page, Integer pageSize, CommonParam param) {
        LecomSearchRequest searchRequest = new LecomSearchRequest();
        searchRequest.setPh(CmsTpConstant.CMSCopyright.SEARCH_MOBILE_COPYRIGHT);
        searchRequest.setDt("1,2");
        searchRequest.setPn(page);
        searchRequest.setPs(pageSize > 0 ? pageSize : 99);
        searchRequest.setUid(param.getUid());
        searchRequest.setRegion(param.getAreaCode());
        searchRequest.setSales_area(param.getImeiArea());
        searchRequest.setUser_setting_country(param.getCountryArea());
        searchRequest.setVipIds(productId.toString());
        searchRequest.setLang(param.getLangcode());
        String clientIp = param.getIp();
        searchRequest.setClient_ip(clientIp);
        if (StringUtils.isNotBlank(clientIp)) {
//            String city_info = IpAddrUtil.getCityInfo(clientIp);
            String city_info = "us";
            searchRequest.setCity_info(city_info);
        }
        searchRequest.setOr("7");
        searchRequest.setSrc(SearchRequest.SEARCH_PARAM_SRC_LETV);
        // 检索只允许出现乐视自有版权的数据
        searchRequest.setExtraParam(parseSearchParam(searchRequest.getExtraParam()));
        return searchRequest;
    }

    private String parseSearchParam(String param) {
        String result = null;
        if (param != null) {
            result = param.replace(":", "=").replace(";", "&");
        }
        return result;
    }

    private ChannelBlockDto getAddOnInfo(PackageInfoResponse packageInfoResponse) {
        AddOnInfoDto addOnInfoDto = new AddOnInfoDto();
        addOnInfoDto.setProductId(packageInfoResponse.getProductId());
        addOnInfoDto.setAddOnId(packageInfoResponse.getId());
        addOnInfoDto.setAddOnName(packageInfoResponse.getName());
        addOnInfoDto.setAddOnLogoPic(packageInfoResponse.getPic());
        addOnInfoDto.setAddOnDesc(packageInfoResponse.getDesc());
        addOnInfoDto.setAddOnPrice(packageInfoResponse.getPrice().toString());
        addOnInfoDto.setAddOnSubscribeStatus(packageInfoResponse.getSubscribeStatus());
        addOnInfoDto.setEndTime(packageInfoResponse.getEndTime());

        List<AddOnInfoDto.AddOnPriceInfo> addOnPricesInfo = this.fillAddOnPricesInfo(packageInfoResponse.getPrice(),
                packageInfoResponse.getDiscountInfos());
        addOnInfoDto.setAddOnPricesInfo(addOnPricesInfo);

        ChannelBlockDto block = new ChannelBlockDto();
        List<BaseDto> focusList = new ArrayList<>();
        focusList.add(addOnInfoDto);
        block.setList(focusList);
        block.setStyle(RecTpConstant.ContentStyle.CONTENT_STYLE_LECOM_ADDON_SUBSCRIBE);
        block.setBlockType(ChannelConstants.DATA_TYPE_ADDON);
        return block;
    }

    private ChannelBlockDto getAddOnData(LecomSearchRequest searchRequest, SearchMixResultTp tpResponse) {
        return getAddOnData(searchRequest.getPn(),searchRequest.getPs(),tpResponse);
    }

    private ChannelBlockDto getAddOnData(Integer pn,Integer ps, SearchMixResultTp tpResponse) {
        List<BaseDto> focusList = new ArrayList<>();
        Integer total = null;
        Integer nextPage = null;
        if (tpResponse != null && tpResponse.getData_count() != 0 && tpResponse.getData_list() != null
                && tpResponse.getData_list().size() > 0) {
            for (SearchMixResult searchResult : tpResponse.getData_list()) {
                BlockDataDto focus = new BlockDataDto();
                focus.setDefaultStream(null);
                focus.setAt(BlockDataDto.HOME_ACTION_TYPE_FULL);
                focus.setPid(searchResult.getAid());
                focus.setVid(searchResult.getVid());
                focus.setName(searchResult.getName());
                focus.setSubTitle(searchResult.getSubname());
                focus.setCid(searchResult.getCategory());
                focus.setType(String.valueOf(searchResult.getDataType()));
                focus.setEpisode(searchResult.getEpisodes());
                focus.setNowEpisodes(searchResult.getNowEpisodes());
                focus.setIsEnd(searchResult.getIsEnd());
                focus.setDirector(searchResult.getDirector());
                focus.setSinger(searchResult.getStars());
                if (StringUtils.isNotBlank(searchResult.getMtime())) {
                    focus.setUpdateTime(Long.parseLong(searchResult.getMtime()));
                }
                focus.setDuration(searchResult.getDuration());
                if (StringUtils.isNotBlank(searchResult.getRating())) {
                    focus.setScore(Float.parseFloat(searchResult.getRating()));
                }
                focus.setSubCategory(searchResult.getSubCategoryName());
                focus.setPay(searchResult.getIspay());
                focus.setSource(ChannelConstants.DataSource.DATA_SOURCE_SEARCH);
                focus.setArea(searchResult.getAreaName());
                if (StringUtils.isNotBlank(searchResult.getReleaseDate())) {
                    long releaseDate = Long.parseLong(searchResult.getReleaseDate());
                    SimpleDateFormat sdf = new SimpleDateFormat(CalendarUtil.SHORT_DATE_FORMAT);
                    focus.setReleaseDate(sdf.format(new Date(releaseDate)));
                }

                Map<String, String> picMap = new HashMap<>();
                String pic = searchResult.getImages().get("300*400");
                picMap.put("pic_3_4", pic);
                picMap.put("pic_4_3", searchResult.getImages().get("400*300"));

                focus.setPic(pic);
                focus.setPicAll(picMap);
                focusList.add(focus);
            }

            total = tpResponse.getData_count();
            int pageSize = (ps != null && ps > 0) ? ps : focusList.size();
            if (pageSize != 0 || pn != null && pn > 0) {
                int maxSize = total / pageSize + (total % pageSize == 0 ? 0 : 1);
                nextPage = (pn + 1) <= maxSize ? (pn + 1) : maxSize;
            }
        }
        ChannelBlockDto channelBlockDto = null;
        if (!CollectionUtils.isEmpty(focusList)) {
            channelBlockDto = new ChannelBlockDto();
            channelBlockDto.setList(focusList);
            channelBlockDto.setCurPage(pn);
            channelBlockDto.setTotal(total);
            channelBlockDto.setNextPage(nextPage);
            channelBlockDto.setBlockType(ChannelConstants.DATA_TYPE_CHANNEL);
        }
        return channelBlockDto;
    }

    private List<AddOnInfoDto.AddOnPriceInfo> fillAddOnPricesInfo(Double originPrice, List<Discount> discountsInfo) {
        List<AddOnInfoDto.AddOnPriceInfo> addOnPricesInfo = new ArrayList<>();
        if (discountsInfo != null) {
            if (originPrice != null) {
                // 有原价和折扣价,添加原价和折扣价
                addOriginPrice(addOnPricesInfo, originPrice, true);
                addDiscountsInfo(addOnPricesInfo, discountsInfo);
            } else {
                // 只有折扣价,添加折扣价
                addDiscountsInfo(addOnPricesInfo, discountsInfo);
            }
        } else {
            if (originPrice != null) {
                // 只有原价,添加原价
                addOriginPrice(addOnPricesInfo, originPrice, false);
            }
        }
        return addOnPricesInfo;
    }

    private void addOriginPrice(List<AddOnInfoDto.AddOnPriceInfo> addOnPricesInfo, Double originPrice,
            boolean hasDiscountInfo) {
        AddOnInfoDto.AddOnPriceInfo addOnPriceInfo;
        String listPrice;
        if (hasDiscountInfo) {
            listPrice = ChannelConstants.DISCOUNT_PRICE_NAME_MAP.get(ChannelConstants.AddOn.NOT_VIP_TYPE).replaceFirst(
                    "PRICE", parsePrice(originPrice));
        } else {
            listPrice = "$" + parsePrice(originPrice) + "/month";
        }

        addOnPriceInfo = new AddOnInfoDto.AddOnPriceInfo();
        addOnPriceInfo.setPriceInfo(listPrice);
        addOnPriceInfo.setPriceType(ChannelConstants.AddOn.NOT_VIP_TYPE);
        addOnPricesInfo.add(addOnPriceInfo);
    }

    private void addDiscountsInfo(List<AddOnInfoDto.AddOnPriceInfo> addOnPricesInfo, List<Discount> discountsInfo) {
        AddOnInfoDto.AddOnPriceInfo addOnPriceInfo;
        String ecoPassPrice;

        Set<Integer> vipTypes = new HashSet<>();
        Integer vipType;
        Integer vipReTurnType;
        Double price;
        for (Discount discountInfo : discountsInfo) {
            vipType = discountInfo.getVipType();
            price = discountInfo.getPrice();
            // 如果没传vipType或vipPrice则不展示
            if (vipType != null && price != null) {
                // 确保一种vipType只传一次
                if (!vipTypes.contains(vipType)) {
                    vipTypes.add(vipType);
                    vipReTurnType = ChannelConstants.DISCOUNT_PRICE_TYPE_MAP.get(vipType);
                    // getMessage
                    ecoPassPrice = ChannelConstants.DISCOUNT_PRICE_NAME_MAP.get(vipReTurnType);
                    if (ecoPassPrice != null) {
                        ecoPassPrice = ecoPassPrice.replaceFirst("PRICE", parsePrice(price));
                    }
                    // 如果没取到配置文件中的配置文案
                    else {
                        ecoPassPrice = "EcoPass Price $PRICE/month".replaceFirst("PRICE", parsePrice(price));
                    }
                    addOnPriceInfo = new AddOnInfoDto.AddOnPriceInfo();
                    addOnPriceInfo.setPriceInfo(ecoPassPrice);
                    addOnPriceInfo.setPriceType(vipReTurnType);
                    addOnPricesInfo.add(addOnPriceInfo);
                }
            }
        }
    }

    private static String parsePrice(Double d) {
        if (d != null) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(d);
        }
        return null;
    }

    private void fillSearchChannelData(RecommendTpResponse recTpRes, CommonParam commonParam,
            Handler<AsyncResult<RecommendTpResponse>> resultHandler) {
        RecommendTpResponse recommendTpResponse = new RecommendTpResponse();
        resultHandler.handle(Future.succeededFuture(recommendTpResponse));
        if (recTpRes.getContentTotal() == null) {
            resultHandler.handle(Future.failedFuture("contentTotal is null"));
        }
        if (StringUtils.isBlank(recTpRes.getContentRid())) {
            resultHandler.handle(Future.failedFuture("contentRid is empty"));
        }
        SearchRequest request = getSearchRequest(recTpRes, commonParam);
        processFilterRequest(request, commonParam.getTerminalApplication());
        Future<SearchMixResultTp> future = Future.future();

        searchTpDao.searchTypes(request, future.completer()); // 调用搜索接口获取数据
        future.setHandler(ar -> {
            SearchMixResultTp tpResponse = ar.result();
            recTpRes.setRec(getRecDataList(tpResponse));
            resultHandler.handle(Future.succeededFuture());
        });
    }

    private List<RecData> getRecDataList(SearchMixResultTp tpResponse) {
        List<RecData> supportData = new ArrayList<>();
        if (tpResponse != null && tpResponse.getData_count() != 0 && tpResponse.getData_list() != null
                && tpResponse.getData_list().size() > 0) {
            for (SearchMixResult searchResult : tpResponse.getData_list()) {
                RecData rec = new RecData();
                rec.setSource(ChannelConstants.DataSource.DATA_SOURCE_SEARCH);
                rec.setCid(searchResult.getCategory());
                rec.setEpisodes(searchResult.getEpisodes());
                rec.setIsend(searchResult.getIsEnd());
                rec.setTitle(searchResult.getName());
                String pic = null;
                if (searchResult.getImages() != null) {
                    Map<String, String> imgs = searchResult.getImages();
                    pic = imgs.get("960*540") != null ? imgs.get("960*540") : imgs.get("400*225");
                }
                if (StringUtils.isBlank(pic)) {
                    pic = searchResult.getImage(400, 300);
                }
                rec.setMobilePic(pic);
                rec.setPicList(searchResult.getImages());
                if (searchResult.getPlayCount() != null) {
                    rec.setPlaycount(searchResult.getPlayCount().toString());
                }
                rec.setDirector(searchResult.getDirector());
                rec.setStarring(searchResult.getStars());
                rec.setSubTitle(searchResult.getSubname());
                rec.setUrl(searchResult.getUrl());
                rec.setDuration(searchResult.getDuration());
                rec.setIs_pay(searchResult.getIspay());
                if (StringUtils.isNotBlank(searchResult.getRating())) {
                    rec.setScore(Float.parseFloat(searchResult.getRating()));
                }
                if (SearchTpConstant.DATA_TYPE.DATA_TYPE_ALUM.intValue() == searchResult.getDataType()) { // 专辑
                    rec.setPid(searchResult.getAid());
                    rec.setRec_content_type("pid");
                } else if (SearchTpConstant.DATA_TYPE.DATA_TYPE_VIDEO.intValue() == searchResult.getDataType()) { // 视频
                    rec.setVid(searchResult.getVid());
                    rec.setRec_content_type("vid");
                } else if (SearchTpConstant.DATA_TYPE.DATA_TYPE_SUBJECT.intValue() == searchResult.getDataType()) { // 专题
                    rec.setZid(searchResult.getAid());
                    rec.setRec_content_type("zid");
                }
                rec.setArea(searchResult.getArea());
                supportData.add(rec);
            }
        }
        return supportData;
    }

    private SearchRequest getSearchRequest(RecommendTpResponse recTpRes, CommonParam commonParam) {
        SearchRequest request = new LecomSearchRequest();
        request.setSales_area(commonParam.getImeiArea());
        request.setUser_setting_country(commonParam.getCountryArea());
        request.setPn(1);
        request.setPs(recTpRes.getContentTotal());
        request.setExtraParam(recTpRes.getContentRid());
        request.setSrc(SearchRequest.SEARCH_PARAM_SRC_LETV); // 检索只允许出现乐视自有版权的数据
        request.setCg(recTpRes.getCid());
        request.setPh(CmsTpConstant.CMSCopyright.SEARCH_MOBILE_COPYRIGHT);
        request.setDt("1");
        request.setLang(commonParam.getLangcode());
        request.setRegion(commonParam.getWcode());
        request.setLc(commonParam.getDevId());
        String clientIp = commonParam.getIp();
        request.setClient_ip(clientIp);
        if (StringUtils.isNotBlank(clientIp)) {
            //ToDO
            String city_info = "us";
//                    = IpAddrUtil.getCityInfo(clientIp);
            request.setCity_info(city_info);
        }
        return request;

    }

    private Map<String, List<Long>> getBlockDataKeys(RecommendTpResponse recommendTpResponse) {
        List<Long> albumCacheKeys = new ArrayList<>();
        List<Long> videoCacheKeys = new ArrayList<>();
        List<Long> keys = new ArrayList<>();
        Map<String, List<Long>> cacheKeys = new HashMap<>();
        Long key;

        for (RecData recData : recommendTpResponse.getRec()) {
            String type = getSkipType(recData);
            if (ChannelConstants.DataType.DATA_TYPE_ALBUM.equals(type)) {
                key = recData.getPid() != null ? recData.getPid() : Long.parseLong(recData.getContent());
                if (key != null) {
                    albumCacheKeys.add(key);
                    keys.add(key);
                }
            } else if (ChannelConstants.DataType.DATA_TYPE_VIDEO.equals(type)) {
                key = recData.getVid() != null ? recData.getVid() : Long.parseLong(recData.getContent());
                if (key != null) {
                    videoCacheKeys.add(key);
                    keys.add(key);
                }
            }
        }

        if (albumCacheKeys.size() > 0) {
            cacheKeys.put("albumCacheKeys", albumCacheKeys);
        }
        if (videoCacheKeys.size() > 0) {
            cacheKeys.put("videoCacheKeys", videoCacheKeys);
        }
        if (keys.size() > 0) {
            cacheKeys.put("keys", keys);
        }
        return cacheKeys;
    }

    private void getCacheFormCbase(List<Long> albumCacheKeys, List<Long> videoCacheKeys,Handler<AsyncResult<Map<String, Object>>> resultHandler) {
        // get Data from cache service
        Map<String, Object> caches = new HashMap<>();
        Future<List<Album>> albumFuture = Future.future();
        Future<List<Video>> videoFuture = Future.future();

        logger.info("albumCacheKeys size: " + albumCacheKeys.size());
        logger.info("videoCacheKeys size: " + videoCacheKeys.size());
        
        // 取专辑的缓存数据
        if (albumCacheKeys.size() > 0) {
            long t1 = System.currentTimeMillis();
            CacheService cacheService = XProxyHelper.createProxy(ChannelServiceImpl.class, CacheService.class, vertx.getDelegate(), CacheService.SERVICE_ADDRESS);
//            cacheService.mgetAlbumByIds("", albumCacheKeys, albumFuture.completer());
            this.mgetAlbumByIds(cacheService,"", albumCacheKeys, albumFuture.completer());
            albumFuture.setHandler(albumResult->{
                logger.info("mgetAlbumByIds waste: " + (System.currentTimeMillis() - t1));
              logger.info("mgetAlbumByIds keys: " + albumCacheKeys);
              if(albumResult.succeeded()){
                  List<Album> albumList = albumResult.result();
                  if (!CollectionUtils.isEmpty(albumList)){
                      for (Album album : albumList) {
                          if(album!=null){
                              caches.put(CacheConstants.getAlbumKey(album.getId()), album);
                              if (album.getVideoId() != null) {
                                  videoCacheKeys.add(album.getVideoId());
                              }
                          }
                      }
                  }
                  long t2 = System.currentTimeMillis();
//                  cacheService.mgetVideoByIds("", videoCacheKeys, videoFuture.completer());
                  this.mgetVideoByIds(cacheService,"", videoCacheKeys, videoFuture.completer());
                  videoFuture.setHandler(videoResult->{
                      logger.info("mgetVideoByIds waste: " + (System.currentTimeMillis() - t2));
                      logger.info("mgetVideoByIds keys: " + videoCacheKeys);
                      if(videoResult.succeeded() && !CollectionUtils.isEmpty(videoResult.result())){
                          for (Video video : videoResult.result()) {
                              if(video!=null){
                                  caches.put(CacheConstants.getVideoKey(video.getId()), video);
                              }
                          }
                          resultHandler.handle(Future.succeededFuture(caches));
                      }else{
                          resultHandler.handle(Future.succeededFuture(caches));
                          logger.error("mgetVideoByIds failed...");
                      }
                  });
              }else{
                  resultHandler.handle(Future.succeededFuture(caches));
                  logger.error("mgetAlbumByIds failed...");
              }
            });
        } else {
            logger.error("albumCacheKeys.size = 0");
            albumFuture.completer().handle(Future.succeededFuture(new ArrayList<>()));
        }
    }


    private void mgetAlbumByIds(CacheService cacheService,String uuid, List<Long> albumids, Handler<AsyncResult<List<Album>>> resultHandler){
//        cacheService.mgetAlbumByIds("", albumCacheKeys, albumFuture.completer());
        long start = System.currentTimeMillis();
        if(!CollectionUtils.isEmpty(albumids)){
            List<Future<Album>> futures = new ArrayList<>();
            for(Long id : albumids){
                Future<Album> future = Future.future();
                cacheService.getAlbumById(uuid, id, future.completer());
                futures.add(future);
            }
            Future<List<Album>> resultFuture = Functional.sequenceFuture(futures);
            List<Album> albums = new ArrayList<>();
            resultFuture.setHandler(result -> {
                if(result.succeeded()){
                    albums.addAll(result.result());
                }else{
                    logger.info("mget albumids failed! "+result.cause());
                }
                resultHandler.handle(Future.succeededFuture(albums));
                long end = System.currentTimeMillis();
                logger.info("new mget waste time:" + (end - start));
            });
        }else{
            logger.info("ids is null!");
            resultHandler.handle(Future.succeededFuture());
        }
    }

    private void mgetVideoByIds(CacheService cacheService,String uuid, List<Long> videoids, Handler<AsyncResult<List<Video>>> resultHandler){
//        cacheService.mgetAlbumByIds("", albumCacheKeys, albumFuture.completer());
        long start = System.currentTimeMillis();
        if(!CollectionUtils.isEmpty(videoids)){
            List<Future<Video>> futures = new ArrayList<>();
            for(Long id : videoids){
                Future<Video> future = Future.future();
                cacheService.getVideoById(uuid, id, future.completer());
                futures.add(future);
            }
            Future<List<Video>> resultFuture = Functional.sequenceFuture(futures);
            List<Video> videos = new ArrayList<>();
            resultFuture.setHandler(result -> {
                if(result.succeeded()){
                    videos.addAll(result.result());
                }else{
                    logger.info("mget videoids failed! "+result.cause());
                }
                resultHandler.handle(Future.succeededFuture(videos));
                long end = System.currentTimeMillis();
                logger.info("new mget waste time:" + (end - start));
            });
        }else{
            logger.info("ids is null!");
            resultHandler.handle(Future.succeededFuture());
        }
    }


    private void processFilterRequest(SearchRequest searchRequest, String terminalApplication) {
        if (searchRequest != null) {
            String extraParam = searchRequest.getExtraParam();
            Map<String, String> paramMap = this.getSearchParamMap(extraParam);
            if (paramMap != null) {
                // 检索条件中已经拼接了dt,则忽略掉原有的dt参数值
                String dt = paramMap.get("dt");
                if (StringUtils.isNotBlank(dt)) {
                    searchRequest.setDt(null);
                }

                String coopPlatform = paramMap.get("coopPlatform");
                if (StringUtils.isNotBlank(coopPlatform)) {
                    // lecom 特殊处理
                    if ("lecom".equals(terminalApplication)) {
                        String letvCpStr = "670056";
                        StringBuilder cpStringBuild = new StringBuilder(coopPlatform);
                        cpStringBuild.append(",-").append(letvCpStr);
                        for (String cp : coopPlatform.split(",")) {
                            if (cp.equals(letvCpStr)) {
                                // -3 : ",-".length()+1;
                                cpStringBuild.delete(cpStringBuild.length() - letvCpStr.length() - 3,
                                        cpStringBuild.length() - 1);
                                break;
                            }
                        }
                        paramMap.put("coopPlatform", cpStringBuild.toString());

                        StringBuilder mapStringBuild = new StringBuilder();
                        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                            mapStringBuild.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
                        }
                        mapStringBuild.deleteCharAt(mapStringBuild.length() - 1);
                        searchRequest.setExtraParam(mapStringBuild.toString());
                    }
                    searchRequest.setCoopPlatform(null);
                }
            }
        }
    }

    private Map<String, String> getSearchParamMap(String extraParam) {
        // extraParam形如 cg=1&vt=180001
        Map<String, String> map = null;
        if (StringUtils.isNotBlank(extraParam)) {
            String[] params = extraParam.split("&");
            if (params.length > 0) {
                map = new HashMap<>();
                for (String param : params) {
                    if (param != null) {
                        String[] str = param.split("=");
                        if (str.length == 2) {
                            map.put(str[0], str[1]);
                        }
                    }
                }
            }
        }
        return map;
    }

    private String getSkipType(RecData rec) {
        String type = null;
        if (rec != null) {
            if ("true".equals(rec.getIs_rec())) {
                switch (rec.getRec_content_type()) {
                case "pid":
                    type = ChannelConstants.DataType.DATA_TYPE_ALBUM;
                    break;
                case "vid":
                    type = ChannelConstants.DataType.DATA_TYPE_VIDEO;
                    break;
                default:
                    break;
                }
            } else if (StringUtils.isBlank(rec.getIs_rec())
                    && rec.getPushflag() != null
                    && (rec.getPushflag().contains(CmsTpConstant.CMSCopyright.ANDROID_COPYRIGHT) || rec
                            .getPushflag().contains(CmsTpConstant.CMSCopyright.SUPER_ANDROID_COPYRIGHT))) {
                // 数据来自cms
                if (StringUtils.isNotBlank(rec.getType())) {
                    switch (rec.getType()) {
                    case CmsTpConstant.CMS_TYPE_VIDEO:// 视频
                        type = ChannelConstants.DataType.DATA_TYPE_VIDEO;
                        break;
                    case CmsTpConstant.CMS_TYPE_ALBUM:// 专辑
                        type = ChannelConstants.DataType.DATA_TYPE_ALBUM;
                        break;
                    case CmsTpConstant.CMS_TYPE_ADDON_PAGE:
                        type = ChannelConstants.DataType.ADDON_PAGE;
                        break;
                    default:
                        break;
                    }
                }
            } else if (rec.getSource() != null) {
                if (rec.getSource() == ChannelConstants.DataSource.DATA_SOURCE_SEARCH) {
                    // 数据来源是搜索
                    if (rec.getPid() != null) {
                        type = ChannelConstants.DataType.DATA_TYPE_ALBUM;
                    }
                    if (rec.getVid() != null) {// 视频
                        type = ChannelConstants.DataType.DATA_TYPE_VIDEO;
                    }
                }
            }
        }
        return type;
    }

    private List<BaseDto> parseBlockData(RecommendTpResponse recTp, Map<String, Object> caches, CommonParam commonParam,
            String pageId, Boolean supportNavigationLabel) {
        ParseDataParam extraParam = new ParseDataParam();
        extraParam.setPageid(pageId);
        extraParam.setCid(recTp.getCid());
        extraParam.setCms_num(recTp.getNum());
        extraParam.setSupportNavigationLabel(supportNavigationLabel);
        extraParam.setSupportCMSPic169(supportNavigationLabel);
        extraParam.setTerminalApplication(commonParam.getTerminalApplication());
        extraParam.setContentstyle(recTp.getContentStyle());
        if (caches != null) {
            extraParam.setCaches(caches);
        }
        List<RecData> recs = recTp.getRec();
        List<BaseDto> blockDatas = new ArrayList<>();
        if (recs != null) {
            for (RecData rec : recs) {
                String type = getSkipType(rec);
                if (type != null) {
                    // 策略模式
                    ChannelBlockParsing channelBlockParsing = new ChannelBlockParsing();
                    ChannelBlockDto block = channelBlockParsing.parseSkipPolicy(rec, type, commonParam, extraParam);
                    if (block != null) {
                        if (block.getList() != null) {
                            blockDatas.addAll(block.getList());
                        }
                    }
                }
            }
        }
        return blockDatas;
    }

    private ChannelBlockDto parseChannelBlock(RecommendTpResponse recTp) {
        if (recTp == null || recTp.getCms_num() == null || recTp.getNum() == null) {
            return null;
        }
        ChannelBlockDto block = new ChannelBlockDto();
        /*********************** 推荐数据上报字段begin *****************/
        block.setRecFragId(recTp.getFragId());
        block.setRecReid(recTp.getReid());
        block.setRecArea(recTp.getArea());
        block.setRecBucket(recTp.getBucket());
        String recType;
        if (CmsTpConstant.CMS_CONTENT_TYPE_SEARCH.equals(recTp.getContentType())
                || CmsTpConstant.CMS_CONTENT_TYPE_FIXED.equals(recTp.getContentType())) {
            // 如果数据来源是固定和搜索，数据并不来自于推荐
            recType = RecTpConstant.SRC_TYPE.EDITOR;
        } else {
            int total = recTp.getNum() != null ? recTp.getNum(): 0;
            int cmsNum = recTp.getCms_num() != null ? recTp.getCms_num(): 0;
            if (cmsNum == total) {// 全部来自人工推荐
                recType = RecTpConstant.SRC_TYPE.EDITOR;
            } else if (cmsNum == 0) {// 全部自动推荐
                recType = RecTpConstant.SRC_TYPE.AUTO;
            } else {
                recType = RecTpConstant.SRC_TYPE.MIX;
            }
        }
        block.setRecSrcType(recType);
        block.setName(recTp.getBlockname());
        block.setCid(String.valueOf(recTp.getCid()));
        block.setBlockid(recTp.getContentId());
        block.setRectCid(recTp.getRedirectCid());
        block.setRectPageId(recTp.getRedirectPageId());
        block.setRectType(recTp.getRedirectType());
        block.setRectUrl(recTp.getRedirectUrl());
        block.setRectVt(recTp.getRedirectVideoType());
        block.setStyle(recTp.getContentStyle());
        setMainTitleRedirect(recTp.getRedirectType(), recTp, block);
        return block;
    }

    private void setMainTitleRedirect(String redirectType, RecommendTpResponse recTp, ChannelBlockDto block) {
        if (redirectType == null) {
            return;
        }
        switch (redirectType) {
        case ChannelBlockDto.CHANNLE_BLOCK_SKIP_PAGE:
            if (StringUtils.isNotBlank(recTp.getRedirectCid())) {
                Integer skipCid = null;
                try {
                    skipCid = Integer.parseInt(recTp.getRedirectCid());
                    block.setRectCName(ChannelSkip.CHANNEL_MAP.get(skipCid));
                } catch (Exception e) {
                    logger.warn("setMainTitleRedirect exception:"+e);
                }
                block.setDataUrl(ChannelSkip.getChannelSkipUrl(recTp.getRedirectPageId(), skipCid, ""));
            }
            break;
        case ChannelBlockDto.CHANNEL_BLOCK_SKIP_FILTR:
            // 跳转筛选条件
            block.setRectField(this.parseRetrieve(recTp.getRedFieldTypeList(), recTp.getRedFieldDetailList()));
            break;
        default:
            break;
        }
    }

    /**
     * 解析跳转需要的筛选条件
     * @param fTypeList
     * @param fDetailList
     * @return
     */
    private List<RectRetrieve> parseRetrieve(String fTypeList, String fDetailList) {
        if (StringUtils.isBlank(fTypeList) || StringUtils.isBlank(fDetailList)) {
            return null;
        }
        // 跳转筛选，由于cms配置的筛选跳转和筛选条件对应不上，需要进行一个映射转化
        List<RectRetrieve> list = new ArrayList<>();
        RectRetrieve retri = new RectRetrieve();
        retri.setRetrieveKey(ChannelSkip.CHANNEL_CMS_FILTER_MAP.get(fTypeList));
        retri.setRetrieveValue(fDetailList);
        list.add(retri);
        return list;
    }

    private <T extends BaseResponse> T setErrorResponse(T response, String errCode) {
        if (response != null) {
            response.setStatus(CommonConstants.ErrorCode.RESPONSE_FAIL_CODE);
            response.setErrorCode(errCode);
            response.setErrorMessage("message");
        }
        return response;
    }

    private void getPageDataResponse(String cmsPageId, CommonParam commonParam,
            Handler<AsyncResult<PageCategoryResponse>> resultHandler) {
        final PageCategoryResponse pageCategoryResponse = new PageCategoryResponse();
        List<ChannelData> channelDataList = new ArrayList<>();
        HashMap<String, Object> plus = null;
        CmsService cmsService = XProxyHelper.createProxy(ChannelServiceImpl.class, CmsService.class, vertx.getDelegate(),
                CmsService.SERVICE_ADDRESS);
        Future<CmsPageTpResponse> cmsPageFuture = Future.future();
        cmsService.getTvCmsBlock("", cmsPageId, commonParam.getLangcode() == null ? "en" : commonParam.getLangcode(),
                cmsPageFuture.completer());
        cmsPageFuture.setHandler(cmsPage -> {
            if (cmsPage.succeeded()) {
                CmsPageTpResponse cmsPageTpResponse = cmsPage.result();
                if (cmsPageTpResponse != null && cmsPageTpResponse.getData() != null) {
                    List<CmsPageTpResponseFrag> cmsPageTpResponseFragList = cmsPageTpResponse.getData().getFrags();
                    if (cmsPageTpResponseFragList != null && cmsPageTpResponseFragList.size() > 0) {
                        for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponseFragList) {
                            ChannelData channelData = new ChannelData();
                            List<Channel> dataList = new ArrayList<>();
                            channelData.setDataList(dataList);
                            channelData.setUiPlateType(NumberUtils.toInt(cmsPageTpResponseFrag.getContentStyle()));
                            channelData.setTitle(cmsPageTpResponseFrag.getContentName());
                            boolean UiFlag = Boolean.FALSE;
                            if (cmsPageTpResponseFrag.getContentStyle() != null
                                    && ("241".equals(cmsPageTpResponseFrag.getContentStyle()) || "258"
                                            .equals(cmsPageTpResponseFrag.getContentStyle()))) {
                                UiFlag = Boolean.TRUE;
                                channelData.setDataUrl(cmsPageTpResponseFrag.getRedirectUrl());
                                // 复线bug日志
                                logger.info("dataUrl :" + channelData.getDataUrl());
                                channelData.setTitleDataType(ChannelConstants.DataType.DATA_TYPE_MULTILIST_RECOMMENDATION);
                            }
                            List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsPageTpResponseFrag.getBlockContents();
                            if (cmsBlockContentTpResponseList != null && cmsBlockContentTpResponseList.size() > 0) {
                                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                                    if ((cmsBlockContentTpResponse.getPushflag() == null)
                                            || !cmsBlockContentTpResponse.getPushflag().contains( CommonConstants.Platform.TV_PLATFROM_CODE)) {
                                        continue;
                                    }
                                    Channel data = this.getDataFromCms(cmsBlockContentTpResponse);
                                    if (data != null) {
                                        dataList.add(data);
                                    }
                                }
                            }
                            // le
                            // addon列表，客户端第一行显示大图，其他行显示小图。兼容运营不配置第一行数据导致显示错乱的错误。逻辑已告知产品，后期会改版
                            if ("lecom".equalsIgnoreCase(commonParam.getTerminalApplication())) {
                                UiFlag = true;
                            }
                            if (UiFlag || (channelData.getDataList() != null && channelData.getDataList().size() > 0)) {
                                channelDataList.add(channelData);
                            }
                        }
                        pageCategoryResponse.setData(channelDataList);
                        pageCategoryResponse.setPlus(plus);
                        resultHandler.handle(Future.succeededFuture(pageCategoryResponse));
                    } else {
                        resultHandler.handle(Future.succeededFuture(pageCategoryResponse));
                    }
                }
            } else {
            resultHandler.handle(Future.succeededFuture(pageCategoryResponse));
            }
        })      ;
    }

    /**
     * 从CMS单元数据提取
     */
    private Channel getDataFromCms(CmsBlockContentTpResponse cmsBlockContentTpResponse) {
        // 没有推送TV端的数据需要过滤
        if ((cmsBlockContentTpResponse.getPushflag() == null)
                || !cmsBlockContentTpResponse.getPushflag().contains( CommonConstants.Platform.TV_PLATFROM_CODE)) {
            return null;
        }
        Channel baseBlock = null;
        if (StringUtils.isEmpty(cmsBlockContentTpResponse.getContent())) {
            /**
             * 如果content为空，则说明是跳转H5，收银台，功能入口，不跳转的图片等
             */
            baseBlock = this.getCmsDataOfEmptyContent(cmsBlockContentTpResponse);
        }
        return baseBlock;
    }

    private Channel getCmsDataOfEmptyContent(CmsBlockContentTpResponse cmsBlockContentTpResponse) {
        if (StringUtils.isNotEmpty(cmsBlockContentTpResponse.getSkipPage())) {// 跳频道页
            Channel channel = new Channel();
            // service.setChannelId(this.getChannelIdFromCmsChannelId(cmsBlockContentTpResponse.getSkipPage()));
            channel.setChannelId(8888);
            channel.setName(cmsBlockContentTpResponse.getTitle());
            channel.setSubName(cmsBlockContentTpResponse.getSubTitle());
            channel.setImg(cmsBlockContentTpResponse.getTvPic());
            channel.setTitleDataType(ChannelConstants.DataType.DATA_TYPE_CHANNEL);
            channel.setTitleIcon(cmsBlockContentTpResponse.getPic1());
            Page pageTmp = this.getPageBean(cmsBlockContentTpResponse.getShorDesc());
            if (pageTmp != null) {
                if (pageTmp.getPageId() != null) {
                    channel.setPageId(pageTmp.getPageId());
                }
                channel.setTitleFocus1(pageTmp.getTitleFocus1());
                channel.setTitleFocus2(pageTmp.getTitleFocus2());
                channel.setTitleBgColor(pageTmp.getTitleBgColor());
            }
            return bulidJumpObj(channel, ChannelConstants.DataType.DATA_TYPE_CHANNEL);
        }
        return null;
    }

    private Page getPageBean(String str) {
        Page page = null;
        if (!Strings.isNullOrEmpty(str)) {
            try {
                page = JsonUtil.MAPPER.readValue(str, Page.class);
            } catch (Exception e) {
                logger.error("getPageBean failed " + e);
            }
        }
        return page;
    }

    private Channel bulidJumpObj(Channel channel, Integer dataType) {
        JumpData jump = new JumpData();
        Channel channel_skip = new Channel();
        channel_skip.setChannelId(channel.getChannelId());
        channel_skip.setPageId(channel.getPageId());
        channel_skip.setDataUrl(channel.getDataUrl());
        channel_skip.setDataType(dataType);
        channel_skip.setCpCategoryId(channel.getCpCategoryId());
        channel_skip.setCpId(channel.getCpId());
        channel_skip.setGlobalId(channel.getGlobalId());
        jump.setType(dataType);
        jump.setValue(channel_skip);
        channel.setJump(jump);
        return channel;
    }

}
