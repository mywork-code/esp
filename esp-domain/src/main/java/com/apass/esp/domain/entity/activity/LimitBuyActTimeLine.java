package com.apass.esp.domain.entity.activity;
import java.util.Date;
import java.util.List;
import com.apass.esp.domain.entity.LimitGoodsSku;
/**
 * 限时购活动  前台展示专用   封装时间条   时间状态   商品列表等信息
 * @author Administrator
 *
 */
public class LimitBuyActTimeLine{
    //所有数据 只显示 status ==1（活动未开始） 和 status ==2（活动进行中）  status==3(活动已过期)不显示
    
    private String atPresent;
    //当前活动抢购中为1   其余所有都有0;   表示该活动处于页面显示正中间
    
    private String time;
    //yesterday:28日 14:00  28日 18:00  28日 22:00;
    //today:10:00  14:00  18:00  22:00;
    //tomorrow:明日 10:00  明日 14:00  明日 18:00  明日 22:00
    
    private String actDesc;
    //活动状态描述  status ==1（显示 ：即将开始）   status ==2&&atPresent==1（显示 ：抢购中）     status ==2&&atPresent==0（显示 ：已开抢）
    //抢购中   和已开抢 数据库状态相同都是（活动进行中）,抢购中标识（活动进行中）最新唯一活动，已开抢标识所有（活动进行中）的活动除开最新开始的活动
    public String getAtPresent() {
        return atPresent;
    }
    public void setAtPresent(String atPresent) {
        this.atPresent = atPresent;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getActDesc() {
        return actDesc;
    }
    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }
    
    
    private String sort;//1-8
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    
    private String limitBuyActId;//冗余活动ID ==活动ID
    private Date startDate;//冗余活动开始日期 ==活动开始日期
    private Byte status;//冗余活动状态 ==活动状态
    private List<LimitGoodsSku> list;//活动商品列表
    public String getLimitBuyActId() {
        return limitBuyActId;
    }
    public void setLimitBuyActId(String limitBuyActId) {
        this.limitBuyActId = limitBuyActId;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public List<LimitGoodsSku> getList() {
        return list;
    }
    public void setList(List<LimitGoodsSku> list) {
        this.list = list;
    }
}