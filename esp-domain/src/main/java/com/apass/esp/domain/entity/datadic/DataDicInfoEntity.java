/**
 * @description 
 * 
 * Copyright (c) 2015-2016 liuchao01,Inc.All Rights Reserved.
 */
package com.apass.esp.domain.entity.datadic;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 数据字典实体
 * @description 
 *
 * @author chenbo   
 * @version $Id: DataDicInfoEntity.java, v 0.1 2016年12月27日 下午6:51:49 chenbo Exp $
 */
@MyBatisEntity
public class DataDicInfoEntity {
    
    /** 商品id */
    private Long   id;

    /** 数据类型编号 **/
    private String dataTypeNo;

    /** 数据类型名称 **/
    private String dataTypeName;

    /** 数据字典编号 **/
    private String dataNo;

    /** 数据字典名称 **/
    private String dataName;

    /** 状态（是否有效）**/
    private String status;

    /** 备注**/
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataTypeNo() {
        return dataTypeNo;
    }

    public void setDataTypeNo(String dataTypeNo) {
        this.dataTypeNo = dataTypeNo;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getDataNo() {
        return dataNo;
    }

    public void setDataNo(String dataNo) {
        this.dataNo = dataNo;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
