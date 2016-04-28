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

	var userCountStore = new Ext.data.JsonStore({
		proxy : new Ext.data.HttpProxy({
					url : 'Users_selectUserCount.action'
				}),
		root : 'items_UserCount',
		totalProperty : 'totalProperty',
		fields : ['id', 'usercount', 'userDepth']
			// autoLoad : false
		});
	
	userCountStore.load({
				params : {
					start : 0,
					limit : 20
				}
			});
	
	var userCountGrid = new Ext.grid.GridPanel({
		renderTo : 'userCount',
		store : userCountStore,
		id : 'userCountGrid',
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
					header : '等级',
					sortable : true,
					width : 200,
					dataIndex : 'userDepth'
				}, {
					header : '数量',
					sortable : true,
					width : 200,
					dataIndex : 'usercount'
				}],
		tbar : [{
			text : "导出到excel",
			iconCls : "icon-print",
			handler : function() {
				var vExportContent = userCountGrid.getExcelXml(); // 获取数据
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
					store : userCountStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : "没有记录"
				}]
	});

		new Ext.Viewport({
			layout : 'border',
			items : [userCountGrid]
		});
		
})
 
