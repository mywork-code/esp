package com.apass.esp.web.offer;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.MyCouponVo;
import com.apass.esp.domain.vo.ProCouponVo;
import com.apass.esp.service.offer.CouponManagerService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/my/coupon")
public class MyCouponManagerController {

	private static final Logger logger = LoggerFactory.getLogger(MyCouponManagerController.class);
	
	@Autowired
	private MyCouponManagerService myCouponManagerService;
	
	@Autowired
	private CouponManagerService couponManagerService;
	
	@ResponseBody
	@RequestMapping("/list")
	public Response getMyCoupons(@RequestBody Map<String, Object> paramMap){
		logger.info("getMyCoupons:--------->{}",GsonUtils.toJson(paramMap));
		String userId = CommonUtils.getValue(paramMap, "userId");
		if(StringUtils.isBlank(userId)){
			logger.error("用户编号不能为空!");
			return Response.fail("用户编号不能为空!");
		}
		try {
			Map<String,Object> params = myCouponManagerService.getCoupons(userId);
			/**
			 * sprint 11 您还有未领取的券,如果size 0 则前端不显示
			 */
			List<ProCouponVo> proCouponRelList=couponManagerService.getCouponList(Long.parseLong(userId));
			params.put("couponsize", proCouponRelList.size());
			return Response.successResponse(params);
		} catch (Exception e) {
			logger.error("get mycoupons is failed------{}", e);
		}
		return Response.fail("我的优惠券获取失败!");
	}
	
	@ResponseBody
	@RequestMapping("/saveCoupon")
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
	
	@ResponseBody
	@RequestMapping("/deleteMyCoupon")
	public Response deleteMyCoupon(@RequestBody Map<String, Object> paramMap){
		logger.info("deleteMyCoupon---------------------->{}",JsonUtil.toJsonString(paramMap));
		String mycouponId = CommonUtils.getValue(paramMap, "mycouponId");
		if(StringUtils.isEmpty(mycouponId)){
			return Response.fail("参数传递有误!");
		}
		try {
			myCouponManagerService.deleteMyCoupon(mycouponId);
			return Response.success("删除成功!");
		} catch (Exception e) {
			logger.error("exception giveCouponToUser :{}",e);
			return Response.fail("删除失败!");
		}
	}
	  /**
     * 您还有优惠券未领取
     * @param paramMap
     * @return
     */
	@ResponseBody
	@RequestMapping("/noGetCoupons")
	public Response noGetCoupons(@RequestBody Map<String, Object> paramMap){
		String userId = CommonUtils.getValue(paramMap, "userId");
		if(StringUtils.isBlank(userId)){
			logger.error("用户id不能为空!");
			return Response.fail("用户id不能为空!");
		}
		List<ProCouponVo> proCouponRelList=couponManagerService.getCouponList(Long.parseLong(userId));
		 return Response.success("加载未领取优惠券成功!",proCouponRelList);
	}

}
