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
    
    <title>用户详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/public/commons.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath%>right/js/style.css"  type="text/css">
	<script type="text/javascript" src="<%=basePath%>pages/js/register.js"></script>		
  </head>
  <script type="text/javascript">
        <%  Users user = ((Users)session.getAttribute("user"));
        		String singlemoney = (String)session.getAttribute("singlemoney");
        		if(user == null){
        %>
        	window.parent.open('<%=basePath%>login.jsp', '_self');
        <%}else {%>
    	  var userTrueName = '<%=user.getUsername() %>';
        	  <%}%>
</script>
  <body>
    <section class="main w12">
   <div class="inputsec">
   <form action="updateSelfUsers.action" id="form1" name="form1" method="post" namespace="/" >  
     <ul><input type="hidden" value="${user.id }"  name="user.id" >
	   <li><label class="fl mr2">推荐人：</label>
           <input type="text" tabindex="3" id="jbPhone" value="${user.parentiphone }" disabled="true " autocomplete="off" maxlength="11" onBlur="checkjbPhone()" style="ime-mode:disabled" class="txt-m fl " title="请输入推荐人11位手机号码" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="jbPhoneTip"></span> </li>
       <li><label class="fl mr2">手机号码：</label>
           <input type="text" tabindex="3" id="regPhone" value="${user.telephone }" disabled="true " autocomplete="off" maxlength="11" onBlur="checkregPhone()" style="ime-mode:disabled" class="txt-m fl " title="请输入11位手机号码" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="regPhoneTip"></span> </li>
	   <li><label class="fl mr2">姓名：</label>
           <input type="text" tabindex="3" name="user.username" value="${user.username }"  disabled="true " id="nickname" autocomplete="off" onBlur="checknickname()" style="ime-mode:disabled" class="txt-m fl " title="请输入您的姓名" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
           <span id="nicknameTip"></span> </li>
	   <li><label class="fl mr2">身份证号：</label>
           <input type="text" tabindex="3" name="user.usercard" value="${user.usercard }" disabled="true " id="jbCredentialsCode" autocomplete="off" maxlength="18" onBlur="checkjbCredentialsCode()" style="ime-mode:disabled" class="txt-m fl " title="请输入您的身份证号" onClick="if(value==defaultValue){value='';this.style.color='#333'}" >
          <span id="jbCredentialsCodeTip"></span> </li>
	   <li><label class="fl mr2">开户银行：</label>
           <input type="text" tabindex="3" name="user.bankname" value="${user.bankname }" id="bankname" autocomplete="off" onBlur="checkbankname()" class="txt-m fl " title="请输入开户银行地址" >
          <span id="banknameTip"></span> </li>
	   <li><label class="fl mr2">开户行：</label>
           <input type="text" tabindex="3" name="user.bankaddress" value="${user.bankaddress }" id="bankaddress" autocomplete="off" onBlur="checkbankaddress()" class="txt-m fl " title="请输入开户银行地址" ><span class="fl"><button  onclick="comfrimBank()" class="txt-bu">开户行查询</button></span>
          <span id="bankaddressTip"></span> </li>
	   <li><label class="fl mr2">开户卡号：</label>
           <input type="text" tabindex="3" name="user.bankcard" value="${user.bankcard }" id="bankcard" autocomplete="off" onBlur="checkBankcard()" style="ime-mode:disabled" class="txt-m fl " title="请输入您的银行卡号"  >
          <span id="bankcardTip"></span> </li>
	   <li><label class="fl mr2">快递方式：</label><div class="txt-m fl "><select style="line-height:inherit; width:inherit" id="logs" name="user.shiptype" onBlur="checkLogs()" style="ime-mode:disabled" title="请选择快递方式"> <option value="">请选择</option>
        <option  value="0">省外邮递</option>
        <option  value="1">省内邮递</option>
        <option  value="2">自取</option> 
		</select></div> <span id="logsTip"></span> </li>
       <li><label class="fl mr2">收货地址：</label>
           <input type="text" tabindex="3" id="regPhone1" name="user.shipaddress" value="${user.shipaddress }" autocomplete="off"  class="txt-m fl " title="请输入收货地址，自取可不填" >
           <span id="regPhoneTip1"></span> </li>
       
       <li><label class="fl mr2">验证码：</label><span class="fl yzm">
	   <input type="text" class="fl txt-y" id="checkNum" name="delData" title="不区分大小写" onBlur="checkNumber()" maxLength="6" onClick="if(value==defaultValue){value='';this.style.color='#333'}"/>
       <a class="fl mr2 mt2"><img src="<%=basePath%>rand.action"  id="checkNumImage" width="83" height="27"  alt=""/></a><a class="fl chg" onClick="changeCheckNum()" title="点击换一张">换一张</a></span>
      	  <span id="checkNumTip"></span> </li>
       <li class="clr"><label class="db fl">&nbsp;</label><input type="submit" value="保存" class="db fl next"/><input type="reset" value="取消" class="db fl next"/></li>
     </ul>
	 </form>
   </div>
 </section>
  </body>
</html>
