package com.apass.esp.web.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.service.datadic.DataDicService;
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
    private DataDicService        dataDicService;

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

            respBody.setRows(serviceProcessList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("订单售后信息查询失败", e);
            respBody.setMsg("订单售后信息查询失败");
        }
        return respBody;
    }
}
