package com.apass.esp.invoice;

import com.apass.esp.invoice.model.DataDescription;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.apass.esp.invoice.model.GlobalInfo;
import com.apass.esp.invoice.model.ReturnStateInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by jie.xu on 17/3/28.
 * 电子发票
 */
public class ElectronicInvoiceService {
  private static final String testUrl = "http://fw1test.shdzfp.com:7500/axis2/services/SajtIssueInvoiceService?wsdl";



  public String requestFaPiao() throws Exception {
    EncryptInvoiceContentHandler handler = new EncryptInvoiceContentHandler();
    String params = generateXMl(handler.fapiaoKJData());
    System.out.println(params);
    JaxWsDynamicClientFactory jwdc = JaxWsDynamicClientFactory.newInstance();
    org.apache.cxf.endpoint.Client client = jwdc.createClient(testUrl);

    Object[] tsobjects = client.invoke("eiInterface", new Object[]{params});
    String responeMessage = (String) tsobjects[0];
    return responeMessage;
  }


  private String generateXMl(String dataContentXml) {
    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version='1.0' encoding='utf-8' ?>\n" +
        "<interface xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
        "\txsi:schemaLocation='http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd'\n" +
        "\tversion='DZFP1.0'>");
    sb.append("\r\n");
    sb.append(createGlobalInfoXml());
    sb.append("\r\n");
    sb.append(createReturnStateInfoXml());
    sb.append("\r\n");
    sb.append(dataContentXml);
    sb.append("\r\n");
    sb.append("</interface>");
    return sb.toString();
  }

  private String createGlobalInfoXml() {
    GlobalInfo globalInfo = new GlobalInfo();
    globalInfo.setTerminalCode("0");
    globalInfo.setAppId("DZFP");
    globalInfo.setVersion("1.0");
    globalInfo.setInterfaceCode("ECXML.FPKJ.BC.E_INV");//开具发票
    globalInfo.setRequestCode("111MFWIK");
    globalInfo.setRequestTime("2016-11-28 10:19:16");
    globalInfo.setResponseCode("121");
    globalInfo.setDataExchangeId("111MFWIKECXML.FPKJ.BC.E_INV20161128eXl4EymmJ");
    globalInfo.setUserName("111MFWIK");
    globalInfo.setPassWord("6051435131bDo4Vs6uBMpJfjwVUdCiSwyy");
    globalInfo.setTaxpayerId("310101000000090");
    globalInfo.setAuthorizationCode("3100000090");

    XStream xStream = new XStream();
    xStream.alias("globalInfo", GlobalInfo.class);
    String xml = xStream.toXML(globalInfo);
    return xml;
  }

  private String createReturnStateInfoXml() {
    ReturnStateInfo stateInfo = new ReturnStateInfo();
    stateInfo.setReturnCode(StringUtils.EMPTY);
    stateInfo.setReturnMessage(StringUtils.EMPTY);
    XStream xStream = new XStream();
    xStream.alias("returnStateInfo", ReturnStateInfo.class);
    String xml = xStream.toXML(stateInfo);
    return xml;
  }


  private String createContentXml(String contentXml) {
    StringBuilder sb = new StringBuilder();
    sb.append("<Data>");
    sb.append("\r\n");
    DataDescription dataDescription = new DataDescription();
    dataDescription.setZipCode("0");
    dataDescription.setCodeType("0");
    dataDescription.setEncryptCode("0");
    XStream xStream = new XStream();
    xStream.alias("dataDescription", DataDescription.class);
    String dataDescriptionXml = xStream.toXML(dataDescription);
    sb.append(dataDescriptionXml);
    sb.append("\r\n");
    sb.append("<content>");
    sb.append("\r\n");
    sb.append(contentXml);
    sb.append("\r\n");
    sb.append("</content>");
    sb.append("\r\n");
    sb.append("</Data>");
    return sb.toString();
  }

  /**
   * 创建发票开具content
   */
  private String createFaPiaoKJXml() throws UnsupportedEncodingException {
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
    return new BASE64Encoder().encode(content.getBytes("UTF-8"));
  }

  public static void main(String[] args) throws Exception {
    ElectronicInvoiceService service = new ElectronicInvoiceService();
    System.out.println(service.requestFaPiao());
//   System.out.println(new String(new BASE64Decoder().decodeBuffer("5Y+R56Wo6K+35rGC5rWB5rC05Y+35bey57uP5a2Y5Zyo")));
  }
}
