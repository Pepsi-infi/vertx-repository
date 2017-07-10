package util;

import constants.ChannelConstants;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhushenghao1 on 16/12/2.
 */
public class ChannelSkip {
    public static Map<Integer, String> CHANNEL_SKIP_SEARCH_US = new HashMap<Integer, String>(); // 跳搜索的频道

    public static Map<String, String> CHANNEL_TOP_PAGEID = new HashMap<String, String>(); // 各频道排行的pageid
    public static Map<String, String> CHANNEL_TOP_DATA_TYPE = new HashMap<String, String>(); // 各频道排行的pageid
    public static Map<Integer, String> CHANNEL_MAP = new HashMap<Integer, String>();
    public static Map<String, String> CHANNEL_CMS_FILTER_MAP = new HashMap<>(); // cms筛选跳转配置条件与检索的映射
    public static Map<Integer, String> CHANNEL_CID_TOPTYPE = new HashMap<Integer, String>(); // 各频道排行的pageid

    static {
        CHANNEL_SKIP_SEARCH_US.put(1012,
                "filter=playStreamFChannelSkipeatures:4k;dt:1;cg:1;or:1;vt=180001&channelid=1012&page=1&pageSize=50"); // 4K频道
        CHANNEL_SKIP_SEARCH_US.put(1013,
                "filter=playStreamFeatures:1080p;dt:1;cg:1;or:1;vt=180001&channelid=1013&page=1&pageSize=50"); // 1080p
        CHANNEL_SKIP_SEARCH_US.put(1014,
                "filter=playStreamFeatures:3d;dt:1;cg:1;or:1;vt=180001&channelid=1014&page=1&pageSize=50"); // 3D频道
        // CHANNEL_SKIP_SEARCH.put(1023,
        // "filter=playStreamFeatures:db;dt:1;cg:1;or:1;vt=180001&channelid=1023&page=1&pageSize=50");//
        CHANNEL_SKIP_SEARCH_US.put(1050,
                "filter=playStreamFeatures:dts;dt:1;cg:1;or:1;vt=180001&channelid=1050&page=1&pageSize=50");// dts频道
        CHANNEL_SKIP_SEARCH_US.put(1001,
                "filter=playStreamFeatures:db;dt:1;cg:1;or:1;vt=180001&channelid=1001&page=1&pageSize=50");// 杜比频道
        // 影院声
        CHANNEL_SKIP_SEARCH_US.put(1024,
                "filter=playStreamFeatures:2k;dt:1;cg:1;or:1;vt=180001&channelid=1024&page=1&pageSize=50"); // 2k频道
        CHANNEL_SKIP_SEARCH_US.put(1023,
                "filter=playStreamFeatures:db;dt:1;cg:1;or:1;vt=180001&channelid=1023&page=1&pageSize=50");// 影院声

        CHANNEL_TOP_PAGEID.put("1002921117", "dayTVPlay.jsn"); // 电视剧排行
        CHANNEL_TOP_PAGEID.put("1002921172", "dayFilmPlay.jsn"); // 电影排行
        CHANNEL_TOP_PAGEID.put("1002921160", "dayVarPlay.jsn"); // 综艺排行
        CHANNEL_TOP_PAGEID.put("1002921094", "dayEntPlay.jsn"); // 娱乐排行
        CHANNEL_TOP_PAGEID.put("1002920002", "dayMusicPlay.jsn"); // 音乐排行
        CHANNEL_TOP_PAGEID.put("1002948836", "daySportPlay.jsn"); // 体育排行
        CHANNEL_TOP_PAGEID.put("1002921053", "dayDocPlay.jsn"); // 记录片排行
        CHANNEL_TOP_PAGEID.put("1002950208", "dayComicPlay.jsn"); // 动漫排行
        CHANNEL_TOP_PAGEID.put("1002921096", "dayFinancePlay.jsn"); // 财经排行
        CHANNEL_TOP_PAGEID.put("1002949323", "dayCarPlay.jsn"); // 汽车排行
        CHANNEL_TOP_PAGEID.put("1002949324", "dayFashionPlay.jsn"); // 风尚排行
        CHANNEL_TOP_PAGEID.put("1002949326", "dayTravelPlay.jsn"); // 旅游排行

        CHANNEL_CID_TOPTYPE.put(2, "dayTVPlay.jsn"); // 电视剧排行
        CHANNEL_CID_TOPTYPE.put(1, "dayFilmPlay.jsn"); // 电影排行
        CHANNEL_CID_TOPTYPE.put(11, "dayVarPlay.jsn"); // 综艺排行
        CHANNEL_CID_TOPTYPE.put(3, "dayEntPlay.jsn"); // 娱乐排行
        CHANNEL_CID_TOPTYPE.put(9, "dayMusicPlay.jsn"); // 音乐排行
        CHANNEL_CID_TOPTYPE.put(4, "daySportPlay.jsn"); // 体育排行
        CHANNEL_CID_TOPTYPE.put(16, "dayDocPlay.jsn"); // 记录片排行
        CHANNEL_CID_TOPTYPE.put(5, "dayComicPlay.jsn"); // 动漫排行
        CHANNEL_CID_TOPTYPE.put(22, "dayFinancePlay.jsn"); // 财经排行
        CHANNEL_CID_TOPTYPE.put(14, "dayCarPlay.jsn"); // 汽车排行
        CHANNEL_CID_TOPTYPE.put(20, "dayFashionPlay.jsn"); // 风尚排行
        CHANNEL_CID_TOPTYPE.put(23, "dayTravelPlay.jsn"); // 旅游排行

        CHANNEL_MAP.put(1, "CHANNEL.NAME.FILM");
        CHANNEL_MAP.put(2, "CHANNEL.NAME.TV");
        CHANNEL_MAP.put(3, "CHANNEL.NAME.ENT");
        CHANNEL_MAP.put(4, "CHANNEL.NAME.SPORT");
        CHANNEL_MAP.put(5, "CHANNEL.NAME.CARTOON");
        CHANNEL_MAP.put(9, "CHANNEL.NAME.MUSIC");
        CHANNEL_MAP.put(11, "CHANNEL.NAME.VARITY");
        CHANNEL_MAP.put(14, "CHANNEL.NAME.CAR");
        CHANNEL_MAP.put(16, "CHANNEL.NAME.DFILM");
        CHANNEL_MAP.put(20, "CHANNEL.NAME.FASHION");
        CHANNEL_MAP.put(22, "CHANNEL.NAME.CAIJING");
        CHANNEL_MAP.put(23, "CHANNEL.NAME.TRAVELLING");
        CHANNEL_MAP.put(34, "CHANNEL.NAME.PARENT");
        CHANNEL_MAP.put(35, "CHANNEL.NAME.PET");
        CHANNEL_MAP.put(36, "CHANNEL.NAME.ADV");
        CHANNEL_MAP.put(104, "CHANNEL.NAME.GAME");
        CHANNEL_MAP.put(1000, "CHANNEL.NAME.VIP");// 移动会员-移动专用
        CHANNEL_MAP.put(1003, "CHANNEL.NAME.VIP");// 会员频道
        CHANNEL_MAP.put(1004, "CHANNEL.NAME.NBA");
        CHANNEL_MAP.put(1008, "CHANNEL.NAME.HOMEMADE");
        CHANNEL_MAP.put(1009, "CHANNEL.NAME.ZIXUN");
        CHANNEL_MAP.put(1012, "CHANNEL.NAME.4K");
        CHANNEL_MAP.put(1013, "CHANNEL.NAME.1080P");
        CHANNEL_MAP.put(1014, "CHANNEL.NAME.3D");
        CHANNEL_MAP.put(1017, "CHANNEL.NAME.USTV");
        CHANNEL_MAP.put(1019, "CHANNEL.NAME.YINGCHAO");
        CHANNEL_MAP.put(1020, "CHANNEL.NAME.TECH");
        CHANNEL_MAP.put(1021, "CHANNEL.NAME.EDU");
        CHANNEL_MAP.put(1023, "CHANNEL.NAME.DTS");
        CHANNEL_MAP.put(1024, "CHANNEL.NAME.2K");

        CHANNEL_CMS_FILTER_MAP.put("3", "sc"); // 影片分类
        CHANNEL_CMS_FILTER_MAP.put("5", "area"); // 地区
        CHANNEL_CMS_FILTER_MAP.put("7", "language"); // 语言
        CHANNEL_CMS_FILTER_MAP.put("179", "isEnd"); // 是否完结
        CHANNEL_CMS_FILTER_MAP.put("346", "isHomemade");// 是否自制

        CHANNEL_CMS_FILTER_MAP.put("18", "vtp");// 专辑类型
        CHANNEL_CMS_FILTER_MAP.put("29", "download_platform");// 允许下载平台
        CHANNEL_CMS_FILTER_MAP.put("42", "ph");// 播放平台
        CHANNEL_CMS_FILTER_MAP.put("62", "contentRating");// 内容分级
        CHANNEL_CMS_FILTER_MAP.put("14", " payPlatform");// 付费平台
        CHANNEL_CMS_FILTER_MAP.put("67", "coopPlatform");// 合作平台

        CHANNEL_CMS_FILTER_MAP.put("59", "st");// 分类
    }

    public static String getChannelSkipUrl(String type, String pageid, Integer cid,
                                           String specialMark) {
        if (StringUtils.isNotBlank(pageid) && "18".equals(type)) {
            return ChannelConstants.LE_HTTPS_HOST + ChannelConstants.ADDON_PAGE_PATH + "?addonid=" + pageid + "&page=1";
        }
        return getChannelSkipUrl(pageid, cid, specialMark);
    }

    public static String getChannelSkipUrl(String pageid, Integer cid, String specialMark) {
        if ("1".equals(specialMark) && cid != null) {
            return ChannelConstants.LE_HTTPS_HOST + ChannelConstants.ADDON_PAGE_PATH+ "?cid=" + cid;
        }
        Map<Integer, String> searchurl = CHANNEL_SKIP_SEARCH_US;
        if (searchurl!=null&&searchurl.size()>0) {
            searchurl = CHANNEL_SKIP_SEARCH_US;
        }

        if (cid != null && searchurl.containsKey(cid)) {
            // 搜索
            return ChannelConstants.LE_HTTPS_HOST + ChannelConstants.SEARCH_DATA_PATH + "?" + searchurl.get(cid);
        }
        return ChannelConstants.LE_HTTPS_HOST + ChannelConstants.CHANNEL_DATA_PATH + "?pageid=" + pageid;
    }

    //@le_auto_letv Le车载乐视视频单独处理
    public static String getLeAutoChannelSkipUrl(String channelId,String filter){
        return ChannelConstants.LE_HTTPS_HOST + ChannelConstants.SEARCH_DATA_PATH +"?filter=" + filter + "&cid=" + channelId + "&page=1&pageSize=60";
    }
}