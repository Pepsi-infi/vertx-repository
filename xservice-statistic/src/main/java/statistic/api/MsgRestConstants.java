package statistic.api;

/**
 * Created by lufei
 * Date : 2017/7/25 11:46
 * Description :
 */
public class MsgRestConstants {

    interface Msg {

        static final int HTTP_PORT = 18888;

        static final String SERVICE_NAME = "/mc-statistic";

        static final String SERVICE_ROOT = "/mc-statistic";

        //统计发送
        static final String MSG_SEND = "/mc-statistic/msg/statSend.json";

        //统计到达
        static final String MSG_ARRIVE = "/mc-statistic/msg/statArrive.json";


    }
}
