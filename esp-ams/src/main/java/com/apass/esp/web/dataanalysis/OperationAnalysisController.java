package com.apass.esp.web.dataanalysis;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.service.UsersService;
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
			String newpassword = CommonUtils.getValue(map, "password");
			String conformnewpassword = CommonUtils.getValue(map, "conformpassword");
			if (StringUtils.isAnyBlank(oldpassword, newpassword, conformnewpassword)) {
				map.put("msg", "旧密码、新密码、确认密码不能为空！");
				return Response.fail("旧密码、新密码、确认密码不能为空！",map);
			}
			if (!StringUtils.equals(newpassword, conformnewpassword)) {
				map.put("msg", "新密码和确认新密码不一致！");
				return Response.fail("新密码和确认新密码不一致！",map);
			}
			usersService.resetpassword(username, oldpassword, newpassword);
			map.put("msg", "确认新密码修改成功！");
			return Response.success("确认新密码修改成功！",map);
		} catch (BusinessException e) {
			logger.error(e.getErrorDesc(), e);
			map.put("msg", e.getErrorDesc());
			return Response.fail(e.getErrorDesc(),map);
		} catch (Exception e) {
			logger.error("修改密码失败！", e);
			map.put("msg", "修改密码失败！");
			return Response.fail("修改密码失败！",map);
		}
	}
}