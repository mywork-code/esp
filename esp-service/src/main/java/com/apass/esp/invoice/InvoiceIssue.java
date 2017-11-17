package com.apass.esp.invoice;
import java.io.File;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import com.aisino.PKCS7;
import com.apass.esp.invoice.model.FaPiaoCommonNode;
import com.apass.esp.invoice.model.FaPiaoDLoad;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
public class InvoiceIssue implements InvoiceHandler{
    @Override
    public String fapiaoEmailSend(List<FaPiaoCommonNode> sendinfo,List<List<FaPiaoCommonNode>> invoiceinfolist) throws Exception {
        return this.createFaPiaoEmailXml(sendinfo,invoiceinfolist);
    }
    @Override
    public String fapiaoDownLoad(FaPiaoDLoad entity) throws Exception {
        return this.createFaPiaoDLXml(entity);
    }
    @Override
    public String fapiaoKJData(FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        return this.createFaPiaoKJXml(ensale,list,enbuy);
    }
    /**
     * 3.3 创建邮箱发票推送content
     * @param sendinfo
     * @param invoiceinfolist
     * @return
     * @throws Exception 
     */
    private String createFaPiaoEmailXml(List<FaPiaoCommonNode> sendinfo, List<List<FaPiaoCommonNode>> invoiceinfolist) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<REQUEST_EMAILPHONEFPTS class=\"REQUEST_EMAILPHONEFPTS\">").append("\r\n");
            sb.append("<TSFSXX class=\"TSFSXX\">").append("\r\n");
                sb.append("<COMMON_NODES class=\"COMMON_NODE;\" size=\"4\">").append("\r\n");
                //邮箱发票推送-推送方式信息 一条
                for(FaPiaoCommonNode node : sendinfo){
                    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
                    xStream.autodetectAnnotations(true);
                    String faPiaoKJXml = xStream.toXML(node);
                    sb.append(faPiaoKJXml).append("\r\n");
                }
                sb.append("</COMMON_NODES>").append("\r\n");
            sb.append("</TSFSXX>").append("\r\n");
            sb.append("<FPXXS class=\"FPXX;\" size=\"").append(invoiceinfolist.size()+"\">").append("\r\n");
                //邮箱发票推送-发票信息   多条 
                for(List<FaPiaoCommonNode> node : invoiceinfolist){
                    sb.append("<FPXX>").append("\r\n");
                        sb.append("<COMMON_NODES class=\"COMMON_NODE;\" size=\"5\">").append("\r\n");
                        for(FaPiaoCommonNode en : node){
                            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
                            xStream.autodetectAnnotations(true);
                            String faPiaoKJXml = xStream.toXML(en);
                            sb.append(faPiaoKJXml).append("\r\n");
                        }
                        sb.append("</COMMON_NODES>").append("\r\n");
                    sb.append("</FPXX>").append("\r\n");
                }
            sb.append("<FPXXS>").append("\r\n");
        sb.append("</REQUEST_EMAILPHONEFPTS>");
        String str = sb.toString();
        return encodeText(str);
    }
    /**
     * 3.2 创建发票下载content
     * @param entity
     * @return
     * @throws Exception 
     */
    private String createFaPiaoDLXml(FaPiaoDLoad entity) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<REQUEST_FPXXXZ_NEW class=\"REQUEST_FPXXXZ_NEW\">");
        sb.append("\r\n");
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.autodetectAnnotations(true);
        String faPiaoKJXml = xStream.toXML(entity);
        sb.append(faPiaoKJXml);
        sb.append("\r\n");
        sb.append("</REQUEST_FPXXXZ_NEW>");
        String str = sb.toString();
        return encodeText(str);
    }
    /**
     * 3.1 创建发票开具content
     * @return
     * @throws Exception
     */
    private String createFaPiaoKJXml(FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<REQUEST_FPKJXX class=\"REQUEST_FPKJXX\">");
        sb.append("\r\n");
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.autodetectAnnotations(true);
        String faPiaoKJXml = xStream.toXML(ensale);
        faPiaoKJXml = faPiaoKJXml.replaceFirst("<FPKJXX_FPTXX>", "<FPKJXX_FPTXX class=\"FPKJXX_FPTXX\">");
        sb.append(faPiaoKJXml);
//        System.out.println("faPiaoKJXml--LOG:\n"+faPiaoKJXml);
//        System.out.println("-----------------faPiaoKJXml--over----------");
        sb.append("\r\n");
        sb.append("<FPKJXX_XMXXS class=\"FPKJXX_XMXX;\" size=").append("\""+list.size()+"\">");
        sb.append("\r\n");
        for(FaPiaoKJXM entity : list){
            XStream xStream2 = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream2.autodetectAnnotations(true);
            String str = xStream2.toXML(entity);
            sb.append(str);
            sb.append("\r\n");
//            System.out.println("FaPiaoKJXM--LOG:\n"+str);
//            System.out.println("-----------------FaPiaoKJXM--one by one----------");
        }
        sb.append("</FPKJXX_XMXXS>");
        sb.append("\r\n");
        XStream xStream3 = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream3.autodetectAnnotations(true);
        String ddXml = xStream3.toXML(enbuy);
        ddXml = ddXml.replaceFirst("<FPKJXX_DDXX>", "<FPKJXX_DDXX class=\"FPKJXX_DDXX\">");
        sb.append(ddXml);
//        System.out.println("FaPiaoKJDD--LOG:\n"+ddXml);
//        System.out.println("-----------------FaPiaoKJDD--over----------");
        sb.append("\r\n");
        sb.append("</REQUEST_FPKJXX>");
        String content = sb.toString();
//        System.out.println("content--LOG:\n"+content);
//        System.out.println("-----------------content--over----------");
        return encodeText(content);
    }
    private String encodeText(String source) throws Exception{
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
}