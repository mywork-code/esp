package com.aisino;

import com.apass.esp.invoice.CaConstant;
import com.google.common.io.Closer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;

public final class PKCS7 {
    private final static Logger LOGGER = LoggerFactory.getLogger(PKCS7.class);

    //设置信任链
    public static native boolean setTrusts(String trusts);

    //设置解密证书
    public static native boolean setDecryptPfx(byte[] decPfx, String passwd);

    //设置签名证书
    public static native boolean setSignedPfx(byte[] sigPfx, String passwd);

    //验证证书，成功返回1
    public static native int validateCert(String base64Cert);

    //打包数字信封，传递加密证书(即接收者的证书)
    public synchronized static native byte[] signedAndEnveloped(String encBase64Cert, byte[] inData);

    //解包数字信封
    public synchronized static native PKCS7 unpack(byte[] inData);

    //获取错误码
    public static native int getLastError();

    /**
     * sigCert、serial、subject、data以下参数 请不要有任何幻想修改，包括你看不惯的命名！！！！！！！！！！！！！！
     */
    private String sigCert; //签名证书
    private String serial; //证书序列号
    private String subject; //证书主题
    private byte[] data; //原文

    static {
        System.load(CaConstant.getCaFilePath("DLLADDRESS2"));
    }


    public PKCS7() {
    }

    /**
     * @param trustsBytes     证书信任链
     * @param privatePFXBytes 加密/签名私钥
     * @param privatePFXKey   私钥密码
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
     * 依据文件绝对路径, 读取文件
     *
     * @param fileUri 文件绝对路径
     * @return byte[] 读取成功的文件字节流
     */
    private byte[] readFile(String fileUri) {
        final Closer closer = Closer.create();
        try {
            final BufferedInputStream bufferedInputStream = closer.register(new BufferedInputStream(new FileInputStream(fileUri)));
            final byte[] bufferedBytes = new byte[bufferedInputStream.available()];

            bufferedInputStream.read(bufferedBytes, 0, bufferedBytes.length);

            return bufferedBytes;
        } catch (IOException e) {
            LOGGER.error("read file ioException:", e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                LOGGER.error("close file ioException:", e);
            }
        }

        return new byte[0];
    }

    /**
     * 签名加密
     *
     * @param plainContent     预加密的原文
     * @param publicPFXBytes 公钥加/解密证书的绝对路径
     * @return 加密后的密文数据
     */
    public byte[] pkcs7Encrypt(String plainContent, byte[] publicPFXBytes) {
        try {
            final byte[] certBytes = publicPFXBytes;

            if (certBytes == null) {
                throw new Exception("传入参数公钥为NULL,不可用");
            }

            final String encCert = new String(certBytes);
            if (1 != validateCert(encCert)) {//证书无效
                throw new Exception("" + getLastError());
            }

            if (isNullOrEmpty(plainContent)) {
                throw new Exception("传入参数原文为NULL,不可用");
            }

            return signedAndEnveloped(encCert, plainContent.getBytes("UTF-8"));
        } catch (Exception e) {
            LOGGER.error("pkcs7Encrypt Exception:", e);
        }

        return new byte[0];
    }

    /**
     * 解密验签
     *
     * @param decodeBase64EncryptTxtBytes 经过Base64解压后的密文字节流
     * @return byte[] 经过解密的明文字节流
     */
    public byte[] pkcs7Decrypt(byte[] decodeBase64EncryptTxtBytes) {
        byte[] decryptBytes = new byte[0];
        try {
            //解密
            if (decodeBase64EncryptTxtBytes == null) {
                throw new Exception("传入参数密文为NULL,不可用");
            }

            final PKCS7 pkcs7 = unpack(decodeBase64EncryptTxtBytes);

            if (pkcs7 == null) {
                throw new Exception("" + getLastError());
            }

            decryptBytes = pkcs7.data;
        } catch (Exception e) {
            LOGGER.error("pkcs7Decrypt Exception:", e);
        }

        return decryptBytes;
    }


    /**
     客户端事件需要  客户端私钥(pfx)、pwd + 平台公钥(cer)
     平台端事件需要  平台端私钥(pfx)、pwd + 客户端公钥(cer)
     */
    public static void main(String[] args) throws Exception {
        //加密使用的对方加密公钥（客户端调用传入服务端公钥，服务端调用传入客户端公钥）
        //final String source = "姆神要吃巧克力";

    	
        final String source="<REQUEST_COMMON_DOWNLOAD_CA class=\"REQUEST_COMMON_DOWNLOAD_CA\">\n" +
                "  <DSPTBM>12110302</DSPTBM>\n" +
                "  <DSPTZCM>51807674</DSPTZCM>\n" +
                "  <NSRSBH>510107698868804</NSRSBH>\n" +
                "  <NSRSQM>1234567890Ko1ztjNt4xGg76Tqxl4rvg==</NSRSQM>\n" +
                "</REQUEST_COMMON_DOWNLOAD_CA>";


        LOGGER.info("原文:{}",source);

        System.out.println(System.getProperty("java.library.path"));
        final String trustsBytes = CaConstant.getCaFilePath("PUBLIC_TRUSTS");
        String decryptPFXBytes = CaConstant.getCaFilePath("CLIENT_DECRYPTPFX");
        String decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");
        
       // 客户端加密过程 :客户端私钥(pfx)、pwd + 平台公钥(cer)
        final PKCS7 pkcs7Client = new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes)), decryptPFXKey);
        final byte[] encodeData = pkcs7Client.pkcs7Encrypt(source, FileUtils.readFileToByteArray(new File(CaConstant.getCaFilePath("PLATFORM_DECRYPTCER"))));
        String encodeText= new String(Base64.encodeBase64(encodeData));
        LOGGER.info("客户端加密:{}",encodeText);
        final String base64EncryptTxt = encodeText;
        System.out.println(base64EncryptTxt);


      //  平台端解密过程 :平台端私钥(pfx)、pwd
        decryptPFXBytes = CaConstant.getProperty("PLATFORM_DECRYPTPFX");
        decryptPFXKey = CaConstant.getProperty("PLATFORM_DECRYPTPFX_KEY");
        final PKCS7 pkcs7Platform = new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes)), decryptPFXKey);

        final byte[] decodeData = pkcs7Platform.pkcs7Decrypt(Base64.decodeBase64(base64EncryptTxt));
        LOGGER.info("平台端解密:{}",new String(decodeData));
        System.out.println(new String(decodeData));


       //  平台端加密过程 :平台端私钥(pfx)、pwd + 客户端公钥(cer)
         String decryptPFXBytes1 = CaConstant.getProperty("PLATFORM_DECRYPTPFX");
         String decryptPFXKey1 = CaConstant.getProperty("PLATFORM_DECRYPTPFX_KEY");
    	
       final String source2 = "姆神要吃巧克力";
        LOGGER.info("平台端加密原文:{}",source2);
        final PKCS7 pkcs7Client1=new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes1)), decryptPFXKey1);
        final byte[] encodeData2 = pkcs7Client1.pkcs7Encrypt(source2, FileUtils.readFileToByteArray(new File(CaConstant.getProperty("CLIENT_DECRYPTCER"))));
        String encodeText2= new String(Base64.encodeBase64(encodeData2));
        LOGGER.info("平台端加密:{}",encodeText2);
        System.out.println(encodeText2);


        //客户端解密过程 :*客户端私钥(pfx)、pwd
         decryptPFXBytes = CaConstant.getProperty("CLIENT_DECRYPTPFX");
        decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");
        final PKCS7 pkcs7Client2 = new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes)), decryptPFXKey);
        final String base64EncryptTxt2 = encodeText2;
        final byte[] decodeData2 = pkcs7Client2.pkcs7Decrypt(Base64.decodeBase64(base64EncryptTxt2));
        LOGGER.info("客户端解密:{}",new String(decodeData2));
        System.out.println(new String(decodeData2));

    }


   
}
