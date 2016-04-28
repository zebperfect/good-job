var check = null;
var submit = null;
var del = null;
var _record = null;
var flag = null;
var jsonData = "";

Ext.onReady(function() {

			Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
			Ext.QuickTips.init();

			// 提示是否继续执行
			check = function(formTemp, url, store) {
				Ext.MessageBox.confirm("操作提示", "您确定要继续当前操作吗？",
						function(button) {
							if (button == "yes") {
								submit(formTemp, url, store)
							}
						});
			}

			// 提交表单
			submit = function(formTemp, urlTemp, store) {
				if (formTemp.form.isValid()) {
					formTemp.form.submit({
								url : urlTemp,
								success : function(form, action) {
									Ext.Msg.alert('操作成功', '操作成功！');
									if (null != store)
										store.load();
								},
								failure : function(form, action) {
									Ext.Msg.alert('操作失败', '操作失败！');
								},
								waitMsg : '正在提交操作，稍后...'
							});

				} else {
					Ext.Msg.alert('信息', '请填写完成再提交!');
				}
			};

			del = function(grid, store, urlTemp, id) {
				_record = grid.getSelectionModel().getSelections();
				flag = grid.getSelectionModel().getSelected();
				if (flag) {
					Ext.MessageBox.confirm('确认删除', '你确认要删除这条数据吗？',
							function(btn) {
								if (btn == "yes") {
									for (var i = 0; i < _record.length; i++) {
										ss = _record[i].get(id);
										if (i == 0) {
											jsonData = jsonData + ss;
										} else {
											jsonData = jsonData + "," + ss;
										}
									}
									// 向Action中传输需要删除的记录的pkId的拼接字符串
									Ext.Ajax.request({
												url : urlTemp,
												params : {
													delData : jsonData
												},
												success : function(result,
														request) {
													store.load();
													Ext.Msg.alert('提示', '删除成功');
												},
												failure : function(result,
														request) {
													Ext.Msg.alert('提示', '删除失败');
												}
											});

								}
							});
				} else {
					Ext.Msg.alert('删除操作', '请选择要删除的数据项！');
				}
			};
		});

// 时间转换，格式yyyymmddhhmmss
function timeConvert(oldTime) {
	var str = "";
	if(oldTime != null){		
		str = oldTime.substring(0, 4) + "年" + oldTime.substring(4, 6) + "月"
		+ oldTime.substring(6, 8) + "日" + oldTime.substring(8, 10) + "时"
		+ oldTime.substring(10, 12) + "分" + oldTime.substring(12, 14) + "秒";
	}
	return str;
};