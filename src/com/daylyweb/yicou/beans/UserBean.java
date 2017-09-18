package com.daylyweb.yicou.beans;

import java.sql.Date;

public class UserBean {
	private int uid;
	private String username;
	private String password;
	private String phone;
	private String mail;
	private Date birthday;
	private String nickname;
	private String headerimg;
	
	public UserBean(){}
	public UserBean(String username,String password,String phone,String mail,Date birthday,String nickname,String headerimg)
	{
		this.username=username;
		this.password=password;
		this.phone=phone;
		this.mail=mail;
		this.birthday=birthday;
		this.nickname=nickname;
		this.headerimg=headerimg;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeaderimg() {
		return headerimg;
	}
	public void setHeaderimg(String headerimg) {
		this.headerimg = headerimg;
	}
}
