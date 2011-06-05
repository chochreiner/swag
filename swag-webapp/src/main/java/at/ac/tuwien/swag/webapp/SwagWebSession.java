package at.ac.tuwien.swag.webapp;

import java.util.Set;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;

public final class SwagWebSession extends AbstractAuthenticatedWebSession {
	private static final long serialVersionUID = -5435752385275403581L;
	
	@Inject
	private LoginService login;
	
	public SwagWebSession( Request request ) {
		super( request );
	}

	// authentication authorization stuff
	public void logout() {
		login.logout();
	}


	@Override
	public boolean isSignedIn() {
		return login.isLoggedIn();
	}
	
	@Override
	public boolean authenticate( String username, String password ) {
		try {
			login.login( username, password );
			
			return true;
		} catch ( AuthorizationException e ) {
			return false;
		}
	}

	
	@Override
	public Roles getRoles() {
		Set<String> roles = login.getRoles();
		Roles r = new Roles();
		
		r.addAll( roles );
		
		return r;
	}

}
