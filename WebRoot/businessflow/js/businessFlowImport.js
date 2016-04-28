Ext.override(Ext.form.Action.Submit, {
			// private
			processResponse : function(response) {
				this.response = response;
				// 增加下面几句代码就OK啦
				// //////////////////////
				var data = response.responseText;
				if (data.indexOf('<pre') != -1) {
					response.responseText = data.substring(5, data.length - 6);
					this.response = Ext.util.JSON.decode(response.responseText);
				}
				// /////////////////////////
				if (!response.responseText) {
					return true;
				}
				this.result = this.handleResponse(response);
				return this.result;
			}
		});
var businessflowStore = new Ext.data.JsonStore({
			proxy : new Ext.data.HttpProxy({
						url : ''
					}),
			waitMsgTarget : true,
			root : "items_Businessflow",
			totalProperty : "totalProperty",
			successProperty : 'success',
			fields : ['payid', 'servicecode', 'paycode', 'paydate','paytime',
			          'tradingflow','localdate', 'usercode','useriphone','tradingchannel', 
			          'endcode', 'psamcard', 'busacquirer', 'busflow','busnumber',
			          'busdate', 'bustime', 'busenddate', 'sendbusflow','sendbusdate',
			          'sendbustime', 'paymainacc', 'paysecondacc','paymoney', 'payfee', 
			          'paystate','paydesc', 'paymark']
		});

var cm = new Ext.grid.ColumnModel({
			defaults : {
				align : 'center'
			},
			columns : [{
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
					}]
		});

var addForm = new Ext.form.FormPanel({
	title : "",
	labelWidth : 80,
	bodyStyle : 'padding:5px 5px 0',
	labelAlign : "right",
	id : 'adjuntInformation',
	layout : "form",
	fileUpload : true,
	autoHeight : true,
	items : [{
				xtype : "hidden",
				fieldLabel : "文件名称",
				name : 'uploadFlowFileName',
				id : 'uploadFlowFileName',
				anchor : "100%"
			},{
				xtype : 'textfield',
				fieldLabel : '交易流水文件',
				name : 'uploadFlowFile',
				id : 'uploadFlowFile',
				inputType : 'file',
				msgTarget : 'under',
				validator : function(value) {
					if (value != "") {
						var arr = value.split('.');
						// xls支持
						if (arr[arr.length - 1] != 'xls') {
							return '文件不合法，请上传excel文件(系统只识别xls后缀文件，office07版xlsx请转换成03版xls再提交)';
						} else {
							Ext.getCmp('uploadFlowFileName').setValue(arr[arr.length - 1]);
							return true;
						}
					}
				},
				blankText : '没有文件！',
				anchor : '100%',
				allowBlank : false
			}],
	fbar : [{
		xtype : 'button',
		text : '确定',
		iconCls : 'icon-accept',
		handler : function() {
			if (addForm.form.isValid()) {
				addForm.getForm().submit({
					url : 'Businessflow_uploadfile.action',
					waitMsg : '系统正在处理文件，请耐心等候...',
					params : {
						BrowserInformation : BrowserInformation
					},
					success : function(form, action) {
						var url = "Businessflow_showRecord.action";
						businessflowStore.proxy = new Ext.data.HttpProxy({
									url : url
								});
						businessflowStore.load({
									url : url

								});
						addForm.form.reset();
						addWin.hide();
					},
					failure : function() {
						Ext.Ghost.msg("提示信息", "文件处理失败");
					}
				});
			} else {
				Ext.Ghost.msg('信息', '请填写完成后再提交!');
			}
		}
	}, {
		xtype : 'button',
		text : '取消',
		iconCls : 'icon-cancel',
		handler : function() {
			addForm.form.reset();
			addWin.hide();
		}
	}]
});
var addWin = new Ext.Window({
			title : "交易流水Excel导入",
			plain : true,
			modal : true,
			resizable : false,
			width : 400,
			autoHeight : true,
			closeAction : 'hide',
			items : [addForm]
		});
var businessflowGrid = new Ext.grid.GridPanel({
	title : '导入客户交易流水列表>>先进行‘导入Excel’，审查确认面板显示数据无误后，点击‘保存’进行数据保存',
	striptRows : true,
	store : businessflowStore,
	region : 'center',
	cm : cm,
	tbar : [{
				text : '导入EXCEL',
				iconCls : 'icon-save',
				handler : function() {
					alert("请确认并保证文件加入'备注说明'列(最后列)，该列只允许录入1或者0.49，注意一定要省去%符号，不然系统不识别。");
					addWin.show();
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					var i = businessflowGrid.getStore().getCount();
					var gridData = [];
					Ext.each(businessflowGrid.getStore().getRange(), function(
									record) {
								gridData.push(record.data);
							});
					var flag=0;
					for(var i=0;i<gridData.length;i++){
						var it=gridData[i];
	                	if(it.paydesc == null){
	                		flag=1;
	                		alert("请确认导入文件添加的'备注说明'列数据格式为1或者0.49,当前数据格式错误。");
	                		break;
	                	}
					}
					if(flag == 0){						
						var listData = Ext.encode(gridData);
						Ext.Ajax.request({
							url : "Businessflow_addBusinessFlowRecord.action",
							params : {
								allData : listData
							},
							success : function(response, options) {
								var obj = Ext.util.JSON.decode(response.responseText);
								if (obj.success == true) {
									Ext.Msg.alert('成功提示', '客户交易流水信息已成功保存到数据库\n\r：'+obj.allData+'，错误记录交易流水为：'+obj.errorData);
									businessflowGrid.getStore().commitChanges();
								} else {
									Ext.Msg.alert('提示', '保存 出错');
								}
							}
						});
					}
				}
			}],
	bbar : [new Ext.PagingToolbar({
				store : businessflowStore,
				pageSize : 15,
				displayInfo : true,
				beforePageText : "第",
				afterPageText : "/　{0}页",
				firstText : "首页",
				prevText : "上一页",
				nextText : "下一页",
				lastText : "尾页",
				refreshText : "刷新",
				displayMsg : "当前显示记录从　{0}　-　{1}　总　{2}　条记录",
				emptyMsg : "没有相关记录!",
				plugins : [new Ext.ux.plugins.PageComboResizer()],
				doLoad : function(start) {
					record_start = start;
					var o = {}, pn = this.getParams();
					o[pn.start] = start;
					o[pn.limit] = this.pageSize;
					this.store.load({
								params : o
							});
				}
			})]
});
Ext.onReady(function() {
			Ext.BLANK_IMAGE_URL = basePath
					+ 'ExtJs/resources/images/default/s.gif';
			Ext.QuickTips.init();
			new Ext.Viewport({
						layout : 'border',
						items : [businessflowGrid]
					});
		});