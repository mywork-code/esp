package com.apass.esp.mapper;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProGroupGoodsMapper extends GenericMapper<ProGroupGoods, Long>{

   ProGroupGoods selectLatestByGoodsId(@Param("goodsId") Long goodsId);


   ProGroupGoods selectByGoodsIdAndActivityId(@Param("goodsId") Long goodsId,
                                              @Param("activityId") Long activityId);

}
