package util;

import cache.constants.CacheConstants;
import cache.dto.Album;
import cache.dto.Video;
import com.google.common.primitives.Ints;
import constants.ChannelConstants;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import service.dto.channelPage.BaseDto;
import service.dto.channelPage.BlockDataDto;
import service.dto.channelPage.ChannelBlockDto;
import service.param.CommonParam;
import service.param.ParseDataParam;
import tp.rec.response.RecData;
import video.VideoConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhushenghao1 on 16/12/30.
 */
public class ChannelBlockParsing {
    final static ChannelHelper channelHelper = new ChannelHelper();

    public ChannelBlockDto parseSkipPolicy(RecData recData, String type, CommonParam param, ParseDataParam extraParam) {
        if (recData == null) {
            return null;
        }
        // 跳转到视频，包括cms和rec的视频
        ChannelBlockDto block = null;
        BlockDataDto focus = null;
        if (ChannelConstants.DataType.DATA_TYPE_VIDEO.equals(type)) {
            RecData rec = (RecData) recData;
            String videoId = rec.getVid() != null ? String.valueOf(rec.getVid()) : rec.getContent();
            Video video = null;
            if (extraParam != null) {
                Map<String, Object> caches = extraParam.getCaches();
                if (caches != null) {
                    video = (Video) caches.get(CacheConstants.getVideoKey(videoId));
                }
            }
            if (video != null) {
                focus = new BlockDataDto();
                focus.setAt(BlockDataDto.HOME_ACTION_TYPE_FULL);
                focus.setType(ChannelConstants.DataType.DATA_TYPE_VIDEO);
                focus.setVid(video.getId());
                focus.setPid(video.getAlbumId());
                focus.setCid(video.getCategoryId());
                focus.setVideoType(String.valueOf(video.getType()));

                focus.setTag(video.getTag());
                focus.setSubCategory(video.getSubCategoryName());
                focus.setDirector(video.getDirectory());
                focus.setArea(video.getAreaName());
                focus.setDuration(String.valueOf(video.getDuration()));
                focus.setReleaseDate(video.getReleaseDate());

                String name = rec.getTitle();
                String subTitle = rec.getSubTitle();
                if ("true".equals(rec.getIs_rec())&&ChannelConstants.DataType.DATA_TYPE_VIDEO.equals(type)) {
                    name = rec.getTitle();
                    subTitle = rec.getSubtitle();
                    if (rec.getCid() != null && VideoConstants.Category.VARIETY == rec.getCid().intValue()) {
                        // 推荐的综艺视频
                        subTitle = rec.getVidsubtitle();
                        name = rec.getPidname();
                    }
                }
                focus.setName(name);
                focus.setSubTitle(subTitle);
                focus.setSinger(video.getStarring());
                focus.setUpdateTime(video.getUpdateTime());
                String pageId = extraParam.getPageid();
                String terminalApplication = extraParam.getTerminalApplication();
                //角标
                if (video.getAlbumId() != null && video.getAlbumId() > 0) {
                    focus.setCornerLabel(channelHelper.fillCornerLabelByCache(null, video,
                            ChannelConstants.DataType.DATA_TYPE_VIDEO, pageId, extraParam.getTerminalApplication()));
                }

                if ("lecom".equals(terminalApplication)) {
                    focus.setPay(null);
                }
                if (rec.getSource() == ChannelConstants.DataSource.DATA_SOURCE_SEARCH&&
                        ChannelConstants.DataType.DATA_TYPE_VIDEO.equals(type)) {
                    // 搜索来的数据
                    focus.setDuration(rec.getDuration());
                    focus.setArea(rec.getArea());
                    focus.setScore(rec.getScore());
                    focus.setPlayCount(rec.getPlaycount());
                    focus.setPay(rec.getIs_pay());
                }

                // 填充图片,默认按照已有逻辑填充
                this.fillVideoPic(focus, video, rec, type, extraParam);
                this.afterParseSkipPolicy(rec, focus, extraParam);
            }
        } else if (ChannelConstants.DataType.DATA_TYPE_ALBUM.equals(type)) {
            RecData rec = (RecData) recData;
            String albumId = rec.getPid() != null ? String.valueOf(rec.getPid()) : rec.getContent();
            Album album = null;
            Map<String, Object> caches = extraParam.getCaches();
            if (caches != null) {
                album = (Album) caches.get(CacheConstants.getAlbumKey(albumId));
            }
            if (album != null) {
                focus = new BlockDataDto();
                // 常态属性
                focus.setAt(BlockDataDto.HOME_ACTION_TYPE_FULL);

                focus.setCmsid(rec.getId());
                focus.setPid(album.getId());
                focus.setCid(album.getCategoryId());
                focus.setType(ChannelConstants.DataType.DATA_TYPE_ALBUM);
                focus.setAlbumType(String.valueOf(album.getType()));
                focus.setUpdateTime(album.getUpdateTime());

                // 经常调整属性
                String name = rec.getTitle();
                String subTitle = rec.getSubTitle();
                if ("true".equals(rec.getIs_rec())&&ChannelConstants.DataType.DATA_TYPE_ALBUM.equals(type)) {
                    subTitle = rec.getPidsubtitle();
                    name = rec.getPidname();
                }
                focus.setName(name);
                focus.setSubTitle(subTitle);

                // 非经常变动属性
                focus.setScore(album.getScore());
                focus.setSinger(album.getStarring());
                focus.setDirector(album.getDirectory());
                focus.setSubCategory(album.getSubCategoryName());
                focus.setReleaseDate(album.getReleaseDate());
                focus.setArea(album.getAreaName());
                focus.setTag(album.getTag());

                // 剧集更新相关
                focus.setIsEnd(String.valueOf(album.getIsEnd()));
                focus.setEpisode(String.valueOf(album.getEpisode()));
                focus.setNowEpisodes(album.getNowEpisodes());

                if (album.getIsyuanxian() != null) {
                    focus.setPay(String.valueOf(album.getIsyuanxian()));
                }

                String vtypeFlag = rec.getVtypeFlag();
                Video v = null;
                if (album.getVideoId() != null) {
                    v = (Video) caches.get(CacheConstants.getVideoKey(album.getVideoId()));
                    if (v != null && StringUtils.isNotBlank(v.getVtypeFlag())) {
                        vtypeFlag = StringUtils.isNotBlank(vtypeFlag) ? vtypeFlag + "," + v.getVtypeFlag()
                                : v.getVtypeFlag();
                    }
                }

                String pageId = extraParam.getPageid();
                // 角标

                focus.setCornerLabel(channelHelper.fillCornerLabelByCache(album, v, ChannelConstants.DataType.DATA_TYPE_ALBUM,
                        pageId, extraParam.getTerminalApplication()));

                String terminalApplication = extraParam.getTerminalApplication();


                if (rec.getSource() == ChannelConstants.DataSource.DATA_SOURCE_SEARCH
                        &&ChannelConstants.DataType.DATA_TYPE_ALBUM.equals(type)) {
                    //搜索来的数据
                    focus.setDuration(rec.getDuration());
                    focus.setArea(rec.getArea());
                    focus.setScore(rec.getScore());
                    focus.setPlayCount(rec.getPlaycount());
                    focus.setPay(rec.getIs_pay());
                }

                // 填充图片
                this.fillAlbumPic(focus, album, rec, type, extraParam);
                this.afterParseSkipPolicy(rec, focus, extraParam);
            }
        } else if (ChannelConstants.SkipType.ADDON_PAGE.equals(type)) {
            RecData rec = recData;
            if (ChannelConstants.SkipType.ADDON_PAGE.equals(type)
                    && (StringUtils.isBlank(rec.getContent()) || Ints.tryParse(rec.getContent()) == null)) {
                return null;
            }
            focus = new BlockDataDto();
            if(ChannelConstants.SkipType.ADDON_PAGE.equals(type)){
                focus.setAddOnId(Ints.tryParse(rec.getContent()));
                focus.setAt(BlockDataDto.HOME_ACTION_TYPE_ADDONPAGE);
            }
            focus.setCmsid(rec.getId());

            Integer cid = null;
            if(StringUtils.isNotBlank(rec.getSkipPage())){
                cid = Ints.tryParse(rec.getSkipPage());
            }
            focus.setCid(cid);// skipPage中存储跳转的频道

            focus.setName(rec.getTitle());
            focus.setSubTitle(rec.getSubTitle());

            focus.setCname(ChannelSkip.CHANNEL_MAP.get(cid));

            focus.setDataUrl(ChannelSkip.getChannelSkipUrl(rec.getType(), rec.getContent(), cid, "0"));
            this.afterParseSkipPolicy(rec, focus, extraParam);

            //填充图片
            this.fillSkipChannelPic(focus, rec);
            this.afterParseSkipPolicy(rec, focus, extraParam);
        }

        if (focus != null) {
            block = new ChannelBlockDto();
            List<BaseDto> focusList = new ArrayList<>();
            focusList.add(focus);
            block.setList(focusList);
        }
        return block;
    }

    private void fillVideoPic(BlockDataDto blockDataDto, Video video, RecData rec, String type, ParseDataParam extraParam) {
        String pic = null;
        Map<String, String> picAll = new HashMap<>();
        if("true".equals(rec.getIs_rec())&&ChannelConstants.DataType.DATA_TYPE_VIDEO.equals(type)) {
            //推荐数据
            picAll.put("pic_16_9",rec.getPic400_225());
            picAll.put("pic_3_4",rec.getPicST());
//            picAll.put("pic_3_4","");3:4的图还没给
        } else if (rec.getSource() == ChannelConstants.DataSource.DATA_SOURCE_SEARCH&&ChannelConstants.DataType.DATA_TYPE_VIDEO.equals(type)) {
            //搜索数据
            picAll.put("pic_16_9",rec.getPicList().get("400*225"));
            picAll.put("pic_3_4",rec.getPicList().get("300*400"));
        } else {
            //cms人工配置数据
            if(StringUtils.isNotBlank(rec.getMobilePic())){
                pic = rec.getMobilePic();
                picAll.put("pic_16_9",pic);
                picAll.put("pic_3_4",pic);
            } else if(!MapUtils.isEmpty(rec.getPicList())){
                picAll.put("pic_16_9",rec.getPicList().get("400x225"));
                picAll.put("pic_3_4",rec.getPicList().get("300x400"));
            }
        }
        blockDataDto.setPic(pic);
        blockDataDto.setPicAll(picAll);
    }

    private void fillAlbumPic(BlockDataDto blockDataDto, Album album, RecData rec, String type, ParseDataParam extraParam) {
        String pic = null;
        Map<String, String> picAll = new HashMap<>();
        if ("true".equals(rec.getIs_rec())&&ChannelConstants.DataType.DATA_TYPE_ALBUM.equals(type)) {
            picAll.put("pic_16_9",rec.getPic400_225());
            picAll.put("pic_3_4",rec.getPicST());
        } else if(rec.getSource() == ChannelConstants.DataSource.DATA_SOURCE_SEARCH&& ChannelConstants.DataType.DATA_TYPE_ALBUM.equals(type)){
            //搜索的数据
            picAll.put("pic_16_9",rec.getPicList().get("400*225"));
            picAll.put("pic_3_4",rec.getPicList().get("300*400"));
        } else {
            //手动数据只有一种图
            if(StringUtils.isNotBlank(rec.getMobilePic())){
                pic = rec.getMobilePic();
                picAll.put("pic_16_9",pic);
                picAll.put("pic_3_4",pic);
            } else if(!MapUtils.isEmpty(rec.getPicList())){
                picAll.put("pic_16_9",rec.getPicList().get("400x225"));
                picAll.put("pic_3_4",rec.getPicList().get("300x400"));
            }
        }
        blockDataDto.setPic(pic);
        blockDataDto.setPicAll(picAll);
    }

    private void fillSkipChannelPic(BlockDataDto focus,RecData rec){
        String pic = null;
        Map<String, String> picAll = new HashMap<>();

        if(StringUtils.isNotBlank(rec.getMobilePic())){
            pic = rec.getMobilePic();
            picAll.put("pic_16_9",pic);
            picAll.put("pic_3_4",pic);
        } else if(!MapUtils.isEmpty(rec.getPicList())){
            picAll.put("pic_16_9",rec.getPicList().get("400x225"));
            picAll.put("pic_3_4",rec.getPicList().get("300x400"));
        }

        focus.setPic(pic);
        focus.setPicAll(picAll);
    }

    public void afterParseSkipPolicy(RecData src,BlockDataDto dst,ParseDataParam extraParam){
        if(src.getSource() != null){
            dst.setSource(src.getSource());
        } else if ("true".equals(src.getIs_rec())){
            dst.setSource(ChannelConstants.DataSource.DATA_SOURCE_REC);
        } else {
            dst.setSource(ChannelConstants.DataSource.DATA_SOURCE_CMS);
        }
    }
}