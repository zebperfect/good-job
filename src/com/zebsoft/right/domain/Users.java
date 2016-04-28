package com.zebsoft.right.domain;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable {

	// Fields

	private Integer id;
	private String telephone;
	private String password;
	private String usercode;
	private Integer parentid;
	private String parentiphone;
	private Integer depth;
	private String path;
	private String username;
	private String usercard;
	private String bankname;
	private String bankcard;
	private String bankaddress;
	private Integer shiptype;
	private String shipaddress;
	private String paymoney;
	private String userdesc;
	private String createtime;
	private Integer usable;
	private Integer state;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(String telephone, String password, Integer usable,
			Integer state) {
		this.telephone = telephone;
		this.password = password;
		this.usable = usable;
		this.state = state;
	}

	/** full constructor */
	public Users(String telephone, String password, String usercode,
			Integer parentid, String parentiphone, Integer depth, String path, String username,
			String usercard, String bankname,String bankcard, String bankaddress,String paymoney,
			Integer shiptype, String shipaddress, String userdesc, String createtime, Integer usable,
			Integer state) {
		this.telephone = telephone;
		this.password = password;
		this.usercode = usercode;
		this.parentid = parentid;
		this.parentiphone = parentiphone;
		this.depth = depth;
		this.path = path;
		this.username = username;
		this.usercard = usercard;
		this.bankname = bankname;
		this.bankcard = bankcard;
		this.bankaddress = bankaddress;
		this.shiptype = shiptype;
		this.shipaddress = shipaddress;
		this.paymoney = paymoney;
		this.userdesc = userdesc;
		this.createtime = createtime;
		this.usable = usable;
		this.state = state;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsercard() {
		return this.usercard;
	}

	public void setUsercard(String usercard) {
		this.usercard = usercard;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankaddress() {
		return this.bankaddress;
	}

	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}

	public Integer getShiptype() {
		return this.shiptype;
	}

	public void setShiptype(Integer shiptype) {
		this.shiptype = shiptype;
	}

	public String getShipaddress() {
		return this.shipaddress;
	}

	public void setShipaddress(String shipaddress) {
		this.shipaddress = shipaddress;
	}

	public String getUserdesc() {
		return userdesc;
	}

	public void setUserdesc(String userdesc) {
		this.userdesc = userdesc;
	}

	public Integer getUsable() {
		return this.usable;
	}

	public void setUsable(Integer usable) {
		this.usable = usable;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getParentiphone() {
		return parentiphone;
	}

	public void setParentiphone(String parentiphone) {
		this.parentiphone = parentiphone;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	public String getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}

}