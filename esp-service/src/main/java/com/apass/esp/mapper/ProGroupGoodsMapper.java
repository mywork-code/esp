package com.apass.esp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface ProGroupGoodsMapper extends GenericMapper<ProGroupGoods, Long>{


   ProGroupGoods selectLatestByGoodsId(@Param("goodsId") Long goodsId);


   ProGroupGoods selectByGoodsIdAndActivityId(@Param("goodsId") Long goodsId,
                                              @Param("activityId") Long activityId);
   ProGroupGoods selectOneByGoodsIdAndActivityId(@Param("goodsId") Long goodsId,
                                                 @Param("activityId") Long activityId);
   List<ProGroupGoods> selectGoodsByGroupId(@Param("groupId") Long groupId);
   //查看商品是否存在其他有效的活动中
   List<ProGroupGoods> selectEffectiveGoodsByGoodsId(@Param("goodsId") Long goodsId);

   
   Integer getMaxSortOrder(@Param("groupId") Long groupId);
	//分页查询	
   List<ProGroupGoodsVo> getProGroupGoodsListPage(ProGroupGoodsQuery query);
   //分页总数量
   Integer getProGroupGoodsListPageCount(ProGroupGoodsQuery query);
   
   Integer updateGoods(ProGroupGoods proGroupGoods);
   
   //查看该活动下是否已经存在成功添加到分组的商品
   Integer checkActivityGroupGoods(@Param("activityId") Long activityId);
   //删除活动下的商品
   Integer delectGoodsByActivityId(@Param("activityId") Long activityId);
   
}
