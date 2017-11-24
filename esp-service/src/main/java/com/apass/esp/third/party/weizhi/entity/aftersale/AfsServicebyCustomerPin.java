package com.apass.esp.third.party.weizhi.entity.aftersale;

/**
 * Created by xiaohai on 2017/11/24.
 */
public class AfsServicebyCustomerPin {
    private String afsServiceId;
    private Integer customerExpect;
    private String customerExpectName;
    private String afsApplyTime;
    private long orderId;
    private long wareId;
    private String wareName;
    private Integer afsServiceStep;
    private String afsServiceStepName;
    private Integer cancel;

    public String getAfsServiceId() {
        return afsServiceId;
    }

    public void setAfsServiceId(String afsServiceId) {
        this.afsServiceId = afsServiceId;
    }

    public Integer getCustomerExpect() {
        return customerExpect;
    }

    public void setCustomerExpect(Integer customerExpect) {
        this.customerExpect = customerExpect;
    }

    public String getCustomerExpectName() {
        return customerExpectName;
    }

    public void setCustomerExpectName(String customerExpectName) {
        this.customerExpectName = customerExpectName;
    }

    public String getAfsApplyTime() {
        return afsApplyTime;
    }

    public void setAfsApplyTime(String afsApplyTime) {
        this.afsApplyTime = afsApplyTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getWareId() {
        return wareId;
    }

    public void setWareId(long wareId) {
        this.wareId = wareId;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public Integer getAfsServiceStep() {
        return afsServiceStep;
    }

    public void setAfsServiceStep(Integer afsServiceStep) {
        this.afsServiceStep = afsServiceStep;
    }

    public String getAfsServiceStepName() {
        return afsServiceStepName;
    }

    public void setAfsServiceStepName(String afsServiceStepName) {
        this.afsServiceStepName = afsServiceStepName;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }
}
