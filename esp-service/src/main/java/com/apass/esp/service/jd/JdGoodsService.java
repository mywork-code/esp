package com.apass.esp.service.jd;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsIsDelete;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;

/**
 * Created by jie.xu on 17/7/5.
 */
@Service
public class JdGoodsService {
	@Autowired
	private JdGoodsMapper jdGoodsMapper;
	
	@Autowired
	private JdCategoryMapper jdCategoryMapper;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsStockInfoService goodsStockInfoService;
	
	/**
	 * 关联京东类目 
	 * @param paramMap
	 * @throws BusinessException 
	 */
	public void relevanceJdCategory(Map<String, Object> paramMap) throws BusinessException {
		//往t_esp_goods_base_info和t_esp_goods_stock_info表插入数据 
		String cateId = (String)paramMap.get("cateId");//京东类目id
		
		
		//根据第三级类目 id查询京东三级类目下所有商品
		List<JdGoods> JdGoodsList = jdGoodsMapper.queryGoodsByThirdCateId(cateId);
		if(JdGoodsList == null){
			throw new BusinessException("京东此类目下无商品");
		}
		for (JdGoods jdGoods : JdGoodsList) {
			//封闭数据,往t_esp_goods_base_info表插入数据 
			GoodsInfoEntity entity = new GoodsInfoEntity();
			entity.setCategoryId1(Long.valueOf((String) paramMap.get("categoryId1")));
			entity.setCategoryId2(Long.valueOf((String) paramMap.get("categoryId2")));
			entity.setCategoryId3(Long.valueOf((String) paramMap.get("categoryId3")));
			entity.setGoodsName(jdGoods.getName());
			entity.setGoodsTitle("");
			entity.setGoodsType(GoodsType.GOOD_NORMAL.getCode());
			entity.setMerchantCode("jd");
			entity.setStatus(GoodStatus.GOOD_NEW.getCode());
			entity.setIsDelete(GoodsIsDelete.GOOD_NODELETE.getCode());
			entity.setListTime(null);
			entity.setDelistTime(null);
			//entity.setGoodId(jdGoods.getSkuId());
			entity.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
			entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
			entity.setSource("jd");
			entity.setExternalId(jdGoods.getSkuId().toString());
			entity.setExternalStatus((byte)1);
			entity = goodsService.insert(entity);
			
			//往t_esp_goods_stock_info表插数据
			GoodsStockInfoEntity stockEntity = new GoodsStockInfoEntity();
			stockEntity.setStockTotalAmt(-1l);
			stockEntity.setStockCurrAmt(-1l);
			stockEntity.setGoodsId(entity.getGoodId());
			stockEntity.setGoodsPrice(jdGoods.getJdPrice());
			stockEntity.setMarketPrice(jdGoods.getJdPrice());
			stockEntity.setGoodsCostPrice(jdGoods.getPrice());
			stockEntity.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
			stockEntity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
			goodsStockInfoService.insert(stockEntity);
		}
		
		//更新t_esp_jd_category表数据
		JdCategory jdCategory = new JdCategory();
		jdCategory.setId((Long)paramMap.get("jdCategoryId"));
		jdCategory.setCategoryId1(Long.valueOf((String) paramMap.get("categoryId1")));
		jdCategory.setCategoryId2(Long.valueOf((String) paramMap.get("categoryId2")));
		jdCategory.setCategoryId3(Long.valueOf((String) paramMap.get("categoryId2")));
		jdCategory.setStatus(true);
		jdCategoryMapper.updateByPrimaryKey(jdCategory);
		
	}
}
