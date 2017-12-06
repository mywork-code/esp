package com.apass.esp.service.activity;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.mapper.LimitBuyActMapper;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
/**
 * 对限时购的公共操作
 * @author aopai
 *
 */
@Service
public class LimitCommonService {
	  @Autowired
	  public LimitGoodsSkuMapper limitGoodsSkuMapper;
	  @Autowired
	  public LimitBuyActMapper limitBuyActMapper;
	/**
	 * 根据goodsId判断是否是限时购商品
	 * @param goodsId
	 * @return
	 */
	public Boolean isLimitByGoodsId(Long goodsId){
		LimitGoodsSku entity =new LimitGoodsSku();
		entity.setGoodsId(goodsId);
		List<LimitGoodsSku>  LimitGoodsSkuList=limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
		if(null ==LimitGoodsSkuList ||  LimitGoodsSkuList.size()==0 ){
			return false;
		}
		for (LimitGoodsSku limitGoodsSku : LimitGoodsSkuList) {
			LimitBuyAct limitBuyAct=limitBuyActMapper.selectByPrimaryKey(limitGoodsSku.getLimitBuyActId());
			ActivityStatus activityStatus =getLimitBuyStatus(limitBuyAct.getStartDate(),limitBuyAct.getEndDate());
			if(ActivityStatus.PROCESSING==activityStatus || ActivityStatus.NO==activityStatus){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断限时购活动的状态
	 * @param cfg
	 * @return
	 */
	public ActivityStatus getLimitBuyStatus(Date startTime, Date endTime){
		Date now = new Date();
		if(null == startTime || null == endTime){
			return ActivityStatus.NO;
		}
		if(startTime.getTime() > now.getTime()){
			return ActivityStatus.NO;
		}
		
		if(startTime.getTime() <= now.getTime() && endTime.getTime() >= now.getTime()){
			return ActivityStatus.PROCESSING;
		}
	    return ActivityStatus.END;
	}
}
