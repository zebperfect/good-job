package com.zebsoft.right.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.zebsoft.right.DAO.MenuDAO;
import com.zebsoft.right.DAO.RolemenuDAO;
import com.zebsoft.right.DAO.RoleusersDAO;
import com.zebsoft.right.domain.Menu;
import com.zebsoft.right.domain.MenuComparator;
import com.zebsoft.right.domain.Rolemenu;
import com.zebsoft.right.domain.Roleusers;
import com.zebsoft.right.domain.TreeNode;
import com.zebsoft.right.domain.Users;
/**
 * 类说明：菜单业务层
 * @author Thinker
 * @since 2014年3月12日10:21:22
 * @version 1.0
 */

@Service("menuService")
public class MenuService {

	@Resource
	private MenuDAO menuDAO;
	@Resource
	private RolemenuDAO roleMenuDAO;
	@Resource
	private RoleusersDAO roleUsersDAO;
	
	/**
	 *  找到所有的二级菜单
	 * @return
	 */
	public List<Menu> findAllSecondMenu(){
		return  menuDAO.findAllSecondMenu();
	}
	
	
	/**
	 * 分页显示所有角色
	 * 
	 * @return
	 */
	public Map<String, Object> queryRoleMenu(Integer start, Integer limit){
		return menuDAO.findAllSecondMenu(start,limit);
	}
	
	
	/**
	 * 根据用户显示所有可用的菜单
	 * @author zeb
	 * @since 2014年3月12日10:21:43
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String showMenuByUser(Users user) throws Exception {
		try {
			List<Menu> menuList = new ArrayList<Menu>();
			Set<Menu> menuSet = new HashSet<Menu>();
			List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
			//通过用户找到所对应的角色
			List<Roleusers> roleUsersList = roleUsersDAO.findRoleByUser(String.valueOf(user.getId()));
			//通过角色找到所对应的菜单
			for (int i = 0; i < roleUsersList.size(); i++) {
				List<Rolemenu> menuTempList = roleMenuDAO.findMenuByRole(roleUsersList.get(i).getRoleId().getId());
				for (int j = 0; j < menuTempList.size(); j++) {
					menuList.add(menuTempList.get(j).getMenuId());
				}
			}
			for (int i = 0; i < menuList.size(); i++) {
				// 加入子菜单
				menuSet.add(menuList.get(i));
				// 加入父菜单
				menuSet.add(menuList.get(i).getParent());
			}
			for (Menu menu : menuSet) {
				if (Integer.parseInt(menu.getId()) >= 10) {
					TreeNode treeNode = new TreeNode();
					treeNode.setId(menu.getId());
					treeNode.setText(menu.getName());
					treeNode.setTheSort(Integer.valueOf(menu.getDescriptin()));
					// 二级菜单
					if (!(menu.getParent().getParent() == null)) {
						treeNode.setHref(menu.getUrl());
						treeNode.setParentId(menu.getParent().getId());
					} else {
						// 一级菜单
						treeNode.setHref("");
						treeNode.setParentId("");
					}
					treeNodeList.add(treeNode);
				}
			}
			MenuComparator menuComparator = new MenuComparator();
			Collections.sort(treeNodeList, menuComparator);
			JSONArray jsonObject = JSONArray.fromObject(treeNodeList);
			String content = jsonObject.toString();
			content = "{\'menus\':" + content + "}";
			content = content.replaceAll("\"", "\'");
			// System.out.println("TreeJson:====" + content);
			return content;
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
