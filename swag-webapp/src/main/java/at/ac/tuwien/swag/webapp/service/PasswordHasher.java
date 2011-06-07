package at.ac.tuwien.swag.webapp.service;

public interface PasswordHasher {

	byte[] generateSalt();
	
	byte[] hash( String password, byte[] salt );
	
}
