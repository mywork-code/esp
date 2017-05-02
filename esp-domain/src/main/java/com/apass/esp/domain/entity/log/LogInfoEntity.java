package com.apass.esp.domain.entity.log;

import java.io.Serializable;

import com.apass.gfb.framework.annotation.MyBatisEntity;
/**
 * 
 * @author pengyingchao
 * 日志实体类
 */
@MyBatisEntity
public class LogInfoEntity implements Serializable{
    
    /**
     * 主键标识ID
     */
    private Long id;
    
    /**
     * 操作类型 （insert update delete）
     */
    private String operationType;
    
    /**
     * 操作内容
     */
    private String content;
    
    /**
     * 创建日期
     */
    private String createDate;
    
    /**
     * 操作人
     */
    private String createUser;
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public String getOperationType() {
        return operationType;
    }
    
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
}
