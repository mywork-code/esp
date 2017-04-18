package com.apass.esp.domain.dto.payment;

import org.apache.commons.lang3.StringUtils;

/**
 * 安家派支付--响应对象
 * 
 * @author admin
 *
 */
public class PayResponseDto {

	/**
	 * 响应码 0:成功 1:失败 2：未绑卡 3：无额度....
	 */
	private String resultCode;

	/**
	 * 响应信息
	 */
	private String resultMessage;
	
    /**
     * 交易主订单号
     */
    private String mainOrderId;
    /**
     * 银行卡支付页面html
     */
    private String payPage;

    public String getPayPage() {
        return payPage;
    }

    public void setPayPage(String payPage) {
        this.payPage = payPage;
    }
    
	public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public boolean isSuccess() {
		return StringUtils.equals(resultCode, "0");
	}

}
