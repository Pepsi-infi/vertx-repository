package inke;

import cms.response.CmsBlockContent;
import inke.dto.AnchorDto;
import inke.dto.AnchorListWapperDto;
import inke.dto.AnchorPageDto;
import inke.dto.BannerDto;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import tp.cms.CmsTpDao;
import tp.cms.response.CmsMutilangDataResponse;
import utils.IPUtil;

/**
 * Created by wanglonghu on 17/6/9.
 */
public class AnchorServiceImpl extends AbstractVerticle implements AnchorService {

    private InkeMetaService inkeMetaService;

    private CmsTpDao cmsTpDao;

    @Override
    public void start() throws Exception {
        super.start();

        ProxyHelper.registerService(AnchorService.class, vertx.getDelegate(), this,
                AnchorService.SERVICE_ADDRESS + IPUtil.getInnerIP());

        inkeMetaService = ProxyHelper.createProxy(InkeMetaService.class, vertx.getDelegate(),
                InkeMetaService.SERVICE_ADDRESS, new DeliveryOptions().setSendTimeout(3000));
        cmsTpDao = CmsTpDao.createLocalProxy(vertx.getDelegate());
    }

    @Override
    public void getOnlineAnchorList(int index, String uuid, Handler<AsyncResult<AnchorListWapperDto>> resultHandler) {
        int pageSize = 20;

        Future<List<JsonObject>> onlineAnchorFuture = Future.future();
        inkeMetaService.getOnlineAnchorsOrderByInke(index, pageSize, uuid, onlineAnchorFuture.completer());

        AnchorListWapperDto wapperDto = new AnchorListWapperDto();
        List<AnchorPageDto> data = new ArrayList<>();
        AnchorPageDto pageDto = new AnchorPageDto();
        pageDto.setId(1);
        pageDto.setName("颜值");
        pageDto.setPage(index);

        String bannerBlockId = "9629";
        String langCode = "zh_cn";
        Future<CmsMutilangDataResponse> cmsFuture = Future.future();
        cmsTpDao.getCmsBlockForLang(bannerBlockId, langCode, cmsFuture.completer());

        CompositeFuture.all(cmsFuture, onlineAnchorFuture).setHandler(res -> {
            if (res.succeeded()) {
                CmsMutilangDataResponse cmsBlockData = res.result().resultAt(0);
                List<JsonObject> anchorDtos = res.result().resultAt(1);

                if (index == 1) {
                    pageDto.setBanner(getBannerFromCmsData(cmsBlockData));
                }

                pageDto.setContent(getContentFromInkeMeta(anchorDtos));
                if (CollectionUtils.isNotEmpty(pageDto.getContent())) {
                    pageDto.setPageSize(pageDto.getContent().size());
                } else {
                    pageDto.setPageSize(0);
                }

                data.add(pageDto);
                wapperDto.setData(data);
                resultHandler.handle(Future.succeededFuture(wapperDto));
            } else {
                resultHandler.handle(Future.failedFuture("Get data failed."));
            }
        });
    }

    private List<AnchorDto> getContentFromInkeMeta(List<JsonObject> anchorDtos) {
        List<AnchorDto> anchorList = null;
        if (CollectionUtils.isNotEmpty(anchorDtos)) {
            anchorList = new ArrayList<>();
            for (JsonObject j : anchorDtos) {
                anchorList.add(getAnchorDto(j));
            }
        }

        return anchorList;
    }

    private List<BannerDto> getBannerFromCmsData(CmsMutilangDataResponse cmsBlockData) {
        List<BannerDto> bannerList = null;
        if (cmsBlockData != null && cmsBlockData.getData() != null
                && CollectionUtils.isNotEmpty(cmsBlockData.getData().getBlockContent())) {
            BannerDto bannerDto = null;
            bannerList = new ArrayList<>();
            List<CmsBlockContent> CmsBlockContentList = cmsBlockData.getData().getBlockContent();
            for (CmsBlockContent blockContent : CmsBlockContentList) {
                bannerDto = new BannerDto();

                bannerDto.setJump(Integer.valueOf(blockContent.getSkipType()));
                bannerDto.setImg(blockContent.getPic1());
                bannerDto.setRoomId(blockContent.getContent());
                bannerDto.setUrl(blockContent.getUrl());

                bannerList.add(bannerDto);
            }
        }

        return bannerList;
    }

    private AnchorDto getAnchorDto(JsonObject jsonAnchor) {
        AnchorDto anchorDto = null;
        if (jsonAnchor != null) {
            anchorDto = new AnchorDto();

            // key参见 InkeAnchor 对象定义
            anchorDto.setId(jsonAnchor.getString("id"));
            anchorDto.setPortrait(jsonAnchor.getString("portrait"));
            anchorDto.setOnlineNum(jsonAnchor.getLong("online_users"));
            anchorDto.setNick(jsonAnchor.getString("nick"));
            anchorDto.setCity(jsonAnchor.getString("city"));
            anchorDto.setLiveId(jsonAnchor.getString("live_id"));
            anchorDto.setSource(jsonAnchor.getInteger("source"));
        }

        return anchorDto;
    }
}
