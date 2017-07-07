package com.apass.esp.third.party.jd.entity.base;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * @author xianzhi.wang
 * @time
 */
public class JdApiMessage {
    private long id;
    private JSONObject result;
    private Date time;
    private int type;

    public JdApiMessage() {
    }

    public JdApiMessage(JSONObject jsonObject) {
        this.id = jsonObject.getLongValue("id");
        this.result = jsonObject.getJSONObject("result");
        this.time = jsonObject.getDate("time");
        this.type = jsonObject.getIntValue("type");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JdApiMessage{" +
                "id=" + id +
                ", result=" + result +
                ", time=" + time +
                ", type=" + type +
                '}';
    }
}
