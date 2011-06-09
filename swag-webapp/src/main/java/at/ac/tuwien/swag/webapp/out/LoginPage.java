package at.ac.tuwien.swag.webapp.out;

import javax.persistence.NoResultException;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.util.PasswordHasher;
import at.ac.tuwien.swag.webapp.in.MainPage;
import at.ac.tuwien.swag.webapp.out.form.LoginForm;

import com.google.inject.Inject;

public class LoginPage extends OutPage {
    private static final long serialVersionUID = -6712153643198619949L;

	@Inject( optional = true)
	private UserDAO users;

	@Inject
    private PasswordHasher hasher;
	
    public LoginPage(PageParameters parameters) {
        super(parameters);

        ensureAdminIsPresent();
        
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        LoginForm loginForm = new LoginForm( "loginForm" );
        
        add(loginForm);
    }

	private void ensureAdminIsPresent() {
		if ( users == null ) {
			error( "The are database problems, look into that! Try clicking 'insertTestData'" );
			setResponsePage( MainPage.class );
		}
		
		try {
			users.findByUsername( "system" );
		} catch ( NoResultException e ) {
			User system = new User();
			system.setUsername( "system" );
			system.setPassword( hasher.hash( "aaa" ) );
			system.setEmail( "swag@swag.com" );
			
			system.address( "The interblag" );
			system.fullname( "System administration account" );
			
			users.beginTransaction();
				users.insert( system );
			users.commitTransaction();
		}
	}
}
