package at.ac.tuwien.swag.webapp.out;

import javax.jms.JMSException;
import javax.jms.Queue;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.messages.auth.UserExistsRequest;
import at.ac.tuwien.swag.model.dto.UserDTO;
import at.ac.tuwien.swag.webapp.out.form.LoginForm;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginPage extends OutPage {
    private static final long serialVersionUID = -6712153643198619949L;

	@Inject( optional=true )
	@Named("swag.queue.Authentication")
	private Queue auth;
	
	@Inject
	private JMSHelper jms;
	
    public LoginPage(PageParameters parameters) {
        super(parameters);

        ensureAdminIsPresent();
        
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        LoginForm loginForm = new LoginForm( "loginForm" );
        
        add(loginForm);
    }

	private void ensureAdminIsPresent() {
		if ( auth == null ) {
			error( "Could not get JMS connection to auth-server, look into that! Try clicking 'insertTestData'" );
			return;
		}
		
		try {
			if ( !jms.request( auth, Boolean.class, new UserExistsRequest( "system" ) ) ) {
				jms.request( 
					auth, Boolean.class, 
					new UserDTO( 
						"system",
						"aaa", 
						"The interblag",
						"swag@swag.com", 
						"System administration account", 
						null ,null, null 
					)
				);
			}
		} catch ( JMSException e1 ) {
			error( "Could not get JMS connection to auth-server, look into that! Try clicking 'insertTestData'" );
		}
	}
}
