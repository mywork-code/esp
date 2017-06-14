package com.apass.esp.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.IdNum;
import com.apass.esp.domain.dto.cart.PurchaseRequestDto;
import com.apass.esp.domain.dto.goods.GoodsInfoInOrderDto;
import com.apass.esp.domain.dto.logistics.Trace;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.dto.payment.PayRequestDto;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.cart.CartInfoEntity;
import com.apass.esp.domain.entity.cart.GoodsInfoInCartEntity;
import com.apass.esp.domain.entity.goods.GoodsDetailInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockLogEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.domain.enums.AcceptGoodsType;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.PaymentStatus;
import com.apass.esp.domain.enums.PreDeliveryType;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.repository.address.AddressInfoRepository;
import com.apass.esp.repository.cart.CartInfoRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.repository.goods.GoodsStockLogRepository;
import com.apass.esp.repository.logistics.LogisticsHttpClient;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.order.OrderSubInfoRepository;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.service.address.AddressService;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.esp.service.bill.BillService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.logistics.LogisticsService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.google.common.collect.Lists;

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
	private PaymentHttpClient paymentHttpClient;
	@Autowired
	private ImageService imageService;
	@Autowired
	private BillService billService;
	@Autowired
	private MerchantInforService merchantInforService;

	public static final Integer errorNo = 3; // 修改库存尝试次数

	private static final String ORDERSOURCECARTFLAG = "cart";

	/**
	 * 查询订单概要信息
	 * 
	 * @param customerInfo
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
	 * @param customerInfo
	 * @return
	 */
	public List<OrderDetailInfoEntity> queryOrderDetailInfo(String requestId, String orderId) throws BusinessException {
		try {
			List<OrderDetailInfoEntity> orderDetailInfo = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
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
	 * @param merchantCode
	 * @return
	 */
	public Pagination<OrderSubInfoEntity> queryOrderSubDetailInfoByParam(Map<String, String> map, Page page)
			throws BusinessException {
		try {
			Pagination<OrderSubInfoEntity> orderDetailInfoList = orderSubInfoRepository
					.querySubOrderDetailInfoByParam(map, page);
			return orderDetailInfoList;
		} catch (Exception e) {
			LOGGER.error(" 通过商户号查询订单详细信息失败===>", e);
			throw new BusinessException(" 通过商户号查询订单详细信息失败！", e);
		}
	}
	
	/**
	 * 查询被二次拒绝的订单
	 * @throws BusinessException 
	 */
	public Pagination<OrderSubInfoEntity> queryOrderInfoRejectAgain(Page page) throws BusinessException{
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
	 * @param merchantCode
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
	 * @param merchantCode
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
			 * 首先判断一下订单的预发货的状态
			 *  1.如果为Y，则不更新此字段  2.如果为空，要把值置成N
			 */
			OrderInfoEntity entity = orderInfoRepository.selectByOrderId(orderId);
			//如果是否为预发货，字段为空，则更新字段为N,订单的状态改为D03
			if(StringUtils.isBlank(entity.getPreDelivery())){
				entity.setPreDelivery(PreDeliveryType.PRE_DELIVERY_N.getCode());
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
	 * @param userId
	 *            用户Id
	 * @param totalPayment
	 *            总金额
	 * @param addressId
	 *            地址id
	 * @param purchaseList
	 *            商品列表
	 * @throws BusinessException
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class, BusinessException.class })
	public List<String> confirmOrder(String requestId, Long userId, BigDecimal totalPayment, Long addressId,
			List<PurchaseRequestDto> purchaseList, String sourceFlag,String deviceType) throws BusinessException {
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
		return generateOrder(requestId, userId, totalPayment, purchaseList, addressId,deviceType);
	}

	/**
	 * 修改商品数目 不可循环调用该方法否则无法回滚
	 * 
	 * @param goodsId
	 *            商品Id
	 * @param goodsStockId
	 *            商品库存Id
	 * @param goodsNum
	 *            修改数量
	 * @param additionFlag
	 *            -1 减少 1 增加
	 * @param errorNo
	 *            最大递归次数
	 * @throws BusinessException
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class, BusinessException.class })
	public void modifyGoodsQuantity(Long goodsId, Long goodsStockId, Long goodsNum, Integer additionFlag, int errorNo)
			throws BusinessException {
		LOGGER.info("进入商品库存修改.商品ID[{}],库存ID[{}],修改数量[{}],加减标记[{}].", errorNo, goodsId, goodsStockId, goodsNum,
				additionFlag);
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
					LOGGER.error("modifyGoodsQuantity is fail,successFlag return > 1,goodsStockId:{}", goodsStockId);
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
				LOGGER.error("modifyGoodsQuantity is fail,successFlag return > 1,goodsStockId:{}", goodsStockId);
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
			List<PurchaseRequestDto> purchaseList, Long addressId,String deviceType) throws BusinessException {
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
			String orderId =//commonService.createOrderId(userId);
					commonService.createOrderIdNew(deviceType,merchantInfoEntity.getId());
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

			Integer successStatus = orderInfoRepository.insert(orderInfo);
			if (successStatus < 1) {
				LOG.info(requestId, "生成订单", "订单表数据插入失败");
				throw new BusinessException("订单生成失败!",BusinessErrorCode.ORDER_NOT_EXIST);
			}
			// 插入商品级订单
			for (PurchaseRequestDto purchase : purchaseList) {
				GoodsInfoEntity goods = goodsDao.select(purchase.getGoodsId());
				if (goods.getMerchantCode().equals(merchantCode)) {
					GoodsStockInfoEntity goodsStock = goodsStockDao.select(purchase.getGoodsStockId());
					OrderDetailInfoEntity orderDetail = new OrderDetailInfoEntity();
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
						throw new BusinessException("订单生成失败!",BusinessErrorCode.ORDER_NOT_EXIST);
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
			GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(purchase.getGoodsId(),
					purchase.getGoodsStockId());
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
			GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(purchase.getGoodsId(),
					purchase.getGoodsStockId());
			String merchantCode = goodsDetail.getMerchantCode();
			if (merchantPayment.containsKey(merchantCode)) {
				BigDecimal haveSum = merchantPayment.get(merchantCode);
				haveSum = haveSum
						.add(purchase.getPrice().multiply(BigDecimal.valueOf(Long.valueOf(purchase.getBuyNum()))));
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
			GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(purchase.getGoodsId(),
					purchase.getGoodsStockId());
			//校验是否在不配送区域中
			String unSupportProvinces = goodsDetail.getUnSupportProvince();
			if(unSupportProvinces.contains(address.getProvince())){
				LOG.info(requestId, "该商品暂不支持该地域发货，将不计入结算,该商品不支持的省份有：{}", unSupportProvinces);
				throw new BusinessException("抱歉，"+goodsDetail.getGoodsName() + "商品暂不支持该地域发货，将不计入结算");
			}
			if (goodsDetail.getStockCurrAmt() < purchase.getBuyNum()) {
				LOG.info(requestId, "生成订单前校验,商品库存不足", goodsDetail.getGoodsStockId().toString());
				throw new BusinessException(goodsDetail.getGoodsName() + "商品库存不足\n请修改商品数量");
			}
			if (purchase.getBuyNum() <= 0) {
				LOG.info(requestId, "生成订单前校验,商品购买数量为0", purchase.getBuyNum().toString());
				throw new BusinessException("商品" + goodsDetail.getGoodsName() + "购买数量不能为零");
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
	 * @param goodsId
	 *            商品id
	 * @throws BusinessException
	 */
	public void validateGoodsOffShelf(String requestId, Long goodsId) throws BusinessException {
		Date now = new Date();
		GoodsInfoEntity goodsInfo = goodsDao.select(goodsId);
		if (null == goodsInfo) {
			LOG.info(requestId, "校验商品下架,根据商品id查询商品信息数据为空", goodsId.toString());
			throw new BusinessException("商品号:" + goodsId + ",不存在或商户号不存在！");
		}
		if (now.before(goodsInfo.getListTime()) || now.after(goodsInfo.getDelistTime())
				|| !GoodStatus.GOOD_UP.getCode().equals(goodsInfo.getStatus())) {
			LOG.info(requestId, "校验商品下架,商品已下架", goodsId.toString());
			throw new BusinessException(goodsInfo.getGoodsName() + "商品已下架\n请重新下单");
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
			throw new BusinessException(goodsInfo.getGoodsName() + "商品已下架\n请重新下单");
		}
	}

	/**
	 * 校验商品下架和库存不足[支付校验使用]
	 * 
	 * @param goodsId
	 *            商品Id
	 * @param goodsStockId
	 *            库存Id
	 * @param buyNum
	 *            购买数量
	 * @throws BusinessException
	 */
	public void validateGoodsStock(String requestId, Long goodsId, Long goodsStockId, Long buyNum, String orderId)
			throws BusinessException {
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
		GoodsDetailInfoEntity goodsDetail = goodsDao.loadContainGoodsAndGoodsStockAndMerchant(goodsId, goodsStockId);

		if (goodsDetail.getStockCurrAmt() < buyNum) {
			LOG.info(requestId, "支付失败您的订单商品库存不足", "");
			throw new BusinessException("支付失败您的订单商品库存不足");
		}
		if (buyNum <= 0) {
			LOG.info(requestId, "购买数量不能为零", goodsDetail.getGoodsName() + "购买数量不能为零");
			throw new BusinessException("商品" + goodsDetail.getGoodsName() + "购买数量不能为零");
		}
	}

	/**
	 * 取消订单
	 * 
	 * @param userId
	 * @param orderId
	 * @throws BusinessException
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class, BusinessException.class })
	public void cancelOrder(String requestId, Long userId, String orderId) throws BusinessException {
		OrderInfoEntity order = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
		if (null == order) {
			LOG.info(requestId, "查询订单表信息", "数据为空");
			throw new BusinessException("对不起!该订单不存在!",BusinessErrorCode.ORDER_NOT_EXIST);
		}
		if (!OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
			LOG.info(requestId, "校验订单状态", "当前订单状态不能取消该订单");
			throw new BusinessException("对不起!当前订单状态不能取消该订单",BusinessErrorCode.ORDERSTATUS_NOTALLOW_CANCEL);
		}
		dealWithInvalidOrder(requestId, orderId);
	}

	/**
	 * 处理取消订单
	 * 
	 * @param orderId
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = { Exception.class, BusinessException.class })
	public void dealWithInvalidOrder(String requestId, String orderId) throws BusinessException {

		// 更新订单状态
		LOG.info(requestId, "取消订单,更改订单状态为订单失效", orderId);
		orderInfoRepository.updateStatusByOrderId(orderId, OrderStatus.ORDER_CANCEL.getCode());
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
	public void addGoodsStock(String requestId, String orderId) throws BusinessException {
		Integer errorNum = errorNo;
		List<OrderDetailInfoEntity> orderDetailList = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
		// 加库存
		for (OrderDetailInfoEntity orderDetail : orderDetailList) {
			try {
				for (int i = 0; i < errorNum; i++) {
					GoodsStockInfoEntity goodsStock = goodsStockDao.select(orderDetail.getGoodsStockId());
					if (null == goodsStock) {
						LOG.info(requestId, "加库存,根据商品库存id查询商品库存信息,数据为空", orderDetail.getGoodsStockId().toString());
						throw new BusinessException("商品信息不存在,请联系客服!");
					}
					goodsStock.setStockAmt(goodsStock.getStockCurrAmt());
					// 加库存
					Long stockCurrAmt = goodsStock.getStockCurrAmt() + orderDetail.getGoodsNum();
					goodsStock.setStockCurrAmt(stockCurrAmt);
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

		}
	}

	/**
	 * 延迟收货
	 * 
	 * @param userId
	 * @param orderId
	 * @param returnMap
	 * @throws BusinessException
	 */
	@Transactional
	public void deleyReceiveGoods(String requestId, Long userId, String orderId) throws BusinessException {

		OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
		if (null == orderInfo) {
			LOG.info(requestId, "查询订单信息,数据为空", orderId);
			throw new BusinessException("对不起!订单信息为空",BusinessErrorCode.ORDER_NOT_EXIST);
		}
		if (!OrderStatus.ORDER_SEND.getCode().equals(orderInfo.getStatus())) {
			LOG.info(requestId, "校验订单状态,当前订单状态不能延迟收货", orderId);
			throw new BusinessException("当前订单状态不能延迟收货",BusinessErrorCode.ORDER_DELEY_RECEIVE_ERROR);
		}
		if (orderInfo.getExtendAcceptGoodsNum() >= 1) {
			LOG.info(requestId, "每笔订单只能一次延长收货", orderId);
			throw new BusinessException("每笔订单只能延长一次",BusinessErrorCode.ORDER_DELEY_RECEIVE_ERROR);
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
			throw new BusinessException("对不起!订单信息为空",BusinessErrorCode.ORDER_NOT_EXIST);
		}
		if (!OrderStatus.ORDER_SEND.getCode().equals(orderInfo.getStatus())) {
			LOG.info(requestId, "当前订单状态不能确认收货", orderId);
			throw new BusinessException("当前订单状态不能确认收货",BusinessErrorCode.ORDER_CONFIRM_ERROR);
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
	public void deleteOrderInfo(String requestId, Long userId, String orderId) throws BusinessException {
		OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
		if (null == orderInfo) {
			LOG.info(requestId, "查询订单信息,数据为空", orderId);
			throw new BusinessException("对不起!订单信息为空",BusinessErrorCode.ORDER_NOT_EXIST);
		}
		if (!OrderStatus.ORDER_CANCEL.getCode().equals(orderInfo.getStatus())
				&& !OrderStatus.ORDER_COMPLETED.getCode().equals(orderInfo.getStatus())) {
			LOG.info(requestId, "当前订单状态不能删除订单", orderId);
			throw new BusinessException("当前订单状态不能删除订单",BusinessErrorCode.ORDER_DELETE_ERROR);
		}
		orderInfoRepository.updateStatusByOrderId(orderInfo.getOrderId(), OrderStatus.ORDER_DELETED.getCode());
	}

	/**
	 * 根据 userId、订单状态(可选) 查询订单信息
	 * 
	 * @param userId
	 *            用户id
	 * @param statusStr
	 *            订单状态
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
			OrderDetailInfoDto orderDetailInfoDto = getOrderDetailInfoDto(requestId, userIdVal, order);

			returnOrders.add(orderDetailInfoDto);

			try {
				if (OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
					PayRequestDto req = new PayRequestDto();
					req.setOrderId(order.getOrderId());
					String payRealStatus ="";
					Response response = paymentHttpClient.gateWayTransStatusQuery(requestId, req);
					if(!response.statusResult()){
						payRealStatus = "1";
					}else{
						payRealStatus = (String)response.getData();
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
	
	public OrderDetailInfoDto getOrderDetailInfoDto(String requestId,String orderId) throws BusinessException{
	    
	    if(StringUtils.isBlank(orderId)){
	        throw new BusinessException("订单Id不能为空!",BusinessErrorCode.PARAM_IS_EMPTY);
	    }
	    
        OrderInfoEntity entity = orderInfoRepository.selectByOrderId(orderId);
	    
        OrderDetailInfoDto dto = getOrderDetailInfoDto(requestId, entity.getUserId(),entity);
		List<GoodsInfoInOrderDto> goodsInfoInOrderDtoList = dto.getOrderDetailInfoList();
		for (GoodsInfoInOrderDto goodsInfoInOrderDto :goodsInfoInOrderDtoList) {
			goodsInfoInOrderDto.setGoodsLogoUrlNew(imageService.getImageUrl(EncodeUtils.base64Decode(goodsInfoInOrderDto.getGoodsLogoUrl())));
		}
		return dto;
	}

    private OrderDetailInfoDto getOrderDetailInfoDto(String requestId, Long userIdVal,
                                                     OrderInfoEntity order) throws BusinessException {
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
        	if (null != goodsStock) {
        		goodsInfo.setGoodsLogoUrl(goodsStock.getStockLogo());
        		goodsInfo.setGoodsSkuAttr(goodsStock.getGoodsSkuAttr());
        	}
        	goodsInfo.setGoodsName(orderDetailInfo.getGoodsName());
        	goodsInfo.setGoodsPrice(orderDetailInfo.getGoodsPrice());
        	goodsInfo.setGoodsTitle(orderDetailInfo.getGoodsTitle());
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
        		dealWithInvalidOrder(requestId, order.getOrderId());
        		orderDetailInfoDto.setStatus(OrderStatus.ORDER_CANCEL.getCode());
        	} else {
        		orderDetailInfoDto.setRemainingTime(
        				DateFormatUtil.getDateDiff(DateFormatUtil.addDays(order.getCreateDate(), 1), new Date()));
        	}

        }

        orderDetailInfoDto.setOrderCreateDate(order.getCreateDate());
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
        //账单分期后改为删除按钮
        boolean billOverDueFlag =  billService.queryStatement(userIdVal,order.getOrderId());
        if(billOverDueFlag){
        	LOGGER.info("userId={},账单分期已逾期",userIdVal);
        	orderDetailInfoDto.setRefundAllowedFlag("0");
        } else {
        	try {
        		orderDetailInfoDto.setRefundAllowedFlag("1");
        		// 交易完成的订单是否允许售后操作校验
        		afterSaleService.orderRufundValidate(requestId, userIdVal, order.getOrderId(), order);
        	} catch (Exception e) {
        		LOG.info(requestId, "这个捕获只是为了过滤掉订单售后校验逻辑抛出的异常", "");
        		LOGGER.error(e.getMessage(), e);
        		// 这个捕获只是为了过滤掉 订单售后校验 抛出的 异常
        		orderDetailInfoDto.setRefundAllowedFlag("0");
        	}
        }
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
	public void loadInfoByOrderId(String requestId, Map<String, Object> resultMap, String orderId) throws Exception {
		OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, null);
		if (null == orderInfo) {
			LOG.info(requestId, "查询订单信息,数据为空", orderId);
			throw new BusinessException("该订单信息不存在!",BusinessErrorCode.ORDER_NOT_EXIST);
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
	public void modifyShippingAddress(String requestId, Long addressId, String orderId, Long userId)
			throws BusinessException {
		OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
		if (null == orderInfo) {
			LOG.info(requestId, "查询订单信息,数据为空", orderId);
			throw new BusinessException("该订单信息不存在!",BusinessErrorCode.ORDER_NOT_EXIST);
		}
		if (!OrderStatus.ORDER_NOPAY.getCode().equals(orderInfo.getStatus())) {
			LOG.info(requestId, "当前状态不能修改订单地址", orderId);
			throw new BusinessException("当前状态不能修改订单地址",BusinessErrorCode.ADDRESS_UPDATE_FAILED);
		}
		AddressInfoEntity address = addressInfoDao.select(addressId);
		if (null == address) {
			LOG.info(requestId, "查询地址信息,数据为空", addressId.toString());
			throw new BusinessException("当前地址信息不存在",BusinessErrorCode.ADDRESS_NOT_EXIST);
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
	public void repeatConfirmOrder(String requestId, Long userId, String orderId, Map<String, Object> resultMap)
			throws BusinessException {
		OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, userId);
		if (null == orderInfo) {
			LOG.info(requestId, "查询订单信息,数据为空", orderId);
			throw new BusinessException("该订单不存在!",BusinessErrorCode.ORDER_NOT_EXIST);
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
			//商品新地址
			goodInfo.setGoodsLogoUrlNew(imageService.getImageUrl(EncodeUtils.base64Decode(goodInfo.getGoodsLogoUrl())));
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
							throw new BusinessException("添加商品到购物车失败",BusinessErrorCode.GOODS_ADDTOCART_ERROR);
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
					throw new BusinessException("您的购物车已满，快去结算吧!",BusinessErrorCode.CART_FULL);
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
					throw new BusinessException("添加商品到购物车失败",BusinessErrorCode.GOODS_ADDTOCART_ERROR);
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
	public void modifyOrderAddress(String requestId, String orderId, String userId, AddressInfoEntity addressInfoDto)
			throws BusinessException {

		OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, addressInfoDto.getUserId());
		if (null == orderInfo) {
			LOG.info(requestId, "查询订单信息,数据为空", "");
			throw new BusinessException("该订单信息不存在!",BusinessErrorCode.ORDER_NOT_EXIST);
		}
		if (!OrderStatus.ORDER_NOPAY.getCode().equals(orderInfo.getStatus())) {
			LOG.info(requestId, "当前订单状态不能修改地址", "");
			throw new BusinessException("当前订单状态不能修改地址",BusinessErrorCode.ADDRESS_UPDATE_FAILED);
		}

		// 待付款订单不能修改省、市、区地址
		if (!orderInfo.getProvince().equals(addressInfoDto.getProvince())
				|| !orderInfo.getCity().equals(addressInfoDto.getCity())
				|| !orderInfo.getDistrict().equals(addressInfoDto.getDistrict())) {
			LOG.info(requestId, "待付款订单不能修改省、市、区地址", "");
			throw new BusinessException("待付款订单不能修改省、市、区地址",BusinessErrorCode.ADDRESS_UPDATE_FAILED);
		}
		Long addressId = orderInfo.getAddressId();
		AddressInfoEntity addressInfoEntity = addressService.queryOneAddressByAddressId(orderInfo.getAddressId());
		if (addressInfoEntity == null) {
			addressId = addressService.addAddressInfo(addressInfoDto);
		}else{
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
	public List<OrderDetailInfoEntity> loadOrderDetail(List<OrderInfoEntity> orderList) throws BusinessException {
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
	 * @param userId
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
			throw new BusinessException("对不起!该订单不存在!",BusinessErrorCode.ORDER_NOT_EXIST);
		}
		if (!OrderStatus.ORDER_NOPAY.getCode().equals(order.getStatus())) {
			throw new BusinessException("对不起,当前订单状态不合法",BusinessErrorCode.ORDER_STATUS_INVALID);
		}
		// 校验是否有库存不足或商品下架
		List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
		if (null == orderDetails || orderDetails.size() == 0) {
			throw new BusinessException("该订单信息异常",BusinessErrorCode.ORDER_DETAIL_ERROR);
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
	
	public List<OrderInfoEntity> selectByMainOrderId(String mainOrderId) throws BusinessException{
		List<OrderInfoEntity> list = orderInfoRepository.selectByMainOrderId(mainOrderId);
		return list;
	}
	
	/**
     * 查询待发货订单的信息，切订单的预发货状态为''
     */
    public List<OrderInfoEntity> toBeDeliver() {
    	return orderInfoRepository.toBeDeliver();
    }
    
    /**
     * 更新订单的状态为D03待收货，更新predelivery为Y
     * @param entity
     */
    public void updateOrderStatusAndPreDelivery(OrderInfoEntity entity){
    	orderInfoRepository.updateOrderStatusAndPreDelivery(entity);
    }
}
