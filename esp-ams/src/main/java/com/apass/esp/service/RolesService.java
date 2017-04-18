package com.apass.esp.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.rbac.MenusSettingDO;
import com.apass.esp.domain.entity.rbac.PermissionsDO;
import com.apass.esp.domain.entity.rbac.RoleMenuDO;
import com.apass.esp.domain.entity.rbac.RolePermissionDO;
import com.apass.esp.domain.entity.rbac.RolesDO;
import com.apass.esp.repository.rbac.RolesRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.jwt.common.ListeningCollectionUtils;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.google.common.collect.Lists;

/**
 * 
 * @description Role Service
 *
 * @author lixining
 * @version $Id: RolesService.java, v 0.1 2016年6月23日 下午4:25:04 lixining Exp $
 */
@Component
public class RolesService {
    /**
     * Roles Repository
     */
    @Autowired
    private RolesRepository rolesRepository;

    /**
     * Role Page List
     */
    public PaginationManage<RolesDO> page(RolesDO paramDO, Page page) {
        PaginationManage<RolesDO> result = new PaginationManage<RolesDO>();
        Pagination<RolesDO> response = rolesRepository.page(paramDO, page);
        result.setDataList(response.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(response.getTotalCount());
        return result;
    }

    /**
     * 删除角色
     */
    @Transactional
    public void delete(String roleId) {
        rolesRepository.deleteRolePermissionsByRoleId(roleId);
        rolesRepository.deleteUserRolesByRoleId(roleId);
        rolesRepository.deleteRoleMenusByRoleId(roleId);
        rolesRepository.delete(roleId);
    }

    /**
     * 保存角色
     */
    public void save(RolesDO role) throws BusinessException {
        String id = role.getId();
        String operator = SpringSecurityUtils.getCurrentUser();
        String roleCode = role.getRoleCode();
        List<RolesDO> dataList = rolesRepository.filter(roleCode, StringUtils.isBlank(id) ? null : id);
        if (!CollectionUtils.isEmpty(dataList)) {
            throw new BusinessException("角色编码已存在");
        }
        if (StringUtils.isBlank(id)) {
            role.setCreatedBy(operator);
            role.setUpdatedBy(operator);
            rolesRepository.insert(role);
            return;
        }
        RolesDO rolesDB = rolesRepository.select(id);
        if (rolesDB == null) {
            throw new BusinessException("记录不存在,无法更新, 请刷新列表后重试");
        }
        rolesDB.setUpdatedBy(operator);
        rolesDB.setRoleCode(role.getRoleCode());
        rolesDB.setRoleName(role.getRoleName());
        rolesDB.setDescription(role.getDescription());
        rolesRepository.updateAll(rolesDB);
    }

    /**
     * 主键加载
     */
    public RolesDO select(String roleId) {
        return rolesRepository.select(roleId);
    }

    /**
     * Select Role Menu Settings
     */
    public List<MenusSettingDO> selectRoleMenuSettings(String roleId) {
        List<MenusSettingDO> resultList = Lists.newArrayList();
        if (StringUtils.isBlank(roleId)) {
            return resultList;
        }
        MenusSettingDO rootMenu = new MenusSettingDO();
        rootMenu.setChildren(rolesRepository.selectRoleMenuSettings(roleId, "root"));
        rootMenu.setText("后台管理系统菜单树");
        rootMenu.setId("root");
        rootMenu.setCheckSign("Y");
        resultList.add(rootMenu);
        return resultList;
    }

    /**
     * 保存角色菜单设置
     */
    @Transactional
    public void saveRoleMenuSettings(String roleId, String menus) {
        // 删除角色菜单历史记录操作
        rolesRepository.deleteRoleMenusByRoleId(roleId);
        // 插入新数据
        Set<String> menuIds = ListeningCollectionUtils.tokenizeToSet(menus, ",");
        menuIds.remove("root");
        if (CollectionUtils.isEmpty(menuIds)) {
            return;
        }
        String operator = SpringSecurityUtils.getCurrentUser();
        for (String content : menuIds) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setMenuId(content);
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setCreatedBy(operator);
            roleMenuDO.setUpdatedBy(operator);
            rolesRepository.insertRoleMenu(roleMenuDO);
        }
    }

    /**
     * 加载已分配资源列表
     * 
     * @param roleId
     */
    public List<PermissionsDO> loadAssignedPermissions(String roleId) {
        return rolesRepository.selectAllocatedPermissions(roleId);
    }

    /**
     * 加载可用资源列表
     * 
     * @param roleId
     */
    public List<PermissionsDO> loadAvailablePermissions(String roleId) {
        return rolesRepository.selectAvailablePermissions(roleId);
    }

    /**
     * 保存已分配角色资源列表
     * 
     * @param roleId
     * @param permissions
     * @return
     */
    @Transactional
    public void saveAssignedPermissions(String roleId, String permissions) {
        // 删除角色菜单历史记录操作
        rolesRepository.deleteRolePermissionsByRoleId(roleId);
        // 插入新数据
        Set<String> permissionIds = ListeningCollectionUtils.tokenizeToSet(permissions, ",");
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }
        String operator = SpringSecurityUtils.getCurrentUser();
        for (String permission : permissionIds) {
            RolePermissionDO rolePermissionDO = new RolePermissionDO();
            rolePermissionDO.setPermissionId(permission);
            rolePermissionDO.setRoleId(roleId);
            rolePermissionDO.setCreatedBy(operator);
            rolePermissionDO.setUpdatedBy(operator);
            rolesRepository.insertRolePermission(rolePermissionDO);
        }
    }
    /**
     * 角色权限查看
     * 
     * @param roleId
     */
    public  List<GrantedAuthority> queryGrantedAuthorityList() {
    	 //动态权限设置
        Object principal = SpringSecurityUtils.getAuthentication().getPrincipal();
        if (principal == null || !(principal instanceof ListeningCustomSecurityUserDetails)) {
            return null;
        }
        ListeningCustomSecurityUserDetails details = (ListeningCustomSecurityUserDetails) principal;
        List<GrantedAuthority> list = (List<GrantedAuthority>) details.getAuthorities();
        return list;
    }
}
