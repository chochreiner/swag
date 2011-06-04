package at.ac.tuwien.swag.webapp.service;

import org.apache.wicket.util.lang.Objects;

public class LoginServiceImpl implements LoginService {

	public boolean login( String username, String password ) {
		//TODO: do real auth
		return Objects.equal( username, password ) ;
	}

	public void logout() {
		//TODO: do real auth		
	}
	
}
