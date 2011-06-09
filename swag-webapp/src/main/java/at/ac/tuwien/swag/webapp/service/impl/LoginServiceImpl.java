package at.ac.tuwien.swag.webapp.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Queue;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.messages.auth.AuthenticationReply;
import at.ac.tuwien.swag.messages.auth.AuthenticationRequest;
import at.ac.tuwien.swag.webapp.service.AuthenticationException;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginServiceImpl implements LoginService {

	@Inject
	@Named("MESSAGE_TIMEOUT")
	private long timeout;
	@Inject
	private JMSHelper jms;
	@Inject
	@Named("swag.queue.Authentication")
	private Queue   authentication;
	
    @Override
    public AuthenticationReply authenticate(String username, String password) throws AuthenticationException,
                                                                                     TimeoutExpiredException,
                                                                                     JMSException            {
   		AuthenticationReply auth = jms.request( authentication, 
   		                                        new AuthenticationRequest( username, password ), 
   		                                        timeout );
    		
   		// request denied
   		if ( auth.token == null || auth.roles == null || auth.roles.length == 0 ) 
   			throw new AuthenticationException("Username or password are incorrect");	
    		
   		return auth;
    }

    @Override
    public Set<String> getRoles( String username ) {
        return new HashSet<String>(Arrays.asList(Roles.ADMIN, Roles.USER));
    }
    
}
