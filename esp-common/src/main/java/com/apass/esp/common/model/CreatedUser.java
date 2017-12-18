package com.apass.esp.common.model;
import java.util.Date;
public class CreatedUser extends QueryParams{
    private String createUser;
    private String updateUser;
    private Date createdTime;
    private Date updatedTime;
    public String getCreateUser() {
        return createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    public void fillUser(String user){
        setUpdateUser(user);
    }
    public void fillTime(){
        setUpdatedTime(new Date());
    }
    public void fillAllUser(String user){
        setCreateUser(user);
        setUpdateUser(user);
    }
    public void fillAllTime(){
        setCreatedTime(new Date());
        setUpdatedTime(new Date());
    }
    public void fillAllField(String user){
        fillAllUser(user);
        fillAllTime();
    }
    public void fillField(String user){
        fillUser(user);
        fillTime();
    }
}