package com.apass.esp.service.jd;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.dto.logistics.JdTrack;
import com.apass.esp.domain.dto.logistics.LogisticsResponseDto;
import com.apass.esp.domain.dto.logistics.Trace;
import com.apass.esp.domain.dto.logistics.Track;
import com.apass.esp.domain.dto.logistics.TrackingData;
import com.apass.esp.domain.dto.logistics.TrackingInfo;
import com.apass.esp.domain.entity.common.ConstantEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.utils.ConstantsUtils;
import com.apass.esp.repository.common.ConstantRepository;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdOrderApiClient;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;

@Service
public class JdLogisticsService {

    private static final Logger       LOGGER = LoggerFactory.getLogger(JdLogisticsService.class);

    @Autowired
    private JdOrderApiClient       logisticsHttpClient;
    @Autowired
    private OrderInfoRepository       orderInfoDao;
    @Autowired
    private OrderDetailInfoRepository orderDetailDao;
    @Autowired
    private ImageService imageService;

    
    /**
     * 查询物流信息
     * 
     * @param orderId   订单号
     * @return
     * @throws BusinessException 
     */
    public Map<String, Object> getSignleTrackings(String orderId) throws BusinessException {
        
    	if (StringUtils.isBlank(orderId)) {
            throw new BusinessException("订单号不能为空!");
        }
        
    	OrderInfoEntity entity = orderInfoDao.selectByOrderId(orderId);
    	
        Map<String, Object> resultMap = new HashMap<>();
        LogisticsResponseDto logisticInfo = new LogisticsResponseDto();
        try {
            logisticInfo.setShipperCode(entity.getLogisticsName());
            logisticInfo.setLogisticCode(entity.getLogisticsNo());
            List<Trace> traces = getSignleTracksByOrderId(entity.getExtOrderId());
            logisticInfo.setTraces(traces);
            
            List<OrderDetailInfoEntity> orderDetailList = orderDetailDao.queryOrderDetailInfo(orderId);
            Long goodsNum = 0L;
            for (OrderDetailInfoEntity orderDetail : orderDetailList) {
                goodsNum += orderDetail.getGoodsNum();
            }
            if (null != orderDetailList && orderDetailList.size() > 0) {
                
                resultMap.put("logoInfo", imageService.getImageUrl(orderDetailList.get(0).getGoodsLogoUrl())); //图片logo
            }
            resultMap.put("goodsNum", goodsNum);
            resultMap.put("logisticInfo", logisticInfo);
            resultMap.put("logisticTel", "400-603-3600");
            resultMap.put("signTime", traces.get(traces.size() - 1).getAcceptTime());//签收时间
            logisticInfo.setSuccess(true);
        } catch (Exception e) {
            resultMap.put("logisticInfo", logisticInfo);
            resultMap.put("logisticTel", "400-603-3600");
            logisticInfo.setSuccess(false);
            LOGGER.error("查询物流信息失败", e);
            LOGGER.error("getSignleTrackings->物流单号:{}查询物流信息失败!",entity.getExtOrderId());
        }

        OrderInfoEntity orderInfo = orderInfoDao.selectByOrderId(orderId);
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
        resultMap.put("logisticMerchant", "京东物流"); //物流商户
        resultMap.put("logisticTel", "400-603-3600");
        return resultMap;
    }

    
    
    /**
     * 京东 获取物流轨迹
     * 
     * @param orderId   订单号
     * @return
     * @throws BusinessException
     */
    public List<JdTrack> getSignleTrackingsByOrderId(String jdOrderId) throws BusinessException {
        
    	JdApiResponse<JSONObject> str = logisticsHttpClient.orderOrderTrackQuery(Long.parseLong(jdOrderId));
        
    	List<JdTrack> trackList = GsonUtils.convertList(str.getResult().getString("orderTrack"), JdTrack.class);
    	
    	Collections.reverse(trackList);  
    	return trackList;
    }
    
    /**
     * 根据京东id，获取物流的轨迹
     * @param jdorderId
     * @return
     * @throws BusinessException
     */
    public List<Trace> getSignleTracksByOrderId(String jdorderId) throws BusinessException {
    	 List<JdTrack> tracks =  getSignleTrackingsByOrderId(jdorderId);
         List<Trace> traces = new ArrayList<Trace>();
         if (!CollectionUtils.isEmpty(tracks)) {
             for (JdTrack track : tracks) {
             	Trace jdTrace = new Trace();
             	jdTrace.setAcceptStation(track.getDetails());
             	jdTrace.setAcceptTime(track.getDate());
             	jdTrace.setRemark(track.getStatusDescription());
             	traces.add(jdTrace);
			}
         }
         return traces;
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
    
    
    
}
