package com.apass.esp.service.jd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsIsDelete;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.gfb.framework.cache.CacheManager;
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

    @Autowired
    private OrderDetailInfoRepository orderDetailInfoRepository;
    
//    @Autowired
//    private UsersService usersService;

    @Autowired
    private CacheManager cacheManager;
    /**
     * 关联京东类目
     * 
     * @param paramMap
     * @throws BusinessException
     * @throws ParseException 
     */
    @Transactional(rollbackFor = Exception.class)
    public void relevanceJdCategory(Map<String, String> paramMap) throws BusinessException, ParseException {
        // 往t_esp_goods_base_info和t_esp_goods_stock_info表插入数据
        String cateId = paramMap.get("cateId");// 京东类目id
        String username = paramMap.get("username");// 当前用户
        // 判断是否已经关联类目
        JdCategory jdCatego = jdCategoryMapper.getCateGoryByCatId(Long.valueOf(cateId));
        if (jdCatego.getFlag()) {
            // 取消关联
            disRelevanceJdCategory(paramMap);
        }

        // 根据第三级类目 id查询京东三级类目下所有商品
        List<JdGoods> JdGoodsList = jdGoodsMapper.queryGoodsByThirdCateId(cateId);
        if (JdGoodsList == null) {
            throw new BusinessException("京东此类目下无商品");
        }

        for (JdGoods jdGoods : JdGoodsList) {
            // 封闭数据,往t_esp_goods_base_info表插入数据
            GoodsInfoEntity entity = new GoodsInfoEntity();
            entity.setGoodsTitle("品牌直供正品保证，支持7天退货");
            entity.setCategoryId1(Long.valueOf(paramMap.get("categoryId1")));
            entity.setCategoryId2(Long.valueOf(paramMap.get("categoryId2")));
            entity.setCategoryId3(Long.valueOf(paramMap.get("categoryId3")));
            entity.setGoodsName(jdGoods.getName());
            entity.setGoodsType(GoodsType.GOOD_NORMAL.getCode());
            entity.setMerchantCode("0000097");//usersService.loadBasicInfo().getMerchantCode()
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
            entity.setNewCreatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1900-01-01 00:00:00"));
            //查询数据库是否已经存在此商品
            GoodsInfoEntity insertJdGoods = goodsService.insertJdGoods(entity);

            // 往t_esp_goods_stock_info表插数据
            GoodsStockInfoEntity stockEntity = new GoodsStockInfoEntity();
            stockEntity.setStockTotalAmt(-1l);
            stockEntity.setStockCurrAmt(-1l);
            stockEntity.setStockLogo(jdGoods.getImagePath());
            stockEntity.setGoodsId(insertJdGoods.getGoodId());
            stockEntity.setGoodsPrice(jdGoods.getJdPrice());
            stockEntity.setMarketPrice(jdGoods.getJdPrice());
            stockEntity.setGoodsCostPrice(jdGoods.getPrice());
            stockEntity.setCreateUser(username);
            stockEntity.setUpdateUser(username);
            
            goodsStockInfoService.insert(stockEntity);
        }

        // 更新t_esp_jd_category表数据
        JdCategory jdCategory = new JdCategory();
        jdCategory.setId(Long.valueOf(paramMap.get("jdCategoryId")));
        jdCategory.setCategoryId1(Long.valueOf(paramMap.get("categoryId1")));
        jdCategory.setCategoryId2(Long.valueOf(paramMap.get("categoryId2")));
        jdCategory.setCategoryId3(Long.valueOf(paramMap.get("categoryId3")));
        jdCategory.setFlag(true);
        jdCategoryMapper.updateByPrimaryKeySelective(jdCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<JdGoods> disRelevanceValidate(Map<String, String> paramMap) throws BusinessException {
        String cateId = paramMap.get("cateId");// 京东类目id
        List<GoodsInfoEntity> goodsInfos = goodsService.selectByCategoryId3(cateId);
        if (goodsInfos.size() > 0) {
            LOGGER.info("上架或待审核商品：{}",GsonUtils.toJson(goodsInfos));
            throw new BusinessException("该分类下有上架或待审核商品，请先将商品下架或驳回。");
        }

        // 根据第三级类目 id查询京东三级类目下所有商品
        List<JdGoods> JdGoodsList = jdGoodsMapper.queryGoodsByThirdCateId(cateId);
        if (JdGoodsList == null) {
            throw new BusinessException("京东此类目下无商品");
        }
        // 判断此类目下商品是否有被下单
        List<Long> skuIds = new ArrayList<Long>();
        if (JdGoodsList.size() > 100) {
            int num = JdGoodsList.size() / 100;
            for (int i = 0; i < JdGoodsList.size(); i++) {
                if (i < 100 * num) {
                    skuIds.add(JdGoodsList.get(i).getSkuId());
                    if ((i + 1) % 100 == 0) {
                        List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository
                                .queryOrderDetailBySkuIds(skuIds);
                        if (orderDetails.size() > 0) {
                            LOGGER.info("下单商品：{}",GsonUtils.toJson(orderDetails));
                            throw new BusinessException("该分类下有已下单商品，无法取消关联");
                        }
                        skuIds.clear();
                    }
                } else {
                    skuIds.add(JdGoodsList.get(i).getSkuId());
                }
            }
            if(skuIds.size()>0){
                List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository
                        .queryOrderDetailBySkuIds(skuIds);
                if (orderDetails.size() > 0) {
                    LOGGER.info("下单商品：{}",GsonUtils.toJson(orderDetails));
                    throw new BusinessException("该分类下有已下单商品，无法取消关联");
                }
                skuIds.clear();
            }
        } else {
            for (int i = 0; i < JdGoodsList.size(); i++) {
                skuIds.add(JdGoodsList.get(i).getSkuId());
            }
            if(skuIds.size()>0){
                List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository
                        .queryOrderDetailBySkuIds(skuIds);
                if (orderDetails.size() > 0) {
                    LOGGER.info("下单商品：{}",GsonUtils.toJson(orderDetails));
                    throw new BusinessException("该分类下有已下单商品，无法取消关联");
                }
            }
        }
        return JdGoodsList;
    }

    /**
     * 取消京东类目关联
     * 
     * @param paramMap
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public void disRelevanceJdCategory(Map<String, String> paramMap) throws BusinessException {
        List<JdGoods> JdGoodsList = disRelevanceValidate(paramMap);
        List<String> idsGoods = new ArrayList<String>();// 商品表id
        List<Long> idsStock = new ArrayList<Long>();// 库存表id
        // 删除t_esp_goods_base_info和t_esp_goods_stock_info表中对应京东数据
        if (JdGoodsList.size() > 100) {
            for (int i = 0; i < JdGoodsList.size(); i++) {
                int num = JdGoodsList.size() / 100;
                if (i < 100 * num) {
                    GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(JdGoodsList.get(i)
                            .getSkuId().toString());
                    if (goodsInfoEntity == null) {
                        LOGGER.error("数据库数据有误,externalId:{}", JdGoodsList.get(i).getSkuId().toString());
                        throw new BusinessException("数据库数据有误");
                        // continue;
                    }

                    idsStock.add(Long.valueOf(goodsInfoEntity.getId()));
                    idsGoods.add(JdGoodsList.get(i).getSkuId().toString());

                    if ((i + 1) % 100 == 0) {
                        if (!CollectionUtils.isEmpty(idsStock)) {
                            goodsStockInfoService.deleteJDGoodsStockBatch(idsStock);
                        }
                        if (!CollectionUtils.isEmpty(idsGoods)) {
                            goodsService.deleteJDGoodsBatch(idsGoods);
                        }

                        idsStock.clear();
                        idsGoods.clear();
                    }
                } else {
                    while (i < JdGoodsList.size()) {
                        GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(JdGoodsList
                                .get(i).getSkuId().toString());
                        if (goodsInfoEntity == null) {
                            LOGGER.error("数据库数据有误,externalId:{}", JdGoodsList.get(i).getSkuId().toString());
                            throw new BusinessException("数据库数据有误");
                            // continue;
                        }

                        idsStock.add(Long.valueOf(goodsInfoEntity.getId()));
                        idsGoods.add(JdGoodsList.get(i).getSkuId().toString());
                        i++;
                    }
                    if (!CollectionUtils.isEmpty(idsStock)) {
                        goodsStockInfoService.deleteJDGoodsStockBatch(idsStock);
                    }
                    if (!CollectionUtils.isEmpty(idsGoods)) {
                        goodsService.deleteJDGoodsBatch(idsGoods);
                    }
                }

            }
        } else {
            for (int i = 0; i < JdGoodsList.size(); i++) {
                GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(JdGoodsList.get(i)
                        .getSkuId().toString());
                if (goodsInfoEntity == null) {
                     LOGGER.error("数据库数据有误,externalId:{}",JdGoodsList.get(i).getSkuId().toString());
                     throw new BusinessException("数据库数据有误");
//                    continue;
                }

                idsStock.add(Long.valueOf(goodsInfoEntity.getId()));
                idsGoods.add(JdGoodsList.get(i).getSkuId().toString());
            }
            if (!CollectionUtils.isEmpty(idsStock)) {
                goodsStockInfoService.deleteJDGoodsStockBatch(idsStock);
            }
            if (!CollectionUtils.isEmpty(idsGoods)) {
                goodsService.deleteJDGoodsBatch(idsGoods);
            }

        }

        // 修改t_esp_jd_category表中的状态
        JdCategory jdCategory = new JdCategory();
        jdCategory.setId(Long.valueOf(paramMap.get("jdCategoryId")));
        jdCategory.setFlag(false);
        jdCategoryMapper.updateByPrimaryKeySelective(jdCategory);
    }

}
