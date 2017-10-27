package com.apass.esp.repository.goods;

import com.apass.esp.domain.entity.cart.CartInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsDetailInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @description banner信息Repository
 *
 * @author lixining
 * @version $Id: CustomerInfoRepository.java, v 0.1 2015年8月6日 上午10:51:37 lixining Exp $
 */
@MyBatisRepository
public class GoodsRepository extends BaseMybatisRepository<GoodsInfoEntity, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRepository.class);
    
    @Autowired
    public GoodsStockInfoRepository stockRepository;

    public Pagination<GoodsBasicInfoEntity> loadRecommendGoods(int pageIndex, int pageSize) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageIndex", pageIndex);
        param.put("pageSize", pageSize);
        List<GoodsBasicInfoEntity> goodsBasicInfoEntityList = this.getSqlSession().selectList(
                "loadRecommendGoods", param);
        Pagination<GoodsBasicInfoEntity> pagination = new Pagination<>();
        pagination.setDataList(goodsBasicInfoEntityList);
        pagination.setTotalCount(this.getSqlSession().selectOne("loadRecommendGoodsCount"));
        return pagination;
    }

    public List<GoodsBasicInfoEntity> loadRecommendGoodsList() {
        return this.getSqlSession().selectList("loadRecommendGoodsList");
    }

    public Pagination<GoodsInfoEntity> loadGoodsByPages(Page page, GoodsInfoEntity param) {
        return this.page(param, page);
    }

    /**
     * count
     */
    public Integer countGoods(GoodsInfoEntity domain) {
        return getSqlSession().selectOne("countGoods", domain);
    }

    /**
     * 商品信息列表
     * 
     * @param domain GoodsInfoEntity
     * @param page
     * @return
     */
    public Pagination<GoodsInfoEntity> pageList(GoodsInfoEntity domain, Page page) {
        return this.pageBykey(domain, page, "goodsPageList");

    }
    
    /**
     * 搜索商品
     */
    public Pagination<GoodsInfoEntity> searchList(GoodsInfoEntity domain, Page page){
        return this.pageBykey(domain, page,"searchGoodsList");
    }

    /**
     * 商品信息列表
     * 
     * @param domain GoodsInfoEntity
     * @return
     */
    public List<GoodsInfoEntity> pageList(GoodsInfoEntity domain) {
        return getSqlSession().selectList("goodsPageList", domain);
    }

    /**
     * 说明：商品精选列表
     * 
     * @param domain
     * @param page
     * @return
     * @author xiaohai
     * @time：2016年12月29日 下午2:55:57
     */
    public Pagination<GoodsInfoEntity> pageForSiftList(GoodsInfoEntity domain, Page page) {
        return this.pageBykey(domain, page, "pageForSiftList");

    }

    /**
     * 商品基本信息+商户信息+库存信息
     *
     * @param goodsStockId
     * @return
     */
    public GoodsDetailInfoEntity loadContainGoodsAndGoodsStockAndMerchant(Long goodsStockId) {

        /**
         * 根据stockId获取goodsId
         */
        GoodsStockInfoEntity stock =  stockRepository.select(goodsStockId);
        HashMap<String, Object> param = new HashMap<>();
        param.put("goodsId", stock.getGoodsId());
        param.put("goodsStockId", goodsStockId);
        return this.getSqlSession().selectOne("loadContainGoodsAndGoodsStockAndMerchant", param);
    }

    public Integer goodsPageListCount() {
        // goodsPageListCount
        return getSqlSession().selectOne("countByGoodsType");
    }

    /**
     * 加载商品列表
     * 
     * @return
     */
    public List<GoodsBasicInfoEntity> loadGoodsList() {
        return this.getSqlSession().selectList("loadGoodsList");
    }

    public Integer updateGoods(GoodsInfoEntity entity) {
        return this.getSqlSession().update("updateGoods", entity);
    }

    /**
     * 同步 购物车商品 勾选标记
     * 
     * @param cartDto
     */
    public void synIsSelect(CartInfoEntity cartDto) {
        this.getSqlSession().update("synIsSelect", cartDto);
    }

    public Integer updateGoodsEdit(GoodsInfoEntity dto) {
        return this.getSqlSession().update("updateGoodsEdit", dto);
    }

    /**
     * 校验商品下架时间，修改商品状态
     * 
     * @return
     */
    public void updateGoodsStatusByDelisttime() {
        this.getSqlSession().update("updateGoodsStatusByDelisttime");
    }

    /**
     * 查询所属分类下属的商品的数量（status!=G03 并且 is_delete !='00'）
     * 
     * @return
     */
    public int getBelongCategoryGoodsNumber(long id) {
        return this.getSqlSession().selectOne("getBelongCategoryGoodsNumber", id);
    }

    public List<GoodsInfoEntity> getBelongCategoryGoods(long id) {
        return this.getSqlSession().selectList("getBelongCategoryGoods", id);
    }

    /**
     * 根据类目id查询其类目下所有已经下架了的商品信息
     * 
     * @param id
     * @return
     */
    public List<GoodsInfoEntity> getDownCategoryGoodsByCategoryId(long id) {
        return this.getSqlSession().selectList("getDownCategoryGoodsByCategoryId", id);
    }

    /**
     * 更新已经下架商品的目录
     * 
     * @param id
     */
    public void updateGoodsCategoryStatus(long id) {
        this.getSqlSession().update("updateGoodsCategoryStatus", id);
    }

    public void insertJdGoods(List<GoodsInfoEntity> entityList) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("entityList", entityList);
        this.getSqlSession().insert("insertJdGoods", entityList);
    }

    public void deleteJDGoodsBatch(List<String> ids) {
        this.getSqlSession().delete("deleteJDGoodsBatch", ids);
    }

    public GoodsInfoEntity selectGoodsByExternalId(@Param("externalId") String externalId) {
        try {
            List<GoodsInfoEntity> goodsInfoEnties = this.getSqlSession().selectList(
                    "selectGoodsByExternalId", externalId);
            if (goodsInfoEnties.isEmpty() || goodsInfoEnties == null) {
                LOGGER.error("数据有误，externalId={}的就东商品在商品表里不存在", externalId);
                throw new BusinessException("数据有误");
            }
            return goodsInfoEnties.get(0);
        } catch (BusinessException e) {
            return null;
        }

    }

    public List<GoodsInfoEntity> selectByCategoryId2(Long categoryId) {
        return this.getSqlSession().selectList("selectByCategoryId2", categoryId);
    }

    public GoodsInfoEntity selectGoodsByExternalIdAndStatus(Long skuId) {
        return this.getSqlSession().selectOne("selectGoodsByExternalIdAndStatus", skuId);
    }

    // 根据京东skuid查询数据库中是否已经插入数据
    public GoodsInfoEntity selectGoodsBySkuId(String skuId) {
        return this.getSqlSession().selectOne("selectGoodsByExternalId", skuId);
    }

    // 根据京东skuid查询数据库中是否已经上架了的商品
    public GoodsInfoEntity selectGoodsInfoByExternalId(String skuId) {
        return this.getSqlSession().selectOne("selectGoodsInfoByExternalId", skuId);
    }

    public List<GoodsInfoEntity> pageListForExport(GoodsInfoEntity goodsInfoEntity) {
        return getSqlSession().selectList("pageListForExport", goodsInfoEntity);
    }
    
    /**
     * 获取上架的商品 <br/>  2017-08-16
     * @return
     */
    public List<GoodsInfoEntity> selectUpGoods(int index,int size){
        HashMap<String, Object> param = new HashMap<>();
        param.put("index", index);
        param.put("size", size);
        return getSqlSession().selectList("selectUpGoods",param);
    }


    public List<GoodsInfoEntity> selectJdGoods(int index,int size){
        HashMap<String, Object> param = new HashMap<>();
        param.put("index", index);
        param.put("size", size);
        return getSqlSession().selectList("selectJdGoods",param);
    }
    /**
     * 精选商品列表
     * @param goodsInfoEntity
     * @return
     */
    public List<GoodsInfoEntity> goodsSiftList(GoodsInfoEntity entity) {
        return getSqlSession().selectList("pageForSiftList", entity);
    }

    public List<GoodsInfoEntity> selectByCategoryId2AndsordNo(Map<String, Object> params) {
        return this.getSqlSession().selectList("selectByCategoryId2AndsordNo", params);
    }
}
