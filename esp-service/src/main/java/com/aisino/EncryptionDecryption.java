package com.aisino;
import java.io.File;
import java.util.Iterator;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.apass.esp.common.utils.GZipUtils;
import com.apass.esp.invoice.CaConstant;
import com.apass.esp.invoice.model.InvoiceDLReturn;
import com.apass.esp.invoice.model.ReturnStateInfo;
import sun.misc.BASE64Decoder;
public class EncryptionDecryption {
    private static final int maxContentByteSize = 1024 * 10 ;
    private final static Logger LOGGER = LoggerFactory.getLogger(EncryptionDecryption.class);
    /**
     * 解密返回returnStateInfo
     * @param respXML
     * @return
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    public static ReturnStateInfo getFaPiaoReturnState(String respXML) throws Exception{
        Document doc = DocumentHelper.parseText(respXML);
        Element root = doc.getRootElement();
        Element returnEle = root.element("returnStateInfo");
        String returnCode = returnEle.element("returnCode").getText();
        String returnMessage =  returnEle.element("returnMessage").getText();
        ReturnStateInfo result = new ReturnStateInfo();
        result.setReturnCode(returnCode);
        result.setReturnMessage(new String(new BASE64Decoder().decodeBuffer(returnMessage)));
        return result;
    }
    /**
     * 解密发票下载下载 为STRING <REQUEST_FPKJXX_FPJGXX_NEW class="REQUEST_FPKJXX_FPJGXX_NEW"> 标签</REQUEST_FPKJXX_FPJGXX_NEW>
     * @param respXML
     * @return
     * @throws Exception
     */
    public static String getRequestFaPiaoDL(String respXML) throws Exception{
        Document doc = DocumentHelper.parseText(respXML);
        Element root = doc.getRootElement();
        Element data = root.element("Data");
        Element returnEle = data.element("dataDescription");
        Element contentEle = data.element("content");
        String zipCode = returnEle.element("zipCode").getText();
        String content = contentEle.getText();
        return decrypt(content, zipCode);
    }
    /**
     * 解密发票下载下载 为 InvoiceDLReturn对象
     * @param respXML
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static InvoiceDLReturn getRequestFaPiaoDLRetuen(String respXML) throws Exception{
        String returnentity = getRequestFaPiaoDL(respXML);
        Document doc = DocumentHelper.parseText(returnentity);
        Element root = doc.getRootElement();
        InvoiceDLReturn entity = new InvoiceDLReturn();
        for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
            Element node = it.next();
            String name = FarmartJavaBean.farmartField(node.getName());
            entity = (InvoiceDLReturn) FarmartJavaBean.farmartJavaB(entity, InvoiceDLReturn.class, node.getData(), name);
        }
        return entity;
    }
    /**
     * 加密content
     */
    public static String encryptContent(String content) throws Exception{
       byte[] bytes = content.getBytes();
       if(bytes.length < maxContentByteSize){
           return encodeText(content);
       } else {
           return encodeTextByGzip(content);
       }
    }
    /**
     * 解密
     * 0:不压缩；1：压缩
     */
    public static String decrypt(String encryTxt, String zipCode) throws Exception{
//        byte[] bytes = encryTxt.getBytes();
        if("0".equals(zipCode)){
            return decodeText(encryTxt);
        } else {
            return decodeTextByGzip(encryTxt);
        }
    }
    /**
     *content 字节大小小于10KB,就先ca加密，再base64加密
     */
    private static String encodeText(String source) throws Exception{
        final String trustsBytes = CaConstant.getCaFilePath("PUBLIC_TRUSTS");
        String decryptPFXBytes = CaConstant.getCaFilePath("CLIENT_DECRYPTPFX");
        String decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");
        String cafilepath = CaConstant.getCaFilePath("PLATFORM_DECRYPTCER");
        // 客户端加密过程 :客户端私钥(pfx)、pwd + 平台公钥(cer)
        byte[] trustsBytesarr = FileUtils.readFileToByteArray(new File(trustsBytes));
        byte[] privatePFXBytesarr = FileUtils.readFileToByteArray(new File(decryptPFXBytes));
        final PKCS7 pkcs7Client = new PKCS7(trustsBytesarr, privatePFXBytesarr, decryptPFXKey);
        byte[] publicPFXBytesarr2 = FileUtils.readFileToByteArray(new File(cafilepath));
        final byte[] encodeData = pkcs7Client.pkcs7Encrypt(source, publicPFXBytesarr2);
        String encodeText= new String(Base64.encodeBase64(encodeData));
        return encodeText;
    }

    /**
     *content 字节大小大于10KB,就先ca加密，再gzip压缩，再base64加密
     */
    private static String encodeTextByGzip(String source) throws Exception{
        final String trustsBytes = CaConstant.getCaFilePath("PUBLIC_TRUSTS");
        String decryptPFXBytes = CaConstant.getCaFilePath("CLIENT_DECRYPTPFX");
        String decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");
        String cafilepath = CaConstant.getCaFilePath("PLATFORM_DECRYPTCER");
        // 客户端加密过程 :客户端私钥(pfx)、pwd + 平台公钥(cer)
        byte[] trustsBytesarr = FileUtils.readFileToByteArray(new File(trustsBytes));
        byte[] privatePFXBytesarr = FileUtils.readFileToByteArray(new File(decryptPFXBytes));
        final PKCS7 pkcs7Client = new PKCS7(trustsBytesarr, privatePFXBytesarr, decryptPFXKey);
        byte[] publicPFXBytesarr2 = FileUtils.readFileToByteArray(new File(cafilepath));
        final byte[] encodeData = pkcs7Client.pkcs7Encrypt(source, publicPFXBytesarr2);
        String encodeText= new String(Base64.encodeBase64(GZipUtils.compress(encodeData)));
        return encodeText;
    }
    /**
     * 先base64解码,再gzip解压压缩,再ca解密
     */
    private static String decodeTextByGzip(String encryTxt) throws Exception{
        final String trustsBytes = CaConstant.getCaFilePath("PUBLIC_TRUSTS");
        String decryptPFXBytes = CaConstant.getCaFilePath("CLIENT_DECRYPTPFX");
        String decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");
        final PKCS7 pkcs7Client2 = new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes)), decryptPFXKey);
        final byte[] decodeData2 = pkcs7Client2.pkcs7Decrypt(GZipUtils.decompress(Base64.decodeBase64(encryTxt)));
        LOGGER.info("解密:{}",new String(decodeData2));
        return new String(decodeData2);
    }
    /**
     *先base64解码,再ca解密,这种不需要gzip解压缩
     */
    private static String decodeText(String encryTxt) throws Exception{
        final String trustsBytes = CaConstant.getCaFilePath("PUBLIC_TRUSTS");
        String decryptPFXBytes = CaConstant.getCaFilePath("CLIENT_DECRYPTPFX");
        String decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");
        final PKCS7 pkcs7Client2 = new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes)), decryptPFXKey);
        final byte[] decodeData2 = pkcs7Client2.pkcs7Decrypt(Base64.decodeBase64(encryTxt));
        LOGGER.info("解密:{}",new String(decodeData2));
        return new String(decodeData2);
    }
}