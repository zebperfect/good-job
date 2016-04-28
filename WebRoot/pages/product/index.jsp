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
<meta http-equiv="description" content="This is my page"><meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Mihstore Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />

<link href="<%=basePath%>pages/product/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<%=basePath%>pages/product/js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="<%=basePath%>pages/product/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="<%=basePath%>pages/css/style.css"  type="text/css">	
<!--//theme-style-->
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--fonts-->
<link href='http://fonts.useso.com/css?family=Cabin:400,500,600,700' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="<%=basePath%>pages/product/js/move-top.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/product/js/easing.js"></script>
<!--//fonts-->
<!--//slider-script-->
<script>$(document).ready(function(c) {
	$('.alert-close').on('click', function(c){
		$('.message').fadeOut('slow', function(c){
	  		$('.message').remove();
		});
	});	  
});
</script>
<script>$(document).ready(function(c) {
	$('.alert-close1').on('click', function(c){
		$('.message1').fadeOut('slow', function(c){
	  		$('.message1').remove();
		});
	});	  
});
</script>
<script>$(document).ready(function(c) {
	$('.alert-close2').on('click', function(c){
		$('.message2').fadeOut('slow', function(c){
	  		$('.message2').remove();
		});
	});	  
});
</script>
<script type="text/javascript">
					jQuery(document).ready(function($) {
						$(".scroll").click(function(event){		
							event.preventDefault();
							$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
						});
					});
				</script>	
<!-- start menu -->
<link href="<%=basePath%>pages/product/css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="<%=basePath%>pages/product/js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>				
  </head>
<body>
 <!--header-->
<header>
   <div class="w12 header">
   <a class="db logo fl"><font face="新宋体" style="letter-spacing:5px;" size="14">仁鑫鼎</font><font size="4">|产品中心</font></a>
   <div class="fl textfr"><font size="4"><a href="<%=basePath%>pages/product/index.jsp">产品介绍</a></div><div class="fl textfr"><a href="">使用说明</a></div>
   <div class="fl textfr"><a href="">招商代理</a></div><div class="fl lastfr"><a href="">关于我们</a></font></div>
   <div class="fr logofr">注册或使用遇到问题可<a href="http://wpa.qq.com/msgrd?v=3&uin=3400188229&site=qq&menu=yes" target="_blank" title="在线QQ客服">
			<img src="<%=basePath%>images/qq.gif"></a><br> 或拨打：<strong style="font-size:14px;">400-883-3697</strong><br>
   已经是会员，请&nbsp;&nbsp;<a class="db logbtn fr" href="<%=basePath%>login.jsp">前往登录</a>
</div>
   </div>
 </header>
 <div class="head_border"></div>
	<div class="container">
		<!---->
		<div class="content">

			<div class="col-md-9" align="center">
			<div class="shoe">
				<img class="img-responsive" src="<%=basePath%>pages/product/images/banner.jpg" alt="" >
				<div class="shop">
					<h4>正在 <span>热销中</span></h4>
					<p>2016新款</p>
				</div>
			</div>
				<div class="content-bottom">
					<h3>新款pos机</h3>
					<div class="bottom-grid">
						<div class="col-md-4 shirt">
							<div class="bottom-grid-top">
								<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos.jpg" alt="" >
								<div class="five">
									<h6 >热销</h6>
								</div>
								<div class="pre">
									<p>2014款pos机</p>
									<span>￥360.00</span>
									<div class="clearfix"> </div>
								</div></a>
								
								
							</div>
						</div>
						<div class="col-md-4 shirt">
							<div class="bottom-grid-top">
								<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos1.jpg" alt="" >
								<div class="five">
								<h6 class="one" >热销</h6>
								</div>
								<div class="pre">
									<p>2015款pos机</p>
									<span>￥460.00</span>
									<div class="clearfix"> </div>
								</div></a>
								
								
							</div>
						</div>
						<div class="col-md-4 shirt">
							<div class="bottom-grid-top">
								<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos2.jpg" alt="" >
								<div class="five">
								<h6 class="one1" >热销</h6>
								</div>
								<div class="pre">
									<p>2016款pos机</p>
									<span>￥660.00</span>
									<div class="clearfix"> </div>
								</div></a>
								
								
							</div>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="bottom-grid">
						<div class="col-md-4 shirt">
							<div class="bottom-grid-top">
								<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos3.jpg" alt="" >
								<div class="five">
								<h6 class="one" >热销</h6>
								</div>
								<div class="pre">
									<p>pos充值服务</p>
									<span>￥60.00</span>
									<div class="clearfix"> </div>
								</div></a>
								
								
							</div>
						</div>
						<div class="col-md-4 shirt">
							<div class="bottom-grid-top">
								<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos4.jpg" alt="" >
								<div class="five">
								<h6 class="one1">0利润</h6>
								</div>
								<div class="pre">
									<p>2015热销款</p>
									<span>￥60.00</span>
									<div class="clearfix"> </div>
								</div></a>
								
								
							</div>
						</div>
						<div class="col-md-4 shirt">
							<div class="bottom-grid-top">
								<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos5.jpg" alt="" >
								<div class="five">
								<h6 >热销</h6>
								</div>
								<div class="pre">
									<p>定制pos机</p>
									<span>￥60.00</span>
									<div class="clearfix"> </div>
								</div></a>
								
								
							</div>
						</div>
						<div class="clearfix"> </div>
					</div>
				</div>
				<ul class="start">
					<li><span>1</span></li>
					<li class="arrow"><a href="#">2</a></li>
				</ul>
			</div>

			<div class="col-md-3 col-md">
			
					<div class="content-bottom-grid">
					<h3>热销排行榜</h3>
					<div class="latest-grid">
						<div class="news">
							<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos.jpg" title="name" alt=""></a>
						</div>
						<div class="news-in">
							<h6><a href="<%=basePath%>pages/product/single.jsp">2015款pos机</a></h6>
							<p>功能全面，全方位定制</p>
							<ul>
								<li>售价: <span>￥110</span> </li><b>|</b>
								<li>数量: <span>120</span></li>
							</ul>
						</div>
					<div class="clearfix"> </div>
				</div>
				<div class="latest-grid">
						<div class="news">
							<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos5.jpg" title="name" alt=""></a>
						</div>
						<div class="news-in">
							<h6><a href="<%=basePath%>pages/product/single.jsp">2014款pos机</a></h6>
							<p>功能全面，全方位定制</p>
							<ul>
								<li>售价: <span>￥110</span> </li><b>|</b>
								<li>数量: <span>100</span></li>
							</ul>
						</div>
					<div class="clearfix"> </div>
				</div>
				<div class="latest-grid">
						<div class="news">
							<a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/pos4.jpg" title="name" alt=""></a>
						</div>
						<div class="news-in">
							<h6><a href="<%=basePath%>pages/product/single.jsp">2016款pos机</a></h6>
							<p>功能全面，全方位定制</p>
							<ul>
								<li>售价: <span>￥110</span> </li><b>|</b>
								<li>数量: <span>20</span></li>
							</ul>
						</div>
					<div class="clearfix"> </div>
				</div>
					</div>
				<!---->
				<div class="money">
					<h3>支付方式</h3>
						<ul class="money-in">
						<li><a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/p1.png" title="name" alt=""></a></li>
						<li><a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/p2.png" title="name" alt=""></a></li>
						<li><a href="<%=basePath%>pages/product/single.jsp"><img class="img-responsive" src="<%=basePath%>pages/product/images/p3.png" title="name" alt=""></a></li>
						</ul>
					</div>
			</div>
			<div class="clearfix"> </div>
		</div>
		<!---->
		<div class="footer">
			<p class="footer-class" align="center">Copyright &copy; 2016.Company name All rights reserved <a href="http://www.renxinding.com/" target="_blank" title="青岛仁鑫鼎商贸有限公司">青岛仁鑫鼎商贸有限公司</a> </p>
		<div class="clearfix"> </div>
		</div>	 	
	 </div>
	</div>
	<!---->
</body>
</html>