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
import com.apass.esp.domain.vo.AwardDetailVo;
import com.apass.esp.mapper.AwardActivityInfoMapper;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
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
     * 去的订单
     * @param userId:用户Id
     * @return
     */
    public Map<String, Object> queryCoffers(String userId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        //邀请人数量
        Integer count = awardBindRelMapper.selectCountByUserId(Long.valueOf(userId));
        resultMap.put("inviteCount ", count);
        LOGGER.info("用户Id:{},邀请人数:{}",userId,count);
        
        //根据userId查询金库明细
        List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(Long.valueOf(userId));
        List<AwardDetailVo>  awardDetailVos = new ArrayList<>();
        
        BigDecimal totalCoun = BigDecimal.ZERO;
        if(awardDetails != null && awardDetails.size()>0){
           for (AwardDetail awardDetail : awardDetails) {
               AwardDetailVo awardDetailVo = new AwardDetailVo();
               awardDetailVo.setUserId(awardDetail.getUserId());
               awardDetailVo.setAmount(awardDetail.getAmount());
               awardDetailVo.setType(awardDetail.getType());
               awardDetailVos.add(awardDetailVo);
               if(awardDetail.getType() == 0){
                   totalCoun = totalCoun.add(awardDetail.getAmount());
               }else if(awardDetail.getType() == 1){
                   totalCoun = totalCoun.subtract(awardDetail.getAmount());
               }
           }
        }
        resultMap.put("awardDetails", awardDetailVos);
        resultMap.put("totalCoun",totalCoun);
        
       return resultMap;
    }
   
}
