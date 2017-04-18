package com.apass.esp.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.rbac.MenusDO;
import com.apass.esp.repository.rbac.MenusRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.google.common.collect.Lists;

/**
 * 
 * @description Menu Service
 *
 * @author lixining
 * @version $Id: MenusService.java, v 0.1 2016年6月23日 下午1:48:28 lixining Exp $
 */
@Component
public class MenusService {

    @Autowired
    private MenusRepository menusRepository;

    /**
     * Select user Permit Tree Menu Json
     */
    public List<MenusDO> selectPermitTreeJson() {
        String securityUserId = SpringSecurityUtils.getCurrentUser();
        return menusRepository.selectAvailableMenus(securityUserId, "root");
    }

    /**
     * Menus Page
     */
    public PaginationManage<MenusDO> page(MenusDO paramDO, Page page) {
        PaginationManage<MenusDO> result = new PaginationManage<MenusDO>();
        Pagination<MenusDO> response = menusRepository.page(paramDO, page);
        result.setDataList(response.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(response.getTotalCount());
        return result;
    }

    /**
     * All Menus
     */
    public List<MenusDO> selectAllMenuTreeJson(String menuName) {
        if (StringUtils.isNotBlank(menuName)) {
            return menusRepository.selectAllMenus("root", menuName);
        }
        MenusDO rootMenu = new MenusDO();
        rootMenu.setChildren(menusRepository.selectAllMenus("root", null));
        rootMenu.setText("后台管理系统菜单树");
        rootMenu.setId("root");
        List<MenusDO> resultList = Lists.newArrayList();
        resultList.add(rootMenu);
        return resultList;
    }

    /**
     * 保存菜单数据
     */
    public void save(MenusDO menusDO) throws BusinessException {
        String id = menusDO.getId();
        String operator = SpringSecurityUtils.getCurrentUser();

        String text = menusDO.getText();
        List<MenusDO> dataList = menusRepository.filter(text, StringUtils.isBlank(id) ? null : id);
        if (!CollectionUtils.isEmpty(dataList)) {
            throw new BusinessException("菜单名称已存在");
        }
        if (StringUtils.isBlank(id)) {
            menusDO.setCreatedBy(operator);
            menusDO.setUpdatedBy(operator);
            menusRepository.insert(menusDO);
            return;
        }
        MenusDO menusDB = menusRepository.select(id);
        if (menusDB == null) {
            throw new BusinessException("记录不存在,无法更新, 请刷新列表后重试");
        }
        menusDB.setUpdatedBy(operator);
        menusDB.setText(menusDO.getText());
        menusDB.setUrl(menusDO.getUrl());
        menusDB.setIconCls(menusDO.getIconCls());
        menusDB.setParentId(menusDO.getParentId());
        menusDB.setDisplay(menusDO.getDisplay());
        menusRepository.updateAll(menusDB);
    }

    /**
     * 加载菜单数据
     */
    public MenusDO select(String menuId) {
        return menusRepository.select(menuId);
    }

    /**
     * 删除菜单数据
     */
    @Transactional
    public void delete(String menuId) {
        menusRepository.deleteRoleMenuByMenuId(menuId);
        menusRepository.delete(menuId);
        MenusDO tempMenuDO = new MenusDO();
        tempMenuDO.setParentId(menuId);
        List<MenusDO> subMenuList = menusRepository.filter(tempMenuDO);
        if (CollectionUtils.isEmpty(subMenuList)) {
            return;
        }
        for (MenusDO menu : subMenuList) {
            delete(menu.getId());
        }
    }

}
