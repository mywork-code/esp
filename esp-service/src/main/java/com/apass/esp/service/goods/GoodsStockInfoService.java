package com.apass.esp.service.goods;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.dto.goods.GoodsStockSkuDto;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

@Service
public class GoodsStockInfoService {

    private static final Logger      LOGGER = LoggerFactory.getLogger(GoodsStockInfoService.class);
    @Autowired
    private GoodsStockInfoRepository goodsStockDao;
    @Autowired
    private CommonService commonService;

    /**
     * 商品分页(查询)
     * 
     * @param goodsInfoEntity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PaginationManage<GoodsStockInfoEntity> pageList(GoodsStockInfoEntity goodsStockInfoEntity, String pageNo,
                                                           String pageSize) throws BusinessException {
        Page page = new Page();
        page.setPage(Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo));
        page.setLimit(Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize));

        PaginationManage<GoodsStockInfoEntity> result = new PaginationManage<GoodsStockInfoEntity>();

        Pagination<GoodsStockInfoEntity> entity = goodsStockDao.pageList(goodsStockInfoEntity, page);
        List<GoodsStockInfoEntity> goodsStockInfoEntities = entity.getDataList();
        for(GoodsStockInfoEntity goodStockEntity: goodsStockInfoEntities){
            if(goodStockEntity.getStockCurrAmt() == -1){
                BigDecimal goodsPrice = commonService.calculateGoodsPrice(goodsStockInfoEntity.getGoodsId(),goodsStockInfoEntities.get(0).getId());
                goodStockEntity.setGoodsPrice(goodsPrice);
            }
            BigDecimal goodsCostPrice = goodStockEntity.getGoodsCostPrice();
            BigDecimal priceCostRate = goodStockEntity.getGoodsPrice().divide(goodsCostPrice,4,BigDecimal.ROUND_HALF_UP);
            priceCostRate = priceCostRate.multiply(new BigDecimal(100));
            goodStockEntity.setPriceCostRate(priceCostRate.setScale(2));
        }

        result.setDataList(goodsStockInfoEntities);
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(entity.getTotalCount());
        return result;
    }

    public static void main(String[] args) {
        BigDecimal divide = new BigDecimal(22756).divide(new BigDecimal(5579.22978),BigDecimal.ROUND_CEILING);
        BigDecimal bigDecimal = divide.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal);
    }

    /**
     * 新增
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(GoodsStockInfoEntity entity) {
        return goodsStockDao.insert(entity);
    }

    /**
     * 修改
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(GoodsStockInfoEntity entity) {
        goodsStockDao.update(entity);
    }

    /**
     * 新增
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateService(GoodsStockInfoEntity entity) {
        goodsStockDao.updateService(entity);
    }

	/**
	 * 批量删除京东商品库存
	 * @param idsStock
	 */
	public void deleteJDGoodsStockBatch(List<Long> idsStock) {
	    goodsStockDao.deleteJDGoodsStockBatch(idsStock);
	}
	
	/**
	 * 根据库存id，查询库存详细信息
	 * @param entity
	 * @return
	 */
	public GoodsStockInfoEntity goodsStockInfoEntityByStockId(Long stockId){
		GoodsStockInfoEntity stock = goodsStockDao.getGoodsStockInfoEntityByStockId(stockId);
		return stock;
	}


    public List<GoodsStockSkuDto> getGoodsStockSkuInfo(Long goodsId) {
        return goodsStockDao.getGoodsStockSkuInfo(goodsId);
    }
    /**
     * 查询商品库存
     * @param goodsId
     * @return
     */
    public List<GoodsStockInfoEntity> getGoodsStock(Long goodsId) {
        return goodsStockDao.loadByGoodsId(goodsId);
    }
    /**
     * 删除单个
     * @param goodsStockId
     * @return
     */
    @Transactional
    public Boolean deletegoodsStockInfoById(Long goodsStockId) {
        try{
            GoodsStockInfoEntity entity = goodsStockDao.getGoodsStockInfoEntityByStockId(goodsStockId);
            entity.setDeleteFlag("Y");
            goodsStockDao.updateService(entity);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    /**
     * 批量删除库存
     * @param goodsId
     * @return
     */
    @Transactional
    public Boolean deletegoodsStockInfoByGoodsId(Long goodsId) {
        try{
            List<GoodsStockInfoEntity> list = goodsStockDao.loadByGoodsId(goodsId);
            for(GoodsStockInfoEntity entity : list){
                entity.setDeleteFlag("Y");
                goodsStockDao.updateService(entity);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
