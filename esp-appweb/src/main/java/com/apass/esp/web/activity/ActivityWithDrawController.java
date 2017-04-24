package com.apass.esp.web.activity;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.nothing.RegisterInfoController;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;

@Controller
@RequestMapping("activity/award")
public class ActivityWithDrawController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterInfoController.class);

	@Autowired
	public AwardActivityInfoService awardActivityInfoService;

	@Autowired
	private MobileSmsService mobileRandomService;

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		AwardActivityInfoDto obj = new AwardActivityInfoDto();
		obj.setActivityName("test");
		obj.setStatus((byte) 1);
		obj.setType((byte) 0);
		obj.setaStartDate(new Date());
		long obj1 = awardActivityInfoService.addActivity(obj);
		return obj1 + "";

	}

	/**
	 * 银行卡列表
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/getBankList", method = RequestMethod.POST)
	@ResponseBody
	public Response getBankList(Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		Response res = awardActivityInfoService.getBankList(paramMap);

		return res;
	}

	/**
	 * 查询是否绑定银行卡及卡片信息及身份信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBindCardImformation", method = RequestMethod.POST)
	@ResponseBody
	public Response getBindCardImformation(Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			return Response.fail(userId);
		}
		// if
		// (result.get("status").equals(AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode()))
		// {
		// return Response.successResponse(result);
		// }
		// if
		// (result.get("status").equals(AwardActivity.BIND_STATUS.UNBINDED.getCode()))
		// {
		// return Response.successResponse(result);
		// }
		return Response.successResponse(result);
	}

	/**
	 * 绑定卡片
	 */
	@RequestMapping(value = "/bindCardByUserId", method = RequestMethod.POST)
	@ResponseBody
	public Response bindCardByUserId(Map<String, Object> paramMap) {

		String userId = CommonUtils.getValue(paramMap, "userId");

		String realName = CommonUtils.getValue(paramMap, "realName");
		String cardNo = CommonUtils.getValue(paramMap, "cardNo");
		String mobile = CommonUtils.getValue(paramMap, "mobile");
		String imgFile = CommonUtils.getValue(paramMap, "imgFile");
		// 校验

		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			return Response.fail("对不起,该用户不存在!");
		}

		if (AwardActivity.BIND_STATUS.BINDED.getCode().equals(result.get("status"))) {
			return Response.fail("对不起,该用户已经绑定银行卡");
		}
		paramMap.put("customerId", result.get("customerId"));
		// 客户未绑定身份证 ==>验证身份证
		if (AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode().equals(result.get("status"))) {
			awardActivityInfoService.identityReconize(paramMap);
			return Response.success("success");
			// 绑定
		}

		// 客户已绑定身份证且未绑定银行卡
		if (AwardActivity.BIND_STATUS.UNBINDED.getCode().equals(result.get("status"))) {
			// String identityNo = CommonUtils.getValue(paramMap, "identityNo");

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
		return Response.success("success");
	}

	/**
	 * 身份证上传识别
	 * 
	 * @return
	 */
	public Response uploadImgAndRecognize(Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.UPLOADIMGANDRECOGNIZED.getCode() + "_" + userId;

		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));

		if (result == null || result.size() == 0) {
			return Response.fail("对不起,该用户不存在!");
		}

		if (!AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode().equals(result.get("status"))) {
			return Response.fail("对不起,该用户已绑定身份证");
		}
		paramMap.put("customerId", result.get("customerId"));
		// String imgFile = CommonUtils.getValue(paramMap, "imgFile");
		// String mobile = CommonUtils.getValue(paramMap, "mobile");
		awardActivityInfoService.identityReconize(paramMap);
		return Response.success("success");
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public Response sendRandomCode(Map<String, Object> paramMap) {

		String mobile = CommonUtils.getValue(paramMap, "mobile");// 手机号
		String smsType = CommonUtils.getValue(paramMap, "smsType");// 验证码类型
		if (StringUtils.isAnyBlank(mobile, smsType)) {
			return Response.fail("验证码接收手机号不能为空");
		}
		try {
			mobileRandomService.sendMobileVerificationCode(smsType, mobile);
			return Response.success("验证码发送成功,请注意查收");
		} catch (BusinessException e) {
			LOGGER.error("mobile verification code send fail", e);
			return Response.fail("网络异常,发送验证码失败,请稍后再试");
		}
	}
}
