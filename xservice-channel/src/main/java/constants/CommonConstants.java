package constants;

/**
 * Created by zhushenghao1 on 17/2/9.
 */
public class CommonConstants {
    
    public static class Platform{
        public static final String MOBILE = "141003";
        public static final String TV_PLATFROM_CODE = "420007";
        public static final String TV_PAY_CODE = "141007";
        public static final String TV_PLAY_PLAT_FROM = "420007";// ApplicationUtils.get(ApplicationConstants.IPTV_TV_COPYRIGHT_PARAM);
    }

    public static class TerminalAppType {
        public static final String TERMINAL_APP_LECOM = "lecom"; // le_mobile
        public static final String TERMINAL_APP_LE_AUTO_LEVIEW = "le_auto_onlineent";// 车载Le桌面,在线娱乐桌面  // le_auto
        public static final String TERMINAL_APP_LE_AUTO_LETV = "le_auto_letv";// 车载版乐视视频 
        public static final String TERMINAL_APP_LE_TV = "le"; // le_tv
    }
    
    public static class ErrorCode {
        public static final int RESPONSE_SUC_CODE = 1;
        public static final int RESPONSE_FAIL_CODE = 0;
        public final static String ERROR_CODE_CHANNEL_PARAM_ERROR = "SSC0002"; // 参数不合法
        public final static String ERROR_CODE_HOMEPAGE_DATA_ERROR = "RETRY001"; // 频道无数据时的错误码
    }
    
}
