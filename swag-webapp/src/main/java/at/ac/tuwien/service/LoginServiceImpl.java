package at.ac.tuwien.service;

import org.apache.wicket.util.lang.Objects;

public class LoginServiceImpl implements LoginService {

	public boolean login( String username, String password ) {
		//TODO: do real auth
		return Objects.equal( username, password ) ;
	}
	
}
