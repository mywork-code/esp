package com.apass.esp.service.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	public Boolean isLimitByGoodsId(String skuId){
		LimitGoodsSku entity =new LimitGoodsSku();
		entity.setSkuId(skuId);
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
	 * 根据商品id查询限时购的相关信息
	 * 当返回值为null时，代表该商品没有参加限时购或者参加了已经结束
	 * @return
	 */
	public LimitGoodsSku selectLimitByGoodsId(String skuId) {
		LimitGoodsSku lgs = null;

		LimitGoodsSku entity = new LimitGoodsSku();
		entity.setSkuId(skuId);
		List<LimitGoodsSku> LimitGoodsSkuList = limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
		if (null == LimitGoodsSkuList || LimitGoodsSkuList.size() == 0) {
			return null;
		}
		TreeMap<Integer, Object> mapOn = new TreeMap<>();//正在进行中的活动
		TreeMap<Integer, Object> mapNo = new TreeMap<>();//还没有开始的活动

		for (LimitGoodsSku limitGoodsSku : LimitGoodsSkuList) {
			LimitBuyAct limitBuyAct = limitBuyActMapper.selectByPrimaryKey(limitGoodsSku.getLimitBuyActId());
			ActivityStatus activityStatus = getLimitBuyStatus(limitBuyAct.getStartDate(), limitBuyAct.getEndDate());
			if (ActivityStatus.PROCESSING == activityStatus) {
				long time = new Date().getTime() - limitBuyAct.getStartDate().getTime();
				limitGoodsSku.setTime((int) time);
				mapOn.put((int) time, limitGoodsSku);
			}
			if (ActivityStatus.NO == activityStatus) {
				long time = limitBuyAct.getStartDate().getTime() - new Date().getTime();
				limitGoodsSku.setTime((int) time);
				mapNo.put((int) time, limitGoodsSku);
			}
		}
		if (!mapOn.isEmpty()) {
			lgs = (LimitGoodsSku) mapOn.get(mapOn.firstKey());
		} else {
			lgs = (LimitGoodsSku) mapNo.get(mapNo.firstKey());
		}
		return lgs;
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
