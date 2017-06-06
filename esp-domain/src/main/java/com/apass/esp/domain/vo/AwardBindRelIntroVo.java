package com.apass.esp.domain.vo;

import java.math.BigDecimal;

/**
 * @author xiaohai
 *
 */
public class AwardBindRelIntroVo {
	
	/**
	 * 邀请人手机号
	 */
	private String mobile;
	/**
	 * 可提现金额 
	 */
	private BigDecimal canWithdrawAmount;
	/**
	 * 申请提现提交时间
	 */
	private String applyDate;
	/**
	 * 申请提现金额
	 */
	private BigDecimal amount;
	/**
	 * 推荐人姓名
	 */
	private String realName;
	
	/**
	 * 推荐人银行卡号
	 */
	private String cardNO;
	
	/**
	 * 所属银行
	 */
	private String cardBank;
	
	/**
	 * 放款时间
	 */
	private String arrivedDate;
	
	/**
	 * 放款状态
	 */
	private String status;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getCanWithdrawAmount() {
		return canWithdrawAmount;
	}

	public void setCanWithdrawAmount(BigDecimal canWithdrawAmount) {
		this.canWithdrawAmount = canWithdrawAmount;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getArrivedDate() {
		return arrivedDate;
	}

	public void setArrivedDate(String arrivedDate) {
		this.arrivedDate = arrivedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
