package at.ac.tuwien.swag.webapp;

import org.apache.wicket.Session;
import org.apache.wicket.request.ClientInfo;
import org.apache.wicket.request.Request;

public final class SwagWebSession extends Session {
	private static final long serialVersionUID = -5435752385275403581L;

	public enum Role {
		USER, ADMIN;
	}
	
	public static class Subject {
		public Subject( String username,  Role role ) {
			this.username = username;
			this.role     = role;
		}

		private final String username;
		private final Role   role;
	}
	
	private Subject subject;
	
	public SwagWebSession( Request request ) {
		super( request );
	}

	@Override
	public void cleanupFeedbackMessages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientInfo getClientInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	// authentication authorization stuff

	public boolean isLoggedIn() {
		return subject != null;
	}
	
	public Role getRole() throws AuthorizationException {
		if ( isLoggedIn() ) {
			return getSubject().role;
		} else  {
			throw new AuthorizationException("Not logged in");
		}
	}
	
	private Subject getSubject() throws AuthorizationException {
		if ( subject == null ) throw new AuthorizationException("Not logged in");
		
		return subject;
	}
	
}
