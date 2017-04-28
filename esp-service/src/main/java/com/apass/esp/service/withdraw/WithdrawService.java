package com.apass.esp.service.withdraw;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;



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
            paramMap.put("cardNo",cardNo.substring(cardNo.length()-4, cardNo.length()));//银行卡号后4位
            paramMap.put("cardBank",result.get("cardBank"));//银行名称
            
            //查询全部可提金额金额
            List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(Long.valueOf(userId));
            BigDecimal totalCount = BigDecimal.ZERO;
            if(awardDetails != null && awardDetails.size()>0){
                totalCount = getTotalCount(awardDetails);
            }
            paramMap.put("totalCount",totalCount);//赏金 ，全部提现金额
        }else{
            paramMap.put("page","0");//未绑卡
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
     * 确认提现：往数据库投入一条数据
     * @param userId
     * @param amount
     * @return
     */
    public Integer confirmWithdraw(String userId, String amount) {
        AwardDetail awardDetail = new AwardDetail();
        
        
        return null;
    }
   
}
