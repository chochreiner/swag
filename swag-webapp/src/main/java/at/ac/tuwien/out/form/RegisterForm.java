package at.ac.tuwien.out.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class RegisterForm extends Form {
    private static final long serialVersionUID = 6040480253914226510L;

	private TextField<String> firstname;
	private TextField<String> surname;
	private TextField<String> username;
	private TextField<String> email;
	private PasswordTextField password;
	private PasswordTextField password2;

    public RegisterForm(String id) {
        
    	super(id);
        
        firstname	= new TextField<String>("firstname", new Model<String>());
        surname		= new TextField<String>("surname", new Model<String>());
        username	= new TextField<String>("username", new Model<String>());
        email		= new TextField<String>("email", new Model<String>());
        password	= new PasswordTextField("password", new Model<String>());
        password2	= new PasswordTextField("password2", new Model<String>());

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
