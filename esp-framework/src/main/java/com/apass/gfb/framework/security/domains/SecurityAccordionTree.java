package com.apass.gfb.framework.security.domains;

import java.io.Serializable;
import java.util.List;

/**
 * @description Accordion下面的树节点信息
 * @author listening
 * @version $Id: SecurityAccordionTreeNode.java, v 0.1 2015年4月6日 上午10:35:22 listening Exp $
 */
public class SecurityAccordionTree implements Serializable {
    /**  */
    private static final long           serialVersionUID = 5978231444874854329L;
    /**
     * 节点ID
     */
    private String                      id;
    /**
     * 节点文本显示值
     */
    private String                      text;
    /**
     * 节点图标
     */
    private String                      iconCls;
    /**
     * 节点链接
     */
    private String                      url;
    /**
     * 是否叶子节点
     */
    private boolean                     leaf;
    /**
     * 父节点ID
     */
    private String                      parentId;
    /**
     * 是否展开
     */
    private boolean                     expanded         = true;
    /**
     * 下级子菜单
     */
    private List<SecurityAccordionTree> children;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List<SecurityAccordionTree> getChildren() {
        return children;
    }

    public void setChildren(List<SecurityAccordionTree> children) {
        this.children = children;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

}
