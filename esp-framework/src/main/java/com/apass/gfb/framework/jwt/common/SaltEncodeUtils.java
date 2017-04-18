package com.apass.gfb.framework.jwt.common;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;


/**
 * 
 * @description SALT ENCODE 
 *
 * @author listening
 * @version $Id: SaltEncodeUtils.java, v 0.1 2015年11月25日 上午11:40:37 listening Exp $
 */
public class SaltEncodeUtils {
    private static SecureRandom random           = new SecureRandom();
    public static final String  HASH_ALGORITHM   = "SHA-1";
    public static final int     HASH_INTERATIONS = 1024;
    public static final int     SALT_SIZE        = 8;

    /**
     * 对输入字符串进行sha1散列.
     */
    public static String sha1(String inputValue, String salt) {
        byte[] inputBytes = EncodeUtils.getBytes(inputValue);
        byte[] saltBytes = EncodeUtils.decodeHex(salt);
        byte[] digestByte = digest(inputBytes, HASH_ALGORITHM, saltBytes, HASH_INTERATIONS);
        return EncodeUtils.encodeHex(digestByte);
    }

    /**
     * validate
     */
    public static boolean validate(String plainValue, String encodedValue, String salt) {
        String tempEncodedValue = sha1(plainValue, salt);
        return ListeningStringUtils.equalsIgnoreCase(tempEncodedValue, encodedValue);
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            if (salt != null) {
                digest.update(salt);
            }
            byte[] result = digest.digest(input);
            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成随机的Byte[]作为salt.
     */
    public static String generateSalt(int numBytes) {
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return EncodeUtils.encodeHex(bytes);
    }

    /**
     * 生成随机的Byte[]作为salt.
     */
    public static String generateDefaultSalt() {
        return generateSalt(SALT_SIZE);
    }
}
