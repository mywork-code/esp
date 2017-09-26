package com.apass.esp.service;

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
		return groupGoodsMapper.selectByGoodsId(goodsId);
	}
	
	public Integer insertSelective(ProGroupGoods proGroupGoods){
		return groupGoodsMapper.insertSelective(proGroupGoods);
	}


}
