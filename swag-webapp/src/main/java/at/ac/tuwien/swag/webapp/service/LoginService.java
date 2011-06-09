package at.ac.tuwien.swag.webapp.service;

import java.util.Set;

import javax.jms.JMSException;

import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.messages.auth.AuthenticationReply;

public interface LoginService {

	/**
	 * Check if the credentials are valid
	 * @throws TimeoutExpiredException 
	 * @throws JMSException 
	 */
	AuthenticationReply authenticate( String username, String password ) throws AuthenticationException, 
	                                                                            TimeoutExpiredException, 
	                                                                            JMSException;

	/**
	 * Get a users roles
	 */
	Set<String> getRoles( String username );
	
}
