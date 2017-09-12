package com.apass.esp.domain.dto.activity;

import java.math.BigDecimal;

/**
 * Created by jie.xu on 17/4/24.
 */
public class AwardActivityInfoDto {

    private Long id;

    private String startDate;

    private String endDate;

    private BigDecimal rebate;
    
    private BigDecimal awardAmont;

    private String createBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

	public BigDecimal getAwardAmont() {
		return awardAmont;
	}

	public void setAwardAmont(BigDecimal awardAmont) {
		this.awardAmont = awardAmont;
	}

	@Override
	public String toString() {
		return "AwardActivityInfoDto [id=" + id + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", rebate=" + rebate
				+ ", awardAmont=" + awardAmont + ", createBy=" + createBy + "]";
	}
	
}
