package at.ac.tuwien.swag.messages.auth;

import java.io.Serializable;

public class IsOnlineRequest implements Serializable {
	private static final long serialVersionUID = 5272847469104755644L;
	
	public IsOnlineRequest( String username ) {
		super();
		this.username = username;
	}

	public String username;
	
}
