package com.apass.esp.web.cart;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.cart.GoodsIsSelectDto;
import com.apass.esp.domain.dto.cart.GoodsStockIdNumDto;
import com.apass.esp.domain.dto.cart.ListCartDto;
import com.apass.esp.domain.entity.cart.GoodsInfoInCartEntity;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.service.cart.ShoppingCartService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/cart")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShoppingCartController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加商品到购物车
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/add")
    public Response addGoods(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.CART_ADD.getValue();
        String methodDesc = LogStashKey.CART_ADD.getName();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String goodsStockId = CommonUtils.getValue(paramMap, ParamsCode.GOODS_STOCK_ID);
        String count = CommonUtils.getValue(paramMap, ParamsCode.COUNT);

        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isAnyBlank(userId, goodsStockId, count)) {
        	logger.error("用户ID、商品库存ID、数量不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }

        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            
            shoppingCartService.addGoodsToCart(requestId, userId, goodsStockId, count);
            int goodsAmountInCart = shoppingCartService.getNumOfTypeInCart(userId);
            resultMap.put("goodsAmountInCart", goodsAmountInCart);

            return Response.success("商品添加到购物车成功",resultMap);

        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.GOODS_ADDTOCART_ERROR);
        }
    }

    /**
     * 查看购物车中商品
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/list")
    public Response cartInfoList(Map<String, Object> paramMap) {
        String logStashSign = LogStashKey.CART_LIST.getValue();
        String methodDesc = LogStashKey.CART_LIST.getName();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        
        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isBlank(userId)) {
        	logger.error("用户ID不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        
        try {
            
            Map<String, Object> resultMap = new HashMap<>();

//            List<GoodsInfoInCartEntity> goodsInfoInCartList = shoppingCartService.getGoodsInfoInCart(requestId, userId);
            List<ListCartDto> listCartDtos = shoppingCartService.getGoodsInfoInCart(requestId, userId);

            int goodsAmountInCart = shoppingCartService.getNumOfTypeInCart(userId);

//            resultMap.put("goodsInfoInCartList", goodsInfoInCartList);
            resultMap.put("goodsInfoInCartList", listCartDtos);
            resultMap.put("goodsAmountInCart", goodsAmountInCart);

            return Response.success("查看购物车中商品成功", resultMap);

        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
        }
    }
    
    /**
     * 查看购物车中商品
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/listCart")
    @Deprecated
    public Response listCart(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.CART_LIST.getValue();
        String methodDesc = LogStashKey.CART_LIST.getName();

        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        
        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

        if (StringUtils.isBlank(userId)) {
        	logger.error("用户ID不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {

            List<ListCartDto> cartDtoList = shoppingCartService.getCartDtoList(requestId, userId);

            int goodsAmountInCart = shoppingCartService.getNumOfTypeInCart(userId);

            resultMap.put("cartDtoList", cartDtoList);
            resultMap.put("goodsAmountInCart", goodsAmountInCart);
            resultMap.put("postage", "0");//电商3期511  添加邮费字段（当邮费为0时显示免运费） 20170517
            return Response.success("查看购物车中商品成功", resultMap);

        } catch (BusinessException e) {
            logger.error(e.getErrorDesc(), e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
        } catch (Exception e) {
            logger.error("查看购物车中商品失败:", e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
        }
    }

    /**
     * 修改商品购买数量
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/setAmount")
    @Deprecated
    public Response setAmount(Map<String, Object> paramMap) {

        try {
            String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
            String goodsStockId = CommonUtils.getValue(paramMap, ParamsCode.GOODS_STOCK_ID);
            String count = CommonUtils.getValue(paramMap, ParamsCode.COUNT);

            if (StringUtils.isAnyBlank(userId, goodsStockId, count)) {
            	logger.error("用户ID、商品库存ID、数量不能为空");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }

            shoppingCartService.setGoodsAmount(userId, goodsStockId, count);

            return Response.success("商品数量修改成功");
        } catch (Exception e) {
            logger.error("商品数量修改失败:", e);
            return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
        }
    }

    /**
     * 删除购物车中商品
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/deleteGoods")
    public Response deleteGoods(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.CART_DELETE.getValue();
        String methodDesc = LogStashKey.CART_DELETE.getName();

        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String goodsStockIdStr = CommonUtils.getValue(paramMap, "goodsStockIdStr");

        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isAnyBlank(userId, goodsStockIdStr)) {
            return Response.fail("用户ID、商品库存ID不能为空");
        }
        
        try {
            
            shoppingCartService.deleteGoodsInCart(requestId, Long.valueOf(userId), goodsStockIdStr.split(","));
            return Response.success("删除购物车中商品成功");
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.DELETE_INFO_FAILED);
        }
    }

    /**
     * 购物车完成编辑时，更新客户端购物车最新商品数量信息到数据库;
     * 校验商品库存数据是否充足，商品是否已下架
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/synCartInfo")
    public Response synchronizeCartInfo(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.CART_UPDATE.getValue();
        String methodDesc = LogStashKey.CART_UPDATE.getName();

        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String goodsInfoStr = CommonUtils.getValue(paramMap, "goodsInfoStr");

        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));
        
        if (StringUtils.isBlank(userId)) {
        	logger.error("用户ID不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {

            List<GoodsStockIdNumDto> goodsInfoList = GsonUtils.convertList(goodsInfoStr, GoodsStockIdNumDto.class);

            if(null != goodsInfoList && !goodsInfoList.isEmpty()){
                resultMap = shoppingCartService.updateGoodsNumInCart(requestId, userId, goodsInfoList);
            }

            return Response.success("修改购物车商品数量成功", resultMap);

        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
        }

    }

    /**
     * 查看商品规格
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/viewSku")
    public Response viewSku(Map<String, Object> paramMap) {

        String logStashSign = LogStashKey.CART_VIEWSKU.getValue();
        String methodDesc = LogStashKey.CART_VIEWSKU.getName();
        
        String goodsId = CommonUtils.getValue(paramMap, "goodsId");
        
        String requestId = logStashSign + "_" + goodsId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

        if (StringUtils.isBlank(goodsId)) {
        	logger.error("商品id不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            resultMap = shoppingCartService.getGoodsStockSkuInfo(requestId, goodsId);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.DETAIL_INFO_FAILED);
        }

        return Response.successResponse(resultMap);

    }

    /**
     * 修改商品规格
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/reelectSku")
    public Response reelectSku(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.CART_REELECTSKU.getValue();
        String methodDesc = LogStashKey.CART_REELECTSKU.getName();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String goodsId = CommonUtils.getValue(paramMap, "goodsId");//当是京东商品时，goodsId为修改后商品的
        String preGoodsStockId = CommonUtils.getValue(paramMap, "preGoodsStockId");
        String secGoodsStockId = CommonUtils.getValue(paramMap, "secGoodsStockId");
        String num = CommonUtils.getValue(paramMap, "num");
        
        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

        if (StringUtils.isAnyBlank(userId, goodsId, preGoodsStockId, secGoodsStockId, num)) {
        	logger.error("用户id、商品id、商品库存id、商品购买数量不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        
        if(StringUtils.equals(preGoodsStockId, secGoodsStockId)){
        	logger.error("商品规格未修改,本次提交无效");
            return Response.fail(BusinessErrorCode.ADD_INFO_INVALID);
        }

        try {
            GoodsInfoInCartEntity goodsInfoInCart = shoppingCartService.reelectSku(requestId, userId, goodsId, preGoodsStockId, secGoodsStockId, num);

            resultMap.put("goodsInfoInCart", goodsInfoInCart);
            return Response.success("修改商品规格成功", resultMap);
        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getBusinessErrorCode());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
        }

    }
    
    /**
     * 同步 购物车商品 勾选标记
     * 
     * @param paramMap
     * @return
     */
    @POST
    @Path("/isSelect")
    public Response selectFlag(Map<String, Object> paramMap) {
        
        String logStashSign = LogStashKey.CART_ISSELECT.getValue();
        String methodDesc = LogStashKey.CART_ISSELECT.getName();
        
        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String isSelectInfo = CommonUtils.getValue(paramMap, "isSelectInfo");
        
        String requestId = logStashSign + "_" + userId;
        paramMap.remove("x-auth-token"); //输出日志前删除会话token
        LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

        if (StringUtils.isBlank(userId)) {
        	logger.error("用户ID不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        
        if (StringUtils.isBlank(isSelectInfo)) {
        	logger.error("商品信息不能为空");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }

        try {

            List<GoodsIsSelectDto> goodsIsSelectList = GsonUtils.convertList(isSelectInfo, GoodsIsSelectDto.class);
            
            if(null != goodsIsSelectList && !goodsIsSelectList.isEmpty()){
                shoppingCartService.synIsSelect(requestId, userId, goodsIsSelectList);
            } else {
                LOG.info(requestId, methodDesc, "同步购物车商品勾选标记数据为空");
            }

            return Response.success("同步购物车商品勾选标记成功");

        } catch (BusinessException e) {
            LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
        } catch (Exception e) {
            LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
            return Response.fail(BusinessErrorCode.SYN_CART_FAILED);
        }
    }

}
