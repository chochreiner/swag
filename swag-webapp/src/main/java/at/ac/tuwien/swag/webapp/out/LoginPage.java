package at.ac.tuwien.swag.webapp.out;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.out.form.LoginForm;

public class LoginPage extends OutPage {
    private static final long serialVersionUID = -6712153643198619949L;

    public LoginPage(PageParameters parameters) {
        super(parameters);
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        LoginForm loginForm = new LoginForm( "loginForm" );
        
        add(loginForm);
    }
}
