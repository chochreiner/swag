package at.ac.tuwien.swag.webapp.service.impl;

import javax.jms.JMSException;
import javax.jms.Queue;
import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.messages.auth.AuthenticationReply;
import at.ac.tuwien.swag.messages.auth.AuthenticationRequest;
import at.ac.tuwien.swag.messages.auth.StoreUserRequest;
import at.ac.tuwien.swag.messages.auth.UserExistsRequest;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.model.dto.UserDTO;
import at.ac.tuwien.swag.webapp.service.AuthenticationException;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginServiceImpl implements LoginService {

	@Inject
	@Named("MESSAGE_TIMEOUT")
	private long      timeout;
	@Inject
	private JMSHelper jms;
	@Inject
	@Named("swag.queue.Authentication")
	private Queue     authentication;
	
    @Override
    public AuthenticationReply authenticate(String username, String password) throws AuthenticationException,
                                                                                     TimeoutExpiredException,
                                                                                     JMSException            {
//    	boolean b = jms.request( authentication, Boolean.class, new UserExistsRequest( username ), timeout );
//
//    	AuthenticationReply auth = jms.request( authentication, 
//   		                                        AuthenticationReply.class,
//   		                                        new AuthenticationRequest( username, password ),
//   		                                        timeout );
//    		
//   		
//   		// request denied
//   		if ( !b || auth.token == null || auth.roles == null || auth.roles.length == 0 ) 
//   			throw new AuthenticationException("Username or password are incorrect" );	
//    		
//   		return auth;
    	
    	return new AuthenticationReply( username, new String[] {"ADMIN", "USER"}, "TOKEN" );
    }

    public boolean userExists( String username ) throws JMSException, TimeoutExpiredException {
		return jms.request( authentication, Boolean.class, new UserExistsRequest( username ), timeout );
    }
    
	@Override
	public void storeUser( User user ) throws JMSException, TimeoutExpiredException {
		UserDTO dto = new UserDTO( 
			user.getUsername(), 
			user.getPassword(), 
			user.getAddress(),
			user.getEmail(), 
			user.getFullname(), 
			null, null, null ); 
		
		jms.request( authentication, Boolean.class, new StoreUserRequest( dto ), timeout );
	}
    
}
