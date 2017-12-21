package com.apass.esp.invoice;
import java.util.List;

import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import com.aisino.PassWordCreate;
import com.apass.esp.invoice.model.DataDescription;
import com.apass.esp.invoice.model.FaPiaoDLoad;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.apass.esp.invoice.model.GlobalInfoEctype;
import com.apass.esp.invoice.model.ReturnStateInfo;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.RandomUtils;
import com.thoughtworks.xstream.XStream;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 电子发票
 * @author Administrator
 *
 */
@Service
public class InvoiceIssueService {
//    private static String TESTURL="http://fw1test.shdzfp.com:7500/axis2/services/SajtIssueInvoiceService?wsdl";
   // private static String PRODUCTURL="http://fw1.shdzfp.com:9000/axis2/services/SajtIssueInvoiceService?wsdl";
    private static String TESTURL= "http://fw1test.shdzfp.com:9000/sajt-shdzfp-sl-http/SvrServlet";
    private static String PRODUCTURL="http://112.65.253.4:9012/sajt-shdzfp-sl-http/SvrServlet";
    private static final Logger log = LoggerFactory.getLogger(InvoiceIssueService.class);

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    /**
     * 下载发票
     * @param entity
     * @return
     * @throws Exception
     */
    public String httpRequestFaPiaoDL(FaPiaoDLoad entity) throws Exception {
        String params = createContentDL(entity);
        log.info("下载发票接口参数:--------------->" + params);
        return HttpClientUtils.getMethodPostResponse(getInvoiceUrl(),new StringEntity(params,"UTF-8"));
    }

    /**
     * 开具发票
     * @param ensale
     * @param list
     * @param enbuy
     * @return
     * @throws Exception
     */
    public String httpRequestFaPiaoKJ(FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        String params = createContentXml(ensale,list,enbuy);
        log.info("发票开具接口参数:--------------->" + params);
        return HttpClientUtils.getMethodPostResponse(getInvoiceUrl(),new StringEntity(params,"UTF-8"));
    }

    /**
     * 3.2 <!--交换数据-->
     * @param entity
     * @return
     * @throws Exception
     */
    private String createContentDL(FaPiaoDLoad entity) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(generateXMl("2"));
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
     * @param ensale
     * @param list
     * @param enbuy
     * @return
     * @throws Exception
     */
    private String createContentXml(FaPiaoKJ ensale,List<FaPiaoKJXM> list,FaPiaoKJDD enbuy) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(generateXMl("1"));
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
     * !<dataDescription><!--数据描述-->
     * @return
     * @throws Exception
     */
    private String generateXMl(String type) throws Exception {
        GlobalInfoEctype globalInfo = new GlobalInfoEctype(type,systemEnvConfig.isPROD());
        ReturnStateInfo stateInfo = new ReturnStateInfo("1");
        if(systemEnvConfig.isPROD()){
            globalInfo.setPassWord(PassWordCreate.passWordCreate("38201136", RandomUtils.getNum(10)));
        }else{
            globalInfo.setPassWord(PassWordCreate.passWordCreate("92884519", RandomUtils.getNum(10)));
        }
        globalInfo.setRequestTime(DateFormatUtil.getCurrentDate());
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
    private String getInvoiceUrl(){
        if(systemEnvConfig.isDEV()){
            return TESTURL;
        }
        if(systemEnvConfig.isUAT()){
            return TESTURL;
        }
        if(systemEnvConfig.isPROD()){
            return PRODUCTURL;
        }
        return null;
    }
}