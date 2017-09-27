package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProGroupGoodsMapper extends GenericMapper<ProGroupGoods, Long>{

   ProGroupGoods selectLatestByGoodsId(@Param("goodsId") Long goodsId);

   List<ProGroupGoods> selectGoodsByGroupId(@Param("groupId") Long groupId);

}
