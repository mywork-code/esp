package com.apass.esp.domain.entity.dataanalysis;
import com.apass.esp.domain.entity.DataAppuserRetention;
public class DataAppuserRetentionVo extends DataAppuserRetention {
	private String dayData;
	private String dayType;
    private String day1;
    private String day3;
    private String day7;
    private String day14;
    private String day30;
    private String day7churnuser;
    private String day14churnuser;
    private String day7backuser;
    private String day14backuser;
    public String getDayData() {
		return dayData;
	}
	public void setDayData(String dayData) {
		this.dayData = dayData;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public String getDay1() {
		return day1;
	}
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	public String getDay3() {
		return day3;
	}
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	public String getDay7() {
		return day7;
	}
	public void setDay7(String day7) {
		this.day7 = day7;
	}
	public String getDay14() {
		return day14;
	}
	public void setDay14(String day14) {
		this.day14 = day14;
	}
	public String getDay30() {
		return day30;
	}
	public void setDay30(String day30) {
		this.day30 = day30;
	}
	public String getDay7churnuser() {
		return day7churnuser;
	}
	public void setDay7churnuser(String day7churnuser) {
		this.day7churnuser = day7churnuser;
	}
	public String getDay14churnuser() {
		return day14churnuser;
	}
	public void setDay14churnuser(String day14churnuser) {
		this.day14churnuser = day14churnuser;
	}
	public String getDay7backuser() {
		return day7backuser;
	}
	public void setDay7backuser(String day7backuser) {
		this.day7backuser = day7backuser;
	}
	public String getDay14backuser() {
		return day14backuser;
	}
	public void setDay14backuser(String day14backuser) {
		this.day14backuser = day14backuser;
	}
}