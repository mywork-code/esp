package com.apass.esp.mq.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.common.model.ExtentMerchantCode;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.jd.JdProductState;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.*;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.mapper.MessageListenerMapper;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.WeiZhiPriceApiClient;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.esp.third.party.weizhi.entity.Category;
import com.apass.esp.third.party.weizhi.response.WZPriceResponse;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RandomUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("jdTaskListener")
public class JDTaskListener implements MessageListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(JDTaskListener.class);
  @Autowired
  private GoodsService goodsService;
  @Autowired
  private OrderInfoRepository orderInfoRepository;
  @Autowired
  private JdGoodsMapper jdGoodsMapper;
  @Autowired
  private JdCategoryMapper jdCategoryMapper;
  @Autowired
  private GoodsStockInfoService goodsStockInfoService;
  @Autowired
  private GoodsEsDao goodsEsDao;
  @Autowired
  private MessageListenerMapper messageListenerMapper;

  //微知
  @Autowired
  private WeiZhiPriceApiClient weiZhiPriceApiClient;

  @Autowired
  private WeiZhiProductApiClient weiZhiProductApiClient;

  @Autowired
  private OrderService orderService;
  @Autowired
  private SystemEnvConfig systemEnvConfig;
  @Autowired
  private JdGoodsInfoService jdGoodsInfoService;

  @Override
  public void onMessage(Message message) {
    if (!systemEnvConfig.isPROD()){
      //为了避免测试环境误用微知生产环境配置，而导致消费了生产环境消息数据
      return ;
    }

    JdApiMessage jdApiMessage = JSONObject.parseObject(message.getBody(), JdApiMessage.class);
    //记录消息推送接口调用成功或失败
    com.apass.esp.domain.entity.MessageListener ml = new com.apass.esp.domain.entity.MessageListener();
    if (jdApiMessage == null) {
      LOGGER.info("jdApiMessage null error...");
      return;
    }
    LOGGER.info("consume jd message ===================》" + new String(message.getBody()));
    JSONObject result = jdApiMessage.getResult();
    //价格变更消息
    if (jdApiMessage.getType() == JdMessageEnum.PRICE_SKU.getType()) {//商品价格变更消息
      String skuId = result.getString("skuId");
      ml.setType(JdMessageEnum.PRICE_SKU.getType() + "");
      ml.setSkuid(skuId);
      List<String> skus = new ArrayList<>();
      skus.add(skuId);
      try {
      List<WZPriceResponse> jsonArrayJdApiResponse = weiZhiPriceApiClient.getWzPrice(skus);
      if (jsonArrayJdApiResponse == null  ) {
        return;
      }
        WZPriceResponse wzPriceResponse = jsonArrayJdApiResponse.get(0);
        if(StringUtils.isEmpty(wzPriceResponse.getWzPrice())){
        return;
      }
      BigDecimal wzPrice = new BigDecimal(wzPriceResponse.getWzPrice());
        BigDecimal jdPrice = new BigDecimal(wzPriceResponse.getJDPrice());
      LOGGER.info("message price skuId {} ,wzPrice {} ,jdPrice {}", skuId, wzPrice,jdPrice);
      GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(skuId));
      if (goodsInfoEntity == null) {
        ml.setStatus("0");
        ml.setResult("更新商品价格失败，未查询到相应商品，wzPrice=" + wzPrice + ";jdPrice=" +jdPrice);
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);
        return;
      }

      //检查商品是否可售
        if(!orderService.checkGoodsSalesOrNot(skuId)){
          LOGGER.info("skuId {}-------> 商品不可售，更新状态至下架 ", skuId);
          goodsService.goodDownAndRemoveFromES(goodsInfoEntity.getId());
          ml.setType("102");
          ml.setStatus("0");
          ml.setResult("商品id["+goodsInfoEntity.getId()+"],调用是否可售接口结果：不可售");
          ml.setCreatedTime(new Date());
          ml.setUpdatedTime(new Date());
          messageListenerMapper.insertSelective(ml);
        }

      List<GoodsStockInfoEntity> goodsStockInfoEntityList = goodsService.loadDetailInfoByGoodsId(goodsInfoEntity.getId());
      if (CollectionUtils.isEmpty(goodsStockInfoEntityList)) {
        ml.setStatus("0");
        ml.setResult("更新商品价格失败，未查询到相应商品库存，wzPrice=" + wzPrice + ";jdPrice=" +jdPrice);
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);
        return;
      }

        //更新价格
        GoodsStockInfoEntity goodsStockInfoEntity = goodsStockInfoEntityList.get(0);
        goodsStockInfoEntity.setGoodsCostPrice(wzPrice);
        goodsStockInfoEntity.setGoodsPrice(jdPrice);
        goodsStockInfoEntity.setMarketPrice(jdPrice);
        goodsStockInfoService.update(goodsStockInfoEntity);
        ml.setStatus("1");
        ml.setResult("调用批量查询京东商品价格接口成功，京东最新价格：wzPrice=" + wzPrice + ";jdPrice=" +jdPrice);
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);

        //更新京东表
        JdGoods jdGoods = jdGoodsMapper.queryGoodsBySkuId(Long.valueOf(skuId));
        if (jdGoods == null) {
          return;
        }
        JdGoods updateJdGoods = new JdGoods();
        updateJdGoods.setId(jdGoods.getId());
        updateJdGoods.setUpdateDate(new Date());
        updateJdGoods.setPrice(wzPrice);
        updateJdGoods.setJdPrice(jdPrice);
        jdGoodsMapper.updateByPrimaryKeySelective(updateJdGoods);
      } catch (Exception e) {
        LOGGER.error("message skuId {} update price error ");
        ml.setStatus("0");
        ml.setResult("更新商品价格失败");
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);
        return;
      }
      return;
    }
    if (jdApiMessage.getType() == JdMessageEnum.WITHDRAW_SKU.getType()) {//商品上下架消息
      String skuId = result.getString("skuId");
      ml.setType(JdMessageEnum.WITHDRAW_SKU.getType() + "");
      ml.setSkuid(skuId);
      try {
        List<JdProductState>  productStates = weiZhiProductApiClient.getWeiZhiProductSkuState(skuId);
        if(productStates == null){
          return ;
        }
        for(JdProductState jdProductState : productStates){
          int state = jdProductState.getState();
          LOGGER.info("skuId {}, state {} 消息接收  0表示下架消息 1表示上架消息", skuId, state);
          //下架
          if (state == 0) {
            //直接将商品下架
            GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(skuId));
            if (goodsInfoEntity == null) {
              LOGGER.info("skuId {},未查询到该商品 ", skuId);
              return;
            }
            goodsInfoEntity.setStatus(GoodStatus.GOOD_DOWN.getCode());
            goodsInfoEntity.setUpdateDate(new Date());
            goodsInfoEntity.setDelistTime(new Date());
            goodsInfoEntity.setUpdateUser("wzAdmin");
              Integer count = goodsService.updateService(goodsInfoEntity);
              GoodsInfoEntity entity2 = goodsService.selectByGoodsId(goodsInfoEntity.getId());
              Goods goods = new Goods();
              goods.setId(entity2.getGoodId().intValue());
              LOGGER.info("监听京东商品下架删除索引传递的参数:{}", GsonUtils.toJson(goods));
              if (goods != null) {
                goodsEsDao.delete(goods);
              }
              ml.setStatus("1");
              ml.setResult("商品下架");
              ml.setCreatedTime(new Date());
              ml.setUpdatedTime(new Date());
              messageListenerMapper.insertSelective(ml);
          }else{
            //添加京东商品
            addJDGoods(ml, skuId, 1,jdApiMessage.getType());
          }
        }
       } catch (Exception e) {
      LOGGER.error("delete index error");
      ml.setStatus("0");
      ml.setResult("查询商品上下架失败");
      ml.setCreatedTime(new Date());
      ml.setUpdatedTime(new Date());
      messageListenerMapper.insertSelective(ml);
    }
      return;
    }
    if (jdApiMessage.getType() == JdMessageEnum.DELIVERED_ORDER.getType()) {//订单妥投消息
      long orderId = result.getLongValue("orderId");
      JSONArray jsonArray = result.getJSONArray("orderTrack");
      if(jsonArray.isEmpty()){
        return;
      }
      JSONObject orderTrack = (JSONObject) jsonArray.get(0);
      int state = orderTrack.getIntValue("state");
      String skuId = orderTrack.getString("skuId");
      ml.setType(JdMessageEnum.DELIVERED_ORDER.getType() + "");
      ml.setOrderid(orderId + "");
      LOGGER.info("orderId {}, state {},skuId {},  1表示妥投， 2表示拒收", orderId, state,skuId);
      if (state == 1) {
        LOGGER.info("orderId {}, 已投妥 ", orderId);
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setStatus(OrderStatus.ORDER_COMPLETED.getCode());
        orderInfoEntity.setExtOrderId(String.valueOf(orderId));
        orderInfoRepository.updateOrderStatusByExtOrderId(orderInfoEntity);
        ml.setResult("skuId = " + skuId +",妥投");
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);
      } else {
        LOGGER.info("orderId {}, 已拒收 ", orderId);
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setStatus(OrderStatus.ORDER_TRADCLOSED.getCode());
        orderInfoEntity.setExtOrderId(String.valueOf(orderId));
        orderInfoRepository.updateOrderStatusByExtOrderId(orderInfoEntity);
        ml.setResult("skuId = " + skuId +"拒收");
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);
      }
      return;
    }
    if (jdApiMessage.getType() == JdMessageEnum.DELETEADD_SKU.getType()) {
      String skuId = result.getString("skuId");
      int state = result.getIntValue("state");
      ml.setType(JdMessageEnum.DELETEADD_SKU.getType() + "");
      ml.setSkuid(skuId);
      if (state == 1) {
        //添加京东商品
        addJDGoods(ml, skuId, state,jdApiMessage.getType());
      } else {
        try {
          //商品池商品删除  直接将商品下架
          LOGGER.info("skuId {} type 6 state {} 商品删除", skuId, state);
          GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(skuId);
          if (goodsInfoEntity == null) {
            LOGGER.error("delete goods result {},goodsInfoEntity {}", goodsInfoEntity);
            return;
          }
          goodsInfoEntity.setStatus(GoodStatus.GOOD_DOWN.getCode());
          goodsInfoEntity.setUpdateDate(new Date());
          goodsInfoEntity.setDelistTime(new Date());
          Integer count = goodsService.updateService(goodsInfoEntity);
          if (count == 1) {
            GoodsInfoEntity entity2 = goodsService.selectByGoodsId(goodsInfoEntity.getId());
            Goods goods = new Goods();
            goods.setId(entity2.getGoodId().intValue());
            //goodsService.goodsInfoToGoods(entity2);
            LOGGER.info("监听京东商品池删除,删除索引传递的参数:{}", GsonUtils.toJson(goods));
            if (goods != null) {
              goodsEsDao.delete(goods);
            }
          }
          ml.setResult("商品删除成功");
          ml.setStatus("1");
          ml.setCreatedTime(new Date());
          ml.setUpdatedTime(new Date());
          messageListenerMapper.insertSelective(ml);
        } catch (Exception e) {
          ml.setStatus("0");
          ml.setResult("京东商品删除失败！");
          ml.setCreatedTime(new Date());
          ml.setUpdatedTime(new Date());
          messageListenerMapper.insertSelective(ml);
          return;
        }
      }
    }
  }

  private void addJDGoods(com.apass.esp.domain.entity.MessageListener ml, String skuId, int state,int type){
    Product product = null;
    try {
      product = weiZhiProductApiClient.getWeiZhiProductDetail(skuId);
    } catch (Exception e) {
      LOGGER.error("getWeiZhiProductDetail error",e);
    }
    if (product == null) {
      LOGGER.error("skuId {} type {} state {} error", skuId, type,state);
      return;
    }

    String category = product.getCategory();
    String[] categorys = category.split(";");
    String name = product.getName();//商品名称
    String brandName = product.getBrandName();//
    String imagePath = product.getImagePath();//
    String weight = product.getWeight();//
    String productArea = product.getProductArea();//
    String upc =product.getUpc();//
    String saleUnit = product.getSaleUnit();//
    List<String> skulist = new ArrayList<>();
    skulist.add(skuId);
    List<WZPriceResponse>  wzPriceResponse = null;
    try {
      wzPriceResponse = weiZhiPriceApiClient.getWzPrice(skulist);
    } catch (Exception e) {
      LOGGER.error("getWzPrice error",e);
    }
    if (wzPriceResponse == null) {
      LOGGER.error("skuId {} type {} state {} error", skuId, type,state);
      return;
    }

    BigDecimal price = new BigDecimal(wzPriceResponse.get(0).getWzPrice());
    BigDecimal jdPrice =  new BigDecimal(wzPriceResponse.get(0).getJDPrice());
    int firstCategory = Integer.valueOf(categorys[0]);
    int secondCategory = Integer.valueOf(categorys[1]);
    int thirdCategory = Integer.valueOf(categorys[2]);
    JdGoods jdGoods = new JdGoods();
    jdGoods.setFirstCategory(firstCategory);
    jdGoods.setSecondCategory(secondCategory);
    jdGoods.setThirdCategory(thirdCategory);
    jdGoods.setSkuId(Long.valueOf(skuId));
    jdGoods.setBrandName(brandName);
    jdGoods.setImagePath(imagePath);
    jdGoods.setName(name);
    jdGoods.setProductArea(productArea);
    jdGoods.setJdPrice(jdPrice);
    jdGoods.setPrice(price);
    jdGoods.setSaleUnit(saleUnit);
    jdGoods.setWeight(new BigDecimal(weight));
    jdGoods.setUpc(upc);
    jdGoods.setState(state == 1 ? true : false);
    jdGoods.setUpdateDate(new Date());
    jdGoods.setCreateDate(new Date());
    try {
      jdGoodsMapper.insertSelective(jdGoods);
      ml.setResult("商品添加到jdgoods表成功");
      ml.setStatus("1");
      ml.setCreatedTime(new Date());
      ml.setUpdatedTime(new Date());
      messageListenerMapper.insertSelective(ml);
    } catch (Exception e) {
      LOGGER.error("skuId {} type {} state {} error", skuId, type,state);
      LOGGER.error("insert jdGoodsMapper sql skuid {}", skuId);
      return;
    }
    JdCategory jdCategory3 = jdCategoryMapper.getCateGoryByCatId(thirdCategory);
    if (jdCategory3 == null) {
      addCategory(String.valueOf(thirdCategory), 3);
    }
    JdCategory jdCategory2 = jdCategoryMapper.getCateGoryByCatId(secondCategory);
    if (jdCategory2 == null) {
      addCategory(String.valueOf(secondCategory), 2);
    }
    JdCategory jdCategory1 = jdCategoryMapper.getCateGoryByCatId(firstCategory);
    if (jdCategory1 == null) {
      addCategory(String.valueOf(firstCategory), 1);
    }
    JdCategory jdCategory = jdCategoryMapper.getCateGoryByCatId(thirdCategory);
    //已关联
    if (jdCategory != null && jdCategory.getFlag()) {
      try {
      GoodsInfoEntity entity = new GoodsInfoEntity();
      entity.setGoodsTitle("品牌直供正品保证，支持7天退货");
      entity.setCategoryId1(Long.valueOf(firstCategory));
      entity.setCategoryId2(Long.valueOf(secondCategory));
      entity.setCategoryId3(Long.valueOf(thirdCategory));
      entity.setGoodsName(jdGoods.getName());
      entity.setGoodsType(GoodsType.GOOD_NORMAL.getCode());
      entity.setMerchantCode(ExtentMerchantCode.WZMERCHANTCODE);
      entity.setStatus(GoodStatus.GOOD_NEW.getCode());
      entity.setIsDelete(GoodsIsDelete.GOOD_NODELETE.getCode());
      entity.setListTime(null);
      entity.setDelistTime(null);
      entity.setCreateUser("wz");
      entity.setUpdateUser("wz");
      entity.setSource("wz");
      entity.setGoodsLogoUrl(jdGoods.getImagePath());
      entity.setGoodsSiftUrl(jdGoods.getImagePath());
      entity.setExternalId(jdGoods.getSkuId().toString());
      entity.setUpdateDate(new Date());
      entity.setCreateDate(new Date());
      entity.setNewCreatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1900-01-01 00:00:00"));
      entity.setAttrDesc("");
      entity.setSupport7dRefund("");
      entity.setSiftSort(0);
      entity.setMainGoodsCode("");
      GoodsInfoEntity insertJdGoods = goodsService.insertJdGoods(entity);
        LOGGER.info("----------添加已关联类目商品成功... goodsId = " + insertJdGoods.getId());

      if(insertJdGoods != null){
        // 商品编号
        StringBuffer sb = new StringBuffer();
        sb.append("03");
        String random = RandomUtils.getNum(8);
        sb.append(random);
        entity.setGoodsCode(sb.toString());
        entity.setMainGoodsCode(sb.toString());
        goodsService.updateService(entity);

        //查询similar skuIds
        TreeSet<String> similarSkuIds = jdGoodsInfoService.getJdSimilarSkuIdList(jdGoods.getSkuId().toString());
        if(CollectionUtils.isNotEmpty(similarSkuIds)){
          for (String skuId2: similarSkuIds) {
            //根据skuId去goodsbase表中查,如果存在并且mainGoodsCode存在 存储mainGoodsCode
            GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(skuId2);
            if(goodsInfoEntity != null){
              goodsInfoEntity.setMainGoodsCode(sb.toString());
              goodsService.updateService(goodsInfoEntity);
            }
          }
        }
      }

      // 往t_esp_goods_stock_info表插数据
      GoodsStockInfoEntity stockEntity = new GoodsStockInfoEntity();
      stockEntity.setStockTotalAmt(-1l);
      stockEntity.setStockCurrAmt(-1l);
      stockEntity.setStockLogo(jdGoods.getImagePath());
      stockEntity.setSkuId(String.valueOf(jdGoods.getSkuId()));
      stockEntity.setGoodsId(insertJdGoods.getGoodId());
      stockEntity.setGoodsPrice(jdGoods.getJdPrice());
      stockEntity.setMarketPrice(jdGoods.getJdPrice());
      stockEntity.setGoodsCostPrice(jdGoods.getPrice());
      stockEntity.setCreateUser("wzAdmin");
      stockEntity.setUpdateUser("wzAdmin");
      stockEntity.setSkuId(String.valueOf(jdGoods.getSkuId()));

      //往库存表里插入商品规格
      Map<String, String> jdGoodsSpecification = jdGoodsInfoService.getJdGoodsSpecification(jdGoods.getSkuId());
      if(jdGoodsSpecification != null && jdGoodsSpecification.size() > 0){
        StringBuffer sb = new StringBuffer();
        for(String value:jdGoodsSpecification.values()){
          sb.append(value+" ");
        }
        String goodsSku = sb.toString();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(goodsSku)){
          goodsSku = goodsSku.substring(0,goodsSku.length()-1);

        }
        stockEntity.setGoodsSkuAttr(goodsSku);
      }
      stockEntity.setAttrValIds("");
      stockEntity.setDeleteFlag("N");

        goodsStockInfoService.insert(stockEntity);
        LOGGER.info("----------添加已关联类目商品库存成功... goodsStockId = " + stockEntity.getId());
        ml.setResult("商品添加到base_goods表&goods_stock表成功");
        ml.setStatus("1");
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);
      } catch (Exception e) {
        LOGGER.error("skuId {} type {} state {} error", skuId, type,state,e);
        ml.setStatus("0");
        ml.setResult("京东商品添加失败！");
        ml.setCreatedTime(new Date());
        ml.setUpdatedTime(new Date());
        messageListenerMapper.insertSelective(ml);
        return;
      }
    }
  }

  private void addCategory(String category, int level) {
    try {
    Category cate = weiZhiProductApiClient.getCategory(category);
    if (cate == null ) {
      return;
    }
    Long parentId = cate.getParentId();
    Integer catClass = cate.getCatClass();
    String name = cate.getName();
    Long catId = cate.getCatId();
    Integer state = cate.getState();
    JdCategory jdCategory = new JdCategory();
    jdCategory.setName(name);
    jdCategory.setParentId(parentId);
    jdCategory.setCatClass(catClass);
    jdCategory.setFlag(false);
    jdCategory.setCatId(catId);
    jdCategory.setStatus(state == 1 ? true : false);
    jdCategory.setCategoryId1(0l);
    jdCategory.setCategoryId2(0l);
    jdCategory.setCategoryId3(0l);
    Date now = new Date();
    jdCategory.setCreateDate(now);
    jdCategory.setUpdateDate(now);
      jdCategoryMapper.insertSelective(jdCategory);
    } catch (Exception e) {
      LOGGER.error("insert jdCategoryMapper sql error",e);
    }
  }

}
