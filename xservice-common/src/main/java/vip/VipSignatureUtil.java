package vip;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import utils.MD5;

public class VipSignatureUtil {
	private static final char HEX_DIGITS[] = "0123456789abcdef".toCharArray();

	public static final String SECRET_KEY = "sk_M73mNGt89IA4h0vt48EV";
	public static final String ACCESS_KEY = "ak_6821f189b68d21095efa1baabca21ac1 ";
	public static final String REQUEST_PATH = "/api/v1/binding/status";
	
	protected static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	protected static String join(Iterable<String> strings, String sep) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String item : strings) {
			if (first)
				first = false;
			else
				sb.append(sep);
			sb.append(item);
		}
		return sb.toString();
	}

	public static String sign(String key, String method, String path, byte[] body, Long time,
			Map<String, String[]> params) {
		try {
			String bodyMD5 = "";
			if (body != null && body.length != 0) {
				MessageDigest digest;
				digest = MessageDigest.getInstance("MD5");
				digest.update(body);
				bodyMD5 = toHexString(digest.digest());
			}
			String paramString = "";

			if (params != null) {
				SortedSet<String> set = new TreeSet<String>();
				for (String param : params.keySet()) {
					String[] values = params.get(param);
					for (String value : values) {
						set.add(param + "=" + value);
					}
				}
				paramString = join(set, "&");
			}
			
			SimpleDateFormat fm = new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss 'CST'",Locale.ENGLISH);
            String date = fm.format(new Date(time));
			String stringToSign = method + "\n" + path + "\n" + bodyMD5 + "\n" + date + "\n" + paramString;
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(stringToSign.getBytes());
			return toHexString(rawHmac);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static final String BOSS_NEW_KEY = "7hq34031sa64950s37085adadfds343";
	public static String getVipSignature(Map<String, String> map){
	    StringBuffer buff = new StringBuffer();
	    if(map != null && map.keySet()!= null) {
	        for (String k : map.keySet()) {
                buff.append(k).append("=").append(map.get(k)).append("&");
            }
	        buff.append("key").append("=").append(BOSS_NEW_KEY);
	    }
	    return MD5.Md5(buff.toString());
	}
	
}
