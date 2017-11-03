package com.apass.esp.service.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.GoodsAttrVal;
import com.apass.esp.mapper.GoodsAttrValMapper;
/**
 * 商品不同规格下对应值表
 * @author zqs
 */
@Service
public class GoodsAttrValService {
    @Autowired
    private GoodsAttrValMapper goodsAttrValMapper;
	/**
	 * 根据goodsId查询商品的规格
	 * @param goodsId
	 * @return
	 */
	public List<GoodsAttrVal> queryGoodsAttrValsByGoodsId(Long goodsId){
		return goodsAttrValMapper.queryGoodsAttrValsByGoodsId(goodsId);
	}; 
	
	/**
	 * 根据goodsId,attrId查询商品的规格详情
	 * @param goodsId,attrId
	 * @return
	 */
	public List<GoodsAttrVal> queryByGoodsIdAndAttrId(Long goodsId,Long attrId){
		return goodsAttrValMapper.queryByGoodsIdAndAttrId(goodsId,attrId);
	};
}
