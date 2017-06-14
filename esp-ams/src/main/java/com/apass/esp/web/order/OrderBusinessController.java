package com.apass.esp.web.order;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.CashRefundVo;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 
 * @description 订单相关请求处理
 *
 * @author chenbo
 * @version $Id: OrderQueryController.java, v 0.1 2016年12月22日 上午11:15:57 chenbo
 *          Exp $
 */
@Controller
@RequestMapping("/application/business/order")
public class OrderBusinessController {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OrderBusinessController.class);

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CashRefundService cashRefundService;

	/**
	 * 更新订单物流信息、订单信息
	 */
	@ResponseBody
	@RequestMapping("/updateLogisticsInfoAndOrderInfoByOrderId")
	public Response updateLogisticsDetail(HttpServletRequest request) {
		try {
			// 获取请求的参数
			String orderId = HttpWebUtils.getValue(request, "orderId");
			String logisticsName = HttpWebUtils.getValue(request, "logisticsName");
			String logisticsNo = HttpWebUtils.getValue(request, "logisticsNo");

			// 拼接参数
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderId", orderId);
			map.put("logisticsName", logisticsName);
			map.put("logisticsNo", logisticsNo);

			// 参数验证
			paramCheck(map);

			// 更新物流信息、订单信息
			orderService.updateLogisticsInfoAndOrderInfoByOrderId(map);
		} catch (BusinessException e) {
			LOG.error("物流单号重复或输入错误", e);
			return Response.fail(e.getErrorDesc());
		}
		return Response.success("物流信息添加成功");
	}

	public void paramCheck(Map<String, String> map) throws BusinessException {
		if (StringUtils.isAnyBlank(map.get("orderId"))) {
			throw new BusinessException("订单号不能为空!");
		}

		if (StringUtils.isAnyBlank(map.get("logisticsName"))) {
			throw new BusinessException("物流厂商不能为空!");
		}

		if (StringUtils.isAnyBlank(map.get("logisticsNo"))) {
			throw new BusinessException("物流单号不能为空!");
		}
	}
}
