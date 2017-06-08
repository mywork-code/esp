package com.apass.esp.service.activity;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.common.model.QueryParams;
import com.apass.esp.common.utils.NumberUtils;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardActivityInfoDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.entity.activity.BankEntity;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.EspActivityHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerCreditInfo;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerRateInfo;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AwardActivityInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AwardActivityInfoService.class);

    @Autowired
    public AwardActivityInfoMapper awardActivityInfoMapper;

    @Autowired
    private PaymentHttpClient paymentHttpClient;

    @Autowired
    private CommonHttpClient commonHttpClient;

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
    @Transactional(rollbackFor=Exception.class) 
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
        entity.setaStartDate(DateFormatUtil.string2date(dto.getStartDate(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        entity.setaEndDate(DateFormatUtil.string2date(dto.getEndDate(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
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
     * 编辑活动
     * @param dto
     * @return
     */
    @Transactional(rollbackFor=Exception.class) 
    public Integer editActivity(String id,String rebate,String endDate) {
        AwardActivityInfo awardActivityInfo = new AwardActivityInfo();
        awardActivityInfo.setaEndDate(DateFormatUtil.string2date(endDate, DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        awardActivityInfo.setRebate(BigDecimal.valueOf(Double.valueOf(rebate)/100));
        awardActivityInfo.setId(Long.valueOf(id));
        return awardActivityInfoMapper.updateByPrimaryKeySelective(awardActivityInfo);
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
            vo.setRebate(ai.getRebate().doubleValue()*100+"");
            vo.setUpdateDate(DateFormatUtil.datetime2String(ai.getUpdateDate()));
            result.add(vo);
        }
        if (CollectionUtils.isEmpty(list)) {
            respBody.setTotal(0);
        } else {
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
            throw new BusinessException("未查到指定的活动【" + name.getDesc() + "】",BusinessErrorCode.ACTIVITY_NOT_EXIST);
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
			//CustomerInfo customerInfo =
            Response response = commonHttpClient.getCustomerBasicInfo(requestId,userId);
            if(response==null||!response.statusResult()){
                return null;
            }
            CustomerBasicInfo customerBasicInfo = Response.resolveResult(response,CustomerBasicInfo.class);
			// 客户信息不存在
			if (customerBasicInfo == null) {
				return null;
			}
			resultMap.put("userId", userId);
			resultMap.put("mobile", customerBasicInfo.getMobile());
			resultMap.put("customerId", customerBasicInfo.getCustomerId());
			resultMap.put("identityExpires", customerBasicInfo.getIdentityExpires());
			if(StringUtils.isNotBlank(customerBasicInfo.getRealName())){
				resultMap.put("realName", customerBasicInfo.getRealName());
			}
			// 身份信息未认证
			if (StringUtils.isEmpty(customerBasicInfo.getIdentityNo())) {
				resultMap.put("status", AwardActivity.BIND_STATUS.UNBINDIDENTITY.getCode());
				return resultMap;
			}
			resultMap.put("identityNo", customerBasicInfo.getIdentityNo());
			resultMap.put("customerStatus", customerBasicInfo.getStatus());
            Response responseCredit =  commonHttpClient.getCustomerCreditInfo(requestId,userId);
            if(responseCredit!=null&&responseCredit.statusResult()){
                CustomerCreditInfo customerCreditInfo =  Response.resolveResult(responseCredit,CustomerCreditInfo.class);
                if(customerCreditInfo!=null){
                    resultMap.put("repaymentDate",customerCreditInfo.getRepaymentDate());//还款日
                }
            }

			// 银行卡未绑定
			if (StringUtils.isAnyEmpty(customerBasicInfo.getBankCode(), customerBasicInfo.getCardBank(),
                    customerBasicInfo.getCardType(), customerBasicInfo.getCardNo())) {
				resultMap.put("status", AwardActivity.BIND_STATUS.UNBINDED.getCode());
				return resultMap;
			}
			resultMap.put("status", AwardActivity.BIND_STATUS.BINDED.getCode());
			resultMap.put("cardType", customerBasicInfo.getCardType());
			resultMap.put("cardNo", customerBasicInfo.getCardNo());
			resultMap.put("cardBank", customerBasicInfo.getCardBank());
			resultMap.put("bankCode", customerBasicInfo.getBankCode());
			return resultMap;
		} catch (Exception e) {
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

        BankEntity e8 = new BankEntity();
        e8.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_CCB.getCode());
        e8.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_CCB.getMessage());

        BankEntity e9 = new BankEntity();
        e9.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_BOC.getCode());
        e9.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_BOC.getMessage());

        BankEntity e10 = new BankEntity();
        e10.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_CMB.getCode());
        e10.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_CMB.getMessage());

        BankEntity e11 = new BankEntity();
        e11.setBankCode(AwardActivity.BANK_ENTITY.BANKLIST_ABC.getCode());
        e11.setBankName(AwardActivity.BANK_ENTITY.BANKLIST_ABC.getMessage());
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);
        list.add(e6);
        list.add(e7);
        list.add(e8);
        list.add(e9);
        list.add(e10);
        list.add(e11);
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
