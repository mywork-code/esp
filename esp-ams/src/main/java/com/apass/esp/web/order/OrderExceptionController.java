package com.apass.esp.web.order;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.schedule.OrderInforMailSendScheduleTask;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 异常订单处理类，此类主要用作，支付宝二次退款的处理
 * 
 */
@Controller
@RequestMapping("/application/business/order/exception")
public class OrderExceptionController {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OrderExceptionController.class);

	@Autowired
	private OrderService orderService;
    @Autowired
    private TxnInfoMapper txnInfoMapper;
    @Autowired
    private OrderInforMailSendScheduleTask task;
    
	/**
     * 订单信息页面
     */
    @RequestMapping("/page")
    public ModelAndView handlePage(Map<String, Object> paramMap) {
        return new ModelAndView("order/order-exception",paramMap);
    }
    
    
    /**
     * 订单信息查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<OrderSubInfoEntity> handlePageList(HttpServletRequest request) {
        ResponsePageBody<OrderSubInfoEntity> respBody = new ResponsePageBody<OrderSubInfoEntity>();
        try {
            // 分页参数
            Page page = getPageParam(request);
            
            String refundType = HttpWebUtils.getValue(request, "refundType");
            
            Map<String,String> map = Maps.newHashMap();

            Pagination<OrderSubInfoEntity> orderList =  new Pagination<OrderSubInfoEntity>();
            		
    		if(StringUtils.equals(refundType, "1")){
    			orderList = orderService.queryOrderRefundException(map,page);
    		}else{
    			orderList = orderService.queryOrderCashRefundException(map, page);
    		}
            		
            List<OrderSubInfoEntity> dataList = orderList.getDataList();
            
            respBody.setTotal(orderList.getTotalCount());
            respBody.setRows(dataList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("异常订单列表查询失败", e);
            respBody.setMsg("异常订单列表查询失败");
        }
        return respBody;
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
    
    
    /**
	 * 更新订单物流信息、订单信息
	 */
	@ResponseBody
	@RequestMapping("/refund")
	public Response refundCash(String orderId,String refundType) {
		try {
			// 获取请求的参数
			if(StringUtils.isBlank(orderId)){
				throw new BusinessException("订单好不能为空!");
			}
			orderService.orderCashRefund(orderId,refundType, SpringSecurityUtils
          .getLoginUserDetails().getUsername());
		} catch (BusinessException e) {
			LOG.error("退款操作失败", e);
			return Response.fail(e.getErrorDesc());
		}
		return Response.success("退款操作成功！");
	}
	
	/**
     * 订单详情信息查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryOrderDetailInfo")
    public ResponsePageBody<OrderDetailInfoEntity> queryOrderDetailInfo(HttpServletRequest request) {
        ResponsePageBody<OrderDetailInfoEntity> respBody = new ResponsePageBody<OrderDetailInfoEntity>();
        try {
            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");

            // 通过商户号查收订单详情信息
            List<OrderDetailInfoEntity> orderDetailList = orderService.queryOrderDetailInfo(null,orderId);
            
            for (OrderDetailInfoEntity detail : orderDetailList) {
            	detail.setGoodsAmt(detail.getGoodsPrice().multiply(new BigDecimal(detail.getGoodsNum())));
            	//首先根据订单的Id，查询订单的信息，然后根据订单的main_order_id ,查询交易流水表
            	BigDecimal refundAmt = detail.getGoodsAmt().multiply(getScale(orderId));
            	detail.setRefundAmt(refundAmt);
			}
            
            respBody.setTotal(orderDetailList.size());
            respBody.setRows(orderDetailList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("用户列表查询失败", e);
            respBody.setMsg("用户列表查询失败");
        }
        return respBody;
    }
    
    /**
     * 计算退款比例
     * @param orderId
     * @return
     * @throws BusinessException
     */
    public BigDecimal getScale(String orderId) throws BusinessException{
    	
    	OrderInfoEntity order = orderService.selectByOrderId(orderId);
        
        List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(order.getMainOrderId());
        BigDecimal txtAmount = new BigDecimal(0);
        BigDecimal firstAmount = new BigDecimal(0);
        for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
            txtAmount = txtAmount.add(txnInfoEntity.getTxnAmt());
            if(txnInfoEntity.getTxnType().equalsIgnoreCase(TxnTypeCode.ALIPAY_SF_CODE.getCode())){
                firstAmount = txnInfoEntity.getTxnAmt();
            }
            if(StringUtils.equals(TxnTypeCode.ALIPAY_CODE.getCode(), txnInfoEntity.getTxnType())){
            	firstAmount = txnInfoEntity.getTxnAmt();
            }
        }
        return firstAmount.divide(txtAmount);
    }
    
    @ResponseBody
	@RequestMapping("/daily")
    public Response sendOrderMailEveryDay(){
    	task.sendOrderMailEveryDay();
    	return Response.success("发送成功");
    }
    
    @ResponseBody
   	@RequestMapping("/monthly")
    public Response sendOrderMailOn1stMonth(){
    	task.sendOrderMailOn1stMonth();
    	return Response.success("发送成功");
    }
}
