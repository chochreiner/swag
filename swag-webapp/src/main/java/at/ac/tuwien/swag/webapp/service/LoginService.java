package at.ac.tuwien.swag.webapp.service;

import javax.jms.JMSException;

import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.messages.auth.AuthenticationReply;
import at.ac.tuwien.swag.model.domain.User;

public interface LoginService {

	/**
	 * Check if the credentials are valid
	 * @throws TimeoutExpiredException 
	 * @throws JMSException 
	 */
	AuthenticationReply authenticate( String username, String password ) throws AuthenticationException, 
	                                                                            TimeoutExpiredException, 
	                                                                            JMSException;

	boolean userExists( String username ) throws JMSException, TimeoutExpiredException;

	void storeUser( User user ) throws JMSException, TimeoutExpiredException;
	
}
