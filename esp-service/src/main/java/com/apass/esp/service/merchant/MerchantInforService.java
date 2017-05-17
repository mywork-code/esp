package com.apass.esp.service.merchant;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.repository.merchant.MerchantInforRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

@Service
public class MerchantInforService {
    @Autowired
    public MerchantInforRepository merchantInforRepository;
    private static final Logger    LOGGER = LoggerFactory.getLogger(MerchantInforService.class);

    // 商户信息分页查询
    public Pagination<MerchantInfoEntity> queryMerchantInforPage(Map<String, Object> map,
                                                                 Page page) throws BusinessException {
        return merchantInforRepository.queryMerchantInforPage(map, page);
    }

    // 待审核商户信息分页查询
    public Pagination<MerchantInfoEntity> queryMerchantCheckPage(Map<String, Object> map,
                                                                 Page page) throws BusinessException {
        return merchantInforRepository.queryMerchantCheckPage(map, page);
    }

    // 商户信息查询,不带分页
    public List<MerchantInfoEntity> queryMerchantInfor(Map<String, Object> map) throws BusinessException {
        return merchantInforRepository.queryMerchantInfor(map);
    }

    // 添加商户信息
    public Integer addMerchantInfor(MerchantInfoEntity mity) throws BusinessException {
        Integer result = merchantInforRepository.addMerchantInfor(mity);
        if (result == 1) {
            result = this.addMerchantInforTemp(mity);
        } else {
            result = 0;
        }
        return result;
    }

    // 添加商户信息 临时表t_esp_merchant_temp_info
    public Integer addMerchantInforTemp(MerchantInfoEntity mity) throws BusinessException {
        return merchantInforRepository.addMerchantInforTemp(mity);
    }

    // 编辑商户信息 主表t_esp_merchant_info
    public Integer editMerchantInfor(MerchantInfoEntity mity) throws BusinessException {
        Integer result = merchantInforRepository.editMerchantInfor(mity);
        if (result == 1) {
            result = this.editMerchantInforTemp(mity);
        } else {
            result = 0;
        }

        return result;
    }

    // 编辑商户信息 临时表t_esp_merchant_temp_info
    public Integer editMerchantInforTemp(MerchantInfoEntity mity) throws BusinessException {
        return merchantInforRepository.editMerchantInforTemp(mity);
    }

    // 编辑商户状态 主表t_esp_merchant_info
    public Integer editMerchantStatus(MerchantInfoEntity mity) throws BusinessException {
        Integer result = merchantInforRepository.editMerchantStatus(mity);
        if (result == 1) {
            result = this.editMerchantStatusTemp(mity);
        } else {
            result = 0;
        }
        return result;
    }

    // 编辑商户状态 临时表t_esp_merchant_temp_info
    public Integer editMerchantStatusTemp(MerchantInfoEntity mity) throws BusinessException {
        return merchantInforRepository.editMerchantStatusTemp(mity);
    }

    // 根据商户编码查询去查商户信息（主表）
    public MerchantInfoEntity queryByMerchantCode(String merchantCode) {
        return merchantInforRepository.queryByMerchantCode(merchantCode);
    }

    // 根据商户编码查询去查商户信息（临时表）
    public MerchantInfoEntity queryTempMByMerchantCode(String merchantCode) {
        return merchantInforRepository.queryTempMByMerchantCode(merchantCode);
    }

    public List<MerchantInfoEntity> queryMerchantInfor() {
        return merchantInforRepository.queryMerchantInfor();
    }

    // 把临时表数据同步到主表中 审核通过
    public Integer updateMerchantService(MerchantInfoEntity mityNew) {
        Integer result = 0;
        // 根据id查询临时表数据
        MerchantInfoEntity mityOld = this.queryTempMByMerchantCode(mityNew.getMerchantCode());
        mityOld.setStatus(mityNew.getStatus());
        mityOld.setRemark(mityNew.getRemark());
        try {
            result = this.editMerchantStatusTemp(mityNew);
            result = merchantInforRepository.editMerchantInforTemp(mityOld);
        } catch (BusinessException e) {
            LOGGER.error("修改临时表内容失败", e);
        }
        result = merchantInforRepository.editMerchantInfor(mityOld);

        return result;
    }

    // 根据id查询数据库内容 审核驳回
    public Integer updateMerchantTempService(MerchantInfoEntity mityNew) {
        // 根据id查询主表数据
        MerchantInfoEntity mityOld = this.queryByMerchantCode(mityNew.getMerchantCode());
        mityOld.setStatus(mityNew.getStatus());
        mityOld.setRemark(mityNew.getRemark());
        Integer result = 0;
        try {
            result = this.editMerchantStatusTemp(mityNew);
            result = this.editMerchantInforTemp(mityOld);
        } catch (BusinessException e) {
            LOGGER.error("修改主表内容失败", e);
        }
        return result;
    }

    // 根据id查询临时表内容
    private MerchantInfoEntity queryMerchantTempById(Long id) {
        return merchantInforRepository.queryMerchantTempById(id);
    }

    // 根据id查询主表内容
    private MerchantInfoEntity queryMerchantInfoById(Long id) {
        return merchantInforRepository.queryMerchantInfoById(id);
    }

    public int getMaxId(){
        return merchantInforRepository.getMaxId();
    }
}
