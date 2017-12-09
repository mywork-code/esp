package com.apass.esp.service.activity;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.LimitBuydetail;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.vo.LimitBuyParam;
import com.apass.esp.mapper.LimitBuydetailMapper;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
/**
 * 限时购活动用户限购数量
 * @author wht
 *
 */
@Service
public class LimitBuydetailService {
    @Autowired
    private LimitBuydetailMapper limitBuydetailMapper;
    
    @Autowired
    private LimitGoodsSkuMapper limitGoodsSkuMapper;
    
    
    @Transactional(rollbackFor = { Exception.class})
    public void insertDataToBuyDetaill(LimitBuyParam params){
    	
    	LimitGoodsSku goodSku = limitGoodsSkuMapper.getLimitGoodsSkuList(params.getLimitBuyActId(), params.getSkuId());
    	
    	LimitBuydetail detail = new LimitBuydetail();
    	detail.setBuyNo(params.getNum());
    	detail.setCreatedTime(new Date());
    	detail.setLimitBuyActId(Long.parseLong(params.getLimitBuyActId()));
    	detail.setLimitGoodsSkuId(goodSku.getId());
    	detail.setUpdatedTime(new Date());
    	detail.setUserId(Long.parseLong(params.getUserId()));
    	
    	limitBuydetailMapper.insertSelective(detail);
    }
}