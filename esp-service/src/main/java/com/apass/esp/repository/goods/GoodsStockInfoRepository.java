package com.apass.esp.repository.goods;

import java.util.List;
import java.util.Set;

import com.apass.esp.domain.dto.goods.GoodsStockSkuDto;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class GoodsStockInfoRepository extends BaseMybatisRepository<GoodsStockInfoEntity, Long>{

    /**
     * 获取商品详细信息(尺寸规格价格大小等)
     * 
     * @param goodsId
     * @return
     */
    public List<GoodsStockInfoEntity> loadByGoodsId(Long goodsId) {
        return this.getSqlSession().selectList("loadByGoodsId", goodsId);
    }
    
    /**
     * 根据商品id查询 商品库存中市场价最高价 
     * @param goodsId
     * @return
     */
    public Long getMaxMarketPriceByGoodsId(Long goodsId) {
        return this.getSqlSession().selectOne("getMaxMarketPriceByGoodsId", goodsId);
    }
    /**
     * 获取商品当前库存量
     * 
     * @param goodsStockId
     * @return
     */
    public Long getStockCurrAmt(Long goodsStockId) {
        return this.getSqlSession().selectOne("getStockCurrAmt", goodsStockId);
    }
    
	/**
	 * 商品信息列表
	 * @param domain GoodsInfoEntity
	 * @param page
	 * @return
	 */
	public  Pagination<GoodsStockInfoEntity> pageList(GoodsStockInfoEntity domain, Page page) {
		return this.pageBykey(domain, page, "stockInfoPage");
	 
	}
	/**
	 * 加库存
	 */
	public void updateService(GoodsStockInfoEntity entity){
		this.getSqlSession().update("updateService", entity);
	}
	/**
     * 
     * 
     * @param goodsStock
     * @return
     */
    public Integer updateCurrAmtAndTotalAmount(GoodsStockInfoEntity goodsStock) {
        return this.getSqlSession().update("updateCurrAmtAndTotalAmount", goodsStock);
    }


	public List<GoodsStockSkuDto> getGoodsStockSkuInfo(Long goodsId) {
        return this.getSqlSession().selectList("getSkuInfo", goodsId);
    }

	/**
	 * 批量删除商品库存表商品
	 * @param idsStock
	 * @return 
	 */
	public void deleteJDGoodsStockBatch(List<Long> idsStock) {
		this.getSqlSession().delete("deleteJDGoodsStockBatch", idsStock);
	}
	/**
	 * 根据商品库存id查询 商品来源信息 -
	 * @param stockId
	 * @return
	 */
	public String getGoodsSourceByGoodsStockId(Long stockId){
		return this.getSqlSession().selectOne("getGoodsSourceByGoodsStockId", stockId);
	}
	
	public GoodsStockInfoEntity getGoodsStockInfoEntityByStockId(Long stockId){
		return this.getSqlSession().selectOne("stockById", stockId);
	}

	public void insertGoodsAttr(GoodsStockInfoEntity goodsStockentoty) {
		this.getSqlSession().insert("insertGoodsAttr",goodsStockentoty);
	}
}
