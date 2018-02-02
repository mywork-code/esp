package com.apass.esp.service.rbac;

import com.apass.esp.domain.entity.rbac.MenusDO;
import com.apass.esp.repository.rbac.MenusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @description Menu Service
 *
 * @author lixining
 * @version $Id: MenusService.java, v 0.1 2016年6月23日 下午1:48:28 lixining Exp $
 */
@Component
public class MenuService {

    @Autowired
    private MenusRepository menusRepository;


    /**
     * 加载菜单数据
     */
    public MenusDO select(String menuId) {
        return menusRepository.select(menuId);
    }



}
