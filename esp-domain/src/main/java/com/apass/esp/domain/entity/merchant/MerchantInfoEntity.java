package com.apass.esp.domain.entity.merchant;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 商户信息实体
 * 
 * @author wyy
 *
 */
@MyBatisEntity
public class MerchantInfoEntity {
	/**
	 * 主键标识ID
	 */
	private Long id;
	/**
	 * 商户编码
	 */
	private String merchantCode;
	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 商户所在省份
	 */
	private String merchantProvince;
	/**
	 * 商户所在城市
	 */
	private String merchantCity;
	/**
	 * 商户详细地址
	 */
	private String merchantAddress;
	/**
	 * 商户退货详细地址
	 */
	private String merchantReturnAddress;
	
    /**
     * 商户的收货人名称
     */
    private  String merchantReturnName;
    
    /**
     * 商户的收货人手机号码
     */
    private String merchantReturnPhone;
    
    /**
     * 商户的收货邮编
     */
    private String merchantReturnPostCode;
    
	/**
	 * 邮政编码
	 */
	private String merchantPostcode;
	/**
	 * 商户类型（个人、企业）
	 */
	private String merchantType;
	/**
	 * 结算日期
	 */
	private Integer merchantSettlementDate;
	/**
	 * 结算银行名称
	 */
	private String settlementBankName;
	/**
	 * 商品类型
	 */
	private String settlementCardNo;
	/**
	 * 经营类型
	 */
	private String manageType;
	/**
	 * 企业机构代码
	 */
	private String orgCode;
	/**
	 * 商户状态（-1：无效，1：正常，0：待审核）
	 */
	private String status;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建日期
	 */
	private String createDate;
	/**
	 * 修改人
	 */
	private String updateUser;
	/**
	 * 更新日期
	 */
	private String updateDate;
	/**
	 * 商户昵称
	 */
	private String merchantNickname;
	/**
	 * 商户结算运费
	 */
	private Double merchantSettlementFreight;

	/**
	 * 是否含运费
	 */
	private String isContainFreight;

	/**
	 * 备注
	 */
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsContainFreight() {
		return isContainFreight;
	}

	public void setIsContainFreight(String isContainFreight) {
		this.isContainFreight = isContainFreight;
	}

	public String getMerchantNickname() {
		return merchantNickname;
	}

	public void setMerchantNickname(String merchantNickname) {
		this.merchantNickname = merchantNickname;
	}

	public Double getMerchantSettlementFreight() {
		return merchantSettlementFreight;
	}

	public void setMerchantSettlementFreight(Double merchantSettlementFreight) {
		this.merchantSettlementFreight = merchantSettlementFreight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantProvince() {
		return merchantProvince;
	}

	public void setMerchantProvince(String merchantProvince) {
		this.merchantProvince = merchantProvince;
	}

	public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMerchantPostcode() {
		return merchantPostcode;
	}

	public void setMerchantPostcode(String merchantPostcode) {
		this.merchantPostcode = merchantPostcode;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getSettlementBankName() {
		return settlementBankName;
	}

	public void setSettlementBankName(String settlementBankName) {
		this.settlementBankName = settlementBankName;
	}

	public String getSettlementCardNo() {
		return settlementCardNo;
	}

	public void setSettlementCardNo(String settlementCardNo) {
		this.settlementCardNo = settlementCardNo;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getMerchantSettlementDate() {
		return merchantSettlementDate;
	}

	public void setMerchantSettlementDate(Integer merchantSettlementDate) {
		this.merchantSettlementDate = merchantSettlementDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate);
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateDate);
	}

	public String getMerchantReturnAddress() {
		return merchantReturnAddress;
	}

	public void setMerchantReturnAddress(String merchantReturnAddress) {
		this.merchantReturnAddress = merchantReturnAddress;
	}

    public String getMerchantReturnName() {
        return merchantReturnName;
    }

    public void setMerchantReturnName(String merchantReturnName) {
        this.merchantReturnName = merchantReturnName;
    }

    public String getMerchantReturnPhone() {
        return merchantReturnPhone;
    }

    public void setMerchantReturnPhone(String merchantReturnPhone) {
        this.merchantReturnPhone = merchantReturnPhone;
    }

    public String getMerchantReturnPostCode() {
        return merchantReturnPostCode;
    }

    public void setMerchantReturnPostCode(String merchantReturnPostCode) {
        this.merchantReturnPostCode = merchantReturnPostCode;
    }
	
}
