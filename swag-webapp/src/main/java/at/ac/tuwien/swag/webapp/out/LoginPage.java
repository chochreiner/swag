package at.ac.tuwien.swag.webapp.out;

import javax.jms.JMSException;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.webapp.out.form.LoginForm;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;

public class LoginPage extends OutPage {
    private static final long serialVersionUID = -6712153643198619949L;

    @Inject( optional = true )
    private LoginService login;
    
    public LoginPage(PageParameters parameters) {
        super(parameters);

        ensureAdminIsPresent();
        
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        LoginForm loginForm = new LoginForm( "loginForm" );
        
        add(loginForm);
    }

	private void ensureAdminIsPresent() {
		if ( login == null ) {
			error( "Could not get JMS connection to auth-server, look into that! Try clicking 'insertTestData'" );
			return;
		}
		
		try {
			if ( !login.userExists( "system" ) ) {
				User system = new User(
						"system",
						"aaa", 
						"The interblag",
						"swag@swag.com", 
						"System administration account", 
						null ,null, null 
				);
				
				login.storeUser( system );
			}
		} catch ( JMSException e ) {
			error( "Could not get JMS connection to auth-server, look into that! Try clicking 'insertTestData'" );			
		} catch ( TimeoutExpiredException e ) {
			error( "Connection to authentication server timed out" );
		}
	}
}
