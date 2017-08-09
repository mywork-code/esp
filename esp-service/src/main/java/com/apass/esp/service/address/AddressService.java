package com.apass.esp.service.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.dto.WorkCityJdDto;
import com.apass.esp.domain.dto.cart.PurchaseRequestDto;
import com.apass.esp.domain.entity.WorkCityJd;
import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.AddressDefaultStatus;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.mapper.WorkCityJdMapper;
import com.apass.esp.repository.address.AddressInfoRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.google.common.collect.Maps;

@Service
public class AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressInfoRepository addressInfoRepository;

    @Autowired
    private WorkCityJdMapper cityJdMapper;

    @Autowired
    public GoodsRepository goodsDao;

    /**
     * 查询地址信息
     * 
     * @param customerInfo
     * @return
     */
    public List<AddressInfoEntity> queryAddressInfo(Long userId) throws BusinessException {
        try {
            List<AddressInfoEntity> addressInfoList = addressInfoRepository.queryAddressInfoList(userId);
            return addressInfoList;
        } catch (Exception e) {
            LOGGER.error("查询地址信息失败===>", e);
            throw new BusinessException("查询地址信息失败！", BusinessErrorCode.QUREY_INFO_FAILED);
        }
    }

    /**
     * 查询地址信息(当是京东地址时，如果没有乡镇（towns），则towns为0)
     * 
     * @param customerInfo
     * @return
     */
    public List<AddressInfoEntity> queryAddressInfoJd(Long userId) throws BusinessException {
        try {
            List<AddressInfoEntity> addressInfoList = addressInfoRepository.queryAddressInfoListJd(userId);
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
    @Transactional(rollbackFor = Exception.class)
    public Long addAddressInfo(AddressInfoEntity addAddressInfo) throws BusinessException {
        try {
            if (StringUtils.isNoneBlank(addAddressInfo.getIsDefault())
                    && addAddressInfo.getIsDefault().equals("1")) {
                // 将原来的默认地址置为非默认的
                addressInfoRepository.updateAddressStatus(addAddressInfo.getUserId());
            }
            // 将地址插入数据库
            addressInfoRepository.insert(addAddressInfo);
            return addAddressInfo.getId();
        } catch (Exception e) {
            LOGGER.error("新增地址信息失败===>", e);
            throw new BusinessException("新增地址信息失败！", BusinessErrorCode.ADD_INFO_FAILED);
        }
    }

    /**
     * 新增地址信息
     * 
     * @param customerInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long addAddressInfoV1(AddressInfoEntity addAddressInfo) throws BusinessException {
        try {
            if (StringUtils.isNoneBlank(addAddressInfo.getIsDefault())
                    && addAddressInfo.getIsDefault().equals("1")) {
                // 将原来的默认地址置为非默认的
                addressInfoRepository.updateAddressStatus(addAddressInfo.getUserId());
            }
            // 将地址插入数据库
            addPropertiesToEntity(addAddressInfo);
            addressInfoRepository.insert(addAddressInfo);
            return addAddressInfo.getId();
        }catch(BusinessException e){
        	LOGGER.error("新增地址信息失败===>", e);
        	throw new BusinessException(e.getErrorDesc());
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
    @Transactional(rollbackFor = Exception.class)
    public List<AddressInfoEntity> updateAddressInfo(AddressInfoEntity addInfo) throws BusinessException {
        String isDefault = addInfo.getIsDefault();// 是否是默认地址

        try {
            if (YesNo.isYes(isDefault)) {
                // 将原来的默认地址置为非默认的
                addressInfoRepository.updateAddressStatus(addInfo.getUserId());
            }
            // 更新地址的信息
            Integer updateFlag = addressInfoRepository.updateAddressInfo(addInfo);
            if (updateFlag != 1) {
                throw new BusinessException("更新地址信息失败", BusinessErrorCode.ADDRESS_UPDATE_FAILED);
            }
            // 更新完地址信息，将最新的地址信息返回
            List<AddressInfoEntity> addressInfoList = addressInfoRepository.queryAddressInfoList(addInfo
                    .getUserId());
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
     * @throws BusinessException 
     */
    @Transactional(rollbackFor = Exception.class)
    public List<AddressInfoEntity> updateAddressInfoV1(AddressInfoEntity addInfo) throws BusinessException{
        String isDefault = addInfo.getIsDefault();// 是否是默认地址

        try {
            if (YesNo.isYes(isDefault)) {
                // 将原来的默认地址置为非默认的
                addressInfoRepository.updateAddressStatus(addInfo.getUserId());
            }
            // 更新地址的信息
            addPropertiesToEntity(addInfo);
            Integer updateFlag = addressInfoRepository.updateAddressInfo(addInfo);
            if (updateFlag != 1) {
                throw new BusinessException("更新地址信息失败", BusinessErrorCode.ADDRESS_UPDATE_FAILED);
            }
            // 更新完地址信息，将最新的地址信息返回
            List<AddressInfoEntity> addressInfoList = addressInfoRepository.queryAddressInfoList(addInfo
                    .getUserId());
            return addressInfoList;
        } catch(BusinessException e){
        	LOGGER.error("更新地址信息失败===>", e);
        	throw new BusinessException(e.getErrorDesc());
        } catch (Exception e) {
            LOGGER.error("更新地址信息失败===>", e);
            throw new BusinessException("您填写的收货地址所在地址不完整，请重新填写！", BusinessErrorCode.ADDRESS_UPDATE_FAILED);
        }
    }

    public Integer setDefaultAddress(String addressId) throws BusinessException {

        // 首先根据addressId ,查询对应地址信息的详细信息
        ValidateUtils.isNotBlank(addressId, "地址编号不能为空!");

        AddressInfoEntity entity = addressInfoRepository
                .queryOneAddressByAddressId(Long.parseLong(addressId));
        Integer count = null;
        if (null != entity && null != entity.getUserId()) {
            // 把之前默认收货地址修改为普通收货地址
            addressInfoRepository.updateAddressStatus(entity.getUserId());
            entity.setIsDefault("1");
            count = addressInfoRepository.updateAddressInfo(entity);
        }
        return count;
    }

    /**
     * 更新地址信息
     * 
     * @param customerInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<AddressInfoEntity> deleteAddressInfo(Long userId, String[] idStr) throws BusinessException {
        try {

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userId", userId);
            paramMap.put("idStr", idStr);

            // 直接删除地址的信息
            addressInfoRepository.deleteAddressInfoCTQ(paramMap);
            // 更新完地址信息，将最新的地址信息返回
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
        List<AddressInfoEntity> defaultAddress = addressInfoRepository.queryDefaultByUserId(param);
        if (null != defaultAddress && defaultAddress.size() > 0) {
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

    public AddressInfoEntity queryOneAddressByAddressId(long addressId) {

        return addressInfoRepository.queryOneAddressByAddressId(addressId);
    }

    /**
     * 根据province/city/distract/towns,获取对应的code
     * 
     * @return
     * @throws BusinessException 
     */
    public AddressInfoEntity addPropertiesToEntity(AddressInfoEntity address) throws BusinessException {

        WorkCityJdDto dto1 = new WorkCityJdDto(address.getProvince(), "0");
        WorkCityJd provice = cityJdMapper.selectByNameAndParent(dto1);

        WorkCityJdDto dto2 = new WorkCityJdDto(address.getCity(), provice.getCode());
        WorkCityJd city = cityJdMapper.selectByNameAndParent(dto2);

        WorkCityJdDto dto3 = new WorkCityJdDto(address.getDistrict(), city.getCode());
        WorkCityJd district = cityJdMapper.selectByNameAndParent(dto3);

        
        List<WorkCityJd> townList = cityJdMapper.selectDateByParentId(district.getCode());
        
        if(!CollectionUtils.isEmpty(townList) && StringUtils.isBlank(address.getTowns())){
        	throw new BusinessException("您填写的收货地址所在地址不完整，请重新填写！");
        }
        
        WorkCityJdDto dto4 = new WorkCityJdDto(address.getTowns(), district.getCode());
        WorkCityJd towns = cityJdMapper.selectByNameAndParent(dto4);

        address.setProvinceCode(provice.getCode());
        address.setCityCode(city.getCode());
        address.setDistrictCode(district.getCode());
        address.setTownsCode((null == towns) ? "0" : towns.getCode());

        return address;
    }

    /**
     * 根据商品列表，查询是否存在京东商品，如果存在京东商品，就验证地址是否存在towns
     * 
     * @param purchaseList
     * @param addressId
     * @return
     */
    public Map<String, Object> validateJdGoods(List<PurchaseRequestDto> purchaseList, Long addressId) {

        Boolean bl = false;
        Map<String, Object> maps = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(purchaseList)) {
            for (PurchaseRequestDto purchase : purchaseList) {
                GoodsInfoEntity goods = goodsDao.select(purchase.getGoodsId());
                if (null != goods) {
                    if (StringUtils.equals(goods.getSource(), SourceType.JD.getCode())) {
                        bl = true;
                        break;
                    }
                }
            }
        }
        if (bl) {
            AddressInfoEntity address = queryOneAddressByAddressId(addressId);
            if (null != address && StringUtils.isBlank(address.getTownsCode())) {
                maps.put("notExsitTowns", true);
            }
        }
        return maps;
    }
}
