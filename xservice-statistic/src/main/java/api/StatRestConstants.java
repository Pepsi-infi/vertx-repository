package api;

/**
 * Created by lufei
 * Date : 2017/7/25 11:46
 * Description :
 */
public class StatRestConstants {

    interface Stat {

        static final int HTTP_PORT = 9100;

        static final String SERVICE_NAME = "/mc-statistic";

        static final String SERVICE_ROOT = "/mc-statistic";

        //统计push msg
        static final String PUSH_MSG_STAT = "/mc-statistic/msg/stat.json";

    }

    interface Device {
        static final int HTTP_PORT = 9110;

        //用户设备关系上报接口
        static final String USER_DEVICE_REPORT = "/mc-statistic/device/report.json";
    }


}
