<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="javax.annotation.Resource"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>权限拦截页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<script type="text/javascript">

//struts2拦截器  调用方法

var reLogin = function() {
	var parentWin = window.opener;
	while(parentWin.parent)
	{
	parentWin=parentWin.parent;
	}
	parentWin.parent.href='<%=basePath%>';

}//登录窗口  
var toError = function() {
var parentWin = window.opener;
	parentWin.location.href = '<%=basePath%>/error.jsp';
			window.location.stop();
		}

		Ext.Ajax.on('requestcomplete', function(conn, response, options, e) {
			var returnVal = response.responseText;
			if (returnVal == "UserIsNull") {
				Ext.Msg.alert('提示','您太长时间没有操作系统，为了保证您的账号安全，请<a href="<%=basePath%>login.jsp" target="_top">重新登录</a>!');
				//reLogin();
			} else if (returnVal == "systemHaveException") {
				toError();
			}
		});
	</script>
</body>
</html>
