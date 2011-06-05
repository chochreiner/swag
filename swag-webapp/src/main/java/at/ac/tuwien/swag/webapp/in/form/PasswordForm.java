package at.ac.tuwien.swag.webapp.in.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.Model;

public class PasswordForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private final PasswordTextField password1;
    private final PasswordTextField password2;

    public PasswordForm(String id) {
        super(id);

        password1 = new PasswordTextField("password1", new Model<String>());
        password2 = new PasswordTextField("password2", new Model<String>());

        add(password1);
        add(password2);
    }

    @Override
    protected void onSubmit() {
        error("TDB");
    }
}
