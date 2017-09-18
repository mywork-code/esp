package com.apass.esp.web.order;

import java.util.ArrayList;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.logistics.JdTrack;
import com.apass.esp.domain.dto.logistics.LogisticsResponseDto;
import com.apass.esp.domain.entity.datadic.DataDicInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.PreDeliveryType;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.service.datadic.DataDicService;
import com.apass.esp.service.jd.JdLogisticsService;
import com.apass.esp.service.logistics.LogisticsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 
 * @description 订单查询
 *
 * @author chenbo
 * @version $Id: OrderQueryController.java, v 0.1 2016年12月20日 上午11:15:57 chenbo
 *          Exp $
 */
@Controller
@RequestMapping("/application/business/order")
public class OrderQueryController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(OrderQueryController.class);

    @Autowired
    private OrderService        orderService;
    @Autowired
    private LogisticsService    logisticsService;
    @Autowired
    private DataDicService      dataDicService;
    @Autowired
    private JdLogisticsService jdLogisticsService;

    /**
     * 订单信息页面
     */
    @RequestMapping("/page")
    public ModelAndView handlePage(Map<String, Object> paramMap) {
    	if(SpringSecurityUtils.hasPermission("ORDER_INFO_EDIT")) {
    		paramMap.put("grantedAuthority", "permission");
		}
        return new ModelAndView("order/order-page",paramMap);
    }

    /**
     * 订单信息页面
     */
    @RequestMapping("/pageAll")
    public ModelAndView handlePageAll(Map<String, Object> paramMap) {
    	if(SpringSecurityUtils.hasPermission("ORDER_ALLINFO_EDIT")) {
    		paramMap.put("grantedAuthority", "permission");
		}
        return  new ModelAndView("order/order-pageAll",paramMap);
    }

    /**
     * 订单详情信息页面
     */
    @RequestMapping("/orderDetailPage")
    public String orderDetailPage(HttpServletRequest request, ModelMap model) {
        String orderId = HttpWebUtils.getValue(request, "orderId");
        model.put("orderId", orderId);
        return "order/order-detail";
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

            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");
            String orderStatus = HttpWebUtils.getValue(request, "orderStatus");
            String refundType = HttpWebUtils.getValue(request, "refundType");
            String preDelivery = HttpWebUtils.getValue(request, "preDelivery");
            String createDate = HttpWebUtils.getValue(request, "createDate");
            String telephone = HttpWebUtils.getValue(request, "telephone");
            String name = HttpWebUtils.getValue(request, "name");

            // 获取商户号
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
            String merchantCode = listeningCustomSecurityUserDetails.getMerchantCode();

            // 通过商户号查收订单详情信息
            if(StringUtils.isBlank(orderStatus)){
            	orderStatus = "'D03','D04','D05'";
            }else{
           	    orderStatus = "'"+orderStatus+"'";
            }
            
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            map.put("orderStatus", orderStatus);
            map.put("refundType", refundType);
            map.put("preDelivery", preDelivery);
            map.put("merchantCode", merchantCode);
            map.put("createDate", createDate);
            map.put("telephone", telephone);
            map.put("name", name);

            Pagination<OrderSubInfoEntity> orderList = new Pagination<OrderSubInfoEntity>();

            if (!StringUtils.isAnyBlank(merchantCode)) {
                orderList = orderService.queryOrderSubDetailInfoByParam(map, page);
            }
            
            List<OrderSubInfoEntity> dataList = orderList.getDataList();
            
            respBody.setTotal(orderList.getTotalCount());
            respBody.setRows(dataList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("用户列表查询失败", e);
            respBody.setMsg("用户列表查询失败");
        }
        return respBody;
    }
    
    /**
     *物流信息查询
     */
    @ResponseBody
    @RequestMapping("/v1/jd/pagelistLogistics")
    public ResponsePageBody<JdTrack> Logistics(HttpServletRequest request) {
    	ResponsePageBody<JdTrack> respBody = new ResponsePageBody<JdTrack>();
    	try {
	    	String orderId = HttpWebUtils.getValue(request, "orderId");
	        if(StringUtils.isBlank(orderId)){
	        	throw new BusinessException("订单号不能为空!");
	        }
	        OrderInfoEntity entity = orderService.getOrderInfoEntityByOrderId(orderId);
	        if(null == entity){
	        	throw new BusinessException("订单号为【"+orderId+"】的订单不存在!");
	        }
	        if(!StringUtils.equals(entity.getSource(), SourceType.JD.getCode())){
	        	throw new BusinessException("订单来源不明确!");
	        }
        	//调用  京东的接口
	        if(StringUtils.isBlank(entity.getExtOrderId())){
	        	throw new BusinessException("订单中京东订单号为空!");
	        }
	        try {
	        	//List<JdTrack> trackList = jdLogisticsService.getSignleTrackingsByOrderId(entity.getExtOrderId());
	        	respBody.setMsg("物流信息查询成功！");
                //respBody.setRows(trackList);
                respBody.setStatus(CommonCode.SUCCESS_CODE);
			} catch (Exception e) {
				 LOG.error("物流信息查询失败", e);
				 LOG.error("handlePageList->logisticsNo:{}物流信息查询失败");
				 respBody.setMsg("物流信息查询失败！");
			}
    	} catch (Exception e) {
    		LOG.error("物流信息查询失败！", e);
            respBody.setMsg("物流信息查询失败！");
		}
    	
    	return respBody;
    }
    /**
     *物流信息查询
     */
    @ResponseBody
    @RequestMapping("/pagelistLogistics")
    public ResponsePageBody<OrderSubInfoEntity> pagelistLogistics(HttpServletRequest request) {
        ResponsePageBody<OrderSubInfoEntity> respBody = new ResponsePageBody<OrderSubInfoEntity>();
        OrderSubInfoEntity orderSubInfoEntity = new OrderSubInfoEntity();
        List<OrderSubInfoEntity> list = new ArrayList<>();
        try{
            String orderId = HttpWebUtils.getValue(request, "orderId");
            String logisticsName = HttpWebUtils.getValue(request, "logisticsName");
            String logisticsNo = HttpWebUtils.getValue(request, "logisticsNo");
            
            if(StringUtils.isNotBlank(logisticsNo)){
                orderSubInfoEntity.setLogisticsNo(logisticsNo);
            }
            //物流厂商联系电话和物流公司翻译
            Map<String, String> map2 = new HashMap<String, String>();
            map2.put("dataTypeNo", "100003");
            map2.put("dataNo", logisticsName);
            List<DataDicInfoEntity> dataDicByparam = dataDicService.getDataDicByparam(map2);
            for (DataDicInfoEntity dataDicInfoEntity : dataDicByparam) {
                orderSubInfoEntity.setLogisticsName(dataDicInfoEntity.getDataNo());
                orderSubInfoEntity.setLogisticsNameDes(dataDicInfoEntity.getDataName());
                orderSubInfoEntity.setLogisticsTel(dataDicInfoEntity.getRemark());
                list.add(orderSubInfoEntity);
            }
            
            boolean logisticsFlag = true;//物流查询是否成功标志
            //查询物流状态
            try {
                if (!StringUtils.isAnyBlank(logisticsNo, logisticsName, orderId)) {
                    Map<String, Object> signleTrackingsMap = logisticsService.getSignleTrackings(logisticsName,
                        logisticsNo, orderId);
                    LogisticsResponseDto logisticsResponseDto = (LogisticsResponseDto) signleTrackingsMap
                        .get("logisticInfo");
                    orderSubInfoEntity.setLogisticsStatus(logisticsResponseDto.getState());
                    String signTime = String.valueOf(signleTrackingsMap.get("signTime"));
                    orderSubInfoEntity.setSignTime(StringUtils.isBlank(signTime)?"":signTime);
                    logisticsFlag = logisticsResponseDto.isSuccess();
                }
            } catch (BusinessException e) {
                LOG.error("物流信息查询失败", e);
                LOG.error("handlePageList->logisticsNo:{}物流信息查询失败", logisticsNo);
            }
            
            if(logisticsFlag){
                respBody.setMsg("物流信息查询成功！");
                respBody.setRows(list);
                respBody.setStatus(CommonCode.SUCCESS_CODE);
            }else{
                respBody.setMsg("暂未查到物流信息！");
            }
            
        }catch(Exception e){
            LOG.error("物流信息查询失败！", e);
            respBody.setMsg("物流信息查询失败！");
        }
        
        return respBody;
    }
    

    /**
     * 订单信息查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/pagelistAll")
    public ResponsePageBody<OrderSubInfoEntity> handlePageListAll(HttpServletRequest request) {
        ResponsePageBody<OrderSubInfoEntity> respBody = new ResponsePageBody<OrderSubInfoEntity>();
        try {
            // 分页参数
            Page page = getPageParam(request);

            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");
            String orderStatus = HttpWebUtils.getValue(request, "orderStatus");
            String refundType = HttpWebUtils.getValue(request, "refundType");
            String preDelivery = HttpWebUtils.getValue(request, "preDelivery");
            String createDate = HttpWebUtils.getValue(request, "createDate");
            String telephone = HttpWebUtils.getValue(request, "telephone");
            String name = HttpWebUtils.getValue(request, "name");

            // 通过商户号查收订单详情信息
            if(StringUtils.isBlank(orderStatus)){
            	orderStatus = getOrderAllStatus();
            }else{
            	orderStatus = "'"+orderStatus+"'";
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            map.put("orderStatus", orderStatus);
            map.put("refundType", refundType);
            map.put("preDelivery", preDelivery);
            map.put("createDate", createDate);
            map.put("telephone", telephone);
            map.put("name", name);
            Pagination<OrderSubInfoEntity> orderList = orderService.queryOrderSubDetailInfoByParam(map, page);

            respBody.setTotal(orderList.getTotalCount());
            respBody.setRows(orderList.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("用户列表查询失败", e);
            respBody.setMsg("用户列表查询失败");
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
     * 获取订单的所有的状态
     * @return
     */
    public static String getOrderAllStatus(){
    	
    	StringBuffer buffer = new StringBuffer();
    	for(OrderStatus status : OrderStatus.values()){
    		if((status.ordinal() > 0) && (status.ordinal() < OrderStatus.values().length)){
    			buffer.append(",");
    		}
    		buffer.append("'"+status.getCode()+"'");
    	}
    	return buffer.toString();
    }
}
