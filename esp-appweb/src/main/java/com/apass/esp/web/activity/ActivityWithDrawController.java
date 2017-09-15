package com.apass.esp.web.activity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.enums.AwardActivity.ActivityName;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.registerInfo.RegisterInfoService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

@Controller
@RequestMapping("activity/award")
public class ActivityWithDrawController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityWithDrawController.class);

	@Autowired
	public AwardActivityInfoService awardActivityInfoService;
	@Autowired
	public RegisterInfoService registerInfoService;
	@Autowired
	public AwardBindRelService awardBindRelService;
	@Autowired
	public AwardDetailService awardDetailService;
	@Autowired
	private AwardDetailMapper awardDetailMapper;

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
		if (StringUtils.isEmpty(userId)) {
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
		String cardBank = CommonUtils.getValue(paramMap, "cardBank");
		String mobile = CommonUtils.getValue(paramMap, "mobile");
		if (StringUtils.isAnyBlank(userId, realName, cardNo, bankCode,mobile)) {
			LOGGER.error("传入参数均不能为空");
			return Response.fail("传入参数均不能为空");
		}
		if(!RegExpUtils.mobile(mobile)){
			return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
		}
		if (!RegExpUtils.length(realName, 4, 20)) {
			LOGGER.error("真实姓名输入不合法");
			return Response.fail("真实姓名输入不合法");
		}

		String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			LOGGER.error("对不起,该用户不存在!");
			return Response.fail("对不起,该用户不存在!");
		}
		if (AwardActivity.BIND_STATUS.BINDED.getCode().equals(result.get("status"))) {
			LOGGER.error("对不起,该用户已经绑定银行卡!");
			return Response.fail("对不起,该用户已经绑定银行卡!");
		}
		paramMap.put("customerId", result.get("customerId"));
		paramMap.put("mobile", mobile);
		paramMap.put("identityNo", result.get("identityNo"));
		if (AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode().equals(result.get("status"))) {
			LOGGER.error("对不起,请先上传身份证再绑定卡片");
			return Response.fail("对不起,请先上传身份证再绑定卡片");
		}
		Response res = awardActivityInfoService.validateBindCard(paramMap);
		if (!"1".equals(res.getStatus())) {
			return res;
		}
		String s  = GsonUtils.toJson(res.getData());
		Map <String,Object> m =GsonUtils.convert(s);
		if(!cardBank.equals(m.get("dictName"))){
			LOGGER.error("卡号与所选银行不匹配!");
			return Response.fail("卡号与所选银行不匹配!");
		}

		Response response1 = awardActivityInfoService.latestSignature(paramMap);
		if (!"1".equals(response1.getStatus())) {
			LOGGER.error("请先签名!");
			return Response.fail("请先签名!");
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
			LOGGER.error("参数错误!");
			return Response.fail("传入参数不能为空");
		}
		String requestId = "";
		if ("front".equals(idCardType)) {
			requestId = AwardActivity.AWARD_ACTIVITY_METHOD.UPLOADIMGANDRECOGNIZED.getCode() + "_" + userId;
		} else if ("back".equals(idCardType)) {
			requestId = AwardActivity.AWARD_ACTIVITY_METHOD.UPLOADIMGANDRECOGNIZEDOPPO.getCode() + "_" + userId;
		} else {
			LOGGER.error("参数错误!");
			return Response.fail("参数错误!");
		}

		Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			LOGGER.error("对不起,该用户不存在!");
			return Response.fail("对不起,该用户不存在!");
		}
		if ("front".equals(idCardType)) {
			if (!AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode().equals(result.get("status"))) {
				if(!"00".equals(result.get("customerStatus"))){
					LOGGER.error("对不起,该用户已绑定身份证");
					return Response.fail("对不起,该用户已绑定身份证");
				}
			}
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
			LOGGER.error("对不起,该用户不存在!");
			return Response.fail(BusinessErrorCode.CUSTOMER_NOT_EXIST);
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
		String bankCode = CommonUtils.getValue(paramMap, "bankCode");
		String mobile = CommonUtils.getValue(paramMap, "mobile");
		if (StringUtils.isAnyBlank(userId, realName, cardNo, cardBank, bankCode,mobile)) {
			LOGGER.error("参数值错误");
			return Response.fail("参数值错误");
		}
		if(!RegExpUtils.mobile(mobile)){
			return Response.fail("手机号码不正确");
		}
		if (!RegExpUtils.length(realName, 4, 20)) {
			LOGGER.error("真实姓名输入不合法");
			return Response.fail("真实姓名输入不合法");
		}
		Map<String, Object> result = awardActivityInfoService.getBindCardImformation("contractInit",
				Long.valueOf(userId));
		if (result == null || result.size() == 0) {
			LOGGER.error("对不起,该用户不存在!");
			return Response.fail("对不起,该用户不存在!");
		}
		if ("2".equals(result.get("status"))) {
			LOGGER.error("对不起,请先上传身份证");
			return Response.fail("对不起,请先上传身份证");
		}
		if ("0".equals(result.get("status"))) {
			LOGGER.error("对不起,该用户已绑定银行卡");
			return Response.fail("对不起,该用户已绑定银行卡");
		}
		paramMap.put("customerId", result.get("customerId"));
		paramMap.put("identityNo", result.get("identityNo"));
		paramMap.put("mobile", mobile);
		paramMap.put("repaymentDate", result.get("repaymentDate"));
		paramMap.put("monthlyInterestRate", result.get("monthlyInterestRate"));
		paramMap.put("monthlyServiceRate", result.get("monthlyServiceRate"));
		paramMap.put("monthlyPlatformServiceRate", result.get("monthlyPlatformServiceRate"));
		paramMap.put("creditExpire", result.get("creditExpire"));

		Response res1 = awardActivityInfoService.validateBindCard(paramMap);
		if (!"1".equals(res1.getStatus())) {
			return res1;
		} else {
			String s  = GsonUtils.toJson(res1.getData());
			Map <String,Object> m =GsonUtils.convert(s);
			if(!cardBank.equals(m.get("dictName"))){
				LOGGER.error("卡号与所选银行不匹配!");
				return Response.fail("卡号与所选银行不匹配!");
			}
			Response res = awardActivityInfoService.initContract(paramMap);
			if (StringUtils.isEmpty(String.valueOf(res.getData()))) {
				paramMap.put("status", "0");// 没有签名
				return Response.response("1", "请求数据成功", paramMap);
			}
			paramMap.put("status", "1");// 有签名
			paramMap.put("sign", res.getData());
			paramMap.put("customerEntity", res.getMsg());
			return Response.response("1", "请求数据成功", paramMap);
		}
	}
	
	/**
	 * 当用户首次获得额度时，奖励邀请人奖励金
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/saveAwardInfo", method = RequestMethod.POST)
	public void saveAwardInfo(@RequestBody Map<String, Object> paramMap) {
		String customerId = CommonUtils.getValue(paramMap, "customerId");
		try {
			Response response = registerInfoService.customerIsFirstCredit(customerId);
			if (null != response && "1".equals(response.getStatus())) {
				Map<String, Object> resultMap = (Map<String, Object>) response.getData();
				String isFirstCredit = (String) resultMap.get("isFirstCredit");
				String userId = (String) resultMap.get("userId");//被邀请人的userId
				if ("true".contentEquals(isFirstCredit)) {// 如果该用户是第一次获取额度则奖励给他的邀请人
					AwardActivityInfoVo aInfoVo = awardActivityInfoService.getActivityByName(ActivityName.INTRO);
					if (null != aInfoVo) {
						Date aEndDate = DateFormatUtil.string2date(aInfoVo.getaEndDate(), "yyyy-MM-dd HH:mm:ss");
						int falge = aEndDate.compareTo(new Date());
						if (falge > 0) {
							//获取当前活动下邀请人的信息
							AwardBindRel awardBindRel = awardBindRelService.getByInviterUserId(userId,
									Integer.parseInt(aInfoVo.getId().toString()));
							//判断在当前活动下邀请人是否已经获得了被邀请人的奖励
							Map<String, Object> parMap2=new HashMap<>();
							parMap2.put("userId", awardBindRel.getUserId());
							parMap2.put("activityId",awardBindRel.getActivityId());
							parMap2.put("orderId",userId);
							parMap2.put("type","0");
							int result=awardDetailMapper.isAwardSameUserId(parMap2);//已经获得的奖励金额
							if(0==result){
								//获取当前月的第一天和最后一天的时间
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								//获取当前月第一天
								Calendar c = Calendar.getInstance();    
								c.add(Calendar.MONTH, 0);
								c.set(Calendar.DAY_OF_MONTH,1);
								String first = format.format(c.getTime());
								String firstDay=first+" 00:00:00";
								String nowTime=DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
								if(null !=awardBindRel){
									AwardDetailDto awardDetailDto=new AwardDetailDto();
									awardDetailDto.setActivityId(aInfoVo.getId());
									awardDetailDto.setUserId(awardBindRel.getUserId());
									awardDetailDto.setRealName(awardBindRel.getName());
									awardDetailDto.setMobile(awardBindRel.getMobile());
									awardDetailDto.setOrderId(userId);
									awardDetailDto.setType(new Byte("0"));
									awardDetailDto.setStatus(new Byte("0"));
									awardDetailDto.setCreateDate(new Date());
									awardDetailDto.setUpdateDate(new Date());
									
									Map<String, Object> parMap=new HashMap<>();
									parMap.put("userId", awardBindRel.getUserId());
									parMap.put("type", "0");
									parMap.put("applyDate1", DateFormatUtil.string2date(firstDay, "yyyy-MM-dd HH:mm:ss"));
									parMap.put("applyDate2", DateFormatUtil.string2date(nowTime, "yyyy-MM-dd HH:mm:ss"));
									BigDecimal amountAward=awardDetailMapper.queryAmountAward(parMap);//已经获得的奖励金额
									if(amountAward == null){
										amountAward = new BigDecimal(0);
									}
									BigDecimal  awardAmont=new BigDecimal(aInfoVo.getAwardAmont());//即将获得的奖励金额
									BigDecimal amount=awardAmont.add(amountAward);
									if(new BigDecimal("800").compareTo(amount)>0){//总奖励金额小于800，直接插入记录
										awardDetailDto.setTaxAmount(new BigDecimal("0"));
										awardDetailDto.setAmount(awardAmont);
										awardDetailService.addAwardDetail(awardDetailDto);
									}else if(new BigDecimal("800").compareTo(amountAward)>0 && new BigDecimal("800").compareTo(amount)<0){
										BigDecimal more=amount.subtract(new BigDecimal("800"));
										//扣除20%个人所得税后的奖励金额
										BigDecimal  awardAmont2=awardAmont.subtract(more.multiply(new BigDecimal("0.2")));
										awardDetailDto.setTaxAmount(more.multiply(new BigDecimal("0.2")));
										awardDetailDto.setAmount(awardAmont2);
										awardDetailService.addAwardDetail(awardDetailDto);
									}else if(new BigDecimal("800").compareTo(amountAward)<0){
										awardDetailDto.setTaxAmount(awardAmont.multiply(new BigDecimal("0.2")));
										awardDetailDto.setAmount(awardAmont.multiply(new BigDecimal("0.8")));
										awardDetailService.addAwardDetail(awardDetailDto);
									}
								}
							}
						}
					}
				}
			}
		} catch (BusinessException e) {
			LOGGER.error("customerId 用户获得额度时：customerId="+customerId);
		}
	}
	
}
