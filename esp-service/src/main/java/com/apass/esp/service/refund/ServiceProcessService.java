package com.apass.esp.service.refund;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.repository.refund.ServiceProcessRepository;
import com.apass.gfb.framework.exception.BusinessException;

@Service
public class ServiceProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProcessService.class);

    @Autowired
    public ServiceProcessRepository serviceProcessRepository;

    /**
     * 查询售后信息表
     * 
     * @param orderId
     * @return
     */
    public List<ServiceProcessEntity> queryServiceProcessDetailByOrderId(Map<String, String> map)
            throws BusinessException {
        List<ServiceProcessEntity> serviceProcessList = null;
        try {
            serviceProcessList = serviceProcessRepository.queryServiceProcessDetailInfoByParam(map);
        } catch (Exception e) {
            LOGGER.error("查询订单售后信息失败===>", e);
            throw new BusinessException("查询订单售后信息失败！", e);
        }
        return serviceProcessList;
    }

    /**
     * 查询售后流程表
     * 
     * @param refundId
     * @param nodeName
     * @return
     */
    public List<ServiceProcessEntity> queryServiceProcessByParam(Map<String, String> map)
            throws BusinessException {
        List<ServiceProcessEntity> serviceProcessList = null;
        try {
            serviceProcessList = serviceProcessRepository.queryServiceProcessInfoByParam(map);
        } catch (Exception e) {
            LOGGER.error("查询售后流程表失败===>", e);
            throw new BusinessException("查询售后流程表失败！", e);
        }
        return serviceProcessList;
    }

    /**
     * 修改售后流程表
     * 
     * @param orderId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ServiceProcessEntity> updateProcessDetailByOrderId(Map<String, String> map)
            throws BusinessException {
        List<ServiceProcessEntity> serviceProcessList = null;
        try {
            serviceProcessList = serviceProcessRepository.updateServiceProcessDetailInfoByParam(map);
        } catch (Exception e) {
            LOGGER.error("修改售后流程表失败===>", e);
            throw new BusinessException("修改售后流程表失败！", e);
        }
        return serviceProcessList;
    }
}
