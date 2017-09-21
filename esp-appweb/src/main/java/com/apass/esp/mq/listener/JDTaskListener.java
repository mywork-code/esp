package com.apass.esp.mq.listener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.apass.esp.common.model.JdMerchantCode;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.jd.JdSellPrice;
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
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdOrderApiClient;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
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
  private OrderService orderService;
  @Autowired
  private OrderInfoRepository orderInfoRepository;
  @Autowired
  private JdGoodsMapper jdGoodsMapper;
  @Autowired
  private JdCategoryMapper jdCategoryMapper;
  @Autowired
  private JdOrderApiClient jdOrderApiClient;
  @Autowired
  private JdProductApiClient jdProductApiClient;
  @Autowired
  private GoodsStockInfoService goodsStockInfoService;
  @Autowired
  private GoodsEsDao goodsEsDao;
  @Autowired
  private MessageListenerMapper messageListenerMapper;
  @Override
  public void onMessage(Message message) {
    JdApiMessage jdApiMessage = JSONObject.parseObject(message.getBody(), JdApiMessage.class);
    //记录消息推送接口调用成功或失败
    com.apass.esp.domain.entity.MessageListener ml=new com.apass.esp.domain.entity.MessageListener();
    if (jdApiMessage == null) {
    	ml.setStatus("0");
    	ml.setResult("jdApiMessage null error");
    	ml.setCreateDate(new Date());
    	ml.setUpdateDate(new Date());
    	messageListenerMapper.insert(ml);
      LOGGER.info("jdApiMessage null error...");
    }
    JSONObject result = jdApiMessage.getResult();
    //价格变更消息
    if (jdApiMessage.getType() == JdMessageEnum.PRICE_SKU.getType()) {//商品价格变更消息
      long skuId = result.getLongValue("skuId");
      ml.setType(JdMessageEnum.PRICE_SKU.getType()+"");
      ml.setSkuid(skuId+"");
      Set<Long> skus = new HashSet<>();
      skus.add(skuId);
      JdApiResponse<JSONArray> jsonArrayJdApiResponse = jdProductApiClient.priceSellPriceGet(skus);
      if (jsonArrayJdApiResponse == null
          || !jsonArrayJdApiResponse.isSuccess()
          || jsonArrayJdApiResponse.getResult() == null
          || jsonArrayJdApiResponse.getResult().size() == 0
          ) {
    	  ml.setStatus("0");
    	  ml.setResult("调用批量查询京东商品价格接口失败！");
    	  ml.setCreateDate(new Date());
          ml.setUpdateDate(new Date());
    	  messageListenerMapper.insert(ml);
        return;
      }
      LOGGER.info("message price skuId {} ,response {}", skuId, jsonArrayJdApiResponse.getResult());
      JSONObject jsonObject = (JSONObject) jsonArrayJdApiResponse.getResult().get(0);
      BigDecimal price = (BigDecimal) jsonObject.get("price");
      ml.setStatus("1");
	  ml.setResult("调用批量查询京东商品价格接口成功，京东最新价格：price="+price);
	  ml.setCreateDate(new Date());
	  ml.setUpdateDate(new Date());
	  messageListenerMapper.insert(ml);
      LOGGER.info("message price skuId {} ,price {} ,response {}", skuId, price, jsonArrayJdApiResponse.getResult());
      GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(skuId));
      if (goodsInfoEntity == null) {
        return;
      }
      List<GoodsStockInfoEntity> goodsStockInfoEntityList = goodsService.loadDetailInfoByGoodsId(goodsInfoEntity.getGoodId());
      if (CollectionUtils.isEmpty(goodsStockInfoEntityList)) {
        return;
      }
      try {
        //更新价格
        GoodsStockInfoEntity goodsStockInfoEntity = goodsStockInfoEntityList.get(0);
        goodsStockInfoEntity.setGoodsCostPrice(price);
        goodsStockInfoService.update(goodsStockInfoEntity);
      } catch (Exception e) {
        LOGGER.error("message skuId {} update price error ");
        return;
      }
      //更新京东表
      JdGoods jdGoods = jdGoodsMapper.queryGoodsBySkuId(skuId);
      if (jdGoods == null) {
        return;
      }
      jdGoods.setPrice(price);
      try {
        jdGoodsMapper.updateByPrimaryKeySelective(jdGoods);
      }catch (Exception e){
        LOGGER.error("message jdGoods skuId {} update price error ");
      }
      return;
    }
    if (jdApiMessage.getType() == JdMessageEnum.WITHDRAW_SKU.getType()) {//商品上下架消息
      long skuId = result.getLongValue("skuId");
      ml.setType(JdMessageEnum.WITHDRAW_SKU.getType()+"");
      ml.setSkuid(skuId+"");
      Set<Long> skus = new HashSet<>();
      skus.add(skuId);
      JdApiResponse<JSONArray> productPrice = jdProductApiClient.productStateQuery(skus);
      for (Object o : productPrice.getResult()) {
        JSONObject jsonObject = (JSONObject) o;
        //long skuId1 = jsonObject.getLong("sku");
        int state = jsonObject.getIntValue("state");
        LOGGER.info("skuId {}, state {} 消息接收  0表示下架消息 1表示上架消息", skuId, state);
        //下架
        if (state == 0) {
        	ml.setStatus("1");
        	ml.setResult("商品下架");
        	ml.setCreateDate(new Date());
       	    ml.setUpdateDate(new Date());
       	    messageListenerMapper.insert(ml);
          //直接将商品下架
          GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(skuId));
          if (goodsInfoEntity == null) {
            LOGGER.info("skuId {}, state {} 消息接收  0表示下架消息 1表示上架消息 商品不存在", skuId, state);
            continue;
          }
          goodsInfoEntity.setStatus(GoodStatus.GOOD_DOWN.getCode());
          goodsInfoEntity.setUpdateDate(new Date());
          goodsInfoEntity.setDelistTime(new Date());
          goodsInfoEntity.setUpdateUser("jdAdmin");
          try {
            Integer count = goodsService.updateService(goodsInfoEntity);
            GoodsInfoEntity entity2 = goodsService.selectByGoodsId(goodsInfoEntity.getId());
            Goods goods = new Goods();
            goods.setId(entity2.getGoodId().intValue());
                    //goodsService.goodsInfoToGoods(entity2);
            LOGGER.info("监听京东商品下架删除索引传递的参数:{}", GsonUtils.toJson(goods));
            if (goods != null) {
              goodsEsDao.delete(goods);
            }
          } catch (Exception e) {
            LOGGER.error("delete index error");
          }
        }
      }
      return;
    }
    if (jdApiMessage.getType() == JdMessageEnum.DELIVERED_ORDER.getType()) {//订单妥投消息
      long orderId = result.getLongValue("orderId");
      int state = result.getIntValue("state");
      ml.setType(JdMessageEnum.DELIVERED_ORDER.getType()+"");
      ml.setOrderid(orderId+"");
      LOGGER.info("orderId {}, state {}  1表示妥投 2表示拒收", orderId, state);
      if (state == 1) {
    	ml.setResult("妥投");
    	ml.setCreateDate(new Date());
   	    ml.setUpdateDate(new Date());
   	    messageListenerMapper.insert(ml);
        LOGGER.info("orderId {}, 已投妥 ", orderId);
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setStatus(OrderStatus.ORDER_COMPLETED.getCode());
        orderInfoEntity.setExtOrderId(String.valueOf(orderId));
        //orderInfoEntity.setAcceptGoodsDate(new Date());
        orderInfoRepository.updateOrderStatusByExtOrderId(orderInfoEntity);
      } else {
    	 ml.setResult("拒收");
    	 ml.setCreateDate(new Date());
    	 ml.setUpdateDate(new Date());
    	 messageListenerMapper.insert(ml);
        LOGGER.info("orderId {}, 已拒收 ", orderId);
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setStatus(OrderStatus.ORDER_TRADCLOSED.getCode());
        orderInfoEntity.setExtOrderId(String.valueOf(orderId));
        //orderInfoEntity.setAcceptGoodsDate(new Date());
        orderInfoRepository.updateOrderStatusByExtOrderId(orderInfoEntity);
      }
      return;
    }
    //拆单消息接收
    if (jdApiMessage.getType() == JdMessageEnum.SPLIT_ORDER.getType()) {
      long jdOrderId = result.getLongValue("pOrder");
      ml.setType(JdMessageEnum.SPLIT_ORDER.getType()+"");
      ml.setOrderid(jdOrderId+"");
      LOGGER.info("jdOrderId {}, 拆单消息 ", jdOrderId);
      JdApiResponse<JSONObject> jdApiResponse = jdOrderApiClient.orderJdOrderQuery(jdOrderId);
      if (!jdApiResponse.isSuccess()) {
    	  ml.setStatus("0");
    	  ml.setResult("根据京东订单号，查询京东订单明细接口失败！");
    	  ml.setCreateDate(new Date());
     	  ml.setUpdateDate(new Date());
     	  messageListenerMapper.insert(ml);
        LOGGER.info("confirm order result {}", jdApiResponse);
        return;
      }
      JSONObject jsonObject = jdApiResponse.getResult();
      OrderInfoEntity orderInfoEntity = orderInfoRepository.getOrderInfoByExtOrderId(String.valueOf(jdOrderId));
      if (orderInfoEntity == null) {
    	  ml.setStatus("0");
    	  ml.setResult("根据京东订单号查询数据库订单失败！");
    	  ml.setCreateDate(new Date());
     	  ml.setUpdateDate(new Date());
     	  messageListenerMapper.insert(ml);
        LOGGER.error("confirm order result {},orderInfoEntity {}", jdApiResponse, orderInfoEntity);
        return;
      }
      if (orderInfoEntity.getPreStockStatus() == null || !orderInfoEntity.getPreStockStatus().equalsIgnoreCase(PreStockStatus.PRE_STOCK.getCode())) {
    	  ml.setStatus("0");
    	  ml.setResult("根据京东订单号查询数据库订单中预占库存状态为空或不为预占库存状态！");
    	  ml.setCreateDate(new Date());
     	  ml.setUpdateDate(new Date());
     	  messageListenerMapper.insert(ml);
    	  LOGGER.error("confirm order result {},orderInfoEntity {}", jdApiResponse, orderInfoEntity);
        return;
      }
      try {
        orderService.jdSplitOrderMessageHandle(jsonObject, orderInfoEntity);
  	    ml.setStatus("1");
	    ml.setResult("京东拆单消息处理成功！");
	    ml.setCreateDate(new Date());
 	    ml.setUpdateDate(new Date());
 	    messageListenerMapper.insert(ml);
      } catch (BusinessException e) {
  	    ml.setStatus("0");
  	    ml.setResult("京东拆单消息处理失败！");
  	    ml.setCreateDate(new Date());
   	    ml.setUpdateDate(new Date());
   	    messageListenerMapper.insert(ml);
        LOGGER.error("jdSplitOrderMessageHandle error extOrderId {}", orderInfoEntity.getExtOrderId());
        return;
      }
    }
    if (jdApiMessage.getType() == JdMessageEnum.DELETEADD_SKU.getType()) {
      long skuId = result.getLongValue("skuId");
      int state = result.getIntValue("state");
      ml.setType(JdMessageEnum.DELETEADD_SKU.getType()+"");
      ml.setSkuid(skuId+"");
      if (state == 1) {
    	  ml.setResult("商品添加");
    	  ml.setStatus("1");
	      ml.setCreateDate(new Date());
 	      ml.setUpdateDate(new Date());
 	      messageListenerMapper.insert(ml);
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.productDetailQuery(skuId);
        if (!jdApiResponse.isSuccess() || jdApiResponse == null) {
          LOGGER.error("skuId {} type 6 state {} error", skuId, state);
          return;
        }
        JSONObject jsonObject1 = jdApiResponse.getResult();
        String category = (String) jsonObject1.get("category");
        String[] categorys = category.split(";");
        String name = (String) jsonObject1.get("name");//商品名称
        String brandName = (String) jsonObject1.get("brandName");//
        //Integer state = (Integer) jsonObject1.get("state");
        String imagePath = (String) jsonObject1.get("imagePath");//
        String weight = (String) jsonObject1.get("weight");//
        String productArea = (String) jsonObject1.get("productArea");//
        String upc = (String) jsonObject1.get("upc");//
        String saleUnit = (String) jsonObject1.get("saleUnit");//
        //String category = (String) jsonObject1.get("category");
        String wareQD = (String) jsonObject1.get("wareQD");//
        List<Long> skulist = new ArrayList<Long>();
        skulist.add(skuId);
        JdApiResponse<JSONArray> jsonArrayJdApiResponse = jdProductApiClient.priceSellPriceGet(skulist);
        if (jsonArrayJdApiResponse == null) {
          LOGGER.error("skuId {} type 6 state {} error", skuId, state);
          return;
        }
        JSONArray productPriceList = jsonArrayJdApiResponse.getResult();
        if (productPriceList == null || productPriceList.size() == 0) {
          LOGGER.error("skuId {} type 6 state {} error", skuId, state);
          return;
        }
        JSONObject jsonObject12 = null;
        try {
          jsonObject12 = (JSONObject) productPriceList.get(0);
        } catch (Exception e) {
          LOGGER.error("skuId {} type 6 state {} error", skuId, state);
          return;
        }
        BigDecimal price = (BigDecimal) jsonObject12.get("price");
        BigDecimal jdPrice = (BigDecimal) jsonObject12.get("jdPrice");
        int firstCategory = Integer.valueOf(categorys[0]);
        int secondCategory = Integer.valueOf(categorys[1]);
        int thirdCategory = Integer.valueOf(categorys[2]);
        JdGoods jdGoods = new JdGoods();
        jdGoods.setFirstCategory(firstCategory);
        jdGoods.setSecondCategory(secondCategory);
        jdGoods.setThirdCategory(thirdCategory);
        jdGoods.setSkuId(skuId);
        jdGoods.setBrandName(brandName);
        jdGoods.setImagePath(imagePath);
        jdGoods.setName(name);
        jdGoods.setProductArea(productArea);
        jdGoods.setJdPrice(jdPrice);
        jdGoods.setPrice(price);
        jdGoods.setSaleUnit(saleUnit);
        //jdGoods.setWareQd(wareQD);
        jdGoods.setWeight(new BigDecimal(weight));
        jdGoods.setUpc(upc);
        jdGoods.setState(state == 1 ? true : false);
        jdGoods.setUpdateDate(new Date());
        jdGoods.setCreateDate(new Date());
        try {
          jdGoodsMapper.insertSelective(jdGoods);
        } catch (Exception e) {
          LOGGER.error("skuId {} type 6 state {} error", skuId, state);
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
          GoodsInfoEntity entity = new GoodsInfoEntity();
          entity.setGoodsTitle("品牌直供正品保证，支持7天退货");
          entity.setCategoryId1(Long.valueOf(firstCategory));
          entity.setCategoryId2(Long.valueOf(secondCategory));
          entity.setCategoryId3(Long.valueOf(thirdCategory));
          entity.setGoodsName(jdGoods.getName());
          entity.setGoodsType(GoodsType.GOOD_NORMAL.getCode());
          entity.setMerchantCode(JdMerchantCode.JDMERCHANTCODE);
          entity.setStatus(GoodStatus.GOOD_NEW.getCode());
          entity.setIsDelete(GoodsIsDelete.GOOD_NODELETE.getCode());
          entity.setListTime(null);
          entity.setDelistTime(null);
          entity.setCreateUser("jd");
          entity.setUpdateUser("jd");
          entity.setSource("jd");
          entity.setGoodsLogoUrl(jdGoods.getImagePath());
          entity.setGoodsSiftUrl(jdGoods.getImagePath());
          entity.setExternalId(jdGoods.getSkuId().toString());
          entity.setUpdateDate(new Date());
          entity.setCreateDate(new Date());
          GoodsInfoEntity insertJdGoods = goodsService.insertJdGoods(entity);
          //往t_esp_goods_stock_info表插数据
          GoodsStockInfoEntity stockEntity = new GoodsStockInfoEntity();
          stockEntity.setStockTotalAmt(-1l);
          stockEntity.setStockCurrAmt(-1l);
          stockEntity.setGoodsId(insertJdGoods.getGoodId());
          stockEntity.setGoodsPrice(jdGoods.getJdPrice());
          stockEntity.setMarketPrice(jdGoods.getJdPrice());
          stockEntity.setGoodsCostPrice(jdGoods.getPrice());
          stockEntity.setCreateUser("jdAdmin");
          stockEntity.setUpdateUser("jdAdmin");
          try {
            goodsStockInfoService.insert(stockEntity);
          } catch (Exception e) {
            LOGGER.error("skuId {} type 6 state {} error", skuId, state);
            return;
          }
        }
      } else {
        try {
        	  ml.setResult("商品删除");
        	  ml.setStatus("1");
    	      ml.setCreateDate(new Date());
     	      ml.setUpdateDate(new Date());
     	      messageListenerMapper.insert(ml);
          //商品池商品删除  直接将商品下架
          LOGGER.info("skuId {} type 6 state {} 商品删除", skuId, state);
          GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(skuId));
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
        } catch (Exception e) {
          return;
        }
      }
    }
    LOGGER.info("jdTaskListener start consume message............");
  }
  private void addCategory(String category, int level) {
    JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.getcategory(Long.valueOf(category));
    if (jdApiResponse == null || jdApiResponse.getResult() == null) {
      return;
    }
    if (!jdApiResponse.isSuccess()) {
      return;
    }
    JSONObject jsonObject = jdApiResponse.getResult();
    Integer parentId = jsonObject.getInteger("parentId");
    Integer catClass = jsonObject.getInteger("catClass");
    String name = jsonObject.getString("name");
    Integer catId = jsonObject.getInteger("catId");
    Integer state = jsonObject.getInteger("state");
    JdCategory jdCategory = new JdCategory();
    jdCategory.setName(name);
    jdCategory.setParentId(Long.valueOf(parentId));
    jdCategory.setCatClass(catClass);
    jdCategory.setFlag(false);
    jdCategory.setCatId(Long.valueOf(catId));
    jdCategory.setStatus(state == 1 ? true : false);
    jdCategory.setCategoryId1(0l);
    jdCategory.setCategoryId2(0l);
    jdCategory.setCategoryId3(0l);
    try {
      jdCategoryMapper.insertSelective(jdCategory);
    } catch (Exception e) {
      LOGGER.error("insert jdCategoryMapper sql catId {}", catId);
    }
  }
}
