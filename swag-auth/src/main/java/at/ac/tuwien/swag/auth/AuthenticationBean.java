package at.ac.tuwien.swag.auth;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.persistence.NoResultException;

import at.ac.tuwien.swag.messages.auth.AuthenticationReply;
import at.ac.tuwien.swag.messages.auth.AuthenticationRequest;
import at.ac.tuwien.swag.messages.auth.StoreUserRequest;
import at.ac.tuwien.swag.messages.auth.UserExistsRequest;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.util.MessageHandler;
import at.ac.tuwien.swag.util.PasswordHasher;
import at.ac.tuwien.swag.util.PersistenceBean;

@MessageDriven( mappedName="swag.queue.Authentication" )
public class AuthenticationBean extends MessageHandler {

	@PostConstruct
	public void initialize() throws JMSException {
		users  = new UserDAO( persistence.makeEntityManager() );
		hasher = new PasswordHasher();

		super.initialize( connectionFactory );
	}
	
	public void handle( String msg ) throws JMSException {
		reply( "Hi, Authentication service speaking. It was nice to hear from you" );
	}
	public void handle( AuthenticationRequest msg ) throws JMSException {
		ensureAdminIsPresent();
		
		String username = msg.username;
		String password = msg.password;
		String token    = "DUMMY TOKEN";

		AuthenticationReply reply = new AuthenticationReply( username, null, null );
		
		try {
			String storedHash  = users.findByUsername( username ).getPassword();
						
			if ( hasher.checkPassword( password, storedHash ) ) {
				if ( "system".equals( username ) ) {
					reply.roles = new String[] {"ADMIN", "USER"};
				} else {
					reply.roles = new String[] {"USER"};
				}
				reply.token = token;
			}
		} catch ( NoResultException e ) {
		} catch ( Throwable t ) {
		}
		
		reply( reply );
	}
	public void handle( UserExistsRequest msg ) throws JMSException {
		try {
			users.findByUsername( msg.username );

			reply( Boolean.TRUE );
		} catch ( NoResultException e ) {
			reply( Boolean.FALSE );
		}
	}
	public void handle( StoreUserRequest msg ) throws JMSException {
		User u = new User(
			msg.user.getUsername(),
			hasher.hash( msg.user.getPassword() ),
			msg.user.getAddress(),
			msg.user.getEmail(),
			msg.user.getFullname(),
			null,
			null
		);
		
		users.beginTransaction();
			users.insert( u );
		users.commitTransaction();
		
		reply( Boolean.TRUE );		
	}
	
	//**** PRIVATE PARTS
	
	private void ensureAdminIsPresent() {
		try {
			users.findByUsername( "system" );
		} catch ( NoResultException e ) {
			User system = new User(
				"system",
				"aaa", 
				"The interblag",
				"swag@swag.com", 
				"System administration account", 
				null ,null, null 
			);

			users.beginTransaction();
				users.insert( system );
			users.commitTransaction();				
		}
	}
	
	@EJB
	private PersistenceBean persistence;
	
	@Resource(mappedName="swag.JMS")
	private ConnectionFactory connectionFactory;
	
	private UserDAO        users;
	private PasswordHasher hasher;
}
