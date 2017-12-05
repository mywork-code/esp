package com.apass.esp.mapper;
import java.util.List;

import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface LimitGoodsSkuMapper extends GenericMapper<LimitGoodsSku,Long> {
    /**
     * 
     * @param entity
     * @return
     */
    public List<LimitGoodsSku> getLimitGoodsSkuList(LimitGoodsSku entity);
}