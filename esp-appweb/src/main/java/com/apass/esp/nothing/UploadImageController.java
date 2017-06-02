package com.apass.esp.nothing;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.RefundImageDto;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;

@Path("/uploadImage")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UploadImageController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadImageController.class);

    @Autowired
    private AfterSaleService afterSaleService;
    
    /**
     * 退换货时，上传商品照片
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/refund")
    public Response uploadImageAscend(RefundImageDto refundImageDto){
        
        String logStashSign = LogStashKey.AFTERSALE_UPLOADIMAGEASCEND.getValue();
        String methodDesc = LogStashKey.AFTERSALE_UPLOADIMAGEASCEND.getName();
        
        String userId = refundImageDto.getUserId();
        String orderId = refundImageDto.getOrderId();
        List<String> imageList = refundImageDto.getReturnImage();// 售后图片
        
        String requestId = logStashSign + "_" + orderId;
        LOG.info(requestId, methodDesc, userId);
        
        if(StringUtils.isAnyBlank(userId, orderId)){
        	LOGGER.error("数据不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        
        if(imageList.size() > 3 || imageList.size() < 1){
        	LOGGER.error("请上传1~3张照片");
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        
        try {
            // 订单状态校验，每个订单只允许一次售后
            afterSaleService.orderRufundValidate(requestId, Long.valueOf(userId), orderId, null);
            
            afterSaleService.uploadReturnImageAsend(requestId, userId, orderId, imageList);
            return Response.success("售后商品图片上传成功!");
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(BusinessErrorCode.UPLOAD_PICTURE_FAILED);
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.UPLOAD_PICTURE_FAILED);
        }
        
    }
    
}
