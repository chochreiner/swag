package at.ac.tuwien.swag.webapp;

import org.apache.wicket.Session;
import org.apache.wicket.request.ClientInfo;
import org.apache.wicket.request.Request;

import com.google.inject.Inject;

import at.ac.tuwien.swag.webapp.service.LoginService;

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
	
	@Inject
	private LoginService login;
	
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

	public void login( String username, String password ) throws AuthorizationException {
		if ( login.login( username, password ) ) {
			subject = new Subject( username, Role.USER );			
		}
	}
	
	public void logout() {
		login.logout();
		
		subject = null;
	}
	
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
