package com.apass.esp.nothing;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.service.payment.PaymentService;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
/**
 * 支付BSS回调
 * @description 
 *
 * @author liuming
 * @version $Id: PayCallback.java, v 0.1 2017年3月12日 下午3:00:22 liuming Exp $
 */
@Path("/payment")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PayCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayCallback.class);

    @Autowired
    private PaymentService paymentService;
    
    /**
     * BSS支付成功或失败回调
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/callback")
    public Response callback(Map<String, Object> paramMap) {
        String logStashSign = LogStashKey.PAY_CALLBACK.getValue();
        String methodDesc = LogStashKey.PAY_CALLBACK.getName();

        try {
            String status = CommonUtils.getValue(paramMap, "status"); //支付状态[成功 失败]
            String orderId = CommonUtils.getValue(paramMap, "orderId"); //订单号

            String requestId = logStashSign + "_" + orderId;
            LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
            
            if (StringUtils.isAnyEmpty(status, orderId)) {
                return Response.fail("请选择支付方式!");
            }
            paymentService.callback(requestId ,orderId,status);

        } catch (Exception e) {
            LOGGER.error("订单支付失败", e);
            return Response.fail("订单支付失败!请重新支付");
        }
        return Response.success("支付成功");
    }
}
