package at.ac.tuwien.swag.webapp.service;

import java.util.Set;

public interface LoginService {

	/**
	 * Check if the credentials are valid
	 */
	boolean authenticate( String username, String password );

	/**
	 * Get a users roles
	 */
	Set<String> getRoles( String username );
	
}
