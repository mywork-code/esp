package com.apass.esp.web.rbac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.rbac.RolesDO;
import com.apass.esp.domain.entity.rbac.UsersDO;
import com.apass.esp.service.UsersService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

/**
 * 
 * @description 用户管理
 *
 * @author lixining
 * @version $Id: UsersController.java, v 0.1 2016年6月22日 上午11:15:57 lixining Exp
 *          $
 */
@Controller
@RequestMapping("/application/rbac/user")
public class UsersController {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);
	/**
	 * Users Service
	 */
	@Autowired
	private UsersService usersService;
	@Autowired
	private MerchantInforService merchantInforService;
	private static final String USER_PAGE = "rbac/users-page";

	private static final String SUCCESS = "success";
	private static final String USERNAME = "username";
	private static final String RESETPWD_FAIL = "重置密码失败";
	private static final String USER_ID = "userId";
	private static final String LOADROLES_FAIL = "加载可用角色列表失败";

	/**
	 * 用户管理页面
	 */
	@RequestMapping("/page")
	public String handleUsersPage() {
		return USER_PAGE;
	}

	/**
	 * 用户管理页面
	 */
	@ResponseBody
	@RequestMapping("/merchantList")
	public Response merchantList() {
		// Page page = new Page();
		// page.setPage(1);
		// page.setLimit(10000);
		// Pagination<MerchantInfoEntity> pagination = null;
		List<MerchantInfoEntity> merchantList = null;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("status", "1");
			merchantList = merchantInforService.queryMerchantInfor(map);
			if (merchantList == null) {
			    return Response.fail("商户信息查询失败");
			}
		} catch (BusinessException e) {
			LOG.error("商户信息查询失败", e);
			e.printStackTrace();
		}
		return Response.success(SUCCESS, merchantList);
	}

	/**
	 * 分页列表JSON
	 */
	@ResponseBody
	@RequestMapping("/pagelist")
	public ResponsePageBody<UsersDO> handlePageList(HttpServletRequest request) {
		ResponsePageBody<UsersDO> respBody = new ResponsePageBody<UsersDO>();
		try {
			// 分页参数
			String pageNo = HttpWebUtils.getValue(request, "page");// 页码
			String pageSize = HttpWebUtils.getValue(request, "rows");// 每页显示条数
			Integer pageNoNum = Integer.parseInt(pageNo);
			Integer pageSizeNum = Integer.parseInt(pageSize);
			Page page = new Page();
			page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
			page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);

			// 查询传递的参数
			String username = HttpWebUtils.getValue(request, USERNAME);// 用户帐号
			String realName = HttpWebUtils.getValue(request, "realName");// 用户真实姓名
			UsersDO paramDO = new UsersDO();
			paramDO.setUserName(username);
			paramDO.setRealName(realName);

			// 调用service层分页查询
			PaginationManage<UsersDO> pagination = usersService.page(paramDO, page);
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
	 * 重置密码
	 */
	@ResponseBody
	@RequestMapping("/resetpwd")
	public Response handleResetPassword(HttpServletRequest request) {
		try {
			String oldpassword = HttpWebUtils.getValue(request, "oldpassword");
			String newpassword = HttpWebUtils.getValue(request, "newpassword");
			String renewpassword = HttpWebUtils.getValue(request, "renewpassword");
			if (StringUtils.isAnyBlank(oldpassword, newpassword, renewpassword)) {
				return Response.fail("旧密码、新密码、确认密码不能为空");
			}
			if (!StringUtils.equals(newpassword, renewpassword)) {
				return Response.fail("新密码和确认新密码不一致");
			}
			String username = SpringSecurityUtils.getCurrentUser();
			usersService.resetpassword(username, oldpassword, newpassword);
			return Response.success(SUCCESS);
		} catch (BusinessException e) {
			LOG.error(e.getErrorDesc(), e);
			return Response.fail(e.getErrorDesc());
		} catch (Exception e) {
			LOG.error(RESETPWD_FAIL, e);
			return Response.fail(RESETPWD_FAIL);
		}
	}

	/**
	 * 重置密码
	 */
	@ResponseBody
	@RequestMapping("/forceresetpwd")
	public Response handleForceResetPassword(HttpServletRequest request) {
		try {
			String username = HttpWebUtils.getValue(request, USERNAME);
			String newpassword = HttpWebUtils.getValue(request, "newpassword");
			String renewpassword = HttpWebUtils.getValue(request, "renewpassword");
			if (StringUtils.isAnyBlank(username, newpassword, renewpassword)) {
				return Response.fail("用户账号、新密码、确认密码不能为空");
			}
			if (!RegExpUtils.length(newpassword, 1, 50)) {
				return Response.fail("密码长度不合法");
			}
			if (!StringUtils.equals(newpassword, renewpassword)) {
				return Response.fail("新密码和确认新密码不一致");
			}
			usersService.forceResetpassword(username, newpassword);
			return Response.success(SUCCESS);
		} catch (Exception e) {
			LOG.error(RESETPWD_FAIL, e);
			return Response.fail(RESETPWD_FAIL);
		}
	}

	/**
	 * 加载基本信息
	 */
	@ResponseBody
	@RequestMapping("/loadbasic")
	public Response handleLoadBasic() {
		try {
			return Response.success(SUCCESS, usersService.loadBasicInfo());
		} catch (Exception e) {
			LOG.error("基本信息加载失败", e);
			return Response.fail("基本信息加载失败");
		}
	}

	/**
	 * 保存基本信息
	 */
	@ResponseBody
	@RequestMapping("/savebasic")
	public Response handleSaveBasic(HttpServletRequest request) {
		try {
			String realName = HttpWebUtils.getValue(request, "realname");
			String mobile = HttpWebUtils.getValue(request, "mobile");
			String email = HttpWebUtils.getValue(request, "email");
			if (!RegExpUtils.length(realName, 1, 100)) {
				return Response.fail("真实姓名长度不合法");
			}
			if (StringUtils.isNotBlank(mobile) && !RegExpUtils.mobile(mobile)) {
				return Response.fail("手机号不合法");
			}
			if (!RegExpUtils.length(email, 0, 50)) {
				return Response.fail("邮箱长度不合法");
			}
			UsersDO usersDO = new UsersDO();
			usersDO.setRealName(realName);
			usersDO.setMobile(mobile);
			usersDO.setEmail(email);
			usersService.saveBasicInfo(usersDO);
			return Response.success(SUCCESS);
		} catch (Exception e) {
			LOG.error("load basic fail", e);
			return Response.fail("基本信息加载失败");
		}
	}

	/**
	 * 保存用户
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Response handleSave(HttpServletRequest request) {
		try {
			String username = HttpWebUtils.getValue(request, USERNAME);
			String password = HttpWebUtils.getValue(request, "password");
			String repassword = HttpWebUtils.getValue(request, "repassword");
			String realName = HttpWebUtils.getValue(request, "realName");
			String mobile = HttpWebUtils.getValue(request, "mobile");
			String email = HttpWebUtils.getValue(request, "email");
			if (!RegExpUtils.length(username, 1, 50)) {
				return Response.fail("用户账号长度不合法");
			}
			if (!RegExpUtils.length(password, 1, 50)) {
				return Response.fail("密码长度不合法");
			}
			if (!StringUtils.equals(password, repassword)) {
				return Response.fail("密码和确认密码不一致");
			}
			if (!RegExpUtils.length(realName, 1, 100)) {
				return Response.fail("真实姓名长度不合法");
			}
			if (StringUtils.isNotBlank(mobile) && !RegExpUtils.mobile(mobile)) {
				return Response.fail("手机号不合法");
			}
			if (!RegExpUtils.length(email, 0, 50)) {
				return Response.fail("邮箱长度不合法");
			}
			UsersDO usersDO = new UsersDO();
			usersDO.setUserName(username);
			usersDO.setPassword(new BCryptPasswordEncoder().encode(password));
			usersDO.setRealName(realName);
			usersDO.setMobile(mobile);
			usersDO.setEmail(email);
			usersService.save(usersDO);
			return Response.success(SUCCESS);
		} catch (BusinessException e) {
			LOG.error(e.getErrorDesc(), e);
			return Response.fail(e.getErrorDesc());
		} catch (DuplicateKeyException e) {
			LOG.error("手机号码已存在", e);
			return Response.fail("手机号码已存在");
		} catch (Exception e) {
			LOG.error("保存角色失败", e);
			return Response.fail("保存角色记录失败");
		}
	}

	/**
	 * 删除用户
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Response handleDelete(HttpServletRequest request) {
		try {
			String userId = HttpWebUtils.getValue(request, USER_ID);
			if (StringUtils.isBlank(userId)) {
				return Response.fail("要删除的用户ID不能为空");
			}
			usersService.delete(userId);
			return Response.success(SUCCESS);
		} catch (BusinessException e) {
			LOG.error(e.getErrorDesc(), e);
			return Response.fail(e.getErrorDesc());
		} catch (Exception e) {
			LOG.error("delete user fail", e);
			return Response.fail("用户信息删除失败");
		}
	}

	/**
	 * 可用角色
	 */
	@ResponseBody
	@RequestMapping("/load/available/roles")
	public Response handleLoadAvailableRoles(HttpServletRequest request) {
		try {
			String userId = HttpWebUtils.getValue(request, USER_ID);
			if (StringUtils.isBlank(userId)) {
				return null;
			}
			List<RolesDO> roleList = usersService.loadAvailableRoles(userId);
			return Response.success(SUCCESS, roleList);
		} catch (Exception e) {
			LOG.error(LOADROLES_FAIL, e);
			return Response.fail(LOADROLES_FAIL);
		}
	}

	/**
	 * 已经分配角色
	 */
	@ResponseBody
	@RequestMapping("/load/assigned/roles")
	public Response handleLoadAssignedRoles(HttpServletRequest request) {
		try {
			String userId = HttpWebUtils.getValue(request, USER_ID);
			if (StringUtils.isBlank(userId)) {
				return null;
			}
			List<RolesDO> roleList = usersService.loadAssignedRoles(userId);
			return Response.success(SUCCESS, roleList);
		} catch (Exception e) {
			LOG.error(LOADROLES_FAIL, e);
			return Response.fail(LOADROLES_FAIL);
		}
	}

	/**
	 * 保存用户角色设置
	 */
	@ResponseBody
	@RequestMapping("/save/assigned/roles")
	public Response handleSaveAssignedRoles(HttpServletRequest request) {
		try {
			String userId = HttpWebUtils.getValue(request, USER_ID);
			String roles = HttpWebUtils.getValue(request, "roles");
			if (StringUtils.isBlank(userId)) {
				return Response.fail("用户ID不能为空");
			}
			usersService.saveAssignedRoles(userId, roles);
			return Response.success(SUCCESS);
		} catch (Exception e) {
			LOG.error("保存用户角色分配记录失败", e);
			return Response.fail("保存用户角色记录失败");
		}
	}

	/**
	 * 关联商户
	 */
	@ResponseBody
	@RequestMapping("/relevanceMerchant")
	public Response editMerchantStatus(HttpServletRequest request) {
		Response response = new Response("0", "", "");
		try {
			String id = HttpWebUtils.getValue(request, "id");
			String merchantCode = HttpWebUtils.getValue(request, "merchantCode");

			UsersDO usersDO = new UsersDO();

			if (null != id && !id.trim().isEmpty()) {
				usersDO.setId(id);
			}
			if (null != merchantCode && !merchantCode.trim().isEmpty()) {
				usersDO.setMerchantCode(merchantCode);
			}

			ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
					.getLoginUserDetails();
			usersDO.setUpdatedBy(listeningCustomSecurityUserDetails.getUsername());

			int result = usersService.relevanceMerchant(usersDO);
			if (result == 1) {
				response.setStatus("1");
				response.setMsg("关联商户成功！");
			}
		} catch (Exception e) {
			LOG.error("关联商户失败", e);
			response.setStatus("0");
			response.setMsg("关联商户失败！");
		}
		return response;
	}

}
