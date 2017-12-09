package com.apass.esp.third.party.weizhi.entity.aftersale;

/**
 * 根据订单号、商品编号查询支持的服务类型实体类
 * 根据订单号、商品编号查询支持的商品返回微知方式
 */
public class ComponentExport {
    /**
     * 服务类型码
     */
    private String code;
    /**
     * 服务类型名称
     */
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

