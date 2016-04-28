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

	var businessFlowQueryStore = new Ext.data.JsonStore({
		proxy : new Ext.data.HttpProxy({
					url : 'Businessflow_queryBusinessflow.action'
				}),
		root : 'items_Businessflow',
		totalProperty : 'totalProperty',
		baseParams : {
			start : 0,
			limit : 20
		},
		fields : ['payid', 'servicecode', 'paycode', 'paydate','paytime',
		          'tradingflow','localdate', 'usercode','useriphone','tradingchannel', 
		          'endcode', 'psamcard', 'busacquirer', 'busflow','busnumber',
		          'busdate', 'bustime', 'busenddate', 'sendbusflow','sendbusdate',
		          'sendbustime', 'paymainacc', 'paysecondacc','paymoney', 'payfee', 
		          'paystate','paydesc', 'paymark'],
			 autoLoad : false
		});
	
	selectBusinessFlowQueryForm = new Ext.form.FormPanel(
			{
				title : '查询条件',
				renderTo : 'businessFlowQuery',
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
					title : '交易流水查询',
					labelAlign : 'right',
					width:'100%',
					items : [{
									columnWidth : .4,
									layout : 'form',
									labelWidth : 80,
									items : [{
										layout : 'form',
										items : [
												{
													xtype : 'textfield',
													name : 'businessflow.usercode',
													id : 'flowReport.username.show',
													fieldLabel : '客户编码',
													allowBlank : true,
													width : 400,
													itemCls : 'only-float',
													clearCls : 'allow-float'
												},{
													xtype : 'button',
													iconCls : 'icon-add',
													width : 20,
													handler : function() {
														selectUnitWin.show();
														Ext.getCmp('user.telephone').focus(true,true);
													},
													cls : 'right-part',
													clearCls : 'allow-float'
												} ]
									},{
										xtype : 'textfield',
										name : 'businessflow.servicecode',
										id : 'businessflow.servicecode.show',
										fieldLabel : '服务编码',
										allowBlank : true,
										anchor : '80%'
									}]
									},{
										columnWidth : .4,
										layout : 'form',
										labelWidth : 80,
										items : [{
											xtype : 'datefield',
											fieldLabel : '交易开始日期',
											id : 'businessflow.paydate.show',
											name : 'businessflow.paydate',
											format:'Y-m-d',
											editable : false,
											allowBlank : false,
											anchor : '80%'	
									},{
										xtype : 'datefield',
										fieldLabel : '交易结束日期',
										id : 'businessflow.paytime.show',
										name : 'businessFlowQuery.paytime',
										format:'Y-m-d',
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
									var createDate = Ext.getCmp('businessflow.paydate.show').getValue();
									if(createDate!=''){
										//做去“-”处理
										createDate=createDate.format('Ymd');
									}
									var endDate = Ext.getCmp('businessflow.paytime.show').getValue();
									if(endDate!=''){
										//做去“-”处理
										endDate=endDate.format('Ymd');
									}
									businessFlowQueryStore.proxy = new Ext.data.HttpProxy(
											{
												//处理的时间参数要直接由？+属性参数传到后台，参数名对应对象属性
												url : 'Businessflow_selectBusinessflow.action?businessflow.paydate='+createDate+'&businessflow.paytime='+endDate
											});
									businessFlowQueryStore.load( {
												params : {
													start : 0,
													limit : 20,
													'businessflow.usercode' : Ext.getCmp('flowReport.username.show').getValue(),
													'businessflow.servicecode' : Ext.getCmp('businessflow.servicecode.show').getValue()
												}
											});
								}
							}, '-', {
								text : '重置',
								iconCls : 'icon-reset',
								handler : function() {
									selectBusinessFlowQueryForm.form.reset();
								},
								cls : 'right-part'// ,
							}]
			});
	
	var businessFlowQueryGrid = new Ext.grid.GridPanel({
		renderTo : 'businessFlowQuery',
		store : businessFlowQueryStore,
		sm : sm,
		id : 'businessFlowQueryGrid',
		// height : defaultHeight,
		region : 'center',
		keys : [{
					key : [13],
					fn : keyConvert
				}],
				columns : [sm, {
					header : 'payid',
					dataIndex : 'payid',
					hidden : true,
					sortable : false
				},{
					header : '服务编码',
					width : 100,
					dataIndex : 'servicecode'
				}, {
					header : '交易编码',
					width : 100,
					dataIndex : 'paycode'
				}, {
					header : '交易日期',
					width : 70,
					dataIndex : 'paydate'
				}, {
					header : '交易时间',
					width : 70,
					dataIndex : 'paytime'
				}, {
					header : '交易流水',
					width : 70,
					dataIndex : 'tradingflow'
				}, {
					header : '本地清算日期',
					width : 70,
					dataIndex : 'localdate'
				}, {
					header : '客户编码',
					width : 100,
					dataIndex : 'usercode'
				}, {
					header : '手机号码',
					width : 100,
					dataIndex : 'useriphone'
				}, {
					header : '交易渠道',
					width : 100,
					dataIndex : 'tradingchannel'
				}, {
					header : '终端编码',
					width : 100,
					dataIndex : 'endcode'
				}, {
					header : 'PSAM卡号',
					width : 100,
					dataIndex : 'psamcard'
				}, {
					header : '业务受理方',
					dataIndex : 'busacquirer'
				}, {
					header : '原始业务流水',
					width : 100,
					dataIndex : 'busflow'
				}, {
					header : '原始业务参考号',
					width : 100,
					dataIndex : 'busnumber'
				}, {
					header : '原始业务日期',
					width : 100,
					dataIndex : 'busdate'
				}, {
					header : '原始业务时间',
					width : 100,
					dataIndex : 'bustime'
				}, {
					header : '原始业务结算日期',
					width : 100,
					dataIndex : 'busenddate'
				}, {
					header : '发送方业务流水',
					width : 100,
					dataIndex : 'sendbusflow'
				}, {
					header : '发送方业务日期',
					width : 100,
					dataIndex : 'sendbusdate'
				}, {
					header : '发送方业务时间',
					width : 100,
					dataIndex : 'sendbustime'
				}, {
					header : '交易主账号',
					width : 100,
					dataIndex : 'paymainacc'
				}, {
					header : '第二交易账号',
					width : 100,
					dataIndex : 'paysecondacc'
				}, {
					header : '交易金额(元)',
					width : 100,
					dataIndex : 'paymoney'
				}, {
					header : '交易手续费(元)',
					width : 100,
					dataIndex : 'payfee'
				}, {
					header : '交易状态',
					width : 100,
					dataIndex : 'paystate'
				}, {
					header : '交易标记',
					width : 100,
					dataIndex : 'paymark'
				}, {
					header : '备注说明',
					width : 100,
					dataIndex : 'paydesc'
				}],
		//列表上方的功能选项
		tbar : [{
			text : '删除记录',
			tooltip : '删除被选择的交易流水记录',
			iconCls : "icon-del",
			handler : function() {
				_record = businessFlowQueryGrid.getSelectionModel().getSelections();
				flag = businessFlowQueryGrid.getSelectionModel().getSelected();
				if (_record.length == 0) {
					Ext.Msg.alert('警告', '请选择你要删除的记录！');
				} else {
					Ext.MessageBox.confirm('确认删除', '你确认要删除该记录吗？',
							function(btn) {
								if (btn == "yes") {
									var jsonData = "";
									for (var i = 0; i < _record.length; i++) {
										ss = _record[i].get("payid");
										if (i == 0) {
											jsonData = jsonData + ss;
										} else {
											jsonData = jsonData + ","
													+ ss;
										}
									}
									Ext.Ajax.request({
										url : "Businessflow_delBusinessflow.action",
										params : {
											allData : jsonData
										},
										success : function(from, action) {
											businessFlowQueryStore.reload();
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
		},{
			text : '<font color="red">*注：系统只支持查询近三月（含当月）的交易流水，每日12点以后查看前一日流水。</font>'
		}],
		bbar : [{
					xtype : 'paging',
					pageSize : 20,
					store : businessFlowQueryStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : "没有记录"
				}]
	});

		new Ext.Viewport({
			layout : 'border',
			items : [selectBusinessFlowQueryForm,businessFlowQueryGrid]
		});
		
})
 
