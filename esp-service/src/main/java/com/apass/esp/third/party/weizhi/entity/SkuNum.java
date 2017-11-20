package com.apass.esp.third.party.weizhi.entity;

import java.io.Serializable;
/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年11月16日 下午5:14:23 
 * @description
 */
import java.math.BigDecimal;
public class SkuNum implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long skuId;
    private int num;
    private BigDecimal price;
    
    private boolean bNeedAnnex = true;//表示是否需要附件，默认每个订单都给附件，如果为false,不会给客户发附件 TODO
    private boolean bNeedGift = false;//表示是否需要赠品

    public SkuNum(long skuId, int num) {
        this.skuId = skuId;
        this.num = num;
    }
    
    public SkuNum(long skuId, int num, boolean bNeedAnnex, boolean bNeedGift) {
		super();
		this.skuId = skuId;
		this.num = num;
		this.bNeedAnnex = bNeedAnnex;
		this.bNeedGift = bNeedGift;
	}


	public SkuNum() {
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
    
    public boolean isbNeedAnnex() {
		return bNeedAnnex;
	}

	public void setbNeedAnnex(boolean bNeedAnnex) {
		this.bNeedAnnex = bNeedAnnex;
	}

	public boolean isbNeedGift() {
		return bNeedGift;
	}

	public void setbNeedGift(boolean bNeedGift) {
		this.bNeedGift = bNeedGift;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkuNum)) return false;

        SkuNum skuNum = (SkuNum) o;

        if (skuId != skuNum.skuId) return false;
        return num == skuNum.num;

    }

    @Override
    public int hashCode() {
        int result = (int) (skuId ^ (skuId >>> 32));
        result = 31 * result + num;
        return result;
    }

}
