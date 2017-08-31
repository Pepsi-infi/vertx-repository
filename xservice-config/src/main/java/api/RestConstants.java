package api;

/**
 * Created by lufei
 * Date : 2017/7/25 11:46
 * Description :
 */
public class RestConstants {

    interface Config {


        static final String SERVICE_NAME = "/mc-config";

        static final String SERVICE_ROOT = "/mc-config";

        //im常用语接口
        static final String QUERY_IM_COMMON_LANGUAGE = "/mc-config/im/commonLanguage.json";

        static final String GET_IM_COMMON_LANGUAGE = "/mc-config/im/commonLanguage/get.json";

        static final String DELETE_IM_COMMON_LANGUAGE = "/mc-config/im/commonLanguage/del.json";


    }


}
