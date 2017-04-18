package com.apass.esp.repository.rbac;

import java.util.List;

import com.apass.esp.domain.entity.rbac.RolesDO;
import com.apass.esp.domain.entity.rbac.UserRoleDO;
import com.apass.esp.domain.entity.rbac.UsersDO;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 
 * @description Users Repository
 *
 * @author lixining
 * @version $Id: UsersRepository.java, v 0.1 2016年6月22日 上午11:15:06 lixining Exp $
 */
@MyBatisRepository
public class UsersRepository extends BaseMybatisRepository<UsersDO, String> {

    /**
     *  根据用户查询
     */
    public UsersDO selectByUsername(String username) {
        return getSqlSession().selectOne(getSQL("selectByUserName"), username);
    }

    /**
     * 可用角色列表
     */
    public List<RolesDO> loadAvailableRoles(String userId) {
        return getSqlSession().selectList(getSQL("loadAvailableRoles"), userId);
    }

    /**
     * 已经分配角色列表
     */
    public List<RolesDO> loadAssignedRoles(String userId) {
        return getSqlSession().selectList(getSQL("loadAssignedRoles"), userId);
    }

    /**
     * 删除用户角色列表
     */
    public void deleteUserRolesByUserId(String userId) {
        getSqlSession().delete(getSQL("deleteUserRolesByUserId"), userId);
    }

    /**
     * 插入用户角色
     */
    public void insertUserRole(UserRoleDO userRoleDO) {
        getSqlSession().insert(getSQL("insertUserRole"), userRoleDO);
    }

    /**
     * 是否存在
     */
    public boolean exists(String userName) {
        return selectByUsername(userName) != null;
    }

    /**
     * 重置密码
     */
    public void resetPassword(String username, String password, String operator) {
        UsersDO tempUsersDO = new UsersDO();
        tempUsersDO.setUpdatedBy(operator);
        tempUsersDO.setUserName(username);
        tempUsersDO.setPassword(password);
        getSqlSession().update(getSQL("changePwd"), tempUsersDO);
    }
}
