package com.zebsoft.right.domain;

/**
 * Menu entity. @author MyEclipse Persistence Tools
 */

public class Menu implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String descriptin;
	private String url;
	private String image;
	private Menu parent;
	private Integer state;

	// Constructors

	/** default constructor */
	public Menu() {
	}

	/** minimal constructor */
	public Menu(String name, String url, Menu parent, Integer state) {
		this.name = name;
		this.url = url;
		this.parent = parent;
		this.state = state;
	}

	/** full constructor */
	public Menu(String name, String descriptin, String url, String image,
			Menu parent, Integer state) {
		this.name = name;
		this.descriptin = descriptin;
		this.url = url;
		this.image = image;
		this.parent = parent;
		this.state = state;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptin() {
		return this.descriptin;
	}

	public void setDescriptin(String descriptin) {
		this.descriptin = descriptin;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}