package com.apass.esp.service.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.enums.AddressDefaultStatus;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.repository.address.AddressInfoRepository;
import com.apass.gfb.framework.exception.BusinessException;

@Service
@Transactional
public class AddressService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);
	@Autowired
	private AddressInfoRepository addressInfoRepository;

	/**
	 * 查询地址信息
	 * 
	 * @param customerInfo
	 * @return
	 */
	public List<AddressInfoEntity> queryAddressInfo(Long userId)
			throws BusinessException {
		try {
			List<AddressInfoEntity> addressInfoList = addressInfoRepository.queryAddressInfoList(userId);
			return addressInfoList;
		} catch (Exception e) {
			LOGGER.error("查询地址信息失败===>", e);
			throw new BusinessException("查询地址信息失败！", BusinessErrorCode.QUREY_INFO_FAILED);
		}		
	}
	
	/**
	 * 新增地址信息
	 * 
	 * @param customerInfo
	 * @return
	 */
	public Long addAddressInfo(AddressInfoEntity addAddressInfo)
			throws BusinessException {
		try {
			if(StringUtils.isNoneBlank(addAddressInfo.getIsDefault()) && addAddressInfo.getIsDefault().equals("1")){
				//将原来的默认地址置为非默认的
				addressInfoRepository.updateAddressStatus(addAddressInfo.getUserId());
			}
			//将地址插入数据库
			addressInfoRepository.insert(addAddressInfo);
			return addAddressInfo.getId();
		} catch (Exception e) {
			LOGGER.error("新增地址信息失败===>", e);
			throw new BusinessException("新增地址信息失败！", BusinessErrorCode.ADD_INFO_FAILED);
		}		
	}
	
	/**
	 * 更新地址信息
	 * 
	 * @param customerInfo
	 * @return
	 */
	public List<AddressInfoEntity> updateAddressInfo(AddressInfoEntity addInfo)
			throws BusinessException {
		String isDefault = addInfo.getIsDefault();//是否是默认地址
		
		try {
			if(YesNo.isYes(isDefault)){
				//将原来的默认地址置为非默认的
				addressInfoRepository.updateAddressStatus(addInfo.getUserId());
			}
			//更新地址的信息
			Integer updateFlag = addressInfoRepository.updateAddressInfo(addInfo);
			if(updateFlag != 1){
			    throw new BusinessException("更新地址信息失败",BusinessErrorCode.ADDRESS_UPDATE_FAILED);
			}
			//更新完地址信息，将最新的地址信息返回
			List<AddressInfoEntity> addressInfoList = addressInfoRepository.queryAddressInfoList(addInfo.getUserId());
			return addressInfoList;
		} catch (Exception e) {
			LOGGER.error("更新地址信息失败===>", e);
			throw new BusinessException("更新地址信息失败！", BusinessErrorCode.ADDRESS_UPDATE_FAILED);
		}
	}
	
	/**
	 * 更新地址信息
	 * 
	 * @param customerInfo
	 * @return
	 */
	public List<AddressInfoEntity> deleteAddressInfo(Long userId,String[] idStr)
			throws BusinessException {
		try {
		    
		    Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("userId", userId);
	        paramMap.put("idStr", idStr);
		    
			//直接删除地址的信息
			addressInfoRepository.deleteAddressInfoCTQ(paramMap);
			//更新完地址信息，将最新的地址信息返回
			List<AddressInfoEntity> addressInfoList = addressInfoRepository.queryAddressInfoList(userId);
			return addressInfoList;
		} catch (Exception e) {
			LOGGER.error("删除地址信息失败===>", e);
			throw new BusinessException("删除地址信息失败！", BusinessErrorCode.DELETE_INFO_FAILED);
		}
	}
	/**
	 * 根据用户id查询默认地址信息
	 * 
	 * @param parseLong
	 * @return
	 */
    public AddressInfoEntity queryDefaultByUserId(long userId) {
        AddressInfoEntity param = new AddressInfoEntity();
        param.setUserId(userId);
        param.setIsDefault(AddressDefaultStatus.DEFAULT.getCode());
        List<AddressInfoEntity> defaultAddress= addressInfoRepository.queryDefaultByUserId(param);
        if(null != defaultAddress && defaultAddress.size()>0){
            return defaultAddress.get(0);
        }
        return null;
    }

    /**
     * 查询一条用户的地址，有默认的，返回默认地址;无默认的，随机返回一条地址;无地址数据，返回null
     * 
     * @param parseLong
     * @return
     */
    public AddressInfoEntity queryOneAddressByUserId(long userId) {
        
        return addressInfoRepository.queryOneAddressByUserId(userId);
    }

}
