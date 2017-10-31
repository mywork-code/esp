package com.apass.esp.web.offer;

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
import org.springframework.web.bind.annotation.RequestBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.MyCouponVo;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;

@Path("/my/coupon")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MyCouponManagerController {

	private static final Logger logger = LoggerFactory.getLogger(MyCouponManagerController.class);
	
	@Autowired
	private MyCouponManagerService myCouponManagerService;
	
	@POST
    @Path("/list")
	public Response getMyCoupons(Map<String, Object> paramMap){
		logger.info("getMyCoupons:--------->{}",GsonUtils.toJson(paramMap));
		String userId = CommonUtils.getValue(paramMap, "userId");
		if(StringUtils.isBlank(userId)){
			logger.error("用户编号不能为空!");
			return Response.fail("用户编号不能为空!");
		}
		try {
			Map<String,Object> params = myCouponManagerService.getCoupons(userId);
			return Response.successResponse(params);
		} catch (Exception e) {
			logger.error("get mycoupons is failed------{}", e);
		}
		return Response.fail("我的优惠券获取失败!");
	}
	
	@POST
    @Path("/saveCoupon")
	public Response giveCouponToUser(@RequestBody Map<String, Object> paramMap){
		String userId = CommonUtils.getValue(paramMap, "userId");
		String activityId = CommonUtils.getValue(paramMap, "activityId");
		String couponId = CommonUtils.getValue(paramMap, "couponId");
		if(StringUtils.isBlank(activityId)){
			logger.error("活动编号不能为空!");
			return Response.fail("活动编号不能为空!");
		}
		logger.info("getGroupAndGoodsByGroupId:--------->{}",GsonUtils.toJson(paramMap));
		try {
			int count = myCouponManagerService.giveCouponToUser(new MyCouponVo(Long.parseLong(userId),Long.parseLong(couponId),Long.parseLong(activityId)));
			if(count > 0){
				return Response.success("领取成功!");
			}
		} catch(BusinessException e){
			logger.error("business giveCouponToUser :{}",e);
			return Response.fail(e.getErrorDesc());
		} catch (Exception e) {
			logger.error("exception giveCouponToUser :{}",e);
		}
		return Response.fail("领取失败!");
	}
}
