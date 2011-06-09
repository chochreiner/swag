package at.ac.tuwien.swag.auth;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

public abstract class MessageHandler {
	
	private Session     session;
	private Destination replyTo;
	
	protected void handleMessage( Session session, Destination replyTo, Object msg ) throws JMSException {
		try {
			Method m = getClass().getMethod( "handle", msg.getClass() );
			
			this.session = session;
			this.replyTo = replyTo;
			
			m.invoke( this, msg );
		} catch ( SecurityException e ) {
		} catch ( NoSuchMethodException e ) {
		} catch ( IllegalArgumentException e ) {
		} catch ( IllegalAccessException e ) {
		} catch ( InvocationTargetException e ) {
		}
	}
	
	protected void reply( String msg ) throws JMSException {
		reply( session.createTextMessage( msg ) );
	}

	protected void reply( Serializable msg ) throws JMSException {
		reply( session.createObjectMessage( msg ) );
	}
	
	protected void reply( Message msg ) throws JMSException {
		MessageProducer producer = session.createProducer( replyTo );

		producer.send( msg );
		
		producer.close();		
	}
	
	protected Session     session() { return session; }
	protected Destination replyTo() { return replyTo; }
}
