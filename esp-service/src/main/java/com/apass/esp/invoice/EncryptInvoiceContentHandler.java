package com.apass.esp.invoice;

import com.aisino.PKCS7;
import com.apass.esp.invoice.model.DataDescription;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * Created by jie.xu on 17/4/5.
 * 处理加密报文
 */
public class EncryptInvoiceContentHandler implements InvoiceContentHandler{

  /**
   * 2:CA加密
   * @return
   * @throws Exception
   */
  public String fapiaoKJData() throws Exception{
    StringBuilder sb = new StringBuilder();
    sb.append("<Data>");
    sb.append("\r\n");
    DataDescription dataDescription = new DataDescription();
    dataDescription.setZipCode("0");
    dataDescription.setCodeType("2");
    dataDescription.setEncryptCode("2");
    XStream xStream = new XStream();
    xStream.alias("dataDescription", DataDescription.class);
    String dataDescriptionXml = xStream.toXML(dataDescription);
    sb.append(dataDescriptionXml);
    sb.append("\r\n");
    sb.append("<content>");
    sb.append("\r\n");
    sb.append(createFaPiaoKJXml());
    sb.append("\r\n");
    sb.append("</content>");
    sb.append("\r\n");
    sb.append("</Data>");
    return sb.toString();
  }

  /**
   * 创建发票开具content
   */
  private String createFaPiaoKJXml() throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("<REQUEST_FPKJXX class=\"REQUEST_FPKJXX\">");
    sb.append("\r\n");
    FaPiaoKJ faPiaoKJ = new FaPiaoKJ();
    faPiaoKJ.setFpqqlsh("d2222222222222222217");
    faPiaoKJ.setDsptbm("111MFWIK");
    faPiaoKJ.setNsrsbh("310101000000090");
    faPiaoKJ.setNsrmc("雅诗兰黛（上海）商贸有限公司");
    faPiaoKJ.setNsrdzdah(StringUtils.EMPTY);
    faPiaoKJ.setSwjgDm(StringUtils.EMPTY);
    faPiaoKJ.setDkbz("0");
    faPiaoKJ.setPydm("");
    faPiaoKJ.setKpxm("化妆品");
    faPiaoKJ.setBmbBbh("1.0");
    faPiaoKJ.setXhfNsrsbh("310101000000090");
    faPiaoKJ.setXhfmc("雅诗兰黛（上海）商贸有限公司");
    faPiaoKJ.setXhfDz("上海市闵行区金都路3688号301、302、306室");
    faPiaoKJ.setXhfDh("22039999");
    faPiaoKJ.setXhfYhzh("");
    faPiaoKJ.setGhfmc("许嘉心");
    faPiaoKJ.setGhfNsrsbh("");
    faPiaoKJ.setGhfSf("");
    faPiaoKJ.setGhfDz("");
    faPiaoKJ.setGhfDz("");
    faPiaoKJ.setGhfSj("");
    faPiaoKJ.setGhfEmail("");
    faPiaoKJ.setGhfqylx("01");
    faPiaoKJ.setGhfYhzh("");
    faPiaoKJ.setHyDm("");
    faPiaoKJ.setHyMc("");
    faPiaoKJ.setKpy("财务");
    faPiaoKJ.setSky("");
    faPiaoKJ.setFhr("");
    faPiaoKJ.setKprq("");
    faPiaoKJ.setKplx("1");
    faPiaoKJ.setYfpDm("");
    faPiaoKJ.setYfpHm("");
    faPiaoKJ.setCzdm("10");
    faPiaoKJ.setQdBz("0");
    faPiaoKJ.setQdxmmc("");
    faPiaoKJ.setChyy("");
    faPiaoKJ.setTschbz("");
    faPiaoKJ.setKphjje("20");
    faPiaoKJ.setHjbhsje("0");
    faPiaoKJ.setHjse("0");
    faPiaoKJ.setBz("");
    faPiaoKJ.setByzd1("");
    faPiaoKJ.setByzd2("");
    faPiaoKJ.setByzd3("");
    faPiaoKJ.setByzd4("");
    faPiaoKJ.setByzd5("");
    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
    xStream.autodetectAnnotations(true);
    String faPiaoKJXml = xStream.toXML(faPiaoKJ);
    faPiaoKJXml = faPiaoKJXml.replace("<FPKJXX_FPTXX>", "<FPKJXX_FPTXX class=\"FPKJXX_FPTXX\">");

    sb.append(faPiaoKJXml);
    sb.append("\r\n");
    sb.append("  <FPKJXX_XMXXS class=\"FPKJXX_XMXX;\" size=\"1\">");
    sb.append("\r\n");
    FaPiaoKJXM faPiaoKJXM = new FaPiaoKJXM();
    faPiaoKJXM.setXmmc("0J3M01 净痘凝胶 10ML ");
    faPiaoKJXM.setXmdw("");
    faPiaoKJXM.setGgxh("");
    faPiaoKJXM.setXmsl("1");
    faPiaoKJXM.setHsbz("1");
    faPiaoKJXM.setFphxz("0");
    faPiaoKJXM.setXmdj("20.0");
    faPiaoKJXM.setSpbm("1010101030000000000");
    faPiaoKJXM.setZxbm("");
    faPiaoKJXM.setYhzcbs("0");
    faPiaoKJXM.setLslbs("");
    faPiaoKJXM.setZzstsgl("");
    faPiaoKJXM.setKce("0");
    faPiaoKJXM.setXmje("20.0");
    faPiaoKJXM.setSl("0.17");
    faPiaoKJXM.setSe("");
    faPiaoKJXM.setByzd1("");
    faPiaoKJXM.setByzd2("");
    faPiaoKJXM.setByzd3("");
    faPiaoKJXM.setByzd4("");
    faPiaoKJXM.setByzd5("");
    XStream xStream2 = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
    xStream2.autodetectAnnotations(true);
    sb.append(xStream2.toXML(faPiaoKJXM));
    sb.append("\r\n");
    sb.append("</FPKJXX_XMXXS>");
    sb.append("\r\n");
    FaPiaoKJDD faPiaoKJDD = new FaPiaoKJDD();
    faPiaoKJDD.setDdh("2492684718573093");
    faPiaoKJDD.setThdh("2492684718573093");
    faPiaoKJDD.setDddate("2016-10-31 10:47:17");
    XStream xStream3 = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
    xStream3.autodetectAnnotations(true);
    String ddXml = xStream3.toXML(faPiaoKJDD);
    ddXml = ddXml.replace("<FPKJXX_DDXX>", "<FPKJXX_DDXX class=\"FPKJXX_DDXX\">");
    sb.append(ddXml);
    sb.append("\r\n");
    sb.append("</REQUEST_FPKJXX>");
    String content = sb.toString();
    return encodeText(content);
  }

  private String encodeText(String source) throws Exception{
    final String trustsBytes = CaConstant.getCaFilePath("PUBLIC_TRUSTS");
    String decryptPFXBytes = CaConstant.getCaFilePath("CLIENT_DECRYPTPFX");
    String decryptPFXKey = CaConstant.getProperty("CLIENT_DECRYPTPFX_KEY");

    // 客户端加密过程 :客户端私钥(pfx)、pwd + 平台公钥(cer)
    final PKCS7 pkcs7Client = new PKCS7(FileUtils.readFileToByteArray(new File(trustsBytes)), FileUtils.readFileToByteArray(new File(decryptPFXBytes)), decryptPFXKey);
    final byte[] encodeData = pkcs7Client.pkcs7Encrypt(source, FileUtils.readFileToByteArray(new File(CaConstant.getCaFilePath("PLATFORM_DECRYPTCER"))));
    String encodeText= new String(Base64.encodeBase64(encodeData));
    return encodeText;
  }
}
