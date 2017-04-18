package com.apass.esp.domain.entity.common;

import com.apass.gfb.framework.annotation.MyBatisEntity;
/**
 * 自增序列
 * @description 
 *
 * @author liuming
 * @version $Id: SequenceEntity.java, v 0.1 2017年3月14日 上午10:55:26 liuming Exp $
 */
@MyBatisEntity
public class SequenceEntity {

    /**
     * 自增主键Id
     */
    private Long id;
    /**
     * 用户Id
     */
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    
}
