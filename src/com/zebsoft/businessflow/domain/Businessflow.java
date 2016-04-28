package com.zebsoft.businessflow.domain;

/**
 * Businessflow entity. @author MyEclipse Persistence Tools
 */

public class Businessflow implements java.io.Serializable {

	// Fields

	private String payid;
	private String servicecode;
	private String paycode;
	private String paydate;
	private String paytime;
	private String tradingflow;
	private String localdate;
	private String usercode;
	private String useriphone;
	private String tradingchannel;
	private String endcode;
	private String psamcard;
	private String busacquirer;
	private String busflow;
	private String busnumber;
	private String busdate;
	private String bustime;
	private String busenddate;
	private String sendbusflow;
	private String sendbusdate;
	private String sendbustime;
	private String paymainacc;
	private String paysecondacc;
	private Float paymoney;
	private Float payfee;
	private String paystate;
	private String paydesc;
	private String paymark;
	private Integer state;

	// Constructors

	/** default constructor */
	public Businessflow() {
	}

	/** full constructor */
	public Businessflow(String servicecode, String paycode, String paydate,
			String paytime, String tradingflow, String localdate,
			String usercode, String useriphone, String tradingchannel,
			String endcode, String psamcard, String busacquirer,
			String busflow, String busnumber, String busdate, String bustime,
			String busenddate, String sendbusflow, String sendbusdate,
			String sendbustime, String paymainacc, String paysecondacc,
			Float paymoney, Float payfee, String paystate, String paydesc,
			String paymark, Integer state) {
		this.servicecode = servicecode;
		this.paycode = paycode;
		this.paydate = paydate;
		this.paytime = paytime;
		this.tradingflow = tradingflow;
		this.localdate = localdate;
		this.usercode = usercode;
		this.useriphone = useriphone;
		this.tradingchannel = tradingchannel;
		this.endcode = endcode;
		this.psamcard = psamcard;
		this.busacquirer = busacquirer;
		this.busflow = busflow;
		this.busnumber = busnumber;
		this.busdate = busdate;
		this.bustime = bustime;
		this.busenddate = busenddate;
		this.sendbusflow = sendbusflow;
		this.sendbusdate = sendbusdate;
		this.sendbustime = sendbustime;
		this.paymainacc = paymainacc;
		this.paysecondacc = paysecondacc;
		this.paymoney = paymoney;
		this.payfee = payfee;
		this.paystate = paystate;
		this.paydesc = paydesc;
		this.paymark = paymark;
		this.state = state;
	}

	// Property accessors

	public String getPayid() {
		return this.payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public String getServicecode() {
		return this.servicecode;
	}

	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}

	public String getPaycode() {
		return this.paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getPaydate() {
		return this.paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getPaytime() {
		return this.paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getTradingflow() {
		return this.tradingflow;
	}

	public void setTradingflow(String tradingflow) {
		this.tradingflow = tradingflow;
	}

	public String getLocaldate() {
		return this.localdate;
	}

	public void setLocaldate(String localdate) {
		this.localdate = localdate;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUseriphone() {
		return this.useriphone;
	}

	public void setUseriphone(String useriphone) {
		this.useriphone = useriphone;
	}

	public String getTradingchannel() {
		return this.tradingchannel;
	}

	public void setTradingchannel(String tradingchannel) {
		this.tradingchannel = tradingchannel;
	}

	public String getEndcode() {
		return this.endcode;
	}

	public void setEndcode(String endcode) {
		this.endcode = endcode;
	}

	public String getPsamcard() {
		return this.psamcard;
	}

	public void setPsamcard(String psamcard) {
		this.psamcard = psamcard;
	}

	public String getBusacquirer() {
		return this.busacquirer;
	}

	public void setBusacquirer(String busacquirer) {
		this.busacquirer = busacquirer;
	}

	public String getBusflow() {
		return this.busflow;
	}

	public void setBusflow(String busflow) {
		this.busflow = busflow;
	}

	public String getBusnumber() {
		return this.busnumber;
	}

	public void setBusnumber(String busnumber) {
		this.busnumber = busnumber;
	}

	public String getBusdate() {
		return this.busdate;
	}

	public void setBusdate(String busdate) {
		this.busdate = busdate;
	}

	public String getBustime() {
		return this.bustime;
	}

	public void setBustime(String bustime) {
		this.bustime = bustime;
	}

	public String getBusenddate() {
		return this.busenddate;
	}

	public void setBusenddate(String busenddate) {
		this.busenddate = busenddate;
	}

	public String getSendbusflow() {
		return this.sendbusflow;
	}

	public void setSendbusflow(String sendbusflow) {
		this.sendbusflow = sendbusflow;
	}

	public String getSendbusdate() {
		return this.sendbusdate;
	}

	public void setSendbusdate(String sendbusdate) {
		this.sendbusdate = sendbusdate;
	}

	public String getSendbustime() {
		return this.sendbustime;
	}

	public void setSendbustime(String sendbustime) {
		this.sendbustime = sendbustime;
	}

	public String getPaymainacc() {
		return this.paymainacc;
	}

	public void setPaymainacc(String paymainacc) {
		this.paymainacc = paymainacc;
	}

	public String getPaysecondacc() {
		return this.paysecondacc;
	}

	public void setPaysecondacc(String paysecondacc) {
		this.paysecondacc = paysecondacc;
	}

	public Float getPaymoney() {
		return this.paymoney;
	}

	public void setPaymoney(Float paymoney) {
		this.paymoney = paymoney;
	}

	public Float getPayfee() {
		return this.payfee;
	}

	public void setPayfee(Float payfee) {
		this.payfee = payfee;
	}

	public String getPaystate() {
		return this.paystate;
	}

	public void setPaystate(String paystate) {
		this.paystate = paystate;
	}

	public String getPaydesc() {
		return this.paydesc;
	}

	public void setPaydesc(String paydesc) {
		this.paydesc = paydesc;
	}

	public String getPaymark() {
		return this.paymark;
	}

	public void setPaymark(String paymark) {
		this.paymark = paymark;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}