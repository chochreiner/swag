package at.ac.tuwien.out.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;

public class RegisterForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private RequiredTextField<String> firstname;
    private RequiredTextField<String> surname;
    private RequiredTextField<String> username;
    private RequiredTextField<String> email;
    private PasswordTextField password;
    private PasswordTextField password2;

    public RegisterForm(String id) {

        super(id);

        firstname = new RequiredTextField<String>("firstname", new Model<String>());
        surname = new RequiredTextField<String>("surname", new Model<String>());
        username = new RequiredTextField<String>("username", new Model<String>());
        email = new RequiredTextField<String>("email", new Model<String>());
        password = new PasswordTextField("password", new Model<String>());
        password2 = new PasswordTextField("password2", new Model<String>());

        add(firstname);
        add(surname);
        add(username);
        add(email);
        add(password);
        add(password2);
    }

    @Override
    protected void onSubmit() {
        info("the form was submitted!");
    }
}
