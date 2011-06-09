package at.ac.tuwien.swag.webapp.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.NoResultException;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import at.ac.tuwien.swag.messages.AuthenticationReply;
import at.ac.tuwien.swag.messages.AuthenticationRequest;
import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.util.PasswordHasher;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginServiceImpl implements LoginService {

	@Inject
	private UserDAO users;
	
//	@Inject
//	private JMSHelper jms;
//	@Inject
//	private ConnectionFactory factory;
//	@Inject
//	@Named("swag.queue.Authentication")
//	private Queue   authentication;
	
	@Inject
	private PasswordHasher hasher;
	
	public LoginServiceImpl() {
		
	}
	
    @Override
    public boolean authenticate(String username, String password) {
//    	try {
//    		Connection connection = factory.createConnection();
//    		Session    session    = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
//    		
//    		MessageProducer producer = session.createProducer( authentication );
//    		
//    		ObjectMessage o = session.createObjectMessage();
//    		o.setObject( new AuthenticationRequest( username, password ) );
//    		
//    		producer.send( o, producer.getDeliveryMode(), producer.getPriority(), 2000 );
//    		
//    		MessageConsumer consumer = session.createConsumer( authentication );
//    		
//    		Message m = consumer.receive( 2000 );
//    		
//    		System.err.println( m );
//		} catch ( JMSException e ) {
//			e.printStackTrace();
//		}
    	
    	try {
    		User user = users.findByUsername( username );

    		return hasher.checkPassword( password, user.getPassword() );
//    		return true;
    	} catch ( NoResultException e ) {
    		return false;
    	}
    }

    @Override
    public Set<String> getRoles(String username) {
        return new HashSet<String>(Arrays.asList(Roles.ADMIN, Roles.USER));
    }
    
}
