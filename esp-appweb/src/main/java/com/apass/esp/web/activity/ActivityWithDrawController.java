package com.apass.esp.web.activity;

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
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.web.activity.RegisterInfoController;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

@Controller
@RequestMapping("activity/award")
public class ActivityWithDrawController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterInfoController.class);

	@Autowired
	public AwardActivityInfoService awardActivityInfoService;

	/**
	 * 银行卡列表
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/getBankList", method = RequestMethod.POST)
	@ResponseBody
	public Response getBankList(@RequestBody Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		if (StringUtils.isEmpty(userId)) {
			return Response.fail("userId不能为空");
		}
		Map<String, Object> map = awardActivityInfoService.getBankList();
		return Response.successResponse(map);
	}

	/**
	 * 查询是否绑定银行卡及卡片信息及身份信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBindCardImformation", method = RequestMethod.POST)
	@ResponseBody
	public Response getBindCardImformation(@RequestBody Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		if(StringUtils.isEmpty(userId)){
			return Response.fail("userId为空");
		}
		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			return Response.fail(userId);
		}
		return Response.successResponse(result);
	}

	/**
	 * 绑定卡片
	 */
	@RequestMapping(value = "/bindCardByUserId", method = RequestMethod.POST)
	@ResponseBody
	public Response bindCardByUserId(@RequestBody Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		String realName = CommonUtils.getValue(paramMap, "realName");
		String cardNo = CommonUtils.getValue(paramMap, "cardNo");
		String bankCode = CommonUtils.getValue(paramMap, "bankCode");
		if (StringUtils.isAnyBlank(userId, realName, cardNo, bankCode)) {
			return Response.fail("传入参数均不能为空");
		}
		if (!RegExpUtils.length(realName, 4, 20)) {
			return Response.fail("真实姓名输入不合法");
		}

		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			return Response.fail("对不起,该用户不存在!");
		}
		if (AwardActivity.BIND_STATUS.BINDED.getCode().equals(result.get("status"))) {
			return Response.fail("对不起,该用户已经绑定银行卡");
		}
		paramMap.put("customerId", result.get("customerId"));
		paramMap.put("mobile", result.get("mobile"));
		paramMap.put("identityNo", result.get("identityNo"));
		if (AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode().equals(result.get("status"))) {
			return Response.fail("对不起,请先上传身份证再绑定卡片");
		}
		Response res = awardActivityInfoService.validateBindCard(paramMap);
		if (!"1".equals(res.getStatus())) {
			return res;
		}

		Response response1 = awardActivityInfoService.latestSignature(paramMap);
		if (!"1".equals(response1.getStatus())) {
			return Response.fail("请先签名");
		}
		// 绑卡
		Response response = awardActivityInfoService.bindCard(paramMap);
		if (!"1".equals(response.getStatus())) {
			return response;
		}
		return response;
	}

	/**
	 * 身份证上传识别<正面or反面>
	 * 
	 * @return
	 */

	@RequestMapping(value = "/uploadImgAndRecognize", method = RequestMethod.POST)
	@ResponseBody
	public Response uploadImgAndRecognize(@RequestBody Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		String idCardType = CommonUtils.getValue(paramMap, "idCardType");
		LOGGER.info(userId, idCardType);
		if (StringUtils.isAnyEmpty(idCardType, userId)) {
			return Response.fail("参数错误!");
		}
		String requestId = "";
		if ("front".equals(idCardType)) {
			requestId = AwardActivity.AWARD_ACTIVITY_METHOD.UPLOADIMGANDRECOGNIZED.getCode() + "_" + userId;
		} else if ("back".equals(idCardType)) {
			requestId = AwardActivity.AWARD_ACTIVITY_METHOD.UPLOADIMGANDRECOGNIZEDOPPO.getCode() + "_" + userId;
		} else {
			return Response.fail("参数错误!");
		}

		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			return Response.fail("对不起,该用户不存在!");
		}
		if (!AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode().equals(result.get("status"))) {
			return Response.fail("对不起,该用户已绑定身份证");
		}
		paramMap.put("customerId", result.get("customerId"));
		paramMap.put("mobile", result.get("mobile"));

		Response res = awardActivityInfoService.identityReconize(paramMap);
		return res;
	}

	/**
	 * 合同
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/saveContract", method = RequestMethod.POST)
	@ResponseBody
	public Response saveContract(@RequestBody Map<String, Object> paramMap) {
		
		String userId = CommonUtils.getValue(paramMap, "userId");
		String requestId = "";
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			return Response.fail("对不起,该用户不存在!");
		}
		paramMap.put("customerId", result.get("customerId"));
		Response res = awardActivityInfoService.saveContract(paramMap);
		return res;
	}

	/**
	 * 合同初始化
	 * 
	 * @return
	 */

	@RequestMapping(value = "/contractInit", method = RequestMethod.POST)
	@ResponseBody
	public Response contractInit(@RequestBody Map<String, Object> paramMap) {
		String userId = CommonUtils.getValue(paramMap, "userId");
		String realName = CommonUtils.getValue(paramMap, "realName");
		String cardNo = CommonUtils.getValue(paramMap, "cardNo");
		String cardBank = CommonUtils.getValue(paramMap, "cardBank");
		if (StringUtils.isAnyBlank(userId, realName, cardNo, cardBank)) {
			return Response.fail("参数值错误");
		}
		if (!RegExpUtils.length(realName, 4, 20)) {
			return Response.fail("真实姓名输入不合法");
		}
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation("contractInit",
				Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			return Response.fail("对不起,该用户不存在!");
		}
		if ("2".equals(result.get("status"))) {
			return Response.fail("对不起,请先上传身份证");
		}
		if ("0".equals(result.get("status"))) {
			return Response.fail("对不起,该用户已绑定银行卡");
		}
		paramMap.put("customerId", result.get("customerId"));
		paramMap.put("identityNo", result.get("identityNo"));
		paramMap.put("mobile", result.get("mobile"));
		Response res1 = awardActivityInfoService.validateBindCard(paramMap);
		if (!"1".equals(res1.getStatus())) {
			return res1;
		}
		Response res = awardActivityInfoService.initContract(paramMap);
		if (StringUtils.isEmpty(String.valueOf(res.getData()))) {
			paramMap.put("status", "0");// 没有签名
		}
		paramMap.put("status", "1");// 有签名
		paramMap.put("sign", res.getData());
		paramMap.put("customerEntity", res.getMsg());
		return Response.response("1", "请求数据成功", paramMap);
	}
}
