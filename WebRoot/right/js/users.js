var ss = null;
var useableRoleInfoGrid;
var chooseRoleInfoGrid;
var usersId;

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

	var userStore = new Ext.data.JsonStore({
		proxy : new Ext.data.HttpProxy({
					url : 'Users_queryUsers.action'
				}),
		root : 'items_Users',
		totalProperty : 'totalProperty',
		fields : ['id', 'telephone', 'usercode', 'parentid','parentiphone', 'username', 'usercard','bankcard'
		          , 'bankname', 'bankaddress', 'shiptype', 'shipaddress', 'userdesc', 'usable','createtime']
			// autoLoad : false
		});

	userStore.load({
				params : {
					start : 0,
					limit : 20
				}
			});
 
//users是用于显示数据，user是向后台传递数据	
	selectUsersForm = new Ext.form.FormPanel(
			{
				title : '查询条件',
				renderTo : 'users',
				labelAlign : 'right',
				buttonAlign : 'center',
				layout : 'column',
				region : 'north',
				frame : true,
				collapsible :true,
				defaultType : 'textfield',
				padding : 10,
				keys:[{
                    key:13,  //13代表回车
                    fn:function(){
                         document.getElementById("btnsubmit").click();
                    },
                    scope:this
                }],
				hight : 100,
				items : [{
					layout : 'column',
					xtype : 'fieldset',
					title : '用户查询',
					width : '100%',
					labelAlign : 'right',
					items : [{
								columnWidth : .3,
								layout : 'form',
								labelWidth : 80,
								items : [{
									xtype : 'textfield',
									name : 'user.username',
									id : 'user.username',
									fieldLabel : '用户姓名',
									allowBlank : true,
									anchor : '80%'
								},{
									xtype : 'textfield',
									name : 'user.telephone',
									id : 'user.telephone',
									fieldLabel : '手机号',
									allowBlank : true,
									anchor : '80%'										
									}]
								},{
									columnWidth : .3,
									layout : 'form',
									labelWidth : 80,
									items : [{
										xtype : 'datefield',
										fieldLabel : '开始日期',
										id : 'user.usercard',
										name : 'user.usercard',
										format:'Y-m-d H:i:s',      
										editable : false,
										allowBlank : false,
										anchor : '80%'
									},{
										xtype : 'datefield',
										fieldLabel : '结束日期',
										id : 'user.bankcard',
										name : 'user.bankcard',
										format:'Y-m-d H:i:s',      
										editable : false,
										allowBlank : false,
										anchor : '80%'
									}]
									},{
										columnWidth : .3,
										layout : 'form',
										labelWidth : 80,
										items : [{
											fieldLabel : '用户状态',
											xtype : 'combo',
											anchor : '80%',
											valueField : "id",
											displayField : "name",
											mode : 'local',
											forceSelection : true,
											emptyText : '--请选择--',
											hiddenName : 'user.usable',
											editable : false,
											triggerAction : 'all',
											store:new Ext.data.SimpleStore({
										        fields: ['id', 'name'],
										        data:[['0','未激活'],
										              	['1','已激活'],
										              	['2','已发货'],
												        ['3','未绑定编码'],
												        ['4','已绑定']
										        ]
										    }),
											id : 'user.usable.show',
											allowBlank : true
										}]
										}]
					}],
				buttons : [{
								text : '查询',
								iconCls : 'icon-accept',
								cls : 'right-part',
								id:'btnsubmit',
								handler : function() {
									var starttime = Ext.getCmp('user.usercard').getValue();
									var endtime = Ext.getCmp('user.bankcard').getValue();
									if(starttime!=''){
										starttime=starttime.format('YmdHis');
									}
									if(endtime!=''){
										endtime=endtime.format('YmdHis');
									}
									userStore.proxy = new Ext.data.HttpProxy(
											{
												url : 'Users_selectUsers.action'+'?user.usercard='+starttime+'&user.bankcard='+endtime
											});
									userStore.load( {
												params : {
													start : 0,
													limit : 20,
													'user.username' : Ext.getCmp('user.username').getValue(),
													'user.usable' : Ext.getCmp('user.usable.show').getValue(),
													'user.telephone' : Ext.getCmp('user.telephone').getValue()
												}
											});
								}
							}, '-', {
								text : '重置',
								iconCls : 'icon-reset',
								handler : function() {
									selectUsersForm.form.reset();
								},
								cls : 'right-part'// ,
							}, '-', {
								text : "导出到excel",
								iconCls : "icon-print",
								handler : function() {
									Ext.MessageBox.confirm('确认', '你确认要导出以上时间段查找出的数据吗？<br\>'+
											'<font color="red">*Excel导出只针对时间段查询，其他查询字段无用</font>', function(btn) {
										if (btn == "yes") {

											var starttime = Ext.getCmp('user.usercard').getValue();
											var endtime = Ext.getCmp('user.bankcard').getValue();
											if(starttime!=''){
												starttime=starttime.format('Ymd');
											}
											if(endtime!=''){
												endtime=endtime.format('Ymd');
											}
											var url = "Users_exportExcel.action"+'?user.usercard='+starttime+'&user.bankcard='+endtime;
											Ext.Ajax.request({
														url : url,
														success : function(response, options) {
															var obj = Ext.util.JSON
																	.decode(response.responseText);
															if (obj.success == true) {
																window.open(basePath + "/" + obj.path);
															} else {
																Ext.Ghost.msg('提示', '导出失败');
															}
														}
													});
										}
									})
								}
							}]
			});

	// 创建用于展示的grid
	var usersGrid = new Ext.grid.GridPanel({
		renderTo : 'users',
		store : userStore,
		sm : sm,
		id : 'usersGrid',
		// height : defaultHeight,
		region : 'center',
		keys : [{
					key : [13],
					fn : keyConvert
				}],
		columns : [sm, {
					header : 'id',
					dataIndex : 'id',
					hidden : true,
					sortable : false
				},{
					header : '客户手机号',
					sortable : true,
					width : 120,
					dataIndex : 'telephone',
					renderer : function(value) {
						return "<a href=javascript:updateUser() title = '查看客户基本信息'>" + value + "</a>";
						}
				}, {
					header : '客户编码',
					sortable : true,
					width : 120,
					dataIndex : 'usercode',
					renderer : function(value) {
						return "<a href=javascript:updateUserCode() title = '绑定客户编码'>" + value + "</a>";
						}
				}, {
					header : '用户分级统计',
					width : 100,
					dataIndex : 'usercode',
					renderer : function(value) {
						return "<a href=javascript:showUserCount() title = '查看该用户分级统计'>" + "用户统计" + "</a>";
					}
				}, {
					header : '推荐人',
					sortable : true,
					width : 120,
					dataIndex : 'parentiphone',
					renderer : function(value) {
						if(value == '13888888888'){
							return "无推荐人";
						}else{
							return value;
						}
					}
				},{
					header : '姓名',
					sortable : true,
					width : 150,
					dataIndex : 'username'
				},{
					header : '身份证号',
					sortable : true,
					width : 200,
					dataIndex : 'usercard'
				},{
					header : '激活状态',
					sortable : true,
					width : 80,
					dataIndex : 'usable',
					renderer : function(value) {
						if (value == '0'){							
							return "未激活";
						}else if (value == "1"){							
							return "已激活";
						}else if (value == "2"){							
							return "已发货";
						}else if (value == "3"){							
							return "未绑定编码";
						}else if (value == "4"){							
							return "已绑定";
						}
					}
				},{
					header : '开户行',
					sortable : true,
					width : 100,
					dataIndex : 'bankname'
				},{
					header : '开户帐号',
					sortable : true,
					width : 100,
					dataIndex : 'bankcard'
				},{
					header : '开户地址',
					sortable : true,
					width : 100,
					dataIndex : 'bankaddress'
				},{
					header : '物流类型',
					sortable : true,
					width : 80,
					dataIndex : 'shiptype',
					renderer : function(value) {
						if (value == '0'){							
							return "省外";
						}else if (value == "1"){							
							return "省内";
						}else if (value == "2"){							
							return "自取";
						}
					}
				},{
					header : '收货地址',
					sortable : true,
					width : 150,
					dataIndex : 'shipaddress'
				},{
					header : '备注',
					sortable : true,
					width : 150,
					dataIndex : 'userdesc'
				},{
					header : '创建时间',
					sortable : true,
					width : 150,
					dataIndex : 'createtime',
					renderer:timeConvert
				}],
		tbar : [{
			text : '添加用户',
			tooltip : '添加用户',
			iconCls : "icon-add",
			handler : function() {
				addForm.form.reset();
				winTitle = "添加用户";
				addWin.setTitle(winTitle);
				addWin.show();
			}
		}, '-', {
			text : '修改用户',
			tooltip : '修改被选择的用户信息',
			iconCls : "icon-edit",
			handler : function() {
				updateUser();
			}
		}, '-', {
			text : '修改状态',
			tooltip : '修改被选择的用户状态信息',
			iconCls : "icon-edit",
			handler : function() {
				updateUsable();
			}
		}, '-', {
			text : '绑定客户编码',
			tooltip : '绑定被选择的用户编码信息',
			iconCls : "icon-edit",
			handler : function() {
				updateUserCode();
			}
		}, '-', {
			id : '',
			text : '重置密码',
			iconCls : 'li-key',
			tooltip : '给选中的用户重置密码',
			handler : function() {
				resetPassword();
			}
		}, '-', {
			text : '删除用户',
			tooltip : '删除被选择的用户',
			iconCls : "icon-del",
			handler : function() {
				_record = usersGrid.getSelectionModel().getSelections();
				flag = usersGrid.getSelectionModel().getSelected();
				if (_record.length == 0) {
					Ext.Msg.alert('警告', '请选择你要删除的用户！');
				} else {
					Ext.MessageBox.confirm('确认删除', '你确认要删除该用户吗？(不推荐您使用此功能，可尝试修改该用户激活状态限制其使用)',
							function(btn) {
								if (btn == "yes") {
									var jsonData = "";
									for (var i = 0; i < _record.length; i++) {
										ss = _record[i].get("id");
										if (i == 0) {
											jsonData = jsonData + ss;
										} else {
											jsonData = jsonData + ","
													+ ss;
										}
									}
									Ext.Ajax.request({
										url : "Users_delUsers.action",
										params : {
											delData : jsonData
										},
										success : function(from, action) {
											userStore.reload();
											Ext.Msg
													.alert('成功',
															'删除成功！');

										},
										failure : function(form, action) {
											Ext.Msg
													.alert('失败',
															'删除失败！');
										}
									});
								}
							});
				}
			}
		}],
		bbar : [{
					xtype : 'paging',
					pageSize : 20,
					store : userStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : "没有记录"
				}]
	});
	updateUser = function(){
		_record = usersGrid.getSelectionModel().getSelected();
		flag = usersGrid.getSelectionModel().getSelections();
		if (flag.length == 1) {
			winTitle = "修改用户信息";
			updateWin.setTitle(winTitle);
			updateWin.show();
			updateForm.getForm().load({
				url : 'Users_findUsersById.action?user.id='
						+ _record.get('id'),
				waitMsg : '正在载入数据...',
				failure : function() {
					Ext.Msg.alert('编辑', '载入失败');
				},
				success : function() {
				}
			});
		} else
			Ext.Msg.alert('错误', '请选择一道要编辑的用户！');
	};
	//修改用户状态
	updateUsable = function(){
		_record = usersGrid.getSelectionModel().getSelected();
		flag = usersGrid.getSelectionModel().getSelections();
		if (flag.length == 1) {
			Ext.getCmp("updateId1").setValue(_record.get("id"));
			Ext.getCmp("updateTelephone1").setValue(_record.get("telephone"));
			Ext.getCmp("updateUsable1").setValue(_record.get("usable"));
			Ext.getCmp("updateUserdesc1").setValue(_record.get("userdesc"));
			winTitle = "修改用户状态";
			usableWin.setTitle(winTitle);
			usableWin.show();
			usableForm.getForm().load();
		} else
			Ext.Msg.alert('错误', '请选择一道要编辑的用户！');
	};
	
	//绑定客户编码
	updateUserCode = function(){
		_record = usersGrid.getSelectionModel().getSelected();
		flag = usersGrid.getSelectionModel().getSelections();
		if (flag.length == 1) {
			Ext.getCmp("updateId").setValue(_record.get("id"));
			Ext.getCmp("updateTelephone").setValue(_record.get("telephone"));
			Ext.getCmp("updateUsercode").setValue(_record.get("usercode"));
			Ext.getCmp("updateUsable").setValue(_record.get("usable"));
			Ext.getCmp("updateUserdesc").setValue(_record.get("userdesc"));
			winTitle = "绑定客户编码信息";
			usercodeWin.setTitle(winTitle);
			usercodeWin.show();
			usercodeForm.getForm().load();
		} else
			Ext.Msg.alert('错误', '请选择一道要编辑的用户！');
	};
	
	// 重置密码
	var resetPassword = function() {
		var record = usersGrid.getSelectionModel().getSelected();
		var selections = usersGrid.getSelectionModel().getSelections();
		if (selections.length == 1) {
			Ext.Msg.confirm('提示', '你确定要重置该用户的密码吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : 'Users_resetPassword.action?user.id='
										+ record.get('id'),
								success : function(response, options) {
									var obj = Ext.util.JSON.decode(response.responseText);
									if (obj.success == true) {
										Ext.Ghost.msg('提示', '重置密码成功');

									} else {
										Ext.Ghost.msg('提示', '重置密码失败');
									}
								}
							});
				}
			});
		} else {
			Ext.Ghost.msg('提示', '请您选择一条记录');
		}
	};
	
	//查看用户分级统计
	showUserCount = function(){
		_record = usersGrid.getSelectionModel().getSelected();
		flag = usersGrid.getSelectionModel().getSelections();
		if (flag.length == 1) {
			Ext.getCmp("user.telephone.show").setValue(_record.get("telephone"));
			winTitle = "当前用户分级统计";
			showUserCountWin.setTitle(winTitle);
			showUserCountWin.show();
			showUserCountForm.getForm().load();
			//当前统计
			showUserCountStore.proxy = new Ext.data.HttpProxy({
				url : 'Users_selectUserCount.action'
			});
			showUserCountStore.reload( {
					params : {
						'user.telephone': _record.get("telephone"),
						'start' : 0,		
						'limit' : 10
			}
			});
		} else
			Ext.Msg.alert('错误', '请选择一道要查看的用户分级数量！');
	};
	
	var addForm = new Ext.FormPanel({
		labelWidth : 75,
		labelAlign : 'right',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		waitMsgTarget : true,
		items : [{
			layout : 'column',
			xtype : 'fieldset',
			title : '用户信息',
			items : [{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 80,
						items : [{
							xtype : 'textfield',
							name : 'user.telephone',
							id : 'user.telephone.create',
							fieldLabel : '手机号',
							allowBlank : false,
							anchor : '100%',
							listeners : {
								blur : function() {
										Ext.Ajax.request({
											url : 'Users_findUsersByTelephone.action',
											success : function(response) {
												var info = Ext.decode(response.responseText);
												if(info != null && info.success ==true){	
													Ext.getCmp('user.telephone.create').markInvalid('此手机号已存在，无法操作');
												}else{
													return true;
												}
											},
											failure : function(response) {
												Ext.Ghost.msg(
														'查询失败','请确认系统可以正常访问！');
											},
											waitMsg : '正在加载数据，稍后...',  
						                    params:'user.telephone='+Ext.getCmp('user.telephone.create').getValue()
										});
								}
							}
						}, {
							xtype : 'textfield',
							name : 'user.usercode',
							id : 'user.usercode.create',
							fieldLabel : '客户编号',
							allowBlank : true,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							name : 'user.parentiphone',
							id : 'user.parentiphone.create',
							fieldLabel : '推荐人',
							allowBlank : true,
							anchor : '100%',
							listeners : {
								blur : function() {
										Ext.Ajax.request({
											url : 'Users_findUsersByTelephone.action',
											success : function(response) {
												var info = Ext.decode(response.responseText);
												if(info != null && info.success ==true){	
													return true;
												}else{
													Ext.getCmp('user.parentiphone.create').markInvalid('此手机号不存在，无法做推荐操作');
												}
											},
											failure : function(response) {
												Ext.Ghost.msg(
														'查询失败','请确认系统可以正常访问！');
											},
											waitMsg : '正在加载数据，稍后...',  
						                    params:'user.telephone='+Ext.getCmp('user.parentiphone.create').getValue()
										});
								}
							}
						}, {
							fieldLabel : '激活状态',
							xtype : 'combo',
							anchor : '100%',
							valueField : "id",
							displayField : "name",
							mode : 'local',
							forceSelection : true,
							emptyText : '--请选择--',
							editable : false,
							triggerAction : 'all',
							hiddenName : 'user.usable',
							store:new Ext.data.SimpleStore({
						        fields: ['id', 'name'],
						        data:[['0','未激活'],
						              ['1','已激活'],
						              ['2','已发货'],
						              ['3','未绑定'],
						              ['4','已绑定编码']
						        ]
						    }),
							id : 'user.usable.create',
							allowBlank : false
						}, {
							xtype : 'textarea',
							name : 'user.bankname',
							id : 'user.bankname.create',
							fieldLabel : '开户行',
							allowBlank : true,
							anchor : '100%'
						}, {
							xtype : 'textarea',
							name : 'user.bankaddress',
							id : 'user.bankaddress.create',
							fieldLabel : '开户地址',
							allowBlank : true,
							anchor : '100%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 80,
						items : [{
							xtype : 'textfield',
							name : 'user.username',
							id : 'user.username.create',
							fieldLabel : '姓名',
							allowBlank : false,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							name : 'user.usercard',
							id : 'user.usercard.create',
							fieldLabel : '身份证号',
							allowBlank : false,
							anchor : '100%'
						}, {
							fieldLabel : '物流类型',
							xtype : 'combo',
							anchor : '100%',
							valueField : "id",
							displayField : "name",
							mode : 'local',
							forceSelection : true,
							emptyText : '--请选择--',
							editable : false,
							triggerAction : 'all',
							hiddenName : 'user.shiptype',
							store:new Ext.data.SimpleStore({
						        fields: ['id', 'name'],
						        data:[['0','省外'],
						              ['1','省内'],
						              ['2','自取']
						        ]
						    }),
							id : 'user.shiptype.create',
							allowBlank : true
						}, {
							xtype : 'textfield',
							name : 'user.bankcard',
							id : 'user.bankcard.create',
							fieldLabel : '开户帐号',
							allowBlank : true,
							anchor : '100%'
						}, {
							xtype : 'textarea',
							name : 'user.shipaddress',
							id : 'user.shipaddress.create',
							fieldLabel : '收货地址',
							allowBlank : true,
							anchor : '100%'
						}, {
							xtype : 'textarea',
							name : 'user.userdesc',
							id : 'user.userdesc.create',
							fieldLabel : '备注',
							allowBlank : true,
							anchor : '100%'
						}]
					} ]
				}, {
					html : '<font color="red">*系统默认密码为123456，需用户登录自行修改</font>'+
					'<br/><font color="red">*选择激活状态时请确认是否缴费，否则将影响后期的设备提成计算，系统默认非激活状态为未缴费、其他状态为已缴费</font>'
			} ]
			});

		addWin = new Ext.Window({
			layout : 'form',
			width : 650,
			height : 400,
			modal : true,
			resizable : false,
			plain : true,
			closable : false,
			autoScroll:true,
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			buttonAlign:"center",
			items : addForm,
			buttons : [{
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (addForm.form.isValid()) {
						addForm.form.submit( {
									url : 'Users_addUsers.action' ,
									success : function(
											from, action,success) {
										userStore.reload();
										Ext.Ghost.msg('保存成功','添加新的用户成功！');
										addWin.hide();
									},
									failure : function(
											form, action,success) {
										Ext.Ghost.msg(
												'保存失败','已存在该用户信息或服务器未响应，请重新尝试');
									},
									waitMsg : '正在保存数据，稍后...'
								});
					} else {
						Ext.Ghost.msg('信息', '请填写完成再提交!');
					}
				}
			}, '-', {
				text : '取消',
				iconCls : 'icon-reset',
				handler : function() {
				addForm.form.reset();
				addWin.hide();
				},
				cls : 'right-part'// ,
			}]
		});
		
		
		var _jsonFormReader = new Ext.data.JsonReader({
			root : 'items_Users'
		}, [{
					name : 'user.id',
					mapping : 'id'
				}, {
					name : 'user.telephone',
					mapping : 'telephone'
				}, {
					name : 'user.usercode',
					mapping : 'usercode'
				}, {
					name : 'user.parentiphone',
					mapping : 'parentiphone'
				}, {
					name : 'user.username',
					mapping : 'username'
				}, {
					name : 'user.usercard',
					mapping : 'usercard'
				}, {
					name : 'user.bankname',
					mapping : 'bankname'
				}, {
					name : 'user.bankcard',
					mapping : 'bankcard'
				}, {
					name : 'user.bankaddress',
					mapping : 'bankaddress'
				}, {
					name : 'user.shiptype',
					mapping : 'shiptype'
				},{
					name : 'user.shipaddress',
					mapping : 'shipaddress'	
				},{
					name : 'user.usable',
					mapping : 'usable'	
				},{
					name : 'user.userdesc',
					mapping : 'userdesc'	
				}]);
		
		var updateForm = new Ext.FormPanel({
			labelWidth : 75,
			labelAlign : 'right',
			frame : true,
			reader : _jsonFormReader,
			bodyStyle : 'padding:5px 5px 0',
			waitMsgTarget : true,
			items : [{
						layout : 'column',
						xtype : 'fieldset',
						title : '用户信息',
						items : [{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 80,
							items : [{
								name : 'user.id',
								xtype : 'hidden'
							},{
								xtype : 'textfield',
								name : 'user.telephone',
								id : 'user.telephone.update',
								fieldLabel : '手机号',
								allowBlank : false,
								anchor : '100%'
							},{
								xtype : 'textfield',
								name : 'user.usercode',
								id : 'user.usercode.update',
								fieldLabel : '客户编号',	
								allowBlank : true,
								anchor : '100%'
							},{
								xtype : 'textfield',
								name : 'user.parentiphone',
								id : 'user.parentiphone.update',
								fieldLabel : '推荐人',	
								allowBlank : true,
								anchor : '100%'
							},{
								fieldLabel : '激活状态',
								xtype : 'combo',
								anchor : '100%',
								valueField : "id",
								displayField : "name",
								mode : 'local',
								forceSelection : true,
								emptyText : '--请选择--',
								editable : false,
								readOnly:true,
								triggerAction : 'all',
								hiddenName : 'user.usable',
								store:new Ext.data.SimpleStore({
							        fields: ['id', 'name'],
							        data:[['0','未激活'],
							              ['1','已激活'],
							              ['2','已发货'],
							              ['3','未绑定'],
							              ['4','已绑定编码']
							        ]
							    }),
								id : 'user.usable.update',
								allowBlank : false
							},{
								xtype : 'textarea',
								name : 'user.bankname',
								id : 'user.bankname.update',
								fieldLabel : '开户行',	
								allowBlank : true,
								anchor : '100%'
							},{
								xtype : 'textarea',
								name : 'user.bankaddress',
								id : 'user.bankaddress.update',
								fieldLabel : '开户地址',	
								allowBlank : true,
								anchor : '100%'
							}]
					},{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 80,
							items : [{
								xtype : 'textfield',
								name : 'user.username',
								id:'user.username.update',
								fieldLabel : '姓名',	
								allowBlank : false,
								anchor : '100%'
							},{
								xtype : 'textfield',
								name : 'user.usercard',
								id:'user.usercard.update',
								fieldLabel : '身份证号',	
								allowBlank : false,
								anchor : '100%'
							},{
								fieldLabel : '物流类型',
								xtype : 'combo',
								anchor : '100%',
								valueField : "id",
								displayField : "name",
								mode : 'local',
								forceSelection : true,
								emptyText : '--请选择--',
								editable : false,
								triggerAction : 'all',
								hiddenName : 'user.shiptype',
								store:new Ext.data.SimpleStore({
							        fields: ['id', 'name'],
							        data:[['0','省外'],
							              ['1','省内'],
							              ['2','自取']
							        ]
							    }),
								id : 'user.shiptype.update',
								allowBlank : true
							},{
								xtype : 'textfield',
								name : 'user.bankcard',
								id:'user.bankcard.update',
								fieldLabel : '开户帐号',	
								allowBlank : true,
								anchor : '100%'
							},{
								xtype : 'textarea',
								name : 'user.shipaddress',
								id:'user.shipaddress.update',
								fieldLabel : '收货地址',	
								allowBlank : true,
								anchor : '100%'
							},{
								xtype : 'textarea',
								name : 'user.userdesc',
								id : 'user.userdesc.update',
								fieldLabel : '备注',	
								allowBlank : true,
								anchor : '100%'
							}]
						}]
						}, {
							html : '<font color="red">*用户激活状态是指用户是否缴费和是否绑定编码，除想限制用户登录（改为未缴费）外，其他无需修改此值</font>'+
										'<br/><font color="red">*为增加系统稳定性，请尽量不要再此修改用户信息，绑定客户编码可在专门窗口登记。</font>'
						}]
				});

			updateWin = new Ext.Window({
				layout : 'form',
				width : 650,
				height : 400,
				modal : true,
				resizable : false,
				plain : true,
				closable : false,
				autoScroll:true,
				bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
				buttonAlign:"center",
				items : updateForm,
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						if (updateForm.form.isValid()) {
							  updateForm.form.submit( {
										url : 'Users_updateUsers.action' ,
										success : function(
												from, action,success) {
											userStore.reload();
											Ext.Ghost.msg('保存成功','修改用户成功！');
											updateWin.hide();
										},
										failure : function(
												form, action,success) {
											Ext.Ghost.msg(
													'保存失败','服务器未响应或填写信息有有误，请重新尝试');
										},
										waitMsg : '正在保存数据，稍后...'
									});
						} else {
							Ext.Ghost.msg('信息', '请填写完成再提交!');
						}
					}
				}, '-', {
					text : '取消',
					iconCls : 'icon-reset',
					handler : function() {
					updateForm.form.reset();
					updateWin.hide();
					},
					cls : 'right-part'// ,
				}]
			});
			//修改用户状态
			var usableForm = new Ext.FormPanel({
				labelWidth : 75,
				labelAlign : 'right',
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				waitMsgTarget : true,
				items : [{
							layout : 'column',   
							xtype : 'fieldset',
							title : '状态信息',
							items : [{
								columnWidth : .6,
								layout : 'form',
								labelWidth : 80,
								items : [{
									name : 'user.id',
									id : 'updateId1',
									xtype : 'hidden'
								},{
									xtype : 'textfield',
									id : 'updateTelephone1',
									fieldLabel : '客户手机号',	
									allowBlank : false,
									disabled:true,
									anchor : '100%'
								},{
									fieldLabel : '激活状态',
									xtype : 'combo',
									anchor : '100%',
									valueField : "id",
									displayField : "name",
									mode : 'local',
									forceSelection : true,
									emptyText : '--请选择--',
									editable : false,
									triggerAction : 'all',
									hiddenName : 'user.usable',
									store:new Ext.data.SimpleStore({
								        fields: ['id', 'name'],
								        data:[['0','未激活'],
								              ['1','已激活'],
								              ['2','已发货'],
								              ['3','未绑定'],
								              ['4','已绑定编码']
								        ]
								    }),
									id : 'updateUsable1',
									allowBlank : false
								},{
									xtype : 'textarea',
									name : 'user.userdesc',
									id : 'updateUserdesc1',
									fieldLabel : '描述',	
									allowBlank : true,
									anchor : '100%'
								}]
						}]
				}, {
					html : '<font color="red">*如要改绑定客户编号，请到绑定窗口修改，此次不支持。</font>'+
					'<br/><font color="red">*此处可单独修改激活状态，如注销某用户，可将其状态改为未激活。</font>'
			}]
			});	
			
			usableWin = new Ext.Window({
				layout : 'form',
				width : 650,
				height : 280,
				modal : true,
				resizable : false,
				plain : true,
				closable : false,
				items : usableForm,
				autoScroll:true,
				bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
				buttonAlign:"center",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						if (usableForm.form.isValid()) {
							  usableForm.form.submit( {
										url : 'Users_updateUserCode.action' ,
										success : function(
												from, action,success) {
											userStore.reload();
											Ext.Ghost.msg('保存成功','修改状态成功！');
											usableWin.hide();
										},
										failure : function(
												form, action,success) {
											Ext.Ghost.msg(
													'保存失败','服务器未响应或填写数据有误，请重新尝试');
										},
										waitMsg : '正在保存数据，稍后...'
									});
						} else {
							Ext.Ghost.msg('信息', '请填写完成再提交!');
						}
					}
				}, '-', {
					text : '取消',
					iconCls : 'icon-reset',
					handler : function() {
					usableForm.form.reset();
					usableWin.hide();
					},
					cls : 'right-part'// ,
				}]
			});
			
			//绑定窗口
			var usercodeForm = new Ext.FormPanel({
				labelWidth : 75,
				labelAlign : 'right',
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				waitMsgTarget : true,
				items : [{
							layout : 'column',   
							xtype : 'fieldset',
							title : '绑定信息',
							items : [{
								columnWidth : .6,
								layout : 'form',
								labelWidth : 80,
								items : [{
									name : 'user.id',
									id : 'updateId',
									xtype : 'hidden'
								},{
									xtype : 'textfield',
									id : 'updateTelephone',
									fieldLabel : '客户手机号',	
									allowBlank : false,
									disabled:true,
									anchor : '100%'
								},{
									xtype : 'textfield',
									name : 'user.usercode',
									id : 'updateUsercode',
									fieldLabel : '客户编码',	
									allowBlank : false,
									anchor : '100%'
								},{
									fieldLabel : '激活状态',
									xtype : 'combo',
									anchor : '100%',
									valueField : "id",
									displayField : "name",
									mode : 'local',
									forceSelection : true,
									emptyText : '--请选择--',
									editable : false,
									triggerAction : 'all',
									hiddenName : 'user.usable',
									store:new Ext.data.SimpleStore({
								        fields: ['id', 'name'],
								        data:[['0','未激活'],
								              ['1','已激活'],
								              ['2','已发货'],
								              ['3','未绑定'],
								              ['4','已绑定编码']
								        ]
								    }),
									id : 'updateUsable',
									allowBlank : false
								},{
									xtype : 'textarea',
									name : 'user.userdesc',
									id : 'updateUserdesc',
									fieldLabel : '描述',	
									allowBlank : true,
									anchor : '100%'
								}]
						}]
				}, {
					html : '<font color="red">*绑定客户编码时，请注意系统默认会将激活状态改为已绑定</font>'+
					'<br/><font color="red">*此处可单独修改激活状态，如注销某用户，可将其状态改为未激活。</font>'
			}]
			});	
			
			usercodeWin = new Ext.Window({
				layout : 'form',
				width : 650,
				height : 305,
				modal : true,
				resizable : false,
				plain : true,
				closable : false,
				items : usercodeForm,
				autoScroll:true,
				bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
				buttonAlign:"center",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						if (usercodeForm.form.isValid()) {
							  usercodeForm.form.submit( {
										url : 'Users_updateUserCode.action' ,
										success : function(
												from, action,success) {
											userStore.reload();
											Ext.Ghost.msg('保存成功','绑定客户编码或修改状态成功！');
											usercodeWin.hide();
										},
										failure : function(
												form, action,success) {
											Ext.Ghost.msg(
													'保存失败','服务器未响应或填写数据有误，请重新尝试');
										},
										waitMsg : '正在保存数据，稍后...'
									});
						} else {
							Ext.Ghost.msg('信息', '请填写完成再提交!');
						}
					}
				}, '-', {
					text : '取消',
					iconCls : 'icon-reset',
					handler : function() {
					usercodeForm.form.reset();
					usercodeWin.hide();
					},
					cls : 'right-part'// ,
				}]
			});

	new Ext.Viewport({
				layout : 'border',
				items : [selectUsersForm,usersGrid]
			});
});
 
