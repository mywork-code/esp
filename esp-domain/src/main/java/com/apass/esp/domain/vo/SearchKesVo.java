package com.apass.esp.domain.vo;

public class SearchKesVo {
	
	private String keyValue;

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public SearchKesVo(String keyValue) {
		super();
		this.keyValue = keyValue;
	}

	public SearchKesVo() {
		super();
	}
	
	public boolean equals(Object obj) {   
        if (obj instanceof SearchKesVo) {   
        	SearchKesVo u = (SearchKesVo) obj;   
            return this.getKeyValue().equals(u.keyValue);   
        }   
        return super.equals(obj);
    }
}
