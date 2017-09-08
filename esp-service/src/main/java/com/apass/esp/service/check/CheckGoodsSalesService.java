package com.apass.esp.service.check;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.entity.order.SkuNum;
import com.apass.gfb.framework.utils.GsonUtils;

@Service
public class CheckGoodsSalesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckGoodsSalesService.class);

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private GoodsEsDao goodsEsDao;

	public void checkGoodsSales() {
		int count = goodsService.selectJDGoodsCount();
		int num = count / 100;
		for (int i = 0; i < num + 1; i++) {
			GoodsBasicInfoEntity gbinfo = new GoodsBasicInfoEntity();
			gbinfo.setPage(i * 100);
			gbinfo.setRows(100);
			List<GoodsBasicInfoEntity> goodslist = goodsService.searchJDGoodsList(gbinfo);
			for (int j = 0; j < goodslist.size(); j++) {
//				LOGGER.info("商品goodsID", GsonUtils.toJson(goodslist.get(j).getGoodId().toString()));
				// 从ES中查询商品详情信息
				Goods goods = IndexManager.getDocument("goods", IndexType.GOODS,
						Integer.parseInt(goodslist.get(j).getGoodId().toString()));
				String externalId = goodslist.get(j).getExternalId();// 外部商品id
				List<SkuNum> skuNumList = new ArrayList<>();
				SkuNum skuNum = new SkuNum();
				skuNum.setNum(1);
				skuNum.setSkuId(Long.parseLong(externalId));
				skuNumList.add(skuNum);
				// 验证商品是否可售
				if (!orderService.checkGoodsSalesOrNot(skuNumList)) {
					// 京东不可售
					if (null != goods) {
						GoodsInfoEntity entity = goodsService.selectByGoodsId(goodslist.get(j).getGoodId());
						Goods goods2 = goodsService.goodsInfoToGoods(entity);
						LOGGER.info("商品下架，删除索引传递的参数:{}", GsonUtils.toJson(goods2));
						goodsEsDao.delete(goods2);
					}
				} else {
					// 京东可售
					if (null == goods) {
						GoodsInfoEntity entity2 = goodsService.selectByGoodsId(goodslist.get(j).getGoodId());
						Goods goods3 = goodsService.goodsInfoToGoods(entity2);
						LOGGER.info("审核通过,添加索引传递的参数:{}", GsonUtils.toJson(goods3));
						goodsEsDao.add(goods3);
					}
				}

			}
		}

	}
}
