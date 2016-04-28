Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	Ext.apply(Ext.form.VTypes, {
				password : function(val, field) {
					if (field.confirmTo) {
						var pwd = Ext.get(field.confirmTo);
						if (val.trim() == pwd.getValue().trim()) {
							return true;
						} else {
							return false;
						}
						return false;
					}
				}
			});
	var updateForm = new Ext.FormPanel({
		frame : true,
		title : '修改当前用户的密码',
		labelWidth : 100,
		// style : 'margin:0px 0px 0px 0px; padding:0px 0px 0px
		// 0px;border-style: none; border-width: 0px;',
		buttonAlign : 'center',
		labelAlign : 'right',
		border : '0',
		// applyTo : 'updatePasswordDiv',
		height : 200,
		width : 400,
		defaults : {
			width : 160,
			xtype : "textfield",
			inputType : 'password',
			allowBlank : false
		},
		items : [{
					fieldLabel : '输入旧密码',
					name : 'oldPassword',
					maxLength : 20,
					blankText : '密码为空！',
					maxLengthText : '密码长度不能超过20位！',
					id : 'oldPassword'
				}, {
					fieldLabel : '输入新密码',
					name : 'newPassword',
					id : 'newPassword',
					maxLength : 20,
					blankText : '密码为空',
					maxLengthText : '密码长度不能超过20位！'
				}, {
					fieldLabel : '确认新密码',
					id : 'NewPwdTwo',
					maxLength : 20,
					blankText : '密码为空',
					maxLengthText : '密码长度不能超过20位！',
					vtype : 'password',
					vtypeText : '两次密码不一致',
					confirmTo : 'newPassword'
				}],
		buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					disabled : false,
					handler : function() {
						if (updateForm.form.isValid()) {
							updateForm.form.submit({
										url : 'Users_updatePassword.action',
										success : function(form, action) {
											updateForm.getForm().reset();
											Ext.Msg.alert('提示',
													'修改密码成功，请牢记您的新密码！');
										},
										failure : function(form, action) {
											updateForm.getForm().reset();
											Ext.Msg.alert('提示',
													'修改失败，请您检查原密码是否正确！');
										},
										waitMsg : '正在保存数据，稍后...'
									});
						} else {
							if (!updateForm.form.isValid())
								Ext.Msg.alert('提示', '请填写完成再提交!');
						}
					}
				}, {
					text : '重置',
					iconCls : 'icon-reset',
					handler : function() {
						updateForm.getForm().reset();
					}
				}]
	});
	Ext.getCmp("oldPassword").focus(true, true);
	var updateForm2 = new Ext.Panel({
				frame : true,
				applyTo : 'mainPanel',
				height : '100',
				items : [updateForm]
			})
	var height = document.getElementById('mainPanel').offsetHeight;
	updateForm2.setHeight(height);
});