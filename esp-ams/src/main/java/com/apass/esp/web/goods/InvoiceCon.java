package com.apass.esp.web.goods;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.invoice.InvoiceService;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * 发票接口相关
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceCon {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceCon.class);
    @Autowired
    private InvoiceService invoiceService;
    /**
     * 申请中发票开具
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/invoiceIssue")
    public Response invoiceIssue(@RequestBody Map<String, Object> paramMap) {
        try{
            String orderId = CommonUtils.getValue(paramMap, "orderId");
            OrderInfoEntity order = new OrderInfoEntity();
            order.setOrderId(orderId);
            if(invoiceService.invoiceCheck(order)){
                return Response.success("发票信息开具成功,支持下载!");
            }else{
                return Response.fail("发票信息开具失败,不支持下载!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("发票信息开具异常");
        }
    }
}