/*
 * Pprun's Public Domain.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Constant used in the utility method.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class CommonUtil {
    protected static final Logger requestLogger = LoggerFactory.getLogger("requestTimeLog");
    public static ThreadLocal<List<String>> logList = new ThreadLocal<List<String>>();
    public static final String UTF8 = "UTF-8";
    public static final String GB18030 = "GB18030";
    public static final String GBK = "gbk";
    public static final String TIME_ZONE_UTC = "UTC";
    public static final String downloadkey = "itv12345678!@#$%^&*";
    public static final String LEMOB_PLATFROM = "lemob_video";
    public static final String MOBILE_PLATFROM = "420003";
    public static final String LEADING_PLATFROM = "420020";
    public static final String[] PHONE_PLATFROMS = {"420003", "420020"};
    public static final String MOBILE_WEB_TV_PLATFROM = "420001,420003,420007,420020";
    public static final String[] PLATFORMS = { "420001", "420003", "420007" , "420020"};

    /**
     * This is quick way to round a {@link BigDecimal#setScale(2,
     * java.math.RoundingMode.ROUND_HALF_UP)}.
     * <p>
     * No roundingMode parameter was provided because, I trust, if you know how
     * to specify the {@literal roundingMode}, you might not need this utility
     * method.
     * </p>
     * @param value
     *            the value to round.
     * @return the rouned value by setting the scale to {@literal 2} and
     *         {@literal RuondingMode} to {@link BigDecimal#ROUND_HALF_UP}.
     */
    public static BigDecimal round(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Make an empty {@code ""} if the given String is {@code null}.
     * @param src
     * @return
     */
    public static String nullToEmpty(String src) {
        return src == null ? "" : src;
    }

    public static String join(Collection<String> collectionOfStrings, String delimeter) {
        StringBuilder result = new StringBuilder();
        for (String s : collectionOfStrings) {
            result.append(s);
            result.append(delimeter);
        }
        return result.substring(0, result.length() - 1);
    }

    public static Boolean checkSig(Map<String, Object> params) {
        String sig = (String) params.get("sig");
        params.remove("sig");
        String md5sig = getMd5Str(params, downloadkey);
        if (!md5sig.equalsIgnoreCase(sig)) {
            return false;
        }

        // Long timestamp = (Long) params.get("timestamp");
        // Long currentTimeStamp = System.currentTimeMillis();
        // if (Math.abs(currentTimeStamp - timestamp) > 1000 * 60 * 60) {
        // return false;
        // }
        return true;
    }

    /**
     * 获得md5值
     * @param params
     * @return
     */
    public static String getMd5Str(Map<String, Object> params, String secrectKey) {
        List<String> listStr = new ArrayList<String>();
        String md5 = "";

        String key_value = "";
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = params.get(key) + "";
            listStr.add(key + "=" + value);
        }
        Collections.sort(listStr);
        for (String str : listStr) {
            key_value += str + "&";
        }
        String md5str = key_value.substring(0, key_value.length() - 1) + secrectKey;
        try {
            md5 = MessageDigestUtil.md5(md5str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static void printLog(Long totalTime, List<String> logList) {
        try {
            StringBuffer sb = new StringBuffer();
            if (totalTime > 200 && logList != null) {
                sb = new StringBuffer();
                for (String log : logList) {
                    sb.append(log);
                }
                requestLogger.info(Thread.currentThread().getName() + "   total:" + totalTime + sb.toString());
            }

        } catch (Exception e) {
            requestLogger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        // Map<String,Object> params = new HashMap<String, Object>();
        // params.put("vrsVideoInfoId", "454685");
        // params.put("timestamp", "1367489032051");
        // params.put("stream", "1300");
        try {
            System.out.println(MessageDigestUtil.md5("timestamp=1421597448951&videoid=20004716itv12345678!@#$%^&*"
                    .getBytes("UTF-8")));

            String time = "2014-05-13 18:30:52";

            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = null;
            try {
                d = (Date) f.parseObject(time);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Timestamp ts = new Timestamp(d.getTime());
            System.out.println(d.getTime());

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
