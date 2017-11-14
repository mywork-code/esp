package com.apass.esp.nothing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.goods.GoodsAttrService;
import com.apass.esp.service.goods.GoodsService;

@Controller
@RequestMapping(value = "/synchronous")
public class SynchronousGoodsStockData {
	 private static final Logger LOGGER  = LoggerFactory.getLogger(SynchronousGoodsStockData.class);
	 @Autowired
	 private GoodsService goodsService;
	 @Autowired
	 private GoodsAttrService goodsAttrService;
	 @Autowired
	 private GoodsStockInfoRepository goodsStockDao;
		/**
		 *  同步已上架自己商户商品库存数据
		 * @return
		 */
		 @RequestMapping(value = "/goodsStock", method = RequestMethod.GET)
		 public void SynchronousGoodsStock(){
			 List<GoodsInfoEntity> goodsList=goodsService.getNotJDgoodsList();
			 for (GoodsInfoEntity goodsInfoEntity : goodsList) {
				 GoodsAttr entity =new GoodsAttr();
				 entity.setName(goodsInfoEntity.getGoodsSkuType());
				 Boolean result=goodsAttrService.getGoodsAttrListByName(entity);
				 Long attrId=0l;
				 if(!result){
					 //TODO插入商品属性表记录并且返回主键id
					 Long result2=goodsAttrService.addGoodsAttr2(goodsInfoEntity.getGoodsSkuType(), "admin");
					 if(result2!=-1){
						 attrId=result2;
					 }
				 }
				 List<GoodsStockInfoEntity> goodsStockInfoList = goodsStockDao.loadByGoodsId(goodsInfoEntity.getId());
				 for (GoodsStockInfoEntity goodsStockInfoEntity : goodsStockInfoList) {
					//TODO商品不同规格下对应值表插入数据并返回主键id
					 
					 //TODO 更新t_esp_goods_stock_info表中的attr_val_ids字段
				}
			}
		 }
}
