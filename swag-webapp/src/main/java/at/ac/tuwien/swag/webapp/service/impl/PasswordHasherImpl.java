package at.ac.tuwien.swag.webapp.service.impl;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.wicket.util.lang.Objects;

import at.ac.tuwien.swag.webapp.service.PasswordHasher;

public class PasswordHasherImpl implements PasswordHasher {

	public PasswordHasherImpl() {
		try {
			this.random  = new SecureRandom();
			this.digest  = MessageDigest.getInstance( HASH_ALGORITHM );
			this.charset = Charset.forName( CHARSET );
		} catch ( NoSuchAlgorithmException e ) {
			throw new RuntimeException( e );
		}
	}
	
	@Override
	public String hash( String password ) {
		return hash( password, generateSalt() );
	}

	public boolean checkPassword( String plaintextPwd, String storedHash ) {
		byte[] storedBytes = toBytes( storedHash );
		storedBytes = Base64.decodeBase64( storedBytes );
		
		byte[] salt = extractSalt( storedBytes );
		
		return Objects.equal( hash( plaintextPwd, salt ), storedHash );
	}
	
	//***** PRIVATE PARTS 
	
	private String hash( String password, byte[] salt ) {
		byte[] pwd = toBytes( password );

		// hash with salt
		digest.update( salt );
		digest.update( pwd );			
		pwd = digest.digest();

		// re-hash multiple times
		for ( int i = 0; i < NUM_HASHING_ITERATIONS; i++ ) {
			digest.update( pwd );
			pwd = digest.digest();
		}
				
		// prepend salt and base 64 encode
		pwd = prependSalt( salt, pwd );
		pwd = Base64.encodeBase64( pwd );
	
		return fromBytes( pwd );
	}
	
	// string <--> byte[]
	private byte[] toBytes( String str ) {
		return str.getBytes( charset );
	}
	private String fromBytes( byte[] bs ) {
		return new String( bs, charset );
	}

	// insert/extract salt
	private byte[] prependSalt( byte[] salt, byte[] pwd ) {
		byte[] out = Arrays.copyOf( salt, salt.length + pwd.length );
		
		System.arraycopy( pwd, 0, salt, salt.length, pwd.length );
		
		return out;
	}
	private byte[] extractSalt( byte[] pwd ) {
		return Arrays.copyOf( pwd, SALT_LENGTH_IN_BYTES );
	}
	
	private byte[] generateSalt() {
		byte[] salt = new byte[SALT_LENGTH_IN_BYTES];
		
		random.nextBytes( salt );
		
		return salt;
	}
	
	// constants
	private final static String HASH_ALGORITHM = "SHA-256";
	private final static String CHARSET        = "UTF-8";
	
	private final static int    SALT_LENGTH_IN_BYTES   = 32;
	private final static int    NUM_HASHING_ITERATIONS = 1024;
	
	// members
	private final SecureRandom  random;
	private final MessageDigest digest;
	private final Charset       charset;
}
