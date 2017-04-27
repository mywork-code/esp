package com.apass.esp.service.aftersale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.dto.cart.GoodsStockIdNumDto;
import com.apass.esp.domain.dto.goods.GoodsInfoInOrderDto;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.dto.refund.ServiceProcessDto;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.repository.datadic.DataDicRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.repository.merchant.MerchantInforRepository;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.repository.refund.RefundDetailInfoRepository;
import com.apass.esp.repository.refund.ServiceProcessRepository;
import com.apass.esp.service.fileview.FileViewService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Component
@Transactional
public class AfterSaleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterSaleService.class);

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
    public OrderInfoRepository       orderInfoRepository;
    
    @Autowired
    private DataDicRepository dataDicRepository;

    @Autowired
    private FileViewService fileViewService;
    
    @Autowired
    private MerchantInforRepository memChantRepository;

    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public void returnGoods(String requestId, String userId, String orderId, BigDecimal returnPriceVal, String operate, String reason,
                            String content, List<GoodsStockIdNumDto> returngoodsList, int imageNum) throws BusinessException {

        Long userIdVal = Long.valueOf(userId);
        
        /** 1. 订单状态校验(每个订单只允许一次售后操作) */
        OrderInfoEntity orderInfo = orderRufundValidate(requestId, userIdVal, orderId, null);
        
        /** 2. 校验订单中是否存在要退货的商品，退货数量不大于已购买的数量 */
        // 查询商品订单详情
        List<OrderDetailInfoEntity> orderDetailInfoList = orderDetailInfoDao.queryOrderDetailInfo(orderId);
        // 商品订单详情信息 按 商品库存id 分组
        Map<Long, OrderDetailInfoEntity> resultMap = new HashMap<Long, OrderDetailInfoEntity>();
        // 用于过滤 客户端(可能)传过来重复的商品库存id
        List<Long> goodsStockIdList = new LinkedList<Long>();
        
        for (int i = 0; i < orderDetailInfoList.size(); i++) {
            OrderDetailInfoEntity orderDetailInfo = new OrderDetailInfoEntity();
            orderDetailInfo = orderDetailInfoList.get(i);
            if (!resultMap.containsKey(orderDetailInfo.getGoodsStockId())) {
                resultMap.put(orderDetailInfo.getGoodsStockId(), orderDetailInfo);
                goodsStockIdList.add(orderDetailInfo.getGoodsStockId());
            }
        }

        // 校验退货库存id(退货商品必须在订单详情表中存在) 和 数量(退货商品数量不能大于购买的数量)
        // 并且   计算退货商品总金额
        BigDecimal refundAmt = BigDecimal.ZERO;
        for (GoodsStockIdNumDto idNum : returngoodsList) {
            if (resultMap.containsKey(idNum.getGoodsStockId()) && goodsStockIdList.contains(idNum.getGoodsStockId())) {
                if (resultMap.get(idNum.getGoodsStockId()).getGoodsNum().intValue() < idNum.getGoodsNum()) {
                    LOG.info(requestId, "商品库存id退货数量大于购买数量", String.valueOf(idNum.getGoodsStockId()));
                    throw new BusinessException("无效的商品数量");
                }
                
                // 计算退货商品总金额
                refundAmt = refundAmt.add(resultMap.get(idNum.getGoodsStockId()).getGoodsPrice().multiply(BigDecimal.valueOf(idNum.getGoodsNum())));
                // list 删除 已匹配到的 商品库存id  
                goodsStockIdList.remove(idNum.getGoodsStockId());
            } else {
                LOG.info(requestId, "订单详情表中无该商品库存id", String.valueOf(idNum.getGoodsStockId()));
                throw new BusinessException("无效的商品id");
            }
        }

        /** 3. 校验 服务端计算的退货金额 与 页面传过来的是否一致 */
        if (operate.equals(YesNo.NO.getCode()) && refundAmt.compareTo(returnPriceVal) != 0) {
            LOG.info(requestId, "退货金额计算错误", String.valueOf(refundAmt));
            throw new BusinessException("退货金额错误");
        }

        /** 4. 售后数据入库操作 */
        RefundInfoEntity refundInfo = new RefundInfoEntity();
        refundInfo.setOrderId(orderInfo.getOrderId());
        refundInfo.setOrderAmt(orderInfo.getOrderAmt());
        refundInfo.setRefundAmt(refundAmt);
        
        // Yes-1:换货  No-0:退货
        refundInfo.setRefundType(operate);
        refundInfo.setStatus(RefundStatus.REFUND_STATUS01.getCode());
        refundInfo.setRefundReason(reason);
        refundInfo.setRemark(content);
        
        /** 售后图片地址数据 */
        StringBuilder imageUrl = new StringBuilder();
        for(int i=0; i<imageNum; i++){
            if(i==0){
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

        //保存 退货信息
        orderRefundDao.insert(refundInfo);
        if (null == refundInfo.getId()) {
            throw new BusinessException("退货信息保存失败!");
        }

        //插入售后流程数据
        insertServiceProcessInfo(refundInfo.getId(), RefundStatus.REFUND_STATUS01.getCode());

        // 遍历 获取订单详情ID 和 数量
        for (GoodsStockIdNumDto idNum : returngoodsList) {

            OrderDetailInfoEntity OrderDetailDto = resultMap.get(idNum.getGoodsStockId());
            RefundDetailInfoEntity refundDetailInfo = new RefundDetailInfoEntity();

            refundDetailInfo.setOrderId(orderInfo.getOrderId());
            refundDetailInfo.setOrderDetailId(OrderDetailDto.getId());
            refundDetailInfo.setGoodsPrice(OrderDetailDto.getGoodsPrice());
            refundDetailInfo.setGoodsNum(Long.valueOf(idNum.getGoodsNum()));

            // 保存退货详情
            refundDetailInfoDao.insert(refundDetailInfo);
            if (null == refundDetailInfo.getId()) {
                throw new BusinessException("退货详情保存失败!");
            }
        }

        // 更新订单状态为 售后中
        OrderInfoEntity oiDto = new OrderInfoEntity();
        oiDto.setId(orderInfo.getId());
        oiDto.setStatus(OrderStatus.ORDER_RETURNING.getCode());
        Integer subOrderUpdateStatus = orderInfoDao.update(oiDto);
        if (subOrderUpdateStatus < 1) {
            throw new BusinessException("订单状态更新失败!");
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
    public OrderInfoEntity orderRufundValidate(String requestId, Long userId, String orderId, OrderInfoEntity orderInfo) throws BusinessException {
        
        if(null == orderInfo){
            orderInfo = getOrderInfo(userId, orderId);
        }
        if (null == orderInfo) {
            LOG.info(requestId, "查询订单数据为空", orderId);
            throw new BusinessException("当前订单编号不存在,不允许进行售后操作!");
        }
        
        // 已收货的订单才允许退换货
        if (!orderInfo.getStatus().equals(OrderStatus.ORDER_COMPLETED.getCode())) {
            LOG.info(requestId, "校验订单状态", "当前订单状态非交易完成，不支持退换货");
            throw new BusinessException("当前订单状态不支持售后操作!");
        }
        
        // 交易完成的订单 7天内 才可售后操作
        if(DateFormatUtil.isExpired(orderInfo.getAcceptGoodsDate(), 7)){
            LOG.info(requestId, "订单交易完成超过7天不能进行售后操作", "");
            throw new BusinessException("当前订单状态不支持售后操作!");
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        List<RefundInfoEntity> refundInfoList = orderRefundDao.queryRefundInfoByParam(map);
        // 每个订单只允许一次售后操作
        if(null != refundInfoList && !refundInfoList.isEmpty()){
            LOG.info(requestId, "当前订单已做过售后操作，不能再次进行售后操作", "");
            throw new BusinessException("当前订单状态不支持售后操作!");
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
    public void insertServiceProcessInfo(Long refundId, String nodeName) throws BusinessException {
        ServiceProcessEntity Dto = new ServiceProcessEntity();
        Dto.setRefundId(refundId);
        Dto.setNodeName(nodeName);
        serviceProcessDao.insert(Dto);
        if (null == Dto.getId()) {
            throw new BusinessException("保存售后流程信息失败!");
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
	public void insertServiceProcessAllInfo(Long refundId, String nodeName, String approvalComments)
			throws BusinessException {
		ServiceProcessEntity Dto = new ServiceProcessEntity();
		Dto.setRefundId(refundId);
		Dto.setNodeName(nodeName);
		Dto.setApprovalComments(approvalComments);
		serviceProcessDao.insert(Dto);
		if (null == Dto.getId()) {
			throw new BusinessException("保存售后流程信息失败!");
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
    public OrderInfoEntity getOrderInfo(Long userId, String orderId) throws BusinessException {
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
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public void submitLogisticsInfo(String requestId, String userId, String refundId, String orderId, String logisticsName, String logisticsNo) throws BusinessException {

        Long userIdVal = Long.valueOf(userId);
        Long refundIdVal = Long.valueOf(refundId);
        
        /** 1. 校验订单状态 */
        OrderInfoEntity orderInfo = getOrderInfo(userIdVal, orderId);
        // 售后中的订单才允许提交物流信息
        if (!orderInfo.getStatus().equals(OrderStatus.ORDER_RETURNING.getCode())) {
            LOG.info(requestId, "校验订单状态,当前订单状态不能提交物流信息", orderInfo.getStatus());
            throw new BusinessException("当前订单状态不能提交物流信息!");
        }
        
        /** 2. 查询售后信息 */
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("id", refundIdVal);
        param.put("orderId", orderId);
        List<RefundInfoEntity> refundInfoList = orderRefundDao.queryRefundInfoByParam(param);
        if (null == refundInfoList || refundInfoList.isEmpty()) {
            LOG.info(requestId, "售后信息查询", "数据为空");
            throw new BusinessException("无售后记录,无法提交物流信息!");
        }
        
        /** 3. 校验售后信息 */
        RefundInfoEntity refundInfo = refundInfoList.get(0);
        if(!refundInfo.getStatus().equals(RefundStatus.REFUND_STATUS01.getCode())){
            LOG.info(requestId, "售后状态查询,当前售后状态不允许提交物流信息", refundInfo.getStatus());
            throw new BusinessException("当前售后状态不允许提交物流信息");
        }
        
        if(null == refundInfo.getIsAgree() || !refundInfo.getIsAgree().equals("1")){
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
        insertServiceProcessInfo(refundIdVal, RefundStatus.REFUND_STATUS02.getCode());
        
    }

    /**
     * 查看售后进度
     * 
     * @param subOrderId
     * @throws BusinessException
     */
    public Map<String, Object> viewProgress(String requestId, String userId, String orderId) throws BusinessException {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Long userIdVal = Long.valueOf(userId);

        ServiceProcessDto serviceProcessDto = new ServiceProcessDto();

        /**
         * 获取订单的详情
         */
        OrderInfoEntity orderInfo = orderInfoDao.selectByOrderIdAndUserId(orderId, userIdVal);

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
        
        /**
         * 根据商户的编码获取商户的详细信息，然后获取商户的退货地址
         */
        MerchantInfoEntity merchantInfo = memChantRepository.queryByMerchantCode(orderInfo.getMerchantCode());
        
        if(null == merchantInfo){
            LOG.info(requestId, "根据商户编码获取商户详细信息", "数据为空");
            throw new BusinessException("无效的商户编码!");
        }
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderId", orderId);
        List<RefundInfoEntity> refundInfoList = orderRefundDao.queryRefundInfoByParam(param);
        if (null == refundInfoList || refundInfoList.isEmpty()) {
            LOG.info(requestId, "售后信息查询", "数据为空");
            throw new BusinessException("无售后信息,无法查询售后进度!");
        }

        // 退换货信息
        RefundInfoEntity refundInfo = refundInfoList.get(0);

        serviceProcessDto.setRefundId(refundInfo.getId());
        serviceProcessDto.setStatus(refundInfo.getStatus());
        serviceProcessDto.setRefundType(refundInfo.getRefundType());
        
        /** status 状态 RS01, 退货信息表字段 is_agree=1 时，可提交物流信息   */
        if (refundInfo.getStatus().equals(RefundStatus.REFUND_STATUS01.getCode())
            && null != refundInfo.getIsAgree() && refundInfo.getIsAgree().equals("1")) {
            serviceProcessDto.setIsAllowed("1");
            //在商品退换货的时候，加上商户的退货地址
            serviceProcessDto.setMerchantInfoReturnAddress(merchantInfo.getMerchantReturnAddress());
        }

        /** RS02、RS03、RS04、RS05 客户端显示客户发货物流地址 */
        if (RefundStatus.showSlogistics(refundInfo.getStatus())) {
            serviceProcessDto.setSlogisticsName(refundInfo.getSlogisticsName());
            serviceProcessDto.setSlogisticsNo(refundInfo.getSlogisticsNo());
        }

        /** 换货(refundType=1) RS04、RS05 客户端显示商户发货物流地址 */
        if (refundInfo.getRefundType().equals("1") && RefundStatus.showRlogistics(refundInfo.getStatus())) {
            serviceProcessDto.setRlogisticsName(dataDicRepository.queryDataNameByDataNo(refundInfo.getRlogisticsName()));
            serviceProcessDto.setRlogisticsNo(refundInfo.getRlogisticsNo());
        }

        ServiceProcessEntity spDto = new ServiceProcessEntity();
        spDto.setRefundId(refundInfo.getId());
        List<ServiceProcessEntity> serviceProcessList = serviceProcessDao.filter(spDto);

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
        List<RefundDetailInfoEntity> refundDetailInfoList = refundDetailInfoDao.filter(RefundDetailInfoQueryDto);
        
        
        List<GoodsInfoInOrderDto> goodsListInEachOrder = new ArrayList<GoodsInfoInOrderDto>();
        // 退换货商品总数目
        int goodsSum = 0;
        for(RefundDetailInfoEntity refundDetailInfo : refundDetailInfoList){
            goodsSum += refundDetailInfo.getGoodsNum();
            
            OrderDetailInfoEntity orderDetailInfo = orderDetailInfoRepository.select(refundDetailInfo.getOrderDetailId());
            GoodsInfoInOrderDto goodsInfo = new GoodsInfoInOrderDto();
            goodsInfo.setGoodsId(orderDetailInfo.getGoodsId());
            goodsInfo.setGoodsStockId(orderDetailInfo.getGoodsStockId());
            goodsInfo.setBuyNum(refundDetailInfo.getGoodsNum());
            GoodsStockInfoEntity goodsStock = goodsStockDao.select(orderDetailInfo.getGoodsStockId());
            goodsInfo.setGoodsLogoUrl(goodsStock.getStockLogo());
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
    public void uploadReturnImage(String requestId, String userId, String orderId, String returnImage) throws BusinessException {
        String[] imagelist = returnImage.split(",");
        if(imagelist.length > 3){
            LOG.info(requestId, "计算图片数量", "图片数量超过3张");
            throw new BusinessException("最多只能上传3张照片");
        }
        String dir = userId + "/" + orderId;
        for (int i = 0; i < imagelist.length; i++) {
            String type = "refundimage" + "_" + i;

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("dir", dir);
            paramMap.put("imgFile", imagelist[i]);
            paramMap.put("imgType", type);

            //保存特征图片到服务器
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
    public void uploadReturnImageAsend(String requestId, String userId, String orderId, List<String> returnImage) throws BusinessException {
        
        String dir = userId + "/" + orderId;
        
        for(int i=0; i<returnImage.size(); i++){
            
            String type = "refundimage" + "_" + i;

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("dir", dir);
            paramMap.put("imgFile", returnImage.get(i));
            paramMap.put("imgType", type);

            //保存特征图片到服务器
            fileViewService.uploadReturnImage(requestId, paramMap);
        }
    }
    
    /**
     * 用户申请换货，商家重新发货的物流单号显示已签收，标记该售后信息为售后完成
     * @throws BusinessException 
     */
    public void updateRefundToCompleted(String rlogisticsId) throws BusinessException{
        List<RefundInfoEntity> refundList = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rlogisticsId", rlogisticsId);
        
        refundList = orderRefundDao.queryRefundInfoByParam(map);
        
        if(null != refundList && !refundList.isEmpty()){
            
            RefundInfoEntity refundInfo = refundList.get(0);
            if(refundInfo.getStatus().equals(RefundStatus.REFUND_STATUS04.getCode())){
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("orderId", refundInfo.getOrderId());
                paramMap.put("status", RefundStatus.REFUND_STATUS04.getCode());
                
                // 换货 商家重新发货物流显示已签收，设置退货信息表 售后完成时间 为当前时间，不改变售后流程状态
                int updateFlag = orderRefundDao.updateRefundStatusAndCtimeByOrderId(paramMap);
                if(updateFlag != 1){
                    LOGGER.error("商家再发货物流显示已签收，更新售后数据状态为售后完成失败,refundId[{}]",refundInfo.getId());
                }
                
            }
        }
    }
    
}
