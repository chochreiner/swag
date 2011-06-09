package at.ac.tuwien.swag.messages.auth;

import java.io.Serializable;

public class AuthenticationReply implements Serializable {
	private static final long serialVersionUID = -6414167636717668051L;

	public final String   username;
	public final String[] roles;
	public final String   token;

	public AuthenticationReply( String username, String[] roles, String token ) {
		super();
		this.username = username;
		this.roles    = roles;
		this.token    = token;
	}
}
