package com.apass.esp.service.logistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mockito.asm.tree.TryCatchBlockNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  物流信息
 * @description 
 *
 * @author liuming
 * @version $Id: LogisticsService.java, v 0.1 2016年12月27日 下午1:41:29 liuming Exp $
 */

import com.apass.esp.domain.dto.logistics.LogisticsRequestDto;
import com.apass.esp.domain.dto.logistics.LogisticsResponseDto;
import com.apass.esp.domain.dto.logistics.Trace;
import com.apass.esp.domain.dto.logistics.Track;
import com.apass.esp.domain.dto.logistics.TrackingData;
import com.apass.esp.domain.dto.logistics.TrackingInfo;
import com.apass.esp.domain.dto.logistics.TrackingRequestDto;
import com.apass.esp.domain.entity.common.ConstantEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.TrackingmoreStatus;
import com.apass.esp.domain.utils.ConstantsUtils;
import com.apass.esp.domain.vo.LogisticsFirstDataVo;
import com.apass.esp.repository.common.ConstantRepository;
import com.apass.esp.repository.logistics.LogisticsHttpClient;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.esp.service.common.ImageService;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;

@Service
public class LogisticsService {

    private static final Logger       LOGGER = LoggerFactory.getLogger(LogisticsService.class);

    @Autowired
    private LogisticsHttpClient       logisticsHttpClient;
    @Autowired
    private OrderInfoRepository       orderInfoDao;
    @Autowired
    private OrderDetailInfoRepository orderDetailDao;
    @Autowired
    private ConstantRepository        constantDao;
    @Autowired
    private CacheManager              cacheManager;
    @Autowired
    private OrderRefundRepository     orderRefundRepository;
    @Autowired
    private AfterSaleService          afterSaleService;
    @Autowired
    private ImageService imageService;

    /**
     * 快递鸟查询物流详情
     * 
     * @param logisticCode  物流单号
     * @param shipperCode   物流厂商编码
     * @return
     * @throws Exception
     */
    public LogisticsResponseDto loadLogisticInfo(String logisticCode, String shipperCode) throws Exception {
        LogisticsResponseDto response = null;
        //  优先取缓存物流信息
        try {
            String logisticsStr = cacheManager.get(shipperCode + "_" + logisticCode);
            if (StringUtils.isNotEmpty(logisticsStr)) {
                return GsonUtils.convertObj(logisticsStr, LogisticsResponseDto.class);
            }
        } catch (Exception e) {
            LOGGER.error("loadLogisticInfo cacheManager get fail", e);
        }
        try {
            LogisticsRequestDto dto = new LogisticsRequestDto();
            dto.setLogisticCode(logisticCode);
            dto.setShipperCode(shipperCode);
            String responseJson = logisticsHttpClient.getOrderTracesByJson(GsonUtils.toJson(dto));
            response = GsonUtils.convertObj(responseJson, LogisticsResponseDto.class);
            //  物流信息存入缓存1小时
            try {
                if (response.isSuccess()) {
                    cacheManager.set(shipperCode + "_" + logisticCode, responseJson, 60 * 60);
                }
            } catch (Exception e) {
                LOGGER.error("loadLogisticInfo cacheManager set fail", e);
            }
        } catch (Exception e) {
            LOGGER.error("查询物流信息失败!", e);
            response = new LogisticsResponseDto();
            response.setSuccess(false);
            response.setReason("物流信息查询失败!");
        }
        return response;
    }

    /**
     * 查询订单物流信息[调用]
     * 
     * @param orderId   订单号
     * @param returnMap 返回信息
     * @throws Exception
     */
    public void loadLogistics(String orderId, Map<Object, Object> returnMap) throws Exception {
        OrderInfoEntity orderInfo = orderInfoDao.selectByOrderIdAndUserId(orderId, null);
        if (null == orderInfo) {
            LOGGER.error("loadLogistics orderInfo is null");
            throw new BusinessException("您的订单信息缺失!稍后再试");
        }
        List<OrderDetailInfoEntity> orderDetailList = orderDetailDao.queryOrderDetailInfo(orderId);
        Long goodsNum = 0L;
        for (OrderDetailInfoEntity orderDetail : orderDetailList) {
            goodsNum += orderDetail.getGoodsNum();
        }
        if (null != orderDetailList && orderDetailList.size() > 0) {
            returnMap.put("logoInfo", orderDetailList.get(0).getGoodsLogoUrl()); //图片logo
        }
        LogisticsResponseDto logisticInfo = this.loadLogisticInfo(orderInfo.getLogisticsNo(),
            orderInfo.getLogisticsName());
        returnMap.put("logisticInfo", logisticInfo); //物流信息
        ConstantEntity kdNiao = constantDao.selectByDataNoAndDataTypeNo(ConstantsUtils.KDNIAO_DATATYPENO,
            orderInfo.getLogisticsName());
        returnMap.put("logisticMerchant", kdNiao.getDataName()); //物流商户
        returnMap.put("goodsNum", goodsNum);
        returnMap.put("logisticTel", kdNiao.getRemark());
    }

    /**
     * Trackingmore 物流订阅
     * 
     * @param trackingNumber    物流单号
     * @param carrierCode       快递厂商编码
     * @param orderId           订单号
     * @throws BusinessException
     */
    public void subscribeSignleTracking(String trackingNumber, String carrierCode,
                                        String typeId, String type) throws BusinessException {
        TrackingRequestDto request = new TrackingRequestDto();
        request.setCarrierCode(carrierCode);
        request.setTrackingNum(trackingNumber);
        request.setOrderId(typeId);

        TrackingData trackData = logisticsHttpClient.subscribeSignleTracking(request);

        //  订阅成功 保存返回Id 供回调接口使用
        if("refund".equals(type)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("refundId", typeId);
            map.put("rlogisticsId", trackData.getId());
            orderRefundRepository.updateRlogisticsIdbyRefundId(map);
        } else if("order".equals(type)) {
            OrderInfoEntity orderInfo = orderInfoDao.selectByOrderIdAndUserId(typeId, null);
            orderInfo.setLogisticsId(trackData.getId());
            orderInfoDao.update(orderInfo);
        }
        

    }

    /**
     * 查询物流信息
     * 
     * @param carrierCode   物流厂商编码
     * @param trackingNumber    物流单号
     * @return
     * @throws BusinessException 
     */
    public Map<String, Object> getSignleTrackings(String carrierCode, String trackingNumber,
                                                  String orderId) throws BusinessException {
        if (StringUtils.isAnyEmpty(carrierCode, trackingNumber, orderId)) {
            throw new BusinessException("物流单号或物流厂商不能为空");
        }
        Map<String, Object> resultMap = new HashMap<>();
        LogisticsResponseDto logisticInfo = new LogisticsResponseDto();
        // get请求参数拼接  append("/cn")设置语言为中文
        StringBuilder sb = new StringBuilder(carrierCode);
        sb.append("/").append(trackingNumber).append("/cn");

        try {
            logisticInfo.setShipperCode(carrierCode);
            logisticInfo.setLogisticCode(trackingNumber);
            TrackingData trackData = logisticsHttpClient.getSignleTrackings(sb.toString());
            if (null != trackData) {

                logisticInfo.setState(convertTransportStatus(trackData.getStatus()));
                TrackingInfo originInfo = trackData.getOriginInfo();

                List<Track> trackList = originInfo.getTrackinfo();
                List<Trace> traces = new ArrayList<Trace>();
                for (Track track : trackList) {
                    Trace trace = new Trace();
                    trace.setAcceptTime(track.getDate());
                    trace.setAcceptStation(track.getStatusDescription());
                    trace.setRemark(track.getDetails());
                    traces.add(trace);
                }
                logisticInfo.setTraces(traces);
                resultMap.put("logisticInfo", logisticInfo);
                resultMap.put("logisticTel", originInfo.getPhone());
                resultMap.put("signTime", traces.get(traces.size() - 1).getAcceptTime());//签收时间
            }
            logisticInfo.setSuccess(true);
        } catch (Exception e) {
            resultMap.put("logisticInfo", logisticInfo);
            ConstantEntity constant = constantDao.selectByDataNoAndDataTypeNo(ConstantsUtils.TRACKINGMORE_DATATYPENO,
                carrierCode);
            resultMap.put("logisticTel", constant.getRemark());
            logisticInfo.setSuccess(false);
            LOGGER.error("查询物流信息失败", e);
            LOGGER.error("getSignleTrackings->物流单号:{}查询物流信息失败!", trackingNumber);
        }

        OrderInfoEntity orderInfo = orderInfoDao.selectByOrderIdAndUserId(orderId, null);
        if (null == orderInfo) {
            LOGGER.error("loadLogistics orderInfo is null");
            throw new BusinessException("您的订单信息缺失!稍后再试");
        }
        List<OrderDetailInfoEntity> orderDetailList = orderDetailDao.queryOrderDetailInfo(orderId);
        Long goodsNum = 0L;
        for (OrderDetailInfoEntity orderDetail : orderDetailList) {
            goodsNum += orderDetail.getGoodsNum();
        }
        if (null != orderDetailList && orderDetailList.size() > 0) {
            
            resultMap.put("logoInfo", imageService.getImageUrl(orderDetailList.get(0).getGoodsLogoUrl())); //图片logo
        }
        resultMap.put("goodsNum", goodsNum);
        ConstantEntity constant = constantDao.selectByDataNoAndDataTypeNo(ConstantsUtils.TRACKINGMORE_DATATYPENO,
            orderInfo.getLogisticsName());
        resultMap.put("logisticMerchant", constant.getDataName()); //物流商户
        resultMap.put("logisticTel", constant.getRemark());
        return resultMap;
    }

    
    
    /**
     * Trackingmore 获取物流轨迹
     * 
     * @param orderId   订单号
     * @return
     * @throws BusinessException
     */
    public List<Trace> getSignleTrackingsByOrderId(String orderId) throws BusinessException {
        List<Trace> traces = new ArrayList<Trace>();
        OrderInfoEntity orderInfo = orderInfoDao.selectByOrderIdAndUserId(orderId, null);
        // get请求参数拼接
        StringBuilder sb = new StringBuilder(orderInfo.getLogisticsName());
        sb.append("/").append(orderInfo.getLogisticsNo());
        TrackingData trackData = logisticsHttpClient.getSignleTrackings(sb.toString());
        if (null != trackData) {
            TrackingInfo originInfo = trackData.getOriginInfo();
            if (null!=originInfo) {
                List<Track> trackList = originInfo.getTrackinfo();
                if (null!=trackList && trackList.size()>0) {
                    for (Track track : trackList) {
                        Trace trace = new Trace();
                        trace.setAcceptTime(track.getDate());
                        trace.setAcceptStation(track.getStatusDescription());
                        trace.setRemark(track.getDetails());
                        traces.add(trace);
                    }
                }
            }
        }
        return traces;
    }

    /**
     * Trackingmore 物流状态转换成物流状态描述
     * 
     * @param status    物流信息状态
     * @return
     */
    public String convertTransportStatus(String status) {
        String statusDesc = "";
        if (TrackingmoreStatus.PENDING.getCode().equals(status)) {
            statusDesc = TrackingmoreStatus.PENDING.getMessage();
        } else if (TrackingmoreStatus.NOTFOUND.getCode().equals(status)) {
            statusDesc = TrackingmoreStatus.NOTFOUND.getMessage();
        } else if (TrackingmoreStatus.TRANSIT.getCode().equals(status)) {
            statusDesc = TrackingmoreStatus.TRANSIT.getMessage();
        } else if (TrackingmoreStatus.PICKUP.getCode().equals(status)) {
            statusDesc = TrackingmoreStatus.PICKUP.getMessage();
        } else if (TrackingmoreStatus.DELIVERED.getCode().equals(status)) {
            statusDesc = TrackingmoreStatus.DELIVERED.getMessage();
        } else if (TrackingmoreStatus.EXCEPTION.getCode().equals(status)) {
            statusDesc = TrackingmoreStatus.EXCEPTION.getMessage();
        } else {
            statusDesc = TrackingmoreStatus.EXCEPTION.getMessage();
        }
        return statusDesc;
    }

    /**
     * Trackingmore 物流状态变更推送
     * 
     * @param trackData
     */
    public void webHook(String requestId, TrackingData trackData) {
        String lotisticStatus = trackData.getStatus();
        if (StringUtils.isEmpty(trackData.getId())) {
            return;
        }

        if (!TrackingmoreStatus.DELIVERED.getCode().equals(lotisticStatus)) {
            return;
        }
        //  有延迟收货   则在延迟3天即可
        OrderInfoEntity orderInfo = orderInfoDao.loadBylogisticsId(trackData.getId());

        // 物流单号不在订单数据中，则可能是售后换货时，商家再次发货的物流单号
        if (null == orderInfo) {

            try {
                afterSaleService.updateRefundToCompleted(trackData.getId());
            } catch (BusinessException e) {
                LOGGER.error("换货商家再发货签收售后信息更新为售后完成失败", e);
                LOGGER.error("换货商家再发货签收售后信息更新为售后完成失败->logisticsId:{}", trackData.getId());
            }
            return;
        }
        LOGGER.info("trackingmore回调推送->webHook->logisticsId:{}->status:{}->orderStatus:{}", trackData.getId(),
            trackData.getStatus(), orderInfo.getStatus());
        if (null != orderInfo && OrderStatus.ORDER_SEND.getCode().equals(orderInfo.getStatus())) {
            Date now = new Date();
            Integer delayDateNum = 0;
            if (orderInfo.getExtendAcceptGoodsNum() > 0) {
                delayDateNum = ConstantsUtils.DELAYCONFIRMGOODSTEN;
            } else {
                delayDateNum = ConstantsUtils.DELAYCONFIRMGOODSSEVEN;
            }
            LOGGER.info("trackingmore回调推送->webHook->logisticsId:{}->delayDateNum:{}", trackData.getId(), delayDateNum);
            Date lastAcceptDate = DateFormatUtil.addDays(now, delayDateNum);
            orderInfo.setLastAcceptGoodsDate(lastAcceptDate);
            Date signData = this.getLogisticsSignDate(trackData);
            orderInfo.setLogisticsSignDate(signData);
            orderInfoDao.update(orderInfo);
        }
    }
    /**
     * TrackingMore 获取物流签收时间
     * 
     * @param trackData
     * @return
     */
    public Date getLogisticsSignDate(TrackingData trackData) {
        Date signDate=null;
        try {
            Track track = null;
            List<Track> tracks = trackData.getOriginInfo().getTrackinfo();
            if (null != tracks && tracks.size() > 0) {
                track = tracks.get(0);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                signDate = sd.parse(track.getDate());
            }
        } catch (Exception e) {
            LOGGER.error("getLogisticsSignDate->logisticsId:{} 获取物流签收时间失败",e);
        }
        return signDate;
    }
    
    public Map<String, Object> loadLogisticInfo(String orderId) throws BusinessException {
        OrderInfoEntity orderInfo = orderInfoDao.selectByOrderIdAndUserId(orderId, null);
        return this.getSignleTrackings(orderInfo.getLogisticsName(), orderInfo.getLogisticsNo(), orderId);
    }
    
    /**
     * 根据订单的Id,查询订单的快递信息，只查询最新的一条
     * @param orderId
     * @return
     * @throws BusinessException
     */
    public LogisticsFirstDataVo loadFristLogisticInfo(String orderId) throws BusinessException{
    	
    	LogisticsFirstDataVo logisticInfo = new LogisticsFirstDataVo();
    	//根据订单的id获取订单详情
    	OrderInfoEntity orderInfo = orderInfoDao.selectByOrderId(orderId);
    	if(orderInfo != null){
    		//如果物流商编号和物流单号任何一项为空，则返回空
    		if(StringUtils.isBlank(orderInfo.getLogisticsNo()) || StringUtils.isBlank(orderInfo.getLogisticsName()) ){
    			return null;
    		}
    		logisticInfo.setLogisticsNo(orderInfo.getLogisticsNo());
    		
			List<Trace> traceList =  null;
					
			try{
				//如果查询物流出现异常的时候，就默认轨迹不存在
				traceList =	getSignleTrackingsByOrderId(orderInfo.getOrderId());
			}catch(Exception e){
				LOGGER.error("编号为{}的订单，查询物流信息的时候出现错误！",orderInfo.getOrderId());
			}
					
        	if(!CollectionUtils.isEmpty(traceList)){
        		logisticInfo.setTrace(traceList.get(0));
        	}
        	
        	ConstantEntity constant = constantDao.selectByDataNoAndDataTypeNo(ConstantsUtils.TRACKINGMORE_DATATYPENO,
                    orderInfo.getLogisticsName());
        	logisticInfo.setLogisticsName(constant!=null?constant.getDataName():orderInfo.getLogisticsName());
    	}
    	
    	return logisticInfo;
    }
    
    
}
