package com.apass.esp.service.withdraw;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;



/**
 * @description 提现
 *
 * @author xiaohai
 * @version $Id: WithdrawService.java, v 0.1 2017年6月8日 上午9:29:43 xiaohai Exp $
 */
@Service
@Transactional
public class WithdrawService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawService.class);
    @Autowired
    private AwardDetailMapper awardDetailMapper;
    @Autowired
    private AwardActivityInfoService awardActivityInfoService;
    
    
    /**
     * 根据用户Id查询提现页面
     * @param userId
     * @return
     */
    public Map<String, Object> queryWithdrawByUserId(String userId) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        //查询用户是否绑卡
        String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
        Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
        LOGGER.info("是否绑卡查询结果：{}",GsonUtils.toJson(result));
        if (result == null || result.size() == 0) {
            LOGGER.info("该用户不存在,userId:{}",userId);
            throw new RuntimeException("对不起,该用户不存在!");
        }
        if(AwardActivity.BIND_STATUS.BINDED.getCode().equals(result.get("status"))){
            paramMap.put("page", "1");//已绑卡
            String cardNo = (String)result.get("cardNo");
            paramMap.put("cardNo",cardNo);//银行卡号后4位
            paramMap.put("cardNoLastFour",cardNo.substring(cardNo.length()-4, cardNo.length()));//银行卡号后4位
            paramMap.put("cardBank",result.get("cardBank"));//银行名称
            paramMap.put("bankCode",result.get("bankCode"));//银行code
            
            //查询全部可提金额金额
            List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(Long.valueOf(userId));
            BigDecimal totalCount = BigDecimal.ZERO;
            if(awardDetails != null && awardDetails.size()>0){
                totalCount = getTotalCount(awardDetails);
            }
            paramMap.put("totalCount",totalCount);//赏金 ，全部提现金额
        }else{
            paramMap.put("page","0");//未绑卡
            paramMap.put("mobile", result.get("mobile"));//手机号
            paramMap.put("identityNo", result.get("identityNo"));//身份证号码
            paramMap.put("identityExpires", result.get("identityExpires"));//身份证有效期
        }
        
        return paramMap;
    }

    /**
     * 计算全部可提现金额
     * @param awardDetails
     * @return
     */
    public BigDecimal getTotalCount(List<AwardDetail> awardDetails){
        BigDecimal totalCount = BigDecimal.ZERO;
        for (AwardDetail awardDetail : awardDetails) {
            if(awardDetail.getType() == AwardActivity.AWARD_TYPE.GAIN.getCode()){
                totalCount = totalCount.add(awardDetail.getAmount());
            }else if(awardDetail.getType() == AwardActivity.AWARD_TYPE.WITHDRAW.getCode()){
                totalCount = totalCount.subtract(awardDetail.getAmount());
            }
        }
        return totalCount;
    }

    /**
     * 确认提现：往数据库插入一条数据
     * @param cardNo:银行卡号 
     * @param cardBank：银行名称
     * @param userId:用户Id
     * @param amount:金额
     * @return
     * @throws BusinessException 
     */
    public Integer confirmWithdraw(String userId, String amount, String cardBank, String cardNo) throws BusinessException {
        AwardDetail awardDetail = new AwardDetail();
        awardDetail.setAmount(BigDecimal.valueOf(Long.valueOf(amount)));
        awardDetail.setUserId(Long.valueOf(userId));
        AwardActivityInfoVo awardActivityInfoVo = awardActivityInfoService.getActivityByName(AwardActivity.ActivityName.INTRO);
        LOGGER.info("活动详细信息：[{}]",GsonUtils.toJson(awardActivityInfoVo));
        //awardActivityInfoVo不会为空，否则方法内部会抛异常
        awardDetail.setActivityId(awardActivityInfoVo.getId());
        awardDetail.setType((byte) AwardActivity.AWARD_TYPE.WITHDRAW.getCode());
        awardDetail.setStatus((byte) AwardActivity.AWARD_STATUS.PROCESSING.getCode());
        awardDetail.setMainOrderId(null);
        awardDetail.setArrivedDate(null);
        awardDetail.setCardBank(cardBank);
        awardDetail.setCardNo(cardNo);
        awardDetail.setCreateDate(new Date());
        awardDetail.setUpdateDate(new Date());
        
        //获取扣税金额
        BigDecimal taxAmount = getTaxAmount(userId,amount);
        awardDetail.setTaxAmount(taxAmount);
        
        int count = awardDetailMapper.insert(awardDetail);
        
        return count;
    }

    /**
     * 获取扣税金额
     * @param userId
     * @param amount
     * @return
     */
    private BigDecimal getTaxAmount(String userId, String amount) {
        Date beginMonthDay = getBeginMonthDay();
        List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetailWithDate(Long.valueOf(userId),
                DateFormatUtil.dateToString(beginMonthDay, DateFormatUtil.YYYY_MM_DD_HH_MM_SS),DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        LOGGER.info("用户:{}本月提现详情:{}",userId,GsonUtils.toJson(awardDetails));
        BigDecimal totolAmount = BigDecimal.valueOf(Long.valueOf(amount));
        BigDecimal taxAmount = BigDecimal.ZERO;
        for (AwardDetail awardDetail : awardDetails) {
            if(awardDetail.getType() == AwardActivity.AWARD_TYPE.WITHDRAW.getCode()){
                totolAmount = totolAmount.add(awardDetail.getAmount());
            }
        }
        if(totolAmount.compareTo(BigDecimal.valueOf(800))>0){
            taxAmount = (totolAmount.subtract(BigDecimal.valueOf(800))).multiply(BigDecimal.valueOf(0.2));
        }
        LOGGER.info("用户:{}本月提现总额:{}，需缴个税：{}",userId,totolAmount,taxAmount);
        return taxAmount;
    }

    /**
     * 获取本月开始时间
     * @return
     */
    private Date getBeginMonthDay() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        
        return DateFormatUtil.date2date(cale.getTime(), DateFormatUtil.YYYY_MM_DD);
    } 
   
}
