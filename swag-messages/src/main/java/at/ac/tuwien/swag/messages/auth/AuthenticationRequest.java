package at.ac.tuwien.swag.messages.auth;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
	private static final long serialVersionUID = -946887692877725788L;

	public final String username;
	public final String password;

	public AuthenticationRequest( String username, String password ) {
		this.username = username;
		this.password = password;
	}	
}
