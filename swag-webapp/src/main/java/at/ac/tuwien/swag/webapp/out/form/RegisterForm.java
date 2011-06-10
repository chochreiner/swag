package at.ac.tuwien.swag.webapp.out.form;

import javax.jms.JMSException;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import at.ac.tuwien.swag.messages.TimeoutExpiredException;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;

public class RegisterForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private static final String JMS_ERR_MSG = "An error occured while connecting to the user data server";
    private static final String TIMEOUT_MSG = "Connection to the user data server timed out";
    
    private RequiredTextField<String> firstname;
    private RequiredTextField<String> surname;
    private RequiredTextField<String> email;
    private RequiredTextField<String> address;
    private RequiredTextField<String> username;
    private PasswordTextField password;
    private PasswordTextField password2;

    @Inject
    private LoginService login;
    
    public RegisterForm(String id) {
        super(id);

        firstname = new RequiredTextField<String>("firstname", new Model<String>());
        surname   = new RequiredTextField<String>("surname", new Model<String>());
        email     = new RequiredTextField<String>("email", new Model<String>());

        address   = new RequiredTextField<String>("address", new Model<String>() );
        
        username  = new RequiredTextField<String>("username", new Model<String>());

        password  = new PasswordTextField("password", new Model<String>());
        password2 = new PasswordTextField("password2", new Model<String>());
        
        // validators
        username.add( new AbstractValidator<String>() {
			private static final long serialVersionUID = -823294603268753833L;

			@Override
			protected void onValidate( IValidatable<String> validatable ) {
				String username = validatable.getValue();
				
				try {
					if ( login.userExists( username ) ) {
						validatable.error( new ValidationError().setMessage( "Username exists" ) );
					}
				} catch ( JMSException e ) {
					validatable.error( new ValidationError().setMessage( JMS_ERR_MSG ) );
				} catch ( TimeoutExpiredException e ) {
					validatable.error( new ValidationError().setMessage( TIMEOUT_MSG ) );
				}
			}
        });
        
        this.add( new EqualPasswordInputValidator( password, password2 ) );
        email.add( EmailAddressValidator.getInstance() );
        
        add(firstname);
        add(surname);
        add(username);
        add(email);
        add(address);
        add(password);
        add(password2);
    }

    @Override
    protected void onSubmit() {
    	String username  = this.username.getModel().getObject();
    	String password  = this.password.getModel().getObject();
    	
    	String fullname = this.firstname.getModel().getObject() + " " + this.surname.getModel().getObject();
    	String address  = this.address.getModel().getObject();
    	
    	String email    = this.email.getModel().getObject();
    	
    	User user = new User().username( username )
    	                      .fullname( fullname )
    	                      .address( address )
    	                      .email( email )
    	                      .password( password );
    	
    	try {
			login.storeUser( user );
		} catch ( JMSException e ) {
			error( JMS_ERR_MSG );
			return;
		} catch ( TimeoutExpiredException e ) {
			error( TIMEOUT_MSG );
			return;
		}
    		
    	getPage().setResponsePage( getApplication().getHomePage() );
    	info( "Thank you for registering" );
    }
        
}
