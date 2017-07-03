package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;

public class AfsapplyForString implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long jdOrderId;
	private Integer customerExpect;
	private String questionDesc;
	private Boolean isNeedDetectionReport;
	private String questionPic;
	private Boolean isHasPackage;
	private Integer packageDesc;
	private String asCustomerDto;
	private String asPickwareDto;
	private String asReturnwareDto;
	private String asDetailDto;
	public Long getJdOrderId() {
		return jdOrderId;
	}
	public void setJdOrderId(Long jdOrderId) {
		this.jdOrderId = jdOrderId;
	}
	public Integer getCustomerExpect() {
		return customerExpect;
	}
	public void setCustomerExpect(Integer customerExpect) {
		this.customerExpect = customerExpect;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	public Boolean getIsNeedDetectionReport() {
		return isNeedDetectionReport;
	}
	public void setIsNeedDetectionReport(Boolean isNeedDetectionReport) {
		this.isNeedDetectionReport = isNeedDetectionReport;
	}
	public String getQuestionPic() {
		return questionPic;
	}
	public void setQuestionPic(String questionPic) {
		this.questionPic = questionPic;
	}
	public Boolean getIsHasPackage() {
		return isHasPackage;
	}
	public void setIsHasPackage(Boolean isHasPackage) {
		this.isHasPackage = isHasPackage;
	}
	public Integer getPackageDesc() {
		return packageDesc;
	}
	public void setPackageDesc(Integer packageDesc) {
		this.packageDesc = packageDesc;
	}
	public String getAsCustomerDto() {
		return asCustomerDto;
	}
	public void setAsCustomerDto(String asCustomerDto) {
		this.asCustomerDto = asCustomerDto;
	}
	public String getAsPickwareDto() {
		return asPickwareDto;
	}
	public void setAsPickwareDto(String asPickwareDto) {
		this.asPickwareDto = asPickwareDto;
	}
	public String getAsReturnwareDto() {
		return asReturnwareDto;
	}
	public void setAsReturnwareDto(String asReturnwareDto) {
		this.asReturnwareDto = asReturnwareDto;
	}
	public String getAsDetailDto() {
		return asDetailDto;
	}
	public void setAsDetailDto(String asDetailDto) {
		this.asDetailDto = asDetailDto;
	}
	
	
}
