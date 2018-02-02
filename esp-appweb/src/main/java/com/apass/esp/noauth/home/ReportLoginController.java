package com.apass.esp.noauth.home;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.apass.gfb.framework.jwt.TokenManager;
import com.apass.gfb.framework.security.domains.SecurityAccordion;
import com.apass.gfb.framework.security.domains.SecurityAccordionTree;
import com.apass.gfb.framework.security.domains.SecurityMenus;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.rbac.UsersDO;
import com.apass.esp.service.rbac.UsersService;
import com.apass.gfb.framework.security.toolkit.ListeningAuthenticationManager;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * 报表相关数据  
 * 
 * 报表登录
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/noauth/dataanalysis/reportLoginController")
public class ReportLoginController {
    private static final Logger logger = LoggerFactory.getLogger(ReportLoginController.class);
    @Autowired
    private ListeningAuthenticationManager listeningAuthenticationManager;
    @Autowired
	private UsersService usersService;
    @Autowired
	public TokenManager tokenManager;
    // 安家派token失效时间间隔(默认7天失效)
 	public static final Long TOKEN_EXPIRES_SPACE = 7 * 24 * 60 * 60L;
    /**
     * 报表APP登录
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public Response login(@RequestBody Map<String, Object> map) {
        try{
            String username = CommonUtils.getValue(map, "username");
            String password = CommonUtils.getValue(map, "password");
            if (StringUtils.isAnyBlank(username, password)) {
            	map.put("msg", "用户名或密码不能为空！");
                return Response.fail("用户名或密码不能为空！",map);
            }
            //获取菜单
            Object principal = SpringSecurityUtils.getAuthentication().getPrincipal();
            if (principal == null || !(principal instanceof ListeningCustomSecurityUserDetails)) {
            	map.put("msg", "加载登陆菜单失败,请联系管理员！");
                return Response.fail("加载登陆菜单失败,请联系管理员！",map);
            }
            List<SecurityAccordionTree> resultList = new ArrayList<SecurityAccordionTree>();
            ListeningCustomSecurityUserDetails details = (ListeningCustomSecurityUserDetails) principal;
            SecurityMenus securityMenus = details.getSecurityMenus();
            treatSecurityMenus(securityMenus, resultList);
            String ifShowGenral = "0";
            String ifShowRunAnalysis = "0";
            for(SecurityAccordionTree securityTree:resultList){
                if(StringUtils.equals(securityTree.getText(),"数据报表")){
                    for(SecurityAccordionTree chirld: securityTree.getChildren()){
                        if(StringUtils.equals(chirld.getText(),"应用概况")){
                            ifShowGenral = "1";
                        }
                        if(StringUtils.equals(chirld.getText(),"运营分析")){
                            ifShowRunAnalysis = "1";
                        }
                    }
                }
            }
            UsersDO users = usersService.selectByUsername(username);
            String usersId = users.getId();
            String token = tokenManager.createToken(usersId, username, TOKEN_EXPIRES_SPACE);
            map.put("usersId",usersId);
            map.put("token",token);
            listeningAuthenticationManager.authentication(username, password);
            map.put("ifShowGenral",ifShowGenral);
            map.put("ifShowRunAnalysis",ifShowRunAnalysis);
            map.put("msg", "用户登录成功！");
            return Response.success("用户登录成功！",map);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            map.put("msg", "用户名或密码不正确！");
            return Response.fail("用户名或密码不正确！",map);
        }
    }
    /**
     * Convert SecurityMenus To List<SecurityMenusModel>
     * @param securityMenus
     * @param resultList
     */
    private void treatSecurityMenus(SecurityMenus securityMenus, List<SecurityAccordionTree> resultList) {
        List<SecurityAccordion> accordionList = securityMenus.getSecurityAccordionList();
        Map<String, List<SecurityAccordionTree>> menuMap = securityMenus.getAccordionTreeListMap();
        for (SecurityAccordion accordion : accordionList) {
            SecurityAccordionTree accordionMenu = new SecurityAccordionTree();
            accordionMenu.setId(accordion.getId());
            accordionMenu.setText(accordion.getText());
            if (menuMap == null || !menuMap.containsKey(accordion.getId())) {
                resultList.add(accordionMenu);
                continue;
            }
            if (!CollectionUtils.isEmpty(menuMap.get(accordion.getId()))) {
                accordionMenu.setChildren(menuMap.get(accordion.getId()));
                resultList.add(accordionMenu);
            }
        }
    }
}