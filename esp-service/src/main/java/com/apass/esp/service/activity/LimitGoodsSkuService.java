package com.apass.esp.service.activity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.activity.LimitGoodsSkuVo;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;
/**
 * 限时购活动商品
 * @author wht
 *
 */
@Service
public class LimitGoodsSkuService {
    @Autowired
    public LimitGoodsSkuMapper limitGoodsSkuMapper;
    @Autowired
    public GoodsStockInfoService goodsStockInfoService;
    @Autowired
    public GoodsService goodsService;
    @Autowired
    private CategoryInfoService categoryInfoService;
    /**
     * CREATE
     * @param entity
     * @return
     */
    @Transactional
    public Long createEntity(LimitGoodsSku entity){
        int i = limitGoodsSkuMapper.insert(entity);
        if(i==1){
            return entity.getId();
        }
        return null;
    }
    /**
     * CREATE
     * @param entity
     * @return
     */
    @Transactional
    public Long createEntityBySelect(LimitGoodsSku entity){
        int i = limitGoodsSkuMapper.insertSelective(entity);
        if(i==1){
            return entity.getId();
        }
        return null;
    }
    /**
     * READ BY ID
     * @param id
     * @return
     */
    public LimitGoodsSku readEntity(Long id) {
        return limitGoodsSkuMapper.selectByPrimaryKey(id);
    }
    /**
     * READ LIST
     * @param userId
     * @return
     */
    public List<LimitGoodsSku> readEntityList(LimitGoodsSku entity) {
        return limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
    }
    /**
     * UPDATED
     * @param entity
     * @return
     */
    @Transactional
    public LimitGoodsSku updatedEntity(LimitGoodsSku entity) {
        Integer i = limitGoodsSkuMapper.updateByPrimaryKeySelective(entity);
        if(i==1){
            return entity;
        }
        return null;
    }
    /**
     * DELETE BY ID
     * @param entity
     * @return
     */
    @Transactional
    public Integer deleteEntity(LimitGoodsSku entity) {
        return limitGoodsSkuMapper.deleteByPrimaryKey(entity.getId());
    }
    /**
     * DELETE BY ID
     * @return
     */
    @Transactional
    public Integer deleteEntity(Long id) {
        return limitGoodsSkuMapper.deleteByPrimaryKey(id);
    }
    /**
     * findGoodsInfoListBySkuId
     * 冗余goodsinfo字段
     * @param list
     * @return
     */
    public List<LimitGoodsSkuVo> findGoodsInfoListBySkuId(List<LimitGoodsSku> list) {
        List<LimitGoodsSkuVo> skuvolist = new ArrayList<LimitGoodsSkuVo>();
        Long sortNo = 0L;
        for(LimitGoodsSku entity : list){
            LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
            GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(entity.getSkuId());
            GoodsInfoEntity goods = goodsService.selectByGoodsId(stock.getGoodsId());
            Category cate = categoryInfoService.selectNameById(goods.getCategoryId1());
            //复制商品表
            BeanUtils.copyProperties(goods, vo);
            vo.setGoodsId(goods.getId());
            vo.setId(null);
            //复制库存表
            vo.setStockCurrAmt(stock.getStockCurrAmt());
            vo.setMarketPrice(stock.getMarketPrice());
            //复制导入数据
            vo.setActivityPrice(entity.getActivityPrice());
            vo.setSkuId(entity.getSkuId());
            //复制类目名称数据
            vo.setCategoryId1Name(cate.getCategoryName());
            vo.setSortNo(++sortNo);
            skuvolist.add(vo);
        }
        return skuvolist;
    }
    /**
     * getLimitGoodsList by LIMITBUYACTID
     * @param entity
     * @return
     */
    public ResponsePageBody<LimitGoodsSkuVo> getLimitGoodsList(LimitGoodsSku entity) {
        List<LimitGoodsSku> skulist = null;
        List<LimitGoodsSkuVo> skuvolist = null;
        if(entity.getLimitBuyActId()==null){
            skuvolist = new ArrayList<LimitGoodsSkuVo>();
        }else{
            skulist = readEntityList(entity);
            skuvolist = findGoodsInfoListBySkuId(skulist);
        }
        ResponsePageBody<LimitGoodsSkuVo> pageBody = new ResponsePageBody<LimitGoodsSkuVo>();
        pageBody.setTotal(skuvolist.size());
        pageBody.setRows(skuvolist);
        pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
        return pageBody;
    }
}