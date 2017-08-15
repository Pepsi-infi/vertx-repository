package api;

/**
 * Created by lufei
 * Date : 2017/7/25 11:46
 * Description :
 */
public class StatRestConstants {

    interface Stat {

        static final int HTTP_PORT = 8081;

        static final String SERVICE_NAME = "/mc-statistic";

        static final String SERVICE_ROOT = "/mc-statistic";

        //pushMsg上报接口
        static final String PUSH_MSG_REPORT = "/mc-statistic/v1/msg/report.json";

        //查询pushMsg统计
        static final String QUERY_PUSH_MSG_STAT = "/mc-statistic/v1/msgStats.json";

    }

    interface Device {
        static final int HTTP_PORT = 8081;

        //用户设备关系上报接口
        static final String DEVICE_REPORT = "/mc-statistic/v1/devices/report.json";
    }


}
