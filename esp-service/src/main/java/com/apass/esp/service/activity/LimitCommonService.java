package com.apass.esp.service.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitBuyDetail;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.activity.LimitGoodsSkuVo;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.vo.LimitBuyParam;
import com.apass.esp.mapper.LimitBuyActMapper;
import com.apass.esp.mapper.LimitBuyDetailMapper;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
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
	@Autowired
	private LimitBuyDetailMapper buydetailMapper;
	@Autowired
	private GoodsStockInfoRepository goodsStockDao;
	@Autowired
	private GoodsRepository goodsDao;
	/**
	 * 根据goodsId判断是否是限时购商品
	 * @param goodsId
	 * @return
	 */
	public Boolean isLimitByGoodsId(String skuId){
		LimitGoodsSku entity =new LimitGoodsSku();
		entity.setSkuId(skuId);
		entity.setUpLoadStatus((byte)1);
		List<LimitGoodsSku>  LimitGoodsSkuList=limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
		if(null ==LimitGoodsSkuList ||  LimitGoodsSkuList.size()==0 ){
			return false;
		}
		for (LimitGoodsSku limitGoodsSku : LimitGoodsSkuList) {
			LimitBuyAct limitBuyAct=limitBuyActMapper.selectByPrimaryKey(limitGoodsSku.getLimitBuyActId());
			if(limitBuyAct==null){
			    continue;
			}
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
	 * 1.首先判断该商品存在多少个进行的活动中或者还未开始的活动中
	 * 2.将活动开始时间与服务器时间的时间差（）
	 * @return
	 */
	public LimitGoodsSkuVo selectLimitByGoodsId(String userId, String skuId) {
		LimitGoodsSkuVo lgs = null;
		if (StringUtils.isBlank(skuId)) {
			return null;
		}
		LimitGoodsSku entity = new LimitGoodsSku();
		entity.setSkuId(skuId);
		entity.setUpLoadStatus((byte) 1);
		List<LimitGoodsSku> LimitGoodsSkuList = limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
		if (null == LimitGoodsSkuList || LimitGoodsSkuList.size() == 0) {
			return null;
		}
		TreeMap<Long, Object> mapOn = new TreeMap<>();// 正在进行中的活动
		TreeMap<Long, Object> mapNo = new TreeMap<>();// 还没有开始的活动

		for (LimitGoodsSku limitGoodsSku : LimitGoodsSkuList) {
			// 判断限时购当前剩余数量
			if (limitGoodsSku.getLimitCurrTotal() <= 0) {
				continue;
			}
			LimitBuyAct limitBuyAct = limitBuyActMapper.selectByPrimaryKey(limitGoodsSku.getLimitBuyActId());
			if (null == limitBuyAct) {
				continue;
			}
			ActivityStatus activityStatus = getLimitBuyStatus(limitBuyAct.getStartDate(), limitBuyAct.getEndDate());
			LimitGoodsSkuVo limitGoodsSkuVo = getLimitGoodsSkuToLimitGoodsSkuVo(limitGoodsSku);
			limitGoodsSkuVo.setLimitBuyActId(limitBuyAct.getId());
			limitGoodsSkuVo.setStartTime(limitBuyAct.getStartDate());
			limitGoodsSkuVo.setEndTime(limitBuyAct.getEndDate());

			/**
			 * 根据限时购的活动ID和用户ID和skuID,查询某一用户在某一活动下，购买某一件商品的数量
			 */
			if (StringUtils.isNoneBlank(userId)) {
				LimitBuyParam limitBuyParam = new LimitBuyParam();
				limitBuyParam.setUserId(userId);
				limitBuyParam.setLimitBuyActId(limitBuyAct.getId() + "");
				limitBuyParam.setSkuId(limitGoodsSku.getId() + "");
				List<LimitBuyDetail> buyDetails = buydetailMapper.getUserBuyGoodsNum(limitBuyParam);
				/**
				 * 计算用户购买了同一个活动同一商品的件数
				 */
				long goodsSum = 0l;
				long limitPersonNum = 01;
				for (LimitBuyDetail limitBuydetail : buyDetails) {
					goodsSum += limitBuydetail.getBuyNo();
				}
				limitPersonNum = limitGoodsSkuVo.getLimitNum() - goodsSum;
				if (limitPersonNum > 0) {
					limitGoodsSkuVo.setLimitPersonNum(limitPersonNum);
				} else {
					limitGoodsSkuVo.setLimitPersonNum(0l);
				}
			}
			if (ActivityStatus.PROCESSING == activityStatus) {
				long time = new Date().getTime() - limitBuyAct.getStartDate().getTime();// 已经开始了多少时间
				long time2 = limitBuyAct.getEndDate().getTime() - new Date().getTime();// 离结束还有多少时间
				limitGoodsSkuVo.setTime(time2);
				limitGoodsSkuVo.setLimitFalg("InProgress");
				mapOn.put(time, limitGoodsSkuVo);
			}
			if (ActivityStatus.NO == activityStatus) {
				long time = limitBuyAct.getStartDate().getTime() - new Date().getTime();// 离限时购开始还有多少时间
				limitGoodsSkuVo.setTime(time);
				limitGoodsSkuVo.setLimitFalg("NotBeginning");
				mapNo.put(time, limitGoodsSkuVo);
			}
		}
		if (!mapOn.isEmpty()) {
			lgs = (LimitGoodsSkuVo) mapOn.get(mapOn.firstKey());
		}
		if (null == lgs && !mapNo.isEmpty()) {
			lgs = (LimitGoodsSkuVo) mapNo.get(mapNo.firstKey());
		}
		return lgs;
	}

	/**
	 * 根据商品id查询限时购的相关信息 当返回值为null时，代表该商品没有参加限时购或者参加了已经结束
	 * 1.首先判断该商品存在多少个进行的活动中或者还未开始的活动中 2.将活动开始时间与服务器时间的时间差（）
	 * 
	 * @return
	 */
	public Map<String, Object> selectLimitByGoodsId2(String userId, String skuId) {
		Map<String, Object> resultMap = new HashMap<>();
		LimitGoodsSkuVo lgs = null;
		if (StringUtils.isBlank(skuId)) {
			return null;
		}
		LimitGoodsSku entity = new LimitGoodsSku();
		entity.setSkuId(skuId);
		entity.setUpLoadStatus((byte) 1);
		List<LimitGoodsSku> LimitGoodsSkuList = limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
		if (null == LimitGoodsSkuList || LimitGoodsSkuList.size() == 0) {
			return null;
		}
		TreeMap<Long, Object> mapOn = new TreeMap<>();// 正在进行中的活动
		TreeMap<Long, Object> mapNo = new TreeMap<>();// 还没有开始的活动

		for (LimitGoodsSku limitGoodsSku : LimitGoodsSkuList) {
			// 判断限时购当前剩余数量
			if (limitGoodsSku.getLimitCurrTotal() <= 0) {
				continue;
			}
			LimitBuyAct limitBuyAct = limitBuyActMapper.selectByPrimaryKey(limitGoodsSku.getLimitBuyActId());
			if (null == limitBuyAct) {
				continue;
			}
			ActivityStatus activityStatus = getLimitBuyStatus(limitBuyAct.getStartDate(), limitBuyAct.getEndDate());
			LimitGoodsSkuVo limitGoodsSkuVo = getLimitGoodsSkuToLimitGoodsSkuVo(limitGoodsSku);
			limitGoodsSkuVo.setLimitBuyActId(limitBuyAct.getId());
			limitGoodsSkuVo.setStartTime(limitBuyAct.getStartDate());
			limitGoodsSkuVo.setEndTime(limitBuyAct.getEndDate());

			/**
			 * 根据限时购的活动ID和用户ID和skuID,查询某一用户在某一活动下，购买某一件商品的数量
			 */
			if (StringUtils.isNoneBlank(userId)) {
				LimitBuyParam limitBuyParam = new LimitBuyParam();
				limitBuyParam.setUserId(userId);
				limitBuyParam.setLimitBuyActId(limitBuyAct.getId() + "");
				limitBuyParam.setSkuId(limitGoodsSku.getId() + "");
				List<LimitBuyDetail> buyDetails = buydetailMapper.getUserBuyGoodsNum(limitBuyParam);
				/**
				 * 计算用户购买了同一个活动同一商品的件数
				 */
				long goodsSum = 0l;
				long limitPersonNum = 01;
				for (LimitBuyDetail limitBuydetail : buyDetails) {
					goodsSum += limitBuydetail.getBuyNo();
				}
				limitPersonNum = limitGoodsSkuVo.getLimitNum() - goodsSum;
				if (limitPersonNum > 0) {
					limitGoodsSkuVo.setLimitPersonNum(limitPersonNum);
				} else {
					limitGoodsSkuVo.setLimitPersonNum(0l);
				}
			}
			if (ActivityStatus.PROCESSING == activityStatus) {
				long time = new Date().getTime() - limitBuyAct.getStartDate().getTime();// 已经开始了多少时间
				long time2 = limitBuyAct.getEndDate().getTime() - new Date().getTime();// 离结束还有多少时间
				limitGoodsSkuVo.setTime(time2);
				limitGoodsSkuVo.setLimitFalg("InProgress");
				mapOn.put(time, limitGoodsSkuVo);
			}
			if (ActivityStatus.NO == activityStatus) {
				long time = limitBuyAct.getStartDate().getTime() - new Date().getTime();// 离限时购开始还有多少时间
				limitGoodsSkuVo.setTime(time);
				limitGoodsSkuVo.setLimitFalg("NotBeginning");
				mapNo.put(time, limitGoodsSkuVo);
			}
		}
		if (!mapOn.isEmpty()) {
			lgs = (LimitGoodsSkuVo) mapOn.get(mapOn.firstKey());
			long key = mapOn.firstKey();
			resultMap.put("falge", "on");
			resultMap.put("key", key);
		}
		if (null == lgs && !mapNo.isEmpty()) {
			lgs = (LimitGoodsSkuVo) mapNo.get(mapNo.firstKey());
			long key = mapNo.firstKey();
			resultMap.put("falge", "no");
			resultMap.put("key", key);
		}
		return resultMap;
	}
	public LimitGoodsSkuVo getLimitGoodsSkuToLimitGoodsSkuVo(LimitGoodsSku lgs){
		LimitGoodsSkuVo lgsv=new LimitGoodsSkuVo();
		lgsv.setId(lgs.getId());
		lgsv.setLimitBuyActId(lgs.getLimitBuyActId());
		lgsv.setGoodsId(lgs.getGoodsId());
		lgsv.setSkuId(lgs.getSkuId());
		lgsv.setMarketPrice(lgs.getMarketPrice());
		lgsv.setActivityPrice(lgs.getActivityPrice());
		lgsv.setLimitNumTotal(lgs.getLimitNumTotal());
		lgsv.setLimitNum(lgs.getLimitNum());
		lgsv.setSortNo(lgs.getSortNo());
		lgsv.setUrl(lgs.getUrl());
		return lgsv;
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
	
	/**
	 * 验证限时购的商品购买数量
	 * @param limitActivityId 限时购活动Id
	 * @param skuId 商品的skuId
	 * @param num 商品的数量
	 * @param userId 用户Id
	 * @param goodsId 商品编号
	 * @param goodsStockId 商品库存
	 * @return
	 */
	public boolean validateLimitGoodsNumsByGoodsIdAndStockId(LimitBuyParam params){
		GoodsInfoEntity goods = goodsDao.select(params.getGoodsId());
    	String skuId = null;
    	if(StringUtils.isNotBlank(goods.getSource())){
    		skuId = goods.getExternalId();
    	}else{
    		GoodsStockInfoEntity stock = goodsStockDao.select(params.getGoodsStockId());
    		skuId = stock.getSkuId();
    	}
    	params.setSkuId(skuId);
		return validteLimitGoodsNums(params);
	}
	
	/**
	 * 验证限时购的商品购买数量
	 * @param limitActivityId 限时购活动Id
	 * @param skuId 商品的skuId
	 * @param num 商品的数量
	 * @param userId 用户Id
	 * @return
	 */
	public boolean validteLimitGoodsNums(LimitBuyParam params){
		/**
		 * 首先根据限时购活动的Id和skuID，查询出活动下，该skuID的限购数量和总限购数量
		 */
		LimitGoodsSku sku = new LimitGoodsSku();
		sku.setLimitBuyActId(Long.parseLong(params.getLimitBuyActId()));
		sku.setSkuId(params.getSkuId());
		List<LimitGoodsSku> goodsSku = limitGoodsSkuMapper.getLimitGoodsSkuList(sku);
		if(CollectionUtils.isEmpty(goodsSku)){
			return true;
		}
		
		LimitGoodsSku limitGoods = goodsSku.get(0);
		
		if(limitGoods.getLimitCurrTotal() <= 0){
			return false;
		}
		
		if(limitGoods.getLimitNum() == 0){
			return true;
		}
		
		/**
		 * 根据限时购的活动ID和用户ID和skuID,查询某一用户在某一活动下，购买某一件商品的数量
		 */
		List<LimitBuyDetail> buyDetails = buydetailMapper.getUserBuyGoodsNum(new LimitBuyParam(params.getUserId(), limitGoods.getId()+""));
		/**
		 * 计算用户购买了同一个活动同一商品的件数
		 */
		long goodsSum = 0l;
		for (LimitBuyDetail limitBuydetail : buyDetails) {
			goodsSum += limitBuydetail.getBuyNo();
		}
		goodsSum += params.getNum().longValue();
		//总件数-本次购买的件数
		long remaining = limitGoods.getLimitCurrTotal().longValue() - params.getNum().longValue();
		if(remaining >= 0 && limitGoods.getLimitNum().longValue() >= goodsSum){
			return true;
		}
		return false;
	}
}
