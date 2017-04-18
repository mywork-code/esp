package com.apass.esp.domain.entity.common;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 系统参数实体
 * 
 * @author chenbo
 */
@MyBatisEntity
public class SystemParamEntity {

    /**
     * 主键id
     */
    private Long       id;

    /**
     * 商户结算折扣率%(成本价/市场价)
     */
    private BigDecimal merchantSettleRate;
    /**
     * 商品价格折扣率%(市场价*折扣率=当前售价)
     */
    private BigDecimal goodsPriceRate;
    /**
     * 创建人
     */
    private String     createUser;
    /**
     * 创建日期
     */
    private Date       createDate;

    /**
     * 修改人
     */
    private String     updateUser;
    /**
     * 修改日期
     */
    private Date       updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMerchantSettleRate() {
        return merchantSettleRate;
    }

    public void setMerchantSettleRate(BigDecimal merchantSettleRate) {
        this.merchantSettleRate = merchantSettleRate;
    }

    public BigDecimal getGoodsPriceRate() {
        return goodsPriceRate;
    }

    public void setGoodsPriceRate(BigDecimal goodsPriceRate) {
        this.goodsPriceRate = goodsPriceRate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return DateFormatUtil.datetime2String(createDate);
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUpdateDateStr() {
        return DateFormatUtil.datetime2String(updateDate);
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
