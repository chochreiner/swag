package at.ac.tuwien.swag.messages;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

public class JMSHelper implements Serializable {
	private static final long serialVersionUID = 4059612595902480317L;

	private final Session         session;
//	private final MessageConsumer consumer;
//	private final MessageProducer producer;
	
	public JMSHelper( Session session ) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public <Response> Response request( Destination dest, Serializable msg ) throws JMSException {
		send( dest, msg );
	
		return (Response) ((ObjectMessage) receive( dest )).getObject();
	}
	
	public void send( Destination dest, Serializable msg ) throws JMSException {
		MessageProducer producer = session.createProducer( dest );
		
		ObjectMessage o = session.createObjectMessage();
		o.setObject( msg );
		
		producer.send( o, producer.getDeliveryMode(), producer.getPriority(), 3000 );
	}

	public Message receive( Destination dest ) throws JMSException {
		MessageConsumer consumer = session.createConsumer( dest );
		
		return consumer.receive();
	}
	
}
