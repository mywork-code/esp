package com.apass.esp.service.aftersale;

import java.math.BigDecimal;
import java.util.*;

import com.apass.esp.third.party.jd.entity.aftersale.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.cart.GoodsStockIdNumDto;
import com.apass.esp.domain.dto.goods.GoodsInfoInOrderDto;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.dto.refund.ServiceProcessDto;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.domain.enums.JDReturnType;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.repository.datadic.DataDicRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.repository.merchant.MerchantInforRepository;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.repository.refund.RefundDetailInfoRepository;
import com.apass.esp.repository.refund.ServiceProcessRepository;
import com.apass.esp.service.address.AddressService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.fileview.FileViewService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.third.party.jd.client.JdAfterSaleApiClient;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;

@Component
public class AfterSaleService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AfterSaleService.class);

	@Autowired
	private OrderInfoRepository orderInfoDao;

	@Autowired
	private OrderRefundRepository orderRefundDao;

	@Autowired
	private OrderDetailInfoRepository orderDetailInfoDao;

	@Autowired
	private RefundDetailInfoRepository refundDetailInfoDao;

	@Autowired
	private ServiceProcessRepository serviceProcessDao;

	@Autowired
	public OrderDetailInfoRepository orderDetailInfoRepository;

	@Autowired
	private GoodsStockInfoRepository goodsStockDao;

	@Autowired
	public OrderInfoRepository orderInfoRepository;

	@Autowired
	private DataDicRepository dataDicRepository;

	@Autowired
	private FileViewService fileViewService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private MerchantInforRepository memChantRepository;

	@Autowired
	private JdAfterSaleApiClient jdAfterSaleApiClient;

	@Autowired
	private AddressService addressService;

	@Autowired
	private GoodsService goodsService;

	@Transactional(rollbackFor = { Exception.class })
	public void returnGoods(String requestId, String userId, String orderId,
			BigDecimal returnPriceVal, String operate, String reason,
			String content, List<GoodsStockIdNumDto> returngoodsList,
			int imageNum) throws BusinessException {

		Long userIdVal = Long.valueOf(userId);

		/** 1. 订单状态校验(每个订单只允许一次售后操作) */
		OrderInfoEntity orderInfo = orderRufundValidate(requestId, userIdVal,
				orderId, null);

		/** 2. 校验订单中是否存在要退货的商品，退货数量不大于已购买的数量 */
		// 查询商品订单详情
		List<OrderDetailInfoEntity> orderDetailInfoList = orderDetailInfoDao
				.queryOrderDetailInfo(orderId);
		// 商品订单详情信息 按 商品库存id 分组
		Map<Long, OrderDetailInfoEntity> resultMap = new HashMap<Long, OrderDetailInfoEntity>();
		// 用于过滤 客户端(可能)传过来重复的商品库存id
		List<Long> goodsStockIdList = new LinkedList<Long>();

		for (int i = 0; i < orderDetailInfoList.size(); i++) {
			OrderDetailInfoEntity orderDetailInfo = new OrderDetailInfoEntity();
			orderDetailInfo = orderDetailInfoList.get(i);
			if (!resultMap.containsKey(orderDetailInfo.getGoodsStockId())) {
				resultMap.put(orderDetailInfo.getGoodsStockId(),
						orderDetailInfo);
				goodsStockIdList.add(orderDetailInfo.getGoodsStockId());
			}
		}

		// 校验退货库存id(退货商品必须在订单详情表中存在) 和 数量(退货商品数量不能大于购买的数量)
		// 并且 计算退货商品总金额
		BigDecimal refundAmt = BigDecimal.ZERO;
		for (GoodsStockIdNumDto idNum : returngoodsList) {
			if (resultMap.containsKey(idNum.getGoodsStockId())
					&& goodsStockIdList.contains(idNum.getGoodsStockId())) {
				if (resultMap.get(idNum.getGoodsStockId()).getGoodsNum()
						.intValue() < idNum.getGoodsNum()) {
					LOG.info(requestId, "商品库存id退货数量大于购买数量",
							String.valueOf(idNum.getGoodsStockId()));
					throw new BusinessException("无效的商品数量",
							BusinessErrorCode.PARAM_VALUE_ERROR);
				}

				// 计算退货商品总金额
				refundAmt = refundAmt.add(resultMap
						.get(idNum.getGoodsStockId()).getGoodsPrice()
						.multiply(BigDecimal.valueOf(idNum.getGoodsNum())));
				// list 删除 已匹配到的 商品库存id
				goodsStockIdList.remove(idNum.getGoodsStockId());
			} else {
				LOG.info(requestId, "订单详情表中无该商品库存id",
						String.valueOf(idNum.getGoodsStockId()));
				throw new BusinessException("无效的商品id",
						BusinessErrorCode.PARAM_VALUE_ERROR);
			}
		}

		/** 3. 校验 服务端计算的退货金额 与 页面传过来的是否一致 */
		if (operate.equals(YesNo.NO.getCode())
				&& refundAmt.compareTo(returnPriceVal) != 0) {
			LOG.info(requestId, "退货金额计算错误", String.valueOf(refundAmt));
			throw new BusinessException("退货金额错误",
					BusinessErrorCode.PARAM_VALUE_ERROR);
		}

		// 如果是京东商品，校验是否可售后，支持服务的类型。以及商品的返回方式
		// 存放审核拒绝的商品库存id
		List<Long> refuseStockIds = Lists.newArrayList();
		String jdReturnType = "";
		if ("jd".equals(orderInfo.getSource())) {
			AfsApply afsApply = new AfsApply();
			afsApply.setJdOrderId(Long.valueOf(orderInfo.getExtOrderId()));// 京东订单号
			// afsApply.setJdOrderId(59904143604l);//京东订单号
			afsApply.setUserId(Long.valueOf(userId));
			if (operate.equals(YesNo.NO.getCode())) {
				afsApply.setCustomerExpect(10);// 客户预期（退货(10)、换货(20)、维修(30)）
			} else {
				afsApply.setCustomerExpect(20);// 客户预期（退货(10)、换货(20)、维修(30)）
			}
			afsApply.setQuestionDesc(content);// 产品问题描述
			/** 客户信息实体 */
			AsCustomerDto asCustomerDto = new AsCustomerDto();
			asCustomerDto.setCustomerContactName(orderInfo.getName());
			asCustomerDto.setCustomerPostcode(orderInfo.getPostcode());
			asCustomerDto.setCustomerMobilePhone(orderInfo.getTelephone());
			asCustomerDto.setCustomerTel(orderInfo.getTelephone());
			afsApply.setAsCustomerDtok(asCustomerDto);

			/** 取件信息实体 */
			AsPickwareDto asPickwareDto = new AsPickwareDto();
			// 根据addressId查询地址
			AddressInfoEntity addressInfoEntity = addressService
					.queryOneAddressByAddressId(orderInfo.getAddressId());
			asPickwareDto.setPickwareType(4);
			asPickwareDto.setPickwareProvince(Integer.valueOf(addressInfoEntity
					.getProvinceCode()));
			asPickwareDto.setPickwareCity(Integer.valueOf(addressInfoEntity
					.getCityCode()));
			asPickwareDto.setPickwareCounty(Integer.valueOf(addressInfoEntity
					.getDistrictCode()));
			asPickwareDto.setPickwareVillage(StringUtils
					.isEmpty(addressInfoEntity.getTownsCode()) ? 0 : Integer
					.valueOf(addressInfoEntity.getTownsCode()));
			asPickwareDto.setPickwareAddress(addressInfoEntity.getAddress());
			afsApply.setAsPickwareDtok(asPickwareDto);

			/** 返件信息实体 */
			AsReturnwareDto asReturnwareDto = new AsReturnwareDto();
			if (operate.equals(YesNo.NO.getCode())) {
				asReturnwareDto.setReturnwareType(10);
			} else {
				asReturnwareDto.setReturnwareType(20);
			}
			asReturnwareDto
					.setReturnwareAddress(addressInfoEntity.getAddress());
			asReturnwareDto.setReturnwareProvince(Integer
					.valueOf(addressInfoEntity.getProvinceCode()));
			asReturnwareDto.setReturnwareCity(Integer.valueOf(addressInfoEntity
					.getCityCode()));
			asReturnwareDto.setReturnwareCounty(Integer
					.valueOf(addressInfoEntity.getDistrictCode()));
			asReturnwareDto.setReturnwareVillage(StringUtils
					.isEmpty(addressInfoEntity.getTownsCode()) ? 0 : Integer
					.valueOf(addressInfoEntity.getTownsCode()));
			asReturnwareDto
					.setReturnwareAddress(addressInfoEntity.getAddress());

			afsApply.setAsReturnwareDtok(asReturnwareDto);

			/** 申请单明细 */
			AsDetailDto asDetailDto = new AsDetailDto();

			// 一个订单的商品只会属于一个商户，如果订单来自京东，则订单下所有商品均来自京东
			for (GoodsStockIdNumDto goodsStockIdNumDto : returngoodsList) {
				OrderDetailInfoEntity orderDetailInfoEntity = resultMap
						.get(goodsStockIdNumDto.getGoodsStockId());
				asDetailDto.setSkuId(Long.valueOf(orderDetailInfoEntity
						.getSkuId()));
				// asDetailDto.setSkuId(4126762l);
				asDetailDto.setSkuNum(goodsStockIdNumDto.getGoodsNum());
				afsApply.setAsDetailDtok(asDetailDto);

				JdApiResponse<Integer> jdApiResponse = jdAfterSaleApiClient
						.afterSaleAvailableNumberCompQuery(
								Long.valueOf(orderInfo.getExtOrderId()),
								Long.valueOf(orderDetailInfoEntity.getSkuId()));
				LOGGER.info("skuId为{}的京东商品可退货数量：{}",
						orderDetailInfoEntity.getSkuId(),
						jdApiResponse.toString());
				if (jdApiResponse.isSuccess()) {// 支持售后
					JdApiResponse<JSONArray> jdApiResponse2 = jdAfterSaleApiClient
							.afterSaleCustomerExpectCompQuery(Long
									.valueOf(orderInfo.getExtOrderId()), Long
									.valueOf(orderDetailInfoEntity.getSkuId()));
					String jdApiResponse2Str = GsonUtils.toJson(jdApiResponse2
							.getResult());
					String type = "";
					if (operate.equals(YesNo.NO.getCode())) {
						type = "退";
					} else {
						type = "换";
					}
					if (!jdApiResponse2Str.contains(operate.equals(YesNo.NO
							.getCode()) ? "10" : "20")) {
						LOGGER.error("skuId为{}的京东商品不支持" + type + "货",
								orderDetailInfoEntity.getSkuId());
						refuseStockIds
								.add(goodsStockIdNumDto.getGoodsStockId());
						throw new BusinessException("该商品不支持售后",
								BusinessErrorCode.PARAM_VALUE_ERROR);
					}

					JdApiResponse<JSONArray> jdApiResponse3 = jdAfterSaleApiClient
							.afterSaleWareReturnJdCompQuery(Long
									.valueOf(orderInfo.getExtOrderId()), Long
									.valueOf(orderDetailInfoEntity.getSkuId()));
					String jdApiResponse3String = GsonUtils
							.toJson(jdApiResponse3.getResult());
					if (jdApiResponse3String.contains("4")) {
						asPickwareDto.setPickwareType(4);
					} else if (jdApiResponse3String.contains("40")) {
						asPickwareDto.setPickwareType(40);
					} else if (jdApiResponse3String.contains("7")) {
						asPickwareDto.setPickwareType(7);
					} else {
						LOGGER.error("skuId为{}的京东商品返回京东方式有误",
								orderDetailInfoEntity.getSkuId());
						refuseStockIds
								.add(goodsStockIdNumDto.getGoodsStockId());
						throw new BusinessException("该商品不支持售后",
								BusinessErrorCode.PARAM_VALUE_ERROR);
					}

					for (int i = 0; i < JDReturnType.values().length; i++) {
						if (asPickwareDto.getPickwareType() == JDReturnType
								.values()[i].getCode()) {
							jdReturnType = JDReturnType.values()[i]
									.getMessage();
						}
					}
					afsApply = AfsApply.fromOriginalJson(afsApply);

					// 调用京东接口：申请服务
					JdApiResponse<Integer> jdApiResponse1 = jdAfterSaleApiClient
							.afterSaleAfsApplyCreate(afsApply);
					if (!jdApiResponse1.isSuccess()) {
						throw new BusinessException("该商品不支持售后",
								BusinessErrorCode.PARAM_VALUE_ERROR);
					}
				} else {
					refuseStockIds.add(goodsStockIdNumDto.getGoodsStockId());
					throw new BusinessException("该商品不支持售后",
							BusinessErrorCode.PARAM_VALUE_ERROR);
				}
			}
		}

		/** 4. 售后数据入库操作 */
		RefundInfoEntity refundInfo = new RefundInfoEntity();
		refundInfo.setOrderId(orderInfo.getOrderId());
		refundInfo.setOrderAmt(orderInfo.getOrderAmt());
		refundInfo.setRefundAmt(refundAmt);
		refundInfo.setJdReturnType(jdReturnType);
		// Yes-1:换货 No-0:退货
		refundInfo.setRefundType(operate);
		refundInfo.setStatus(RefundStatus.REFUND_STATUS01.getCode());
		refundInfo.setRefundReason(reason);
		refundInfo.setRemark(content);

		/** 售后图片地址数据 */
		StringBuilder imageUrl = new StringBuilder();
		for (int i = 0; i < imageNum; i++) {
			if (i == 0) {
				imageUrl.append("/eshop/refund/");
				imageUrl.append(userId);
				imageUrl.append("/");
				imageUrl.append(orderId);
				imageUrl.append("/refundimage_0.jpg");
			} else {
				imageUrl.append(",/eshop/refund/");
				imageUrl.append(userId);
				imageUrl.append("/");
				imageUrl.append(orderId);
				imageUrl.append("/refundimage_");
				imageUrl.append(i);
				imageUrl.append(".jpg");
			}
		}
		refundInfo.setGoodsUrl(imageUrl.toString());

		// 保存 退货信息
		orderRefundDao.insert(refundInfo);
		if (null == refundInfo.getId()) {
			throw new BusinessException("退货信息保存失败!",
					BusinessErrorCode.ORDER_REFUNDINFO_SAVE_FAILED);
		}

		// 插入售后流程数据
		insertServiceProcessInfo(refundInfo.getId(),
				RefundStatus.REFUND_STATUS01.getCode(), "");

		// 遍历 获取订单详情ID 和 数量
		for (GoodsStockIdNumDto idNum : returngoodsList) {
			OrderDetailInfoEntity OrderDetailDto = resultMap.get(idNum
					.getGoodsStockId());
			RefundDetailInfoEntity refundDetailInfo = new RefundDetailInfoEntity();

			if (refuseStockIds.contains(idNum.getGoodsStockId())) {
				refundDetailInfo.setStatus(RefundStatus.REFUND_STATUS06
						.getCode());
			} else {
				refundDetailInfo.setStatus(RefundStatus.REFUND_STATUS01
						.getCode());
			}
			refundDetailInfo.setSource(orderInfo.getSource());
			refundDetailInfo.setOrderId(orderInfo.getOrderId());
			refundDetailInfo.setOrderDetailId(OrderDetailDto.getId());
			refundDetailInfo.setGoodsPrice(OrderDetailDto.getGoodsPrice());
			refundDetailInfo.setGoodsId(OrderDetailDto.getGoodsId());
			refundDetailInfo.setGoodsNum(Long.valueOf(idNum.getGoodsNum()));

			// 保存退货详情
			refundDetailInfoDao.insert(refundDetailInfo);
			if (null == refundDetailInfo.getId()) {
				throw new BusinessException("退货详情保存失败!",
						BusinessErrorCode.ORDER_REFUNDDETAIL_SAVE_FAILED);
			}
		}

		// 更新订单状态为 售后中
		OrderInfoEntity oiDto = new OrderInfoEntity();
		oiDto.setId(orderInfo.getId());
		oiDto.setStatus(OrderStatus.ORDER_RETURNING.getCode());
		Integer subOrderUpdateStatus = orderInfoDao.update(oiDto);
		if (subOrderUpdateStatus < 1) {
			throw new BusinessException("订单状态更新失败!", BusinessErrorCode.NO);
		}
	}

	/**
	 * 售后 订单状态校验(交易完成的订单只能做一次售后、且在交易完成15天内)
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws BusinessException
	 */
	public OrderInfoEntity orderRufundValidate(String requestId, Long userId,
			String orderId, OrderInfoEntity orderInfo) throws BusinessException {

		if (null == orderInfo) {
			orderInfo = getOrderInfo(userId, orderId);
		}
		if (null == orderInfo) {
			LOG.info(requestId, "查询订单数据为空", orderId);
			throw new BusinessException("当前订单编号不存在,不允许进行售后操作!",
					BusinessErrorCode.PARAM_IS_EMPTY);
		}

		// 已收货的订单才允许退换货
		if (!orderInfo.getStatus()
				.equals(OrderStatus.ORDER_COMPLETED.getCode())) {
			LOG.info(requestId, "校验订单状态", "当前订单状态非交易完成，不支持退换货");
			throw new BusinessException("当前订单状态不支持售后操作!",
					BusinessErrorCode.ORDER_STATUS_INVALID);
		}

		// 交易完成的订单 7天内 才可售后操作
		if (DateFormatUtil.isExpired(orderInfo.getAcceptGoodsDate(), 7)) {
			LOG.info(requestId, "订单交易完成超过7天不能进行售后操作", "");
			throw new BusinessException("当前订单状态不支持售后操作!",
					BusinessErrorCode.ORDER_STATUS_INVALID);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		List<RefundInfoEntity> refundInfoList = orderRefundDao
				.queryRefundInfoByParam(map);
		// 每个订单只允许一次售后操作
		if (null != refundInfoList && !refundInfoList.isEmpty()) {
			LOG.info(requestId, "当前订单已做过售后操作，不能再次进行售后操作", "");
			throw new BusinessException("当前订单状态不支持售后操作!",
					BusinessErrorCode.ORDER_STATUS_INVALID);
		}

		return orderInfo;
	}

	/**
	 * 插入售后流程数据
	 * 
	 * @param refundId
	 * @param nodeName
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void insertServiceProcessInfo(Long refundId, String nodeName,
			String nodeMessage) throws BusinessException {
		ServiceProcessEntity Dto = new ServiceProcessEntity();
		Dto.setRefundId(refundId);
		Dto.setNodeName(nodeName);
		Dto.setNodeMessage(nodeMessage);
		serviceProcessDao.insert(Dto);
		if (null == Dto.getId()) {
			throw new BusinessException("保存售后流程信息失败!",
					BusinessErrorCode.ORDER_REFUNDSAVE_FAILED);
		}
	}

	/**
	 * 插入完整售后流程数据
	 * 
	 * @param refundId
	 * @param nodeName
	 * @param approvalComments
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void insertServiceProcessAllInfo(Long refundId, String nodeName,
			String approvalComments) throws BusinessException {
		ServiceProcessEntity Dto = new ServiceProcessEntity();
		Dto.setRefundId(refundId);
		Dto.setNodeName(nodeName);
		Dto.setApprovalComments(approvalComments);
		serviceProcessDao.insert(Dto);
		if (null == Dto.getId()) {
			throw new BusinessException("保存售后流程信息失败!",
					BusinessErrorCode.ORDER_REFUNDSAVE_FAILED);
		}
	}

	/**
	 * 校验订单状态(售后操作时)
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws BusinessException
	 */
	public OrderInfoEntity getOrderInfo(Long userId, String orderId)
			throws BusinessException {
		return orderInfoDao.selectByOrderIdAndUserId(orderId, userId);
	}

	/**
	 * 保存物流厂商、单号信息
	 * 
	 * @param subOrderId
	 * @param logisticsName
	 * @param logisticsNo
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void submitLogisticsInfo(String requestId, String userId,
			String refundId, String orderId, String logisticsName,
			String logisticsNo) throws BusinessException {

		Long userIdVal = Long.valueOf(userId);
		Long refundIdVal = Long.valueOf(refundId);

		/** 1. 校验订单状态 */
		OrderInfoEntity orderInfo = getOrderInfo(userIdVal, orderId);
		// 售后中的订单才允许提交物流信息
		if (!orderInfo.getStatus()
				.equals(OrderStatus.ORDER_RETURNING.getCode())) {
			LOG.info(requestId, "校验订单状态,当前订单状态不能提交物流信息", orderInfo.getStatus());
			throw new BusinessException("当前订单状态不能提交物流信息!",
					BusinessErrorCode.ORDER_STATUS_INVALID);
		}

		/** 2. 查询售后信息 */
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", refundIdVal);
		param.put("orderId", orderId);
		List<RefundInfoEntity> refundInfoList = orderRefundDao
				.queryRefundInfoByParam(param);
		if (null == refundInfoList || refundInfoList.isEmpty()) {
			LOG.info(requestId, "售后信息查询", "数据为空");
			throw new BusinessException("无售后记录,无法提交物流信息!");
		}

		/** 3. 校验售后信息 */
		RefundInfoEntity refundInfo = refundInfoList.get(0);
		if (!refundInfo.getStatus().equals(
				RefundStatus.REFUND_STATUS01.getCode())) {
			LOG.info(requestId, "售后状态查询,当前售后状态不允许提交物流信息",
					refundInfo.getStatus());
			throw new BusinessException("当前售后状态不允许提交物流信息");
		}
		if (!"jd".equalsIgnoreCase(orderInfo.getSource())) {
			if (null == refundInfo.getIsAgree()
					|| !refundInfo.getIsAgree().equals("1")) {
				LOG.info(requestId, "等待客服审核,暂时不能提交物流信息", "");
				throw new BusinessException("等待客服审核,暂时不能提交物流信息!");
			}
			/** 4. 保存物流信息 */
			RefundInfoEntity riDto = new RefundInfoEntity();
			riDto.setId(refundIdVal);
			riDto.setSlogisticsName(logisticsName);
			riDto.setSlogisticsNo(logisticsNo);
			riDto.setStatus(RefundStatus.REFUND_STATUS02.getCode());

			int updateFlag = orderRefundDao.submitLogisticsInfo(riDto);
			if (updateFlag < 1) {
				LOG.info(requestId, "保存物流厂商、单号信息", "数据入库失败");
				throw new BusinessException("保存物流厂商、单号信息失败!");
			}

			/** 5. 插入售后流程数据 */
			insertServiceProcessInfo(refundIdVal,
					RefundStatus.REFUND_STATUS02.getCode(), "");

		} else {
			JdApiResponse<JSONObject> afsInfo = jdAfterSaleApiClient
					.afterSaleServiceListPageQuery(
							Long.valueOf(orderInfo.getExtOrderId()), 1, 10);
			if (!afsInfo.isSuccess() || afsInfo.getResult() == null) {
				throw new BusinessException("调用第三方接口失败!");
			}
			String result = afsInfo.getResult().getString("serviceInfoList");
			if (result == null || "".equals(result)) {
				throw new BusinessException("调用第三方接口失败!");
			}
			JSONArray array = JSONArray.parseArray(result);
			for (Object object : array) {
				JSONObject jsonObject = (JSONObject) object;
				AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
				long afsServiceId = newAfsInfo.getAfsServiceId();
				SendSku sendSku = new SendSku();
				sendSku.setAfsServiceId(Integer.parseInt(String
						.valueOf(afsServiceId)));
				sendSku.setDeliverDate(DateFormatUtil.getCurrentDate());
				sendSku.setExpressCompany(logisticsName);
				sendSku.setExpressCode(logisticsNo);
				sendSku.setFreightMoney(new BigDecimal(6));
				jdAfterSaleApiClient.afterSaleSendSkuUpdate(sendSku);
			}
			/** 4. 保存物流信息 */
			RefundInfoEntity riDto = new RefundInfoEntity();
			riDto.setId(refundIdVal);
			riDto.setSlogisticsName(logisticsName);
			riDto.setSlogisticsNo(logisticsNo);
			riDto.setStatus(RefundStatus.REFUND_STATUS02.getCode());
			int updateFlag = orderRefundDao.submitLogisticsInfo(riDto);
			if (updateFlag < 1) {
				LOG.info(requestId, "保存物流厂商、单号信息", "数据入库失败");
				throw new BusinessException("保存物流厂商、单号信息失败!");
			}

		}

	}

	/**
	 * 查看售后进度
	 * 
	 * @param subOrderId
	 * @throws BusinessException
	 */
	public Map<String, Object> viewProgress(String requestId, String userId,
			String orderId) throws BusinessException {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Long userIdVal = Long.valueOf(userId);

		ServiceProcessDto serviceProcessDto = new ServiceProcessDto();

		/**
		 * 获取订单的详情
		 */
		OrderInfoEntity orderInfo = orderInfoDao.selectByOrderIdAndUserId(
				orderId, userIdVal);

		if (null == orderInfo) {
			LOG.info(requestId, "订单数据查询", "数据为空");
			throw new BusinessException("无效的订单号!");
		}
		// 订单状态
		String orderStatus = orderInfo.getStatus();
		if (!orderStatus.equals(OrderStatus.ORDER_RETURNING.getCode())) {
			LOG.info(requestId, "当前订单状态不支持售后进度查询", orderStatus);
			throw new BusinessException("当前订单状态不支持售后进度查询!");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		List<RefundInfoEntity> refundInfoList = orderRefundDao
				.queryRefundInfoByParam(param);
		if (null == refundInfoList || refundInfoList.isEmpty()) {
			LOG.info(requestId, "售后信息查询", "数据为空");
			throw new BusinessException("无售后信息,无法查询售后进度!");
		}

		// 退换货信息
		RefundInfoEntity refundInfo = refundInfoList.get(0);
		serviceProcessDto.setSource(orderInfo.getSource());
		serviceProcessDto.setRefundId(refundInfo.getId());
		serviceProcessDto.setStatus(refundInfo.getStatus());
		serviceProcessDto.setRefundType(refundInfo.getRefundType());
		if(StringUtils.isNotEmpty(refundInfo.getJdReturnType())){
			serviceProcessDto.setJdReturnType(refundInfo.getJdReturnType().equalsIgnoreCase("上门取件")?"4":"40");
		}
		if (!"jd".equalsIgnoreCase(orderInfo.getSource())) {
			/**
			 * 根据商户的编码获取商户的详细信息，然后获取商户的退货地址
			 */
			MerchantInfoEntity merchantInfo = memChantRepository
					.queryByMerchantCode(orderInfo.getMerchantCode());

			if (null == merchantInfo) {
				LOG.info(requestId, "根据商户编码获取商户详细信息", "数据为空");
				throw new BusinessException("无效的商户编码!");
			}
			// 在商品退换货的时候，加上商户的退货地址
			serviceProcessDto.setMerchantInfoReturnAddress(merchantInfo
					.getMerchantReturnAddress());
			serviceProcessDto.setMerchantReturnName(merchantInfo
					.getMerchantReturnName());
			serviceProcessDto.setMerchantReturnPhone(merchantInfo
					.getMerchantReturnPhone());
			/** status 状态 RS01, 退货信息表字段 is_agree=1 时，可提交物流信息 */
			if (refundInfo.getStatus().equals(
					RefundStatus.REFUND_STATUS01.getCode())
					&& null != refundInfo.getIsAgree()
					&& refundInfo.getIsAgree().equals("1")) {
				serviceProcessDto.setIsAllowed("1");
			}

			/** RS02、RS03、RS04、RS05 客户端显示客户发货物流地址 */
			if (RefundStatus.showSlogistics(refundInfo.getStatus())) {
				serviceProcessDto.setSlogisticsName(refundInfo
						.getSlogisticsName());
				serviceProcessDto.setSlogisticsNo(refundInfo.getSlogisticsNo());
			}

			/** 换货(refundType=1) RS04、RS05 客户端显示商户发货物流地址 */
			if (refundInfo.getRefundType().equals("1")
					&& RefundStatus.showRlogistics(refundInfo.getStatus())) {
				serviceProcessDto.setRlogisticsName(dataDicRepository
						.queryDataNameByDataNo(refundInfo.getRlogisticsName()));
				serviceProcessDto.setRlogisticsNo(refundInfo.getRlogisticsNo());
			}
		}
		ServiceProcessEntity spDto = new ServiceProcessEntity();
		spDto.setRefundId(refundInfo.getId());
		List<ServiceProcessEntity> serviceProcessList = serviceProcessDao
				.filter(spDto);

		if (null == serviceProcessList || serviceProcessList.isEmpty()) {
			LOG.info(requestId, "售后流程详情表查询", "数据为空");
			throw new BusinessException("售后流程数据为空!");
		}

		for (ServiceProcessEntity spe : serviceProcessList) {
			switch (spe.getNodeName()) {
			case "RS01":
				serviceProcessDto.setRs01Time(spe.getCreateDate());
				break;
			case "RS02":
				serviceProcessDto.setRs02Time(spe.getCreateDate());
				break;
			case "RS03":
				serviceProcessDto.setRs03Time(spe.getCreateDate());
				break;
			case "RS04":
				serviceProcessDto.setRs04Time(spe.getCreateDate());
				break;
			case "RS05":
				serviceProcessDto.setRs05Time(spe.getCreateDate());
				break;
			case "RS06":
				serviceProcessDto.setRs06Time(spe.getCreateDate());
				break;
			default:
				LOG.info(requestId, "售后流程详情表节点名称异常", spe.getNodeName());
				throw new BusinessException("售后流程状态异常!");
			}
		}

		// 展示退换货商品数据
		RefundDetailInfoEntity RefundDetailInfoQueryDto = new RefundDetailInfoEntity();
		RefundDetailInfoQueryDto.setOrderId(orderId);
		List<RefundDetailInfoEntity> refundDetailInfoList = refundDetailInfoDao
				.filter(RefundDetailInfoQueryDto);
		if ("jd".equalsIgnoreCase(orderInfo.getSource())) {
			if (!RefundStatus.REFUND_STATUS01.getCode().equalsIgnoreCase(
					serviceProcessDto.getStatus())) {
				
				JdApiResponse<JSONObject> afsInfo = jdAfterSaleApiClient
						.afterSaleServiceListPageQuery(
								Long.valueOf(orderInfo.getExtOrderId()), 1, 10);
				if (!afsInfo.isSuccess() || afsInfo.getResult() == null) {
					throw new BusinessException("调用第三方接口失败!");
				}
				String result = afsInfo.getResult()
						.getString("serviceInfoList");
				if (result == null || "".equals(result)) {
					throw new BusinessException("调用第三方接口失败!");
				}
				
				JSONArray array = JSONArray.parseArray(result);
				if(refundInfo.getJdReturnType().equals(JDReturnType.TO_YOUR_HOME1.getMessage())){
					StringBuffer sb1 = new StringBuffer();// 京东审核通过
					StringBuffer sb2 = new StringBuffer(); // 京东审核拒绝
					StringBuffer sb = new StringBuffer(); // 最终
					for (int i = 0; i < array.size(); i++) {
						JSONObject jsonObject = (JSONObject) array.get(i);
						AfsInfo newAfsInfo = AfsInfo.fromOriginalJson(jsonObject);
						
						for (RefundDetailInfoEntity refundDetailInfo : refundDetailInfoList) {
							long skuId = newAfsInfo.getWareId();
							Long goodsId = refundDetailInfo.getGoodsId();
							GoodsInfoEntity goodsInfoEntity = goodsService
									.selectByGoodsId(goodsId);
							
							if (goodsInfoEntity == null) {
								continue;
							}
							
							if (skuId == Long.valueOf(goodsInfoEntity
									.getExternalId())) {
								OrderDetailInfoEntity orderDetailInfo = orderDetailInfoRepository
										.select(refundDetailInfo.getOrderDetailId());
								sb.append("审核结果：");
								String goodsNameSt = orderDetailInfo.getGoodsName();
								if (newAfsInfo.getAfsServiceStep() != 20
										&& newAfsInfo.getAfsServiceStep() != 60
										&& newAfsInfo.getAfsServiceStep() != 10) {
									sb1.append((goodsNameSt.length() < 6 ? goodsNameSt
											: goodsNameSt.substring(0, 6) + "...")
											+ ",¥"
											+ orderDetailInfo.getGoodsPrice()
											.toString());
								} else {
									sb2.append((goodsNameSt.length() < 6 ? goodsNameSt
											: goodsNameSt.substring(0, 6) + "...")
											+ ",¥"
											+ orderDetailInfo.getGoodsPrice()
											.toString());
								}
							}
						}
					}
					
					if (StringUtils.isNotBlank(sb1.toString())) {
						sb1.append("，以上商品达成售后申请，趣花采用京东配送将在1-3天内上门取件， 请准备好商品交给配送人员。如实际收货发现商品与描述不符，商品将原物返回。");
					}
					if (StringUtils.isNotBlank(sb2.toString())) {
						sb2.append(" 以上商品未通过售后服务,");
					}
					serviceProcessDto.setMemo(sb.append(sb2).append(sb1)
							.append("如有疑问请联系客服：021-51349369，感谢您的支持。").toString());
					
				}else{
					//TODO 京东商品客户发货
				}
			}
		}
		List<GoodsInfoInOrderDto> goodsListInEachOrder = new ArrayList<GoodsInfoInOrderDto>();
		// 退换货商品总数目
		int goodsSum = 0;
		for (RefundDetailInfoEntity refundDetailInfo : refundDetailInfoList) {
			goodsSum += refundDetailInfo.getGoodsNum();

			OrderDetailInfoEntity orderDetailInfo = orderDetailInfoRepository
					.select(refundDetailInfo.getOrderDetailId());
			GoodsInfoInOrderDto goodsInfo = new GoodsInfoInOrderDto();
			goodsInfo.setGoodsId(orderDetailInfo.getGoodsId());
			goodsInfo.setGoodsStockId(orderDetailInfo.getGoodsStockId());
			goodsInfo.setBuyNum(refundDetailInfo.getGoodsNum());
			GoodsStockInfoEntity goodsStock = goodsStockDao
					.select(orderDetailInfo.getGoodsStockId());
			goodsInfo.setGoodsLogoUrl(goodsStock.getStockLogo());
			goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(goodsStock
					.getStockLogo()));
			goodsInfo.setGoodsName(orderDetailInfo.getGoodsName());
			goodsInfo.setGoodsPrice(orderDetailInfo.getGoodsPrice());
			goodsInfo.setGoodsTitle(orderDetailInfo.getGoodsTitle());
			goodsInfo.setGoodsSkuAttr(goodsStock.getGoodsSkuAttr());

			goodsListInEachOrder.add(goodsInfo);
		}

		OrderDetailInfoDto orderDetailInfoDto = new OrderDetailInfoDto();
		orderDetailInfoDto.setOrderId(orderInfo.getOrderId());
		orderDetailInfoDto.setOrderAmt(refundInfo.getRefundAmt());
		orderDetailInfoDto.setGoodsNumSum(goodsSum);
		orderDetailInfoDto.setStatus(orderInfo.getStatus());
		orderDetailInfoDto.setOrderDetailInfoList(goodsListInEachOrder);
		orderDetailInfoDto.setOrderCreateDate(orderInfo.getCreateDate());
		orderDetailInfoDto.setSource(orderInfo.getSource());

		resultMap.put("serviceProcessDto", serviceProcessDto);
		resultMap.put("refundOrderInfo", orderDetailInfoDto);

		return resultMap;

	}

	/**
	 * 退换货时上传商品照片
	 * 
	 * @param userId
	 * @param returnImage
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void uploadReturnImage(String requestId, String userId,
			String orderId, String returnImage) throws BusinessException {
		String[] imagelist = returnImage.split(",");
		if (imagelist.length > 3) {
			LOG.info(requestId, "计算图片数量", "图片数量超过3张");
			throw new BusinessException("最多只能上传3张照片",
					BusinessErrorCode.UPLOAD_PICTURE_FAILED);
		}
		String dir = userId + "/" + orderId;
		for (int i = 0; i < imagelist.length; i++) {
			String type = "refundimage" + "_" + i;

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dir", dir);
			paramMap.put("imgFile", imagelist[i]);
			paramMap.put("imgType", type);

			// 保存特征图片到服务器
			fileViewService.uploadReturnImage(requestId, paramMap);
		}

	}

	/**
	 * 退换货时上传商品照片
	 * 
	 * @param userId
	 * @param orderId
	 * @param returnImage
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void uploadReturnImageAsend(String requestId, String userId,
			String orderId, List<String> returnImage) throws BusinessException {

		String dir = userId + "/" + orderId;

		for (int i = 0; i < returnImage.size(); i++) {

			String type = "refundimage" + "_" + i;

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dir", dir);
			paramMap.put("imgFile", returnImage.get(i));
			paramMap.put("imgType", type);

			// 保存特征图片到服务器
			fileViewService.uploadReturnImage(requestId, paramMap);
		}
	}

	/**
	 * 用户申请换货，商家重新发货的物流单号显示已签收，标记该售后信息为售后完成
	 * 
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateRefundToCompleted(String rlogisticsId)
			throws BusinessException {
		List<RefundInfoEntity> refundList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rlogisticsId", rlogisticsId);

		refundList = orderRefundDao.queryRefundInfoByParam(map);

		if (null != refundList && !refundList.isEmpty()) {

			RefundInfoEntity refundInfo = refundList.get(0);
			if (refundInfo.getStatus().equals(
					RefundStatus.REFUND_STATUS04.getCode())) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("orderId", refundInfo.getOrderId());
				paramMap.put("status", RefundStatus.REFUND_STATUS04.getCode());

				// 换货 商家重新发货物流显示已签收，设置退货信息表 售后完成时间 为当前时间，不改变售后流程状态
				int updateFlag = orderRefundDao
						.updateRefundStatusAndCtimeByOrderId(paramMap);
				if (updateFlag != 1) {
					LOGGER.error("商家再发货物流显示已签收，更新售后数据状态为售后完成失败,refundId[{}]",
							refundInfo.getId());
				}

			}
		}
	}

}
