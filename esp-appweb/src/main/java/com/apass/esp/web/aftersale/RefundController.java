package com.apass.esp.web.aftersale;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.aftersale.TxnInfoDto;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Controller
@RequestMapping("")
public class RefundController {

    @Autowired
    private CashRefundService cashRefundService;

    @Autowired
    private OrderService orderService;

    /**
     * 退款详情
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "v1/refund/cashRefundDetailByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public Response cashRefundDetail(@RequestBody Map<String, Object> paramMap) {
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
        resultMap.put("orderDetailInfoDto", orderDetailInfoDto);
        return Response.successResponse(resultMap);
    }

    /**
     * 撤销申请
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "v1/refund/cancelRefund", method = RequestMethod.POST)
    @ResponseBody
    public Response cancelRefund(@RequestBody Map<String, Object> paramMap) {
        String userId = CommonUtils.getValue(paramMap, "userId");
        String orderId = CommonUtils.getValue(paramMap, "orderId");
        if (StringUtils.isAnyEmpty(userId, orderId)) {
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        CashRefundDto cashRefundDto = cashRefundService.getCashRefundByOrderId(orderId);
        //1:退款提交，2等待商家审核 才能进行撤销
        if (cashRefundDto == null || cashRefundDto.getStatus() != 1 || cashRefundDto.getStatus() != 2) {
            return Response.fail(BusinessErrorCode.NO);
        }
        cashRefundDto.setStatus(4);
        cashRefundService.update(cashRefundDto);
        return Response.successResponse();
    }
}
