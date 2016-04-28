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

	var userProfitStore = new Ext.data.JsonStore({
		proxy : new Ext.data.HttpProxy({
					url : 'Userdto_selectProfitCount.action'
				}),
		root : 'items_UserDTO',
		totalProperty : 'totalProperty',
		fields : ['id', 'userdepth', 'usercount','profitfee','profitcount','username']
			// autoLoad : false
		});
	
	userProfitStore.load({
				params : {
					start : 0,
					limit : 20
				}
			});
	
	selectuserProfitForm = new Ext.form.FormPanel(
			{
				title : '查询条件',
				renderTo : 'userProfitCount',
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
                    key:13,  //13代表回车
                    fn:function(){
                         document.getElementById("btnsubmit").click();
                    },
                    scope:this
                }],
				items : [{
					layout : 'column',
					xtype : 'fieldset',
					title : '设备提成查询',
					labelAlign : 'right',
					width:'100%',
					items : [{
									columnWidth : .4,
									layout : 'form',
									labelWidth : 80,
									items : [{
										xtype : 'datefield',
										fieldLabel : '开始日期',
										id : 'user.usercard.show',
										name : 'user.usercard',
										format:'Y-m-d H:i:s',
										editable : false,
										allowBlank : false,
										anchor : '80%'	
									}]
									},{
										columnWidth : .4,
										layout : 'form',
										labelWidth : 80,
										items : [{
										xtype : 'datefield',
										fieldLabel : '结束日期',
										id : 'user.bankcard.show',
										name : 'user.bankcard',
										format:'Y-m-d H:i:s',
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
									var createDate = Ext.getCmp('user.usercard.show').getValue();
									if(createDate!=''){
										//做去“-”处理
										createDate=createDate.format('YmdHis');
									}
									var endDate = Ext.getCmp('user.bankcard.show').getValue();
									if(endDate!=''){
										//做去“-”处理
										endDate=endDate.format('YmdHis');
									}
									userProfitStore.proxy = new Ext.data.HttpProxy(
											{
												//处理的时间参数要直接由？+属性参数传到后台，参数名对应对象属性
												url : 'Userdto_selectProfitCount.action?user.usercard='+createDate+'&user.bankcard='+endDate
											});
									userProfitStore.load( {
												params : {
													start : 0,
													limit : 20
												}
											});
								}
							}, '-', {
								text : '重置',
								iconCls : 'icon-reset',
								handler : function() {
									selectuserProfitForm.form.reset();
								},
								cls : 'right-part'// ,
							}]
			});
	
	var userProfitGrid = new Ext.grid.GridPanel({
		renderTo : 'userProfitCount',
		store : userProfitStore,
		id : 'userProfitGrid',
		// height : defaultHeight,
		region : 'center',
		keys : [{
					key : [13],
					fn : keyConvert
				}],
				columns : [ {
					header : 'id',
					dataIndex : 'id',
					hidden : true,
					sortable : false
				},{
					header : '客户姓名',
					sortable : true,
					width : 200,
					dataIndex : 'username'
				}, {
					header : '层级',
					sortable : true,
					width : 200,
					dataIndex : 'userdepth'
				}, {
					header : '数量',
					sortable : true,
					width : 200,
					dataIndex : 'usercount'
				}, {
					header : '提成（元）',
					sortable : true,
					width : 200,
					dataIndex : 'profitfee'
				}, {
					header : '总额（元）',
					sortable : true,
					width : 200,
					dataIndex : 'profitcount'
				}],
		tbar : [{
			text : "导出到excel",
			iconCls : "icon-print",
			handler : function() {
				var vExportContent = userProfitGrid.getExcelXml(); // 获取数据
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
		},{
			text:'<font color="red">*当前显示为最新各级用户数，如中间断级，说明断级用户被封或删除</font>'
		}],
		bbar : [{
					xtype : 'paging',
					pageSize : 20,
					store : userProfitStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : "没有记录"
				}]
	});

		new Ext.Viewport({
			layout : 'border',
			items : [selectuserProfitForm,userProfitGrid]
		});
		
})
 
