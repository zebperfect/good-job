<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zebsoft.right.domain.Users"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>欢迎使用注册仁鑫鼎系统</title>
<link rel="stylesheet" href="<%=basePath%>pages/css/style.css"  type="text/css">
<script type="text/javascript">
document.createElement("section");
document.createElement("article");
document.createElement("footer");
document.createElement("header");
document.createElement("hgroup");
document.createElement("nav");
document.createElement("menu");
</script>
<script type="text/javascript" src="<%=basePath%>pages/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/js/stepBar.js"></script>
<script type="text/javascript">
        <%  Users user = ((Users)session.getAttribute("tempuser"));
        		if(user == null){
        %>
        	window.parent.open('<%=basePath%>pages/register.jsp', '_self');
        <%}else {%>
    	  var userTrueName = '<%=user.getUsername() %>';
        	  <%}%>
</script>
<script type="text/javascript">
$(function(){
	stepBar.init("stepBar",{
		step : 4,
		change : true,
		animation : true
	});
});
</script>  
</head>
<body>
 <header>
   <div class="w12 header">
   <a class="db logo fl"><font face="新宋体" style="letter-spacing:5px;" size="14">仁鑫鼎</font><font size="4">|注册中心</font></a>
   <div class="fl textfr"><font size="4"><a href="<%=basePath%>pages/product/index.jsp">产品介绍</a></div><div class="fl textfr"><a href="">使用说明</a></div>
   <div class="fl textfr"><a href="">招商代理</a></div><div class="fl lastfr"><a href="">关于我们</a></font></div>
   <div class="fr logofr">注册或使用遇到问题可<a href="http://wpa.qq.com/msgrd?v=3&uin=3400188229&site=qq&menu=yes" target="_blank" title="在线QQ客服">
			<img src="<%=basePath%>images/qq.gif"></a><br> 或拨打：<strong style="font-size:14px;">400-883-3697</strong><br>
   已经是会员，请&nbsp;&nbsp;<a class="db logbtn fr" href="<%=basePath%>login.jsp">前往登录</a>
</div>
   </div>
 </header>
 <div class="head_border"></div>
 <section class="main w12">
   <div class="w13">
   	<div id="stepBar" class="ui-stepBar-wrap">
		<div class="ui-stepBar">
			<div class="ui-stepProcess"></div>
		</div>
		<div class="ui-stepInfo-wrap">
			<table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence">1</a>
						<p class="ui-stepName">帐户注册</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence">2</a>
						<p class="ui-stepName">购买设备</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence">3</a>
						<p class="ui-stepName">支付方式</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence">4</a>
						<p class="ui-stepName">账户激活</p>
					</td>
				</tr>
			</table>
		</div>
	</div>
   </div>
   <div class="success">
     <ul>
       <li class="suc1">&nbsp;恭喜您注册并激活成功</li>
       <li>注册姓名：<em class="mr3">${userTrueName}</em>登录账号（手机号）<em>${user.telephone}</em></li>
       <li class="suc2"><a class="db mr4 fl" href="login.jsp">去登录系统后台</a></li>
	   <font color="red">友情提示：若您收到产品 ,首次刷卡使用后，请根据网站右上方联系方式告知管理员，以便后台登记您的设备信息，使您能够查询交易流水和提成，更好的使用系统。</font>
     </ul>
   </div>
 </section>
</body>
</html>