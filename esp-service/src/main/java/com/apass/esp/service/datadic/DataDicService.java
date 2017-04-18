package com.apass.esp.service.datadic;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.entity.datadic.DataDicInfoEntity;
import com.apass.esp.repository.datadic.DataDicRepository;
import com.apass.gfb.framework.exception.BusinessException;

/**
 * 数据字典服务类
 * @description 
 *
 * @author chenbo
 * @version $Id: DataDicService.java, v 0.1 2016年12月27日 下午6:39:38 chenbo Exp $
 */
@Component
public class DataDicService {

    @Autowired
    private DataDicRepository dataDicRepository;

    /**
     * 查询数据字典
     * @throws BusinessException 
     */
    public List<DataDicInfoEntity> getDataDicByparam(Map<String, String> map) throws BusinessException {
        //如果数据字典状态为空，默认查询有效的
        if (StringUtils.isAnyBlank(map.get("status"))) {
            map.put("status", "1");
        }
        List<DataDicInfoEntity> list = dataDicRepository.queryDataDicInfoByParam(map);
        return list;
    }
}
