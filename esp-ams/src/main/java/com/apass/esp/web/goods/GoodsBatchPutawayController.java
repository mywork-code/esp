package com.apass.esp.web.goods;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.SystemParamService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.google.common.collect.Maps;
/**
 * wz商品批量上架
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/goods/goodsBatchPutawayController")
public class GoodsBatchPutawayController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CategoryInfoService categoryInfoService;
    @Autowired
    private GoodsStockInfoService goodsStockInfoService;
    @Autowired
    private SystemParamService systemParamService;
    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsEsDao goodsEsDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBatchPutawayController.class);
    /**
     * wz批量上架
     * 上架参考GoodsBaseInfoController shelves 方法
     * 复核参考GoodsBaseInfoController checkview 方法 
     */
    @ResponseBody
    @RequestMapping("/batchPutaway")
    @LogAnnotion(operationType = "wz商品批量上架", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public void batchPutaway() {
        LOGGER.info("wz商品批量上架..");
        try{
            Map<String,Object> params = Maps.newHashMap();
            params.put("status",GoodStatus.GOOD_NEW.getCode());
            params.put("source",SourceType.WZ.getCode());
            params.put("isDelete","01");
            List<GoodsInfoEntity> goodsList= goodsService.selectByCategoryId2AndsordNo(params);
            for(GoodsInfoEntity goods : goodsList){
                LOGGER.info("wz商品批量上架方法，goodId:{}",goods.getId());
                String user = SpringSecurityUtils.getCurrentUser();
                goods.setGoodsTitle("品牌直供正品保证，京东配送快至当天到货");//修改标准小标题
                goods.setUpdateUser(user);
                if (goods.getListTime()==null) {//商品上架时间为空,设置上架日期
                    goods.setListTime(new Date());
                }
                goodsService.updateService(goods);
                List<GoodsStockInfoEntity> stockList = goodsStockInfoService.getGoodsStock(goods.getId());
                String skuId = goods.getExternalId();
                if (stockList.isEmpty()||stockList.size()>2) {//商品库存为空或者错误
                    continue;
                }
                if (goods.getGoodsName().startsWith("【")) {//商品标题名称非法！
                	continue;
                }
                GoodsStockInfoEntity goodsStockInfo = stockList.get(0);
                BigDecimal goodsPrice = goodsStockInfo.getGoodsPrice();
                BigDecimal goodsCostPrice = goodsStockInfo.getGoodsCostPrice();
                if(goodsCostPrice.compareTo(new BigDecimal(110))<0){//微知协议价格低于110元
                    continue;
                }
                if(!orderService.checkGoodsSalesOrNot(skuId)){//验证商品是否可售状态
                    continue;
                }
                Long categoryId1 = goods.getCategoryId1();
                Category category = categoryInfoService.selectNameById(categoryId1);
                String arr = "运动,数码,鞋服,百货,数码,宠物";//运动，数码，鞋服，百货，数码，宠物  非此一级类目不安排上架
				if(category!=null&&StringUtils.isNotBlank(category.getCategoryName())&&!StringUtils.contains(arr, category.getCategoryName())){
					continue;
				}
                SystemParamEntity systemParamEntity =  systemParamService.querySystemParamInfo().get(0);
                BigDecimal dividePoint = goodsPrice.divide(goodsCostPrice, 4, BigDecimal.ROUND_DOWN);
                BigDecimal dividePoint1 = systemParamEntity.getPriceCostRate().multiply(new BigDecimal(0.01)).setScale(4, BigDecimal.ROUND_DOWN);
                Map<String, Object> descMap = jdGoodsInfoService.getJdGoodsSimilarSku(Long.valueOf(skuId));
                String jdGoodsSimilarSku = (String) descMap.get("jdGoodsSimilarSku");
                int jdSimilarSkuListSize = (int) descMap.get("jdSimilarSkuListSize");
                if (StringUtils.isBlank(jdGoodsSimilarSku) && jdSimilarSkuListSize > 0) {//未关联的微知商品      京东商品无法匹配规格无法上架
                    continue;
                }
                goods.setAttrDesc(jdGoodsSimilarSku);
                if (dividePoint.compareTo(dividePoint1) == -1) {//商品已进入保本率审核页面
                    goods.setStatus(GoodStatus.GOOD_BBEN.getCode());//保本审核 不上架
                    goods.setUpdateUser(user);
                    goodsService.updateService(goods);
                } else {//商品已进入复核状态 
                    goods.setStatus(GoodStatus.GOOD_NOCHECK.getCode());// 商品复核  全部上架
                    goods.setUpdateUser(user);
                    goods.setUpdateDate(new Date());
                    goodsService.updateService(goods);
                    //以下为商品复核方法   见GoodsBaseInfoController   checkview 方法  
                    //默认复核批量通过全部上架
                    goods.setStatus(GoodStatus.GOOD_UP.getCode());
                    goods.setUpdateUser(user);
                    goods.setUpdateDate(new Date());
                    goods.setRemark("商品批量复核");
                    goodsService.updateService(goods);
                    //TODO在ES中相似规格的商品只上架一件（即：如果商品多规格则在ES中添加一个规格）
                    //如果ES中没有商品规格中任何一个，则添加到ES中
                    Goods entity = goodsService.goodsInfoToGoods(goodsService.selectByGoodsId(goods.getId()));
                    goodsEsDao.update(entity);
                }
            }
        }catch(Exception e){
        	LOGGER.error("wz商品批量上架,出现异常");
        }
    }
}