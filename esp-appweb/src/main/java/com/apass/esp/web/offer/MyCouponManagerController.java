package com.apass.esp.web.offer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.MyCouponVo;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;

@Controller
@RequestMapping("/my/coupon")
public class MyCouponManagerController {

	private static final Logger logger = LoggerFactory.getLogger(MyCouponManagerController.class);
	
	@Autowired
	private MyCouponManagerService myCouponManagerService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Response getMyCoupons(Map<String, Object> paramMap){
		
		
		return Response.successResponse(null);
	}
	
	
	
	@RequestMapping("/saveCoupon")
	@ResponseBody
	public Response giveCouponToUser(Map<String, Object> paramMap){
		String userId = CommonUtils.getValue(paramMap, "userId");
		String activityId = CommonUtils.getValue(paramMap, "activityId");
		String couponId = CommonUtils.getValue(paramMap, "couponId");
		if(StringUtils.isBlank(activityId)){
			logger.error("活动编号不能为空!");
			return Response.fail("活动编号不能为空!");
		}
		logger.info("getGroupAndGoodsByGroupId:--------->{}",GsonUtils.toJson(paramMap));
		try {
			myCouponManagerService.giveCouponToUser(new MyCouponVo(Long.parseLong(userId),Long.parseLong(couponId),Long.parseLong(activityId)));
			return Response.success("领取成功!");
		} catch(BusinessException e){
			logger.error("business giveCouponToUser :{}",e);
			return Response.fail(e.getErrorDesc());
		} catch (Exception e) {
			logger.error("exception giveCouponToUser :{}",e);
			return Response.fail("领取失败!");
		}
	}
}
