package at.ac.tuwien.swag.webapp.common;

import org.apache.wicket.Session;
import org.apache.wicket.request.ClientInfo;
import org.apache.wicket.request.Request;

public class SwagWebSession extends Session {
	private static final long serialVersionUID = -5435752385275403581L;

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
}
