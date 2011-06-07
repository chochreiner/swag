package at.ac.tuwien.swag.webapp.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import at.ac.tuwien.swag.webapp.service.PasswordHasher;

public class PasswordHasherImpl implements PasswordHasher {

	public PasswordHasherImpl() {
		try {
			this.digest = MessageDigest.getInstance( "SHA-256" );
		} catch ( NoSuchAlgorithmException e ) {
			throw new RuntimeException( e );
		}
	}
	
	@Override
	public byte[] generateSalt() {
		byte[] salt = new byte[SALT_LENGTH_IN_BYTES];
		
		random.nextBytes( salt );
		
		return salt;
	}
	

	@Override
	public byte[] hash( String password, byte[] salt ) {
		try {
			byte[] pwd = password.getBytes( "UTF-8" );

			digest.update( salt );
			digest.update( pwd );
			
			byte[] bytes = digest.digest();
			
			for ( int i = 0; i < NUM_HASHING_ITERATIONS; i++ ) {
				digest.update( bytes );
				bytes = digest.digest();
			}
			
			//TODO: base 64 encode
			return bytes;
		} catch ( UnsupportedEncodingException e ) {
			throw new RuntimeException( e );
		}
	}

	private final static int SALT_LENGTH_IN_BYTES   = 32;
	private final static int NUM_HASHING_ITERATIONS = 1024;
	
	private final SecureRandom  random = new SecureRandom();
	private final MessageDigest digest;
}
