package com.apass.esp.invoice;

import com.apass.esp.invoice.model.*;

import java.util.List;

/**
 * Created by DELL on 2017/11/22.
 */
public interface InvoiceHandler {
    /**
     * 3.3 请求邮箱发票推送
     * @return
     * @throws Exception
     */
    public abstract String fapiaoEmailSend(List<FaPiaoCommonNode> sendinfo, List<List<FaPiaoCommonNode>> invoiceinfolist) throws Exception;
    /**
     * 3.2 请求发票下载
     * @return
     * @throws Exception
     */
    public abstract String fapiaoDownLoad(FaPiaoDLoad entity) throws Exception;
    /**
     * 3.1 请求发票开具
     * @return
     * @throws Exception
     */
    public abstract String fapiaoKJData(FaPiaoKJ ensale, List<FaPiaoKJXM> list, FaPiaoKJDD enbuy) throws Exception;
}
