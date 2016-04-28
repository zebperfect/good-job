var rightDownGrid;
var leftUpGrid;
var leftDowmGrid;
var rightUpGrid;
var commonPanel;
var infoHtml;
var informStore;
Ext.onReady(function() {
			Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
			var detailForm = new Ext.form.FormPanel({
						region : 'center',
						frame : true,
						layout : 'fit',
						height:500,
						html:'<h2>欢迎登录仁鑫鼎网上管理平台</h2>'
					})
			new Ext.Viewport({
						layout : 'border',
						items : [detailForm]
					});
		});