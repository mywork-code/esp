package com.apass.esp.service.purchase;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsDetailInfoEntity;
import com.apass.esp.service.address.AddressService;
import com.apass.esp.service.goods.GoodsService;


@Service
public class PurchaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseService.class);
    @Autowired
    private  AddressService addressService;
    
    @Autowired
    private GoodsService goodsService; 
    /**
     * 立即购买初始化
     * 
     * @param returnMap
     */
    public void buyRightNowInit(Map<String, Object> returnMap,Long userId,Long goodsId,Long goodsStockId) {
        
        //地址信息
        AddressInfoEntity addressInfo = addressService.queryDefaultByUserId(userId);
        returnMap.put("addressInfo", addressInfo);
        //查询商品商户详情
        GoodsDetailInfoEntity goodsDetail= goodsService.loadContainGoodsAndGoodsStockAndMerchant(goodsId,goodsStockId);
        returnMap.put("goodsDetail", goodsDetail);
    }

    
}
