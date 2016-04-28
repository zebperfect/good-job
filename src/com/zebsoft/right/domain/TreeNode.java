package com.zebsoft.right.domain;

import java.util.List;

public class TreeNode {
	private String cls;// 添加到节点的样式
	private String href;// 设置节点的连接属性
	private String hrefTarget;// 设置将要显示节点连接的目标框架
	private String icon;// 设置节点图标对应的路径
	private String iconCls;// 设置节点图标对应的样式
	private String qtip;// 设置节点上的提示信息
	private String text;// 设置显示在节点上的文本信息
	private String id;
	private List<TreeNode> children;
	private boolean leaf;
	private String parentId;
	private int theSort;

	public TreeNode() {
	};

	public TreeNode(String cls, String href, String hrefTarget, String icon, String iconCls, String qtip, String text,
			String id, List<TreeNode> children, boolean leaf) {
		super();
		this.cls = cls;
		this.href = href;
		this.hrefTarget = hrefTarget;
		this.icon = icon;
		this.iconCls = iconCls;
		this.qtip = qtip;
		this.text = text;
		this.id = id;
		this.children = children;
		this.leaf = leaf;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getTheSort() {
		return theSort;
	}

	public void setTheSort(int theSort) {
		this.theSort = theSort;
	}

}
