<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");

	String prjpath = request.getContextPath();
	request.setAttribute("prjpath", prjpath);
%>

<script type="text/javascript">
var prjpath = "${prjpath}"; 
var basePath = '<%=basePath%>';
</script>
	
<%-- 通用的js与css --%>
<script type="text/javascript" src="${prjpath }/javascript/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>ExtJs/resources/css/ext-all.css">

<%--中文字体大小css 	--%>
<link id="extjsStyle3" rel="stylesheet" type="text/css" href="<%=basePath%>ExtJs/icon.css">	
<link id="extjsStyle2" rel="stylesheet" type="text/css" href="<%=basePath%>ExtJs/resources/css/xtheme-blue.css">
<link id="extjsStyle" rel="stylesheet" type="text/css" href="<%=basePath%>public/CSS/FormLayout.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>ExtJs/ghost.css">
<link rel="shortcut icon" href="<%=basePath%>images/favicon.ico">
<script type="text/javascript" src="<%=basePath%>ExtJs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>ExtJs/ext-all.js"></script>
<script type="text/javascript" src="<%=basePath%>ExtJs/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/pluigin/TabCloseMenu.js"></script>
<script type="text/javascript" src="<%=basePath %>ExtJs/ghost.js"></script>	
 <script type="text/javascript" src="<%=basePath%>ExtJs/PageComboResizer/PageComboResizer.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=basePath%>kindeditor/editor.js"></script>
<script type="text/javascript" src="<%=basePath%>public/JS/commonMethod.js"></script>
<jsp:include page="/public/right.jsp"></jsp:include>

<script type="text/javascript" >
function keyConvert() {
	if (event.keyCode == 13 && event.srcElement.type != "button") {
		event.keyCode = 9;
	}
};
</script>
<script type="text/javascript">
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

	//以下进行测试  
	var BrowserInformation;
	if (Sys.ie)
		BrowserInformation = 'IE: ' + Sys.ie;
	else if (Sys.firefox)
		BrowserInformation = "Firefox: " + Sys.firefox;
	else if (Sys.chrome)
		BrowserInformation = "Chrome: " + Sys.chrome;
	else if (Sys.opera)
		BrowserInformation = "Opera: " + Sys.opera;
	else if (Sys.safari)
		BrowserInformation = "Safari: " + Sys.safari;
</script>
 