<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>邮件验证失败...</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<style>
			.icon
			{
				width:200px;
				height:200px;
				background:url(../images/err.png) no-repeat;
				background-size:cover;
				margin:0 auto;
			}
		</style>
	</head>
	<body>
		<div style="width:90%;margin:10% auto;text-align:center">
			<div class="icon"></div>
			<h1 style="margin:20px auto;">${msg}</h1>
			<h3 style="margin:20px auto;">您可能需要：<a href="login" style="">登陆</a><a href="regist" style="margin-left:10px;">重新注册</a></h3>
		</div>
	</body>

</html>