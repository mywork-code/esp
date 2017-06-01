package com.apass.esp.service.nation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.apass.esp.domain.enums.CityEnums;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.entity.common.DictDTO;
import com.apass.esp.domain.entity.nation.NationEntity;
import com.apass.esp.repository.nation.NationRepository;
import com.apass.gfb.framework.exception.BusinessException;

/**
 * 查询省份市
 *
 * @author zengqingshan
 * @version $Id: NationService.java, v 0.1 2016年12月21日 下午3:57:07 zengqingshan
 *          Exp $
 * @description
 */
@Service
public class NationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NationService.class);
    /**
     * 直辖市
     */
    private static final String[] CENTRL_CITY_ARRAY = {"110000", "120000", "310000", "500000"};
    private static final List<String> CENTRL_CITY_LIST = Arrays.asList(CENTRL_CITY_ARRAY);

    /**
     * 新疆、西藏、甘肃省、宁夏、内蒙古、青海省、香港、澳门、台湾省
     */
    private static final List<String> FORBIDDEN_PROVINCE = Arrays.asList(
            new String[]{"650000", "540000", "620000", "640000", "150000", "630000", "810000", "820000", "710000"});

    @Autowired
    private NationRepository nationRepository;

    /**
     * 查询城市公共方法
     *
     * @param districtCode 城市code
     * @param cityFlag     true：查询市，false：查询区
     * @return
     * @throws BusinessException
     */
    public List<DictDTO> queryDistrictForAms(String districtCode, boolean cityFlag) throws BusinessException {
        List<DictDTO> result = new ArrayList<DictDTO>();
        List<NationEntity> dataList = null;

        boolean getAreaFlag = true;
        if (districtCode.equals("000000")) { // 查询省份
            getAreaFlag = true;
        } else {
            getAreaFlag = false;
        }

        try {
            dataList = nationRepository.selectCityList(districtCode);
        } catch (Exception e) {
        	LOGGER.error("城市列表查询失败",e);
            throw new BusinessException("城市列表查询失败",BusinessErrorCode.QUREY_INFO_FAILED);
        }
        boolean isCentrlCity = CENTRL_CITY_LIST.contains(districtCode);

        if (isCentrlCity) {
            DictDTO dict = new DictDTO();
            dict.setCode(districtCode);
            switch (districtCode) {
                case "110000":
                    dict.setName(CityEnums.BEIJING.getName());
                    dict.setPrefix(CityEnums.BEIJING.getPrefix());
                    break;
                case "120000":
                    dict.setName(CityEnums.TIANJIN.getName());
                    dict.setPrefix(CityEnums.TIANJIN.getPrefix());
                    break;
                case "310000":
                    dict.setName(CityEnums.SHANGHAI.getName());
                    dict.setPrefix(CityEnums.SHANGHAI.getPrefix());
                    break;
                case "500000":
                    dict.setName(CityEnums.CHONGQING.getName());
                    dict.setPrefix(CityEnums.CHONGQING.getPrefix());
                    break;
                default:
                    break;
            }
            result.add(dict);
            return result;
        }

        if (CollectionUtils.isEmpty(dataList)) {
            return result;
        }

        for (NationEntity nation : dataList) {
            /** 省份查询, 部分省份不展示 */
            if (getAreaFlag && FORBIDDEN_PROVINCE.contains(nation.getCode())) {
                continue;
            }
            DictDTO dict = new DictDTO();
            dict.setCode(nation.getCode());
            dict.setPrefix(nation.getPrefix());
            if (getAreaFlag) {
                dict.setName(nation.getProvince());
            } else {
                if (isCentrlCity) {
                    dict.setName(nation.getDistrict());
                } else {
                    if (cityFlag) {
                        dict.setName(nation.getCity());
                    } else {
                        dict.setName(nation.getDistrict());
                    }
                }
            }
            result.add(dict);
        }

        return result;
    }


    public List<DictDTO> queryDistrictArea(String districtCode) throws BusinessException {
        List<DictDTO> result = new ArrayList<DictDTO>();
        List<NationEntity> dataList = null;

        try {
            dataList = nationRepository.selectCityList(districtCode);
        } catch (Exception e) {
        	LOGGER.error("城市列表查询失败",e);
            throw new BusinessException("城市列表查询失败",BusinessErrorCode.QUREY_INFO_FAILED);
        }
        if (CollectionUtils.isEmpty(dataList)) {
            return result;
        }
        boolean isCentrlCity = CENTRL_CITY_LIST.contains(districtCode);
        for (NationEntity nation : dataList) {

            DictDTO dict = new DictDTO();
            dict.setCode(nation.getCode());
            dict.setPrefix(nation.getPrefix());
            dict.setName(nation.getDistrict());
            result.add(dict);
        }

        return result;
    }

    /**
     * 查询城市公共方法
     *
     * @param districtCode
     *            城市code
     * @param cityFlag
     *            true：查询市，false：查询区
     * @return
     * @throws BusinessException
     */
    public List<DictDTO> queryDistrict(String districtCode, boolean cityFlag) throws BusinessException {
        List<DictDTO> result = new ArrayList<DictDTO>();
        List<NationEntity> dataList = null;

        boolean getAreaFlag = true;
        if (districtCode.equals("000000")) { // 查询省份
            getAreaFlag = true;
        } else {
            getAreaFlag = false;
        }

        try {
            dataList = nationRepository.selectCityList(districtCode);
        } catch (Exception e) {
            throw new BusinessException("城市列表查询失败", e);
        }
        if (CollectionUtils.isEmpty(dataList)) {
            return result;
        }
        boolean isCentrlCity = CENTRL_CITY_LIST.contains(districtCode);
        for (NationEntity nation : dataList) {
            /** 省份查询, 部分省份不展示 */
            if(getAreaFlag && FORBIDDEN_PROVINCE.contains(nation.getCode())){
                continue;
            }
            DictDTO dict = new DictDTO();
            dict.setCode(nation.getCode());
            dict.setPrefix(nation.getPrefix());
            if (getAreaFlag) {
                dict.setName(nation.getProvince());
            } else {
                if (isCentrlCity) {
                    dict.setName(nation.getDistrict());
                } else {
                    if (cityFlag) {
                        dict.setName(nation.getCity());
                    } else {
                        dict.setName(nation.getDistrict());
                    }
                }
            }
            result.add(dict);
        }

        return result;
    }
}
