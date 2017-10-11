package util;


import java.security.MessageDigest;
import java.util.Base64;

import utils.MD5;

public class Md5Util {
	
	private static final String SALT = "HXWcjvQWVG1wI4FQBLZpQ3pWj48AV63d";
    /***
     * MD5加密 生成32位md5码
     * 
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    /**
     * MD5加盐
     * @param inbuf
     * @return
     * @throws Exception 
     */
    public static String encodeByMd5AndSalt(String inbuf) throws Exception {
        return md5Encode(md5Encode(inbuf) + SALT);
    }
    
    /**
     * MD5加盐
     * @param inbuf
     * @return
     * @throws Exception 
     */
    public static String encodeByMd5AndDynamicSalt(String inbuf,String salt) throws Exception {
        return md5Encode(md5Encode(inbuf) + salt);
    }

	public static void main(String[] args) {
		try {
			System.out.println(md5Encode("hello1234"));
			System.out.println(MD5.Md5("hello1234"));
			String encodeStr=Base64.getEncoder().encodeToString("hello1234".getBytes());
			String originStr=new String(Base64.getDecoder().decode(encodeStr));
			System.out.println(encodeStr);
			System.out.println(originStr);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
