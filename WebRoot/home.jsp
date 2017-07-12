<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib  uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
	<head>
		<title>一凑网-一元凑单商品集</title>
		<meta http-equiv="content-Type" content="text/html;charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width">
		<meta name="Keyword" content="一元凑单，一元商品，京东凑单">
		<meta name="Author" content="xiaozhi">
		<meta name="Description" content="一元凑单">
		<link rel="stylesheet" media="screen and (max-width: 1100px)" href="css/index/css1.css" />			<!-- 1024*n-->
		<link rel="stylesheet" media="screen and (max-width: 1300px) and (min-width: 1100px)" href="css/index/css2.css" />  <!-- 1366*768缩放 -->
		<link rel="stylesheet" media="screen and (min-width: 1300px) and (max-width:1600px)" href="css/index/css3.css" />  <!-- 1366*768 -->
		<link rel="stylesheet" media="screen and (min-width: 1600px) and (max-width:1800px)" href="css/index/css4.css" />  <!-- 1920*1080缩放 -->
		<link rel="stylesheet" media="screen and (min-width: 1800px)" href="css/index/css5.css" />  <!--1920*1080-->
	</head>
	<body align="center" style="text-align:center">
		<div id="body">
			<div id="header">
				<div id="logo"><a href="<%=basePath%>"><img src="images/logo.png" /></a></div>
				<div>
					<div id="guider" >
							<ul>
								<li>请  
									<a href="user/login">登录</a> 
									<a href="user/regist">注册</a>
								</li>
								<li><a href="#">我的一凑</a></li>
								<li><a href="#" >留言板</a></li>
								<li><a href="#">建议反馈</a></li>
								<li><a href="#">关于我们</a></li>
								<li><a href="#">进入后台</a></li>
								<li><a id="mianzeurl" onclick="javascript:showMianze()">免责声明</a></li>
							</ul>
					</div>
					<div>	
						<div id="erweima" ><img src="images/erweima.png" /><p>扫码有惊喜 </p></div>
					</div>
				</div>
			</div> <!-- end of header -->
			
			<div id="content">
				<div id="content-left">
					<div id="left-header">
							<div class="guidesel">
								<a href="<%=basePath%>">一元凑单</a>
							</div>
							<div class="guide">
								<a href="<%=basePath%>music">听歌</a>
							</div>
<!-- 							<div class="guide">
								<a href="#">查快递</a>
							</div> -->
					</div>
					<div id="content-border">
						<div id="content-content">
							<div id="filter">
								<form id="form" method="post">   <!-- 未指定action调用默认action 即index.action -->
									<span class="shop" >选择商城：
											<div id="shopselect">
												<input type="hidden" name="shop" value="${shop}"/>
												<a class="selected">
													<span>${shopStr}</span>
													<span style="float:right;margin-right:3px"><img class="jiantou" src="images/index/down.png" /></span>
												</a>
											</div>
											<div class="shopselectoption">
												<a class="shopoption" value="1">京东&nbsp;</a>
												<a class="shopoption" value="2">天猫&nbsp;</a>
												<a class="shopoption" value="3">一号店&nbsp;</a>
												<a class="shopoption" value="4">全部&nbsp;</a>
											</div> 
										</span>
										
										
										<span class="area">选择地区：
											<div id="areaselect">
												<input type="hidden" name="area" value="${area}"/>
												<a class="selected"><!-- &nbsp;&nbsp;全部地区&nbsp;&nbsp;-->
													<span style="margin-left:13px;">${areaStr}</span>
													<span style="float:right;margin-right:3px"><img class="jiantou" src="images/index/down.png"  /></span>
												</a>
											</div>
											<div class="areaselectoption">
												<a class="areaoption" value="1">华北（北京、山西等）</a>
												<a class="areaoption" value="2">华东（山东、浙江等）</a>
												<a class="areaoption" value="3">华南（广东、广西等）</a>
												<a class="areaoption" value="4">华中（湖北、河南等）</a>
												<a class="areaoption" value="5">西南（四川、云南等）</a>
												<a class="areaoption" value="6">东北（辽宁、吉林等）</a>
												<a class="areaoption" value="7">西北（宁夏、新疆等）</a>
												<a class="areaoption" value="8">全部地区</a>
											</div> 
										</span>
										<span class="cou">
											凑一下
										</span>
								</form>
							</div>
		     					<c:forEach items="${goods}" begin="0" end="14" var="good">
		     						<div class="goodsdiv">
										<div class="pic">
											<a href="${good.url}" target="_blank" title="${good.name}"><img src="${good.imgurl}" title="${good.name}" /></a>
										</div>
										<div class="name">
											<a href="${good.url}" target="_blank"  title="${good.name}">${good.name}</a>
										</div>
										<div class="sales"><span>已售${good.sales}件</span></div>
										<div class="price">
											<span>￥${good.price}</span>
										</div>	
									</div>
		     					</c:forEach>
						</div>
					</div> 
				</div> <!-- end of content-left -->
				
				<div id="content-right">
					  <div id="right-guide">
								<a>我的主页</a>
								<a>写留言</a>
								<a>提建议</a>
								<a>联系我们</a>
					  </div>
					  <div id="btngroup">
					  	<div id="topbtn" class="btn" href="#top" title="回到顶部">
					  		<img src="images/index/top.png"/>
					  	</div>
					  	<div id="upbtn" class="btn" title="上一页">
					  		<img src="images/index/upbtn.png"/>
					  	</div>
					  	<div id="downbtn" class="btn" title="下一页">
					  		<img src="images/index/downbtn.png"/>
					  	</div>
					  	<div id="stoptn" class="btn" title="关闭自动加载商品">
					  		<img src="images/index/stop.png"/>
					  	</div>
					  </div>
				</div> <!-- end of content-right -->
				
			</div> <!-- end of contennt -->
			
			<div id="footer" >
				<div>
					<ul>
						<li><a href="#">关于我们</a></li><span class="span">|</span>
						<li><a href="#">联系我们</a></li><span class="span">|</span>
						<li><a href="#">建议反馈</a></li><span class="span">|</span>
						<li><a href="#">商务合作</a></li><span class="span">|</span>
						<li><a href="#">营销中心</a></li><span class="span">|</span>
						<li><a href="#">手机一元凑</a></li><span class="span">|</span>
						<li><a href="#">友情链接</a></li>
					</ul>
				</div>
				<p id="mianzediv" style="font-size:16px;line-height:32px;margin:0 auto;width:100%;">免责声明：仅供个人学习交流使用！</p>
			</div>  <!-- end of footer -->
			
		</div>  <!-- end of body -->
		<script src="js/jquery.js" type="text/javascript" charset="UTF-8"></script>
		<script src="js/index.js" type="text/javascript" charset="UTF-8"></script>
	</body>
</html>