package com.apass.gfb.framework.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import net.sf.json.JSON;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 * 
 * @description RSA Utils
 *              <p>
 *              1. generate keystore with password "wodliwu" keytool -genkey -v
 *              -alias listening -keyalg RSA -keysize 1024 -validity 365
 *              -keystore listening.jks -keypass wodliwu -storepass wodliwu
 *              -dname "CN=CN,OU=CN,O=CN,L=CN,ST=CN,C=zh_CN"
 *
 *              2. keytool -list -rfc -keystore mykeystore.jks
 *              </p>
 * @author listening
 * @version $Id: RSAUtils.java, v 0.1 2015年11月8日 下午4:54:10 listening Exp $
 */
public class RSAUtils {
	/**
	 * 加密块大小 - 如果内容大于117字节需要分段加密
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
	/**
	 * KeyFactory algorithm
	 */
	private static final String KEY_ALGORTHM = "RSA";
	/**
	 * Signature algorithm
	 */
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	/**
	 * 公钥
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";
	/**
	 * 私钥
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 生成公钥和私钥
	 */
	private static HashMap<String, Object> getKeys()
			throws NoSuchAlgorithmException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);
		return map;
	}

	/**
	 * 取得公钥，并转化为String类型
	 * 
	 * @param keyMap
	 * @return String
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return EncodeUtils.encodeBase64(key.getEncoded());
	}

	/**
	 * 取得私钥，并转化为String类型
	 * 
	 * @param keyMap
	 * @return String
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return EncodeUtils.encodeBase64(key.getEncoded());
	}

	/**
	 * 用私钥加密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String value, String key)
			throws Exception {
		byte[] data = EncodeUtils.getBytes(value);
		// 解密密钥
		byte[] keyBytes = EncodeUtils.decodeBase64(key);
		// 取私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return EncodeUtils.encodeBase64(doFinalBySegment(cipher, data, true));
	}

	/**
	 * 用私钥解密
	 * 
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String value, String key)
			throws Exception {
		byte[] data = EncodeUtils.decodeBase64(value);
		// 对私钥解密
		byte[] keyBytes = EncodeUtils.decodeBase64(key);
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return EncodeUtils.getString(doFinalBySegment(cipher, data, false));
	}

	/**
	 * 用公钥加密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return byte[]
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String value, String key)
			throws Exception {
		byte[] data = EncodeUtils.getBytes(value);
		// 对公钥解密
		byte[] keyBytes = EncodeUtils.decodeBase64(key);
		// 取公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return EncodeUtils.encodeBase64(doFinalBySegment(cipher, data, true));
	}

	/**
	 * 用公钥解密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return byte[]
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String value, String key)
			throws Exception {
		byte[] data = EncodeUtils.decodeBase64(value);
		// 对私钥解密
		byte[] keyBytes = EncodeUtils.decodeBase64(key);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return EncodeUtils.getString(doFinalBySegment(cipher, data, false));
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            //加密数据
	 * @param privateKey
	 *            //私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密私钥
		byte[] keyBytes = EncodeUtils.decodeBase64(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				keyBytes);
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		// 取私钥匙对象
		PrivateKey privateKey2 = keyFactory
				.generatePrivate(pkcs8EncodedKeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateKey2);
		signature.update(data);
		return EncodeUtils.encodeBase64(signature.sign());
	}

	/**
	 * doFinal By Segment since the max block size is limited
	 * 
	 * @param cipher
	 * @param source
	 * @param isEncode
	 * @return
	 * @throws Exception
	 */
	private static byte[] doFinalBySegment(Cipher cipher, byte[] source,
			boolean isEncode) throws Exception {
		ByteArrayOutputStream out = null;
		try {
			int blockSize = isEncode ? MAX_ENCRYPT_BLOCK : MAX_DECRYPT_BLOCK;
			if (source.length <= blockSize) {
				return cipher.doFinal(source);
			}
			out = new ByteArrayOutputStream();
			int offsetIndex = 0, offset = 0, sourceLength = source.length;
			byte[] buffer = null;
			while (sourceLength - offset > 0) {
				if (sourceLength - offset > blockSize) {
					buffer = cipher.doFinal(source, offset, blockSize);
				} else {
					buffer = cipher.doFinal(source, offset, sourceLength
							- offset);
				}
				out.write(buffer, 0, buffer.length);
				offsetIndex++;
				offset = offsetIndex * blockSize;
			}
			return out.toByteArray();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Test RSA
	 */
	public void testRSA() throws Exception {
		Map<String, Object> keyMap = getKeys();
		String privateKey = getPrivateKey(keyMap);
		String publicKey = getPublicKey(keyMap);
		System.err.println("private:" + privateKey);
		System.err.println("public:" + publicKey);
		String data = "woldiwu";

		Map<String,Object> params = new HashMap<>();
		Map<String,Object> paramJson = new HashMap<>();
		paramJson.put("sku","107164,179638");
		params.put("method","biz.product.state.query");
		params.put("jdParams", JSONObject.valueToString(paramJson));

		String content = encryptByPrivateKey(data, privateKey);
		System.err.println("private encode ->" + content);

		String publicDecryptContent = decryptByPublicKey(content, publicKey);
		System.err.println("public decode->" + publicDecryptContent);
	}

	public void testRsa1() throws Exception {
		Map<String,Object> params = new HashMap<>();
		Map<String,Object> paramJson = new HashMap<>();
		paramJson.put("sku","107164,179638");
		params.put("method","biz.product.state.query");
		params.put("jdParams", JSONObject.valueToString(paramJson));
		String content = 	encryptByPublicKey(JSONObject.valueToString(params),"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLJmVDX0IQBxA99/7F0HlLzSsskIJ9S6a0TYgxNaAaM6Kpoipe24ozwPGUi6PmMZvdaLeDE8bkqkBTf6BienaBFGBxH1xXgVmiy/MEb9gBelbt9H6j6UQ/b6rr5xn1RLdQjAf0HCuGswUzbMzOwzeVmfwuxq6zlagMPC4abtVidQIDAQAB");
		System.err.println("public encode ->" + content);

		String publicDecryptContent = decryptByPrivateKey(content, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIsmZUNfQhAHED33/sXQeUvNKyyQgn1LprRNiDE1oBozoqmiKl7bijPA8ZSLo+Yxm91ot4MTxuSqQFN/oGJ6doEUYHEfXFeBWaLL8wRv2AF6Vu30fqPpRD9vquvnGfVEt1CMB/QcK4azBTNszM7DN5WZ/C7GrrOVqAw8Lhpu1WJ1AgMBAAECgYBHlsBgUgDV6BxmFVGhCeltoyeELPDFb4dbmcvorVMqR8h3B3RPCxnmGGzxZIo1Iu6ykW5gZGteN20SyEWGhMS0Tr8PPtkIV8sJxknUh5DGpqrGXwkAFx8IdJYcWf3t2nnFJpeqhfdq/maVBDLl2IOBJ9CxNqHLEbBusKush/XAfQJBANFA+oN6Tc4+GVqje1No5E2ZfleEx8RhvG6VmWpaGkD5psMlqFa3clpKK9FfTsCiFGYkMUUumbLxLOe9B2t/bdcCQQCqPD84trprP4tYi+23vRDNlB3hVFcRR54950QTZGs9oTlhhvS9cZTxqgTkAZIdhwsU9UhGgXpHF6j7LiLkVzCTAkA8DwRX3DCcs2Ifi7CH3iE4zIdgozGbAdpNew6PYdELORxOAm7whLKDi6pL66j0zRBByL2Dd76Rh3s2ACEzD139AkBI8xBUiVlRZCWeiDgSKJ7uua4kRTQaX4pcFKKDfODHxDMGrAJsvCOLe8QkwNeQd7I0BPg33nw7wtPS725cU1CpAkEAq3N/I3BrZ9brCyP/Q7ZyoGmU4T/lWQNK3BS3+WYQocRRNVeyMkAwuEj7btjHTQMgIwfdZmVU6UC5NUhM9jKyFw==");
		System.err.println("private decode->" + publicDecryptContent);
	}

	public static void main(String[] args) throws Exception {
		RSAUtils rsaUtils = new RSAUtils();
		rsaUtils.testRsa1();
	}
}
