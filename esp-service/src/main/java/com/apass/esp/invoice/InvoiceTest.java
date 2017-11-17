package com.apass.esp.invoice;
import java.util.ArrayList;
import java.util.List;

import com.apass.esp.invoice.model.*;
import org.apache.commons.lang.StringUtils;

public class InvoiceTest {
    public static void main(String[] args) throws Exception {
        GlobalInfoEctype globalInfo = new GlobalInfoEctype();
        globalInfo.setTerminalCode("0");
        globalInfo.setAppId("DZFP");
        globalInfo.setVersion("1.0");
        globalInfo.setInterfaceCode("ECXML.FPXZ.CX.E_INV");//开具发票
        globalInfo.setRequestCode("111MFWIK");
        globalInfo.setRequestTime("2016-11-28 10:19:16");
        globalInfo.setResponseCode("121");
        globalInfo.setDataExchangeId("111MFWIKECXML.FPXZ.CX.E_INV20161128eXl4EymmJ");
        globalInfo.setUserName("111MFWIK");
        globalInfo.setPassWord("6051435131bDo4Vs6uBMpJfjwVUdCiSwyy");
        globalInfo.setTaxpayerId("310101000000090");
        globalInfo.setAuthorizationCode("3100000090");

        ReturnStateInfo stateInfo = new ReturnStateInfo();
        stateInfo.setReturnCode(StringUtils.EMPTY);
        stateInfo.setReturnMessage(StringUtils.EMPTY);

        FaPiaoKJ faPiaoKJ = new FaPiaoKJ();
        faPiaoKJ.setFpqqlsh("d2222222222222221234");
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

        List<FaPiaoKJXM> list = new ArrayList<FaPiaoKJXM>();
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
        list.add(faPiaoKJXM);

//        FaPiaoKJDD faPiaoKJDD = new FaPiaoKJDD();
//        faPiaoKJDD.setDdh("2492684718573093");
//        faPiaoKJDD.setThdh("2492684718573093");
//        faPiaoKJDD.setDddate("2016-10-31 10:47:17");
//        InvoiceIssueService service = new InvoiceIssueService();
//        String s = service.requestFaPiaoKJ(globalInfo,stateInfo,faPiaoKJ,list,faPiaoKJDD);
//        System.out.println(s);
//        System.out.println(service.getFaPiaoReturnState(s).getReturnMessage());

        FaPiaoDLoad   faPiaoDLoad = new FaPiaoDLoad();
        faPiaoDLoad.setDdh("2492684718512345");
        faPiaoDLoad.setDsptbm("111MFWIK");
        faPiaoDLoad.setFpqqlsh("d22222222222222255667");
        faPiaoDLoad.setNsrsbh("310101000000090");
        faPiaoDLoad.setPdfXzfs("3");
        InvoiceIssueService service = new InvoiceIssueService();
       String s = service.requestFaPiaoDL(globalInfo,stateInfo,faPiaoDLoad);
        System.out.println(s);
        System.out.println(service.getFaPiaoReturnState(s).getReturnMessage());
    }
}