package at.ac.tuwien.swag.webapp.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.util.lang.Objects;

import at.ac.tuwien.swag.webapp.AuthorizationException;

public class LoginServiceImpl implements LoginService {

	public void login( String username, String password ) throws AuthorizationException {
		//TODO: do real auth
		
		if ( !Objects.equal( username, password ) ) {
			throw new AuthorizationException( "Username and password must be equal. TODO: change that" );
		} else {
			this.username = username;
		}
	}

	public void logout() {
		//TODO: do real auth
		username = null;
	}


	@Override
	public boolean isLoggedIn() {
		return username != null;
	}

	@Override
	public Set<String> getRoles() {
		return new HashSet<String>( Arrays.asList( "ADMIN", "USER" ) );
	}
	
	private String username;
	
}
