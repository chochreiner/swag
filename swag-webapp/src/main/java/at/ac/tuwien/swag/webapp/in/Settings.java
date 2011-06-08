package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.in.form.DeleteUserForm;
import at.ac.tuwien.swag.webapp.in.form.PasswordForm;
import at.ac.tuwien.swag.webapp.in.form.SettingsForm;

public class Settings extends InPage {
    private static final long serialVersionUID = -7581862576542602255L;

    public Settings(PageParameters parameters) {
        super(parameters);
        
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        Form<?> settingsForm = new SettingsForm("settingsForm");
        add(settingsForm);

        Form<?> passwordForm = new PasswordForm("passwordForm");
        add(passwordForm);

        Form<?> deleteUserForm = new DeleteUserForm( "deleteUser" );
        add(deleteUserForm);
    }

}
