package com.apass.esp.domain.enums;

/**
 * @description 物流状态枚举类
 *
 * @author dell
 * @version $Id: TrackingmoreStatus.java, v 0.1 2017年2月21日 下午6:04:38 dell Exp $
 */
public enum TrackingmoreStatus {

                                PENDING("pending", "查询中"),        //新增包裹正在查询中

                                NOTFOUND("notfound", "查询不到"),        //包裹目前查询不到

                                TRANSIT("transit", "运输中"),    //包裹正从发件国运往目的国

                                PICKUP("pickup", "派送中"),    //包裹正在派送中或已到达当地收发点，你可以继续派送或收件

                                DELIVERED("delivered", "已签收"),     //包裹已成功妥投

                                UNDELIVERED("undelivered", "投递失败"),  //快递员尝试过投递但失败

                                EXCEPTION("exception", "问题件"),   //包裹出现异常

                                EXPIRED("expired", "无结果"); //一直没有派送结果

    private String code;

    private String message;

    private TrackingmoreStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
