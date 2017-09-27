package com.apass.esp.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.mapper.ProGroupGoodsMapper;
import com.apass.esp.service.offer.ActivityCfgService;

/**
 * Created by jie.xu on 17/9/26.
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class ProGroupGoodsService {
  @Autowired
  private ProGroupGoodsMapper groupGoodsMapper;

  @Autowired
  private ActivityCfgService activityCfgService;

  public ProGroupGoodsBo getByGoodsId(Long goodsId){
    ProGroupGoods groupGoods =  groupGoodsMapper.selectLatestByGoodsId(goodsId);
    if(groupGoods == null){
      return null;
    }

      ProActivityCfg activityCfg = activityCfgService.getById(groupGoods.getActivityId());
      if(activityCfg == null){
        return null;
      }
      ProGroupGoodsBo bo = new ProGroupGoodsBo();
      bo.setActivityId(groupGoods.getActivityId());
      bo.setActivityPrice(groupGoods.getActivityPrice());
      bo.setGoodsId(goodsId);
      bo.setValidActivity(ActivityStatus.PROCESSING == activityCfgService.getActivityStatus(activityCfg));
      return bo;
  }
	public ProGroupGoods selectByGoodsId(Long goodsId){
		return groupGoodsMapper.selectLatestByGoodsId(goodsId);
	}
	
	public Integer insertSelective(ProGroupGoods proGroupGoods){
		return groupGoodsMapper.insertSelective(proGroupGoods);
	}

  /**
   * 判断商品活动是否失效
   */
  public ActivityStatus isValidActivity(String activityId,Long goodsId){
    if(StringUtils.isEmpty(activityId)){
      return ActivityStatus.NO;
    }
    ProGroupGoods groupGoods = groupGoodsMapper.selectByGoodsIdAndActivityId(goodsId,Long.valueOf(activityId));
    if(groupGoods == null){
      return ActivityStatus.NO;
    }
    ProActivityCfg activityCfg = activityCfgService.getById(groupGoods.getActivityId());
    if(activityCfg == null){
      return ActivityStatus.NO;
    }
    return activityCfgService.getActivityStatus(activityCfg);
  }
}
