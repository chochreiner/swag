package at.ac.tuwien.swag.messages.auth;

import java.io.Serializable;

public class LogoutRequest implements Serializable {
	private static final long serialVersionUID = 6280198907761324783L;

	public LogoutRequest( String username ) {
		super();
		this.username = username;
	}

	public String username;
}
