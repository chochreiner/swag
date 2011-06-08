package at.ac.tuwien.swag.mail;

import java.util.Properties;
import javax.annotation.Resource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailServiceImpl implements MailService {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_PORT = "465";
	private static final String emailMsgTxt = "Test Message Contents";
	private static final String emailSubjectTxt = "A test from gmail";
	private static final String emailFromAddress = "";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static final String[] sendTo = { "" };

    @Resource(name="mail/session")
    private Session session;
    
	@Override
	public void sendMessage(String senderAddress, String recipientAddress, String subject,
			String messageText) throws MessagingException {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", SMTP_HOST_NAME);
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.debug", "true");
//		props.put("mail.smtp.port", SMTP_PORT);
//		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
//		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
//		props.put("mail.smtp.socketFactory.fallback", "false");
//        
//		Session session = Session.getDefaultInstance(props,
//				new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(
//								"", "");
//					}
//				});
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(senderAddress);
		msg.setFrom(addressFrom);
		InternetAddress addressTo = new InternetAddress(recipientAddress);
		msg.setRecipient(Message.RecipientType.TO, addressTo);
		msg.setSubject(subject);
		msg.setContent(messageText, "text/plain");
		Transport.send(msg);
	}
}
