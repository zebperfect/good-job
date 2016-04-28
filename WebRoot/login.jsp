<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户登录</title>
     <link rel="shortcut icon" href="<%=basePath%>images/favicon.ico">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
	body{
  		background-image: url(pages/images/bg.jpg);
  		background-repeat:no-repeat;
 	}
	.logintd{
  		background-color:#ffffff;
  		margin:0 auto;
  		position:relative;
  		
 	}

	</style>
	<script type="text/javascript" >
function keyConvert() {
	if (event.keyCode == 13 && event.srcElement.type != "button") {
		event.keyCode = 9;
	}
};
var basePath = '<%=basePath%>';
 </script>
	<jsp:include page="/public/commons.jsp"></jsp:include>
    <script type="text/javascript" src="<%=basePath%>javascript/login.js"></script>
  </head>
	
 <body onkeydown="keyConvert();">
  <table width="370px" height="250px" border="0" style="margin-top: 200px"  align="center">
  <tr class="logintd" align="center"><td align="center"><font color="#795942" face="Arial, Helvetica, sans-serif" size="+2">管理系统</font></td></tr>
  <tr class="logintd" align="center"><td >
			<div id="loginDiv" style="height:110;width: 180"></div>
	</td></tr>
	<tr class="logintd" align="center"><td align="right"><a href="http://wpa.qq.com/msgrd?v=3&uin=3400188229&site=qq&menu=yes" target="_blank" title="在线QQ客服">
			<img align="left" src="images/qq.gif"></a><a href="<%=basePath%>pages/register.jsp"><font class="fl">立即去注册</font></a>&nbsp;&nbsp;</td></tr>
	</table>
</body>
</html>
