package util;

import java.text.SimpleDateFormat;

/**
 * Created by weim on 2017/7/31.
 */
public class DateUtil {

    private static SimpleDateFormat DATE_TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd");
    /**
     *
     * @return
     */
    public static String getDateTime(long time){
        return DATE_TIME_FORMATER.format(time);
    }

    public static String getDate(long time){
        return DATE_FORMATER.format(time);
    }

}
