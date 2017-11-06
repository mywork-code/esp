package com.apass.esp.domain.vo;
import com.apass.esp.domain.entity.GoodsAttr;

import java.util.Date;

/**
 * 商品属性
 * @author ht
 * 20171027  sprint11  新增商品属性维护
 */
public class GoodsAttrVo extends GoodsAttr{
    /**
     * 是否关联对应类目
     */
    private boolean flag ;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}