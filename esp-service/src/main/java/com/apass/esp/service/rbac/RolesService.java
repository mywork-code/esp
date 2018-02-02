package com.apass.esp.service.rbac;

import com.apass.esp.domain.entity.rbac.*;
import com.apass.esp.repository.rbac.RolesRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.jwt.common.ListeningCollectionUtils;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
