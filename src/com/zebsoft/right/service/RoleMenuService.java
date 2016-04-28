package com.zebsoft.right.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zebsoft.right.DAO.MenuDAO;
import com.zebsoft.right.DAO.RoleDAO;
import com.zebsoft.right.DAO.RolemenuDAO;
import com.zebsoft.right.domain.Menu;
import com.zebsoft.right.domain.Role;
import com.zebsoft.right.domain.Rolemenu;
import com.zebsoft.right.domain.Users;
import com.zebsoft.zzz.util.LogHelper;
import com.zebsoft.zzz.util.SystemState;
/**
 * 类说明：角色-菜单业务层
 * @author Thinker
 * @since 2014年3月12日10:19:51
 * @version 1.0
 */

@Service("roleMenuService")
public class RoleMenuService {
	
	
	
	private static final Logger log = LoggerFactory.getLogger("rightRolemenu");
	@Resource
	private RolemenuDAO roleMenuDAO;
	@Resource
	private MenuDAO menuDAO;
	@Resource
	private RoleDAO roleDAO;
	
	/**
	 * 通过角色Id找到菜单
	 * 
	 */
	public Map<String , Object> findMenuByRole(String roleId){
		List<Rolemenu> roleMenuList = roleMenuDAO.findMenuByRole(roleId);
		List<Menu> tempMenuList = new ArrayList<Menu>();
		for (Rolemenu rolemenu : roleMenuList) {
			String menuId = rolemenu.getMenuId().getId();
			Menu menu = menuDAO.findMenuById(menuId);
			tempMenuList.add(menu);
			}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("items", tempMenuList);
		map.put("count", tempMenuList.size());
		return map;
		}
	
	
	/**
	 * 给角色添加菜单（一次只能添加一个菜单）
	 * @return
	 * @throws Exception 
	 */
	public boolean addRoleMenu(String roleId,String menuId,Users user){
		Rolemenu tempRolemenu = roleMenuDAO.findByRoleIdAndMenuId(roleId, menuId);
		if(tempRolemenu == null){
			Role roleTemp  = roleDAO.findRoleById(roleId);
			Menu menuTemp = menuDAO.findMenuById(menuId);
			Rolemenu rolemenu = new Rolemenu();
			rolemenu.setRoleId(roleTemp);
			rolemenu.setMenuId(menuTemp);
			rolemenu.setState(SystemState.USE);
			roleMenuDAO.save(rolemenu);
			log.info(LogHelper.userLog(user.getUsername()+"为角色是"+roleTemp.getName()+"的角色添加了一个"+menuTemp.getName()+"的菜单"));
		}
		return true;
	}
	
	
	/**
	 * 给角色删除菜单（一次只能删除一个菜单）
	 * @return
	 * @throws Exception 
	 */
	public boolean delRoleMenu(String roleId,String menuId,Users user){
		//Users user = (Users) session.get("user");
		Rolemenu tempRolemenu = roleMenuDAO.findByRoleIdAndMenuId(roleId, menuId);
		tempRolemenu.setState(SystemState.NOUSE);
		roleMenuDAO.update(tempRolemenu);
		log.info(LogHelper.userLog(user.getUsername()+"为角色是"+tempRolemenu.getRoleId().getName()+"的角色添加了一个"+tempRolemenu.getMenuId().getName()+"的菜单"));
		return true;
	}
	
	
}
