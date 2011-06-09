package at.ac.tuwien.swag.messages;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

public class JMSHelper implements Serializable {
	private static final long serialVersionUID = 4059612595902480317L;

	private final Connection      connection;
	private final Session         session;
	
	public JMSHelper( Connection connection ) throws JMSException {
		this.connection = connection;
		this.session    = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
		
		connection.start();
	}

	public <Response> Response request( Destination dest, String msg ) throws JMSException {
		return request( dest, makeMessage( msg ) );
	}
	
	public <Response> Response request( Destination dest, Serializable msg ) throws JMSException {
		return request( dest, makeMessage( msg ) );
	}

	public <Response> Response request( Destination dest, String msg, long timeout ) throws JMSException {
		return request( dest, makeMessage( msg ), timeout );
	}
	
	public <Response> Response request( Destination dest, Serializable msg, long timeout ) throws JMSException {
		return request( dest, makeMessage( msg ), timeout );
	}
	
	public void send( Destination dest, String msg ) throws JMSException {
		send( dest, null, makeMessage( msg ) );
	}
	
	public void send( Destination dest, Serializable msg ) throws JMSException {
		send( dest, null, makeMessage( msg ) );
	}

	public Message receive( Destination dest ) throws JMSException {
		MessageConsumer consumer = session.createConsumer( dest );
		
		Message msg = consumer.receive();
		
		consumer.close();
		
		return msg;
	}
	
	public Message receive( Destination dest, long timeout ) throws JMSException {
		MessageConsumer consumer = session.createConsumer( dest );
		
		Message msg = consumer.receive( timeout );
		
		consumer.close();
		
		return msg;
	}
	
	public Message receiveNoWait( Destination dest ) throws JMSException {
		MessageConsumer consumer = session.createConsumer( dest );
		
		Message msg = consumer.receiveNoWait();
		
		consumer.close();
		
		return msg;
	}
	
	public void close() {
		try {
			connection.close();
		} catch ( JMSException e ) {
			e.printStackTrace();
		}
	}
	
	//***** PRIVATE PARTS
	
	private <Response> Response request( Destination dest, Message msg ) throws JMSException {
		TemporaryQueue replyTo = session.createTemporaryQueue();
		
		send( dest, replyTo, msg );

		Message reply = receive( replyTo );
		
		Response response = getResponse( reply );
		
		replyTo.delete();
		
		return response;
	}
	private <Response> Response request( Destination dest, Message msg, long timeout ) throws JMSException {
		TemporaryQueue replyTo = session.createTemporaryQueue();
		
		send( dest, replyTo, msg );

		Message reply = receive( replyTo, timeout );
		
		Response response = getResponse( reply );
		
		replyTo.delete();
		
		return response;
	}
	
	private void send( Destination dest, Destination replyTo, Message msg ) throws JMSException {
		MessageProducer producer = session.createProducer( dest );
		
		msg.setJMSReplyTo( replyTo );
		
		producer.send( msg );
		
		producer.close();
	}
	
	@SuppressWarnings("unchecked")
	private <Response> Response getResponse( Message msg ) throws JMSException {
		if ( msg instanceof ObjectMessage ) {
			return (Response) ((ObjectMessage) msg).getObject();
		} else if ( msg instanceof TextMessage ){
			return (Response) ((TextMessage) msg).getText();
		} else {
			return (Response) msg;
		}
	}
	
	private Message makeMessage( String txt ) throws JMSException {
		return session.createTextMessage( txt );
	}
	private Message makeMessage( Serializable s ) throws JMSException {
		return session.createObjectMessage( s );
	}
	
}
