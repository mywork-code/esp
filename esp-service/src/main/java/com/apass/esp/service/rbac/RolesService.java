package com.apass.esp.service.rbac;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.apass.esp.domain.entity.rbac.RoleMenuDO;
import com.apass.esp.repository.rbac.RolesRepository;
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
     * Select Role Menu Settings
     */
    public List<RoleMenuDO> selectRoleMenuByRoleId(String roleId) {
        return rolesRepository.selectRoleMenuByRoleId(roleId);
    }

}
