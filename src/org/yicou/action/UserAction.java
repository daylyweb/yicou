package org.yicou.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.yicou.beans.UserBean;
import org.yicou.service.UserService;
import org.yicou.util.AesUtil;
import org.yicou.util.MailUtil;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	HttpServletRequest request = ServletActionContext.getRequest();
	String path = request.getContextPath();
	String basePath  = request.getScheme()+"://"+request.getServerName()+path+"/";
	HttpServletResponse response = ServletActionContext.getResponse();
	final String mailcontent="<h2>user，欢迎您注册一凑网，请点击以下链接完成注册激活！</h2>\n<div style='margin:0 auto;width:80%'>\n<strong><a href='regurl' style='display:block;margin:0 auto;width:50%'>点此激活</a></strong>\n<h3>如以上链接无法打开请复制以下链接到浏览器打开：regurl</h3>\n</div>\n<a href='"+basePath+"' style='display:block;color:red;font-size:16px;float:right;margin-right:60px;'>@一凑网</a>";
	private MailUtil mailutil;
	private UserService userService;
	private UserBean loginuser;
	private String username;
	private String password;
	private String email;
	private String key;
	private String msg;
	public String login()
	{
		UserBean loginuser;
		if(username==null || password==null){return "index";}
		loginuser = userService.queryUserBySql("select * from user where username='"+username+"' and password='"+password+"'");
		if (loginuser!=null){
			request.getSession().setAttribute("loginuser", loginuser);
			Cookie cookie = new Cookie("loginuser",AesUtil.encrypt(loginuser.getUsername()+","+loginuser.getPassword()));
			cookie.setMaxAge(86400);
			response.addCookie(cookie);
			return SUCCESS;
		}
		loginuser = userService.queryUserBySql("select * from user where mail='"+username+"' and password='"+password+"'");
		if (loginuser!=null){
			request.getSession().setAttribute("loginuser", loginuser);
			request.getSession().setAttribute("loginuser", loginuser);
			Cookie cookie = new Cookie("loginuser",AesUtil.encrypt(loginuser.getUsername()+","+loginuser.getPassword()));
			cookie.setMaxAge(86400);
			response.addCookie(cookie);
			return SUCCESS;
		}
		return ERROR;
	}
	
	public String regist()
	{
		if(username==null || password==null || email==null){return "index";}
		String key=username+","+password+","+email+","+new Date().getTime();  //用户名,密码,邮箱,时间戳
		String regurl = basePath+"user/mailvali?key="+AesUtil.encrypt(key);
		String temp=mailcontent.replaceAll("regurl", regurl);
		temp=temp.replaceFirst("user", username);
		if(this.sendRegMail(email, "一凑网用户邮箱验证",temp)) return SUCCESS;
		return ERROR;
	}
	public void userVali()
	{
		JSONObject jo= new JSONObject();
		if(username==null) return;
		boolean flag1 = userService.isExistByEmail(username);
		boolean flag2 = userService.isExistByUserName(username);
		jo.put("username", flag2);
		jo.put("email", flag1);
		
		try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	
	public String mailVali()
	{
		String keystr = AesUtil.decrypt(key);  
		String[] value  = keystr.split(",", 4);  //用户名,密码,邮箱,时间戳
		if(new Date().getTime()-new Date(Long.valueOf(value[3])*1000L).getTime()>600000)
		{
			this.msg="验证链接已过期！";
			return ERROR;
		}
		else if(userService.isExistByUserName(value[0]) || userService.isExistByEmail(value[2]))  
		{
			this.msg="用户已通过验证！无需重复验证！";
			return ERROR;
		}
		else
		{
			if(userService.insert(new UserBean(value[0],value[1],"",value[2],new java.sql.Date(new Date().getTime()),value[0],"")))
			{
				return SUCCESS;
			}
			else
			{
				this.msg="用户注册失败！请联系我们！";
				return ERROR;
			}
			
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setMailutil(MailUtil mailutil) {
		this.mailutil = mailutil;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public UserBean getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(UserBean loginuser) {
		this.loginuser = loginuser;
	}
	public boolean sendRegMail(String to,String mailtitle,String mailcontent)
	{
		boolean flag=false;
		try {
			mailutil.init();
			mailutil.getSession();
			mailutil.sendMail(mailtitle, mailcontent, to, username);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
