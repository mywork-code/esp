package com.apass.esp.service.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.repository.httpClient.EspActivityHttpClient;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.utils.BeanUtils;
import com.apass.gfb.framework.exception.BusinessException;

@Service
public class AwardActivityInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardActivityInfoService.class);

	@Autowired
	public AwardActivityInfoMapper awardActivityInfoMapper;

	@Autowired
	private PaymentHttpClient paymentHttpClient;

	@Autowired
	private EspActivityHttpClient espActivityHttpClient;

	/**
	 * 添加活动
	 * 
	 * @param awardActivityInfo
	 * @return
	 */
	public long addActivity(AwardActivityInfoDto awardActivitydto) {
		AwardActivityInfo awardActivityInfo= new AwardActivityInfo();
		BeanUtils.copyProperties(awardActivityInfo, awardActivitydto);
		awardActivityInfoMapper.insert(awardActivityInfo);
		return awardActivityInfo.getId();
	}

	/**
	 * 查询用户是否绑卡及绑卡信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getBindCardImformation(String requestId, long userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = paymentHttpClient.getCustomerInfo(requestId, userId);
			// 客户信息不存在
			if (customerInfo == null) {
				return null;
			}
			resultMap.put("userId", userId);
			resultMap.put("mobile", customerInfo.getMobile());
			resultMap.put("customerId", customerInfo.getCustomerId());
			// 身份信息未认证
			if (StringUtils.isEmpty(customerInfo.getIdentityNo())) {
				resultMap.put("status", AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode());
				return resultMap;
			}
			resultMap.put("identityNo", customerInfo.getIdentityNo());
			// 银行卡未绑定
			if (StringUtils.isAnyEmpty(customerInfo.getBankCode(), customerInfo.getCardBank(),
					customerInfo.getCardType(), customerInfo.getCardNo())) {
				resultMap.put("status", AwardActivity.BIND_STATUS.UNBINDED.getCode());
				return resultMap;
			}
			resultMap.put("status", AwardActivity.BIND_STATUS.BINDED.getCode());
			resultMap.put("cardType", customerInfo.getCardType());
			resultMap.put("cardNo", customerInfo.getCardNo());
			resultMap.put("cardBank", customerInfo.getCardBank());
			resultMap.put("bankCode", customerInfo.getBankCode());
			return resultMap;
		} catch (BusinessException e) {
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 验卡是否本人 以及是否支持该银行
	 * 
	 * @param map
	 * @return
	 */
	public Response validateBindCard(Map<String, Object> map) {
		Response res = espActivityHttpClient.validateBindCard(map);
		return res;
	}

	/**
	 * 绑卡
	 * 
	 * @param map
	 * @return
	 */
	public Response bindCard(Map<String, Object> map) {
		Response res = espActivityHttpClient.bindCard(map);
		return res;
	}

	/**
	 * 银行卡列表
	 * 
	 * @param map
	 * @return
	 */
	public Response getBankList(Map<String, Object> map) {
		Response res = espActivityHttpClient.getBankList(map);
		return res;
	}

	/**
	 * 上传并解析身份证 返回身份证号码
	 * 
	 * @param map
	 * @return
	 */
	public Response identityReconize(Map<String, Object> map) {
		Response res = espActivityHttpClient.identityReconize(map);
		return res;
	}

}
