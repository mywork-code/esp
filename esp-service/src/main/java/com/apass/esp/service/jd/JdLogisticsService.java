package com.apass.esp.service.jd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.apass.esp.domain.enums.TrackingmoreStatus;
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
    private ConstantRepository        constantDao;
    @Autowired
    private ImageService imageService;

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
            TrackingData trackData = null;//logisticsHttpClient.getSignleTrackings(sb.toString());
            if (null != trackData) {

                //logisticInfo.setState(convertTransportStatus(trackData.getStatus()));
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
     * 京东 获取物流轨迹
     * 
     * @param orderId   订单号
     * @return
     * @throws BusinessException
     */
    public List<JdTrack> getSignleTrackingsByOrderId(String orderId) throws BusinessException {
        
    	JdApiResponse<JSONObject> str = logisticsHttpClient.orderOrderTrackQuery(Long.parseLong(orderId));
        
    	List<JdTrack> trackList = GsonUtils.convertList(str.getResult().toString(), JdTrack.class);
    	
    	return trackList;
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
