var selectSettleForm;
var selectSettleGrid;
var selectSettleStore;
var selectSettleWin;
var _selectedSettle;
var insertSettleInfo;
var conditionsSelectButton;
var flag;
Ext.onReady(function() {

	Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
	
	selectSettleForm = new Ext.form.FormPanel({
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
					id : 'user.telephone1',
					width : 162,
					itemCls : 'only-float',
					clearCls : 'allow-float'
				}, {
					fieldLabel : '客户姓名',
					id : 'user.username1',
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
						selectSettleStore.proxy = new Ext.data.HttpProxy({
							url: 'Users_selectUsers.action'
						});
						selectSettleStore.reload({
								params : {
									start : 0,
									limit : 20,
									'user.telephone' : Ext.getCmp('user.telephone1').getValue(),
									'user.username' : Ext.getCmp('user.username1').getValue()
								}
						});
					}
				}, {
					xtype : 'button',
					iconCls : 'icon-reset',
					text : '重置',
					handler : function() {
						selectSettleForm.form.reset();
					},
					itemCls : 'right-part',
					clearCls : 'allow-float'
				}]
	});

	selectSettleStore = new Ext.data.JsonStore({
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

	selectSettleGrid = new Ext.grid.GridPanel({
				title : '选定单位',
				store : selectSettleStore,
				sm : sm,
				listeners : {
							'rowclick' : function() {
								_selectedSettle = selectSettleGrid.getSelectionModel().getSelected();
								if(_selectedSettle.get("usercode") == null){
									alert("当前客户编号为空，请选择已经绑定客户编号的用户。");
								}else{									
									Ext.getCmp('user.id.show').setValue(_selectedSettle
											.get("id"));
									Ext.getCmp('user.usercode.show').setValue(_selectedSettle
											.get("usercode"));
									selectSettleWin.hide();
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
							store : selectSettleStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
							emptyMsg : "没有记录"
						}, new Ext.Toolbar.Fill(), {
							xtype : 'button',
							text : '确定',
							handler : function() {
								insertSettleInfo();
							}
						}, {
							xtype : 'button',
							text : '关闭',
							handler : function() {
								selectSettleWin.hide();
							}
						}]
			});

	selectSettleWin = new Ext.Window({
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
							items : selectSettleForm
						}, {
							y : '15%',
							width : '100%',
							height : '80%',
							items : selectSettleGrid
						}]
			});

	insertSettleInfo = function() {
		_selectedSettle = selectSettleGrid.getSelectionModel().getSelected();
		Ext.getCmp('user.id.show').setValue(_selectedSettle
				.get("id"));
		Ext.getCmp('user.usercode.show').setValue(_selectedSettle
				.get("usercode"));
		selectSettleWin.hide();
		if(flag=='base'){
			conditionsSelectButton();
		}
	}
});