package at.ac.tuwien.swag.messages.auth;

import java.io.Serializable;

import at.ac.tuwien.swag.model.dto.UserDTO;

public class UpdateUserRequest implements Serializable {
	private static final long serialVersionUID = -3144089580289942553L;

	public UpdateUserRequest( UserDTO user ) {
		this.user = user;
	}

	public UserDTO user;
	
}
