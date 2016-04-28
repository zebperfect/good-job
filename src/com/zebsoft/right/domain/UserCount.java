package com.zebsoft.right.domain;

/**
 * dto对象，暂存各级用户量
 * @author 张恩备
 * @date 2016-3-14 下午03:36:25
 */
public class UserCount {

	private String id;
	private String userDepth;
	private String usercount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserDepth() {
		return userDepth;
	}
	public void setUserDepth(String userDepth) {
		this.userDepth = userDepth;
	}
	public String getUsercount() {
		return usercount;
	}
	public void setUsercount(String usercount) {
		this.usercount = usercount;
	}
	
}
