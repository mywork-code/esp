package com.apass.esp.invoice;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Invoice;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
/**
 * 发票接口相关
 * @author Administrator
 *
 */
@Path("/invoiceController")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class InvoiceController {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    private InvoiceService invoiceService;
    /**
     * 发票记录
     * @param paramMap
     * @return
     */
    @POST
    @Path("/invoiceDetails")
    public Response invoiceDetails(Map<String, Object> paramMap) {
        try{
            String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            String orderId = CommonUtils.getValue(paramMap, "orderId");
            String id = CommonUtils.getValue(paramMap, "id");
            Invoice entity = new Invoice();
            entity.setOrderId(orderId);
            entity.setUserId(Long.parseLong(userId));
            List<Invoice> list = invoiceService.readEntityList(entity);
            return Response.success("success", list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
        }
    }
}