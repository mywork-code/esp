package com.apass.esp.web.activity;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;

@Controller
@RequestMapping("activity/award")
public class ActivityWithDrawController {
	@Autowired
	public AwardActivityInfoService awardActivityInfoService;

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		AwardActivityInfo obj = new AwardActivityInfo();
		obj.setActivityName("test");
		obj.setStatus((byte) 1);
		obj.setType((byte) 0);
		obj.setaStartDate(new Date());
		long obj1 = awardActivityInfoService.addActivity(obj);
		return obj1 + "";

	}

	/**
	 * 查询是否绑定银行卡及卡片信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBindCardImformation", method = RequestMethod.POST)
	@ResponseBody
	public Response getBindCardImformation( Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
		String result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null) {
			return Response.fail(userId);
		} else if (result.equals(AwardActivity.BIND_STATUS.UNBINDED.getCode())) {
			return Response.successResponse("unbind");
		} else {
			return Response.successResponse(result);
		}
	}

	/**
	 * 绑定卡片
	 */
	@RequestMapping(value = "/bindCardByUserId", method = RequestMethod.POST)
	@ResponseBody
	public Response bindCardByUserId(@RequestBody Map<String, Object> paramMap) {

		String userId = CommonUtils.getValue(paramMap, "userId");
		String identityNo = CommonUtils.getValue(paramMap, "identityNo");
		String realName = CommonUtils.getValue(paramMap, "realName");
		String cardNo = CommonUtils.getValue(paramMap, "cardNo");
		String mobile = CommonUtils.getValue(paramMap, "mobile");
		// 校验

		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
		String result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null) {
			return Response.fail("对不起,该用户不存在!");
		}
		Map<String, Object> map = GsonUtils.convert(result);
		if (map.containsKey("status") && "0".equals(map.get("status"))) {
			return Response.fail("对不起,该用户已经绑定银行卡");
		}
		// 客户存在且未绑定银行卡

		paramMap.put("customerId", map.get("customerId"));

		// 验卡是否本人 以及是否支持该银行

		Response res = awardActivityInfoService.validateBindCard(paramMap);
		if (!"1".equals(res.getStatus())) {
			return Response.fail(userId);
			// Map resMap = GsonUtils.convert((String) res.getData());
		}
		// 绑卡

		Response response = awardActivityInfoService.bindCard(paramMap);
		if (!"1".equals(response.getStatus())) {
			return Response.fail(userId);
		}
		return Response.success("success");

	}

}
