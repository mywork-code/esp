package com.apass.esp.service.goods;

import java.util.List;
import java.util.Set;

import com.apass.esp.domain.dto.goods.GoodsStockSkuDto;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

@Service
public class GoodsStockInfoService {

    private static final Logger      LOGGER = LoggerFactory.getLogger(GoodsStockInfoService.class);
    @Autowired
    private GoodsStockInfoRepository goodsStockDao;

    /**
     * 商品分页(查询)
     * 
     * @param goodsInfoEntity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PaginationManage<GoodsStockInfoEntity> pageList(GoodsStockInfoEntity goodsStockInfoEntity, String pageNo,
                                                           String pageSize) {
        Page page = new Page();
        page.setPage(Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo));
        page.setLimit(Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize));

        PaginationManage<GoodsStockInfoEntity> result = new PaginationManage<GoodsStockInfoEntity>();

        Pagination<GoodsStockInfoEntity> entity = goodsStockDao.pageList(goodsStockInfoEntity, page);

        result.setDataList(entity.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(entity.getTotalCount());
        return result;
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
}
