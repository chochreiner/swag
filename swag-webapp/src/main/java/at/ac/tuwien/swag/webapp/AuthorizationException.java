package at.ac.tuwien.swag.webapp;

public class AuthorizationException extends Exception {
	private static final long serialVersionUID = 5014296826569255805L;

	public AuthorizationException() {
		super();
	}

	public AuthorizationException( String message, Throwable cause ) {
		super( message, cause );
	}

	public AuthorizationException( String message ) {
		super( message );
	}

	public AuthorizationException( Throwable cause ) {
		super( cause );
	}
}
