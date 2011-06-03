package at.ac.tuwien.out.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import at.ac.tuwien.in.InPage;
import at.ac.tuwien.service.LoginService;

public class LoginForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private static final String ERR_MSG_BAD_CREDENTIALS = "The password and/or username you entered were incorrect";
    
    private final TextField<String> username;
    private final PasswordTextField password;

    @Inject
    private LoginService login;
    
    public LoginForm(String id) {
        super(id);

        username = new TextField<String>("username", new Model<String>());
        password = new PasswordTextField("password", new Model<String>());

        add(username);
        add(password);
    }

    @Override
    protected void onSubmit() {
    	String usr = username.getModel().getObject();
    	String pwd = password.getModel().getObject();
    	
    	if ( login.login( usr, pwd ) ) {
    		getPage().setResponsePage( InPage.class );
    	} else {
    		this.error( ERR_MSG_BAD_CREDENTIALS );
    	}
    }
}
