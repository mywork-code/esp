package com.apass.esp.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 奖励明细表 返回前端数据 
 *
 * @author xiaohai
 * @version $Id: AwardDetailVo.java, v 0.1 2017年6月6日 下午8:15:21 xiaohai Exp $
 */
public class AwardDetailVo {
    /**
     * 用户Id
     */
    private Long userId;
    
    /**
     * 获得或提现金额
     */
    private BigDecimal amount;
    
    /**
     * 类型：0，获得，1提现
     */
    private Byte type;
    
    /**
     * 状态：0成功，1失败，2处理中
     */
    private Byte status;
    
    /**
     * 到账时间
     */
    private String arrivedDate;
    
    /**
     * 提前时间
     */
    private String createDate;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Byte getType() {
        return type;
    }
    public void setType(Byte type) {
        this.type = type;
    }
   
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public String getArrivedDate() {
        return arrivedDate;
    }
    public void setArrivedDate(String arrivedDate) {
        this.arrivedDate = arrivedDate;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
          
    

}
