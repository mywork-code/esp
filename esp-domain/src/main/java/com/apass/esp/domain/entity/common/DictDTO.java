package com.apass.esp.domain.entity.common;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 字典对象
 * 
 * @description
 *
 * @author zengqingshan
 * @version $Id: DictDTO.java, v 0.1 2016年12月22日 上午9:25:26 zengqingshan Exp $
 */

@MyBatisEntity
public class DictDTO {
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 城市首字母
	 */
	private String prefix;

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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
