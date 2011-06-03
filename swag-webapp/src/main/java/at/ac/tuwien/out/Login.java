package at.ac.tuwien.out;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class Login extends OutPage {

    public Login(PageParameters parameters) {
        super(parameters);
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        Form loginForm = new Form("loginForm") {
            @Override
            protected void onSubmit() {
                info("the form was submitted!");
            }
        };

        loginForm.add(new TextField<String>("name"));
        loginForm.add(new PasswordTextField("password"));

        add(loginForm);

    }
}
