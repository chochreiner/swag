package at.ac.tuwien.swag.webapp.service;

public class AuthenticationException extends Exception {
	private static final long serialVersionUID = -1494733820276363575L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException( String message, Throwable cause ) {
		super( message, cause );
	}

	public AuthenticationException( String message ) {
		super( message );
	}

	public AuthenticationException( Throwable cause ) {
		super( cause );
	}
}
