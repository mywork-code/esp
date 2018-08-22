package com.apass.esp.domain.vo.zhongyuan;

/**
 * Created by DELL on 2018/8/22.
 */
public class ZYEmpInfoVo {
    private String CompanyName;
    private String QHRewardType;
    private String EmpName;
    private String EmpTel;

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getQHRewardType() {
        return QHRewardType;
    }

    public void setQHRewardType(String QHRewardType) {
        this.QHRewardType = QHRewardType;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getEmpTel() {
        return EmpTel;
    }

    public void setEmpTel(String empTel) {
        EmpTel = empTel;
    }
}
