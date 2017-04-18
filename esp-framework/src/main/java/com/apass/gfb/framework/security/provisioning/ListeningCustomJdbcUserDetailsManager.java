package com.apass.gfb.framework.security.provisioning;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import com.apass.gfb.framework.security.userdetails.ListeningCustomJdbcDaoImpl;


/**
 * 
 * @description 备注：此类只是占位作用,不参与任何功能
 *
 * @author lixining
 * @version $Id: ListeningCustomJdbcUserDetailsManager.java, v 0.1 2016年6月22日 上午10:17:33 lixining Exp $
 */
public class ListeningCustomJdbcUserDetailsManager extends ListeningCustomJdbcDaoImpl
                                                   implements UserDetailsManager, GroupManager {

    protected final Log logger = LogFactory.getLog(getClass());

    public void setUserCache(UserCache userCache) {
        Assert.notNull(userCache, "userCache cannot be null");
    }

    @Override
    public List<String> findAllGroups() {
        return null;
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        return null;
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
    }

    @Override
    public void deleteGroup(String groupName) {
    }

    @Override
    public void renameGroup(String oldName, String newName) {
    }

    @Override
    public void addUserToGroup(String username, String group) {
    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        return null;
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
    }

    @Override
    public void createUser(UserDetails user) {
    }

    @Override
    public void updateUser(UserDetails user) {
    }

    @Override
    public void deleteUser(String username) {
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

}
