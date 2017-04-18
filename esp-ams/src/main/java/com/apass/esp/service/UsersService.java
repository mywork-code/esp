package com.apass.esp.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.rbac.RolesDO;
import com.apass.esp.domain.entity.rbac.UserRoleDO;
import com.apass.esp.domain.entity.rbac.UsersDO;
import com.apass.esp.repository.rbac.UsersRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.jwt.common.ListeningCollectionUtils;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;

/**
 * 
 * @description Users Service
 *
 * @author lixining
 * @version $Id: UsersService.java, v 0.1 2016年6月23日 下午3:02:05 lixining Exp $
 */
@Component
public class UsersService {
	/**
	 * Users Repository
	 */
	@Autowired
	private UsersRepository usersRepository;

	/**
	 * Users List
	 */
	public PaginationManage<UsersDO> page(UsersDO usersDO, Page page) {
		PaginationManage<UsersDO> result = new PaginationManage<UsersDO>();
		// 调用dao层分页查询
		Pagination<UsersDO> response = usersRepository.page(usersDO, page);
		result.setDataList(response.getDataList());
		result.setPageInfo(page.getPageNo(), page.getPageSize());
		result.setTotalCount(response.getTotalCount());
		return result;
	}

	/**
	 * 可用分配角色
	 */
	public List<RolesDO> loadAvailableRoles(String userId) {
		return usersRepository.loadAvailableRoles(userId);
	}

	/**
	 * 已分配角色
	 */
	public List<RolesDO> loadAssignedRoles(String userId) {
		return usersRepository.loadAssignedRoles(userId);
	}

	/**
	 * 保存用户角色设置
	 */
	@Transactional
	public void saveAssignedRoles(String userId, String roles) {
		usersRepository.deleteUserRolesByUserId(userId);
		Set<String> roleIds = ListeningCollectionUtils.tokenizeToSet(roles, ",");
		if (CollectionUtils.isEmpty(roleIds)) {
			return;
		}
		String operator = SpringSecurityUtils.getCurrentUser();
		for (String role : roleIds) {
			UserRoleDO userRoleDO = new UserRoleDO();
			userRoleDO.setUserId(userId);
			userRoleDO.setRoleId(role);
			userRoleDO.setCreatedBy(operator);
			userRoleDO.setUpdatedBy(operator);
			usersRepository.insertUserRole(userRoleDO);
		}
	}

	/**
	 * 删除用户记录
	 * 
	 * @throws BusinessException
	 */
	@Transactional
	public void delete(String userId) throws BusinessException {
		UsersDO usersDO = usersRepository.select(userId);
		if (usersDO == null) {
			throw new BusinessException("用户不存在");
		}
		if (StringUtils.equals(usersDO.getUserName(), "admin")) {
			throw new BusinessException("管理员(admin)不可删除");
		}
		usersRepository.deleteUserRolesByUserId(userId);
		usersRepository.delete(userId);
	}

	/**
	 * 保存账号
	 * 
	 * @throws BusinessException
	 */
	public void save(UsersDO usersDO) throws BusinessException {
		String username = usersDO.getUserName();
		if (usersRepository.exists(username)) {
			throw new BusinessException("用户名已经存在");
		}
		usersDO.setStatus("1");
		usersDO.setCreatedBy(SpringSecurityUtils.getCurrentUser());
		usersDO.setUpdatedBy(SpringSecurityUtils.getCurrentUser());
		usersRepository.insert(usersDO);
	}

	/**
	 * 重置密码
	 * 
	 * @param username
	 * @param oldpassword
	 * @param newpassword
	 * @throws BusinessException
	 */
	public void resetpassword(String username, String oldpassword, String newpassword) throws BusinessException {
		UsersDO usersDO = usersRepository.selectByUsername(username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(oldpassword, usersDO.getPassword())) {
			throw new BusinessException("旧密码不正确");
		}
		String operator = SpringSecurityUtils.getCurrentUser();
		usersRepository.resetPassword(username, new BCryptPasswordEncoder().encode(newpassword), operator);
	}

	/**
	 * 强制重置密码
	 */
	public void forceResetpassword(String username, String newpassword) {
		String operator = SpringSecurityUtils.getCurrentUser();
		usersRepository.resetPassword(username, new BCryptPasswordEncoder().encode(newpassword), operator);
	}

	public UsersDO loadBasicInfo() {
		String username = SpringSecurityUtils.getCurrentUser();
		UsersDO usersDO = usersRepository.selectByUsername(username);
		usersDO.setPassword(null);
		usersDO.setStatus(null);
		usersDO.setUserName(null);
		usersDO.setId(null);
		return usersDO;
	}

	public void saveBasicInfo(UsersDO usersDO) throws BusinessException {
		String username = SpringSecurityUtils.getCurrentUser();
		UsersDO dbUsersDO = usersRepository.selectByUsername(username);
		if (dbUsersDO == null) {
			throw new BusinessException("客户不存在请重新登录");
		}
		usersDO.setId(dbUsersDO.getId());
		usersDO.setUpdatedBy(username);
		usersDO.setStatus("1");
		usersRepository.updateAll(usersDO);
	}

	public int relevanceMerchant(UsersDO usersDO) {
		return usersRepository.update(usersDO);
	}
}
