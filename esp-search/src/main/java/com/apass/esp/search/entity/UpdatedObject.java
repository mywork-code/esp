package com.apass.esp.search.entity;

import com.apass.esp.search.enums.OperatorType;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/15
 * @see
 * @since JDK 1.8
 */
public class UpdatedObject <T> {
    private T object;

    private OperatorType type;

    public UpdatedObject(T object, OperatorType type) {
        this.object = object;
        this.type = type;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public OperatorType getType() {
        return type;
    }

    public void setType(OperatorType type) {
        this.type = type;
    }
}
