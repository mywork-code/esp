package com.apass.esp.service.offer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.esp.mapper.ProGroupGoodsMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;

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
	 * 获取活动配置信息
	 * @param query
	 * @return
	 * @throws BusinessException 
	 */
	public ResponsePageBody<ProGroupGoodsVo> getProGroupGoodsListPage(ProGroupGoodsQuery query) throws BusinessException{
		ResponsePageBody<ProGroupGoodsVo> pageBody = new ResponsePageBody<ProGroupGoodsVo>();
		List<ProGroupGoodsVo> configList = groupGoodsMapper.getProGroupGoodsListPage(query);
		Integer count = groupGoodsMapper.getProGroupGoodsListPageCount(query);
		
		pageBody.setTotal(count);
		pageBody.setRows(configList);
		pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return pageBody;
	}


}
