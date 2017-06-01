package com.apass.esp.nothing;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.logistics.TrackingData;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.service.logistics.LogisticsService;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.GsonUtils;

import net.sf.json.JSONObject;

/**
 * 物流回调
 * @description 
 *
 * @author liuming
 * @version $Id: LogisticsWebHookController.java, v 0.1 2017年3月16日 下午3:09:42 liuming Exp $
 */
@Path("/webHook")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LogisticsWebHookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsWebHookController.class);
    @Autowired
    private LogisticsService    logisticsService;

    @POST
    @Path("/logistics")
    public Response webHook(String param) {
        
        String logStashSign = LogStashKey.WEBHOOK_LOGISTICS.getValue();
        String methodDesc = LogStashKey.WEBHOOK_LOGISTICS.getName();
        
        LOGGER.info("trackingmore_webHook->param:{}", param);
        JSONObject jsonObj = JSONObject.fromObject(param);
        String dataStr = jsonObj.getString("data");

        TrackingData trackData = GsonUtils.convertObj(dataStr, TrackingData.class);
        
        if (null == trackData) {
            LOG.info("webHook[" + dataStr + "]回调失败");
            LOGGER.error("webHook[{}]回调失败",dataStr);
            return Response.fail(BusinessErrorCode.CALLBACK_FUNCTION_FAILED);
        }
        
        String trackingNumber = trackData.getTrackingNumber();
        
        String requestId = logStashSign + "_" + trackingNumber;
        LOG.info(requestId, methodDesc, dataStr);
        
        logisticsService.webHook(requestId, trackData);

        return Response.success("调用成功");
    }
}