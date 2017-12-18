package com.apass.esp.mapper;


import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.gfb.framework.mybatis.GenericMapper;

import java.util.List;

public interface JdGoodsMapper extends GenericMapper<JdGoods,Long> {

	List<JdGoods> queryGoodsByThirdCateId(String cateId);

	JdGoods queryGoodsBySkuId(long skuId);

}