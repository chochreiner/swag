package at.ac.tuwien.swag.webapp;

import javax.jms.JMSException;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.messages.auth.AuthenticationReply;
import at.ac.tuwien.swag.model.dto.SquareDTO;
import at.ac.tuwien.swag.webapp.service.AuthenticationException;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;

public final class SwagWebSession extends AuthenticatedWebSession {
    private static final long serialVersionUID = -5435752385275403581L;

    @Inject
    private LoginService login;

    public SwagWebSession(Request request) {
        super(request);
        mapname = null;
    }

    // authentication authorization stuff
    @Override
    public boolean authenticate(String username, String password) {
    	try {
			credentials = login.authenticate( username, password );
			
            signIn(true);
            return true;
		} catch ( AuthenticationException e ) {
			error( e.getMessage() );
            return false;
		} catch ( TimeoutExpiredException e ) {
			error( "The connection to the authentication server timed out" );
            return false;
		} catch ( JMSException e ) {
			error( "An error occured while connecting to the authentication server" );
			return false;
		}
    }

    @Override
    public void signOut() {
        super.signOut();
        
        credentials = null;
        mapname     = null;
        homebase    = null;
    }

    @Override
    public Roles getRoles() {
        if (!isSignedIn()) {
            return new Roles();
        }

        return new Roles( credentials.roles );
    }

    private AuthenticationReply credentials;
    private String mapname;
    private SquareDTO homebase;

    public String getUsername() {
        return credentials.username;
    }

	public String getMapname() {
		return mapname;
	}

	public void setMapname(String mapname) {
		this.mapname = mapname;
	}

	public SquareDTO getHomebase() {
		return homebase;
	}

	public void setHomebase(SquareDTO homebase) {
		this.homebase = homebase;
	}
}
