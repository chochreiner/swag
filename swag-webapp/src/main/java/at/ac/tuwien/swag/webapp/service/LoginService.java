package at.ac.tuwien.swag.webapp.service;

import at.ac.tuwien.swag.webapp.AuthorizationException;

public interface LoginService {

	boolean login( String username, String password ) throws AuthorizationException;

	void logout();
	
}
