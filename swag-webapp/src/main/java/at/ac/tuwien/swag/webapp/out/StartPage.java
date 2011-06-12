package at.ac.tuwien.swag.webapp.out;

import javax.jms.JMSException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.webapp.service.ExecutorService;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;

public class StartPage extends OutPage {
	private static final long serialVersionUID = 1000701427791102913L;

	@Inject
	private ExecutorService exec;
	@Inject
	private LoginService login;
	
	public StartPage(PageParameters parameters) {
        super(parameters);    
        
        try {
        	// ping services so glassfish starts the beans
        	login.userExists( "system" );
        	exec.ping();
        } catch ( JMSException e ) {
			e.printStackTrace();
		} catch ( TimeoutExpiredException e ) {
			e.printStackTrace();
		}
	}
}
