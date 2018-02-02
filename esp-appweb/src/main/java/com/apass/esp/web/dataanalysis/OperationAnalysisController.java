package com.apass.esp.web.dataanalysis;
import java.util.List;
import java.util.Map;

import com.apass.gfb.framework.security.domains.SecurityAccordion;
import com.apass.gfb.framework.security.domains.SecurityAccordionTree;
import com.apass.gfb.framework.security.domains.SecurityMenus;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.service.dataanalysis.DataAppuserRetentionService;
import com.apass.esp.service.dataanalysis.DataEsporderAnalysisService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.ListeningAuthenticationManager;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * 报表相关数据  
 * 
 * 用户留存页面相关
 * 运行分析页面相关
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/noauth/dataanalysis/operationAnalysisController")
public class OperationAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(OperationAnalysisController.class);
    @Autowired
    private DataAppuserRetentionService dataAppuserRetentionService;
    @Autowired
    private DataEsporderAnalysisService dataEsporderAnalysisService;
    @Autowired
    private ListeningAuthenticationManager listeningAuthenticationManager;
    @Autowired
	private UsersService usersService;
    @Autowired
    private SecurityInfoController securityInfo;
    /**
     * 用户留存数据载入
     * @param map
     * 参数含有
	 * dateStart  起止日期
	 * dateEnd 起止日期
	 * platformids 平台类型
     * @return  map
     * 含有
     * {
     * status:1,
     * mag:"用户留存数据载入成功",
     * data:
     * 	{
     * 		newList = 【newList】
     * 		activityList = 【activityList】
     * 	}
     * }
     * newList:[DataAppuserRetentionVo]
     * activityList:[DataAppuserRetentionVo]
     */
    @ResponseBody
    @RequestMapping("/getAppuserRetentionList")
    public Response getAppuserRetentionList(@RequestBody Map<String, Object> map) {
    	try{
            return dataAppuserRetentionService.getAppuserRetentionList(map);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return Response.fail("用户留存数据载入失败,"+e.getErrorDesc());
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("用户留存数据载入失败");
        }
    }
    /**
     * 运营分析数据载入
     * @param map
     * 参数含有
	 * dateStart  起止日期
	 * dateEnd 起止日期
	 * platformids 平台类型
     * @return map
     * 含有 
     * 含有
     * {
     * status:1,
     * mag:"运营分析数据载入成功",
     * data:【orderAnalysisVo】
     * }
     */
    @ResponseBody
    @RequestMapping("/getOperationAnalysisList")
    public Response getOperationAnalysisList(@RequestBody Map<String, Object> map) {
        try{
            return dataEsporderAnalysisService.getOperationAnalysisList(map);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return Response.fail("运行分析数据载入失败,"+e.getErrorDesc());
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("运行分析数据载入失败");
        }
    }
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
            listeningAuthenticationManager.authentication(username, password);
            //获取菜单
            List<SecurityAccordionTree> resultList = Lists.newArrayList();
            Object principal = SpringSecurityUtils.getAuthentication().getPrincipal();
            if (principal == null || !(principal instanceof ListeningCustomSecurityUserDetails)) {
                return Response.fail("加载登陆菜单失败,请联系管理员");
            }
            ListeningCustomSecurityUserDetails details = (ListeningCustomSecurityUserDetails) principal;
            SecurityMenus securityMenus = details.getSecurityMenus();
            securityInfo.treatSecurityMenus(securityMenus, resultList);

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
     * 报表APP密码修改
     * @param map
     * @return
     */
    @ResponseBody
	@RequestMapping("/updatePassword")
	public Response updatePassword(@RequestBody Map<String, Object> map) {
		try {
			String username = CommonUtils.getValue(map, "username");
			String oldpassword = CommonUtils.getValue(map, "oldpassword");
			String newpassword = CommonUtils.getValue(map, "newpassword");
			String conformnewpassword = CommonUtils.getValue(map, "conformnewpassword");
			if (StringUtils.isAnyBlank(oldpassword, newpassword, conformnewpassword)) {
				map.put("msg", "旧密码、新密码、确认密码不能为空！");
				return Response.fail("旧密码、新密码、确认密码不能为空！",map);
			}
			if (!StringUtils.equals(newpassword, conformnewpassword)) {
				map.put("msg", "新密码和确认新密码不一致！");
				return Response.fail("新密码和确认新密码不一致！",map);
			}
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (!encoder.matches(oldpassword, usersService.selectByUsername(username).getPassword())) {
				map.put("msg", "旧密码不正确！");
				return Response.fail("旧密码不正确！",map);
			}
			return usersService.resetpassword(username, newpassword,map);
		} catch (Exception e) {
			logger.error("修改密码失败！", e);
			map.put("msg", "修改密码失败！");
			return Response.fail("修改密码失败！",map);
		}
	}
}