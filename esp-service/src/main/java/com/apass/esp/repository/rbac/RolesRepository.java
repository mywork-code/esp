package com.apass.esp.repository.rbac;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.apass.esp.domain.entity.rbac.MenusSettingDO;
import com.apass.esp.domain.entity.rbac.PermissionsDO;
import com.apass.esp.domain.entity.rbac.RoleMenuDO;
import com.apass.esp.domain.entity.rbac.RolePermissionDO;
import com.apass.esp.domain.entity.rbac.RolesDO;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;
import com.google.common.collect.Maps;

/**
 * 
 * @description Role Repository
 *
 * @author lixining
 * @version $Id: RolesRepository.java, v 0.1 2016年6月22日 上午11:14:16 lixining Exp $
 */
@MyBatisRepository
public class RolesRepository extends BaseMybatisRepository<RolesDO, String> {

    /**
     *  根据角色ID删除角色权限表记录
     */
    public void deleteRolePermissionsByRoleId(String roleId) {
        String sql = this.getSQL("deleteRolePermissionsByRoleId");
        this.getSqlSession().delete(sql, roleId);
    }

    /**
     * 根据角色ID删除角色菜单表
     */
    public void deleteRoleMenusByRoleId(String roleId) {
        String sql = this.getSQL("deleteRoleMenusByRoleId");
        this.getSqlSession().delete(sql, roleId);
    }

    /**
     * 过滤角色编码
     */
    public List<RolesDO> filter(String roleCode, String id) {
        RolesDO rolesDO = new RolesDO();
        rolesDO.setRoleCode(roleCode);
        rolesDO.setNeId(id);
        return filter(rolesDO);
    }

    /**
     * 根据角色ID删除用户角色表记录
     */
    public void deleteUserRolesByRoleId(String roleId) {
        String sql = this.getSQL("deleteUserRolesByRoleId");
        this.getSqlSession().delete(sql, roleId);
    }

    /**
     * 角色菜单设置列表
     */
    public List<MenusSettingDO> selectRoleMenuSettings(String roleId, String parentId) {
        String sql = this.getSQL("selectMenuSettingList");
        Map<String, String> paramsMap = Maps.newHashMap();
        paramsMap.put("roleId", roleId);
        paramsMap.put("parentId", parentId);
        List<MenusSettingDO> tempList = this.getSqlSession().selectList(sql, paramsMap);
        if (CollectionUtils.isEmpty(tempList)) {
            return null;
        }
        for (MenusSettingDO menu : tempList) {
            menu.setChildren(selectRoleMenuSettings(roleId, menu.getId()));
        }
        return tempList;
    }

    /**
     * 角色菜单设置列表
     */
    public void insertRoleMenu(RoleMenuDO roleMenuDO) {
        String sql = this.getSQL("insertRoleMenu");
        this.getSqlSession().insert(sql, roleMenuDO);
    }

    /**
     * selectAvailablePermissions
     */
    public List<PermissionsDO> selectAvailablePermissions(String roleId) {
        return this.getSqlSession().selectList(getSQL("selectAvailablePermissions"), roleId);
    }

    /**
     * selectAllocatedPermissions
     */
    public List<PermissionsDO> selectAllocatedPermissions(String roleId) {
        return this.getSqlSession().selectList(getSQL("selectAllocatedPermissions"), roleId);
    }

    /**
     * insertRoleMenu
     */
    public void insertRolePermission(RolePermissionDO rolePermissionDO) {
        String sql = this.getSQL("insertRolePermission");
        this.getSqlSession().insert(sql, rolePermissionDO);
    }
}
