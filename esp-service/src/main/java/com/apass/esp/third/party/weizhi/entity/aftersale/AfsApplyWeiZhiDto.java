package com.apass.esp.third.party.weizhi.entity.aftersale;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.third.party.jd.entity.aftersale.AfsApply;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class AfsApplyWeiZhiDto {
    private Long wzOrderId;
    private String token;
    private Long userId;//userId
    private Integer customerExpect;// 客户预期
    private String questionDesc;// 产品问题描述
    private Integer isNeedDetectionReport;// 是否需要检测报告
    private Boolean isNeedDetectionReportBoolean;
    private String questionPic;// 问题描述图片
    private Integer isHasPackage;// 是否有包装
    private Boolean isHasPackageBoolean;
    private Integer packageDesc;// 包装描述
    private AsCustomerDto asCustomerDtok;
    private AsPickwareDto asPickwareDtok;
    private AsReturnwareDto asReturnwareDtok;
    private AsDetailDto asDetailDtok;
    private String asCustomerDto;// AsCustomerDto 客户信息实体
    private String asPickwareDto;// AsPickwareDto 取件信息实体
    private String asReturnwareDto;// AsReturnwareDto 返件信息实体
    private String asDetailDto;// AsDetailDto 申请单明细

    public void initDTOK() {
        this.asCustomerDtok = JSONObject.parseObject(asCustomerDto, AsCustomerDto.class);
        this.asPickwareDtok = JSONObject.parseObject(asPickwareDto, AsPickwareDto.class);
        this.asReturnwareDtok = JSONObject.parseObject(asReturnwareDto, AsReturnwareDto.class);
        this.asDetailDtok = JSONObject.parseObject(asDetailDto, AsDetailDto.class);
    }

    public static <T> String initDTO(T t) {
        return JSON.toJSONString(t);
    }

    public static AfsApplyWeiZhiDto fromOriginalJson(AfsApplyWeiZhiDto afsApply) {
        afsApply.setAsCustomerDto(initDTO(afsApply.asCustomerDtok));
        afsApply.setAsDetailDto(initDTO(afsApply.asDetailDtok));
        afsApply.setAsPickwareDto(initDTO(afsApply.asPickwareDtok));
        afsApply.setAsReturnwareDto(initDTO(afsApply.asReturnwareDtok));
        afsApply.setIsHasPackageBoolean(new Integer(1).equals(afsApply.isHasPackage) ? true : false);
        afsApply.setIsNeedDetectionReportBoolean(new Integer(1).equals(afsApply.isNeedDetectionReport) ? true : false);
        return afsApply;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getNeedDetectionReportBoolean() {
        return isNeedDetectionReportBoolean;
    }

    public void setNeedDetectionReportBoolean(Boolean needDetectionReportBoolean) {
        isNeedDetectionReportBoolean = needDetectionReportBoolean;
    }

    public Boolean getHasPackageBoolean() {
        return isHasPackageBoolean;
    }

    public void setHasPackageBoolean(Boolean hasPackageBoolean) {
        isHasPackageBoolean = hasPackageBoolean;
    }

    public AsCustomerDto getAsCustomerDtok() {
        return asCustomerDtok;
    }

    public void setAsCustomerDtok(AsCustomerDto asCustomerDtok) {
        this.asCustomerDtok = asCustomerDtok;
    }

    public AsPickwareDto getAsPickwareDtok() {
        return asPickwareDtok;
    }

    public void setAsPickwareDtok(AsPickwareDto asPickwareDtok) {
        this.asPickwareDtok = asPickwareDtok;
    }

    public AsReturnwareDto getAsReturnwareDtok() {
        return asReturnwareDtok;
    }

    public void setAsReturnwareDtok(AsReturnwareDto asReturnwareDtok) {
        this.asReturnwareDtok = asReturnwareDtok;
    }

    public AsDetailDto getAsDetailDtok() {
        return asDetailDtok;
    }

    public void setAsDetailDtok(AsDetailDto asDetailDtok) {
        this.asDetailDtok = asDetailDtok;
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

    public Integer getIsNeedDetectionReport() {
        return isNeedDetectionReport;
    }

    public void setIsNeedDetectionReport(Integer isNeedDetectionReport) {
        this.isNeedDetectionReport = isNeedDetectionReport;
    }

    public String getQuestionPic() {
        return questionPic;
    }

    public void setQuestionPic(String questionPic) {
        this.questionPic = questionPic;
    }

    public Integer getIsHasPackage() {
        return isHasPackage;
    }

    public void setIsHasPackage(Integer isHasPackage) {
        this.isHasPackage = isHasPackage;
    }

    public Integer getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(Integer packageDesc) {
        this.packageDesc = packageDesc;
    }

    public Boolean getIsNeedDetectionReportBoolean() {
        return isNeedDetectionReportBoolean;
    }

    public void setIsNeedDetectionReportBoolean(Boolean isNeedDetectionReportBoolean) {
        this.isNeedDetectionReportBoolean = isNeedDetectionReportBoolean;
    }

    public Boolean getIsHasPackageBoolean() {
        return isHasPackageBoolean;
    }

    public void setIsHasPackageBoolean(Boolean isHasPackageBoolean) {
        this.isHasPackageBoolean = isHasPackageBoolean;
    }

    public Long getWzOrderId() {
        return wzOrderId;
    }

    public void setWzOrderId(Long wzOrderId) {
        this.wzOrderId = wzOrderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
