package org.yicou.util;

import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.yicou.util.MyAuthenticator;

public class MailUtil {
	
	private String username;
	private String password;
	private String smtphost;
	private String smtpport;
	private String isssl;
	private String sendername;
	Properties prop = new Properties();
	Session session;
	public MailUtil(){}
	
	
	public void init() throws Exception
	{
			prop.load(new InputStreamReader(MailUtil.class.getResourceAsStream("mail.properties"),"UTF-8"));
			
			this.smtphost = prop.getProperty("smtphost", "smtp.163.com");
			this.smtpport = prop.getProperty("smtpport","465");
			this.username = prop.getProperty("username", "18344103036@163.com");
			this.password = prop.getProperty("password", "qwead520");
			this.isssl = prop.getProperty("isssl", "true");
			this.sendername = prop.getProperty("sendername", "一凑网");
			prop.setProperty("mail.smtp.host", smtphost);
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.auth","true");
			prop.setProperty("mail.smtp.ssl.enable", isssl);
			prop.setProperty("mail.smtp.port",smtpport);
	}
	
	public void getSession() throws Exception
	{
		this.session = Session.getInstance(prop, new MyAuthenticator(username,password));
		this.session.setDebug(true);
	}
	public boolean sendMail(String mailtitle,String mailContent,String receiver,String receivername) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username,sendername,"UTF-8"));
		message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiver,receivername,"UTF-8"));
		message.setContent(mailContent, "text/html;charset=utf-8");
		message.setSubject(mailtitle, "UTF-8");
		message.saveChanges();
		session.getTransport();
		Transport ts = session.getTransport();
		ts.connect();
		ts.sendMessage(message,message.getAllRecipients());
		ts.close();
		return true;
	}
	
	/*public static void main(String[] arg0)
	{
		try {
			 MailUtil ml = new MailUtil();
			 ml.init();
			 ml.getSession();
			 ml.sendMail("小只邮件测试", "<div style='margin:0 auto;width:50%'>\n<h2>admin，欢迎您注册一凑网，请点击以下链接完成注册激活！</h2>\n<strong><a href='http://www.baidu.com' style='display:block;margin:0 auto;width:50%'>点此激活</a></strong>\n<h3>如以上链接无法打开请复制以下链接到浏览器打开：www.baidu.com</h3>\n<a href='http://123.207.161.170' style='display:block;color:red;font-size:16px;float:right;margin-right:60px;'>@一凑网</a>\n</div>", "925673945@qq.com", "尊敬的用户");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}


class MyAuthenticator extends Authenticator{  
    String userName="";  
    String password="";  
    public MyAuthenticator(){  
          
    }  
    public MyAuthenticator(String userName,String password){  
        this.userName=userName;  
        this.password=password;  
    }  
     protected PasswordAuthentication getPasswordAuthentication(){     
        return new PasswordAuthentication(userName, password);     
     }   
}