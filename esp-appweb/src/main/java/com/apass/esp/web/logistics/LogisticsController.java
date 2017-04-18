package com.apass.esp.web.logistics;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.domain.Response;
import com.apass.esp.service.logistics.LogisticsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;


/**
 * 物流信息查询
 * @description 
 *
 * @author liuming
 * @version $Id: LogisticsController.java, v 0.1 2016年12月28日 上午9:37:41 liuming Exp $
 */
@Path("/logistics")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LogisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsController.class);
    @Autowired
    private LogisticsService    logisticsService;

    /**
     * 根据订单号查询物流信息
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/loadByOrderId")
    public Response loadLosgitics(Map<String, Object> paramMap) {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            String orderId = CommonUtils.getValue(paramMap, "orderId");
            if (StringUtils.isEmpty(orderId)) {
                LOGGER.info("订单Id不能为空");
                return Response.fail("您的订单信息缺失!稍后再试");
            }
            returnMap= logisticsService.loadLogisticInfo(orderId);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOGGER.error("物流信息查询失败!", e);
            return Response.fail("物流信息查询失败!");
        }
        return Response.successResponse(returnMap);
    }

}