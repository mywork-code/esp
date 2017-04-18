package com.apass.gfb.framework.security.domains;

import java.io.Serializable;

/**
 * @description ·Accordion Menu菜单
 * @author Listening
 * @version $Id: SecurityAccordion.java, v 0.1 2014年9月28日 下午7:47:37 Listening Exp $
 */
public class SecurityAccordion implements Serializable {
    /**  */
    private static final long serialVersionUID = -5607240827673494561L;
    /**
     * 节点ID
     */
    private String            id;
    /**
     * 节点文本
     */
    private String            text;
    /**
     * 节点图标
     */
    private String            iconCls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

}
