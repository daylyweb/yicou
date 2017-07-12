<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv="Pragma" content="no-cache"> 
		<meta http-equiv="Cache-Control" content="no-cache"> 
		<meta http-equiv="Expires" content="0"> 
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>一凑网-用户注册</title> 
		<link rel="stylesheet" href="../css/regiester/style.css" />
	</head> 
<body> 
<div style="width:95%;min-width:90%;min-height:100%">
	<div style="width:70%;height:100%;float:right;">
		<div style="float:left;color:rgba(1, 45, 49, 0.44);font-size:56px;margin-top:20%;margin-left:60px;">青，取之于蓝而青于蓝。</div>
		<div style="float:right;color:rgba(1, 45, 49, 0.44);font-size:56px;margin-top:10%">冰，水为之而寒于水。</div>
	</div>

    <div style="width:30%;height:100%;float:left;">
    	<div class="login">
    		<a href=".." style="font-size: 15px;top: 18px;width: 70px;position: absolute;right: 10%;">返回首页</a>
    		<div class="message">一凑网-用户注册</div>
    		<div id="darkbannerwrap"></div>
    		<form action="regist" method="post" autocomplete="on" onsubmit="return check()">
    			<input name="username" placeholder="用户名" required="required" type="text"  onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')">
    			<hr class="hr14">
    			<input name="email" placeholder="邮箱" required="required" type="email">
    			<hr class="hr14">
    			<input name="password" placeholder="密码" required="required" type="password" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')">
    			<hr class="hr14">
    			<input name="password2" placeholder="确认密码" required="required" type="password" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')">
    			<hr class="hr14">
    			<input value="注册" style="width:100%;" type="submit">
    			<hr class="hr18">
    				<a href="<%=basePath%>" target="_blank" stule="float:left">@一凑网 </a>©用户注册页
    				<a href="login" style="float:right;font-size:15px;">已有账户登陆</a>
    		</form>
    	</div>
    </div>
</div>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/layer/layer.js"></script>
		<script type="text/javascript">
			$('input[name=username]').blur(function(){
					if($(this).val()==null || $(this).val()=="") return;
					$.get('<%=basePath%>/user/uservali',{username:$(this).val()},function(data){
						if(data.username){layer.tips('用户名已存在!请修改！', 'input[name=username]',{tips:3});$('input[name=username]').focus();}
						else return;
					},'json');
				});
				
			$('input[name=email]').blur(function(){
					if($(this).val()==null || $(this).val()=="") return;
					$.get('<%=basePath%>/user/uservali',{username:$(this).val()},function(data){
						if(data.email){layer.tips('邮箱已存在!请修改！', 'input[name=email]',{tips:3});$('input[name=email]').focus();}
						else return;
					},'json');
				});
				
				function check()
				{
					if($("input[name=password2]").val()!=$("input[name=password]").val())
					{
						layer.tips('两次输入的密码不一致！',"input[name=password2]",{tips:1});
						$("input[name=password2]").focus();
						return false;
					}
					return true;
				}
		</script>
</body>
</html>