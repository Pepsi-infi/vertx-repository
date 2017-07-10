package service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cache.utils.TimeUtil;
import constants.CacheConstants;
import constants.LiveConstants;
import function.Functional;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import service.LiveService;
import service.dto.cmsPage.CmsCategoryDto;
import service.dto.cmsPage.CmsChannelDto;
import service.dto.cmsPage.CmsPageDto;
import service.dto.cmsPage.CmsPageWrapper;
import service.dto.theaterpack.MaterialInfo;
import service.dto.theaterpack.TheaterPackedWapper;
import service.dto.theaterpack.WaterMarkDeliveryInfo;
import service.dto.theaterpack.WaterMarkInfo;
import service.dto.theaterpack.WaterMarksDto;
import service.param.LiveCommonParam;
import tp.cms.CmsTpDao;
import tp.cms.response.CmsColumnContentResponse;
import tp.cms.response.CmsColumnListResponse;
import tp.cms.response.CmsColumnResponse;
import tp.cms.response.CmsFragResponse;
import tp.cms.response.CmsPageResponse;
import tp.live.LiveTpDao;
import tp.live.request.ProgramWaterMarkRequest;
import tp.live.request.TheaterWaterMarkRequest;
import tp.live.response.ChannelListResponse;
import tp.live.response.ChannelResponse;
import tp.live.response.Doc;
import tp.live.response.LmsDataListResponse;
import tp.live.response.LmsDataResponse;
import tp.live.response.ProgramWaterMarkData;
import tp.live.response.ProgramWaterMarkTpResponse;
import tp.live.response.ResourceItem;
import tp.live.response.TheaterWaterMarkTpResponse;
import tp.live.response.WarterMarkTpResponse;
import tp.live.response.WaterMarkDeliveryTpResponse;
import tp.live.response.ZhiBoDataResponse;
import tp.live.response.ZhiBoDataSetTpResponse;
import utils.BaseResponse;
import utils.SystemCache;

public class LiveServiceImpl extends BaseServiceVerticle implements LiveService {
    private static final Logger logger = LoggerFactory.getLogger(LiveServiceImpl.class);

    private CmsTpDao cmsTpDao;
    
    private LiveTpDao liveTpDao;

    private Cache<String, Object> guawaCache;

    public LiveServiceImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();
        this.publishService(LiveService.SERVICE_NAME, LiveService.SERVICE_ADDRESS);
        this.publishService(LiveService.LOCAL_SERVICE_NAME, LiveService.getLocalAddress());
        
        cmsTpDao = CmsTpDao.createLocalProxy(vertx.getDelegate());
        liveTpDao = LiveTpDao.createLocalProxy(vertx.getDelegate());
        guawaCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES) // 数据被创建多久后被移除
                .maximumSize(10240).build();
    }
    
    private void publishService(String serviceName, String serviceAddress){
        XProxyHelper.registerService(LiveService.class, vertx.getDelegate(), this, serviceAddress);
        publishEventBusService(serviceName, serviceAddress, LiveService.class);
    }

    /**
     * 通过pageId获取简约列表
     */
    @Override
    public void getProgramListByPageId(long uuid, String pageId, String columnId, String langCode,
            Handler<AsyncResult<CmsPageWrapper>> result) {
        long start = SystemCache.currentTimeMillis;

        String key = CacheConstants.getCMSDataByPageId(pageId);
        CmsPageResponse cachedPageResponse = (CmsPageResponse) guawaCache.getIfPresent(key);

        if (cachedPageResponse == null) {
            Future<String> pageTpFuture = Future.future();
            cmsTpDao.getCMSDataByPageId(uuid, "live", pageId, langCode, pageTpFuture.completer());
            pageTpFuture.setHandler(re -> {
                if (re.succeeded()) {
                    logger.info("uuid: " + uuid + " getCMSDataByPageId waste time: " + (SystemCache.currentTimeMillis - start));
                    CmsPageResponse tpResponse = Json.decodeValue(re.result(), CmsPageResponse.class);
                    guawaCache.put(key, tpResponse);
                    this.generateProgramList(tpResponse, columnId, langCode, result, pageId, uuid);
                } else {
                    logger.error("uuid: " + uuid + " getProgramListByPageId failed; " + re.cause());
                    this.setErrorResponse("", new CmsPageWrapper(), result);
                }
            });
        } else {
            logger.info("uuid: " + uuid + " cachedPageResponse waste time: " + (SystemCache.currentTimeMillis - start));
            this.generateProgramList(cachedPageResponse, columnId, langCode, result, pageId, uuid);
        }
    }

    private void generateProgramList(CmsPageResponse cmsPageResponse, String columnId, String langCode,
            Handler<AsyncResult<CmsPageWrapper>> result, String pageid, long uuid) {
        if (cmsPageResponse == null || cmsPageResponse.getCode() != 200 || cmsPageResponse.getData() == null
                || CollectionUtils.isEmpty(cmsPageResponse.getData().getFrags())) {
            logger.warn("uuid: " + uuid + " generateProgramList failed; cmsPageResponse is null");
            result.handle(Future.failedFuture("cmsPageResponse is null"));
        } else {
            long start = System.currentTimeMillis();
            List<CmsFragResponse> frags = cmsPageResponse.getData().getFrags();
            columnId = StringUtils.isBlank(columnId) ? frags.get(0).getContentId() : columnId;
            //1、一级栏目手动配置数据
            CmsPageDto cmsPageDto = new CmsPageDto();
            cmsPageDto.setCategoryList(getCategoryDto(cmsPageResponse, columnId));
            cmsPageDto.setPageId(pageid);

            //2、一级栏目自动数据(searchUrl)
            Future<List<CmsChannelDto>> cloumnOwnChannelFuture = Future.future();
            this.getColumnOwnSearchUrlData(cmsPageResponse, columnId, cloumnOwnChannelFuture.completer());
            logger.info("uuid: " + uuid + " generateOwnData waste time: " + (System.currentTimeMillis() - start));
            
            //3、一级栏目contentId对应的数据
            Future<List<CmsChannelDto>> cloumnChannelFuture = this.getColumnChannelFuture(columnId, langCode, uuid);

            //4、处理future
            this.dealWithFutures(result, columnId, cmsPageDto, cloumnOwnChannelFuture, cloumnChannelFuture);
        }
    }

    private Future<List<CmsChannelDto>> getColumnChannelFuture(String columnId, String langCode, long uuid) {
        Future<List<CmsChannelDto>> cloumnChannelFuture = Future.future();
        long start = System.currentTimeMillis();
        String key = CacheConstants.getCMSColumnListByColumnId(columnId);
        CmsColumnListResponse cachedCmsColumnListResponse = (CmsColumnListResponse) guawaCache.getIfPresent(key);
        if (cachedCmsColumnListResponse == null) {
            Future<String> subColumnListFuture = Future.future();
            // 获取一级栏目conentId中的数据
            cmsTpDao.getCMSColumnList(columnId, langCode, "live", subColumnListFuture.completer());
            subColumnListFuture.setHandler(r -> {
                if (r.succeeded()) {
                    logger.info(uuid + " getCMSColumnList waste time: " + (System.currentTimeMillis() - start));
                    CmsColumnListResponse tpResponse = Json.decodeValue(r.result(), CmsColumnListResponse.class);
                    guawaCache.put(key, tpResponse);
                    this.getCMSColumnChannelList(tpResponse, columnId, cloumnChannelFuture.completer());
                } else {
                    logger.error(uuid + " getCMSColumnList failed; " + r.cause());
                }
            });
        } else {
            logger.info(uuid + " cachedCmsColumnListResponse waste time: " + (System.currentTimeMillis() - start));
            this.getCMSColumnChannelList(cachedCmsColumnListResponse, columnId, cloumnChannelFuture.completer());
        }
        return cloumnChannelFuture;
    }

    /**
     * 处理返回结果
     * 
     * @param result
     * @param columnid
     * @param cmsPageDto
     * @param cloumnOwnChannelFuture
     * @param cloumnChannelFuture
     */
    private void dealWithFutures(Handler<AsyncResult<CmsPageWrapper>> result, String columnid, CmsPageDto cmsPageDto,
            Future<List<CmsChannelDto>> cloumnOwnChannelFuture, Future<List<CmsChannelDto>> cloumnChannelFuture) {
        // 处理
        long start = SystemCache.currentTimeMillis;
        Future<CompositeFuture> compositeFuture = CompositeFuture.all(cloumnOwnChannelFuture, cloumnChannelFuture);
        compositeFuture.setHandler(composite -> {
            if (composite.succeeded()) {
                List<CmsChannelDto> channelList = new ArrayList<>();
                List<CmsChannelDto> columnOwnChannel = composite.result().resultAt(0);
                if (CollectionUtils.isNotEmpty(columnOwnChannel)) {
                    channelList.addAll(columnOwnChannel);
                }

                List<CmsChannelDto> cloumnChannel = composite.result().resultAt(1);
                if (CollectionUtils.isNotEmpty(cloumnChannel)) {
                    channelList.addAll(cloumnChannel);
                }

                try {
                    CmsPageWrapper cmsPageWrapper = new CmsPageWrapper();
                    for (CmsCategoryDto categoryDto : cmsPageDto.getCategoryList()) {
                        if (columnid.equalsIgnoreCase(String.valueOf(categoryDto.getId()))) {
                            if (CollectionUtils.isEmpty(categoryDto.getChannelList())) {
                                categoryDto.setChannelList(channelList);
                            } else {
                                categoryDto.getChannelList().addAll(channelList);
                            }
                            break;
                        }
                    }
                    cmsPageWrapper.setData(cmsPageDto);
                    result.handle(Future.succeededFuture(cmsPageWrapper));
                    logger.info("dealWithFutures waste time: " + (SystemCache.currentTimeMillis - start));
                } catch (Exception e) {
                    result.handle(Future.failedFuture(composite.cause()));
                    logger.error("dealWithFutures EXCEPTION " + e.getMessage());
                }
            } else {
                result.handle(Future.failedFuture(composite.cause()));
            }
        });
    }

    /**
     * 获取一级栏目本身配置的searchUrl中的数据
     * 
     * @param cmsPageResponse
     * @param columnid
     * @param handler
     */
    private void getColumnOwnSearchUrlData(CmsPageResponse cmsPageResponse, String columnid,
            Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        long start = SystemCache.currentTimeMillis;
        int fragSize = cmsPageResponse.getData().getFrags().size();
        for (CmsFragResponse cmsFragResponse : cmsPageResponse.getData().getFrags()) {
            fragSize--;
            if (columnid.equalsIgnoreCase(cmsFragResponse.getContentId())) {
                CmsColumnResponse columnTpDto = cmsFragResponse.getColumnEntity();
                if (2 != columnTpDto.getHasDataSearchAPI() && StringUtils.isNotBlank(columnTpDto.getSearchUrl())) {
                    this.getColumnCmsSearchUrlData(columnTpDto, handler);
                } else {
                    handler.handle(Future.succeededFuture(Collections.emptyList()));
                }
                logger.info("getColumnOwnSearchUrlData waste time: " + (SystemCache.currentTimeMillis - start));
                break;
            } else {
                if (fragSize == 0) {
                    handler.handle(Future.succeededFuture(Collections.emptyList()));
                }
            }
        }
    }

    /**
     * 获取一级分类数据
     * 
     * @param cmsPageResponse
     * @param columnid
     * @return
     */
    private List<CmsCategoryDto> getCategoryDto(CmsPageResponse cmsPageResponse, String columnid) {
        List<CmsCategoryDto> categoryList = new ArrayList<CmsCategoryDto>();
        for (CmsFragResponse cmsFragResponse : cmsPageResponse.getData().getFrags()) {
            List<CmsChannelDto> cmsColumnEntity = null;
            if (cmsFragResponse != null && columnid.equalsIgnoreCase(cmsFragResponse.getContentId())
                    && cmsFragResponse.getColumnEntity() != null && 1 == cmsFragResponse.getColumnEntity().getStatus()
                    && 2 == cmsFragResponse.getColumnEntity().getHasDataSearchAPI()) {
                cmsColumnEntity = this.getCMSColumnEntity(cmsFragResponse.getColumnEntity().getColumnContent());
            }
            categoryList.add(this.getCategoryDto(cmsFragResponse, cmsColumnEntity));
        }
        return categoryList;
    }

    /**
     * 获取一级栏目通过版块id配置的内容
     * 
     * @param subColumnList
     * @param columnid
     * @param handler
     */
    private void getCMSColumnChannelList(CmsColumnListResponse subColumnList, String columnid,
            Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        List<CmsChannelDto> channelList = new ArrayList<CmsChannelDto>();
        if (subColumnList == null || subColumnList.getStatusCode() != 200
                || CollectionUtils.isEmpty(subColumnList.getData())) {
            logger.warn("get subColumnList from CMS is null, columnid: " + columnid);
            handler.handle(Future.succeededFuture(Collections.emptyList()));
        } else {
            List<Future<List<CmsChannelDto>>> allChannelFuture = new ArrayList<>();
            for (CmsColumnResponse cmsColumnResponse : subColumnList.getData()) {
                // 成功
                if (1 == cmsColumnResponse.getStatus()) {
                    // 自动数据
                    if (2 == cmsColumnResponse.getHasDataSearchAPI()) {
                        List<CmsChannelDto> cmsColumnEntity = this
                                .getCMSColumnEntity(cmsColumnResponse.getColumnContent());
                        if (CollectionUtils.isNotEmpty(cmsColumnEntity)) {
                            channelList.addAll(cmsColumnEntity);
                        }
                    } else {
                        // 走searchurl
                        Future<List<CmsChannelDto>> channelListFuture = Future.future();
                        this.getColumnCmsSearchUrlData(cmsColumnResponse, channelListFuture.completer());
                        allChannelFuture.add(channelListFuture);
                    }
                }
            }

            this.dealwithCMSColumnChanneFutureList(handler, channelList, allChannelFuture);
        }
    }

    private void dealwithCMSColumnChanneFutureList(Handler<AsyncResult<List<CmsChannelDto>>> handler,
            List<CmsChannelDto> channelList, List<Future<List<CmsChannelDto>>> allChannelFuture) {
        // 自动数据形成多个future一起执行
        long start = SystemCache.currentTimeMillis;
        Future<List<List<CmsChannelDto>>> resultFuture = Functional.sequenceFuture(allChannelFuture);
        resultFuture.setHandler(re -> {
            if (re.succeeded()) {
                List<List<CmsChannelDto>> resultList = re.result();
                List<CmsChannelDto> allcolumnChannelData = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(resultList)) {
                    for (List<CmsChannelDto> result : resultList) {
                        if (CollectionUtils.isNotEmpty(result)) {
                            allcolumnChannelData.addAll(result);
                        }
                    }
                    allcolumnChannelData.addAll(channelList);
                    handler.handle(Future.succeededFuture(allcolumnChannelData));
                } else {
                    allcolumnChannelData.addAll(channelList);
                    handler.handle(Future.succeededFuture(allcolumnChannelData));
                }
                logger.info("dealwithCMSColumnChanneFutureList waste time: " + (SystemCache.currentTimeMillis - start));
            } else {
                handler.handle(Future.succeededFuture(Collections.emptyList()));
            }
        });
    }

    /**
     * 获得分类的channel 数据
     */
    private void getColumnCmsSearchUrlData(CmsColumnResponse columnTpDto,
            Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        if (columnTpDto != null && columnTpDto.getColumnType() != null) {
            switch (columnTpDto.getColumnType()) {
                case LiveConstants.CMS.COLUMN_TYPE_LUNBO:
                case LiveConstants.CMS.COLUMN_TYPE_WEISHI: // 卫视和轮播获取数据的方式一样
                    this.getLunBoData(columnTpDto, handler);
                    break;
                case LiveConstants.CMS.COLUMN_TYPE_LIVE:
                    // http://api.live.letv.com/v1/liveRoom/queryLives/10,20,30,40,50,60,70,80,90?clientId=1060704002&belongArea=100
                    this.getZhiBoData(columnTpDto, handler);
                    break;
                default:
                    handler.handle(Future.succeededFuture(Collections.emptyList()));
                    logger.error("column, id=" + columnTpDto.getId() + " illage");
                    break;
            }
        }
    }

    /**
     * 获得直播数据
     */
    private void getZhiBoData(CmsColumnResponse columnTpDto, Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        try {
            long start = SystemCache.currentTimeMillis;
            if (StringUtils.isNotBlank(columnTpDto.getSearchUrl())) {
                int sourceType = this.getZhiboDataSourceType(columnTpDto.getSearchUrl());
                if (1 == sourceType) {// 获取直播后台数据
                    this.getZhiBoDataFromCloudPlatform(columnTpDto, handler);
                } else if (2 == sourceType) {// 获取第三方直播数据
                    this.getZhiBoDataFromLMS(columnTpDto, handler);
                } else {// 都不属于则未知无法处理
                    handler.handle(Future.succeededFuture(Collections.emptyList()));
                }
                logger.info("getZhiBoData waste time: " + (SystemCache.currentTimeMillis - start));
            } else {
                handler.handle(Future.succeededFuture(Collections.emptyList()));
            }
        } catch (Exception e) {
            handler.handle(Future.succeededFuture(Collections.emptyList()));
            logger.error("getZhiBoData EXCEPTION " + e.getMessage());
        }
    }

    /**
     * 获得直播后台的直播数据
     */
    private void getZhiBoDataFromCloudPlatform(CmsColumnResponse columnTpDto,
            Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        //cache缓存
        long start = SystemCache.currentTimeMillis;
        String key = CacheConstants.getCMSDataByTypeAndSearchUrl(LiveConstants.CacheType.LIVE_TYPE_LIVEROOM, columnTpDto.getSearchUrl());
        ZhiBoDataSetTpResponse zhiBoDataSetCache = (ZhiBoDataSetTpResponse) guawaCache.getIfPresent(key);
        if(zhiBoDataSetCache != null) {
            logger.info("getZhiBoDataFromCloudPlatform by cache waste time: " + (SystemCache.currentTimeMillis - start));
            handler.handle(Future.succeededFuture(this.dealWithZhiBoData(zhiBoDataSetCache)));
        }else{
            Future<String> superLiveFuture = Future.future();
            liveTpDao.getZhiBoDataFromCloudPlatform(columnTpDto.getSearchUrl(), superLiveFuture.completer());
            superLiveFuture.setHandler(re -> {
                if (re.succeeded()) {
                    ZhiBoDataSetTpResponse zhiBoDataSetResponse = Json.decodeValue(re.result(), ZhiBoDataSetTpResponse.class);
                    guawaCache.put(key, zhiBoDataSetResponse);
                    logger.info("getZhiBoDataFromCloudPlatform by http waste time: " + (SystemCache.currentTimeMillis - start));
                    handler.handle(Future.succeededFuture(this.dealWithZhiBoData(zhiBoDataSetResponse)));
                } else {
                    handler.handle(Future.succeededFuture(Collections.emptyList()));
                    logger.error("getZhiBoDataFromCloudPlatform failed; " + re.cause());
                }
            });
        }
    }

    private List<CmsChannelDto> dealWithZhiBoData(ZhiBoDataSetTpResponse zhiBoDataSetResponse) {
        // 推荐取的直播信息可能有些没有被直播后台监控导致没有缓存信息，这样的节目应该从列表中删除
        long start = SystemCache.currentTimeMillis;
        List<CmsChannelDto> channelDtos = null;
        if (zhiBoDataSetResponse != null && !CollectionUtils.isEmpty(zhiBoDataSetResponse.getRows())) {
            channelDtos = new ArrayList<CmsChannelDto>();
            Collection<ZhiBoDataResponse> zhibo = zhiBoDataSetResponse.getRows();
            for (ZhiBoDataResponse zhiBoDataResponse : zhibo) {
                if (StringUtils.isNotBlank(zhiBoDataResponse.getSplatid())) {
                    String[] split = zhiBoDataResponse.getSplatid().split(",");
                    HashSet<String> splitids = new HashSet<>(Arrays.asList(split));
                    if (splitids.contains(LiveConstants.SplatId.cibn_tvlive_clientid)) {
                        try {
                            CmsChannelDto channelDto = new CmsChannelDto();
                            channelDto.setLiveType(zhiBoDataResponse.getLiveType());
                            String level2 = zhiBoDataResponse.getLevel2();
                            level2 = StringUtils.isNotBlank(level2) ? level2 : zhiBoDataResponse.getTypeName();
                            channelDto.setChannelName(level2);
                            channelDto.setChannelId(zhiBoDataResponse.getId());
                            channelDto.setWeight(zhiBoDataResponse.getWeight());
                            channelDto.setIsDolby(zhiBoDataResponse.getIsDolby());
                            channelDtos.add(channelDto);
                        } catch (Exception e) {
                            logger.error("dealWithZhiBoData failed; " + e.getCause());
                        }
                    }
                }
            }
        }
        logger.info("dealWithZhiBoData  waste time: " + (SystemCache.currentTimeMillis - start));
        return channelDtos;
    }

    /**
     * 获得LMS直播后台数据
     */
    private void getZhiBoDataFromLMS(CmsColumnResponse columnTpDto, Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        //cache缓存
        long start = SystemCache.currentTimeMillis;
        String key = CacheConstants.getCMSDataByTypeAndSearchUrl(LiveConstants.CacheType.LIVE_TYPE_LMS, columnTpDto.getSearchUrl());
        LmsDataListResponse lmsDataListCache = (LmsDataListResponse) guawaCache.getIfPresent(key);
        if(lmsDataListCache != null) {
            this.getDataFromLMS(lmsDataListCache, handler);
            logger.info("getZhiBoDataFromLMS by cache waste time: " + (SystemCache.currentTimeMillis -start));
        }else{
            Future<String> lmsDataFuture = Future.future();
            liveTpDao.getZhiBoDataFromLMSBySearchUrl(columnTpDto.getSearchUrl(), null, 0, 200, null, lmsDataFuture.completer());
            lmsDataFuture.setHandler(re -> {
                if (re.succeeded()) {
                    LmsDataListResponse lmsDataListResponse = Json.decodeValue(re.result(), LmsDataListResponse.class);
                    guawaCache.put(key, lmsDataListResponse);
                    this.getDataFromLMS(lmsDataListResponse, handler);
                    logger.info("getZhiBoDataFromLMS by http waste time: " + (SystemCache.currentTimeMillis -start));
                } else {
                    logger.error("getZhiBoDataFromLMS faild; " + re.cause());
                    handler.handle(Future.succeededFuture(Collections.emptyList()));
                }
            });
        }
    }

    /**
     * 从lms获取数据
     * 
     * @param lmsDataListResponse
     * @param handler
     */
    private void getDataFromLMS(LmsDataListResponse lmsDataListResponse,
            Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        if (lmsDataListResponse == null || !"0".equals(lmsDataListResponse.getCode())) {
            handler.handle(Future.succeededFuture(Collections.emptyList()));
        }
        List<CmsChannelDto> channelDtos = new ArrayList<>();
        List<LmsDataResponse> lmsDataResponseList = lmsDataListResponse.getData();
        if (CollectionUtils.isNotEmpty(lmsDataResponseList)) {
            for (LmsDataResponse lmsDataResponse : lmsDataResponseList) {
                channelDtos.add(this.getChannelDtoByLmsData(lmsDataResponse));
            }
        }
        if (!CollectionUtils.isEmpty(channelDtos)) {
            List<CmsChannelDto> sortLMSData = this.sortLMSData(channelDtos);
            if (CollectionUtils.isNotEmpty(sortLMSData)) {
                handler.handle(Future.succeededFuture(sortLMSData));
            } else {
                logger.error("getDataFromLMS sortLMSData is null");
                handler.handle(Future.succeededFuture(Collections.emptyList()));
            }
        } else {
            logger.error("getDataFromLMS channelDtos is null");
            handler.handle(Future.succeededFuture(Collections.emptyList()));
        }
    }

    /**
     * 组装lms dto
     * 
     * @param lmsDataResponse
     * @return
     */
    private CmsChannelDto getChannelDtoByLmsData(LmsDataResponse lmsDataResponse) {
        CmsChannelDto channelDto = new CmsChannelDto();
        channelDto.setChannelId(lmsDataResponse.getRoomId());
        channelDto.setChannelName(lmsDataResponse.getAnchorName());
        channelDto.setChannelPic(lmsDataResponse.getLiveRoomPic());
        channelDto.setType(LiveConstants.LiveType.LIVE_TYPE_LIVEROOM);
        channelDto.setIsPay("0");
        channelDto.setIsLiveFromTV("0");
        channelDto.setIsAnchor(1);
        channelDto.setAvator(lmsDataResponse.getHeadPic());
        channelDto.setNickName(lmsDataResponse.getAnchorName());
        channelDto.setThirdLiveId(lmsDataResponse.getPartnerRoomId());
        channelDto.setLiveType(LiveConstants.LiveType.LIVE_THIRD_CHANNELID_PREFIX + lmsDataResponse.getSid()); // sid表示第三方来源(斗鱼/聚美)等
        channelDto.setChannelName(lmsDataResponse.getAnchorName());
        String liveRoomWeight = lmsDataResponse.getLiveRoomWeight();
        Integer weight = StringUtils.isBlank(liveRoomWeight) ? 0 : Integer.valueOf(liveRoomWeight);
        channelDto.setWeight(weight);
        // channelDto.setSupportPushVideo(false);
        if (lmsDataResponse.getSid().equals(LiveConstants.CMS.THIRD_DOUYU_SOURCEID)) {
            channelDto.setWaterLogo(LiveConstants.LMS.Third_douyu_logo);
        } else if (lmsDataResponse.getSid().equals(LiveConstants.CMS.THIRD_JUMEI_SOURCEID)) {

        } else if (lmsDataResponse.getSid().equals(LiveConstants.CMS.THIRD_XINGYUN_SOURCEID)) {
            channelDto.setWaterLogo(LiveConstants.LMS.Third_xingyun_logo);
        }
        return channelDto;
    }

    /**
     * 安装权重排序
     * 
     * @param channelDtos
     * @return
     */
    private List<CmsChannelDto> sortLMSData(List<CmsChannelDto> channelDtos) {
        if (channelDtos != null && channelDtos.size() > 0) {
            Collections.sort(channelDtos, new Comparator<CmsChannelDto>() {
                @Override
                public int compare(CmsChannelDto o1, CmsChannelDto o2) {
                    try {
                        int userOnlineCount1 = o1.getWeight() == null ? 0 : o1.getWeight();
                        int userOnlineCount2 = o2.getWeight() == null ? 0 : o2.getWeight();
                        int result = userOnlineCount2 - userOnlineCount1;
                        result = (result == 0 ? 0 : (result > 0 ? 1 : -1));
                        return result;
                    } catch (Exception e) {
                        logger.error("sortThridLiveData EXCEPTION" + e.getMessage());
                    }
                    return 0;
                }
            });
        }
        return channelDtos;
    }

    /**
     * 获得直播数据的数据来源（1:直播后台 2:第三方数据）
     * 
     * @param url
     * @return 返回对应数据类型。如果不存在默认返回1。如果异常则认为是未知的类型返回-1
     */
    private int getZhiboDataSourceType(String url) {
        int sourceType = 1;// 直播数据来源（1:直播后台 2:第三方数据）
        try {
            if (url.contains("?")) {
                String paramString = url.split("\\?")[1];
                if (StringUtils.isNotBlank(paramString)) {
                    if (paramString.contains("&")) {
                        String[] params = paramString.split("&");
                        for (String param : params) {
                            if (param.contains("sourcetype")) {
                                sourceType = Integer.parseInt(param.split("=")[1]);
                                break;
                            }
                        }
                    } else {
                        if (paramString.contains("sourcetype")) {
                            sourceType = Integer.parseInt(paramString.split("=")[1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("get zhibo data source type error,url=" + url, e);
            return -1;
        }
        return sourceType;
    }

    /**
     * 获得轮播数据
     * 
     * @param columnTpDto
     *            栏目信息
     * @param handler
     */
    private void getLunBoData(CmsColumnResponse columnTpDto, Handler<AsyncResult<List<CmsChannelDto>>> handler) {
        if (StringUtils.isNotBlank(columnTpDto.getSearchUrl())) {
            //cache缓存
            String key = CacheConstants.getCMSDataByTypeAndSearchUrl(LiveConstants.CacheType.LIVE_TYPE_LUNBO_WEISHI, columnTpDto.getSearchUrl());
            ChannelListResponse channelListCache = (ChannelListResponse) guawaCache.getIfPresent(key);
            if(channelListCache != null) {
                handler.handle(Future.succeededFuture(this.dealWithLunboData(channelListCache)));
            }else{
                Future<String> channelListFuture = Future.future();
                liveTpDao.getLunBoDataFromCloudPlatform(columnTpDto.getSearchUrl(), channelListFuture.completer());
                channelListFuture.setHandler(re -> {
                    if (re.succeeded()) {
                        ChannelListResponse channelListResponse = Json.decodeValue(re.result(), ChannelListResponse.class);
                        guawaCache.put(key, channelListResponse);
                        handler.handle(Future.succeededFuture(this.dealWithLunboData(channelListResponse)));
                    } else {
                        logger.error("getLunBoData failed; " + re.cause());
                        handler.handle(Future.succeededFuture(Collections.emptyList()));
                    }
                });
            }
        } else {
            logger.error("getLunBoData failed SearchUrl is null");
            handler.handle(Future.succeededFuture(Collections.emptyList()));
        }
    }

    private List<CmsChannelDto> dealWithLunboData(ChannelListResponse lunBoDataResponse) {
        List<CmsChannelDto> channelDtos = null;
        if (lunBoDataResponse != null && !CollectionUtils.isEmpty(lunBoDataResponse.getRows())) {
            List<ChannelResponse> lunBo = lunBoDataResponse.getRows();
            channelDtos = new ArrayList<CmsChannelDto>();
            for (ChannelResponse channelResponse : lunBo) {
                if (StringUtils.isNotBlank(channelResponse.getSplatid())) {
                    String[] split = channelResponse.getSplatid().split(",");
                    HashSet<String> splitids = new HashSet<>(Arrays.asList(split));
                    if (splitids.contains(LiveConstants.SplatId.cibn_tvlive_clientid)) {
                        CmsChannelDto dto = new CmsChannelDto();
                        dto.setChannelId(channelResponse.getChannelId());
                        dto.setChannelEname(channelResponse.getChannelName());
                        dto.setNumericKeys(channelResponse.getNumericKeys());
                        dto.setChannelEname(channelResponse.getChannelEname());
                        channelDtos.add(dto);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(channelDtos)) {
                this.sortChannelByOrderNo(channelDtos);
            }
        }
        return channelDtos;
    }

    private List<CmsChannelDto> getCMSColumnEntity(List<CmsColumnContentResponse> columnContents) {
        List<CmsChannelDto> columnChannels = null;
        if (!CollectionUtils.isEmpty(columnContents)) {
            columnChannels = new ArrayList<CmsChannelDto>();
            // 占位符列表
            List<CmsColumnContentResponse> placeHolders = new ArrayList<CmsColumnContentResponse>();
            CmsChannelDto channelDto = null;
            for (CmsColumnContentResponse columnContentTpDto : columnContents) {
                try {
                    // 直播类型(0：直播占位；1：轮播； 2：卫视；3：直播； 4：焦点; 5：咪咕; 6:第三方直播站位)
                    switch (columnContentTpDto.getLiveType()) {
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_PLACEHOLDER:// 0:直播占位符
                        placeHolders.add(columnContentTpDto);
                        break;
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_PLACEHOLDER_THIRDPARTY_LIVE:// 6：第三方直播占位符
                        placeHolders.add(columnContentTpDto);
                        break;
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_LUNBO:// 1:轮播
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_WEISHI:// 2:卫视
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_LIVE:// 3:直播
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_MIGU:// 5:咪咕
                        channelDto = new CmsChannelDto();
                        channelDto.setChannelId(columnContentTpDto.getDataId());
                        channelDto.setChannelName(columnContentTpDto.getDataName());
                        channelDto.setColumnId(String.valueOf(columnContentTpDto.getColumnId()));
                        channelDto.setOrderNo(String.valueOf(columnContentTpDto.getOrderr()));
                        channelDto.setLiveType(String.valueOf(columnContentTpDto.getLiveType()));
                        break;
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_MIX:
                        break;
                    case LiveConstants.CMS.COLUMN_CONTENT_TYPE_ACTIVITY:// 7:活动
                        channelDto = this.ConvertActivityContent(columnContentTpDto);
                        break;
                    default:
                        channelDto = new CmsChannelDto();
                        channelDto.setChannelId(columnContentTpDto.getDataId());
                        channelDto.setChannelName(columnContentTpDto.getDataName());
                        channelDto.setColumnId(String.valueOf(columnContentTpDto.getColumnId()));
                        channelDto.setOrderNo(String.valueOf(columnContentTpDto.getOrderr()));
                        channelDto.setLiveType(String.valueOf(columnContentTpDto.getLiveType()));
                        break;
                    }
                    if (channelDto != null) {
                        columnChannels.add(channelDto);
                        channelDto.setOrderNo(String.valueOf(columnContentTpDto.getOrderr()));
                        if (1 == columnContentTpDto.getIsRecommend()) {
                            channelDto.setIsArtificialRecommend("1");
                        }
                    }
                } catch (Throwable e) {
                    logger.error("getColumnCmsManData error", e.getMessage());
                }
            }
            if (CollectionUtils.isEmpty(columnChannels)) {
                return columnChannels;
            } else {
                this.sortChannelByOrderNo(columnChannels);
                return columnChannels;
            }
        }
        return columnChannels;
    }

    /**
     * 将活动内容转化为SuperliveChannelDto
     */
    private CmsChannelDto ConvertActivityContent(CmsColumnContentResponse activityContent) {
        try {
            if (activityContent != null) {
                Integer cmsRtype = LiveConstants.CMS.CMS_COLUMN_ACTIVITY_RTYPE_2_LIVE_RTYPE
                        .get(activityContent.getSkipType());
                if (cmsRtype != null) {
                    CmsChannelDto cmsChannelDto = new CmsChannelDto();
                    switch (cmsRtype) {
                    case LiveConstants.CMS.CMS_RTYPE_LIVE:// 101:直播
                    case LiveConstants.CMS.CMS_RTYPE_LECHANNEL: // 102:轮播 卫视
                        cmsChannelDto = new CmsChannelDto();
                        cmsChannelDto.setChannelId(activityContent.getDataId());
                        cmsChannelDto.setLiveType(String.valueOf(activityContent.getLiveType()));
                        cmsChannelDto.setOrderNo(String.valueOf(activityContent.getOrderr()));
                        cmsChannelDto.setrValue(activityContent.getSkipValue());
                        break;
                    case LiveConstants.CMS.CMS_RTYPE_FIRST_CATEGORY:
                        cmsChannelDto.setrValue("cmscid_" + activityContent.getSkipValue());
                        break;
                    case LiveConstants.CMS.CMS_RTYPE_SECOND_CATEGORY:
                        // TODO
                        break;
                    default:
                        cmsChannelDto.setrValue(activityContent.getSkipValue());
                        break;
                    }
                    cmsChannelDto.setType(String.valueOf(LiveConstants.CMS.COLUMN_CONTENT_TYPE_ACTIVITY));
                    cmsChannelDto.setrType(cmsRtype);
                    cmsChannelDto.setChannelName(activityContent.getDataName());
                    cmsChannelDto.setChannelDesc(activityContent.getDataName());
                    cmsChannelDto.setChannelPic(activityContent.getPic());
                    cmsChannelDto.setCorner(activityContent.getCorner1());
                    String outputType = activityContent.getOutputType();
                    String webViewType = StringUtils.isBlank(outputType) ? "0" : outputType;
                    cmsChannelDto.setWebViewType(webViewType);
                    return cmsChannelDto;
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("ConvertActivityContent error," + activityContent.toString(), e);
            return null;
        }
    }

    /**
     * 轮播台卫视台根据orderNo 排序
     */
    private void sortChannelByOrderNo(List<CmsChannelDto> list) {
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<CmsChannelDto>() {
                @Override
                public int compare(CmsChannelDto o1, CmsChannelDto o2) {
                    String orderNo1 = o1.getOrderNo();
                    String orderNo2 = o2.getOrderNo();
                    // 设一个比较大的默认值，怕编辑添负值，所以没有int的最大值
                    int orderInt1 = (int) Math.pow(2, 30);
                    int orderInt2 = (int) Math.pow(2, 30);
                    try {
                        if (StringUtils.isNotBlank(orderNo1) && orderNo1.matches("\\d+")) {
                            orderInt1 = Integer.parseInt(orderNo1);
                        }
                        if (StringUtils.isNotBlank(orderNo2) && orderNo2.matches("\\d+")) {
                            orderInt2 = Integer.parseInt(orderNo2);
                        }
                    } catch (Exception e) {
                        logger.warn("channnel sort error.", e);
                    }
                    int result = orderInt1 - orderInt2;
                    result = (result == 0 ? 0 : (result > 0 ? 1 : -1));
                    return result;
                }
            });
        }
    }

    /**
     * 获取cms一级栏目大分类dto
     * 
     * @param cmsFragResponse
     * @param cmsColumnEntity
     * @return
     */
    private CmsCategoryDto getCategoryDto(CmsFragResponse cmsFragResponse, List<CmsChannelDto> cmsColumnEntity) {
        CmsCategoryDto cmsCategoryDto = new CmsCategoryDto();
        if (cmsFragResponse == null || cmsFragResponse.getColumnEntity() == null) {
            return cmsCategoryDto;
        }
        CmsColumnResponse columnEntity = cmsFragResponse.getColumnEntity();
        cmsCategoryDto.setId(columnEntity.getId());
        cmsCategoryDto.setCategoryId("cmscid_" + columnEntity.getId());
        cmsCategoryDto.setCategoryName(columnEntity.getColumnName());
        cmsCategoryDto.setDataSource(1);
        cmsCategoryDto.setIsPersonalizedSort(cmsFragResponse.getIsOrder());
        cmsCategoryDto.setChannelList(cmsColumnEntity);
        return cmsCategoryDto;
    }

    private void setErrorResponse(String errorCode, CmsPageWrapper wrapper,
            Handler<AsyncResult<CmsPageWrapper>> result) {
        wrapper.setStatus(BaseResponse.RESPONSE_FAIL_CODE);
        wrapper.setErrorCode(errorCode);
        wrapper.setErrorMessage("get cmsdate error");
        result.handle(Future.succeededFuture(wrapper));
    }

    /**
     * 剧场包装水印信息
     */
    @Override
    public void getTheaterpackWaterMark(String playbillId, String channelId, LiveCommonParam commonParam, String date,
            Handler<AsyncResult<TheaterPackedWapper>> resultHandler) {
        String currDate = TimeUtil.timestamp2YYMMDD(System.currentTimeMillis());
        String nextDate = TimeUtil.timestamp2YYMMDD(System.currentTimeMillis() + 2 * 60 * 60 * 1000);
        // 用于测试
        if (StringUtils.isNotBlank(date)) {
            currDate = date;
            nextDate = date;
        }
        List<String> dateList = new ArrayList<>();
        dateList.add(currDate);
        if (!currDate.equalsIgnoreCase(nextDate)) {
            dateList.add(nextDate);
        }
        CompositeFuture programWaterFuture = this.getProgramWaterMarkFutures(channelId, dateList);
        CompositeFuture theaterWaterFuture = this.getTheaterWaterMarkFutures(channelId, dateList);
        CompositeFuture future = CompositeFuture.all(programWaterFuture, theaterWaterFuture);
        future.setHandler(re -> {
            if (re.succeeded()) {
                this.generateWaterMarks(re.result(), playbillId, resultHandler);
            } else {
                TheaterPackedWapper wapper = new TheaterPackedWapper();
                wapper.setStatus(BaseResponse.RESPONSE_FAIL_CODE);
                wapper.setErrorCode("");
                wapper.setErrorMessage("get TheaterWaterMark failed");
                resultHandler.handle(Future.succeededFuture(wapper));
            }
        });
    }

    /**
     * 节目水印future
     * 
     * @param channelId
     * @param dateList
     * @return
     */
    private CompositeFuture getProgramWaterMarkFutures(String channelId, List<String> dateList) {
        List<Future> futures = new ArrayList<>();
        Future<String> programWaterMarkFuture = null;
        for (String date : dateList) {
            programWaterMarkFuture = this.getProgramWaterMarkFuture(channelId, date);
            futures.add(programWaterMarkFuture);
        }
        return CompositeFuture.all(futures);
    }

    /**
     * 剧场水印future
     * 
     * @param channelId
     * @param dateList
     * @return
     */
    private CompositeFuture getTheaterWaterMarkFutures(String channelId, List<String> dateList) {
        List<Future> futures = new ArrayList<>();
        Future<String> theaterWaterMarkFuture = null;
        for (String date : dateList) {
            theaterWaterMarkFuture = this.getTheaterWaterMarkFuture(channelId, date);
            futures.add(theaterWaterMarkFuture);
        }
        return CompositeFuture.all(futures);
    }

    private Future<String> getProgramWaterMarkFuture(String channelId, String date) {
        Future<String> programWaterMarkFuture = Future.future();
        try {
            ProgramWaterMarkRequest programWaterMarkRequest = new ProgramWaterMarkRequest();
            programWaterMarkRequest.setChannelId(NumberUtils.toInt(channelId));
            programWaterMarkRequest.setCurPage(1);
            programWaterMarkRequest.setPageSize(50);
            programWaterMarkRequest.setPlayDate(date);
            programWaterMarkRequest.setPlatform("lelive");
            liveTpDao.getProgramWaterMark(programWaterMarkRequest, programWaterMarkFuture.completer());
        } catch (Exception e) {
            logger.error("setProgramWaterMarkList Exception " + e.getMessage());
        }
        return programWaterMarkFuture;
    }

    private Future<String> getTheaterWaterMarkFuture(String channelId, String date) {
        Future<String> theaterWaterMarkFuture = Future.future();
        try {
            TheaterWaterMarkRequest theaterWaterMarkRequest = new TheaterWaterMarkRequest();
            theaterWaterMarkRequest.setChannelId(NumberUtils.toInt(channelId));
            theaterWaterMarkRequest.setPlayDate(date);
            theaterWaterMarkRequest.setPlatform("lelive");
            liveTpDao.getTheaterWaterMark(theaterWaterMarkRequest, theaterWaterMarkFuture.completer());
        } catch (Exception e) {
            logger.error("setTheaterWaterMarkList Exception " + e.getMessage());
        }
        return theaterWaterMarkFuture;
    }

    /**
     * 如果playbillId不为空，则从只获取playbillId之后一段时间的水印
     * 
     * @param result
     * @param playbillId
     * @param resultHandler
     */
    private void generateWaterMarks(CompositeFuture result, String playbillId,
            Handler<AsyncResult<TheaterPackedWapper>> resultHandler) {
        List<WaterMarkDeliveryInfo> porgramMarkList = new ArrayList<WaterMarkDeliveryInfo>();
        List<WaterMarkDeliveryInfo> theaterMarkList = new ArrayList<WaterMarkDeliveryInfo>();
        try {
            CompositeFuture programWaterFuture = result.resultAt(0);
            int programMarkSize = programWaterFuture.size();
            for (int i = 0; i < programMarkSize; i++) {
                this.setProgramWaterMarkByIndex(porgramMarkList, programWaterFuture, i, playbillId);
            }
        } catch (Exception e) {
            logger.error("generate porgramMarkList Exception " + e.getMessage());
        }

        try {
            CompositeFuture theaterWaterFuture = result.resultAt(1);
            int theaterMarkSize = theaterWaterFuture.size();
            for (int j = 0; j < theaterMarkSize; j++) {
                this.setTheaterWaterMarkByIndex(theaterMarkList, theaterWaterFuture, j, playbillId);
            }
        } catch (Exception e) {
            logger.error("generate theaterMarkList Exception " + e.getMessage());
        }

        WaterMarksDto waterMarksDto = new WaterMarksDto();
        waterMarksDto.setTheaterWaterMarkList(theaterMarkList);
        waterMarksDto.setProgramWaterMarkList(porgramMarkList);
        TheaterPackedWapper wapper = new TheaterPackedWapper();
        wapper.setData(waterMarksDto);

        resultHandler.handle(Future.succeededFuture(wapper));
    }

    private void setProgramWaterMarkByIndex(List<WaterMarkDeliveryInfo> waterMarks, CompositeFuture result, int index,
            String playbillId) {
        // 节目水印
        ProgramWaterMarkTpResponse programWaterMarkTpResponse = Json.decodeValue(result.resultAt(index), ProgramWaterMarkTpResponse.class);
        if (programWaterMarkTpResponse != null && 1 == programWaterMarkTpResponse.getResult()
                && programWaterMarkTpResponse.getData() != null) {
            ProgramWaterMarkData programWaterMarkData = programWaterMarkTpResponse.getData();
            if (!CollectionUtils.isEmpty(programWaterMarkData.getItems())) {
                boolean isAfterPlaybillId = false;
                for (WaterMarkDeliveryTpResponse waterMarkDelivery : programWaterMarkData.getItems()) {
                    WaterMarkDeliveryInfo waterMarkDeliveryInfo = null;
                    if (StringUtils.isNotBlank(playbillId)) {
                        if (String.valueOf(waterMarkDelivery.getPlaybillItemId()).equalsIgnoreCase(playbillId)) {
                            isAfterPlaybillId = true;
                        }
                        if (isAfterPlaybillId) {
                            waterMarkDeliveryInfo = this.getWaterMarkInfo(waterMarkDelivery);
                        }
                    } else {
                        waterMarkDeliveryInfo = this.getWaterMarkInfo(waterMarkDelivery);
                    }

                    if (waterMarkDeliveryInfo != null) {
                        waterMarks.add(waterMarkDeliveryInfo);
                    }
                }
            }
        }
    }

    private void setTheaterWaterMarkByIndex(List<WaterMarkDeliveryInfo> waterMarks, CompositeFuture result, int index,
            String playbillId) {
        // 剧场水印
        TheaterWaterMarkTpResponse theaterWaterMarkTpResponse = Json.decodeValue(result.resultAt(index), TheaterWaterMarkTpResponse.class);
        if (theaterWaterMarkTpResponse != null && 1 == theaterWaterMarkTpResponse.getResult()
                && !CollectionUtils.isEmpty(theaterWaterMarkTpResponse.getData())) {
            boolean isAfterPlaybillId = false;
            for (WaterMarkDeliveryTpResponse waterMarkDelivery : theaterWaterMarkTpResponse.getData()) {
                WaterMarkDeliveryInfo theaterWaterMarkInfo = null;
                if (StringUtils.isNotBlank(playbillId)) {
                    if (String.valueOf(waterMarkDelivery.getPlaybillItemId()).equalsIgnoreCase(playbillId)) {
                        isAfterPlaybillId = true;
                    }
                    if (isAfterPlaybillId) {
                        theaterWaterMarkInfo = this.getWaterMarkInfo(waterMarkDelivery);
                    }
                } else {
                    theaterWaterMarkInfo = this.getWaterMarkInfo(waterMarkDelivery);
                }

                if (theaterWaterMarkInfo != null) {
                    waterMarks.add(theaterWaterMarkInfo);
                }
            }
        }
    }

    private WaterMarkDeliveryInfo getWaterMarkInfo(WaterMarkDeliveryTpResponse waterMarkDelivery) {
        WaterMarkDeliveryInfo theaterWaterMarkInfo = new WaterMarkDeliveryInfo();
        theaterWaterMarkInfo.setPlaybillId(waterMarkDelivery.getPlaybillItemId());
        theaterWaterMarkInfo.setType(waterMarkDelivery.getType());
        theaterWaterMarkInfo.setWaterMarkId(waterMarkDelivery.getWatermarkId());
        theaterWaterMarkInfo.setBeginTime(waterMarkDelivery.getBeginTime());
        theaterWaterMarkInfo.setEndTime(waterMarkDelivery.getEndTime());
        theaterWaterMarkInfo.setProgramId(waterMarkDelivery.getDataId());
        theaterWaterMarkInfo.setProgramType(waterMarkDelivery.getProgramType());
        theaterWaterMarkInfo.setSplatld(waterMarkDelivery.getSplatId());

        if (waterMarkDelivery.getWartermark() != null) {
            WarterMarkTpResponse wartermark = waterMarkDelivery.getWartermark();
            WaterMarkInfo waterMarkInfo = new WaterMarkInfo();
            waterMarkInfo.setCircleCount(wartermark.getCycleNum());
            waterMarkInfo.setDisplayFrequency(wartermark.getDisplayFrequency());
            waterMarkInfo.setDisplayType(wartermark.getDisplayType());
            waterMarkInfo.setId(wartermark.getId());
            if (CollectionUtils.isNotEmpty(wartermark.getResourceItemList())) {
                List<ResourceItem> resourceItemList = wartermark.getResourceItemList();
                List<MaterialInfo> materialInfos = new ArrayList<>();
                for (ResourceItem resourceItem : resourceItemList) {
                    MaterialInfo materialInfo = new MaterialInfo();
                    materialInfo.setDuration(resourceItem.getDuration());
                    materialInfo.setHeight(resourceItem.getPxHeight());
                    materialInfo.setPic(resourceItem.getUrl());
                    if (CollectionUtils.isNotEmpty(resourceItem.getDocItemList())) {
                        List<String> docs = new ArrayList<>();
                        for (Doc doc : resourceItem.getDocItemList()) {
                            docs.add(doc.getDocument());
                        }
                        materialInfo.setTextList(docs);
                    }
                    materialInfo.setWidth(resourceItem.getPxWidth());
                    materialInfos.add(materialInfo);
                }
                waterMarkInfo.setMaterialList(materialInfos);
            }
            waterMarkInfo.setName(wartermark.getName());
            waterMarkInfo.setPosition(wartermark.getPos());
            waterMarkInfo.setPreDuration(wartermark.getPreDuration());
            waterMarkInfo.setStatus(wartermark.getStatus());
            waterMarkInfo.setStyle(wartermark.getStyle());
            waterMarkInfo.setType(wartermark.getType());
            theaterWaterMarkInfo.setWaterMarkInfo(waterMarkInfo);
        }
        return theaterWaterMarkInfo;
    }

    private JsonObject getJsonConf(String configPath) {
        logger.info("rediPath: " + configPath);
        JsonObject conf = new JsonObject();
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = ctxClsLoader.getResourceAsStream(configPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conf = new JsonObject(sb.toString());
            logger.info(
                    "Loaded redis.json file from [" + configPath + "/redis.json] and config.json=" + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load configuration file" + e);
        }
        return conf;
    }

}
