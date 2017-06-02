package com.apass.esp.web.purchase;

import java.util.HashMap;
import java.util.Map;

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
import com.apass.esp.service.purchase.PurchaseService;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;


@Path("/purchase")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PurchaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);
    @Autowired
    private PurchaseService purchaseService;
    
    /**
     * 
     * 立即购买初始化 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/buyNow/init")
    public Response init(Map<String, Object> paramMap){
        Map<String,Object> returnMap = new HashMap<>();
        try {
            Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
            Long goodsId = CommonUtils.getLong(paramMap, ParamsCode.GOODS_ID);
            Long goodsStockId = CommonUtils.getLong(paramMap, ParamsCode.GOODS_STOCK_ID);
            if(null==userId){
            	LOGGER.error("对不起!用户号不能为空");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            if(null==goodsId){
            	LOGGER.error("对不起!商品号不能为空");
            	return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            if(null==goodsStockId){
            	LOGGER.error("对不起!商品库存号不能为空");
            	return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            purchaseService.buyRightNowInit(returnMap,userId,goodsId,goodsStockId);
        } catch (Exception e) {
            LOGGER.error("立即购买初始化失败!请稍后再试", e);
            return Response.fail(BusinessErrorCode.BUY_NOWINIT_FAILED);
        }
        return Response.successResponse(returnMap);
    }
    

    
}
