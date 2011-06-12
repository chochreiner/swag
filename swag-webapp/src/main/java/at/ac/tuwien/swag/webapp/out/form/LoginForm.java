package at.ac.tuwien.swag.webapp.out.form;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.MainPage;

public class LoginForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

//    private static final String ERR_MSG_BAD_CREDENTIALS = "The password and/or username you entered were incorrect";
    
    private final TextField<String> username;
    private final PasswordTextField password;
    
    public LoginForm(String id) {
        super(id);

        username = new RequiredTextField<String>("username", new Model<String>());
        password = new PasswordTextField("password", new Model<String>());
        
        ((SwagWebSession) getSession()).ping();
        
        add(username);
        add(password);
    }

    @Override
    protected void onSubmit() {
    	String usr = username.getModel().getObject();
    	String pwd = password.getModel().getObject();
    	
   		if ( ((AbstractAuthenticatedWebSession) getSession()).authenticate( usr, pwd ) ) {
   			getPage().setResponsePage( MainPage.class );    			
//   		} else {
//   			this.error( ERR_MSG_BAD_CREDENTIALS );    		    			
   		}
    }
}
