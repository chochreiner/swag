package at.ac.tuwien.swag.webapp.service;

import java.util.Set;

import at.ac.tuwien.swag.webapp.AuthorizationException;

public interface LoginService {

	void login( String username, String password ) throws AuthorizationException;

	void logout();
	
	boolean isLoggedIn();

	Set<String> getRoles();
	
}
