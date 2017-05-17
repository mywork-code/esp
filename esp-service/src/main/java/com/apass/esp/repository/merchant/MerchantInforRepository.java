package com.apass.esp.repository.merchant;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class MerchantInforRepository extends BaseMybatisRepository<MerchantInfoEntity, Long> {

    // 商户信息分页查询
    public Pagination<MerchantInfoEntity> queryMerchantInforPage(Map<String, Object> map, Page page) {
        return page(map, page, getSQL("queryMerchantInforList"));
    }

    // 待审核商户信息分页查询
    public Pagination<MerchantInfoEntity> queryMerchantCheckPage(Map<String, Object> map, Page page) {
        return page(map, page, getSQL("queryMerchantCheckList"));
    }

    // 商户信息查询
    public List<MerchantInfoEntity> queryMerchantInfor(Map<String, Object> map) {
        // return page(map, getSQL("queryMerchantInforList"));
        return getSqlSession().selectList("queryMerchantInforListNoPage", map);
    }

    // 添加商户信息到到商户主表 t_esp_merchant_info
    public Integer addMerchantInfor(MerchantInfoEntity mity) {
        return getSqlSession().insert(getSQL("insertMerchantInfor"), mity);
    }

    // 添加商户信息到临时表 t_esp_merchant_temp_info
    public Integer addMerchantInforTemp(MerchantInfoEntity mity) {
        return getSqlSession().insert(getSQL("insertMerchantInforToTemp"), mity);
    }

    // 编辑商户信息到到商户主表 t_esp_merchant_info
    public Integer editMerchantInfor(MerchantInfoEntity mity) {
        return getSqlSession().update(getSQL("editMerchantInfor"), mity);
    }

    // 编辑商户信息到临时表 t_esp_merchant_temp_info
    public Integer editMerchantInforTemp(MerchantInfoEntity mity) {
        return getSqlSession().update(getSQL("editMerchantInforToTemp"), mity);
    }

    // 编辑商户信息 主表 t_esp_merchant_info
    public Integer editMerchantStatus(MerchantInfoEntity mity) {
        return getSqlSession().update(getSQL("editMerchantStatus"), mity);
    }

    // 编辑商户信息 临时表 t_esp_merchant_temp_info
    public Integer editMerchantStatusTemp(MerchantInfoEntity mity) {
        return getSqlSession().update(getSQL("editMerchantStatusTemp"), mity);
    }

    // 根据商户编码查询商户信息（主表）
    public MerchantInfoEntity queryByMerchantCode(String merchantCode) {
        return (MerchantInfoEntity) getSqlSession().selectOne("selectBymerchantCode", merchantCode);
    }

    public List<MerchantInfoEntity> queryMerchantInfor() {
        return getSqlSession().selectList("selectMerchantInforList");
    }

    // 根据id查询临时表中的内容
    public MerchantInfoEntity queryMerchantTempById(Long id) {
        return getSqlSession().selectOne("queryMerchantTempById", id);
    }

    // 根据id查询主表中的内容
    public MerchantInfoEntity queryMerchantInfoById(Long id) {
        return getSqlSession().selectOne("queryMerchantInfoById", id);
    }

    // 根据商户编码查询商户信息（临时表）
    public MerchantInfoEntity queryTempMByMerchantCode(String merchantCode) {
        MerchantInfoEntity mity = (MerchantInfoEntity) getSqlSession().selectOne("selectTempBymerchantCode",
            merchantCode);
        return mity;
    }
    //得到最大的ID
    public int getMaxId(){
        return (int)getSqlSession().selectOne("selectMaxId");
    }
}
