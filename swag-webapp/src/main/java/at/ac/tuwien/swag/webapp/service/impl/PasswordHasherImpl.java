package at.ac.tuwien.swag.webapp.service.impl;

import at.ac.tuwien.swag.webapp.service.PasswordHasher;

public class PasswordHasherImpl implements PasswordHasher {

	public String hash( String password ) {
		return password;
	}
	
}
