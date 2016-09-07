/**
 * 2016.09.01
 * Shirp
 * 发送邮件
 * 需要使用ConfigManager类从资源文件读取部分参数
 * */
package com.sl.java.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public class MailUtil {

		/**
		 * @param fromTitle
		 *            邮件标题
		 * @param mailFrom
		 *            发送邮件的邮箱
		 * @param mailTo
		 *            接收邮件的邮箱
		 * @param strText
		 *            邮件内容
		 */
		public static void send(String fromTitle, String mailFrom, String mailTo, final String strText) {
			try {
				// 发件人使用发邮件的电子信箱服务器
				String host = ConfigManager.getConfigData("MAILHOST");

				// 创建 properties ，里面包含了发送邮件服务器的地址。
				Properties props = new Properties();
				// 发送邮件服务器的地址
				props.put("mail.smtp.host", host);
				// 通过验证 默认为false
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.localhost", "localhost");
				// 校验发信人权限
				MyAuthenticator myauth = new MyAuthenticator(ConfigManager.getConfigData("SENDMAIL"), ConfigManager.getConfigData("MAILPASS"));
				// 创建 session
				Session session = Session.getDefaultInstance(props, myauth);
				// session.setDebug(true);//打开调试
				// 创建 邮件的message，message对象包含了邮件众多有的部件，都是封装成了set方法去设置的
				MimeMessage message = new MimeMessage(session);
				// 设置发信人
				message.setFrom(new InternetAddress(mailFrom));
				message.setDataHandler(new DataHandler(new ByteArrayDataSource(strText.toString(), "text/html")));
				// 收信人
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
				// 邮件标题
				message.setSubject(fromTitle);
				// 邮件内容
				// message.setText(strText);
				// 保存以上工作
				message.saveChanges();

				// 发送邮件
				Transport.send(message);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("邮件发送失败");
			}
		}

		public static void main(String[] args) {
			StringBuffer url = new StringBuffer(ConfigManager.getConfigData("ACTIVATION_URL") + "confrimAccount.action");
			String company=ConfigManager.getConfigData("COMPANY");
			String sendMail=ConfigManager.getConfigData("SENDMAIL");
			try {
				StringBuilder mailMessage = new StringBuilder();
				mailMessage.append("<html><head>"+company+"激活邮件</head><body><br />尊敬的用户，您好！<br /><br />欢迎注册"+company+"平台,请激活账户以完成注册；<br /><br />");
				mailMessage.append("<a href='" + url + "'>" + url + "</a>");
				mailMessage.append("如有任何问题，欢迎查阅"+company+"平台<a href='"+"http://a.youkapos.com/"+"'>常见问题</a>或直接与我们联系，邮件地址：<br /><a href='mailto:"+sendMail+"'>"+sendMail+";</a>");
				mailMessage.append("<br />期待与您的合作，谨此致意！<br /><br />"+company+" 服务团队");
				mailMessage.append("</body></html>");
				send(company+" 注册激活邮件", sendMail, "429379083@qq.com", mailMessage.toString());
			} catch (Exception e) {
				System.out.println("发送邮件时发生异常");
				e.printStackTrace();
			}
		}
	}


class MyAuthenticator extends Authenticator {
	String userName = "";
	String password = "";

	public MyAuthenticator() {

	}

	public MyAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
