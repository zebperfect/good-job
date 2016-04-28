<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zebsoft.right.domain.Users"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>仁鑫鼎网上管理平台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/public/commons.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath%>public/CSS/index.css"  type="text/css">
	<script type="text/javascript" >
		var basePath = '<%=basePath%>';
      </script>
	 <script type="text/javascript" src="<%=basePath%>javascript/general.js">
        </script>
        <!-- ENDLIBS -->
        <script type="text/javascript">
        <%  Users rightUser = ((Users)session.getAttribute("user"));
        		if(rightUser == null){
        %>
        	window.parent.open('<%=basePath%>login.jsp', '_self');
        <%}else {%>
    	  var userTrueName = '<%=rightUser.getUsername() %>';
        	  <%}%>
        </script>
       <script type="text/javascript" src="<%=basePath%>javascript/index.js">
        </script>
  </head>
  
  <body >
  </body>
</html>
