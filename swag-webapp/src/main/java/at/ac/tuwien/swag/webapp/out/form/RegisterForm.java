package at.ac.tuwien.swag.webapp.out.form;

import javax.persistence.NoResultException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.webapp.service.PasswordHasher;

import com.google.inject.Inject;

public class RegisterForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private RequiredTextField<String> firstname;
    private RequiredTextField<String> surname;
    private RequiredTextField<String> email;
    private RequiredTextField<String> address;
    private RequiredTextField<String> username;
    private PasswordTextField password;
    private PasswordTextField password2;

    @Inject
    private UserDAO userDAO;
    @Inject
    private PasswordHasher hasher;
    
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
					userDAO.findByUsername( username );
				} catch ( NoResultException e ) {
					error( validatable, "userNameExists" );
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
    	                      .password( hasher.hash( password ) );

    	userDAO.beginTransaction();
    		userDAO.insert( user );
    	userDAO.commitTransaction();
    		
    	getPage().setResponsePage( getApplication().getHomePage() );
    	info( "Thank you for registering" );
    }
        
}
