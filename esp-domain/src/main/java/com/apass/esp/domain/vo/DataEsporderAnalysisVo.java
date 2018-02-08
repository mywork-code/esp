package com.apass.esp.domain.vo;
import java.util.List;
import com.apass.esp.domain.entity.DataEsporderAnalysis;
public class DataEsporderAnalysisVo extends DataEsporderAnalysis {
	private String dayData;
	private String registeruser;
	private String activeuser;
	private String percent;
	private List<DataEsporderdetailVo> list;
	public String getDayData() {
		return dayData;
	}
	public void setDayData(String dayData) {
		this.dayData = dayData;
	}
	public String getRegisteruser() {
		return registeruser;
	}
	public void setRegisteruser(String registeruser) {
		this.registeruser = registeruser;
	}
	public String getActiveuser() {
		return activeuser;
	}
	public void setActiveuser(String activeuser) {
		this.activeuser = activeuser;
	}
	public List<DataEsporderdetailVo> getList() {
		return list;
	}
	public void setList(List<DataEsporderdetailVo> list) {
		this.list = list;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
}