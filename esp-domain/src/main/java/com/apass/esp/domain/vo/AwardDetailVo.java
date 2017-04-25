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
    private Long userId;
    private BigDecimal amount;
    private Byte type;
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
    
    
 

}
