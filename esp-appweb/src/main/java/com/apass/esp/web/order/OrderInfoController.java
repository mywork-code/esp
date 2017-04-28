package com.apass.esp.web.order;

import java.math.BigDecimal;
import java.util.Date;
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

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.dto.cart.PurchaseRequestDto;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class OrderInfoController {

    private static final Logger LOGGER   = LoggerFactory.getLogger(OrderInfoController.class);
    @Autowired
    public OrderService         orderService;
    
	@Autowired
	public AwardActivityInfoService awardActivityInfoService;
	
	@Autowired
	public AwardBindRelService awardBindRelService;
	
	@Autowired
	public AwardDetailService awardDetailService;

    private static final String NO_USER  = "对不起!用户号不能为空";
    private static final String ORDER_ID = "orderId";

    /**
     * 下单
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/confirmOrder")
    public Response confirmOrder(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_GENERATE.getValue();
        String methodDesc = LogStashKey.ORDER_GENERATE.getName();
        
        String userIdStr = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String totalPaymentStr = CommonUtils.getValue(paramMap, "totalPayment"); //订单总金额
        String addressIdStr = CommonUtils.getValue(paramMap, "addressId"); //收货地址Id
        String buyInfo = CommonUtils.getValue(paramMap, "buyInfo"); //购买商品列表
        
        String sourceFlag = CommonUtils.getValue(paramMap, "sourceFlag"); //订单来源标记[购物车 或 直接购买]
        
        String requestId = logStashSign + "_" + userIdStr;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        Map<String, Object> resultMap=Maps.newHashMap();
        
        
        try {
            
            BigDecimal totalPayment = null;
            if (null == userIdStr) {
                return Response.fail(NO_USER);
            }
            if (!StringUtils.isNumeric(userIdStr)) {
                return Response.fail("用户名传入非法!");
            }
            Long userId=Long.valueOf(userIdStr);
            if (null == addressIdStr) {
                return Response.fail("用户收货地址不能为空");
            }
            if (!StringUtils.isNumeric(addressIdStr)) {
                return Response.fail("地址信息传入非法!");
            }
            Long addressId=Long.valueOf(addressIdStr);
            if (StringUtils.isEmpty(buyInfo)) {
                return Response.fail("购买商品信息不能为空!");
            }
            if (StringUtils.isEmpty(totalPaymentStr)) {
                return Response.fail("购买商品总金额不能为空!");
            } else {
                totalPayment = new BigDecimal(totalPaymentStr);
            }
            List<PurchaseRequestDto> purchaseList = GsonUtils.convertList(buyInfo, PurchaseRequestDto.class);
            if (purchaseList.isEmpty()) {
                return Response.fail("请选择所购买的商品");
            }
            
            List<String> orders = orderService.confirmOrder(requestId, userId, totalPayment, addressId, purchaseList,sourceFlag);
            resultMap.put("orderList", orders);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("订单生成失败!请稍后再试");
        }
        return Response.success("订单生成成功!",resultMap);
    }

    /**
     *  取消订单
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/cancelOrder")
    public Response cancelOrder(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_CANCEL.getValue();
        String methodDesc = LogStashKey.ORDER_CANCEL.getName();
        
        Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID); //  订单号
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (null == userId) {
            return Response.fail(NO_USER);
        }
        
        try {
            
            orderService.cancelOrder(requestId, userId, orderId);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("订单取消失败!请稍后再试");
        }
        return Response.success("取消订单成功!");
    }

    /**
     * 延迟收货
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/deleyReceivedGoods")
    public Response deleyReceiveGoods(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_DELAY.getValue();
        String methodDesc = LogStashKey.ORDER_DELAY.getName();
        
        Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (null == userId) {
            LOGGER.info("用户号不能为空!");
            return Response.fail("登录失效请重新登录!");
        }
        if (StringUtils.isEmpty(orderId)) {
            return Response.fail("请求订单信息不能为空!");
        }
        
        try {
            
            orderService.deleyReceiveGoods(requestId, userId, orderId);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("延迟收货失败!请稍后再试");
        }
        return Response.success("延迟收货成功!");
    }

    /**
     * 确认收货
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/confirmReceiveGoods")
    public Response confirmReceiveGoods(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_CONFIRM_RECEIVE.getValue();
        String methodDesc = LogStashKey.ORDER_CONFIRM_RECEIVE.getName();
        
        Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (null == userId) {
            LOGGER.info("用户号不能为空!");
            return Response.fail("登录失效请重新登录!");
        }
        if (StringUtils.isEmpty(orderId)) {
            return Response.fail("请求订单信息不能为空!");
        }
        
        try {
            
            orderService.confirmReceiveGoods(requestId, userId, orderId);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("确认收货失败!请稍后再试");
        }
		// 该订单是否可以返现
		AwardActivityInfoVo awardActivityInfoVo = null;
		try {
			awardActivityInfoVo = awardActivityInfoService.getActivityByName(AwardActivity.ActivityName.INTRO);
		} catch (BusinessException e) {
			LOGGER.error("getActivityBy intro error userId {},orderId {}", userId, orderId);
			return Response.success("确认收货成功!");
		}
		// 返现活动存在
		if (awardActivityInfoVo != null) {
			OrderInfoEntity orderInfoEntity = null;
			try {
				orderInfoEntity = orderService.selectByOrderId(orderId);
			} catch (BusinessException e) {
				LOGGER.error("selectByOrderId orderId{},userId{} error", orderId, userId);
				return Response.success("确认收货成功!");
			}
			if (orderInfoEntity != null) {// 订单存在
				Date startDate = DateFormatUtil.string2date(awardActivityInfoVo.getaStartDate(), "yyyy-MM-dd HH:mm:ss");
				Date endDate = DateFormatUtil.string2date(awardActivityInfoVo.getaEndDate(), "yyyy-MM-dd HH:mm:ss");
				Date date = orderInfoEntity.getCreateDate();// 下单时间
				LOGGER.info("userId {}  ,orderId {} ,activity id {},startDate {},endDate {},curDate {}", userId,
						orderId, awardActivityInfoVo.getId(), startDate, endDate, date);
				if (date.before(endDate) && date.after(startDate)) {// 下单时间在活动有效期
					AwardBindRel awardBindRel = awardBindRelService.getByInviterUserId(String.valueOf(userId));
					if (awardBindRel != null) {// 当前用户已经被邀请
						AwardDetailDto awardDetailDto = new AwardDetailDto();
						awardDetailDto.setActivityId(awardBindRel.getActivityId());
						// 返点金额
						String rebateString = awardActivityInfoVo.getRebate();
						BigDecimal rebate = new BigDecimal(rebateString.substring(0,rebateString.length()-1)).multiply(new BigDecimal(0.01));
						awardDetailDto.setAmount(orderInfoEntity.getOrderAmt()
								.multiply(rebate));
						awardDetailDto.setMainOrderId(orderId);
						awardDetailDto.setCreateDate(new Date());
						awardDetailDto.setUpdateDate(new Date());
						// 处理中
						awardDetailDto.setStatus((byte) 2);
						// 获得
						awardDetailDto.setType((byte) 0);
						awardDetailDto.setUserId(userId);
						awardDetailService.addAwardDetail(awardDetailDto);
						LOGGER.info(
								"userId {}  ,orderId {} ,activity id {},orderInfoEntity.getOrderAmt {} , awardActivityInfoVo.getRebate {}",
								userId, orderId, awardActivityInfoVo.getId(), orderInfoEntity.getOrderAmt(),
								awardActivityInfoVo.getRebate());
						return Response.success("确认收货成功!");
					}
				}
			}
		}
		return Response.success("确认收货成功!");
	}

    /** 
     * 删除订单
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/deleteOrderInfo")
    public Response deleteOrderInfo(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_DELETE.getValue();
        String methodDesc = LogStashKey.ORDER_DELETE.getName();
        
        Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (null == userId) {
            LOGGER.info("用户号不能为空!");
            return Response.fail("登录失效请重新登录!");
        }
        if (StringUtils.isEmpty(orderId)) {
            return Response.fail("请求订单信息不能为空!");
        }
        
        try {
            
            orderService.deleteOrderInfo(requestId, userId, orderId);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("删除订单失败!请稍后再试");
        }
        return Response.success("删除订单成功!");
    }
    
    /**
     * 我的订单-重新下单
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/repeatConfirmOrder")
    public Response repeatConfirmOrder(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_REPEAT_CONFIRM.getValue();
        String methodDesc = LogStashKey.ORDER_REPEAT_CONFIRM.getName();
        
        Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (null == userId) {
            LOGGER.info("用户号不能为空!");
            return Response.fail("登录失效请重新登录!");
        }
        if (StringUtils.isEmpty(orderId)) {
            return Response.fail("重新下单该笔订单号不能为空!");
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            
            orderService.repeatConfirmOrder(requestId, userId, orderId,resultMap);
            return Response.success("重新下单初始化成功",resultMap);
        }catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("重新下单初始化失败");
        }
    }
    
    /**
     * 重新下单添加至购物车
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/reOrder/addShopCart")
    public Response reOrder(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_CONFIRM_FAIL_ADDCART.getValue();
        String methodDesc = LogStashKey.ORDER_CONFIRM_FAIL_ADDCART.getName();
        
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isEmpty(orderId)) {
            return Response.fail("重新下单订单号不能为空");
        }
        if (null == userId) {
            LOGGER.info("用户号不能为空!");
            return Response.fail("登录失效请重新登录!");
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            
            orderService.reOrder(requestId, orderId, userId);
            return Response.success("重新下单添加至购物车成功", resultMap);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("重新下单添加至购物车失败");
        }
    }
    
    /**
     * 修改订单收货地址
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/modifyShippingAddress")
    public Response modifyShippingAddress(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_NOPAY_MODIFYADDRESS.getValue();
        String methodDesc = LogStashKey.ORDER_NOPAY_MODIFYADDRESS.getName();
        
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        Long userId = CommonUtils.getLong(paramMap, "userId");
        Long addressId = CommonUtils.getLong(paramMap, "addressId");
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isEmpty(orderId)) {
            return Response.fail("请求订单信息不能为空!");
        }
        if (null == addressId) {
            return Response.fail("请求地址信息不能为空!");
        }
        if (null == userId) {
            return Response.fail("请求地址信息不能为空!");
        }
        
        try {
            
            orderService.modifyShippingAddress(requestId, addressId, orderId, userId);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("修改订单收货地址失败!请稍后再试");
        }
        return Response.success("修改订单收货地址成功!");
    }

    /**
     * 根据 userId、订单状态(可选) 查询订单信息
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/queryOrderInfo")
    public Response queryOrderInfo(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_QUERY.getValue();
        String methodDesc = LogStashKey.ORDER_QUERY.getName();
        
        String userId = CommonUtils.getValue(paramMap, "userId");
        String statusStr = CommonUtils.getValue(paramMap, "statusStr");
        
        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

        if (StringUtils.isBlank(userId)) {
            return Response.fail(NO_USER);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            
            List<OrderDetailInfoDto> resultList = orderService.getOrderDetailInfo(requestId, userId, statusStr);
            resultMap.put("orderInfoList", resultList);
            return Response.success("操作成功", resultMap);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("订单查询失败,请稍后再试或联系客服!");
        }
    }

    /**
     * 查询订单收货地址及签收信息
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/loadByOrderId")
    public Response loadByOrderId(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_LOADADDR.getValue();
        String methodDesc = LogStashKey.ORDER_LOADADDR.getName();
        
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isBlank(orderId)) {
            return Response.fail("订单号不能为空");
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            
            orderService.loadInfoByOrderId(requestId, resultMap, orderId);
            return Response.success("查询地址信息成功", resultMap);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("查询订单失败请稍后再试");
        }
    }
    
    
    /**
     * 查询订单详情[根据订单号查询订单明细]
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/queryOrderDetailInfo")
    public Response queryOrderDetailInfo(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_QUERY_DETAIL.getValue();
        String methodDesc = LogStashKey.ORDER_QUERY_DETAIL.getName();
        
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isBlank(orderId)) {
            return Response.fail("订单号不能为空");
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            
            List<OrderDetailInfoEntity> orderDetails = orderService.queryOrderDetailInfo(requestId, orderId);
            resultMap.put("orderInfoList", orderDetails);
            return Response.success("查询订单明细成功", resultMap);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("订单明细查询失败");
        }
    }
    /**
     * 查询 用户 待付款、待发货、待收货 订单数量
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/getWaitedOrderNum")
    public Response getWaitedOrderNum(Map<String, Object> paramMap) {
        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            if (StringUtils.isBlank(userId)) {
                return Response.fail("用户id不能为空");
            }
            resultMap = orderService.getOrderNum(userId);
            return Response.success("订单数量查询成功", resultMap);
        } catch (Exception e) {
            LOGGER.error("订单数量查询失败", e);
            return Response.fail("订单数量查询失败请稍后再试!");
        }
    }
    
    /**
     * 修改待付款订单收货地址
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/modifyOrderAddress")
    public Response modifyOrderAddress(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.ORDER_QUERY_DETAIL.getValue();
        String methodDesc = LogStashKey.ORDER_QUERY_DETAIL.getName();
        
        String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
        String userId = CommonUtils.getValue(paramMap, "userId");
        String name = CommonUtils.getValue(paramMap, "name");
        String telephone = CommonUtils.getValue(paramMap, "telephone");
        String province = CommonUtils.getValue(paramMap, "province");
        String city = CommonUtils.getValue(paramMap, "city");
        String district = CommonUtils.getValue(paramMap, "district");
        String address = CommonUtils.getValue(paramMap, "address");
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isBlank(orderId)) {
            return Response.fail("订单号不能为空!");
        }
        
        if (StringUtils.isBlank(userId)) {
            return Response.fail("用户id不能为空!");
        }
        
        if(StringUtils.isAnyBlank(name, telephone, province, city, district, address)){
            return Response.fail("地址信息字段不能为空!");
        }
        
        try {
            
            AddressInfoEntity addressInfoDto = new AddressInfoEntity();
            addressInfoDto.setUserId(Long.valueOf(userId));
            addressInfoDto.setName(name);
            addressInfoDto.setTelephone(telephone);
            addressInfoDto.setProvince(province);
            addressInfoDto.setCity(city);
            addressInfoDto.setDistrict(district);
            addressInfoDto.setAddress(address);
            addressInfoDto.setIsDefault("0");
            
            
            orderService.modifyOrderAddress(requestId, orderId, userId, addressInfoDto);
            
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("修改订单收货地址失败,请稍后再试或联系客服");
        }
        return Response.success("修改订单收货地址成功!");
    }
    
    /**
     * 待付款页付款库存不足或商品下架时  删除订单加入购物车
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/payAfterFail")
    public Response payAfterFail(Map<String, Object> paramMap) {
        try {
            String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
            String userIdStr = CommonUtils.getValue(paramMap, "userId");
            
            if (StringUtils.isBlank(orderId)) {
                return Response.fail("订单号不能为空!");
            }
            if (StringUtils.isBlank(userIdStr)) {
                return Response.fail("用户号不能为空!");
            }
            if (!StringUtils.isNumeric(userIdStr)) {
                return Response.fail("用户名传入非法!");
            }
            Long userId=Long.valueOf(userIdStr);
            orderService.payAfterFail(orderId, userId);
            
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
        	LOGGER.error("商品信息加入购物车失败", e);
            return Response.fail("商品信息加入购物车失败");
        }
        return Response.success("支付失败加入购物车成功");
    }
    
    
}
