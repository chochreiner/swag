package at.ac.tuwien.swag.webapp.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.webapp.service.LoginService;
import at.ac.tuwien.swag.webapp.service.PasswordHasher;

import com.google.inject.Inject;

public class LoginServiceImpl implements LoginService {

	@Inject
	private UserDAO users;
	
	@Inject
	private PasswordHasher hasher;
	
    @Override
    public boolean authenticate(String username, String password) {
    	try {
    		User user = users.findByUsername( username );

    		return hasher.checkPassword( password, user.getPassword() );
//    		return true;
    	} catch ( NoResultException e ) {
    		return false;
    	}
    }

    @Override
    public Set<String> getRoles(String username) {
        return new HashSet<String>(Arrays.asList(Roles.ADMIN, Roles.USER));
    }

}
