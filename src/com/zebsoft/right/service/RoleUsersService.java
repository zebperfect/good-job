package com.zebsoft.right.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zebsoft.right.DAO.RoleDAO;
import com.zebsoft.right.DAO.RoleusersDAO;
import com.zebsoft.right.DAO.UsersDAO;
import com.zebsoft.right.domain.Role;
import com.zebsoft.right.domain.Roleusers;
import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.LogHelper;
import com.zebsoft.zzz.util.SystemState;

/**
 * 类说明：用户-角色业务层
 * @author Thinker
 * @since 2014年3月12日10:17:43
 * @version 1.0
 */
@Service("roleUsersService")
public class RoleUsersService {

	
	private static final Logger log = LoggerFactory.getLogger("rightRoleusers");
	@Resource
	private RoleusersDAO roleusersDAO;
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private UsersDAO usersDAO;
	
	/**
	 * 通过用户Id找到角色
	 * 
	 */
	public Map<String , Object> findRoleByUsersId(String usersId){
		List<Roleusers> roleusersList = roleusersDAO.findRoleByUser(usersId);
		List<Role> roleList = new ArrayList<Role>();
		for (Roleusers roleusers : roleusersList) {
			String roleId = roleusers.getRoleId().getId();
			Role role = roleDAO.findRoleById(roleId);
			roleList.add(role);
			}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("items", roleList);
		map.put("count", roleList.size());
		return map;
		}
	
	
	/**
	 * 给用户添加角色（一次只能添加一个角色）
	 * @return
	 * @throws Exception 
	 */
	public boolean addRoleUsers(String userId,String roleId,Users user){
		Roleusers tempRoleuers = roleusersDAO.findByUserIdAndRoleId(userId, roleId);
		if(tempRoleuers == null){
			Users userTemp  = usersDAO.findByUserId(Integer.valueOf(userId));
			Role roleTemp = roleDAO.findRoleById(roleId);
			Roleusers roleusers = new Roleusers();
			roleusers.setRoleId(roleTemp);
			roleusers.setUsersId(userTemp);
			roleusers.setState(SystemState.USE);
			roleusersDAO.save(roleusers);
			log.info(LogHelper.userLog(user.getUsername()+"为用户名是"+userTemp.getUsername()+"的用户添加了一个"+roleTemp.getName()+"的角色"));
		}
		return true;
	}
	
	
	/**
	 * 给用户删除角色（一次只能删除一个角色）
	 * @return
	 * @throws Exception 
	 */
	public boolean delRoleUsers(String userId,String roleId,Users user){
		Roleusers roleusers = roleusersDAO.findByUserIdAndRoleId(userId,roleId);
		roleusers.setState(SystemState.NOUSE);
		roleusersDAO.update(roleusers);
		log.info(LogHelper.userLog(user.getUsername()+"为用户名是"+roleusers.getUsersId().getUsername()+"的用户删除了一个"+roleusers.getRoleId().getName()+"的角色"));
		return true;
	}
		
		
	
}
