package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProGroupGoodsMapper extends GenericMapper<ProGroupGoods, Long>{


   ProGroupGoods selectLatestByGoodsId(@Param("goodsId") Long goodsId);

   List<ProGroupGoods> selectGoodsByGroupId(@Param("groupId") Long groupId);
   
	//通过goodsId查询成功管理活动的商品信息
	ProGroupGoods selectByGoodsId(@Param("goodsId") long goodsId);
	//分页查询	
   List<ProGroupGoodsVo> getProGroupGoodsListPage(ProGroupGoodsQuery query);
   //分页总数量
   Integer getProGroupGoodsListPageCount(ProGroupGoodsQuery query);

}