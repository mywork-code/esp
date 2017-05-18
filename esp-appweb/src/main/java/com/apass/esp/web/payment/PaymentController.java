package com.apass.esp.web.payment;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.payment.PayInfoEntity;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.domain.enums.PaymentType;
import com.apass.esp.service.payment.PaymentService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  支付
 * @description 
 *
 * @author liuming
 * @version $Id: PaymentController.java, v 0.1 2017年3月2日 下午6:07:19 liuming Exp $
 */
@Path("/payment")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    /**
     * 支付方式初始化
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/initPaymentMethod")
    public Response initPaymentMethod(Map<String, Object> paramMap) {
        Map<String,Object> resultMap = Maps.newHashMap();
        String logStashSign = LogStashKey.PAY_INIT_METHOD.getValue();
        String methodDesc = LogStashKey.PAY_INIT_METHOD.getName();
        try {

            String userIdStr = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);      //用户号
            String orderListStr = CommonUtils.getValue(paramMap, "orderList");      //  订单订单列表
            
            String requestId = logStashSign + "_" + userIdStr;
            LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
            
            if (!StringUtils.isNumeric(userIdStr)) {
                return Response.fail("用户名传入非法!");
            }
            Long userId = Long.valueOf(userIdStr);
            List<String> orderList = GsonUtils.convertList(orderListStr,  new TypeToken<List<String>>(){});
            if (orderList.isEmpty()) {
                return Response.fail("请选择要支付的订单!");
            }
            resultMap = paymentService.initPaymentMethod(requestId,userId, orderList);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOGGER.error("订单支付失败", e);
            return Response.fail("订单支付失败!请重新支付");
        }
        return Response.success("支付成功",resultMap);
    }
    
    /**
     * 支付方式选择确认
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/confirmPayMethod")
    public Response confirmPayMethod(Map<String, Object> paramMap) {
        Map<String,Object> resultMap = Maps.newHashMap();
        try {
            String logStashSign = LogStashKey.PAY_CONFIRM_METHOD.getValue();
            String methodDesc = LogStashKey.PAY_CONFIRM_METHOD.getName();
            
            String userIdStr = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);      //  用户号
            String orderListStr = CommonUtils.getValue(paramMap, "orderList");      //  订单订单列表
            String paymentType = CommonUtils.getValue(paramMap, "paymentType");         //  选择支付方式
            
            String requestId = logStashSign + "_" + userIdStr;
            LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
            
            if (!StringUtils.isNumeric(userIdStr)) {
                return Response.fail("用户名传入非法!");
            }
            Long userId = Long.valueOf(userIdStr);
            if (StringUtils.isEmpty(paymentType)) {
                return Response.fail("请选择支付方式!");
            }
            if (!PaymentType.CARD_PAYMENT.getCode().equals(paymentType) && !PaymentType.CREDIT_PAYMENT.getCode().equals(paymentType)) {
                return Response.fail("请选择正确支付方式!");
            }
            List<String> orderList = GsonUtils.convertList(orderListStr,  new TypeToken<List<String>>(){});
            if (orderList.isEmpty()) {
                return Response.fail("请选择要支付的订单!");
            }
            PayInfoEntity payInfo = paymentService.confirmPayMethod(requestId , userId, orderList,paymentType);
            resultMap.put("resultMap", payInfo);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOGGER.error("订单支付失败", e);
            return Response.fail("订单支付失败!请重新支付");
        }
        return Response.success("支付成功",resultMap);
    }
    
    /**
     * 确认付款
     *
     * T05 银行卡支付，T02 额度支付(需要cardNo)
     * @param paramMap
     * @return
     */
    @POST
    @Path("/defary")
    public Response defary(Map<String, Object> paramMap) {
        String payPage = ""; 
        String logStashSign = LogStashKey.PAY_DEFARY.getValue();
        String methodDesc = LogStashKey.PAY_DEFARY.getName();
        try {
            String userIdStr = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            String paymentType = CommonUtils.getValue(paramMap, "paymentType");     //  选择支付方式
            String orderListStr = CommonUtils.getValue(paramMap, "orderList");      //  订单订单列表
            String cardNo = CommonUtils.getValue(paramMap, "cardNo");      //  银行卡号
            
            String requestId = logStashSign + "_" + userIdStr;
            LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
            
            if (!StringUtils.isNumeric(userIdStr)) {
                return Response.fail("用户名传入非法!");
            }
            Long userId = Long.valueOf(userIdStr);
            if (StringUtils.isEmpty(paymentType)) {
                return Response.fail("请选择支付方式!");
            }
            if(PaymentType.CREDIT_PAYMENT.getCode().equals(paymentType)){
                if (StringUtils.isEmpty(cardNo)) {
                    return Response.fail("请填写银行卡号!");
                }
            }
            List<String> orderList = GsonUtils.convertList(orderListStr,  new TypeToken<List<String>>(){});
            if (orderList.isEmpty()) {
                return Response.fail("请选择要支付的订单!");
            }
            payPage = paymentService.defary(requestId,userId, orderList, paymentType,cardNo);
            
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOGGER.error("订单支付失败", e);
            return Response.fail("订单支付失败!请重新支付");
        }
        return Response.success("支付页获取成功", payPage);
    }
    
	/**
	 * 支付状态查询
	 * 
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/queryPayStatus")
	public Response queryPayStatus(Map<String, Object> paramMap) {
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			String orderList = CommonUtils.getValue(paramMap, "orderList"); // 订单ID
			if (StringUtils.isEmpty(orderList)) {
				return Response.fail("订单ID不能为空!");
			}
			String orderArray[]=orderList.split(",");
			String response = paymentService.queryPayStatus(orderArray);
			resMap.put("pstatus", response);
		} catch (Exception e) {
			LOGGER.error("交易状态查询", e);
			return Response.fail("交易状态查询");
		}
		return Response.success("交易状态查询", resMap);
	}
	
	/**
	 * 确认离开支付页  未支付尝试回滚库存
	 * 
	 * @param paramMap
	 * @return
	 */
    @POST
    @Path("/leavePayRollStock")
    public Response leavePayRollStock(Map<String, Object> paramMap) {
        Map<String, String> resMap = new HashMap<String, String>();
        String logStashSign = LogStashKey.PAY_LEAVE_ROLLSTOCK.getValue();
        String methodDesc = LogStashKey.PAY_LEAVE_ROLLSTOCK.getName();
        try {
            String orderList = CommonUtils.getValue(paramMap, "orderList"); // 订单ID
            String userIdStr = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            if (StringUtils.isEmpty(orderList)) {
                return Response.fail("订单ID不能为空!");
            }
            String requestId = logStashSign + "_" + userIdStr;
            LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
            
            List<String> orders = GsonUtils.convertList(orderList,  new TypeToken<List<String>>(){});
            
            paymentService.leavePayRollStock(requestId,orders);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        }  catch (Exception e) {
            LOGGER.error("返回库存处理失败", e);
            return Response.fail("返回库存处理失败");
        }
        return Response.success("返回库存处理成功", resMap);
    }
}
