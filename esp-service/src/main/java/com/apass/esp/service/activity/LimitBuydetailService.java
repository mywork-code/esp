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
    
    @Transactional(rollbackFor = { Exception.class})
    public void insertDataToBuyDetaill(LimitBuyParam params){
    	
    	LimitGoodsSku sku = new LimitGoodsSku();
		sku.setLimitBuyActId(Long.parseLong(params.getLimitBuyActId()));
		sku.setSkuId(params.getSkuId());
		List<LimitGoodsSku> goodsSku = limitGoodsSkuMapper.getLimitGoodsSkuList(sku);
    	
    	LimitGoodsSku goodSku = goodsSku.get(0);
    	long currTotal = goodSku.getLimitCurrTotal() - params.getNum();
    	goodSku.setLimitCurrTotal(currTotal);
    	goodSku.setUpdatedTime(new Date());
    	limitGoodsSkuMapper.updateByPrimaryKeySelective(goodSku);
    	
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