var skin = '';
//设置cookie
function setCookie(c_name, value, expiredays) {
	var exdate = new Date()
	exdate.setDate(exdate.getDate() + expiredays)
	document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

this.skin = getCookie('extjsStyle');
//读取cookie
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
function changeSkinOuter(skin) {
	document.getElementById('extjsStyle').href = basePath + 'ExtJs/resources/css/' + skin;
	try {
		document.frames['IndexIframe'].window.document.styleSheets.extjsStyle2.href = basePath + 'ExtJs/resources/css/' + skin;
		// 当换肤时 对已经打开的页面进行变色
		var len = document.getElementsByTagName("iframe").length;
		var iframes = document.getElementsByTagName("iframe");
		for (i = 0; i < len; i++) {
			var id = iframes[i].id;
			document.frames[id].window.document.styleSheets.extjsStyle2.href = basePath + 'ExtJs/resources/css/' + skin;
		}
	} catch (err) {
	}
	// 设置cookie 名称 数据 保存天数遍
	setCookie('extjsStyle', skin, 999999);

}
function changeSkinInter(id, skin) {
	try {
		document.frames[id].window.document.styleSheets.extjsStyle2.href = basePath + 'ExtJs/resources/css/' + skin;
	} catch (err) {
	}
}
function reLogin() {
	Ext.Ajax.request({
				url : "Users_loginOut.action"
			});
	window.location.href = basePath + "login.jsp";
}

function logout() {
	Ext.Ajax.request({
		url : "Users_loginOut.action"
	});
	window.location.href = basePath + "login.jsp";
}

function fullscreen() {
	var w = Ext.getCmp('north');
	w.collapsed ? w.expand() : w.collapse();
	// var w2 = Ext.getCmp('west');
	// w2.collapsed ? w2.expand() : w2.collapse();
}
onload = function() {
	setTimeout(upfullscreenClose, 5000);
}
// 向上全屏
upfullscreen = function() {
	var w = Ext.getCmp('north');
	w.collapsed ? w.expand() : w.collapse();
}
var upfullscreenClose = function() {
	var w = Ext.getCmp('north');
	 w.collapsed ? w.expand() : w.expand();
//	if (w.collapsed) {
//
//	} else {
//		w.collapse();
//	}

	// reload();
}

// 向左全屏
leftfullscreen = function() {
	var w2 = Ext.getCmp('west');
	w2.collapsed ? w2.expand() : w2.collapse();
}
function reload(id) {
	window.document.getElementsByTagName('iframe')[0].src = window.document.getElementsByTagName('iframe')[0].src;
}
function changeUrl(node) {

	if (node.attributes.url != "#") {

		changeUrl2(node);
		// setTimeout(changeSkin, 5000);
	}
}
//加載tab 如果沒有就新建一個,如果有 就直接打開
function addTab(node) {
	upfullscreenClose();
	var tab = Ext.getCmp('mainContent');
	if (!tab.findById(node.attributes.id)) {
		tab.add({
					id : node.attributes.id,
					xtype : "panel",
					closable : true,
					title : node.attributes.text,
					html : '<div><iframe id="' + node.attributes.id + 'Iframe"  onload="changeSkinInter(\'' + node.attributes.id
							+ 'Iframe\',skin);" src="' + node.attributes.href + '" name="' + node.attributes.id
							+ 'Iframe" frameborder="0" style="height:100%;width:100%" scrolling="yes"></div>'
				});
		tab.setActiveTab(node.attributes.id);
	} else {
		tab.setActiveTab(node.attributes.id);
	}
}

function changeUrl2(node) {
	if (node.url != "#") {
		addTab(node)
		// document.getElementById("centeriframe").src = url;
	}
}

// 定义使用改变个性化定制的控件
// 该控制实际上为一个可供选择样式表值的下拉框
// 当改变下拉框的选择时则调用Ext.util.CSS.swapStyleSheet来替换其样式表路径
Ext.ux.ThemeChange = Ext.extend(Ext.form.ComboBox, {
			labelSeparator : ':',
			fieldLabel : '选择皮肤',
			// hideLabel:false,
			editable : false,
			displayField : 'theme',
			valueField : 'css',
			typeAhead : true,
			mode : 'local',
			value : 'ext-all.css',
			readonly : true,
			triggerAction : 'all',
			selectOnFocus : true,
			initComponent : function() {
				var themes = [['更换皮肤', 'ext-all.css'], ['银白色', 'xtheme-silverCherry.css'], ['浅灰色', 'xtheme-gray.css'],
						['橄榄色', 'xtheme-olive.css'], ['橙色', 'xtheme-orange.css'], ['紫色', 'xtheme-purple.css'], ['粉红色', 'xtheme-pink.css'],
						['深灰色', 'xtheme-darkgray.css'], ['gtheme', 'xtheme-gtheme.css'], ['2brave', 'xtheme-2brave.css'],
						['access', 'xtheme-access.css'], ['slickness', 'xtheme-slickness.css']];
				this.store = new Ext.data.SimpleStore({
							fields : ['theme', 'css'],
							data : themes
						});
			},
			initEvents : function() {
				this.on('collapse', function() {
							skin = this.getValue();
							changeSkinOuter(skin);
						});
			}
		});
Ext.reg('xthemeChange', Ext.ux.ThemeChange);

// 菜单面板
/**
 * 扩展了Panel类，其布局设为accordion，所在区域为west；该组件初始化后会根据配置的url和root向后台发
 * 起请求并解析返回的json串，根据parentcode为空的结点生成TreePanel，子节点通过parentcode属性添加为
 * 对应结点的子节点，注意此处每个节点的code必须小于父节点并接大于下方的其它结点;
 * 
 * 1.1更新： 1.不再需要leaf属性，程序内部判断； 2.store用完后即销毁，不再可用；
 * 3.修改了结点点击的触发事件，仅注册一次以减少内存占用，该方法传递给监听函数一个Ext.tree.TreeNode对象，
 * 可通过node.attributes属性获取结点属性；
 * 4.添加了一个getNodeById方法，该方法通过id字符串返回对应Ext.tree.TreeNode对象；
 * 
 * @author chemzqm@gmail.com
 * @version 1.1
 * @since 2010-5-9
 * 
 */

MenuPanel = Ext.extend(Ext.Panel, {
			/**
			 * @cfg(url) 发送请求的地址
			 */
			/**
			 * @cfg(root) json数组的根字符串
			 */
			constructor : function(config) {
				MenuPanel.superclass.constructor.call(this, Ext.apply({
									maxSize : 400,
									minSize : 100,
									collapseMode : 'mini',
									collapsible : true,// 收缩状态
									animCollapse : true,
									animate : true,
									id : 'menuPanel',
									width : 210,
									header : false,
									split : true,
									layout : 'accordion',
									region : 'west',
									autoScroll : true,// 滚动条
									margins : '0 0 0 0',
									cmargins : '0 0 0 0',
									ref : 'menuPanel'
								}, config || {}));
			},
			initComponent : function() {
				MenuPanel.superclass.initComponent.call(this);
				this.addEvents( /**
								 * @event itemclick 树结点被点击时触发 参数：node
								 *        当前结点对象，record 当前结点对应record对象
								 */
						'click', /**
									 * @event afterload 菜单项加载完毕后触发
									 */
						'afterload');
				if (!this.store) {
					this.store = new Ext.data.JsonStore({
								url : this.url,
								root : this.root,
								fields : ['id', 'text', 'parentId', 'iconCls', 'href']
							});
				}
				this.store.load({
							callback : this.loadTrees,
							scope : this
						});
			},
			loadTrees : function(records, o, s) {
				var pnodes, trees = [], tree;
				this.store.sort('id');
				for (var i = 0; i < records.length; i++) {
					var record = records[i];
					if (!record.get('parentId')) {
						tree = this.creatTreeConfig(record);
						trees.push(tree);
						pnodes = [];
						pnodes.push(tree.root);
					} else {
						var next_record = records[i + 1];
						var isLeaf = !next_record || next_record.get('parentId') != record.get('id');
						this.addTreeNode(pnodes, record, isLeaf);
					}
				}
				Ext.each(trees, function(tree) {
							this.add(tree);
						}, this);
				this.mon(this.el, 'click', this.onClick, this);
				this.doLayout();
				this.store.destroy();
				this.fireEvent('afterload', this);
			},
			// @public 根据结点id找到结点对象
			getNodeById : function(id) {
				var node, trees = this.findByType('treepanel', true);
				Ext.each(trees, function(tree) {
							node = tree.getNodeById(id);
							return !node;// 找到的话返回false
						});
				return node;
			},
			onClick : function(e, t, o) {
				if (Ext.fly(t).hasClass('x-tree-ec-icon')) {// 点击伸展按钮时无视
					return;
				}
				var el, id, node;
				if (el = e.getTarget('.x-tree-node-el', 3, true)) {
					e.stopEvent();
					id = el.getAttributeNS('ext', 'tree-node-id');
					node = this.getNodeById(id);
					// var workPanel = this.ownerCt.workPanel;
					// workPanel.showTab(node);//
					// alert(node.attributes.href);
					this.fireEvent('click', this, node);
				}
			},
			creatTreeConfig : function(record) {
				var config = {
					xtype : 'treepanel',
					autoScroll : true,
					rootVisible : false,
					lines : false,
					title : '&nbsp;&nbsp;' + record.get('text'),
					iconCls : 'li-menu',
					// record.get('iconCls'),
					border : false,
					root : {
						nodeType : 'async',
						expanded : true,
						id : record.get('id'),
						children : []
					},
					listeners : {
						// 'deactivate': function(tree){
						// tree.getSelectionModel().clearSelections(true);
						// }

						click : function(node, e) {
							// alert('b:'+node.attributes.href);
							changeUrl(node);
							// alert('a');
						}

					}
				};
				return config;
			},
			addTreeNode : function(pnodes, record, isLeaf) {
				var len = pnodes.length;
				for (var i = len - 1; i >= 0; i--) {
					if (pnodes[i].id != record.get('parentId')) {
						pnodes.pop();
					} else {
						var parent = pnodes[i].children;
						var node = {
							text : record.get('text'),
							id : record.get('id'),
							iconCls : 'li-menu2',
							// record.get('iconCls'),
							href : record.get('href'),
							leaf : isLeaf
						};
						if (!isLeaf) {
							node.children = [];
							pnodes.push(node);
						}
						parent.push(node);
						return;
					}
				}
			},
			// @public根据node对象/id显示结点所在tree并选中
			selectNode : function(node) {
				var tree;
				if (Ext.isString(node)) {
					node = this.getNodeById(node);
				}
				tree = node.getOwnerTree();
				this.getLayout().setActiveItem(tree.getId());
				tree.expandPath(node.getPath());
				tree.getSelectionModel().select(node);
			}
		});

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	// initZoom("zoom");

	var menupanel = new MenuPanel({
				root : 'menus',
				// ref : 'menuPanel',
				// url : 'menu.json',
				url : 'Menu_showMenuByUser.action',
				// tbar : ['<image src="images//user_add.png" border=0 />菜单'],
				listeners : {
					'click' : Ext.emptyFn
				}
			});
	var bodyWidth = document.body.clientWidth;
	var viewport = new Ext.Viewport({
		title : '仁鑫鼎网上管理平台',
		layout : 'border',
		renderTo : Ext.getBody(),
		items : [{// 上边
			title : '',
			id : "north",
			region : 'north',
			xtype : 'panel',
			collapsible : true,
			html:'',
//			html : '<div class="w22"><a class="db logo fl"><font face="新宋体" style="letter-spacing:1px;" size="14">青岛仁鑫鼎商贸有限公司</font></a>'+
//			'<div class="fl textfr"><font size="8"><a href="pages/product/index.jsp">产品介绍</a></font></div><div class="fl textfr"><a href="">使用说明</a></div><div class="fl textfr"><a href="">招商代理</a></div><div class="fl lastfr"><a href="">关于我们</a></div><div class="fr logofr">如注册或使用遇到问题可<a href="http://wpa.qq.com/msgrd?v=3&uin=3400188229&site=qq&menu=yes" target="_blank" title="在线QQ客服">'
//			+'<img src="images/qq.gif"></a><br> 或拨打：<strong style="font-size:14px;">400-883-3697</strong></div></div>',
			 spilt : true,
			height : 130
		}, {	// 左边
					title : '用户：'
							+ userTrueName
							+ '<a onclick="reLogin();"><image src="images//user_add.png" border=0 title=\"重新登录\" /></a>&nbsp;'
							+'<a onclick="logout();"><image src="images//user_delete.png" border=0 title=\"退出登录\" /></a>&nbsp;'
							+'<a onclick="reload()" style="cursor:pointer"><image src="images//refresh.png" border=0 title=\"刷新页面\" /></a>',
					region : 'west',
					id : 'west',
					width : 170, 
					minSize : 200,
					maxSize : 400,
					margins : '0 0 0 0',
					collapsible : true,
					split : true,
					layout : 'fit',
					items : [menupanel]
				}, {
					xtype : 'tabpanel',
					region : 'center',
					resizeTabs : true, // turn on tab resizing
					minTabWidth : 115,
					tabWidth : 135,
					enableTabScroll : true,
					width : 600,
					height : 250,
					defaults : {
						autoScroll : true
					},
					plugins : new Ext.ux.TabCloseMenu(),
					// tbar:[loginButton],
					// contentEl : 'centeriframe',
					collapsible : false,
					id : 'mainContent',
					width : '100%',
					height : '100%',
					activeTab : 0,
					items : [{
						id : 'Index',
						xtype : "panel",
						closable : false,
						title : '主页',
						html : '<div><iframe id="IndexIframe" onload  ="changeSkinInter(\'IndexIframe\',skin);" src="defaultIndex.jsp" name="IndexIframe" frameborder="0" style="height:100%;width:100%" scrolling="yes"></div>'
					}]

				}, {
					region : 'south',
					xtype : 'box',
					html : '<table><tr><td width="130px"><span id ="changeSkinPanel"></span></td><td align="center" width=" '
							+ (bodyWidth - 130)
							+ ' "><span><img height="20px" src="images/logo.gif"/></span><span id="x-tab-panel-header"><strong></strong></h2></span></td></tr></table>'
				}]
	});
	// 换肤功能面板
	var pan = new Ext.Panel({
				// title:'定制个性化风格',
				items : new Ext.ux.ThemeChange(),
				renderTo : 'changeSkinPanel'
			});
	// 如果cookie里有皮肤,加载
	if (skin) {
		changeSkinOuter(skin);
	}

});
