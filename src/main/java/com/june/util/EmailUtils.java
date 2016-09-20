package com.june.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * 简单的邮件发送 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年9月7日 下午6:33:34
 */
public class EmailUtils {

//	public static void main(String[] args) throws Exception {
//		Properties prop = new Properties();
//		InputStream in = SpringTaskController.class.getResourceAsStream("/config.properties");
//		prop.load(in);
//		EmailUtils.sendMail(prop.getProperty("fromEmail"), prop.getProperty("toEmail"), prop.getProperty("emailName"),prop.getProperty("emailPassword"), "猪蹄", "内容");
//	}

	private static final String PROTOCOL = "smtp";
	private static final String PROTOCOL_URL = "smtp.163.com";
	
	/**
	 * 发送邮件 (暂时只支持163邮箱发送)
	 * 
	 * @param fromEmail
	 *            发送邮箱
	 * @param toEmail
	 *            接收邮箱
	 * @param emailName
	 *            163邮箱登录名
	 * @param emailPassword
	 *            密码,注意密钥是163邮箱注册后设置好的授权码，并不一定是邮箱的密码
	 * @param title
	 *            发送主题
	 * @param centent
	 *            发送内容
	 * @throws Exception
	 */
	public static void sendMail(String fromEmail, String toEmail, String emailName, String emailPassword, String title,
			String centent) throws Exception {

		Properties properties = new Properties();// 创建Properties对象
		properties.setProperty("mail.transport.protocol", PROTOCOL);// 设置传输协议
		properties.put("mail.smtp.host", PROTOCOL_URL);// 设置发信邮箱的smtp地址
		properties.setProperty("mail.smtp.auth", "true"); // 验证
		Authenticator auth = new AjavaAuthenticator(emailName, emailPassword); // 使用验证，创建一个Authenticator
		Session session = Session.getDefaultInstance(properties, auth);// 根据Properties，Authenticator创建Session
		//Session session = Session.getInstance(properties);
		Message message = new MimeMessage(session);// Message存储发送的电子邮件信息
		message.setFrom(new InternetAddress(fromEmail));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));// 设置收信邮箱
		// 指定邮箱内容及ContentType和编码方式
		message.setContent(centent, "text/html;charset=utf-8");
		message.setSubject(title);// 设置主题
		message.setSentDate(new Date());// 设置发信时间
		message.saveChanges();//存储邮件信息 
		Transport.send(message);// 发送
//		Transport transport = session.getTransport();  
//        transport.connect(emailName, emailPassword);  
//        transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址  
//        transport.close();  
	}
}

// 创建传入身份验证信息的 Authenticator类
class AjavaAuthenticator extends Authenticator {
	private String user;
	private String pwd;

	public AjavaAuthenticator(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pwd);
	}
	
}