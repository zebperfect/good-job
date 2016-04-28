<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统分润月报查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/public/commons.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>public/JS/exttoexcel.js"></script>
	<script type="text/javascript" src="<%=basePath%>profitreport/js/selectUserCodeForm.js"></script>	
	<script type="text/javascript" src="<%=basePath%>profitreport/js/profitReportQuery.js"></script>	
  </head>
  
   <body>
    <div id="profitReportQuery" style="width:100%; height:100%"></div>
  </body>
</html>
