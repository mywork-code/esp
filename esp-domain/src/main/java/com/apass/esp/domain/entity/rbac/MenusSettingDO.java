package com.apass.esp.domain.entity.rbac;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @description 菜单 
 * 
 * @author Listening
 * @version $Id: MenusDO.java, v 0.1 2014年10月25日 下午10:49:17 Listening Exp $
 */
@MyBatisEntity
public class MenusSettingDO {
    /**
     * 主键标识id
     */
    private String               id;
    /**
     * 文本标题
     */
    private String               text;
    /**
     * 是否选中
     */
    @JsonIgnore
    private String               checkSign;
    /**
     * 父节点
     */
    private String               parentId;
    /**
     * 子节点
     */
    private List<MenusSettingDO> children;

    public String getCheckSign() {
        return checkSign;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    public List<MenusSettingDO> getChildren() {
        return children;
    }

    public void setChildren(List<MenusSettingDO> children) {
        this.children = children;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean isChecked() {
        return CollectionUtils.isEmpty(children) ? "Y".equals(checkSign) : null;
    }
}
