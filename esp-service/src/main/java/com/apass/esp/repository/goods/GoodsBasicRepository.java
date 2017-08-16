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
	// 通过类目id查询商品[客户端分页](商品上架时间)(按商品销量排列)(商品创建时间)(商品售价)
	public List<GoodsBasicInfoEntity> loadGoodsByParam(GoodsBasicInfoEntity param) {
		return getSqlSession().selectList("loadGoodsByParam", param);
	}	
	// 通过类目id查询商品(共多少商品)
	public Integer loadGoodsByParamCount(GoodsBasicInfoEntity param){
		return getSqlSession().selectOne("loadGoodsByParamCount", param);
	}
	/**
     * 搜索商品(新品，默认)
     */
    public List<GoodsBasicInfoEntity> searchList(GoodsBasicInfoEntity domain){
    	return getSqlSession().selectList("searchGoodsList",domain);
    }
	/**
     * 搜索商品(销量)
     */
    public List<GoodsBasicInfoEntity> searchGoodsListAmount(GoodsBasicInfoEntity domain){
    	return getSqlSession().selectList("searchGoodsListAmount",domain);
    }
    /**
     * 搜索商品(价格)
     */
    public List<GoodsBasicInfoEntity> searchGoodsListPrice(GoodsBasicInfoEntity domain){
    	return getSqlSession().selectList("searchGoodsListPrice",domain);
    }
	// 搜索商品(共多少商品)
	public Integer searchGoodsListCount(GoodsBasicInfoEntity domain){
		return getSqlSession().selectOne("searchGoodsListCount",domain);
	}
	// 搜索商品 根据goodsId查询商品详情 
	public GoodsBasicInfoEntity serchGoodsByGoodsId(String goodsId){
		return getSqlSession().selectOne("serchGoodsByGoodsId",goodsId);
	}
	/**
	 * 热卖单品(新)
	 * @return
	 */
	public List<String> popularGoods(int begin,int count){
		HashMap<String, Object> param = new HashMap<>();
		param.put("begin", begin);
		param.put("count", count);
		return getSqlSession().selectList("popularGoods",param);
	}

	public int popularGoodsCount(){
		return getSqlSession().selectOne("popularGoodsCount");
	}


	public int getRemainderGoodsNewCount(){
		return getSqlSession().selectOne("getRemainderGoodsNewCount");
	}

	/**
	 * 必买清单（新）
	 * @param pageBegin
	 * @param count
	 * @return
	 */
	public List<String> getRemainderGoodsNew(int pageBegin, int count) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("pageBegin", pageBegin);
		param.put("count", count);
		return this.getSqlSession().selectList("getRemainderGoodsNew", param);
	}


}
