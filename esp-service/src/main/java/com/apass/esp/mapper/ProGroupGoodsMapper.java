package com.apass.esp.mapper;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

public interface ProGroupGoodsMapper extends GenericMapper<ProGroupGoods, Long>{

   ProGroupGoods selectLatestByGoodsId(@Param("goodsId") Long goodsId);
	
}
