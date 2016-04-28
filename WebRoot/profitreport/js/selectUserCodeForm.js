var selectUnitForm;
var selectUnitGrid;
var selectUnitStore;
var selectUnitWin;
var _selectedUnit;
var insertUnitInfo;
var conditionsSelectButton;
var flag;
Ext.onReady(function() {

	Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
	
	selectUnitForm = new Ext.form.FormPanel({
		title : '请输入查询条件',
		labelAlign : 'center',
		frame : true,
//		keys:[{
//                key: [13],
//                fn:select
//		      }],
		defaultType : 'textfield',
		autoWidth:true,
		padding : 5,
		items : [{
					fieldLabel : '手机号',
					id : 'user.telephone',
					width : 162,
					itemCls : 'only-float',
					clearCls : 'allow-float'
				}, {
					fieldLabel : '客户姓名',
					id : 'user.username',
					width : 162,
					itemCls : 'only-float',
					clearCls : 'allow-float'
				}, {
					xtype : 'button',
					text : '查询',
					iconCls : 'icon-select',
					cls : 'right-part',
					clearCls : 'allow-float',
					handler : function select () {
						selectUnitStore.proxy = new Ext.data.HttpProxy({
							url: 'Users_selectUsers.action'
						});
						selectUnitStore.reload({
								params : {
									start : 0,
									limit : 20,
									'user.telephone' : Ext.getCmp('user.telephone').getValue(),
									'user.username' : Ext.getCmp('user.username').getValue()
								}
						});
					}
				}, {
					xtype : 'button',
					iconCls : 'icon-reset',
					text : '重置',
					handler : function() {
						selectUnitForm.form.reset();
					},
					itemCls : 'right-part',
					clearCls : 'allow-float'
				}]
	});

	selectUnitStore = new Ext.data.JsonStore({
				root : 'items_Users',
				totalProperty : 'totalProperty',
				baseParams : {
					start : 0,
					limit : 10
				},
				fields : ['id', 'telephone', 'usercode','parentiphone', 'username','usable','createtime'],
				autoLoad : false
			});

	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});

	selectUnitGrid = new Ext.grid.GridPanel({
				title : '选定单位',
				store : selectUnitStore,
				sm : sm,
				listeners : {
							'rowclick' : function() {
								_selectedUnit = selectUnitGrid.getSelectionModel().getSelected();
								if(_selectedUnit.get("usercode") == null){
									alert("当前客户编号为空，请选择已经绑定客户编号的用户。");
								}else{									
									Ext.getCmp('flowReport.username.show').setValue(_selectedUnit
											.get("usercode"));
									selectUnitWin.hide();
									if(flag=='base'){
										conditionsSelectButton();
									}
								}
						}},
				columns : [sm, new Ext.grid.RowNumberer({
									header : '序号',
									width : 50
								}), {
							header : '姓名',
							dataIndex : 'username',
							width : 70
						}, {
							header : '手机号',
							dataIndex : 'telephone',
							width : 100
						}, {
							header : '客户编码',
							dataIndex : 'usercode',
							width : 150
						}, {
							header : '推荐人手机',
							dataIndex : 'parentiphone',
							width : 70
						}, {
							header : '用户状态',
							dataIndex : 'usable',
							width : 70
						}, {
							header : '创建时间',
							dataIndex : 'createtime',
							width : 70
						}],
				width : '100%',
				height : '375',
				bbar : [{
							xtype : 'paging',
							pageSize : 10,
							store : selectUnitStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
							emptyMsg : "没有记录"
						}, new Ext.Toolbar.Fill(), {
							xtype : 'button',
							text : '确定',
							handler : function() {
								insertUnitInfo();
							}
						}, {
							xtype : 'button',
							text : '关闭',
							handler : function() {
								selectUnitWin.hide();
							}
						}]
			});

	selectUnitWin = new Ext.Window({
				width : 700,
				height : 480,
				resizable : false,
				title : '选定客户编码',
				layout : 'absolute',
				plain : true,
//				closable : true,
				closeAction:'hide',
				items : [{
							width : '100%',
							height : '20%',
							items : selectUnitForm
						}, {
							y : '15%',
							width : '100%',
							height : '80%',
							items : selectUnitGrid
						}]
			});

	insertUnitInfo = function() {
		_selectedUnit = selectUnitGrid.getSelectionModel().getSelected();
		Ext.getCmp('flowReport.username.show').setValue(_selectedUnit
				.get("usercode"));
		selectUnitWin.hide();
		if(flag=='base'){
			conditionsSelectButton();
		}
	}
});