package com.apass.esp.web.rbac;

import java.net.URLDecoder;
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
import com.apass.esp.domain.entity.rbac.MenusDO;
import com.apass.esp.service.MenusService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

/**
 * 
 * @description 菜单管理
 *
 * @author lixining
 * @version $Id: MenusController.java, v 0.1 2016年6月22日 上午11:15:57 lixining Exp $
 */
@Controller
@RequestMapping("/application/rbac/menu")
public class MenusController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(MenusController.class);
    /**
     * Menu Service
     */
    @Autowired
    private MenusService        menusService;

    /**
     * 菜單页面加载
     */
    @RequestMapping("/page")
    public String handlePage() {
        return "rbac/menus-page";
    }

    /**
     * 菜单Tree数据JSON
     */
    @ResponseBody
    @RequestMapping("/treejson")
    public Response handleTreeJson(HttpServletRequest request) {
        try {
            List<MenusDO> menuList = menusService.selectPermitTreeJson();
            return Response.success("success", menuList);
        } catch (Exception e) {
            LOG.error("load tree json fail", e);
            return Response.fail("菜单加载失败");
        }
    }

    /**
     * 菜单维护列表
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public Response handlePageList(HttpServletRequest request) {
        try {
            //			String menuName = HttpWebUtils.getValue(request, "menuName");
            String menuName = HttpWebUtils.getValue(request, "menuName");
            if (StringUtils.isNoneBlank(menuName)) {
                menuName = URLDecoder.decode(menuName, "utf-8");
            }

            List<MenusDO> menuList = menusService.selectAllMenuTreeJson(menuName);
            return Response.success("success", menuList);
        } catch (Exception e) {
            LOG.error("load menu fail", e);
            return Response.fail("菜单加载失败");
        }
    }

    /**
     * 保存数据
     */
    @ResponseBody
    @RequestMapping("/save")
    public Response handleSave(HttpServletRequest request) {
        try {
            String menuId = HttpWebUtils.getValue(request, "id", null);
            String text = HttpWebUtils.getValue(request, "text");
            String url = HttpWebUtils.getValue(request, "url");
            String iconCls = HttpWebUtils.getValue(request, "iconCls");
            String display = HttpWebUtils.getValue(request, "display");
            String parentId = HttpWebUtils.getValue(request, "parentId");
            if (!RegExpUtils.length(text, 1, 50)) {
                return Response.fail("菜单名称长度不合法");
            }
            if (!RegExpUtils.length(iconCls, 0, 50)) {
                return Response.fail("菜单图标长度不合法");
            }
            if (!RegExpUtils.length(url, 0, 100)) {
                return Response.fail("链接地址长度不合法");
            }
            if (!RegExpUtils.length(parentId, 1, 50)) {
                return Response.fail("父节点ID长度不合法");
            }
            if (!StringUtils.isNumeric(display) || CommonUtils.getInt(display) <= 0) {
                return Response.fail("显示顺序必须为非0正整数");
            }
            MenusDO menusDO = new MenusDO();
            menusDO.setId(menuId);
            menusDO.setDisplay(Integer.parseInt(display));
            menusDO.setIconCls(iconCls);
            menusDO.setParentId(parentId);
            menusDO.setText(text);
            menusDO.setUrl(url);
            menusService.save(menusDO);
            return Response.success("success");
        } catch (BusinessException e) {
            LOG.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.error("保存菜单失败", e);
            return Response.fail("保存菜单记录失败");
        }
    }

    /**
     * 删除数据
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Response handleDelete(HttpServletRequest request) {
        try {
            String menuId = HttpWebUtils.getValue(request, "menuId");
            if (StringUtils.isBlank(menuId)) {
                return Response.fail("菜单ID不能为空");
            }
            // 删除菜单记录 
            menusService.delete(menuId);
            return Response.success("success");
        } catch (Exception e) {
            LOG.error("删除菜单失败", e);
            return Response.fail("删除菜单记录失败");
        }
    }

    /**
     * 加载数据
     */
    @ResponseBody
    @RequestMapping("/load")
    public Response handleLoad(HttpServletRequest request) {
        try {
            String menuId = HttpWebUtils.getValue(request, "menuId", null);
            if (StringUtils.isBlank(menuId)) {
                return Response.fail("菜单ID不能为空");
            }
            MenusDO menu = menusService.select(menuId);
            if (menu == null) {
                throw new BusinessException("菜单记录不存在, 请刷新列表后重试");
            }
            return Response.success("success", menu);
        } catch (BusinessException e) {
            LOG.error(e.getErrorDesc(), e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOG.error("加载菜单失败", e);
            return Response.fail("加载菜单记录失败");
        }
    }

}
