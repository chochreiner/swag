package at.ac.tuwien.swag.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.JMSException;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.messages.auth.AuthenticationReply;
import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.model.dto.SquareDTO;
import at.ac.tuwien.swag.webapp.service.AuthenticationException;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;

public final class SwagWebSession extends AuthenticatedWebSession {
    private static final long serialVersionUID = -5435752385275403581L;

    @Inject
    private LoginService login;

    @Inject
    MapDAO mapDao;
    
    public SwagWebSession(Request request) {
        super(request);
        mapname = null;
    }

    /**
     * Ping auth server so glassfish instantiates it.
     */
    public void ping() {
        try {
			login.userExists( "system" );
		} catch ( JMSException e ) {
		} catch ( TimeoutExpiredException e ) {
		}
    }
    
    // authentication authorization stuff
    @Override
    public boolean authenticate(String username, String password) {
    	try {
			credentials = login.authenticate( username, password );
			
			 // If a map exist set the first one
	        String query = "SELECT mu.map FROM MapUser mu JOIN mu.user u WHERE u.username=:username";
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("username", username);
	        List<Map> maps = mapDao.findByQuery(query, params);
	        if(!maps.isEmpty()){
	        	this.mapname = maps.get(0).getName();
	        }
			
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

        String user = null;
        
        if ( credentials != null )
        	user = credentials.username;
        
        credentials 		= null;
        mapname     		= null;
        homebase    		= null;
        selectedSquareId	= 0;

        if ( user != null )
        	login.logout( user );
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
    private long selectedSquareId;
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

	public long getSelectedSquareId() {
		return selectedSquareId;
	}

	public void setSelectedSquareId(long selectedSquareId) {
		this.selectedSquareId = selectedSquareId;
	}
}
