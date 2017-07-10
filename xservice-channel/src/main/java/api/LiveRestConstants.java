package api;

public class LiveRestConstants {

    // http://wiki.letv.cn/pages/viewpage.action?pageId=63399087

    interface TV {
        static final int HTTP_PORT = 2203;

        static final String SERVICE_NAME = "/tv-channel";

        static final String SERVICE_ROOT = "/tv-channel";

        /**
         * 简约列表
         */
        static final String PROGRAM_LIST = "/tv-channel/programlist/get.json";

        static final String THEATERPACKED_GET = "/tv-channel/watermark/get.json";

        /**
         * 获得频道当前播放节目
         * 
         * params: channelIds
         */
        static final String CHANNEL_CURRENT_PROGRAM = "/tv-channel/current/program.json";
    }

    interface MOBILE {
        static final int HTTP_PORT = 2103;

        static final String SERVICE_NAME = "/mobile-channel";

        static final String SERVICE_ROOT = "/mobile-channel";

        static final String PROGRAM_LIST = "/mobile-channel/programlist/get.json";

        /**
         * Since: mobile live 5.0
         * Param: anchorid={主播id}&index={分页索引id}
         */
        String ANCHOR_LIST = "/mobile-channel/anchor/online.json";
    }
}
