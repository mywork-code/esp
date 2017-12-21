package com.aisino;


import com.apass.esp.common.utils.OSUtils;
import com.apass.esp.invoice.CaConstant;

public class PKCS7 {

	// 加载动态库
	static {
		if(OSUtils.WINDOWS){
			System.load(CaConstant.getCaFilePath("WINDOWS_DLLADDRESS2"));
		}else{
			System.load(CaConstant.getCaFilePath("DLLADDRESS2"));
		}
		
	}

	// 设置信任链
	public static native boolean setTrusts(String trusts);

	// 设置解密证书
	public static native boolean setDecryptPfx(byte[] decPfx, String passwd);

	// 设置签名证书
	public static native boolean setSignedPfx(byte[] sigPfx, String passwd);

	// 验证证书，成功返回1
	public static native int validateCert(String base64Cert);

	// 打包数字信封，传递加密证书(即接收者的证书)
	public synchronized static native byte[] signedAndEnveloped(String encBase64Cert, byte[] inData);

	// 解包数字信封
	public synchronized static native PKCS7 unpack(byte[] inData);

	// 获取错误码
	public static native int getLastError();

	/**
	 * 打包数字信封-新增支持多线程并发
	 *
	 * @param trusts
	 *            信任链
	 * @param encBase64Cert
	 *            加密证书
	 * @param sigPfx
	 *            pfx格式签名证书
	 * @param pfxpasswd
	 *            pfx格式签名证书的保护口令
	 * @param inData
	 *            原文
	 * @return pkcs7格式的密文数据
	 */
	public static native byte[] signedAndEnvelopedMulti(String trusts, String encBase64Cert, byte[] sigPfx,
			String pfxpasswd, byte[] inData);

	/**
	 * 打包数字信封-解包数字信封-新增支持多线程并发
	 *
	 * @param trusts
	 *            信任链
	 * @param decPfx
	 *            pfx格式解密证书
	 * @param pfxpasswd
	 *            pfx格式解密证书
	 * @param inData
	 *            密文
	 * @return 类对象
	 */
	public static native PKCS7 unpackMulti(String trusts, byte[] decPfx, String pfxpasswd, byte[] inData);

	public String sigCert; // 签名证书
	public String serial; // 证书序列号
	public String subject; // 证书主题
	public byte[] data; // 原文

	public PKCS7() {
	}

	/**
	 * @param trustsBytes
	 *            证书信任链
	 * @param privatePFXBytes
	 *            加密/签名私钥
	 * @param privatePFXKey
	 *            私钥密码
	 * @throws Exception
	 */
	public PKCS7(byte[] trustsBytes, byte[] privatePFXBytes, String privatePFXKey) throws Exception {
		if (!setTrusts(new String(trustsBytes))) {
			throw new Exception("" + getLastError());
		}

		if (!setDecryptPfx(privatePFXBytes, privatePFXKey)) {
			throw new Exception("" + getLastError());
		}

		if (!setSignedPfx(privatePFXBytes, privatePFXKey)) {
			throw new Exception("" + getLastError());
		}
	}

	/**
	 * 签名加密
	 * 
	 * @param plainContent
	 *            预加密的原文
	 * @param publicPFXBytes
	 *            公钥加/解密证书的绝对路径
	 * @return 加密后的密文数据
	 */
	public static byte[] pkcs7Encrypt(String plainContent, byte[] publicPFXBytes) {
		try {
			final byte[] certBytes = publicPFXBytes;

			if (certBytes == null) {
				throw new Exception("传入参数公钥为NULL,不可用");
			}

			final String encCert = new String(certBytes);
			if (1 != validateCert(encCert)) {// 证书无效
				throw new Exception("" + getLastError());
			}
			return signedAndEnveloped(encCert, plainContent.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new byte[0];
	}

	/**
	 * 解密验签
	 * 
	 * @param decodeBase64EncryptTxtBytes
	 *            经过Base64解压后的密文字节流
	 * @return byte[] 经过解密的明文字节流
	 */
	public static byte[] pkcs7Decrypt(byte[] decodeBase64EncryptTxtBytes) {
		byte[] decryptBytes = new byte[0];
		try {
			// 解密
			if (decodeBase64EncryptTxtBytes == null) {
				throw new Exception("传入参数密文为NULL,不可用");
			}

			final PKCS7 pkcs7 = unpack(decodeBase64EncryptTxtBytes);

			if (pkcs7 == null) {
				throw new Exception("" + getLastError());
			}

			decryptBytes = pkcs7.data;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return decryptBytes;
	}

	
}
