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
    public static String dateTimeGmt2Local(String srcDate){
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
    public static String dateGmt2Local(String srcDate){
        if(StringUtils.isNotBlank(srcDate)) {
            Date date = null;
            if(srcDate.indexOf("Z") != -1) {
                //传入的是标准时间，所以比北京时间少8个小时， 指定Locale.US
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
                sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
                try {
                    date = sdf.parse(srcDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }else{
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(srcDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            SimpleDateFormat chinaFormat = new SimpleDateFormat("yyyy-MM-dd");
            srcDate = chinaFormat.format(date);
        }
        return srcDate;
    }

    /**
     * 字符串转日期
     * 
     * @param str
     *            时间
     * @param pattern
     *            格式
     * @return
     */
    public static Date strToDate(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 日期格式化
     * 
     * @param date
     *            日期
     * @param pattern
     *            格式
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);
    }


    public static void main(String[] args) {

        System.out.println(dateGmt2Local("2017-08-28T22:03:07.162Z"));
        System.out.println(dateTimeGmt2Local("2017-08-28T22:03:07.162Z"));
        System.out.println(dateGmt2Local("2017-08-28 22:03:07"));
    }
}
