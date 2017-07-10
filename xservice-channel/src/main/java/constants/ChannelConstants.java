package constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChannelConstants {
    // 终端应用和对应的端的map
    public static final Map<String, List<String>> CMS_SHORTCUT_BLOCK_MAP = new HashMap<>();
    public static final Map<String, String> REC_HOME_PAGE_MAP = new HashMap<>();
    public static final List<String> LE_COM_SHORTCUT = new ArrayList<>();
    public static final List<String> LE_AUTO_SHORTCUT = new ArrayList<>();
    public static final List<String> LE_TV_SHORTCUT = new ArrayList<>();
    public static final String DATA_TYPE_CHANNEL = "1";
    public static final String DATA_TYPE_ADDON = "2";
    public static final int LE_HTTPS_HOST = 0;
    public static final int ADDON_PAGE_PATH = 0;
    public static final int SEARCH_DATA_PATH = 0;
    public static final int CHANNEL_DATA_PATH = 0;
    public static final String CMS_TYPE_ADDON_PAGE = null;
    
    public static final String TV_PAY_CODE = "141007";

    static {
        LE_COM_SHORTCUT.add("7687");
        LE_COM_SHORTCUT.add("7683");
        LE_AUTO_SHORTCUT.add("8453");
        LE_TV_SHORTCUT.add("8690");

        CMS_SHORTCUT_BLOCK_MAP.put("lecom", LE_COM_SHORTCUT);
        CMS_SHORTCUT_BLOCK_MAP.put("le_auto_letv", LE_AUTO_SHORTCUT);
        CMS_SHORTCUT_BLOCK_MAP.put("le", LE_TV_SHORTCUT);

        REC_HOME_PAGE_MAP.put("lecom", "1003448423");
        REC_HOME_PAGE_MAP.put("le_auto_letv", "1003568475");
        REC_HOME_PAGE_MAP.put("le", "1003448011");
    }

    /**
     * 频道页板块数据类型
     */
    public static class DataType {
        public final static String DATA_TYPE_ALBUM = "1"; // 数据类型定义--专辑
        public final static String DATA_TYPE_VIDEO = "2"; // 数据类型定义--视频
        public static final String ADDON_PAGE = "3";
        public final static int DATA_TYPE_CHANNEL = 6;// 频道
        public final static int DATA_TYPE_MULTILIST_RECOMMENDATION = 12;// 定制页面
        public final static int DATA_TYPE_PAGE = 15;// 定制页面
    }

    /**
     * 非板块数据跳转类型
     */
    public static class SkipType {
        public static final Integer CHANNEL = 1;// CMS专辑
        public static final Integer SEARCH_PAGE = 2;
        public static final Integer ADDON_PAGE = 3;
        public static final Integer NAVIGATION_PAGE = null;
        public static final Integer SKIP_TYPE_H5_PAGE = null;
        public static final Integer SKIP_TYPE_SEARCH_PAGE = null;
        public static final Integer SKIP_TYPE_NAVIGATION_PAGE = null;

        public static Set<Integer> SKIP_LIST_ALL = new HashSet<>();
        static {
            SKIP_LIST_ALL.add(ADDON_PAGE);
        }
    }

    // 数据来源
    public interface DataSource {
        public static final int DATA_SOURCE_CMS = 1; // 数据来源--CMS
        public static final int DATA_SOURCE_REC = 2; // 数据来源--推荐
        public static final int DATA_SOURCE_SEARCH = 4; // 数据来源--搜索
    }

    public interface CornerLabel {
        String IS_PAY = "2";// 付费
    }

    public interface AddOn {
        /**
         * le会员类型
         */
        Integer VIP_ECOPASS_TYPE_TEST = 200;
        Integer VIP_ECOPASS_TYPE_ONLINE = 206;
        Integer NOT_VIP_TYPE = -1;

        /**
         * le会员价格文案
         */
        String DISCOUNT_PRICE_NAME_LE_NO = "List Price $PRICE/month";
        String DISCOUNT_PRICE_NAME_LE_PASS = "EcoPass Price $PRICE/month";
    }

    public static Map<Integer, String> DISCOUNT_PRICE_NAME_MAP = new HashMap<>();
    public static Map<Integer, Integer> DISCOUNT_PRICE_TYPE_MAP = new HashMap<>();
    static {
        DISCOUNT_PRICE_NAME_MAP.put(AddOn.NOT_VIP_TYPE, AddOn.DISCOUNT_PRICE_NAME_LE_NO);
        DISCOUNT_PRICE_NAME_MAP.put(AddOn.VIP_ECOPASS_TYPE_TEST, AddOn.DISCOUNT_PRICE_NAME_LE_PASS);
        DISCOUNT_PRICE_TYPE_MAP.put(AddOn.VIP_ECOPASS_TYPE_ONLINE, AddOn.VIP_ECOPASS_TYPE_TEST);
    }
}
