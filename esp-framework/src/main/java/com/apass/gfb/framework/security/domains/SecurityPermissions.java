package com.apass.gfb.framework.security.domains;

import java.io.Serializable;

/**
 * 
 * @description Shiro权限
 * 
 * @author Listening
 * @version $Id: ShiroPermission.java, v 0.1 2014年11月2日 上午10:42:22 Listening Exp $
 */
public class SecurityPermissions implements Serializable {
    /**  */
    private static final long serialVersionUID = -9155553437010778403L;

    /**
     * ID
     */
    private String            id;

    /**
     * 权限
     */
    private String            permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
