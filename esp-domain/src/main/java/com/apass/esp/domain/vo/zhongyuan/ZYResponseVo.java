package com.apass.esp.domain.vo.zhongyuan;

import java.util.List;

/**
 * Created by DELL on 2018/8/22.
 */
public class ZYResponseVo {
    private boolean IsSuccess;
    private Integer Total;
    private List<ZYEmpInfoVo> Result;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public Integer getTotal() {
        return Total;
    }

    public void setTotal(Integer total) {
        Total = total;
    }

    public List<ZYEmpInfoVo> getResult() {
        return Result;
    }

    public void setResult(List<ZYEmpInfoVo> result) {
        Result = result;
    }
}
