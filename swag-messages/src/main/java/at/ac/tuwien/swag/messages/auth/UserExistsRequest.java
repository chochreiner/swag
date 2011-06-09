package at.ac.tuwien.swag.messages.auth;

import java.io.Serializable;

public class UserExistsRequest implements Serializable {
	private static final long serialVersionUID = -8011170978399971447L;

	public final String username;
	
	public UserExistsRequest( String username ) {
		this.username = username;
	}
}
