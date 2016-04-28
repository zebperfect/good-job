package com.zebsoft.monthsettlement.domain;

/**
 * Monthsettlement entity. @author MyEclipse Persistence Tools
 */

public class Monthsettlement implements java.io.Serializable {

	// Fields

	private String settlementid;
	private Integer userid;
	private String telephone;
	private String usercode;
	private Float profitmoney;
	private Float devicemoney;
	private Float totalmoney;
	private String inmonth;
	private Integer state;

	// Constructors

	/** default constructor */
	public Monthsettlement() {
	}

	/** minimal constructor */
	public Monthsettlement(Integer userid, Integer state) {
		this.userid = userid;
		this.state = state;
	}

	/** full constructor */
	public Monthsettlement(Integer userid, String telephone, String usercode,
			Float profitmoney, Float devicemoney, Float totalmoney,
			String inmonth, Integer state) {
		this.userid = userid;
		this.telephone = telephone;
		this.usercode = usercode;
		this.profitmoney = profitmoney;
		this.devicemoney = devicemoney;
		this.totalmoney = totalmoney;
		this.inmonth = inmonth;
		this.state = state;
	}

	// Property accessors

	public String getSettlementid() {
		return this.settlementid;
	}

	public void setSettlementid(String settlementid) {
		this.settlementid = settlementid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public Float getProfitmoney() {
		return this.profitmoney;
	}

	public void setProfitmoney(Float profitmoney) {
		this.profitmoney = profitmoney;
	}

	public Float getDevicemoney() {
		return this.devicemoney;
	}

	public void setDevicemoney(Float devicemoney) {
		this.devicemoney = devicemoney;
	}

	public Float getTotalmoney() {
		return this.totalmoney;
	}

	public void setTotalmoney(Float totalmoney) {
		this.totalmoney = totalmoney;
	}

	public String getInmonth() {
		return this.inmonth;
	}

	public void setInmonth(String inmonth) {
		this.inmonth = inmonth;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}