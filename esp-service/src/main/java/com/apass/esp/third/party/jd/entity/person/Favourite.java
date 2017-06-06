package com.apass.esp.third.party.jd.entity.person;

import java.io.Serializable;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Favourite implements Serializable {
    private long id;
    private long skuId;
    private long personId;
    private String mallId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

    
}
