var showUserCountGrid;
var showUserCountStore;
var showUserCountWin;
Ext.onReady(function() {

	Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	var sm = new Ext.grid.CheckboxSelectionModel();
	 var smUseableRoleInfo = new Ext.grid.CheckboxSelectionModel();
	 var smChooseRoleInfo = new Ext.grid.CheckboxSelectionModel();
	keyConvert = function() {
		if (event.keyCode == 13 && event.srcElement.type != "button") {
			event.keyCode = 9;
		}
	};

	showUserCountStore = new Ext.data.JsonStore({
		root : 'items_UserCount',
		totalProperty : 'totalProperty',
		baseParams : {
			start : 0,
			limit : 10
		},
		fields : ['id', 'usercount', 'userDepth'],
		autoLoad : false
	});

	showUserCountForm = new Ext.form.FormPanel({
		title : '请输入查询时间段',
		labelAlign : 'center',
		frame : true,
		defaultType : 'textfield',
		labelWidth : 70,
		buttonAlign:"center",
		padding : 5,
		items : [{
					fieldLabel : '手机号',
					id : 'user.telephone.show',
					xtype : 'textfield',
					disabled:true,
					itemCls : 'right-part',
					clearCls : 'allow-float'
				}, {
					xtype : 'datefield',
					fieldLabel : '开始时间',
					id : 'user.usercard.show',
					name : 'user.usercard',
					format:'Y-m-d H:i:s',      
					editable : false,
					allowBlank : true,
					itemCls : 'right-part',
					clearCls : 'allow-float'
				}, {
					xtype : 'datefield',
					fieldLabel : '结束时间',
					id : 'user.bankcard.show',
					name : 'user.bankcard',
					format:'Y-m-d H:i:s',      
					editable : false,
					allowBlank : true,
					itemCls : 'right-part',
					clearCls : 'allow-float'
				}],
				buttons : [{
					text : '查询',
					iconCls : 'icon-accept',
					handler : function() {
						var starttime = Ext.getCmp('user.usercard.show').getValue();
						var endtime = Ext.getCmp('user.bankcard.show').getValue();
						if(starttime!=''){
							starttime=starttime.format('YmdHis');
						}
						if(endtime!=''){
							endtime=endtime.format('YmdHis');
						}
						showUserCountStore.proxy = new Ext.data.HttpProxy({
							url: 'Users_selectUserCount.action'+'?user.usercard='+starttime+'&user.bankcard='+endtime
						});
						showUserCountStore.reload({
								params : {
									start : 0,
									limit : 10,
									'user.telephone' : Ext.getCmp('user.telephone.show').getValue()
								}
						});
					}
				}, '-', {
					text : '重置',
					iconCls : 'icon-reset',
					handler : function() {
						showUserCountForm.form.reset();
						}
				}, '-', {
					text : "导出到excel",
					iconCls : "icon-print",
					handler : function() {
						var vExportContent = showUserCountGrid.getExcelXml(); // 获取数据
						if (Ext.isIE8 || Ext.isIE6 || Ext.isIE7 || Ext.isSafari
								|| Ext.isSafari2 || Ext.isSafari3) { // 判断浏览器
							var fd = Ext.get('frmDummy');
							if (!fd) {
								fd = Ext.DomHelper.append(Ext.getBody(), {
											tag : 'form',
											method : 'post',
											id : 'frmDummy',
											action : 'public/exportExcelUrl.jsp',
											target : '_blank',
											name : 'frmDummy',
											cls : 'x-hidden',
											cn : [{
														tag : 'input',
														name : 'exportContent',
														id : 'exportContent',
														type : 'hidden'
													}]
										}, true);
							}
							fd.child('#exportContent').set({
										value : vExportContent
									});
							fd.dom.submit();
						} else {
							document.location = 'data:application/vnd.ms-excel;base64,'
									+ Base64.encode(vExportContent);
						}
					}
				}]
	});
	
showUserCountGrid = new Ext.grid.GridPanel({
//		title : '用户统计记录',
		store : showUserCountStore,
		sm : sm,
        autoScroll: true,  
		columns : [sm, {
			header : 'id',
			dataIndex : 'id',
			hidden : true,
			sortable : false
		},{
			header : '层级',
			sortable : true,
			width : 100,
			dataIndex : 'userDepth'
		}, {
			header : '用户数量',
			sortable : true,
			width : 100,
			dataIndex : 'usercount'
		}],
		width : '100%',
		height : '375',
		bbar : [{
					xtype : 'paging',
					pageSize : 10,
					store : showUserCountStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : "没有记录"
				}, new Ext.Toolbar.Fill(), {
					xtype : 'button',
					text : '关闭',
					handler : function() {
						showUserCountWin.hide();
					}
				}]
	});

showUserCountWin = new Ext.Window({
	width : 700,
	height : 500,
	resizable : false,
//	title : '选定单位',
	layout : 'absolute',
	plain : true,
//	closable : true,
	closeAction:'hide',
	items : [{
				width : '100%',
				height : '25%',
				items : showUserCountForm
			}, {
				y : '20%',
				width : '100%',
				height : '75%',
				items : showUserCountGrid
			}]
});
	
});