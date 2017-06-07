package com.apass.esp.service.withdraw;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.vo.AwardDetailVo;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;


/**
 * @description 我的金库
 *
 * @author xiaohai
 * @version $Id: CoffersBaseService.java, v 0.1 2017年6月6日 下午3:30:22 xiaohai Exp $
 */
@Service
@Transactional
public class WithdrawService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawService.class);
    @Autowired
    private AwardBindRelMapper awardBindRelMapper;
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
        
        //查询用户是否绑卡
        String requestId = AwardActivity.AWARD_ACTIVITY_METHOD.BINDCARD.getCode() + "_" + userId;
        Map<String, Object> result = awardActivityInfoService.getBindCardImformation(requestId, Long.valueOf(userId));
        LOGGER.info("是否绑卡查询结果：{}",GsonUtils.toJson(result));
        if (result == null || result.size() == 0) {
            LOGGER.info("该用户不存在,userId:{}",userId);
            throw new RuntimeException("对不起,该用户不存在!");
        }
        if(AwardActivity.BIND_STATUS.BINDED.getCode().equals(result.get("status"))){
            result.put("page", "1");//已绑卡
        }else{
            result.put("page","0");//未绑卡
        }
        result.put("cardNo",result.get("cardNo"));//银行卡号
        result.put("cardBank",result.get("cardBank"));//银行名称
        
        //查询全部可提金额金额
        List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(Long.valueOf(userId));
        BigDecimal totalCoun = BigDecimal.ZERO;
        if(awardDetails != null && awardDetails.size()>0){
            for (AwardDetail awardDetail : awardDetails) {
                if(awardDetail.getType() == 0){
                    totalCoun = totalCoun.add(awardDetail.getAmount());
                }else if(awardDetail.getType() == 1){
                    totalCoun = totalCoun.subtract(awardDetail.getAmount());
                }
            }
        }
        result.put("totalCoun",totalCoun);//赏金 ，全部提现金额
        
        return result;
    }
   
}
