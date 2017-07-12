<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>一凑网-用户登陆</title> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv="Pragma" content="no-cache"> 
		<meta http-equiv="Cache-Control" content="no-cache"> 
		<meta http-equiv="Expires" content="0"> 
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link rel="stylesheet" href="../css/login/style.css" />
	</head> 
	<body> 
		<div style="width:70%;height:100%;float:left;">
			<div style="width:100%;float:left;">
				<div style="float:left;color:rgba(1, 45, 49, 0.44);font-size:56px;margin-top:20%;margin-left:10%;">青，取之于蓝而青于蓝。</div>
				<div style="float:right;color:rgba(1, 45, 49, 0.44);font-size:56px;margin-top:10%;margin-right:10%;">冰，水为之而寒于水。</div>
			</div>
		</div>

		<div style="width:30%;height:100%;float:left;">
				<div class="login">
					<a href="../" style="font-size: 15px;top: 18px;width: 70px;position: absolute;right: 10%;">返回首页</a>
				    <div class="message">一凑网-用户登录</div>
				    <div id="darkbannerwrap"></div>
				    <form action="login" method="post" autocomplete="on">
						<input name="username" placeholder="用户名/邮箱" required="required" type="text" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')"/>
						<hr class="hr14">
					    <input name="password" placeholder="密码" required="required" type="password" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')"/>
						<hr class="hr14">
						<input value="登录" style="width:100%;" type="submit" />
						<hr class="hr18">
							<a href="regist" style="float:right;font-size:15px;">新用户注册</a>
							<a href="#" style="float:left;font-size:15px;">忘记密码</a>
					</form>
				</div>
				<div class="copyright"><a href="<%=basePath %>" target="_blank">@一凑网 </a>©用户登陆页</div>
		</div>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/layer/layer.js"></script>
		<script type="text/javascript">
			$('input[name=username]').blur(function(){
					if($(this).val()==null || $(this).val()=="") return;
					$.get('<%=basePath%>/user/uservali',{username:$(this).val()},function(data){
						if(data.username || data.email) return;
						else layer.tips('用户名或邮箱不存在!', 'input[name=username]',{tips:3});$('input[name=username]').focus();
					},'json');
				});
		</script>
	</body>
</html>