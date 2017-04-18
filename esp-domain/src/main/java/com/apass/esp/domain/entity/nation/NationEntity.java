package com.apass.esp.domain.entity.nation;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 获取地址(省市)
 * @description 
 *
 * @author zengqingshan
 * @version $Id: NationEntity.java, v 0.1 2016年12月21日 下午2:37:45 zengqingshan Exp $
 */
@MyBatisEntity
public class NationEntity {
     
    /**
     * ID
     */
    private Long   id;
    /**
     * 编码
     */
    private String code;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 地区
     */
    private String district;
    /**
     * 父节点
     */
    private Long   parent;
    /**
     * 首字母
     */
    private String prefix;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public Long getParent() {
        return parent;
    }
    public void setParent(Long parent) {
        this.parent = parent;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
}
