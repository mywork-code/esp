package com.apass.esp.web.address;

import java.util.HashMap;
import java.util.List;
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

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.enums.CityJdEnums;
import com.apass.esp.service.address.AddressService;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.google.common.collect.Maps;


@Path("/v1/address")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AddressInfoV1Controller {
  private static final Logger logger = LoggerFactory.getLogger(AddressInfoV1Controller.class);
  @Autowired
  private AddressService addressService;

  /**
   * <pre>
   * 新增地址信息
   * </pre>
   */
  @POST
  @Path("/addAddressInfo")
  public Response addAddressInfo(Map<String, Object> paramMap) {
    try {
      AddressInfoEntity addressInfoEntity = paramsToEntity(paramMap, true);
      Map<String, Object> resultMap = Maps.newHashMap();
      Long addressId = addressService.addAddressInfoV1(addressInfoEntity);
      resultMap.put("addressId", addressId.toString());
      return Response.successResponse(resultMap);
    } catch (BusinessException e) {
      logger.error("添加地址信息失败，错误原因", e);
      return Response.fail(e.getErrorDesc());
    } catch (Exception e) {
      logger.error("添加地址信息失败，错误原因", e);
      return Response.fail("添加地址信息失败！");
    }
  }

  /**
   * <pre>
   * 根据用户id查询地址信息列表
   * </pre>
   */
  @POST
  @Path("/queryAddressInfo")
  public Response queryAddressInfo(Map<String, Object> paramMap) {
    try {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      String userId = CommonUtils.getValue(paramMap, "userId");
      ValidateUtils.isNotBlank(userId, "用户Id为空！", BusinessErrorCode.PARAM_IS_EMPTY);
      List<AddressInfoEntity> addressInfoList = addressService.queryAddressInfo(Long.parseLong(userId));
      resultMap.put("addressInfoList", addressInfoList);
      return Response.success("查询地址信息成功！", resultMap);
    } catch (BusinessException e) {
      logger.error("查询地址信息失败，错误原因", e);
      return Response.fail(e.getErrorDesc());
    } catch (Exception e) {
      logger.error("查询地址信息失败，错误原因", e);
      return Response.fail("查询地址信息失败！");
    }
  }

  /**
   * <pre>
   * 根据用户id查询默认地址信息
   *
   * </pre>
   */
  @POST
  @Path("/queryDefaultByUserId")
  public Response queryAddressInfoByUserId(Map<String, Object> paramMap) {
    try {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      String userId = CommonUtils.getValue(paramMap, "userId");
      ValidateUtils.isNotBlank(userId, "用户Id为空！", BusinessErrorCode.PARAM_IS_EMPTY);
      AddressInfoEntity defaultAddress = addressService.queryDefaultByUserId(Long.parseLong(userId));
      resultMap.put("defaultAddress", defaultAddress);
      return Response.success("查询地址信息成功！", resultMap);
    } catch (BusinessException e) {
      logger.error("查询地址信息失败，错误原因", e);
      return Response.fail(e.getErrorDesc());
    } catch (Exception e) {
      logger.error("查询地址信息失败，错误原因", e);
      return Response.fail("查询默认地址信息失败！");
    }
  }

  /**
   * <pre>
   * 根据用户id查询默认地址信息
   *
   * 若当前用户存在默认地址，则返回默认地址信息;
   * 若当前用户有地址数据，但都没设置为默认地址，则随机返回第一条地址数据;
   * 若当前用户无地址数据，则返回null;
   *
   * </pre>
   */
  @POST
  @Path("/queryOneAddressByUserId")
  public Response queryOneAddressByUserId(Map<String, Object> paramMap) {
    try {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      String userId = CommonUtils.getValue(paramMap, "userId");
      ValidateUtils.isNotBlank(userId, "用户Id为空！", BusinessErrorCode.PARAM_IS_EMPTY);
      AddressInfoEntity defaultAddress = addressService.queryOneAddressByUserId(Long.parseLong(userId));
      resultMap.put("defaultAddress", defaultAddress);
      return Response.success("查询地址信息成功", resultMap);
    } catch (BusinessException e) {
      logger.error("查询地址信息失败，错误原因", e);
      return Response.fail(e.getErrorDesc());
    } catch (Exception e) {
      logger.error("查询地址信息失败，错误原因", e);
      return Response.fail("查询地址信息失败");
    }
  }

  /**
   * <pre>
   * 更新地址信息
   * </pre>
   */
  @POST
  @Path("/updateAddressInfo")
  public Response updateAddressInfo(Map<String, Object> paramMap) {
    try {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      AddressInfoEntity addInfo = paramsToEntity(paramMap, false);
      List<AddressInfoEntity> addressInfoList = addressService.updateAddressInfoV1(addInfo);
      resultMap.put("addressInfoList", addressInfoList);
      return Response.success("更新地址信息成功!", resultMap);
    } catch (BusinessException e) {
      logger.error(e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc());
    } catch (Exception e) {
      logger.error("更新地址信息失败，错误原因", e);
      return Response.fail("更新地址信息失败!");
    }
  }

  /**
   * <pre>
   * 设置默认地址信息
   * </pre>
   */
  @POST
  @Path("/setDefaultAddress")
  public Response defaultAddress(Map<String, Object> paramMap) {
    try {
      String addressId = CommonUtils.getValue(paramMap, "addressId");
      ValidateUtils.isNotBlank(addressId, "地址编号不能为空！");
      addressService.setDefaultAddress(addressId);
      return Response.success("设置默认地址信息成功!");
    } catch (BusinessException e) {
      logger.error(e.getErrorDesc(), e);
      return Response.fail(e.getErrorDesc());
    } catch (Exception e) {
      logger.error("设置默认地址信息失败，错误原因", e);
      return Response.fail("设置默认地址信息失败!");
    }
  }

  /**
   * <pre>
   * 删除地址信息
   * </pre>
   */
  @POST
  @Path("/deleteAddressInfo")
  public Response deleteAddressInfo(Map<String, Object> paramMap) {
    try {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      String userId = CommonUtils.getValue(paramMap, "userId");
      // 以逗号分隔的订单字符串
      String idStr = CommonUtils.getValue(paramMap, "idStr");
      ValidateUtils.isNotBlank(userId, "用户Id为空！", BusinessErrorCode.PARAM_IS_EMPTY);
      ValidateUtils.isNotBlank(idStr, "地址Id为空！", BusinessErrorCode.PARAM_IS_EMPTY);
      List<AddressInfoEntity> addressInfoList = addressService.deleteAddressInfo(
          Long.parseLong(userId), idStr.split(","));
      resultMap.put("addressInfoList", addressInfoList);
      return Response.success("删除地址信息成功!", resultMap);
    } catch (BusinessException e) {
      logger.error("删除地址信息失败，错误原因", e);
      return Response.fail(e.getErrorDesc(), e.getBusinessErrorCode());
    } catch (Exception e) {
      logger.error("删除地址信息失败，错误原因", e);
      return Response.fail("删除地址信息失败!");
    }
  }

  /**
   * 把map数据转为entity
   *
   * @return
   * @throws BusinessException
   */
  public AddressInfoEntity paramsToEntity(Map<String, Object> paramMap, boolean isSave)
      throws BusinessException {
    String userId = CommonUtils.getValue(paramMap, "userId");
    String province = CommonUtils.getValue(paramMap, "province"); // 省
    String city = CommonUtils.getValue(paramMap, "city"); // 市
    String district = CommonUtils.getValue(paramMap, "district"); // 区
    String towns = CommonUtils.getValue(paramMap, "towns"); // 乡镇
    String address = CommonUtils.getValue(paramMap, "address"); // 街道(详细地址)
    String postcode = CommonUtils.getValue(paramMap, "postcode"); // 邮编(暂未使用)
    String name = CommonUtils.getValue(paramMap, "name"); // 收货人姓名
    String telephone = CommonUtils.getValue(paramMap, "telephone"); // 收货人电话号码
    String isDefault = CommonUtils.getValue(paramMap, "isDefault"); // 是否是默认地址
    String id = CommonUtils.getValue(paramMap, "id"); // 地址id(表主键)
    // 验证参数
    validateParams(id, userId, province, city, district, towns, address, name, telephone, isDefault,
        isSave);
    // 封装参数
    AddressInfoEntity addressInfoEntity = new AddressInfoEntity();
    addressInfoEntity.setAddress(address);
    addressInfoEntity.setCity(city);
    addressInfoEntity.setDistrict(district);
    addressInfoEntity.setTowns(StringUtils.isBlank(towns) ? "" : towns);
    addressInfoEntity.setIsDefault(isDefault);
    addressInfoEntity.setName(name);
    addressInfoEntity.setPostcode(postcode);
    addressInfoEntity.setProvince(province);
    addressInfoEntity.setTelephone(telephone);
    addressInfoEntity.setUserId(Long.parseLong(userId));
    if (!isSave) {
      addressInfoEntity.setId(Long.valueOf(id));
    }
    return addressInfoEntity;
  }

  /**
   * 验证参数
   *
   * @param userId
   * @param province
   * @param city
   * @param district
   * @param towns
   * @param address
   * @param name
   * @param telephone
   * @param isDefault
   * @throws BusinessException
   */
  public void validateParams(String id, String userId, String province, String city, String district,
                             String towns, String address, String name, String telephone, String isDefault, boolean isSave)
      throws BusinessException {
    ValidateUtils.isNotBlank(userId, "用户编号不能为空！");
    ValidateUtils.isNotBlank(province, "请将收货地址所在区域填写完整！");
    ValidateUtils.isNotBlank(city, "请将收货地址所在区域填写完整！");
    ValidateUtils.isNotBlank(district, "请将收货地址所在区域填写完整！");
    /*if (!CityJdEnums.isContains(province)) {
      ValidateUtils.isNotBlank(towns, "请将收货地址所在区域填写完整！");
    }*/
    ValidateUtils.isNotBlank(address, "详细收货地址不能为空！");
    ValidateUtils.checkLength(address, 5, 80, "详细收货地址限5~80字！");
    ValidateUtils.isNotBlank(name, "收货人姓名不能为空！");
    ValidateUtils.checkLength(name, 1, 10, "收货人姓名输入不合法!");
    ValidateUtils.isNotBlank(telephone, "收货人联系电话不能为空！");
    ValidateUtils.checkLength(telephone, 1, 15, "收货人电话格式错误!");
    ValidateUtils.isNotBlank(isDefault, "是否默认地址不能为空！");
    if (!isSave) {
      ValidateUtils.isNotBlank(id, "地址编号不能为空！");
    }
  }
}
