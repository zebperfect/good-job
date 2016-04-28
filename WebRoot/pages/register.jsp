<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="<%=basePath%>pages/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/js/stepBar.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/js/register.js"></script>
<script type="text/javascript">
$(function(){
	stepBar.init("stepBar",{
		step : 1,
		change : true,
		animation : true
	});
});
    function myCheck()
    {
       for(var i=1;i<document.form1.elements.length-1;i++)
       {
          if(trim(document.form1.elements[i].value)=="")
          {
             alert("当前表单不能有空项");
             document.form1.elements[i].focus();
             return false;
          }
       }
       alert("信息填写无误，注册手机号即是登录用户名，请牢记手机号、密码。");
       return true;
      
    }
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
   <div class="inputsec">
   <form action="registerUser.action" id="form1" name="form1" method="post" onsubmit="return myCheck()" namespace="/" >  
     <ul>
	   <li><label class="fl mr2">推荐人：</label>
           <input type="text" tabindex="3" id="jbPhone" name="user.parentiphone" autocomplete="off" maxlength="11" onBlur="checkjbPhone()" style="ime-mode:disabled" class="txt-m fl " title="请输入推荐人11位手机号码" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="jbPhoneTip"></span> </li>
	   <li><label class="fl mr2">姓名：</label>
           <input type="text" tabindex="3" name="user.username" id="nickname" autocomplete="off" onBlur="checknickname()" class="txt-m fl " title="请输入您的姓名" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="nicknameTip"></span> </li>
	   <li><label class="fl mr2">身份证号：</label>
           <input type="text" tabindex="3" name="user.usercard" id="jbCredentialsCode" autocomplete="off" maxlength="18" onBlur="checkjbCredentialsCode()" style="ime-mode:disabled" class="txt-m fl " title="请输入您的身份证号" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
          <span id="jbCredentialsCodeTip"></span> </li>
	   <li><label class="fl mr2">密码：</label>
           <input type="password" tabindex="3" name="user.password" id="password" autocomplete="off" onBlur="checkpassword()" style="ime-mode:disabled" class="txt-m fl " title="请输入您的密码" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
          <span id="passwordTip"></span> </li>
	   <li><label class="fl mr2">确认密码：</label>
           <input type="password" tabindex="3" name="newPassword" id="passwordRepeat" autocomplete="off" onBlur="checkpasswrodb()" style="ime-mode:disabled" class="txt-m fl " title="请输入再次输入密码，并和上面一致" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="passwordRepeatTip"></span> </li>
       <li><label class="fl mr2">手机号码：</label>
           <input type="text" tabindex="3" id="regPhone" name="user.telephone" autocomplete="off" maxlength="11" onBlur="checkregPhone()" style="ime-mode:disabled" class="txt-m fl " title="请输入11位手机号码" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="regPhoneTip"></span> </li>
       <li><label class="fl mr2">验证码：</label><span class="fl yzm">
	   <input type="text" class="fl txt-y" id="checkNum" name="delData" title="不区分大小写" onBlur="checkNumber()" maxLength="6" onClick="if(value==defaultValue){value='';this.style.color='#333'}"/>
       <a class="fl mr2 mt2"><img src="<%=basePath%>rand.action"  id="checkNumImage" width="83" height="27"  alt=""/></a><a class="fl chg" onClick="changeCheckNum()" title="点击换一张">换一张</a></span>
      	  <span id="checkNumTip"></span> </li>
       <li><label class="fl mr2">校验码：</label><span class="fl yzm2"><input type="text" class="fl txt-j" id="SmsCheckCode" name="path" title="请输入校验码" onClick="if(value==defaultValue){value='';this.style.color='#333'}"/></span><input type="button" id="btnSendCode" name="btnSendCode" value="获取验证码" onClick="sendMessage()" class="db fl chg send"/>
       </li>
       <li><label class="fl">&nbsp;</label><span class="fl"><input type="checkbox" name="checkbox1" checked="checked" value="1">我已阅读并同意<a style="text-decoration:underline;">《仁鑫鼎服务条款协议》</a></span></li>
       <li class="clr"><label class="db fl">&nbsp;</label><input type="submit" value="注册" class="db fl next"/></li>
     </ul>
	 </form>
   </div>
 </section>
</body>
</html>