package constants;

import java.util.HashMap;
import java.util.Map;

public class LiveConstants {
    public static final String DEFAULT_CMS_TV_PAGE = "1003551090";
    public static final String DEFAULT_CMS_MOBILE_PAGE = "1003589978";

    
    public interface SplatId{
        public static final String cn_live_clientid ="1036";
        public static final String cibn_tvlive_clientid="1060704002";
    }
    
    public interface LMS{
        public static String Third_douyu_logo = "http://i3.letvimg.com/lc06_iscms/201611/24/10/57/537cc0307c2e40d1ab23749758460e2d.png";
        public static String Third_xingyun_logo = "http://i3.letvimg.com/lc07_iscms/201612/28/15/43/a46fc628fa6649ad90dfd4f63e0b929a.png";
    }
    
    
    public interface CacheType{
        public static final String LIVE_TYPE_LIVEROOM ="cloud_platform";
        public static final String LIVE_TYPE_LUNBO_WEISHI ="lunbo_weishi";
        public static final String LIVE_TYPE_LMS ="lms";
    }
    
    public interface LiveType{
        public static final String LIVE_TYPE_LIVEROOM = "2";// 直播
        public static final String LIVE_TYPE_CHANNEL_LUNBO = "3";// 轮播
        public static final String LIVE_TYPE_CHANNEL_WEISHI = "4";// 卫视
        public static final String LIVE_TYPE_CHANNEL_REQUEST_CHANNEL = "5";// 点播台
        public static final String LIVE_ROOM_DOUYU = "third_5";
        public static final String LIVE_THIRD_CHANNELID_PREFIX = "third_";
    }
    
//    public static class Http{
//        public static final String CMS_HOST = "api.cms.le.com";
//        public static final String LMS_HOST = "api.lms.le.com";
//        public static final String MOBILE_DEFAULT_PAGE = "1003589978";
//        public static final String TV_DEFAULT_PAGE = "1003551090";
//        public static final int PORT = 80;
//    }
    
    public static class Stream{
        public static final Map<String, Integer> STREAM_CODE_SORT_VSLUE = new HashMap<String, Integer>();// 码流排序值
        public static final String CODE_NAME_99 = "99";
        public static final String CODE_NAME_180 = "180";
        public static final String CODE_NAME_350 = "350";
        public static final String CODE_NAME_800 = "800";
        public static final String CODE_NAME_1000 = "1000";
        public static final String CODE_NAME_1300 = "1300";
        public static final String CODE_NAME_720p = "720p";
        public static final String CODE_NAME_1080p = "1080p";
        public static final String CODE_NAME_1080p3m = "1080p3m";
        public static final String CODE_NAME_1080p6m = "1080p6m";
        public static final String CODE_NAME_2K = "2k";// 2k
        public static final String CODE_NAME_2K_H265 = "2k_h265";// 2k
        public static final String CODE_NAME_4K = "4k";// 4k
        public static final String CODE_NAME_3d720p = "3d720p";
        public static final String CODE_NAME_3d1080p = "3d1080p";
        public static final String CODE_NAME_3d1080p6M = "3d1080p6m";
        // 杜比新增加码流--start
        public static final String CODE_NAME_DOLBY_800 = "800_db";
        public static final String CODE_NAME_DOLBY_1300 = "1300_db";
        public static final String CODE_NAME_DOLBY_720p = "720p_db";
        public static final String CODE_NAME_DOLBY_1080p = "1080p_db";
        public static final String CODE_NAME_DOLBY_1080p6m = "1080p6m_db";
        public static final String CODE_NAME_DOLBY_2K = "2k_db";
        public static final String CODE_NAME_DOLBY_4K = "4k_db";

        // 第三方cp码流
        public static final String CODE_NAME_CP_64 = "cp64";
        public static final String CODE_NAME_CP_200 = "cp200";
        public static final String CODE_NAME_CP_350 = "cp350";
        public static final String CODE_NAME_CP_400 = "cp400";
        public static final String CODE_NAME_CP_600 = "cp600";
        public static final String CODE_NAME_CP_800 = "cp800";
        public static final String CODE_NAME_CP_1100 = "cp1100";
        public static final String CODE_NAME_CP_1200 = "cp1200";
        public static final String CODE_NAME_CP_1300 = "cp1300";
        public static final String CODE_NAME_CP_1500 = "cp1500";
        public static final String CODE_NAME_CP_1800 = "cp1800";
        public static final String CODE_NAME_CP_2000 = "cp2000";
        public static final String CODE_NAME_CP_2500 = "cp2500";

        static {
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_99, 0);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_350, 1);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1000, 2);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1300, 3);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_720p, 4);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p, 5);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p3m, 6);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p6m, 7);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p, 8);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d720p, 9);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p6M, 10);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_720p, 11);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1300, 12);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_800, 13);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1080p, 14);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_4K, 15);

            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_64, 1);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_200, 2);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_350, 3);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_400, 4);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_600, 5);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_800, 6);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1100, 7);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1200, 8);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1300, 9);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1500, 10);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1800, 11);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_2000, 12);
            STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_2500, 13);
        }
    }
    
    public static class CMS{
        /**
         * 第三方直播来源 具体值第三方直播提供
         */
        public static final String THIRD_DOUYU_SOURCEID = "5";
        public static final String THIRD_JUMEI_SOURCEID = "6";
        public static final String THIRD_XINGYUN_SOURCEID = "7";
        
        
        /***************** 栏目类型 start ****************/
        /**
         * 栏目类型，轮播：1
         */
        public final static int COLUMN_TYPE_LUNBO = 1;
        /**
         * 栏目类型，卫视：2
         */
        public final static int COLUMN_TYPE_WEISHI = 2;
        /**
         * 栏目类型，直播：3
         */
        public final static int COLUMN_TYPE_LIVE = 3;
        /**
         * 栏目类型，焦点（混合）4
         */
        public final static int COLUMN_TYPE_MIX = 4;

        /***************** 栏目类型 end ****************/
        
        
        /***************** 栏目手动推荐数据类型 start ****************/
        /**
         * 栏目内容数据直播类型，轮播：1
         */
        public final static int COLUMN_CONTENT_TYPE_LUNBO = 1;
        /**
         * 栏目内容数据直播类型，卫视：2
         */
        public final static int COLUMN_CONTENT_TYPE_WEISHI = 2;
        /**
         * 栏目内容数据直播类型，直播：3
         */
        public final static int COLUMN_CONTENT_TYPE_LIVE = 3;
        /**
         * 栏目内容数据直播类型，焦点（混合）4 TODO 混合数据类型应该不存在
         */
        public final static int COLUMN_CONTENT_TYPE_MIX = 4;
        /**
         * 栏目内容数据直播类型，咪咕：5
         */
        public final static int COLUMN_CONTENT_TYPE_MIGU = 5;
        /**
         * 栏目内容数据直播类型，直播占位符：0
         */
        public final static int COLUMN_CONTENT_TYPE_PLACEHOLDER = 0; // 栏目下的直播占位符
        /**
         * 栏目内容数据直播类型，第三方直播占位符：6
         */
        public final static int COLUMN_CONTENT_TYPE_PLACEHOLDER_THIRDPARTY_LIVE = 6; // 栏目下的直播占位符

        /**
         * 栏目内容数据直播类型，活动：7
         */
        public final static int COLUMN_CONTENT_TYPE_ACTIVITY = 7; // 栏目下的直播占位符
        /***************** 栏目手动推荐数据类型 end ****************/

        /***************** 栏目状态 start ****************/
        /**
         * 栏目状态：1 上线
         */
        public final static int COLUMN_STATUS_ONLINE = 1;
        /**
         * 栏目状态：0 下线
         */
        public final static int COLUMN_STATUS_OFFLINE = 0;
        /***************** 栏目状态 end ****************/
        
        
        /**
         * 跳转到直播：101
         */
        public static final int CMS_RTYPE_LIVE = 101;
        /**
         * 跳转到轮播：102
         */
        public static final int CMS_RTYPE_LECHANNEL = 102;
        
        /**
         * 跳转到H5：201
         */
        public static final int CMS_RTYPE_H5_WEBVIEW = 201;
        
        /**
         * 跳转到内部webview：201
         */
        public static final int CMS_RTYPE_INNER_WEBVIEW = 201;
        /**
         * 跳转到外部webview：202
         */
        public static final int CMS_RTYPE_EXTERNAL_WEBVIEW = 202;
        /**
         * 跳转到一级栏目：301
         */
        public static final int CMS_RTYPE_FIRST_CATEGORY = 301;
        /**
         * 跳转到二级栏目：302
         */
        public static final int CMS_RTYPE_SECOND_CATEGORY = 302;
        public static final Map<Integer, Integer> CMS_COLUMN_ACTIVITY_RTYPE_2_LIVE_RTYPE = new HashMap<Integer, Integer>(4);
        static{
            CMS_COLUMN_ACTIVITY_RTYPE_2_LIVE_RTYPE.put(6, 201);// H5对应内部webview
            CMS_COLUMN_ACTIVITY_RTYPE_2_LIVE_RTYPE.put(8, 301);// 跳转到一级栏目
            CMS_COLUMN_ACTIVITY_RTYPE_2_LIVE_RTYPE.put(9, 302);// 跳转到二级栏目
            CMS_COLUMN_ACTIVITY_RTYPE_2_LIVE_RTYPE.put(10, 101);// 跳转到直播
            CMS_COLUMN_ACTIVITY_RTYPE_2_LIVE_RTYPE.put(11, 102);// 跳转到轮播、卫视等
        }
        
        
        public final static String BLOCK_DATA_TYPE_CHANNEL = "1"; // 数据类型定义--专辑
        public final static String BLOCK_DATA_TYPE_ADDON = "2"; // 数据类型定义--视频

        public final static String CMS_TYPE_CHANNEL_PAGE = "7";// cms中基本信息维护中的页面
        public final static String CMS_TYPE_ADDON_PAGE = "18";// cms中基本信息维护中的addon页面,默认各终端一致

        public final static String CMS_TYPE_VIDEO = "1"; // cms中基本信息维护中的视频
        public final static String CMS_TYPE_ALBUM = "2"; // cms中基本信息维护中的专辑
        
        public static final String CMS_CONTENT_TYPE_FIXED = "4";// 固定内容
        public static final String CMS_CONTENT_TYPE_SEARCH = "5";// 搜索类型
    }

    public static class Client{
        /**
         * 特别的手机端得标识是mobilelive,但是客户端如果是手机的时候不传这个参数，因此这个参数不存在则代表手机端
         */
        public static final String LIVE_SPLAT_CLIENT_MOBILE = "mobilelive";
        /**
         * splatClent 为tv端：tvlive
         */
        public static final String LIVE_SPLAT_CLIENT_TVLIVE = "tvlive";
        /**
         * splatClent 为atv端：atv
         */
        public static final String LIVE_SPLAT_CLIENT_ATV = "atv";

        /**
         * splatClent 为roku端：roku
         */
        public static final String LIVE_SPLAT_CLIENT_ROKU = "roku";

        /**
         * splatClent 为vizio端：vizio
         */
        public static final String LIVE_SPLAT_CLIENT_VIZIO = "vizio";
    }
    
}
