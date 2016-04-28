package com.zebsoft.profitreport.domain;

/**
 * dto对象，存储用户设备提成统计
 * @author 张恩备
 * @date 2016-3-14 下午04:16:27
 */
public class UserDTO {

	private String id;
	private String userdepth;
	private String usercount;
	private String profitfee;
	private String profitcount;
	private String username;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserdepth() {
		return userdepth;
	}
	public void setUserdepth(String userdepth) {
		this.userdepth = userdepth;
	}
	public String getUsercount() {
		return usercount;
	}
	public void setUsercount(String usercount) {
		this.usercount = usercount;
	}
	
	public String getProfitfee() {
		return profitfee;
	}
	public void setProfitfee(String profitfee) {
		this.profitfee = profitfee;
	}
	public String getProfitcount() {
		return profitcount;
	}
	public void setProfitcount(String profitcount) {
		this.profitcount = profitcount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
