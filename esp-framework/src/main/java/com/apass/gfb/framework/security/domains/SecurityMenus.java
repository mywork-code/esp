package com.apass.gfb.framework.security.domains;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @description 菜单
 * @author Listening
 * @version $Id: SecurityMenus.java, v 0.1 2014年11月2日 上午10:48:41 Listening Exp $
 */
public class SecurityMenus implements Serializable {
	/**  */
	private static final long serialVersionUID = -4487765409965920067L;
	/**
	 * Accordion菜单数据
	 */
	private List<SecurityAccordion> securityAccordionList = Lists.newArrayList();
	/**
	 * Accordion Tree Nodes
	 */
	private Map<String, List<SecurityAccordionTree>> accordionTreeListMap = Maps.newHashMap();

	public Map<String, List<SecurityAccordionTree>> getAccordionTreeListMap() {
		return accordionTreeListMap;
	}

	public void setAccordionTreeListMap(Map<String, List<SecurityAccordionTree>> accordionTreeListMap) {
		this.accordionTreeListMap = accordionTreeListMap;
	}

	public List<SecurityAccordion> getSecurityAccordionList() {
		return securityAccordionList;
	}

	public void setSecurityAccordionList(List<SecurityAccordion> securityAccordionList) {
		this.securityAccordionList = securityAccordionList;
	}

	public void addTreeData(String menuId, List<SecurityAccordionTree> dataList) {
		accordionTreeListMap.put(menuId, dataList);
	}

}
