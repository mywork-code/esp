package com.apass.esp.domain.entity.contract;

import com.apass.gfb.framework.annotation.MyBatisEntity;

import java.util.Date;

/**
 * 合同Schedule
 */
@MyBatisEntity
public class ContractScheduleEntity {

    /**
     * ID
     */
    private Long id;
    /**
     * 主订单号
     */
    private String mainOrderId;
    /**
     * 合同类型
     */
    private String contractType;
    /**
     * 合同状态 0-待生成
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
