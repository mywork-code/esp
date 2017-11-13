package com.apass.esp.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.domain.entity.GoodsAttrVal;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by jie.xu on 17/10/27.
 */
public interface GoodsAttrValMapper  extends GenericMapper<GoodsAttrVal, Long> {
    /**
     * 通过属性D查询属性值列表
     * @param editCategoryId1
     * @return
     */
    public List<GoodsAttrVal> goodsAttrValListByAttrId(GoodsAttrVal entity);
	/**
	 * 根据goodsId查询商品的规格
	 * @param goodsId
	 * @return
	 */
	List<GoodsAttrVal> queryGoodsAttrValsByGoodsId(Long goodsId);
	
	/**
	 * 根据goodsId,attrId查询商品的规格详情
	 * @param goodsId
	 * @return
	 */
	List<GoodsAttrVal> queryByGoodsIdAndAttrId(@Param("goodsId") Long goodsId,@Param("attrId") Long attrId);
}
