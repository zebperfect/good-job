package com.zebsoft.right.domain;

/**
 * Rolemenu entity. @author MyEclipse Persistence Tools
 */

public class Rolemenu implements java.io.Serializable {

	// Fields

	private String id;
	private Role roleId;
	private Menu menuId;
	private Integer state;

	// Constructors

	/** default constructor */
	public Rolemenu() {
	}

	/** full constructor */
	public Rolemenu(Role roleId, Menu menuId, Integer state) {
		this.roleId = roleId;
		this.menuId = menuId;
		this.state = state;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public Menu getMenuId() {
		return menuId;
	}

	public void setMenuId(Menu menuId) {
		this.menuId = menuId;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}