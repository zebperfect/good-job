package com.zebsoft.right.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zebsoft.right.DAO.RoleDAO;
import com.zebsoft.right.domain.Role;
import com.zebsoft.zzz.util.LogHelper;
import com.zebsoft.zzz.util.SystemState;
/**
 * 类说明：角色业务层
 * @author Thinker
 * @since 2014年3月12日10:18:40
 * @version 1.0
 */

@Service("roleService")
public class RoleService {

	@Resource
	private RoleDAO roleDAO;
	private static final Logger log = LoggerFactory.getLogger("rightRole");
	
	/**
	 * 添加角色
	 * @return
	 */
	public boolean addRole(Role role){
		List<Role> roleList = roleDAO.findRoleByName(role.getName());
		if(roleList.size() == 0){
			role.setState(SystemState.USE);
			roleDAO.save(role);
			log.info(LogHelper.userLog("添加了一个角色名为"+role.getName()+"的角色"));
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * 分页显示所有角色
	 * 
	 * @return
	 */
	public Map<String, Object> queryRole(Integer start, Integer limit){
		return roleDAO.queryRole(start,limit);
	}
	
	/**
	 *  删除角色（支持多条删除）
	 * @param delData
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	public boolean delRole(String delData) throws Exception{
		 String roleName = roleDAO.delRole(delData);
		 log.info(LogHelper.userLog("删除了角色名为"+roleName+"的角色"));
		 return true;
	}
	
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public boolean updateRole(Role role){
		List<Role> roleTempList = roleDAO.findRoleByName(role.getName());
		if(roleTempList.size() >0){
			return false;
		}
		else{
			Role roleTemp = roleDAO.findRoleById(role.getId());
				if(roleTemp!=null){
					roleTemp.setName(role.getName());
					roleDAO.save(roleTemp);
					log.info(LogHelper.userLog("修改了角色名为"+roleTemp.getName()+"的名称"));
					return true;
				}
				else
					return false;
		}
	}
	
	/**
	 * 根据用户Id找到角色对象
	 * @return 
	 */
	public Role findRoleById(String roleId){
		return roleDAO.findRoleById(roleId);
	}
	
}
