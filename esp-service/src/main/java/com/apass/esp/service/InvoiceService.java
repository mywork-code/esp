package com.apass.esp.service;

import com.apass.esp.domain.dto.InvoiceDto;
import com.apass.esp.domain.entity.Invoice;
import com.apass.esp.mapper.InvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by DELL on 2017/11/20.
 */
public class InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    /**
     *创建发票
     */
    @Transactional(rollbackFor = Exception.class)
    public Invoice createInvoice(InvoiceDto invoiceDto){
        Invoice in = new Invoice();
        in.setCompanyName(invoiceDto.getCompanyName());
        in.setContent(invoiceDto.getContent());
        in.setCreatedTime(new Date());
        in.setOrderAmt(invoiceDto.getOrderAmt());
        in.setTelphone(invoiceDto.getTelphone());
        in.setUserId(invoiceDto.getUserId());
        in.setUpdatedTime(new Date());
        in.setStatus((byte)1);
        in.setHeadType((byte)1);
        in.setTaxpayerNum(invoiceDto.getTaxpayerNum());
        in.setOrderId(invoiceDto.getOrderId());
        in.setSeller("上海奥派数据科技有限公司");
        invoiceMapper.insertSelective(in);
        return in;
    }

}
