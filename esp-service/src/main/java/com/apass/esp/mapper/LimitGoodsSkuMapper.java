package com.apass.esp.mapper;
import java.util.List;

import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface LimitGoodsSkuMapper extends GenericMapper<LimitGoodsSku,Long> {
    public List<LimitGoodsSku> getLimitGoodsSkuPage(LimitGoodsSku entity);
    public Integer getLimitGoodsSkuPageCount(LimitGoodsSku entity);
    /**
     * 
     * @param entity
     * @return
     */
    public List<LimitGoodsSku> getLimitGoodsSkuList(LimitGoodsSku entity);
    
    
    public Integer updateLimitGoods(LimitGoodsSku entity);
    
    /**
     * 根据限时购活动的Id和skuId，只能查询出一条数据
     * @param limitBuyActId
     * @param skuId
     * @return
     */
//    public LimitGoodsSku getLimitGoodsSkuList(@Param("limitBuyActId") String limitBuyActId,@Param("skuId") String skuId);
}