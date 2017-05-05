package com.apass.esp.service.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.common.utils.NumberUtils;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.entity.activity.BankEntity;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.repository.httpClient.EspActivityHttpClient;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;

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
	 * 根据活动ID得到活动
	 * 
	 * @param id
	 * @return
	 */
	public AwardActivityInfo getAwardActivityInfoById(long id) {
		return awardActivityInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 活动设置无效
	 */
	public void updateUneffectiveActivity(Long activityId, String updateBy) {
		AwardActivityInfo entity = new AwardActivityInfo();
		entity.setUpdateDate(new Date());
		entity.setUpdateBy(updateBy);
		entity.setId(activityId);
		entity.setStatus((byte) AwardActivity.ACTIVITY_STATUS.UNEFFECTIVE.getCode());
		awardActivityInfoMapper.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 添加活动
	 * 
	 * @return
	 */
	public AwardActivityInfo addActivity(AwardActivityInfoDto dto) {
		AwardActivityInfo entity = new AwardActivityInfo();
		entity.setActivityName(AwardActivity.ActivityName.INTRO.getValue());
		entity.setaStartDate(DateFormatUtil.string2date(dto.getStartDate()));
		entity.setaEndDate(DateFormatUtil.string2date(dto.getEndDate()));
		entity.setRebate(NumberUtils.divide100(dto.getRebate()));
		entity.setType((byte) AwardActivity.ACTIVITY_TYPE.PERSONAL.getCode());
		entity.setStatus((byte) AwardActivity.ACTIVITY_STATUS.EFFECTIVE.getCode());
		entity.setCreateBy(dto.getCreateBy());
		entity.setUpdateBy(dto.getCreateBy());
		entity.setCreateDate(new Date());
		entity.setUpdateDate(new Date());
		awardActivityInfoMapper.insert(entity);
		return entity;
	}


	/**
	 * 获取活动
	 * 
	 * @return
	 */
	public ResponsePageBody<AwardActivityInfoVo> listActivity(QueryParams query) {
		ResponsePageBody<AwardActivityInfoVo> respBody = new ResponsePageBody<>();
		List<AwardActivityInfo> list = awardActivityInfoMapper.pageEffectiveList(query);
		List<AwardActivityInfoVo> result = new ArrayList<>();
		for (AwardActivityInfo ai : list) {
			AwardActivityInfoVo vo = new AwardActivityInfoVo();
			vo.setId(ai.getId());
			vo.setActivityName(ai.getActivityName());
			vo.setaStartDate(DateFormatUtil.datetime2String(ai.getaStartDate()));
			vo.setaEndDate(DateFormatUtil.datetime2String(ai.getaEndDate()));
			vo.setRebate(NumberUtils.multiply100(ai.getRebate()) + "%");
			vo.setUpdateDate(DateFormatUtil.datetime2String(ai.getUpdateDate()));
			result.add(vo);
		}
		if(CollectionUtils.isEmpty(list)){
			respBody.setTotal(0);
		}else{
			respBody.setTotal(awardActivityInfoMapper.count());
		}
		respBody.setRows(result);
		respBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return respBody;

	}

	public boolean validateAwardActivity() {

		return false;
	}

	public boolean isExistActivity(AwardActivity.ActivityName name) {
		AwardActivityInfo ai = awardActivityInfoMapper.selectByName(name.getValue());
		return ai != null;
	}

	/**
	 * 通过活动名称获得指定活动
	 */
	public AwardActivityInfoVo getActivityByName(AwardActivity.ActivityName name) throws BusinessException {
		AwardActivityInfo ai = awardActivityInfoMapper.selectByName(name.getValue());
		if (ai == null) {
			throw new BusinessException("未查到指定的活动【" + name.getDesc() + "】");
		}
		AwardActivityInfoVo vo = new AwardActivityInfoVo();
		vo.setId(ai.getId());
		vo.setActivityName(ai.getActivityName());
		vo.setaStartDate(DateFormatUtil.datetime2String(ai.getaStartDate()));
		vo.setaEndDate(DateFormatUtil.datetime2String(ai.getaEndDate()));
		vo.setRebate(NumberUtils.multiply100(ai.getRebate()) + "%");
		return vo;
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
			resultMap.put("identityExpires", customerInfo.getIdentityExpires());
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
		    LOGGER.error("查询用户是否绑卡及绑卡信息", e);
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
	public Map<String, Object> getBankList() {
		List<BankEntity> list = new ArrayList<BankEntity>();
		BankEntity e1 = new BankEntity();
		e1.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_ICBC.getCode());
		e1.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_ICBC.getMessage());
		BankEntity e2 = new BankEntity();
		e2.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_CMBC.getCode());
		e2.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_CMBC.getMessage());
		BankEntity e3 = new BankEntity();
		e3.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_CEB.getCode());
		e3.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_CEB.getMessage());
		BankEntity e4 = new BankEntity();
		e4.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_GDB.getCode());
		e4.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_GDB.getMessage());
		BankEntity e5 = new BankEntity();
		e5.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_CITIC.getCode());
		e5.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_CITIC.getMessage());
		BankEntity e6 = new BankEntity();
		e6.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_CIB.getCode());
		e6.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_CIB.getMessage());
		BankEntity e7 = new BankEntity();
		e7.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_PAB.getCode());
		e7.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_PAB.getMessage());
		list.add(e1);
		list.add(e2);
		list.add(e3);
		list.add(e4);
		list.add(e5);
		list.add(e6);
		list.add(e7);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankList", list);
		map.put("bankTotal", list.size());
		return map;
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

	/**
	 * 保存合同
	 * 
	 * @param map
	 * @return
	 */
	public Response saveContract(Map<String, Object> map) {
		Response res = espActivityHttpClient.saveContract(map);
		return res;
	}

	/**
	 * 初始化
	 * 
	 * @param map
	 * @return
	 */
	public Response initContract(Map<String, Object> map) {
		Response res = espActivityHttpClient.initContract(map);
		return res;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public Response latestSignature(Map<String, Object> map) {
		Response res = espActivityHttpClient.latestSignature(map);
		return res;
	}
}
