package com.apass.esp.web.rbac;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.rbac.MenusSettingDO;
import com.apass.esp.domain.entity.rbac.PermissionsDO;
import com.apass.esp.domain.entity.rbac.RolesDO;
import com.apass.esp.service.RolesService;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

/**
 * 
 * @description 角色管理
 *
 * @author lixining
 * @version $Id: UsersController.java, v 0.1 2016年6月22日 上午11:15:57 lixining Exp $
 */
@Controller
@RequestMapping("/application/rbac/role")
public class RolesController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(RolesController.class);
    /**
     * role Service
     */
    @Autowired
    private RolesService        rolesService;

    /**
     * 角色界面
     */
    @RequestMapping("/page")
    public String handlePage() {
        return "rbac/roles-page";
    }

    /**
     * 角色列表JSON
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<RolesDO> handlePageList(HttpServletRequest request) {
        ResponsePageBody<RolesDO> respBody = new ResponsePageBody<RolesDO>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            Integer pageNoNum = Integer.parseInt(pageNo);
            Integer pageSizeNum = Integer.parseInt(pageSize);
            Page page = new Page();
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);
            String roleCode = HttpWebUtils.getValue(request, "roleCode");
            String roleName = HttpWebUtils.getValue(request, "roleName");
            RolesDO paramDO = new RolesDO();
            paramDO.setRoleCode(roleCode);
            paramDO.setRoleName(roleName);
            PaginationManage<RolesDO> pagination = rolesService.page(paramDO, page);
            respBody.setTotal(pagination.getTotalCount());
            respBody.setRows(pagination.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("列表查询失败", e);
            respBody.setMsg("列表查询失败");
        }
        return respBody;
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    public Response handleSave(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "id", null);
            String roleCode = HttpWebUtils.getValue(request, "roleCode");
            String roleName = HttpWebUtils.getValue(request, "roleName");
            String description = HttpWebUtils.getValue(request, "description");
            if (StringUtils.isAnyBlank(roleCode, roleName)) {
                return Response.fail("编码或角色名称不能为空");
            }
            if (!RegExpUtils.length(roleCode, 1, 100)) {
                return Response.fail("角色编码长度不合法");
            }
            if (!RegExpUtils.length(roleName, 1, 100)) {
                return Response.fail("角色名称长度不合法");
            }
            if (!RegExpUtils.length(description, 0, 200)) {
                return Response.fail("角色描述长度不合法");
            }
            RolesDO role = new RolesDO();
            role.setId(roleId);
            role.setRoleCode(roleCode);
            role.setRoleName(roleName);
            role.setDescription(description);
            // 删除角色记录 
            rolesService.save(role);
            return Response.success("success");
        } catch (BusinessException e) {
            LOG.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.error("保存角色失败", e);
            return Response.fail("保存角色记录失败");
        }
    }

    /**
     * 删除角色
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Response handleDelete(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "roleId");
            if (StringUtils.isBlank(roleId)) {
                return Response.fail("角色ID不能为空");
            }
            // 删除角色记录 
            rolesService.delete(roleId);
            return Response.success("success");
        } catch (Exception e) {
            LOG.error("删除角色失败", e);
            return Response.fail("删除角色记录失败");
        }
    }

    /**
     * 删除角色
     */
    @ResponseBody
    @RequestMapping("/load")
    public Response handleLoad(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "roleId");
            if (StringUtils.isBlank(roleId)) {
                return Response.fail("角色ID不能为空");
            }
            // 删除角色记录 
            RolesDO rolesDO = rolesService.select(roleId);
            if (rolesDO == null) {
                throw new BusinessException("角色记录不存在,请刷新列表后重试");
            }
            return Response.success("success", rolesDO);
        } catch (BusinessException e) {
            LOG.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.error("删除角色失败", e);
            return Response.fail("删除角色记录失败");
        }
    }

    /**
     * 加载角色菜单设置
     */
    @ResponseBody
    @RequestMapping("/load/rolemenu/settings")
    public Response handleLoadRoleMenuSettings(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "roleId");
            List<MenusSettingDO> menuList = rolesService.selectRoleMenuSettings(roleId);
            return Response.success("success", menuList);
        } catch (Exception e) {
            LOG.error("加载角色菜单失败", e);
            return Response.fail("加载角色菜单失败");
        }
    }

    /**
     * 保存角色菜单设置
     */
    @ResponseBody
    @RequestMapping("/save/rolemenu/settings")
    public Response handleSaveRoleMenuSettings(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "roleId");
            String menus = HttpWebUtils.getValue(request, "menus");
            if (StringUtils.isBlank(roleId)) {
                return Response.fail("角色ID不能为空");
            }
            rolesService.saveRoleMenuSettings(roleId, menus);
            return Response.success("success");
        } catch (Exception e) {
            LOG.error("角色菜单保存失败", e);
            return Response.fail("角色菜单设置失败");
        }
    }

    /**
     * 角色资源可用列表
     */
    @ResponseBody
    @RequestMapping("/load/available/permissions")
    public Response handleLoadAvailablePermissions(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "roleId");
            if (StringUtils.isBlank(roleId)) {
                return null;
            }
            List<PermissionsDO> permissionList = rolesService.loadAvailablePermissions(roleId);
            return Response.success("success", permissionList);
        } catch (Exception e) {
            LOG.error("加载可分配资源失败", e);
            return Response.fail("加载可分配资源失败");
        }
    }

    /**
     * 角色资源已分配列表
     */
    @ResponseBody
    @RequestMapping("/load/assigned/permissions")
    public Response handleLoadAssignedPermissions(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "roleId");
            if (StringUtils.isBlank(roleId)) {
                return null;
            }
            List<PermissionsDO> permissionList = rolesService.loadAssignedPermissions(roleId);
            return Response.success("success", permissionList);
        } catch (Exception e) {
            LOG.error("加载已分配资源失败", e);
            return Response.fail("加载已分配资源失败");
        }
    }

    /**
     * 保存资源列表
     */
    @ResponseBody
    @RequestMapping("/save/assigned/permissions")
    public Response handleSaveAssignedPermissions(HttpServletRequest request) {
        try {
            String roleId = HttpWebUtils.getValue(request, "roleId");
            String permissions = HttpWebUtils.getValue(request, "permissions");
            if (StringUtils.isBlank(roleId)) {
                return Response.fail("角色ID不能为空");
            }
            rolesService.saveAssignedPermissions(roleId, permissions);
            return Response.success("success");
        } catch (Exception e) {
            LOG.error("保存资源分配记录失败", e);
            return Response.fail("保存资源分配记录失败");
        }
    }
}
