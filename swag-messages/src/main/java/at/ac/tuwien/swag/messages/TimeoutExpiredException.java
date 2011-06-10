package at.ac.tuwien.swag.messages;

public class TimeoutExpiredException extends Exception {
	private static final long serialVersionUID = 8608043025692404517L;

	public TimeoutExpiredException() {
		super();
	}

	public TimeoutExpiredException( String message, Throwable cause ) {
		super( message, cause );
	}

	public TimeoutExpiredException( String message ) {
		super( message );
	}

	public TimeoutExpiredException( Throwable cause ) {
		super( cause );
	}
}
