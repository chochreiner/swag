package at.ac.tuwien.swag.webapp.in.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.service.PasswordHasher;

import com.google.inject.Inject;

public class PasswordForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private final PasswordTextField password1;
    private final PasswordTextField password2;

    @Inject
    private UserDAO users;

    @Inject
    private PasswordHasher hasher;
   
    public PasswordForm(String id) {
        super(id);

        password1 = new PasswordTextField("password1", new Model<String>());
        password2 = new PasswordTextField("password2", new Model<String>());

        add( new EqualPasswordInputValidator( password1, password2 ) );
        
        add(password1);
        add(password2);
    }

    @Override
    protected void onSubmit() {
        String username = ((SwagWebSession) getSession()).getUsername();

        User user = users.findByUsername(username);

        String password = password1.getModel().getObject();
        
        user = users.findByUsername(username);
        user.setPassword( hasher.hash( password ) );

        users.beginTransaction();
        	users.update(user);
        users.commitTransaction();
        	
        info("Your password was updated");
    }
}
