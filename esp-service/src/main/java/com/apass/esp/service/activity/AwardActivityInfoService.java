package com.apass.esp.service.activity;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AwardActivityInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardActivityInfoService.class);

	@Autowired
	public AwardActivityInfoMapper awardActivityInfoMapper;

	@Autowired
	private PaymentHttpClient paymentHttpClient;

	/**
	 * 添加活动
	 * 
	 * @return
	 */
	public AwardActivityInfo addActivity(AwardActivityInfoDto dto) {
		AwardActivityInfo entity = new AwardActivityInfo();
		entity.setActivityName(dto.getName());

		awardActivityInfoMapper.insert(entity);
		return entity;
	}

	/**
	 * 查询用户是否绑卡及绑卡信息
	 * 
	 * @param userId
	 * @return
	 */
	public String getBindCardImformation(String requestId, long userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = paymentHttpClient.getCustomerInfo(requestId, userId);
			// 客户信息不存在
			if (customerInfo == null) {
				return null;
			}
			// 未绑定
			if (StringUtils.isAnyEmpty(customerInfo.getBankCode(), customerInfo.getCardBank(),
					customerInfo.getCardType(), customerInfo.getCardNo())) {
				resultMap.put("userId", userId);
				resultMap.put("customerId", customerInfo.getCustomerId());
				resultMap.put("status", AwardActivity.BIND_STATUS.UNBINDED.getCode());
				return GsonUtils.toJson(resultMap);
			}
			resultMap.put("status", AwardActivity.BIND_STATUS.BINDED.getCode());
			resultMap.put("userId", userId);
			resultMap.put("customerId", customerInfo.getCustomerId());
			resultMap.put("cardType", customerInfo.getCardType());
			resultMap.put("cardNo", customerInfo.getCardNo());
			resultMap.put("cardBank", customerInfo.getCardBank());
			resultMap.put("bankCode", customerInfo.getBankCode());
			return GsonUtils.toJson(resultMap);
		} catch (BusinessException e) {
			return null;
		}
	}

	/**
	 * 验卡是否本人 以及是否支持该银行
	 * 
	 * @param map
	 * @return
	 */
	public Response validateBindCard(Map<String, Object> map) {
		Response res = paymentHttpClient.validateBindCard(map);
		return res;
	}

	// 绑卡
	public Response bindCard(Map<String, Object> map) {
		Response res = paymentHttpClient.bindCard(map);
		return res;
	}

}
