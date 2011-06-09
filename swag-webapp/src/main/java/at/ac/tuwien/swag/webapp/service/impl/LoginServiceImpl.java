package at.ac.tuwien.swag.webapp.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.persistence.NoResultException;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import at.ac.tuwien.swag.messages.AuthenticationReply;
import at.ac.tuwien.swag.messages.AuthenticationRequest;
import at.ac.tuwien.swag.messages.JMSHelper;
import at.ac.tuwien.swag.model.dao.UserDAO;
import at.ac.tuwien.swag.model.domain.User;
import at.ac.tuwien.swag.util.PasswordHasher;
import at.ac.tuwien.swag.webapp.service.LogService;
import at.ac.tuwien.swag.webapp.service.LoginService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginServiceImpl implements LoginService {

    @Inject
    private UserDAO users;

    @Inject
    private JMSHelper jms;
    @Inject
    @Named("swag.queue.Authentication")
    private Queue authentication;

    @Inject
    private PasswordHasher hasher;

    @Inject
    private LogService logger;

    public LoginServiceImpl() {

    }

    @Override
    public boolean authenticate(String username, String password) {
        try {
            AuthenticationReply auth = jms.request(authentication, new AuthenticationRequest(username, password));

            System.err.println(auth);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        try {
            User user = users.findByUsername(username);

            logger.logUserAction("Login", "User [" + username + "] logged in.");

            return hasher.checkPassword(password, user.getPassword());
            // return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public Set<String> getRoles(String username) {
        return new HashSet<String>(Arrays.asList(Roles.ADMIN, Roles.USER));
    }

}
