package com.zebsoft.right.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.right.domain.Menu;
import com.zebsoft.right.domain.Role;
import com.zebsoft.right.domain.Users;
import com.zebsoft.right.service.MenuService;


/**
 * 类说明  权限管理类（分两级菜单，主菜单与二级菜单）
 * @author zeb
 * @since 2014年3月11日21:02:39
 * @version V1.0
 */
@Controller("menuAction")
@Scope("prototype")
public class MenuAction extends ActionSupport{

	/*****************类属性（↓）********************/
	private boolean success;
	private Integer start;
	private Integer limit;
	private Long totalProperty;
	private String content;
	private List<Menu> items_Menu;
	private Role role;
	private Map session = ActionContext.getContext().getSession();
	
	@Resource
	private MenuService menuService;
	/*****************类属性（↑）********************/

	/*****************类方法（↓）********************/
	/**
	 * 根据权限显示角色可用的菜单
	 * @throws Exception 
	 */
	public String queryRoleMenu() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = menuService
					.queryRoleMenu(start, limit);
			items_Menu = (List<Menu>) map.get("items");
			totalProperty = (Long) map.get("count");
			this.success = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 找到所有的二级菜单
	 * @return
	 * @throws Exception 
	 */
	public String findAllSecondMenu() throws Exception{
		try {
			this.success = false;
			items_Menu= menuService.findAllSecondMenu();
				this.success = true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 根据登录用户显示菜单
	 * @return
	 * @throws Exception 
	 */
	public String showMenuByUser() throws Exception{
		try {
			this.success = false;
			Map session = ActionContext.getContext().getSession();
			Users user = (Users) session.get("user");
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			content = menuService.showMenuByUser(user);
		} catch (RuntimeException e) {
			throw e;
		}
		return "treeJson";
	}

	/*****************类方法（↑）********************/
	/*****************类getset方法（↓）********************/

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Menu> getItems_Menu() {
		return items_Menu;
	}

	public void setItems_Menu(List<Menu> itemsMenu) {
		items_Menu = itemsMenu;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	/*****************类getset方法（↑）********************/
}
