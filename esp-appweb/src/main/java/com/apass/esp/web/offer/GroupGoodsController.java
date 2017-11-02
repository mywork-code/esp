package com.apass.esp.web.offer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.ProCouponVo;
import com.apass.esp.service.offer.CouponManagerService;
import com.apass.esp.service.offer.GroupManagerService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;

@Controller
@RequestMapping("/activity/group")
public class GroupGoodsController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupGoodsController.class);
	
	@Autowired
	private GroupManagerService groupManagerService;
	
	@Autowired
	private CouponManagerService couponManagerService;
	
	@RequestMapping("/getGroupAndGoods")
	@ResponseBody
	public Response getGroupAndGoodsByGroupId(@RequestBody Map<String, Object> paramMap){
		String activityId = CommonUtils.getValue(paramMap, "activityId");
		String bannerId = CommonUtils.getValue(paramMap, "bannerId");
		String userId = CommonUtils.getValue(paramMap, "userId");
		logger.info("getGroupAndGoodsByGroupId---------------------->{}",JsonUtil.toJsonString(paramMap));
		if(StringUtils.isEmpty(activityId) || StringUtils.isEmpty(userId) ){
			return Response.fail("参数传递有误!");
		}
		try {
			Map<String,Object> maps = groupManagerService.getGroupsAndGoodsByActivityId(activityId,bannerId);
			/**
			 * sprint 11 根据活动的Id，获取对应优惠券的信息
			 */
			List<ProCouponVo> couponVos = couponManagerService.getCouponVos(userId,activityId);
			maps.put("coupons", couponVos);
			return Response.success("查询成功!", maps);
		} catch(BusinessException e){
			logger.error("business activityId :{}",e);
			return Response.fail(e.getErrorDesc());
		} catch (Exception e) {
			logger.error("exception activityId :{}",e);
			return Response.fail("活动查询失败!");
		}
	}
}
