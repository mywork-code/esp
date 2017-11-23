package com.apass.esp.invoice;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
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
    public Response invoiceDetails(@RequestBody Map<String, Object> paramMap) {
        try{
            String orderId = CommonUtils.getValue(paramMap, "orderId");
            return invoiceService.invoiceDetails(orderId);
           // return invoiceService.invoiceDetails("346738");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("发票详情查询失败");
        }
    }
    /**
     * 发票记录
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/invoiceRecord")
    public Response invoiceRecord(@RequestBody Map<String, Object> paramMap) {
        try{
            logger.info("invoiceRecord方法开始执行了，参数哦:{}", GsonUtils.toJson(paramMap));            
            String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            return invoiceService.invoiceRecord(Long.parseLong(userId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("开票记录查询失败");
        }
    }
    /**
     * 申请中发票记录修改
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/invoiceUpdate")
    public Response invoiceUpdate(@RequestBody Map<String, Object> paramMap) {
        try{
            return invoiceService.invoiceUpdate(paramMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("发票信息修改失败");
        }
    }
}