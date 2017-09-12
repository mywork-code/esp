package com.apass.esp.web.refund;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 
 * @description 售后业务处理类
 *
 * @author chenbo
 * @version $Id: OrderQueryController.java, v 0.1 2017年1月4日 上午11:15:57 chenbo
 *          Exp $
 */
@Controller
@RequestMapping("/application/business/refund")
public class RefundBusinessController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(RefundBusinessController.class);

    @Autowired
    private OrderRefundService  orderRefundService;

    /**
     * 同意客户售后申请
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/agreeRefundApplyByOrderId")
    @LogAnnotion(operationType = "同意售后", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response agreeRefundApplyByOrderId(HttpServletRequest request) {
        try {
            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");
            String refundId = HttpWebUtils.getValue(request, "refundId");

            // 用户信息
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
//            String userId = listeningCustomSecurityUserDetails.getUserId();
            String username = listeningCustomSecurityUserDetails.getUsername();
            // 通过订单号更新售后状态
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            map.put("refundId", refundId);
            map.put("approvalUser", username);
            map.put("approvalComments", "同意");
            // 参数验证
            paramCheck(map);

            orderRefundService.agreeRefundApplyByOrderId(map);
        } catch (BusinessException e) {
            LOG.error("售后申请已未同意", e);
            return Response.fail(e.getErrorDesc());
        }
        return Response.success("售后申请已同意");
    }

    /**
     * 售后驳回处理
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/rejectRequestByOrderId")
    @LogAnnotion(operationType = "驳回售后", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response rejectByOrderId(HttpServletRequest request) {
        try {
            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");
            String refundId = HttpWebUtils.getValue(request, "refundId");
            String rejectReason = HttpWebUtils.getValue(request, "rejectReason");

            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
            String userId = listeningCustomSecurityUserDetails.getUserId();

            // 通过订单号更新售后状态
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            map.put("refundId", refundId);
            map.put("rejectReason", rejectReason);
            map.put("approvalUser", userId);
            map.put("approvalComments", "驳回");

            paramCheck(map);

            if (StringUtils.isAnyBlank(map.get("rejectReason"))) {
                throw new BusinessException("请填写驳回原因!");
            }
            orderRefundService.rejectRequestByOrderId(map);
        } catch (BusinessException e) {
            LOG.error("售后驳回失敗！！", e);
            return Response.fail(e.getErrorDesc());
        }
        return Response.success("驳回成功！！");
    }

    /**
     * 确认收货
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmReceiptByOrderId")
    @LogAnnotion(operationType = "商家确认收货", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response confirmReceiptByOrderId(HttpServletRequest request) {
        try {
            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");
            String refundId = HttpWebUtils.getValue(request, "refundId");
            String refundType = HttpWebUtils.getValue(request, "refundType");
            if (StringUtils.isAnyBlank(refundId)) {
                throw new BusinessException("售后id不能为空!");
            }

            // 通过订单号更新售后状态
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            map.put("refundId", refundId);
            map.put("refundType", refundType);
            // 参数验证
            paramCheck(map);
            orderRefundService.confirmReceiptByOrderId(map);
        } catch (BusinessException e) {
            LOG.error("确认收货失敗！！", e);
            return Response.fail(e.getErrorDesc());
        }
        return Response.success("确认收货成功！！");
    }

    /**
     * 重新发货
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendGoodsAgain")
    @LogAnnotion(operationType = "商家重新发货", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response sendGoodsAgain(HttpServletRequest request) {
        try {
            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");
            // 物流厂商
            String rlogisticsName = HttpWebUtils.getValue(request, "rlogisticsName");
            // 物流单号
            String rlogisticsNo = HttpWebUtils.getValue(request, "rlogisticsNo");
            //售后id
            String refundId = HttpWebUtils.getValue(request, "refundId");

            if (StringUtils.isAnyBlank(refundId)) {
                throw new BusinessException("售后id不能为空!");
            }

            // 通过订单号更新重新发货的物流信息
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            map.put("refundId", refundId);
            map.put("rlogisticsName", rlogisticsName);
            map.put("rlogisticsNo", rlogisticsNo);
            // 参数验证
            paramCheck(map);
            orderRefundService.sendGoodsAgain(map);
        } catch (BusinessException e) {
            LOG.error("重新发货失敗！！", e);
            return Response.fail(e.getErrorDesc());
        }
        return Response.success("重新发货成功！！");
    }

    /**
     * 确认退款通过订单号
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmRefundByOrderId")
    @LogAnnotion(operationType = "商家确认退款", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response confirmRefundByOrderId(HttpServletRequest request) {
        try {
            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");
            String refundId = HttpWebUtils.getValue(request, "refundId");

            if (StringUtils.isAnyBlank(refundId)) {
                throw new BusinessException("售后id不能为空!");
            }

            // 通过订单号更新重新发货的物流信息
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            map.put("refundId", refundId);

            // 参数验证
            paramCheck(map);
            orderRefundService.confirmRefundByOrderId(map);
        } catch (BusinessException e) {
            LOG.error("确认收货失敗！！", e);
            return Response.fail(e.getErrorDesc());
        }
        return Response.success("退款成功！！");
    }

    public void paramCheck(Map<String, String> map) throws BusinessException {
        if (StringUtils.isAnyBlank(map.get("orderId"))) {
            throw new BusinessException("订单号不能为空!");
        }
    }
}
