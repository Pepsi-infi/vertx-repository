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
        static final String QUERY_IM_COMMON_LANGUAGE = "/mc-config/v1/commonLanguages.json";
        //im常用语接口 详情
        static final String GET_IM_COMMON_LANGUAGE = "/mc-config/v1/commonLanguages/get.json";
        //im常用语接口 删除
        static final String DELETE_IM_COMMON_LANGUAGE = "/mc-config/v1/commonLanguages/del.json";

        //敏感词接口
        static final String QUERY_SENSITIVE_WORD = "/mc-config/v1/sensitiveWords.json";
        //敏感词接口 详情
        static final String GET_SENSITIVE_WORD = "/mc-config/v1/sensitiveWords/get.json";
        //敏感词接口 删除
        static final String DELETE_SENSITIVE_WORD = "/mc-config/v1/sensitiveWords/del.json";


    }


}
