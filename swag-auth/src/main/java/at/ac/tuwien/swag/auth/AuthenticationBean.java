package at.ac.tuwien.swag.auth;

import java.util.Enumeration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

@MessageDriven( mappedName="swag.queue.Authentication" )
public class AuthenticationBean implements MessageListener {

	@PostConstruct
	public void initialize() throws JMSException {
		connection = connectionFactory.createConnection();
		session    = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );

		connection.start();
	}
	
	@Override
	public void onMessage( Message msg ) {
		try {
			Destination origin = msg.getJMSReplyTo();

			session.createProducer( origin ).send(
				session.createTextMessage( "Hi, Authentication service speaking. It was nice to hear from you" ) 
			);
		} catch ( JMSException e ) {
			e.printStackTrace();
		}
	}

	@Resource(mappedName="swag.JMSPool")
	private ConnectionFactory connectionFactory;
	
	private Connection connection;
	private Session    session;
	
}
