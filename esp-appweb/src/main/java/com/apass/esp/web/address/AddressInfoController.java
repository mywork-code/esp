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
import com.apass.esp.service.address.AddressService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

@Path("/address")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AddressInfoController {

    private static final Logger logger = LoggerFactory.getLogger(AddressInfoController.class);
    
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
			String userId = CommonUtils.getValue(paramMap, "userId");
			
			String province = CommonUtils.getValue(paramMap, "province");    //省
			String city = CommonUtils.getValue(paramMap, "city");            //市
			String district = CommonUtils.getValue(paramMap, "district");    //区
			String address = CommonUtils.getValue(paramMap, "address");      //街道(详细地址)
			String postcode = CommonUtils.getValue(paramMap, "postcode");    //邮编(暂未使用)
			String name = CommonUtils.getValue(paramMap, "name");            //收货人姓名
			String telephone = CommonUtils.getValue(paramMap, "telephone");  //收货人电话号码
			String isDefault = CommonUtils.getValue(paramMap, "isDefault");  //是否是默认地址
						
			if (StringUtils.isAnyBlank(userId, province, city, district, address, name, telephone, isDefault)) {
				logger.error("地址信息字段不能为空！");
				return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
			}
			
			if (!RegExpUtils.length(name, 4, 16)) {
				logger.error("收货人姓名输入不合法");
                return Response.fail(BusinessErrorCode.PARAM_FORMAT_ERROR);
            }
			
			if (telephone.length() > 15){
				logger.error("收货人电话输入不合法");
			    return Response.fail(BusinessErrorCode.PARAM_FORMAT_ERROR);
			}
			
			if (address.length() < 5 || address.length() > 80) {
				logger.error("详细收货地址限5~80字");
                return Response.fail(BusinessErrorCode.PARAM_FORMAT_ERROR);
            }
			
			
			AddressInfoEntity addressInfoEntity=new AddressInfoEntity();
			addressInfoEntity.setAddress(address);
			addressInfoEntity.setCity(city);
			addressInfoEntity.setDistrict(district);
			addressInfoEntity.setIsDefault(isDefault);
			addressInfoEntity.setName(name);
			addressInfoEntity.setPostcode(postcode);
			addressInfoEntity.setProvince(province);
			addressInfoEntity.setTelephone(telephone);
			addressInfoEntity.setUserId(Long.parseLong(userId));
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			Long addressId = addressService.addAddressInfo(addressInfoEntity);
			
			resultMap.put("addressId", addressId.toString());
			return Response.successResponse(resultMap);
		} catch (Exception e) {
		    logger.error("添加地址信息失败，错误原因", e);
			return Response.fail(BusinessErrorCode.ADD_INFO_FAILED);
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
			
			if (StringUtils.isBlank(userId)) {
				logger.error("用户Id为空！");
				return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
			}
			
			List<AddressInfoEntity> addressInfoList = addressService.queryAddressInfo(Long.parseLong(userId));
			resultMap.put("addressInfoList", addressInfoList);
			return Response.success("查询地址信息成功", resultMap);
		} catch (Exception e) {
		    logger.error("查询地址信息失败，错误原因", e);
			return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
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
            
            if (StringUtils.isBlank(userId)) {
            	logger.error("用户Id为空！");
            	return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            
            AddressInfoEntity defaultAddress = addressService.queryDefaultByUserId(Long.parseLong(userId));
            resultMap.put("defaultAddress", defaultAddress);
            return Response.success("查询地址信息成功", resultMap);
        } catch (Exception e) {
            logger.error("查询地址信息失败，错误原因", e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
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
            
            if (StringUtils.isBlank(userId)) {
            	logger.error("用户Id为空！");
                return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
            }
            
            AddressInfoEntity defaultAddress = addressService.queryOneAddressByUserId(Long.parseLong(userId));
            
            resultMap.put("defaultAddress", defaultAddress);
            return Response.success("查询地址信息成功", resultMap);
        } catch (Exception e) {
            logger.error("查询地址信息失败，错误原因", e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
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
			
			String userId = CommonUtils.getValue(paramMap, "userId");        //用户id
			String id = CommonUtils.getValue(paramMap, "id");                //地址id(表主键)
            String province = CommonUtils.getValue(paramMap, "province");    //省
            String city = CommonUtils.getValue(paramMap, "city");            //市
            String district = CommonUtils.getValue(paramMap, "district");    //区
            String address = CommonUtils.getValue(paramMap, "address");      //街道(详细地址)
            String postcode = CommonUtils.getValue(paramMap, "postcode");    //邮编(暂未使用)
            String name = CommonUtils.getValue(paramMap, "name");            //收货人姓名
            String telephone = CommonUtils.getValue(paramMap, "telephone");  //收货人电话号码
            String isDefault = CommonUtils.getValue(paramMap, "isDefault");  //是否是默认地址

			if (StringUtils.isAnyBlank(userId, id)) {
				logger.error("地址信息字段不能为空！");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            
            if (null != name && !RegExpUtils.length(name, 4, 16)) {
            	logger.error("收货人姓名输入不合法");
                return Response.fail(BusinessErrorCode.PARAM_FORMAT_ERROR);
            }
            
            if (null != telephone && telephone.length() > 15){
            	logger.error("收货人电话输入不合法");
                return Response.fail(BusinessErrorCode.PARAM_FORMAT_ERROR);
            }
            
            if (null != address && (address.length() < 5 || address.length() > 80)) {
            	logger.error("详细收货地址限5~80字");
                return Response.fail(BusinessErrorCode.PARAM_FORMAT_ERROR);
            }
			
			AddressInfoEntity addInfo=new AddressInfoEntity();
			addInfo.setId(Long.valueOf(id));
			addInfo.setAddress(address);
			addInfo.setCity(city);
			addInfo.setDistrict(district);
			addInfo.setIsDefault(isDefault);
			addInfo.setName(name);
			addInfo.setPostcode(postcode);
			addInfo.setProvince(province);
			addInfo.setTelephone(telephone);
			addInfo.setUserId(Long.valueOf(userId));
            
			List<AddressInfoEntity> addressInfoList = addressService.updateAddressInfo(addInfo);
			resultMap.put("addressInfoList", addressInfoList);
			return Response.success("更新地址信息成功", resultMap);
		} catch (BusinessException e) {
            logger.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            logger.error("更新地址信息失败，错误原因", e);
			return Response.fail(BusinessErrorCode.ADDRESS_UPDATE_FAILED);
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
			//以逗号分隔的订单字符串
			String idStr = CommonUtils.getValue(paramMap, "idStr");
			
			if (StringUtils.isBlank(userId)) {
				logger.error("用户Id为空！");
				return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
			}
			if (StringUtils.isBlank(idStr)) {
				logger.error("地址Id为空！");
				return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
			}
			
			List<AddressInfoEntity> addressInfoList = addressService.deleteAddressInfo(Long.parseLong(userId),idStr.split(","));
			resultMap.put("addressInfoList", addressInfoList);
			return Response.success("删除地址信息成功", resultMap);
		} catch (Exception e) {
		    logger.error("删除地址信息失败，错误原因", e);
			return Response.fail(BusinessErrorCode.DELETE_INFO_FAILED);
		}
	}
}
