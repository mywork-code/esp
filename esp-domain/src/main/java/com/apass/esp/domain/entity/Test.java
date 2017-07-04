package com.apass.esp.domain.entity;

import java.math.BigDecimal;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Test {

    private Integer id;

    private Long skuid;

    private BigDecimal price;

    private BigDecimal jdprice;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSkuid() {
        return skuid;
    }

    public void setSkuid(Long skuid) {
        this.skuid = skuid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getJdprice() {
        return jdprice;
    }

    public void setJdprice(BigDecimal jdprice) {
        this.jdprice = jdprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
