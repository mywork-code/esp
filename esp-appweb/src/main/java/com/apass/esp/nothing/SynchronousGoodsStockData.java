package com.apass.esp.nothing;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.esp.domain.entity.GoodsAttrVal;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.goods.GoodsAttrService;
import com.apass.esp.service.goods.GoodsAttrValService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.utils.RandomUtils;

@Controller
@RequestMapping(value = "/synchronous")
public class SynchronousGoodsStockData {
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousGoodsStockData.class);
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsAttrService goodsAttrService;
	@Autowired
	private GoodsStockInfoRepository goodsStockDao;
	@Autowired
	private GoodsAttrValService goodsAttrValService;

	/**
	 * 同步已上架自己商户商品库存数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goodsStock", method = RequestMethod.GET)
	public void SynchronousGoodsStock() {
		List<GoodsInfoEntity> goodsList = goodsService.getNotJDgoodsList();
		for (GoodsInfoEntity goodsInfoEntity : goodsList) {
			GoodsAttr entity = new GoodsAttr();
			if (StringUtils.isNotEmpty(goodsInfoEntity.getGoodsSkuType())) {
				entity.setName(goodsInfoEntity.getGoodsSkuType());
				List<GoodsAttr> GoodsAttrList = goodsAttrService.getGoodsAttrsByName(entity);
				Long attrId = null;
				if (null != GoodsAttrList && GoodsAttrList.size() > 0) {
					attrId = GoodsAttrList.get(0).getId();
				} else {
					// 插入商品属性表记录并且返回主键id
					Long result2 = goodsAttrService.addGoodsAttr2(goodsInfoEntity.getGoodsSkuType(), "admin");
					if (result2 != -1) {
						attrId = result2;
					}
				}
				List<GoodsStockInfoEntity> goodsStockInfoList = goodsStockDao.loadByGoodsId(goodsInfoEntity.getId());
				GoodsAttrVal goodsAttrVal = null;
				Integer sort = 0;
				Long GoodsAttrValId = null;
				for (GoodsStockInfoEntity goodsStockInfoEntity : goodsStockInfoList) {
					if (StringUtils.isBlank(goodsStockInfoEntity.getSkuId())) {//排除已经添加了的数据（非京东）
						// 商品不同规格下对应值表插入数据并返回主键id
						goodsAttrVal = new GoodsAttrVal();
						List<GoodsAttrVal> list = goodsAttrValService.goodsAttrValList(attrId,
								goodsStockInfoEntity.getGoodsId(), goodsStockInfoEntity.getGoodsSkuAttr());
						if (null == list || list.size() == 0) {
							goodsAttrVal.setAttrId(attrId);
							goodsAttrVal.setGoodsId(goodsStockInfoEntity.getGoodsId());
							goodsAttrVal.setAttrVal(goodsStockInfoEntity.getGoodsSkuAttr());
							goodsAttrVal.setSort(++sort);
							goodsAttrVal.setCreatedTime(new Date());
							goodsAttrVal.setUpdatedTime(new Date());
							int result4 = goodsAttrValService.insertAttrVal(goodsAttrVal);
							if (result4 == 1) {
								GoodsAttrValId = goodsAttrVal.getId();
							}
							String rand=RandomUtils.getNum(2);
							// 更新t_esp_goods_stock_info表中的attr_val_ids字段
							GoodsStockInfoEntity goodsStockInfo = new GoodsStockInfoEntity();
							goodsStockInfo.setId(goodsStockInfoEntity.getId());
							goodsStockInfo.setSkuId(goodsInfoEntity.getGoodsCode()+rand);
							goodsStockInfo.setAttrValIds(GoodsAttrValId.toString());
							goodsStockInfo.setUpdateUser("admin");
							goodsStockDao.updateService(goodsStockInfo);
						}

					}
				}
			}
		}
	}
}
