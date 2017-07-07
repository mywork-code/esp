package com.apass.esp.third.party.jd.entity.person;

import java.io.Serializable;
import java.util.List;
/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Searchhistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6924023350324989498L;
	private long personId;
	private String searchName;

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}

