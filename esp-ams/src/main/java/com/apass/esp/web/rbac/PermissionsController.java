package com.apass.esp.web.rbac;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.rbac.PermissionsDO;
import com.apass.esp.service.PermissionsService;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

/**
 * 
 * @description RBAC-资源管理
 *
 * @author lixining
 * @version $Id: PermissionsController.java, v 0.1 2016年6月22日 上午11:15:57 lixining Exp $
 */
@Controller
@RequestMapping("/application/rbac/permission")
public class PermissionsController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(PermissionsController.class);
    /**
     * Permission Service
     */
    @Autowired
    private PermissionsService  permissionsService;

    /**
     * 资源页面
     */
    @RequestMapping("/page")
    public String handlePage() {
        return "rbac/permissions-page";
    }

    /**
     * 资源列表
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<PermissionsDO> handlePageList(HttpServletRequest request) {
        ResponsePageBody<PermissionsDO> respBody = new ResponsePageBody<PermissionsDO>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            Integer pageNoNum = Integer.parseInt(pageNo);
            Integer pageSizeNum = Integer.parseInt(pageSize);
            Page page = new Page();
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);
            String permissionCode = HttpWebUtils.getValue(request, "permissionCode");
            String permissionName = HttpWebUtils.getValue(request, "permissionName");
            PermissionsDO paramDO = new PermissionsDO();
            paramDO.setPermissionCode(StringUtils.isBlank(permissionCode) ? null : permissionCode);
            paramDO.setPermissionName(StringUtils.isBlank(permissionName) ? null : permissionName);
            PaginationManage<PermissionsDO> pagination = permissionsService.page(paramDO, page);
            respBody.setTotal(pagination.getTotalCount());
            respBody.setRows(pagination.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("用户列表查询失败", e);
            respBody.setMsg("用户列表查询失败");
        }
        return respBody;
    }

    /**
     * 保存资源
     */
    @ResponseBody
    @RequestMapping("/load")
    public Response handleLoad(HttpServletRequest request) {
        try {
            String permissionId = HttpWebUtils.getValue(request, "permissionId", null);
            if (StringUtils.isBlank(permissionId)) {
                return Response.fail("资源ID不能为空");
            }
            PermissionsDO permission = permissionsService.select(permissionId);
            if (permission == null) {
                throw new BusinessException("资源记录不存在,请刷新列表后重试");
            }
            return Response.success("success", permission);
        } catch (BusinessException e) {
            LOG.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.error("加载资源失败", e);
            return Response.fail("加载资源记录失败");
        }
    }

    /**
     * 保存资源
     */
    @ResponseBody
    @RequestMapping("/save")
    public Response handleSave(HttpServletRequest request) {
        try {
            String permissionId = HttpWebUtils.getValue(request, "id", null);
            String permissionCode = HttpWebUtils.getValue(request, "permissionCode");
            String permissionName = HttpWebUtils.getValue(request, "permissionName");
            String description = HttpWebUtils.getValue(request, "description");
            if (StringUtils.isAnyBlank(permissionCode, permissionName)) {
                return Response.fail("资源编码或资源名称不能为空");
            }
            if (!RegExpUtils.length(permissionCode, 1, 100)) {
                return Response.fail("资源编码长度不合法");
            }
            if (!RegExpUtils.length(permissionName, 1, 100)) {
                return Response.fail("资源名称长度不合法");
            }
            if (!RegExpUtils.length(description, 0, 200)) {
                return Response.fail("资源描述长度不合法");
            }
            PermissionsDO permission = new PermissionsDO();
            permission.setId(permissionId);
            permission.setPermissionCode(permissionCode);
            permission.setPermissionName(permissionName);
            permission.setDescription(description);
            // 删除资源记录 
            permissionsService.save(permission);
            return Response.success("success");
        } catch (BusinessException e) {
            LOG.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.error("保存资源失败", e);
            return Response.fail("保存资源记录失败");
        }
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Response handleDelete(HttpServletRequest request) {
        try {
            String permissionId = HttpWebUtils.getValue(request, "permissionId");
            if (StringUtils.isBlank(permissionId)) {
                return Response.fail("资源ID不能为空");
            }
            // 删除资源记录 
            permissionsService.delete(permissionId);
            return Response.success("success");
        } catch (Exception e) {
            LOG.error("删除资源失败", e);
            return Response.fail("删除资源记录失败");
        }
    }
}
