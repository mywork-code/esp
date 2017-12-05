package com.apass.esp.invoice;

import java.util.List;

import com.aisino.EncryptionDecryption;
import com.apass.esp.invoice.model.FaPiaoCommonNode;
import com.apass.esp.invoice.model.FaPiaoDLoad;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class InvoiceIssue implements InvoiceHandler {
    @Override
    public String fapiaoEmailSend(List<FaPiaoCommonNode> sendinfo, List<List<FaPiaoCommonNode>> invoiceinfolist) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<REQUEST_EMAILPHONEFPTS class=\"REQUEST_EMAILPHONEFPTS\">").append("\r\n");
        sb.append("<TSFSXX class=\"TSFSXX\">").append("\r\n");
        sb.append("<COMMON_NODES class=\"COMMON_NODE;\" size=\"4\">").append("\r\n");
        //邮箱发票推送-推送方式信息 一条
        for (FaPiaoCommonNode node : sendinfo) {
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.autodetectAnnotations(true);
            String faPiaoKJXml = xStream.toXML(node);
            sb.append(faPiaoKJXml).append("\r\n");
        }
        sb.append("</COMMON_NODES>").append("\r\n");
        sb.append("</TSFSXX>").append("\r\n");
        sb.append("<FPXXS class=\"FPXX;\" size=\"").append(invoiceinfolist.size() + "\">").append("\r\n");
        //邮箱发票推送-发票信息   多条
        for (List<FaPiaoCommonNode> node : invoiceinfolist) {
            sb.append("<FPXX>").append("\r\n");
            sb.append("<COMMON_NODES class=\"COMMON_NODE;\" size=\"5\">").append("\r\n");
            for (FaPiaoCommonNode en : node) {
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
        return EncryptionDecryption.encryptContent(str);
    }

    @Override
    public String fapiaoDownLoad(FaPiaoDLoad entity) throws Exception {
        StringBuilder sb = new StringBuilder();
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.autodetectAnnotations(true);
        String faPiaoKJXml = xStream.toXML(entity);
        sb.append(faPiaoKJXml);
        String str = sb.toString();
        str = str.replace("<REQUEST_FPXXXZ_NEW>", "<REQUEST_FPXXXZ_NEW class=\"REQUEST_FPXXXZ_NEW\">");
        System.out.println(str);
        return EncryptionDecryption.encryptContent(str);
    }

    @Override
    public String fapiaoKJData(FaPiaoKJ ensale, List<FaPiaoKJXM> list, FaPiaoKJDD enbuy) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<REQUEST_FPKJXX class=\"REQUEST_FPKJXX\">").append("\r\n");

        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.autodetectAnnotations(true);
        String faPiaoKJXml = xStream.toXML(ensale);
        faPiaoKJXml = faPiaoKJXml.replaceFirst("<FPKJXX_FPTXX>", "<FPKJXX_FPTXX class=\"FPKJXX_FPTXX\">");
        sb.append(faPiaoKJXml).append("\r\n");

        sb.append("<FPKJXX_XMXXS class=\"FPKJXX_XMXX;\" size=").append("\"" + list.size() + "\">").append("\r\n");
        for (FaPiaoKJXM entity : list) {
            XStream xStream2 = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream2.autodetectAnnotations(true);
            String str = xStream2.toXML(entity);
            sb.append(str).append("\r\n");
        }
        sb.append("</FPKJXX_XMXXS>").append("\r\n");

        XStream xStream3 = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream3.autodetectAnnotations(true);
        String ddXml = xStream3.toXML(enbuy);
        ddXml = ddXml.replaceFirst("<FPKJXX_DDXX>", "<FPKJXX_DDXX class=\"FPKJXX_DDXX\">");
        sb.append(ddXml).append("\r\n");

        sb.append("</REQUEST_FPKJXX>");
        String content = sb.toString();
        return EncryptionDecryption.encryptContent(content);
    }

}