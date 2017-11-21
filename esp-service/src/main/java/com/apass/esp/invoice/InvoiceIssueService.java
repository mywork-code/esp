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
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.RandomUtils;
import com.thoughtworks.xstream.XStream;
/**
 * 电子发票
 * @author Administrator
 *
 */
public class InvoiceIssueService {
    private static final String testUrl = "http://fw1test.shdzfp.com:7500/axis2/services/SajtIssueInvoiceService?wsdl";
    /**
     * 3.2  返回报文示例与数据项说明
     * @param entity
     * @return
     * @throws Exception
     */
    public String requestFaPiaoDL(FaPiaoDLoad entity) throws Exception {
        String params = createContentDL(entity);
        System.out.println(params);
        JaxWsDynamicClientFactory jwdc = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = jwdc.createClient(testUrl);
        Object[] tsobjects = client.invoke("eiInterface", new Object[]{params});
        return (String) tsobjects[0];
    }
    /**
     * 3.1  返回报文示例（只有外层报文，没 Content ）：返回报文只有外层报文，Content 内容为空，无数据项。  直接返回
     * @param ensale
     * @param list
     * @param enbuy
     * @return
     * @throws Exception
     */
    public String requestFaPiaoKJ(FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        String params = createContentXml(ensale,list,enbuy);
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
    private String createContentDL(FaPiaoDLoad entity) throws Exception {
        StringBuilder sb = new StringBuilder();
        //globalInfo
        //returnStateInfo
        //dataDescription
        sb.append(generateXMl("2"));
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
    private String createContentXml(FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        StringBuilder sb = new StringBuilder();
        //globalInfo
        //returnStateInfo
        //dataDescription
        sb.append(generateXMl("1"));
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
     * !<dataDescription><!--数据描述-->
     * @return
     * @throws Exception
     */
    private String generateXMl(String type) throws Exception {
        GlobalInfoEctype globalInfo = new GlobalInfoEctype(type);
        ReturnStateInfo stateInfo = new ReturnStateInfo("1");
        globalInfo.setPassWord(PassWordCreate.passWordCreate("92884519", RandomUtils.getNum(10)));
        globalInfo.setRequestTime(DateFormatUtil.getCurrentTime("YYYY-MM-DD HH:MM:SS"));
        StringBuffer sbex = new StringBuffer(globalInfo.getRequestCode());
        sbex.append(globalInfo.getInterfaceCode());
        sbex.append(DateFormatUtil.getCurrentTime("YYYYMMDD"));
        sbex.append("eXl4EymmJ");//111MFWIKECXML.FPKJ.BC.E_INV20161128eXl4EymmJ
        globalInfo.setDataExchangeId(sbex.toString());
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
        sb.append(globalInfostr);
        sb.append("\r\n");
        //returnStateInfo
        XStream xStreamreturn = new XStream();
        xStreamreturn.alias("returnStateInfo", ReturnStateInfo.class);
        String stateInfostr = xStreamreturn.toXML(stateInfo);
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
        sb.append(dataDescriptionXml);
        sb.append("\r\n");
        return sb.toString();
    }
}