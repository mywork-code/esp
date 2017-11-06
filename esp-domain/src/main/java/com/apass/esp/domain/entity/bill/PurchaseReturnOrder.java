package com.apass.esp.domain.entity.bill;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
/**
 * 
 * @author ht
 *
 */
public class PurchaseReturnOrder extends OrderInfoEntity{
    /*商品订单表主键*/
    private Long orderInfoId;
    /*公司编码*/
    private String companyCode;
    /*项目组订单编号  */
    private String orderId;
    /*订单类型  */
    private String orderType;
    /*供应商名称  */
    private String supNo;
    /*供应商编号 */
    private String merchantCode;
    /*运费*/
    private String carriage;
    /*原订单编号*/
    private String oldOrderId;
    private String mainOrderId;
    /*创建日期   */
    //private Date createDate;
    public Long getOrderInfoId() {
        return orderInfoId;
    }
    public void setOrderInfoId(Long orderInfoId) {
        this.orderInfoId = orderInfoId;
    }
    public String getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public String getSupNo() {
        return supNo;
    }
    public void setSupNo(String supNo) {
        this.supNo = supNo;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }
    public String getCarriage() {
        return carriage;
    }
    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }
    public String getOldOrderId() {
        return oldOrderId;
    }
    public void setOldOrderId(String oldOrderId) {
        this.oldOrderId = oldOrderId;
    }

    @Override
    public String getMainOrderId() {
        return mainOrderId;
    }

    @Override
    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }
}
