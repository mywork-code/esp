package com.apass.esp.third.party.jd.entity.order;

import java.io.Serializable;
import java.util.Date;
/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class OrderTrack implements Serializable {

    //日期
    private Date msgTime;

    //内容信息
    private String content;

    //操作人
    private String operator;


    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
