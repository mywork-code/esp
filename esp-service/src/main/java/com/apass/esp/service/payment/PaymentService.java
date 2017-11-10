package com.apass.esp.service.payment;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.payment.PayRequestDto;
import com.apass.esp.domain.dto.payment.PayResponseDto;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.CashRefundTxn;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockLogEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.payment.PayInfoEntity;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.domain.enums.CashRefundTxnStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.PayFailCode;
import com.apass.esp.domain.enums.PaymentStatus;
import com.apass.esp.domain.enums.PaymentType;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.domain.kvattr.DownPayRatio;
import com.apass.esp.domain.utils.ConstantsUtils;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.mapper.ProMyCouponMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.repository.goods.GoodsStockLogRepository;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerCreditInfo;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.offer.ProGroupGoodsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.esp.service.refund.CashRefundTxnService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.monitor.annotation.Monitor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单支付
 * 
 * @description
 *
 * @author liuming
 * @version $Id: PaymentService.java, v 0.1 2017年3月3日 下午3:04:04 liuming Exp $
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class PaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
	private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

	@Autowired
	private PaymentHttpClient paymentHttpClient;
	@Autowired
	private CommonHttpClient commonHttpClient;
	@Autowired
	private OrderInfoRepository orderDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private GoodsStockInfoRepository goodsStockDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private GoodsStockLogRepository goodsStockLogDao;
	@Autowired
	private OrderDetailInfoRepository orderDetailDao;
	@Autowired
	private CashRefundService cashRefundService;
	@Autowired
	public CashRefundTxnService cashRefundTxnService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private KvattrService kvattrService ;

	@Autowired
	private ProGroupGoodsService proGroupGoodsService;
	
	@Autowired
	private ProActivityCfgMapper proActivityCfgMapper;
	
	@Autowired
	private ProMyCouponMapper myCouponMapper;

	@Autowired
	private MyCouponManagerService myCouponManagerService;

	/**
	 * 支付[银行卡支付或信用支付]
	 * 
	 * @param userId
	 *            用户id
	 * @param orderList
	 *            订单列表
	 * @param paymentType
	 *            支付方式
	 * @throws BusinessException
	 */

	@Transactional(rollbackFor = { Exception.class, BusinessException.class })
	@Monitor(methodDesc = "支付[银行卡支付或信用支付]")
	public String defary(String requestId ,Long userId, List<String> orderList, String paymentType, String cardNo,String systemType,String downPayType) throws BusinessException {
		// 校验订单状态
		Map<String, Object> data = validateDefary(requestId,userId, orderList);
		if(!data.containsKey("totalAmt")){
			throw new BusinessException("抱歉，暂不支持该地区发货！");
		}
		BigDecimal totalAmt = (BigDecimal) data.get("totalAmt");
		
		@SuppressWarnings("unchecked")
		//订单列表
		List<OrderInfoEntity> orderInfoList = (List<OrderInfoEntity>) data.get("orderInfoList");
		//订单详情列表
		List<OrderDetailInfoEntity> orderDetailList = orderService.loadOrderDetail(orderInfoList);
		
		//校验商品上下架及库存
        for (OrderDetailInfoEntity orderDetail : orderDetailList) {
            String requestIdOrder = requestId + "_" + orderDetail.getOrderId();
            orderService.validateGoodsStock(requestIdOrder, orderDetail.getGoodsId(), orderDetail.getGoodsStockId(),orderDetail.getGoodsNum(),orderDetail.getOrderId());
        }
        //更新库存
        for (OrderInfoEntity order : orderInfoList) {
            if(!StringUtils.equals(order.getSource(), SourceType.JD.getCode())){
            	GoodsStockLogEntity stockLog = goodsStockLogDao.loadByOrderId(order.getOrderId());
                LOG.info(requestId + "_" + order.getOrderId(), "库存记录日志表:",stockLog == null ? null : GsonUtils.toJson(order));
                if (null == stockLog) {
                    //减库存
                    modifyGoodsStock(requestId, userId, order);
                    //插入库存日志
                    GoodsStockLogEntity goodStockLog = new GoodsStockLogEntity();
                    goodStockLog.setOrderId(order.getOrderId());
                    goodStockLog.setUserId(userId);
                    goodsStockLogDao.insert(goodStockLog);
                }
            }
        	
        }
        //交易描述
        String txnDesc = obtainTxnDesc(orderDetailList);
        
		// 支付
		//TODO 是否需要判断支付类型做相应的处理
		Response response = defary(userId, paymentType, totalAmt, txnDesc, cardNo,orderList,systemType,downPayType);
		if (null == response||!response.statusResult()) {
			throw new BusinessException("支付失败");
		}
		PayResponseDto rayResp = (PayResponseDto)response.getData();

		if (!PayFailCode.CG.getCode().equals(rayResp.getResultCode())) {
			// 支付失败
			throw new BusinessException(rayResp.getResultCode(), rayResp.getResultMessage());
		}
		
		// 主订单号[对应交易流水]
		String mainOrderId = rayResp.getMainOrderId();
		
		//用mainOrderId查询订单信息
		List<OrderInfoEntity> orders = orderDao.selectByMainOrderId(mainOrderId);
		
		OrderInfoEntity entity = null;
		// 为空时,表示之前没支付过  或者  存在单笔支付历史
		if(orders == null || orders.size() == 0 || orders.size() == 1){
		    // 修改订单[订单状态及主订单号]
	        for (OrderInfoEntity order : orderInfoList) {
	            entity = new OrderInfoEntity();
	            entity.setId(order.getId());
	            entity.setMainOrderId(mainOrderId);
	            entity.setPayType(paymentType);
	            orderDao.update(entity);
	        }
		} else { // 存在历史多笔订单一起支付,更新当前支付订单主id,其他更新成空
		    List<String> orderIds = new ArrayList<String>();
		    for (OrderInfoEntity order : orderInfoList) { // 获取当前支付订单号
		        orderIds.add(order.getOrderId());
		    }
		    
	        for (OrderInfoEntity o : orders) { // 更新数据库存在的重复主订单信息
	            entity = new OrderInfoEntity();
	            entity.setId(o.getId());
	            entity.setPayType(paymentType);
	            if(orderIds.contains(o.getOrderId())){
	                entity.setMainOrderId(mainOrderId);
	                orderIds.remove(o.getOrderId()); // 更新完的订单信息移除
	            }else{
	                entity.setMainOrderId("");
	            }
	            orderDao.update(entity);
	        }
	        
	        for (OrderInfoEntity order : orderInfoList) { // 存在剩余的当前支付订单,更新主订单信息
	            if(orderIds.contains(order.getOrderId())){
	                entity = new OrderInfoEntity();
	                entity.setId(order.getId());
	                entity.setMainOrderId(mainOrderId);
	                entity.setPayType(paymentType);
	                orderDao.update(order);
	            }
	        }
		}
		return rayResp.getPayPage();
	}
	/**
	 * 获取交易描述
	 * 
	 * @param orderDetailList
	 * @return
	 */
    public String obtainTxnDesc(List<OrderDetailInfoEntity> orderDetailList) {
        StringBuilder sb = new StringBuilder();
        for (OrderDetailInfoEntity orderDetail : orderDetailList) {
            sb.append(orderDetail.getGoodsName() + " ;");
        }
        String txnDesc = sb.toString();
        
        txnDesc = txnDesc.endsWith(";") ? txnDesc.substring(0, txnDesc.length() - 1) : txnDesc;
        txnDesc = txnDesc.length() < 80 ? txnDesc : txnDesc.substring(0, 75);
        return txnDesc;
    }
	
	   /**
     * 根据订单详情列表修改商品库存
     * 
     * @param userId
     * @return
     * @throws BusinessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class, BusinessException.class })
	@Monitor(methodDesc = "根据订单详情列表修改商品库存")
	private void modifyGoodsStock(String requestId, Long userId, OrderInfoEntity orderInfo) throws BusinessException {
        //获取该订单详情列表
        List<OrderDetailInfoEntity> orderDetailList = orderDetailDao.queryOrderDetailInfo(orderInfo.getOrderId() + "");
        for (OrderDetailInfoEntity orderDetail : orderDetailList) {
            // 商品购买数量
            Long buyNum = orderDetail.getGoodsNum();
            // 商品Id
            Long goodsId = orderDetail.getGoodsId();
            // 商品库存Id
            Long goodsStockId = orderDetail.getGoodsStockId();
            // 默认调用尝试次数
            Integer errorNum = 3;
            GoodsInfoEntity goodsInfo = goodsDao.select(goodsId);
            for (int i = 0; i < errorNum; i++) {
                LOG.info(requestId, "开始减少库存","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
                GoodsStockInfoEntity goodsStock = goodsStockDao.select(goodsStockId);
                if (null == goodsStock) {
                    throw new BusinessException("商品信息不存在,请联系客服!");
                }
                LOG.info(requestId, "查询出库存量", "商品库存Id:[" + goodsStockId + "]商品库存:[" + goodsStock.getStockCurrAmt()+ "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
                goodsStock.setStockAmt(goodsStock.getStockCurrAmt());
                // 减库存
                if (goodsStock.getStockCurrAmt() >= buyNum) {
                    Long stockCurrAmt = goodsStock.getStockCurrAmt() - buyNum;
                    goodsStock.setStockCurrAmt(stockCurrAmt);
                    Integer successFlag = goodsStockDao.updateCurrAmtAndTotalAmount(goodsStock);
                    LOG.info(requestId, "更新库存记录", "商品库存Id:[" + goodsStockId + "],successFlag:[" + successFlag+ "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
                    if (successFlag == 0) {
                        if (errorNum <= 0) {
                            LOG.info(requestId, "更新库存失败尝试次数已用完","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
                            throw new BusinessException(goodsInfo.getGoodsName() + "商品库存不足");
                        }
                        errorNum--;
                        LOG.info(requestId, "本次更新库存失败尝试再次更新","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
                        continue;
                    } else if (successFlag > 1) {
                        LOG.info(requestId, "更新库存异常","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
                        throw new BusinessException(goodsInfo.getGoodsName() + "商品库存更新异常请联系客服!");
                    } else if (successFlag == 1) {
                        LOG.info(requestId, "更新库存成功","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
                        break;
                    }
                } else {
                    LOG.info(requestId, "商品库存更新失败", "商品库存不足");
                    throw new BusinessException(goodsInfo.getGoodsName() + "商品库存不足");
                }
            }
        }
    }
	
	/**
	 * 根据订单详情列表修改商品库存
	 * 
	 * @param userId
	 * @param orderDetailList
	 * @return
	 * @throws BusinessException
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class, BusinessException.class })
	private String modifyGoodsStock(String requestId,Long userId, List<OrderDetailInfoEntity> orderDetailList) throws BusinessException {
		StringBuilder sb = new StringBuilder();
		for (OrderDetailInfoEntity orderDetail : orderDetailList) {
			// 商品购买数量
			Long buyNum = orderDetail.getGoodsNum();
			// 商品Id
			Long goodsId = orderDetail.getGoodsId();
			// 商品库存Id
			Long goodsStockId = orderDetail.getGoodsStockId();
			// 默认调用尝试次数
			Integer errorNum = 3;
			GoodsInfoEntity goodsInfo = goodsDao.select(goodsId);
			sb.append(goodsInfo.getGoodsName() + ",");
			for (int i = 0; i < errorNum; i++) {
			    LOG.info(requestId, "开始减少库存","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
				GoodsStockInfoEntity goodsStock = goodsStockDao.select(goodsStockId);
				if (null == goodsStock) {
					throw new BusinessException("商品信息不存在,请联系客服!");
				}
				LOG.info(requestId, "查询出库存量", "商品库存Id:[" + goodsStockId + "]商品库存:[" + goodsStock.getStockCurrAmt()+ "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
				goodsStock.setStockAmt(goodsStock.getStockCurrAmt());
				// 减库存
				if (goodsStock.getStockCurrAmt() >= buyNum) {
					Long stockCurrAmt = goodsStock.getStockCurrAmt() - buyNum;
					goodsStock.setStockCurrAmt(stockCurrAmt);
					Integer successFlag = goodsStockDao.updateCurrAmtAndTotalAmount(goodsStock);
					LOG.info(requestId, "更新库存记录", "商品库存Id:[" + goodsStockId + "],successFlag:[" + successFlag+ "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
					if (successFlag == 0) {
						if (errorNum <= 0) {
						    LOG.info(requestId, "更新库存失败尝试次数已用完","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
							throw new BusinessException(goodsInfo.getGoodsName() + "商品库存不足");
						}
						errorNum--;
						LOG.info(requestId, "本次更新库存失败尝试再次更新","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
						continue;
					} else if (successFlag > 1) {
					    LOG.info(requestId, "更新库存异常","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
						throw new BusinessException(goodsInfo.getGoodsName() + "商品库存更新异常请联系客服!");
					} else if (successFlag == 1) {
					    LOG.info(requestId, "更新库存成功","商品库存Id:[" + goodsStockId + "],购买数量:[" + buyNum + "],errorNum:" + errorNum);
						break;
					}
				} else {
					throw new BusinessException(goodsInfo.getGoodsName() + "商品库存不足");
				}
			}
		}
		return sb.toString(); // 交易描述
	}

	/**
	 * 调用BSS 支付接口
	 * 
	 * @param userId
	 *            用户id
	 * @param paymentType
	 *            支付类型
	 * @param totalAmt
	 *            支付总金额
	 * @return
	 * @throws BusinessException
	 */
	@Monitor(methodDesc = "调用BSS 支付接口")
	private Response defary(Long userId, String paymentType, BigDecimal totalAmt, String txnDesc, String cardNo,List<String> orderList,String systemType,String downPayType) throws BusinessException {
		// 随机设置交易流水主订单号
		String mainOrder = obtainMainOrderId(orderList);
		PayRequestDto payReq = new PayRequestDto();
		payReq.setUserId(String.valueOf(userId));
		payReq.setOrderId(mainOrder);
		payReq.setTxnDesc(txnDesc);
		// 支付总金额
		payReq.setPayAmt(totalAmt);
		payReq.setPayType(paymentType);
		payReq.setSystemType(systemType);
		payReq.setDownPayType(downPayType);
		// 首付金额
		Response response = commonHttpClient.getCustomerBasicInfo("",userId);
				//paymentHttpClient.getCustomerInfo("",userId);
		if(!response.statusResult()){
			throw new BusinessException("客户信息查询失败");
		}
		CustomerBasicInfo customerBasicInfo = Response.resolveResult(response,CustomerBasicInfo.class);
		if (customerBasicInfo == null) {
			throw new BusinessException("客户信息查询失败");
		}
		Response responseCredit =  commonHttpClient.getCustomerCreditInfo("",userId);
		if(!responseCredit.statusResult()){
			throw new BusinessException("额度信息查询失败");
		}
		CustomerCreditInfo customerCreditInfo = Response.resolveResult(responseCredit,CustomerCreditInfo.class);
		if (customerBasicInfo == null) {
			throw new BusinessException("额度信息查询失败");
		}
		PayInfoEntity payInfo = calculateCreditPayRatio(customerCreditInfo.getAvailableAmount(), totalAmt);
		payReq.setDownPayAmt(payInfo.getCreditPayDownPayAmt());
		if(StringUtils.isNotEmpty(cardNo)){
			if (!cardNo.equals(customerBasicInfo.getCardNo())) {
				throw new BusinessException("支付银行卡号与绑定银行卡号不符");
			}
			payReq.setAccNo(cardNo);
		}
		return paymentHttpClient.defary(payReq);
	}
	
	/**
	 * 调用BSS 查询支付状态
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@Monitor(methodDesc = "调用BSS 查询支付状态")
	public Response queryPayStatus(String orderArray[]) throws BusinessException {
	    //数组转换成集合
	    List<String> orders = Arrays.asList(orderArray);
	    String orderId = obtainMainOrderId(orders);
		// 随机设置交易流水主订单号
		PayRequestDto payReq = new PayRequestDto();
		payReq.setOrderId(orderId);
		return paymentHttpClient.queryPayStatus(payReq);
	}

	/**
	 * 校验订单及支付
	 * 
	 * @param userId
	 *            客户号
	 * @param orderList
	 *            订单列表
	 * @return
	 * @throws BusinessException
	 */
	@Monitor(methodDesc = "校验订单及支付")
	public Map<String, Object> validateDefary(String requestId,Long userId, List<String> orderList) throws BusinessException {
		Map<String, Object> data = Maps.newHashMap();
		List<OrderInfoEntity> orderInfoList = Lists.newArrayList();
		BigDecimal totalAmt = BigDecimal.ZERO;
		// 1.订单状态校验
		for (String orderId : orderList) {
			OrderInfoEntity orderInfo = orderDao.selectByOrderIdAndUserId(orderId, userId);
			if (null == orderInfo || !OrderStatus.ORDER_NOPAY.getCode().equals(orderInfo.getStatus())) {
			    LOG.info(requestId, "订单状态已变更，暂不支持付款", orderId+"订单状态已变更，暂不支持付款");
				throw new BusinessException("抱歉，订单状态已变更，暂不支持付款");
			}
			if (!PaymentStatus.NOPAY.getCode().equals(orderInfo.getPayStatus())
					&& !PaymentStatus.PAYFAIL.getCode().equals(orderInfo.getPayStatus())) {
			    LOG.info(requestId, "订单状态已变更，暂不支持付款", orderId+"订单状态已变更，暂不支持付款");
				throw new BusinessException("抱歉，订单状态已变更，暂不支持付款");
			}
			if(null != orderInfo.getCouponId() && orderInfo.getCouponId() > 0){
				ProMyCoupon coupon = myCouponMapper.selectByPrimaryKey(orderInfo.getCouponId());
				Date now = new Date();
				if(coupon.getStartDate().getTime() > now.getTime() || coupon.getEndDate().getTime() < now.getTime()){
					throw new BusinessException("您的优惠券已过期，暂不支持付款");
				}
			}
			/**
			 * 查询订单下的所有的订单详情
			 */
			List<OrderDetailInfoEntity> orderDetailList =  orderDetailDao.queryOrderDetailInfo(orderId);
			OrderInfoEntity orderEntity = orderService.getOrderInfoEntityByOrderId(orderId);
			/**
			 * 如果是京东订单，则不要做验证，如果是其他商户订单则正常验证
			 */
			for (OrderDetailInfoEntity detail : orderDetailList) {
				//验证商品的价格是否发生改变，如何改变则将改订单设为无效
	            BigDecimal price = commonService.calculateGoodsPrice(detail.getGoodsId() ,detail.getGoodsStockId());
	            if(!(detail.getGoodsPrice().compareTo(price)==0)){
	            	LOG.info(requestId, "id为"+detail.getGoodsId()+"的商品价格发生改变，请重新购买！",detail.getGoodsStockId().toString());
	    			throw new BusinessException(orderId,"商品价格已变动，请重新下单",BusinessErrorCode.GOODS_PRICE_CHANGE_ERROR);
	            }
	            /**
	             * 验证活动是否过期
	             */
	            String activityId = detail.getProActivityId();
	            Date now = new Date();
	            if(StringUtils.isNotBlank(activityId)){
	            	ProActivityCfg cfg = proActivityCfgMapper.selectByPrimaryKey(Long.parseLong(activityId));
	            	if(null == cfg){
	            		throw new BusinessException("抱歉，您的订单内含活动已过期的商品");
	            	}
	            	if(cfg.getStartTime().getTime() > now.getTime() || cfg.getEndTime().getTime() < now.getTime() ){
	            		throw new BusinessException("抱歉，您的订单内含活动已过期的商品");
	            	}
	            }
	            
				//验证商品是否已经下架
				orderService.validateGoodsOffShelf(requestId, detail.getGoodsId());
				//如果是京东订单，则不要做以下判断
				if(!StringUtils.equals(orderEntity.getSource(), SourceType.JD.getCode())){
					//商品的购买数量
					Long goodNum = detail.getGoodsNum();
					//商品的当前库存
					Long stockCurrAmt = detail.getStockCurrAmt();
					if(goodNum > stockCurrAmt){
						LOG.info(requestId, "商品库存不足，订单不允许付款", orderId+"商品的库存不足");
						throw new BusinessException("抱歉，您的订单内含库存不足商品\n请修改商品数量");
					}
					//验证不配送区域
					Map<String,Object> resultMap = orderService.validateGoodsUnSupportProvince(requestId, orderId, detail.getGoodsId());
					//判断商品活动是否失效
					ActivityStatus validActivityFlag = proGroupGoodsService.isValidActivity(detail.getProActivityId(),detail.getGoodsId());
					resultMap.put(OrderService.PRO_ACTIVITY_FLAG,validActivityFlag);
					Boolean s = (Boolean)resultMap.get("unSupportProvince");
		    		if(s || validActivityFlag == ActivityStatus.END){
		    			 return resultMap;
		    		}
	    		}
			}
			totalAmt = totalAmt.add(orderInfo.getOrderAmt());
			orderInfoList.add(orderInfo);
		}
		if (null == orderInfoList || orderInfoList.size() == 0) {
		    LOG.info(requestId, "不存在待支付订单","orderList:"+GsonUtils.toJson(orderList));
			throw new BusinessException("该笔交易不存在待支付订单");
		}
		data.put("totalAmt", totalAmt);
		data.put("orderInfoList", orderInfoList);
		return data;
	}

	/**
	 * 初始化用户可使用的支付方式
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 * @throws BusinessException
	 */
	@Monitor(methodDesc="支付初始化")
	public Map<String, Object> initPaymentMethod(String requestId,Long userId, List<String> orderList) throws BusinessException {
		Map<String, Object> resultMap = Maps.newHashMap();
		String page = null;

		Map<String, Object> validateMap = this.validateDefary(requestId,userId, orderList);
		
		if(!validateMap.containsKey("totalAmt")){
			return validateMap;
		}
		// 待支付总金额
		BigDecimal totalAmt = (BigDecimal) validateMap.get("totalAmt");

		resultMap.put("orderAmt", totalAmt);

		/**
		 * 查询用户基本信息和绑卡信息
		 */
		Response response = commonHttpClient.getCustomerBasicInfo(requestId,userId);
		if(!response.statusResult()){
			page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;//可银行卡 可支付宝
			resultMap.put("page", page);
			return resultMap;
		}
		/**
		 * 查询用户基本信息和绑卡信息
		 */
		CustomerBasicInfo customerBasicInfo = Response.resolveResult(response,CustomerBasicInfo.class);
		if (customerBasicInfo == null) {
			page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;//可银行卡 可支付宝
			resultMap.put("page", page);
			return resultMap;
		}
		resultMap.put("bankCode", customerBasicInfo.getBankCode());
		resultMap.put("cardNo", customerBasicInfo.getCardNo());
		resultMap.put("cardBank", customerBasicInfo.getCardBank());
		resultMap.put("cardType", customerBasicInfo.getCardType());
		
		/**
		 * 查询用户的查询未结清借款&额度消费已出账笔数
		 */
		Response resp = paymentHttpClient.creditPayAuthority(userId);
		if(!resp.statusResult()){
			page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;//可银行卡 可支付宝
			resultMap.put("page", page);
			return resultMap;
		}
		Integer num = ((Double)resp.getData()).intValue();
		
		/**
		 * 用户的费率信息及CustomerId
		 */
		Response responseCredit = commonHttpClient.getCustomerCreditInfo(requestId,userId);

		if(!responseCredit.statusResult()){
			page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;//可银行卡 可支付宝
			resultMap.put("page", page);
			return resultMap;
		}
		CustomerCreditInfo customerCreditInfo = Response.resolveResult(responseCredit,CustomerCreditInfo.class);
		if (customerCreditInfo == null) {
			page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;//可银行卡 可支付宝
			resultMap.put("page", page);
			return resultMap;
		}
		if(num != null && num >= 3){
			page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;//可银行卡 可支付宝
			resultMap.put("page", page);
			LOG.logstashResponse(requestId, "初始化支付方式返回", GsonUtils.toJson(resultMap));
			return resultMap;
		}
		//1、用户可用额度为0
		BigDecimal availableAmt = customerCreditInfo.getAvailableAmount();
		if(availableAmt == null|| availableAmt.compareTo(BigDecimal.ZERO) == 0){
			if("06".equals(customerBasicInfo.getStatus())
					|| "03".equals(customerBasicInfo.getStatus())){
				page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;
			} else {
				//跳到授信页
				page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYONE;
			}
		} else {
			//2、用户可用额度>0
			// 计算额度支付金额
			PayInfoEntity payInfo = calculateCreditPayRatio(customerCreditInfo.getAvailableAmount(), totalAmt);
			
			if(payInfo.isSupportCreditPay()){
				Response overDue = paymentHttpClient.hasOverDueBill(userId);
				boolean overDue1 = false;
				if(!overDue.statusResult()){
					overDue1=true;
				}else{
					overDue1 = (boolean)overDue.getData();
				}
				if(overDue1){
					page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE;
				} else {
					Map<String, Object> param = Maps.newHashMap();
					param.put("userId", userId);
					param.put("customerId", customerCreditInfo.getCustomerId());
					param.put("amount", payInfo.getCreditPayAmt()); // 额度支付总金额
					Response response1 = paymentHttpClient.creditPaymentAuth(param);
					if(!response1.statusResult()){
						page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE; // 只支持银行卡支付
					}else{
						// 支持额度支付
						Map<String, String> map = (Map<String, String>) response1.getData();
						if ("1".equals(map.get("available"))) {
							if("1".equals(map.get("needAuthActive"))){
								//需要额度激活
								page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYONE; // 支持额度支付
							}else{
								page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTWO; // 支持额度支付
							}
						} else {
							page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE; // 只支持银行卡支付 或支付宝
						}
					}
				}
			}else{
				page = ConstantsUtils.PayMethodPageShow.CHOOSEPAYTHREE; // 只支持银行卡支付 或支付宝
			}
			
		}

		resultMap.put("page", page);
		LOG.logstashResponse(requestId, "初始化支付方式返回", GsonUtils.toJson(resultMap));
		return resultMap;
	}

	/**
	 * 判断可用支付方式,额度支付金额,银行卡支付金额,支付宝支付金额
	 * 
	 * @param creditAvailAmt
	 *            用户可用额度
	 * @param orderAmt
	 *            订单总金额
	 * @return
	 */
	public PayInfoEntity calculateCreditPayRatio(BigDecimal creditAvailAmt, BigDecimal orderAmt) {

		PayInfoEntity payInfo = new PayInfoEntity();
		boolean supportCredit = false;
		// 首付金额
		BigDecimal downPayAmt = BigDecimal.ZERO;
		// 信用支付金额
		BigDecimal creditPayAmt = BigDecimal.ZERO;
	    //银行卡支付
	    BigDecimal cardPayAmt = orderAmt; //默认订单金额

	    String paymentType = PaymentType.ALIPAY_PAYMENT.getCode();
	    
		if(creditAvailAmt != null && creditAvailAmt.compareTo(BigDecimal.ZERO) == 1){
			// 订单金额的50%
			BigDecimal halfOrderAmt = orderAmt.multiply(BigDecimal.valueOf(0.5));
			//订单金额的90%
			BigDecimal orderAmt90 = orderAmt.multiply(BigDecimal.valueOf(0.9));
			if(creditAvailAmt.compareTo(halfOrderAmt) == -1) {
				//用户可用额度<订单价格50%，不展示趣花
				cardPayAmt = orderAmt;
			} else if (creditAvailAmt.compareTo(orderAmt90) >= 0) {
				//首付比例固定10%
				supportCredit = true;
				paymentType = PaymentType.CREDIT_PAYMENT.getCode();
				downPayAmt = scale2Decimal(orderAmt.multiply(new BigDecimal(getDownRatio())));
				creditPayAmt = orderAmt.subtract(downPayAmt);
			} else if (creditAvailAmt.compareTo(halfOrderAmt) >= 0
					&& creditAvailAmt.compareTo(orderAmt90) == -1) {
				supportCredit = true;
				paymentType = PaymentType.CREDIT_PAYMENT.getCode();
				downPayAmt = scale2Decimal(orderAmt.subtract(creditAvailAmt));
				creditPayAmt = orderAmt.subtract(downPayAmt);
			}
		}
		payInfo.setCreditPayAmt(creditPayAmt);
		payInfo.setCreditPayDownPayAmt(downPayAmt);
		payInfo.setSupportCreditPay(supportCredit);
		payInfo.setPaymentType(paymentType);
		// 若使用银行卡支付需要金额
		payInfo.setCardPayAmt(cardPayAmt);
		return payInfo;
	}

  /**
   * 获取指定位以下的数字
   */
  @SuppressWarnings("unused")
  private String getDigitNum(BigDecimal creditAvailAmt , Double digit){
    double d = creditAvailAmt.doubleValue() % digit;
    return decimalFormat.format(d);
  }
  
  /**
   * 设置首付比例
   * @return
   */
  public String getDownRatio(){
	  DownPayRatio ratio = kvattrService.get(new DownPayRatio());
	  return ratio.getRatio();
  }

	/**
	 * 支付方式选择确认
	 * 
	 * @param userId
	 *            用户id
	 * @param orderList
	 *            待支付订单号列表
	 * @param paymentType
	 *            支付类型
	 * @return
	 * @throws BusinessException
	 */
	@Monitor(methodDesc="支付方式选择确认")
	public PayInfoEntity confirmPayMethod(String requestId, Long userId, List<String> orderList, String paymentType) throws BusinessException {
		PayInfoEntity payInfo = new PayInfoEntity();
		payInfo.setUserId(userId);
		// 设置支付方式
		payInfo.setPaymentType(paymentType);

		Map<String, Object> validateMap = validateDefary(requestId,userId, orderList);
		if(!validateMap.containsKey("totalAmt")){
			throw new BusinessException("抱歉，暂不支持该地区发货！");
		}
		
		BigDecimal totalAmt = (BigDecimal) validateMap.get("totalAmt");
		@SuppressWarnings("unchecked")
		List<OrderInfoEntity> orderInfoList = (List<OrderInfoEntity>) validateMap.get("orderInfoList");

		List<OrderDetailInfoEntity> orderDetailList = orderService.loadOrderDetail(orderInfoList);
		if (orderDetailList.size() <= 0) {
			throw new BusinessException("支付失败,订单数据异常");
		}
		for (OrderDetailInfoEntity orderDetail : orderDetailList) {
		    String requestIdOrder=requestId+"_"+orderDetail.getOrderId();
			orderService.validateGoodsStock(requestIdOrder,orderDetail.getGoodsId(), orderDetail.getGoodsStockId(),orderDetail.getGoodsNum(),orderDetail.getOrderId());
		}
		Response response  =  commonHttpClient.getCustomerBasicInfo(requestId, userId);
		if(!response.statusResult()){
			throw new BusinessException("客户信息查询失败");
		}
		CustomerBasicInfo customerBasicInfo = Response.resolveResult(response,CustomerBasicInfo.class);
		if (customerBasicInfo == null) {
			throw new BusinessException("客户信息查询失败");
		}

		Response responseCredit =  commonHttpClient.getCustomerCreditInfo(requestId,userId);
		if(!responseCredit.statusResult()){
			throw new BusinessException("额度信息查询失败");
		}
		CustomerCreditInfo customerCreditInfo = Response.resolveResult(responseCredit,CustomerCreditInfo.class);
		if (customerBasicInfo == null) {
			throw new BusinessException("额度信息查询失败");
		}
		// 设置不同支付方式支付金额
		payInfo = calculateCreditPayRatio(customerCreditInfo.getAvailableAmount(), totalAmt);
		payInfo.setCardPayAmt(totalAmt);
		payInfo.setBankCode(customerBasicInfo.getBankCode());
		payInfo.setCardNo(customerBasicInfo.getCardNo());
		payInfo.setCardType(customerBasicInfo.getCardType());
		payInfo.setCardBank(customerBasicInfo.getCardBank());
		return payInfo;
	}

	/**
	 * 支付成功||失败回调处理
	 * 
	 * @param mainOrderId
	 *            主订单号
	 * @param status
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class, BusinessException.class })
    public void callback(String requestId ,String mainOrderId, String status) {
		LOGGER.info("------------------------------PaymentService callback already come in------------------------");
        OrderInfoEntity param = new OrderInfoEntity();
        param.setMainOrderId(mainOrderId);
        List<OrderInfoEntity> payingOrders = orderDao.filter(param);
        if (null == payingOrders || payingOrders.isEmpty()) {
            LOGGER.info("callback_{}_payingOrders:{}", mainOrderId, payingOrders);
            return ;
        }
        LOG.info(requestId, "正在支付中的订单,payingOrders:", GsonUtils.toJson(payingOrders));
        LOGGER.info("-------------status{}--------------",YesNo.isNo(status));
        //付款失败,商品库存回滚
        if (YesNo.isNo(status)) {
            GoodsStockLogEntity stockLog = goodsStockLogDao.loadByOrderId(mainOrderId);
            LOGGER.info("callback_{}stockLog:{}", mainOrderId, stockLog);
            if (null != stockLog) {
                LOG.info(requestId, "正在支付中的订单,stockLog:", stockLog.getOrderId());
                try {
                    List<OrderDetailInfoEntity> orderDetails = orderService.loadOrderDetail(payingOrders);
                    for (OrderDetailInfoEntity orderDetail : orderDetails) {
                        try {
													if(!SourceType.JD.getCode().equals(orderDetail.getSource())){
														orderService.modifyGoodsQuantity(orderDetail.getGoodsId(), orderDetail.getGoodsStockId(),
																orderDetail.getGoodsNum(), 1, OrderService.errorNo);
													}
                        } catch (Exception e) {
                            LOGGER.error("callback_{}_goodsStockId{} updateGoodsStock fail:{}", mainOrderId,
                                orderDetail.getGoodsStockId(), e);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.info("callback_{} updateGoodsStock fail:{}", mainOrderId, e);
                }
            }

        }
        //删除库存记录
        for (OrderInfoEntity order : payingOrders) {
        	LOGGER.info("No matter whether you successd or not,delete logs");
        	goodsStockLogDao.deleteByOrderId(order.getOrderId());
		}
	}
	
	/**
	 * 确认离开支付页  未支付尝试回滚库存
	 * 
	 * @param requestId
	 * @param orders
	 * @throws BusinessException
	 */
	@Monitor(methodDesc="确认离开支付页  未支付尝试回滚库存")
    public void leavePayRollStock(String requestId , List<String> orders) throws BusinessException {
        PayRequestDto req = new PayRequestDto();
        
        req.setOrderId(obtainMainOrderId(orders));
        LOG.info(requestId, "主订单号:", req.getOrderId());
        //支付状态
		String payRealStatus="";
		Response res = paymentHttpClient.gateWayTransStatusQuery(requestId,req);
		if(!res.statusResult()){
			payRealStatus="01";
		}else{
			payRealStatus =(String)res.getData();
		}
        //00:支付成功 非00:支付失败
        if (!"00".equals(payRealStatus)) {
            for (String orderId : orders) {
            	OrderInfoEntity order = orderDao.selectByOrderId(orderId);
            	if(!StringUtils.equals(order.getSource(), SourceType.JD.getCode())){
            		GoodsStockLogEntity sotckLog = goodsStockLogDao.loadByOrderId(orderId);
                    if (null==sotckLog) {
                        continue;
                    }
                    //存在回滚
                    orderService.addGoodsStock(requestId, orderId);
            	}
            }
        }
        
    }
    
    /**
     * 获取主订单号[获取订单列表中最小的一个]
     * 
     * @return
     * @throws BusinessException 
     */
	@Monitor(methodDesc="获取主订单号[获取订单列表中最小的一个]")
    public String obtainMainOrderId(List<String> orders) throws BusinessException {
        if (null == orders || orders.size() == 0) {
            throw new BusinessException("订单传入非法");
        }
		String mainOrderIdStr = orders.get(0);
		BigInteger mainOrderId = new BigInteger(mainOrderIdStr);
        for (String orderId : orders) {
            if (!StringUtils.isNumeric(orderId)) {
                throw new BusinessException("传入订单号:{}不合法", orderId);
            }
            BigInteger orderIdInt = new BigInteger(orderId);
            if (mainOrderId.compareTo(orderIdInt) > 0) {
                mainOrderId = orderIdInt;
							  mainOrderIdStr = orderId;
            }
        }
        return mainOrderIdStr;
    }

    private   BigDecimal scale2Decimal(BigDecimal origin){
			return origin.setScale(2,BigDecimal.ROUND_HALF_UP);
		}
	/**
	 * BSS退款成功 || 失败更新数据库
	 * @param oriTxnCode 
	 * @param requestId：日志标记
	 * @param orderId：订单id
	 * @param status:订单状态
	 * @throws BusinessException 
	 */
	public void refundCallback(String requestId, String orderId, String status, String oriTxnCode) throws BusinessException {
		LOGGER.info("-------------------PaymentService refundCallback-----------------");
		CashRefund cashRefund = new CashRefund();
		cashRefund.setOrderId(orderId);
		cashRefund.setUpdateDate(new Date());
		CashRefundDto cashDto = getCashRefundByOrderId(orderId);
		LOGGER.info("-------------------PaymentService refundCallback parameter status--:"+status+"--->orderId--:"+orderId+"---->oriTxnCode---:"+oriTxnCode);
		//退货成功：修改退货信息表和订单表的状态
		if(YesNo.isYes(status)){
			LOGGER.info("--------hahah come in surprise--------------");
			//修改退款信息表状态为退款成功
			cashRefund.setId(cashDto.getId());
			cashRefund.setStatus(Integer.valueOf(CashRefundStatus.CASHREFUND_STATUS4.getCode()));
			cashRefund.setStatusD(new Date());
			cashRefundService.updateRefundCashStatusByOrderid(cashRefund);
			
			//修改退款流水表状态
			updateCashRefundTxnByOrderId(oriTxnCode,CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode(),cashDto.getId());
			
			//修改订单状态为交易关闭

			OrderInfoEntity order =   orderService.selectByOrderId(orderId);

			OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
			orderInfoEntity.setOrderId(orderId);
			orderInfoEntity.setStatus(OrderStatus.ORDER_TRADCLOSED.getCode());
			Long couponId = order.getCouponId();
			if(couponId > 0){
				//订单失效时优惠券id 置为负数，比如couponId = -418
				orderInfoEntity.setCouponId(couponId * -1);
			}
			orderService.updateOrderStatus(orderInfoEntity);

			//退款成功 则返回优惠券
			myCouponManagerService.returnCoupon(order.getUserId(),couponId,orderId);
		}else{
			//退货失败：修改退款流水表状态
			updateCashRefundTxnByOrderId(oriTxnCode,CashRefundTxnStatus.CASHREFUNDTXN_STATUS3.getCode(),cashDto.getId());
		}
	}
	
	/**
	 * 根据orderId修改退款流水表
	 * @param oriTxnCode
	 * @param cashRefundId 
	 * @return
	 * @throws BusinessException
	 */
	private void updateCashRefundTxnByOrderId(String oriTxnCode,String refundTxnStatus, Long cashRefundId)
			throws BusinessException {
		List<CashRefundTxn> cashRefundTxns = cashRefundTxnService.queryCashRefundTxnByCashRefundId(cashRefundId);
		LOGGER.info("退款流水表数据：{}",GsonUtils.toJson(cashRefundTxns));
		
		if(cashRefundTxns == null){
			LOGGER.error("退款流水表数据有误，退款详情id{}",cashRefundId);
			throw new BusinessException("退款流水表数据有误");
		}else{
			for (CashRefundTxn cashReTxn : cashRefundTxns) {
				if(TxnTypeCode.SF_CODE.getCode().equals(cashReTxn.getTypeCode()) 
						|| TxnTypeCode.KQEZF_CODE.getCode().equals(cashReTxn.getTypeCode())
						|| TxnTypeCode.ALIPAY_CODE.getCode().equals(cashReTxn.getTypeCode())
						|| TxnTypeCode.ALIPAY_SF_CODE.getCode().equals(cashReTxn.getTypeCode())){
					cashReTxn.setOriTxnCode(oriTxnCode);
					cashReTxn.setStatus(refundTxnStatus);
					cashReTxn.setUpdateDate(new Date());
					cashRefundTxnService.updateStatusByCashRefundId(cashReTxn);
				}
			}
		}
	}
	/**
	 * 根据orderId查询退款详情
	 * @param orderId
	 * @return
	 * @throws BusinessException
	 */
	private CashRefundDto getCashRefundByOrderId(String orderId)
			throws BusinessException {
		CashRefundDto caDto = cashRefundService.getCashRefundByOrderId(orderId);
		if(caDto == null){
			LOGGER.error("退款详情表数据有误：{}",orderId);
			throw new BusinessException("退款详情表数据有误");
		}
		return caDto;
	}

}
