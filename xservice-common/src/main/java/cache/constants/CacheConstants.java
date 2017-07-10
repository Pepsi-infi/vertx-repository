package cache.constants;

import org.apache.commons.lang.StringUtils;

/**
 * Cache Key {类名}[s]-{id} eg: VideoCacheDto-V-591762
 */
public class CacheConstants {

    /**
     * 视频关联片段
     */
    private static final String Segments_Vid_ = "Segments_Vid_";

    /**
     * 专辑关联片段
     */
    private static final String Segments_Aid_ = "Segments_Aid_";// 专辑id+上线时间
                                                                // 例如:Segments_Aid_10014326_20160101

    /**
     * 专辑下正片列表
     */
    private static final String LECOM_VIDEOS_PAGELIST_ALBUM = "Lecom_Videos_PageList_Album_";// lecom独有 专辑下正片视频分页列表
    private static final String POSITIVE_VIDEOS_PAGELIST_ALBUM = "Positive_Videos_PageList_Album_";// 专辑下正片视频分页列表
    private static final String POSITIVE_VIDEOLIST_ALBUM_PAGE = "Positive_VideoList_Album_Page_";// 专辑下分页正片视频列表
    private static final String POSITIVE_VIDEOLIST_ALBUM_MONTH = "Positive_VideoList_Album_Month_";// 专辑下分月正片视频列表

    /**
     * 专辑下预告片视频列表
     */
    private static final String YUGAO_VIDEOLIST_ALBUM = "Yugao_VideoList_Album_";

    /**
     * 专辑下其它片视频列表
     */
    private static final String OTHER_VIDEOLIST_ALBUM = "Other_VideoList_Album_";

    /**
     * 专辑缓存
     */
    private static final String PlayCacheEntity_A_ = "PlayCacheEntity_A_";

    /**
     * 视频缓存
     */
    private static final String PlayCacheEntity_V_ = "PlayCacheEntity_V_";

    /**
     * 活水印
     */
    private static final String LiveWaterMark_Cid_ = "LiveWaterMark_Cid_";

    private static final String LiveWaterMark_Base = "LiveWaterMark_Base";

    private static final String LiveWaterMark_Pid_ = "LiveWaterMark_Pid_";

    /**
     * 乐词关注度
     */
    private static final String HotWord_Attention_ = "HotWord_Attention_";

    /**
     * 壁纸缓存
     */
    private static final String WallPaper_ = "WallPaper_";

    /**
     * 专辑关联台词
     */
    private static final String Lines_Pid_ = "Lines_Pid_";

    /**
     * 乐词缓存
     */
    private static final String HotWord_ = "HotWord_";

    /**
     * 台词缓存
     */
    private static final String Lines_ = "Lines_";


    
    /**
     * 音频缓存
     */
    private static final String PlayCacheEntity_Audio= "PlayCacheEntity_Audio_";

    /**
     * 音频专辑缓存
     */
    private static final String Audios_Pagelist_Ost = "Audios_PageList_Ost_";// 音频下分页列表
    private static final String Audiolist_Ost_Page = "AudioList_Ost_Page_";// 音频下分页音频列表
    private static final String PlayCacheEntity_Ost = "PlayCacheEntity_Ost_"; //音频专辑缓存

    private static final String PlayInteract_ = "PlayInteract_";
    private static final String PlayInteract_V_ = "PlayInteract_V_";
    private static final String PlayInteract_A_ = "PlayInteract_A_";

    public interface Video {
        public static final String VideoCacheEntity_ = "VideoCacheEntity_";
        public static final String AlbumCacheEntity_ = "AlbumCacheEntity_";
    }

    /**
     * BOSS付费频道信息缓存key值
     */
    public static final String BOSS_PAY_CHANNEL_DATA = "pay_channel_data_";// 数据缓存key

    /**
     * Get HotWord cache key by hotWordId
     * @param hotWordId
     *            not null
     * @return
     */
    public static String getHotWordKey(Long hotWordId) {
        if(hotWordId == null){
            return null;
        }
        return new StringBuffer(HotWord_).append(hotWordId).toString();
    }

    /**
     * Get Line cache key by id
     * @param lineId
     * @return
     */
    public static String getLineKey(Long lineId) {
        if(lineId == null){
            return null;
        }
        return new StringBuffer(Lines_).append(lineId).toString();
    }

    /**
     * Get segments by videoid
     * @param videoId
     *            not null
     * @return
     */
    public static String getVideoSegments(Long videoId) {
//        Assert.notNull(videoId);
        if(videoId == null){
            return null;
        }
        return new StringBuffer(Segments_Vid_).append(videoId).toString();
    }

    /**
     * Get segments by albumid
     * @param albumId
     *            not null
     * @return
     */
    public static String getAlbumSegmentsKey(Long albumId) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(Segments_Aid_).append(albumId).toString();
    }

    /**
     * Get waterMark by categoryId
     * @param categoryId
     *            not null
     * @return
     */
    public static String getWaterMarkKeyByCid(Integer categoryId) {
//        Assert.notNull(categoryId);
        if(categoryId == null){
            return null;
        }
        return new StringBuffer(LiveWaterMark_Cid_).append(categoryId).toString();
    }

    /**
     * Get waterMark by albumId
     * @param albumId
     *            not null
     * @return
     */
    public static String getWaterMarkKeyByPid(Long albumId) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(LiveWaterMark_Pid_).append(albumId).toString();
    }

    /**
     * Get Common waterMark
     * @return
     */
    public static String getWaterMarkKeyCommon() {
        return LiveWaterMark_Base;
    }

    /**
     * Get HotWord attention key by hotWord id
     * @param hotWordId
     *            not null
     * @return
     */
    public static String getHotWordAttentionKey(Long hotWordId) {
//        Assert.notNull(hotWordId);
        if(hotWordId == null){
            return null;
        }
        return new StringBuffer(HotWord_Attention_).append(hotWordId).toString();
    }

    /**
     * Get album cache key by albumId.
     * @param albumId
     *            not null.
     * @return
     */
    public static String getAlbumKey(Long albumId) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(PlayCacheEntity_A_).append(albumId).toString();
    }

    /**
     * Get album cache key by albumId.
     * @param albumId
     *            not null.
     * @return
     */
    public static String getAlbumKey(String albumId) {
//        Assert.notNull(albumId);
        if(StringUtils.isBlank(albumId)){
            return null;
        }
        return new StringBuffer(PlayCacheEntity_A_).append(albumId).toString();
    }

    /**
     * Get video cache key by videoId.
     * @param videoId
     *            not null.
     * @return
     */
    public static String getVideoKey(Long videoId) {
//        Assert.notNull(videoId);
        if(videoId == null){
            return null;
        }
        return new StringBuffer(PlayCacheEntity_V_).append(videoId).toString();
    }

    /**
     * Get video cache key by videoId.
     * @param videoId
     *            not null.
     * @return
     */
    public static String getVideoKey(String videoId) {
//        Assert.notNull(videoId);
        if(StringUtils.isBlank(videoId)){
            return null;
        }
        return new StringBuffer(PlayCacheEntity_V_).append(videoId).toString();
    }

    /**
     * Get play interact by videoid
     * @param videoId
     * @return
     */
    public static String getPlayInteractKeyByVideoId(Long videoId) {
//        Assert.notNull(videoId);
        if(videoId == null){
            return null;
        }
        return new StringBuffer(PlayInteract_V_).append(videoId).toString();
    }

    /**
     * Get play interact by videoid
     * @param videoId
     * @return
     */
    public static String getPlayInteractKeyByVideoId(String videoId) {
        if(StringUtils.isBlank(videoId)){
            return null;
        }
        return new StringBuffer(PlayInteract_V_).append(videoId).toString();
    }

    /**
     * Get play interact by album id.
     * @param albumId
     * @return
     */
    public static String getPlayInteractKeyByAlbumId(Long albumId) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(PlayInteract_A_).append(albumId).toString();
    }

    /**
     * Get play interact by album id.
     * @param albumId
     * @return
     */
    public static String getPlayInteractKeyByAlbumId(String albumId) {
//        Assert.notNull(albumId); 
        if(StringUtils.isBlank(albumId)){
            return null;
        }
        return new StringBuffer(PlayInteract_A_).append(albumId).toString();
    }

    /**
     * Get positive video list by album id, page size is 100.
     * @param albumId
     * @param page
     *            0, 1, 2...
     * @return
     */
    public static String getPositiveVideoListByPage(Long albumId, Integer page) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(POSITIVE_VIDEOLIST_ALBUM_PAGE).append(albumId).append("_").append(page).toString();
    }

    /**
     * Get positive video list by album id
     * @param albumId
     * @param month
     *            201601, 201611...
     * @return
     */
    public static String getPositiveVideoListByMonth(Long albumId, Integer month) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(POSITIVE_VIDEOLIST_ALBUM_MONTH).append(albumId).append("_").append(month).toString();
    }

    /**
     * 获得专辑下预告片缓存key
     * @param albumId
     * @return
     */
    public static String getPreVideoListKey(Long albumId) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(YUGAO_VIDEOLIST_ALBUM).append(albumId).toString();
    }

    /**
     * 获得专辑下其他视频列表
     * @param albumId
     * @return
     */
    public static String getOtherVideoListKey(Long albumId) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(OTHER_VIDEOLIST_ALBUM).append(albumId).toString();
    }

    /**
     * 获得专辑下其他视频列表
     * @param albumId
     * @return
     */
    public static String getOtherVideoListKey(String albumId) {
//        Assert.notNull(albumId);
        if(StringUtils.isBlank(albumId)){
            return null;
        }
        return new StringBuffer(OTHER_VIDEOLIST_ALBUM).append(albumId).toString();
    }

    /**
     * 获得视频分页列表索引
     * @param albumId
     * @return
     */
    public static String getVideoPageIndex(Long albumId) {
//        Assert.notNull(albumId);
        if(albumId == null){
            return null;
        }
        return new StringBuffer(POSITIVE_VIDEOS_PAGELIST_ALBUM).append(albumId).toString();
    }

    public static String getPayChannelKey(Integer channelId){
//        Assert.notNull(channelId);
        if(channelId == null){
            return null;
        }
        return new StringBuffer(BOSS_PAY_CHANNEL_DATA).append(channelId).toString();
    }

    @Deprecated
    public static final String SUPERLIVE_LIVEROOM = "SuperLiveLiveRoom";// 直播列表
    public static final String SUPERLIVE_LIVEROOM_SPORTS = "SuperLiveLiveRoomSports";// 体育直播列表
    public static final String SUPERLIVE_LIVEROOM_ENT = "SuperLiveLiveRoomEnt";// 娱乐直播列表
    public static final String SUPERLIVE_LIVEROOM_OTHER = "SuperLiveLiveRoomOther";// 其他直播列表
    public static final String SUPERLIVE_LIVEROOM_MUSIC = "SuperLiveLiveRoomMusic";// 音乐直播列表
    public static final String SUPERLIVE_LIVEROOM_LIVEID = "SuperLiveLiveRoomLiveId_";// 获取单个节目信息

    public static final String SUPERLIVE_CATEGORY = "SuperLiveCategory";// 超级live类型列表：例如：直播、卫视、地方、明星、电影等
    public static final String SUPERLIVE_TOP_CATEGORY = "SuperLiveTopCategory";// 超级live类型列表：例如：直播、卫视、地方、明星、电影等
    public static final String SUPERLIVE_CATEGORY_CHANNEL_LIST_ = "SuperLiveCategoryChannelList_";// 类型下的频道列表
    public static final String SUPERLIVE_CATEORY_DEFUALT_CHANNEL_DATA_ID = "SuperLiveCategoryDefaultChannelDataId_";// 类型下的默认频道列表
    public static final String SUPERLIVE_USER_ORDER = "SuperLiveUserOrder_";// 用户数据订制
    public static final String SUPERLIVE_CMS_CATEGORY_DATA = "SuperLiveCmsCategoryData";// CMS中配置的一级分类及频道下数据
    public static final String SUPERLIVE_HOMEPAGE_DTO = "SuperLiveHomePageDto";// 超级首页

    // 频道详情Dto
    public static final String SUPERLIVE_CHANNEL_ID = "SuperLiveChannelId_";
    // 一级分类+标签/子分类下的频道列表
    public static final String SUPERLIVE_SUBTYPE_CHANNELLIST_ = "SuperLiveSubTypeChannelList_";
    public static final String SUPERLIVE_SUBCATEGORY_LIST_ = "SuperLiveSubcategoryList_";// 根据父类查询子类的id
    // 所有直播列表
    public static final String SUPERLIVE_LIVEROOM_PORGRAMS_ALL = "SuperLiveLiveRoomProgramsAll";
    // 直播一级分类节目列表 ,目前体育高网、体育足球、体育综合、娱乐、音乐
    public static final String SUPERLIVE_LIVEROOM_TOP_CATEGORY_PORGRAMS_ = "SuperLiveLiveRoomTopCategoryPrograms_";
    // 直播一级（体育、娱乐、音乐）+二级类（网球、国内足球、发布会，演唱会）直播节目列表
    public static final String SUPERLIVE_LIVEROOM_SUB_CATEGORY_PORGRAMS_ = "SuperLiveLiveRoomSubCategoryPrograms_";
    public static final String SUPERLIVE_USER_CHANNEL_ORDER_ = "SuperLiveUserChannelOrder_";

    public static final String SUPERLIVE_CMS_REC_DATA = "SuperLiveCmsRecData";// CMS中配置的一级分类及频道下数据

    public static final String SUPERLIVE_PUSHED_MAP = "SuperLivePushedMap"; // 超级live已经推送的直播消息列表

    public interface channel {
        public static final String HOME_PAGE_LIVE = "HOME_PAGE_LIVE";// 老版本的大首页直播数据

        public static final String CHANNEL_PAGE_LIVE_ = "channel_page_live_";

        public static final String CHANNEL_PAGE_NAVIGATION = "Channel_page_navigation_";

        public static final String CHANNEL_PAGE_TOP = "Channel_page_top_";

        public static final String CHANNEL_PAGE_DATA = "Channel_page_data_";

        public static final String CHANNEL_SEARCH_PAGE = "Channel_search_page_";

        public static final String CHANNEL_PAGE_LIVE_WCODE_ = "channel_page_live_{wcode}_";//多语言

        public static final String CHANNEL_TVOD_PAGE = "Channel_tvod_page_";
    }


    /**
     * 生成key方法
     * @param baseKey
     * @param value
     * @return
     */
    public static final String buildKey(String baseKey, String... value) {
        if (value != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < value.length; i++) {
                sb.append(value[i]);
                if (i < value.length - 1) {
                    sb.append("_");
                }
            }
            baseKey += sb.toString();
        }
        return baseKey;
    }

    public static void main(String[] args) {
        System.out.println(getAlbumKey(""));
    }

    /**
     * 获取音频key
     * @param audioId
     * @return
     */
    public static String getAudioKey(Long audioId) {
//        Assert.notNull(audioId);
        if(audioId == null){
            return null;
        }
        return new StringBuffer(PlayCacheEntity_Audio).append(audioId).toString();
    }

    /**
     * 获取音频专辑key
     * @param ostId
     * @return
     */
    public static String getOstKey(Long ostId) {
//        Assert.notNull(ostId);
        if(ostId == null){
            return null;
        }
        return new StringBuffer(PlayCacheEntity_Ost).append(ostId).toString();
    }

    /**
     * 获取音频索引
     * @param ostId
     * @return
     */
    public static String getAudiosPageIndexKey(Long ostId){
//        Assert.notNull(ostId);
        if(ostId == null){
            return null;
        }
        return new StringBuffer(Audios_Pagelist_Ost).append(ostId).toString();
    }

    /**
     * 音频下分页正片视频列表
     */
    public static String getAudiosListByPage(Long ostId, Integer page){
//        Assert.notNull(ostId);
//        Assert.notNull(page);
        if(ostId == null || page == null){
            return null;
        }
        return new StringBuffer(Audiolist_Ost_Page).append(ostId).append("_").append(page).toString();
    }
    
    public static String getLecomVideoPageIndex(Long albumId) {
        if(albumId == null){
            return null;
        }
        return new StringBuffer(LECOM_VIDEOS_PAGELIST_ALBUM).append(albumId).toString();
    }
}
