package com.apass.esp.service.jd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsIsDelete;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;

/**
 * Created by jie.xu on 17/7/5.
 */
@Service
public class JdGoodsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdGoodsService.class);
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
	public void relevanceJdCategory(Map<String, String> paramMap) throws BusinessException {
		//往t_esp_goods_base_info和t_esp_goods_stock_info表插入数据 
		String cateId = paramMap.get("cateId");//京东类目id
		String username = paramMap.get("username");//当前用户

		//根据第三级类目 id查询京东三级类目下所有商品
		List<JdGoods> JdGoodsList = jdGoodsMapper.queryGoodsByThirdCateId(cateId);
		if(JdGoodsList == null){
			throw new BusinessException("京东此类目下无商品");
		}
		
		for (JdGoods jdGoods : JdGoodsList) {
			//封闭数据,往t_esp_goods_base_info表插入数据 
			GoodsInfoEntity entity = new GoodsInfoEntity();
			entity.setCategoryId1(Long.valueOf(paramMap.get("categoryId1")));
			entity.setCategoryId2(Long.valueOf(paramMap.get("categoryId2")));
			entity.setCategoryId3(Long.valueOf(paramMap.get("categoryId3")));
			entity.setGoodsName(jdGoods.getName());
			entity.setGoodsTitle("");
			entity.setGoodsType(GoodsType.GOOD_NORMAL.getCode());
			entity.setMerchantCode("0000103");
			entity.setStatus(GoodStatus.GOOD_NEW.getCode());
			entity.setIsDelete(GoodsIsDelete.GOOD_NODELETE.getCode());
			entity.setListTime(null);
			entity.setDelistTime(null);
			entity.setCreateUser(username);
			entity.setUpdateUser(username);
			entity.setSource("jd");
			entity.setGoodsLogoUrl(jdGoods.getImagePath());
			entity.setGoodsSiftUrl(jdGoods.getImagePath());
			entity.setExternalId(jdGoods.getSkuId().toString());
			entity.setExternalStatus((byte)1);
			GoodsInfoEntity insertJdGoods = goodsService.insertJdGoods(entity);
			
			//往t_esp_goods_stock_info表插数据
			GoodsStockInfoEntity stockEntity = new GoodsStockInfoEntity();
			stockEntity.setStockTotalAmt(-1l);
			stockEntity.setStockCurrAmt(-1l);
			stockEntity.setGoodsId(insertJdGoods.getGoodId());
			stockEntity.setGoodsPrice(jdGoods.getJdPrice());
			stockEntity.setMarketPrice(jdGoods.getJdPrice());
			stockEntity.setGoodsCostPrice(jdGoods.getPrice());
			stockEntity.setCreateUser(username);
			stockEntity.setUpdateUser(username);
			goodsStockInfoService.insert(stockEntity);
		}
		
		
		//更新t_esp_jd_category表数据
		JdCategory jdCategory = new JdCategory();
		jdCategory.setId(Long.valueOf(paramMap.get("jdCategoryId")));
		jdCategory.setCategoryId1(Long.valueOf(paramMap.get("categoryId1")));
		jdCategory.setCategoryId2(Long.valueOf(paramMap.get("categoryId2")));
		jdCategory.setCategoryId3(Long.valueOf(paramMap.get("categoryId3")));
		jdCategory.setFlag(true);
		jdCategoryMapper.updateByPrimaryKeySelective(jdCategory);
	}

	/**
	 * 取消京东类目关联
	 * @param paramMap
	 * @throws BusinessException 
	 */
	public void disRelevanceJdCategory(Map<String, String> paramMap) throws BusinessException {
		String cateId = paramMap.get("cateId");//京东类目id
		List<GoodsInfoEntity> goodsInfos = goodsService.selectByCategoryId3(cateId);
		LOGGER.info("存在已上架或待审核商品，分别是：{}",GsonUtils.toJson(goodsInfos));
		if(goodsInfos.size()>0){
			throw new BusinessException("该分类下有上架或待审核商品，请先将商品下架或驳回。");
		}
		
		//根据第三级类目 id查询京东三级类目下所有商品
		List<JdGoods> JdGoodsList = jdGoodsMapper.queryGoodsByThirdCateId(cateId);
		if(JdGoodsList == null){
			throw new BusinessException("京东此类目下无商品");
		}
		
		List<String> idsGoods = new ArrayList<String>();
		List<Long> idsStock = new ArrayList<Long>();
		//删除t_esp_goods_base_info和t_esp_goods_stock_info表中对应京东数据
		if(JdGoodsList.size()>100){
			for(int i=0; i<JdGoodsList.size(); i++){
				int num = JdGoodsList.size()/100;
				if(i<100*num){
					GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(JdGoodsList.get(i).getSkuId().toString());
					if(goodsInfoEntity == null){
						LOGGER.error("数据库数据有误,externalId:{}",JdGoodsList.get(i).getSkuId().toString());
						throw new BusinessException("数据库数据有误");
					}
					
					idsStock.add(Long.valueOf(goodsInfoEntity.getId()));
					idsGoods.add(JdGoodsList.get(i).getSkuId().toString());
					
					if((i+1)/100 == 0){
						if(idsStock != null && idsStock.size() !=0){
							goodsStockInfoService.deleteJDGoodsStockBatch(idsStock);
						}
						goodsService.deleteJDGoodsBatch(idsGoods);
						idsStock.clear();
						idsGoods.clear();
					}
				}else{
					while(i<JdGoodsList.size()){
						GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(JdGoodsList.get(i).getSkuId().toString());
						if(goodsInfoEntity == null){
							LOGGER.error("数据库数据有误,externalId:{}",JdGoodsList.get(i).getSkuId().toString());
							throw new BusinessException("数据库数据有误");
						}
						
						idsStock.add(Long.valueOf(goodsInfoEntity.getId()));
						idsGoods.add(JdGoodsList.get(i).getSkuId().toString());
						i++;
					 }
					if(idsStock != null && idsStock.size() !=0){
						goodsStockInfoService.deleteJDGoodsStockBatch(idsStock);
					}
					goodsService.deleteJDGoodsBatch(idsGoods);
				}
				
			}
		}else{
			for(int i=0; i<JdGoodsList.size(); i++){
				GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(JdGoodsList.get(i).getSkuId().toString());
				if(goodsInfoEntity == null){
					LOGGER.error("数据库数据有误,externalId:{}",JdGoodsList.get(i).getSkuId().toString());
					throw new BusinessException("数据库数据有误");
				}
				
				idsStock.add(Long.valueOf(goodsInfoEntity.getId()));
				idsGoods.add(JdGoodsList.get(i).getSkuId().toString());
			}
			if(idsStock != null && idsStock.size() !=0){
				goodsStockInfoService.deleteJDGoodsStockBatch(idsStock);
			}
			goodsService.deleteJDGoodsBatch(idsGoods);
		}
		
		//修改t_esp_jd_category表中的状态
		JdCategory jdCategory = new JdCategory();
		jdCategory.setId(Long.valueOf(paramMap.get("jdCategoryId")));
		jdCategory.setFlag(false);
		jdCategoryMapper.updateByPrimaryKeySelective(jdCategory);
	}
	
	
	
	
}
