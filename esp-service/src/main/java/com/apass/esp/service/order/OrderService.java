package com.apass.esp.service.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.common.code.ErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.IdNum;
import com.apass.esp.domain.dto.cart.PurchaseRequestDto;
import com.apass.esp.domain.dto.goods.GoodsInfoInOrderDto;
import com.apass.esp.domain.dto.logistics.Trace;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.dto.payment.PayRequestDto;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.CashRefundTxn;
import com.apass.esp.domain.entity.JdGoodSalesVolume;
import com.apass.esp.domain.entity.RepayFlow;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.cart.CartInfoEntity;
import com.apass.esp.domain.entity.cart.GoodsInfoInCartEntity;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.entity.goods.GoodsDetailInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockLogEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.domain.enums.*;
import com.apass.esp.domain.utils.ConstantsUtils;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.enums.AcceptGoodsType;
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.PaymentStatus;
import com.apass.esp.domain.enums.PreDeliveryType;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.CashRefundTxnMapper;
import com.apass.esp.mapper.JdGoodSalesVolumeMapper;
import com.apass.esp.mapper.RepayFlowMapper;
import com.apass.esp.repository.address.AddressInfoRepository;
import com.apass.esp.repository.cart.CartInfoRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.repository.goods.GoodsStockLogRepository;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerCreditInfo;
import com.apass.esp.repository.logistics.LogisticsHttpClient;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.order.OrderSubInfoRepository;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.service.address.AddressService;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.esp.service.bill.BillService;
import com.apass.esp.service.bill.CustomerServiceClient;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.service.logistics.LogisticsService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdOrderApiClient;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.order.OrderReq;
import com.apass.esp.third.party.jd.entity.order.PriceSnap;
import com.apass.esp.third.party.jd.entity.order.SkuNum;
import com.apass.esp.third.party.jd.entity.person.AddressInfo;
import com.apass.esp.third.party.jd.entity.product.Stock;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.logstash.logback.encoder.com.lmax.disruptor.BusySpinWaitStrategy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = { Exception.class })
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderInfoRepository orderInfoRepository;

    @Autowired
    public OrderSubInfoRepository orderSubInfoRepository;

    @Autowired
    public OrderDetailInfoRepository orderDetailInfoRepository;

    @Autowired
    public GoodsRepository goodsDao;

    @Autowired
    public AddressInfoRepository addressInfoDao;

    @Autowired
    private GoodsStockInfoRepository goodsStockDao;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CartInfoRepository cartInfoRepository;

    @Autowired
    private AfterSaleService afterSaleService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private LogisticsHttpClient logisticsHttpClient;

    @Autowired
    private GoodsStockLogRepository goodsStcokLogDao;

    @Autowired
    private GoodsStockInfoRepository getGoodsStockDao;

    @Autowired
    private PaymentHttpClient paymentHttpClient;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BillService billService;

    @Autowired
    private MerchantInforService merchantInforService;

    @Autowired
    private JdGoodSalesVolumeMapper jdGoodSalesVolumeMapper;

    @Autowired
    private JdProductApiClient jdProductApiClient;

    @Autowired
    private JdOrderApiClient jdOrderApiClient;

    @Autowired
    private CashRefundMapper cashRefundMapper;

    @Autowired
    private RepayFlowMapper flowMapper;

    @Autowired
    private CashRefundTxnMapper cashRefundTxnMapper;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private OrderRefundRepository orderRefundRepository;

    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;

    @Autowired
    private CommonHttpClient commonHttpClient;

    public static final Integer errorNo = 3; // 修改库存尝试次数

    private static final String ORDERSOURCECARTFLAG = "cart";

    /**
     * 查询订单概要信息
     *
     * @return
     */
    public List<OrderInfoEntity> queryOrderInfo(Long userId, String[] statusStr) throws BusinessException {
        List<OrderInfoEntity> orderInfoList = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("statusArray", statusStr);
        try {
            orderInfoList = orderInfoRepository.queryOrderInfoList(paramMap);
        } catch (Exception e) {
            LOGGER.error("查询订单信息失败===>", e);
            throw new BusinessException("查询订单信息失败！", e);
        }
        return orderInfoList;
    }

    /**
     * 查询订单详细信息
     *
     * @return
     */
    public List<OrderDetailInfoEntity> queryOrderDetailInfo(String requestId, String orderId)
            throws BusinessException {
        try {
            List<OrderDetailInfoEntity> orderDetailInfo = orderDetailInfoRepository
                    .queryOrderDetailInfo(orderId);
            for (OrderDetailInfoEntity orderDetail : orderDetailInfo) {
                orderDetail.setGoodsLogoUrl(EncodeUtils.base64Encode(orderDetail.getGoodsLogoUrl()));
            }
            return orderDetailInfo;
        } catch (Exception e) {
            LOG.info(requestId, "查询订单详细信息失败", "");
            LOGGER.error("查询订单详细信息失败", e);
            throw new BusinessException("查询订单详细信息失败！", BusinessErrorCode.ORDER_DETAIL_ERROR);
        }
    }

    /**
     * 通过商户号查询订单详细信息
     *
     * @return
     */
    public Pagination<OrderSubInfoEntity> queryOrderSubDetailInfoByParam(Map<String, String> map, Page page)
            throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderDetailInfoList = orderSubInfoRepository
                    .querySubOrderDetailInfoByParam(map, page);
            List<OrderSubInfoEntity> list = orderDetailInfoList.getDataList();
            // 查询改订单的用户名和电话
            for (OrderSubInfoEntity osifty : list) {
                Response response = commonHttpClient.getCustomerBasicInfo("", osifty.getUserId());
                if (response.statusResult()) {
                    CustomerBasicInfo customer = Response.resolveResult(response, CustomerBasicInfo.class);
                    osifty.setUserName(customer.getMobile());
                    osifty.setRealName(customer.getRealName());
                }
            }
            return orderDetailInfoList;
        } catch (Exception e) {
            LOGGER.error(" 通过商户号查询订单详细信息失败===>", e);
            throw new BusinessException(" 通过商户号查询订单详细信息失败！", e);
        }
    }

    /**
     * @throws BusinessException 查询异常订单，即为支付宝申请二次退款的订单
     */
    public Pagination<OrderSubInfoEntity> queryOrderCashRefundException(Map<String, String> map, Page page)
            throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderDetailInfoList = orderSubInfoRepository
                    .queryOrderCashRefundException(map, page);
            List<OrderSubInfoEntity> subList = orderDetailInfoList.getDataList();
            Map<Long, CustomerBasicInfo> maps = Maps.newHashMap();
            for (OrderSubInfoEntity order : subList) {
                setProperties(maps, order);
            }
            return orderDetailInfoList;
        } catch (Exception e) {
            LOGGER.error(" 通过商户号查询订单详细信息失败===>", e);
            throw new BusinessException(" 通过商户号查询订单详细信息失败！", e);
        }
    }

    public void setProperties(Map<Long, CustomerBasicInfo> maps, OrderSubInfoEntity order)
            throws BusinessException {
        CustomerBasicInfo customer = null;
        if (!maps.isEmpty() && maps.containsKey(order.getUserId())) {
            customer = maps.get(order.getUserId());
        } else {
            Response response = commonHttpClient.getCustomerBasicInfo("", order.getUserId());
            if (!response.statusResult()) {
                throw new BusinessException("客户信息查询失败");
            }
            customer = Response.resolveResult(response, CustomerBasicInfo.class);
            maps.put(order.getUserId(), customer);
        }
        if (null != customer) {
            order.setUserName(customer.getMobile());
            order.setRealName(customer.getRealName());
            order.setCardBank(customer.getCardBank());
            order.setCardNo(customer.getCardNo());
        }
    }

    public Pagination<OrderSubInfoEntity> queryOrderRefundException(Map<String, String> map, Page page)
            throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderDetailInfoList = orderSubInfoRepository
                    .queryOrderRefundException(map, page);

            List<OrderSubInfoEntity> subList = orderDetailInfoList.getDataList();
            Map<Long, CustomerBasicInfo> maps = Maps.newHashMap();
            for (OrderSubInfoEntity order : subList) {
                setProperties(maps, order);
            }
            return orderDetailInfoList;
        } catch (Exception e) {
            LOGGER.error(" 通过商户号查询订单详细信息失败===>", e);
            throw new BusinessException(" 通过商户号查询订单详细信息失败！", e);
        }
    }

    /**
     * 查询被二次拒绝的订单
     * 
     * @throws BusinessException
     */
    public Pagination<OrderSubInfoEntity> queryOrderInfoRejectAgain(Page page) throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderDetailInfoList = orderSubInfoRepository
                    .queryOrderInfoRejectAgain(page);
            return orderDetailInfoList;
        } catch (Exception e) {
            LOGGER.error("查询被二次拒绝的订单===>", e);
            throw new BusinessException("查询被二次拒绝的订单！", e);
        }
    }

    /**
     * 通过订单号更新物流信息
     *
     * @return
     */
    @Transactional
    public void updateLogisticsInfoByOrderId(Map<String, String> map) throws BusinessException {
        try {
            orderSubInfoRepository.updateLogisticsInfoByOrderId(map);
        } catch (Exception e) {
            LOGGER.error(" 更新物流信息失败", e);
            throw new BusinessException(" 更新物流信息失败！", e);
        }
    }

    /**
     * 通过订单号更新物流信息、订单状态
     *
     * @return
     */
    @Transactional
    public void updateLogisticsInfoAndOrderInfoByOrderId(Map<String, String> map) throws BusinessException {
        try {
            // 调用第三方物流信息查询机构
            String carrierCode = map.get("logisticsName"); // 物流厂商
            String trackingNumber = map.get("logisticsNo"); // 物流单号
            String orderId = map.get("orderId"); // 订单编号
            List<Map<String, String>> carriersDetect = logisticsHttpClient.carriersDetect(trackingNumber);
            boolean flag = false;
            for (Map<String, String> map2 : carriersDetect) {
                String trackCode = map2.get("code");
                if (carrierCode.equals(trackCode)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                throw new BusinessException("物流单号与快递公司不符");
            }

            logisticsService.subscribeSignleTracking(trackingNumber, carrierCode, orderId, "order");

            /**
             * 首先判断一下订单的预发货的状态 1.如果为Y，则不更新此字段 2.如果为空，要把值置成N
             */
            OrderInfoEntity entity = orderInfoRepository.selectByOrderId(orderId);
            if (!StringUtils.equals(entity.getPreDelivery(), PreDeliveryType.PRE_DELIVERY_Y.getCode())) {
                entity.setPreDelivery(PreDeliveryType.PRE_DELIVERY_Y.getCode());
                entity.setStatus(OrderStatus.ORDER_SEND.getCode());
                orderInfoRepository.updateOrderStatusAndPreDelivery(entity);
            }

            orderSubInfoRepository.updateLogisticsInfoByOrderId(map);

            // 更新订单状态为待收货 D03 : 代收货
            map.put("orderStatus", OrderStatus.ORDER_SEND.getCode());

            orderSubInfoRepository.updateOrderStatusAndLastRtimeByOrderId(map);

        } catch (Exception e) {
            LOGGER.error("物流单号重复或输入错误", e);
            throw new BusinessException("物流单号重复或输入错误", e);
        }
    }

    /**
     * 生成商品订单
     *
     * @param userId 用户Id
     * @param totalPayment 总金额
     * @param addressId 地址id
     * @param purchaseList 商品列表
     * @throws BusinessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class,
            BusinessException.class })
    public List<String> confirmOrder(String requestId, Long userId, BigDecimal totalPayment, Long addressId,
            List<PurchaseRequestDto> purchaseList, String sourceFlag, String deviceType)
            throws BusinessException {
        int index = 0;
        String[] goodsStockArray = new String[purchaseList.size()];
        // 1 校验信息
        validateCorrectInfo(requestId, totalPayment, addressId, userId, purchaseList, sourceFlag);
        // 2 修改商品数目
        for (PurchaseRequestDto purchase : purchaseList) {
            goodsStockArray[index++] = String.valueOf(purchase.getGoodsStockId());
        }
        // 3 删除购物车记录
        if (StringUtils.isNotEmpty(sourceFlag) && sourceFlag.equals(ORDERSOURCECARTFLAG)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userId", userId);
            paramMap.put("goodsStockIdArr", goodsStockArray);
            try {
                cartInfoRepository.deleteGoodsInCart(paramMap);
            } catch (Exception e) {
                LOG.info(requestId, "删除购物车中商品失败", "");
                LOGGER.error("删除购物车中商品失败", e);
                throw new BusinessException("删除购物车中商品失败", e, BusinessErrorCode.CART_DELETE_ERROR);
            }
        }
        // 4 生成订单
        List<String> orders = generateOrder(requestId, userId, totalPayment, purchaseList, addressId,
                deviceType);

        /**
         * 设置预占库存和修改订单的信息
         */
        preStockStatus(orders, addressId);

        return orders;
    }

    /**
     * 验证传入订单列表，是否存在京东订单
     * 
     * @param orderIdList
     * @return
     * @throws BusinessException
     */
    public List<String> getJdOrder(List<String> orderIdList) throws BusinessException {
        List<String> orders = new ArrayList<String>();
        for (String orderId : orderIdList) {
            OrderInfoEntity entity = selectByOrderId(orderId);
            // 验证订单是否为京东订单
            if (StringUtils.equals(entity.getSource(), SourceType.JD.getCode())) {
                orders.add(orderId);
            }
        }
        return orders;
    }

    /**
     * 设置京东商品预占库存
     * 
     * @return
     * @throws BusinessException
     */
    public String preStockStatus(List<String> orderIdList, Long addressId) throws BusinessException {
        /**
         * 根据传入订单号，检测是京东的订单
         */
        List<String> orders = getJdOrder(orderIdList);
        /**
         * 如果集合为空，就不需要往下一步走
         */
        if (CollectionUtils.isEmpty(orders)) {
            return null;
        }
        /**
         *
         * 首先根据订单的id，获取订单的detail信息，拿到detail信息， 获取goodId,根据goodId获取good信息，然后获取京东商品skuId
         *
         */
        List<SkuNum> skuNumList = new ArrayList<>();
        List<PriceSnap> priceSnaps = new ArrayList<>();
        List<OrderDetailInfoEntity> details = orderDetailInfoRepository
                .queryOrderDetailListByOrderList(orders);
        for (OrderDetailInfoEntity detail : details) {
            GoodsInfoEntity goods = goodsDao.select(detail.getGoodsId());
            SkuNum num = new SkuNum(Long.valueOf(goods.getExternalId()), detail.getGoodsNum().intValue());
            skuNumList.add(num);
        }

        /**
         * 获取用户的地址信息
         */
        AddressInfo addressInfo = getAddressByOrderId(addressId);
        /**
         * 批量查询京东价格
         */
        JSONArray productPriceList = jdProductApiClient.priceSellPriceGet(skuNumList).getResult();
        for (Object jsonArray : productPriceList) {
            JSONObject jsonObject = (JSONObject) jsonArray;
            priceSnaps.add(new PriceSnap(jsonObject.getLong("skuId"), jsonObject.getBigDecimal("price"),
                    jsonObject.getBigDecimal("jdPrice")));
        }

        OrderReq orderReq = new OrderReq();
        orderReq.setSkuNumList(skuNumList);
        orderReq.setAddressInfo(addressInfo);
        orderReq.setOrderPriceSnap(priceSnaps);
        orderReq.setRemark("");
        orderReq.setOrderNo(orders.get(0));
        /**
         * 验证商品是否可售
         */
        if(!checkGoodsSalesOrNot(orderReq.getSkuNumList())){
        	throw new BusinessException("下单失败!");
        }
        /**
         * 批量获取库存接口
         */
        List<Stock> stocks = jdProductApiClient.getStock(orderReq.getSkuNumList(), orderReq.getAddressInfo()
                .toRegion());
        for (Stock stock : stocks) {
            if (!"有货".equals(stock.getStockStateDesc())) {
                LOGGER.info("call jd stock inteface is failed[{}] {}", stock.getSkuId(),
                        stock.getStockStateDesc());
                LOGGER.info(stock.getSkuId() + "_");
                throw new BusinessException("商品库存不足");
            }
        }
        /**
         * 统一下单接口
         */
        JdApiResponse<JSONObject> orderResponse = jdOrderApiClient.orderUniteSubmit(orderReq);
        LOGGER.info(orderResponse.toString());
        if ((!orderResponse.isSuccess() || "0008".equals(orderResponse.getResultCode()))
                && !"3004".equals(orderResponse.getResultCode())) {
            LOGGER.warn("call jd comfireOrder inteface is failed !, {}", orderResponse.toString());
            throw new BusinessException("下单失败!");

        } else if (!orderResponse.isSuccess() || "3004".equals(orderResponse.getResultCode())) {
            LOGGER.warn("call jd comfireOrder is failed ! ", orderResponse.toString());
            throw new BusinessException("下单失败!");
        }
        String jdOrderId = orderResponse.getResult().getString("jdOrderId");

        /**
         * 在京东那边占完库存后，要修改order表中的信息
         */
        Map<String, Object> params = Maps.newHashMap();
        params.put("preStockStatus", PreStockStatus.PRE_STOCK.getCode());
        params.put("updateTime", new Date());
        params.put("extOrderId", jdOrderId);
        for (String orderId : orders) {
            params.put("orderId", orderId);
            orderInfoRepository.updatePreStockStatusByOrderId(params);
        }

        return jdOrderId;
    }

    /**
     * 验证商品是否可售
     */
    public boolean checkGoodsSalesOrNot(List<SkuNum> skuNumList){
    	JdApiResponse<JSONArray> skuCheckResult = jdProductApiClient.productSkuCheckWithSkuNum(skuNumList);
        if (!skuCheckResult.isSuccess()) {
            LOGGER.warn("check order status error, {}", skuCheckResult.toString());
            return false;
        }
        for (Object o : skuCheckResult.getResult()) {
            JSONObject jsonObject = (JSONObject) o;
            int saleState = jsonObject.getIntValue("saleState");
            if (saleState != 1) {
                LOGGER.info("sku[{}] could not sell,detail:", jsonObject.getLongValue("skuId"),
                        jsonObject.toJSONString());
                LOGGER.info(jsonObject.getLongValue("skuId") + "_");
                return false;
            }
        }
        return true;
    }
    
    /**
     * 验证区域是否受限
     * @param skus
     * @param region
     * @return
     * @throws BusinessException
     */
    public boolean productCheckAreaLimitQuery(List<Long> skus,Region region) throws BusinessException{
    	JdApiResponse<JSONArray> unSupportAddress = jdProductApiClient.productCheckAreaLimitQuery(skus, region);
    	if((!unSupportAddress.isSuccess())){
    		return false;
    	}
    	
    	 for (Object o : unSupportAddress.getResult()) {
             JSONObject jsonObject = (JSONObject) o;
             return jsonObject.getBooleanValue("isAreaRestrict");
    	 }
    	 
    	return false;
    	
    }
    
    public AddressInfo getAddressByOrderId(Long addressId) throws BusinessException {

        AddressInfo addressInfo = new AddressInfo();

        if (null == addressId) {
            throw new BusinessException("地址编号不能为空");
        }

        AddressInfoEntity address = addressInfoDao.select(Long.valueOf(addressId));

        addressInfo.setProvinceId(Integer.valueOf(address.getProvinceCode()));
        addressInfo.setCityId(Integer.valueOf(address.getCityCode()));
        addressInfo.setCountyId(Integer.valueOf(address.getDistrictCode()));
        addressInfo.setTownId(StringUtils.isBlank(address.getTownsCode()) ? 0 : Integer.valueOf(address
                .getTownsCode()));
        addressInfo.setAddress(address.getAddress());
        addressInfo.setReceiver(address.getName());
        addressInfo.setEmail("xujie@apass.cn");
        addressInfo.setMobile(address.getTelephone());
        return addressInfo;
    }

    /**
     * 修改商品数目 不可循环调用该方法否则无法回滚
     *
     * @param goodsId 商品Id
     * @param goodsStockId 商品库存Id
     * @param goodsNum 修改数量
     * @param additionFlag -1 减少 1 增加
     * @param errorNo 最大递归次数
     * @throws BusinessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class,
            BusinessException.class })
    public void modifyGoodsQuantity(Long goodsId, Long goodsStockId, Long goodsNum, Integer additionFlag,
            int errorNo) throws BusinessException {
        LOGGER.info("进入商品库存修改.商品ID[{}],库存ID[{}],修改数量[{}],加减标记[{}].", errorNo, goodsId, goodsStockId,
                goodsNum, additionFlag);
        GoodsInfoEntity goodsInfo = goodsDao.select(goodsId);
        if (null == goodsInfo) {
            throw new BusinessException("商品信息不存在,请联系客服!");
        }
        GoodsStockInfoEntity goodsStock = goodsStockDao.select(goodsStockId);
        if (null == goodsStock) {
            throw new BusinessException("商品信息不存在,请联系客服!");
        }
        goodsStock.setStockAmt(goodsStock.getStockCurrAmt());
        LOGGER.info("库存ID[{}],库存数量[{}],修改数量[{}].", goodsStockId, goodsStock.getStockCurrAmt(), goodsNum);
        // 减库存
        if (additionFlag < 0) {
            if (goodsStock.getStockCurrAmt() >= goodsNum) {
                Long stockCurrAmt = goodsStock.getStockCurrAmt() - goodsNum;
                goodsStock.setStockCurrAmt(stockCurrAmt);
                LOGGER.info("开始修改库存数量[{}],库存ID[{}].", stockCurrAmt, goodsStockId);
                Integer successFlag = goodsStockDao.updateCurrAmtAndTotalAmount(goodsStock);
                LOGGER.info("开始修改库存数量[{}],库存ID[{}],修改结果[{}].", stockCurrAmt, goodsStockId, successFlag);
                if (successFlag == 0) {
                    LOGGER.info("修改失败,第[{}]次递归操作修改库存数量.", errorNo);
                    if (errorNo <= 0) {
                        // 报错
                        throw new BusinessException(goodsInfo.getGoodsName() + "商品库存不足");
                    }
                    this.modifyGoodsQuantity(goodsId, goodsStockId, goodsNum, additionFlag, --errorNo);
                } else if (successFlag > 1) {
                    LOGGER.info("数据异常.");
                    LOGGER.error("modifyGoodsQuantity is fail,successFlag return > 1,goodsStockId:{}",
                            goodsStockId);
                    throw new BusinessException(goodsInfo.getGoodsName() + "商品库存更新异常请联系客服!");
                }
            } else {
                throw new BusinessException(goodsInfo.getGoodsName() + "商品库存不足");
            }
        } else {
            // 加库存
            Long stockCurrAmt = goodsStock.getStockCurrAmt() + goodsNum;
            Long stockTotalAmt = goodsStock.getStockTotalAmt() + goodsNum;
            goodsStock.setStockCurrAmt(stockCurrAmt);
            goodsStock.setStockTotalAmt(stockTotalAmt);
            LOGGER.info("开始修改库存数量[{}],库存ID[{}].", stockCurrAmt, goodsStockId);
            Integer successFlag = goodsStockDao.updateCurrAmtAndTotalAmount(goodsStock);
            LOGGER.info("开始修改库存数量[{}],库存ID[{}],修改结果[{}].", stockCurrAmt, goodsStockId, successFlag);
            if (successFlag == 0) {
                LOGGER.info("修改失败,第[{}]次递归操作修改库存数量.", errorNo);
                if (errorNo < 0) {
                    // 报错
                    throw new BusinessException(goodsInfo.getGoodsName() + "增加商品库存失败");
                }
                this.modifyGoodsQuantity(goodsId, goodsStockId, goodsNum, additionFlag, --errorNo);
            } else if (successFlag > 1) {
                LOGGER.error("modifyGoodsQuantity is fail,successFlag return > 1,goodsStockId:{}",
                        goodsStockId);
                throw new BusinessException(goodsInfo.getGoodsName() + "商品库存更新异常请联系客服!");
            }
        }
    }

    /**
     *
     * 生成订单 商品价格使用页面传递
     *
     * @param userId
     * @param totalPayment
     * @param purchaseList
     * @throws BusinessException
     */
    @Transactional(rollbackFor = { Exception.class, BusinessException.class })
    public List<String> generateOrder(String requestId, Long userId, BigDecimal totalPayment,
            List<PurchaseRequestDto> purchaseList, Long addressId, String deviceType)
            throws BusinessException {
        List<String> orderList = Lists.newArrayList();
        // 每商户订单金额
        Map<String, BigDecimal> merchantPaymentMap = sumMerchantPayment(purchaseList);
        AddressInfoEntity address = addressInfoDao.select(addressId);
        for (Map.Entry<String, BigDecimal> merchant : merchantPaymentMap.entrySet()) {
            String merchantCode = merchant.getKey();
            BigDecimal orderAmt = merchant.getValue();
            OrderInfoEntity orderInfo = new OrderInfoEntity();
            orderInfo.setUserId(userId);
            orderInfo.setOrderAmt(orderAmt);
            MerchantInfoEntity merchantInfoEntity = merchantInforService.queryByMerchantCode(merchantCode);

            if (StringUtils.equals(merchantInfoEntity.getMerchantName(), ConstantsUtils.MERCHANTNAME)) {
                orderInfo.setSource(SourceType.JD.getCode());
                orderInfo.setExtParentId("0");
            }
            String orderId = commonService.createOrderIdNew(deviceType, merchantInfoEntity.getId());
            orderList.add(orderId);
            orderInfo.setDeviceType(deviceType);
            orderInfo.setOrderId(orderId);
            orderInfo.setStatus(OrderStatus.ORDER_NOPAY.getCode());
            orderInfo.setProvince(address.getProvince());
            orderInfo.setCity(address.getCity());
            orderInfo.setDistrict(address.getDistrict());
            orderInfo.setAddress(address.getAddress());
            orderInfo.setPostcode(address.getPostcode());
            orderInfo.setName(address.getName());
            orderInfo.setTelephone(address.getTelephone());
            orderInfo.setMerchantCode(merchantCode);
            orderInfo.setExtendAcceptGoodsNum(0);
            orderInfo.setAddressId(addressId);
            orderInfo.setPayStatus(PaymentStatus.NOPAY.getCode());
            Long orderGoodsNum = this.countGoodsNumGroupByMerchantCode(merchantCode, purchaseList);
            orderInfo.setGoodsNum(orderGoodsNum);
            orderInfo.setPreDelivery("N");
            orderInfo.setExtOrderId("");
            orderInfo.setPreStockStatus("");
            Integer successStatus = orderInfoRepository.insert(orderInfo);
            if (successStatus < 1) {
                LOG.info(requestId, "生成订单", "订单表数据插入失败");
                throw new BusinessException("订单生成失败!", BusinessErrorCode.ORDER_NOT_EXIST);
            }
            // 插入商品级订单
            for (PurchaseRequestDto purchase : purchaseList) {
                GoodsInfoEntity goods = goodsDao.select(purchase.getGoodsId());
                if (goods.getMerchantCode().equals(merchantCode)) {
                    GoodsStockInfoEntity goodsStock = goodsStockDao.select(purchase.getGoodsStockId());
                    OrderDetailInfoEntity orderDetail = new OrderDetailInfoEntity();
                    if (StringUtils.equals(goods.getSource(), SourceType.JD.getCode())) {
                        orderDetail.setSource(SourceType.JD.getCode());
                        orderDetail.setSkuId(goods.getExternalId());
                    }
                    orderDetail.setOrderId(orderInfo.getOrderId());
                    orderDetail.setGoodsId(goods.getId());
                    orderDetail.setGoodsStockId(purchase.getGoodsStockId());
                    orderDetail.setGoodsPrice(purchase.getPrice());
                    orderDetail.setGoodsNum(purchase.getBuyNum().longValue());
                    orderDetail.setGoodsTitle(goods.getGoodsTitle());
                    orderDetail.setCategoryCode(goods.getCategoryCode());
                    orderDetail.setGoodsName(goods.getGoodsName());
                    orderDetail.setGoodsSellPt(goods.getGoodsSellPt());
                    orderDetail.setGoodsType(goods.getGoodsType());
                    orderDetail.setGoodsLogoUrl(goodsStock.getStockLogo());
                    orderDetail.setMerchantCode(merchantCode);
                    orderDetail.setListTime(goods.getListTime());
                    orderDetail.setDelistTime(goods.getDelistTime());
                    orderDetail.setProDate(goods.getProDate());
                    orderDetail.setKeepDate(goods.getKeepDate());
                    orderDetail.setSupNo(goods.getSupNo());

                    Integer orderDetailSuccess = orderDetailInfoRepository.insert(orderDetail);
                    if (orderDetailSuccess < 1) {
                        LOG.info(requestId, "生成订单", "订单详情表数据插入失败");
                        throw new BusinessException("订单生成失败!", BusinessErrorCode.ORDER_NOT_EXIST);
                    }
                }
            }
        }
        return orderList;
    }

    private Long countGoodsNumGroupByMerchantCode(String merchantCode, List<PurchaseRequestDto> purchaseList) {
        Long goodsNum = 0L;
        for (PurchaseRequestDto purchase : purchaseList) {
            // 查询商品商户详情
            GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(
                    purchase.getGoodsId(), purchase.getGoodsStockId());
            if (goodsDetail.getMerchantCode().equals(merchantCode)) {
                goodsNum += purchase.getBuyNum();
            }
        }
        return goodsNum;
    }

    /**
     * 统计每个商户订单总金额
     *
     * @param purchaseList
     * @return
     */
    public Map<String, BigDecimal> sumMerchantPayment(List<PurchaseRequestDto> purchaseList) {
        Map<String, BigDecimal> merchantPayment = new HashMap<>();
        for (PurchaseRequestDto purchase : purchaseList) {
            // 查询商品商户详情
            GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(
                    purchase.getGoodsId(), purchase.getGoodsStockId());
            String merchantCode = goodsDetail.getMerchantCode();
            if (merchantPayment.containsKey(merchantCode)) {
                BigDecimal haveSum = merchantPayment.get(merchantCode);
                haveSum = haveSum.add(purchase.getPrice().multiply(
                        BigDecimal.valueOf(Long.valueOf(purchase.getBuyNum()))));
                merchantPayment.put(merchantCode, haveSum);
            } else {
                merchantPayment.put(merchantCode,
                        purchase.getPrice().multiply(BigDecimal.valueOf(Long.valueOf(purchase.getBuyNum()))));
            }
        }
        return merchantPayment;
    }

    /**
     * 生成订单前校验
     *
     * @param totalPayment
     * @param addressId
     * @param purchaseList
     * @throws BusinessException
     */
    public void validateCorrectInfo(String requestId, BigDecimal totalPayment, Long addressId, Long userId,
            List<PurchaseRequestDto> purchaseList, String sourceFlag) throws BusinessException {
        // 校验商品的价格是否已经更改
        for (PurchaseRequestDto purchase : purchaseList) {
            BigDecimal price = commonService.calculateGoodsPrice(purchase.getGoodsId(),
                    purchase.getGoodsStockId());
            if (!(purchase.getPrice().compareTo(price) == 0)) {
                LOG.info(requestId, "id为" + purchase.getGoodsId() + "的商品价格发生改变，请重新购买！", purchase
                        .getGoodsStockId().toString());
                throw new BusinessException("商品价格已变动，请重新下单");
            }
        }
        // 校验商品订单总金额
        BigDecimal countTotalPrice = BigDecimal.ZERO;
        for (PurchaseRequestDto purchase : purchaseList) {
            Long buyNum = Long.valueOf(purchase.getBuyNum());
            countTotalPrice = countTotalPrice.add(purchase.getPrice().multiply(BigDecimal.valueOf(buyNum)));
        }
        if (countTotalPrice.compareTo(totalPayment) != 0) {
            LOG.info(requestId, "生成订单前校验,订单总金额计算错误!", countTotalPrice.toString());
            throw new BusinessException("订单总金额计算错误!");
        }

        // 校验商品上下架
        for (PurchaseRequestDto purchase : purchaseList) {
            // 校验商品下架
            this.validateGoodsOffShelf(requestId, purchase.getGoodsId());

        }
        // 校验地址
        AddressInfoEntity address = addressInfoDao.select(addressId);
        if (null == address || address.getUserId().longValue() != userId.longValue()) {
            LOG.info(requestId, "生成订单前校验,校验地址,该用户地址信息不存在", addressId.toString());
            throw new BusinessException("该用户地址信息不存在");
        }

        // 校验商品库存
        for (PurchaseRequestDto purchase : purchaseList) {
            GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(
                    purchase.getGoodsId(), purchase.getGoodsStockId());
            GoodsInfoEntity goodsInfo = goodsDao.select(goodsDetail.getGoodsId());
            if (goodsInfo.getSource() == null) {
                if (goodsDetail.getStockCurrAmt() < purchase.getBuyNum()) {
                    LOG.info(requestId, "生成订单前校验,商品库存不足", goodsDetail.getGoodsStockId().toString());
                    throw new BusinessException(goodsDetail.getGoodsName() + "商品库存不足\n请修改商品数量");
                }
            } else {
            	//校验京东商品购买数量
            	if(purchase.getBuyNum()>200){
                    LOG.info(requestId, "生成订单前校验,京东商品最多只能买200件", goodsDetail.getGoodsStockId().toString());
                    throw new BusinessException(goodsDetail.getGoodsName() + "最多只能买200件哦",BusinessErrorCode.ORDER_JDGOODS_OVERNUMBER);
            	}
                // 校验地址
                AddressInfoEntity address1 = addressInfoDao.select(addressId);
                if (address1.getProvinceCode() == null || address1.getCityCode() == null
                        || address1.getDistrictCode() == null) {
                    LOG.info(requestId, "生成订单前校验,校验地址,存在京东商品时地址错误", addressId.toString());
                    throw new BusinessException("地址信息格式不正确");
                }

            }
            if (StringUtils.isBlank(goodsInfo.getSource())) {
                if (goodsDetail.getStockCurrAmt() < purchase.getBuyNum()) {
                    LOG.info(requestId, "生成订单前校验,商品库存不足", goodsDetail.getGoodsStockId().toString());
                    throw new BusinessException("抱歉，您的订单内含库存不足商品\n请修改商品数量");
                }
                if (purchase.getBuyNum() <= 0) {
                    LOG.info(requestId, "生成订单前校验,商品购买数量为0", purchase.getBuyNum().toString());
                    throw new BusinessException("商品" + goodsDetail.getGoodsName() + "购买数量不能为零");
                }
            }
            // 校验购物车
            if (StringUtils.isNotEmpty(sourceFlag) && sourceFlag.equals(ORDERSOURCECARTFLAG)) {
                CartInfoEntity cart = new CartInfoEntity();
                cart.setUserId(userId);
                cart.setGoodsStockId(purchase.getGoodsStockId());
                List<CartInfoEntity> cartInfoList = cartInfoRepository.filter(cart);
                if (null == cartInfoList || cartInfoList.isEmpty()) {
                    LOG.info(requestId, "生成订单前校验,校验购物车,订单商品购物车中不存在", purchase.getGoodsStockId().toString());
                    throw new BusinessException("订单商品购物车中不存在");
                }
            }
        }
    }

    /**
     * 校验商品下架
     *
     * @param goodsId 商品id
     * @throws BusinessException
     */
    public void validateGoodsOffShelf(String requestId, Long goodsId) throws BusinessException {
        Date now = new Date();
        GoodsInfoEntity goodsInfo = goodsDao.select(goodsId);
        if (null == goodsInfo) {
            LOG.info(requestId, "校验商品下架,根据商品id查询商品信息数据为空", goodsId.toString());
            throw new BusinessException("商品号:" + goodsId + ",不存在或商户号不存在！");
        }
        if (now.before(goodsInfo.getListTime())
                || !GoodStatus.GOOD_UP.getCode().equals(goodsInfo.getStatus())) {
            LOG.info(requestId, "校验商品下架,商品已下架", goodsId.toString());
            throw new BusinessException("抱歉，您的订单内含下架商品\n请重新下单");
        }
        if (goodsInfo.getSource() == null) {
            if (now.after(goodsInfo.getDelistTime())) {
                LOG.info(requestId, "校验商品下架,商品已下架", goodsId.toString());
                throw new BusinessException("抱歉，您的订单内含下架商品\n请重新下单");
            }
            List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodsId);
            boolean offShelfFlag = true;
            for (GoodsStockInfoEntity goodsStock : goodsList) {
                if (goodsStock.getStockCurrAmt() > 0) {
                    offShelfFlag = false;
                    break;
                }
            }
            if (offShelfFlag) {
                LOG.info(requestId, "校验商品下架,商品各规格数量都为0,已下架", goodsId.toString());
                throw new BusinessException("抱歉，您的订单内含下架商品\n请重新下单");
            }
        }

    }

    /**
     * 校验商品下架和库存不足[支付校验使用]
     *
     * @param goodsId 商品Id
     * @param goodsStockId 库存Id
     * @param buyNum 购买数量
     * @throws BusinessException
     */
    public void validateGoodsStock(String requestId, Long goodsId, Long goodsStockId, Long buyNum,
            String orderId) throws BusinessException {
        Date now = new Date();
        // Step 1 校验商品上下架
        GoodsInfoEntity goodsInfo = goodsDao.select(goodsId);
        if (null == goodsInfo) {
            LOG.info(requestId, "商品:" + goodsId + "不存在", "");
            throw new BusinessException("商品号:" + goodsId + ",不存在或商户号不存在！");
        }
        if (now.before(goodsInfo.getListTime()) || now.after(goodsInfo.getDelistTime())
                || !GoodStatus.GOOD_UP.getCode().equals(goodsInfo.getStatus())) {
            LOG.info(requestId, "支付失败您的订单含有下架商品", "");
            throw new BusinessException("支付失败您的订单含有下架商品");
        }
        GoodsStockLogEntity stockLog = goodsStcokLogDao.loadByOrderId(orderId);
        if (null != stockLog) {
            LOG.info(requestId, "该订单已减库存不做减库存操作", orderId);
            return;
        }
        List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodsId);
        // 不为京东商品
        if (goodsInfo.getSource() == null) {
            boolean offShelfFlag = true;
            for (GoodsStockInfoEntity goodsStock : goodsList) {
                if (goodsStock.getStockCurrAmt() > 0) {
                    offShelfFlag = false;
                    break;
                }
            }
            if (offShelfFlag) {
                LOG.info(requestId, "支付失败您的订单含有下架商品", "");
                throw new BusinessException("支付失败您的订单含有下架商品");
            }

            // Step 2 校验商品库存
            GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(goodsId,
                    goodsStockId);

            if (goodsDetail.getStockCurrAmt() < buyNum) {
                LOG.info(requestId, "支付失败您的订单商品库存不足", "");
                throw new BusinessException("支付失败您的订单商品库存不足");
            }
            if (buyNum <= 0) {
                LOG.info(requestId, "购买数量不能为零", goodsDetail.getGoodsName() + "购买数量不能为零");
                throw new BusinessException("商品" + goodsDetail.getGoodsName() + "购买数量不能为零");
            }
        }
    }

    /**
     * 取消订单
     *
     * @param userId
     * @param orderId
     * @throws BusinessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class,
            BusinessException.class })
    public void cancelOrder(String requestId, Long userId, String orderId) throws BusinessException {
        OrderInfoEntity order = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
        if (null == order) {
            LOG.info(requestId, "查询订单表信息", "数据为空");
            throw new BusinessException("对不起!该订单不存在!", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        if (!OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
            LOG.info(requestId, "校验订单状态", "当前订单状态不能取消该订单");
            throw new BusinessException("对不起!当前订单状态不能取消该订单", BusinessErrorCode.ORDERSTATUS_NOTALLOW_CANCEL);
        }
        dealWithInvalidOrder(requestId, order);
    }

    /**
     * 处理取消订单
     *
     * @throws BusinessException
     */
    @Transactional(rollbackFor = { Exception.class, BusinessException.class })
    public void dealWithInvalidOrder(String requestId, OrderInfoEntity order) throws BusinessException {

        String orderId = order.getOrderId();
        // 更新订单状态
        LOG.info(requestId, "取消订单,更改订单状态为订单失效", orderId);
        orderInfoRepository.updateStatusByOrderId(orderId, OrderStatus.ORDER_CANCEL.getCode());

        if (SourceType.JD.getCode().equals(order.getSource())) {
            return;
        }
        // 回滚库存
        GoodsStockLogEntity goodsStockLog = goodsStcokLogDao.loadByOrderId(orderId);
        if (null != goodsStockLog) {
            LOG.info(requestId, "取消订单,加库存操作start...", orderId);
            addGoodsStock(requestId, orderId);
            LOG.info(requestId, "取消订单,加库存操作end...", orderId);
            LOG.info(requestId, "取消订单,删除商品库存消耗记录", orderId);
            goodsStcokLogDao.deleteByOrderId(orderId);
        }
    }

    /**
     * 加库存
     *
     * @param orderId
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public void addGoodsStock(String requestId, String orderId) throws BusinessException {
        Integer errorNum = errorNo;
        List<OrderDetailInfoEntity> orderDetailList = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
        // 加库存
        for (OrderDetailInfoEntity orderDetail : orderDetailList) {
            try {
                for (int i = 0; i < errorNum; i++) {
                    GoodsStockInfoEntity goodsStock = goodsStockDao.select(orderDetail.getGoodsStockId());
                    if (null == goodsStock) {
                        LOG.info(requestId, "加库存,根据商品库存id查询商品库存信息,数据为空", orderDetail.getGoodsStockId()
                                .toString());
                        throw new BusinessException("商品信息不存在,请联系客服!");
                    }
                    goodsStock.setStockAmt(goodsStock.getStockCurrAmt());
                    // 加库存
                    Long stockCurrAmt = goodsStock.getStockCurrAmt() + orderDetail.getGoodsNum();
                    goodsStock.setStockCurrAmt(stockCurrAmt);
                    if (stockCurrAmt > goodsStock.getStockTotalAmt()) {
                        LOGGER.error("当前库存不能大于总库存");
                        throw new BusinessException("当前库存不能大于总库存", BusinessErrorCode.GOODSSTOCK_UPDATE_ERROR);
                    }

                    Integer successFlag = goodsStockDao.updateCurrAmtAndTotalAmount(goodsStock);
                    if (successFlag == 0) {
                        if (errorNum <= 0) {
                            LOG.info(requestId, "加库存,修改库存尝试次数已达3次", orderDetail.getGoodsStockId().toString());
                            throw new BusinessException("网络异常稍后再试");
                        }
                        errorNum--;
                        continue;
                    } else if (successFlag > 1) {
                        LOG.info(requestId, "加库存,商品库存更新异常", orderDetail.getGoodsStockId().toString());
                        throw new BusinessException(goodsStock.getGoodsName() + "商品库存更新异常请联系客服!");
                    } else if (successFlag == 1) {
                        break;
                    }

                }

            } catch (Exception e) {
                LOG.info(requestId, "加库存操作失败", orderId);
                LOGGER.error("加库存操作失败", e);
                continue;
            }
            goodsStcokLogDao.deleteByOrderId(orderId);
        }
    }

    /**
     * 延迟收货
     *
     * @param userId
     * @param orderId
     * @throws BusinessException
     */
    @Transactional
    public void deleyReceiveGoods(String requestId, Long userId, String orderId) throws BusinessException {

        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单信息,数据为空", orderId);
            throw new BusinessException("对不起!订单信息为空", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        if (!OrderStatus.ORDER_SEND.getCode().equals(orderInfo.getStatus())) {
            LOG.info(requestId, "校验订单状态,当前订单状态不能延迟收货", orderId);
            throw new BusinessException("当前订单状态不能延迟收货", BusinessErrorCode.ORDER_DELEY_RECEIVE_ERROR);
        }
        if (orderInfo.getExtendAcceptGoodsNum() >= 1) {
            LOG.info(requestId, "每笔订单只能一次延长收货", orderId);
            throw new BusinessException("每笔订单只能延长一次", BusinessErrorCode.ORDER_DELEY_RECEIVE_ERROR);
        }

        if (null != orderInfo.getLastAcceptGoodsDate()) {
            Date lastAcceptDate = DateFormatUtil.addDays(orderInfo.getLastAcceptGoodsDate(), 3);
            orderInfo.setLastAcceptGoodsDate(lastAcceptDate);
        }
        orderInfo.setExtendAcceptGoodsNum(1);
        orderInfoRepository.update(orderInfo);
    }

    /**
     * 确认收货
     *
     * @param userId
     * @param orderId
     * @throws BusinessException
     */
    @Transactional
    public void confirmReceiveGoods(String requestId, Long userId, String orderId) throws BusinessException {
        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单信息,数据为空", orderId);
            throw new BusinessException("对不起!订单信息为空", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        if (!OrderStatus.ORDER_SEND.getCode().equals(orderInfo.getStatus())) {
            LOG.info(requestId, "当前订单状态不能确认收货", orderId);
            throw new BusinessException("当前订单状态不能确认收货", BusinessErrorCode.ORDER_CONFIRM_ERROR);
        }
        // 判断如果订单的是否发货状态不为Y(发货)，则置为Y
        if (!StringUtils.equals(PreDeliveryType.PRE_DELIVERY_Y.getCode(), orderInfo.getPreDelivery())) {
            orderInfo.setPreDelivery(PreDeliveryType.PRE_DELIVERY_Y.getCode());
        }
        orderInfo.setAcceptGoodsDate(new Date());
        orderInfo.setAcceptGoodsType(AcceptGoodsType.USERCONFIRM.getCode());
        orderInfo.setStatus(OrderStatus.ORDER_COMPLETED.getCode());
        orderInfoRepository.update(orderInfo);
    };

    /**
     * 删除订单
     *
     * @param userId
     * @param orderId
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderInfo(String requestId, Long userId, String orderId) throws BusinessException {
        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单信息,数据为空", orderId);
            throw new BusinessException("对不起!订单信息为空", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        if (!OrderStatus.ORDER_CANCEL.getCode().equals(orderInfo.getStatus())
                && !OrderStatus.ORDER_COMPLETED.getCode().equals(orderInfo.getStatus())) {
            LOG.info(requestId, "当前订单状态不能删除订单", orderId);
            throw new BusinessException("当前订单状态不能删除订单", BusinessErrorCode.ORDER_DELETE_ERROR);
        }
        orderInfoRepository
                .updateStatusByOrderId(orderInfo.getOrderId(), OrderStatus.ORDER_DELETED.getCode());
    }

    /**
     * 根据 userId、订单状态(可选) 查询订单信息
     *
     * @param userId 用户id
     * @return
     * @throws BusinessException
     */
    public List<OrderDetailInfoDto> getOrderDetailInfo(String requestId, String userId, String orderStatus)
            throws BusinessException {

        Long userIdVal = Long.valueOf(userId);
        OrderInfoEntity orderInfo = new OrderInfoEntity();
        orderInfo.setUserId(userIdVal);
        if (StringUtils.isNotBlank(orderStatus)) {
            orderInfo.setStatus(orderStatus);
        }

        List<OrderDetailInfoDto> returnOrders = new ArrayList<OrderDetailInfoDto>();
        // 查询客户的所有订单
        List<OrderInfoEntity> orderList = orderInfoRepository.filter(orderInfo);

        if (null == orderList || orderList.isEmpty()) {
            return Collections.emptyList();
        }

        for (OrderInfoEntity order : orderList) {
            OrderDetailInfoDto orderDetailInfoDto = getOrderDetailInfoDto(requestId, order);

            returnOrders.add(orderDetailInfoDto);

            try {
                if (OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
                    PayRequestDto req = new PayRequestDto();
                    req.setOrderId(order.getOrderId());
                    String payRealStatus = "";
                    Response response = paymentHttpClient.gateWayTransStatusQuery(requestId, req);
                    if (!response.statusResult()) {
                        payRealStatus = "1";
                    } else {
                        payRealStatus = (String) response.getData();
                    }
                    // 0:支付成功 非零:支付失败
                    if (!YesNo.NO.getCode().equals(payRealStatus)) {
                        GoodsStockLogEntity sotckLog = goodsStcokLogDao.loadByOrderId(order.getOrderId());
                        if (null == sotckLog) {
                            continue;
                        }
                        LOG.info(requestId, "库存记录日志", sotckLog.getOrderId());
                        // 存在回滚
                        addGoodsStock(requestId, order.getOrderId());
                    }
                }

            } catch (Exception e) {
                LOGGER.error("订单查询未支付订单商品库存回滚异常", e);
                LOG.info(requestId, "订单查询未支付订单商品库存回滚异常:orderId:" + order.getOrderId(), "");
            }
        }
        return returnOrders;
    }

    /**
     * 根据订单的Id和状态查询，订单的详情
     * 
     * @param requestId
     * @param orderId
     * @param orderStatus
     * @return
     * @throws BusinessException
     */
    public List<OrderDetailInfoDto> getOrderDetailInfoByOrderId(String requestId, String orderId,
            String orderStatus) throws BusinessException {

        OrderInfoEntity orderInfo = new OrderInfoEntity();
        orderInfo.setOrderId(orderId);
        if (StringUtils.isNotBlank(orderStatus)) {
            orderInfo.setStatus(orderStatus);
        }

        List<OrderDetailInfoDto> returnOrders = new ArrayList<OrderDetailInfoDto>();
        // 查询客户的所有订单
        List<OrderInfoEntity> orderList = orderInfoRepository.filter(orderInfo);

        if (null == orderList || orderList.isEmpty()) {
            return Collections.emptyList();
        }

        for (OrderInfoEntity order : orderList) {
            OrderDetailInfoDto orderDetailInfoDto = getOrderDetailInfoDto(requestId, order);

            returnOrders.add(orderDetailInfoDto);

            try {
                if (OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
                    PayRequestDto req = new PayRequestDto();
                    req.setOrderId(order.getOrderId());
                    String payRealStatus = "";
                    Response response = paymentHttpClient.gateWayTransStatusQuery(requestId, req);
                    if (!response.statusResult()) {
                        payRealStatus = "1";
                    } else {
                        payRealStatus = (String) response.getData();
                    }
                    // 0:支付成功 非零:支付失败
                    if (!YesNo.NO.getCode().equals(payRealStatus)) {
                        GoodsStockLogEntity sotckLog = goodsStcokLogDao.loadByOrderId(order.getOrderId());
                        if (null == sotckLog) {
                            continue;
                        }
                        LOG.info(requestId, "库存记录日志", sotckLog.getOrderId());
                        // 存在回滚
                        addGoodsStock(requestId, order.getOrderId());
                        goodsStcokLogDao.deleteByOrderId(order.getOrderId());
                    }
                }

            } catch (Exception e) {
                LOGGER.error("订单查询未支付订单商品库存回滚异常", e);
                LOG.info(requestId, "订单查询未支付订单商品库存回滚异常:orderId:" + order.getOrderId(), "");
            }
        }
        return returnOrders;
    }

    public OrderDetailInfoDto getOrderDetailInfoDto(String requestId, String orderId)
            throws BusinessException {

        if (StringUtils.isBlank(orderId)) {
            throw new BusinessException("订单Id不能为空!", BusinessErrorCode.PARAM_IS_EMPTY);
        }

        OrderInfoEntity entity = orderInfoRepository.selectByOrderId(orderId);

        OrderDetailInfoDto dto = getOrderDetailInfoDto(requestId, entity);
        List<GoodsInfoInOrderDto> goodsInfoInOrderDtoList = dto.getOrderDetailInfoList();
        for (GoodsInfoInOrderDto goodsInfoInOrderDto : goodsInfoInOrderDtoList) {
            if (StringUtils.isEmpty(dto.getSource())){
                goodsInfoInOrderDto.setGoodsLogoUrlNew(imageService.getImageUrl(EncodeUtils
                        .base64Decode(goodsInfoInOrderDto.getGoodsLogoUrl())));
            }else{
                goodsInfoInOrderDto.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/"+EncodeUtils
                        .base64Decode(goodsInfoInOrderDto.getGoodsLogoUrl()));
            }

        }
        return dto;
    }

    private OrderDetailInfoDto getOrderDetailInfoDto(String requestId, OrderInfoEntity order)
            throws BusinessException {
        // 通过子订单号查询订单详情
        OrderDetailInfoEntity orderDetailParam = new OrderDetailInfoEntity();
        orderDetailParam.setOrderId(order.getOrderId());
        List<OrderDetailInfoEntity> orderDetailInfoList = orderDetailInfoRepository.filter(orderDetailParam);

        List<GoodsInfoInOrderDto> goodsListInEachOrder = new ArrayList<GoodsInfoInOrderDto>();
        // 每笔订单商品数目
        int goodsSum = 0;
        for (OrderDetailInfoEntity orderDetailInfo : orderDetailInfoList) {
            goodsSum += orderDetailInfo.getGoodsNum();

            GoodsInfoInOrderDto goodsInfo = new GoodsInfoInOrderDto();
            goodsInfo.setGoodsId(orderDetailInfo.getGoodsId());
            goodsInfo.setGoodsStockId(orderDetailInfo.getGoodsStockId());
            goodsInfo.setBuyNum(orderDetailInfo.getGoodsNum());
            GoodsStockInfoEntity goodsStock = goodsStockDao.select(orderDetailInfo.getGoodsStockId());
            GoodsInfoEntity goods = goodsDao.select(orderDetailInfo.getGoodsId());
            if (null != goodsStock) {
                goodsInfo.setGoodsLogoUrl(goodsStock.getGoodsLogoUrl());
                goodsInfo.setGoodsSkuAttr(goodsStock.getGoodsSkuAttr());
            }else{
                if (null != goods) {
                    goodsInfo.setGoodsLogoUrl(goods.getGoodsLogoUrl());
                }
            }
            goodsInfo.setGoodsName(orderDetailInfo.getGoodsName());
            goodsInfo.setGoodsPrice(orderDetailInfo.getGoodsPrice());
            goodsInfo.setGoodsTitle(orderDetailInfo.getGoodsTitle());
            if (null != goods) {
                goodsInfo.setUnSupportProvince(goods.getUnSupportProvince());
            }
            goodsListInEachOrder.add(goodsInfo);
        }
        OrderDetailInfoDto orderDetailInfoDto = new OrderDetailInfoDto();
        orderDetailInfoDto.setOrderId(order.getOrderId());
        orderDetailInfoDto.setOrderAmt(order.getOrderAmt());
        orderDetailInfoDto.setGoodsNumSum(goodsSum);
        orderDetailInfoDto.setStatus(order.getStatus());
        orderDetailInfoDto.setOrderDetailInfoList(goodsListInEachOrder);

        // 待付款订单计算剩余付款时间
        if (order.getStatus().equals(OrderStatus.ORDER_NOPAY.getCode())) {
            if (DateFormatUtil.isExpired(order.getCreateDate(), 1)) {
                dealWithInvalidOrder(requestId, order);
                orderDetailInfoDto.setStatus(OrderStatus.ORDER_CANCEL.getCode());
            } else {
                orderDetailInfoDto.setRemainingTime(DateFormatUtil.getDateDiff(
                        DateFormatUtil.addDays(order.getCreateDate(), 1), new Date()));
            }

        }

        orderDetailInfoDto.setOrderCreateDate(order.getCreateDate());
        orderDetailInfoDto.setOrderCreateDateStr(DateFormatUtil.dateToString(order.getCreateDate(), "yyyy-MM-dd HH:mm"));
        orderDetailInfoDto.setProvince(order.getProvince());
        orderDetailInfoDto.setCity(order.getCity());
        orderDetailInfoDto.setDistrict(order.getDistrict());
        orderDetailInfoDto.setAddress(order.getAddress());
        orderDetailInfoDto.setName(order.getName());
        orderDetailInfoDto.setTelephone(order.getTelephone());
        orderDetailInfoDto.setAddressId(order.getAddressId());
        // if (StringUtils.isNotEmpty(orderStatus) &&
        // OrderStatus.ORDER_SEND.getCode().equals(orderStatus)) {
        orderDetailInfoDto.setDelayAcceptGoodFlag(order.getExtendAcceptGoodsNum() + "");
        // }
        // 账单分期后改为删除按钮
        boolean billOverDueFlag = billService.queryStatement(order.getUserId(), order.getOrderId());
        if (billOverDueFlag) {
            LOGGER.info("userId={},账单分期已逾期", order.getUserId());
            orderDetailInfoDto.setRefundAllowedFlag("0");
        } else {
            try {
                orderDetailInfoDto.setRefundAllowedFlag("1");
                // 交易完成的订单是否允许售后操作校验
                afterSaleService.orderRufundValidate(requestId, order.getUserId(), order.getOrderId(), order);
            } catch (Exception e) {
                LOG.info(requestId, "这个捕获只是为了过滤掉订单售后校验逻辑抛出的异常", "");
                LOGGER.error(e.getMessage(), e);
                // 这个捕获只是为了过滤掉 订单售后校验 抛出的 异常
                orderDetailInfoDto.setRefundAllowedFlag("0");
            }
        }
        orderDetailInfoDto.setPreDelivery(order.getPreDelivery());
        orderDetailInfoDto.setUserId(order.getUserId());
        orderDetailInfoDto.setMainOrderId(order.getMainOrderId());
        orderDetailInfoDto.setSource(order.getSource());
        return orderDetailInfoDto;
    }

    /**
     * 获取用户 待付款、待发货、待收货 订单数量
     *
     * @param userId
     * @return
     */
    public Map<String, String> getOrderNum(String userId) {
        Long userIdVal = Long.valueOf(userId);
        Map<String, String> map = new HashMap<String, String>();
        List<IdNum> subOrderNumList = orderInfoRepository.getOrderNum(userIdVal);
        /**
         * D00(待付款)、D02(待发货)、D03(待收货)
         */
        for (IdNum idNum : subOrderNumList) {
            map.put(idNum.getId(), idNum.getNum());
        }
        return map;
    }

    /**
     * 查询订单收货地址
     *
     * @param resultMap
     * @param orderId
     * @throws Exception
     */
    public void loadInfoByOrderId(String requestId, Map<String, Object> resultMap, String orderId)
            throws Exception {
        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, null);
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单信息,数据为空", orderId);
            throw new BusinessException("该订单信息不存在!", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        resultMap.put("orderInfo", orderInfo);
        if (orderInfo.getStatus().equals(OrderStatus.ORDER_COMPLETED.getCode())) {
            List<Trace> traces = logisticsService.getSignleTrackingsByOrderId(orderId);
            LOG.info(requestId, "获取物流轨迹", traces.toString());
            if (null != traces && traces.size() > 0) {
                Trace trace = traces.get(0);
                resultMap.put("trace", trace);
            }
        }
    }

    /**
     * 修改订单收货地址
     *
     * @param addressId
     * @param orderId
     * @param userId
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyShippingAddress(String requestId, Long addressId, String orderId, Long userId)
            throws BusinessException {
        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单信息,数据为空", orderId);
            throw new BusinessException("该订单信息不存在!", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        if (!OrderStatus.ORDER_NOPAY.getCode().equals(orderInfo.getStatus())) {
            LOG.info(requestId, "当前状态不能修改订单地址", orderId);
            throw new BusinessException("当前状态不能修改订单地址", BusinessErrorCode.ADDRESS_UPDATE_FAILED);
        }
        AddressInfoEntity address = addressInfoDao.select(addressId);
        if (null == address) {
            LOG.info(requestId, "查询地址信息,数据为空", addressId.toString());
            throw new BusinessException("当前地址信息不存在", BusinessErrorCode.ADDRESS_NOT_EXIST);
        }
        orderInfo.setProvince(address.getProvince());
        orderInfo.setCity(address.getCity());
        orderInfo.setAddress(address.getAddress());
        orderInfo.setDistrict(address.getDistrict());
        orderInfo.setName(address.getName());
        orderInfo.setTelephone(address.getTelephone());
        orderInfo.setAddressId(address.getId());
        orderInfoRepository.update(orderInfo);
    }

    /**
     * 重新下单[初始化]
     *
     * @param userId
     * @param orderId
     * @throws BusinessException
     */
    public void repeatConfirmOrder(String requestId, Long userId, String orderId,
            Map<String, Object> resultMap) throws BusinessException {
        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单信息,数据为空", orderId);
            throw new BusinessException("该订单不存在!", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<GoodsInfoInCartEntity> goodsList = new ArrayList<GoodsInfoInCartEntity>();
        List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
        // 商品列表信息 [商品名称+商品规格+商品价格+商品购买数量]
        for (OrderDetailInfoEntity orderDetail : orderDetails) {
            // 判断下架
            this.validateGoodsOffShelf(requestId, orderDetail.getGoodsId());
            // 订单信息
            GoodsStockInfoEntity goodsStock = goodsStockDao.select(orderDetail.getGoodsStockId());
            GoodsInfoEntity goods = goodsDao.select(orderDetail.getGoodsId());
            // 判断库存
            if (goodsStock.getStockCurrAmt() <= 0) {
                throw new BusinessException(orderDetail.getGoodsName() + "商品库存不足!");
            }
            GoodsInfoInCartEntity goodInfo = new GoodsInfoInCartEntity();
            goodInfo.setGoodsId(goodsStock.getGoodsId());
            goodInfo.setGoodsStockId(goodsStock.getGoodsStockId());
            goodInfo.setGoodsName(orderDetail.getGoodsName());
            goodInfo.setGoodsLogoUrl(goodsStock.getStockLogo());
            if (orderDetail.getGoodsNum() > goodsStock.getStockCurrAmt()) {
                goodInfo.setGoodsNum(goodsStock.getStockCurrAmt().intValue());
            } else {
                goodInfo.setGoodsNum(orderDetail.getGoodsNum().intValue());
            }
            BigDecimal goodsPrice = commonService.calculateGoodsPrice(goodsStock.getGoodsId(),
                    goodsStock.getGoodsStockId());

            goodInfo.setGoodsSelectedPrice(goodsPrice);
            goodInfo.setGoodsSkuAttr(goodsStock.getGoodsSkuAttr());
            goodInfo.setMerchantCode(orderInfo.getMerchantCode());
            // 商品新地址
            goodInfo.setGoodsLogoUrlNew(imageService.getImageUrl(EncodeUtils.base64Decode(goodInfo
                    .getGoodsLogoUrl())));
            if (null != goods) {
                goodInfo.setUnSupportProvince(goods.getUnSupportProvince());
            }
            goodsList.add(goodInfo);
            // 订单总金额
            totalAmount = totalAmount.add(goodsPrice.multiply(BigDecimal.valueOf(goodInfo.getGoodsNum())));
        }
        resultMap.put("goodsList", goodsList);
        // 商品总金额
        resultMap.put("totalAmount", totalAmount);
        // 订单收货地址
        AddressInfoEntity address = addressInfoDao.select(orderInfo.getAddressId());

        if (null == address) {
            AddressInfoEntity addressInfo = addressInfoDao.queryOneAddressByUserId(userId);
            resultMap.put("addressInfo", addressInfo);
        } else {
            resultMap.put("addressInfo", address);
        }
    }

    /**
     * 订单查询导出查询
     *
     * @param map
     * @param page
     * @return
     * @throws BusinessException
     */
    public Pagination<OrderSubInfoEntity> queryOrderSubDetailInfoByParamForExport(Map map, Page page)
            throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderDetailInfoListForExport = orderSubInfoRepository
                    .queryOrderSubDetailInfoByParamForExport(map, page);
            return orderDetailInfoListForExport;
        } catch (Exception e) {
            LOGGER.error(" 通过商户号查询订单详细信息失败===>", e);
            throw new BusinessException(" 通过商户号查询订单详细信息失败！", e);
        }
    }

    /**
     * 重新下单异常[库存为零或商品下架]添加至购物车
     *
     * @param orderId
     * @throws BusinessException
     */
    @Transactional(rollbackFor = { Exception.class, BusinessException.class })
    public void reOrder(String requestId, String orderId, Long userId) throws BusinessException {
        List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
        Date now = new Date();
        for (OrderDetailInfoEntity orderDetail : orderDetails) {
            // step1:下架或库存为零商品不做处理
            // 下架商品不处理
            GoodsInfoEntity goodsInfo = goodsDao.select(orderDetail.getGoodsId());
            if (now.before(goodsInfo.getListTime()) || now.after(goodsInfo.getDelistTime())
                    || !GoodStatus.GOOD_UP.getCode().equals(goodsInfo.getStatus())) {
                continue;
            }
            // 该商品下所有库存为零默认为下架
            List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(orderDetail.getGoodsId());
            boolean offShelfFlag = true;
            for (GoodsStockInfoEntity goodsStock : goodsList) {
                if (goodsStock.getStockCurrAmt() > 0) {
                    offShelfFlag = false;
                    break;
                }
            }
            if (offShelfFlag) {
                continue;
            }
            // 库存为零不做处理
            GoodsStockInfoEntity goodsStock = goodsStockDao.select(orderDetail.getGoodsStockId());
            if (goodsStock.getStockCurrAmt() <= 0) {
                continue;
            }

            // setp2:添加至购物车[插入或更新数量]
            // 商品价格
            BigDecimal selectPrice = commonService.calculateGoodsPrice(orderDetail.getGoodsId(),
                    orderDetail.getGoodsStockId());
            // 加入购物车中数量
            int buyNum = 0;
            if (goodsStock.getStockCurrAmt() >= orderDetail.getGoodsNum()) {
                buyNum = orderDetail.getGoodsNum().intValue();
            } else {
                buyNum = goodsStock.getStockCurrAmt().intValue();
            }
            // 获取用户购物车中商品信息
            CartInfoEntity cartDto = new CartInfoEntity();
            cartDto.setUserId(userId);
            List<CartInfoEntity> cartInfoList = cartInfoRepository.filter(cartDto);
            // 标记购物车中是否已存在该商品
            boolean goodsFlag = false;

            // 购物车已存在该商品，则增加数量
            if (null != cartInfoList && !cartInfoList.isEmpty()) {
                for (CartInfoEntity cartinfo : cartInfoList) {
                    if (cartinfo.getGoodsStockId().longValue() == orderDetail.getGoodsStockId().longValue()) {

                        int totalNum = cartinfo.getGoodsNum() + buyNum;
                        goodsFlag = true;
                        CartInfoEntity saveToCart = new CartInfoEntity();
                        saveToCart.setId(cartinfo.getId());
                        saveToCart.setGoodsSelectedPrice(selectPrice);
                        saveToCart.setGoodsNum(totalNum);
                        saveToCart.setIsSelect("1");

                        Integer updateFlag = cartInfoRepository.update(saveToCart);
                        if (updateFlag != 1) {
                            LOG.info(requestId, "添加商品到购物车,更新商品数量失败", "");
                            throw new BusinessException("添加商品到购物车失败", BusinessErrorCode.GOODS_ADDTOCART_ERROR);
                        }
                        break;
                    }
                }
            }
            // 购物车不存该商品，则插入该商品信息
            if (!goodsFlag) {
                int numOfType = null == cartInfoList ? 0 : cartInfoList.size();
                if (numOfType >= 99) {
                    LOG.info(requestId, "购物车商品种类数已满", String.valueOf(numOfType));
                    throw new BusinessException("您的购物车已满，快去结算吧!", BusinessErrorCode.CART_FULL);
                }
                CartInfoEntity saveToCart = new CartInfoEntity();
                saveToCart.setUserId(userId);
                saveToCart.setGoodsStockId(orderDetail.getGoodsStockId());
                saveToCart.setGoodsSelectedPrice(selectPrice);
                saveToCart.setGoodsNum(buyNum);
                saveToCart.setIsSelect("1");
                cartInfoRepository.insert(saveToCart);
                if (null == saveToCart.getId()) {
                    LOG.info(requestId, "添加商品到购物车,插入商品数据失败", "");
                    throw new BusinessException("添加商品到购物车失败", BusinessErrorCode.GOODS_ADDTOCART_ERROR);
                }
            }
        }
    }

    /**
     * 修改待付款订单收货地址
     *
     * @param orderId
     * @param userId
     * @param addressInfoDto
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyOrderAddress(String requestId, String orderId, String userId,
            AddressInfoEntity addressInfoDto) throws BusinessException {

        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId,
                addressInfoDto.getUserId());
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单信息,数据为空", "");
            throw new BusinessException("该订单信息不存在!", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        if (!OrderStatus.ORDER_NOPAY.getCode().equals(orderInfo.getStatus())) {
            LOG.info(requestId, "当前订单状态不能修改地址", "");
            throw new BusinessException("当前订单状态不能修改地址", BusinessErrorCode.ADDRESS_UPDATE_FAILED);
        }

        // 待付款订单不能修改省、市、区地址
        if (!orderInfo.getProvince().equals(addressInfoDto.getProvince())
                || !orderInfo.getCity().equals(addressInfoDto.getCity())
                || !orderInfo.getDistrict().equals(addressInfoDto.getDistrict())) {
            LOG.info(requestId, "待付款订单不能修改省、市、区地址", "");
            throw new BusinessException("待付款订单不能修改省、市、区地址", BusinessErrorCode.ADDRESS_UPDATE_FAILED);
        }
        Long addressId = orderInfo.getAddressId();
        AddressInfoEntity addressInfoEntity = addressService.queryOneAddressByAddressId(orderInfo
                .getAddressId());
        if (addressInfoEntity == null) {
            addressId = addressService.addAddressInfo(addressInfoDto);
        } else {
            addressInfoEntity.setUserId(addressInfoDto.getUserId());
            addressInfoEntity.setTelephone(addressInfoDto.getTelephone());
            addressInfoEntity.setAddressId(addressId);
            addressInfoEntity.setAddress(addressInfoDto.getAddress());
            addressInfoEntity.setCity(addressInfoDto.getCity());
            addressInfoEntity.setProvince(addressInfoDto.getProvince());
            addressInfoEntity.setDistrict(addressInfoDto.getDistrict());
            addressInfoEntity.setName(addressInfoDto.getName());
            addressService.updateAddressInfo(addressInfoEntity);
        }

        OrderInfoEntity orderInfoDto = new OrderInfoEntity();
        orderInfoDto.setId(orderInfo.getId());
        orderInfoDto.setAddress(addressInfoDto.getAddress());
        orderInfoDto.setName(addressInfoDto.getName());
        orderInfoDto.setTelephone(addressInfoDto.getTelephone());
        orderInfoDto.setCity(addressInfoDto.getCity());
        orderInfoDto.setProvince(addressInfoDto.getProvince());
        orderInfoDto.setDistrict(addressInfoDto.getDistrict());
        orderInfoDto.setAddressId(addressId);
        orderInfoRepository.update(orderInfoDto);

    }

    /**
     * 根据订单列表获取订单详情列表
     *
     * @param orderList
     * @return
     * @throws BusinessException
     */
    public List<OrderDetailInfoEntity> loadOrderDetail(List<OrderInfoEntity> orderList)
            throws BusinessException {
        List<OrderDetailInfoEntity> resultDetailList = Lists.newArrayList();
        for (OrderInfoEntity order : orderList) {
            List<OrderDetailInfoEntity> orderDetailList = orderDetailInfoRepository
                    .queryOrderDetailInfo(order.getOrderId() + "");
            for (OrderDetailInfoEntity orderDetail : orderDetailList) {
                resultDetailList.add(orderDetail);
            }
        }
        return resultDetailList;
    }

    /**
     * 根据订单号和用户id查询订单信息
     *
     * @param orderId
     * @return
     * @throws BusinessException
     */
    public OrderInfoEntity selectByOrderId(String orderId) throws BusinessException {
        OrderInfoEntity OorderInfoEntity = orderInfoRepository.selectByOrderId(orderId);
        return OorderInfoEntity;
    }

    /**
     * 待付款页付款库存不足或商品下架时 删除订单加入购物车
     *
     * @param orderId
     * @param userId
     * @throws BusinessException
     */
    @Transactional(rollbackFor = { Exception.class, BusinessException.class })
    public void payAfterFail(String orderId, Long userId) throws BusinessException {
        OrderInfoEntity order = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
        if (null == order) {
            throw new BusinessException("对不起!该订单不存在!", BusinessErrorCode.ORDER_NOT_EXIST);
        }
        if (!OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
            throw new BusinessException("对不起,当前订单状态不合法", BusinessErrorCode.ORDER_STATUS_INVALID);
        }
        // 校验是否有库存不足或商品下架
        List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
        if (null == orderDetails || orderDetails.size() == 0) {
            throw new BusinessException("该订单信息异常", BusinessErrorCode.ORDER_DETAIL_ERROR);
        }

        reOrder("", orderId, userId);
        // 删除订单
        orderInfoRepository.updateStatusByOrderId(orderId, OrderStatus.ORDER_CANCEL.getCode());
    }

    /**
     * 最新订单查询
     *
     * @param userId
     * @return
     */
    public String latestSuccessOrderTime(Long userId) {
        OrderInfoEntity orderInfoEntity = orderInfoRepository.queryLatestSuccessOrderInfo(userId);
        return orderInfoEntity == null ? "" : DateFormatUtil.datetime2String(orderInfoEntity.getCreateDate());
    }

    /**
     * 根据用户的Id,查询出最新的时间
     * 
     * @param userId
     * @return
     */
    public String latestSuccessTime(Long userId) {
        Date orderCreateDate = null;
        Date repayCreateDate = null;

        OrderInfoEntity orderInfo = orderInfoRepository.queryLatestSuccessOrderInfo(userId);
        if (null != orderInfo) {
            orderCreateDate = orderInfo.getCreateDate();
        }

        RepayFlow flow = flowMapper.queryLatestSuccessOrderInfo(userId);
        if (null != flow) {
            repayCreateDate = flow.getCreateDate();
        }

        return DateFormatUtil.datetime2String(getMaxDate(orderCreateDate, repayCreateDate));
    }

    /**
     * 获取两个时间的大小
     * 
     * @param date1
     * @param date2
     * @return
     */
    public Date getMaxDate(Date date1, Date date2) {

        if (null != date1 && null == date2) {
            return date1;
        }

        if (null == date1 && null != date2) {
            return date2;
        }

        if (null != date1 && null != date2) {

            if (date1.before(date2)) {
                return date2;
            } else {
                return date1;
            }
        }

        return null;
    }

    public List<OrderInfoEntity> selectByMainOrderId(String mainOrderId) throws BusinessException {
        List<OrderInfoEntity> list = orderInfoRepository.selectByMainOrderId(mainOrderId);
        return list;
    }

    /**
     * 查询待发货订单的信息，切订单的预发货状态为null
     */
    public List<OrderInfoEntity> toBeDeliver() {
        return orderInfoRepository.toBeDeliver();
    }

    /**
     * 更新订单的状态为D03待收货，更新predelivery为Y
     * 
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusAndPreDelivery(OrderInfoEntity entity) {
        orderInfoRepository.updateOrderStatusAndPreDelivery(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(OrderInfoEntity entity) {
        orderInfoRepository.updateOrderStatus(entity);
    }

    /**
     * 批量把待发货的订单的状态修改为待收货，切PreDelivery为N(未发货)
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusAndPreDelivery() {
        // 获取数据库中所有的待发货状态的订单
        List<OrderInfoEntity> orderList = toBeDeliver();
        List<String> orderIdList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orderList)) {
            for (OrderInfoEntity order : orderList) {
                // 修改订单状态和是否发货
                order.setPreDelivery(PreDeliveryType.PRE_DELIVERY_N.getCode());
                order.setStatus(OrderStatus.ORDER_SEND.getCode());
                order.setUpdateDate(new Date());
                updateOrderStatusAndPreDelivery(order);
                orderIdList.add(order.getOrderId());
            }
            updateJdGoodsSaleVolume(orderIdList);
        }
    }

    /**
     * 更新销量
     * 
     * @param orderIdList
     */

    public void updateJdGoodsSaleVolume(List<String> orderIdList) {
        // 更新销量
        List<OrderDetailInfoEntity> orderDetailInfoEntityList = new ArrayList<>();
        try {
            orderDetailInfoEntityList = orderDetailInfoRepository
                    .queryOrderDetailListByOrderList(orderIdList);
        } catch (BusinessException e) {
            LOGGER.error("orderDetailInfoRepository.queryOrderDetailListByOrderList error...");
        }
        if (!CollectionUtils.isEmpty(orderDetailInfoEntityList)) {
            for (OrderDetailInfoEntity orderDetailInfoEntity : orderDetailInfoEntityList) {
                JdGoodSalesVolume jdGoodSalesVolume = new JdGoodSalesVolume();
                long goodsId = orderDetailInfoEntity.getGoodsId();
                int saleNum = orderDetailInfoEntity.getGoodsNum().intValue();
                Date date = new Date();
                jdGoodSalesVolume.setGoodsId(goodsId);
                jdGoodSalesVolume.setSalesNum(saleNum);
                jdGoodSalesVolume.setCreateDate(date);
                jdGoodSalesVolume.setUpdateDate(date);
                try {
                    int insertValue = jdGoodSalesVolumeMapper.insertSelective(jdGoodSalesVolume);
                } catch (Exception e) {
                    LOGGER.error("updateJdGoodsSaleVolume goodsId {} saleNum {} ", goodsId, saleNum, e);
                }
            }
        }
    }

    /**
     * 下单时，要验证商品的可配送区域
     * 
     * @throws BusinessException
     */
    public Map<String, Object> validateGoodsUnSupportProvince(String requestId, Long addreesId,
            List<PurchaseRequestDto> purchaseList) throws BusinessException {
        // 验证提交信息中，是否存在不知配送区域的商品
        Map<String, Object> results = Maps.newHashMap();
        for (PurchaseRequestDto purchase : purchaseList) {
            GoodsInfoEntity goods = goodsDao.select(purchase.getGoodsId());
            Map<String, Object> resultMaps = new HashMap<>();
            if (null != goods) {
                if (StringUtils.equals(goods.getSource(), SourceType.JD.getCode())) {
                    AddressInfoEntity address = addressInfoDao.select(addreesId);
                    Region region = new Region();
                    if (null != address) {
                        region.setProvinceId(Integer.parseInt(address.getProvinceCode()));
                        region.setCityId(Integer.parseInt(address.getCityCode()));
                        region.setCountyId(Integer.parseInt(address.getDistrictCode()));
                        region.setTownId(StringUtils.isEmpty(address.getTownsCode()) ? 0 : Integer
                                .parseInt(address.getTownsCode()));
                    }
                    String jdgoodsStock = jdGoodsInfoService.getStockBySkuNum(goods.getExternalId(), region,
                            purchase.getBuyNum());
                    if ("无货".equals(jdgoodsStock)) {
                        resultMaps.put("unSupportProvince", true);
                        resultMaps.put("message", "抱歉，暂不支持该地区发货！");
                    }
                } else {
                    // 校验非京东商品的不可发送区域
                    resultMaps = validateGoodsUnSupportProvince(requestId, addreesId, purchase.getGoodsId());
                }
            }
            if (!resultMaps.isEmpty() && (Boolean) resultMaps.get("unSupportProvince")) {
                results.putAll(resultMaps);
                throw new BusinessException("抱歉，暂不支持该地区发货");
            }
        }
        return results;
    }
    
    /**
     * 验证商品是否支持配送区域
     * @param addreesId
     * @param purchaseList
     * @return
     * @throws BusinessException
     */
    public List<PurchaseRequestDto> validateGoodsUnSupportProvince(Long addreesId,List<PurchaseRequestDto> purchaseList) throws BusinessException {
    	for (PurchaseRequestDto purchase : purchaseList) {
            GoodsInfoEntity goods = goodsDao.select(purchase.getGoodsId());
            if (null != goods) {
                if (StringUtils.equals(goods.getSource(), SourceType.JD.getCode())) {
                    AddressInfoEntity address = addressInfoDao.select(addreesId);
                    Region region = new Region();
                    if (null != address) {
                        region.setProvinceId(Integer.parseInt(address.getProvinceCode()));
                        region.setCityId(Integer.parseInt(address.getCityCode()));
                        region.setCountyId(Integer.parseInt(address.getDistrictCode()));
                        region.setTownId(StringUtils.isEmpty(address.getTownsCode()) ? 0 : Integer
                                .parseInt(address.getTownsCode()));
                    }
                    List<Long> skus = new ArrayList<Long>();
                    skus.add(Long.parseLong(goods.getExternalId()));
                    purchase.setUnSupportProvince(productCheckAreaLimitQuery(skus,region));
                } else {
                    // 校验非京东商品的不可发送区域
                	Map<String, Object> resultMaps = validateGoodsUnSupportProvince("", addreesId, purchase.getGoodsId());
                	if(!resultMaps.isEmpty()){
                		purchase.setUnSupportProvince((Boolean)resultMaps.get("unSupportProvince"));
                	}
                }
            }
    	}
        return purchaseList;
    }

    /**
     * 根据订单id 和 商品Id，验证订单下，是否存在不支持配送的商品
     * 
     * @param requestId
     * @param orderId
     * @param goodsId
     * @throws BusinessException
     */
    public Map<String, Object> validateGoodsUnSupportProvince(String requestId, String orderId, Long goodsId)
            throws BusinessException {
        Map<String, Object> resultMap = Maps.newHashMap();

        OrderInfoEntity order = selectByOrderId(orderId);

        GoodsInfoEntity goods = goodsDao.select(goodsId);

        boolean bl = false;
        String message = "";
        if (null != goods) {
            if (StringUtils.isNotBlank(goods.getUnSupportProvince())
                    && goods.getUnSupportProvince().indexOf(order.getProvince()) > -1) {
                bl = true;
                LOG.info(requestId, "订单中商品不支持配送区域", "订单号为" + orderId + "中，商品名称为" + goods.getGoodsName()
                        + "不支持配送");
                message = "抱歉，暂不支持该地区发货！";
                // 订单的状态置为无效
                updateProperties(order.getId());
            }
        }
        resultMap.put("unSupportProvince", bl);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 根据地址id 和 商品Id，验证订单下，是否存在不支持配送的商品
     * 
     * @param requestId
     * @param goodsId
     * @throws BusinessException
     */
    public Map<String, Object> validateGoodsUnSupportProvince(String requestId, Long addressId, Long goodsId)
            throws BusinessException {
        Map<String, Object> resultMap = Maps.newHashMap();

        // 校验地址
        AddressInfoEntity address = addressInfoDao.select(addressId);
        if (null == address) {
            LOG.info(requestId, "生成订单前校验,校验地址,该用户地址信息不存在", addressId.toString());
            throw new BusinessException("该用户地址信息不存在");
        }

        GoodsInfoEntity goods = goodsDao.select(goodsId);

        boolean bl = false;
        String message = "";
        if (null != goods) {
            if (StringUtils.isNotBlank(goods.getUnSupportProvince())
                    && goods.getUnSupportProvince().indexOf(address.getProvince()) > -1) {
                bl = true;
                LOG.info(requestId, "订单中商品不支持配送区域", "抱歉，暂不支持该地区发货！");
                message = "抱歉，暂不支持该地区发货！";
            }
        }
        resultMap.put("unSupportProvince", bl);
        resultMap.put("message", message);
        return resultMap;
    }

    public void updateProperties(Long id) {
        OrderInfoEntity entity = new OrderInfoEntity();
        entity.setId(id);
        entity.setStatus(OrderStatus.ORDER_CANCEL.getCode());
        orderInfoRepository.update(entity);
    }

    /**
     *
     * @param orderStatus
     * @param dateBegin
     * @param dateEnd
     * @return
     */
    public Integer selectOrderCountByStatus(String orderStatus, String dateBegin, String dateEnd) {
        return orderInfoRepository.selectOrderCountByStatus(orderStatus, dateBegin, dateEnd);
    }

    public Integer selectSumAmt(String dateBegin, String dateEnd) {
        return orderInfoRepository.selectSumAmt(dateBegin, dateEnd);
    }

    public Integer selectAliAmt(String dateBegin, String dateEnd) {
        return orderInfoRepository.selectAliAmt(dateBegin, dateEnd);
    }
    public Integer selectCreAmt(String dateBegin, String dateEnd) {
        return orderInfoRepository.selectCreAmt(dateBegin, dateEnd);
    }

    /**
     * 发邮件专用
     * 
     * @param dateBegin
     * @param dateEnd
     * @return
     */
    public List<OrderSubInfoEntity> queryOrderSubInfoByTime(String dateBegin, String dateEnd) {
        HashMap<String, String> param = Maps.newHashMap();
        param.put("dateBegin", dateBegin);
        param.put("dateEnd", dateEnd);
        return orderSubInfoRepository.queryOrderSubInfoByTime(param);
    }

    /**
     * 根据订单号，获取订单信息
     * 
     * @param orderId
     * @return
     */
    public OrderInfoEntity getOrderInfoEntityByOrderId(String orderId) {
        return orderInfoRepository.selectByOrderId(orderId);
    }

    /**
     * 释放预占库存
     * 
     * @throws BusinessException
     */
    public void freedJdStock() throws BusinessException {
        /**
         * 1.首先查询 失效和删除的京东订单 2.循环，拿到订单信息中的京东订单的id 3.调用释放占用库存的接口，传入京东订单的id
         * 4.释放完库存后，修改订单的pre_stock_status状态为3
         */
        List<OrderInfoEntity> orderList = orderInfoRepository.getInvalidAndDeleteJdOrder();
        if (!CollectionUtils.isEmpty(orderList)) {
            for (OrderInfoEntity order : orderList) {
                JdApiResponse<Boolean> jd = jdOrderApiClient.orderCancelorder(Long.valueOf(order
                        .getExtOrderId()));
                if (!jd.isSuccess() || !jd.getResult()) {
                    throw new BusinessException("取消未确认的订单失败!");
                }
            }
        }

        /**
         * 修改订单占用库存字段为取消占用，更新时间
         */
        Map<String, Object> params = Maps.newHashMap();
        params.put("preStockStatus", PreStockStatus.CANCLE_PRE_STOCK.getCode());
        params.put("updateTime", new Date());
        for (OrderInfoEntity order : orderList) {
            params.put("orderId", order.getOrderId());
            orderInfoRepository.updatePreStockStatusByOrderId(params);
        }
    }

    /**
     * 查询预占库存 代发货的订单
     * 
     * @return
     */
    public List<OrderInfoEntity> getOrderByOrderStatusAndPreStatus() {
        return orderInfoRepository.getOrderByOrderStatusAndPreStatus();
    }

    /**
     * 京东拆单消息处理
     * 
     * @param orderInfoEntity
     */
    public void jdSplitOrderMessageHandle(JSONObject jsonObject, OrderInfoEntity orderInfoEntity)
            throws BusinessException {
        String jdOrderIdp = orderInfoEntity.getExtOrderId();
        long jdOrderId = Long.valueOf(jdOrderIdp);
        Object pOrderV = jsonObject.get("pOrder");
        LOGGER.info("jdOrderId {} jdSplitOrderMessageHandle  ", jdOrderId);

        // 确认预占库存 改变订单状态
        Map<String, Object> params = Maps.newHashMap();
        params.put("preStockStatus", PreStockStatus.SURE_STOCK.getCode());
        params.put("updateTime", new Date());
        params.put("extOrderId", jdOrderId);
        params.put("orderId", orderInfoEntity.getOrderId());
        orderInfoRepository.updatePreStockStatusByOrderId(params);

        if (pOrderV instanceof Number) {

            // 订阅物流信息
            try {
                HashMap<String, String> hashMap = new HashMap();
                hashMap.put("logisticsName", "jd");
                hashMap.put("logisticsNo", jdOrderIdp);
                hashMap.put("orderId", orderInfoEntity.getOrderId());
                updateLogisticsInfoAndOrderInfoByOrderId(hashMap);
            } catch (Exception e) {
                return;
            }

        } else {
            // 拆单
            orderInfoEntity.setExtParentId("-1");
            orderInfoRepository.update(orderInfoEntity);
            String merchantCode = orderInfoEntity.getMerchantCode();
            String deviceType = orderInfoEntity.getDeviceType();
            MerchantInfoEntity merchantInfoEntity = merchantInforService.queryByMerchantCode(merchantCode);
            JSONObject pOrderJsonObject = (JSONObject) pOrderV;
            // 父订单状态
            pOrderJsonObject.getIntValue("type");
            pOrderJsonObject.getIntValue("submitState");
            pOrderJsonObject.getIntValue("orderState");
            JSONArray cOrderArray = jsonObject.getJSONArray("cOrder");

            // 拆单 插入子订单
            for (int i = 0; i < cOrderArray.size(); i++) {
                JSONObject cOrderJsonObject = cOrderArray.getJSONObject(i);
                if (cOrderJsonObject.getLongValue("pOrder") != jdOrderId) {
                    LOGGER.info("cOrderJsonObject.getLongValue(\"pOrder\") {}, jdOrderId",
                            cOrderJsonObject.getLongValue("pOrder"), jdOrderId);
                }
                long cOrderId = cOrderJsonObject.getLongValue("jdOrderId");// 京东子订单ID
                OrderInfoEntity cOrderInfoEntity = orderInfoRepository.getOrderInfoByExtOrderId(String
                        .valueOf(cOrderId));
                if (cOrderInfoEntity != null) {
                    continue;
                }
                JSONArray cOrderSkuList = cOrderJsonObject.getJSONArray("sku");
                BigDecimal jdPrice = BigDecimal.ZERO;// 订单金额
                Integer sumNum = 0;
                for (int j = 0; j < cOrderSkuList.size(); j++) {
                    BigDecimal price = cOrderSkuList.getJSONObject(j).getBigDecimal("price");
                    int num = cOrderSkuList.getJSONObject(j).getIntValue("num");
                    jdPrice = jdPrice.add(price.multiply(new BigDecimal(num)));
                    sumNum = sumNum + num;
                }
                // 创建新的订单号
                String cOrderQh = commonService.createOrderIdNew(deviceType, merchantInfoEntity.getId());
                // 拆单创建新的订单
                OrderInfoEntity orderInfo = new OrderInfoEntity();
                orderInfo.setUserId(orderInfoEntity.getUserId());
                orderInfo.setOrderAmt(jdPrice);
                orderInfo.setSource(SourceType.JD.getCode());
                orderInfo.setExtParentId(jdOrderIdp);// 为子订单
                orderInfo.setDeviceType(deviceType);
                orderInfo.setOrderId(cOrderQh);
                orderInfo.setGoodsNum(Long.valueOf(sumNum));
                orderInfo.setPayStatus(PaymentStatus.PAYSUCCESS.getCode());
                orderInfo.setProvince(orderInfoEntity.getProvince());
                orderInfo.setCity(orderInfoEntity.getCity());
                orderInfo.setDistrict(orderInfoEntity.getDistrict());
                orderInfo.setAddress(orderInfoEntity.getAddress());
                orderInfo.setPostcode(orderInfoEntity.getPostcode());
                orderInfo.setName(orderInfoEntity.getName());
                orderInfo.setTelephone(orderInfoEntity.getTelephone());
                orderInfo.setMerchantCode(merchantCode);
                orderInfo.setExtendAcceptGoodsNum(orderInfoEntity.getExtendAcceptGoodsNum());
                orderInfo.setAddressId(orderInfoEntity.getAddressId());
                orderInfo.setPreDelivery(orderInfoEntity.getPreDelivery());
                orderInfo.setCreateDate(orderInfoEntity.getCreateDate());
                orderInfo.setUpdateDate(new Date());
                orderInfo.setMainOrderId(orderInfoEntity.getOrderId());
                orderInfo.setExtOrderId(String.valueOf(cOrderId));
                orderInfo.setStatus(OrderStatus.ORDER_SEND.getCode());
                orderInfo.setPreStockStatus(PreStockStatus.SURE_STOCK.getCode());
                Integer successStatus = orderInfoRepository.insert(orderInfo);
                if (successStatus < 1) {
                    LOGGER.info("jdOrderId {}  cOrderId {} cOrderQh {} create order error  ", jdOrderId,
                            cOrderId, cOrderQh);
                    continue;
                }
                try {
                    // 订阅物流信息
                    HashMap<String, String> hashMap = new HashMap();
                    hashMap.put("logisticsName", "jd");
                    hashMap.put("logisticsNo", String.valueOf(cOrderId));
                    hashMap.put("orderId", cOrderQh);
                    updateLogisticsInfoAndOrderInfoByOrderId(hashMap);
                } catch (Exception e) {

                }

                for (int j = 0; j < cOrderSkuList.size(); j++) {
                    long skuId = cOrderSkuList.getJSONObject(j).getLongValue("skuId");
                    GoodsInfoEntity goodsInfoEntity = goodsDao.selectGoodsByExternalId(String.valueOf(skuId));
                    if (goodsInfoEntity == null) {
                        LOGGER.info("pOrder {}, jdOrderId {} goodsInfoEntity {}",
                                cOrderJsonObject.getLongValue("pOrder"), jdOrderId, goodsInfoEntity);
                        continue;
                    }
                    long goodsId = goodsInfoEntity.getId();
                    BigDecimal price = cOrderSkuList.getJSONObject(j).getBigDecimal("price");
                    int num = cOrderSkuList.getJSONObject(j).getIntValue("num");
                    String name = cOrderSkuList.getJSONObject(j).getString("name");
                    GoodsInfoEntity goods = goodsDao.select(goodsId);
                    List<GoodsStockInfoEntity> goodsStockInfoEntityList = getGoodsStockDao
                            .loadByGoodsId(goodsId);
                    long goodsStockId = goodsStockInfoEntityList.get(0).getGoodsStockId();
                    // orderDetail插入对应记录
                    OrderDetailInfoEntity orderDetail = new OrderDetailInfoEntity();
                    orderDetail.setOrderId(cOrderQh);
                    orderDetail.setGoodsId(goodsId);
                    orderDetail.setSkuId(String.valueOf(skuId));
                    orderDetail.setSource(SourceType.JD.getCode());
                    orderDetail.setGoodsPrice(price);
                    orderDetail.setGoodsNum(Long.valueOf(num));
                    orderDetail.setMerchantCode(merchantCode);
                    orderDetail.setGoodsTitle(goods.getGoodsTitle());
                    orderDetail.setCategoryCode(goods.getCategoryCode());
                    orderDetail.setGoodsName(goods.getGoodsName());
                    orderDetail.setGoodsSellPt(goods.getGoodsSellPt());
                    orderDetail.setGoodsType(goods.getGoodsType());
                    orderDetail.setListTime(goods.getListTime());
                    orderDetail.setDelistTime(goods.getDelistTime());
                    orderDetail.setProDate(goods.getProDate());
                    orderDetail.setKeepDate(goods.getKeepDate());
                    orderDetail.setSupNo(goods.getSupNo());
                    orderDetail.setCreateDate(new Date());
                    orderDetail.setGoodsStockId(goodsStockId);
                    Integer orderDetailSuccess = orderDetailInfoRepository.insert(orderDetail);
                    if (orderDetailSuccess < 1) {
                        LOGGER.info("jdOrderId {}  cOrderId {} cOrderQh {} create order detail error  ",
                                jdOrderId, cOrderId, cOrderQh);
                        continue;
                    }
                }
            }

        }
    }

    /**
     * 根据状态获取所有京东的订单
     * 
     * @param orderStatus
     * @return
     */
    public List<OrderInfoEntity> getJdOrderByOrderStatus(String orderStatus) {
        return orderInfoRepository.getJdOrderByOrderStatus(orderStatus);
    }

    /**
     * 根据订单号，退款
     * 
     * @param orderId
     * @throws BusinessException
     */
    public void orderCashRefund(String orderId, String refundType, String userName) throws BusinessException {
        // 根据订单id，获取订单信息
        OrderInfoEntity order = orderInfoRepository.selectByOrderId(orderId);

        if (StringUtils.equals(refundType, "0")) {
            // 根据订单号id，获取cashrefund的记录
            CashRefund refund = cashRefundMapper.getCashRefundByOrderId(orderId);
            // 根据cashrefund的记录，获取refund_txn的记录
            if (null != refund) {
                List<CashRefundTxn> txnList = cashRefundTxnMapper.queryCashRefundTxnByCashRefundId(refund
                        .getId());
                for (CashRefundTxn cashRefundTxn : txnList) {
                    cashRefundTxn.setStatus("2");
                    cashRefundTxnMapper.updateByPrimaryKey(cashRefundTxn);
                }
                refund.setStatus(Integer.valueOf(CashRefundStatus.CASHREFUND_STATUS4.getCode()));
                refund.setAuditorName(userName);
                refund.setAuditorDate(new Date());
                cashRefundMapper.updateByPrimaryKeySelective(refund);
            }
            order.setStatus(OrderStatus.ORDER_TRADCLOSED.getCode());
            orderInfoRepository.updateOrderStatus(order);
        } else {
            Map<String, Object> map = Maps.newHashMap();
            map.put("orderId", orderId);
            map.put("refundType", "0");
            RefundInfoEntity refundEntity = orderRefundRepository.queryRefundInfoByOrderIdAndRefundType(map);
            if (null != refundEntity) {
                Map<String, String> refundMap = Maps.newHashMap();
                refundMap.put("orderId", orderId);
                refundMap.put("refundId", refundEntity.getId() + "");
                orderRefundService.confirmRefundByOrderId(refundMap);
            }
        }

    }
    
}
