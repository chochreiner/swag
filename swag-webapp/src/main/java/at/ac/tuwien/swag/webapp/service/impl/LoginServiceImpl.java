package at.ac.tuwien.swag.webapp.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.util.lang.Objects;

import at.ac.tuwien.swag.webapp.service.LoginService;

public class LoginServiceImpl implements LoginService {

    @Override
    public boolean authenticate(String username, String password) {
        return Objects.equal(username, password);
    }

    @Override
    public Set<String> getRoles(String username) {
        return new HashSet<String>(Arrays.asList(Roles.ADMIN, Roles.USER));
    }

}
