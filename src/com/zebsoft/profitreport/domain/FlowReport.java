package com.zebsoft.profitreport.domain;

/**
 * 分润月报实体类
 * @author 张恩备
 * @date 2016-3-15 下午06:53:18
 */
public class FlowReport {
	//代理商名称， 服务名称，交易金额，分润笔数，分润金额，开始日期，结束日期，实际开始，实际结束
	private String id;
	private String username;
	private String servicename;
	private Float paymoney;
	private Integer profitnum;
	private Float profitmoney;
	private String startdate;
	private String enddate;
	private String realstartdate;
	private String realenddate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public Float getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(Float paymoney) {
		this.paymoney = paymoney;
	}
	public Integer getProfitnum() {
		return profitnum;
	}
	public void setProfitnum(Integer profitnum) {
		this.profitnum = profitnum;
	}
	public Float getProfitmoney() {
		return profitmoney;
	}
	public void setProfitmoney(Float profitmoney) {
		this.profitmoney = profitmoney;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getRealstartdate() {
		return realstartdate;
	}
	public void setRealstartdate(String realstartdate) {
		this.realstartdate = realstartdate;
	}
	public String getRealenddate() {
		return realenddate;
	}
	public void setRealenddate(String realenddate) {
		this.realenddate = realenddate;
	}
	
}
