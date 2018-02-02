package com.apass.esp.service.rbac;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.rbac.RolesDO;
import com.apass.esp.domain.entity.rbac.UsersDO;
import com.apass.esp.repository.rbac.UsersRepository;
import com.apass.gfb.framework.exception.BusinessException;
@Component
public class UserService {
	@Autowired
	private UsersRepository usersRepository;
	/**
	 * selectByUsername
	 * @param username
	 * @return
	 */
	public UsersDO selectByUsername(String username){
		return usersRepository.selectByUsername(username);
	}
	/**
	 * resetpassword
	 * @param username
	 * @param newpassword
	 * @throws BusinessException
	 */
	public Response resetpassword(String username, String newpassword,Map<String, Object> map) {
		usersRepository.resetPassword(username, newpassword, username);
		map.put("msg", "确认新密码修改成功！");
		return Response.success("确认新密码修改成功！",map);
	}
	/**
	 * 已分配角色
	 */
	public List<RolesDO> loadAssignedRoles(String userId) {
	   return usersRepository.loadAssignedRoles(userId);
	}
}