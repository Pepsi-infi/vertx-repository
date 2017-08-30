package util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

    /**
     * 标准时间转成北京时间
     * @param srcDate
     * @return
     */
    public static String getLocalDate(String srcDate){
        if(StringUtils.isNotBlank(srcDate) && srcDate.indexOf("Z") != -1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.CHINA);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
            Date date = null;
            try {
                date = sdf.parse(srcDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(date);
            //页面获得的是标准时间，这里转换需要
            LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC+08:00"));
            return dateTime.toString();
        }
        return srcDate;
    }

    /**
     * 标准时间转成北京时间
     * @param srcDate
     * @return
     */
    public static Long getTimeMillis(String srcDate){
        Date date = null;
        if(StringUtils.isNotBlank(srcDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                date = sdf.parse(srcDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date == null ? null : date.getTime();
    }

//    public static String _getLocalDate(String srcDate){
//        ZonedDateTime zonedDateTime = ZonedDateTime.parse(srcDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
//        ZonedDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC+08:00"));
//        String localDateTime = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
//        return localDateTime.toString();
//    }

    public static void main(String[] args) {
        System.out.println(getLocalDate("2017-08-28T06:03:07.162Z"));
    }
}
