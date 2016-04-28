<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<%--@ 
taglib uri="http://www.opensymphony.com/oscache"  prefix="oscache"--%>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
%>
<%
	String prjpath = request.getContextPath();//项目根路径
	request.setAttribute("prjpath", prjpath);
	
%>

<script type="text/javascript">
	var prjpath = "${prjpath}";  // 应用程序的根路径 */
</script>
<%-- 通用的js与css --%>
<script type="text/javascript" src="${prjpath }/javascript/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>ExtJs/resources/css/ext-all.css">
<%--中文字体大小css 	--%>
<link id="extjsStyle" rel="stylesheet" type="text/css" href="<%=basePath%>ExtJs/icon.css">
<link id="extjsStyle2" rel="stylesheet" type="text/css" href="<%=basePath%>ExtJs/resources/css/xtheme-blue.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>ExtJs/resources/css/summary.css">
<link id="extjsStyle" rel="stylesheet" type="text/css" href="<%=basePath%>public/CSS/FormLayout.css">
<script type="text/javascript" src="<%=basePath%>ExtJs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>ExtJs/ext-all.js"></script>
<script type="text/javascript" src="<%=basePath%>ExtJs/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath %>ExtJs/JsonReader.js"></script>
<script type="text/javascript" src="<%=basePath %>ExtJs/GroupSummary.js"></script>	
<!--jsp:include page="/public/right.jsp"></jsp:include-->
<%--表格动态分页--%>
<script type="text/javascript" src="<%=basePath%>ExtJs/PageComboResizer/PageComboResizer.js">
</script>
<link rel="shortcut icon" href="<%=basePath%>images/favicon.ico">