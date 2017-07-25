package com.apass.esp.web.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.ServiceProcessService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 
 * @description 售后详细查询
 *
 * @author chenbo
 * @version $Id: OrderQueryController.java, v 0.1 2017年1月3日 上午15:15:57 chenbo
 *          Exp $
 */
@Controller
@RequestMapping("/application/business/serviceProcess")
public class ServiceProcessQueryController {
    /**
     * 日志
     */
    private static final Logger   LOG = LoggerFactory.getLogger(ServiceProcessQueryController.class);

    @Autowired
    private ServiceProcessService serviceProcessService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TxnInfoMapper txnInfoMapper;
    /**
     * 退货信息查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<ServiceProcessEntity> handlePageList(HttpServletRequest request) {
        ResponsePageBody<ServiceProcessEntity> respBody = new ResponsePageBody<ServiceProcessEntity>();
        try {
            // 获取请求的参数
            String orderId = HttpWebUtils.getValue(request, "orderId");

            // 通过订单号查询售后信息
            Map<String, String> map = new HashMap<String, String>();
            map.put("orderId", orderId);
            List<ServiceProcessEntity> serviceProcessList = serviceProcessService
                .queryServiceProcessDetailByOrderId(map);
            //首先根据订单的编号，获取主订单的id 
            OrderInfoEntity entity = orderService.selectByOrderId(orderId);
            if(null != entity){
            	String mainOrderId = entity.getMainOrderId();
            	//根据主订单的id，查询该订单的支付类型
            	List<TxnInfoEntity> txnList = txnInfoMapper.selectByOrderId(mainOrderId);
            	if(!CollectionUtils.isEmpty(txnList)){
            		for (TxnInfoEntity txn : txnList) {
						if(StringUtils.equals(txn.getTxnType(), TxnTypeCode.ALIPAY_CODE.getCode()) || StringUtils.equals(txn.getTxnType(), TxnTypeCode.ALIPAY_SF_CODE.getCode())){
							serviceProcessList.get(0).setPayType(txn.getTxnType());
							break;
						}
						serviceProcessList.get(0).setPayType(txn.getTxnType());
					}
            	}
            }
            respBody.setRows(serviceProcessList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("订单售后信息查询失败", e);
            respBody.setMsg("订单售后信息查询失败");
        }
        return respBody;
    }
}
