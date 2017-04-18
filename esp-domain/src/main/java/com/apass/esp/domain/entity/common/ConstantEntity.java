package com.apass.esp.domain.entity.common;

import com.apass.gfb.framework.annotation.MyBatisEntity;

@MyBatisEntity
public class ConstantEntity {

    /**
     * 数据编号
     */
    private String dataNo;
    /**
     * 数据名称
     */
    private String dataName;
    /**
     * 类型编号
     */
    private String dataTypeNo;
    /**
     * 类型名称
     */
    private String dataTypeName;
    /**
     * 是否有效
     */
    private String status;
    /**
     * 字典描述:DATA_TYPE_NO为100001 时存放 物流电话号码
     */
    private String remark;
    
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
