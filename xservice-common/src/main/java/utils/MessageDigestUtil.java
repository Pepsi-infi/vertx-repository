/*
 * Pprun's Public Domain.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Ideally, we can use commons-codec for the following task, but since glassfish
 * has its own repacked 1.2 commons-codec, but I want the SHA512 in
 * commons-codec-1.4.jar and I did not found the way to workaround.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class MessageDigestUtil {

    private static final String UTF8 = "UTF-8";

    /**
     * MD5 hash function to be "cryptographically secure", the result is 32 HEX
     * characters.
     * <ol>
     * <li>Given a hash, it is computationally infeasible to find an input that
     * produces that hash</li>
     * <li>Given an input, it is computationally infeasible to find another
     * input that produces the same hash</li>
     * </ol>
     * @param plainText
     * @return the MD5 of the input text
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(byte[] plainText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String md5 = new BigInteger(1, messageDigest.digest(plainText)).toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }

        messageDigest.reset();

        return md5;
    }

    /**
     * MD5 hash function to be "cryptographically secure", the result is 32 HEX
     * characters.
     * <ol>
     * <li>Given a hash, it is computationally infeasible to find an input that
     * produces that hash</li>
     * <li>Given an input, it is computationally infeasible to find another
     * input that produces the same hash</li>
     * </ol>
     * @param plainText
     * @param salt
     * @return the MD5 of the input text
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(byte[] plainText, byte[] salt)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(salt);
        String md5 = new BigInteger(1, messageDigest.digest(plainText)).toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }

        messageDigest.reset();

        return md5;
    }

}
