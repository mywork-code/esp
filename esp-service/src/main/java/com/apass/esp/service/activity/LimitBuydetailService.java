package com.apass.esp.service.activity;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.LimitBuyDetail;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.vo.LimitBuyParam;
import com.apass.esp.mapper.LimitBuyDetailMapper;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
import com.apass.gfb.framework.exception.BusinessException;
/**
 * 限时购活动用户限购数量
 * @author wht
 *
 */
@Service
public class LimitBuydetailService {
	
    @Autowired
    private LimitBuyDetailMapper limitBuydetailMapper;
    
    @Autowired
    private LimitGoodsSkuMapper limitGoodsSkuMapper;
    
    @Transactional(rollbackFor = { Exception.class, BusinessException.class })
    public void insertDataToBuyDetaill(LimitBuyParam params) throws BusinessException{
    	
    	LimitGoodsSku sku = new LimitGoodsSku();
		sku.setLimitBuyActId(Long.parseLong(params.getLimitBuyActId()));
		sku.setSkuId(params.getSkuId());
		List<LimitGoodsSku> goodsSku = limitGoodsSkuMapper.getLimitGoodsSkuList(sku);
    	
    	LimitGoodsSku goodSku = goodsSku.get(0);
    	long limitCurrTotal = goodSku.getLimitCurrTotal();
    	long currTotal = limitCurrTotal - params.getNum();
    	goodSku.setLimitCurrTotal(currTotal);
    	goodSku.setLimitNumTotal(limitCurrTotal);
    	goodSku.setUpdatedTime(new Date());
    	Integer num = limitGoodsSkuMapper.updateLimitGoods(goodSku);
    	if(num < 1){
    		if(currTotal < 0){
    			throw new BusinessException("订单价格变动，请重新下单!");
    		}else{
    			throw new BusinessException("系统繁忙，请重新重试!");
    		}
    	}
    	LimitBuyDetail detail = new LimitBuyDetail();
    	detail.setBuyNo(params.getNum());
    	detail.setCreatedTime(new Date());
    	detail.setLimitBuyActId(Long.parseLong(params.getLimitBuyActId()));
    	detail.setLimitGoodsSkuId(goodSku.getId());
    	detail.setUpdatedTime(new Date());
    	detail.setUserId(Long.parseLong(params.getUserId()));
    	detail.setOrderId(params.getOrderId());
    	limitBuydetailMapper.insertSelective(detail);
    }
}