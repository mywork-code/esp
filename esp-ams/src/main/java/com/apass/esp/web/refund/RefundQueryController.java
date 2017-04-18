package com.apass.esp.web.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;

/**
 * 
 * @description 退货详细查询
 *
 * @author chenbo
 * @version $Id: OrderQueryController.java, v 0.1 2016年12月23日 上午11:15:57 chenbo
 *          Exp $
 */
@Controller
@RequestMapping("/application/business/refund")
public class RefundQueryController {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RefundQueryController.class);

	@Autowired
	private OrderRefundService orderRefundService;

	/**
	 * 退货信息查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pagelist")
	public ResponsePageBody<RefundInfoEntity> handlePageList(HttpServletRequest request) {
		ResponsePageBody<RefundInfoEntity> respBody = new ResponsePageBody<RefundInfoEntity>();
		try {
			// 获取请求的参数
			String orderId = HttpWebUtils.getValue(request, "orderId");

			// 通过订单号查询退货信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			List<RefundInfoEntity> orderList = orderRefundService.queryRefundByOrderId(map);
			respBody.setRows(orderList);
			respBody.setStatus(CommonCode.SUCCESS_CODE);
		} catch (Exception e) {
			LOG.error("退货信息查询失败", e);
			respBody.setMsg("退货信息查询失败");
		}
		return respBody;
	}
}
