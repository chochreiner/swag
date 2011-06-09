package at.ac.tuwien.swag.webapp;

import java.util.Set;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;

public final class SwagWebSession extends AuthenticatedWebSession {
    private static final long serialVersionUID = -5435752385275403581L;

    @Inject
    private LoginService login;

    public SwagWebSession(Request request) {
        super(request);
        username = null;
        mapname = null;
    }

    // authentication authorization stuff
    @Override
    public boolean authenticate(String username, String password) {
        if (login.authenticate(username, password)) {
            this.username = username;
            signIn(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void signOut() {
        super.signOut();
        username = null;
        mapname = null;
    }

    @Override
    public Roles getRoles() {
        if (!isSignedIn()) {
            return new Roles();
        }

        Set<String> roles = login.getRoles(username);
        Roles r = new Roles();

        r.addAll(roles);

        return r;
    }

    private String username;
    private String mapname;

    public String getUsername() {
        return username;
    }

	public String getMapname() {
		return mapname;
	}

	public void setMapname(String mapname) {
		this.mapname = mapname;
	}
}
