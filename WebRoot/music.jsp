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
		<title>一元凑-一元凑单品推荐</title>
		<meta http-equiv="content-Type" content="text/html;charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width">
		<meta name="Keyword" content="2shop">
		<meta name="Author" content="">
		<meta name="Description" content="">
		<link rel="stylesheet" href="css/music/player.css">
		<link rel="stylesheet" href="css/music/css3.css" />  <!-- 1366*768 -->
		<link rel="stylesheet" media="screen and (max-width: 1300px) and (min-width: 1100px)" href="css/music/css2.css" />  <!-- 1366*768缩放 -->
		<link rel="stylesheet" media="screen and (min-width: 1600px) and (max-width:1800px)" href="css/music/css4.css" />  <!-- 1920*1080缩放 -->
		<link rel="stylesheet" media="screen and (min-width: 1800px)" href="css/music/css5.css" />  <!--1920*1080-->
		
	</head>
	<body align="center">
		<div id="body">
			<div id="header" >
				<div id="logo" style=""><a href="index.jsp"><img src="<%=basePath%>/images/logo.png" href="<%=basePath%>" /></a></div>
				<div> <!--960-->
					<div id="guider" >
							<ul>
								<li>请&nbsp; 
									<a href="user/login">登录</a>
									<a href="user/regist">注册</a>
								</li>
								<li><a href="#">我的一凑</a></li>
								<li><a href="#" >留言板</a></li>
								<li><a href="#">建议反馈</a></li>
								<li><a href="#">关于我们</a></li>
								<li><a href="#">联系我们</a></li>
								<li><a id="mianzeurl" onclick="javascript:showMianze()">免责声明</a></li>
							</ul>
					</div>
					<div style="float:right;width:100%;">	
						<div id="erweima" ><img src="images/erweima.png" /><p style="float:right;color:#336600;margin-top:-3px;margin-right:20px;">扫码有惊喜 </p></div>
					</div>
				</div>
			</div> <!-- end of header -->
			
			<div id="content">
				<div id="content-left">
					<div id="left-header">
							<div class="guide">
								<a href="<%=basePath%>">一元凑单</a>
							</div>
							<div class="guidesel">
								<a href="">听歌</a>
							</div>
					</div>
					<div id="content-border">
						<div id="content-content">
							<div id="filter">
								<div>
									<div style="width:50%;margin:5px auto;">
										<input type="search" placeholder="     歌曲/歌手    " required="required" name="keyword"/>
										<div class="searchbtn">搜索</div>
									</div>
								</div>
							</div>
							<div id="musiclist">
									<div class="th">
										<span>歌曲</span>
										<span>歌手</span>
										<span>专辑</span>
										<span>操作</span>
									</div>
									<s:iterator id="music" value="%{musics}" status="status">
										<s:if test="#status.getIndex()<10">
											<s:if test="#status.isOdd()">
												<div class="list1">
													<span>
														<div>
															<img class="albumImg" src="<s:property value="albumImg" />" />
															<a class="infoUrl" target="_blank" href="<s:property value="infoUrl" />" title="<s:property value="songName" />"><s:property value="songName" /></a>
														</div>
													</span>
													<span class="singer"><s:property value="singer" /></span>
													<span class="albumName" title="<s:property value="albumName" />"><s:property value="albumName" /></span>
													<span>
														<a class="playbtn" title="播放" index="<s:property value="#status.index" />"></a>
														<a class="addbtn" title="下一首播放" index="<s:property value="#status.index" />"></a>
													</span>
												</div>
											</s:if>
											<s:else>
												<div class="list2">
													<span>
														<div>
															<img class="albumImg" src="<s:property value="albumImg" />" />
															<a class="infoUrl" target="_blank" href="<s:property value="infoUrl" />" title="<s:property value="songName" />"><s:property value="songName" /></a>
														</div>
													</span>
													<span class="singer"><s:property value="singer" /></span>
													<span class="albumName" title="<s:property value="albumName" />"><s:property value="albumName" /></span>
													<span>
														<a class="playbtn" title="播放" index="<s:property value="#status.index" />"></a>
														<a class="addbtn" title="下一首播放" index="<s:property value="#status.index" />"></a>
													</span>
												</div>
											</s:else>
										</s:if>
									</s:iterator>
									<div style="overflow:hidden;">
										<div class="page">
											<span id="firstpage" class="none" title="首页">首页</span>
											<span id="previouspage" class="none" title="上一页">上页</span>
											<s:if test="musics.size<11">
												<span id="nextpage" class="none" title="下一页" >下页</span>
												<span id="lastpage" class="none" title="尾页">尾页</span>
											</s:if>
											<s:else>
												<span id="nextpage" class="pagebtn" title="下一页" >下页</span>
												<span id="lastpage" class="pagebtn" title="尾页">尾页</span>
											</s:else>
										</div>
									</div>
								</div>
						</div>
					</div>
				</div> <!-- end of content-left -->
				<div id="content-right">
					<div id="player">
						<div class="ctrl">
							<div class="tag">
								<strong>Title</strong>
								<span class="artist">Artist</span>
								<span class="album">Album</span>
							</div>
							<div class="control">
								<div class="left">
									<div class="rewind icon"></div>
									<div class="playback icon"></div>
									<div class="fastforward icon"></div>
								</div>
								<div class="volume right">
									<div class="mute icon left"></div>
									<div class="slider left">
										<div class="pace"></div>
									</div>
								</div>
							</div>
							<div class="progress">
								<div class="slider">
									<div class="loaded"></div>
									<div class="pace"></div>
								</div>
								<div class="timer left">0:00</div>
								<div class="right">
									<div class="repeat icon"></div>
									<div class="shuffle icon"></div>
								</div>
							</div>
						</div>
						<ul id="playlist">
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
						</ul>
					</div>
				</div>	<!-- end of content-right -->
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
		<script src="js/jquery.js"></script>
		<script src="js/jquery-ui-1.8.17.custom.min.js"></script>
		<script src="js/music.js"></script>
	</body>
</html>