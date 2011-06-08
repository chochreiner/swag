package at.ac.tuwien.swag.webapp.service;

public interface PasswordHasher {

	String hash( String password );
	
	boolean checkPassword( String plaintextPwd, String storedHash );
	
}
