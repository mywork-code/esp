package com.apass.esp.third.party.jd.entity.person;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class ShoppingCart implements Serializable {
    private int id;
    private long personId;
    private long skuId;
    private int num;
    private int isSelected;
    private BigDecimal price;
    private BigDecimal jdPrice;
    private BigDecimal allPrice;
    private String imagePath;
    private String name;
    private int state;
    private int saleState;
    private String stockStateDesc;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

    public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

	public int getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}

	public BigDecimal getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}
	
	public BigDecimal getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(BigDecimal allPrice) {
		this.allPrice = allPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSaleState() {
		return saleState;
	}

	public void setSaleState(int saleState) {
		this.saleState = saleState;
	}

	public String getStockStateDesc() {
		return stockStateDesc;
	}

	public void setStockStateDesc(String stockStateDesc) {
		this.stockStateDesc = stockStateDesc;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
