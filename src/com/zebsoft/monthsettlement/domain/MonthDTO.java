package com.zebsoft.monthsettlement.domain;

/**
 * 封装前台展示具体信息
 * @author 张恩备
 * @date 2016-4-21 下午12:21:26
 */
public class MonthDTO {
	private String settlementid;
	private Integer userid;
	private String telephone;
	private String usercode;
	private String username;
	private String usercard;
	private String bankname;
	private String bankcard;
	private String bankaddress;
	private Float profitmoney;
	private Float devicemoney;
	private Float totalmoney;
	private String inmonth;
	
	public String getSettlementid() {
		return settlementid;
	}
	public void setSettlementid(String settlementid) {
		this.settlementid = settlementid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsercard() {
		return usercard;
	}
	public void setUsercard(String usercard) {
		this.usercard = usercard;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	public String getBankaddress() {
		return bankaddress;
	}
	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}
	public Float getProfitmoney() {
		return profitmoney;
	}
	public void setProfitmoney(Float profitmoney) {
		this.profitmoney = profitmoney;
	}
	public Float getDevicemoney() {
		return devicemoney;
	}
	public void setDevicemoney(Float devicemoney) {
		this.devicemoney = devicemoney;
	}
	public Float getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Float totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getInmonth() {
		return inmonth;
	}
	public void setInmonth(String inmonth) {
		this.inmonth = inmonth;
	}
	
}
