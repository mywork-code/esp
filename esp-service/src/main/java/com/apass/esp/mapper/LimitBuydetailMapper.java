package com.apass.esp.mapper;
import java.util.List;

import com.apass.esp.domain.entity.LimitBuydetail;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface LimitBuydetailMapper extends GenericMapper<LimitBuydetail,Long> {
    /**
     * 查询该限时购活动商品Id查询所有用户购买记录，计算已购总量  和限购总量比较
     * @param limitGoodsSkuId
     * @return
     */
    public List<LimitBuydetail> findActCurrAmtBylimitGoodsSkuId(LimitBuydetail entity);
}