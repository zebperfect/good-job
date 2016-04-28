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
<!-- 图片模块start-->
<link href="<%=basePath%>pages/picture/css/bootstrap-responsive.min.css" rel="stylesheet"></head>
<link href="<%=basePath%>pages/picture/css/style.css" rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>pages/picture/js/jquery-latest.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/picture/js/js.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/picture/js/slimbox2.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/picture/js/bootstrap.min.js"></script>
<!--end -->
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
<script type="text/javascript" src="<%=basePath%>pages/js/register.js"></script>
<script type="text/javascript">
        <%  Users user = ((Users)session.getAttribute("tempuser"));
        		String singlemoney = (String)session.getAttribute("singlemoney");
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
		step : 2,
		change : true,
		animation : true
	});
});
</script>
<script type="text/javascript">
            function myCheck()
            {
               if(document.form1.logs.value.trim()=="")
                  {
                     alert("物流方式不能为空");
                     document.form1.logs.focus();
                     return false;
                  }else{
             		  return true;
				  }
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
	<div class="cards_wrap grayscale-images no-border no-bounce wide-margin clearfix" id="people_wrapper">
                <li class="cards" id="people_1">
                    <img src="<%=basePath%>pages/product/images/pos.jpg"/>
                    <span class="caption_right">指纹型pos机</span>
                    <span class="caption_right_2" style="color:#FF0000">999元</span>
                    <span class="social_base">
                        <a href="" title="选定该商品"><img src="<%=basePath%>pages/picture/images/buy.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="打开查看详细"><img src="<%=basePath%>pages/picture/images/open.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="分享"><img src="<%=basePath%>pages/picture/images/share.gif" class="social_icons" /></a>
                    </span>
                </li>
                <li class="cards" id="people_2">
                    <img src="<%=basePath%>pages/product/images/pos1.jpg"/>
                    <span class="caption_right">指纹型pos机</span>
                    <span class="caption_right_2" style="color:#FF0000">999元</span>
                    <span class="social_base">
                        <a href="" title="选定该商品"><img src="<%=basePath%>pages/picture/images/buy.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="打开查看详细"><img src="<%=basePath%>pages/picture/images/open.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="分享"><img src="<%=basePath%>pages/picture/images/share.gif" class="social_icons" /></a>
                    </span>
                </li>
                <li class="cards" id="people_3">
                    <img src="<%=basePath%>pages/product/images/pos2.jpg"/>
                    <span class="caption_right">指纹型pos机</span>
                    <span class="caption_right_2" style="color:#FF0000">999元</span>
                    <span class="social_base">
                        <a href="" title="选定该商品"><img src="<%=basePath%>pages/picture/images/buy.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="打开查看详细"><img src="<%=basePath%>pages/picture/images/open.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="分享"><img src="<%=basePath%>pages/picture/images/share.gif" class="social_icons" /></a>
                    </span>
                </li>
                <li class="cards" id="people_4">
                    <img src="<%=basePath%>pages/product/images/pos3.jpg"/>
                    <span class="caption_right">指纹型pos机</span>
                    <span class="caption_right_2" style="color:#FF0000">999元</span>
                    <span class="social_base">
                        <a href="" title="选定该商品"><img src="<%=basePath%>pages/picture/images/buy.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="打开查看详细"><img src="<%=basePath%>pages/picture/images/open.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="分享"><img src="<%=basePath%>pages/picture/images/share.gif" class="social_icons" /></a>
                    </span>
                </li>
				<li class="cards" id="people_5">
                    <img src="<%=basePath%>pages/product/images/pos4.jpg"/>
                    <span class="caption_right">指纹型pos机</span>
                    <span class="caption_right_2" style="color:#FF0000">999元</span>
                    <span class="social_base">
                        <a href="" title="选定该商品"><img src="<%=basePath%>pages/picture/images/buy.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="打开查看详细"><img src="<%=basePath%>pages/picture/images/open.gif" class="social_icons" /></a>
                        <a href="<%=basePath%>pages/product/single.jsp" title="分享"><img src="<%=basePath%>pages/picture/images/share.gif" class="social_icons" /></a>
                    </span>
                </li>
            </div>
   </div>
   <div class="inputsec">
   <form action="paymoney.action" id="form1" name="form1" method="post" namespace="/" onSubmit="return myCheck()">  
     <ul>
     <input type="hidden"  name="user.id" value="<%=user.getId() %>"  >
	   <li><div class="fl mr2">选择图片下按钮可查看产品介绍，系统默认标准款下单</div></li>
	   <li><label class="fl mr2">设备单价：</label><input type="text" tabindex="3" id="paymoney" disabled="true " style="ime-mode:disabled" .disabled="true " class="txt-m fl " value="<%=singlemoney %>元"  > </li>
	   <li><label class="fl mr2">姓名：</label><input type="text" tabindex="3" disabled="true " value="<%=user.getUsername() %>" id="nickname" autocomplete="off"  class="txt-m fl "></li>
	   <li><label class="fl mr2">开户银行：</label>
           <input type="text" tabindex="3" name="user.bankname" id="bankname" autocomplete="off" onBlur="checkbankname()" class="txt-m fl " title="请输入开户银行名称" onClick="if(value==defaultValue){value='';this.style.color='#333'}" ><span class="fl">
          <span id="banknameTip"></span> </li>
	   <li><label class="fl mr2">开户地址：</label>
           <input type="text" tabindex="3" name="user.bankaddress" id="bankaddress" autocomplete="off" onBlur="checkbankaddress()" class="txt-m fl " title="请输入开户银行地址" onClick="if(value==defaultValue){value='';this.style.color='#333'}" ><span class="fl"><button  onclick="comfrimBank()" class="txt-bu">开户行查询</button></span>
          <span id="bankaddressTip"></span> </li>
	   <li><label class="fl mr2">开户卡号：</label>
           <input type="text" tabindex="3" name="user.bankcard" id="bankcard" autocomplete="off" onBlur="checkBankcard()" style="ime-mode:disabled" class="txt-m fl " title="请输入您的银行卡号" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
          <span id="bankcardTip"></span> </li>
	   <li><label class="fl mr2">快递方式：</label><div class="txt-m fl "><select style="line-height:inherit; width:inherit" id="logs" name="user.shiptype" onBlur="checkLogs()" style="ime-mode:disabled" title="请选择快递方式"> <option value="">请选择</option>
        <option value="0">省外邮递</option>
        <option value="1">省内邮递</option>
        <option value="2">自取</option> 
		</select></div> <span id="logsTip"></span> </li>
       <li><label class="fl mr2">收货地址：</label>
           <input type="text" tabindex="3" id="regPhone1" name="user.shipaddress" autocomplete="off"  class="txt-m fl " title="请输入收货地址，自取可不填" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="regPhoneTip1"></span> </li>
       <li class="clr"><label class="db fl">&nbsp;</label><input type="submit" value="确认去付款" class="db fl next"/></li>
     </ul>
	 </form>
   </div>
 </section>
</body>
</html>