package com.apass.esp.invoice;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * 发票接口相关
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/invoiceController")
public class InvoiceController {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    private InvoiceService invoiceService;
    /**
     * 发票详情
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/invoiceDetails")
    public Response invoiceDetails(Map<String, Object> paramMap) {
        try{
            String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            String orderId = CommonUtils.getValue(paramMap, "orderId");
            return invoiceService.invoiceDetails(Long.parseLong(userId),orderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail(StatusCode.FAILED_CODE.getCode(),"发票详情查询失败");
        }
    }
    /**
     * 发票记录
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/invoiceRecord")
    public Response invoiceRecord(Map<String, Object> paramMap) {
        try{
            String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            return invoiceService.invoiceRecord(Long.parseLong(userId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail(StatusCode.FAILED_CODE.getCode(),"开票记录查询失败");
        }
    }
}