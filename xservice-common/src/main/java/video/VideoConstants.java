package video;

public class VideoConstants {

    /**
     * 视频类型
     * @author wanglonghu
     */
    public interface VideoType {

        public static final int ZHENG_PIAN = 180001;// 正片

        public static final int YU_GAO_PIAN = 180002;// 预告片

        public static final int PIAN_DUAN = 182202;// 片段

        public static final int XING_ZUO = 181204;// 星座

        public static final int SHENG_XIAO = 181205;// 生肖
    }

    /**
     * 内容类型分类
     * @author wanglonghu
     */
    public interface Category {

        public static final int FILM = 1;// 电影

        public static final int TV = 2;// 电视剧

        public static final int ENT = 3;// 娱乐

        public static final int SPORT = 4;// 体育

        public static final int CARTOON = 5;// 动漫

        public static final int ZIXUN = 1009;// 资讯

        public static final int EDUCATION = 1021;// 教育

        public static final int YUAN_CHUANG = 7;// 原创

        public static final int OTHER = 8;// 其他

        public static final int MUSIC = 9;// 音乐

        public static final int FUNNY = 10;// 搞笑

        public static final int VARIETY = 11;// 综艺

        public static final int KE_JIAO = 12;// 科教

        public static final int SHENG_HUO = 13;// 生活

        public static final int CAR = 14;// 汽车

        public static final int DFILM = 16;// 纪录片

        public static final int GONG_KAI_KE = 17;// 公开课

        public static final int LETV_MADE = 19;// 乐视制造

        public static final int FENG_SHANG = 20;// 风尚

        public static final int CAI_JING = 22;// 财经

        public static final int TRAVEL = 23;// 旅游

        public static final int HOTSPOT = 30;// 热点

        public static final int QU_YI = 32;// 曲艺

        public static final int XI_QU = 33;// 戏曲

        public static final int PARENTING = 34;// 亲子

        public static final int AD = 36;// 广告

        public static final int TEACH = 1021;// 教育

    }

    /**
     * 地区常量，视频的地区属性
     * @author wanglonghu
     */
    public interface Area {
        public static final String CN = "50001";// 中国大陆

        public static final String HK = "50002";// 香港

        public static final String TW = "50003";// 台湾

        public static final String US = "50071";// 美国
    }

    /**
     * 演员角色
     * @author wanglonghu
     */
    public interface ActorRole {
        public static final int STARRING = 0;// 主演

        public static final int DIRECTOR = 1;// 导演

        public static final int SCRIPTWRITER = 2;// 编剧

        public static final int PRODUCER = 3;// 制片人

        public static final int COMPERE = 4;// 主持人
    }

    /**
     * 各平台版权标识
     */
    public interface Copyright {
        public static final int ALL = 0;// 全网数据，不区分版权
        public static final int PHONE = 420003;// 手机版权
        public static final int WEB = 420001; // 网页版权
        public static final int PAD = 420007; // PAD版权
        public static final int TV = 420005; // TV版权
    }

    public interface Downloadright {
        public static final String PHONE = "290003";// 手机可下载
        public static final String WEB = "290001";// PC可下载
        public static final String PAD = "290002";// 平板可下载
        public static final String TV = "290005";// TV可下载
    }

    public static interface vtypeFlag {
        public static final String VIDEO_TYPE_VR = "2";
    }

    // 语种
    public interface AudioTrack {
        public static final String Mandarin = "1000";// 国语
        public static final String Cantonese = "1001";// 粤语
        public static final String English = "1002";// 英语
        public static final String Korean = "1003";// 韩语
        public static final String Japanese = "1004";// 日语
        public static final String Russian = "1005";// 俄语
        public static final String French = "1006";// 法语
        public static final String German = "1007";// 德语
        public static final String Italian = "1008"; // 意大利语
        public static final String Spanish = "1009";// 西班牙语
        public static final String other = "1010";// 其他
    }

    // 字幕
    public interface Subtile {
        public static final String CHS = "1000";// 中文简体
        public static final String CHT = "1001";// 中文繁体
        public static final String EN = "1002";// 英文
        public static final String CHS_EN = "1003";// 中英文简体
        public static final String CHT_EN = "1004";// 中英文繁体
    }

    // 字幕默认优先级
    public static final String[] SUBTITLEORDER_LECOM = { Subtile.EN, Subtile.CHS_EN, Subtile.CHT_EN, Subtile.CHS,
            Subtile.CHT };
    public static final String[] SUBTITLEORDER_LEADING = { Subtile.CHS_EN, Subtile.CHT_EN, Subtile.CHS, Subtile.CHT,
            Subtile.EN };
    // LECOM音轨优先级 英语/西班牙语/法语/德语/意大利语/国语/粤语/韩语/日语/俄语
    public static final String[] AUDIOTRACKORDER_LECOM = { AudioTrack.English, AudioTrack.Spanish, AudioTrack.French,
            AudioTrack.German, AudioTrack.Italian, AudioTrack.Mandarin, AudioTrack.Cantonese, AudioTrack.Korean,
            AudioTrack.Japanese, AudioTrack.Russian };
    // LEADING音轨优先级 国语/粤语/英语/韩语/日语/俄语/法语/德语/意大利语/西班牙语
    public static final String[] AUDIOTRACKORDER_LEADING = { AudioTrack.Mandarin, AudioTrack.Cantonese,
            AudioTrack.English, AudioTrack.Korean, AudioTrack.Japanese, AudioTrack.Russian, AudioTrack.French,
            AudioTrack.German, AudioTrack.Italian, AudioTrack.Spanish };

    public interface PlayType {
        public static final int PLAY = 1;// 有版权播放平台：web
        public static final int OPEN_VIP = 6;// 有版权播放平台：tv
        public static final int USE_TICKET = 7;// 无版权播放平台
        public static final int VIP_TV = 8;
        public static final int ADDON_SUBSCRIBE = 9;
        public static final int LOGIN = 10;
    }

    public static final Integer[] LECOM_PLAYTYPE = { PlayType.PLAY, PlayType.ADDON_SUBSCRIBE };

    // 内容分级
    public interface ContentRating {
        // G级
        public static final String G = "620011";
        // PG级
        public static final String PG = "620012";
        // PG-13级
        public static final String PG13 = "620013";
        // R级
        public static final String R = "620014";
        // NG-17级
        public static final String NG17 = "620015";
        // NOT RATED级 未定义级别
        public static final String NR = "620062";
        // TV-Y级
        public static final String TVY = "620063";
        // TV-Y7级
        public static final String TVY7 = "620064";
        // TV-Y7-FV级
        public static final String TVY7FV = "620065";
        // TV-G级
        public static final String TVG = "620066";
        // TV-PG级
        public static final String TVPG = "620067";
        // TV-PG-D级
        public static final String TVPGD = "620068";
        // TV-14级
        public static final String TV14 = "620069";
        // TV-MA级
        public static final String TVMAS = "620070";
    }

}
