package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.MessageListener;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.MessageListenerMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.repository.goods.GoodsBasicRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.repository.refund.RefundDetailInfoRepository;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.esp.service.refund.ServiceProcessService;
import com.apass.esp.third.party.jd.client.JdAfterSaleApiClient;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.entity.aftersale.AfsInfo;
import com.apass.esp.third.party.weizhi.client.WeiZhiAfterSaleApiClient;
import com.apass.esp.third.party.weizhi.entity.aftersale.AfsServicebyCustomerPin;
import com.apass.esp.third.party.weizhi.entity.aftersale.SkuObject;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.transform.Source;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * type: class
 * 京东订单售后进度处理
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class JdAfterSaleScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdAfterSaleScheduleTask.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private AfterSaleService afterSaleService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private OrderRefundRepository orderRefundDao;

    @Autowired
    private RefundDetailInfoRepository refundDetailInfoRepository;

    @Autowired
    private ServiceProcessService serviceProcessService;

    @Autowired
    private OrderRefundRepository orderRefundRepository;

    @Autowired
    private CashRefundMapper cashRefundMapper;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private TxnInfoMapper txnInfoMapper;
    @Autowired
    private MessageListenerMapper messageListenerMapper;
    @Autowired
    private WeiZhiAfterSaleApiClient weiZhiAfterSaleApiClient;
    /**
     * 京东售后状态更新
     */
    @Scheduled(cron = "0 0/30 * * * *")
    public void handleJdConfirmPreInventoryTask() {
        List<OrderInfoEntity> orderInfoEntityList = orderService.getJdOrderByOrderStatus("D05");
        LOGGER.info("refund task begin...");
        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList) {

            MessageListener ml=new MessageListener();
            ml.setType("100");
            LOGGER.info("orderInfoEntity.getOrderId() {}",orderInfoEntity.getOrderId());
            ml.setOrderid(orderInfoEntity.getExtOrderId());
            List<SkuObject> serviveList = null;
            try {
                //TODO 根据客户账号和订单号分页查询服务单概要信息
                serviveList = weiZhiAfterSaleApiClient.getServiveList(orderInfoEntity.getExtOrderId(), "1", "10");
            } catch (Exception e) {
                LOGGER.error("调用接口:根据客户账号和订单号分页查询服务单概要信息失败!",e);
            }
            if (serviveList == null) {
            	ml.setStatus("0");
            	ml.setResult("afterSaleServiceListPageQuery接口调用失败！");
            	ml.setCreatedTime(new Date());
            	ml.setUpdatedTime(new Date());
            	messageListenerMapper.insertSelective(ml);
                continue;
            }

            List<AfsServicebyCustomerPin> serviceInfoList = serviveList.get(0).getResult().getResult().getServiceInfoList();
            //因为提交售后只上传一个skuid,所以serviveList只有一个元素
            if (CollectionUtils.isEmpty(serviveList)) {
            	ml.setStatus("0");
            	ml.setResult("获取serviceInfoList组件列表失败！");
            	ml.setCreatedTime(new Date());
            	ml.setUpdatedTime(new Date());
            	messageListenerMapper.insertSelective(ml);
                continue;
            }
            LOGGER.info("orderInfoEntity.getExtOrderId() {},result {}",orderInfoEntity.getExtOrderId(), GsonUtils.toJson(serviceInfoList));
            Integer customerExpect = getCustomerExpect(serviceInfoList, orderInfoEntity.getOrderId());

            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderInfoEntity.getOrderId());
            if (customerExpect == 10) {
                map.put("refundType", "0");
            } else {
                map.put("refundType", "1");
            }
            RefundInfoEntity refundInfoEntity = orderRefundService.queryRefundInfoByOrderIdAndRefundType(map);
            if (refundInfoEntity == null) {
                return;
            }
            process(serviceInfoList, refundInfoEntity.getId(),orderInfoEntity.getOrderId());
            //该售后单的状态改为进度最慢的子售后单进度
            String refundStatus = getStatus(serviceInfoList);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("orderId", orderInfoEntity.getOrderId());
            if (!refundStatus.equalsIgnoreCase(RefundStatus.REFUND_STATUS01.getCode())) {
                //根据状态改变
                paramMap.put("status", refundStatus);
                if(refundStatus.equals(RefundStatus.REFUND_STATUS05.getCode())){
                    paramMap.put("completionTime",new Date());
                }
                LOGGER.info("updateRefundStatusJDByOrderId  orderId {}",orderInfoEntity.getOrderId());
                orderRefundDao.updateRefundStatusJDByOrderId(paramMap);
            }
        }

    }

    /**
     * 得到状态
     *
     * @param jsonArray
     * @return
     */
    private String getStatus(List<AfsServicebyCustomerPin> serviceInfoList) {
        int j = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < serviceInfoList.size(); i++) {
            Integer afsServiceStep = serviceInfoList.get(0).getAfsServiceStep();
            list.add(afsServiceStep);
            if (afsServiceStep == 40 || afsServiceStep == 50) {
                j++;
            }
        }
        if (j == serviceInfoList.size()) {
            return RefundStatus.REFUND_STATUS05.getCode();
        } else {
            Integer step = Collections.min(list);
            if (step == 20 || step == 60) {
                return RefundStatus.REFUND_STATUS06.getCode();
            } else if (step == 31) {
                return RefundStatus.REFUND_STATUS02.getCode();
            } else if (step == 32) {
                return RefundStatus.REFUND_STATUS03.getCode();
            } else if (step == 33 || step == 34) {
                return RefundStatus.REFUND_STATUS04.getCode();
            } else if (step == 40 || step == 50) {
                return RefundStatus.REFUND_STATUS05.getCode();
            } else {
                return RefundStatus.REFUND_STATUS01.getCode();
            }
        }
    }

    /**
     * 插入进度
     *
     * @param jsonArray
     * @param refundId
     * @return
     */
    private void process(List<AfsServicebyCustomerPin> serviceInfoList, long refundId,String orderId) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < serviceInfoList.size(); i++) {
            Integer afsServiceStep = serviceInfoList.get(i).getAfsServiceStep();
            list.add(afsServiceStep);
        }
        Integer i = Collections.min(list);
        LOGGER.info("process orderId {} ,i {}",orderId,i);
        if (i == 20 || i == 60) {
            insertProcess(refundId, RefundStatus.REFUND_STATUS06.getCode(), "",orderId);
        } else if (i == 33 || i == 34||i == 32||i == 31) {
            Map<String, String> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("refundId", String.valueOf(refundId));
            map.put("approvalUser", "jdAdmin");
            map.put("approvalComments", "同意");
            orderRefundRepository.agreeRefundByOrderId(map);
            insertProcess(refundId, RefundStatus.REFUND_STATUS02.getCode(), "",orderId);
            insertProcess(refundId, RefundStatus.REFUND_STATUS03.getCode(), "",orderId);
            insertProcess(refundId, RefundStatus.REFUND_STATUS04.getCode(), "",orderId);
        } else if (i == 40 || i == 50) {
            insertProcess(refundId, RefundStatus.REFUND_STATUS05.getCode(), "",orderId);
        }
    }


    /**
     * 得到 退货(10)、换货(20)
     *
     * @param jsonArray
     * @return
     */
    private Integer getCustomerExpect(List<AfsServicebyCustomerPin> serviceInfoList, String orderId) {
        MessageListener ml=new MessageListener();
        ml.setType("100");
        ml.setOrderid(orderId);
        LOGGER.info("newAfsInfo.. {}",GsonUtils.toJson(serviceInfoList.get(0)));
        for (AfsServicebyCustomerPin afsCusPin : serviceInfoList) {
            RefundDetailInfoEntity refundDetailInfoEntity = new RefundDetailInfoEntity();
            Long skuId = afsCusPin.getWareId();
            GoodsInfoEntity goodsInfoEntity = goodsRepository.selectGoodsBySkuId(skuId.toString());
            refundDetailInfoEntity.setGoodsId(goodsInfoEntity.getId());

            refundDetailInfoEntity.setOrderId(orderId);
            Integer i = afsCusPin.getAfsServiceStep();
            if (i == 20 || i == 60) {
            	if(i == 20){
            		ml.setResult("审核不通过");
            	}else{
            		ml.setResult("取消 ");
            	}
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS06.getCode());
            } else if (i == 31) {
            	ml.setResult("京东收货");
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS02.getCode());
            } else if (i == 32) {
            	ml.setResult("京东收货");
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS03.getCode());
            } else if (i == 34 || i == 40) {
            	if(i == 34){
                	ml.setResult("商家处理");
            	}else{
                	ml.setResult("用户确认");
            	}
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS04.getCode());
            } else if (i == 50) {
            	ml.setResult("完成");
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS05.getCode());
            } else {
            	if(i==10){ml.setResult("完成");}
            	else if(i==21){ml.setResult("客服审核");}
            	else if(i==22){ml.setResult("商家审核");}
            	else if(i==33){ml.setResult("京东处理");}
                refundDetailInfoEntity.setStatus(RefundStatus.REFUND_STATUS01.getCode());
            }
            ml.setCreatedTime(new Date());
            ml.setUpdatedTime(new Date());
            messageListenerMapper.insertSelective(ml);
            refundDetailInfoRepository.updateByStatusAndGoodsId(refundDetailInfoEntity);
        }

        return serviceInfoList.get(0).getCustomerExpect();
    }

    /**
     * 插入进度
     *
     * @param refundId
     * @param status
     */
    private void insertProcess(long refundId, String status, String modeMessage,String orderId) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("refundId", String.valueOf(refundId));
            map.put("nodeName", status);
            List<ServiceProcessEntity> list1 = serviceProcessService.queryServiceProcessByParam(map);
            if (CollectionUtils.isEmpty(list1)) {
                if(status.equalsIgnoreCase(RefundStatus.REFUND_STATUS05.getCode())){
                    //RS05时 退款  退款额度
                    CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);
                    OrderInfoEntity orderEntity = orderInfoRepository.selectByOrderId(orderId);
                    List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(cashRefund.getMainOrderId());
                    if (CollectionUtils.isNotEmpty(txnInfoEntityList)) {
                        BigDecimal txnAmt = new BigDecimal(0);
                        Date date = new Date();
                        if (txnInfoEntityList.size() == 1) {
                            
                        }
                    }

                }
                afterSaleService.insertServiceProcessInfo(refundId, status, modeMessage);
            }
        } catch (Exception e) {
            LOGGER.error("refundId {} status {} 插入进度失败", refundId, status);
        }
    }
}
