package com.apass.esp.domain.enums;

/**
 * type: enum
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public enum JdMessageEnum {
    SPLIT_ORDER(1, "拆单消息"),

    DELIVERED_ORDER(5, "订单妥投消息"),

    WITHDRAW_SKU(4, "商品下架消息");
    private int type;
    private String message;

    JdMessageEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
