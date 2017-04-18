package com.apass.esp.domain.dto.contract;

import java.math.BigDecimal;

/**
 * Created by lixining on 2017/4/5.
 */
public class ContractProductDTO {
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 购买数量
     */
    private Integer productNum;
    /**
     * 总价
     */
    private BigDecimal amount;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
