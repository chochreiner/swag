package at.ac.tuwien.swag.webapp.out;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.out.form.RegisterForm;

public class Register extends OutPage {
    private static final long serialVersionUID = -6712153643198619949L;

    public Register(PageParameters parameters) {
        super(parameters);
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        Form<?> registerForm = new RegisterForm("registerForm");
        add(registerForm);
    }
}
