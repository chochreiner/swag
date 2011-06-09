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

	public <T> T request( Destination dest, Class<T> c, String msg ) throws JMSException {
		return doRequest( dest, c, makeMessage( msg ) );
	}
	
	public <T> T request( Destination dest, Class<T> c, Serializable msg ) throws JMSException {
		return doRequest( dest, c, makeMessage( msg ) );
	}

	public <T> T request( Destination dest, Class<T> c, String msg, long timeout )
	  throws JMSException, TimeoutExpiredException {
		return doRequest( dest, c, makeMessage( msg ), timeout );
	}
	
	public <T> T request( Destination dest, Class<T> c, Serializable msg, long timeout ) 
	  throws JMSException, TimeoutExpiredException {
		return doRequest( dest, c, makeMessage( msg ), timeout );
	}
	
	public void send( Destination dest, String msg ) throws JMSException {
		doSend( dest, null, makeMessage( msg ) );
	}
	
	public void send( Destination dest, Serializable msg ) throws JMSException {
		doSend( dest, null, makeMessage( msg ) );
	}

	public Message receive( Destination dest ) throws JMSException {
		MessageConsumer consumer = session.createConsumer( dest );
		
		Message msg = consumer.receive();
		
		consumer.close();
		
		return msg;
	}
	
	public Message receive( Destination dest, long timeout ) throws JMSException, TimeoutExpiredException {
		MessageConsumer consumer = session.createConsumer( dest );
		
		Message msg = consumer.receive( timeout );
		
		consumer.close();
		
		if ( msg == null ) throw new TimeoutExpiredException();
		
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
	
	private <T> T doRequest( Destination dest, Class<T> c, Message msg ) throws JMSException {
		TemporaryQueue replyTo = session.createTemporaryQueue();
		
		doSend( dest, replyTo, msg );

		Message reply = receive( replyTo );
		
		T response = getResponse( reply, c );
		
		replyTo.delete();
		
		return response;
	}
	private <T> T doRequest( Destination dest, Class<T> c, Message msg, long timeout ) 
	  throws JMSException, TimeoutExpiredException {
		TemporaryQueue replyTo = session.createTemporaryQueue();
		
		doSend( dest, replyTo, msg );

		Message reply = receive( replyTo, timeout );
		
		T response = getResponse( reply, c );
		
		replyTo.delete();
		
		return response;
	}
	
	private void doSend( Destination dest, Destination replyTo, Message msg ) throws JMSException {
		MessageProducer producer = session.createProducer( dest );
		
		msg.setJMSReplyTo( replyTo );
		
		producer.send( msg );
		
		producer.close();
	}
	
	private <Response> Response getResponse( Message msg, Class<Response> c ) throws JMSException {
		if ( c.isAssignableFrom( msg.getClass() ) ) {
			return c.cast( msg );
		}
		
		if ( msg instanceof ObjectMessage ) {
			return c.cast( ((ObjectMessage) msg).getObject() );
		} 
		
		if ( msg instanceof TextMessage ){
			return c.cast( ((TextMessage) msg).getText() );
		}
		
		return null;
	}
	
	private Message makeMessage( String txt ) throws JMSException {
		return session.createTextMessage( txt );
	}
	private Message makeMessage( Serializable s ) throws JMSException {
		return session.createObjectMessage( s );
	}
	
}
