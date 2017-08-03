package com.apass.esp.web.order;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.cart.PurchaseRequestDto;
import com.apass.esp.domain.dto.goods.GoodsInfoInOrderDto;
import com.apass.esp.domain.dto.order.OrderDetailInfoDto;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.domain.enums.CashRefundVoStatus;
import com.apass.esp.domain.enums.CityEnums;
import com.apass.esp.domain.enums.DeviceType;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.vo.LogisticsFirstDataVo;
import com.apass.esp.service.TxnInfoService;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.address.AddressService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.logistics.LogisticsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class OrderInfoController {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoController.class);

  @Autowired
  public OrderService orderService;

  @Autowired
  public AwardActivityInfoService awardActivityInfoService;

  @Autowired
  public AwardBindRelService awardBindRelService;

  @Autowired
  public AwardDetailService awardDetailService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private CashRefundService cashRefundService;

  @Autowired
  private LogisticsService logisticsService;

  @Autowired
  private TxnInfoService txnInfoService;
  
  @Autowired
  private AddressService addressService;

  private static final String ORDER_ID = "orderId";

  /**
   * 下单
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/confirmOrder")
  public Response confirmOrder(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_GENERATE.getValue();
    String methodDesc = LogStashKey.ORDER_GENERATE.getName();

    String userIdStr = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
    String totalPaymentStr = CommonUtils.getValue(paramMap, "totalPayment"); // 订单总金额
    String addressIdStr = CommonUtils.getValue(paramMap, "addressId"); // 收货地址Id
    String buyInfo = CommonUtils.getValue(paramMap, "buyInfo"); // 购买商品列表

    String sourceFlag = CommonUtils.getValue(paramMap, "sourceFlag"); // 订单来源标记[购物车
    // 或
    // 直接购买]
    String deviceType = CommonUtils.getValue(paramMap, "deviceType");//订单渠道
    String requestId = logStashSign + "_" + userIdStr;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    Map<String, Object> resultMap = Maps.newHashMap();
    try {

      BigDecimal totalPayment = null;
      if (null == userIdStr) {
        LOGGER.error("对不起!用户号不能为空");
        return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
      }
      if (!StringUtils.isNumeric(userIdStr)) {
        LOGGER.error("用户名传入非法!");
        return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
      }
      if (StringUtils.isEmpty(deviceType)) {
        deviceType = "android";
      }
      if (!deviceType.equalsIgnoreCase(DeviceType.ANDROID.getName()) && !deviceType.equalsIgnoreCase(DeviceType.IOS.getName())) {
        LOGGER.error("下单渠道参数错误");
        return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
      }
      Long userId = Long.valueOf(userIdStr);
      if (null == addressIdStr) {
        LOGGER.error("用户收货地址不能为空");
        return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
      }
      if (!StringUtils.isNumeric(addressIdStr)) {
        LOGGER.error("地址信息传入非法!");
        return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
      }
      Long addressId = Long.valueOf(addressIdStr);
      if (StringUtils.isEmpty(buyInfo)) {
        LOGGER.error("购买商品信息不能为空!");
        return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
      }
      if (StringUtils.isEmpty(totalPaymentStr)) {
        LOGGER.error("购买商品总金额不能为空!");
        return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
      } else {
        totalPayment = new BigDecimal(totalPaymentStr);
      }
      List<PurchaseRequestDto> purchaseList = GsonUtils.convertList(buyInfo, PurchaseRequestDto.class);
      if (purchaseList.isEmpty()) {
        LOGGER.error("请选择所购买的商品");
        return Response.fail(BusinessErrorCode.PARAM_CONVERT_ERROR);
      }
      /**
       * 如果根据addressId ,获取地址详细信息，如果存在京东商品，则towns必填
       */
      resultMap.putAll(addressService.validateJdGoods(purchaseList, addressId));
      /**
       * 查询商品是否存在不支持配送区域
       */
      if(resultMap.isEmpty()){
    	  resultMap.putAll(orderService.validateGoodsUnSupportProvince(requestId, addressId, purchaseList));
          //如果map为空，则说明订单下，不存在不支持配送的区域
          if(resultMap.isEmpty()){
         		 List<String> orders = orderService.confirmOrder(requestId, userId, totalPayment, addressId,
         			        purchaseList, sourceFlag, deviceType);
         			     resultMap.put("orderList", orders);
          }
      }
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      return Response.fail(BusinessErrorCode.ORDER_CREATE_ERROR);
    }
    return Response.success("订单生成成功!", resultMap);
  }

  /**
   * 取消订单
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/cancelOrder")
  public Response cancelOrder(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_CANCEL.getValue();
    String methodDesc = LogStashKey.ORDER_CANCEL.getName();

    Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
    String orderId = CommonUtils.getValue(paramMap, ORDER_ID); // 订单号

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (null == userId) {
      LOGGER.error("对不起!用户号不能为空");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    try {

      orderService.cancelOrder(requestId, userId, orderId);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("订单取消失败!请稍后再试");
      return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
    }
    return Response.success("取消订单成功!");
  }

  /**
   * 延迟收货
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/deleyReceivedGoods")
  public Response deleyReceiveGoods(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_DELAY.getValue();
    String methodDesc = LogStashKey.ORDER_DELAY.getName();

    Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (null == userId) {
      LOGGER.error("登录失效请重新登录!");
      return Response.fail(BusinessErrorCode.LOGIN_HAS_INVALID);
    }
    if (StringUtils.isEmpty(orderId)) {
      LOGGER.error("请求订单信息不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    try {
      orderService.deleyReceiveGoods(requestId, userId, orderId);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("延迟收货失败!请稍后再试");
      return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
    }
    return Response.success("延迟收货成功!");
  }

  /**
   * 确认收货
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/confirmReceiveGoods")
  public Response confirmReceiveGoods(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_CONFIRM_RECEIVE.getValue();
    String methodDesc = LogStashKey.ORDER_CONFIRM_RECEIVE.getName();

    Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    OrderDetailInfoDto dto = null;
    if (null == userId) {
      LOGGER.error("登录失效请重新登录!");
      return Response.fail(BusinessErrorCode.LOGIN_HAS_INVALID);
    }
    if (StringUtils.isEmpty(orderId)) {
      LOGGER.error("请求订单信息不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    try {
      orderService.confirmReceiveGoods(requestId, userId, orderId);
      dto = orderService.getOrderDetailInfoDto(requestId, orderId);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("确认收货失败!请稍后再试");
      return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
    }

    return Response.success("确认收货成功!", dto);
  }

  /**
   * 删除订单
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/deleteOrderInfo")
  public Response deleteOrderInfo(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_DELETE.getValue();
    String methodDesc = LogStashKey.ORDER_DELETE.getName();

    Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (null == userId) {
      LOGGER.error("用户号不能为空!");
      return Response.fail(BusinessErrorCode.LOGIN_HAS_INVALID);
    }
    if (StringUtils.isEmpty(orderId)) {
      LOGGER.error("请求订单信息不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    try {

      orderService.deleteOrderInfo(requestId, userId, orderId);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("删除订单失败!请稍后再试");
      return Response.fail(BusinessErrorCode.DELETE_INFO_FAILED);
    }
    return Response.success("删除订单成功!");
  }

  /**
   * 我的订单-重新下单
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/repeatConfirmOrder")
  public Response repeatConfirmOrder(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_REPEAT_CONFIRM.getValue();
    String methodDesc = LogStashKey.ORDER_REPEAT_CONFIRM.getName();

    Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);
    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (null == userId) {
      LOGGER.error("用户号不能为空!");
      return Response.fail(BusinessErrorCode.LOGIN_HAS_INVALID);
    }
    if (StringUtils.isEmpty(orderId)) {
      LOGGER.error("重新下单该笔订单号不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      orderService.repeatConfirmOrder(requestId, userId, orderId, resultMap);
      return Response.success("重新下单初始化成功", resultMap);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("重新下单初始化失败");
      return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
    }
  }

  /**
   * 重新下单添加至购物车
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/reOrder/addShopCart")
  public Response reOrder(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_CONFIRM_FAIL_ADDCART.getValue();
    String methodDesc = LogStashKey.ORDER_CONFIRM_FAIL_ADDCART.getName();

    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
    Long userId = CommonUtils.getLong(paramMap, ParamsCode.USER_ID);

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (StringUtils.isEmpty(orderId)) {
      LOGGER.error("重新下单订单号不能为空");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }
    if (null == userId) {
      LOGGER.error("登录失效请重新登录!");
      return Response.fail(BusinessErrorCode.LOGIN_HAS_INVALID);
    }

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      orderService.reOrder(requestId, orderId, userId);
      return Response.success("重新下单添加至购物车成功", resultMap);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("重新下单添加至购物车失败");
      return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
    }
  }

  /**
   * 修改订单收货地址
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/modifyShippingAddress")
  public Response modifyShippingAddress(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_NOPAY_MODIFYADDRESS.getValue();
    String methodDesc = LogStashKey.ORDER_NOPAY_MODIFYADDRESS.getName();

    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
    Long userId = CommonUtils.getLong(paramMap, "userId");
    Long addressId = CommonUtils.getLong(paramMap, "addressId");

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (StringUtils.isEmpty(orderId)) {
      LOGGER.error("请求订单信息不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }
    if (null == addressId) {
      LOGGER.error("请求地址信息不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }
    if (null == userId) {
      LOGGER.error("请求地址信息不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    try {

      orderService.modifyShippingAddress(requestId, addressId, orderId, userId);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("修改订单收货地址失败!请稍后再试");
      return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
    }
    return Response.success("修改订单收货地址成功!");
  }

  /**
   * 根据 userId、订单状态(可选) 查询订单信息
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/queryOrderInfo")
  public Response queryOrderInfo(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_QUERY.getValue();
    String methodDesc = LogStashKey.ORDER_QUERY.getName();

    String userId = CommonUtils.getValue(paramMap, "userId");
    String statusStr = CommonUtils.getValue(paramMap, "statusStr");

    String requestId = logStashSign + "_" + userId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (StringUtils.isBlank(userId)) {
      LOGGER.error("对不起!用户号不能为空");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      List<OrderDetailInfoDto> resultList = orderService.getOrderDetailInfo(requestId, userId,
          statusStr);
      //如果订单的状态为待发货，应该把订单的状态为退款中的并到待发货中
      if (StringUtils.isNotBlank(statusStr) && StringUtils.equals(statusStr, OrderStatus.ORDER_PAYED.getCode())) {
        List<OrderDetailInfoDto> dtoList = orderService.getOrderDetailInfo(requestId, userId,
            OrderStatus.ORDER_REFUNDPROCESSING.getCode());
        if (!CollectionUtils.isEmpty(dtoList)) {
          if (CollectionUtils.isEmpty(resultList)) {
            resultList = dtoList;
          } else {
            resultList.addAll(dtoList);
          }

        }
      }

      for (OrderDetailInfoDto order : resultList) {
        if (StringUtils.equals(order.getStatus(), OrderStatus.ORDER_REFUNDPROCESSING.getCode())) {
          order.setStatus(OrderStatus.ORDER_PAYED.getCode());
        }
      }


      //添加新的图片地址
      for (OrderDetailInfoDto list : resultList) {
        String province = list.getProvince();
        if (StringUtils.isNotEmpty(province)) {
          if (province.equals(CityEnums.BEIJING.getName()) || province.equals(CityEnums.TIANJIN.getName()) || province.equals(CityEnums.CHONGQING.getName()) || province.equals(CityEnums.SHANGHAI.getName())) {
            list.setProvince("");
          }
        }
        List<GoodsInfoInOrderDto> goodsInfoInOrderDtoList = list.getOrderDetailInfoList();
        for (GoodsInfoInOrderDto l : goodsInfoInOrderDtoList) {
          if (StringUtils.isNoneEmpty(l.getGoodsLogoUrl())) {
            if(StringUtils.isNotEmpty(list.getSource())){
              l.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/"+l.getGoodsLogoUrl());
            }else{
              l.setGoodsLogoUrlNew(imageService.getImageUrl(EncodeUtils.base64Decode(l.getGoodsLogoUrl())));
            }
          }
        }
      }
      resultMap.put("orderInfoList", resultList);
      resultMap.put("postage", "0");//电商3期511  添加邮费字段（当邮费为0时显示免运费） 20170517
      return Response.success("操作成功", resultMap);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("订单查询失败,请稍后再试或联系客服!");
      return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
    }
  }

  /**
   * 查询订单的详情  （前天由之前的列表页面带入参数，改为向后台发出请求）
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/v1/queryOrderByOrderId")
  public Response queryOrderInfoById(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_QUERY.getValue();
    String methodDesc = LogStashKey.ORDER_QUERY.getName();

    String orderId = CommonUtils.getValue(paramMap, "orderId");
    String statusStr = CommonUtils.getValue(paramMap, "statusStr");

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (StringUtils.isBlank(orderId)) {
      LOGGER.error("对不起!订单号不能为空");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {
      //根据订单的id和状态，获取订单的详细信息
      List<OrderDetailInfoDto> resultList = orderService.getOrderDetailInfoByOrderId(requestId, orderId,
          statusStr);
      //添加新的图片地址
      for (OrderDetailInfoDto list : resultList) {
        String province = list.getProvince();
        if (StringUtils.isNotEmpty(province)) {
          if (province.equals(CityEnums.BEIJING.getName()) || province.equals(CityEnums.TIANJIN.getName()) || province.equals(CityEnums.CHONGQING.getName()) || province.equals(CityEnums.SHANGHAI.getName())) {
            list.setProvince("");
          }
        }
        List<GoodsInfoInOrderDto> goodsInfoInOrderDtoList = list.getOrderDetailInfoList();
        for (GoodsInfoInOrderDto l : goodsInfoInOrderDtoList) {
          if (StringUtils.isNoneEmpty(l.getGoodsLogoUrl())) {
            if(!StringUtils.isEmpty(list.getSource())){
              l.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/"+l.getGoodsLogoUrl());
            }else{
              l.setGoodsLogoUrlNew(imageService.getImageUrl(EncodeUtils.base64Decode(l.getGoodsLogoUrl())));
            }
          }
        }
      }

      /**
       * 如果订单的状态是D02(待发货的状态)
       */
      OrderDetailInfoDto dto = null;

      if (!CollectionUtils.isEmpty(resultList)) {
        dto = resultList.get(0);
        dto.setOrderCreateDateStr(DateFormatUtil.dateToString(dto.getOrderCreateDate(), DateFormatUtil.YYYY_MM_DD_HH_MM));
        //如果订单为退款中的状态，则把订单的状态手动改成待发货状态
        if (StringUtils.equals(dto.getStatus(), OrderStatus.ORDER_REFUNDPROCESSING.getCode())) {
          dto.setStatus(OrderStatus.ORDER_PAYED.getCode());
        }

        if (StringUtils.equals(dto.getStatus(), OrderStatus.ORDER_PAYED.getCode())) {
          //根据订单的Id,查询退款的申请记录，如果无记录，则页面显示退款按钮
          CashRefundDto cash = cashRefundService.getCashRefundByOrderId(orderId);
          if (cash != null) {
            if (cash.getStatus() == Integer.parseInt(CashRefundStatus.CASHREFUND_STATUS1.getCode())) {
              if (DateFormatUtil.isExpired(cash.getCreateDate(), 1)) {
//                                if(DateFormatUtil.addDMinutes(cash.getCreateDate(),2).before(new Date())){
                //更新数据库字段  恢复额度
                cashRefundService.agreeRefund(cash.getUserId() + "", orderId);
              }
            }
            dto.setCashRefundStatus(cashRefundService.getCashRundStatus(orderId));
          }else {
            //判断用户是否进行主动还款，如果是 则【退款】按钮不显示
            if (txnInfoService.isActiveRepayForConsumableCredit(dto.getUserId(), dto.getMainOrderId())) {
              dto.setCashRefundStatus(CashRefundVoStatus.CASHREFUND_OTHER.getCode());
            } else {
              //根据返回结果，判断页面要显示的按钮
              dto.setCashRefundStatus(cashRefundService.getCashRundStatus(orderId));
            }
          }

        }
      }

      //根据订单获取物流的信息
      LogisticsFirstDataVo logisticInfo = logisticsService.loadFristLogisticInfo(orderId);

      resultMap.put("orderInfoList", dto);
      resultMap.put("postage", "0");//电商3期511  添加邮费字段（当邮费为0时显示免运费） 20170517
      resultMap.put("logisticInfo", logisticInfo);
      return Response.success("操作成功", resultMap);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("订单查询失败,请稍后再试或联系客服!");
      return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
    }
  }

  /**
   * 查询订单收货地址及签收信息
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/loadByOrderId")
  public Response loadByOrderId(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_LOADADDR.getValue();
    String methodDesc = LogStashKey.ORDER_LOADADDR.getName();

    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (StringUtils.isBlank(orderId)) {
      LOGGER.error("订单号不能为空");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    Map<String, Object> resultMap = new HashMap<String, Object>();
    try {
      orderService.loadInfoByOrderId(requestId, resultMap, orderId);
      return Response.success("查询地址信息成功", resultMap);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("查询订单失败请稍后再试");
      return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
    }
  }

  /**
   * 查询订单详情[根据订单号查询订单明细]
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/queryOrderDetailInfo")
  public Response queryOrderDetailInfo(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_QUERY_DETAIL.getValue();
    String methodDesc = LogStashKey.ORDER_QUERY_DETAIL.getName();

    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (StringUtils.isBlank(orderId)) {
      LOGGER.error("订单号不能为空");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    Map<String, Object> resultMap = new HashMap<String, Object>();

    try {

      List<OrderDetailInfoEntity> orderDetails = orderService.queryOrderDetailInfo(requestId, orderId);
      resultMap.put("orderInfoList", orderDetails);
      return Response.success("查询订单明细成功", resultMap);
    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("订单明细查询失败");
      return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
    }
  }

  /**
   * 查询 用户 待付款、待发货、待收货 订单数量
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/getWaitedOrderNum")
  public Response getWaitedOrderNum(Map<String, Object> paramMap) {
    try {
      Map<String, String> resultMap = new HashMap<String, String>();
      String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
      if (StringUtils.isBlank(userId)) {
        LOGGER.error("用户id不能为空");
        return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
      }
      resultMap = orderService.getOrderNum(userId);
      return Response.success("订单数量查询成功", resultMap);
    } catch (Exception e) {
      LOGGER.error("订单数量查询失败", e);
      return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
    }
  }

  /**
   * 修改待付款订单收货地址
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/modifyOrderAddress")
  public Response modifyOrderAddress(Map<String, Object> paramMap) {

    String logStashSign = LogStashKey.ORDER_QUERY_DETAIL.getValue();
    String methodDesc = LogStashKey.ORDER_QUERY_DETAIL.getName();

    String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
    String userId = CommonUtils.getValue(paramMap, "userId");
    String name = CommonUtils.getValue(paramMap, "name");
    String telephone = CommonUtils.getValue(paramMap, "telephone");
    String province = CommonUtils.getValue(paramMap, "province");
    String city = CommonUtils.getValue(paramMap, "city");
    String district = CommonUtils.getValue(paramMap, "district");
    String address = CommonUtils.getValue(paramMap, "address");

    String requestId = logStashSign + "_" + orderId;
    paramMap.remove("x-auth-token"); // 输出日志前删除会话token
    LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

    if (StringUtils.isBlank(orderId)) {
      LOGGER.error("订单号不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    if (StringUtils.isBlank(userId)) {
      LOGGER.error("用户id不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }

    if (StringUtils.isAnyBlank(name, telephone, city, district, address)) {
      LOGGER.error("地址信息字段不能为空!");
      return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
    }
    if (StringUtils.isEmpty(province)) {
      province = city;
    }

    try {

      AddressInfoEntity addressInfoDto = new AddressInfoEntity();
      addressInfoDto.setUserId(Long.valueOf(userId));
      addressInfoDto.setName(name);
      addressInfoDto.setTelephone(telephone);
      addressInfoDto.setProvince(province);
      addressInfoDto.setCity(city);
      addressInfoDto.setDistrict(district);
      addressInfoDto.setAddress(address);
      addressInfoDto.setIsDefault("0");
      addressInfoDto.setProvinceCode("");
      addressInfoDto.setCityCode("");
      addressInfoDto.setDistrictCode("");
      addressInfoDto.setTownsCode("");
      addressInfoDto.setTowns("");

      orderService.modifyOrderAddress(requestId, orderId, userId, addressInfoDto);

    } catch (BusinessException e) {
      LOG.logstashException(requestId, methodDesc, e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
      LOGGER.error("修改订单收货地址失败,请稍后再试或联系客服");
      return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
    }
    return Response.success("修改订单收货地址成功!");
  }

  /**
   * 待付款页付款库存不足或商品下架时 删除订单加入购物车
   *
   * @param paramMap
   * @return
   */
  @POST
  @Path("/payAfterFail")
  public Response payAfterFail(Map<String, Object> paramMap) {
    try {
      String orderId = CommonUtils.getValue(paramMap, ORDER_ID);
      String userIdStr = CommonUtils.getValue(paramMap, "userId");

      if (StringUtils.isBlank(orderId)) {
        LOGGER.error("订单号不能为空!");
        return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
      }
      if (StringUtils.isBlank(userIdStr)) {
        LOGGER.error("用户号不能为空!");
        return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
      }
      if (!StringUtils.isNumeric(userIdStr)) {
        LOGGER.error("用户名传入非法!");
        return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
      }
      Long userId = Long.valueOf(userIdStr);
      orderService.payAfterFail(orderId, userId);

    } catch (BusinessException e) {
      LOGGER.error(e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      LOGGER.error("商品信息加入购物车失败", e);
      return Response.fail(BusinessErrorCode.GOODS_ADDTOCART_ERROR);
    }
    return Response.success("支付失败加入购物车成功");
  }

}
