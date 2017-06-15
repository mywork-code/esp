package com.apass.esp.web.aftersale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.aftersale.TxnInfoDto;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Path("/v1/refund")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RefundController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CashRefundService.class);

    @Autowired
    private CashRefundService cashRefundService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommonHttpClient commonHttpClient;

    /**
     * 退款详情
     *
     * @param paramMap
     * @return
     */
    @POST
    @Path("/cashRefundDetailByOrderId")
    public Response cashRefundDetail(Map<String, Object> paramMap) {
        String userId = CommonUtils.getValue(paramMap, "userId");
        String orderId = CommonUtils.getValue(paramMap, "orderId");
        if (StringUtils.isAnyEmpty(userId, orderId)) {
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        CashRefundDto cashRefundDto = cashRefundService.getCashRefundByOrderId(orderId);

        if (cashRefundDto == null) {
            return Response.fail(BusinessErrorCode.NO);
        }
        Map<String, Object> resultMap = new HashMap<>();
        OrderDetailInfoDto orderDetailInfoDto = null;
        List<TxnInfoDto> txnInfoDtoList = new ArrayList<>();
        try {
            txnInfoDtoList = cashRefundService.getTxnInfoByOrderId(cashRefundDto.getOrderId());
            orderDetailInfoDto = orderService.getOrderDetailInfoDto("", cashRefundDto.getOrderId());
        } catch (BusinessException e) {
            return Response.fail(BusinessErrorCode.NO);
        }
        resultMap.put("txnInfoDtoList", txnInfoDtoList);
        resultMap.put("cashRefundDto", cashRefundDto);
        resultMap.put("GoodsInfoInOrderList", orderDetailInfoDto.getOrderDetailInfoList());
        return Response.successResponse(resultMap);
    }

    /**
     * 撤销申请
     *
     * @param paramMap
     * @return
     */
    @POST
    @Path("/cancelRefund")
    public Response cancelRefund(Map<String, Object> paramMap) {
        String userId = CommonUtils.getValue(paramMap, "userId");
        String orderId = CommonUtils.getValue(paramMap, "orderId");
        if (StringUtils.isAnyEmpty(userId, orderId)) {
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        CashRefundDto cashRefundDto = cashRefundService.getCashRefundByOrderId(orderId);
        //1:退款提交，2等待商家审核 才能进行撤销
        if (cashRefundDto == null || cashRefundDto.getStatus() != 1) {
            return Response.fail(BusinessErrorCode.NO);
        }
        cashRefundDto.setStatus(4);
        cashRefundService.update(cashRefundDto);
        return Response.successResponse();
    }
    
    /**
     * 退款申请
     * @param paramMap
     * @return
     */
    @POST
    @Path("/requestRefund")
    public Response requestRefund(Map<String, Object> paramMap) {
        try {
        	String logStashSign = LogStashKey.ORDER_REQUEST_REFUND.getValue();
            String methodDesc = LogStashKey.ORDER_REQUEST_REFUND.getName();
            
            String orderId = CommonUtils.getValue(paramMap, "orderId");//订单Id
            String userId = CommonUtils.getValue(paramMap, "userId");//用户id
            String reason=CommonUtils.getValue(paramMap, "reason");//退款原因
            String memo=CommonUtils.getValue(paramMap, "memo");//退款说明
            
            String requestId = logStashSign + "_" + orderId;
            LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
            
            if (StringUtils.isBlank(orderId)) {
                LOGGER.error("订单号不能为空!");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            if (StringUtils.isBlank(userId)) {
                LOGGER.error("用户号不能为空!");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            if (StringUtils.isBlank(reason)) {
                LOGGER.error("退款原因不能为空!");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
           
            cashRefundService.requestRefund(requestId,orderId,userId, reason,memo);

        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
        } catch (Exception e) {
            LOGGER.error("退款申请失败", e);
            return Response.fail(BusinessErrorCode.ORDER_REQUEST_REFUND);
        }
        return Response.success("退款申请成功");
    }
    /**
     * 获取退款申请信息
     * @param paramMap
     * @return
     */
    @POST
    @Path("/getRequestRefund")
    public Response getRequestRefundInfo(Map<String, Object> paramMap) {
        try {
        	String logStashSign = LogStashKey.ORDER_GET_REQUEST_REFUND.getValue();
            String methodDesc = LogStashKey.ORDER_GET_REQUEST_REFUND.getName();
            
            String orderId = CommonUtils.getValue(paramMap, "orderId");//订单Id
            String userId = CommonUtils.getValue(paramMap, "userId");//用户id
            
            String requestId = logStashSign + "_" + orderId;
            LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
            
            if (StringUtils.isBlank(orderId)) {
                LOGGER.error("订单号不能为空!");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            if (StringUtils.isBlank(userId)) {
                LOGGER.error("用户号不能为空!");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
           
            CashRefund cashRefund= cashRefundService.getRequestRefundInfo(requestId,orderId,userId);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("reason", cashRefund.getReason());
            resultMap.put("memo", cashRefund.getMemo());
            resultMap.put("amt", cashRefund.getAmt());
            return Response.successResponse(resultMap);
        } catch (Exception e) {
            LOGGER.error("查询退款申请信息失败", e);
            return Response.fail(BusinessErrorCode.ORDER_GET_REQUEST_REFUND);
        }
    }
    /**
     * 同意退款
     *
     * @param paramMap
     * @return
     */
    @POST
    @Path("/agreeRefund")
    public Response agreeRefund(Map<String, Object> paramMap) {
        String userId = CommonUtils.getValue(paramMap, "userId");
        String orderId = CommonUtils.getValue(paramMap, "orderId");
        if (StringUtils.isAnyEmpty(userId, orderId)) {
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        CashRefundDto cashRefundDto = cashRefundService.getCashRefundByOrderId(orderId);
        //1:退款提交 才能进行同意
        if (cashRefundDto == null || cashRefundDto.getStatus() != 1) {
            return Response.fail(BusinessErrorCode.NO);
        }
        Response res = commonHttpClient.updateAvailableAmount("", Long.valueOf(userId), String.valueOf(cashRefundDto.getAmt()));
        if (!res.statusResult()) {
            return Response.fail(BusinessErrorCode.NO);
        }
        cashRefundDto.setStatus(2);
        cashRefundService.update(cashRefundDto);
        return Response.successResponse();
    }
}
