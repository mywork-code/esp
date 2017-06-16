package com.apass.esp.web.order;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.aftersale.CashRefundDtoVo;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.esp.utils.BeanUtils;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.HttpWebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/application/business/cashRefund")
public class AfsQueryController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(AfsQueryController.class);

    @Autowired
    private CashRefundService cashRefundService;

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping("/getCashRefundByOrderId")
    public ResponsePageBody<CashRefundDtoVo> getCashRefundByOrderId(HttpServletRequest request) {
        ResponsePageBody<CashRefundDtoVo> responsePageBody = new ResponsePageBody();
        List<CashRefundDtoVo> list = new ArrayList();

        String orderId = HttpWebUtils.getValue(request, "orderId");
        CashRefundDto cashRefundDto = cashRefundService.getCashRefundByOrderId(orderId);
        CashRefundDtoVo cashRefundDtoVo = new CashRefundDtoVo();
        BeanUtils.copyProperties(cashRefundDtoVo, cashRefundDto);
        try {
            OrderDetailInfoDto orderDetailInfoDto = orderService.getOrderDetailInfoDto("", orderId);
            cashRefundDtoVo.setTotalNum(orderDetailInfoDto.getGoodsNumSum());
            list.add(cashRefundDtoVo);

            responsePageBody.setMsg("lkjl");
            responsePageBody.setStatus("1");
            responsePageBody.setRows(list);
            //return Response.successResponse(cashRefundDtoVo);
        } catch (Exception e) {
            responsePageBody.setMsg("lkjl");
            responsePageBody.setStatus("1");
            responsePageBody.setRows(list);
            //return Response.fail("");
        }

        return responsePageBody;
    }

}
