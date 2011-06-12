package at.ac.tuwien.swag.mail;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Singleton
public class MailServiceImpl implements MailService {

//	private static final String   SMTP_HOST_NAME   = "localhost"; //"smtp.gmail.com";
//	private static final String   SMTP_PORT        = "2525"; //"465";
//	private static final String   emailMsgTxt      = "Test Message Contents";
//	private static final String   emailSubjectTxt  = "A test from gmail";
//	private static final String   emailFromAddress = "";
//	private static final String   SSL_FACTORY      = "javax.net.ssl.SSLSocketFactory";
//	private static final String[] sendTo           = { "" };

    @Resource(name="mail/session")
    private Session session;
    
    @EJB
    private FakeMailServerBean mail;
    
	@Override
	public void sendMessage(String senderAddress, String recipientAddress, String subject, String messageText) {
		mail.ping();

		try {			
			Message msg = new MimeMessage(session);
			
			msg.setFrom( new InternetAddress(senderAddress) );
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress) );
			msg.setSubject(subject);
			
			msg.setContent(messageText, "text/plain");
			
			Transport.send(msg);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
