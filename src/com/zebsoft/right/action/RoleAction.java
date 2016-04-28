package com.zebsoft.right.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zebsoft.right.domain.Role;
import com.zebsoft.right.service.RoleService;


/**
 * 类说明：角色管理
 * @author zeb
 * @since 2014年3月11日21:02:39
 * @version V1.0
 */
@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends ActionSupport{
	
	/*****************类属性（↓）********************/
	private boolean success;
	private Integer start;
	private Integer limit;
	private List<Role> items_Role;
	private long totalProperty;
	private Role role;
	private String delData;
	
	@Resource
	private RoleService roleService;
	/*****************类属性（↑）********************/

	/*****************类方法（↓）********************/
	/**
	 * 分页显示所有角色
	 * @return
	 * @throws Exception 
	 */
	public String queryRole() throws Exception{
		try {
			this.success = false;
			Map<String, Object> map = roleService
					.queryRole(start, limit);
			items_Role = (List<Role>) map.get("items");
			totalProperty = (Long) map.get("count");
			this.success = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加角色
	 * @return
	 * @throws Exception 
	 */
	public String addRole() throws Exception{
		try {
			this.success = false;
			if (roleService.addRole(role)) {
				this.success = true;
			} else{
				this.success = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除角色（支持多条删除）
	 * @return
	 * @throws Exception 
	 */
	public String delRole() throws Exception{
		try {
			this.success = false;
			if(roleService.delRole(delData)){
				this.success = true;
			}
			else{
				this.success = false;
				return ERROR;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return SUCCESS;	
	}
	
	/**
	 * 修改角色信息
	 * @return
	 * @throws Exception 
	 */
	public String updateRole() throws Exception{
		try {
			this.success = false;
			if(roleService.updateRole(role)){
				this.success = true;
			}
			else{
				this.success = false;
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 根据角色Id查找角色
	 * @return
	 * @throws Exception 
	 */
	public String findRoleById() throws Exception{
		try {
			this.success = false;
			items_Role=new ArrayList<Role>();
			items_Role.add(roleService.findRoleById(role.getId()));
			if (items_Role.size() ==1) {
				this.success = true;
			} else
				return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
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

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<Role> getItems_Role() {
		return items_Role;
	}

	public void setItems_Role(List<Role> itemsRole) {
		items_Role = itemsRole;
	}

	public long getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(long totalProperty) {
		this.totalProperty = totalProperty;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getDelData() {
		return delData;
	}

	public void setDelData(String delData) {
		this.delData = delData;
	}
	
	/*****************类getset方法（↑）********************/
}
