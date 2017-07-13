package com.apass.esp.repository.goods;

import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

import java.util.HashMap;
import java.util.List;

@MyBatisRepository
public class GoodsBasicRepository extends BaseMybatisRepository<GoodsBasicInfoEntity, Long> {

	public Pagination<GoodsBasicInfoEntity> loadGoodsPages(Page page, GoodsBasicInfoEntity param) {
		return this.page(param, page);
	}

	public List<String> getRemainderGoods(int pageBegin, int count) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("pageBegin", pageBegin);
		param.put("count", count);
		return this.getSqlSession().selectList("getRemainderGoods", param);
	}

	// 通过类目id查询商品[客户端分页](按商品销量排列)
	public List<GoodsBasicInfoEntity> loadGoodsByCategoryIdAndAmount(GoodsBasicInfoEntity param) {
		return getSqlSession().selectList("loadGoodsByAmount", param);
	}
	// 通过类目id查询商品[客户端分页](按商品销量排列)(商品数量)
	public Integer loadGoodsByAmountCount(GoodsBasicInfoEntity param) {
		return getSqlSession().selectOne("loadGoodsByAmountCount", param);
	}
	// 通过类目id查询商品[客户端分页](按商品创建时间)
	public List<GoodsBasicInfoEntity> loadGoodsByCategoryIdAndNew(GoodsBasicInfoEntity param) {
		return getSqlSession().selectList("loadGoodsByNew", param);
	}

	// 通过类目id查询商品[客户端分页](按商品上架时间降序)
	public List<GoodsBasicInfoEntity> loadGoodsByCategoryIdDefault(GoodsBasicInfoEntity param) {
		return getSqlSession().selectList("loadGoodsByDefault", param);
	}
	
	// 通过类目id查询商品[客户端分页](按商品价格)
	public List<GoodsBasicInfoEntity> loadGoodsByCategoryIdAndPrice(GoodsBasicInfoEntity param) {
		return getSqlSession().selectList("loadGoodsByPrice", param);
	}
	// 通过类目id查询商品[客户端分页](共多少商品)
	public Integer loadGoodsCount(GoodsBasicInfoEntity param){
		return getSqlSession().selectOne("loadGoodsByCount", param);
	}
}
