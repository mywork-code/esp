package com.apass.esp.mapper;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProGroupGoodsMapper extends GenericMapper<ProGroupGoods, Long>{
	//通过goodsId查询成功管理活动的商品信息
	ProGroupGoods selectByGoodsId(@Param("goodsId") long goodsId);
}
