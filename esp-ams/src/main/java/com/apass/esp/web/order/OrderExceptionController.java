package com.apass.esp.web.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.domain.enums.PreDeliveryType;
import com.apass.esp.domain.vo.CashRefundVo;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;

@Controller
@RequestMapping("/application/business/order")
public class OrderExceptionController {

	
	/**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(OrderExceptionController.class);
    
    @Autowired
    private OrderService  orderService;
    
    /**
     * 订单信息页面
     */
    @RequestMapping("/exception/page")
    public ModelAndView handlePage(Map<String, Object> paramMap) {
    	if(SpringSecurityUtils.hasPermission("ORDER_INFO_EDIT")) {
    		paramMap.put("grantedAuthority", "permission");
		}
        return new ModelAndView("order/orderex-page",paramMap);
    }
    
    /**
     * 异常订单信息查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/exception/pagelist")
    public ResponsePageBody<OrderSubInfoEntity> handlePageList(HttpServletRequest request) {
        ResponsePageBody<OrderSubInfoEntity> respBody = new ResponsePageBody<OrderSubInfoEntity>();
        try {
            // 分页参数
            Page page = getPageParam(request);
            // 获取商户号
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
            String merchantCode = listeningCustomSecurityUserDetails.getMerchantCode();

            Pagination<OrderSubInfoEntity> orderList = new Pagination<OrderSubInfoEntity>();

            if (!StringUtils.isAnyBlank(merchantCode)) {
                orderList = orderService.queryOrderInfoRejectAgain(page);
            }
            
            List<OrderSubInfoEntity> dataList = orderList.getDataList();
            
            if(!CollectionUtils.isEmpty(dataList)){
            	for (OrderSubInfoEntity order : dataList) {
					if(order.getPreDelivery().equals(PreDeliveryType.PRE_DELIVERY_Y.getCode())){
						order.setPreDelivery(PreDeliveryType.PRE_DELIVERY_Y.getMessage());
					}else{
						order.setPreDelivery(PreDeliveryType.PRE_DELIVERY_N.getMessage());
					}
				}
            }
            respBody.setTotal(orderList.getTotalCount());
            respBody.setRows(dataList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("异常订单信息查询", e);
            respBody.setMsg("异常订单信息查询");
        }
        return respBody;
    }
    
    public ModelAndView getAfterSalesPage(Map<String, Object> paramMap) {
    	
    	if(SpringSecurityUtils.hasPermission("ORDER_INFO_EDIT")) {
    		paramMap.put("grantedAuthority", "permission");
		}
        return new ModelAndView("order/orderex-page",paramMap);
    }
    
    public List<CashRefund> getCashRefundListByOrderId(String orderId){
    	
    	List<CashRefundVo> vList = null;
    	
    	return null;
    }
    
    private Page getPageParam(HttpServletRequest request) {
        String pageNo = HttpWebUtils.getValue(request, "page");
        String pageSize = HttpWebUtils.getValue(request, "rows");
        Page page = new Page();
        if (pageNo != null && pageSize != null) {
            Integer pageNoNum = Integer.parseInt(pageNo);
            Integer pageSizeNum = Integer.parseInt(pageSize);
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);
        }

        return page;
    }
}
