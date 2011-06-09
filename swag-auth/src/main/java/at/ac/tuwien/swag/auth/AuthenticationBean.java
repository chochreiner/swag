package at.ac.tuwien.swag.auth;

import java.io.Serializable;

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
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import at.ac.tuwien.swag.messages.AuthenticationReply;
import at.ac.tuwien.swag.messages.AuthenticationRequest;

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
			if ( msg instanceof ObjectMessage ) {
				handleObjectMessage( (ObjectMessage) msg );
			} else if ( msg instanceof TextMessage ) {
				handleTextMessage( (TextMessage) msg );
			} else {
				// ignore all other messages
			}
		} catch ( JMSException e ) {
			e.printStackTrace();
		}
	}

	private void handleAuthenticationRequest( AuthenticationRequest request, Destination replyTo ) throws JMSException {
		String username = request.username;
//		String password = request.password;
		String token    = "";

		reply( replyTo, new AuthenticationReply( username, token ) );
	}
	
	private void handleTextMessage( TextMessage msg ) throws JMSException {
		reply( msg.getJMSReplyTo(), "Hi, Authentication service speaking. It was nice to hear from you" );
	}

	private void handleObjectMessage( ObjectMessage msg ) throws JMSException {
		Object      o       = msg.getObject();
		Destination replyTo = msg.getJMSReplyTo();
		
		if ( o instanceof AuthenticationRequest ) {
			handleAuthenticationRequest( (AuthenticationRequest) o, replyTo );
		} else {
			reply( replyTo, "Unknown request type" );
		}
	}
	
	private void reply( Destination replyTo, Message msg ) throws JMSException {
		MessageProducer producer = session.createProducer( replyTo );

		producer.send( msg );
		
		producer.close();
	}

	private void reply( Destination replyTo, String msg ) throws JMSException {
		reply( replyTo,  session.createTextMessage( msg ) );
	}

	private void reply( Destination replyTo, Serializable msg ) throws JMSException {
		reply( replyTo, session.createObjectMessage( msg ) );
	}
	
	@Resource(mappedName="swag.JMS")
	private ConnectionFactory connectionFactory;
	
	private Connection connection;
	private Session    session;
	
}
