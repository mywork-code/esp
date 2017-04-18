package com.apass.esp.domain.entity.rbac.domain;

import java.util.List;

/**
 * 
 * @description Security Menus Model
 *
 * @author lixining
 * @version $Id: SecurityMenusModel.java, v 0.1 2016年6月28日 下午1:45:19 lixining Exp $
 */
public class SecurityMenusModel {
    /**
     * 主键标识id
     */
    private String                   id;
    /**
     * 文本标题
     */
    private String                   text;
    /**
     * 链接地址
     */
    private String                   url;
    /**
     * 子节点
     */
    private List<SecurityMenusModel> children;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SecurityMenusModel> getChildren() {
        return children;
    }

    public void setChildren(List<SecurityMenusModel> children) {
        this.children = children;
    }

}
