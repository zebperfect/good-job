package com.zebsoft.right.domain;

/**
 * Roleusers entity. @author MyEclipse Persistence Tools
 */

public class Roleusers implements java.io.Serializable {

	// Fields

	private String id;
	private Role roleId;
	private Users usersId;
	private Integer state;

	// Constructors

	/** default constructor */
	public Roleusers() {
	}

	/** full constructor */
	public Roleusers(Role roleId, Users usersId, Integer state) {
		this.roleId = roleId;
		this.usersId = usersId;
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

	public Users getUsersId() {
		return usersId;
	}

	public void setUsersId(Users usersId) {
		this.usersId = usersId;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}