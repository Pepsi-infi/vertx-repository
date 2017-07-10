package tp.search.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhushenghao1 on 17/5/5.
 */
public class SearchTpConstant {

    public final static String ERROR_CODE_SEARCH_PARAM_ERROR = "SSC0001"; // 错误码，参数不合法

    public static class DATA_TYPE {
        public final static Integer DATA_TYPE_ALUM = 1; // 搜索数据格式--专辑
        public final static Integer DATA_TYPE_VIDEO = 2; // 搜索数据格式--视频
        public final static Integer DATA_TYPE_SUBJECT = 4; // 搜索数据格式--专题
    }

    public static final int SEARCH_BY_DT = -2; // 数据类型
    public static final int SEARCH_BY_CATEGORY = -1; // 分类(category)
    public static final int SEARCH_BY_SUBCATEGORY = 1; // 子分类分类
    public static final int SEARCH_BY_LABEL = 2; // 标签
    public static final int SEARCH_BY_AREA = 3; // 地区
    public static final int SEARCH_BY_INITIAL_LETTER = 4; // 首字母
    public static final int SEARCH_BY_ACTOR = 5; // 主演、主持人
    public static final int SEARCH_BY_DIRECTOR = 6; // 导演
    public static final int SEARCH_BY_RELEASEDATE = 7; // 发布日期
    public static final int SEARCH_BY_AGE = 8; // 适应年龄
    public static final int SEARCH_BY_ALBUMTYPE = 9; // 专辑类型
    public static final int SEARCH_BY_ALBUMSTYLE = 10; // 专辑风格
    public static final int CARTONGRANKLIST = 11; // 动漫排行
    public static final int FILMRANKLIST = 12; // 电影排行
    public static final int TVRANKLIST = 13; // 电视剧排行
    public static final int ONLINE = 14; // 网络院线
    public static final int SEARCH_BY_TV = 16; // 电视台
    public static final int SEARCH_BY_YUANXIAN = 18; // 是否tv收费院线
    public static final int SEARCH_BY_STREAM_NEW = 19; // 码流
    public static final int SEARCH_BY_ORDER = 20;// 排序
    public static final int SEARCH_BY_STARRING_PLAY = 21;// 演员饰演
    public static final int SEARCH_BY_ALBUM_IS_END = 22;// 专辑是否完结
    public static final int SEARCH_BY_LANGUAGE = 23;// 按语言检索
    public static final int SEARCH_BY_STAR = 24;// 明星
    public static final int SEARCH_BY_STAR_GEND = 25;// 明星性别
    public static final int SEARCH_BY_STAR_PRO = 26;// 明星职业
    public static final int SEARCH_BY_HOME_MADE = 27;// 是否自制剧
    public static final int SEARCH_BY_RECORD_COMPANY = 28;// 来源
    public static final int SEARCH_BY_MMS_CMS_DATA = 29;// 媒资CMS数据
    public static final int SEARCH_BY_PID = 30;// 指定专辑id自动抓取视频列表
    public static final int SEARCH_BY_ZHUANTI_DATA = 31;// label专题列表
    public static final int SEARCH_ZHUANTI = 32;// 专题检索
    public static final int QZRANKLIST = 33;// 亲子排行榜
    public static final int SPORTRANKLIST = 34;// 体育排行榜
    public static final int ENTRANKLIST = 35;// 娱乐排行榜
    public static final int VARRANKLIST = 36;// 综艺排行榜
    public static final int MUSICRANKLIST = 37;// 音乐排行榜
    public static final int DOCRANKLIST = 38;// 纪录片排行榜
    public static final int SEARCH_BY_TAG = 39; // 汽车tag搜索
    public static final int SEARCH_RECREATION_TYPE = 40; // 娱乐类型
    public static final int SEARCH_SPORT_STYLE = 41; // 体育类型
    public static final int SEARCH_BY_VIDEO_DURATION = 42; // 按视频时长检索
    public static final int SEARCH_BY_COOPERATION = 43; // 按合作平台检索
    public static final int SEARCH_BY_PLAYSTREAMS = 49;// 按清晰充检索
    // 乐视儿童相关
    public static final int SEARCH_BY_AGE2 = 44; // 按年龄检索
    public static final int SEARCH_BY_CONTENT = 45; // 按内容属性检索
    public static final int SEARCH_BY_FUNCTION = 46; // 按功能检索
    public static final int SEARCH_BY_LANGUAGE_CHILD = 48;// 按语言检索

    public static final int SEARCH_BY_ALONE = 47; // 是否独播
    public static final int SEARCH_BY_ISPAY = 50;// 是否付费
    public static final int SEARCH_BY_BRAND = 51;// 根据品牌
    public static final int SEARCH_BY_YEAR = 52;// 年份检索

    /**
     * 是否需要将CMS接口返回值从ISO-8859-1转换为UTF-8开关值，1--“开启”，“0”关闭
     */
    public static final String SEARCH_CMS_DECODE_ISO_TO_UTF_ON = "1";

    public final static String LESO_SRC_VRS = "1"; // 专辑来源---内网媒资
    public final static String LESO_SRC_WEB = "2"; // 专辑来源---外网

    /**
     * 请求来源 后期从terminalApplication转换!!!!!!!!!!!!!!!!!!
     */
    public final static String SEARCH_FROM_TV_LESO = "tv_01";// tv乐搜的
    public final static String SEARCH_FROM_TV = "tv_02";// tv版的

    public final static String SEARCH_WCODE_HK = "HK";

    /**
     * 乐视儿童--年龄与内容标签value与推荐接口的value映射
     */
    private static final Map<String, String> TAG_AGE_VALUE = new HashMap<String, String>();// 年龄映射
    private static final Map<String, String> TAG_FUNCTION_VALUE = new HashMap<String, String>();// 按功能映射
    private static final Map<String, String> TAG_CONTENT_VALUE = new HashMap<String, String>();// 按内容属性映射
    private static final Map<String, String> TAG_LANGUAGE_VALUE = new HashMap<String, String>();// 按语言属性映射

    static {
        // 老年龄value
        /*
         * TAG_AGE_VALUE.put("1", "512008,512009,512010,512011,512012,512013");
         * TAG_AGE_VALUE.put("2", "512008");
         * TAG_AGE_VALUE.put("3", "512009");
         * TAG_AGE_VALUE.put("4", "512010");
         * TAG_AGE_VALUE.put("5", "512011");
         * TAG_AGE_VALUE.put("6", "512012");
         * TAG_AGE_VALUE.put("7", "512013");
         */
        TAG_AGE_VALUE.put("1", "840001,840002,840003,840004,840005,840006,840007");
        TAG_AGE_VALUE.put("2", "840001,840002,840003");// 0-3岁
        TAG_AGE_VALUE.put("3", "840002");// 1-2岁
        TAG_AGE_VALUE.put("4", "840003");// 2-3岁
        TAG_AGE_VALUE.put("5", "840004");// 3-4岁
        TAG_AGE_VALUE.put("6", "840005");// 4-5岁
        TAG_AGE_VALUE.put("7", "840006");// 5-6岁
        TAG_AGE_VALUE.put("8", "840007");// 6-12岁
        // 老功能value
        /*
         * TAG_FUNCTION_VALUE
         * .put("1",
         * "592043,592044,592045,592046,592047,592042,592048,592049,592050,592051,592052,592053,"
         * +
         * "592059,592060,592061,592062,592063,592064,592065,592066,592067,592068,592069,592070,592071,592072,592073,592074,592075,"
         * +
         * "592076,592077,592078,592079,592080,592081,592082,592083,592084,592085,592086,592087,592088,592089,592090,592091"
         * );// 不限
         * TAG_FUNCTION_VALUE.put("2", "592043,592044,592045,592046,592047");//
         * 语言
         * TAG_FUNCTION_VALUE.put("3",
         * "592042,592048,592049,592050,592051,592052,592053");// 兴趣
         * TAG_FUNCTION_VALUE.put("4",
         * "592059,592060,592061,592062,592063,592064,592065,592066");// 认知
         * TAG_FUNCTION_VALUE.put("5",
         * "592067,592068,592069,592070,592071,592072,592073,592074,592075");//
         * 人格
         * TAG_FUNCTION_VALUE.put("6", "592076,592077,592078,592079,592080");//
         * 习惯
         * TAG_FUNCTION_VALUE.put("7",
         * "592081,592082,592083,592084,592085,592086");// 社交
         * TAG_FUNCTION_VALUE.put("8", "592087,592088,592089,592090,592091");//
         * 体能
         */
        TAG_FUNCTION_VALUE.put("1", "830001,830008,830015,830016,830017,830018,"
                + "830009,830019,830020,830021,830022,830023,830024,830025,"
                + "830010,830026,830027,830028,830029,830030,830031,830032,830033,"
                + "830011,830034,830035,830036,830037,830038,830039,830040,830041,830042,"
                + "830012,830043,830044,830045,830046,830047," + "830013,830048,830049,830050,830051,830052,830053,"
                + "830014,830054,830055,830056,830057,830058");// 不限
        TAG_FUNCTION_VALUE.put("2", "830001,830008,830015,830016,830017,830018");// 语言
        TAG_FUNCTION_VALUE.put("3", "830009,830019,830020,830021,830022,830023,830024,830025");// 兴趣
        TAG_FUNCTION_VALUE.put("4", "830010,830026,830027,830028,830029,830030,830031,830032,830033");// 认知
        TAG_FUNCTION_VALUE.put("5", "830011,830034,830035,830036,830037,830038,830039,830040,830041,830042");// 人格
        TAG_FUNCTION_VALUE.put("6", "830012,830043,830044,830045,830046,830047");// 习惯
        TAG_FUNCTION_VALUE.put("7", "830013,830048,830049,830050,830051,830052,830053");// 社交
        TAG_FUNCTION_VALUE.put("8", "830014,830054,830055,830056,830057,830058");// 体能

        // 老分类value
        /*
         * TAG_CONTENT_VALUE.put("1",
         * "750003,750002,750079,750078,750077,750076");// 不限
         * TAG_CONTENT_VALUE.put("2", "750003");// 儿歌
         * TAG_CONTENT_VALUE.put("3", "750002");// 故事
         * TAG_CONTENT_VALUE.put("4", "750079");// 音乐
         * TAG_CONTENT_VALUE.put("5", "750078");// 绘本
         * TAG_CONTENT_VALUE.put("6", "750077");// 动画
         * TAG_CONTENT_VALUE.put("7", "750076");// 课程
         */
        TAG_CONTENT_VALUE.put("1",
                "850001,850002,850003,850004,850005,850006,850007,850008,850009,850010,850011,850012,850013");// 不限
        TAG_CONTENT_VALUE.put("2", "850001");// 儿歌
        TAG_CONTENT_VALUE.put("3", "850002");// 故事
        TAG_CONTENT_VALUE.put("4", "850003");// 音乐
        TAG_CONTENT_VALUE.put("5", "850004");// 绘本
        TAG_CONTENT_VALUE.put("6", "850005");// 动画
        TAG_CONTENT_VALUE.put("7", "850006");// 课程
        TAG_CONTENT_VALUE.put("8", "850007");// 节目
        TAG_CONTENT_VALUE.put("9", "850008");// 电影
        TAG_CONTENT_VALUE.put("10", "850009");// 纪录片
        TAG_CONTENT_VALUE.put("11", "850010");// 综艺
        TAG_CONTENT_VALUE.put("12", "850011");// 小学
        TAG_CONTENT_VALUE.put("13", "850012");// 双语
        TAG_CONTENT_VALUE.put("14", "850013");// 父母课程

        TAG_LANGUAGE_VALUE.put("1", "70001,70003,70012");// 不限
        TAG_LANGUAGE_VALUE.put("2", "70001");// 国语
        TAG_LANGUAGE_VALUE.put("3", "70003");// 英语
        TAG_LANGUAGE_VALUE.put("4", "70012");// 无对白

    }

    public static String getAgeValue(String age) {
        return TAG_AGE_VALUE.get(age);
    }

    public static String getFunctionValue(String function) {
        return TAG_FUNCTION_VALUE.get(function);
    }

    public static String getContentValue(String content) {
        return TAG_CONTENT_VALUE.get(content);
    }

    public static String getLanguageValue(String language) {
        return TAG_LANGUAGE_VALUE.get(language);
    }

    public static final String CARD_ID_PARAM_FOR_SEARCH = "102-103-104-106-105";// 去掉了猜你喜欢201

}
