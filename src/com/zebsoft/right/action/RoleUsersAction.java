package com.zebsoft.right.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.right.domain.Role;
import com.zebsoft.right.domain.Users;
import com.zebsoft.right.service.RoleUsersService;

/**
 * 类说明：角色-用户关联管理
 * @author zeb
 * @since 2014年3月11日21:02:39
 * @version V1.0
 */
@Controller("roleUsersAction")
@Scope("prototype")
public class RoleUsersAction extends ActionSupport{

	/*****************类属性（↓）********************/
	private Map session = ActionContext.getContext().getSession();
	private List<Role> items_Role;
	private boolean success;
	private Users user;
	private String roleId;
	private String userId;
	private Integer totalProperty;
	@Resource
	private RoleUsersService roleUsersService;
	/*****************类属性（↑）********************/

	/*****************类方法（↓）********************/
	/**
	 * 通过用户Id找到角色
	 * @throws Exception 
	 * 
	 */
	public String findRoleByUsersId() throws Exception{
		try {
			this.success = false;
			 Map<String , Object> map= roleUsersService.findRoleByUsersId(String.valueOf(user.getId()));
			 items_Role = (List<Role>) map.get("items");
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
	 * 给用户添加角色（一次只能添加一个角色）
	 * @return
	 * @throws Exception 
	 */
	public String addRoleUsers() throws Exception{
		try {
			Users user = (Users) session.get("user");
			this.success = false;
			if(roleUsersService.addRoleUsers(userId,roleId,user)){
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
	 * 给用户删除角色（一次只能删除一个角色）
	 * @return
	 * @throws Exception 
	 */
	public String delRoleUsers() throws Exception{
		try {
			Users user = (Users) session.get("user");
			this.success = false;
			if(roleUsersService.delRoleUsers(userId,roleId,user)){
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
	public List<Role> getItems_Role() {
		return items_Role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(Integer totalProperty) {
		this.totalProperty = totalProperty;
	}

	public void setItems_Role(List<Role> itemsRole) {
		items_Role = itemsRole;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	/*****************类getset方法（↑）********************/
}
