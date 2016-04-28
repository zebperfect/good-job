var ss = null;

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

	var settlementStore = new Ext.data.JsonStore({
		proxy : new Ext.data.HttpProxy({
					url : 'MonthDTO_selectSettlement.action'
				}),
		root : 'items_MonthDTO',
		totalProperty : 'totalProperty',
		baseParams : {
			start : 0,
			limit : 10
		},
		fields : ['settlementid', 'userid', 'telephone','usercode','username','usercard', 'bankname'
		          ,'bankcard','bankaddress','profitmoney','devicemoney','totalmoney','inmonth'],
			 autoLoad : false
		});
	
	selectsettlementForm = new Ext.form.FormPanel(
			{
				title : '查询条件',
				renderTo : 'settlementQuery',
				labelAlign : 'right',
				buttonAlign : 'center',
				layout : 'column',
				region : 'north',
				frame : true,
				collapsible :true,
				defaultType : 'textfield',
				padding : 10,
				hight : 100,
				keys:[{
                    key:13,
                    fn:function(){
                         document.getElementById("btnsubmit").click();
                    },
                    scope:this
                }],
				items : [{
					layout : 'column',
					xtype : 'fieldset',
					title : '月结算查询',
					labelAlign : 'right',
					width:'100%',
					items : [{
									columnWidth : .4,
									layout : 'form',
									labelWidth : 80,
									items : [{
										xtype : 'textfield',
										name : 'queryTelephone',
										id : 'user.telephone.show',
										fieldLabel : '客户手机号',
										allowBlank : true,
										width : 400,
										itemCls : 'only-float',
										clearCls : 'allow-float'
									}, {
										xtype : 'button',
										iconCls : 'icon-add',
										width : '5%',
										handler : function() {
											selectPhoneWin.show();
											Ext.getCmp('user.telephone').focus(true, true);
										},
										cls : 'right-part',
										clearCls : 'allow-float'
									}]
									},{
										columnWidth : .4,
										layout : 'form',
										labelWidth : 80,
										items : [{
											xtype : 'datefield',
											fieldLabel : '结算月份',
											id : 'queryDate.show',
											name : 'queryDate',
											format:'Y年m月',
											editable : false,
											allowBlank : false,
											anchor : '80%'	
									}]
								}]
					}],
				buttons : [{
								text : '查询',
								iconCls : 'icon-accept',
								cls : 'right-part',
								id:'btnsubmit',
								handler : function() {
									if (selectsettlementForm.form.isValid()) {
										var createDate = Ext.getCmp('queryDate.show').getValue();
										if(createDate!=''){
											createDate=createDate.format('Ym');
										}
										settlementStore.proxy = new Ext.data.HttpProxy(
												{
													url : 'MonthDTO_selectSettlement.action?queryDate='+createDate
												});
										settlementStore.load( {
													params : {
														start : 0,
														limit : 20,
														'queryTelephone' : Ext.getCmp('user.telephone.show').getValue()
													}
												});
									} else {
										Ext.Ghost.msg('信息', '请填写完成再提交!');
									}
								}
							}, '-', {
								text : '重置',
								iconCls : 'icon-reset',
								handler : function() {
									selectsettlementForm.form.reset();
								},
								cls : 'right-part'
							}, '-', {
								text : "导出到excel",
								iconCls : "icon-print",
								handler : function() {
									Ext.MessageBox.confirm('确认', '你确认要导出以上查询字段查找出的数据吗？<br\>'+
											'<font color="red">*用户未绑定客户编号，导出的数据分润为0</font>', function(btn) {
										if (btn == "yes") {
											var starttime = Ext.getCmp('queryDate.show').getValue();
											var queryTelephone = Ext.getCmp('user.telephone.show').getValue();
											if(starttime == '' && queryTelephone == ''){
												alert("查询条件不允许全为空，请设定导出条件！");
											}else{												
												if(starttime!=''){
													starttime=starttime.format('Ym');
												}
												var url = "MonthDTO_exportExcel.action"+'?queryDate='+starttime+'&queryTelephone='+queryTelephone;
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
										}
									})
								}
							}]
			});
	
	var settlementGrid = new Ext.grid.GridPanel({
		renderTo : 'settlementQuery',
		store : settlementStore,
		id : 'settlementGrid',
		// height : defaultHeight,
		region : 'center',
		keys : [{
					key : [13],
					fn : keyConvert
				}],
				columns : [ {
					header : 'settlementid',
					dataIndex : 'settlementid',
					hidden : true,
					sortable : false
				},{
					header : '客户手机号',
					sortable : true,
					width : 100,
					dataIndex : 'telephone'
				},{
					header : '客户编码',
					sortable : true,
					width : 100,
					dataIndex : 'usercode'
				},{
					header : '客户姓名',
					sortable : true,
					width : 100,
					dataIndex : 'username'
				}, {
					header : '身份证号',
					sortable : true,
					width : 150,
					dataIndex : 'usercard'
				}, {
					header : '开户银行',
					sortable : true,
					width : 150,
					dataIndex : 'bankname'
				}, {
					header : '银行卡号',
					sortable : true,
					width : 200,
					dataIndex : 'bankcard'
				}, {
					header : '开户地',
					sortable : true,
					width : 200,
					dataIndex : 'bankaddress'
				}, {
					header : '分润（元）',
					sortable : true,
					width : 100,
					dataIndex : 'profitmoney'
				}, {
					header : '提成（元）',
					sortable : true,
					width : 100,
					dataIndex : 'devicemoney'
				}, {
					header : '总额（元）',
					sortable : true,
					width : 100,
					dataIndex : 'totalmoney'
				}, {
					header : '月份',
					sortable : true,
					width : 100,
					dataIndex : 'inmonth'
				}],
		tbar : [{
			text : '自定义结算',
			tooltip : '自己指定查询用户和时间段，得出当前范围内的结算额',
			iconCls : "icon-edit",
			handler : function() {
				settingQuery();
			}
		}, '-', {
			text : "页面内容导出到excel",
			iconCls : "icon-print",
			handler : function() {
				Ext.MessageBox.confirm('确认', '你确认要导出当前页面显示的内容数据吗？<br\>'+
						'<font color="red">*注意下方分页数，若数据有分页，系统默认只导出第一页，如想全导出数据，请选择上方查询导出Excel</font>', function(btn) {
					if (btn == "yes") {
						var vExportContent = settlementGrid.getExcelXml(); // 获取数据
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
				})
			}
		},{
			text:'<font color="red">*系统每月初（2号）自动结算上一月所有用户的分润提成额，导出上月所有结算请在此时间后操作</font>'
		}],
		bbar : [{
					xtype : 'paging',
					pageSize : 20,
					store : settlementStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : "没有记录"
				}]
	});
	
	//填写结算条件
	settingQuery = function(){
		winTitle = "填写结算条件";
		settingQueryWin.setTitle(winTitle);
		settingQueryWin.show();
		settingQueryForm.getForm().load();
	};
	
	settingQueryForm = new Ext.form.FormPanel(
			{
				title : '自定义结算为实时数据计算，请在空闲时间使用',
				labelAlign : 'right',
				buttonAlign : 'center',
				layout : 'column',
				region : 'north',
				frame : true,
				collapsible :true,
				defaultType : 'textfield',
				padding : 10,
				hight : 100,
				items : [{
					layout : 'column',
					xtype : 'fieldset',
					title : '填写结算条件',
					labelAlign : 'right',
					width:'100%',
					items : [{
									columnWidth : .5,
									layout : 'form',
									labelWidth : 80,
									items : [{
										name : 'path',
										id:'user.id.show',
										xtype : 'hidden'
									},{
										layout : 'form',
										items : [
												{
													xtype : 'textfield',
													id : 'user.usercode.show',
													fieldLabel : '客户编码',
													allowBlank : false,
													width : 200,
													itemCls : 'only-float',
													clearCls : 'allow-float'
												},{
													xtype : 'button',
													iconCls : 'icon-add',
													width : 20,
													handler : function() {
														selectSettleWin.show();
														Ext.getCmp('user.telephone1').focus(true,true);
													},
													cls : 'right-part',
													clearCls : 'allow-float'
												} ]
									}]
									},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 80,
										items : [{
											xtype : 'datefield',
											fieldLabel : '开始日期',
											id : 'queryTelephone1.show',
											name : 'queryTelephone',
											format:'Y-m-d',
											editable : false,
											allowBlank : false,
											anchor : '80%'	
									},{
										xtype : 'datefield',
										fieldLabel : '结束日期',
										id : 'queryDate1.show',
										name : 'queryDate',
										format:'Y-m-d',
										editable : false,
										allowBlank : false,
										anchor : '80%'	
								}]
										}]
					}]
			});
	
	settingQueryWin = new Ext.Window({
		layout : 'form',
		width : 700,
		height : 230,
		modal : true,
		resizable : false,
		plain : true,
		closable : false,
		items : settingQueryForm,
		autoScroll:true,
		bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
		buttonAlign:"center",
		buttons : [{
			text : '确定',
			iconCls : 'icon-accept',
			cls : 'right-part',
			id:'btnsubmit',
			handler : function() {
				if (settingQueryForm.form.isValid()) {
					var starttime = Ext.getCmp('queryTelephone1.show').getValue();
					var endtime = Ext.getCmp('queryDate1.show').getValue();
					if(starttime!=''){
						starttime=starttime.format('Ymd');
					}
					if(endtime!=''){
						endtime=endtime.format('Ymd');
					}
					settlementStore.proxy = new Ext.data.HttpProxy(
							{
								url : 'MonthDTO_settlementByUser.action?queryTelephone='+starttime+'&queryDate='+endtime
							});
					settlementStore.load( {
								params : {
									start : 0,
									limit : 20,
									'path' : Ext.getCmp('user.id.show').getValue()
								},
					callback: function(records, options, success){ 
		                  Ext.Msg.alert('提示', '自定义结算加载完毕，信息为实时生成数据，可能与数据库导出数据不同，请仔细核对。');
					}
							});
					settingQueryForm.form.reset();
					settingQueryWin.hide();
				} else {
					Ext.Ghost.msg('信息', '请填写完成再提交!');
				}
				
			}
		}, '-', {
			text : '重置',
			iconCls : 'icon-reset',
			handler : function() {
				settingQueryForm.form.reset();
			},
			cls : 'right-part'
		}, '-', {
			text : '关闭',
			iconCls : 'icon-reset',
			handler : function() {
				settingQueryForm.form.reset();
				settingQueryWin.hide();
			},
			cls : 'right-part'
		}]
	});
	
		new Ext.Viewport({
			layout : 'border',
			items : [selectsettlementForm,settlementGrid]
		});
		
})
 
