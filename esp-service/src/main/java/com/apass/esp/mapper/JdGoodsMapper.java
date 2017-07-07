package com.apass.esp.mapper;


import java.util.List;
import java.util.Map;

import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface JdGoodsMapper extends GenericMapper<JdGoods,Long> {

	List<JdGoods> queryGoodsByThirdCateId(String cateId);

}