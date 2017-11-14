package com.apass.esp.nothing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
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
	@ResponseBody
	@RequestMapping(value = "/goodsStock", method = RequestMethod.GET)
	public  Response SynchronousGoodsStock() {
		Map<String,Object> map=new HashMap<>();
		List<String> list1=new ArrayList<>();//所有非京东的商品的goodsid
		List<String> list2=new ArrayList<>();//所有非京东的商品且skuid为空的goodsStockId
		List<String> list3=new ArrayList<>();//所有非京东的商品的规格更新成功的goodsStockId
		List<GoodsInfoEntity> goodsList = goodsService.getNotJDgoodsList();
		map.put("非京东商品的数量为：", goodsList.size());
		for (GoodsInfoEntity goodsInfoEntity : goodsList) {
			list1.add(goodsInfoEntity.getId().toString());
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
						list2.add(goodsStockInfoEntity.getId().toString());
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
							list3.add(goodsStockInfo.getId().toString());
						}

					}
				}
			}
		}
		map.put("所有非京东的商品的goodsid", list1);
		map.put("所有非京东的商品且skuid为空的goodsStockId", list2);
		map.put("所有非京东的商品的规格更新成功的goodsStockId", list3);
		return Response.success("非京东商品规格同步成功！", map);
	}
}
