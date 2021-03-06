package com.apass.esp.service.coffers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.vo.AwardDetailVo;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;


/**
 * @description 我的金库
 *
 * @author xiaohai
 * @version $Id: CoffersBaseService.java, v 0.1 2017年6月6日 下午3:30:22 xiaohai Exp $
 */
@Service
@Transactional
public class CoffersBaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffersBaseService.class);
    @Autowired
    private AwardBindRelMapper awardBindRelMapper;
    @Autowired
    private AwardDetailMapper awardDetailMapper;
    
    /**
     * 我的金库
     * @param userId:用户Id
     * @return
     */
    public Map<String, Object> queryCoffers(String userId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        //邀请人数量
        Integer count = awardBindRelMapper.selectCountByUserId(Long.valueOf(userId));
        resultMap.put("inviteCount", count);
        LOGGER.info("用户Id:{},邀请人数:{}",userId,count);
        
        //根据userId查询金库明细
        List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(Long.valueOf(userId));
        LOGGER.info("我的的金库详细信息：{}",GsonUtils.toJson(awardDetails));
        List<AwardDetailVo>  awardDetailVos = new ArrayList<>();
        
        BigDecimal totalCoun = BigDecimal.ZERO;
        if(awardDetails != null && awardDetails.size()>0){
           for (AwardDetail awardDetail : awardDetails) {
               AwardDetailVo awardDetailVo = new AwardDetailVo();
               awardDetailVo.setUserId(awardDetail.getUserId());
               awardDetailVo.setAmount(awardDetail.getAmount());
               awardDetailVo.setType(awardDetail.getType());
               awardDetailVo.setStatus(awardDetail.getStatus());
               awardDetailVo.setArrivedDate(DateFormatUtil.dateToString(awardDetail.getArrivedDate()));
               awardDetailVo.setCreateDate(DateFormatUtil.dateToString(awardDetail.getCreateDate()));
               awardDetailVo.setDate(awardDetailVo.getArrivedDate()==null ? awardDetailVo.getArrivedDate():awardDetailVo.getCreateDate());
               awardDetailVos.add(awardDetailVo);
               if(awardDetail.getType() == AwardActivity.AWARD_TYPE.GAIN.getCode() && awardDetail.getStatus() == AwardActivity.AWARD_STATUS.SUCCESS.getCode()){
                   totalCoun = totalCoun.add(awardDetail.getAmount());
               }else if(awardDetail.getType() == AwardActivity.AWARD_TYPE.WITHDRAW.getCode()){
                   totalCoun = totalCoun.subtract(awardDetail.getAmount());
               }
           }
        }
        LOGGER.info("用户Id：{}，用户赏金：{}",userId,totalCoun);
        resultMap.put("awardDetails", awardDetailVos);
        resultMap.put("totalCoun",totalCoun);
        
       return resultMap;
    }
   
}
