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

	var userReportQueryStore = new Ext.data.JsonStore({
		proxy : new Ext.data.HttpProxy({
					url : 'ProfitReport_queryReportByUser.action'
				}),
		root : 'items_FlowReports',
		totalProperty : 'totalProperty',
		fields : ['id', 'username', 'servicename', 'paymoney','profitnum',
		          'profitmoney','startdate', 'enddate','realstartdate','realenddate']
			// autoLoad : false
		});
	
	userReportQueryStore.load({
				params : {
					start : 0,
					limit : 20
				}
			});
	
	selectuserReportQueryForm = new Ext.form.FormPanel(
			{
				title : '查询条件',
				renderTo : 'userReportQuery',
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
					title : '分润月报查询',
					labelAlign : 'right',
					width:'100%',
					items : [{
									columnWidth : .4,
									layout : 'form',
									labelWidth : 80,
									items : [{
										fieldLabel : '层级',
										xtype : 'combo',
										anchor : '80%',
										valueField : "id",
										displayField : "name",
										mode : 'local',
										forceSelection : true,
										emptyText : '--请选择--',
										editable : false,
										triggerAction : 'all',
										hiddenName : 'flowReport.profitnum',
										store:new Ext.data.SimpleStore({
									        fields: ['id', 'name'],
									        data:[['1','二级'],
									              ['2','三级'],
									              ['3','四级']
									        ]
									    }),
										id : 'flowReport.profitnum.show',
										allowBlank : true
									}]
									},{
										columnWidth : .4,
										layout : 'form',
										labelWidth : 80,
										items : [{
											xtype : 'datefield',
											fieldLabel : '交易开始日期',
											id : 'flowReport.startdate.show',
											name : 'flowReport.startdate',
											format:'Y-m-d',
											editable : false,
											allowBlank : false,
											anchor : '80%'	
									},{
										xtype : 'datefield',
										fieldLabel : '交易结束日期',
										id : 'flowReport.enddate.show',
										name : 'flowReport.enddate',
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
									var createDate = Ext.getCmp('flowReport.startdate.show').getValue();
									if(createDate!=''){
										//做去“-”处理
										createDate=createDate.format('Ymd');
									}
									var endDate = Ext.getCmp('flowReport.enddate.show').getValue();
									if(endDate!=''){
										//做去“-”处理
										endDate=endDate.format('Ymd');
									}
									userReportQueryStore.proxy = new Ext.data.HttpProxy(
											{
												//处理的时间参数要直接由？+属性参数传到后台，参数名对应对象属性
												url : 'ProfitReport_selectFlowReport.action?flowReport.startdate='+createDate+'&flowReport.enddate='+endDate
											});
									userReportQueryStore.load( {
												params : {
													start : 0,
													limit : 20,
													'flowReport.profitnum' : Ext.getCmp('flowReport.profitnum.show').getValue()
												}
											});
								}
							}, '-', {
								text : '重置',
								iconCls : 'icon-reset',
								handler : function() {
									selectuserReportQueryForm.form.reset();
								},
								cls : 'right-part'// ,
							}]
			});
	
	var userReportQueryGrid = new Ext.grid.GridPanel({
		renderTo : 'userReportQuery',
		store : userReportQueryStore,
		sm : sm,
		id : 'userReportQueryGrid',
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
				}, {
					header : '客户名称',
					width : 150,
					dataIndex : 'username'
				},{
					header : '服务编码',
					width : 200,
					dataIndex : 'servicename'
				}, {
					header : '交易金额',
					width : 100,
					dataIndex : 'paymoney'
				}, {
					header : '分润笔数',
					width : 100,
					dataIndex : 'profitnum'
				}, {
					header : '分润金额(元)',
					width : 100,
					dataIndex : 'profitmoney'
				}, {
					header : '开始时间',
					width : 150,
					dataIndex : 'startdate'
				}, {
					header : '结束时间',
					width : 150,
					dataIndex : 'enddate'
				}, {
					header : '实际开始时间',
					width : 150,
					dataIndex : 'realstartdate'
				}, {
					header : '实际结束时间',
					width : 150,
					dataIndex : 'realenddate'
				}],
		//列表上方的功能选项
		tbar : [{
			text : "导出到excel",
			iconCls : "icon-print",
			handler : function() {
				var vExportContent = userReportQueryGrid.getExcelXml(); // 获取数据
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
		}, {
			text : '<font color="red">*注：系统只支持查询近三月（含当月）的分润</font>'
		}],
		bbar : [{
					xtype : 'paging',
					pageSize : 20,
					store : userReportQueryStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : "没有记录"
				}]
	});

		new Ext.Viewport({
			layout : 'border',
			items : [selectuserReportQueryForm,userReportQueryGrid]
		});
		
})
 
