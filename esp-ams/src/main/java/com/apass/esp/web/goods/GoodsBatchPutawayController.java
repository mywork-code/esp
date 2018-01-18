package com.apass.esp.web.goods;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.manager.IndexManager;
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
    private GoodsStockInfoService goodsStockInfoService;
    @Autowired
    private SystemParamService systemParamService;
    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsEsDao goodsEsDao;
    @ResponseBody
    @RequestMapping("/batchPutaway")
    @LogAnnotion(operationType = "wz商品批量上架", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public void batchPutaway() {
        try{
            //上架方法参考 见GoodsBaseInfoController   shelves 方法
            Map<String,Object> params = Maps.newHashMap();
            params.put("status",GoodStatus.GOOD_NEW.getCode());
            params.put("source",SourceType.WZ.getCode());
            params.put("isDelete","01");
            List<GoodsInfoEntity> goodsList= goodsService.selectByCategoryId2AndsordNo(params);
            for(GoodsInfoEntity goods : goodsList){
                List<GoodsStockInfoEntity> stockList = goodsStockInfoService.getGoodsStock(goods.getId());
                String skuId = goods.getExternalId();
                String user = SpringSecurityUtils.getCurrentUser();
                if (stockList.isEmpty()||stockList.size()>2) {
                    continue;
//                    return "商品库存为空,请添加！";
                }
                if (goods.getListTime()==null) {
                    goods.setListTime(new Date());
//                    return "商品上架时间不能为空，请先选择类目！";
                }
                GoodsStockInfoEntity goodsStockInfo = stockList.get(0);
                BigDecimal goodsPrice = goodsStockInfo.getGoodsPrice();
                BigDecimal goodsCostPrice = goodsStockInfo.getGoodsCostPrice();
                if(goodsCostPrice.compareTo(new BigDecimal(99))<0){
                    continue;
//                    return "微知协议价格低于99元，不能上架";
                }
                //验证商品是否可售（当验证为不可售时，提示操作人员）
                if(!orderService.checkGoodsSalesOrNot(skuId)){
                    continue;
//                     return "该微知商品暂时不可售，不能上架";
                }
                SystemParamEntity systemParamEntity =  systemParamService.querySystemParamInfo().get(0);
                BigDecimal dividePoint = goodsPrice.divide(goodsCostPrice, 4, BigDecimal.ROUND_DOWN);
                BigDecimal dividePoint1 = systemParamEntity.getPriceCostRate().multiply(new BigDecimal(0.01)).setScale(4, BigDecimal.ROUND_DOWN);
                Map<String, Object> descMap = jdGoodsInfoService.getJdGoodsSimilarSku(Long.valueOf(skuId));
                String jdGoodsSimilarSku = (String) descMap.get("jdGoodsSimilarSku");
                int jdSimilarSkuListSize = (int) descMap.get("jdSimilarSkuListSize");
                if (StringUtils.isBlank(jdGoodsSimilarSku) && jdSimilarSkuListSize > 0) {
                    continue;
//                        return "该京东商品无法匹配规格无法上架！";
                }
                goods.setAttrDesc(jdGoodsSimilarSku);
                if (dividePoint.compareTo(dividePoint1) == -1) {
                    goods.setStatus(GoodStatus.GOOD_BBEN.getCode());
                    goods.setUpdateUser(user);
                    goodsService.updateService(goods);
                    //保本审核 不上架
//                    return "该商品已进入保本率审核页面";
                } else {
                    //商品复核  全部上架
                    goods.setStatus(GoodStatus.GOOD_NOCHECK.getCode());
                    goods.setUpdateUser(user);
                    goods.setUpdateDate(new Date());
                    goodsService.updateService(goods);
//                    return "该商品已进入带审核状态";
                    //一下为商品复核方法   见GoodsBaseInfoController   checkview 方法
                    goods.setStatus(GoodStatus.GOOD_UP.getCode());
                    goods.setUpdateUser(user);
                    goods.setUpdateDate(new Date());
                    goods.setRemark("商品批量复核");
                    goodsService.updateService(goods);
                    //TODO在ES中相似规格的商品只上架一件（即：如果商品多规格则在ES中添加一个规格）
                    Boolean goodsInESNumFalg=true;
                    TreeSet<String> similarSkuIds=jdGoodsInfoService.getJdSimilarSkuIdList(skuId);
                    if(CollectionUtils.isNotEmpty(similarSkuIds)){
                        similarSkuIds.remove(skuId);
                    }
                    for (String string : similarSkuIds) {
                        GoodsInfoEntity goodsInfo=goodsService.selectGoodsByExternalId(string);
                        Goods goodsfromES=null;
                        if(null !=goodsInfo){
                            goodsfromES=IndexManager.goodSearchFromESBySkuId(goodsInfo.getId());
                        }
                        if(null !=goodsfromES){//该商品的相似规格已经在ES中存在
                            goodsInESNumFalg=false;
                            break;
                        }
                    }
                    Goods entity = goodsService.goodsInfoToGoods(goodsService.selectByGoodsId(goods.getId()));
                    //如果ES中没有商品规格中任何一个，则添加到ES中
                    if(goodsInESNumFalg){
                          goodsEsDao.add(entity);
                    }
                }
            }
        }catch(Exception e){
            
        }
    }
}