<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD><TITLE>你访问的页面不存在或被删除！</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<META http-equiv=refresh content=3;url=<%=basePath%>index.jsp>
<STYLE type=text/css>.font14 {
	FONT-SIZE: 14px
}
.font12 {
	FONT-SIZE: 12px
}
.font12 a{
	FONT-SIZE: 12px; color: #CC0000; text-decoration:none;
}
</STYLE>

<META content="MSHTML 6.00.2900.3354" name=GENERATOR></HEAD>
<BODY>
<TABLE height=500 cellSpacing=0 cellPadding=0 width=500 align=center 
background=<%=basePath%>/images/x.gif border=0>
  <TBODY>
  <TR>
    <TD height=330>　</TD></TR>
  <TR>
    <TD vAlign=top>
      <DIV class=font14 align=center><STRONG>你访问的页面<FONT 
      color=#0099ff>不存在</FONT>或被<FONT color=#ff0000>删除！<BR></FONT></STRONG><SPAN 
      class=font12><FONT color=#666666>三秒后自动返回<A href="<%=basePath%>index.jsp">首页</A>........ 
    </FONT></SPAN></DIV></TD></TR></TBODY></TABLE></BODY></HTML>
