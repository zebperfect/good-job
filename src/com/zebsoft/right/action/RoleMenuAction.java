package com.zebsoft.right.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.right.domain.Menu;
import com.zebsoft.right.domain.Role;
import com.zebsoft.right.domain.Users;
import com.zebsoft.right.service.RoleMenuService;

/**
 * 类说明：角色-菜单（权限）关联管理
 * @author zeb
 * @since 2014年3月11日21:02:39
 * @version V1.0
 */
@Controller("roleMenuAction")
@Scope("prototype")
public class RoleMenuAction extends ActionSupport{

	/*****************类属性（↓）********************/
	private Map session = ActionContext.getContext().getSession();
	private boolean success;
	private Role role;
	private List<Menu> items_Menu;
	private Integer  totalProperty;
	private String menuId;
	
	@Resource
	private RoleMenuService roleMenuService;
	/*****************类属性（↑）********************/

	/*****************类方法（↓）********************/
		/**
		 * 通过角色Id找到拥有的二级菜单
		 * @throws Exception 
		 * 
		 */
		public String findMenuByRole() throws Exception{
			try {
				this.success = false;
				Map<String , Object> map= roleMenuService.findMenuByRole(role.getId());
				items_Menu = (List<Menu>) map.get("items");
				totalProperty =  (Integer) map.get("count");
				success = true;
			} catch (Exception e) {
				// TODO: handle exception
				success = false;
				throw e;
			}
			return SUCCESS;
		}
	
	/**
	 * 给角色添加菜单
	 * @return
	 * @throws Exception 
	 */
	public String addRoleMenu() throws Exception{
		try {
			Users user = (Users) session.get("user");
			this.success = false;
			if(roleMenuService.addRoleMenu(role.getId(),menuId,user)){
				this.success = true;
			}
			else 
				this.success=false;
		} catch (Exception e) {
			// TODO: handle exception
			success = false;
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 给角色删除菜单（支持多条删除）
	 * @return
	 * @throws Exception 
	 */
	public String delRoleMenu() throws Exception{
		try {
			Users user = (Users) session.get("user");
			this.success = false;
			if(roleMenuService.delRoleMenu(role.getId(),menuId,user)){
				this.success = true;
			}
			else 
				this.success=false;
		} catch (Exception e) {
			// TODO: handle exception
			success = false;
			throw e;
		}
		return SUCCESS;
	}
	/*****************类方法（↑）********************/
	/*****************类getset方法（↓）********************/

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public List<Menu> getItems_Menu() {
		return items_Menu;
	}

	public void setItems_Menu(List<Menu> itemsMenu) {
		items_Menu = itemsMenu;
	}

	public Integer getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(Integer totalProperty) {
		this.totalProperty = totalProperty;
	}
	
	/*****************类getset方法（↑）********************/
}
