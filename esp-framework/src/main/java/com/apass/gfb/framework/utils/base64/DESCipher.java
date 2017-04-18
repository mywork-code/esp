package com.apass.gfb.framework.utils.base64;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by jingming.zhang on 2015/11/2.
 */
public class DESCipher {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DESCipher.class);
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static final String UTF_8 = "utf-8";

    public static String encode(String key, String data) {
        try {
            while (key.length() < 8) {
                key += "0";
            }

            DESKeySpec dks = new DESKeySpec(key.getBytes(UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec ips = new IvParameterSpec("12345678".getBytes(UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);
            byte[] bytes = cipher.doFinal(data.getBytes(UTF_8));
            return new String(Base64.encode(bytes));
        } catch (Exception e) {
        	LOGGER.error("出错",e);
            return "";
        }
    }

    public static String decode(String key, String data) {
        try {
            while (key.length() < 8) {
                key += "0";
            }

            DESKeySpec dks = new DESKeySpec(key.getBytes(UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec ips = new IvParameterSpec("12345678".getBytes(UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ips);
            return new String(cipher.doFinal(Base64.decode(data)));
        } catch (Exception e) {
        	LOGGER.error("出错",e);
            return "";
        }
    }
    
    
    
}
