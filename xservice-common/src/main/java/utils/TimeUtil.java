package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


public class TimeUtil {

    /**
     * Long 型 timestamp 时间转成 yyyy-MM-dd HH:mm:ss 格式
     * @param timestamp
     * @return
     */
    public static String timestamp2date(Long timestamp) {

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timestamp ts = new Timestamp(timestamp);

        return sd.format(ts);
    }

    public static Long string2timestamp(String dateStr) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        if(StringUtils.isNotBlank(dateStr)){
            try {
                ts = Timestamp.valueOf(dateStr);
            } catch (Exception e) {
                ts = new Timestamp(Long.valueOf(dateStr));
            }
        }

        return ts.getTime();
    }

    public static Date string2date(String dateStr) {
        Date date = new Date();

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static double daysUp2Now(String dateStr) {

        Long now = System.currentTimeMillis();

        Long ts = TimeUtil.string2timestamp(dateStr);

        double days = (now - ts) / 1000 / 60 / 60 / 24;

        return days;
    }

    public static String seconds2Mmss(Integer seconds) {
        int ssi = seconds % 60;
        int mmi = seconds / 60;

        String ss = String.valueOf(ssi);
        if (ss.length() == 1) {
            ss = "0" + ss;
        }

        String mm = String.valueOf(mmi);
        if (mm.length() == 1) {
            mm = "0" + mm;
        }
        return mm + ":" + ss;
    }

    public static String seconds2HHmmss(Integer seconds) {
        int ssi = seconds % 60;
        int hhi = seconds / 60 / 60;
        int mmi = (seconds - hhi * 60 * 60) / 60;

        String ss = String.valueOf(ssi);
        if (1 == ss.length()) {
            ss = "0" + ss;
        }

        String mm = String.valueOf(mmi);
        if (1 == mm.length()) {
            mm = "0" + mm;
        }

        String hh = String.valueOf(hhi);
        if (1 == hh.length()) {
            hh = "0" + hh;
        }

        return hh + ":" + mm + ":" + ss;
    }

    public static String seconds2HHmmWithoutZero(Integer seconds) {
        String result = "";
        int hhi = seconds / 60 / 60;
        int mmi = (seconds - hhi * 60 * 60) / 60;

        String mm = String.valueOf(mmi);

        String hh = String.valueOf(hhi);
        result = hh + ":" + mm;

        return result;
    }

    /**
     * 将yyyy | yyyy-MM | yyyy-MM-dd 格式的String转成long型
     * @param date
     * @return
     */
    public static Long date2Timestamp(String date) {
        Long timestamp = 0l;

        if (StringUtils.isNotEmpty(date)) {
            try {
                if (date.matches("\\d{4}")) {
                    DateFormat sdf = new SimpleDateFormat("yyyy");
                    timestamp = sdf.parse(date).getTime();
                } else if (date.matches("\\d{4}-\\d{2}")) {
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    timestamp = sdf.parse(date).getTime();
                } else if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    timestamp = sdf.parse(date).getTime();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return timestamp;
    }

    /**
     * 将 HH:mm:ss时间格式的string转成long型（毫秒）
     */
    public static Long hhmmss2Timestamp(String time) {
        Long timestamp = 0l;

        if (StringUtils.isNotEmpty(time) && time.matches("\\d{2}:\\d{2}:\\d{2}")) {
            String[] timeStr = time.split(":");
            timestamp = Long.valueOf(timeStr[0]) * 60 * 60 * 1000 + Long.valueOf(timeStr[1]) * 60 * 1000
                    + Long.valueOf(timeStr[2]) * 1000;
        }

        return timestamp;
    }

    /**
     * 获取零点时间
     * @param timestamp
     *            毫秒时间戳
     * @param offset
     *            偏移量
     *            例如timestamp = 1420507775463并且offset =
     *            24*60*60*1000l的时候，返回的时间戳是第二天的0点
     * @return
     */
    public static Long getZeroPiont(Long timestamp, Long offset) {
        Calendar cal = Calendar.getInstance();
        timestamp = timestamp + offset;
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static void main(String[] args) throws Exception {
//        boolean contains = ArrayUtils.contains(MessageUtils.LETV_SERVER_TERMINAL, "letv_leading_app");
//        System.out.println("ddd" + contains);
//        System.out.println("--" + TimeUtil.date2Timestamp("2016-08-18"));
//        List<String> lcl = new ArrayList<String>();
//        lcl.add("a");
//        lcl.add("b");
//        lcl.add("c");
//        Predicate<PackageInfo> predicate1 = new Predicate<PackageInfo>() {  
//            @Override  
//            public boolean apply(PackageInfo input) {  
//                return input.getTypeGroup() == VipTpConstant.VipType.ADDON;  
//            }  
//        };  
//        List<String> t = new ArrayList<String>( Collections2.filter(lcl, new Predicate<String>(){
//            @Override
//            public boolean apply(String input) {
//                return input.equals("a");
//            }}));
//        System.out.println("length:" + t.get(0));
//        Long timestamp = (1467016063502l - 1467015763502l)/1000;
//        String dateStr = "2014-06-06 17:07:57";
//        System.out.println(timestamp);
//
//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        String strTime = sd.format(Calendar.getInstance().getTime());
//        System.out.println(strTime);
//        System.out.println(System.currentTimeMillis());
//        System.out.println(TimeUtil.string2timestamp(dateStr));
//        System.out.println(daysUp2Now(dateStr));
//        System.out.println(seconds2Mmss(20202));
//        System.out.println(seconds2Mmss(56));
//        System.out.println(seconds2HHmmss(38));
//        System.out.println(seconds2HHmmWithoutZero(135));
//
//        Date date = new Date();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
//        String time = format.format(date);
//        System.out.println("guawa cache stat passwd:"
//                + MessageDigestUtil.md5((time + "PO6xoEhToVXHU").getBytes("UTF-8")));
    }
}
