package com.apass.esp.repository.rbac;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.apass.esp.domain.entity.rbac.MenusDO;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;
import com.google.common.collect.Maps;

/**
 * 
 * @description menu repository
 *
 * @author lixining
 * @version $Id: MenusRepository.java, v 0.1 2016年6月22日 上午11:12:33 lixining Exp $
 */
@MyBatisRepository
public class MenusRepository extends BaseMybatisRepository<MenusDO, String> {

    /**
     * Select Available menus
     * 
     * @return List<MenusDO>
     */
    public List<MenusDO> selectAvailableMenus(String username, String parentId) {
        String sqlScript = this.getSQL("selectAvailableMenus");
        Map<String, String> maps = Maps.newHashMap();
        maps.put("username", username);
        maps.put("parentId", parentId);
        List<MenusDO> resultList = this.getSqlSession().selectList(sqlScript);
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        for (MenusDO menusDO : resultList) {
            menusDO.setChildren(selectAvailableMenus(username, menusDO.getId()));
        }
        return resultList;
    }

    /**
     * Select All menus
     * 
     * @return List<MenusDO>
     */
    public List<MenusDO> selectAllMenus(String parentId, String menuName) {
        MenusDO tempMenusDO = new MenusDO();
        tempMenusDO.setParentId(parentId);
        if (StringUtils.equals(parentId, "root")) {
            tempMenusDO.setText(menuName);
        }
        List<MenusDO> resultList = filter(tempMenusDO);
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        for (MenusDO menusDO : resultList) {
            menusDO.setChildren(selectAllMenus(menusDO.getId(), menuName));
        }
        return resultList;
    }

    /**
     * 根据菜单名称 + neId过滤
     */
    public List<MenusDO> filter(String text, String neId) {
        MenusDO tempMenusDO = new MenusDO();
        tempMenusDO.setText(text);
        tempMenusDO.setNeId(neId);
        return filter(tempMenusDO);
    }

    public void deleteRoleMenuByMenuId(String menuId) {
        getSqlSession().delete(getSQL("deleteRoleMenuByMenuId"), menuId);
    }
}
