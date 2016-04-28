Ext.onReady(function() {
			Ext.BLANK_IMAGE_URL = 'ExtJs/resources/images/default/s.gif';
			Ext.QuickTips.init();

			var loginform = new Ext.FormPanel({
						// title:'用户登录',
						labelWidth : 50,
						frame : false,
						height : 127,
						width : 225,
						bodyStyle : 'padding:5px 5px 0',
						// width : 350,
						waitMsgTarget : true,
						renderTo : 'loginDiv',
						baseCls : "panel",
						// panel背景
						buttonAlign : 'left',
						defaults : {
			// width : 230
						},
						defaultType : 'textfield',
						items : [{
									fieldLabel : '用户名',
									name : 'user.telephone',
									allowBlank : false,
									id : 'userNameId'
								}, {
									fieldLabel : '密码',
									name : 'user.password',
									inputType : 'password',
									allowBlank : false
								}, {
									fieldLabel : '验证码',  
				                    width : 70,  
				                    blankText : '验证码不能为空',
									id : 'randCode',
									name : 'user.username',
									allowBlank : false
								}],
						buttons : [{
									text : '登陆',
									disabled : false,
									handler : function() {
										if (loginform.form.isValid()) {
											loginform.form.submit({
														url : 'login.action',
														success : function(form, action) {
															loginform.getForm().reset();
															if(action.result.start != null && action.result.start == 1){
																Ext.Msg.alert('提示','<font >当前用户未激活，无法进入系统，请点击此<a href="pages/paymoney.jsp">购买链接</a>激活。</font>');
															}else if(action.result.success){																
																var woiwo = window.open(basePath + 'index.jsp', '_self', 'fullscreen=yes');
																if (!woiwo) {
																	alert('管理窗口被您系统中的拦截器拦截，如果您使用了窗口拦截器，请尝试关闭它，以便打开该窗口。');
																} else {
																	woiwo.moveTo(0, 0);
																	woiwo.resizeTo(screen.availWidth, screen.availHeight);
																	woiwo.outerWidth = screen.availWidth;
																	woiwo.outerHeight = screen.availHeight;
																}
															}
														},
														failure : function(form, action) {
															Ext.Msg.alert('登陆失败', '用户名、密码或验证码错误！');
															loginform.getForm().reset();
														},
														waitMsg : '正在登陆，稍后...'
													});
										} else {
											Ext.Msg.alert('信息', '请填写完成再提交!');
										}
									}
								}, {
									text : '重置',
									handler : function() {
										loginform.getForm().reset();
									}
								}]
					});
			var bd = Ext.getDom('randCode');  
		    var bd2 = Ext.get(bd.parentNode);  
		    var d = new Date().getTime();
		    bd2.createChild({  
		        tag : 'img',  
		        src : 'rand.action?d='+d,  
		        align : 'absbottom'  
		    });  
			Ext.getCmp("userNameId").focus(true, true);
		});