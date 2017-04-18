package com.apass.esp.web.aftersale;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.cart.GoodsStockIdNumDto;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.domain.enums.RefundReason;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;

@Path("/afterSale")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AfterSaleController {

    @Autowired
    private AfterSaleService afterSaleService;
    
    /**
     * 退换货时，上传商品照片
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/uploadImage")
    public Response uploadImage(Map<String, Object> paramMap){
        
        String logStashSign = LogStashKey.AFTERSALE_UPLOADIMAGE.getValue();
        String methodDesc = LogStashKey.AFTERSALE_UPLOADIMAGE.getName();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, "orderId");
        String returnImage = CommonUtils.getValue(paramMap, "returnImage");
        
        String requestId = logStashSign + "_" + orderId;
        LOG.info(requestId, methodDesc, userId);
        
        if(StringUtils.isAnyBlank(userId, orderId, returnImage)){
            return Response.fail("数据不能为空");
        }
        
        try {
            // 订单状态校验，每个订单只允许一次售后
            afterSaleService.orderRufundValidate(requestId, Long.valueOf(userId), orderId, null);
            
            afterSaleService.uploadReturnImage(requestId, userId, orderId, returnImage);
            return Response.success("售后商品图片上传成功!");
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("售后商品图片上传失败!");
        }
        
    }
    
    /**
     * 退换货
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/returngoods")
    public Response returnGoods(Map<String, Object> paramMap){
        
        String logStashSign = LogStashKey.AFTERSALE_RETURNGOODS.getValue();
        String methodDesc = LogStashKey.AFTERSALE_RETURNGOODS.getName();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, "orderId");
        String returnPrice = CommonUtils.getValue(paramMap, "returnPrice");
        String operate = CommonUtils.getValue(paramMap, "operate");     
        String reason = CommonUtils.getValue(paramMap, "reason");
        String content = CommonUtils.getValue(paramMap, "content");
        String returngoodsInfo = CommonUtils.getValue(paramMap, "returngoodsInfo");
        String imageNum = CommonUtils.getValue(paramMap, "imageNum");
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token  
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        try {
            
            int imageNumVal = Integer.parseInt(imageNum);
            BigDecimal returnPriceVal = new BigDecimal(returnPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            
            if (StringUtils.isBlank(userId)) {
                return Response.fail("用户名不能为空");
            }
            
            if(StringUtils.isBlank(orderId)){
                return Response.fail("订单编号不能为空");
            }
            
            if(!RefundReason.isLegal(reason)){
                return Response.fail("退换货原因不合法");
            }
            
            // Yes-1:换货  No-0:退货
            if(!YesNo.isLegal(operate)){
                return Response.fail("退换货操作不合法");
            }
            
            // 退货时校验退款金额
            if(operate.equals(YesNo.NO.getCode()) && StringUtils.isBlank(returnPrice)){
                return Response.fail("退款金额不能为空");
            }
            
            if(StringUtils.isBlank(returngoodsInfo)){
                return Response.fail("请先选择要退换货的商品");
            }
            
            if(imageNumVal > 3 || imageNumVal < 1){
                return Response.fail("请上传1~3张图片");
            }
            
            List<GoodsStockIdNumDto> returngoodsList = GsonUtils.convertList(returngoodsInfo, GoodsStockIdNumDto.class);
            
            if(null == returngoodsList || returngoodsList.isEmpty()){
                LOG.info(requestId, methodDesc, "未提交要退换货的商品数据");
                return Response.fail("请选择需要退换货的商品");
            }
            
            afterSaleService.returnGoods(requestId, userId, orderId, returnPriceVal, operate, reason, content, returngoodsList, imageNumVal);
            
            
            return Response.success("退换货成功");
            
        } catch(BusinessException e){
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("退换货操作失败,请联系客服!");
        }
    }
    
    /**
     * 查看进度、售后流程
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/progress")
    public Response viewProgress(Map<String, Object> paramMap){
        
        String logStashSign = LogStashKey.AFTERSALE_PROGRESS.getValue();
        String methodDesc = LogStashKey.AFTERSALE_PROGRESS.getName();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String orderId = CommonUtils.getValue(paramMap, "orderId");
    
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        if(StringUtils.isAnyBlank(userId, orderId)){
            return Response.fail("用户id、订单号不能为空");
        }
        
        try {
           
           resultMap  = afterSaleService.viewProgress(requestId, userId, orderId);
           
           return Response.successResponse(resultMap);
        } catch(BusinessException e){
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("售后进度查询失败,请稍后再试或联系客服!");
        }
       
    }
    
    /**
     * 提交退换货物流信息
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/submitLogistics")
    public Response submitLogistics(Map<String, Object> paramMap){
        
        String logStashSign = LogStashKey.AFTERSALE_SUBMITLOGISTICS.getValue();
        String methodDesc = LogStashKey.AFTERSALE_SUBMITLOGISTICS.getName();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String refundId = CommonUtils.getValue(paramMap, "refundId");
        String orderId = CommonUtils.getValue(paramMap, "orderId");
        String logisticsName = CommonUtils.getValue(paramMap, "logisticsName");
        String logisticsNo = CommonUtils.getValue(paramMap, "logisticsNo");
        
        String requestId = logStashSign + "_" + orderId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isAnyBlank(userId, refundId, orderId, logisticsName, logisticsNo)) {
            return Response.fail("用户id、订单编号、物流厂商、物流单号不能为空");
        }
        
        try {
            
            afterSaleService.submitLogisticsInfo(requestId, userId, refundId, orderId, logisticsName, logisticsNo);
            
            return Response.success("提交售后物流信息成功!");
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail("提交售后物流信息失败,请稍后再试或联系客服!");
        }
    }
    
}
