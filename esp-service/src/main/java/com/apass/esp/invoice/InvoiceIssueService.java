package com.apass.esp.invoice;
import java.util.List;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import com.aisino.PassWordCreate;
import com.apass.esp.invoice.model.DataDescription;
import com.apass.esp.invoice.model.FaPiaoDLoad;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.apass.esp.invoice.model.GlobalInfoEctype;
import com.apass.esp.invoice.model.ReturnStateInfo;
import com.apass.gfb.framework.utils.RandomUtils;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.misc.BASE64Decoder;
/**
 * 电子发票
 * @author Administrator
 *
 */
public class InvoiceIssueService {
    private static final String testUrl = "http://fw1test.shdzfp.com:7500/axis2/services/SajtIssueInvoiceService?wsdl";
    /**
     * 3.2
     * @param globalInfo
     * @param stateInfo
     * @param entity
     * @return
     * @throws Exception
     */
    public String requestFaPiaoDL(GlobalInfoEctype globalInfo,ReturnStateInfo stateInfo,FaPiaoDLoad entity) throws Exception {
        String params = createContentDL(globalInfo,stateInfo,entity);
        System.out.println(params);
        JaxWsDynamicClientFactory jwdc = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = jwdc.createClient(testUrl);
        Object[] tsobjects = client.invoke("eiInterface", new Object[]{params});
        return (String) tsobjects[0];
    }

    public ReturnStateInfo getFaPiaoReturnState(String respXML) throws Exception{
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
     * 3.1
     * @param globalInfo
     * @param stateInfo
     * @param ensale
     * @param list
     * @param enbuy
     * @return
     * @throws Exception
     */
    public String requestFaPiaoKJ(GlobalInfoEctype globalInfo,ReturnStateInfo stateInfo,FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        String params = createContentXml(globalInfo,stateInfo,ensale,list,enbuy);
        System.out.println(params);
        JaxWsDynamicClientFactory jwdc = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = jwdc.createClient(testUrl);
        Object[] tsobjects = client.invoke("eiInterface", new Object[]{params});
        return (String) tsobjects[0];
    }
    /**
     * 3.2 <!--交换数据-->
     * @param globalInfo
     * @param stateInfo
     * @param entity
     * @return
     * @throws Exception
     */
    private String createContentDL(GlobalInfoEctype globalInfo, ReturnStateInfo stateInfo, FaPiaoDLoad entity) throws Exception {
        StringBuilder sb = new StringBuilder();
        //globalInfo
        //returnStateInfo
        //dataDescription
        sb.append(generateXMl(globalInfo, stateInfo));
        //content
        sb.append("<content>");
        sb.append("\r\n");
        InvoiceHandler invoice = new InvoiceIssue();
        String invoicedata = invoice.fapiaoDownLoad(entity);
        sb.append(invoicedata);
        sb.append("\r\n");
        sb.append("</content>");
        sb.append("\r\n");
        sb.append("</Data>");
        sb.append("\r\n");
        sb.append("</interface>");
        return sb.toString();
    }
    /**
     * 3.1 <!--交换数据-->
     * @param globalInfo
     * @param stateInfo
     * @param ensale
     * @param list
     * @param enbuy
     * @return
     * @throws Exception
     */
    private String createContentXml(GlobalInfoEctype globalInfo,ReturnStateInfo stateInfo,FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        StringBuilder sb = new StringBuilder();
        //globalInfo
        //returnStateInfo
        //dataDescription
        sb.append(generateXMl(globalInfo, stateInfo));
        //content
        sb.append("<content>");
        sb.append("\r\n");
        InvoiceHandler invoice = new InvoiceIssue();
        String invoicedata = invoice.fapiaoKJData(ensale,list,enbuy);
//        System.out.println("invoicedata--LOG BY CA:\n"+invoicedata);
//        System.out.println("-----------------invoicedata--over----------");
        sb.append(invoicedata);
        sb.append("\r\n");
        sb.append("</content>");
        sb.append("\r\n");
        sb.append("</Data>");
        sb.append("\r\n");
        sb.append("</interface>");
        return sb.toString();
    }
    /**
     * 报文发送通用信息
     * @param globalInfo  <!--全局信息-->
     * @param stateInfo <!--返回信息-->
     * !
     * <dataDescription><!--数据描述-->
     * @return
     * @throws Exception
     */
    private String generateXMl(GlobalInfoEctype globalInfo,ReturnStateInfo stateInfo) throws Exception {
        globalInfo.setPassWord(PassWordCreate.passWordCreate("92884519", RandomUtils.getNum(10)));
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='utf-8' ?>\n" +
            "<interface xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n" +
            "\txsi:schemaLocation='http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd'\n" +
            "\tversion='DZFP1.0'>");
        sb.append("\r\n");
        //globalInfo
        XStream xStreamglobal = new XStream();
        xStreamglobal.alias("globalInfo", GlobalInfoEctype.class);
        String globalInfostr = xStreamglobal.toXML(globalInfo);
//        System.out.println("globalInfostr--LOG:\n"+globalInfostr);
//        System.out.println("-----------------globalInfostr--over----------");
        sb.append(globalInfostr);
        sb.append("\r\n");
        //returnStateInfo
        XStream xStreamreturn = new XStream();
        xStreamreturn.alias("returnStateInfo", ReturnStateInfo.class);
        String stateInfostr = xStreamreturn.toXML(stateInfo);
//        System.out.println("stateInfostr--LOG:\n"+stateInfostr);
//        System.out.println("-----------------stateInfostr--over----------");
        sb.append(stateInfostr);
        sb.append("\r\n");
        sb.append("<Data>");
        sb.append("\r\n");
        //dataDescription
        DataDescription dataDescription = new DataDescription();
        dataDescription.setZipCode("0");
        dataDescription.setEncryptCode("2");
        dataDescription.setCodeType("CA加密");
        XStream xStreamdataDesc = new XStream();
        xStreamdataDesc.alias("dataDescription", DataDescription.class);
        String dataDescriptionXml = xStreamdataDesc.toXML(dataDescription);
//        System.out.println("dataDescriptionXml--LOG:\n"+dataDescriptionXml);
//        System.out.println("-----------------dataDescriptionXml--over----------");
        sb.append(dataDescriptionXml);
        sb.append("\r\n");
        return sb.toString();
    }
}