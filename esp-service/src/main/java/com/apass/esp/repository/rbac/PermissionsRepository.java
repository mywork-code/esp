package com.apass.esp.repository.rbac;

import java.util.List;

import com.apass.esp.domain.entity.rbac.PermissionsDO;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 
 * @description Permissions Repository
 *
 * @author lixining
 * @version $Id: PermissionsRepository.java, v 0.1 2016年6月22日 上午11:13:19 lixining Exp $
 */
@MyBatisRepository
public class PermissionsRepository extends BaseMybatisRepository<PermissionsDO, String> {

    /**
     * 删除角色权限表中的资源记录
     */
    public void deleteRolePermissionsByPermissionId(String permissionId) {
        String sql = this.getSQL("deleteRolePermissionsByPermissionId");
        this.getSqlSession().delete(sql, permissionId);
    }

    /**
     * 删除角色权限表中的资源记录
     */
    public List<PermissionsDO> filter(String permissionCode, String neId) {
        PermissionsDO tempPermission = new PermissionsDO();
        tempPermission.setPermissionCode(permissionCode);
        tempPermission.setNeId(neId);
        return this.filter(tempPermission);
    }
}
